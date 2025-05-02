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

package org.omnione.did.zkp.core.manager;

import org.omnione.did.zkp.exception.ZkpException;
import org.omnione.did.zkp.crypto.signature.ProofVerifier;
import org.omnione.did.zkp.datamodel.proof.Proof;
import org.omnione.did.zkp.datamodel.proof.verifyparam.ProofVerifyParam;
import org.omnione.did.zkp.datamodel.proofrequest.AttributeInfo;
import org.omnione.did.zkp.datamodel.proofrequest.PredicateInfo;
import org.omnione.did.zkp.datamodel.proofrequest.ProofRequest;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class ZkpProofManager {

    /**
     * Creates a ProofRequest object based on given attributes, predicates, and a verifier nonce.
     *
     * @param name the name of the proof request
     * @param version the version of the proof request
     * @param proofRequestAttribute the map of attributes requested for proof
     * @param proofRequestPredicate the map of predicates requested for proof
     * @param verifierNonce a randomly generated nonce to prevent replay attacks
     * @return a created ProofRequest object
     */
    public static ProofRequest requestProofReq(String name,
                                               String version,
                                               Map<String, AttributeInfo> proofRequestAttribute,
                                               Map<String, PredicateInfo> proofRequestPredicate,
                                               BigInteger verifierNonce) {
        ProofRequest proofRequest = new ProofRequest();
        proofRequest.setName(name);
        proofRequest.setVersion(version);
        proofRequest.setNonce(verifierNonce);
        proofRequest.setRequestedAttributes(proofRequestAttribute);
        proofRequest.setRequestedPredicates(proofRequestPredicate);
        return proofRequest;
    }

    /**
     * Verifies the provided Proof object against the ProofRequest and verification parameters.
     *
     * @param proof the Proof object submitted for verification
     * @param nonce the nonce used during the proof generation
     * @param proofRequest the original ProofRequest to verify against
     * @param proofVerifyParams the list of parameters needed for verification (schemas, credential definitions, etc.)
     * @return true if the proof is valid, false otherwise
     * @throws ZkpException if proof verification fails or an error occurs
     */
    public boolean verifyProof(Proof proof,
                                   BigInteger nonce,
                                   ProofRequest proofRequest,
                                   List<ProofVerifyParam> proofVerifyParams) throws ZkpException {

        return ProofVerifier.verify(proofRequest, proof, proofVerifyParams, nonce);
    }
}
