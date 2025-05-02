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

public class Identifiers {
    @SerializedName("credDefId")
    @Expose
    private String credDefId;

    @SerializedName("schemaId")
    @Expose
    private String schemaId;

    @SerializedName("revRegId")
    @Expose
    private String revRegId;

    public Identifiers(String schemaId, String credDefId, String revRegId, String timestemp) {

        this.schemaId = schemaId;
        this.credDefId = credDefId;
        this.revRegId = revRegId;
    }

    public String getCredDefId() {
        return credDefId;
    }

    public void setCredDefId(String credDefId) {
        this.credDefId = credDefId;
    }

    public String getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(String schemaId) {
        this.schemaId = schemaId;
    }

    public String getRevRegId() {
        return revRegId;
    }

    public void setRevRegId(String revRegId) {
        this.revRegId = revRegId;
    }

}
