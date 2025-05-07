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

package org.omnione.did.zkp.datamodel.schema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.omnione.did.zkp.datamodel.util.GsonWrapper;

import java.util.List;

public class CredentialSchema {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("attrNames")
    @Expose
    private List<String> attrNames;
    @SerializedName("attrTypes")
    @Expose
    private List<AttributeType> attrTypes;
    @SerializedName("tag")
    @Expose
    private String tag;

//    private int seqNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public List<String> getAttrNames() {
        return attrNames;
    }

    public void setAttrNames(List<String> attrNames) {
        this.attrNames = attrNames;
    }

    public List<AttributeType> getAttrTypes() {
        return attrTypes;
    }

    public void setAttrTypes(List<AttributeType> attrTypes) {
        this.attrTypes = attrTypes;
    }

//    public int getSeqNo() {
//        return seqNo;
//    }
//
//    public void setSeqNo(int seqNo) {
//        this.seqNo = seqNo;
//    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String toJson() {
        GsonWrapper gson = new GsonWrapper();
        return gson.toJson(this);
    }
}
