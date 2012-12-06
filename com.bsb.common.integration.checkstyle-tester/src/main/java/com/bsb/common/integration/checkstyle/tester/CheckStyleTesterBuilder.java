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
package com.bsb.common.integration.checkstyle.tester;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;

/**
 * Builder of {@link CheckStyleTesterBuilder}.
 *
 * @author Sebastien Gerard
 */
public final class CheckStyleTesterBuilder {

    /**
     * The system property to be used to locate the root directory containing the <tt>.java</tt> source
     * files to scan. Can also refer to a single <tt>.java</tt> {@link File}.
     */
    public static final String SOURCE_LOCATION = "checkstyle.sourceLocation";

    private final File configurationFile;
    private Locale locale = Locale.ROOT;
    private File sourceLocation;

    /**
     * @see #forConfigFile
     */
    public static CheckStyleTesterBuilder forConfigFile(String configurationFile) {
        final URL resource = CheckStyleTesterBuilder.class.getResource(configurationFile);

        if (resource != null) {
            File file;
            try {
                file = new File(resource.toURI());
            } catch (URISyntaxException e) {
                file = new File(resource.getPath());
            }
            return forConfigFile(file);
        } else {
            return forConfigFile(new File(configurationFile));
        }
    }

    /**
     * Returns a new instance of the builder.
     *
     * @param configurationFile the file containing the rules to be applied
     * @return a new builder instance
     * @throws IllegalArgumentException if the given file does not exist
     */
    public static CheckStyleTesterBuilder forConfigFile(File configurationFile) {
        if (!configurationFile.exists()) {
            throw new IllegalArgumentException("The configuration file [" + configurationFile +
                    "] has not been found");
        }
        return new CheckStyleTesterBuilder(configurationFile);
    }

    /**
     * Creates the builder with the configuration file (mandatory).
     *
     * @param configurationFile the CheckStyle configuration file
     */
    private CheckStyleTesterBuilder(File configurationFile) {
        this.configurationFile = configurationFile;
    }

    /**
     * Specifies the location of the source to be analyzed (mandatory information).
     *
     * @param source the directory containing Java sources files to analyze,
     * or the Java source file itself
     * @return this instance
     */
    public CheckStyleTesterBuilder withSourceLocation(File source) {
        this.sourceLocation = source;
        return this;
    }

    /**
     * Specifies the location of the source to be analyzed. The location of the
     * source code is defined in the system property {@link #SOURCE_LOCATION}.
     *
     * @return this instance
     * @throws IllegalStateException if the system property is not defined, or if the
     * file does not exist
     * @see #withSourceLocation(java.io.File)
     */
    public CheckStyleTesterBuilder withSourceLocationAsProperty() {
        final String location = System.getProperty(SOURCE_LOCATION);

        if (location == null) {
            throw new IllegalStateException("The source location has not been set, " +
                    "please set the system property [" + SOURCE_LOCATION + "]");
        }

        return withSourceLocation(new File(location));
    }

    /**
     * Specifies the locale to be used in the resolution of violation messages.
     * <p/>
     * Be careful, CheckStyle uses a static cache to store messages (see
     * {@link com.puppycrawl.tools.checkstyle.api.LocalizedMessage#BUNDLE_CACHE}).
     * If the same message must be translated in different languages, the class loader
     * must be dropped between tests.
     *
     * @param locale the locale to use
     * @return this instance
     */
    public CheckStyleTesterBuilder withLocale(Locale locale) {
        this.locale = locale;
        return this;
    }

    /**
     * Creates a new tester instance.
     *
     * @return a new tester instance
     */
    public CheckStyleTester build() {
        if (sourceLocation == null) {
            throw new IllegalStateException("The source location has not been specified");
        }

        if (configurationFile == null) {
            throw new IllegalStateException("The configuration file has not been specified");
        }

        return new CheckStyleTester(configurationFile, sourceLocation, locale);
    }

}