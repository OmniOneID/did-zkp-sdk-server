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

package org.omnione.did.zkp.exception;

public enum ZkpErrorCode implements ZkpErrorCodeInterface {
    ERR_CODE_CORE_SDK_BASE("SSDKZKP", ""),

    ERR_CODE_ZKP_FAIL("00001", "failure"),
    ERR_CODE_ZKP_SUCCESS("00000", "succeed"),
    ERR_CODE_ZKP_UNKNOWN("11111", "unknown"),

    ERR_CODE_ZKP_COMMON_BASE(ERR_CODE_CORE_SDK_BASE, "00", ""),
    ERR_CODE_ZKP_BIG_NUMBER_TO_BYTE_FAIL(ERR_CODE_ZKP_COMMON_BASE, "001", "big number from byte fail"),
    ERR_CODE_ZKP_BIG_NUMBER_FROM_BYTE_FAIL(ERR_CODE_ZKP_COMMON_BASE, "002", "big number from json fail"),
    ERR_CODE_ZKP_BIG_NUMBER_FROM_JSON_FAIL(ERR_CODE_ZKP_COMMON_BASE, "003", "big number hexa string fail"),
    ERR_CODE_ZKP_BIG_NUMBER_FROM_HEXA_STRING_FAIL(ERR_CODE_ZKP_COMMON_BASE, "004", "big number from hexa string fail"),
    ERR_CODE_ZKP_BIG_NUMBER_COMPARE_FAIL(ERR_CODE_ZKP_COMMON_BASE, "005", "big number compare fail"),
    ERR_CODE_ZKP_APPEND_BIG_NUMBER_FOR_HASH_FAIL(ERR_CODE_ZKP_COMMON_BASE, "006", "append big number for hash fail"),
    ERR_CODE_ZKP_FINAL_FOR_HASH_FAIL(ERR_CODE_ZKP_COMMON_BASE, "007", "final for hash fail"),
    ERR_CODE_ZKP_IO(ERR_CODE_ZKP_COMMON_BASE, "008", "io error"),
    ERR_CODE_ZKP_NO_SUCH_ALG(ERR_CODE_ZKP_COMMON_BASE, "009", "no such algorithm"),
    ERR_CODE_ZKP_NULL(ERR_CODE_ZKP_COMMON_BASE, "010", "null error"),
    ERR_CODE_ZKP_DUPLICATED(ERR_CODE_ZKP_COMMON_BASE, "011", "duplicated key"),
    ERR_CODE_ZKP_NOT_SUPPORTED_TYPE(ERR_CODE_ZKP_COMMON_BASE, "012", "not supported type"),

