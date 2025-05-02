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

package org.omnione.did.zkp.datamodel.credentialrequest;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import org.omnione.did.zkp.datamodel.util.GsonWrapper;
import org.omnione.did.zkp.datamodel.util.BigIntegerSerializer;

import java.math.BigInteger;

public class CredentialRequest {

    @SerializedName("proverDID")
    @Expose
    private String proverDid;

    @SerializedName("credDefId")
    @Expose
    private String credDefId;

    @JsonAdapter(BigIntegerSerializer.class)
    @SerializedName("nonce")
    @Expose
    private BigInteger nonce;

    @SerializedName("blindedMs")
    @Expose
    private BlindedCredentialSecrets blindedMs;

    @SerializedName("blindedMsCorrectnessProof")
    @Expose
    private BlindedCredentialSecretsCorrectnessProof blindedMsProof;


    public String getProverDid() {
        return proverDid;
    }
    public void setProverDid(String proverDid) {
        this.proverDid = proverDid;
    }

    public String getCredDefId() {
        return credDefId;
    }
    public void setCredDefId(String credDefId) {
        this.credDefId = credDefId;
    }

    public BigInteger getNonce() {
        return nonce;
    }
    public void setNonce(BigInteger nonce) {
        this.nonce = nonce;
    }

    public BlindedCredentialSecrets getBlindedMs() {
        return blindedMs;
    }
    public void setBlindedMs(BlindedCredentialSecrets blindedMs) {
        this.blindedMs = blindedMs;
    }

    public BlindedCredentialSecretsCorrectnessProof getBlindedMsProof() {
        return blindedMsProof;
    }
    public void setBlindedMsProof(BlindedCredentialSecretsCorrectnessProof blindedMsProof) {
        this.blindedMsProof = blindedMsProof;
    }

    public String toJson() {
        return GsonWrapper.getGsonPrettyPrinting().toJson(this);
    }

}
