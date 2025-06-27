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
import org.omnione.did.zkp.datamodel.util.BigIntegerMapSerializer;
import org.omnione.did.zkp.datamodel.util.BigIntegerSerializer;

import java.math.BigInteger;
import java.util.LinkedHashMap;

public class CredentialPrimaryPublicKey {

    @JsonAdapter(BigIntegerSerializer.class)
    @SerializedName("n")
    @Expose
    private BigInteger n;

    @JsonAdapter(BigIntegerSerializer.class)
    @SerializedName("z")
    @Expose
    private BigInteger z;

    @JsonAdapter(BigIntegerSerializer.class)
    @SerializedName("s")
    @Expose
    private BigInteger s;

    @JsonAdapter(BigIntegerMapSerializer.class)
    @SerializedName("r")
    @Expose
    private LinkedHashMap<String, BigInteger> r;

    @JsonAdapter(BigIntegerSerializer.class)
    @SerializedName("rctxt")
    @Expose
    private BigInteger rctxt;

    public CredentialPrimaryPublicKey() {}

    public CredentialPrimaryPublicKey(BigInteger n, BigInteger s, BigInteger z, LinkedHashMap<String, BigInteger> r) {
        this.n = n;
        this.s = s;
        this.z = z;
        this.r = r;
    }

    public CredentialPrimaryPublicKey(BigInteger n, BigInteger s, BigInteger z, LinkedHashMap<String, BigInteger> r, BigInteger rctxt) {
        this.n = n;
        this.s = s;
        this.z = z;
        this.r = r;
        this.rctxt = rctxt;
    }

    public BigInteger getN() {
        return n;
    }
    public void setN(BigInteger n) {
        this.n = n;
    }

    public BigInteger getZ() {
        return z;
    }
    public void setZ(BigInteger z) {
        this.z = z;
    }

    public BigInteger getS() {
        return s;
    }
    public void setS(BigInteger s) {
        this.s = s;
    }

    public LinkedHashMap<String, BigInteger> getR() {
        return r;
    }
    public void setR(LinkedHashMap<String, BigInteger> r) {
        this.r = r;
    }

    public BigInteger getRctxt() {
        return rctxt;
    }

    public void setRctxt(BigInteger rctxt) {
        this.rctxt = rctxt;
    }
}