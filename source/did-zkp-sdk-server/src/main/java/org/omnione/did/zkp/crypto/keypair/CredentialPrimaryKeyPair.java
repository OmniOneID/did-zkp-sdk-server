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

package org.omnione.did.zkp.crypto.keypair;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.omnione.did.zkp.datamodel.util.GsonWrapper;

public class CredentialPrimaryKeyPair {

    @SerializedName("privateKey")
    @Expose
    private CredentialPrimaryPrivateKey privateKey;
    @SerializedName("publicKey")
    @Expose
    private CredentialPrimaryPublicKey publicKey;
    @SerializedName("publicKeyMetadata")
    @Expose
    private PublicKeyMetadata publicKeyMetadata;

    public CredentialPrimaryKeyPair() {
    }

    public CredentialPrimaryKeyPair(CredentialPrimaryPublicKey publicKey, CredentialPrimaryPrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public CredentialPrimaryKeyPair(CredentialPrimaryPublicKey publicKey, CredentialPrimaryPrivateKey privateKey, PublicKeyMetadata publicKeyMetadata) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.publicKeyMetadata = publicKeyMetadata;
    }

    public void setPrivateKey(
        CredentialPrimaryPrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public void setPublicKey(
        CredentialPrimaryPublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public void setPublicKeyMetadata(
        PublicKeyMetadata publicKeyMetadata) {
        this.publicKeyMetadata = publicKeyMetadata;
    }
    public CredentialPrimaryPublicKey getPublicKey() {
        return publicKey;
    }
    public CredentialPrimaryPrivateKey getPrivateKey() {
        return privateKey;
    }
    public PublicKeyMetadata getPublicKeyMetadata() {
        return publicKeyMetadata;
    }

    public String toJson() {
        GsonWrapper gson = new GsonWrapper();
        return gson.toJson(this);
    }

    public void fromJson(String val) {
        GsonWrapper gson = new GsonWrapper();
        CredentialPrimaryKeyPair result = gson.fromJson(val, CredentialPrimaryKeyPair.class);

        this.privateKey = result.getPrivateKey();
        this.publicKey = result.getPublicKey();
        this.publicKeyMetadata = result.getPublicKeyMetadata();
    }
}