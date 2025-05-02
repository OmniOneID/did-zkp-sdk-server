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

package org.omnione.did.zkp.datamodel.proof;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.omnione.did.zkp.datamodel.enums.PredicateType;
import org.omnione.did.zkp.exception.ZkpErrorCode;
import org.omnione.did.zkp.exception.ZkpException;

import java.math.BigInteger;

public class Predicate {
    @SerializedName("pType")
    @Expose
    private PredicateType pType;

    @SerializedName("pValue")
    @Expose
    private int pValue;

    @SerializedName("attrName")
    @Expose
    private String attrName;

    public Predicate() {}
    public Predicate(String attrName, PredicateType pType, int value) {
        this.pType = pType;
        this.attrName = attrName;
        this.pValue = value;
    }

    public PredicateType getPType() {
        return pType;
    }
    public void setPType(PredicateType pType) {
        this.pType = pType;
    }

    public int getPValue() {
        return pValue;
    }
    public void setPValue(int pValue) {
        this.pValue = pValue;
    }

    public String getAttrName() {
        return attrName;
    }
    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public int getDelta(int attr_value) {
        int delta = 0;

        switch(pType) {
            case GE:
                delta = attr_value - pValue;
                break;
            case GT:
                delta = attr_value - pValue - 1;
                break;
            case LE:
                delta = pValue - attr_value;
                break;
            case LT:
                delta = pValue - attr_value - 1;
                break;
        }
        return delta;
    }
    //Wrapper Method
    public int getDelta(BigInteger value) throws ZkpException {

        if (value == null) {
            throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_NULL, "delta is null ");
        }
        return getDelta(value.intValue());
    }

    public BigInteger getDeltaPrime() {
        int invalue = pValue;
        switch(pType) {
            case GT:
                invalue++;
                break;
            case LT:
                invalue--;
                break;
        }
        return new BigInteger(Integer.toString(invalue), 10);
    }

    public boolean isLess() {
        if (pType == PredicateType.LE || pType == PredicateType.LT) {
            return true;
        }
        return false;
    }
}
