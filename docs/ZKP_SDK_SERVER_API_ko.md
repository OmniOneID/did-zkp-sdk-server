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

- 주제: ZKP SDK Server API
- 작성: 김상준
- 일자: 2025-04-28
- 버전: v2.0.0

| 버전   | 일자       | 변경 내용                 |
| ------ | ---------- | -------------------------|
| v2.0.0 | 2025-04-28 | 초기 작성                 |


<div style="page-break-after: always;"></div>

# 목차
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
 - [2. 참조 클래스](#2-참조-클래스)
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
`자격증명 스키마를 최초로 생성합니다.`


### Input Parameters
| Parameter   | Type            | Description                        | **M/O** | **Notes** |
|-------------|-----------------|------------------------------------|---------|-----------|
| issuerDid   | String           | 발급자의 DID                      | M       |           |
| name        | String           | 스키마 이름                        | M       |           |
| version     | String           | 스키마 버전                        | M       |           |
| attrNames   | List<String>     | 속성 이름 목록                    | M       |           |
| attrTypes   | List<AttributeType> | 속성 타입 목록                | M       | attrNames 순서와 일치해야 함 |
| tag         | String           | 스키마 태그                         | M       | 선택적 그룹 식별자 |

## Output Parameters
| Type             | Description          | **M/O** | **Notes** |
|------------------|----------------------|---------|-----------|
| CredentialSchema | 생성된 스키마 객체     | M       |           |

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
`스키마를 기반으로 자격증명 정의를 생성합니다.`

### Input Parameters
| Parameter                  | Type                          | Description                          | **M/O** | **Notes** |
|-----------------------------|-------------------------------|--------------------------------------|---------|-----------|
| issuerDid                   | String                        | 발급자의 DID                         | M       |           |
| schema                      | CredentialSchema              | 스키마 정보                          | M       | createSchema 결과 |
| credentialPrimaryPublicKey  | CredentialPrimaryPublicKey    | 자격증명 발급용 공개키 정보            | M       |           |

### Output Parameters
| Type                 | Description                   | **M/O** | **Notes** |
|----------------------|-------------------------------|---------|-----------|
| CredentialDefinition | 생성된 자격증명 정의 객체       | M       |           |

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
`키 정합성 증명 및 관련 정보를 기반으로 CredentialOffer를 생성합니다.`

### Input Parameters
| Parameter            | Type                     | Description                              | **M/O** | **Notes** |
|----------------------|---------------------------|------------------------------------------|---------|-----------|
| keyCorrectnessProof  | KeyCorrectnessProof       | 공개키의 정합성 증명                    | M       |           |
| schemaId             | String                    | 관련된 스키마 ID                          | M       |           |
| credDefId            | String                    | 관련된 자격증명 정의 ID                   | M       |           |
| nonce                | BigInteger                | 보안용 랜덤 난수                         | M       |           |

### Output Parameters
| Type             | Description               | **M/O** | **Notes** |
|------------------|----------------------------|---------|-----------|
| CredentialOffer  | 생성된 CredentialOffer     | M       |           |

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
`자격증명 요청 및 정의에 기반하여 서명된 Credential을 발급합니다.`

### Input Parameters
| Parameter             | Type                               | Description                                      | **M/O** | **Notes** |
|-----------------------|------------------------------------|--------------------------------------------------|---------|-----------|
| credentialDefinition  | CredentialDefinition               | 자격증명 정의                                   | M       |           |
| credSignature         | CredentialSignature                | 생성된 자격증명 서명                            | M       |           |
| proof                 | SignatureCorrectnessProof          | 서명 정합성 증명                                | M       |           |
| credentialValue       | LinkedHashMap<String, AttributeValue> | 자격증명 속성 이름과 값                       | M       | 스키마와 정렬되어야 함 |
| credentialRequest     | CredentialRequest                  | 소유자의 자격증명 요청                          | M       |           |
| nonce                 | BigInteger                         | 요청 검증용 랜덤 난수                           | M       |           |

### Output Parameters
| Type         | Description         | **M/O** | **Notes** |
|--------------|---------------------|---------|-----------|
| Credential   | 발급된 Credential    | M       |           |

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
`속성 또는 조건 검증을 위한 증명 요청을 생성합니다.`

### Input Parameters
| Parameter               | Type                         | Description                               | **M/O** | **Notes** |
|--------------------------|------------------------------|-------------------------------------------|---------|-----------|
| name                     | String                       | 증명 요청 이름                            | M       |           |
| version                  | String                       | 증명 요청 버전                            | M       |           |
| proofRequestAttribute    | Map<String, AttributeInfo>   | 요청할 속성 정보                          | M       |           |
| proofRequestPredicate    | Map<String, PredicateInfo>   | 요청할 조건 정보                          | M       |           |
| verifierNonce            | BigInteger                   | 증명 고유성을 위한 난수                   | M       |           |

### Output Parameters
| Type         | Description         | **M/O** | **Notes** |
|--------------|---------------------|---------|-----------|
| ProofRequest | 생성된 Proof Request | M       |           |

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
`제출된 증명을 증명 요청과 검증 매개변수를 기준으로 검증합니다.`

### Input Parameters
| Parameter          | Type                        | Description                                 | **M/O** | **Notes** |
|--------------------|------------------------------|---------------------------------------------|---------|-----------|
| proof              | Proof                        | 제출된 증명 객체                             | M       |           |
| nonce              | BigInteger                   | 검증 시 사용한 난수                           | M       |           |
| proofRequest       | ProofRequest                 | 검증 기준이 되는 증명 요청                    | M       |           |
| proofVerifyParams  | List&lt;ProofVerifyParam&gt;       | 검증에 필요한 파라미터 목록                   | M       |           |

### Output Parameters
| Type     | Description                     | **M/O** | **Notes** |
|----------|----------------------------------|---------|-----------|
| boolean  | 증명 검증 결과 | M       | true: 성공, false: 실패          |

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
`ZkpZkpWalletManager`

### Function Name
`getZkpWalletManager`

### Function Description
선택한 `ZkpWalletManagerType`에 따라 `ZkpWalletManagerInterface` 인스턴스를 반환합니다.

#### Input Parameters
| Parameter | Type | Description | **M/O** | Notes |
|-----------|------|-------------|---------|-------|
| type | ZkpWalletManagerType | 지갑 관리자 타입 (예: FILE) | M | |

#### Output Parameters
| Type | Description | **M/O** | Notes |
|------|-------------|---------|-------|
| ZkpWalletManagerInterface | 반환된 지갑 관리자 인스턴스 | M | |

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
새로운 지갑 파일을 생성합니다.

### Input Parameters
| Parameter          | Type            | Description                   |
|--------------------|------------------|-------------------------------|
| walletFilePath     | String           | 지갑 파일 생성 경로           |
| securePassword     | char[]           | 암호화에 사용할 비밀번호      |
| walletEncryptType  | WalletEncryptType| 암호화 방식                    |

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
기존 지갑 파일에 연결합니다.

### Input Parameters
| Parameter       | Type   | Description               |
|-----------------|--------|---------------------------|
| walletFilePath  | String | 연결할 지갑 파일의 경로   |
| securePassword  | char[] | 비밀번호                   |

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
지갑 연결 여부를 확인합니다.

### Input Parameters
n/a

### Output Parameters
| Type     | Description     |
|----------|-----------------|
| boolean  | 연결 상태 |

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
지갑에 ZKP 키를 추가합니다.

### Input Parameters
| Parameter      | Type          | Description           |
|----------------|---------------|-----------------------|
| zkpKeyElement  | ZkpKeyElement | 추가할 ZKP 키 객체    |

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
지정한 ZKP 키를 지갑에서 제거합니다.

### Input Parameters
| Parameter | Type   | Description          |
|-----------|--------|----------------------|
| keyId     | String | 제거할 키의 식별자    |

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
키 식별자로 ZKP 키를 조회합니다.

### Input Parameters
| Parameter | Type   | Description      |
|-----------|--------|------------------|
| keyId     | String | 조회할 키의 식별자    |

### Output Parameters
| Type          | Description        |
|---------------|--------------------|
| ZkpKeyElement | 조회한 ZKP key   |

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
지정한 키 식별자가 존재하는지 확인합니다.

### Input Parameters
| Parameter | Type   | Description           |
|-----------|--------|-----------------------|
| keyId     | String | 존재 여부를 확인할 키의 식별자 |

### Output Parameters
| Type     | Description        |
|----------|--------------------|
| boolean  | 키의 존재여부 |

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
등록된 모든 ZKP 키 식별자 목록을 반환합니다.

### Input Parameters
n/a

### Output Parameters
| Type            | Description         |
|-----------------|---------------------|
| List<String>    | ZKP 키 식별자 목록  |

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
등록된 모든 ZKP 키를 삭제합니다.

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
랜덤한 ZKP 키를 생성합니다.

### Input Parameters
| Parameter   | Type         | Description              |
|-------------|--------------|--------------------------|
| keyId       | String       | 생성할 키의 ID           |
| attrNames   | List\<String> | 속성 이름 목록           |

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
ZKP 키 정합성 증명을 생성합니다.

### Input Parameters
| Parameter | Type   | Description     |
|-----------|--------|-----------------|
| keyId     | String | 키의 식별자     |

### Output Parameters
| Type    | Description         |
|---------|---------------------|
| byte[]  | 생성된 키 증명 데이터 |

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
ZKP 기반 자격증명 서명을 생성합니다.

### Input Parameters
| Parameter            | Type              | Description             |
|----------------------|-------------------|-------------------------|
| keyId                | String            | 서명할 키의 식별자          |
| credentialRequest    | CredentialRequest | 자격증명 요청           |
| credentialValues     | CredentialValues  | 자격증명 속성 값        |

### Output Parameters
| Type    | Description             |
|---------|-------------------------|
| byte[]  | 생성된 credential signature |

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
자격증명 서명에 대한 증명을 생성합니다.

### Input Parameters
| Parameter           | Type   | Description                  |
|---------------------|--------|------------------------------|
| keyId               | String | 서명에 사용된 키 식별자           |
| credentialSignature | byte[] | 생성된 자격증명 서명          |
| nonce               | byte[] | 검증을 위한 난수              |

### Output Parameters
| Type    | Description          |
|---------|----------------------|
| byte[]  | 생성된 자격증명에 대한 서명 데이터  |

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
자격증명 발급용 개인키/공개키 쌍을 조회합니다.

### Input Parameters
| Parameter | Type   | Description      |
|-----------|--------|------------------|
| keyId     | String | 조회할 키 식별자     |

### Output Parameters
| Type                    | Description              |
|--------------------------|--------------------------|
| CredentialPrimaryKeyPair | 자격증명 발급용 키쌍 |

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
자격증명 발급용 공개키를 조회합니다.

### Input Parameters
| Parameter | Type   | Description     |
|-----------|--------|-----------------|
| keyId     | String | 조회할 키 식별자     |

### Output Parameters
| Type                       | Description                   |
|-----------------------------|-------------------------------|
| CredentialPrimaryPublicKey  | 자격증명 발급용 공개키 |

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

## 2. 참조 클래스

### 2.1. Enumerations

### 2.1.1. CredentialType
| Field | Type | Description |
|------|------|------|
| CL | int | Camenisch-Lysyanskaya 자격증명 유형 (값: 1) |

### 2.1.2. Marker
| Field | Type | Description |
|------|------|------|
| CRED_SCHEMA | int | 자격증명 스키마 마커 (값: 2) |
| CRED_DEF | int | 자격증명 정의 마커 (값: 3) |

### 2.1.3. PredicateType
| Field | Type | Description |
|------|------|------|
| GE | String | 이상 연산자 (">=") |
| LE | String | 이하 연산자 ("<=") |
| GT | String | 초과 연산자 (">") |
| LT | String | 미만 연산자 ("<") |
| EQ | String | 같음 연산자 ("==") |

### 2.2. Credential

### 2.2.1. AttributeValue
| Field | Type | Description | M/O |
|------|------|------|----------|
| encoded | BigInteger | 인코딩된 속성 값 | M |
| raw | String | 원본 속성 값 | M |

### 2.2.2. Credential
| Field | Type | Description | M/O |
|------|------|------|----------|
| credentialId | String | 자격증명의 고유 식별자 | M |
| schemaId | String | 참조된 스키마 ID | M |
| credDefId | String | 참조된 자격증명 정의 ID | M |
| revRegDefId | String | 철회 등록 정의 ID | 선택 |
| values | LinkedHashMap<String, AttributeValue> | 자격증명에 포함된 속성 값 | M |
| credentialSignature | CredentialSignature | 자격증명 서명 | M |
| signatureCorrectnessProof | SignatureCorrectnessProof | 서명의 정합성 증명 | M |

### 2.2.3. CredentialSignature
| Field | Type | Description | M/O |
|------|------|------|----------|
| primaryCredential | PrimaryCredentialSignature | 주요 자격증명 서명 | M |

### 2.2.4. PrimaryCredentialSignature
| Field | Type | Description | M/O |
|------|------|------|----------|
| a | BigInteger | 서명 구성 요소 a | M |
| e | BigInteger | 서명 구성 요소 e | M |
| v | BigInteger | 서명 구성 요소 v | M |
| q | BigInteger | 서명 구성 요소 q | M |
| m2 | BigInteger | 서명 구성 요소 m2 | M |

### 2.2.5. SignatureCorrectnessProof
| Field | Type | Description | M/O |
|------|------|------|----------|
| se | BigInteger | 정합성 증명 se 값 | M |
| c | BigInteger | 정합성 증명 c 값 | M |

### 2.2.6. CredentialValues
| Field | Type | Description | M/O |
|------|------|------|----------|
| values | Map<String, AttributeValue> | 속성명-값 쌍의 맵 | M |

### 2.3. Credential Offer

### 2.3.1. CredentialOffer
| Field | Type | Description | M/O |
|------|------|------|----------|
| nonce | BigInteger | 난수 값 | M |
| schemaId | String | 자격증명 스키마 ID | M |
| credDefId | String | 자격증명 정의 ID | M |
| keyCorrectnessProof | KeyCorrectnessProof | 키 정합성 증명 | M |
| methodName | String | 발급 방식 명칭 | 선택 |

### 2.3.2. KeyCorrectnessProof
| Field | Type | Description | M/O |
|------|------|------|----------|
| c | BigInteger | 구성 요소 c | M |
| xzCap | BigInteger | 구성 요소 xzCap | M |
| xrCap | LinkedHashMap<String, BigInteger> | 구성 요소 xrCap | M |

### 2.4. Credential Request

### 2.4.1. BlindedCredentialSecrets
| Field | Type | Description | M/O |
|------|------|------|----------|
| u | BigInteger | 블라인딩된 구성 요소 u | M |
| ur | PointG1 | 블라인딩된 구성 요소 ur | M |
| vPrime | BigInteger | 블라인딩된 구성 요소 vPrime | M |
| vrPrime | GroupOrderElement | 블라인딩된 구성 요소 vrPrime | M |
| hiddenAttrs | List<String> | 숨겨진 속성 목록 | M |
| committedAttrs | LinkedHashMap<String, BigInteger> | 커밋된 속성 맵 | M |

### 2.4.2. BlindedCredentialSecretsCorrectnessProof
| Field | Type | Description | M/O |
|------|------|------|----------|
| c | BigInteger | 정합성 증명 구성 요소 c | M |
| vDashCap | BigInteger | 정합성 증명 구성 요소 vDashCap | M |
| mCaps | LinkedHashMap<String, BigInteger> | 정합성 증명 구성 요소 mCaps | M |
| rCaps | Map<String, BigInteger> | 정합성 증명 구성 요소 rCaps | M |

### 2.4.3. CredentialRequest
| Field | Type | Description | M/O |
|------|------|------|----------|
| proverDID | String | 증명자 DID | M |
| credDefId | String | 자격증명 정의 ID | M |
| nonce | BigInteger | 난수 값 | M |
| blindedMs | BlindedCredentialSecrets | 블라인딩된 자격증명 비밀 정보 | M |
| blindedMsProof | BlindedCredentialSecretsCorrectnessProof | 블라인딩된 비밀 정보에 대한 정합성 증명 | M |

### 2.5. Credential Definition

### 2.5.1. CredentialDefinition
| Field | Type | Description | M/O |
|------|------|------|----------|
| id | String | 자격증명 정의의 고유 식별자 | M |
| schemaId | String | 연결된 스키마 ID | M |
| ver | String | 버전 문자열 | M |
| type | CredentialType | 자격증명 유형 | M |
| value | CredentialDefinitionValue | 자격증명 정의 값 | M |
| tag | String | 태그 문자열 | 선택 |

### 2.5.2. CredentialDefinitionValue
| Field | Type | Description | M/O |
|------|------|------|----------|
| primary | CredentialPrimaryPublicKey | 자격증명의 기본 공개키 | M |

### 2.6. Proof

### 2.6.1. AggregatedProof
| Field | Type | Description | M/O |
|------|------|------|----------|
| cHash | BigInteger | 해시 값 c | M |
| cList | Vector<byte[]> | c 값 리스트 | M |

### 2.6.2. Identifiers
| Field | Type | Description | M/O |
|------|------|------|----------|
| credDefId | String | 자격증명 정의 ID | M |
| schemaId | String | 스키마 ID | M |

### 2.6.3. NonCredentialSchema
| Field | Type | Description | M/O |
|------|------|------|----------|
| nonCredSchema | Set<String> | 자격증명 외 속성 목록 | M |

### 2.6.4. Predicate
| Field | Type | Description | M/O |
|------|------|------|----------|
| pType | PredicateType | 조건 연산자 유형 | M |
| pValue | int | 조건에 사용될 값 | M |
| attrName | String | 조건이 적용될 속성 이름 | M |

### 2.6.5. PrimaryEqualProof
| Field | Type | Description | M/O |
|------|------|------|----------|
| revealedAttrs | TreeMap<String, BigInteger> | 공개된 속성 맵 | M |
| aPrime | BigInteger | 증명 구성 요소 aPrime | M |
| e | BigInteger | 증명 구성 요소 e | M |
| v | BigInteger | 증명 구성 요소 v | M |
| m | Map<String, BigInteger> | 증명 구성 요소 m | M |
| m2 | BigInteger | 증명 구성 요소 m2 | M |

### 2.6.6. PrimaryPredicateInequalityProof
| Field | Type | Description | M/O |
|------|------|------|----------|
| u | Map<String, BigInteger> | 구성 요소 u | M |
| r | Map<String, BigInteger> | 구성 요소 r | M |
| t | Map<String, BigInteger> | 구성 요소 t | M |
| mj | BigInteger | 구성 요소 mj | M |
| alpha | BigInteger | 구성 요소 alpha | M |
| predicate | Predicate | 조건 정보 | M |

### 2.6.7. PrimaryProof
| Field | Type | Description | M/O |
|------|------|------|----------|
| eqProof | PrimaryEqualProof | 동일성 증명 구성 요소 | M |
| neProofs | Vector<PrimaryPredicateInequalityProof> | 부등식 증명 구성 요소 리스트 | M |

### 2.6.8. Proof
| Field | Type | Description | M/O |
|------|------|------|----------|
| proofs | Vector<SubProof> | 하위 증명 목록 | M |
| aggregatedProof | AggregatedProof | 집계 증명 | M |
| requestedProof | RequestedProof | 요청된 증명 | M |
| identifiers | List<Identifiers> | 관련 식별자 목록 | M |

### 2.6.9. SubProof
| Field | Type | Description | M/O |
|------|------|------|----------|
| primaryProof | PrimaryProof | 기본 증명 구성 요소 | M |

### 2.6.10. ProofVerifyParam
| Field | Type | Description | M/O |
|------|------|------|----------|
| schema | CredentialSchema | 검증에 사용할 스키마 | M |
| credentialDefinition | CredentialDefinition | 검증에 사용할 자격증명 정의 | M |

### 2.7. Proof Request

### 2.7.1. AttributeInfo
| Field | Type | Description | M/O |
|------|------|------|----------|
| name | String | 요청하는 속성 이름 | M |
| restrictions | List<Map<String, String>> | 속성에 대한 제약 조건 | O |

### 2.7.2. PredicateInfo
| Field | Type | Description | M/O |
|------|------|------|----------|
| name | String | 조건을 적용할 속성 이름 | M |
| pType | PredicateType | 조건 유형 | M |
| pValue | int | 비교 기준 값 | M |
| restrictions | List<Map<String, String>> | 조건에 대한 제약 조건 | O |

### 2.7.3. ProofRequest
| Field | Type | Description | M/O |
|------|------|------|----------|
| name | String | 증명 요청 이름 | M |
| version | String | 버전 문자열 | M |
| nonce | BigInteger | 난수 값 | M |
| requestedAttributes | Map<String, AttributeInfo> | 요청된 속성 정보 맵 | M |
| requestedPredicates | Map<String, PredicateInfo> | 요청된 조건 정보 맵 | M |

### 2.7.4. RequestedProof
| Field | Type | Description | M/O |
|------|------|------|----------|
| selfAttestedAttrs | Map<String, String> | 자기 주장 속성 (Holder가 직접 입력한 값) | O |
| predicates | Map<String, Map<String, String>> | 제출된 조건 정보 | O |
| revealedAttrs | Map<String, Map<String, String>> | 공개된 속성 정보 | O |
| unrevealedAttrs | Map<String, Map<String, String>> | 공개되지 않은 속성 정보 | O |

### 2.7.5. SubProofRequest
| Field | Type | Description | M/O |
|------|------|------|----------|
| revealedAttrs | TreeSet<String> | 공개할 속성 이름 목록 | M |
| predicates | HashSet<Predicate> | 적용할 조건 목록 | M |

### 2.8. Credential Schema

### 2.8.1. AttributeType
| Field | Type | Description | M/O |
|------|------|------|----------|
| label | String | 속성의 라벨 | M |
| type | Type | 속성의 유형 (STRING 또는 NUMBER) | M |

### 2.8.2. CredentialSchema
| Field | Type | Description | M/O |
|------|------|------|----------|
| id | String | 스키마 고유 식별자 | M |
| name | String | 스키마 이름 | M |
| version | String | 버전 문자열 | M |
| attrNames | List<String> | 속성 이름 목록 | M |
| attrTypes | List<AttributeType> | 속성 유형 목록 | M |
| tag | String | 태그 문자열 | O |

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
