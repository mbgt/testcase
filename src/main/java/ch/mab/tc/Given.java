/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.mab.tc;

import ch.mab.tc.jaxb.FakturaType;
import ch.mab.tc.jaxb.InkassoFallType;
import ch.mab.tc.jaxb.TestcaseType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author mab
 */
public class Given {
    
    private static final Logger LOG = LogManager.getLogger(Given.class);
    
    private TestcaseType testcase;
    
    public Given(TestcaseType testcase) {
        this.testcase = testcase;
    }
    
    public void setup() {
        LOG.info("create testcase");
       
        testcase.getInkassoFall().forEach(this::createFall);
    }
    
    private void createFall(InkassoFallType inkassoFall) {
        LOG.info(String.format("Inkassofall %s", inkassoFall.getId()));
         
        inkassoFall.getFaktura().forEach(this::createFaktura);
    }
    
    private void createFaktura(FakturaType faktura) {
        LOG.info(String.format("Faktura %s", faktura.getId()));
    }
}
