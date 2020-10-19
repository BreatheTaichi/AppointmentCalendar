/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import VC.AppointmentsController;
import VC.Main;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.TimeZone;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author G
 */
public class Appointment extends Stamp {

   public static boolean apptCollision(ZonedDateTime zStart, ZonedDateTime zEnd) {
      LocalDateTime start = zStart.toLocalDateTime();
      LocalDateTime end = zEnd.toLocalDateTime();
      // Can't use lambda, won't return from the loop
      for(Appointment a : VC.Main.appointments) {
         if(Objects.equals(a.getID(), AppointmentsController.activeAppt.getID())) {
            continue;
         }

         ZonedDateTime dateEnd = ZonedDateTime.parse(a.getEnd());
         LocalDateTime thisStart = a.getLocalDateTime();
         LocalDateTime thisEnd = dateEnd.toLocalDateTime();
         LocalDateTime s1, s2, e1, e2;
         
         // Find earlier start date then: if (s1 <= e2) && (s2 <= e1) true is overlap
         if(start.isBefore(thisStart)) {
            s1 = start;
            s2 = thisStart;
            e1 = end;
            e2 = thisEnd;
         } else {
            s1 = thisStart;
            s2 = start;
            e1 = thisEnd;
            e2 = end;
         }
         if(thisStart.format(AppointmentsController.yearMonthDay)
                 .equals(start.format(AppointmentsController.yearMonthDay))) {
            if((s1.isBefore(e2)) && (s2.isBefore(e1))) {
                  return true;
            }
         }
      }
      return false;
   }

   public static boolean apptCollisionNew(ZonedDateTime zStart, ZonedDateTime zEnd) {
      LocalDateTime start = zStart.toLocalDateTime();
      LocalDateTime end = zEnd.toLocalDateTime();
      // Can't use lambda, won't return from the loop
      for(Appointment a : VC.Main.appointments) {
//         if(Objects.equals(a.getID(), AppointmentsController.activeAppt.getID())) {
//            continue;
//         }

         ZonedDateTime dateEnd = ZonedDateTime.parse(a.getEnd());
         LocalDateTime thisStart = a.getLocalDateTime();
         LocalDateTime thisEnd = dateEnd.toLocalDateTime();
         LocalDateTime s1, s2, e1, e2;
         
         // Find earlier start date then: if (s1 <= e2) && (s2 <= e1) true is overlap
         if(start.isBefore(thisStart)) {
            s1 = start;
            s2 = thisStart;
            e1 = end;
            e2 = thisEnd;
         } else {
            s1 = thisStart;
            s2 = start;
            e1 = thisEnd;
            e2 = end;
         }
         if(thisStart.format(AppointmentsController.yearMonthDay)
                 .equals(start.format(AppointmentsController.yearMonthDay))) {
            if((s1.isBefore(e2)) && (s2.isBefore(e1))) {
                  return true;
            }
         }
      }
      return false;
   }

   public static boolean startIsBeforeEnd(ZonedDateTime zStart, ZonedDateTime zEnd) {
      return zStart.isAfter(zEnd);
   }

   public static Appointment getAppointmentObj(Integer id) {
      for (Appointment appt : VC.Main.appointments) {
         if (appt.getID().equals(id)) {
            return appt;
         }
      }
      return null;
   }
   
   public static ObservableList<String> getList(Integer id) {
      ObservableList<String> app = FXCollections.observableArrayList();
      id = id - 1;
      for(Appointment a : Main.appointments) {
         if (a.getUserID().equals(id)) {
            ZonedDateTime apTime = ZonedDateTime.parse(a.getStart());
            String apString = apTime.format(VC.AppointmentsController.ymdhm);
            String cust = Customer.getCustomerObj(a.getCustomerID()).getCustomerName();
            String custApString = cust + "  " + apString;
            app.add(custApString);
         }
      }
      return app;
   }

   private final IntegerProperty appointmentID = new SimpleIntegerProperty();
   private final IntegerProperty customerID = new SimpleIntegerProperty();
   private final IntegerProperty userID = new SimpleIntegerProperty();
   private final StringProperty title = new SimpleStringProperty();
   private final StringProperty description = new SimpleStringProperty();
   private final StringProperty location = new SimpleStringProperty();
   private final StringProperty contact = new SimpleStringProperty();
   private final StringProperty type = new SimpleStringProperty();
   private final StringProperty url = new SimpleStringProperty();
   private final StringProperty start = new SimpleStringProperty();
   private final StringProperty end = new SimpleStringProperty();

   public Appointment(Integer id, Integer customerID,
           String type, String description,
           String location, String contact, String url,
           String start, String end,
           ZonedDateTime createDate, String createdBy,
           ZonedDateTime lastUpdate, String lastUpdateBy) {

      setAppointmentID(id);
      setCustomerID(customerID);
//      setUserID(userID);
//      setTitle(title);
      setDescription(description);
      setLocation(location);
      setContact(contact);
      setType(type);
      setURL("N/A");
      setStart(start);
      setEnd(end);
      setCreateDate(createDate);
      setCreatedBy(createdBy);
      setLastUpdate(lastUpdate);
      setLastUpdateBy(lastUpdateBy);
   }

