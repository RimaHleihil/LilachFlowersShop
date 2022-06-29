/**
 * Sample Skeleton for 'cata.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import org.greenrobot.eventbus.EventBus;

public class catacontrol implements Initializable {

    @FXML private Button ManagmentB;
    @FXML private Button complainButton;
    @FXML private Button compResponeButton;
    @FXML private Button ShowButton;
    @FXML private Label welcomeText;
    @FXML private Button signInButton;
    @FXML private TextField usernameTF;
    @FXML private TextField passwordTF;
    @FXML private Button signIpButton;
    @FXML private Label instructions;


    @FXML
    void ShowCatalogue(ActionEvent event) {
        try {
            Message msg=new Message(Message.getAllItems);
            SimpleClient.getClient().sendToServer(msg);
            System.out.println("message sent to server to get all products ");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Subscribe
    public void LogInEvent(LogInEvent event)
    {
        System.out.println("login page!we are back!");
        int retval=event.getVal();
        if(retval==1)
        {
            System.out.println("log in success!");
            try {
                App.setRoot("UserPersonalP");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else if(retval==0){
            instructions.setText("Can not find any user with this username and password!\n " +
                    "check your username and password or sign in if you do not have an account.");
            usernameTF.setText(""); passwordTF.setText("");

        }
        else if(retval==-11){
            instructions.setText("something bad happened!");
            usernameTF.setText(""); passwordTF.setText("");

        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        EventBus.getDefault().register(this);
    }
    @FXML
    void signIn(ActionEvent event) {
        if (usernameTF.getText().equals("") || passwordTF.getText().equals("")) {
            instructions.setTextFill(Color.color(0.7, 0, 0));
            instructions.setText("A field or two are empty! please enter your credentials again.");
        } else {
            String[] NamePass={usernameTF.getText().toString(),passwordTF.getText().toString()};
            Message LogInMSG=new Message(Message.LoggingIn_S,NamePass);
            SimpleClient.getClient().sendMessageToServer(LogInMSG);

        }

    }

    @FXML
    void signUp(ActionEvent event) {
        try {
            App.setRoot("signUpPage");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    void ManagementPage(ActionEvent event) {
        try {
            App.setRoot("ManagersPage");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
