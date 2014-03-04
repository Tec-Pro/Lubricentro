/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication8;

import java.math.BigDecimal;

/**
 *
 * @author jacinto
 */
public class JavaApplication8 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        BigDecimal bd = new BigDecimal("-1");
        System.out.println(bd.toString());
        System.out.println(bd.signum());
        bd = bd.negate();
        System.out.println(bd.toString() + " positivo");
        bd =  bd.negate();
        System.out.println(bd.toString()+ " negativo");
    }
}
