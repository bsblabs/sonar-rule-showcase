/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bsb.common.integration.checkstyle.util;

import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * @author Sebastien Gerard
 */
public class AbstractCheckStateTest {

    @Test(expected = IllegalStateException.class)
    public void checkBothNull() {
        new TestableCheckState().checkBeforeLog();
    }

    @Test(expected = IllegalStateException.class)
    public void checkLineNull() {
        final TestableCheckState state = new TestableCheckState();
        state.setViolationLine(0);
        state.checkBeforeLog();
    }

    @Test(expected = IllegalStateException.class)
    public void checkColumnNull() {
        final TestableCheckState state = new TestableCheckState();
        state.setViolationColumn(0);
        state.checkBeforeLog();
    }

    @Test
    public void checkSucceed() {
        final TestableCheckState state = new TestableCheckState();
        state.setViolationColumn(0);
        state.setViolationLine(0);
        state.checkBeforeLog();
    }

    @Test
    public void getViolationColumn() {
        final TestableCheckState state = new TestableCheckState();
        state.setViolationColumn(99);

        assertEquals("column", 99, state.getViolationColumn());
    }

    @Test
    public void getViolationLine() {
        final TestableCheckState state = new TestableCheckState();
        state.setViolationLine(99);

        assertEquals("column", 99, state.getViolationLine());
    }

    public static final class TestableCheckState extends AbstractCheckState {
    }

}
