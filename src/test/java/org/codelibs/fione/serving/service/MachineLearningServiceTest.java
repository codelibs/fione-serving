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
package org.codelibs.fione.serving.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.codelibs.fione.serving.FioneServingConstants;
import org.codelibs.fione.serving.entity.PredictionData;
import org.codelibs.fione.serving.exception.ModelAccessException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import hex.genmodel.easy.RowData;

@SpringBootTest
class MachineLearningServiceTest {

    @Autowired
    private MachineLearningService machineLearningService;

    @BeforeAll
    static void beforeAll() {
        Path modelPath = Paths.get("src", "test", "resources", "model", "DeepLearning.zip");
        System.setProperty(FioneServingConstants.SERVING_MODEL_PATH, modelPath.toFile().getAbsolutePath());
    }

    @Test
    void testGetModel() {
        assertNotNull(machineLearningService.getModel());
    }

    @Test
    void testGetModelReturnsNonNull() {
        assertNotNull(machineLearningService.getModel());
        assertNotNull(machineLearningService.getModel()._algoName);
    }

    @Test
    void testPredictWithSingleInstance() {
        RowData rowData = new RowData();
        rowData.put("AGE", "68");
        rowData.put("RACE", "2");
        rowData.put("DCAPS", "2");
        rowData.put("VOL", "0");
        rowData.put("GLEASON", "6");

        List<RowData> dataList = new ArrayList<>();
        dataList.add(rowData);

        List<PredictionData> result = machineLearningService.predict(dataList);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertNotNull(result.get(0));
    }

    @Test
    void testPredictWithMultipleInstances() {
        List<RowData> dataList = new ArrayList<>();

        RowData rowData1 = new RowData();
        rowData1.put("AGE", "68");
        rowData1.put("RACE", "2");
        rowData1.put("DCAPS", "2");
        rowData1.put("VOL", "0");
        rowData1.put("GLEASON", "6");
        dataList.add(rowData1);

        RowData rowData2 = new RowData();
        rowData2.put("AGE", "65");
        rowData2.put("RACE", "1");
        rowData2.put("DCAPS", "1");
        rowData2.put("VOL", "5");
        rowData2.put("GLEASON", "7");
        dataList.add(rowData2);

        List<PredictionData> result = machineLearningService.predict(dataList);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testPredictWithEmptyList() {
        List<RowData> dataList = new ArrayList<>();

        List<PredictionData> result = machineLearningService.predict(dataList);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testPredictReturnsValidPredictionData() {
        RowData rowData = new RowData();
        rowData.put("AGE", "68");
        rowData.put("RACE", "2");
        rowData.put("DCAPS", "2");
        rowData.put("VOL", "0");
        rowData.put("GLEASON", "6");

        List<RowData> dataList = new ArrayList<>();
        dataList.add(rowData);

        List<PredictionData> result = machineLearningService.predict(dataList);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        PredictionData predictionData = result.get(0);
        assertNotNull(predictionData);
    }

    @Test
    void testPredictWithInvalidData() {
        RowData rowData = new RowData();
        rowData.put("INVALID_COLUMN", "value");

        List<RowData> dataList = new ArrayList<>();
        dataList.add(rowData);

        List<PredictionData> result = machineLearningService.predict(dataList);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).containsKey("exception") || result.get(0).containsKey("label")
                || result.get(0).containsKey("value"));
    }

    @Test
    void testGetModelPath() {
        String modelPath = machineLearningService.getModelPath();
        assertNotNull(modelPath);
        assertFalse(modelPath.isEmpty());
    }

    @Test
    void testGetModelPathThrowsExceptionWhenNull() {
        System.clearProperty(FioneServingConstants.SERVING_MODEL_PATH);
        MachineLearningService service = new MachineLearningService();
        assertThrows(ModelAccessException.class, () -> service.getModelPath());

        // Restore the property for other tests
        Path modelPath = Paths.get("src", "test", "resources", "model", "DeepLearning.zip");
        System.setProperty(FioneServingConstants.SERVING_MODEL_PATH, modelPath.toFile().getAbsolutePath());
    }

    @Test
    void testEnableLeafAssignmentDefaultFalse() {
        boolean result = machineLearningService.enableLeafAssignement();
        // Default should be false when environment variable is not set
        assertEquals(false, result);
    }

    @Test
    void testEnableContributionsDefaultFalse() {
        boolean result = machineLearningService.enableContributions();
        // Default should be false when environment variable is not set
        assertEquals(false, result);
    }

    @Test
    void testPredictWithNullValues() {
        RowData rowData = new RowData();
        rowData.put("AGE", null);
        rowData.put("RACE", null);

        List<RowData> dataList = new ArrayList<>();
        dataList.add(rowData);

        List<PredictionData> result = machineLearningService.predict(dataList);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testCreatePredictionDataWithValidRowData() {
        RowData rowData = new RowData();
        rowData.put("AGE", "68");
        rowData.put("RACE", "2");
        rowData.put("DCAPS", "2");
        rowData.put("VOL", "0");
        rowData.put("GLEASON", "6");

        PredictionData result = machineLearningService.createPredictionData(rowData);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}
