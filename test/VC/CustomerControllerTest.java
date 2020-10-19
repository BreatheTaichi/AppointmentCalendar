/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VC;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 *
 * @author G
 */
@RunWith(Parameterized.class)
public class CustomerControllerTest {

   @Parameters
   public static Collection<Object[]> data() {
      Object[][] data = new Object[][] { {"1", "a"},
                                         {"a", "1"},  
                                         {"a", "a"} };
      return Arrays.asList(data);
   }
   private String m1;
   private String m2;
   
   public CustomerControllerTest(String p1, String p2) {
      m1 = p1;
      m2 = p2;
      System.out.println("m1: " + m1 
              + "  m2: " + m2);
   }
   
   @Test
   public void testCheckZipPhone() {
      CustomerController instance = new CustomerController();
      
      Boolean expResult = true;
      Boolean result = instance.checkZipPhone(m1, m2);
      assertEquals(expResult, result);
   }   
}
