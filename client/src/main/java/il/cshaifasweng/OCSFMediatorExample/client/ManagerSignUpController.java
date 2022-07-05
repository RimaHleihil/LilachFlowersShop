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

public class ManagerSignUpController implements Initializable{

    @FXML private ComboBox<String> AccountTCB;
    @FXML private TextField idTF;
    @FXML private Label instructions;
    @FXML private TextField nameTF;
    @FXML private Label password;
    @FXML private TextField passwordTF;
    @FXML private Button signUpButton;
    @Subscribe
    public void SignUpManagersEvent(SignUpManagersEvent event)
    {
        System.out.println("signup page MANAGERS!we are back!");
        int retval=event.getVal();
        System.out.println(retval);
        if(retval==1)
        {
            try {
                App.setRoot("ManagersPage");
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
            System.out.println("--------------------11111");
            instructions.setText("something bad happened!");
            nameTF.setText(""); passwordTF.setText(""); idTF.setText("");
            instructions.setText("something bad happened!");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EventBus.getDefault().register(this);
        AccountTCB.getItems().addAll("Chain", "Store");
    }
    @FXML
    void signUp(ActionEvent event) {
        String type = AccountTCB.getSelectionModel().getSelectedItem();
        if (nameTF.getText().equals("") || passwordTF.getText().equals("") || idTF.getText().equals("")
                || type==null) {
            instructions.setTextFill(Color.color(0.7, 0, 0));
            instructions.setText("there is an empty field please, fill out all required information.");
        }
        else {
            if(type.equals("Chain"))
            {
                ChainManager chainManager=new ChainManager(idTF.getText(),nameTF.getText(),passwordTF.getText());
                Message SignUpMSG=new Message(Message.ManagerSignUp_S,chainManager,"Chain");
                SimpleClient.getClient().sendMessageToServer(SignUpMSG);
            }
            else {
                StoreManager storeManager=new StoreManager(idTF.getText(),nameTF.getText(),passwordTF.getText());
                Message SignUpMSG=new Message(Message.ManagerSignUp_S,storeManager,"Store");
                SimpleClient.getClient().sendMessageToServer(SignUpMSG);
            }

        }

    }

}
