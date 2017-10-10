/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.mab.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mab
 */
public class Faktura {
    private final String id;
    private final String belegart;
    private final LocalDate valuta;
    private final List<FakturaPosition> positionen;

    public Faktura(String id, String belegart, LocalDate valuta) {
        this.id = id;
        this.belegart = belegart;
        this.valuta = valuta;
        this.positionen = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getBelegart() {
        return belegart;
    }

    public LocalDate getValuta() {
        return valuta;
    }

    public List<FakturaPosition> getPositionen() {
        return positionen;
    }
}
