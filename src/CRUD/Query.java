/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUD;

import java.sql.ResultSet;
import java.sql.Statement;
import static CRUD.DBConnection.conn;
/**
 *
 * @author G
 */
public class Query {
   private static Statement stmt;
   private static ResultSet result;
   
   public static void makeQuery(String q) {
      try {
         stmt = conn.createStatement();
         if(q.toLowerCase().startsWith("select")) result = stmt.executeQuery(q);
         if(q.toLowerCase().startsWith("delete") 
                 || q.toLowerCase().startsWith("update")
                 || q.toLowerCase().startsWith("insert")) stmt.executeUpdate(q); 
      } catch(Exception e) {
         System.out.println("Error: " + e.getMessage());
      }
   }
   
   public static ResultSet getResult() {
      return result;
   }
}
