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

    private File testcaseFile;
    private File resultFile;
    
    private TestcaseType testcase;

    public Testcase(File gwtFile, File resultFile) {
        this.testcaseFile = gwtFile;
        this.resultFile = resultFile;
    }

    public void execute() throws Exception {
        testcase = load();
        setup();
        
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

    private void setup() throws Exception {
        testcase = load();
        
        Given given = new Given(testcase);
        
        given.setup();
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
