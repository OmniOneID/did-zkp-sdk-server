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

import org.omnione.did.zkp.exception.ZkpErrorCode;
import org.omnione.did.zkp.exception.ZkpException;
import org.omnione.did.zkp.crypto.util.SignatureUtils;
import org.omnione.did.zkp.datamodel.credential.Credential;
import org.omnione.did.zkp.datamodel.definition.CredentialDefinition;
import org.omnione.did.zkp.datamodel.credentialoffer.CredentialOffer;
import org.omnione.did.zkp.datamodel.credentialrequest.CredentialRequest;
import org.omnione.did.zkp.datamodel.credential.AttributeValue;
import org.omnione.did.zkp.datamodel.credential.CredentialSignature;
import org.omnione.did.zkp.datamodel.credential.SignatureCorrectnessProof;
import org.omnione.did.zkp.datamodel.credentialoffer.KeyCorrectnessProof;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.UUID;

public class ZkpCredentialManager {
    /**
     * Creates a CredentialOffer object based on the given key correctness proof, schema ID, credential definition ID, and nonce.
     *
     * @param keyCorrectnessProof the key correctness proof for the credential
     * @param schemaId the ID of the associated credential schema
     * @param credDefId the ID of the associated credential definition
     * @param nonce a randomly generated nonce for security
     * @return a populated CredentialOffer object
     * @throws ZkpException if any error occurs during the creation process
     */
    public CredentialOffer createCredentialOffer(KeyCorrectnessProof keyCorrectnessProof,
                                                        String schemaId,
                                                        String credDefId,
                                                        BigInteger nonce) throws ZkpException {

        CredentialOffer credOffer = new CredentialOffer();
        credOffer.setSchemaId(schemaId);
        credOffer.setCredDefId(credDefId);
        credOffer.setNonce(nonce);
        credOffer.setKeyCorrectnessProof(keyCorrectnessProof);
        return credOffer;
    }

    /**
     * Creates a Credential object by verifying the credential request and assembling credential information.
     *
     * @param credentialDefinition the credential definition used for credential issuance
     * @param credSignature the credential signature
     * @param proof the signature correctness proof
     * @param credentialValue the attribute values of the credential
     * @param credentialRequest the credential request from the holder
     * @param nonce the nonce used during verification
     * @param vcId the ID from the VC
     * @return a fully populated Credential object
     * @throws ZkpException if the credential request verification fails
     */
    public Credential createCredential(CredentialDefinition credentialDefinition,
                                                        CredentialSignature credSignature,
                                                        SignatureCorrectnessProof proof,
                                                        LinkedHashMap<String, AttributeValue> credentialValue,
                                                        CredentialRequest credentialRequest,
                                                        BigInteger nonce,
                                                        String vcId) throws ZkpException {
        if (!new SignatureUtils().verifyCredentialRequest(credentialDefinition.getValue().getPrimary(),
                credentialRequest.getBlindedMs(), credentialRequest.getBlindedMsProof(), nonce))
            throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_BIG_NUMBER_COMPARE_FAIL,"verify credentialRequest fail in createCredential");

        Credential credential = new Credential();
        credential.setCredentialId(vcId);
        credential.setCredDefId(credentialDefinition.getId());
        credential.setSchemaId(credentialDefinition.getSchemaId());
        credential.setRevRegDefId(null);
        credential.setValues(credentialValue);

        credential.setCredentialSignature(credSignature);
        credential.setSignatureCorrectnessProof(proof);

        return credential;
    }

}
