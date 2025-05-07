/*
 * Copyright 2025 OmniOne.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.omnione.did.zkp.crypto.signature;

import org.omnione.did.zkp.datamodel.proof.NonCredentialSchema;
import org.omnione.did.zkp.datamodel.proofrequest.SubProofRequest;
import org.omnione.did.zkp.exception.ZkpErrorCode;
import org.omnione.did.zkp.exception.ZkpException;
import org.omnione.did.zkp.core.util.bulider.ChallengeBuilder;
import org.omnione.did.zkp.crypto.constant.ZkpCryptoConstants;
import org.omnione.did.zkp.crypto.keypair.CredentialPrimaryPublicKey;
import org.omnione.did.zkp.crypto.util.BigIntegerUtil;
import org.omnione.did.zkp.datamodel.util.GsonWrapper;
import org.omnione.did.zkp.datamodel.definition.CredentialDefinition;
import org.omnione.did.zkp.datamodel.proof.*;
import org.omnione.did.zkp.datamodel.proof.verifyparam.ProofVerifyParam;
import org.omnione.did.zkp.datamodel.proofrequest.AttributeInfo;
import org.omnione.did.zkp.datamodel.proofrequest.PredicateInfo;
import org.omnione.did.zkp.datamodel.proofrequest.ProofRequest;
import org.omnione.did.zkp.datamodel.proofrequest.RequestedProof;
import org.omnione.did.zkp.datamodel.schema.CredentialSchema;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class ProofVerifier {

    private static Vector<BigInteger> _verify_ne_predicate(CredentialPrimaryPublicKey p_pub_key,
                                                           PrimaryPredicateInequalityProof proof, BigInteger c_hash) throws ZkpException {


        Vector<BigInteger> tau_list = BigIntegerUtil.calc_tne(p_pub_key, proof.getU(), proof.getR(), proof.getMj(),
                proof.getAlpha(), proof.getT(), proof.getPredicate().isLess());

        for (int i = 0; i < ZkpCryptoConstants.ITERATION; i++) {
            BigInteger cur_t = proof.getT().get(Integer.toString(i));
            if (cur_t == null)
                throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_NULL, "Value by key [" + i + "] not found in proof.t ");

            BigInteger tau = cur_t.modPow(c_hash, p_pub_key.getN()).modInverse(p_pub_key.getN())
                    .multiply(tau_list.get(i)).mod(p_pub_key.getN());
            tau_list.set(i, tau);
        }

        BigInteger delta = proof.getT().get(ZkpCryptoConstants.DELTA);
        if (delta == null)
            throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_NULL, "Value by key DELTA not found in proof.t ");

        BigInteger delta_prime = (proof.getPredicate().isLess() == true) ? delta.modInverse(p_pub_key.getN()) : delta;

        BigInteger tau = p_pub_key.getZ().modPow(proof.getPredicate().getDeltaPrime(), p_pub_key.getN())
                .multiply(delta_prime).modPow(c_hash, p_pub_key.getN()).modInverse(p_pub_key.getN())
                .multiply(tau_list.get(ZkpCryptoConstants.ITERATION)).mod(p_pub_key.getN());

        tau_list.set(ZkpCryptoConstants.ITERATION, tau);

        tau = delta.modPow(c_hash, p_pub_key.getN()).modInverse(p_pub_key.getN())
                .multiply(tau_list.get(ZkpCryptoConstants.ITERATION + 1)).mod(p_pub_key.getN());

        tau_list.set(ZkpCryptoConstants.ITERATION + 1, tau);

        return tau_list;

    }

    private static Vector<BigInteger> _verify_equality(CredentialPrimaryPublicKey p_pub_key,
                                                       PrimaryEqualProof proof,
                                                       BigInteger c_hash,
                                                       CredentialSchema cred_schema,
                                                       NonCredentialSchema non_cred_schema,
                                                       SubProofRequest sub_proof_request) throws ZkpException {

        HashSet<String> unrevealed_attrs = new HashSet<String>();

        Set<String> nonCredSchemaList = non_cred_schema.getNonCredSchema();
        List<String> credSchemaList = cred_schema.getAttrNames();

        unrevealed_attrs.addAll(nonCredSchemaList);
        unrevealed_attrs.addAll(credSchemaList);

        unrevealed_attrs.removeAll(sub_proof_request.getRevealedAttrs());

        BigInteger t1 = BigIntegerUtil.calc_teq(p_pub_key, proof.getaPrime(), proof.getE(), proof.getV(), proof.getM(), proof.getM2(), unrevealed_attrs);

        BigInteger rar = proof.getaPrime().modPow(ZkpCryptoConstants.LARGE_E_START_VALUE, p_pub_key.getN());

        for (String attr : proof.getRevealedAttrs().keySet()) {

            BigInteger encoded_value = proof.getRevealedAttrs().get(attr);
            BigInteger cur_r = p_pub_key.getR().get(attr);

            if (cur_r == null)
                throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_NULL, "Value by key '{}' not found in pk.r ");

            rar = rar.multiply(cur_r.modPow(encoded_value, p_pub_key.getN())).mod(p_pub_key.getN());
        }

        BigInteger t2 = rar.modInverse(p_pub_key.getN()).multiply(p_pub_key.getZ()).modPow(c_hash, p_pub_key.getN()).modInverse(p_pub_key.getN());
        Vector<BigInteger> t_list = new Vector<BigInteger>();
        t_list.add(t1.multiply(t2).mod(p_pub_key.getN()));
        return t_list;

    }

    private static void checkAddSubProofRequestParamsConsistency(RequestedProof requestedProof,
                                                                 ProofRequest proofRequest,
                                                                 SubProofRequest sub_proof_request,
                                                                 CredentialSchema cred_schema) throws ZkpException {
        List<String> attrSet = cred_schema.getAttrNames();

        if (attrSet.containsAll(sub_proof_request.getRevealedAttrs()) == false)
            throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_VERIFIER_NOT_SATISFIED_ATTRIBUTE_IN_SUB_PROOF, "Credential doesn't contain requested attribute");

        System.out.println("sub_proof_request: "+sub_proof_request);
        for (Predicate entry : sub_proof_request.getPredicates()) {
            if (attrSet.contains(entry.getAttrName()) == false)
                throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_VERIFIER_NOT_SATISFIED_ATTRIBUTE_IN_SUB_PROOF, "Credential doesn't contain attribute requested in predicate");
        }

        Set<String> proofRequestKeySet = new HashSet<String>();
        Map<String, AttributeInfo> requestedAttributes = proofRequest.getRequestedAttributes();
        Map<String, PredicateInfo> predicateAttributes = proofRequest.getRequestedPredicates();
        for (String key: requestedAttributes.keySet()) {
            if (requestedAttributes.get(key).getRestrictions().size() > 0) {
                proofRequestKeySet.add(key);
            }
        }

        for (String key: predicateAttributes.keySet()) {
            if (predicateAttributes.get(key).getRestrictions().size() > 0) {
                proofRequestKeySet.add(key);
            }
        }
        
        Set<String> reqProofKeySet = new HashSet<String>();

        Map<String, Map<String, String>> revealedAttrs = requestedProof.getRevealedAttrs();
        for (String revealedKey : revealedAttrs.keySet()) {
            reqProofKeySet.add(revealedKey);
        }

        Map<String, Map<String, String>> unrevealedAttrs = requestedProof.getUnrevealedAttrs();
        for (String unrevealedKey : unrevealedAttrs.keySet()) {
            reqProofKeySet.add(unrevealedKey);
        }

        Map<String, Map<String, String>> predicateAttrs = requestedProof.getPredicates();
        for (String predicateKey : predicateAttrs.keySet()) {
            reqProofKeySet.add(predicateKey);
        }
        System.out.println("requestedProof KeyList: "+GsonWrapper.getGsonPrettyPrinting().toJson(reqProofKeySet));
        /**
         * 사용자가 선택한 데이터(requested_proof in proof)가 proofRequest 의 조건에 만족하는지 체크
         **/