    ERR_CODE_ZKP_CREDENTIAL_MANAGER_BASE(ERR_CODE_CORE_SDK_BASE, "01", ""),
    ERR_CODE_ZKP_ISSUER_GENERATE_PR_PRIVATE_KEY_FAIL(ERR_CODE_ZKP_CREDENTIAL_MANAGER_BASE, "001", "generate pr private key fail"),
    ERR_CODE_ZKP_ISSUER_GENERATE_PR_PUBLIC_KEY_FAIL(ERR_CODE_ZKP_CREDENTIAL_MANAGER_BASE, "002", "generate pr public key fail"),
    ERR_CODE_ZKP_ISSUER_GENERATE_PR_KEY_METADATA_FAIL(ERR_CODE_ZKP_CREDENTIAL_MANAGER_BASE, "003", "generate pr key metadata fail"),
    ERR_CODE_ZKP_ISSUER_GENERATE_CRED_PRIMARY_KEY_FAIL(ERR_CODE_ZKP_CREDENTIAL_MANAGER_BASE, "004", "generate credential primary key fail"),
    ERR_CODE_ZKP_ISSUER_GENERATE_CRED_KEY_CORRECTNESS_PROOF_FAIL(ERR_CODE_ZKP_CREDENTIAL_MANAGER_BASE, "005", "generate credential key correctness proof fail"),
    ERR_CODE_ZKP_ISSUER_GENERATE_CRED_REVOCATION_KEY_FAIL(ERR_CODE_ZKP_CREDENTIAL_MANAGER_BASE, "006", "generate credential revocation key fail"),
    ERR_CODE_ZKP_ISSUER_INSERT_CRED_DEF_DATA_TO_WALLET_FAIL(ERR_CODE_ZKP_CREDENTIAL_MANAGER_BASE, "007", "generate credential definition data to wallet fail"),
    ERR_CODE_ZKP_ISSUER_SELECT_CRED_DEF_DATA_FROM_WALLET_FAIL(ERR_CODE_ZKP_CREDENTIAL_MANAGER_BASE, "008", "generate credential definition data from wallet fail"),
    ERR_CODE_ZKP_ISSUER_GENERATE_REVOCATION_KEY_FAIL(ERR_CODE_ZKP_CREDENTIAL_MANAGER_BASE, "009", "generate revocation key fail"),
    ERR_CODE_ZKP_ISSUER_GENERATE_REVOCATION_REGISTRY_FAIL(ERR_CODE_ZKP_CREDENTIAL_MANAGER_BASE, "010", "generate revocation registry fail"),
    ERR_CODE_ZKP_ISSUER_CREATE_AND_STORE_TAILS_FAIL(ERR_CODE_ZKP_CREDENTIAL_MANAGER_BASE, "011", "create and store tails fail"),
    ERR_CODE_ZKP_ISSUER_SELECT_REV_REG_DATA_FROM_WALLET_FAIL(ERR_CODE_ZKP_CREDENTIAL_MANAGER_BASE, "012", "select revocation registry data from wallet"),
    ERR_CODE_ZKP_ISSUER_ISSUED_CRED_NUMBER_OVER(ERR_CODE_ZKP_CREDENTIAL_MANAGER_BASE, "013", "issued credential number over"),
    ERR_CODE_ZKP_ISSUER_NO_COMPARE_CRED_DEF_ID(ERR_CODE_ZKP_CREDENTIAL_MANAGER_BASE, "014", "no compare credential definition id"),
    ERR_CODE_ZKP_ISSUER_CHECK_BLINDED_SECRETS_CORRECTNESS_PROOF_FAIL(ERR_CODE_ZKP_CREDENTIAL_MANAGER_BASE, "015", "check blinded secrets correctness proof fail"),
    ERR_CODE_ZKP_ISSUER_GET_AVAILABLE_REVOCATION_REGISTRY_FAIL(ERR_CODE_ZKP_CREDENTIAL_MANAGER_BASE, "016", "get available revocation registry fail"),
    ERR_CODE_ZKP_ISSUER_GENERATE_CREDENTIAL_CONTEXT_FAIL(ERR_CODE_ZKP_CREDENTIAL_MANAGER_BASE, "017", "generate credential context fail"),
    ERR_CODE_ZKP_ISSUER_GENERATE_PRIMARY_CREDENTIAL_FAIL(ERR_CODE_ZKP_CREDENTIAL_MANAGER_BASE, "018", "generate primary credential fail"),
    ERR_CODE_ZKP_ISSUER_GENERATE_SIGNATURE_CORRECTNESS_PROOF_FAIL(ERR_CODE_ZKP_CREDENTIAL_MANAGER_BASE, "019", "generate signature correctness proof fail"),
    ERR_CODE_ZKP_ISSUER_GENERATE_REVOCATION_CREDENTIAL_FAIL(ERR_CODE_ZKP_CREDENTIAL_MANAGER_BASE, "020", "generate revocation credential fail"),
    ERR_CODE_ZKP_ISSUER_GENERATE_WITNESS_FAIL(ERR_CODE_ZKP_CREDENTIAL_MANAGER_BASE, "021", "generate witness fail"),
    ERR_CODE_ZKP_ISSUER_GENERATE_CREDENTIAL_ID_FAIL(ERR_CODE_ZKP_CREDENTIAL_MANAGER_BASE, "022", "generate credential id fail"),
    ERR_CODE_ZKP_ISSUER_UPDATE_REV_REG_DATA_TO_WALLET_FAIL(ERR_CODE_ZKP_CREDENTIAL_MANAGER_BASE, "023", "update revocation registry filed"),
    ERR_CODE_ZKP_ISSUER_NO_COMPARE_REV_REG_DEF_ID(ERR_CODE_ZKP_CREDENTIAL_MANAGER_BASE, "024", "no compare revocation credential definition id"),
    ERR_CODE_ZKP_ISSUER_NOT_SATISFIED_CREDENTIAL_NUMBER(ERR_CODE_ZKP_CREDENTIAL_MANAGER_BASE, "025", "at least greater then maxCredNum 0"),
    ERR_CODE_ZKP_ISSUER_NOT_CONTAIN_REVOCATION_INDEX_IN_USED_ID(ERR_CODE_ZKP_CREDENTIAL_MANAGER_BASE, "026", "not contain revocation index in usedId"),

