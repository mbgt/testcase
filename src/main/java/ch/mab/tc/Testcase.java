/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.mab.tc;

import ch.mab.tc.jaxb.TestcaseType;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author mab
 */
public class Testcase {

    private final File testcaseFile;
    private final File xmlResult;
    private final File htmlResult;
  

    public Testcase(File testcaseFile, File resultFile, File htmlResult) {
        this.testcaseFile = testcaseFile;
        this.xmlResult = resultFile;
        this.htmlResult = htmlResult;
    }

    public void execute() throws Exception {
        Given given = given();
        when(given);
        then(given);
    }

    private Given given() throws Exception {
        TestcaseType testcase = loadTestcase();
        Given given = new Given(testcase);
        given.setup();
        return given;
    }
    
    private void when(Given given) {
       
    }
    
    private void then(Given given) {
        Then then = new Then(given);
        String kontoauszug = readKontoauszug();
        try (OutputStream xmlOutputStream = new BufferedOutputStream(new FileOutputStream(xmlResult));
             OutputStream htmlOutputStream = new BufferedOutputStream(new FileOutputStream(htmlResult))) {   
            then.serializeKontoauszug(kontoauszug, xmlOutputStream);
            then.transformKontoauszug(kontoauszug, htmlOutputStream);
        }
        catch (Exception ex) {
            throw new RuntimeException("Failed to serialize Kontoauszug", ex);
        }
        then.verify(kontoauszug);
    }
    
    private TestcaseType loadTestcase() throws Exception {
        InputStream is =  new BufferedInputStream(new FileInputStream(testcaseFile));

        Marshalling marshalling = new Marshalling();

        return marshalling.unmarhall(is);
    }

    private String readKontoauszug() {
        
        return "Kontoauszug";
    }
}
