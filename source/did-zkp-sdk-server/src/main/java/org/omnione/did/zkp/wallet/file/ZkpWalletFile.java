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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.omnione.did.zkp.exception.ZkpErrorCode;
import org.omnione.did.zkp.exception.ZkpException;
import org.omnione.did.zkp.wallet.key.data.ZkpWallet;

public class ZkpWalletFile {

	public static boolean ONETIME_LOAD = false;
	private String walletFilePath;

//	private static CryptoWallet data;
	private ZkpWallet zkpWalletData;
	private final static String WALLET_FILE_EXTENSION_NAME = ".wallet";

	/**
	 * WalletFile constructor. Initialize by adding the wallet file extension to the specified path.
	 * @param pathWithName wallet file paths and names
	 */
	public ZkpWalletFile(String pathWithName) {

		String ext = null;
		int i = pathWithName.lastIndexOf('.');

		if (i > 0 && i < pathWithName.length() - 1) {
			ext = pathWithName.substring(i).toLowerCase();
		}

		if (ext == null || !ext.contentEquals(WALLET_FILE_EXTENSION_NAME)) {
			pathWithName = pathWithName + WALLET_FILE_EXTENSION_NAME;
		}

		walletFilePath = pathWithName;

	}

	/**
	 * Check that the wallet file exists.
	 *
	 * @return File Existence
	 */
	public boolean isExist() {
		File f = new File(walletFilePath);
		return f.exists();
	}

	 /**
	  * Clears the wallet data stored in memory.
     */
    public void clearData() {
    	zkpWalletData = null;
    }

	/**
	 *  Save the wallet data to a file and reload it.
	 *
	 * @param zkpWalletData Wallet data to store
	 * @throws ZkpException Fired when a file write fails.
	 */
	public void write(ZkpWallet zkpWalletData) throws ZkpException {
		String data_str = zkpWalletData.toJson();

		try {
			writeToFile(data_str);
		} catch (IOException e) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_FILE_WRITE_FAIL, e);
		}
		try {
			loadFromFile();
		} catch (IOException e) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_FILE_LOAD_FAIL, e);
		}
	}

	/**
	 * Returns the currently stored wallet data.
	 *
	 * @return Saved CryptoWallet data
	 * @throws ZkpException Fired when a file fails to load.
	 */
	public ZkpWallet getData() throws ZkpException {
		try {
			if (ONETIME_LOAD && zkpWalletData == null) {
				loadFromFile();
			} else if (!ONETIME_LOAD) {
				loadFromFile();
			}
		} catch (IOException e) {
			throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_WALLET_FILE_LOAD_FAIL, e);
		}

		return zkpWalletData;
	}

	/**
	 * Returns the path to the wallet file.
	 *
	 * @return wallet file path
	 */
	public String getFilePath() {
		return walletFilePath;
	}

	/**
	 * (private method) Save wallet data as a wallet file.
	 * 
	 * @param data Data string to store
	 * @throws IOException Fired when a file write fails.
	 */
	private void writeToFile(String data) throws IOException {
		FileWriter fw = null;

		try {
			File file = new File(walletFilePath);
			fw = new FileWriter(file);
			fw.write(data);

		} finally {
			fw.close();
		}
	}

	/**
	 * (private method) Load the wallet file and save it as wallet data.
	 * 
	 * @throws IOException Fired when a file read fails.
	 */
	private void loadFromFile() throws IOException {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			File file = new File(walletFilePath);
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			StringBuilder buf = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				buf.append(line);
			}
			zkpWalletData = new ZkpWallet();
			zkpWalletData.fromJson(buf.toString());
		} finally {

			if (br != null)
				br.close();
			if (fr != null)
				fr.close();

		}
	}

}
