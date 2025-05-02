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

package org.omnione.did.zkp.wallet.key.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.omnione.did.crypto.enums.MultiBaseType;
import org.omnione.did.crypto.exception.CryptoException;
import org.omnione.did.crypto.util.CryptoUtils;
import org.omnione.did.crypto.util.MultiBaseUtils;
import org.omnione.did.zkp.datamodel.credential.CredentialValues;
import org.omnione.did.zkp.exception.ZkpErrorCode;
import org.omnione.did.zkp.exception.ZkpException;
import org.omnione.did.zkp.crypto.generator.ZkpKeyPairGenerator;
import org.omnione.did.zkp.crypto.keypair.CredentialPrimaryKeyPair;
import org.omnione.did.zkp.datamodel.credential.CredentialSignature;
import org.omnione.did.zkp.datamodel.credentialrequest.BlindedCredentialSecrets;
import org.omnione.did.zkp.datamodel.credentialrequest.CredentialRequest;
import org.omnione.did.zkp.wallet.crypto.encryption.EncryptionHelper;
import org.omnione.did.zkp.wallet.enums.ZkpWalletEncryptType;
import org.omnione.did.zkp.wallet.file.ZkpCoreWalletFileHelper;
import org.omnione.did.zkp.wallet.file.ZkpWalletFile;
import org.omnione.did.zkp.wallet.file.ZkpWalletFileHandler.ZkpWalletFileHelper;
import org.omnione.did.zkp.wallet.key.ZkpWalletManagerInterface;
import org.omnione.did.zkp.wallet.key.adapter.ZkpWalletManagerAdapter;
import org.omnione.did.zkp.wallet.key.data.*;
import org.omnione.did.zkp.wallet.key.data.ZkpKeyElement.ZkpKeyType;
import org.omnione.did.zkp.wallet.util.Constants;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ZkpWalletManagerImpl implements ZkpWalletManagerInterface {

	protected ZkpWalletFileHelper walletFileHelper;
	protected ZkpWalletFile zkpWalletFile;
	
	public byte[] derivedKeyBytes = null;
	private ZkpWallet cryptoZkpWallet;
	
	EncryptionHelper encryptionHelper = new EncryptionHelper();
	
	public ZkpWalletManagerImpl() {
	
	}
	public ZkpWalletManagerImpl(String walletFilePath) throws ZkpException {
		if (walletFilePath == null || walletFilePath.isEmpty()) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_INVALID_WALLET_FILE);

		}
		
		this.zkpWalletFile = new ZkpWalletFile(walletFilePath);
		if (!this.zkpWalletFile.isExist()) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_FILE_LOAD_FAIL);
		}
	}
	
	@Override
	public void create (String walletFilePath, char[] securePassword, ZkpWalletEncryptType zkpWalletEncryptType)
			throws ZkpException {
		if (walletFilePath == null || walletFilePath.isEmpty()) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_INVALID_WALLET_FILE);

		}

		if (securePassword == null || securePassword.length == 0) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_INVALID_PASSWORD);
		}

		setDefaultEncryptionInfo(walletFilePath, securePassword, zkpWalletEncryptType);
		generateSecretPhrase(securePassword);
	}

	private void generateSecretPhrase(char[] securePassword) throws ZkpException {
		walletFileHelper = new ZkpCoreWalletFileHelper(this.zkpWalletFile);

		if (walletFileHelper.isExistSecretPhrase()) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_ALREADY_SECRET_PHRASE);
		}

		// generate & save a secretPhrase
		byte[] secretPhraseBytes = walletFileHelper.generateSecretPhrase(securePassword);

		ZkpWallet cryptoZkpWallet = this.zkpWalletFile.getData();
		try {
			cryptoZkpWallet.getHead().getSecureKeyInfo().setSecretPhrase(MultiBaseUtils.encode(secretPhraseBytes, MultiBaseType.base58btc));
		} catch (CryptoException e) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_INVALID_SECRET_PHRASE, e);
		}
		this.zkpWalletFile.write(cryptoZkpWallet);

	}

	private void setDefaultEncryptionInfo(String filePath, char[] securePassword, ZkpWalletEncryptType zkpWalletEncryptType)
			throws ZkpException {

		this.zkpWalletFile = new ZkpWalletFile(filePath);

		if (this.zkpWalletFile.isExist()) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_ALREADY_FILE);
		}

		ZkpWallet data = new ZkpWallet();
		HeadElement head = new HeadElement();

		String[] splitEncryptType = zkpWalletEncryptType.getRawValue().split("-");
		String aesAlgorithm = splitEncryptType.length > 0 ? splitEncryptType[0] : null;
		Integer keySize = splitEncryptType.length > 1 ? Integer.valueOf(splitEncryptType[1]) / 8 : null;
		String mode = splitEncryptType.length > 2 ? splitEncryptType[2] : null;
		String padding = splitEncryptType.length > 3 ? splitEncryptType[3] : null;

		EncryptionInfoElement encryptionInfoElement = new EncryptionInfoElement();
		encryptionInfoElement.setAesAlgorithm(aesAlgorithm);
		encryptionInfoElement.setKeySize(keySize);
		encryptionInfoElement.setMode(mode);
		encryptionInfoElement.setPadding(padding);

		head.setEncryptionInfo(encryptionInfoElement);

		SecureKeyInfoElement secureKeyInfoElement = new SecureKeyInfoElement();
		secureKeyInfoElement.setIterations(2048);
		try {
			secureKeyInfoElement.setSalt(MultiBaseUtils.encode(CryptoUtils.generateSalt(),MultiBaseType.getByCharacter(Constants.keyEncodingType)));
		} catch (CryptoException e) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_CRYPTO_GEN_RANDOM_FAIL, e);
		}

		head.setSecureKeyInfo(secureKeyInfoElement);

		head.setEncoding(new EncodingElement());
		data.setHead(head);
		// write and load
		this.zkpWalletFile.write(data);

	}

	@Override
	public boolean isConnect() {
		return (derivedKeyBytes != null ? true : false);
	}

	@Override
	public boolean disConnect() {
		if (derivedKeyBytes != null) {
			Arrays.fill(derivedKeyBytes, (byte) 0x00);
			derivedKeyBytes = null;
		}

		this.zkpWalletFile.clearData();

		return true;
	}

	@Override
	public void connect(String filePath, char[] securePassword) throws ZkpException {
	
		if (securePassword == null || securePassword.length == 0) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_INVALID_PASSWORD);
		}
		
		this.zkpWalletFile = new ZkpWalletFile(filePath);
		if (!this.zkpWalletFile.isExist()) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_NOT_EXISTS_FILE);
		}
		
		this.walletFileHelper = new ZkpCoreWalletFileHelper(this.zkpWalletFile);
		byte[] result = this.walletFileHelper.authenticate(securePassword);
		
		derivedKeyBytes = new byte[result.length];
		System.arraycopy(result, 0, derivedKeyBytes, 0, result.length);	
		
		this.cryptoZkpWallet = this.zkpWalletFile.getData();

	}

	private byte[] generateDerivedKey(char[] source, EncryptionInfoElement encryptionInfoElement,
			SecureKeyInfoElement secureKeyInfoElement) throws ZkpException {
		byte[] derivedKey = null;

		try {
			derivedKey = encryptionHelper.pbkdf2(source, MultiBaseUtils.decode(secureKeyInfoElement.getSalt()),
					secureKeyInfoElement.getIterations(), encryptionInfoElement.getKeySize());

		} catch (CryptoException e) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_DEFAULTKEYSTORE_KEYGEN_FAIL, e);
		}

		return derivedKey;

	}

	private String generateSecretPhrase(char[] source, EncryptionInfoElement encryptionInfoElement,
			SecureKeyInfoElement secureKeyInfoElement) throws ZkpException {
		String secretPhrase;
		try {

			byte[] derivedKey = generateDerivedKey(source, encryptionInfoElement, secureKeyInfoElement);

			byte[] key = Arrays.copyOfRange(derivedKey, 0, encryptionInfoElement.getKeySize());
			byte[] iv = Arrays.copyOfRange(derivedKey, encryptionInfoElement.getKeySize(), derivedKey.length);

			byte[] data = encryptionHelper.encrypt(Constants.initialPhrase.getBytes(StandardCharsets.UTF_8), key, iv,
					encryptionInfoElement.getSymmetricCipherTypeString(), encryptionInfoElement.getPadding());

			secretPhrase = MultiBaseUtils.encode(data, MultiBaseType.base58btc);

			return secretPhrase;
		} catch (CryptoException e) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_DEFAULTKEYSTORE_KEYGEN_FAIL, e);
		}

	}

	@Override
	public void changePassword(char[] currenSecurePassword, char[] newSecurePassword) throws ZkpException {

		if (!zkpWalletFile.isExist()) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_FILE_LOAD_FAIL);
		}

		ZkpWallet tmpZkpWalletData = null;

		tmpZkpWalletData = zkpWalletFile.getData();

		if (tmpZkpWalletData == null) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_FILE_LOAD_FAIL);
		}

		String secretPhrase = tmpZkpWalletData.getHead().getSecureKeyInfo().getSecretPhrase();
		
		if (secretPhrase == null || secretPhrase.length() == 0) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_PASSWORD_NOT_SET);
		}

		if (currenSecurePassword == null || currenSecurePassword.length == 0 || newSecurePassword == null || newSecurePassword.length == 0) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_INVALID_PASSWORD);
		}
	


		// todo. Duplicate logic present. Needs to be fixed
		String recoveredSecretPhrase = null;	
		recoveredSecretPhrase = generateSecretPhrase(currenSecurePassword, tmpZkpWalletData.getHead().getEncryptionInfo()
					, tmpZkpWalletData.getHead().getSecureKeyInfo());
		
		if (recoveredSecretPhrase == null) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_DEFAULTKEYSTORE_KEYGEN_FAIL);
		}

		if (!secretPhrase.contentEquals(recoveredSecretPhrase)) { 
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_PASSWORD_NOT_MATCH_WITH_THE_SET_ONE);
		} else if (Arrays.equals(currenSecurePassword, newSecurePassword)) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_PASSWORD_SAME_AS_OLD);
		}

		byte[] proxyByte = generateDerivedKey(currenSecurePassword, tmpZkpWalletData.getHead().getEncryptionInfo(),
				tmpZkpWalletData.getHead().getSecureKeyInfo());

		this.derivedKeyBytes = new byte[proxyByte.length];
		System.arraycopy(proxyByte, 0, this.derivedKeyBytes, 0, proxyByte.length);

		ArrayList<ZkpKeyElement> keys = null;  // Decrypted key info
		ArrayList<ZkpKeyElement> oldKeys = tmpZkpWalletData.getZkpKeys();
		if (oldKeys != null) {
			keys = decryptPrivateKey(oldKeys);
		}

		String newSecretPhrase = null;

		newSecretPhrase = generateSecretPhrase(newSecurePassword, tmpZkpWalletData.getHead().getEncryptionInfo(),
				tmpZkpWalletData.getHead().getSecureKeyInfo());

		HeadElement head = tmpZkpWalletData.getHead();
		SecureKeyInfoElement secureKeyInfoElement = head.getSecureKeyInfo();
		secureKeyInfoElement.setSecretPhrase(newSecretPhrase);
		head.setSecureKeyInfo(secureKeyInfoElement);
		tmpZkpWalletData.setHead(head);

		Arrays.fill(proxyByte, (byte) 0x00);
		proxyByte = generateDerivedKey(newSecurePassword, tmpZkpWalletData.getHead().getEncryptionInfo(),
				tmpZkpWalletData.getHead().getSecureKeyInfo());


		Arrays.fill(this.derivedKeyBytes, (byte) 0x00);
		this.derivedKeyBytes = null;

		
		this.derivedKeyBytes = new byte[proxyByte.length];
		System.arraycopy(proxyByte, 0, this.derivedKeyBytes, 0, proxyByte.length);
		
		if (keys != null && keys.size() > 0) {
			for (ZkpKeyElement element : keys) {

				byte[] decoded = null;
                try {
                    decoded = MultiBaseUtils.decode(element.getPrivateKey());
                } catch (CryptoException e) {
                    throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_CRYPTO_ENCRYPT);
                }
	
				String encodedPrivateKey = encrypt(decoded, tmpZkpWalletData.getHead().getEncoding().getKeyEncodingType());

				if (encodedPrivateKey == null) {
					throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_CRYPTO_ENCRYPT);
				}
				element.setPrivateKey(encodedPrivateKey);
			}

			tmpZkpWalletData.setZkpKeys(keys);
			
		}
		
		zkpWalletFile.write(tmpZkpWalletData);
	}

	private byte[] symEncPrivateKey(byte[] privateKey) throws ZkpException {
		return symEncPrivateKey(privateKey, derivedKeyBytes);
	}

	private byte[] symEncPrivateKey(byte[] privateKey, byte[] derivedKey) throws ZkpException {

		HeadElement head = this.cryptoZkpWallet.getHead();
		EncryptionInfoElement encryptionInfo = head.getEncryptionInfo();

		byte[] key = Arrays.copyOfRange(derivedKey, 0, encryptionInfo.getKeySize());
		byte[] iv = Arrays.copyOfRange(derivedKey, encryptionInfo.getKeySize(), derivedKey.length);

		byte[] data = encryptionHelper.encrypt(privateKey, key, iv, encryptionInfo.getSymmetricCipherTypeString(),
				encryptionInfo.getPadding());

		return data;

	}

	protected byte[] symDecPrivateKey(byte[] encPrivateKey) throws ZkpException {
		return symDecPrivateKey(encPrivateKey, derivedKeyBytes);
	}

	protected byte[] symDecPrivateKey(byte[] encPrivateKey, byte[] derivedKey) throws ZkpException {

		HeadElement head = this.cryptoZkpWallet.getHead();
		EncryptionInfoElement encryptionInfo = head.getEncryptionInfo();

		byte[] key = Arrays.copyOfRange(derivedKey, 0, encryptionInfo.getKeySize());
		byte[] iv = Arrays.copyOfRange(derivedKey, encryptionInfo.getKeySize(), derivedKey.length);

		byte[] data = encryptionHelper.decrypt(encPrivateKey, key, iv, encryptionInfo.getSymmetricCipherTypeString(),
				encryptionInfo.getPadding());

		return data;

	}

	protected String encrypt(byte[] toBeEncoded, String encodingType) throws ZkpException {

		// private key
		byte[] encrypted = symEncPrivateKey(toBeEncoded);

		try {
			return MultiBaseUtils.encode(encrypted, MultiBaseType.getByCharacter(encodingType));
		} catch (CryptoException e) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_CRYPTO_ENCRYPT, e);
		}
		// return encodeByte(encrypted, encodingType);
	}

	protected byte[] decrypt(String toBeDecoded) throws ZkpException {

		byte[] decoded = new byte[0];
		try {
			decoded = MultiBaseUtils.decode(toBeDecoded);
		} catch (CryptoException e) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_CRYPTO_DECRYPT, e);
		}
		byte[] decrypted = symDecPrivateKey(decoded);
		return decrypted;
	}


	private ArrayList<ZkpKeyElement> decryptPrivateKey(ArrayList<ZkpKeyElement> keys) throws ZkpException {
		for (ZkpKeyElement element : keys) {
			String pKeyString = element.getPrivateKey();

			byte[] valueByte = null;
			valueByte = decrypt(pKeyString);
			if (valueByte == null) {
				throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_CRYPTO_DECRYPT);
			}

			try {
				element.setPrivateKey(MultiBaseUtils.encode(valueByte, MultiBaseType.base58btc));
			} catch (CryptoException e) {
				throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_INVALID_PRIVATE_KEY, e);
			}
		}
		return keys;
	}

	/**
	 * Remove a ZkpKeyElement by keyId.
	 *
	 * @param keyId
	 * @throws ZkpException
	 */
	public void removeZkpKey(String keyId) throws ZkpException {
		if (!isConnect())
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_DISCONNECT);

		ZkpKeyElement key = getZkpKeyElement(keyId);
		if (key == null)
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_KEYID_NOT_EXIST);

		ZkpWallet data = zkpWalletFile.getData();
		if (data == null) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_FILE_LOAD_FAIL);
		}

		boolean isChanged = false;

		ArrayList<ZkpKeyElement> zkpKeys = data.getZkpKeys();
		if (zkpKeys != null) {
			for (ZkpKeyElement k : zkpKeys) {
				if (k.getKeyId().equals(keyId)) {
					if (zkpKeys.remove(k)) {
						isChanged = true;
						break;
					}
				}
			}
		}

		if (isChanged == true) {
			data.setZkpKeys(zkpKeys);
			zkpWalletFile.write(data);
		}
	}

	/**
	 * Add a ZkpKeyElement.
	 *
	 * @param key
	 * @throws ZkpException
	 */
	public void addZkpKey(ZkpKeyElement key) throws ZkpException {
		if (!isConnect())
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_DISCONNECT);

		if (key == null) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_IWKEY_IS_NULL);
		}

		if (key.getKeyId() == null || key.getKeyId().isEmpty()) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_KEYID_EMPTY_NAME);
		}

		if (!key.getKeyId().matches("^[0-9a-zA-Z.]+$"))
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_INVALID_KEYID_NAME);

		if (key.getType() == null || key.getType().isEmpty()) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_INVALID_ALGORITHM_TYPE);
		}

		// Check if key with same ID already exists
		if (zkpWalletFile.getData() != null) {
			// Check in ZKP keys
			if (zkpWalletFile.getData().getZkpKeys() != null) {
				ArrayList<ZkpKeyElement> zkpKeyEles = zkpWalletFile.getData().getZkpKeys();
				for (ZkpKeyElement zkpKeyEle : zkpKeyEles) {
					if (zkpKeyEle.getKeyId().equals(key.getKeyId())) {
						throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_KEYID_ALREADY_EXIST);
					}
				}
			}
		}

		// Encrypt the private key
		String encodingType = zkpWalletFile.getData().getHead().getEncoding().getKeyEncodingType();
        byte[] privateKeyBytes = key.getPrivateKey().getBytes();
        String encryptedPrivateKey = encrypt(privateKeyBytes, encodingType);
        key.setPrivateKey(encryptedPrivateKey);

        // Encoding the public key and public key metadata
		try {
			byte[] publicKeyBytes = key.getPublicKey().getBytes();
			String encodedPublicKey = MultiBaseUtils.encode(publicKeyBytes, MultiBaseType.getByCharacter(encodingType));
			key.setPublicKey(encodedPublicKey);

			byte[] publicKeyMetadataBytes = key.getPublicKeyMetadata().getBytes();
			String encodedPublicKeyMetadata = MultiBaseUtils.encode(publicKeyMetadataBytes, MultiBaseType.getByCharacter(encodingType));
			key.setPublicKeyMetadata(encodedPublicKeyMetadata);
		} catch (CryptoException e) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_INVALID_PRIVATE_KEY, e);
		}

		ZkpWallet data = zkpWalletFile.getData();
		ArrayList<ZkpKeyElement> zkpKeys = data.getZkpKeys();

		if (zkpKeys == null) {
			zkpKeys = new ArrayList<ZkpKeyElement>();
		}
		zkpKeys.add(key);
		data.setZkpKeys(zkpKeys);

		zkpWalletFile.write(data);
	}

	/**
	 * Get a ZkpKeyElement by keyId.
	 *
	 * @param keyId
	 * @return
	 * @throws ZkpException
	 */
	public ZkpKeyElement getZkpKeyElement(String keyId) throws ZkpException {
		if (!isConnect())
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_DISCONNECT);

		ZkpWallet data = zkpWalletFile.getData();
		ArrayList<ZkpKeyElement> zkpKeys = data.getZkpKeys();
		if (zkpKeys == null)
			return null;

		for (ZkpKeyElement key : zkpKeys) {
			if (key.getKeyId().equals(keyId)) {
				return key;
			}
		}

		return null;
	}

	/**
	 * Get decrypted ZkpKeyElement by keyId.
	 *
	 * @param keyId
	 * @return
	 * @throws ZkpException
	 */
	public ZkpKeyElement getDecryptedAndDecodedZkpKeyElement(String keyId) throws ZkpException {
		ZkpKeyElement key = getZkpKeyElement(keyId);
		if (key == null)
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_KEYID_NOT_EXIST);

		// Create a copy of the key
		ZkpKeyElement decryptedKey = new ZkpKeyElement();
		decryptedKey.setKeyId(key.getKeyId());
		decryptedKey.setType(key.getType());

		// Decrypt the private key
		try {
			byte[] decodedKey = MultiBaseUtils.decode(key.getPrivateKey());
			byte[] decryptedBytes = symDecPrivateKey(decodedKey);
			String decryptedPrivateKey = new String(decryptedBytes);
			decryptedKey.setPrivateKey(decryptedPrivateKey);
		} catch (CryptoException e) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_INVALID_PRIVATE_KEY, e);
		}

		// Decode the public key and metadata
		try {
			byte[] decodedPublicKey = MultiBaseUtils.decode(key.getPublicKey());
			String decodedPublicKeyStr = new String(decodedPublicKey);
			decryptedKey.setPublicKey(decodedPublicKeyStr);

			byte[] decodedPublicKeyMetadata = MultiBaseUtils.decode(key.getPublicKeyMetadata());
			String decodedPublicKeyMetadataStr = new String(decodedPublicKeyMetadata);
			decryptedKey.setPublicKeyMetadata(decodedPublicKeyMetadataStr);
		} catch (CryptoException e) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_INVALID_PRIVATE_KEY, e);
		}

		return decryptedKey;
	}

	/**
	 * Check if a ZkpKeyElement with the given keyId exists.
	 *
	 * @param keyId
	 * @return
	 * @throws ZkpException
	 */
	public boolean isExistZkpKey(String keyId) throws ZkpException {
		if (!isConnect()) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_DISCONNECT);
		}

		if (keyId == null || keyId.isEmpty()) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_INVALID_KEYID_NAME);
		}

		if (getZkpKeyIdList() != null && getZkpKeyIdList().contains(keyId)) {
			return true;
		}

		return false;
	}

	/**
	 * Get a list of all ZkpKeyElement keyIds.
	 *
	 * @return
	 * @throws ZkpException
	 */
	public List<String> getZkpKeyIdList() throws ZkpException {
		if (!isConnect()) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_DISCONNECT);
		}

		ZkpWallet data = zkpWalletFile.getData();
		ArrayList<ZkpKeyElement> zkpKeys = data.getZkpKeys();
		if (zkpKeys == null || zkpKeys.size() == 0) {
			return null;
		}

		List<String> keyIds = new ArrayList<String>();
		for (ZkpKeyElement key : zkpKeys) {
			keyIds.add(key.getKeyId());
		}

		return keyIds;
	}

	/**
	 * Remove all ZkpKeyElements.
	 *
	 * @throws ZkpException
	 */
	public void removeAllZkpKeys() throws ZkpException {
		if (!isConnect()) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_DISCONNECT);
		}

		ZkpWallet data = zkpWalletFile.getData();
		if (data == null) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_FILE_LOAD_FAIL);
		}

		ArrayList<ZkpKeyElement> zkpKeys = data.getZkpKeys();

		if (zkpKeys != null && zkpKeys.size() > 0) {
			data.setZkpKeys(null);
			zkpWalletFile.write(data);
		}
	}

	/**
	 * Helper method to decrypt all ZkpKeyElement private keys.
	 * Used during password change.
	 *
	 * @param zkpKeys
	 * @return
	 * @throws ZkpException
	 */
	private ArrayList<ZkpKeyElement> decryptZkpPrivateKey(ArrayList<ZkpKeyElement> zkpKeys) throws ZkpException {
		for (ZkpKeyElement element : zkpKeys) {
			String pKeyString = element.getPrivateKey();

			byte[] valueByte = null;
			valueByte = decrypt(pKeyString);
			if (valueByte == null) {
				throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_CRYPTO_DECRYPT);
			}

			try {
				element.setPrivateKey(MultiBaseUtils.encode(valueByte, MultiBaseType.base58btc));
			} catch (CryptoException e) {
				throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_INVALID_PRIVATE_KEY, e);
			}
		}
		return zkpKeys;
	}

	@Override
	public byte[] generateZkpKeyProof(String keyId) throws ZkpException {
		CredentialPrimaryKeyPair credentialPrimaryKeyPair = getCredentialPrimaryKeyPair(keyId);
		return new ZkpWalletManagerAdapter().zkpKeyProof(credentialPrimaryKeyPair);
	}

	@Override
	public byte[] generateZkpSignature(String keyId, CredentialRequest credentialRequest, CredentialValues credentialValues) throws ZkpException {
		CredentialPrimaryKeyPair credentialPrimaryKeyPair = getCredentialPrimaryKeyPair(keyId);
		String proverDid = credentialRequest.getProverDid();
		BlindedCredentialSecrets blindMs = credentialRequest.getBlindedMs();
		return new ZkpWalletManagerAdapter().zkpSignature(proverDid, credentialPrimaryKeyPair, credentialValues, blindMs);
	}

	@Override
	public byte[] generateZkpSignatureProof(String keyId, CredentialSignature credentialSignature, BigInteger nonce) throws ZkpException {
		CredentialPrimaryKeyPair credentialPrimaryKeyPair = getCredentialPrimaryKeyPair(keyId);
		return new ZkpWalletManagerAdapter().zkpSignatureProof(credentialSignature, credentialPrimaryKeyPair, nonce);
	}

	public void generateRandomZkpKey(String keyId, List<String> attrNames) throws ZkpException {
		if (!isConnect()) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_DISCONNECT);
		}

		if (isExistZkpKey(keyId)) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_KEYID_ALREADY_EXIST);
		}

		ZkpKeyPairGenerator zkpKeyPairGenerator = new ZkpKeyPairGenerator();

		if (keyId.equals("")) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_KEYID_EMPTY_NAME);
		}

		CredentialPrimaryKeyPair credentialPrimaryKeyPair = null;
		try {
			credentialPrimaryKeyPair = zkpKeyPairGenerator.generateKeyPair(attrNames);
		} catch (ZkpException e) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_CRYPTO_GEN_KEY_FAIL);
		}

		ZkpKeyElement zkpKeyElement = new ZkpKeyElement();
		zkpKeyElement.setKeyId(keyId);
		zkpKeyElement.setType(ZkpKeyType.ANONCRED_ISSUER);

		JsonObject jsonObject = JsonParser.parseString(credentialPrimaryKeyPair.toJson()).getAsJsonObject();
		zkpKeyElement.setPrivateKey(jsonObject.get("privateKey").toString());
		zkpKeyElement.setPublicKey(jsonObject.get("publicKey").toString());
		zkpKeyElement.setPublicKeyMetadata(jsonObject.get("publicKeyMetadata").toString());

		addZkpKey(zkpKeyElement);
	}
}