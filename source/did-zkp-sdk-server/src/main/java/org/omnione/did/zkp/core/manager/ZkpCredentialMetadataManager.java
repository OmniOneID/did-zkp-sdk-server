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

import org.omnione.did.zkp.exception.ZkpException;
import org.omnione.did.zkp.datamodel.schema.AttributeType;
import org.omnione.did.zkp.datamodel.definition.CredentialDefinition;
import org.omnione.did.zkp.datamodel.schema.CredentialSchema;
import org.omnione.did.zkp.datamodel.enums.CredentialType;
import org.omnione.did.zkp.crypto.keypair.CredentialPrimaryPublicKey;

import java.util.List;

public class ZkpCredentialMetadataManager {

    /**
     * Creates a CredentialSchema object using issuer information and attribute definitions.
     *
     * @param issuerDid the DID of the issuer
     * @param name the name of the schema
     * @param version the version of the schema
     * @param attrNames the list of attribute names
     * @param attrTypes the list of attribute types corresponding to the attribute names
     * @param tag an optional tag for grouping or categorization
     * @return a newly created CredentialSchema object
     */
    public CredentialSchema createSchema(String issuerDid,
                                         String name,
                                         String version,
                                         List<String> attrNames,
                                         List<AttributeType> attrTypes,
                                         String tag) {

        CredentialSchema credentialSchema = new CredentialSchema();
        credentialSchema.setId(ZkpIdHelper.generateSchemaId(issuerDid, name, version));
        credentialSchema.setVersion(version);
        credentialSchema.setName(name);
        credentialSchema.setAttrNames(attrNames);
        credentialSchema.setAttrTypes(attrTypes);
        credentialSchema.setTag(tag);

        return credentialSchema;
    }

    /**
     * Creates a CredentialDefinition object based on a given CredentialSchema and public key.
     *
     * @param issuerDid the DID of the issuer
     * @param schema the CredentialSchema on which the definition is based
     * @param credentialPrimaryPublicKey the primary public key for credential issuance
     * @return a newly created CredentialDefinition object
     * @throws ZkpException if an error occurs during ID generation or definition creation
     */
    public CredentialDefinition createDefinition(String issuerDid, CredentialSchema schema,
                                                 CredentialPrimaryPublicKey credentialPrimaryPublicKey) throws ZkpException {

        CredentialDefinition credentialDefinition = new CredentialDefinition();
        credentialDefinition.setSchemaId(schema.getId());
        credentialDefinition.setId(ZkpIdHelper.generateCredentialDefinitionId(issuerDid, schema.getId(), schema.getTag()));
        credentialDefinition.setType(CredentialType.CL);
        credentialDefinition.setVer(schema.getVersion());
        credentialDefinition.setPrimaryKey(credentialPrimaryPublicKey);
        credentialDefinition.setTag(schema.getTag());

        return credentialDefinition;
    }

    public CredentialDefinition createDefinition(String issuerDid, CredentialSchema schema,
                                                 CredentialPrimaryPublicKey credentialPrimaryPublicKey,
                                                 String credentialDefinitionVersion,
                                                 String credentialDefinitionTag) throws ZkpException {

        CredentialDefinition credentialDefinition = new CredentialDefinition();
        credentialDefinition.setSchemaId(schema.getId());
        credentialDefinition.setId(ZkpIdHelper.generateCredentialDefinitionId(issuerDid, schema.getId(), schema.getTag()));
        credentialDefinition.setType(CredentialType.CL);
        credentialDefinition.setVer(credentialDefinitionVersion);
        credentialDefinition.setPrimaryKey(credentialPrimaryPublicKey);
        credentialDefinition.setTag(credentialDefinitionTag);

        return credentialDefinition;
    }

}
