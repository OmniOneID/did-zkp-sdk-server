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

package org.omnione.did.zkp.datamodel.definition;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.omnione.did.zkp.crypto.keypair.CredentialPrimaryPublicKey;

public class CredentialDefinitionValue {

    @SerializedName("primary")
    @Expose
    private CredentialPrimaryPublicKey primary;

    public CredentialPrimaryPublicKey getPrimary() {
        return primary;
    }
    public void setPrimary(CredentialPrimaryPublicKey primary) {
        this.primary = primary;
    }

}
