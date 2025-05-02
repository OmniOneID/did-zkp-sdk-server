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

package org.omnione.did.zkp.wallet.file;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.omnione.did.crypto.exception.CryptoException;
import org.omnione.did.crypto.util.MultiBaseUtils;
import org.omnione.did.zkp.exception.ZkpErrorCode;
import org.omnione.did.zkp.exception.ZkpException;
import org.omnione.did.zkp.wallet.crypto.encryption.EncryptionHelper;
import org.omnione.did.zkp.wallet.file.ZkpWalletFileHandler.ZkpWalletFileHelper;
import org.omnione.did.zkp.wallet.key.data.EncryptionInfoElement;
import org.omnione.did.zkp.wallet.key.data.HeadElement;
import org.omnione.did.zkp.wallet.key.data.SecureKeyInfoElement;
import org.omnione.did.zkp.wallet.util.Constants;

/**
 * The CoreWalletFileHelper class implements the WalletFileHelper interface.
 * It also provides functionality for generating and authenticating the SecretPhrase of a wallet file.
 */
public class ZkpCoreWalletFileHelper implements ZkpWalletFileHelper {

	private ZkpWalletFile zkpWalletFile;
	private boolean isSecretPhrase;
	private EncryptionHelper encryptionHelper = new EncryptionHelper();

	/**
	 * CoreWalletFileHelper constructor.
	 *
	 * @param zkpWalletFile wallet file object
	 * @throws ZkpException Occurs when wallet data cannot be loaded
	 */
	public ZkpCoreWalletFileHelper(ZkpWalletFile zkpWalletFile) throws ZkpException {
		this.zkpWalletFile = zkpWalletFile;
		this.isSecretPhrase = this.zkpWalletFile.getData().getHead().getSecureKeyInfo().getSecretPhrase() != null;

	}

    /**
     * Returns whether the SecretPhrase exists.
     * @return SecretPhrase existence
     */
	@Override
	public boolean isExistSecretPhrase() {

		return this.isSecretPhrase;
	}

	 /**
     * Create a SecretPhrase with password.
     *
     * @param securePassword password
     * @return Byte array of the generated SecretPhrase
     * @throws ZkpException If an error occurs during SecretPhrase generation
     */
	@Override
	public byte[] generateSecretPhrase(char[] securePassword) throws ZkpException {
		HeadElement head = this.zkpWalletFile.getData().getHead();
		SecureKeyInfoElement secureKeyInfo = head.getSecureKeyInfo();
		EncryptionInfoElement encryptionInfo = head.getEncryptionInfo();

		byte[] data = null;
		try {

			byte[] derivedKey = encryptionHelper.pbkdf2(securePassword, MultiBaseUtils.decode(secureKeyInfo.getSalt()),
					secureKeyInfo.getIterations(), encryptionInfo.getKeySize());

			byte[] key = Arrays.copyOfRange(derivedKey, 0, encryptionInfo.getKeySize());
			byte[] iv = Arrays.copyOfRange(derivedKey, encryptionInfo.getKeySize(), derivedKey.length);

			data = encryptionHelper.encrypt(Constants.initialPhrase.getBytes(StandardCharsets.UTF_8), key, iv,
					encryptionInfo.getSymmetricCipherTypeString(), encryptionInfo.getPadding());

			return data;
		} catch (CryptoException e) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_DEFAULTKEYSTORE_KEYGEN_FAIL, e);
		}

	}


    /**
     *  Use securePassword to authenticate the SecretPhrase.
     *
     * @param securePassword password
     * @return derivedKey Byte array of the generated SecretPhrase
     * @throws ZkpException Fires when securePassword authentication fails
     */
	@Override
	public byte[] authenticate(char[] securePassword) throws ZkpException {

		byte[] derivedKey = null;
		HeadElement head = this.zkpWalletFile.getData().getHead();
		SecureKeyInfoElement secureKeyInfo = head.getSecureKeyInfo();
		EncryptionInfoElement encryptionInfo = head.getEncryptionInfo();
		
		byte[] data = null;
		try {

			derivedKey = encryptionHelper.pbkdf2(securePassword, MultiBaseUtils.decode(secureKeyInfo.getSalt()),
					secureKeyInfo.getIterations(), encryptionInfo.getKeySize());

			byte[] key = Arrays.copyOfRange(derivedKey, 0, encryptionInfo.getKeySize());
			byte[] iv = Arrays.copyOfRange(derivedKey, encryptionInfo.getKeySize(), derivedKey.length);

			data = encryptionHelper.decrypt(MultiBaseUtils.decode(secureKeyInfo.getSecretPhrase()), key, iv,
					encryptionInfo.getSymmetricCipherTypeString(), encryptionInfo.getPadding());

			if (!Arrays.equals(data, Constants.initialPhrase.getBytes(StandardCharsets.UTF_8))) {
				throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_DEFAULTKEYSTORE_AUTHENTICATE_FAIL);
			}

			Arrays.fill(data, (byte) 0x00);

			return derivedKey;

		} catch (CryptoException e) {
            throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_DEFAULTKEYSTORE_AUTHENTICATE_FAIL.getCode(),
                    e.getMessage());
        }

	}
}