    ERR_CODE_ZKP_PROOF_MANAGER_BASE(ERR_CODE_CORE_SDK_BASE, "02", ""),
    ERR_CODE_ZKP_VERIFIER_DUPLICATE_RESTRICTION(ERR_CODE_ZKP_PROOF_MANAGER_BASE, "001", "duplicate restriction"),
    ERR_CODE_ZKP_VERIFIER_VERIFY_NON_REVOCATION_PROOF_FAIL(ERR_CODE_ZKP_PROOF_MANAGER_BASE, "002", "verify non revocation proof fail"),
    ERR_CODE_ZKP_VERIFIER_VERIFY_PRIMARY_EQ_PROOF_FAIL(ERR_CODE_ZKP_PROOF_MANAGER_BASE, "003", "verify primary equal proof fail"),
    ERR_CODE_ZKP_VERIFIER_VERIFY_PRIMARY_NE_PROOF_FAIL(ERR_CODE_ZKP_PROOF_MANAGER_BASE, "004", "verify primary non equal proof fail"),
    ERR_CODE_ZKP_VERIFIER_VERIFY_PRIMARY_PROOF_FAIL(ERR_CODE_ZKP_PROOF_MANAGER_BASE, "005", "verify primary proof fail"),
    ERR_CODE_ZKP_VERIFIER_NOT_SATISFIED_ATTRIBUTE_IN_SUB_PROOF(ERR_CODE_ZKP_PROOF_MANAGER_BASE, "006", "not satisfied attribute in sub proof"),

