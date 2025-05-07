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

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import org.omnione.did.zkp.exception.ZkpException;
import org.omnione.did.zkp.core.util.AMCLUtils;
import org.omnione.did.zkp.core.util.acml.BN254.BIG;
import org.omnione.did.zkp.core.util.acml.BN254.ECP2;
import org.omnione.did.zkp.core.util.acml.BN254.PAIR;
import org.omnione.did.zkp.core.util.acml.BN254.ROM;
import org.omnione.did.zkp.datamodel.util.ZkpSerializer;

public class Tail {

    @JsonAdapter(ZkpSerializer.class)
    @SerializedName("tail")
    private PointG2 t;

    public Tail() {
        t = new PointG2();
    }

    public ECP2 new_tail(int index, PointG2 g_dash, GroupOrderElement gamma) throws ZkpException {

        byte[] i_bytes = AMCLUtils.transform_i32_to_array_of_i8(index);
        BIG pow = GroupOrderElement.from_bytes(i_bytes);
        pow = gamma.getBn().powmod(pow, new BIG(ROM.CURVE_Order));
        t.setPoint(PAIR.G2mul(g_dash.getPoint(), pow));
        return t.getPoint();
    }

    public void setTail(PointG2 t) {
        this.t = t;
    }

    public PointG2 getTail() {
        return t;
    }
}
