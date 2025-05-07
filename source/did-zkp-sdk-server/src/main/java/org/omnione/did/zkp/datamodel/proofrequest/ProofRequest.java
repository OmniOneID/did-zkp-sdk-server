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

package org.omnione.did.zkp.datamodel.proofrequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import org.omnione.did.zkp.exception.ZkpErrorCode;
import org.omnione.did.zkp.exception.ZkpException;
import org.omnione.did.zkp.datamodel.util.GsonWrapper;
import org.omnione.did.zkp.datamodel.util.BigIntegerSerializer;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;

public class ProofRequest {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("version")
    @Expose
    private String version;

    @JsonAdapter(BigIntegerSerializer.class)
    @SerializedName("nonce")
    @Expose
    private BigInteger nonce;

    @SerializedName("requestedAttributes")
    @Expose
    private Map<String, AttributeInfo> requestedAttributes;

    @SerializedName("requestedPredicates")
    @Expose
    private Map<String, PredicateInfo> requestedPredicates;


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }

    public BigInteger getNonce() {
        return nonce;
    }
    public void setNonce(BigInteger nonce) {
        this.nonce = nonce;
    }

    public Map<String, AttributeInfo> getRequestedAttributes() {
        return requestedAttributes;
    }
    public void setRequestedAttributes(Map<String, AttributeInfo> requestedAttributes) {
        this.requestedAttributes = requestedAttributes;
    }

    public Map<String, PredicateInfo> getRequestedPredicates() {
        return requestedPredicates;
    }
    public void setRequestedPredicates(Map<String, PredicateInfo> requestedPredicates) {
        this.requestedPredicates = requestedPredicates;
    }

    public SubProofRequest getSubProofRequest(int index, RequestedProof requestedProof) {


        Map<String, Map<String, String>> revealedAttrMap = requestedProof.getRevealedAttrs();
        Map<String, Map<String, String>> predicatedAttrMap = requestedProof.getPredicates();
        SubProofRequest subProofRequest = new SubProofRequest();
        for (String revealedAttrKey : revealedAttrMap.keySet()) {
            Map<String, String> revealedAttrValue = revealedAttrMap.get(revealedAttrKey);
            for (String revealedAttrSubKey : revealedAttrValue.keySet()) {
                if (revealedAttrSubKey.equals("subProofIndex")) {
                    String subProofIndex = revealedAttrValue.get(revealedAttrSubKey);
                    if (index == Integer.parseInt(subProofIndex)) {
                        for (Map.Entry<String, AttributeInfo> entry : this.getRequestedAttributes().entrySet()) {
                            if (revealedAttrKey.equals(entry.getKey())) {
                                subProofRequest.addRevealedAttribute(entry.getValue().getName());
                            }
                        }
                    }
                }
            }
        }

        for (String predicateAttrKey : predicatedAttrMap.keySet()) {
            Map<String, String> predicateAttrValue = predicatedAttrMap.get(predicateAttrKey);

            for (String predicateAttrSubKey : predicateAttrValue.keySet()) {

                if (predicateAttrSubKey.equals("subProofIndex")) {
                    String subProofIndex = predicateAttrValue.get(predicateAttrSubKey);

                    if (index == Integer.parseInt(subProofIndex)) {

                        for (Map.Entry<String, PredicateInfo> entry : this.getRequestedPredicates().entrySet()) {
                            if (predicateAttrKey.equals(entry.getKey())) {
                                subProofRequest.addPredicate(entry.getValue().getName(), entry.getValue().getPType(), entry.getValue().getPValue());
                            }
                        }

                    }
                }
            }
        }

        return subProofRequest;
    }

    public String toJson() {
        GsonWrapper gson = new GsonWrapper();
        return gson.toJson(this);
    }
}
