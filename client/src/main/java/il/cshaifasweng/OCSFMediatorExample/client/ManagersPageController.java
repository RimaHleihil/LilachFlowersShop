package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagersPageController  implements Initializable {

    @FXML private ComboBox<String> AccountTCB;
    @FXML private Button LogInButton;
    @FXML private Label instructions;
    @FXML private TextField nameTF;
    @FXML private Label password;
    @FXML private TextField passwordTF;
    @FXML private Button signUpButton;
    @Subscribe
    public void LogInManagersEvent(LogInManagersEvent event)
    {
        System.out.println("login page managers!we are back!");
        int retval=event.getVal();
        System.out.println(retval);
        if(retval==1)
        {
            System.out.println("log in success!");
            try {
                App.setRoot("ManagersPersonalPage");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else if(retval==0){
            instructions.setText("Can not find any user with this username and password!\n " +
                    "check your username and password or sign in if you do not have an account.");
            nameTF.setText(""); passwordTF.setText("");
        }
        else if(retval==-11){
            instructions.setText("something bad happened!");
            nameTF.setText(""); passwordTF.setText("");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EventBus.getDefault().register(this);
        AccountTCB.getItems().addAll("Chain", "Store");
    }
    @FXML
    void LogIn(ActionEvent event) {
        String type = AccountTCB.getSelectionModel().getSelectedItem();
        if (nameTF.getText().equals("") || passwordTF.getText().equals("") || type.equals("")) {
            instructions.setTextFill(Color.color(0.7, 0, 0));
            instructions.setText("A field or two are empty! please enter your credentials again.");
        } else {
            String[] NamePass={nameTF.getText().toString(),passwordTF.getText().toString()};
            if(type.equals("Chain"))
            {
                Message LogInMSG=new Message(Message.ManagerLogIn_S,NamePass,"Chain");
                SimpleClient.getClient().sendMessageToServer(LogInMSG);
            }
            else {
                Message LogInMSG=new Message(Message.ManagerLogIn_S,NamePass,"Store");
                SimpleClient.getClient().sendMessageToServer(LogInMSG);
            }
        }
    }

    @FXML
    void signUp(ActionEvent event) {
        try {
            App.setRoot("ManagersSignUp");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
