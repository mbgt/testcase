package ch.mab.tc;

import ch.mab.tc.jaxb.InstitutionType;
import ch.mab.tc.jaxb.KategorieType;
import ch.mab.tc.jaxb.KontoauszugType;
import ch.mab.tc.jaxb.ObjectFactory;
import ch.mab.tc.jaxb.PositionType;
import ch.mab.tc.jaxb.TestcaseType;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.transform.stream.StreamSource;

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
    
    private KontoauszugType jaxbKontoauszug() {
        KontoauszugType kontoauszug = factory.createKontoauszugType();
        
        List<String> belege = Collections.singletonList("belege");
        
        List<PositionType> positionen = belege.stream().map(this::jaxbPosition)  //
                .collect(Collectors.toList());
        kontoauszug.getPosition().addAll(positionen);
        
        return kontoauszug;
    }
    
    private PositionType jaxbPosition(String position) {
        
        PositionType jaxbPosition = factory.createPositionType();
        InstitutionType institution = factory.createInstitutionType();
        KategorieType kategorie = factory.createKategorieType();
        
        institution.setArt("gemeinde");
        institution.setNummer(10);
        
        kategorie.setType("einnkommen");
        kategorie.setSubtype("angestellt");
        
        jaxbPosition.setKategorie(kategorie);
        jaxbPosition.setInstitution(institution);
        jaxbPosition.setBetrag(new BigDecimal(100));
        
        return jaxbPosition;
    }

    void serializeKontoauszug(String kontoauszug, OutputStream resultOs) throws Exception {
        
        KontoauszugType jaxbKontoauszug = jaxbKontoauszug();
        
        Marshalling marshalling = new Marshalling();
        
        marshalling.marshall(jaxbKontoauszug, resultOs);
    }
    
    void transformKontoauszug(String kontoauszug, OutputStream resultOs) throws Exception {
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        serializeKontoauszug(kontoauszug, byteOutput);
        
        ByteArrayInputStream byteInput = new ByteArrayInputStream(byteOutput.toByteArray());
        transformer.transform(byteInput, resultOs);
    }
}
