/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VC;

import Model.Appointment;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author G
 */
public class TypeByMonthController implements Initializable {
   
   private ObservableList<Appointment> appts = Main.appointments;
   private ObservableList<String> appointmentList = FXCollections.observableArrayList();

    @FXML
    private ListView<String> typeListView;

   @FXML void backButtonAction(ActionEvent event) { 

      try {
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(AppointmentsController.class.getResource("/VC/Appointments.fxml"));
         AnchorPane ap = (AnchorPane) loader.load();

         Stage cStage = new Stage();
         cStage.setTitle("Customer Information");
         Scene scene = new Scene(ap);
         scene.getStylesheets().add("Model/calendar.css");

         cStage.hide();
         cStage.setScene(scene);
         
         Stage stage = (Stage) typeListView.getScene().getWindow();
         stage.close();
         
         cStage.show();
      } catch (Exception e) {
         System.out.println(e.getMessage());
      }
   }

    // Fill ListView with:  Month, Year  Types of appointment: 2
    private void fillListView() {
       Set monthYear = new HashSet<>();
       Set type = new HashSet<>();
       
       for(Appointment a : appts) {
          ZonedDateTime zdt = ZonedDateTime.parse(a.getStart());
          String toMY = zdt.format(AppointmentsController.my);
          monthYear.add(toMY);
       }
       
       monthYear.forEach(a -> {
         Main.appointments.forEach(b -> {
            ZonedDateTime zdt = ZonedDateTime.parse(b.getStart());
            String toMY = zdt.format(AppointmentsController.my);
            if (a.equals(toMY)) {
               type.add(b.getType());
            }
            });
           int typeCount = type.size();
           appointmentList.add(a + "  Types of appointment: " + type.size());
           type.clear();
         });
       typeListView.setItems(appointmentList);
    }
   /**
    * Initializes the controller class.
    */
   @Override
   public void initialize(URL url, ResourceBundle rb) {
      fillListView();
   }   
   
}
