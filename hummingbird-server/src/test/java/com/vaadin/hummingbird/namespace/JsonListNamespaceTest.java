/*
 * Copyright 2000-2016 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.hummingbird.namespace;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Assert;
import org.junit.Test;

import elemental.json.Json;
import elemental.json.JsonValue;

public class JsonListNamespaceTest
        extends AbstractNamespaceTest<DependencyListNamespace> {

    private DependencyListNamespace namespace = createNamespace();

    @Test
    public void testSerializable() {
        namespace.add(Json.create("bar"));
        namespace.add(Json.createNull());
        namespace.add(Json.create(true));
        namespace.add(Json.create(5));
        namespace.add(Json.createObject());
        namespace.add(Json.createArray());

        List<JsonValue> values = new ArrayList<>();
        int size = namespace.size();
        for (int i = 0; i < size; i++) {
            values.add(namespace.get(i));
        }

        ListNamespace<JsonValue> copy = SerializationUtils
                .deserialize(SerializationUtils.serialize(namespace));

        Assert.assertNotSame(namespace, copy);

        Assert.assertEquals(values.size(), copy.size());
        for (int i = 0; i < size; i++) {
            JsonValue originalValue = values.get(i);
            JsonValue copyValue = copy.get(i);
            Assert.assertEquals(originalValue.toJson(), copyValue.toJson());
        }

        // Also verify that original value wasn't changed by the serialization
        Assert.assertEquals(values.size(), namespace.size());
        for (int i = 0; i < size; i++) {
            Assert.assertEquals(values.get(i), namespace.get(i));
        }

    }

}