   public LocalDate getLocalDate() {
      ZonedDateTime date = ZonedDateTime.parse(this.getStart());
      return date.toLocalDate();
   }

   public LocalDateTime getLocalDateTime() {
      ZonedDateTime date = ZonedDateTime.parse(this.getStart());
      return date.toLocalDateTime();
   }

   @Override public int hashCode() {
      return appointmentID.hashCode();
   }

   @Override public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      }
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      final Appointment other = (Appointment) obj;
      return Objects.equals(this.appointmentID, other.appointmentID);
   }

   public IntegerProperty appointmentIDProperty() {
      return appointmentID;
   }

   public final void setAppointmentID(int appointmentID) {
      appointmentIDProperty().set(appointmentID);
   }

   public Integer getID() {
      return appointmentIDProperty().get();
   }

   public IntegerProperty customerIDProperty() {
      return customerID;
   }

   public final void setCustomerID(int customerID) {
      customerIDProperty().set(customerID);
   }

   public Integer getCustomerID() {
      return customerIDProperty().get();
   }

   public IntegerProperty userIDProperty() {
      return userID;
   }

   public final void setUserID(Integer userID) {
      userIDProperty().set(userID);
   }

   public Integer getUserID() {
      return userIDProperty().get();
   }

   public StringProperty titleProperty() {
      return title;
   }

   public final void setTitle(String title) {
      titleProperty().set(title);
   }

   public String getTitle() {
      return titleProperty().get();
   }

   public StringProperty descriptionProperty() {
      return description;
   }

   public final void setDescription(String description) {
      descriptionProperty().set(description);
   }

   public String getDescription() {
      return descriptionProperty().get();
   }

   public StringProperty locationProperty() {
      return location;
   }

   public final void setLocation(String location) {
      locationProperty().set(location);
   }

   public String getLocation() {
      return locationProperty().get();
   }

   public StringProperty contactProperty() {
      return contact;
   }

   public final void setContact(String contact) {
      contactProperty().set(contact);
   }

   public String getContact() {
      return contactProperty().get();
   }

   public StringProperty typeProperty() {
      return type;
   }

   public final void setType(String type) {
      typeProperty().set(type);
   }

   public String getType() {
      return typeProperty().get();
   }

   public StringProperty urlProperty() {
      return url;
   }

   public final void setURL(String url) {
      urlProperty().set(url);
   }

   public String getURL() {
      return urlProperty().get();
   }

   public StringProperty startProperty() {
      return start;
   }

   public final void setStart(String start) {
      ZoneId zid = ZoneId.of("UTC");
      ZonedDateTime toLocTZ = ZonedDateTime.parse(start)
              .withZoneSameInstant(zid);
      start = toLocTZ.toString();
      startProperty().set(start);
   }

   public final void setStart(ZonedDateTime start) {
      ZoneId zid = ZoneId.of("UTC");
      ZonedDateTime toLocTZ = start
              .withZoneSameInstant(zid);
      start = toLocTZ;
      startProperty().set(start.toString());
   }

   public String getStart() {
      ZoneId zid = TimeZone.getDefault().toZoneId();
      ZonedDateTime toLocTZ = ZonedDateTime.parse(startProperty().get())
           .withZoneSameInstant(zid);
      return toLocTZ.toString();
   }

   public String getStartYMDHM() {
      ZonedDateTime thisDay = ZonedDateTime.parse(getStart());
      return thisDay.format(VC.AppointmentsController.ymdhm);
   }

   public String getStartYMD() {
      ZonedDateTime thisDay = ZonedDateTime.parse(getStart());
      return thisDay.format(VC.AppointmentsController.yearMonthDay);
   }

   public String getStartMY() {
      ZonedDateTime thisDay = ZonedDateTime.parse(getStart());
      return thisDay.format(VC.AppointmentsController.my);
   }

   public StringProperty endProperty() {
      return end;
   }

   public final void setEnd(String end) {
      ZoneId zid = ZoneId.of("UTC");
      ZonedDateTime toLocTZ = ZonedDateTime.parse(end)
              .withZoneSameInstant(zid);
      end = toLocTZ.toString();
      endProperty().set(end);
   }

   public final void setEnd(ZonedDateTime end) {
      ZoneId zid = ZoneId.of("UTC");
      ZonedDateTime toLocTZ = end.withZoneSameInstant(zid);
      end = toLocTZ;
      endProperty().set(end.toString());
   }

   public String getEnd() {
      ZoneId zid = TimeZone.getDefault().toZoneId();
      ZonedDateTime toLocTZ = ZonedDateTime.parse(endProperty().get())
           .withZoneSameInstant(zid);
      return toLocTZ.toString();
   }

   public String getEndYMDHM() {
      ZonedDateTime thisDay = ZonedDateTime.parse(getEnd());
      return thisDay.format(VC.AppointmentsController.ymdhm);
   }

   public String getEndYMD() {
      ZonedDateTime thisDay = ZonedDateTime.parse(getEnd());
      return thisDay.format(VC.AppointmentsController.yearMonthDay);
   }
}
