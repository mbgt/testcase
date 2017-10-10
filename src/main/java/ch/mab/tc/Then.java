package ch.mab.tc;

import ch.mab.tc.jaxb.InstitutionType;
import ch.mab.tc.jaxb.KategorieType;
import ch.mab.tc.jaxb.KontoauszugType;
import ch.mab.tc.jaxb.ObjectFactory;
import ch.mab.tc.jaxb.PositionType;
import ch.mab.tc.jaxb.TestcaseType;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mab on 08.10.17.
 */
public class Then {

    private final Given given;
    private final ObjectFactory factory = new ObjectFactory();


    Then(Given given) {
        this.given = given;
    }

    public void verify(String kontoauszug) {
        // load kontoauszug
        // save kontoauszug in serialised format
        // compare expected kontoauszug with real kontoauszug
    }
    
    private KontoauszugType transformKontoauszug() {
        KontoauszugType kontoauszug = factory.createKontoauszugType();
        
        List<String> belege = Collections.EMPTY_LIST;
        
        List<PositionType> positionen = belege.stream().map(this::transformPosition)  //
                .collect(Collectors.toList());
        kontoauszug.getPosition().addAll(positionen);
        
        return kontoauszug;
    }
    
    private PositionType transformPosition(String positionen) {
        
        PositionType position = factory.createPositionType();
        InstitutionType institution = factory.createInstitutionType();
        KategorieType kategorie = factory.createKategorieType();
        
        institution.setArt(null);
        institution.setNummer(0);
        
        kategorie.setType(null);
        kategorie.setSubtype(null);
        
        position.setKategorie(kategorie);
        position.setInstitution(institution);
        position.setBetrag(BigDecimal.ZERO);
        
        return position;
    }

    void serializeKontoauszug(String kontoauszug, OutputStream resultOs) throws IOException {
        resultOs.write("Kontoauszug".getBytes());
    }
}
