package com.bsb.common.integration.checkstyle.util;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * Bunch of methods retrieving information for AST nodes.
 *
 * @author Sebastien Gerard
 */
public final class CheckStyleUtils {

    private CheckStyleUtils() {
    }

    /**
     * Returns the import clause under a String Format.
     *
     * @param importClause the import clause
     * @return the import clause under a String Format.
     * @throws IllegalArgumentException if the given node is not a {@link com.puppycrawl.tools.checkstyle.api
     * .TokenTypes#IMPORT}
     */
    public static String getImport(DetailAST importClause) throws IllegalArgumentException {
        if (importClause.getType() != TokenTypes.IMPORT) {
            throw new IllegalArgumentException("The given node must be an import");
        }

        return FullIdent.createFullIdentBelow(importClause).getText();
    }

    /**
     * Returns all the implements clauses under a String format.
     *
     * @param implementsClause the implements clauses
     * @return all the implements clauses under a String format
     * @throws IllegalArgumentException if the given node is not a {@link com.puppycrawl.tools.checkstyle.api
     * .TokenTypes#IMPLEMENTS_CLAUSE}
     */
    public static List<String> getImplementedClauses(DetailAST implementsClause) throws IllegalArgumentException {
        if (implementsClause.getType() != TokenTypes.IMPLEMENTS_CLAUSE) {
            throw new IllegalArgumentException("The given node must be an implements clause");
        }

        final List<String> implementClauses = new ArrayList<String>();
        DetailAST implementedClass = implementsClause.getFirstChild();

        while (implementedClass != null) {
            // FQN or Non-FQN
            if ((implementedClass.getType() == TokenTypes.IDENT) || (implementedClass.getType() == TokenTypes.DOT)) {
                implementClauses.add(FullIdent.createFullIdent(implementedClass).getText());
            }

            implementedClass = implementedClass.getNextSibling();
        }

        return implementClauses;
    }

}
