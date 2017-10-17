package ch.mab.tc;

import ch.mab.tc.jaxb.*;

import javax.xml.bind.JAXBElement;
import java.io.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Created by mab on 08.10.17.
 */
public class Then {

    private final Given given;
    private final ObjectFactory factory = new ObjectFactory();
    
    private static final Transform transformer;
    
    static {
        InputStream stylesheet = Then.class.getResourceAsStream("/xslt/testcase.xsl");
        transformer = new Transform(stylesheet);
    }


    Then(Given given) {
        this.given = given;
    }

    public void verify(String kontoauszug) {
        // load kontoauszug
        // save kontoauszug in serialised format
        // compare expected kontoauszug with real kontoauszug
    }
    
    private JAXBElement<KontoauszugType> jaxbKontoauszug() {
        KontoauszugType kontoauszug = factory.createKontoauszugType();
        
        List<String> belege = Collections.singletonList("belege");
        
        List<PositionType> positionen = belege.stream().map(this::jaxbPosition)  //
                .collect(Collectors.toList());

        kontoauszug.setBelegart("zahlung");
        kontoauszug.getPosition().addAll(positionen);
        
        return factory.createKontoauszug(kontoauszug);
    }
    
    private PositionType jaxbPosition(String position) {
        
        PositionType jaxbPosition = factory.createPositionType();
        InstitutionType institution = factory.createInstitutionType();
        KategorieType kategorie = factory.createKategorieType();
        
        institution.setArt("gemeinde");
        institution.setNummer(1234);
        
        kategorie.setType("einkommenssteuer");
        
        jaxbPosition.setKategorie(kategorie);
        jaxbPosition.setInstitution(institution);
        jaxbPosition.setBetrag(new BigDecimal(100));
        
        return jaxbPosition;
    }

    void serializeKontoauszug(String kontoauszug, OutputStream resultOs) throws Exception {
        
        JAXBElement<KontoauszugType> jaxbKontoauszug = jaxbKontoauszug();
        
        Marshalling marshalling = new Marshalling();
        
        marshalling.marshall(jaxbKontoauszug, resultOs);
    }
    
    void transformKontoauszug(File testcaseFile, String kontoauszug, OutputStream resultOs) throws Exception {
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        serializeKontoauszug(kontoauszug, byteOutput);
        
        ByteArrayInputStream byteInput = new ByteArrayInputStream(byteOutput.toByteArray());
        Properties properties = new Properties();
        properties.put("testcase", testcaseFile.getPath());
        transformer.transform(byteInput, properties, resultOs);
    }
}
