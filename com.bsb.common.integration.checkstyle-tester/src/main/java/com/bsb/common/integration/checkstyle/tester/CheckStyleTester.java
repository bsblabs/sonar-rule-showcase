package com.bsb.common.integration.checkstyle.tester;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.util.Collections.*;
import static org.apache.commons.io.FileUtils.*;

/**
 * Service performing a CheckStyle analysis in a directory and helping to
 * perform assertions on the results.
 *
 * @author Sebastien Gerard
 */
public class CheckStyleTester {

    private final Map<File, ClassCheckResult> results = new HashMap<File, ClassCheckResult>();
    private final List<File> files;

    /**
     * Creates the tester with all the needed information.
     *
     * @param configurationFile the file containing the rules to be applied
     * @param file the directory containing Java sources files to analyze,
     * or the Java source file itself
     * @param locale the locale used to resolve messages
     */
    public CheckStyleTester(File configurationFile, File file, Locale locale) {
        try {
            final Checker checker = new Checker();

            final ClassLoader moduleClassLoader = CheckStyleTester.class.getClassLoader();
            checker.setModuleClassLoader(moduleClassLoader);

            checker.setLocaleCountry(locale.getCountry());
            checker.setLocaleLanguage(locale.getLanguage());
            checker.configure(getConfiguration(configurationFile));

            checker.addListener(new AuditListenerAsserter());
            files = getFilesToScan(file);
            checker.process(files);
        } catch (CheckstyleException e) {
            throw new IllegalStateException("Error while initializing the tester", e);
        }
    }

    /**
     * Returns the result of the analysis performed on the source code
     * of the given class.
     *
     * @param clazz the given class
     * @return the analysis result
     * @throws IllegalArgumentException if the source code of the given class has not been analyzed
     */
    public ClassCheckResult get(Class<?> clazz) throws IllegalArgumentException {
        return getResult(getSourceFile(clazz));
    }

    /**
     * Asserts that no exception has been thrown during the analysis.
     */
    public CheckStyleTester assertNoException() {
        for (Map.Entry<File, ClassCheckResult> entry : results.entrySet()) {
            entry.getValue().assertNoException();
        }

        return this;
    }

    /**
     * Returns the result of the analysis performed on the source code
     * referred by the given event.
     *
     * @param event the given event
     * @return the analysis result
     */
    private ClassCheckResult getResult(AuditEvent event) {
        return getResult(new File(event.getFileName()));
    }

    /**
     * Returns the result of the analysis performed on the source code
     * of the given file.
     *
     * @param analyzedFile the given file
     * @return the analysis result
     * @throws IllegalStateException if the given file has not been initialized
     * and <tt>failIfNotFound</tt> is <tt>true</tt>
     */
    private ClassCheckResult getResult(File analyzedFile) {
        ClassCheckResult asserter = results.get(analyzedFile);

        if (asserter == null) {
            asserter = new ClassCheckResult();
            results.put(analyzedFile, asserter);
        }

        return asserter;
    }

    /**
     * Returns the source code file of the given class.
     *
     * @param clazz the given class
     * @return the source code file
     * @throws IllegalArgumentException if the source code of the given class has not been analyzed
     */
    private File getSourceFile(Class<?> clazz) throws IllegalArgumentException {
        final String classFile = clazz.getName().replace(".", File.separator) + ".java";

        for (File file : files) {
            if (file.getPath().contains(classFile)) {
                return file;
            }
        }

        throw new IllegalArgumentException("The class [" + clazz + "] has not been analysed");
    }

    /**
     * Returns the files to scan. If the given file is a directory, all the files
     * contained in this directory are returned (recursive), otherwise the file
     * itself is returned.
     *
     * @param file the given file
     * @return the files to scan
     * @throws IllegalArgumentException if the given file does not exist,
     * or it's not a Java source file
     */
    @SuppressWarnings("unchecked")
    private List<File> getFilesToScan(File file) throws IllegalArgumentException {
        final List<File> filesToScan;
        if (file.isDirectory()) {
            filesToScan = new ArrayList<File>(listFiles(file, new String[]{"java"}, true));
        } else if (file.exists()) {
            if (!file.getName().endsWith(".java")) {
                throw new IllegalArgumentException("The file [" + file + "] is not a Java source file.");
            }

            filesToScan = singletonList(file);
        } else {
            throw new IllegalArgumentException("The file [" + file + "] does not exist.");
        }
        return filesToScan;
    }

    /**
     * Returns the CheckStyle configuration based on the given
     * configuration file.
     *
     * @param configurationFile the given configuration file
     * @return the configuration object based on the given configuration file
     * @throws CheckstyleException if the configuration cannot be loaded
     */
    private static Configuration getConfiguration(File configurationFile) throws CheckstyleException {
        try {
            return ConfigurationLoader.loadConfiguration(
                    new InputSource(new FileInputStream(configurationFile)),
                    new PropertiesExpander(System.getProperties()), false);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Error while loading the configuration file [" +
                    configurationFile + "]", e);
        }
    }

    /**
     * The listener helps to record information about the analysis.
     */
    private class AuditListenerAsserter implements AuditListener {

        @Override
        public void auditStarted(AuditEvent auditEvent) {
        }

        @Override
        public void auditFinished(AuditEvent auditEvent) {
        }

        @Override
        public void fileStarted(AuditEvent auditEvent) {
            getResult(auditEvent);
        }

        @Override
        public void fileFinished(AuditEvent auditEvent) {
        }

        @Override
        public void addError(AuditEvent auditEvent) {
            getResult(auditEvent).addError(auditEvent);
        }

        @Override
        public void addException(AuditEvent auditEvent, Throwable throwable) {
            getResult(auditEvent).addError(auditEvent);
        }
    }

}