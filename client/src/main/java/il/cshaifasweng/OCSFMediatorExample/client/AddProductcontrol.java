package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddProductcontrol {

    @FXML // fx:id="nameLabel"
    private Label nameLabel; // Value injected by FXMLLoader

    @FXML // fx:id="priceLabel"
    private Label priceLabel; // Value injected by FXMLLoader

    @FXML // fx:id="kindLabel"
    private Label kindLabel; // Value injected by FXMLLoader

    @FXML // fx:id="nameTF"
    private TextField nameTF; // Value injected by FXMLLoader

    @FXML // fx:id="priceTF"
    private TextField priceTF; // Value injected by FXMLLoader

    @FXML // fx:id="kindTF"
    private TextField kindTF; // Value injected by FXMLLoader

    @FXML // fx:id="addButton"
    private Button addButton; // Value injected by FXMLLoader

    @FXML // fx:id="catalogButton"
    private Button catalogButton; // Value injected by FXMLLoader

    @FXML // fx:id="instructions"
    private Label instructions; // Value injected by FXMLLoader

    @FXML
    void addProduct(ActionEvent event) throws IOException {
        Item itemToAdd = new Item();
        if (nameTF.getText().isEmpty()||priceTF.getText().isEmpty()||kindTF.getText().isEmpty()){
            instructions.setText("there's an empty field");
            return;
        }
        itemToAdd.setName(nameTF.getText());
        itemToAdd.setPrice(Double.parseDouble(priceTF.getText()));
        itemToAdd.setKind(kindTF.getText());
        Message msg = new Message(Message.addProduct,itemToAdd);
        nameTF.clear();
        priceTF.clear();
        kindTF.clear();
        SimpleClient.getClient().sendToServer(msg);
    }

    @FXML
    void goHome(ActionEvent event) {
        Stage stage = (Stage) catalogButton.getScene().getWindow();
        stage.close();
    }

}

