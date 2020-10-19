/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import VC.Main;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author G
 */
public class User extends Stamp {
   public User(Integer id, String userName, 
           String password, int active,
           ZonedDateTime createDate, String createdBy, 
           ZonedDateTime lastUpdate, String lastUpdateBy) {
      setUserID(id);
      setUserName(userName);
      setPassword(password);
      setActive(active);
      setCreateDate(createDate);
      setCreatedBy(createdBy);
      setLastUpdate(lastUpdate);
      setLastUpdateBy(lastUpdateBy);
   }
   
//   private int currentUser;
   private IntegerProperty userID = new SimpleIntegerProperty();
   private StringProperty userName = new SimpleStringProperty();
   private StringProperty password = new SimpleStringProperty();
   private IntegerProperty active = new SimpleIntegerProperty();
   
   public static User getUserObj(Integer id) {
      for(User user : VC.Main.users) {
         if(Objects.equals(user.getID(), id)) return user;
      }
      return null;
   }
   
   public static Map<String, Integer> getMap() {
      Map<String, Integer> appt = new HashMap<>();
      for(User c : Main.users) {
         appt.put(c.getUserName(), c.getID());
      }
      return appt;
   }
   
   public IntegerProperty userIDProperty() {
      return userID;
   }
   public final void setUserID(int userID) {
      this.userIDProperty().set(userID);
   }
   public int getID() {
      return userIDProperty().get();
   }
   
   public StringProperty userNameProperty() {
      return userName;
   }
   public final void setUserName(String userName) {
      this.userNameProperty().set(userName);
   }
   public String getUserName() {
      return userNameProperty().get();
   }
   
   public StringProperty passwordProperty() {
      return password;
   }
   public final void setPassword(String password) {
      this.passwordProperty().set(password);
   }
   public String getPassword() {
      return passwordProperty().get();
   }
   public IntegerProperty activeProperty() {
      return active;
   }
   public final void setActive(int active) {
      this.activeProperty().set(active);
   }
   public int getAcive() {
      return activeProperty().get();
   }
}

