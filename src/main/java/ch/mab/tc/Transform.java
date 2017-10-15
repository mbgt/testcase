/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.mab.tc;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Supplier;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

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

    synchronized public void transform(InputStream source, Properties properties, OutputStream result) {
        StreamSource xmlSource = new StreamSource(source);
        StreamResult streamResult = new StreamResult(result);
        Transformer tf = transformer();
        try {
            tf.setOutputProperty(OutputKeys.METHOD, "html");
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            properties.forEach((key, value) -> tf.setParameter(key.toString(), value));
            tf.transform(xmlSource, streamResult);
            
        } catch (TransformerException ex) {
            throw new RuntimeException("Failed to transform", ex);
        }
    }
 
    
   private Transformer transformer() {
        Supplier<Transformer> newTransformer = () -> {
            try  {
                StreamSource styleSourcce = new StreamSource(stylesheet);
                return TRANSFORMER_FACTORY.newTransformer(styleSourcce);
            } catch (TransformerConfigurationException ex) {
                throw new RuntimeException("Failed to load stylesheet", ex);
            }
        };
        
        if (!transformer.isPresent()) {
            transformer = Optional.of(newTransformer.get());
        }
        return transformer.get();
    }
}
