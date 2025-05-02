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

package org.omnione.did.zkp.datamodel.credential;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import org.omnione.did.zkp.exception.ZkpErrorCode;
import org.omnione.did.zkp.exception.ZkpException;
import org.omnione.did.zkp.datamodel.util.BigIntegerSerializer;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AttributeValue {

    @JsonAdapter(BigIntegerSerializer.class)
    @SerializedName("encoded")
    @Expose
    private BigInteger encoded;
    @SerializedName("raw")
    @Expose
    private String raw;

    public String getRaw() {
        return raw;
    }
    public BigInteger getEncoded() {
        return encoded;
    }

    public void setRaw(String raw) throws ZkpException {
        this.raw = raw;
        this.encoded = genEncode(raw);
//        this.encoded = new BigInteger(raw);
    }
    public void setEncoded(BigInteger encoded) {
        this.encoded = encoded;
    }

    public BigInteger getEncode() {
        return this.encoded;
    }

    private BigInteger genEncode(String raw) throws ZkpException {

        try {
            return new BigInteger(raw);

        } catch (NumberFormatException ne) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                return new BigInteger(1, md.digest(raw.getBytes()));

            } catch (NoSuchAlgorithmException ex) {
                throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_NO_SUCH_ALG);
            }
        }

    }
}
