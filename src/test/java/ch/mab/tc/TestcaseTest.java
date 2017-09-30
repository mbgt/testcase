package ch.mab.tc;

import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.InputStream;


/**
 * Created by mab on 30.09.17.
 */
public class TestcaseTest {

    @Test
    public void load() throws JAXBException {

        InputStream is = getClass().getResourceAsStream("/esrzahlung.xml");

        Marshalling marshalling = new Marshalling();

        Object unmarhall = marshalling.unmarhall(is);

        Assert.assertNotNull(unmarhall);
    }
}
