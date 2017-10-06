/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.mab.tc;


import ch.mab.tc.jaxb.InkassoFallType;
import ch.mab.tc.jaxb.TestcaseType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

/**
 *
 * @author mab
 */
public class Marshalling {

    private static JAXBContext context;

    public Marshalling() throws JAXBException {
        if (context == null) {
            context = JAXBContext.newInstance("ch.mab.tc.jaxb");
        }
    }

    public TestcaseType unmarhall(InputStream is) throws JAXBException {

        Unmarshaller unmarshaller = context.createUnmarshaller();

        JAXBElement root = (JAXBElement) unmarshaller.unmarshal(is);

        return (TestcaseType) root.getValue();

    }
}
