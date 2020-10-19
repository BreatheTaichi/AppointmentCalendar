/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VC;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.nio.file.Files;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author G
 */
public class Login implements Initializable {

   public static Integer currentUserID;
   public static String currentUser;
   
   ResourceBundle res = ResourceBundle.getBundle("lib/Login", Main.loc);
   Set<String> keyRes = res.keySet();
   String login = res.getString("login");
   String username = res.getString("user");
   String password = res.getString("pass");
   String error = res.getString("error");
   File loginStamp = new File("loginStamp.txt");
   
   
   @FXML private Label errorLabel;
   @FXML private Label usernameLabel;
   @FXML private Label passwordLabel;
   @FXML private Button loginButton;

   @FXML
   private TextField usernameField;

   @FXML
   private TextField passwordField;
   
   @FXML
   private void handleLoginButtonAction(ActionEvent event) throws IOException, Exception {
      
      // Lambda to go through each user and check username and password.  More 
      // compact, easier to read.
      VC.Main.users.forEach(u -> {
         if(u.getUserName().equals(usernameField.getText()) 
                 && u.getPassword().equals(passwordField.getText())) {
            currentUserID = u.getID();
            currentUser = u.getUserName();
            String stamp = (currentUser + "  " + ZonedDateTime.now() + "\n");
            
            try {
               writeFile(stamp, loginStamp);
            } catch (Exception e) {}
            
            try {
               int width = 1262;
               int height = 1004;
               FXMLLoader loader = new FXMLLoader(getClass()
                       .getResource("/VC/Appointments.fxml"));
               Stage stage = (Stage)errorLabel.getScene().getWindow();
               stage.setTitle("Calendar");

               Rectangle2D userScreen = Screen.getPrimary().getVisualBounds();
               stage.setX((userScreen.getWidth() - width) / 2);
               stage.setY((userScreen.getHeight() - height) / 2);

               Scene scene = new Scene(loader.load());
               scene.getStylesheets().add("Model/calendar.css");

               stage.setScene(scene);
            } catch (IOException e) {
               e.printStackTrace();
               System.out.println(e.getMessage());
            }
         } else {
            errorLabel.setText(error);
         }
      });
   }
   
   public static void writeFile(String data, File destination) throws IOException {
      try (
         BufferedWriter writer = new BufferedWriter(new FileWriter(destination, true))) {
            writer.write(data);
            writer.newLine();
      }
   }
   
   @Override
   public void initialize(URL url, ResourceBundle rb) {
      
      // Localize Login label and button text
      usernameLabel.setText(username);
      passwordLabel.setText(password);
      loginButton.setText(login);
      
      
   }  
}
