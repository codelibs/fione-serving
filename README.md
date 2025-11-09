Fione Serving
[![Java CI with Maven](https://github.com/codelibs/fione-serving/actions/workflows/maven.yml/badge.svg)](https://github.com/codelibs/fione-serving/actions/workflows/maven.yml)
=========

## Overview

Fione Serving is a RESTful API server for serving H2O MOJO (Model Object, Optimized) models. It provides a simple and efficient way to deploy H2O machine learning models for real-time predictions via HTTP endpoints.

Built on Spring Boot 3.5.7, Fione Serving supports various H2O model types including:
- Binomial classification models
- Multinomial classification models
- Regression models
- XGBoost models (via h2o-genmodel-ext-xgboost)

## Requirements

- Java 17 or later
- Maven 3.x
- H2O MOJO model file (.zip)

## Build

Build the project using Maven:

```bash
mvn clean package
```

This will create an executable JAR file in the `target` directory:
```
target/fione-serving-3.40.0.0-SNAPSHOT.jar
```

## Configuration

### Required Environment Variables

- `MODEL_PATH`: Path to your H2O MOJO model file (.zip)

### Optional Environment Variables

- `ENABLE_LEAF_ASSIGNMENT`: Set to `true` to enable leaf assignment for tree-based models (default: `false`)
- `ENABLE_CONTRIBUTIONS`: Set to `true` to enable SHAP contributions for supported models (default: `false`)

### Alternative Configuration

You can also specify the model path using a system property:
```bash
-Dfione.serving.model.path=/path/to/model.zip
```

## Usage

### Running the Server

1. Set the required environment variable:
```bash
export MODEL_PATH=/path/to/your/model.zip
```

2. Run the application:
```bash
java -jar target/fione-serving-3.40.0.0-SNAPSHOT.jar
```

Or use both methods together:
```bash
MODEL_PATH=/path/to/model.zip java -jar target/fione-serving-3.40.0.0-SNAPSHOT.jar
```

The server will start on port 8080 by default.

### API Endpoints

#### 1. Health Check

Check if the server is running:

**Request:**
```bash
curl http://localhost:8080/ping
```

**Response:**
```
200 OK
```

#### 2. Get Model Information

Retrieve detailed information about the loaded model:

**Request:**
```bash
curl -X GET http://localhost:8080/model \
  -H "Content-Type: application/json"
```

**Response Example:**
```json
{
  "names": ["feature1", "feature2", "target"],
  "domains": [null, null, ["class0", "class1"]],
  "responseColumn": "target",
  "algoName": "GBM",
  "h2oVersion": "3.40.0.4",
  "category": "Binomial",
  "uuid": "model-uuid",
  "supervised": true,
  "nfeatures": 2,
  "nclasses": 2,
  "mojo_version": 1.0,
  "instance": {
    "feature1": "?",
    "feature2": "?"
  }
}
```

#### 3. Make Predictions

Send prediction requests to the model:

**Request:**
```bash
curl -X POST http://localhost:8080/invocations \
  -H "Content-Type: application/json" \
  -d '{
    "instances": [
      {
        "feature1": "value1",
        "feature2": 123.45
      },
      {
        "feature1": "value2",
        "feature2": 678.90
      }
    ]
  }'
```

**Response Examples:**

For **binomial/multinomial classification**:
```json
{
  "predictions": [
    {
      "label": "class1",
      "index": 1,
      "class_probabilities": [0.23, 0.77]
    },
    {
      "label": "class0",
      "index": 0,
      "class_probabilities": [0.82, 0.18]
    }
  ]
}
```

For **regression**:
```json
{
  "predictions": [
    {
      "value": 42.5
    },
    {
      "value": 38.2
    }
  ]
}
```

## Development

### Running in Development Mode

For development with auto-reload:

```bash
mvn spring-boot:run
```

### Running Tests

Execute the test suite:

```bash
mvn test
```

## License

Licensed under the Apache License, Version 2.0. See the LICENSE file for details.

## Project Information

- **Version**: 3.40.0.0-SNAPSHOT
- **Spring Boot**: 3.5.7
- **H2O Version**: 3.40.0.4
- **Organization**: [CodeLibs](https://www.codelibs.org/)
- **Issues**: [GitHub Issues](https://github.com/codelibs/fione-serving/issues)

