package ch.mab.tc;

import ch.mab.tc.jaxb.InstitutionType;
import ch.mab.tc.jaxb.KategorieType;
import ch.mab.tc.jaxb.KontoauszugType;
import ch.mab.tc.jaxb.ObjectFactory;
import ch.mab.tc.jaxb.PositionType;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mab on 08.10.17.
 */
public class Then {

    private Testcase testcase;
    private final ObjectFactory factory = new ObjectFactory();

    public Then(Testcase testcase) {
        this.testcase = testcase;
    }

    public void verify() {
        // load kontoauszug
        // save kontoauszug in serialised format
        // compare expected kontoauszug with real kontoauszug
        ObjectFactory factory = new ObjectFactory();
        KontoauszugType kontoauszug = factory.createKontoauszugType();
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
}
