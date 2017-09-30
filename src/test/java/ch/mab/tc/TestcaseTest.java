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
 * Created by mab on 30.09.17.
 */
@RunWith(Parameterized.class)
public class TestcaseTest {

    @Parameterized.Parameters(name="{0}")
    public static Collection<String[]> loadTests() {

        URL rootUrl = Testcase.class.getResource("/");

        File[] files = new File(rootUrl.getPath()).listFiles();

        return files !=null ?Arrays.stream(files) //
                .map(File::getPath) //
                .filter(s->s.endsWith(".xml")) //
                .map(s -> new String[]{s.substring(s.lastIndexOf('/')+1), s}) //
                .collect(toList()) //
                : Collections.emptyList();
    }

    @Parameterized.Parameter(0)
    public String name;

    @Parameterized.Parameter(1)
    public String resource;

    @Test
    public void load() throws Exception {

        Testcase testcase = new Testcase(resource);
        testcase.execute();
    }
}
