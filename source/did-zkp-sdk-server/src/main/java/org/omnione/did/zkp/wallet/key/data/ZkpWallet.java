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

import java.util.ArrayList;
import org.omnione.did.zkp.wallet.util.json.JsonConverterUtils;

public class ZkpWallet {
	private HeadElement head;

	private ArrayList<ZkpKeyElement> zkpKeys;

	public void setHead(HeadElement headElement) {
		this.head = headElement;
	}

	public HeadElement getHead() {
		return head;
	}

	public ArrayList<ZkpKeyElement> getZkpKeys() {
		return zkpKeys;
	}

	public void setZkpKeys(ArrayList<ZkpKeyElement> zkpKeys) {
		this.zkpKeys = zkpKeys;
	}

	public String toJson() {
		JsonConverterUtils gson = new JsonConverterUtils();
		return gson.toJson(this);
	}

	public void fromJson(String val) {
		JsonConverterUtils gson = new JsonConverterUtils();
		ZkpWallet data = gson.fromJson(val, ZkpWallet.class);
		head = data.getHead();
		zkpKeys = data.getZkpKeys();

	}

}
