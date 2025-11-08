/*
 * Copyright 2012-2023 CodeLibs Project and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.codelibs.fione.serving.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import hex.genmodel.easy.RowData;

class InvocationsRequestTest {

    @Test
    void testDefaultConstructor() {
        InvocationsRequest request = new InvocationsRequest();
        assertNotNull(request);
    }

    @Test
    void testSetAndGetInstances() {
        InvocationsRequest request = new InvocationsRequest();
        List<RowData> instances = new ArrayList<>();
        RowData rowData = new RowData();
        rowData.put("AGE", "68");
        instances.add(rowData);

        request.setInstances(instances);

        assertNotNull(request.getInstances());
        assertEquals(1, request.getInstances().size());
        assertEquals("68", request.getInstances().get(0).get("AGE"));
    }

    @Test
    void testSetInstancesWithEmptyList() {
        InvocationsRequest request = new InvocationsRequest();
        List<RowData> instances = new ArrayList<>();

        request.setInstances(instances);

        assertNotNull(request.getInstances());
        assertTrue(request.getInstances().isEmpty());
    }

    @Test
    void testSetInstancesWithMultipleItems() {
        InvocationsRequest request = new InvocationsRequest();
        List<RowData> instances = new ArrayList<>();

        RowData rowData1 = new RowData();
        rowData1.put("AGE", "68");
        instances.add(rowData1);

        RowData rowData2 = new RowData();
        rowData2.put("AGE", "65");
        instances.add(rowData2);

        request.setInstances(instances);

        assertNotNull(request.getInstances());
        assertEquals(2, request.getInstances().size());
    }

    @Test
    void testSetInstancesWithNull() {
        InvocationsRequest request = new InvocationsRequest();
        request.setInstances(null);

        assertNull(request.getInstances());
    }

    @Test
    void testToString() {
        InvocationsRequest request = new InvocationsRequest();
        List<RowData> instances = new ArrayList<>();
        request.setInstances(instances);

        String result = request.toString();

        assertNotNull(result);
        assertTrue(result.contains("InvocationsRequest"));
    }

    @Test
    void testEqualsAndHashCode() {
        InvocationsRequest request1 = new InvocationsRequest();
        InvocationsRequest request2 = new InvocationsRequest();

        List<RowData> instances = new ArrayList<>();
        request1.setInstances(instances);
        request2.setInstances(instances);

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void testSetInstancesPreservesData() {
        InvocationsRequest request = new InvocationsRequest();
        List<RowData> instances = new ArrayList<>();

        RowData rowData = new RowData();
        rowData.put("AGE", "68");
        rowData.put("RACE", "2");
        rowData.put("DCAPS", "2");
        instances.add(rowData);

        request.setInstances(instances);

        assertEquals("68", request.getInstances().get(0).get("AGE"));
        assertEquals("2", request.getInstances().get(0).get("RACE"));
        assertEquals("2", request.getInstances().get(0).get("DCAPS"));
    }
}
