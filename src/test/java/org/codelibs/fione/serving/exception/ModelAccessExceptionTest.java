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
package org.codelibs.fione.serving.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class ModelAccessExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String message = "Model access failed";
        ModelAccessException exception = new ModelAccessException(message);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testConstructorWithMessageAndIOException() {
        String message = "Model access failed";
        IOException cause = new IOException("IO error");
        ModelAccessException exception = new ModelAccessException(message, cause);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testExceptionIsFIoneServingSystemException() {
        ModelAccessException exception = new ModelAccessException("Test");

        assertTrue(exception instanceof FIoneServingSystemException);
    }

    @Test
    void testExceptionIsRuntimeException() {
        ModelAccessException exception = new ModelAccessException("Test");

        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testConstructorWithNullMessage() {
        ModelAccessException exception = new ModelAccessException(null);

        assertNotNull(exception);
        assertNull(exception.getMessage());
    }

    @Test
    void testConstructorWithNullIOException() {
        String message = "Model access failed";
        ModelAccessException exception = new ModelAccessException(message, (IOException) null);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testConstructorWithEmptyMessage() {
        String message = "";
        ModelAccessException exception = new ModelAccessException(message);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testSerialVersionUID() {
        ModelAccessException exception = new ModelAccessException("Test");

        // Just verify the exception can be instantiated
        assertNotNull(exception);
    }

    @Test
    void testThrowException() {
        try {
            throw new ModelAccessException("Model not found");
        } catch (ModelAccessException e) {
            assertEquals("Model not found", e.getMessage());
        }
    }

    @Test
    void testThrowExceptionWithIOException() {
        IOException ioException = new IOException("File not found");
        try {
            throw new ModelAccessException("Failed to load model", ioException);
        } catch (ModelAccessException e) {
            assertEquals("Failed to load model", e.getMessage());
            assertEquals(ioException, e.getCause());
            assertEquals("File not found", e.getCause().getMessage());
        }
    }

    @Test
    void testExceptionInheritance() {
        ModelAccessException exception = new ModelAccessException("Test");

        assertTrue(exception instanceof FIoneServingSystemException);
        assertTrue(exception instanceof RuntimeException);
        assertTrue(exception instanceof Exception);
        assertTrue(exception instanceof Throwable);
    }

    @Test
    void testIOExceptionCauseType() {
        IOException ioException = new IOException("IO error");
        ModelAccessException exception = new ModelAccessException("Model error", ioException);

        assertNotNull(exception.getCause());
        assertTrue(exception.getCause() instanceof IOException);
    }

    @Test
    void testModelPathNotFoundScenario() {
        String message = "MODEL_PATH is null.";
        ModelAccessException exception = new ModelAccessException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testModelLoadFailureScenario() {
        String modelPath = "/path/to/model.zip";
        IOException ioException = new IOException("Cannot read file");
        ModelAccessException exception = new ModelAccessException("Failed to initialize " + modelPath, ioException);

        assertTrue(exception.getMessage().contains("Failed to initialize"));
        assertTrue(exception.getMessage().contains(modelPath));
        assertEquals(ioException, exception.getCause());
    }
}
