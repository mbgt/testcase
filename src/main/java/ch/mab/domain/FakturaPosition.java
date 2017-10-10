/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.mab.domain;

import java.math.BigDecimal;

/**
 *
 * @author mab
 */
public class FakturaPosition {
    private String katType;
    private String instArt;
    private int instNr;
    private BigDecimal betrag;

    public FakturaPosition(String katType, String instArt, int instNr, BigDecimal betrag) {
        this.katType = katType;
        this.instArt = instArt;
        this.instNr = instNr;
        this.betrag = betrag;
    }

    public String getKatType() {
        return katType;
    }

    public String getInstArt() {
        return instArt;
    }

    public int getInstNr() {
        return instNr;
    }

    public BigDecimal getBetrag() {
        return betrag;
    }
}
