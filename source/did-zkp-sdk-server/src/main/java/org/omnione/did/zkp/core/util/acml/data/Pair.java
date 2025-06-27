/*
 * Copyright 2024 OmniOne.
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
import org.omnione.did.zkp.core.util.acml.data.PointG1;
import org.omnione.did.zkp.core.util.acml.data.PointG2;
import org.omnione.did.zkp.exception.ZkpException;
import org.omnione.did.zkp.core.util.acml.BN254.BIG;
import org.omnione.did.zkp.core.util.acml.BN254.CONFIG_BIG;
import org.omnione.did.zkp.core.util.acml.BN254.FP12;
import org.omnione.did.zkp.core.util.acml.BN254.PAIR;
import org.omnione.did.zkp.datamodel.util.ZkpEncodeMethod;
import org.omnione.did.zkp.exception.ZkpErrorCode;

import java.io.IOException;

public class Pair implements ZkpEncodeMethod {

    @Expose
    private FP12 pair;

    public Pair() {
        this.pair = new FP12();
    }

    public Pair(PointG1 p, PointG2 q) {

        this.pair = PAIR.fexp(PAIR.ate(q.getPoint(), p.getPoint()));
        this.pair.reduce();
    }

    public byte[] toBytes() {

        byte[] v  = new byte[CONFIG_BIG.MODBYTES * 16];
        this.pair.toBytes(v);
        return v;
    }

    public FP12 getPair() {
        return pair;
    }

    public void setPair(FP12 pair) {
        this.pair = pair;
    }

    public String getEncodeString() {
        StringBuilder string = new StringBuilder();
        string.append(pair.geta().geta().geta().XES).append(" ").append(pair.geta().geta().geta().x.toString()).append(" ")
                .append(pair.geta().geta().getb().XES).append(" ").append(pair.geta().geta().getb().x.toString()).append(" ")
                .append(pair.geta().getb().geta().XES).append(" ").append(pair.geta().getb().geta().x.toString()).append(" ")
                .append(pair.geta().getb().getb().XES).append(" ").append(pair.geta().getb().getb().x.toString()).append(" ")
                .append(pair.getb().geta().geta().XES).append(" ").append(pair.getb().geta().geta().x.toString()).append(" ")
                .append(pair.getb().geta().getb().XES).append(" ").append(pair.getb().geta().getb().x.toString()).append(" ")
                .append(pair.getb().getb().geta().XES).append(" ").append(pair.getb().getb().geta().x.toString()).append(" ")
                .append(pair.getb().getb().getb().XES).append(" ").append(pair.getb().getb().getb().x.toString()).append(" ")
                .append(pair.getc().geta().geta().XES).append(" ").append(pair.getc().geta().geta().x.toString()).append(" ")
                .append(pair.getc().geta().getb().XES).append(" ").append(pair.getc().geta().getb().x.toString()).append(" ")
                .append(pair.getc().getb().geta().XES).append(" ").append(pair.getc().getb().geta().x.toString()).append(" ")
                .append(pair.getc().getb().getb().XES).append(" ").append(pair.getc().getb().getb().x.toString());

        return string.toString();
    }

    public void setEncodeString(String str) {
        String[] result = str.split(" ");

        pair.geta().geta().geta().XES = Integer.parseInt(result[0]);
        pair.geta().geta().geta().x.copy(BIG.fromString(result[1]));
        pair.geta().geta().getb().XES = Integer.parseInt(result[2]);
        pair.geta().geta().getb().x.copy(BIG.fromString(result[3]));
        pair.geta().getb().geta().XES = Integer.parseInt(result[4]);
        pair.geta().getb().geta().x.copy(BIG.fromString(result[5]));
        pair.geta().getb().getb().XES = Integer.parseInt(result[6]);
        pair.geta().getb().getb().x.copy(BIG.fromString(result[7]));
        pair.getb().geta().geta().XES = Integer.parseInt(result[8]);
        pair.getb().geta().geta().x.copy(BIG.fromString(result[9]));
        pair.getb().geta().getb().XES = Integer.parseInt(result[10]);
        pair.getb().geta().getb().x.copy(BIG.fromString(result[11]));
        pair.getb().getb().geta().XES = Integer.parseInt(result[12]);
        pair.getb().getb().geta().x.copy(BIG.fromString(result[13]));
        pair.getb().getb().getb().XES = Integer.parseInt(result[14]);
        pair.getb().getb().getb().x.copy(BIG.fromString(result[15]));
        pair.getc().geta().geta().XES = Integer.parseInt(result[16]);
        pair.getc().geta().geta().x.copy(BIG.fromString(result[17]));
        pair.getc().geta().getb().XES = Integer.parseInt(result[18]);
        pair.getc().geta().getb().x.copy(BIG.fromString(result[19]));
        pair.getc().getb().geta().XES = Integer.parseInt(result[20]);
        pair.getc().getb().geta().x.copy(BIG.fromString(result[21]));
        pair.getc().getb().getb().XES = Integer.parseInt(result[22]);
        pair.getc().getb().getb().x.copy(BIG.fromString(result[23]));
    }
}
