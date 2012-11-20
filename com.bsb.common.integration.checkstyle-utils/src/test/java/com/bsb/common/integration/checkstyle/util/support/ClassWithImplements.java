package com.bsb.common.integration.checkstyle.util.support;

import java.util.Iterator;

/**
 * @author Sebastien Gerard
 */
@SuppressWarnings("serial")
public class ClassWithImplements implements Iterable<Object>, java.io.Serializable {

    @Override
    public Iterator<Object> iterator() {
        throw new UnsupportedOperationException();
    }

}
