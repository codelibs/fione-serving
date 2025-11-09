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
package org.codelibs.fione.serving;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

class FioneServingConstantsTest {

    @Test
    void testModelPathConstant() {
        assertEquals("MODEL_PATH", FioneServingConstants.MODEL_PATH);
    }

    @Test
    void testEnableLeafAssignmentConstant() {
        assertEquals("ENABLE_LEAF_ASSIGNMENT", FioneServingConstants.ENABLE_LEAF_ASSIGNMENT);
    }

    @Test
    void testEnableContributionsConstant() {
        assertEquals("ENABLE_CONTRIBUTIONS", FioneServingConstants.ENABLE_CONTRIBUTIONS);
    }

    @Test
    void testServingModelPathConstant() {
        assertEquals("fione.serving.model.path", FioneServingConstants.SERVING_MODEL_PATH);
    }

    @Test
    void testAllConstantsAreNotNull() {
        assertNotNull(FioneServingConstants.MODEL_PATH);
        assertNotNull(FioneServingConstants.ENABLE_LEAF_ASSIGNMENT);
        assertNotNull(FioneServingConstants.ENABLE_CONTRIBUTIONS);
        assertNotNull(FioneServingConstants.SERVING_MODEL_PATH);
    }

    @Test
    void testAllConstantsAreNotEmpty() {
        assertEquals(false, FioneServingConstants.MODEL_PATH.isEmpty());
        assertEquals(false, FioneServingConstants.ENABLE_LEAF_ASSIGNMENT.isEmpty());
        assertEquals(false, FioneServingConstants.ENABLE_CONTRIBUTIONS.isEmpty());
        assertEquals(false, FioneServingConstants.SERVING_MODEL_PATH.isEmpty());
    }

    @Test
    void testConstantValues() {
        // Verify exact values
        assertEquals("MODEL_PATH", FioneServingConstants.MODEL_PATH);
        assertEquals("ENABLE_LEAF_ASSIGNMENT", FioneServingConstants.ENABLE_LEAF_ASSIGNMENT);
        assertEquals("ENABLE_CONTRIBUTIONS", FioneServingConstants.ENABLE_CONTRIBUTIONS);
        assertEquals("fione.serving.model.path", FioneServingConstants.SERVING_MODEL_PATH);
    }

    @Test
    void testPrivateConstructor()
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<FioneServingConstants> constructor = FioneServingConstants.class.getDeclaredConstructor();
        assertEquals(true, Modifier.isPrivate(constructor.getModifiers()));

        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    void testConstantsArePublicStaticFinal() throws NoSuchFieldException {
        // Test MODEL_PATH
        int modelPathModifiers = FioneServingConstants.class.getField("MODEL_PATH").getModifiers();
        assertEquals(true, Modifier.isPublic(modelPathModifiers));
        assertEquals(true, Modifier.isStatic(modelPathModifiers));
        assertEquals(true, Modifier.isFinal(modelPathModifiers));

        // Test ENABLE_LEAF_ASSIGNMENT
        int enableLeafModifiers = FioneServingConstants.class.getField("ENABLE_LEAF_ASSIGNMENT").getModifiers();
        assertEquals(true, Modifier.isPublic(enableLeafModifiers));
        assertEquals(true, Modifier.isStatic(enableLeafModifiers));
        assertEquals(true, Modifier.isFinal(enableLeafModifiers));

        // Test ENABLE_CONTRIBUTIONS
        int enableContribModifiers = FioneServingConstants.class.getField("ENABLE_CONTRIBUTIONS").getModifiers();
        assertEquals(true, Modifier.isPublic(enableContribModifiers));
        assertEquals(true, Modifier.isStatic(enableContribModifiers));
        assertEquals(true, Modifier.isFinal(enableContribModifiers));

        // Test SERVING_MODEL_PATH
        int servingPathModifiers = FioneServingConstants.class.getField("SERVING_MODEL_PATH").getModifiers();
        assertEquals(true, Modifier.isPublic(servingPathModifiers));
        assertEquals(true, Modifier.isStatic(servingPathModifiers));
        assertEquals(true, Modifier.isFinal(servingPathModifiers));
    }
}
