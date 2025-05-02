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
import org.omnione.did.zkp.core.util.acml.data.GroupOrderElement;
import org.omnione.did.zkp.datamodel.util.GsonWrapper;
import org.omnione.did.zkp.core.util.acml.data.PointG1;
import org.omnione.did.zkp.datamodel.util.BigIntegerMapSerializer;
import org.omnione.did.zkp.datamodel.util.BigIntegerSerializer;
import org.omnione.did.zkp.datamodel.util.ZkpSerializer;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class BlindedCredentialSecrets {

    @JsonAdapter(BigIntegerSerializer.class)
    @SerializedName("u")
    @Expose
    private BigInteger u;

    @JsonAdapter(ZkpSerializer.class)
    @SerializedName("ur")
    @Expose
    private PointG1 ur;

    @Expose(serialize = false, deserialize = false)
    @JsonAdapter(BigIntegerSerializer.class)
    private BigInteger vPrime;

    @JsonAdapter(ZkpSerializer.class)
    @Expose(serialize = false, deserialize = false)
    private GroupOrderElement vrPrime;

    @SerializedName("hiddenAttributes")
    private List<String> hiddenAttrs;

    @JsonAdapter(BigIntegerMapSerializer.class)
    private LinkedHashMap<String, BigInteger> committedAttrs;

    public BlindedCredentialSecrets() {}

    public BlindedCredentialSecrets(BigInteger u, GroupOrderElement vr_prime, PointG1 ur, BigInteger v_prime,
                                    List<String> hiddenAttrs, LinkedHashMap<String, BigInteger> committedAttrs) {
        this.u = u;
        this.ur = ur;
        this.vrPrime = vr_prime;
        this.hiddenAttrs = (hiddenAttrs == null) ? new LinkedList<String>() : hiddenAttrs;
        this.committedAttrs = (committedAttrs == null) ? new LinkedHashMap<String, BigInteger>() : committedAttrs;

        this.vPrime = v_prime;
    }

    public BigInteger getU() {
        return u;
    }

    public PointG1 getUr() {
        return ur;
    }

    public void setUr(PointG1 ur) {
        this.ur = ur;
    }

    public BigInteger getVPrime() {
        return vPrime;
    }

    public void setVPrime(BigInteger v_prime) {
        this.vPrime = v_prime;
    }

    public List<String> getHiddenAttrs() {
        return hiddenAttrs;
    }

    public LinkedHashMap<String, BigInteger> getCommittedAttrs() {
        return committedAttrs;
    }

    public GroupOrderElement getVrPrime() {
        return vrPrime;
    }

    public void setVrPrime(GroupOrderElement vr_prime) {
        this.vrPrime = vr_prime;
    }

    public String toJson() {
        GsonWrapper gson = new GsonWrapper();
        return gson.toJson(this);
    }
}
