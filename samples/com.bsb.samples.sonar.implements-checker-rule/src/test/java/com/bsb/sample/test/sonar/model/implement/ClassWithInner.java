package com.bsb.sample.test.sonar.model.implement;

import java.io.Closeable;
import java.io.Externalizable;
import java.io.File;
import java.io.FilenameFilter;
import java.io.Flushable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamConstants;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.nio.CharBuffer;
import java.util.Iterator;

/**
 * @author Sebastien Gerard
 */
public class ClassWithInner {

    public class InnerClass implements Serializable, Iterable<Object>, Comparable<Object>,
        Cloneable, Readable, Annotation, Closeable, Externalizable, Flushable, FilenameFilter,
        ObjectStreamConstants {

        @Override
        public void close() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int compareTo(Object o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Iterator<Object> iterator() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int read(CharBuffer cb) throws IOException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void flush() throws IOException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean accept(File dir, String name) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            throw new UnsupportedOperationException();
        }

    }

}
