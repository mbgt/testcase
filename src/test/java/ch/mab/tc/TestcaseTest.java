package ch.mab.tc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;


/**
 * Created by mab on 30.09.17.
 */
@RunWith(Parameterized.class)
public class TestcaseTest {

    @Parameterized.Parameters(name="{0}")
    public static Collection<String> loadTests() {

        URL rootUrl = Testcase.class.getResource("/");

        File[] files = new File(rootUrl.getPath()).listFiles();

        return files !=null ?Arrays.stream(files) //
                .map(File::getPath) //
                .filter(s->s.endsWith(".xml")) //
                .collect(Collectors.toList()): Collections.emptyList();
    }

    @Parameterized.Parameter
    public String resource;

    @Test
    public void load() throws Exception {

        Testcase testcase = new Testcase(resource);
        testcase.execute();
    }
}
