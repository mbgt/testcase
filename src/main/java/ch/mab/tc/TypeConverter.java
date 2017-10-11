/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.mab.tc;

import java.time.Year;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author mab
 */
public class TypeConverter {
    
    public static String printYear(Year year) {
        return DatatypeConverter.printInt(year.getValue());
    }
    
    public static Year parseYear(String year) {
        return Year.parse(year);
    }
}
