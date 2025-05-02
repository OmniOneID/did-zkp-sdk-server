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

package org.omnione.did.zkp.wallet.key.data;

import java.util.HashMap;
import java.util.Map;
import org.omnione.did.zkp.wallet.util.json.JsonConverterUtils;

public class ZkpKeyElement {
	public enum ZkpKeyType {
		ANONCRED_ISSUER {
			public String toString() {
				return "AnonCredIssuer";
			}
		}
	}

	private String keyId;
	private String type;
	private String privateKey;
	private String publicKey;
	private String publicKeyMetadata;

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setType(ZkpKeyType type) { this.type = type.toString(); }

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPublicKey() { return publicKey; }

	public void setPublicKey(String publicKey) { this.publicKey = publicKey; }

	public String getPublicKeyMetadata() { return publicKeyMetadata; }

	public void setPublicKeyMetadata(String publicKeyMetadata) { this.publicKeyMetadata = publicKeyMetadata; }

	public String toJson() {
		JsonConverterUtils gson = new JsonConverterUtils();
		Map<String, Object> jsonMap = new HashMap<>();

		jsonMap.put("keyId", keyId);
		jsonMap.put("type", type);
		jsonMap.put("privateKey", privateKey);
		jsonMap.put("publicKey", publicKey);
		jsonMap.put("publicKeyMetadata", publicKeyMetadata);

		return gson.toJson(jsonMap);
	}

	@SuppressWarnings("unchecked")
	public void fromJson(String val) {
		JsonConverterUtils gson = new JsonConverterUtils();
		Map<String, Object> jsonMap = gson.fromJson(val, Map.class);

		if (jsonMap.containsKey("keyId")) {
			this.keyId = (String) jsonMap.get("keyId");
		}

		if (jsonMap.containsKey("type")) {
			this.type = (String) jsonMap.get("type");
		}

		if (jsonMap.containsKey("privateKey")) {
			this.privateKey = (String) jsonMap.get("privateKey");
		}

		if (jsonMap.containsKey("publicKey")) {
			this.publicKey = (String) jsonMap.get("publicKey");
		}

		if (jsonMap.containsKey("publicKeyMetadata")) {
			this.publicKeyMetadata = (String) jsonMap.get("publicKeyMetadata");
		}

		jsonMap.remove("keyId");
		jsonMap.remove("type");
		jsonMap.remove("privateKey");
		jsonMap.remove("publicKey");
		jsonMap.remove("publicKeyMetadata");
	}

}