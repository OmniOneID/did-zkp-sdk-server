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
import org.omnione.did.zkp.datamodel.util.BigIntegerMapSerializer;
import org.omnione.did.zkp.datamodel.util.BigIntegerSerializer;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;

public class BlindedCredentialSecretsCorrectnessProof {

    @JsonAdapter(BigIntegerSerializer.class)
    @SerializedName("c")
    @Expose
    private BigInteger c;

    @JsonAdapter(BigIntegerSerializer.class)
    @SerializedName("vDashCap")
    @Expose
    private BigInteger vDashCap;

    @JsonAdapter(BigIntegerMapSerializer.class)
    @SerializedName("mCaps")
    @Expose
    private LinkedHashMap<String, BigInteger> mCaps;

    @JsonAdapter(BigIntegerMapSerializer.class)
    @SerializedName("rCaps")
    @Expose
    private Map<String, BigInteger> rCaps;

    public BigInteger getC() {
        return c;
    }
    public void setC(BigInteger c) {
        this.c = c;
    }

    public BigInteger getVDashCap() {
        return vDashCap;
    }
    public void setVDashCap(BigInteger vDashCap) {
        this.vDashCap = vDashCap;
    }

    public LinkedHashMap<String, BigInteger> getmCaps() {
        return mCaps;
    }
    public void setMCaps(LinkedHashMap<String, BigInteger> mCaps) {
        this.mCaps = mCaps;
    }

    public Map<String, BigInteger> getRCaps() {
        return rCaps;
    }
    public void setRCaps(Map<String, BigInteger> rCaps) {
        this.rCaps = rCaps;
    }
}
