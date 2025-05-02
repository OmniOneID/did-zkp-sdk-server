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
import org.omnione.did.zkp.core.util.acml.BN254.BIG;
import org.omnione.did.zkp.core.util.acml.BN254.ECP;
import org.omnione.did.zkp.core.util.acml.BN254.PAIR;
import org.omnione.did.zkp.core.util.acml.BN254.ROM;
import org.omnione.did.zkp.datamodel.util.ZkpEncodeMethod;

import java.io.IOException;

public class PointG1 implements ZkpEncodeMethod {
    @Expose
    private ECP point;

    public PointG1() {

        BIG point_x = new BIG(ROM.CURVE_Gx);
        BIG point_y = new BIG(ROM.CURVE_Gy);
        ECP gen_g1 = new ECP(point_x, point_y);

        this.point = PAIR.G1mul(gen_g1, AMCLUtils.randModOrder(AMCLUtils.getRand()));
    }


    public ECP getPoint() {
        return point;
    }


    public void setPoint(ECP point) {
        this.point = point;
    }

    public String getEncodeString() {
        StringBuilder string = new StringBuilder();
        string.append(point.getx().XES).append(" ").append(point.getx().x.toString()).append(" ")
                .append(point.gety().XES).append(" ").append(point.gety().x.toString()).append(" ")
                .append(point.getz().XES).append(" ").append(point.getz().x.toString());
        return string.toString();
    }

    public void setEncodeString(String str) {
        String[] result = str.split(" ");
        
        point.getx().XES = Integer.parseInt(result[0]);
        point.getx().x.copy(BIG.fromString(result[1]));
        point.gety().XES = Integer.parseInt(result[2]);
        point.gety().x.copy(BIG.fromString(result[3]));
        point.getz().XES = Integer.parseInt(result[4]);
        point.getz().x.copy(BIG.fromString(result[5]));
    }
}
