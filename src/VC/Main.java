
package VC;

import CRUD.DBConnection;
import CRUD.Query;
import Model.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Jaime Gladish BSITSW
 *    
 * Usernames and passwords are:
 *    User1 password1
 *    User2 password2
 * 
 */

public class Main extends Application {

   public static ObservableList<Country> countries = FXCollections.observableArrayList();
   public static ObservableList<City> cities = FXCollections.observableArrayList();
   public static ObservableList<Address> addresses = FXCollections.observableArrayList();
   public static ObservableList<Customer> customers = FXCollections.observableArrayList();
   public static ObservableList<Appointment> appointments = FXCollections.observableArrayList();
   public static ObservableList<User> users = FXCollections.observableArrayList();
   
   public static Locale loc = Locale.getDefault();


   public static void fillUsers() throws SQLException {

      users.clear();

      Query.makeQuery("SELECT userId, userName, password, active, "
              + "createDate, createBy, lastUpdate, lastUpdatedBy FROM user");
      ResultSet result = Query.getResult();

      while (result.next()) {
         Integer id = result.getInt("userId");
         String userName = result.getString("userName");
         String password = result.getString("password");
         Integer active = result.getInt("active");

         String fixTime = result.getString("createDate");
         fixTime = fixTime.replace(" ", "T");
         LocalDateTime created1 = LocalDateTime.parse(fixTime);
         ZonedDateTime created = created1.atZone(ZoneId.of("UTC"));

         String createdBy = result.getString("createBy");

         fixTime = result.getString("lastUpdate");
         fixTime = fixTime.replace(" ", "T");
         LocalDateTime lastUpdate1 = LocalDateTime.parse(fixTime);
         ZonedDateTime lastUpdate = lastUpdate1.atZone(ZoneId.of("UTC"));

         String lastUpdateBy = result.getString("lastUpdatedBy");

         users.add(new User(id, userName, password, active,
                 created, createdBy,
                 lastUpdate, lastUpdateBy));
      }
   }

   public static void fillAppointments() throws SQLException {

      appointments.clear();
      ZoneId zid = ZoneId.of("UTC");

      Query.makeQuery("SELECT appointmentId, customerId, title, description, "
              + "location, contact, url, start, end, "
              + "createDate, createdBy, lastUpdate, lastUpdateBy FROM appointment");
      ResultSet result = Query.getResult();

      while (result.next()) {
         Integer id = result.getInt("appointmentId");
         Integer custID = result.getInt("customerId");
         String title = result.getString("title");
         String description = result.getString("description");
         String location = result.getString("location");
         String contact = result.getString("contact");
         String url = result.getString("url");

         String fixTime = result.getString("createDate");
         fixTime = fixTime.replace(" ", "T");
         LocalDateTime created1 = LocalDateTime.parse(fixTime);
         ZonedDateTime created = created1.atZone(ZoneId.systemDefault());

         String createdBy = result.getString("createdBy");

         fixTime = result.getString("lastUpdate");
         fixTime = fixTime.replace(" ", "T");
         LocalDateTime lastUpdate1 = LocalDateTime.parse(fixTime);
         ZonedDateTime lastUpdate = lastUpdate1.atZone(ZoneId.systemDefault());

         fixTime = result.getString("start");
         fixTime = fixTime.replace(" ", "T");
         LocalDateTime start01 = LocalDateTime.parse(fixTime);
         ZonedDateTime start02 = start01.atZone(zid);
         String start = start02.withZoneSameInstant(ZoneId.systemDefault()).toString();

         fixTime = result.getString("end");
         fixTime = fixTime.replace(" ", "T");
         LocalDateTime end01 = LocalDateTime.parse(fixTime);
         ZonedDateTime end02 = end01.atZone(zid);
         String end = end02.withZoneSameInstant(ZoneId.systemDefault()).toString();

         String lastUpdateBy = result.getString("lastUpdateBy");

         appointments.add(new Appointment(id, custID, title, description, location,
                 contact, url, start, end,
                 created, createdBy,
                 lastUpdate, lastUpdateBy));
      }
   }

   public static void fillCustomers() throws SQLException {
      customers.clear();

      Query.makeQuery("SELECT customerId, customerName, addressId, active, "
              + "createDate, createdBy, lastUpdate, lastUpdateBy FROM customer");
      ResultSet result = Query.getResult();

      while (result.next()) {
         Integer id = result.getInt("customerId");
         String name = result.getString("customerName");
         Integer addressID = result.getInt("addressId");
         Integer active = result.getInt("active");

         String fixTime = result.getString("createDate");
         fixTime = fixTime.replace(" ", "T");
         LocalDateTime created1 = LocalDateTime.parse(fixTime);
         ZonedDateTime created = created1.atZone(ZoneId.of("UTC"));

         String createdBy = result.getString("createdBy");

         fixTime = result.getString("lastUpdate");
         fixTime = fixTime.replace(" ", "T");
         LocalDateTime lastUpdate1 = LocalDateTime.parse(fixTime);
         ZonedDateTime lastUpdate = lastUpdate1.atZone(ZoneId.of("UTC"));

         String lastUpdateBy = result.getString("lastUpdateBy");

         customers.add(new Customer(id, name, addressID, active,
                 created, createdBy,
                 lastUpdate, lastUpdateBy));
      }
   }

