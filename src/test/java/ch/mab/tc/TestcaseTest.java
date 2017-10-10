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

import static java.util.stream.Collectors.toList;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 * @author mab
 */
@RunWith(Parameterized.class)
public class TestcaseTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd-hh:mm:ss");

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

        File xmlFile = tempFolder.newFile("actualKontoauszug.xml");
        File htmlFile = tempFolder.newFile("actualKontoauszug.html");
        Testcase testcase = new Testcase(testcaseFile, xmlFile, htmlFile);
        testcase.execute();

        copyTempDir(tempFolder);
    }

    private void copyTempDir(TemporaryFolder tempFolder) {

        File dir = tempFolder.getRoot();
        File parentDir = dir.getParentFile();

        String dirName = "esrZahlung" + FORMATTER.format(LocalDateTime.now());

        File newDir = new File(parentDir, dirName);

        if (newDir.mkdir()) {
            Arrays.stream(dir.listFiles())
                    .forEach((File file) -> {
                        try {
                            Files.copy(file.toPath(), new File(newDir, file.getName()).toPath());
                        } catch (IOException ex) {
                           throw new RuntimeException("Failed to store result files", ex);
                        }
                    });
        }
    }
}
