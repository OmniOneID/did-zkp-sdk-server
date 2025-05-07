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

package org.omnione.did.zkp.crypto.constant;

import java.math.BigInteger;

public class ZkpCryptoConstants {
    public static final String MASTER_SECRET_KEY = "masterSecret";
    public final static String DELTA = "DELTA";
    public final static int ITERATION = 4;
    public final static int LARGE_E_START = 596;
    public final static BigInteger LARGE_E_START_VALUE = BigInteger.ONE.shiftLeft(LARGE_E_START);
    public static final String HASH_ALG = "SHA-256";
    public static final int LARGE_NONCE = 80;
    public static final int LARGE_PRIME = 1024;

}
