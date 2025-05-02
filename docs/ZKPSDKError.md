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

ZKP SDK Error
==

- Topic: ZKPSDKError
- Author: Sangjun Kim
- Date: 2025-04-30
- Version: v2.0.0

| Version          | Date       | Changes                  |
| ---------------- | ---------- | ------------------------ |
| v2.0.0  | 2025-04-30 | Initial version          |

<div style="page-break-after: always;"></div>

# Table of Contents
- [Model](#model)
  - [Error Response](#error-response)
- [Error Code](#error-code)
  - [1.1. Common (00xxx)](#11-common00xxx)
  - [1.2. CredentialManager (01xxx)](#12-credentialmanager01xxx)
  - [1.3. ProofManager (02xxx)](#13-proofmanager02xxx)
  - [1.4. ZkpWalletManager (03xxx)](#14-zkpwalletmanager03xxx)

# Model
## Error Response

### Description
```
Error struct for ZKP SDK Server. It has code and message pair.
Code starts with SSDKZKP.
```

### Declaration
```java
public class ErrorResponse {
    private final String code;
    private final String description;
}
```

### Property

| Name               | Type       | Description                            | **M/O** | **Note**              |
|--------------------|------------|----------------------------------------|---------|-----------------------|
| code               | String     | Error code. It starts with SSDKZKP     |    M    |                       | 
| message            | String     | Error description                      |    M    |                       | 

<br>

# Error Code
## 1. ZKP SDK Server
### 1.1. Common(00xxx)

| Error Code   | Error Message                             | Description |
|--------------|-------------------------------------------|-------------|
| SSDKZKP00001 | big number from byte fail                 | -           |
| SSDKZKP00002 | big number from json fail                 | -           |
| SSDKZKP00003 | big number hexa string fail               | -           |
| SSDKZKP00004 | big number from hexa string fail          | -           |
| SSDKZKP00005 | big number compare fail                   | -           |
| SSDKZKP00006 | append big number for hash fail           | -           |
| SSDKZKP00007 | final for hash fail                       | -           |
| SSDKZKP00008 | error io                                  | -           |
| SSDKZKP00009 | no such algorithm                         | -           |
| SSDKZKP00010 | null                                      | -           |
| SSDKZKP00011 | duplicated key                            | -           |
| SSDKZKP00012 | not supported type                        | -           |

<br>

### 1.2. CredentialManager(01xxx)

| Error Code   | Error Message                                      | Description |
|--------------|----------------------------------------------------|-------------|
| SSDKZKP01001 | generate pr private key fail                       | -           |
| SSDKZKP01002 | generate pr public key fail                        | -           |
| SSDKZKP01003 | generate pr key metadata fail                      | -           |
| SSDKZKP01004 | generate credential primary key fail               | -           |
| SSDKZKP01005 | generate credential key correctness proof fail     | -           |
| SSDKZKP01006 | generate credential revocation key fail            | -           |
| SSDKZKP01007 | generate credential definition data to wallet fail | -           |
| SSDKZKP01008 | generate credential definition data from wallet fail | -         |
| SSDKZKP01009 | generate revocation key fail                       | -           |
| SSDKZKP01010 | generate revocation registry fail                  | -           |
| SSDKZKP01011 | create and store tails fail                        | -           |
| SSDKZKP01012 | select revocation registry data from wallet        | -           |
| SSDKZKP01013 | issued credential number over                      | -           |
| SSDKZKP01014 | no compare credential definition id                | -           |
| SSDKZKP01015 | check blinded secrets correctness proof fail       | -           |
| SSDKZKP01016 | get available revocation registry fail             | -           |
| SSDKZKP01017 | generate credential context fail                   | -           |
| SSDKZKP01018 | generate primary credential fail                   | -           |
| SSDKZKP01019 | generate signature correctness proof fail          | -           |
| SSDKZKP01020 | generate revocation credential fail                | -           |
| SSDKZKP01021 | generate witness fail                              | -           |
| SSDKZKP01022 | generate credential id fail                        | -           |
| SSDKZKP01023 | update revocation registry filed                   | -           |
| SSDKZKP01024 | no compare revocation credential definition id     | -           |
| SSDKZKP01025 | at least greater then maxCredNum 0                 | -           |
| SSDKZKP01026 | not contain revocation index in usedId             | -           |

<br>

### 1.3. ProofManager(02xxx)

| Error Code   | Error Message                                 | Description |
|--------------|-----------------------------------------------|-------------|
| SSDKZKP02001 | duplicate restriction                         | -           |
| SSDKZKP02002 | verify non revocation proof fail              | -           |
| SSDKZKP02003 | verify primary equal proof fail               | -           |
| SSDKZKP02004 | verify primary non equal proof fail           | -           |
| SSDKZKP02005 | verify primary proof fail                     | -           |
| SSDKZKP02006 | not satisfied attribute in sub proof          | -           |

<br>

### 1.4. ZkpWalletManager(03xxx)

| Error Code   | Error Message                                      | Description |
|--------------|----------------------------------------------------|-------------|
| SSDKZKP03001 | Failed to encrypt                                  | -           |
| SSDKZKP03002 | Failed to decrypt                                  | -           |
| SSDKZKP03003 | Failed to generate random byte                     | -           |
| SSDKZKP03004 | Failed to generate key                             | -           |
| SSDKZKP03005 | Failed to compress PublicKey                       | -           |
| SSDKZKP03006 | Failed to uncompress PublicKey                     | -           |
| SSDKZKP03007 | Failed to signature                                | -           |
| SSDKZKP03008 | Verify signature is failed                         | -           |
| SSDKZKP03009 | Failed to compress signature                       | -           |
| SSDKZKP03010 | WalletManager is disconnected                      | -           |
| SSDKZKP03011 | Failed to load the WalletFile                      | -           |
| SSDKZKP03012 | Failed to write the WalletFile                     | -           |
| SSDKZKP03013 | The keyId does not exist                           | -           |
| SSDKZKP03014 | The KeyId is already existed                       | -           |
| SSDKZKP03015 | The Name for KeyId is empty                        | -           |
| SSDKZKP03016 | Algorithm type is invalid                          | -           |
| SSDKZKP03017 | The Name for KeyId must only be alphaNumeric       | -           |
| SSDKZKP03018 | IWKey is null                                      | -           |
| SSDKZKP03019 | Invalid PrivateKey                                 | -           |
| SSDKZKP03020 | Invalid PublicKey                                  | -           |
| SSDKZKP03021 | KeyInfo is empty                                   | -           |
| SSDKZKP03022 | The password does not set                          | -           |
| SSDKZKP03023 | The password does not match with the set one       | -           |
| SSDKZKP03024 | The password is(are) invalid for use               | -           |
| SSDKZKP03025 | New password is the same as the old one            | -           |
| SSDKZKP03026 | AES Encryption is failed                           | -           |
| SSDKZKP03027 | AES Decryption is failed                           | -           |
| SSDKZKP03028 | Failed to generate shared secret                   | -           |
| SSDKZKP03029 | Sign value is invalid                              | -           |
| SSDKZKP03030 | could not find recid.                              | -           |
| SSDKZKP03031 | fail convert sign data to eostype.                 | -           |
| SSDKZKP03032 | The r value must be 32 bytes.                      | -           |
| SSDKZKP03033 | Key generation in DefaultKeyStore is fail          | -           |
| SSDKZKP03034 | Password authentication failed                     | -           |
| SSDKZKP03035 | Method name must be lower case alphanumeric (colon accepted) and have range between from 1 to 20 length | - |
| SSDKZKP03036 | Failed to add the key                              | -           |
| SSDKZKP03037 | SecretPhrase already exists                        | -           |
| SSDKZKP03038 | Invalid SecretPhrase                               | -           |
| SSDKZKP03039 | The file already exists                            | -           |
| SSDKZKP03040 | The file not exists                                | -           |
| SSDKZKP03041 | Invalid wallet file path with name                 | -           |

<br>
