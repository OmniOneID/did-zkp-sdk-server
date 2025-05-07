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

package org.omnione.did.zkp.wallet.key;

import org.omnione.did.zkp.exception.ZkpException;
import org.omnione.did.zkp.wallet.file.ZkpWalletFile;
import org.omnione.did.zkp.wallet.key.manager.FileZkpWalletManager;

public class ZkpWalletManagerFactory {

	public enum ZkpWalletManagerType {
		FILE, ;
	}

	public static void setKeyOneTimeLoad(boolean load) {
		ZkpWalletFile.ONETIME_LOAD = load;
	}

	/**
	 * Retrieves an instance of the ZkpWalletManagerInterface based on the specified wallet manager type.
	 *
	 * @param type the type of ZKP wallet manager to retrieve
	 * @return an instance of ZkpWalletManagerInterface corresponding to the given type
	 * @throws ZkpException if any error occurs during the retrieval process
	 */
	public static ZkpWalletManagerInterface getZkpWalletManager(ZkpWalletManagerType type) throws ZkpException {
		ZkpWalletManagerInterface manager = null;
			
		switch (type) {
		case FILE:
			manager = new FileZkpWalletManager();
			break;
		default:
			break;
		}

		return manager;
	}
}