    ERR_CODE_ZKP_WALLET_BASE(ERR_CODE_CORE_SDK_BASE, "03", ""),
    ERR_CODE_ZKP_WALLET_CRYPTO_ENCRYPT(ERR_CODE_ZKP_WALLET_BASE, "001", "Failed to encrypt"),
    ERR_CODE_ZKP_WALLET_CRYPTO_DECRYPT(ERR_CODE_ZKP_WALLET_BASE, "002", "Failed to decrypt"),
    ERR_CODE_ZKP_WALLET_CRYPTO_GEN_RANDOM_FAIL(ERR_CODE_ZKP_WALLET_BASE, "003", "Failed to generate random byte"),
    ERR_CODE_ZKP_WALLET_CRYPTO_GEN_KEY_FAIL(ERR_CODE_ZKP_WALLET_BASE, "004", "Failed to generate key"),
    ERR_CODE_ZKP_WALLET_CRYPTO_COMPRESS_PUBLIC_KEY_FAIL(ERR_CODE_ZKP_WALLET_BASE, "005", "Failed to compress PublicKey"),
    ERR_CODE_ZKP_WALLET_CRYPTO_UNCOMPRESS_PUBLIC_KEY_FAIL(ERR_CODE_ZKP_WALLET_BASE, "006", "Failed to uncompress PublicKey"),
    ERR_CODE_ZKP_WALLET_SIG_FAIL_SIGN(ERR_CODE_ZKP_WALLET_BASE, "007", "Failed to signature"),
    ERR_CODE_ZKP_WALLET_SIG_VERIFY_SIGN_FAIL(ERR_CODE_ZKP_WALLET_BASE, "008", "Verify signature is failed"),
    ERR_CODE_ZKP_WALLET_SIG_COMPRESS_SIGN_FAIL(ERR_CODE_ZKP_WALLET_BASE, "009", "Failed to compress signature"),
    ERR_CODE_ZKP_WALLET_DISCONNECT(ERR_CODE_ZKP_WALLET_BASE, "010", "WalletManager is disconnected"),
    ERR_CODE_ZKP_WALLET_FILE_LOAD_FAIL(ERR_CODE_ZKP_WALLET_BASE, "011", "Failed to load the WalletFile"),
    ERR_CODE_ZKP_WALLET_FILE_WRITE_FAIL(ERR_CODE_ZKP_WALLET_BASE, "012", "Failed to write the WalletFile"),
    ERR_CODE_ZKP_WALLET_KEYID_NOT_EXIST(ERR_CODE_ZKP_WALLET_BASE, "013", "The keyId does not exist"),
    ERR_CODE_ZKP_WALLET_KEYID_ALREADY_EXIST(ERR_CODE_ZKP_WALLET_BASE, "014", "The KeyId is already existed"),
    ERR_CODE_ZKP_WALLET_KEYID_EMPTY_NAME(ERR_CODE_ZKP_WALLET_BASE, "015", "The Name for KeyId is empty"),
    ERR_CODE_ZKP_WALLET_INVALID_ALGORITHM_TYPE(ERR_CODE_ZKP_WALLET_BASE, "016", "Algorithm type is invalid"),
    ERR_CODE_ZKP_WALLET_INVALID_KEYID_NAME(ERR_CODE_ZKP_WALLET_BASE, "017", "The Name for KeyId must only be alphaNumeric"),
    ERR_CODE_ZKP_WALLET_IWKEY_IS_NULL(ERR_CODE_ZKP_WALLET_BASE, "018", "IWKey is null"),
    ERR_CODE_ZKP_WALLET_INVALID_PRIVATE_KEY(ERR_CODE_ZKP_WALLET_BASE, "019", "Invalid PrivateKey"),
    ERR_CODE_ZKP_WALLET_INVALID_PUBLIC_KEY(ERR_CODE_ZKP_WALLET_BASE, "020", "Invalid PublicKey"),
    ERR_CODE_ZKP_WALLET_KEYINFO_EMPTY(ERR_CODE_ZKP_WALLET_BASE, "021", "KeyInfo is empty"),
    ERR_CODE_ZKP_WALLET_PASSWORD_NOT_SET(ERR_CODE_ZKP_WALLET_BASE, "022", "The password does not set"),
    ERR_CODE_ZKP_WALLET_PASSWORD_NOT_MATCH_WITH_THE_SET_ONE(ERR_CODE_ZKP_WALLET_BASE, "023", "The password does not match with the set one"),
    ERR_CODE_ZKP_WALLET_INVALID_PASSWORD(ERR_CODE_ZKP_WALLET_BASE, "024", "The password is(are) invalid for use"),
    ERR_CODE_ZKP_WALLET_PASSWORD_SAME_AS_OLD(ERR_CODE_ZKP_WALLET_BASE, "025", "New password is the same as the old one"),
    ERR_CODE_ZKP_WALLET_AES_ENCRYPT_FAIL(ERR_CODE_ZKP_WALLET_BASE, "026", "AES Encryption is failed"),
    ERR_CODE_ZKP_WALLET_AES_DECRYPT_FAIL(ERR_CODE_ZKP_WALLET_BASE, "027", "AES Decryption is failed"),
    ERR_CODE_ZKP_WALLET_GEN_SECRET_FAIL(ERR_CODE_ZKP_WALLET_BASE, "028", "Failed to generate shared secret"),
    ERR_CODE_ZKP_WALLET_INVALID_SIGN_VALUE(ERR_CODE_ZKP_WALLET_BASE, "029", "Sign value is invalid"),
    ERR_CODE_ZKP_WALLET_NOT_FIND_RECID(ERR_CODE_ZKP_WALLET_BASE, "030", "could not find recid."),
    ERR_CODE_ZKP_WALLET_FAIL_CONVERT_COMPACT(ERR_CODE_ZKP_WALLET_BASE, "031", "fail convert sign data to eostype."),
    ERR_CODE_ZKP_WALLET_INVALID_R_S_VALUE(ERR_CODE_ZKP_WALLET_BASE, "032", "The r value must be 32 bytes."),
    ERR_CODE_ZKP_WALLET_DEFAULTKEYSTORE_KEYGEN_FAIL(ERR_CODE_ZKP_WALLET_BASE, "033", "Key generation in DefaultKeyStore is fail"),
    ERR_CODE_ZKP_WALLET_DEFAULTKEYSTORE_AUTHENTICATE_FAIL(ERR_CODE_ZKP_WALLET_BASE, "034", "Password authentication failed"),
    ERR_CODE_ZKP_WALLET_INVALID_METHODNAME(ERR_CODE_ZKP_WALLET_BASE, "035", "Method name must be lower case alphanumeric (colon accepted) and have range between from 1 to 20 length"),
    ERR_CODE_ZKP_WALLET_ADD_KEY_FAIL(ERR_CODE_ZKP_WALLET_BASE, "036", "Failed to add the key"),
    ERR_CODE_ZKP_WALLET_ALREADY_SECRET_PHRASE(ERR_CODE_ZKP_WALLET_BASE, "037", "SecretPhrase already exists"),
    ERR_CODE_ZKP_WALLET_INVALID_SECRET_PHRASE(ERR_CODE_ZKP_WALLET_BASE, "038", "Invalid SecretPhrase"),
    ERR_CODE_ZKP_WALLET_ALREADY_FILE(ERR_CODE_ZKP_WALLET_BASE, "039", "The file already exists"),
    ERR_CODE_ZKP_WALLET_NOT_EXISTS_FILE(ERR_CODE_ZKP_WALLET_BASE, "040", "The file not exists"),
    ERR_CODE_ZKP_WALLET_INVALID_WALLET_FILE(ERR_CODE_ZKP_WALLET_BASE, "041", "Invalid wallet file path with name");

    private String code;
    private String msg;

    private ZkpErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ZkpErrorCode(ZkpErrorCode errCodeKeymanagerKeyBase, String subCode, String msg) {
        this.code = errCodeKeymanagerKeyBase.getCode() + subCode;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public static ZkpErrorCodeInterface getEnumByCode(int code) {
        for (ZkpErrorCode iwCode : ZkpErrorCode.values()) {
            if (iwCode.getCode().equals(code)) {
                return iwCode;
            }
        }
        throw new AssertionError("Unknown Enum Code");
    }
}