//        for (String ref : proofRequestKeySet) {
//            try {
//                if (!reqProofKeySet.contains(ref))
//                    throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_VERIFIER_NOT_SATISFIED_ATTRIBUTE_IN_SUB_PROOF, ref + " doesn't exist in requested proof");
//            } catch (IOException e) {
//                throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_VERIFIER_NOT_SATISFIED_ATTRIBUTE_IN_SUB_PROOF, ref + " doesn't exist in requested proof");
//            }
//        }
    }

    public static boolean verify(ProofRequest proofRequest, Proof proof, List<ProofVerifyParam> proofVerifyParams, BigInteger nonce) throws ZkpException {

        ChallengeBuilder builder = new ChallengeBuilder();

        System.out.println("the number of certificates included is "+proof.getProofs().size());
        for (int i = 0 ; i < proof.getProofs().size(); i++) {
            System.out.println("#################### certificate "+i+" start");
            CredentialSchema schema = null;
            CredentialDefinition credentialDefinition = null;

            if (proof.getIdentifiers().size() == 0)
                throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_NULL, "check verify [proof.getIdentifiers() is null]");

            Identifiers identifiers = proof.getIdentifiers().get(i);

            for (ProofVerifyParam proofVerifyParam : proofVerifyParams) {
                System.out.println("proofVerifyParam: "+GsonWrapper.getGsonPrettyPrinting().toJson(proofVerifyParam));
                if (identifiers.getSchemaId().equals(proofVerifyParam.getSchema().getId()))
                    schema = proofVerifyParam.getSchema();
                if (identifiers.getCredDefId().equals(proofVerifyParam.getCredentialDefinition().getId()))
                    credentialDefinition = proofVerifyParam.getCredentialDefinition();

            }

            if (schema == null)
                throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_NULL, "check verify [schema is null]");
            else if (credentialDefinition == null)
                throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_NULL, "check verify [credentialDefinition is null]");

            Vector<byte[]> tauList = new Vector<byte[]>();
            Vector<BigInteger> tList = new Vector<BigInteger>();

            SubProof proofItem = proof.getProofs().get(i);

            PrimaryProof primaryProof = proofItem.getPrimaryProof();

            NonCredentialSchema nonCredSchema = new NonCredentialSchema();
            nonCredSchema.addAttr(ZkpCryptoConstants.MASTER_SECRET_KEY);

            SubProofRequest subProofRequest = proofRequest.getSubProofRequest(i, proof.getRequestedProof());
            System.out.println("verify subProofRequest: "+GsonWrapper.getGsonPrettyPrinting().toJson(subProofRequest));

            checkAddSubProofRequestParamsConsistency(proof.getRequestedProof(), proofRequest, subProofRequest, schema);

            Vector<BigInteger> t_hat = _verify_equality(credentialDefinition.getValue().getPrimary(),
                    primaryProof.getEqProof(),
                    proof.getAggregatedProof().getcHash(),
                    schema,
                    nonCredSchema,
                    subProofRequest);

            for (PrimaryPredicateInequalityProof neProof : primaryProof.getNeProofs()) {
                t_hat.addAll(_verify_ne_predicate(credentialDefinition.getValue().getPrimary(), neProof, proof.getAggregatedProof().getcHash()));
            }

            tList.addAll(t_hat);
            builder.add(tList);
            System.out.println("#################### certificate "+i+" end");
        }

        builder.add(proof.getAggregatedProof().getcList());
        builder.add(nonce);

        BigInteger c_hver = builder.buildWithHashing();

        if (!c_hver.equals(proof.getAggregatedProof().getcHash())) {
            System.out.println("verifier proof verify [c_hver, proof.getAggregatedProof().getcHash()] not match " + "c_hver: "+c_hver + ", c_hash: "+proof.getAggregatedProof().getcHash());
            throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_BIG_NUMBER_COMPARE_FAIL, "verifier proof verify [c_hver, proof.getAggregatedProof().getcHash()] not match");
        }
        return true;
    }
}
