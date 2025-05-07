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
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import org.omnione.did.zkp.datamodel.util.BigIntegerSerializer;

import java.math.BigInteger;

public class CredentialPrimaryPrivateKey {

    @JsonAdapter(BigIntegerSerializer.class)
    @SerializedName("p")
    @Expose
    private BigInteger p;

    @JsonAdapter(BigIntegerSerializer.class)
    @SerializedName("q")
    @Expose
    private BigInteger q;

    public CredentialPrimaryPrivateKey(BigInteger p, BigInteger q) {
        this.p = p;
        this.q = q;
    }

    public BigInteger getP() {
        return p;
    }
    public void setP(BigInteger p) {
        this.p = p;
    }

    public BigInteger getQ() {
        return q;
    }
    public void setQ(BigInteger q) {
        this.q = q;
    }
}
