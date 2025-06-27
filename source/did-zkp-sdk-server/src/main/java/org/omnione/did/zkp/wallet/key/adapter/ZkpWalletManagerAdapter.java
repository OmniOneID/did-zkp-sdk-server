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

package org.omnione.did.zkp.wallet.key.adapter;

import org.omnione.did.zkp.datamodel.credential.CredentialValues;
import org.omnione.did.zkp.crypto.keypair.CredentialPrimaryKeyPair;
import org.omnione.did.zkp.crypto.keypair.CredentialPrimaryPrivateKey;
import org.omnione.did.zkp.crypto.keypair.CredentialPrimaryPublicKey;
import org.omnione.did.zkp.crypto.keypair.PublicKeyMetadata;
import org.omnione.did.zkp.datamodel.util.GsonWrapper;
import org.omnione.did.zkp.datamodel.credential.CredentialSignature;
import org.omnione.did.zkp.datamodel.credential.PrimaryCredentialSignature;
import org.omnione.did.zkp.datamodel.credential.SignatureCorrectnessProof;
import org.omnione.did.zkp.datamodel.credentialrequest.BlindedCredentialSecrets;
import org.omnione.did.zkp.datamodel.credentialoffer.KeyCorrectnessProof;
import org.omnione.did.zkp.exception.ZkpException;
import org.omnione.did.zkp.wallet.crypto.sign.SignatureHelper;
import org.omnione.did.zkp.wallet.key.data.ZkpKeyElement;
import org.omnione.did.zkp.wallet.key.impl.ZkpWalletManagerImpl;

import java.math.BigInteger;

public class ZkpWalletManagerAdapter extends ZkpWalletManagerImpl {
	private SignatureHelper signatureHelper = new SignatureHelper();
	
	public ZkpWalletManagerAdapter() {}
	
	public ZkpWalletManagerAdapter(String walletfile_pathWithName) throws ZkpException {
		super(walletfile_pathWithName);
	}

	public byte[] zkpKeyProof(CredentialPrimaryKeyPair credentialPrimaryKeyPair) throws ZkpException {

		KeyCorrectnessProof keyCorrectnessProof = signatureHelper.keyProof(credentialPrimaryKeyPair);
		String zkpKeyCorrectnessProof = GsonWrapper.getGson().toJson(keyCorrectnessProof);
		return zkpKeyCorrectnessProof.getBytes();
	}

	public byte[] zkpSignature(String proverDid, CredentialPrimaryKeyPair credentialKeyPair, CredentialValues
			credentialValues, BlindedCredentialSecrets blindMs) throws ZkpException {

		PrimaryCredentialSignature signature = signatureHelper.sign(proverDid, credentialKeyPair, credentialValues, blindMs);
		String zkpSignature = GsonWrapper.getGson().toJson(signature);
		return zkpSignature.getBytes();
	}

	public byte[] zkpSignatureProof(CredentialSignature credentialSignature, CredentialPrimaryKeyPair credentialKeyPair, BigInteger nonce)
			throws ZkpException {
		SignatureCorrectnessProof signatureProof = signatureHelper.addProof(credentialSignature, credentialKeyPair, nonce);
		String zkpSignatureProof = GsonWrapper.getGson().toJson(signatureProof);
		return zkpSignatureProof.getBytes();
	}

	public CredentialPrimaryKeyPair getCredentialPrimaryKeyPair(String keyId) throws ZkpException {
		if (!isConnect()) {
			return null;
		}

		ZkpKeyElement zkpKeyElement = getDecryptedAndDecodedZkpKeyElement(keyId);

		if (zkpKeyElement == null)
			return null;
		CredentialPrimaryKeyPair credentialPrimaryKeyPair = new CredentialPrimaryKeyPair();
		credentialPrimaryKeyPair.setPrivateKey(GsonWrapper.getGson().fromJson(zkpKeyElement.getPrivateKey(), CredentialPrimaryPrivateKey.class));
		credentialPrimaryKeyPair.setPublicKey(GsonWrapper.getGson().fromJson(zkpKeyElement.getPublicKey(), CredentialPrimaryPublicKey.class));
		credentialPrimaryKeyPair.setPublicKeyMetadata(GsonWrapper.getGson().fromJson(zkpKeyElement.getPublicKeyMetadata(), PublicKeyMetadata.class));

		return credentialPrimaryKeyPair;
	}

	public CredentialPrimaryPublicKey getCredentialPrimaryPublicKey(String keyId) throws ZkpException {
		if (!isConnect()) {
			return null;
		}
		ZkpKeyElement zkpKeyElement = getDecryptedAndDecodedZkpKeyElement(keyId);
		if (zkpKeyElement == null)
			return null;
		return GsonWrapper.getGson().fromJson(zkpKeyElement.getPublicKey(), CredentialPrimaryPublicKey.class);
	}
}
