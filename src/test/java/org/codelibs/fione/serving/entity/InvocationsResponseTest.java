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

class InvocationsResponseTest {

    @Test
    void testDefaultConstructor() {
        InvocationsResponse response = new InvocationsResponse();
        assertNotNull(response);
    }

    @Test
    void testSetAndGetPredictions() {
        InvocationsResponse response = new InvocationsResponse();
        List<PredictionData> predictions = new ArrayList<>();
        PredictionData predictionData = new PredictionData();
        predictionData.put("label", "0");
        predictions.add(predictionData);

        response.setPredictions(predictions);

        assertNotNull(response.getPredictions());
        assertEquals(1, response.getPredictions().size());
        assertEquals("0", response.getPredictions().get(0).get("label"));
    }

    @Test
    void testSetPredictionsWithEmptyList() {
        InvocationsResponse response = new InvocationsResponse();
        List<PredictionData> predictions = new ArrayList<>();

        response.setPredictions(predictions);

        assertNotNull(response.getPredictions());
        assertTrue(response.getPredictions().isEmpty());
    }

    @Test
    void testSetPredictionsWithMultipleItems() {
        InvocationsResponse response = new InvocationsResponse();
        List<PredictionData> predictions = new ArrayList<>();

        PredictionData predictionData1 = new PredictionData();
        predictionData1.put("label", "0");
        predictions.add(predictionData1);

        PredictionData predictionData2 = new PredictionData();
        predictionData2.put("label", "1");
        predictions.add(predictionData2);

        response.setPredictions(predictions);

        assertNotNull(response.getPredictions());
        assertEquals(2, response.getPredictions().size());
    }

    @Test
    void testSetPredictionsWithNull() {
        InvocationsResponse response = new InvocationsResponse();
        response.setPredictions(null);

        assertNull(response.getPredictions());
    }

    @Test
    void testToString() {
        InvocationsResponse response = new InvocationsResponse();
        List<PredictionData> predictions = new ArrayList<>();
        response.setPredictions(predictions);

        String result = response.toString();

        assertNotNull(result);
        assertTrue(result.contains("InvocationsResponse"));
    }

    @Test
    void testEqualsAndHashCode() {
        InvocationsResponse response1 = new InvocationsResponse();
        InvocationsResponse response2 = new InvocationsResponse();

        List<PredictionData> predictions = new ArrayList<>();
        response1.setPredictions(predictions);
        response2.setPredictions(predictions);

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void testSetPredictionsPreservesData() {
        InvocationsResponse response = new InvocationsResponse();
        List<PredictionData> predictions = new ArrayList<>();

        PredictionData predictionData = new PredictionData();
        predictionData.put("label", "0");
        predictionData.put("index", 0);
        predictionData.put("value", 0.95);
        predictions.add(predictionData);

        response.setPredictions(predictions);

        assertEquals("0", response.getPredictions().get(0).get("label"));
        assertEquals(0, response.getPredictions().get(0).get("index"));
        assertEquals(0.95, response.getPredictions().get(0).get("value"));
    }

    @Test
    void testSetPredictionsWithComplexData() {
        InvocationsResponse response = new InvocationsResponse();
        List<PredictionData> predictions = new ArrayList<>();

        PredictionData predictionData = new PredictionData();
        predictionData.put("label", "0");
        predictionData.put("class_probabilities", new double[] { 0.8, 0.2 });
        predictions.add(predictionData);

        response.setPredictions(predictions);

        assertNotNull(response.getPredictions());
        assertEquals(1, response.getPredictions().size());
        assertTrue(response.getPredictions().get(0).containsKey("class_probabilities"));
    }
}
