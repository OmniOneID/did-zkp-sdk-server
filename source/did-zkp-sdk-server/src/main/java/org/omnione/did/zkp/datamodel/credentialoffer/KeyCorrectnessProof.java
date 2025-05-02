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

package org.omnione.did.zkp.datamodel.credentialoffer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import org.omnione.did.zkp.datamodel.util.BigIntegerMapSerializer;
import org.omnione.did.zkp.datamodel.util.BigIntegerSerializer;

import java.math.BigInteger;
import java.util.LinkedHashMap;

public class KeyCorrectnessProof {
    @JsonAdapter(BigIntegerSerializer.class)
    @SerializedName("c")
    @Expose
    private BigInteger c;

    @JsonAdapter(BigIntegerSerializer.class)
    @SerializedName("xzCap")
    @Expose
    private BigInteger xzCap;

    @JsonAdapter(BigIntegerMapSerializer.class)
    @SerializedName("xrCap")
    @Expose
    private LinkedHashMap<String, BigInteger> xrCap;

    public KeyCorrectnessProof() {}
    public KeyCorrectnessProof(BigInteger c, BigInteger xzCap, LinkedHashMap<String, BigInteger> xrCap) {
        this.c = c;
        this.xzCap = xzCap;
        this.xrCap = xrCap;
    }

    public BigInteger getC() {
        return c;
    }public void setC(BigInteger c) {
        this.c = c;
    }
    public BigInteger getXzCap() {
        return xzCap;
    }

    public LinkedHashMap<String, BigInteger> getXrCap() {
        return new LinkedHashMap<String, BigInteger>(xrCap);
    }
}
