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
    private final File resultFile;
  

    public Testcase(File testcaseFile, File resultFile) {
        this.testcaseFile = testcaseFile;
        this.resultFile = resultFile;
    }

    public void execute() throws Exception {
        TestcaseType given = given();
        when(given);
        then(given);
    }

    private TestcaseType given() throws Exception {
        TestcaseType testcase = load();
        Given given = new Given(testcase);
        given.setup();
        return testcase;
    }
    
    private void when(TestcaseType testcase) {
    }
    
    private void then(TestcaseType testcase) {
        Then then = new Then(testcase);
        String kontoauszug = readKontoauszug();
        try (OutputStream resultOs = new BufferedOutputStream(new FileOutputStream(resultFile))) {   
            then.serializeKontoauszug(kontoauszug, resultOs);
        }
        catch (Exception ex) {
            throw new RuntimeException("Failed to serialize Kontoauszug", ex);
        }
        then.verify(kontoauszug);
    }
    
    private TestcaseType load() throws Exception {
        InputStream is =  new BufferedInputStream(new FileInputStream(testcaseFile));

        Marshalling marshalling = new Marshalling();

        return marshalling.unmarhall(is);
    }

    private String readKontoauszug() {
        
        return "Kontoauszug";
    }
}
