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

package org.omnione.did.zkp.wallet.enums;

import java.util.EnumSet;

public enum ZkpWalletEncryptType {
	AES_256_CBC_PKCS5Padding("AES-256-CBC-PKCS5Padding");
	
	
	String aesAlgorithm; 
	Integer keySize; 
	String mode; 
	String padding; 

	private String rawValue;

	ZkpWalletEncryptType(String rawValue) {
		this.rawValue = rawValue;	
	}
	

	public String getRawValue() {
		return rawValue;
	}
	
	public static ZkpWalletEncryptType fromString(String text) {
		for (ZkpWalletEncryptType b : ZkpWalletEncryptType.values()) {
			if (b.rawValue.equalsIgnoreCase(text)) {
				return b;
			}
		}
		return null;
	}

	public static EnumSet<ZkpWalletEncryptType> all() {
		return EnumSet.allOf(ZkpWalletEncryptType.class);
	}

}
