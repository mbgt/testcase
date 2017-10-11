package ch.mab.tc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;

import static java.util.stream.Collectors.toList;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 * @author mab
 */
@RunWith(Parameterized.class)
public class TestcaseTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm:ss");

    private static final String RESULT_DIR = "esrZahlung" + FORMATTER.format(LocalDateTime.now());

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> loadTests() {

        URL rootUrl = Testcase.class.getResource("/testcase");

        File[] files = new File(rootUrl.getPath()).listFiles();

        return files != null ? Arrays.stream(files) //
                .filter(file -> file.getName().endsWith(".xml")) //
                .map(file -> new Object[]{file.getName(), file}) //
                .collect(toList()) //
                : Collections.emptyList();
    }

    @Parameterized.Parameter(0)
    public String name;

    @Parameterized.Parameter(1)
    public File testcaseFile;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void execute() throws Exception {
        System.out.println(name);

        File xmlFile = tempFolder.newFile("kontoauszug.xml");
        File htmlFile = tempFolder.newFile("kontoauszug.html");
        Testcase testcase = new Testcase(testcaseFile, xmlFile, htmlFile);
        testcase.execute();

        copyTmpFiles(tempFolder);
    }

    private void copyTmpFiles(TemporaryFolder tempFolder) {

        File tempDir = tempFolder.getRoot();
        File parentDir = tempDir.getParentFile();

        File resultDir = new File(parentDir, RESULT_DIR);

        Consumer<File> copyFile = file -> {
            try {
                Files.copy(file.toPath(), new File(resultDir, file.getName()).toPath());
            } catch (IOException ex) {
                throw new RuntimeException("Failed to copy result file " + file.getPath(), ex);
            }
        };

        if (resultDir.exists() || resultDir.mkdir()) {
            Arrays.stream(tempDir.listFiles()).forEach(copyFile);
        }
    }
    
}
