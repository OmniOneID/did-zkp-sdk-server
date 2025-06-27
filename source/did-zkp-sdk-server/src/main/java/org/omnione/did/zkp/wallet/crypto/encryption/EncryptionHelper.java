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

package org.omnione.did.zkp.wallet.crypto.encryption;

import org.omnione.did.crypto.engines.CipherInfo;
import org.omnione.did.crypto.exception.CryptoException;
import org.omnione.did.crypto.util.CryptoUtils;
import org.omnione.did.zkp.exception.ZkpErrorCode;
import org.omnione.did.zkp.exception.ZkpException;

public class EncryptionHelper extends AbstractEncryptionHelper {

	public byte[] encrypt(byte[] source, byte[] key, byte[] iv, String cipherSpec, String padding)
			throws ZkpException {
		byte[] ecryptData = null;
		try {
			CipherInfo cipherInfo = new CipherInfo(getSymmetricCipherType(cipherSpec), getSymmetricPaddingType(padding));
			ecryptData = CryptoUtils.encrypt(source, cipherInfo, key, iv);
		} catch (CryptoException e) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_CRYPTO_ENCRYPT, e);
		}
		return ecryptData;
	}

	public byte[] decrypt(byte[] cipherText, byte[] key, byte[] iv, String cipherSpec, String padding)
			throws ZkpException {
		byte[] decryptedData = null;
		try {
			CipherInfo cipherInfo = new CipherInfo(getSymmetricCipherType(cipherSpec), getSymmetricPaddingType(padding));
			decryptedData = CryptoUtils.decrypt(cipherText, cipherInfo, key, iv);
		} catch (CryptoException e) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_CRYPTO_DECRYPT, e);
		}
		return decryptedData;
	}
	
	public byte[] pbkdf2(char[] source, byte[] salt, int iterations, int keySize) throws ZkpException {
		try {
			return CryptoUtils.pbkdf2(source, salt, iterations, (keySize + 16) * 8);
		} catch (CryptoException e) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_CRYPTO_GEN_KEY_FAIL, e);
		}
	}

}
