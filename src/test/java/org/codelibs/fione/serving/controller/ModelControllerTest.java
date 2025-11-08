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

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
class ModelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void beforeAll() {
        Path modelPath = Paths.get("src", "test", "resources", "model", "DeepLearning.zip");
        System.setProperty(FioneServingConstants.SERVING_MODEL_PATH, modelPath.toFile().getAbsolutePath());
    }

    @Test
    void testExecute() throws Exception {
        mockMvc.perform(get("/model").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.names").exists()).andExpect(jsonPath("$.algoName").exists())
                .andExpect(jsonPath("$.h2oVersion").exists());
    }

    @Test
    void testExecuteReturnsModelInformation() throws Exception {
        mockMvc.perform(get("/model").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.names").isArray()).andExpect(jsonPath("$.domains").exists())
                .andExpect(jsonPath("$.responseColumn").exists()).andExpect(jsonPath("$.algoName").exists())
                .andExpect(jsonPath("$.h2oVersion").exists()).andExpect(jsonPath("$.category").exists())
                .andExpect(jsonPath("$.uuid").exists()).andExpect(jsonPath("$.supervised").exists())
                .andExpect(jsonPath("$.nfeatures").exists()).andExpect(jsonPath("$.nclasses").exists());
    }

    @Test
    void testExecuteReturnsModelDescriptor() throws Exception {
        mockMvc.perform(get("/model").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.modelDescriptor").exists());
    }

    @Test
    void testExecuteReturnsInstance() throws Exception {
        mockMvc.perform(get("/model").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.instance").exists());
    }

    @Test
    void testExecuteReturnsValidJson() throws Exception {
        mockMvc.perform(get("/model").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap());
    }

    @Test
    void testExecuteMultipleTimes() throws Exception {
        for (int i = 0; i < 3; i++) {
            mockMvc.perform(get("/model").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                    .andExpect(jsonPath("$.algoName").exists());
        }
    }

    @Test
    void testExecuteReturnsNfeatures() throws Exception {
        mockMvc.perform(get("/model").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.nfeatures").value(notNullValue()));
    }

    @Test
    void testExecuteReturnsNclasses() throws Exception {
        mockMvc.perform(get("/model").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.nclasses").value(notNullValue()));
    }
}
