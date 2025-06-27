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
import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class SubProof {

    @SerializedName("primaryProof")
    @Expose
    private PrimaryProof primaryProof;

    public SubProof(PrimaryProof primaryProof) {
        this.primaryProof = primaryProof;
    }

    public PrimaryProof getPrimaryProof() {
        return this.primaryProof;
    }

    public Map<String, String> getRevealedAttrs() {
        Map<String, String> ret = new HashMap<String, String>();
        for (Map.Entry<String, BigInteger> entry : primaryProof.getEqProof().getRevealedAttrs().entrySet()) {
            ret.put(entry.getKey(), entry.getValue().toString());
        }
        return ret;
    }

}
