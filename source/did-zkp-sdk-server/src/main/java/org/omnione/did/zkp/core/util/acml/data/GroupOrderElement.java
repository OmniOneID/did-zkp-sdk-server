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

package org.omnione.did.zkp.core.util.acml.data;


import org.omnione.did.zkp.core.util.AMCLUtils;
import org.omnione.did.zkp.core.util.acml.BN254.CONFIG_BIG;
import org.omnione.did.zkp.datamodel.util.ZkpEncodeMethod;
import org.omnione.did.zkp.exception.ZkpErrorCode;
import org.omnione.did.zkp.exception.ZkpException;
import org.omnione.did.zkp.core.util.acml.BN254.BIG;
import org.omnione.did.zkp.core.util.acml.BN254.ROM;

import java.io.IOException;
import java.util.Vector;

public class GroupOrderElement implements ZkpEncodeMethod {
    private BIG bn;

    public GroupOrderElement(){

        this.bn = AMCLUtils.randModOrder(AMCLUtils.getRand());
    }

    public BIG getBn() {
        return bn;
    }

    public void setBn(BIG bn) {
        this.bn = bn;
    }

    public BIG add_mod(BIG r) {

        BIG sum = this.getBn();
        sum.add(r);
        sum.mod(new BIG(ROM.CURVE_Order));
        return sum;
    }

    public byte[] toBytes() {

        byte[] vec  = new byte[CONFIG_BIG.MODBYTES];
        this.getBn().toBytes(vec);
        return vec;
    }

    public static BIG from_bytes(byte[] b) {

        Vector<Byte> vec = new Vector<Byte>();
        for (int i = 0; i < b.length; i++) {
            vec.add(b[i]);
        }
        int len = vec.size();
        if (len < CONFIG_BIG.MODBYTES) {
            int diff = CONFIG_BIG.MODBYTES - len;
            byte[] result = new byte[diff];
            for (int i = 0; i < diff; i++) {
                result[i] = 0x00;
            }
            byte[] output = new byte[32];
            System.arraycopy(result, 0, output, 0, result.length);
            System.arraycopy(b, 0, output, diff, b.length);
            return BIG.fromBytes(output);
        }
        return BIG.fromBytes(b);

    }

    public void setEncodeString(String str) {
        bn.copy(BIG.fromString(str));
    }

    public String getEncodeString() throws ZkpException{
        return getBn().toString();
    }
}
