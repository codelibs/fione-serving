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

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.codelibs.fione.serving.service.MachineLearningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hex.genmodel.MojoModel;
import hex.genmodel.descriptor.ModelDescriptor;

@RestController
@RequestMapping("/model")
public class ModelController {

    @Autowired
    MachineLearningService machineLearningService;

    @GetMapping(consumes = "application/json")
    @ResponseBody
    public Map<String, Object> execute() {
        final MojoModel mojoModel = machineLearningService.getModel();
        final Map<String, Object> map = new LinkedHashMap<>();
        map.put("names", mojoModel._names);
        map.put("domains", mojoModel._domains);
        map.put("responseColumn", mojoModel._responseColumn);
        map.put("offsetColumn", mojoModel._offsetColumn);
        map.put("algoName", mojoModel._algoName);
        map.put("h2oVersion", mojoModel._h2oVersion);
        map.put("category", mojoModel._category);
        map.put("uuid", mojoModel._uuid);
        map.put("supervised", mojoModel._supervised);
        map.put("nfeatures", mojoModel._nfeatures);
        map.put("nclasses", mojoModel._nclasses);
        map.put("balanceClasses", mojoModel._balanceClasses);
        map.put("defaultThreshold", mojoModel._defaultThreshold);
        map.put("priorClassDistrib", mojoModel._priorClassDistrib);
        map.put("modelClassDistrib", mojoModel._modelClassDistrib);
        map.put("mojo_version", mojoModel._mojo_version);
        map.put("modelDescriptor", createModelDescriptor(mojoModel._modelDescriptor));
        map.put("instance", Arrays.stream(mojoModel._names).filter(s -> !s.equals(mojoModel._responseColumn))
                .collect(Collectors.toMap(s -> s, s -> "?")));
        return map;
    }

    private Map<String, Object> createModelDescriptor(final ModelDescriptor descriptor) {
        if (descriptor == null) {
            return null;
        }
        final Map<String, Object> map = new LinkedHashMap<>();
        map.put("scoringDomains", descriptor.scoringDomains());
        map.put("projectVersion", descriptor.projectVersion());
        map.put("algoName", descriptor.algoName());
        map.put("algoFullName", descriptor.algoFullName());
        map.put("offsetColumn", descriptor.offsetColumn());
        map.put("weightsColumn", descriptor.weightsColumn());
        map.put("foldColumn", descriptor.foldColumn());
        map.put("modelCategory", descriptor.getModelCategory());
        map.put("supervised", descriptor.isSupervised());
        map.put("nfeatures", descriptor.nfeatures());
        map.put("features", descriptor.features());
        map.put("nclasses", descriptor.nclasses());
        map.put("columnNames", descriptor.columnNames());
        map.put("balanceClasses", descriptor.balanceClasses());
        map.put("defaultThreshold", descriptor.defaultThreshold());
        map.put("priorClassDist", descriptor.priorClassDist());
        map.put("modelClassDist", descriptor.modelClassDist());
        map.put("uuid", descriptor.uuid());
        map.put("timestamp", descriptor.timestamp());
        return map;
    }
}
