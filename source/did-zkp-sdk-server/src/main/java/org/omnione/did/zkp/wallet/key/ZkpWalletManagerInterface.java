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

import org.omnione.did.zkp.datamodel.credential.CredentialValues;
import org.omnione.did.zkp.crypto.keypair.CredentialPrimaryKeyPair;
import org.omnione.did.zkp.crypto.keypair.CredentialPrimaryPublicKey;
import org.omnione.did.zkp.datamodel.credential.CredentialSignature;
import org.omnione.did.zkp.datamodel.credentialrequest.CredentialRequest;
import org.omnione.did.zkp.exception.ZkpException;
import org.omnione.did.zkp.wallet.enums.ZkpWalletEncryptType;
import org.omnione.did.zkp.wallet.key.data.ZkpKeyElement;

import java.math.BigInteger;
import java.util.List;

public interface ZkpWalletManagerInterface {

	/**
	 * Creates a new wallet file with the specified parameters.
	 *
	 * @param walletFilePath       The path where the wallet file will be created.
	 * @param securePassword       The secure password used for encryption.
	 * @param zkpWalletEncryptType The encryption type to be used for the wallet.
	 * @throws ZkpException If an error occurs during wallet creation.
	 */
	public void create(String walletFilePath, char[] securePassword,
			ZkpWalletEncryptType zkpWalletEncryptType) throws ZkpException;

	/**
	 * Connects to an existing wallet file with the specified parameters.
	 *
	 * @param walletFilePath The path to the existing wallet file.
	 * @param securePassword The secure password used to connect to the wallet.
	 * @throws ZkpException If an error occurs during wallet connection.
	 */
	public void connect(String walletFilePath, char[] securePassword) throws ZkpException;

	/**
	 * Changes the secure password of the connected wallet.
	 *
	 * @param currentSecurePassword The current secure password.
	 * @param newSecurePassword     The new secure password.
	 * @throws ZkpException If an error occurs during password change.
	 */
	public void changePassword(char[] currentSecurePassword, char[] newSecurePassword)
			throws ZkpException, ZkpException;

	/**
	 * Disconnects from the connected wallet.
	 *
	 * @return True if disconnected successfully, false otherwise.
	 */
	public boolean disConnect();

	/**
	 * Checks if the wallet is currently connected.
	 *
	 * @return True if connected, false otherwise.
	 */
	public boolean isConnect();

	/**
	 * Add a ZkpKeyElement to the wallet.
	 *
	 * @param key The ZkpKeyElement to add.
	 * @throws ZkpException If an error occurs during key addition.
	 */
	public void addZkpKey(ZkpKeyElement key) throws ZkpException;

	/**
	 * Remove a ZkpKeyElement by keyId.
	 *
	 * @param keyId The ID of the key to remove.
	 * @throws ZkpException If an error occurs during key removal.
	 */
	public void removeZkpKey(String keyId) throws ZkpException;

	/**
	 * Get a ZkpKeyElement by keyId.
	 *
	 * @param keyId The ID of the key.
	 * @return The ZkpKeyElement.
	 * @throws ZkpException If an error occurs during key retrieval.
	 */
	public ZkpKeyElement getZkpKeyElement(String keyId) throws ZkpException;

	/**
	 * Get a decrypted ZkpKeyElement by keyId.
	 *
	 * @param keyId The ID of the key.
	 * @return The decrypted ZkpKeyElement.
	 * @throws ZkpException If an error occurs during key retrieval or decryption.
	 */
	public ZkpKeyElement getDecryptedAndDecodedZkpKeyElement(String keyId) throws ZkpException;

	/**
	 * Check if a ZkpKeyElement with the specified keyId exists in the wallet.
	 *
	 * @param keyId The ID of the key.
	 * @return True if the key exists, false otherwise.
	 * @throws ZkpException If an error occurs during key existence check.
	 */
	public boolean isExistZkpKey(String keyId) throws ZkpException;

	/**
	 * Get a list of all ZkpKeyElement keyIds present in the wallet.
	 *
	 * @return The list of ZkpKeyElement keyIds.
	 * @throws ZkpException If an error occurs during key ID retrieval.
	 */
	public List<String> getZkpKeyIdList() throws ZkpException;

	/**
	 * Remove all ZkpKeyElements from the wallet.
	 *
	 * @throws ZkpException If an error occurs during key removal.
	 */
	public void removeAllZkpKeys() throws ZkpException;

	/**
	 * Generates a random ZKP key associated with the specified key ID and attribute names.
	 *
	 * @param keyId     the unique identifier for the key
	 * @param attrNames the list of attribute names associated with the key
	 * @throws ZkpException if any error occurs during the key generation process
	 */
	public void generateRandomZkpKey(String keyId, List<String> attrNames) throws ZkpException;

	/**
	 * Generates a ZKP key correctness proof for the key associated with the given key ID.
	 *
	 * @param keyId the unique identifier for the key
	 * @return a byte array containing the key correctness proof
	 * @throws ZkpException if any error occurs during the proof generation process
	 */
	public byte[] generateZkpKeyProof(String keyId) throws ZkpException;

	/**
	 * Generates a ZKP signature using the specified key ID, credential request, and credential
	 * values.
	 *
	 * @param keyId             the unique identifier for the key
	 * @param credentialRequest the credential request containing necessary cryptographic information
	 * @param credentialValues  the credential values to be signed
	 * @return a byte array containing the generated ZKP signature
	 * @throws ZkpException if any error occurs during the signature generation process
	 */
	public byte[] generateZkpSignature(String keyId, CredentialRequest credentialRequest,
			CredentialValues credentialValues) throws ZkpException;

	/**
	 * Generates a ZKP signature correctness proof using the specified key ID, credential signature,
	 * and nonce.
	 *
	 * @param keyId               the unique identifier for the key
	 * @param credentialSignature the credential signature to generate a proof for
	 * @param nonce               a randomly generated nonce for security
	 * @return a byte array containing the signature correctness proof
	 * @throws ZkpException if any error occurs during the proof generation process
	 */
	public byte[] generateZkpSignatureProof(String keyId, CredentialSignature credentialSignature,
			BigInteger nonce) throws ZkpException;

	/**
	 * Retrieves the credential primary key pair (public and private keys) associated with the
	 * specified key ID.
	 *
	 * @param keyId the unique identifier for the key
	 * @return the CredentialPrimaryKeyPair associated with the given key ID
	 * @throws ZkpException if any error occurs during the retrieval process
	 */
	public CredentialPrimaryKeyPair getCredentialPrimaryKeyPair(String keyId) throws ZkpException;

	/**
	 * Retrieves the credential primary public key associated with the specified key ID.
	 *
	 * @param keyId the unique identifier for the key
	 * @return the CredentialPrimaryPublicKey associated with the given key ID
	 * @throws ZkpException if any error occurs during the retrieval process
	 */
	public CredentialPrimaryPublicKey getCredentialPrimaryPublicKey(String keyId) throws ZkpException;
}
