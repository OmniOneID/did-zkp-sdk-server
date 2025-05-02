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
import org.omnione.did.zkp.datamodel.util.BigIntegerMapSerializer;
import org.omnione.did.zkp.datamodel.util.BigIntegerSerializer;

import java.math.BigInteger;
import java.util.Map;

public class PrimaryPredicateInequalityProof {

    @JsonAdapter(BigIntegerMapSerializer.class)
    @SerializedName("u")
    @Expose
    private Map<String, BigInteger> u;

    @JsonAdapter(BigIntegerMapSerializer.class)
    @SerializedName("r")
    @Expose
    private Map<String,BigInteger> r;

    @JsonAdapter(BigIntegerMapSerializer.class)
    @SerializedName("t")
    @Expose
    private Map<String,BigInteger> t;

    @JsonAdapter(BigIntegerSerializer.class)
    @SerializedName("mj")
    @Expose
    private BigInteger mj;

    @JsonAdapter(BigIntegerSerializer.class)
    @SerializedName("alpha")
    @Expose
    private BigInteger alpha;

    @SerializedName("predicate")
    @Expose
    private Predicate predicate;


    public PrimaryPredicateInequalityProof() {}
    public PrimaryPredicateInequalityProof(Map<String, BigInteger> u, Map<String, BigInteger> r, Map<String, BigInteger> t,
                                           BigInteger mj, BigInteger alpha, Predicate predicate) {
        this.u = u;
        this.r = r;
        this.mj = mj;
        this.alpha = alpha;
        this.t = t;
        this.predicate = predicate;
    }


    public Map<String, BigInteger> getU() {
        return u;
    }
    public void setU(Map<String, BigInteger> u) {
        this.u = u;
    }

    public Map<String, BigInteger> getR() {
        return r;
    }
    public void setR(Map<String, BigInteger> r) {
        this.r = r;
    }

    public BigInteger getMj() {
        return mj;
    }
    public void setMj(BigInteger mj) {
        this.mj = mj;
    }

    public BigInteger getAlpha() {
        return alpha;
    }
    public void setAlpha(BigInteger alpha) {
        this.alpha = alpha;
    }

    public Map<String, BigInteger> getT() {
        return t;
    }
    public void setT(Map<String, BigInteger> t) {
        this.t = t;
    }

    public Predicate getPredicate() {
        return predicate;
    }
    public void setPredicate(Predicate predicate) {
        this.predicate = predicate;
    }
}
