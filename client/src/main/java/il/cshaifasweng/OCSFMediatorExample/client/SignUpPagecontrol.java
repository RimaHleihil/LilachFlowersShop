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
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import org.greenrobot.eventbus.EventBus;

public class SignUpPagecontrol  implements Initializable {
    @FXML private ComboBox<String> AccountTCB;
    @FXML private TextField passwordTF;
    @FXML private TextField nameTF;
    @FXML private TextField idTF;
    @FXML private Button signUpButton;
    @FXML private Label instructions;
    @FXML private TextField CardNumTF;
    @FXML private TextField cvvTF;

    @Subscribe
    public void SignUpEvent(SignUpEvent event)
    {
        System.out.println("signup page!we are back!");
        int retval=event.getVal();
        if(retval==1)
        {
            try {
                App.setRoot("cata");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else if(retval==0){
            instructions.setText("exist");
            nameTF.setText(""); passwordTF.setText(""); idTF.setText("");
            instructions.setText("exist");
        }
        else if(retval==-1){
            instructions.setText("something bad happened!");
            nameTF.setText(""); passwordTF.setText(""); idTF.setText("");
            instructions.setText("something bad happened!");
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AccountTCB.getItems().addAll("First","Second","Third");
        EventBus.getDefault().register(this);
    }

    @FXML
    void signUp(ActionEvent event) throws InterruptedException {
        String type = AccountTCB.getSelectionModel().getSelectedItem();
            if (nameTF.getText().equals("") || passwordTF.getText().equals("") || idTF.getText().equals("")
                    || type==null || CardNumTF.getText().equals("") || cvvTF.getText().equals("")) {
                instructions.setTextFill(Color.color(0.7, 0, 0));
                instructions.setText("there is an empty field please, fill out all required information.");
            }
            else {
                String[] NamePass={idTF.getText(),nameTF.getText(),passwordTF.getText(),
                        type,CardNumTF.getText(),cvvTF.getText()};
                Message SignUpMSG=new Message(Message.SignUp_S,NamePass);
                SimpleClient.getClient().sendMessageToServer(SignUpMSG);
                //TimeUnit.SECONDS.sleep(4);
            }
    }
}

