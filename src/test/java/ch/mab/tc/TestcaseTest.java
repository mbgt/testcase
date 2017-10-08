package ch.mab.tc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static java.util.stream.Collectors.toList;


/**
 * @author mab
 */
@RunWith(Parameterized.class)
public class TestcaseTest {

    @Parameterized.Parameters(name="{0}")
    public static Collection<Object[]> loadTests() {

        URL rootUrl = Testcase.class.getResource("/testcase");

        File[] files = new File(rootUrl.getPath()).listFiles();

        return files !=null ? Arrays.stream(files) //
                .filter(file->file.getName().endsWith(".xml")) //
                .map(file -> new Object[]{file.getName(), file}) //
                .collect(toList()) //
                : Collections.emptyList();
    }

    @Parameterized.Parameter(0)
    public String name;

    @Parameterized.Parameter(1)
    public File resource;

    @Test
    public void load() throws Exception {
        System.out.println(name);

        Testcase testcase = new Testcase(resource);
        testcase.execute();
    }
}
