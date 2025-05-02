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

import org.omnione.did.crypto.enums.SymmetricCipherType;
import org.omnione.did.crypto.enums.SymmetricPaddingType;
import org.omnione.did.zkp.exception.ZkpException;

public abstract class AbstractEncryptionHelper {

	abstract public byte[] encrypt(byte[] source, byte[] key, byte[] iv, String cipherSpec, String padding) throws ZkpException;
	
	abstract public byte[] decrypt(byte[] cipherText, byte[] key, byte[] iv, String cipherSpec, String padding) throws ZkpException;
	
	protected SymmetricCipherType getSymmetricCipherType(String cipherSpec) {
		SymmetricCipherType symCipherType = SymmetricCipherType.fromString(cipherSpec);
		return symCipherType;
	}

	protected SymmetricPaddingType getSymmetricPaddingType(String padding) {
		SymmetricPaddingType symPaddingType = SymmetricPaddingType.fromString(padding);
		return symPaddingType;
	}

}
