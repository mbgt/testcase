/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.mab.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mab
 */
public class InkassoFall {
    private final String id;
    private final String forderungsart;
    private final int forderungjahr;
    private final List<Faktura> faktura;

    public InkassoFall(String id, String forderungsart, int forderungjahr) {
        this.id = id;
        this.forderungsart = forderungsart;
        this.forderungjahr = forderungjahr;
        this.faktura = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getForderungsart() {
        return forderungsart;
    }

    public int getForderungjahr() {
        return forderungjahr;
    }

    public List<Faktura> getFaktura() {
        return faktura;
    }
}
