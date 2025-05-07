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

package org.omnione.did.zkp.crypto.generator;

import org.omnione.did.zkp.exception.ZkpErrorCode;
import org.omnione.did.zkp.exception.ZkpException;
import org.omnione.did.zkp.crypto.constant.ZkpCryptoConstants;
import org.omnione.did.zkp.crypto.keypair.CredentialPrimaryKeyPair;
import org.omnione.did.zkp.crypto.keypair.CredentialPrimaryPrivateKey;
import org.omnione.did.zkp.crypto.keypair.CredentialPrimaryPublicKey;
import org.omnione.did.zkp.crypto.keypair.PublicKeyMetadata;
import org.omnione.did.zkp.crypto.util.BigIntegerUtil;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;

public class ZkpKeyPairGenerator {
    public CredentialPrimaryKeyPair generateKeyPair(List<String> attrNames) throws ZkpException {
        return generateKeyPair(attrNames, ZkpCryptoConstants.MASTER_SECRET_KEY);
    }

    private CredentialPrimaryKeyPair generateKeyPair(List<String> attrNames, String masterSecret) throws ZkpException {

        if (attrNames == null || attrNames.size() <= 0) {
            throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_NULL, "attributes null in generateKeyPair");
        }

        if (!attrNames.contains(masterSecret)) {
            attrNames.add(masterSecret);
        }

        BigIntegerUtil generator = new BigIntegerUtil();
        BigInteger p_safe;
        BigInteger p;
        BigInteger q_safe;
        BigInteger q;
        p_safe = BigInteger.probablePrime(ZkpCryptoConstants.LARGE_PRIME + 1, generator.getRandom());
        p = p_safe.shiftRight(1);
        q_safe = BigInteger.probablePrime(ZkpCryptoConstants.LARGE_PRIME + 1, generator.getRandom());
        q = q_safe.shiftRight(1);

        BigInteger n;
        BigInteger s;
        BigInteger z;
        LinkedHashMap<String, BigInteger> r;
        BigInteger rctxt;

        BigInteger xz;
        LinkedHashMap<String, BigInteger> xr;

        n = p_safe.multiply(q_safe);
        s = generator.generateQR(n);
        xz = generator.generateX(p, q);
        z = s.modPow(xz, n);

        rctxt = s.modPow(generator.generateX(p, q), n);

        r = new LinkedHashMap<String, BigInteger>();
        xr = new LinkedHashMap<String, BigInteger>();

        for (String attrName : attrNames) {
            BigInteger xr_value = generator.generateX(p, q);
            xr.put(attrName, xr_value);
            r.put(attrName, s.modPow(xr_value, n));
        }

        CredentialPrimaryPublicKey publicKey = new CredentialPrimaryPublicKey(n, s, z, r, rctxt);
        CredentialPrimaryPrivateKey privateKey = new CredentialPrimaryPrivateKey(p, q);
        PublicKeyMetadata publicKeyMetadata = new PublicKeyMetadata(xz, xr);

        return new CredentialPrimaryKeyPair(publicKey, privateKey, publicKeyMetadata);
    }
}
