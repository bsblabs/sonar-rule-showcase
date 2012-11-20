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
