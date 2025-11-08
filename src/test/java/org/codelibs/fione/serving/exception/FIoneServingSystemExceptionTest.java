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

import org.junit.jupiter.api.Test;

class FIoneServingSystemExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String message = "Test exception message";
        FIoneServingSystemException exception = new FIoneServingSystemException(message);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testConstructorWithMessageAndCause() {
        String message = "Test exception message";
        Throwable cause = new RuntimeException("Cause exception");
        FIoneServingSystemException exception = new FIoneServingSystemException(message, cause);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testExceptionIsRuntimeException() {
        FIoneServingSystemException exception = new FIoneServingSystemException("Test");

        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testConstructorWithNullMessage() {
        FIoneServingSystemException exception = new FIoneServingSystemException(null);

        assertNotNull(exception);
        assertNull(exception.getMessage());
    }

    @Test
    void testConstructorWithNullCause() {
        String message = "Test exception message";
        FIoneServingSystemException exception = new FIoneServingSystemException(message, null);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testConstructorWithEmptyMessage() {
        String message = "";
        FIoneServingSystemException exception = new FIoneServingSystemException(message);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testSerialVersionUID() {
        FIoneServingSystemException exception = new FIoneServingSystemException("Test");

        // Just verify the exception can be instantiated
        assertNotNull(exception);
    }

    @Test
    void testThrowException() {
        try {
            throw new FIoneServingSystemException("Test exception");
        } catch (FIoneServingSystemException e) {
            assertEquals("Test exception", e.getMessage());
        }
    }

    @Test
    void testThrowExceptionWithCause() {
        RuntimeException cause = new RuntimeException("Original cause");
        try {
            throw new FIoneServingSystemException("Test exception", cause);
        } catch (FIoneServingSystemException e) {
            assertEquals("Test exception", e.getMessage());
            assertEquals(cause, e.getCause());
            assertEquals("Original cause", e.getCause().getMessage());
        }
    }

    @Test
    void testExceptionInheritance() {
        FIoneServingSystemException exception = new FIoneServingSystemException("Test");

        assertTrue(exception instanceof RuntimeException);
        assertTrue(exception instanceof Exception);
        assertTrue(exception instanceof Throwable);
    }
}
