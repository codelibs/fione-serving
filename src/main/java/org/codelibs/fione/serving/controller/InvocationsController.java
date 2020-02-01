/*
 * Copyright 2012-2020 CodeLibs Project and the Others.
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codelibs.fione.serving.entity.InvocationsRequest;
import org.codelibs.fione.serving.entity.InvocationsResponse;
import org.codelibs.fione.serving.service.MachineLearningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invocations")
public class InvocationsController {
    private static Logger logger = LogManager.getLogger(InvocationsController.class);

    @Autowired
    MachineLearningService machineLearningService;

    @PostMapping(consumes = "application/json")
    @ResponseBody
    public InvocationsResponse execute(@RequestBody final InvocationsRequest request) {
        if (logger.isDebugEnabled()) {
            logger.debug("/invocations {}", request);
        }
        final InvocationsResponse response = new InvocationsResponse();
        response.setPredictions(machineLearningService.predict(request.getInstances()));
        return response;
    }
}
