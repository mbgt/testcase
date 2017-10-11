/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.mab.tc;

import ch.mab.domain.Faktura;
import ch.mab.domain.FakturaPosition;
import ch.mab.domain.InkassoFall;
import ch.mab.tc.jaxb.FakturaType;
import ch.mab.tc.jaxb.InkassoFallType;
import ch.mab.tc.jaxb.PositionType;
import ch.mab.tc.jaxb.TestcaseType;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author mab
 */
public class Given {

    private static final Logger LOG = LogManager.getLogger(Given.class);

    private final TestcaseType testcase;

    private List<InkassoFall> faelle;

    public Given(TestcaseType testcase) {
        this.testcase = testcase;
    }

    public TestcaseType getTestcase() {
        return testcase;
    }

    public void setup() {
        LOG.info("create testcase");

        faelle = testcase.getInkassoFall().stream()
                .map(this::createFall)
                .collect(Collectors.toList());
    }

    public List<InkassoFall> getFaelle() {
        return faelle;
    }

    private InkassoFall createFall(InkassoFallType jaxbInkassoFall) {
        LOG.info(String.format("Inkassofall %s", jaxbInkassoFall.getId()));

        InkassoFall inkassoFall = new InkassoFall(jaxbInkassoFall.getId(), jaxbInkassoFall.getForderungsart().value(), jaxbInkassoFall.getForderungjahr().getValue());

        List<Faktura> faktura = jaxbInkassoFall.getFaktura().stream()
                .map(this::createFaktura)
                .collect(Collectors.toList());
        inkassoFall.getFaktura().addAll(faktura);
        return inkassoFall;
    }

    private Faktura createFaktura(FakturaType jaxbFaktura) {
        LOG.info(String.format("Faktura %s", jaxbFaktura.getId()));

        XMLGregorianCalendar valuta = jaxbFaktura.getValuta();
        LocalDate localValuta = LocalDate.of(valuta.getYear(), valuta.getMonth(), valuta.getDay());
        Faktura faktura = new Faktura(jaxbFaktura.getId(), jaxbFaktura.getBelegart() != null ? jaxbFaktura.getBelegart().value() : "", localValuta);

        List<FakturaPosition> fakturaPositionen = jaxbFaktura.getPosition().stream() //
                .map(this::createPosition) //
                .collect(Collectors.toList());
        faktura.getPositionen().addAll(fakturaPositionen);
        return faktura;
    }

    private FakturaPosition createPosition(PositionType position) {
        return new FakturaPosition(position.getKategorie().getType(),
                position.getInstitution().getArt(),
                position.getInstitution().getNummer() != null ? position.getInstitution().getNummer() : 0,
                position.getBetrag());
    }
}
