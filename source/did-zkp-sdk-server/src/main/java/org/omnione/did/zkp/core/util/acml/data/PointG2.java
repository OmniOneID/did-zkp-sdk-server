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


import com.google.gson.annotations.Expose;
import org.omnione.did.zkp.exception.ZkpErrorCode;
import org.omnione.did.zkp.exception.ZkpException;
import org.omnione.did.zkp.core.util.AMCLUtils;
import org.omnione.did.zkp.core.util.acml.BN254.*;
import org.omnione.did.zkp.datamodel.util.ZkpEncodeMethod;

import java.io.IOException;

public class PointG2 implements ZkpEncodeMethod {

    @Expose
    private ECP2 point;

    public PointG2() {

        BIG point_xa = new BIG(ROM.CURVE_Pxa);
        BIG point_xb = new BIG(ROM.CURVE_Pxb);
        BIG point_ya = new BIG(ROM.CURVE_Pya);
        BIG point_yb = new BIG(ROM.CURVE_Pyb);

        FP2 point_x = new FP2(point_xa, point_xb);
        FP2 point_y = new FP2(point_ya, point_yb);

        ECP2 gen_g2 = new ECP2(point_x, point_y);

        point = PAIR.G2mul(gen_g2, AMCLUtils.randModOrder(AMCLUtils.getRand()));
    }

    public ECP2 add(ECP2 value) {
        this.point.add(value);
        return this.point;
    }

    public void setEncodeString(String str) {
        String[] result = str.split(" ");

        point.getx().geta().XES = Integer.parseInt(result[0]);
        point.getx().geta().x.copy(BIG.fromString(result[1]));
        point.getx().getb().XES = Integer.parseInt(result[2]);
        point.getx().getb().x.copy(BIG.fromString(result[3]));
        point.gety().getb().XES = Integer.parseInt(result[4]);
        point.gety().geta().x.copy(BIG.fromString(result[5]));
        point.gety().getb().XES = Integer.parseInt(result[6]);
        point.gety().getb().x.copy(BIG.fromString(result[7]));
        point.getz().geta().XES = Integer.parseInt(result[8]);
        point.getz().geta().x.copy(BIG.fromString(result[9]));
        point.getz().getb().XES = Integer.parseInt(result[10]);
        point.getz().getb().x.copy(BIG.fromString(result[11]));

    }

    public String getEncodeString() {
        StringBuilder string = new StringBuilder();
        string.append(point.getx().geta().XES).append(" ").append(point.getx().geta().x.toString()).append(" ")
          .append(point.getx().getb().XES).append(" ").append(point.getx().getb().x.toString()).append(" ")
          .append(point.gety().geta().XES).append(" ").append(point.gety().geta().x.toString()).append(" ")
          .append(point.gety().getb().XES).append(" ").append(point.gety().getb().x.toString()).append(" ")
          .append(point.getz().geta().XES).append(" ").append(point.getz().geta().x.toString()).append(" ")
          .append(point.getz().getb().XES).append(" ").append(point.getz().getb().x.toString());
        return string.toString();
    }

    public ECP2 getPoint() {
        return point;
    }

    public void setPoint(ECP2 point) {
        this.point = point;
    }

    public byte[] toBytes() throws ZkpException {
        byte[] v = new byte[CONFIG_BIG.MODBYTES * 4];
        this.point.toBytes(v);
        return v;
    }
}
