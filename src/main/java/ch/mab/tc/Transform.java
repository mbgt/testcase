/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.mab.tc;

import java.io.InputStream;
import java.io.OutputStream;
import java.time.Year;
import java.util.Optional;
import java.util.function.Supplier;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author mab
 */
public class Transform {

    private static final TransformerFactory TRANSFORMER_FACTORY = TransformerFactory.newInstance();

    private final InputStream stylesheet;
    private Optional<Transformer> transformer = Optional.empty();

    public Transform(InputStream stylesheet) {
        this.stylesheet = stylesheet;
    }

    synchronized public void transform(InputStream source, OutputStream result) {
        StreamSource xmlSource = new StreamSource(source);
        StreamResult streamResult = new StreamResult(result);
        try {
            transformer().transform(xmlSource, streamResult);
        } catch (TransformerException ex) {
            throw new RuntimeException("Failed to transform", ex);
        }
    }
 
    
   private Transformer transformer() {
        Supplier<Transformer> newTransformer = () -> {
            try {
                StreamSource styleSourcce = new StreamSource(stylesheet);
                return TRANSFORMER_FACTORY.newTransformer(styleSourcce);
            } catch (TransformerConfigurationException ex) {
                throw new RuntimeException("Failed to load stylesheet", ex);
            }
        };
        
        return transformer.orElseGet(newTransformer);
    }
}
