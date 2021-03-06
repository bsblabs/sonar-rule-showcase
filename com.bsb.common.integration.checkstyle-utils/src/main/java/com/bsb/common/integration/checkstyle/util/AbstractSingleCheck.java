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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;

/**
 * Abstract class providing a initial infrastructure simplifying the usage
 * of the super class. This class is used when only one violation can be
 * thrown per source file.
 *
 * @author Sebastien Gerard
 */
public abstract class AbstractSingleCheck extends Check {

    @Override
    public final void beginTree(DetailAST aRootAST) {
        super.beginTree(aRootAST);

        setSeverity(getLevel().getName());
        createState(aRootAST);
    }


    @Override
    public void finishTree(DetailAST def) {
        super.finishTree(def);
        getState().checkAfterVisit();

        if (!isConstraintSatisfied()) {
            getState().checkBeforeLog();

            log(getState().getViolationLine(), getState().getViolationColumn(), getViolationMessage());
        }
    }

    /**
     * Creates a new state.
     *
     * @param rootAST the root node associated to the current Java source file
     */
    protected abstract void createState(DetailAST rootAST);

    /**
     * Returns the current state of the checkAfterVisit. A new state
     * is created every time {@link #beginTree(com.puppycrawl.tools.checkstyle.api.DetailAST)} is called.
     *
     * @return the current checkAfterVisit state
     */
    protected abstract AbstractCheckState getState();

    /**
     * Checks whether the constraint is satisfied. This method
     * is called when the checkAfterVisit has finished to visit the current
     * Java source file (cf. {@link #finishTree(com.puppycrawl.tools.checkstyle.api.DetailAST)}).
     *
     * @return <tt>true</tt> if the constraint is satisfied, otherwise <tt>false</tt>
     *         and a violation will be created
     */
    protected abstract boolean isConstraintSatisfied();

    /**
     * Returns the message used in the violation.
     *
     * @return the violation message
     */
    protected abstract String getViolationMessage();

    /**
     * Returns the level associated to the checkAfterVisit.
     *
     * @return the level associated to the checkAfterVisit
     */
    protected SeverityLevel getLevel() {
        return SeverityLevel.ERROR;
    }

}
