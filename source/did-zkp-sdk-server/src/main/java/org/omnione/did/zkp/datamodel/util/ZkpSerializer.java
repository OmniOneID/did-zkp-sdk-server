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

package org.omnione.did.zkp.datamodel.util;


import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.omnione.did.zkp.core.util.acml.data.GroupOrderElement;
import org.omnione.did.zkp.exception.ZkpErrorCode;
import org.omnione.did.zkp.exception.ZkpException;
import org.omnione.did.zkp.core.util.acml.data.Pair;
import org.omnione.did.zkp.core.util.acml.data.PointG1;
import org.omnione.did.zkp.core.util.acml.data.PointG2;

import java.io.IOException;

public class ZkpSerializer implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {

        TypeAdapter defaultAdapter = gson.getAdapter(typeToken);
        return new Adapter(defaultAdapter);
    }

    private static class Adapter<T extends ZkpEncodeMethod> extends TypeAdapter<T> {

        final TypeAdapter<T> defaultAdapter;

        Adapter(TypeAdapter<T> defaultAdapter) {
            this.defaultAdapter = defaultAdapter;
        }

        public void write(JsonWriter out, T value) throws IOException {
            try {
                out.value(value.getEncodeString());
            } catch (ZkpException e) {
                try {
                    throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_BIG_NUMBER_FROM_JSON_FAIL);
                } catch (ZkpException zkpException) {
                    zkpException.printStackTrace();
                }
            }
        }

        public T read(JsonReader in) throws IOException {

            String encodedString = null;
            while (in.hasNext()) {
                try {
                    encodedString = in.nextString();
                } catch (IOException e) {
                    break;
                }
            }
            if(encodedString == null) {
                try {
                    throw new ZkpException(ZkpErrorCode.ERR_CODE_ZKP_BIG_NUMBER_FROM_JSON_FAIL);
                } catch (ZkpException e) {
                    throw new RuntimeException(e);
                }
            }
            String[] array = encodedString.split(" ");

            if (array.length == 12) {
                PointG2 g2 = new PointG2();
                g2.setEncodeString(encodedString);
                return (T)g2;
            }
            else if (array.length == 6) {
                PointG1 g1 = new PointG1();
                g1.setEncodeString(encodedString);
                return (T)g1;
            }
            else if (array.length == 1) {
                GroupOrderElement groupOrderElement = new GroupOrderElement();
                groupOrderElement.setEncodeString(encodedString);
                return (T)groupOrderElement;
            }
            else {
                Pair pair = new Pair();
                pair.setEncodeString(encodedString);
                return (T)pair;
            }
        }
    }
}

