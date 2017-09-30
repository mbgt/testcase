/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.mab.tc;

import ch.mab.tc.jaxb.InkassoFallType;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 *
 * @author mab
 */
public class Testcase {

    private String resource;

    public Testcase(String resource) {
        this.resource = resource;
    }

    public void execute() throws Exception {
        setup();
    }

    private InkassoFallType load() throws Exception {
        InputStream is =  new BufferedInputStream(new FileInputStream(resource));

        Marshalling marshalling = new Marshalling();

        return marshalling.unmarhall(is);
    }

    private void setup() throws Exception {
        load();
    }

}
