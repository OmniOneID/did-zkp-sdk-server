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

import java.util.HashSet;
import java.util.Set;

//TODO: 클래스 재고려 필요
public class NonCredentialSchema {
    private Set<String> nonCredSchema;

    public NonCredentialSchema() {
        nonCredSchema = new HashSet<String>();
    }

    public void addAttr(String attr) {
        nonCredSchema.add(attr);
    }

    public Set<String> getNonCredSchema() {
        return nonCredSchema;
    }
}
