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

package org.omnione.did.zkp.wallet.crypto.sign;

import org.omnione.did.zkp.datamodel.credential.CredentialValues;
import org.omnione.did.zkp.datamodel.credential.CredentialSignature;
import org.omnione.did.zkp.datamodel.credential.PrimaryCredentialSignature;
import org.omnione.did.zkp.datamodel.credential.SignatureCorrectnessProof;
import org.omnione.did.zkp.datamodel.credentialrequest.BlindedCredentialSecrets;
import org.omnione.did.zkp.crypto.keypair.CredentialPrimaryKeyPair;
import org.omnione.did.zkp.datamodel.credentialoffer.KeyCorrectnessProof;

import java.math.BigInteger;
import org.omnione.did.zkp.exception.ZkpException;

public abstract class AbstractSignatureHelper {
  abstract public KeyCorrectnessProof keyProof(CredentialPrimaryKeyPair credentialPrimaryKeyPair)
      throws ZkpException;

  abstract public PrimaryCredentialSignature sign(String proverDid, CredentialPrimaryKeyPair credentialKeyPair, CredentialValues credentialValues, BlindedCredentialSecrets blindMs)
      throws ZkpException;

  abstract public SignatureCorrectnessProof addProof(CredentialSignature credentialSignature, CredentialPrimaryKeyPair credentialKeyPair, BigInteger nonce)
      throws ZkpException;

}
