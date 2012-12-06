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

import com.bsb.common.integration.checkstyle.tester.CheckStyleTesterBuilder;
import com.bsb.common.integration.checkstyle.util.support.ClassWithImplements;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import static com.puppycrawl.tools.checkstyle.api.TokenTypes.*;
import static java.util.Arrays.*;
import static junit.framework.Assert.*;

/**
 * @author Sebastien Gerard
 */
public class CheckStyleUtilsTest {

    @Test
    public void getImplementedClauses() {
        final DetailAST rootAST = getRootNode(getFile(ClassWithImplements.class));

        final DetailAST classDef = findFirstSibling(rootAST, CLASS_DEF);
        final List<String> clauses = CheckStyleUtils.getImplementedClauses(classDef.findFirstToken(IMPLEMENTS_CLAUSE));

        assertEquals("implements clauses",
            asList(Iterable.class.getSimpleName(), Serializable.class.getName()), clauses);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getImplementedClausesNotImplementsClause() {
        final DetailAST rootAST = getRootNode(getFile(ClassWithImplements.class));

        final List<String> clauses = CheckStyleUtils.getImplementedClauses(findFirstSibling(rootAST, CLASS_DEF));

        assertEquals("implements clauses",
            asList(Iterable.class.getSimpleName(), Serializable.class.getName()), clauses);
    }

    @Test
    public void getImport() {
        final DetailAST rootAST = getRootNode(getFile(ClassWithImplements.class));

        final String clauseString = CheckStyleUtils.getImport(findFirstSibling(rootAST, IMPORT));

        assertEquals("import clause", Iterator.class.getName(), clauseString);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getImportNotImportClause() {
        final DetailAST rootAST = getRootNode(getFile(ClassWithImplements.class));

        final String clauseString = CheckStyleUtils.getImport(findFirstSibling(rootAST, CLASS_DEF));

        assertEquals("import clause", Iterator.class.getName(), clauseString);
    }

    protected String getFile(Class<?> clazz) {
        final String base = System.getProperty(CheckStyleTesterBuilder.SOURCE_LOCATION);

        if (base == null) {
            throw new IllegalStateException("The system property [" +
                CheckStyleTesterBuilder.SOURCE_LOCATION + "] is not defined");
        }

        return base + "/" + clazz.getSimpleName() + ".java";
    }

    protected DetailAST getRootNode(String sourceFile) throws IllegalArgumentException, IllegalStateException {
        final File file = new File(sourceFile);
        try {
            final FileText fileText = new FileText(file, "UTF-8");

            final FileText text = FileText.fromLines(file, fileText);
            final FileContents contents = new FileContents(text);

            return TreeWalker.parse(contents);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot read the file [" + sourceFile + "]", e);
        } catch (Exception e) {
            throw new IllegalStateException("Error while creating the root AST node from file [" + sourceFile + "]", e);
        }
    }

    protected DetailAST findFirstSibling(DetailAST node, int tokenType) {
        DetailAST currentSibling = node;

        do {
            if (currentSibling.getType() == tokenType) {
                return currentSibling;
            }

            currentSibling = currentSibling.getNextSibling();
        } while (currentSibling != null);

        throw new IllegalStateException("Cannot find the first sibling having the type [" + tokenType + "]");
    }

}
