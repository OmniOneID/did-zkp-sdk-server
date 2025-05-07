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

package org.omnione.did.zkp.crypto.util;

import org.omnione.did.crypto.enums.DigestType;
import org.omnione.did.crypto.exception.CryptoException;
import org.omnione.did.crypto.util.DigestUtils;
import org.omnione.did.zkp.datamodel.credential.CredentialValues;
import org.omnione.did.zkp.exception.ZkpErrorCode;
import org.omnione.did.zkp.exception.ZkpException;
import org.omnione.did.zkp.core.manager.ZkpCoreConstants;
import org.omnione.did.zkp.crypto.keypair.CredentialPrimaryKeyPair;
import org.omnione.did.zkp.crypto.keypair.CredentialPrimaryPrivateKey;
import org.omnione.did.zkp.crypto.keypair.CredentialPrimaryPublicKey;
import org.omnione.did.zkp.crypto.keypair.PublicKeyMetadata;
import org.omnione.did.zkp.datamodel.credential.AttributeValue;
import org.omnione.did.zkp.datamodel.credential.CredentialSignature;
import org.omnione.did.zkp.datamodel.credential.PrimaryCredentialSignature;
import org.omnione.did.zkp.datamodel.credential.SignatureCorrectnessProof;
import org.omnione.did.zkp.datamodel.credentialrequest.BlindedCredentialSecrets;
import org.omnione.did.zkp.datamodel.credentialrequest.BlindedCredentialSecretsCorrectnessProof;
import org.omnione.did.zkp.core.util.AMCLUtils;
import org.omnione.did.zkp.core.util.bulider.ChallengeBuilder;
import org.omnione.did.zkp.datamodel.credentialoffer.KeyCorrectnessProof;

import java.io.IOException;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;

public class SignatureUtils {
    public static PrimaryCredentialSignature generateSignature(String proverId,
                                                 CredentialPrimaryKeyPair credentialKeyPair,
                                                 CredentialValues credentialValues,
                                                 BlindedCredentialSecrets blindedSecret) throws ZkpException {
        PrimaryCredentialSignature pCredSignature = generateCredential(proverId, credentialKeyPair, credentialValues, blindedSecret);
        return  pCredSignature;
    }

    public static SignatureCorrectnessProof generateSignatureProof(CredentialSignature credentialSignature,
                                                                   CredentialPrimaryKeyPair credentialKeyPair,
                                                                   BigInteger nonce) throws ZkpException {

        return generateSignatureCorrectnessProof(credentialSignature,
                credentialKeyPair,
                nonce);
    }
    public static PrimaryCredentialSignature generateCredential(String proverId,
                                                         CredentialPrimaryKeyPair credentialKeyPair,
                                                         CredentialValues credentialValues,
                                                         BlindedCredentialSecrets blindedSecret) throws ZkpException {

        CredentialPrimaryPublicKey primaryPublicKey = credentialKeyPair.getPublicKey();
        CredentialPrimaryPrivateKey primaryPrivateKey = credentialKeyPair.getPrivateKey();

        final BigInteger u = blindedSecret.getU();
        final BigInteger s = primaryPublicKey.getS();
        final BigInteger z = primaryPublicKey.getZ();
        final BigInteger n = primaryPublicKey.getN();
        final Map<String, BigInteger> r = primaryPublicKey.getR();

        BigIntegerUtil generator = new BigIntegerUtil();
        BigInteger v_prime_prime = generator.createRandomBigInteger(ZkpCoreConstants.LARGE_VPRIME_VPRIME - 1).setBit(ZkpCoreConstants.LARGE_VPRIME_VPRIME - 1);
        BigInteger e = generator.createRandomPrime(ZkpCoreConstants.LARGE_E_START_VALUE, ZkpCoreConstants.LARGE_E_END_RANGE, ZkpCoreConstants.LARGE_E_MAX_BITS);
        if(e == null) {
            throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_ISSUER_GENERATE_PRIMARY_CREDENTIAL_FAIL);
        }
        BigInteger US_vPrime_Prime = u.multiply(s.modPow(v_prime_prime, n)).mod(n);

