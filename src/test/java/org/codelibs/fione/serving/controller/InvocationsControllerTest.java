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
package org.codelibs.fione.serving.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.codelibs.fione.serving.FioneServingConstants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class InvocationsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void beforeAll() {
        Path modelPath = Paths.get("src", "test", "resources", "model", "DeepLearning.zip");
        System.setProperty(FioneServingConstants.SERVING_MODEL_PATH, modelPath.toFile().getAbsolutePath());
    }

    @Test
    void testExecuteWithValidRequest() throws Exception {
        String requestJson =
                "{\"instances\":[{\"AGE\":\"68\",\"RACE\":\"2\",\"DCAPS\":\"2\",\"VOL\":\"0\",\"GLEASON\":\"6\"}]}";

        mockMvc.perform(post("/invocations").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk()).andExpect(jsonPath("$.predictions").exists())
                .andExpect(jsonPath("$.predictions").isArray());
    }

    @Test
    void testExecuteReturnsValidPredictions() throws Exception {
        String requestJson =
                "{\"instances\":[{\"AGE\":\"68\",\"RACE\":\"2\",\"DCAPS\":\"2\",\"VOL\":\"0\",\"GLEASON\":\"6\"}]}";

        mockMvc.perform(post("/invocations").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk()).andExpect(jsonPath("$.predictions").isNotEmpty());
    }

    @Test
    void testExecuteWithMultipleInstances() throws Exception {
        String requestJson =
                "{\"instances\":[{\"AGE\":\"68\",\"RACE\":\"2\",\"DCAPS\":\"2\",\"VOL\":\"0\",\"GLEASON\":\"6\"},"
                        + "{\"AGE\":\"65\",\"RACE\":\"1\",\"DCAPS\":\"1\",\"VOL\":\"5\",\"GLEASON\":\"7\"}]}";

        mockMvc.perform(post("/invocations").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk()).andExpect(jsonPath("$.predictions").isArray())
                .andExpect(jsonPath("$.predictions.length()").value(2));
    }

    @Test
    void testExecuteWithEmptyInstances() throws Exception {
        String requestJson = "{\"instances\":[]}";

        mockMvc.perform(post("/invocations").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk()).andExpect(jsonPath("$.predictions").isArray())
                .andExpect(jsonPath("$.predictions.length()").value(0));
    }

    @Test
    void testExecuteReturnsCorrectStructure() throws Exception {
        String requestJson =
                "{\"instances\":[{\"AGE\":\"68\",\"RACE\":\"2\",\"DCAPS\":\"2\",\"VOL\":\"0\",\"GLEASON\":\"6\"}]}";

        mockMvc.perform(post("/invocations").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk()).andExpect(jsonPath("$.predictions").exists())
                .andExpect(jsonPath("$.predictions[0]").exists());
    }

    @Test
    void testExecuteWithInvalidJson() throws Exception {
        String requestJson = "{invalid json}";

        mockMvc.perform(post("/invocations").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testExecuteWithoutContentType() throws Exception {
        String requestJson =
                "{\"instances\":[{\"AGE\":\"68\",\"RACE\":\"2\",\"DCAPS\":\"2\",\"VOL\":\"0\",\"GLEASON\":\"6\"}]}";

        mockMvc.perform(post("/invocations").content(requestJson)).andExpect(status().is4xxClientError());
    }

    @Test
    void testExecuteWithNullRequest() throws Exception {
        mockMvc.perform(post("/invocations").contentType(MediaType.APPLICATION_JSON).content("null"))
                .andExpect(status().is4xxClientError());
    }
}
