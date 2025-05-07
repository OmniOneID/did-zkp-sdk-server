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

package org.omnione.did.zkp.datamodel.proof;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import org.omnione.did.zkp.datamodel.util.BigIntegerSerializer;

import java.math.BigInteger;
import java.util.Vector;

public class AggregatedProof {

    @SerializedName("cHash")
    @Expose
    @JsonAdapter(BigIntegerSerializer.class)
    private BigInteger cHash;

    @SerializedName("cList")
    @Expose
    private Vector<byte[]> cList;

    public AggregatedProof() {
    }

    public AggregatedProof(BigInteger cHash, Vector<byte[]> cList) {
        this.cHash = cHash;
        this.cList = cList;
    }

    public BigInteger getcHash() {
        return cHash;
    }

    public void setcHash(BigInteger cHash) {
        this.cHash = cHash;
    }

    public Vector<byte[]> getcList() {
        return cList;
    }

    public void setcList(Vector<byte[]> cList) {
        this.cList = cList;
    }
}