        int revIdx = 0;
        BigInteger credentialContext = getCredentialContext(proverId, String.valueOf(revIdx));

        US_vPrime_Prime = US_vPrime_Prime.multiply(primaryPublicKey.getRctxt().modPow(credentialContext, n)).mod(n);

        for (String key : credentialValues.getValues().keySet()) {
            AttributeValue attributeValue = credentialValues.getValues().get(key);
            if (r.get(key) == null || attributeValue == null) {
                throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_ISSUER_GENERATE_PRIMARY_CREDENTIAL_FAIL, "{"+key+"} attribute is not exist in primaryPublicKey r");
            }

            US_vPrime_Prime = US_vPrime_Prime.multiply(r.get(key).modPow(attributeValue.getEncoded(), n)).mod(n);
        }

        BigInteger q = z.multiply(US_vPrime_Prime.modInverse(n)).mod(n);

        BigInteger e_inverse = e.modInverse(primaryPrivateKey.getP().multiply(primaryPrivateKey.getQ()));

        BigInteger a = q.modPow(e_inverse, n);

        PrimaryCredentialSignature pCredSign = new PrimaryCredentialSignature(a, e, v_prime_prime);
        pCredSign.setQ(q);
        pCredSign.setM2(credentialContext);

        return pCredSign;
    }
    public static SignatureCorrectnessProof generateSignatureCorrectnessProof(CredentialSignature credSign, CredentialPrimaryKeyPair credentialKeyPair, BigInteger nonce) throws ZkpException {

        CredentialPrimaryPrivateKey privateKey = credentialKeyPair.getPrivateKey();
        CredentialPrimaryPublicKey publicKey = credentialKeyPair.getPublicKey();

        BigIntegerUtil generator = new BigIntegerUtil();

        final BigInteger a = credSign.getPrimaryCredential().getA();
        final BigInteger e = credSign.getPrimaryCredential().getE();
        final BigInteger q = credSign.getPrimaryCredential().getQ();

        final BigInteger n = publicKey.getN();
        final BigInteger pq = privateKey.getP().multiply(privateKey.getQ());

        BigInteger r = generator.createRandom(pq);
        BigInteger a_tidle = q.modPow(r, n);

        BigInteger c_prime = new ChallengeBuilder()
                .add(q)
                .add(a)
                .add(a_tidle)
                .add(nonce)
                .buildWithHashing();

        BigInteger se = r.subtract(c_prime.multiply(e.modInverse(pq).mod(pq))).mod(pq);

        return new SignatureCorrectnessProof(c_prime, se);
    }

    private static BigInteger getCredentialContext(String proverId, String idIndex) throws ZkpException {

        try {
            byte[] attrProverId = DigestUtils.getDigest(proverId.getBytes(), DigestType.SHA256);
            byte[] attrRevIdx = DigestUtils.getDigest(idIndex.getBytes(), DigestType.SHA256);

            attrProverId = AMCLUtils.reverse(attrProverId);
            attrRevIdx = AMCLUtils.reverse(attrRevIdx);
            if(attrProverId == null) {
                throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_ISSUER_GENERATE_CREDENTIAL_CONTEXT_FAIL);
            }
            if(attrRevIdx == null) {
                throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_ISSUER_GENERATE_CREDENTIAL_CONTEXT_FAIL);
            }
            byte[] result = new byte[attrProverId.length + attrRevIdx.length];
            System.arraycopy(attrProverId, 0, result, 0, attrProverId.length);
            System.arraycopy(attrRevIdx, 0, result, attrProverId.length, attrRevIdx.length);
            return BigIntegerUtil.getHash(result);
        } catch (CryptoException e) {
            throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_ISSUER_GENERATE_CREDENTIAL_CONTEXT_FAIL);
        }
    }

    public boolean verifyCredentialRequest(CredentialPrimaryPublicKey publicKey,
                                           BlindedCredentialSecrets secrets,
                                           BlindedCredentialSecretsCorrectnessProof proof,
                                           BigInteger nonce) throws ZkpException {

        final BigInteger s = publicKey.getS();
        final BigInteger n = publicKey.getN();
        final LinkedHashMap<String, BigInteger> r = publicKey.getR();

        final BigInteger u = secrets.getU();

        final BigInteger proof_c = proof.getC();
        final BigInteger v_dash_cap = proof.getVDashCap();
        final LinkedHashMap<String, BigInteger> m_caps = proof.getmCaps();

        BigInteger u_cap = u.modInverse(n).modPow(proof_c, n).multiply(s.modPow(v_dash_cap, n));

        for (String key : r.keySet()) {
            if (m_caps.containsKey(key)) {
                BigInteger m_cap = m_caps.get(key);
                u_cap = u_cap.multiply(r.get(key).modPow(m_cap, n));
            }
        }
        u_cap = u_cap.mod(n);

        BigInteger c_cap = new ChallengeBuilder()
                .add(u)
                .add(u_cap)
                .add(nonce)
                .buildWithHashing();


        if (c_cap != null && c_cap.equals(proof_c)) {
            return true;
        }
        // ERR_CODE_ZKP_ISSUER_CHECK_BLINDED_SECRETS_CORRECTNESS_PROOF_FAIL
        System.out.println("verify CredentialRequest c_cap and proof_c not match\n" + "c_cap :"+c_cap.toString()+"\n" + "proof_c :"+proof_c.toString());
//        throw new ZkpException(ErrorCode.ERR_CODE_ZKP_BIG_NUMBER_COMPARE_FAIL, "verify CredentialRequest c_cap and proof_c not match");
        return false;
    }

    public static KeyCorrectnessProof generateKeyProof(CredentialPrimaryKeyPair keyPair) throws ZkpException {
        CredentialPrimaryPublicKey publicKey = keyPair.getPublicKey();
        CredentialPrimaryPrivateKey privateKey = keyPair.getPrivateKey();
        PublicKeyMetadata publicKeyMetadata = keyPair.getPublicKeyMetadata();

        BigIntegerUtil generator = new BigIntegerUtil();

        final BigInteger n = publicKey.getN();
        final BigInteger s = publicKey.getS();

        final BigInteger p = privateKey.getP();
        final BigInteger q = privateKey.getQ();

        final BigInteger xz = publicKeyMetadata.getXz();
        final BigInteger xz_tilde = generator.generateX(p, q);
        final BigInteger z_tilde = s.modPow(xz_tilde, n);

        LinkedHashMap<String, BigInteger> xr = publicKeyMetadata.getXr();

        LinkedHashMap<String, BigInteger> xr_tilde = new LinkedHashMap<String, BigInteger>();
        LinkedHashMap<String, BigInteger> r_tilde = new LinkedHashMap<String, BigInteger>();

        for (String attrName : publicKey.getR().keySet()) {
            BigInteger xr_tilde_value = generator.generateX(p, q);
            xr_tilde.put(attrName, xr_tilde_value);
            r_tilde.put(attrName, s.modPow(xr_tilde_value, n));
        }

        BigInteger c = new ChallengeBuilder()
                .add(publicKey.getZ())
                .add(publicKey.getR())
                .add(z_tilde)
                .add(r_tilde)
                .buildWithHashing();

        BigInteger xz_cap = c.multiply(xz).add(xz_tilde);

        LinkedHashMap<String, BigInteger> xr_cap = new LinkedHashMap<String, BigInteger>();

        for (String attrName : publicKey.getR().keySet()) {
            BigInteger xr_value = xr.get(attrName);
            BigInteger xr_tilde_value = xr_tilde.get(attrName);

            xr_cap.put(attrName, c.multiply(xr_value).add(xr_tilde_value));
        }
        return new KeyCorrectnessProof(c, xz_cap, xr_cap);

    }
}
