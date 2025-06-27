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

package org.omnione.did.zkp.core.manager;


import org.omnione.did.zkp.datamodel.enums.CredentialType;
import org.omnione.did.zkp.datamodel.enums.Marker;

public class ZkpIdHelper {

    private static String DELIMITER = ":";

    // 2oTF7LvWLUft1UC6qVkTCg:2:DriverLicense:1.0
    public static String generateSchemaId(String issuerDID, String schemaName, String schemaVersion) {
        return issuerDID + DELIMITER + Marker.CRED_SCHEMA.getMaker() + DELIMITER + schemaName + DELIMITER + schemaVersion;
    }
    // 2oTF7LvWLUft1UC6qVkTCg:3:CL:2oTF7LvWLUft1UC6qVkTCg:2:DriverLicense:1.0:TAG1
    public static String generateCredentialDefinitionId(String issuerDID, String schemaId, String tag) {
        return issuerDID + DELIMITER + Marker.CRED_DEF.getMaker() +DELIMITER + CredentialType.CL + DELIMITER + schemaId + DELIMITER + tag;
    }

}
