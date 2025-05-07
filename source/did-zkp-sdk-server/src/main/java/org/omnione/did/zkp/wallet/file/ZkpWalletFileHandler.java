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

import org.omnione.did.zkp.exception.ZkpException;

public class ZkpWalletFileHandler {
    /**
     * WalletFileHelper interface
     */
    public static interface ZkpWalletFileHelper {
    	 /**
         * Check that the wallet file exists.
         *
         * @return SecretPhrase existence
         */
    	boolean isExistSecretPhrase();
   	 
    	byte[] generateSecretPhrase(char [] securePassword) throws ZkpException;
    	 /**
         *  Use securePassword to authenticate the SecretPhrase.
         *
         * @param securePassword password
         * @return derivedKey Byte array of the generated SecretPhrase
         * @throws ZkpException Fires when securePassword authentication fails
         */
    	byte[] authenticate(char [] securePassword) throws ZkpException;
    
    	
    }
    
}