   public static void fillAddresses() throws SQLException {
      addresses.clear();

      Query.makeQuery("SELECT addressId, address, address2, cityId, postalCode, phone, "
              + "createDate, createdBy, lastUpdate, lastUpdateBy FROM address");
      ResultSet result = Query.getResult();

      while (result.next()) {
         Integer id = result.getInt("addressId");
         String address = result.getString("address");
         String address2 = result.getString("address2");
         Integer cityID = result.getInt("cityId");
         String zipcode = result.getString("postalCode");
         String phone = result.getString("phone");

         String fixTime = result.getString("createDate");
         fixTime = fixTime.replace(" ", "T");
         LocalDateTime created1 = LocalDateTime.parse(fixTime);
         ZonedDateTime created = created1.atZone(ZoneId.of("UTC"));

         String createdBy = result.getString("createdBy");

         fixTime = result.getString("lastUpdate");
         fixTime = fixTime.replace(" ", "T");
         LocalDateTime lastUpdate1 = LocalDateTime.parse(fixTime);
         ZonedDateTime lastUpdate = lastUpdate1.atZone(ZoneId.of("UTC"));

         String lastUpdateBy = result.getString("lastUpdateBy");

         addresses.add(new Address(id, address, address2, cityID, zipcode, phone,
                 created, createdBy,
                 lastUpdate, lastUpdateBy));
      }
   }

   public static void fillCities() throws SQLException {
      cities.clear();

      Query.makeQuery("SELECT cityId, city, countryId, createDate,"
              + "createdBy, lastUpdate, lastUpdateBy FROM city");
      ResultSet result = Query.getResult();

      while (result.next()) {
         Integer id = result.getInt("cityId");
         String city = result.getString("city");
         Integer countryID = result.getInt("countryId");

         String fixTime = result.getString("createDate");
         fixTime = fixTime.replace(" ", "T");
         LocalDateTime created1 = LocalDateTime.parse(fixTime);
         ZonedDateTime created = created1.atZone(ZoneId.of("UTC"));

         String createdBy = result.getString("createdBy");

         fixTime = result.getString("lastUpdate");
         fixTime = fixTime.replace(" ", "T");
         LocalDateTime lastUpdate1 = LocalDateTime.parse(fixTime);
         ZonedDateTime lastUpdate = lastUpdate1.atZone(ZoneId.of("UTC"));

         String lastUpdateBy = result.getString("lastUpdateBy");

         cities.add(new City(id, city, countryID, created, createdBy,
                 lastUpdate, lastUpdateBy));
      }
   }

   public static void fillCountries() throws SQLException {
      countries.clear();

      Query.makeQuery("SELECT countryId, country, createDate, "
              + "createdBy, lastUpdate, lastUpdateBy FROM country");
      ResultSet result = Query.getResult();

      while (result.next()) {
         Integer id = result.getInt("countryId");
         String country = result.getString("country");

         String fixTime = result.getString("createDate");
         fixTime = fixTime.replace(" ", "T");
         LocalDateTime created1 = LocalDateTime.parse(fixTime);
         ZonedDateTime created = created1.atZone(ZoneId.of("UTC"));

         String createdBy = result.getString("createdBy");

         fixTime = result.getString("lastUpdate");
         fixTime = fixTime.replace(" ", "T");
         LocalDateTime lastUpdate1 = LocalDateTime.parse(fixTime);
         ZonedDateTime lastUpdate = lastUpdate1.atZone(ZoneId.of("UTC"));

         String lastUpdateBy = result.getString("lastUpdateBy");

         countries.add(new Country(id, country, created, createdBy,
                 lastUpdate, lastUpdateBy));
      }
   }

   /**
    * @param args the command line arguments
    * @throws java.sql.SQLException
    * @throws java.lang.ClassNotFoundException
    */
   public static void main(String[] args) throws
           SQLException, ClassNotFoundException, Exception {

      //Locale.setDefault(new Locale("es"));
      //TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
      
      DBConnection.makeConnection();

      fillCountries();
      fillCities();
      fillAddresses();
      fillCustomers();
      fillAppointments();
      fillUsers();

      launch(args);

      DBConnection.closeConnection();
   }
   
   @Override
   public void start(Stage stage) throws Exception {
      Parent root = FXMLLoader.load(getClass().getResource("/VC/Login.fxml"));
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
   }
}
