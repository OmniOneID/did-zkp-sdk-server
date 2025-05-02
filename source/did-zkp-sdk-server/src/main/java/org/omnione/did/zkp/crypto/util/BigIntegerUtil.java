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

import org.omnione.did.zkp.crypto.constant.ZkpCryptoConstants;
import org.omnione.did.zkp.crypto.keypair.CredentialPrimaryPublicKey;
import org.omnione.did.zkp.exception.ZkpErrorCode;
import org.omnione.did.zkp.exception.ZkpException;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class BigIntegerUtil {


    public static String createNonce1() throws ZkpException{
        String nonce = "";

        try {
            SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
            String randomNum = String.valueOf(prng.nextInt());

            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] result = sha.digest(randomNum.getBytes());
            nonce = byteArrayToHex(result);
            System.out.println(nonce);
        } catch (NoSuchAlgorithmException e) {
            throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_FAIL);
        }

        return nonce;
    }

    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder();
        for(final byte b: a) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

    public static String hexEncode(byte[] aInput) {
        StringBuilder result = new StringBuilder();

        char[] digits = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

        for (int idx = 0; idx < aInput.length; ++idx) {
            byte b = aInput[idx];
            result.append(digits[(b & 0xf0) >> 4]);
            result.append(digits[b & 0x0f]);
        }

        return result.toString();
    }


    private final SecureRandom random;
    private static final int MAX_ITERATIONS = 1000;
    private static final BigInteger TWO = BigInteger.valueOf(2);
    private static final BigInteger THREE = BigInteger.valueOf(3);

    public BigIntegerUtil() {
        this.random = new SecureRandom();
    }

    public static BigInteger generateX(BigInteger p, BigInteger q, SecureRandom random) throws ZkpException {
        return createRandomInRange(TWO, p.multiply(q).subtract(THREE), random).add(TWO);
    }

    public static BigInteger generateQR(BigInteger n, SecureRandom random) throws ZkpException {
        BigInteger temp = createRandomInRange(BigInteger.ZERO, n, random);
        return temp.multiply(temp).mod(n);
    }

    public static BigInteger createRandomInteger(int bitLength, SecureRandom random) {
        return new BigInteger(1, createRandom(bitLength, random));
    }

    public static BigInteger createRandomInRange(BigInteger min, BigInteger max, SecureRandom random) throws ZkpException {
        int cmp = min.compareTo(max);
        if (cmp >= 0) {
            if (cmp > 0) {
                throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_UNKNOWN, "'MIN' may not be greater than 'MAX'");
            }
            return min;
        }

        if (min.bitLength() > max.bitLength() / 2) {
            return createRandomInRange(BigInteger.ZERO, max.subtract(min), random).add(min);
        }

        for (int i = 0; i < MAX_ITERATIONS; ++i) {
            BigInteger x = createRandomBigInteger(max.bitLength(), random);
            if (x.compareTo(min) >= 0 && x.compareTo(max) <= 0) {
                return x;
            }
        }

        return createRandomBigInteger(max.subtract(min).bitLength() - 1, random).add(min);
    }

    public static BigInteger createRandomBigInteger(int bitLength, SecureRandom random) {
        return new BigInteger(1, createRandom(bitLength, random));
    }

    public static BigInteger createRandomPrime(BigInteger start, int intervalBits, int maxBits, SecureRandom random) {
        int certainty = maxBits <= 1024 ? 80 : (96 + 16 * ((maxBits) - 1) /1024);

        BigInteger rv = null;
        while (true) {

            byte[] base = createRandom(intervalBits, random);
            base[base.length - 1] |= 0x01;

            rv = start.add(new BigInteger(1, base));

            if (rv.bitLength() > maxBits)
                continue;

            if (rv.isProbablePrime(certainty) == true)
                break;
        }
        return rv;
    }

    private static byte[] createRandom(int bitLength, SecureRandom random) {

        int nBytes = (bitLength + 7) / 8;

        byte[] rv = new byte[nBytes];
        random.nextBytes(rv);

        int xBits = 8 * nBytes - bitLength;
        rv[0] &= (byte) (255 >>> xBits);

        return rv;
    }

    public static BigInteger fromBytes(byte[] bytes) {
        return new BigInteger(1, bytes);
    }

    public static byte[] asUnsignedByteArray(BigInteger value) {
        byte[] bytes = value.toByteArray();

        if (bytes[0] == 0) {
            byte[] tmp = new byte[bytes.length - 1];
            System.arraycopy(bytes, 1, tmp, 0, tmp.length);
            return tmp;
        }
        return bytes;
    }

    public static BigInteger getHash(byte[] src) throws ZkpException {
        try {
            MessageDigest md = MessageDigest.getInstance(ZkpCryptoConstants.HASH_ALG);
            return new BigInteger(1, md.digest(src));
        } catch (NoSuchAlgorithmException e) {
            throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_NO_SUCH_ALG, "SHA-256");
        }
    }

    public static BigInteger calc_teq(CredentialPrimaryPublicKey publicKey,
                                      BigInteger a_prime,
                                      BigInteger e,
                                      BigInteger v,
                                      Map<String, BigInteger> m_tilde,
                                      BigInteger m2_tilde,
                                      Set<String> unrevealed_attrs) throws ZkpException {

        BigInteger t = a_prime.modPow(e, publicKey.getN());

        BigInteger cur_r, cur_m;

        for (String key : unrevealed_attrs) {

            cur_r = publicKey.getR().get(key);
            cur_m = m_tilde.get(key);

            if (cur_r == null)
                throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_NULL, "Value by key '{}' not found in pk.r ");

            if (cur_m == null)
                throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_NULL, "Value by key '{}' not found in m_tilde ");

            t = t.multiply(cur_r.modPow(cur_m, publicKey.getN())).mod(publicKey.getN());
        }

        t = t.multiply(publicKey.getS().modPow(v, publicKey.getN())).mod(publicKey.getN());

        t = t.multiply(publicKey.getRctxt().modPow(m2_tilde, publicKey.getN())).mod(publicKey.getN());

        return t;
    }

    public static Vector<BigInteger> calc_tne(CredentialPrimaryPublicKey p_pub_key,
                                              Map<String, BigInteger> u,
                                              Map<String, BigInteger> r,
                                              BigInteger mj,
                                              BigInteger alpha,
                                              Map<String, BigInteger> t,
                                              boolean is_less) throws ZkpException {

        Vector<BigInteger> t_list = new Vector<BigInteger>();
        for (int i = 0; i < ZkpCryptoConstants.ITERATION; i++) {
            BigInteger cur_u = u.get(Integer.toString(i));
            if (cur_u == null)
                throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_NULL, "Value by key [" + i + "] not found in u");

            BigInteger cur_r = r.get(Integer.toString(i));
            if (cur_r == null)
                throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_NULL, "Value by key [" + i + "] not found in r");

            BigInteger t_tau = p_pub_key.getZ().modPow(cur_u, p_pub_key.getN()).multiply(p_pub_key.getS().modPow(cur_r, p_pub_key.getN())).mod(p_pub_key.getN());
            t_list.add(t_tau);
        }

        BigInteger delta = r.get(ZkpCryptoConstants.DELTA);
        if (delta == null)
            throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_NULL, "Value by key DELTA not found in r");

        BigInteger delta_predicate = (is_less == true) ? delta.negate() : delta;

        BigInteger t_tau = p_pub_key.getZ().modPow(mj, p_pub_key.getN()).multiply(p_pub_key.getS().modPow(delta_predicate, p_pub_key.getN())).mod(p_pub_key.getN());

        t_list.add(t_tau);

        BigInteger q = BigInteger.ONE;

        for (int i = 0; i < ZkpCryptoConstants.ITERATION; i++) {
            BigInteger cur_t = t.get(Integer.toString(i));
            if (cur_t == null)
                throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_NULL, "Value by key [" + i + "] not found in t");

            BigInteger cur_u = u.get(Integer.toString(i));
            if (cur_u == null)
                throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_NULL, "Value by key [" + i + "] not found in u");

            q = cur_t.modPow(cur_u, p_pub_key.getN()).multiply(q);
        }

        q = p_pub_key.getS().modPow(alpha, p_pub_key.getN()).multiply(q).mod(p_pub_key.getN());
        // Add q to t_list
        t_list.add(q);

        return t_list;
    }

    public static int[] four_squares(int delta) throws ZkpException {
        if (delta < 0)
            throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_UNKNOWN, "Cannot express a negative number as sum of four squares" + delta);

        int[] roots = new int[]{(int) largest_square_less_than(delta), 0, 0, 0};

        for (int i = roots[0]; i > 0; i--) {
            roots[0] = i;
            if (delta == roots[0]) {
                roots[1] = roots[2] = roots[3] = 0;
                return roots;
            }
            roots[1] = largest_square_less_than(delta - (roots[0] * roots[0]));
            for (int j = roots[1]; j > 0; j--) {
                roots[1] = j;
                if (delta == ((roots[0] * roots[0]) + (roots[1] * roots[1]))) {
                    roots[2] = roots[3] = 0;
                    return roots;
                }
                roots[2] = largest_square_less_than(delta - ((roots[0] * roots[0]) + (roots[1] * roots[1])));
                for (int k = roots[2]; k > 0; k--) {
                    roots[2] = k;
                    int exptemp = (roots[0] * roots[0]) + (roots[1] * roots[1]) + (roots[2] * roots[2]);
                    if (delta == exptemp) {
                        roots[3] = 0;
                        return roots;
                    }
                    roots[3] = largest_square_less_than(delta - exptemp);
                    exptemp += (roots[3] * roots[3]);
                    if (delta == exptemp) {
                        return roots;
                    }

                }
            }
        }
        return roots;
    }
    private static int largest_square_less_than(int usize) {
        return (int) Math.floor(Math.sqrt((double) usize));
    }

    public BigInteger generateX(BigInteger p, BigInteger q) throws ZkpException {
        return generateX(p, q, this.random);
    }
    public BigInteger generateQR(BigInteger n) throws ZkpException {
        return generateQR(n, this.random);
    }
    public BigInteger createRandomBigInteger(int bitLength) {
        return createRandomBigInteger(bitLength, this.random);
    }

    public BigInteger createRandom(BigInteger max) throws ZkpException {
        return createRandomInRange(BigInteger.ZERO, max, this.random);
    }
    public BigInteger createRandomPrime(BigInteger start, int intervalBits, int maxBits) {
        return createRandomPrime(start, intervalBits, maxBits, this.random);
    }

    public SecureRandom getRandom() {
        return this.random;
    }

    public BigInteger generateNonce() {
        return createRandomBigInteger(ZkpCryptoConstants.LARGE_NONCE, this.random);
    }

}
