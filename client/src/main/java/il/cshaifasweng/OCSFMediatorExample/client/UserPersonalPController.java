package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class UserPersonalPController {

    @FXML
    private Button CancelHistoryB;

    @FXML
    private Button OrderButton;

    @FXML
    private Button OrdersHistoryB;

    @FXML
    private Label instructions;

    @FXML
    private Button CatalogB;

    @FXML
    private Button HomeB;


    @FXML
    void GoHome(ActionEvent event) {
        try {
            App.setRoot("cata");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void GoToCatalog(ActionEvent event) {
        try {
            Message msg=new Message(Message.getAllItems);
            SimpleClient.getClient().sendToServer(msg);
            System.out.println("message sent to server to get all products ");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    void CancelHistoryPage(ActionEvent event) {
        try {
            App.setRoot("CancelHistoryList");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void GoToOrderPage(ActionEvent event) {
        try {
            Client c = SimpleClient.getClient().getUser();
            System.out.println(c.getUsername());
            Message msg=new Message(Message.ItemListForC_S, SimpleClient.getClient().getUser());
            SimpleClient.getClient().sendToServer(msg);
            System.out.println("message sent to server to get all items in basket ");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            App.setRoot("Order");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void OrdersHistoryPage(ActionEvent event) {
        try {
            App.setRoot("OrderHistoryList");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
