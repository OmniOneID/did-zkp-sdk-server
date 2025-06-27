---
puppeteer:
    pdf:
        format: A4
        displayHeaderFooter: true
        landscape: false
        scale: 0.8
        margin:
            top: 1.2cm
            right: 1cm
            bottom: 1cm
            left: 1cm
    image:
        quality: 100
        fullPage: false
---

ZKP SDK Server API
==

- Subject: ZKP SDK Server API
- Author: Sangjun Kim
- Date: 2025-04-28
- Version: v2.0.0

| Version | Date       | Changes                   |
| ------- | ---------- | -------------------------- |
| v2.0.0  | 2025-04-28 | Initial version            |

<div style="page-break-after: always;"></div>

# Table of Contents

 - [1. APIs](#1-apis)
   - [1.1. Credential Metadata](#11-credential-metadata)
     - [1.1.1. createSchema](#111-createschema)
     - [1.1.2. createDefinition](#112-createdefinition)
   - [1.2. Credential](#12-credential)
     - [1.2.1. createCredentialOffer](#121-createcredentialoffer)
     - [1.2.2. createCredential](#122-createcredential)
   - [1.3. Proof](#13-proof)
     - [1.3.1. requestProofReq](#131-requestproofreq)
     - [1.3.2. verifyProof](#132-verifyproof)
   - [1.4. ZkpWalletManager](#14-zkpwalletmanager)
     - [1.4.1. getZkpWalletManager](#141-getzkpwalletmanager)
     - [1.4.2. create](#142-create)
     - [1.4.3. connect](#143-connect)
     - [1.4.4. isConnect](#144-isconnect)
     - [1.4.5. addZkpKey](#145-addzkpkey)
     - [1.4.6. removeZkpKey](#146-removezkpkey)
     - [1.4.7. getZkpKeyElement](#147-getzkpkeyelement)
     - [1.4.8. isExistZkpKey](#148-isexistzkpkey)
     - [1.4.9. getZkpKeyIdList](#149-getzkpkeyidlist)
     - [1.4.10. removeAllZkpKeys](#1410-removeallzkpkeys)
     - [1.4.11. generateRandomZkpKey](#1411-generaterandomzkpkey)
     - [1.4.12. generateZkpKeyProof](#1412-generatezkpkeyproof)
     - [1.4.13. generateZkpSignature](#1413-generatezkpsignature)
     - [1.4.14. generateZkpSignatureProof](#1414-generatezkpsignatureproof)
     - [1.4.15. getCredentialPrimaryKeyPair](#1415-getcredentialprimarykeypair)
     - [1.4.16. getCredentialPrimaryPublicKey](#1416-getcredentialprimarypublickey)
 - [2. Reference Classes](#2-reference-classes)
   - [2.1. Enumerations](#21-enumerations)
     - [2.1.1. CredentialType](#211-credentialtype)
     - [2.1.2. Marker](#212-marker)
     - [2.1.3. PredicateType](#213-predicatetype)
   - [2.2. Credential](#22-credential)
     - [2.2.1. AttributeValue](#221-attributevalue)
     - [2.2.2. Credential](#222-credential)
     - [2.2.3. CredentialSignature](#223-credentialsignature)
     - [2.2.4. PrimaryCredentialSignature](#224-primarycredentialsignature)
     - [2.2.5. SignatureCorrectnessProof](#225-signaturecorrectnessproof)
     - [2.2.6. CredentialValues](#226-credentialvalues)
   - [2.3. Credential Offer](#23-credential-offer)
     - [2.3.1. CredentialOffer](#231-credentialoffer)
     - [2.3.2. KeyCorrectnessProof](#232-keycorrectnessproof)
   - [2.4. Credential Request](#24-credential-request)
     - [2.4.1. BlindedCredentialSecrets](#241-blindedcredentialsecrets)
     - [2.4.2. BlindedCredentialSecretsCorrectnessProof](#242-blindedcredentialsecretscorrectnessproof)
     - [2.4.3. CredentialRequest](#243-credentialrequest)
   - [2.5. Credential Definition](#25-credential-definition)
     - [2.5.1. CredentialDefinition](#251-credentialdefinition)
     - [2.5.2. CredentialDefinitionValue](#252-credentialdefinitionvalue)
   - [2.6. Proof](#26-proof)
     - [2.6.1. AggregatedProof](#261-aggregatedproof)
     - [2.6.2. Identifiers](#262-identifiers)
     - [2.6.3. NonCredentialSchema](#263-noncredentialschema)
     - [2.6.4. Predicate](#264-predicate)
     - [2.6.5. PrimaryEqualProof](#265-primaryequalproof)
     - [2.6.6. PrimaryPredicateInequalityProof](#266-primarypredicateinequalityproof)
     - [2.6.7. PrimaryProof](#267-primaryproof)
     - [2.6.8. Proof](#268-proof)
     - [2.6.9. SubProof](#269-subproof)
     - [2.6.10. ProofVerifyParam](#2610-proofverifyparam)
   - [2.7. Proof Request](#27-proof-request)
     - [2.7.1. AttributeInfo](#271-attributeinfo)
     - [2.7.2. PredicateInfo](#272-predicateinfo)
     - [2.7.3. ProofRequest](#273-proofrequest)
     - [2.7.4. RequestedProof](#274-requestedproof)
     - [2.7.5. SubProofRequest](#275-subproofrequest)
   - [2.8. Credential Schema](#28-credential-schema)
     - [2.8.1. AttributeType](#281-attributetype)
     - [2.8.2. CredentialSchema](#282-credentialschema)
   - [2.9. ZKP Keys](#29-zkp-keys)
     - [2.9.1. CredentialPrimaryKeyPair](#291-credentialprimarykeypair)
     - [2.9.2. CredentialPrimaryPrivateKey](#292-credentialprimaryprivatekey)
     - [2.9.3. CredentialPrimaryPublicKey](#293-credentialprimarypublickey)
     - [2.9.4. PublicKeyMetadata](#294-publickeymetadata)
     - [2.9.5. ZkpKeyElement](#295-zkpkeyelement)


# 1. APIs
## 1.1. Credential Metadata

## 1.1.1. createSchema

### Class Name
`ZkpCredentialMetadataManager`

### Function Name
`createSchema`

### Function Description
`Initial creation of the Credential Schema`


### Input Parameters
| Parameter   | Type            | Description                        | **M/O** | **Notes** |
|-------------|-----------------|------------------------------------|---------|-----------|
| issuerDid   | String           | Issuer's DID                      | M       |           |
| name        | String           | Schema name                       | M       |           |
| version     | String           | Schema version                    | M       |           |
| attrNames   | List&lt;String&gt; | List of attribute names           | M       |           |
| attrTypes   | List&lt;AttributeType&gt; | List of attribute types      | M       | Must match attrNames order |
| tag         | String           | Schema tag                        | M       | Optional grouping identifier |

## Output Parameters
| Type             | Description          | **M/O** | **Notes** |
|------------------|----------------------|---------|-----------|
| CredentialSchema | Created Schema object | M       |           |

### Function Declaration

```java
CredentialSchema createSchema(String issuerDid,
                               String name,
                               String version,
                               List<String> attrNames,
                               List<AttributeType> attrTypes,
                               String tag)
```

## Function Usage Example

```java

ZkpCredentialMetadataManager metadataManager = new ZkpCredentialMetadataManager();

String issuerDid = "did:omn:issuer";
String schemaName = "EmployeeCredential";
String schemaVersion = "1.0";
String tag = "employee";

List<String> attrNames = Arrays.asList("name", "employeeId", "department");
List<AttributeType> attrTypes = Arrays.asList(
    AttributeType.STRING,
    AttributeType.STRING,
    AttributeType.STRING
);

CredentialSchema credentialSchema = metadataManager.createSchema(
    issuerDid,
    schemaName,
    schemaVersion,
    attrNames,
    attrTypes,
    tag
);

```

## 1.1.2. createDefinition

### Class Name
`ZkpCredentialMetadataManager`

### Function Name
`createDefinition`

### Function Description
`Create a Credential Definition based on a Credential Schema.`

### Input Parameters
| Parameter                  | Type                          | Description                          | **M/O** | **Notes** |
|-----------------------------|-------------------------------|--------------------------------------|---------|-----------|
| issuerDid                   | String                        | Issuer's DID                         | M       |           |
| schema                      | CredentialSchema              | Schema information                  | M       | Result of createSchema |
| credentialPrimaryPublicKey  | CredentialPrimaryPublicKey    | Public key information for credential | M    |           |

### Output Parameters
| Type                 | Description                   | **M/O** | **Notes** |
|----------------------|-------------------------------|---------|-----------|
| CredentialDefinition | Created Credential Definition | M       |           |

### Function Declaration

```java
CredentialDefinition createDefinition(String issuerDid,
                                       CredentialSchema schema,
                                       CredentialPrimaryPublicKey credentialPrimaryPublicKey) throws Exception
```

---

## Function Usage Example

```java

ZkpCredentialMetadataManager metadataManager = new ZkpCredentialMetadataManager();

String issuerDid = "did:omn:issuer";
String schemaName = "EmployeeCredential";
String schemaVersion = "1.0";
String tag = "employee";

List<String> attrNames = Arrays.asList("name", "employeeId", "department");
List<AttributeType> attrTypes = Arrays.asList(
    AttributeType.STRING,
    AttributeType.STRING,
    AttributeType.STRING
);

CredentialSchema credentialSchema = metadataManager.createSchema(
    issuerDid,
    schemaName,
    schemaVersion,
    attrNames,
    attrTypes,
    tag
);

// Assume credentialPrimaryPublicKey is generated elsewhere
CredentialPrimaryPublicKey credentialPrimaryPublicKey = new CredentialPrimaryPublicKey();

CredentialDefinition credentialDefinition = metadataManager.createDefinition(
    issuerDid,
    credentialSchema,
    credentialPrimaryPublicKey
);
```


<br>

## 1.2. Credential

## 1.2.1. createCredentialOffer

### Class Name
`ZkpCredentialManager`

### Function Name
`createCredentialOffer`

### Function Description
`Create a Credential Offer based on Key Correctness Proof and related Information.`

### Input Parameters
| Parameter            | Type                     | Description                              | **M/O** | **Notes** |
|----------------------|---------------------------|-----------------------------------------|---------|-----------|
| keyCorrectnessProof   | KeyCorrectnessProof       | Correctness proof for public key     | M       |           |
| schemaId              | String                   | Associated Schema ID                  | M       |           |
| credDefId             | String                   | Associated Credential Definition ID     | M       |           |
| nonce                 | BigInteger               | Random nonce used for Security          | M       |           |

### Output Parameters
| Type             | Description               | **M/O** | **Notes** |
|------------------|----------------------------|---------|-----------|
| CredentialOffer  | Generated Credential Offer | M       |           |

### Function Declaration

```java
CredentialOffer createCredentialOffer(KeyCorrectnessProof keyCorrectnessProof,
                                       String schemaId,
                                       String credDefId,
                                       BigInteger nonce) throws Exception
```

## Function Usage Example

```java

ZkpCredentialManager credentialManager = new ZkpCredentialManager();

// Assume keyCorrectnessProof, schemaId, credDefId, nonce are already prepared
CredentialOffer credentialOffer = credentialManager.createCredentialOffer(
    keyCorrectnessProof,
    schemaId,
    credDefId,
    nonce
);

```

## 1.2.2. createCredential

### Class Name
`ZkpCredentialManager`

### Function Name
`createCredential`

### Function Description
`Issue a Credential by signing based on the Credential Request and associated Definitions.`

### Input Parameters
| Parameter             | Type                                 | Description                                | **M/O** | **Notes** |
|-----------------------|--------------------------------------|-------------------------------------------|---------|-----------|
| credentialDefinition  | CredentialDefinition                 | Credential Definition                     | M       |           |
| credSignature         | CredentialSignature                  | Generated Credential Signature            | M       |           |
| proof                 | SignatureCorrectnessProof            | Proof of Signature Correctness            | M       |           |
| credentialValue       | LinkedHashMap&lt;String, AttributeValue&gt; | Credential attributes and their values | M | Values must align with schema |
| credentialRequest     | CredentialRequest                    | Credential Request from Holder            | M       |           |
| nonce                 | BigInteger                           | Random nonce for request verification     | M       |           |

### Output Parameters
| Type         | Description         | **M/O** | **Notes** |
|--------------|---------------------|---------|-----------|
| Credential   | Issued Credential    | M       |           |

### Function Declaration

```java
Credential createCredential(CredentialDefinition credentialDefinition,
                             CredentialSignature credSignature,
                             SignatureCorrectnessProof proof,
                             LinkedHashMap<String, AttributeValue> redentialValue,
                             CredentialRequest credentialRequest,
                             BigInteger nonce) throws Exception
```

## Function Usage Example

```java
ZkpCredentialManager credentialManager = new ZkpCredentialManager();

// Assume credentialDefinition, credentialSignature, proof, credentialValue, redentialRequest, nonce are ready
Credential credential = credentialManager.createCredential(
    credentialDefinition,
    credentialSignature,
    proof,
    credentialValue,
    credentialRequest,
    nonce
);
```

<br>

## 1.3. Proof

## 1.3.1. requestProofReq

### Class Name
`ZkpProofManager`

### Function Name
`requestProofReq`

### Function Description
`Generate a Proof Request to verify specific attributes or predicates.`

### Input Parameters
| Parameter               | Type                              | Description                               | **M/O** | **Notes** |
|--------------------------|-----------------------------------|-------------------------------------------|---------|-----------|
| name                     | String                            | Name of the Proof Request                 | M       |           |
| version                  | String                            | Version of the Proof Request              | M       |           |
| proofRequestAttribute    | Map&lt;String, AttributeInfo&gt; | Attributes requested for proof           | M       |           |
| proofRequestPredicate    | Map&lt;String, PredicateInfo&gt; | Predicates requested for proof           | M       |           |
| verifierNonce            | BigInteger                        | Random nonce to ensure proof uniqueness   | M       |           |

### Output Parameters
| Type         | Description         | **M/O** | **Notes** |
|--------------|---------------------|---------|-----------|
| ProofRequest | Created Proof Request | M       |           |

### Function Declaration

```java
ProofRequest requestProofReq(String name,
                             String version,
                             Map<String, AttributeInfo> proofRequestAttribute,
                             Map<String, PredicateInfo> proofRequestPredicate,
                             BigInteger verifierNonce)
```

## Function Usage Example

```java

ZkpProofManager proofManager = new ZkpProofManager();

String proofName = "Proof of Age";
String proofVersion = "1.0";
Map<String, AttributeInfo> requestedAttributes = new HashMap<>();
Map<String, PredicateInfo> requestedPredicates = new HashMap<>();
BigInteger verifierNonce = new BigInteger(80, new SecureRandom());

ProofRequest proofRequest = proofManager.requestProofReq(
    proofName,
    proofVersion,
    requestedAttributes,
    requestedPredicates,
    verifierNonce
);
```


## 1.3.2. verifyProof

### Class Name
`ZkpProofManager`

### Function Name
`verifyProof`

### Function Description
`Verify the submitted Proof against the Proof Request and Verification Parameters.`

### Input Parameters
| Parameter          | Type                        | Description                                | **M/O** | **Notes** |
|--------------------|------------------------------|--------------------------------------------|---------|-----------|
| proof              | Proof                        | Proof presented by the holder              | M       |           |
| nonce              | BigInteger                   | Nonce used during the verification         | M       |           |
| proofRequest       | ProofRequest                 | Proof Request that defines what is verified| M       |           |
| proofVerifyParams  | List&lt;ProofVerifyParam&gt; | Parameters required for verifying proof   | M       |           |

### Output Parameters
| Type     | Description                      | **M/O** | **Notes** |
|----------|----------------------------------|---------|-----------|
| boolean  | Result of Proof Verification (true/false) | M     | true: verification success, false: failure |

### Function Declaration

```java
boolean verifyProof(Proof proof,
                    BigInteger nonce,
                    ProofRequest proofRequest,
                    List<ProofVerifyParam> proofVerifyParams) throws Exception
```

## Function Usage Example

```java
ZkpProofManager proofManager = new ZkpProofManager();

boolean isValid = proofManager.verifyProof(
    proof,
    nonce,
    proofRequest,
    proofVerifyParams
);

if (isValid) {
    System.out.println("Proof verification succeeded!");
} else {
    System.out.println("Proof verification failed.");
}
```

### 1.4. ZkpWalletManager

### 1.4.1. getZkpWalletManager

### Class Name
`ZkpWalletManager`

### Function Name
`getZkpWalletManager`

### Function Description
Returns a `ZkpWalletManagerInterface` instance depending on the selected `ZkpWalletManagerType`

#### Input Parameters
| Parameter | Type | Description | **M/O** | Notes |
|-----------|------|-------------|---------|-------|
| type | ZkpWalletManagerType | Wallet Manager Type(e.g., FILE) | M | |

#### Output Parameters
| Type | Description | **M/O** | Notes |
|------|-------------|---------|-------|
| ZkpWalletManagerInterface | Wallet Manager instance | M | |

#### Function Declaration
```java
public static ZkpWalletManagerInterface getZkpWalletManager(ZkpWalletManagerType type) throws Exception
```

### Function Usage Example
```java
ZkpWalletManagerInterface zkpWalletManager = ZkpWalletManagerFactory.getZkpWalletManager(ZkpWalletManagerFactory.ZkpWalletManagerType.FILE);
```

### 1.4.2. create

### Class Name
`ZkpWalletManagerInterface`

### Function Name
`create`

### Function Description
Create a new wallet file.

### Input Parameters
| Parameter          | Type                    | Description                       |
|--------------------|--------------------------|-----------------------------------|
| walletFilePath     | String                   | Path to create the wallet file    |
| securePassword     | char[]                   | Password for encryption           |
| walletEncryptType  | WalletEncryptType        | Type of encryption                |

### Output Parameters
void

### Function Declaration
```java
void create(String walletFilePath, char[] securePassword, WalletEncryptType walletEncryptType) throws Exception;
```

### Function Usage Example
```java
ZkpWalletManagerInterface zkpWalletManager = ZkpWalletManagerFactory.getZkpWalletManager(ZkpWalletManagerFactory.ZkpWalletManagerType.FILE);

zkpWalletManager.create("/issuer.zkpwallet", "1234".toCharArray(), WalletEncryptType.AES_256_CBC_PKCS5Padding);
```

### 1.4.3. connect

### Class Name
`ZkpWalletManagerInterface`

### Function Name
`connect`

### Function Description
Connect to an existing wallet file.

### Input Parameters
| Parameter       | Type    | Description                  |
|-----------------|---------|------------------------------|
| walletFilePath  | String  | Path of the wallet to connect |
| securePassword  | char[]  | Password                     |

### Output Parameters
void

### Function Declaration
```java
void connect(String walletFilePath, char[] securePassword) throws Exception;
```

### Function Usage Example
```java
ZkpWalletManagerInterface zkpWalletManager = ZkpWalletManagerFactory.getZkpWalletManager(ZkpWalletManagerFactory.ZkpWalletManagerType.FILE);

zkpWalletManager.connect("/issuer.zkpwallet", "1234".toCharArray());

```


### 1.4.4. isConnect

### Class Name
`ZkpWalletManagerInterface`

### Function Name
`isConnect`

### Function Description
Check whether the wallet is connected.

### Input Parameters
n/a

### Output Parameters
| Type     | Description     |
|----------|-----------------|
| boolean  | Connection state |

### Function Declaration
```java
boolean isConnect();
```

### Function Usage Example
```java
ZkpWalletManagerInterface zkpWalletManager = ZkpWalletManagerFactory.getZkpWalletManager(ZkpWalletManagerFactory.ZkpWalletManagerType.FILE);

boolean connected = zkpWalletManager.isConnect();
System.out.println("Is connected: " + connected);
```

### 1.4.5. addZkpKey

### Class Name
`ZkpWalletManagerInterface`

### Function Name
`addZkpKey`

### Function Description
Add a ZKP key to the wallet.

### Input Parameters
| Parameter      | Type          | Description             |
|----------------|---------------|-------------------------|
| zkpKeyElement  | ZkpKeyElement  | ZKP key to be added     |

### Output Parameters
void

### Function Declaration
```java
void addZkpKey(ZkpKeyElement zkpKeyElement) throws Exception;
```

### Function Usage Example
```java
ZkpWalletManagerInterface zkpWalletManager = ZkpWalletManagerFactory.getZkpWalletManager(ZkpWalletManagerFactory.ZkpWalletManagerType.FILE);

ZkpKeyElement zkpKeyElement = new ZkpKeyElement(); // assumed prepared 
zkpWalletManager.addZkpKey(zkpKeyElement);

```

### 1.4.6. removeZkpKey

### Class Name
`ZkpWalletManagerInterface`

### Function Name
`removeZkpKey`

### Function Description
Remove a specific ZKP key from the wallet.

### Input Parameters
| Parameter | Type    | Description       |
|-----------|---------|-------------------|
| keyId     | String  | ID of the key to remove |

### Output Parameters
void

### Function Declaration
```java
void removeZkpKey(String keyId) throws Exception;
```

### Function Usage Example
```java
ZkpWalletManagerInterface zkpWalletManager = ZkpWalletManagerFactory.getZkpWalletManager(ZkpWalletManagerFactory.ZkpWalletManagerType.FILE);

zkpWalletManager.removeZkpKey("zkpKeyId");

```


### 1.4.7. getZkpKeyElement

### Class Name
`ZkpWalletManagerInterface`

### Function Name
`getZkpKeyElement`

### Function Description
Retrieve a ZKP key by its ID.

### Input Parameters
| Parameter | Type    | Description     |
|-----------|---------|-----------------|
| keyId     | String  | ID of the key    |

### Output Parameters
| Type          | Description        |
|---------------|--------------------|
| ZkpKeyElement | Retrieved ZKP key   |

### Function Declaration
```java
ZkpKeyElement getZkpKeyElement(String keyId) throws Exception;
```

### Function Usage Example
```java
ZkpWalletManagerInterface zkpWalletManager = ZkpWalletManagerFactory.getZkpWalletManager(ZkpWalletManagerFactory.ZkpWalletManagerType.FILE);

ZkpKeyElement element = zkpWalletManager.getZkpKeyElement("zkpKeyId");

```

---


### 1.4.8. isExistZkpKey

### Class Name
`ZkpWalletManagerInterface`

### Function Name
`isExistZkpKey`

### Function Description
Check if a ZKP key exists.

### Input Parameters
| Parameter | Type    | Description      |
|-----------|---------|------------------|
| keyId     | String  | ID to check       |

### Output Parameters
| Type     | Description        |
|----------|--------------------|
| boolean  | Existence of the key |

### Function Declaration
```java
boolean isExistZkpKey(String keyId);
```

### Function Usage Example
```java
ZkpWalletManagerInterface zkpWalletManager = ZkpWalletManagerFactory.getZkpWalletManager(ZkpWalletManagerFactory.ZkpWalletManagerType.FILE);

boolean exists = zkpWalletManager.isExistZkpKey("zkpKeyId");
System.out.println("Key exists: " + exists);
```


### 1.4.9. getZkpKeyIdList

### Class Name
`ZkpWalletManagerInterface`

### Function Name
`getZkpKeyIdList`

### Function Description
Retrieve the list of all registered ZKP key IDs.

### Input Parameters
n/a

### Output Parameters
| Type            | Description         |
|-----------------|---------------------|
| List<String>    | List of ZKP key IDs  |

### Function Declaration
```java
List<String> getZkpKeyIdList();
```

### Function Usage Example
```java
ZkpWalletManagerInterface zkpWalletManager = ZkpWalletManagerFactory.getZkpWalletManager(ZkpWalletManagerFactory.ZkpWalletManagerType.FILE);

List<String> keyIdList = zkpWalletManager.getZkpKeyIdList();
System.out.println("ZKP Key IDs: " + keyIdList);
```

### 1.4.10. removeAllZkpKeys

### Class Name
`ZkpWalletManagerInterface`

### Function Name
`removeAllZkpKeys`

### Function Description
Remove all registered ZKP keys.

### Input Parameters
n/a

### Output Parameters
void

### Function Declaration
```java
void removeAllZkpKeys() throws Exception;
```

### Function Usage Example
```java
ZkpWalletManagerInterface zkpWalletManager = ZkpWalletManagerFactory.getZkpWalletManager(ZkpWalletManagerFactory.ZkpWalletManagerType.FILE);

zkpWalletManager.removeAllZkpKeys();

```

### 1.4.11. generateRandomZkpKey

### Class Name
`ZkpWalletManagerInterface`

### Function Name
`generateRandomZkpKey`

### Function Description
Generate a random ZKP key.

### Input Parameters
| Parameter   | Type             | Description                 |
|-------------|------------------|-----------------------------|
| keyId       | String            | ID of the key to be generated |
| attrNames   | List<String>      | List of attribute names      |

### Output Parameters
void

### Function Declaration
```java
void generateRandomZkpKey(String keyId, List<String> attrNames) throws Exception;
```

### Function Usage Example
```java
ZkpWalletManagerInterface zkpWalletManager = ZkpWalletManagerFactory.getZkpWalletManager(ZkpWalletManagerFactory.ZkpWalletManagerType.FILE);

List<String> attributes = Arrays.asList("name", "age", "email");
zkpWalletManager.generateRandomZkpKey("zkpKeyId", attributes);

```

### 1.4.12. generateZkpKeyProof

### Class Name
`ZkpWalletManagerInterface`

### Function Name
`generateZkpKeyProof`

### Function Description
Generate a ZKP Key Correctness Proof.

### Input Parameters
| Parameter | Type    | Description      |
|-----------|---------|------------------|
| keyId     | String  | ID of the key     |

### Output Parameters
| Type    | Description         |
|---------|---------------------|
| byte[]  | Generated proof data |

### Function Declaration
```java
byte[] generateZkpKeyProof(String keyId) throws Exception;
```

### Function Usage Example
```java
ZkpWalletManagerInterface zkpWalletManager = ZkpWalletManagerFactory.getZkpWalletManager(ZkpWalletManagerFactory.ZkpWalletManagerType.FILE);

byte[] proof = zkpWalletManager.generateZkpKeyProof("zkpKeyId");

```

### 1.4.13. generateZkpSignature

### Class Name
`ZkpWalletManagerInterface`

### Function Name
`generateZkpSignature`

### Function Description
Generate a ZKP-based credential signature.

### Input Parameters
| Parameter            | Type                  | Description                |
|----------------------|-----------------------|----------------------------|
| keyId                | String                | ID of the signing key      |
| credentialRequest    | CredentialRequest     | Credential request         |
| credentialValues     | CredentialValues      | Values for credential      |

### Output Parameters
| Type    | Description             |
|---------|-------------------------|
| byte[]  | Generated credential signature |

### Function Declaration
```java
byte[] generateZkpSignature(String keyId, CredentialRequest credentialRequest, CredentialValues credentialValues) throws Exception;
```

### Function Usage Example
```java
ZkpWalletManagerInterface zkpWalletManager = ZkpWalletManagerFactory.getZkpWalletManager(ZkpWalletManagerFactory.ZkpWalletManagerType.FILE);

CredentialRequest credentialRequest = new CredentialRequest(); // assumed prepared
CredentialValues credentialValues = new CredentialValues();    // assumed prepared
byte[] signature = zkpWalletManager.generateZkpSignature("zkpKeyId", credentialRequest, credentialValues);

```

### 1.4.14. generateZkpSignatureProof

### Class Name
`ZkpWalletManagerInterface`

### Function Name
`generateZkpSignatureProof`

### Function Description
Generate a proof for a credential signature.

### Input Parameters
| Parameter           | Type    | Description               |
|---------------------|---------|---------------------------|
| keyId               | String  | ID of the signing key     |
| credentialSignature | byte[]  | Credential signature      |
| nonce               | byte[]  | Random nonce for verification |

### Output Parameters
| Type    | Description          |
|---------|----------------------|
| byte[]  | Generated proof data  |

### Function Declaration
```java
byte[] generateZkpSignatureProof(String keyId, byte[] credentialSignature, byte[] nonce) throws Exception;
```

### Function Usage Example
```java
ZkpWalletManagerInterface zkpWalletManager = ZkpWalletManagerFactory.getZkpWalletManager(ZkpWalletManagerFactory.ZkpWalletManagerType.FILE);

byte[] credentialSignature = {}; // assumed prepared
byte[] nonce = {};                // assumed prepared

byte[] proof = zkpWalletManager.generateZkpSignatureProof("zkpKeyId", credentialSignature, nonce);

```

### 1.4.15. getCredentialPrimaryKeyPair

### Class Name
`ZkpWalletManagerInterface`

### Function Name
`getCredentialPrimaryKeyPair`

### Function Description
Retrieve the Credential Primary Key Pair.

### Input Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| keyId     | String | ID of the key |

### Output Parameters
| Type                    | Description              |
|--------------------------|--------------------------|
| CredentialPrimaryKeyPair | Credential primary key pair |

### Function Declaration
```java
CredentialPrimaryKeyPair getCredentialPrimaryKeyPair(String keyId) throws Exception;
```

### Function Usage Example
```java
ZkpWalletManagerInterface zkpWalletManager = ZkpWalletManagerFactory.getZkpWalletManager(ZkpWalletManagerFactory.ZkpWalletManagerType.FILE);

CredentialPrimaryKeyPair keyPair = zkpWalletManager.getCredentialPrimaryKeyPair("zkpKeyId");

```

### 1.4.16. getCredentialPrimaryPublicKey

### Class Name
`ZkpWalletManagerInterface`

### Function Name
`getCredentialPrimaryPublicKey`

### Function Description
Retrieve the Credential Primary Public Key.

### Input Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| keyId     | String | ID of the key |

### Output Parameters
| Type                       | Description                   |
|-----------------------------|-------------------------------|
| CredentialPrimaryPublicKey  | Credential primary public key |

### Function Declaration
```java
CredentialPrimaryPublicKey getCredentialPrimaryPublicKey(String keyId) throws Exception;
```

### Function Usage Example
```java
ZkpWalletManagerInterface zkpWalletManager = ZkpWalletManagerFactory.getZkpWalletManager(ZkpWalletManagerFactory.ZkpWalletManagerType.FILE);

CredentialPrimaryPublicKey publicKey = zkpWalletManager.getCredentialPrimaryPublicKey("zkpKeyId");

```

<br>

## 2. Reference Classes

### 2.1. Enumerations

### 2.1.1. CredentialType
| Field | Type | Description |
|-------|------|-------------|
| CL | int | Camenisch-Lysyanskaya credential type (value: 1) |

### 2.1.2. Marker
| Field | Type | Description |
|-------|------|-------------|
| CRED_SCHEMA | int | Credential schema marker (value: 2) |
| CRED_DEF | int | Credential definition marker (value: 3) |

### 2.1.3. PredicateType
| Field | Type | Description |
|-------|------|-------------|
| GE | String | Greater than or equal to operator (">=") |
| LE | String | Less than or equal to operator ("<=") |
| GT | String | Greater than operator (">") |
| LT | String | Less than operator ("<") |
| EQ | String | Equal to operator ("==") |

### 2.2. Credential

### 2.2.1. AttributeValue
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| encoded | BigInteger | Encoded attribute value | M |
| raw | String | Raw attribute value | M |

### 2.2.2. Credential
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| credentialId | String | Unique identifier for credential | M |
| schemaId | String | ID of the schema this credential is based on | M |
| credDefId | String | ID of the credential definition | M |
| revRegDefId | String | ID of revocation registry definition | O |
| values | LinkedHashMap<String, AttributeValue> | Attribute values contained in credential | M |
| credentialSignature | CredentialSignature | Signature of the credential | M |
| signatureCorrectnessProof | SignatureCorrectnessProof | Proof of correctness for signature | M |

### 2.2.3. CredentialSignature
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| primaryCredential | PrimaryCredentialSignature | Primary credential signature | M |

### 2.2.4. PrimaryCredentialSignature
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| a | BigInteger | Signature component a | M |
| e | BigInteger | Signature component e | M |
| v | BigInteger | Signature component v | M |
| q | BigInteger | Signature component q | M |
| m2 | BigInteger | Signature component m2 | M |

### 2.2.5. SignatureCorrectnessProof
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| se | BigInteger | Correctness proof component se | M |
| c | BigInteger | Correctness proof component c | M |

### 2.2.6. CredentialValues
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| values | Map<String, AttributeValue> | Map of attribute names to values | M |

### 2.3. Credential Offer

### 2.3.1. CredentialOffer
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| nonce | BigInteger | Nonce value | M |
| schemaId | String | ID of schema for credential | M |
| credDefId | String | ID of credential definition | M |
| keyCorrectnessProof | KeyCorrectnessProof | Proof of correctness for key | M |
| methodName | String | Method name for issuance | O |

### 2.3.2. KeyCorrectnessProof
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| c | BigInteger | Correctness proof component c | M |
| xzCap | BigInteger | Correctness proof component xzCap | M |
| xrCap | LinkedHashMap<String, BigInteger> | Correctness proof component xrCap | M |

### 2.4. Credential Request

### 2.4.1. BlindedCredentialSecrets
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| u | BigInteger | Blinded component u | M |
| ur | PointG1 | Blinded component ur | M |
| vPrime | BigInteger | Blinded component vPrime | M |
| vrPrime | GroupOrderElement | Blinded component vrPrime | M |
| hiddenAttrs | List<String> | List of hidden attributes | M |
| committedAttrs | LinkedHashMap<String, BigInteger> | Map of committed attributes | M |

### 2.4.2. BlindedCredentialSecretsCorrectnessProof
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| c | BigInteger | Correctness proof component c | M |
| vDashCap | BigInteger | Correctness proof component vDashCap | M |
| mCaps | LinkedHashMap<String, BigInteger> | Correctness proof component mCaps | M |
| rCaps | Map<String, BigInteger> | Correctness proof component rCaps | M |

### 2.4.3. CredentialRequest
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| proverDID | String | DID of the prover | M |
| credDefId | String | ID of the credential definition | M |
| nonce | BigInteger | Nonce value | M |
| blindedMs | BlindedCredentialSecrets | Blinded credential secrets | M |
| blindedMsProof | BlindedCredentialSecretsCorrectnessProof | Correctness proof for blinded secrets | M |

### 2.5. Credential Definition

### 2.5.1. CredentialDefinition
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| id | String | Unique identifier for credential definition | M |
| schemaId | String | ID of the associated schema | M |
| ver | String | Version string | M |
| type | CredentialType | Type of credential | M |
| value | CredentialDefinitionValue | Value of credential definition | M |
| tag | String | Tag string | O |

### 2.5.2. CredentialDefinitionValue
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| primary | CredentialPrimaryPublicKey | Primary public key for credential | M |

### 2.6. Proof

### 2.6.1. AggregatedProof
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| cHash | BigInteger | Hash value c | M |
| cList | Vector<byte[]> | List of c values | M |

### 2.6.2. Identifiers
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| credDefId | String | ID of credential definition | M |
| schemaId | String | ID of schema | M |

### 2.6.3. NonCredentialSchema
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| nonCredSchema | Set<String> | Set of non-credential schema attributes | M |

### 2.6.4. Predicate
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| pType | PredicateType | Type of predicate | M |
| pValue | int | Value for predicate | M |
| attrName | String | Name of attribute for predicate | M |

### 2.6.5. PrimaryEqualProof
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| revealedAttrs | TreeMap<String, BigInteger> | Map of revealed attributes | M |
| aPrime | BigInteger | Proof component aPrime | M |
| e | BigInteger | Proof component e | M |
| v | BigInteger | Proof component v | M |
| m | Map<String, BigInteger> | Proof component m | M |
| m2 | BigInteger | Proof component m2 | M |

### 2.6.6. PrimaryPredicateInequalityProof
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| u | Map<String, BigInteger> | Proof component u | M |
| r | Map<String, BigInteger> | Proof component r | M |
| t | Map<String, BigInteger> | Proof component t | M |
| mj | BigInteger | Proof component mj | M |
| alpha | BigInteger | Proof component alpha | M |
| predicate | Predicate | Predicate for this proof | M |

### 2.6.7. PrimaryProof
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| eqProof | PrimaryEqualProof | Equal proof component | M |
| neProofs | Vector<PrimaryPredicateInequalityProof> | Inequality proof components | M |

### 2.6.8. Proof
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| proofs | Vector<SubProof> | List of sub-proofs | M |
| aggregatedProof | AggregatedProof | Aggregated proof | M |
| requestedProof | RequestedProof | Requested proof | M |
| identifiers | List<Identifiers> | List of identifiers | M |

### 2.6.9. SubProof
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| primaryProof | PrimaryProof | Primary proof component | M |

### 2.6.10. ProofVerifyParam
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| schema | CredentialSchema | Schema for verification | M |
| credentialDefinition | CredentialDefinition | Credential definition for verification | M |

### 2.7. Proof Request

### 2.7.1. AttributeInfo
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| name | String | Name of attribute | M |
| restrictions | List<Map<String, String>> | Restrictions on attribute | O |

### 2.7.2. PredicateInfo
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| name | String | Name of attribute for predicate | M |
| pType | PredicateType | Type of predicate | M |
| pValue | int | Value for predicate | M |
| restrictions | List<Map<String, String>> | Restrictions on predicate | O |

### 2.7.3. ProofRequest
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| name | String | Name of proof request | M |
| version | String | Version string | M |
| nonce | BigInteger | Nonce value | M |
| requestedAttributes | Map<String, AttributeInfo> | Map of requested attributes | M |
| requestedPredicates | Map<String, PredicateInfo> | Map of requested predicates | M |

### 2.7.4. RequestedProof
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| selfAttestedAttrs | Map<String, String> | Self-attested attributes | O |
| predicates | Map<String, Map<String, String>> | Predicates in proof | O |
| revealedAttrs | Map<String, Map<String, String>> | Revealed attributes | O |
| unrevealedAttrs | Map<String, Map<String, String>> | Unrevealed attributes | O |

### 2.7.5. SubProofRequest
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| revealedAttrs | TreeSet<String> | Set of revealed attribute names | M |
| predicates | HashSet<Predicate> | Set of predicates | M |

### 2.8. Credential Schema

### 2.8.1. AttributeType
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| label | String | Label for attribute type | M |
| type | Type | Type of attribute (STRING or NUMBER) | M |

### 2.8.2. CredentialSchema
| Field | Type | Description | M/O |
|-------|------|-------------|-----|
| id | String | Unique identifier for schema | M |
| name | String | Name of schema | M |
| version | String | Version string | M |
| attrNames | List<String> | List of attribute names | M |
| attrTypes | List<AttributeType> | List of attribute types | M |
| tag | String | Tag string | O |

### 2.9. ZKP Keys

### 2.9.1. CredentialPrimaryKeyPair
| Field | Type | Description | M/O |
|------|------|------|----------|
| privateKey | CredentialPrimaryPrivateKey | 개인 키 | M |
| publicKey | CredentialPrimaryPublicKey | 공개 키 | M |
| publicKeyMetadata | PublicKeyMetadata | 공개 키 메타데이터 | M |

### 2.9.2. CredentialPrimaryPrivateKey
| Field | Type | Description | M/O |
|------|------|------|----------|
| p | BigInteger | 소수 p | M |
| q | BigInteger | 소수 q | M |

### 2.9.3. CredentialPrimaryPublicKey
| Field | Type | Description | M/O |
|------|------|------|----------|
| n | BigInteger | 공개 모듈러스 n | M |
| z | BigInteger | 공개 값 z | M |
| s | BigInteger | 공개 값 s | M |
| r | LinkedHashMap<String, BigInteger> | 속성 값들 | M |
| rctxt | BigInteger | 무작위 커밋 | M |

### 2.9.4. PublicKeyMetadata
| Field | Type | Description | M/O |
|------|------|------|----------|
| xz | BigInteger | xz 값 | M |
| xr | LinkedHashMap<String, BigInteger> | xr 값 맵 | M |

### 2.9.5. ZkpKeyElement
| Field | Type | Description | M/O |
|------|------|------|----------|
| keyId | String | 키 식별자 | M |
| type | String | 키 유형 | M |
| privateKey | String | 직렬화된 개인 키 | M |
| publicKey | String | 직렬화된 공개 키 | M |
| publicKeyMetadata | String | 직렬화된 공개 키 메타데이터 | M |
