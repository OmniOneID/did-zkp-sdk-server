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

package org.omnione.did.zkp.core.util;

import org.omnione.did.zkp.core.util.acml.BN254.*;
import org.omnione.did.zkp.core.util.acml.HASH256;
import org.omnione.did.zkp.core.util.acml.RAND;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;

public class AMCLUtils {
    private static final BIG gx = new BIG(ROM.CURVE_Gx);
    private static final BIG gy = new BIG(ROM.CURVE_Gy);
    static final ECP genG1 = new ECP(gx, gy);
    private static final BIG pxa = new BIG(ROM.CURVE_Pxa);
    private static final BIG pxb = new BIG(ROM.CURVE_Pxb);
    private static final FP2 px = new FP2(pxa, pxb);
    private static final BIG pya = new BIG(ROM.CURVE_Pya);
    private static final BIG pyb = new BIG(ROM.CURVE_Pyb);
    private static final FP2 py = new FP2(pya, pyb);
    static final ECP2 genG2 = new ECP2(px, py);
    static final FP12 genGT = PAIR.fexp(PAIR.ate(genG2, genG1));
    static final BIG GROUP_ORDER = new BIG(ROM.CURVE_Order);
    static final int FIELD_BYTES = BIG.BIGBITS;

    private AMCLUtils() {
    }

    public static byte[] reverse(byte[] array) {

        if (null == array) {
            return null;
        }

        int i = 0;
        int j = array.length - 1;
        byte tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
        return array;
    }

    // Byte order : Little
    public static byte[] transform_i32_to_array_of_i8(int x) {
        int idx = 0;
        byte[] result = new byte[4];  // for java 6
        for (int i = 3; i >= 0 ; i--) {
            result[idx] = (byte)(x >> (i * 8));
//            System.out.println("i :"+i+", result :"+result[i]);
            idx++;
        }

        return result;

    }

    public static RAND getRand() {
        // construct a secure seed
        int entropy_bytes = 128;

        SecureRandom random = new SecureRandom();
//        byte[] seed = new byte[entropy_bytes];
        byte[] seed = random.generateSeed(entropy_bytes);
//        System.out.println(AMCLUtils.byteArrayToHex(seed));
        // create a new amcl.RAND and initialize it with the generated seed
        RAND rng = new RAND();
        rng.clean();
        rng.seed(entropy_bytes, seed);

        return rng;
    }

    public static BIG randModOrder(RAND rng) {
        BIG q = new BIG(ROM.CURVE_Order);
        // Takes random element in this Zq.
        return BIG.randomnum(q, rng);
    }

    public static BIG hashModOrder(byte[] data) {
        HASH256 hash = new HASH256();
        for (byte b : data) {
            hash.process(b);
        }

        byte[] hasheddata = hash.hash();

        BIG ret = BIG.fromBytes(hasheddata);
        ret.mod(AMCLUtils.GROUP_ORDER);

        return ret;
    }

    public static byte[] bigToBytes(BIG big) {
        byte[] ret = new byte[AMCLUtils.FIELD_BYTES];
        big.toBytes(ret);
        return ret;
    }

    static byte[] ecpToBytes(ECP e) {
        byte[] ret = new byte[2 * FIELD_BYTES + 1];
        e.toBytes(ret, false);
        return ret;
    }

    static byte[] ecpToBytes(ECP2 e) {
        byte[] ret = new byte[4 * FIELD_BYTES];
        e.toBytes(ret);
        return ret;
    }

    static byte[] append(byte[] data, byte[] toAppend) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            stream.write(data);
            stream.write(toAppend);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stream.toByteArray();
    }

    static byte[] append(byte[] data, boolean[] toAppend) {
        byte[] toAppendBytes = new byte[toAppend.length];
        for (int i = 0; i < toAppend.length; i++) {
            toAppendBytes[i] = toAppend[i] ? (byte) 1 : (byte) 0;
        }
        return append(data, toAppendBytes);
    }

    static BIG modAdd(BIG a, BIG b, BIG m) {
        BIG c = a.plus(b);
        c.mod(m);
        return c;
    }

    static BIG modSub(BIG a, BIG b, BIG m) {
        return modAdd(a, BIG.modneg(b, m), m);
    }

}
