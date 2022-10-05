/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package congestioncontrol;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 *
 * @author TONNY KAMAU MWANGI
 */
public class MainController implements Initializable {
    
    @FXML
    private Button btnprint;

    @FXML
    private ComboBox<Integer> hourofday;

    @FXML
    private TextField numberplate;

    @FXML
    private ComboBox<Integer> dayofweek;

    @FXML
    private TextField hourstostay;

    @FXML
    private ComboBox<Integer> pwb;
    @FXML
    private Label confirmation;
     @FXML
    private Button btnconfirm;

    @FXML
    private Label showplate;

    @FXML
    private Label showpwb;

    @FXML
    private Label showhourstay;

    @FXML
    private Label showhour;

    @FXML
    private Label showday;
     @FXML
    private TextArea showhistory;

    
    ObservableList<Integer> options = FXCollections.observableArrayList( 6,7, 8, 9, 16, 17, 18,19 );
    ObservableList<Integer>list = FXCollections.observableArrayList(1,2,3,4,5,6,7);
    ObservableList<Integer> lists = FXCollections.observableArrayList(1,0);
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        if(event.getSource()== btnprint){
        main();
        
        }
        else if(event.getSource()==btnconfirm){
        getValues();
        
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hourofday.setItems(options);
        dayofweek.setItems(list);
        pwb.setItems(lists);
        hourofday.getSelectionModel().selectFirst();
        dayofweek.getSelectionModel().selectFirst();
        pwb.getSelectionModel().selectFirst();
     
        
    }   
        int dayhours,day,stay,PWB;
        String platenumber;
    public void getValues(){
        try{
        dayhours=hourofday.getValue();
        platenumber= numberplate.getText();
        day=dayofweek.getValue();
        stay=Integer.parseInt(hourstostay.getText());
        PWB=pwb.getValue();
         clearFields();
         
         if(platenumber.trim().equals("")){
              setLError(Color.TOMATO,"Please Check Your Plate Number!!");
            }else{
              if(stay > 0 && stay < 24){
                showplate.setText(platenumber);
                showhour.setText(""+dayhours);
                showday.setText(""+day);
                showhourstay.setText(""+stay);
                showpwb.setText(""+PWB);
            } else{
             setLError(Color.TOMATO,"maximum hours are 24 !! minimum is 1hour !!");
            }
         }
        }catch(Exception e){
             setLError(Color.TOMATO,"Check and Confirm Your Inputs");         
        }
           
    }
     private void setLError(Color color, String text) {
       confirmation.setTextFill(color);
        confirmation.setText(text);
        System.out.println(text);
    }
     private void clearFields() {
        numberplate.clear();
        hourstostay.clear();
        confirmation.setText("");
        showhistory.appendText(" ");
        
        
    }
    
    int total;
    public void main(){
        int first3hours,nexthours;
        
    if(PWB == 1){
    total=0;
    }else{
        if (stay>3){
            first3hours=((stay-3)*300);
            nexthours=(3*150);
            
            total=first3hours+nexthours+200;
            }else{
            total = stay*150+200;
                    }
                }
    System.out.println("-------KCCA Congestion Fee Ticket-------------");
    System.out.println("Car plate: " + platenumber);
    System.out.println("Number of Hours: "+ stay);
    System.out.println("Congestion Fee: "+ total);
    System.out.println("Tip: Travelling with a friend saves money & reduces traffic");
    System.out.println(" ") ;
    System.out.println("----------------------------------TICKET HISTORY-------------------------") ;
             setLError(Color.GREEN,"Printing Reciept");
             printTicket();
             printTicketHistory();        
    } 
    public void printTicket() {
     String ticket="-------KCCA Congestion Fee Ticket-------------\n"
            + "Car plate: " + platenumber+ "\n"
            + "Number of Hours: "+ stay+ "\n"
            + "Congestion Fee: "+ total+ "\n"
            + "Tip: Travelling with a friend saves money & reduces traffic\n"; 
    try (FileWriter fw = new FileWriter("Tickets.txt", true);
      BufferedWriter bw = new BufferedWriter(fw)) {
      bw.write(ticket);
      bw.newLine();
  }catch (IOException e) {
      setLError(Color.TOMATO,"An Error occurred");
      e.printStackTrace();
    }}
    public void printTicketHistory(){
     try (BufferedReader br = new BufferedReader(new FileReader("Tickets.txt"))) {
   String strCurrentLine;
   while ((strCurrentLine = br.readLine()) != null) {
    System.out.println(strCurrentLine);
    showhistory.appendText(strCurrentLine);
    showhistory.appendText("\n");
   }
  } catch (IOException e) {
   setLError(Color.TOMATO,"An Error occurred");   
   e.printStackTrace();
  }
    }
    }
    


