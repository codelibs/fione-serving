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
package org.codelibs.fione.serving.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codelibs.fione.serving.FioneServingConstants;
import org.codelibs.fione.serving.entity.PredictionData;
import org.codelibs.fione.serving.exception.ModelAccessException;
import org.springframework.stereotype.Service;

import hex.ModelCategory;
import hex.genmodel.MojoModel;
import hex.genmodel.PredictContributionsFactory;
import hex.genmodel.algos.tree.SharedTreeMojoModel;
import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.exception.PredictException;
import hex.genmodel.easy.prediction.AbstractPrediction;
import hex.genmodel.easy.prediction.BinomialModelPrediction;
import hex.genmodel.easy.prediction.MultinomialModelPrediction;
import hex.genmodel.easy.prediction.RegressionModelPrediction;

@Service
public class MachineLearningService {
    private static Logger logger = LogManager.getLogger(MachineLearningService.class);

    private MojoModel mojoModel;

    private EasyPredictModelWrapper predictModel;

    @PostConstruct
    public void initialize() {
        if (logger.isDebugEnabled()) {
            logger.debug("initializing MachineLearningService");
        }
        final String modelPath = getModelPath();
        try {
            mojoModel = MojoModel.load(modelPath);
            final EasyPredictModelWrapper.Config config =
                    new EasyPredictModelWrapper.Config().setModel(mojoModel).setEnableLeafAssignment(enableLeafAssignement())
                            .setEnableContributions(enableContributions());
            predictModel = new EasyPredictModelWrapper(config);
        } catch (final IOException e) {
            throw new ModelAccessException("Failed to initialize " + modelPath, e);
        }
    }

    protected String getModelPath() {
        final String modelPath =
                System.getProperty(FioneServingConstants.SERVING_MODEL_PATH, System.getenv(FioneServingConstants.MODEL_PATH));
        if (modelPath == null) {
            throw new ModelAccessException("MODEL_PATH is null.");
        }
        return modelPath;
    }

    protected boolean enableLeafAssignement() {
        final String value = System.getenv(FioneServingConstants.ENABLE_LEAF_ASSIGNMENT);
        if (Boolean.parseBoolean(value)) {
            return mojoModel instanceof SharedTreeMojoModel;
        }
        return false;
    }

    protected boolean enableContributions() {
        final String value = System.getenv(FioneServingConstants.ENABLE_CONTRIBUTIONS);
        if (Boolean.parseBoolean(value)) {
            return mojoModel instanceof PredictContributionsFactory && ModelCategory.Multinomial != mojoModel.getModelCategory();
        }
        return false;
    }

    public List<PredictionData> predict(final List<RowData> dataList) {
        return dataList.stream().map(this::createPredictionData).collect(Collectors.toList());
    }

    protected PredictionData createPredictionData(final RowData data) {
        final PredictionData predictionData = new PredictionData();
        try {
            final AbstractPrediction p = predictModel.predict(data);
            if (p instanceof BinomialModelPrediction) {
                final BinomialModelPrediction prediction = (BinomialModelPrediction) p;
                predictionData.put("label", prediction.label);
                predictionData.put("index", prediction.labelIndex);
                predictionData.put("class_probabilities", prediction.classProbabilities);
            } else if (p instanceof MultinomialModelPrediction) {
                final MultinomialModelPrediction prediction = (MultinomialModelPrediction) p;
                predictionData.put("label", prediction.label);
                predictionData.put("index", prediction.labelIndex);
                predictionData.put("class_probabilities", prediction.classProbabilities);
            } else if (p instanceof RegressionModelPrediction) {
                final RegressionModelPrediction prediction = (RegressionModelPrediction) p;
                predictionData.put("value", prediction.value);
            } else {
                throw new ModelAccessException("Unknown prediction class: " + p.getClass());
            }
        } catch (final PredictException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("Failed to predict {}", data, e);
            }
            predictionData.put("exception", e.getMessage());
        }
        return predictionData;
    }

    public MojoModel getModel() {
        return mojoModel;
    }
}
