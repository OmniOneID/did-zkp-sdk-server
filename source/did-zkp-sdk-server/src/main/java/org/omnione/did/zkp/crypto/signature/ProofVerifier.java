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
import org.omnione.did.zkp.datamodel.enums.PredicateType;
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

    private static final String SUB_PROOF_INDEX = "subProofIndex";
    private static final String RESTRICTION_CRED_DEF_ID = "credDefId";
    private static final String RESTRICTION_SCHEMA_ID = "schemaId";

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
    }

    private static void checkProofStructure(Proof proof) throws ZkpException {
        if (proof.getProofs() == null || proof.getProofs().isEmpty())
            throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_VERIFIER_NOT_SATISFIED_ATTRIBUTE_IN_SUB_PROOF, "Proof doesn't contain any sub proof");

        if (proof.getIdentifiers() == null || proof.getIdentifiers().size() != proof.getProofs().size())
            throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_VERIFIER_NOT_SATISFIED_ATTRIBUTE_IN_SUB_PROOF, "Proof identifiers don't correspond to sub proofs");

        if (proof.getRequestedProof() == null || proof.getAggregatedProof() == null)
            throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_NULL, "check verify [requestedProof or aggregatedProof is null]");
    }

    /**
     * 요청한 referent 가 모두 증명(requested_proof)에 있고, 증명의 referent 는 모두 요청한 것인지 체크.
     * referent 가 가리키는 증명서가 요청의 restrictions 를 만족하는지도 체크
     **/
    private static void checkRequestedReferents(ProofRequest proofRequest, Proof proof) throws ZkpException {
        RequestedProof requestedProof = proof.getRequestedProof();
        Map<String, AttributeInfo> requestedAttributes = orEmpty(proofRequest.getRequestedAttributes());
        Map<String, PredicateInfo> requestedPredicates = orEmpty(proofRequest.getRequestedPredicates());

        Map<String, Map<String, String>> revealedAttrs = orEmpty(requestedProof.getRevealedAttrs());
        Map<String, Map<String, String>> unrevealedAttrs = orEmpty(requestedProof.getUnrevealedAttrs());
        Map<String, Map<String, String>> predicateAttrs = orEmpty(requestedProof.getPredicates());

        for (String ref : predicateAttrs.keySet()) {
            if (!requestedPredicates.containsKey(ref))
                throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_VERIFIER_NOT_SATISFIED_ATTRIBUTE_IN_SUB_PROOF, ref + " is not requested in proof request");
        }
        for (String ref : revealedAttrs.keySet()) {
            if (!requestedAttributes.containsKey(ref))
                throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_VERIFIER_NOT_SATISFIED_ATTRIBUTE_IN_SUB_PROOF, ref + " is not requested in proof request");
            if (unrevealedAttrs.containsKey(ref))
                throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_VERIFIER_NOT_SATISFIED_ATTRIBUTE_IN_SUB_PROOF, ref + " is both revealed and unrevealed in requested proof");
        }
        for (String ref : unrevealedAttrs.keySet()) {
            if (!requestedAttributes.containsKey(ref))
                throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_VERIFIER_NOT_SATISFIED_ATTRIBUTE_IN_SUB_PROOF, ref + " is not requested in proof request");
        }

        for (Map.Entry<String, PredicateInfo> entry : requestedPredicates.entrySet()) {
            PredicateType pType = entry.getValue().getPType();
            if (pType == null || pType == PredicateType.EQ)
                throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_VERIFIER_NOT_SATISFIED_ATTRIBUTE_IN_SUB_PROOF, "Unsupported predicate type requested in " + entry.getKey());

            int index = getSubProofIndex(entry.getKey(), predicateAttrs.get(entry.getKey()), proof.getProofs().size());
            checkRestrictions(entry.getKey(), entry.getValue().getRestrictions(), proof.getIdentifiers().get(index));
        }

        for (Map.Entry<String, AttributeInfo> entry : requestedAttributes.entrySet()) {
            Map<String, String> ref = revealedAttrs.containsKey(entry.getKey()) ? revealedAttrs.get(entry.getKey()) : unrevealedAttrs.get(entry.getKey());

            int index = getSubProofIndex(entry.getKey(), ref, proof.getProofs().size());
            checkRestrictions(entry.getKey(), entry.getValue().getRestrictions(), proof.getIdentifiers().get(index));
        }
    }

    /**
     * 비공개로 제출한 요청 속성도 sub proof 의 증명서(스키마)에 있는 속성이어야 한다
     **/
    private static void checkUnrevealedAttributes(ProofRequest proofRequest, RequestedProof requestedProof, int index, CredentialSchema cred_schema) throws ZkpException {
        Map<String, AttributeInfo> requestedAttributes = orEmpty(proofRequest.getRequestedAttributes());

        for (Map.Entry<String, Map<String, String>> entry : orEmpty(requestedProof.getUnrevealedAttrs()).entrySet()) {
            if (Integer.parseInt(entry.getValue().get(SUB_PROOF_INDEX)) != index)
                continue;

            if (!cred_schema.getAttrNames().contains(requestedAttributes.get(entry.getKey()).getName()))
                throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_VERIFIER_NOT_SATISFIED_ATTRIBUTE_IN_SUB_PROOF, "Credential doesn't contain requested attribute");
        }
    }

    private static int getSubProofIndex(String referent, Map<String, String> ref, int subProofCount) throws ZkpException {
        if (ref == null || ref.get(SUB_PROOF_INDEX) == null)
            throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_VERIFIER_NOT_SATISFIED_ATTRIBUTE_IN_SUB_PROOF, referent + " doesn't exist in requested proof");

        int index;
        try {
            index = Integer.parseInt(ref.get(SUB_PROOF_INDEX));
        } catch (NumberFormatException e) {
            throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_VERIFIER_NOT_SATISFIED_ATTRIBUTE_IN_SUB_PROOF, "Invalid subProofIndex of " + referent);
        }
        if (index < 0 || index >= subProofCount)
            throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_VERIFIER_NOT_SATISFIED_ATTRIBUTE_IN_SUB_PROOF, "Invalid subProofIndex of " + referent);
        return index;
    }

    /**
     * restrictions 가 있으면, 그중 하나의 조건(credDefId, schemaId)이 모두 증명서 identifiers 와 일치해야 한다
     **/
    private static void checkRestrictions(String referent, List<Map<String, String>> restrictions, Identifiers identifiers) throws ZkpException {
        if (restrictions == null || restrictions.isEmpty())
            return;

        for (Map<String, String> restriction : restrictions) {
            if (restriction == null || restriction.isEmpty())
                continue;

            boolean matched = true;
            for (Map.Entry<String, String> condition : restriction.entrySet()) {
                String value;
                if (RESTRICTION_CRED_DEF_ID.equals(condition.getKey()))
                    value = identifiers.getCredDefId();
                else if (RESTRICTION_SCHEMA_ID.equals(condition.getKey()))
                    value = identifiers.getSchemaId();
                else
                    value = null;

                if (value == null || !value.equals(condition.getValue())) {
                    matched = false;
                    break;
                }
            }
            if (matched)
                return;
        }
        throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_VERIFIER_NOT_SATISFIED_ATTRIBUTE_IN_SUB_PROOF, "Credential doesn't satisfy restrictions of " + referent);
    }

    /**
     * sub proof 가 요청한 것을 증명했는지 체크.
     * 공개 속성 집합과 조건(속성, 부등호, 기준값) 목록이 요청과 같아야 하고,
     * 각 조건 증명의 mj 는 동등성 증명의 같은 속성 값(m[attr])과 같아야 한다
     **/
    private static void checkVerifyParamsConsistency(PrimaryProof primaryProof, SubProofRequest sub_proof_request) throws ZkpException {
        if (primaryProof == null || primaryProof.getEqProof() == null
                || primaryProof.getEqProof().getRevealedAttrs() == null || primaryProof.getEqProof().getM() == null)
            throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_NULL, "check verify [primaryProof is null]");

        PrimaryEqualProof eqProof = primaryProof.getEqProof();

        if (!eqProof.getRevealedAttrs().keySet().equals(sub_proof_request.getRevealedAttrs()))
            throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_VERIFIER_NOT_SATISFIED_ATTRIBUTE_IN_SUB_PROOF, "Proof revealed attributes don't correspond to requested attributes");

        List<String> requestedPredicates = new ArrayList<String>();
        for (Predicate predicate : sub_proof_request.getPredicates()) {
            requestedPredicates.add(predicateKey(predicate));
        }

        List<String> provedPredicates = new ArrayList<String>();
        for (PrimaryPredicateInequalityProof neProof : orEmpty(primaryProof.getNeProofs())) {
            Predicate predicate = neProof.getPredicate();
            if (predicate == null || predicate.getAttrName() == null || predicate.getPType() == null || predicate.getPType() == PredicateType.EQ)
                throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_VERIFIER_NOT_SATISFIED_ATTRIBUTE_IN_SUB_PROOF, "Unsupported predicate in proof");

            BigInteger m = eqProof.getM().get(predicate.getAttrName());
            if (m == null || !m.equals(neProof.getMj()))
                throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_VERIFIER_NOT_SATISFIED_ATTRIBUTE_IN_SUB_PROOF, "Predicate proof is not bound to credential attribute " + predicate.getAttrName());

            provedPredicates.add(predicateKey(predicate));
        }

        Collections.sort(requestedPredicates);
        Collections.sort(provedPredicates);
        if (!requestedPredicates.equals(provedPredicates))
            throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_VERIFIER_NOT_SATISFIED_ATTRIBUTE_IN_SUB_PROOF, "Proof predicates don't correspond to requested predicates");
    }

    // Predicate 에 equals/hashCode 가 없어 필드 값으로 비교한다
    private static String predicateKey(Predicate predicate) {
        return predicate.getAttrName() + "|" + predicate.getPType() + "|" + predicate.getPValue();
    }

    private static <K, V> Map<K, V> orEmpty(Map<K, V> map) {
        return map == null ? Collections.<K, V>emptyMap() : map;
    }

    private static <T> List<T> orEmpty(List<T> list) {
        return list == null ? Collections.<T>emptyList() : list;
    }

    public static boolean verify(ProofRequest proofRequest, Proof proof, List<ProofVerifyParam> proofVerifyParams, BigInteger nonce) throws ZkpException {

        checkProofStructure(proof);
        checkRequestedReferents(proofRequest, proof);

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
            checkUnrevealedAttributes(proofRequest, proof.getRequestedProof(), i, schema);
            checkVerifyParamsConsistency(primaryProof, subProofRequest);

            Vector<BigInteger> t_hat = _verify_equality(credentialDefinition.getValue().getPrimary(),
                    primaryProof.getEqProof(),
                    proof.getAggregatedProof().getcHash(),
                    schema,
                    nonCredSchema,
                    subProofRequest);

            for (PrimaryPredicateInequalityProof neProof : orEmpty(primaryProof.getNeProofs())) {
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
