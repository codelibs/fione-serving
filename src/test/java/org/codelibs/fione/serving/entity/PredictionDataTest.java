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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PredictionDataTest {

    @Test
    void testDefaultConstructor() {
        PredictionData predictionData = new PredictionData();
        assertNotNull(predictionData);
        assertTrue(predictionData.isEmpty());
    }

    @Test
    void testPutAndGet() {
        PredictionData predictionData = new PredictionData();
        predictionData.put("label", "0");

        assertEquals("0", predictionData.get("label"));
    }

    @Test
    void testPutMultipleValues() {
        PredictionData predictionData = new PredictionData();
        predictionData.put("label", "0");
        predictionData.put("index", 0);
        predictionData.put("value", 0.95);

        assertEquals("0", predictionData.get("label"));
        assertEquals(0, predictionData.get("index"));
        assertEquals(0.95, predictionData.get("value"));
    }

    @Test
    void testContainsKey() {
        PredictionData predictionData = new PredictionData();
        predictionData.put("label", "0");

        assertTrue(predictionData.containsKey("label"));
        assertFalse(predictionData.containsKey("nonexistent"));
    }

    @Test
    void testSize() {
        PredictionData predictionData = new PredictionData();
        assertEquals(0, predictionData.size());

        predictionData.put("label", "0");
        assertEquals(1, predictionData.size());

        predictionData.put("index", 0);
        assertEquals(2, predictionData.size());
    }

    @Test
    void testIsEmpty() {
        PredictionData predictionData = new PredictionData();
        assertTrue(predictionData.isEmpty());

        predictionData.put("label", "0");
        assertFalse(predictionData.isEmpty());
    }

    @Test
    void testRemove() {
        PredictionData predictionData = new PredictionData();
        predictionData.put("label", "0");
        predictionData.put("index", 0);

        predictionData.remove("label");

        assertFalse(predictionData.containsKey("label"));
        assertTrue(predictionData.containsKey("index"));
    }

    @Test
    void testClear() {
        PredictionData predictionData = new PredictionData();
        predictionData.put("label", "0");
        predictionData.put("index", 0);

        predictionData.clear();

        assertTrue(predictionData.isEmpty());
    }

    @Test
    void testPutWithClassProbabilities() {
        PredictionData predictionData = new PredictionData();
        double[] probabilities = new double[] { 0.8, 0.2 };
        predictionData.put("class_probabilities", probabilities);

        assertNotNull(predictionData.get("class_probabilities"));
        assertTrue(predictionData.get("class_probabilities") instanceof double[]);
    }

    @Test
    void testPutWithException() {
        PredictionData predictionData = new PredictionData();
        predictionData.put("exception", "Error message");

        assertEquals("Error message", predictionData.get("exception"));
    }

    @Test
    void testPutNullValue() {
        PredictionData predictionData = new PredictionData();
        predictionData.put("label", null);

        assertTrue(predictionData.containsKey("label"));
        assertNull(predictionData.get("label"));
    }

    @Test
    void testOverwriteValue() {
        PredictionData predictionData = new PredictionData();
        predictionData.put("label", "0");
        predictionData.put("label", "1");

        assertEquals("1", predictionData.get("label"));
    }

    @Test
    void testKeySet() {
        PredictionData predictionData = new PredictionData();
        predictionData.put("label", "0");
        predictionData.put("index", 0);

        assertEquals(2, predictionData.keySet().size());
        assertTrue(predictionData.keySet().contains("label"));
        assertTrue(predictionData.keySet().contains("index"));
    }

    @Test
    void testValues() {
        PredictionData predictionData = new PredictionData();
        predictionData.put("label", "0");
        predictionData.put("index", 0);

        assertEquals(2, predictionData.values().size());
        assertTrue(predictionData.values().contains("0"));
        assertTrue(predictionData.values().contains(0));
    }

    @Test
    void testEntrySet() {
        PredictionData predictionData = new PredictionData();
        predictionData.put("label", "0");
        predictionData.put("index", 0);

        assertEquals(2, predictionData.entrySet().size());
    }

    @Test
    void testExtendsLinkedHashMap() {
        PredictionData predictionData = new PredictionData();
        predictionData.put("first", "1");
        predictionData.put("second", "2");
        predictionData.put("third", "3");

        // LinkedHashMap maintains insertion order
        Object[] keys = predictionData.keySet().toArray();
        assertEquals("first", keys[0]);
        assertEquals("second", keys[1]);
        assertEquals("third", keys[2]);
    }
}
