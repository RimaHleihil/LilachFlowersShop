/**
 * Sample Skeleton for 'Catalog.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class Catalogcontrol implements Initializable {
    double percentage=1;
    ObservableList<Item> data = FXCollections.observableArrayList();
    @FXML //fx:id="CataButton"
    private Button CataButton;

    @FXML
    private Button Add2Pascket;

    @FXML //fx:id="editButton"
    private Button editButton;

    @FXML // fx:id="addButton"
    private Button addButton; // Value injected by FXMLLoader

    @FXML // fx:id="deleteButton"
    private Button deleteButton; // Value injected by FXMLLoader

    @FXML // fx:id="discountButton"
    private Button discountButton; // Value injected by FXMLLoader

    @FXML // fx:id="cancelDiscountButton"
    private Button cancelDiscountButton; // Value injected by FXMLLoader


    @FXML
    private Button homebutton;

    @FXML
    private TableView<Item> ProductTable;

    @FXML
    private TableColumn<Item, String> imageCol;

    @FXML
    private TableColumn<Item, String> productName;

    @FXML
    private TableColumn<Item, String> productKind;

    @FXML
    private TableColumn<Item, Double> productPrice;

    @FXML
    private Button showProductDetails;

    public static Item selectedItem=new Item();
    public static Item itemByPercentage=new Item();

    @FXML
    void addDiscount(ActionEvent event) {
        Button applyDiscount = new Button("Apply");

        Label discountLabel = new Label("Discount Percentage");
        TextField discountTF = new TextField("0");
        discountTF.setMaxWidth(75);
        StackPane discountLayOut = new StackPane();
        StackPane.setAlignment(discountLabel, Pos.CENTER_LEFT);
        StackPane.setAlignment(discountTF, Pos.CENTER_RIGHT);
        StackPane.setAlignment(applyDiscount, Pos.BOTTOM_CENTER);
        discountLayOut.getChildren().addAll(discountLabel, discountTF,applyDiscount);


        Scene discountScene = new Scene(discountLayOut, 230, 100);
        Stage discountWindow = new Stage();
        discountWindow.setTitle("Add Discount");
        discountWindow.setScene(discountScene);
        discountWindow.show();

        applyDiscount.setOnAction(e -> {
            double percentage = (Double.parseDouble(discountTF.getText()));
            for(int i=0; i<ProductTable.getItems().size();i++){
                ProductTable.getItems().get(i).setPrice(percentage*ProductTable.getItems().get(i).getPrice()/100);
                System.out.println(ProductTable.getItems().get(i).getPrice());
                itemByPercentage = ProductTable.getItems().get(i);
                Message percentageMessage = new Message(Message.updateItem,itemByPercentage);
                SimpleClient.getClient().sendMessageToServer(percentageMessage);
            }
            handleRefresh(new ActionEvent());
            discountWindow.close();
        });
    }
    @FXML
    void addProduct(ActionEvent event){
        Parent parent = null;
        FXMLLoader loader = new FXMLLoader(App.class.getResource("addProduct.fxml"));
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Add Product");
        stage.setScene(new Scene(parent));
        stage.show();
//        stage.setOnHiding((e) -> {
//            handleRefresh(new ActionEvent());
//        });
    }

    @FXML
    void deleteProudct(ActionEvent event) {
        int index = ProductTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        selectedItem = ProductTable.getSelectionModel().getSelectedItem();
        Message msg = new Message(Message.deleteProduct, selectedItem);
        try {
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  void initCol() {
        try {
            imageCol.setCellValueFactory(new PropertyValueFactory<>("ImgURL"));
            imageCol.setCellFactory(param -> new ImageTableCell<>());
            imageCol.prefWidthProperty().bind(ProductTable.widthProperty().multiply(.30));

            productName.setCellValueFactory(new PropertyValueFactory<>("name"));
            productName.prefWidthProperty().bind(ProductTable.widthProperty().multiply(.30));

            productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            productPrice.prefWidthProperty().bind(ProductTable.widthProperty().multiply(.20));

            productKind.setCellValueFactory(new PropertyValueFactory<>("kind"));
            productKind.prefWidthProperty().bind(ProductTable.widthProperty().multiply(.20));

        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCol();
        List<Item> myList= (List<Item>) SimpleClient.data;
        loadData(myList);

    }
    public void loadData(List<Item> myItems) {
        try {
            data.clear();
            for(Item m: myItems) {
                data.add(m);
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        ProductTable.setItems(data);
    }


    @FXML
    void goHome(ActionEvent event) {
        try {
            App.setRoot("cata");
        }
        catch(IOException ex){
            ex.printStackTrace();
        }

    }


    @FXML
    void showDetails(ActionEvent event) {
        selectedItem=ProductTable.getSelectionModel().getSelectedItem();
        Label firstLabel=new Label("Product Name");
        Label secondLabel=new Label("Product Price");
        Label thirdLabel=new Label("Product Kind");
        Label fourthLabel=new Label(selectedItem.getName());
        Label fifthLabel=new Label(selectedItem.getKind());
        Label sixthLabel=new Label(String.valueOf(selectedItem.getPrice()));
        StackPane secondaryLayOut = new StackPane();
        StackPane.setAlignment(fourthLabel,Pos.TOP_CENTER);
        StackPane.setAlignment(firstLabel, Pos.TOP_LEFT);
        StackPane.setAlignment(secondLabel, Pos.CENTER_LEFT);
        StackPane.setAlignment(thirdLabel, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(fifthLabel,Pos.BOTTOM_CENTER);
        StackPane.setAlignment(sixthLabel,Pos.CENTER);
        secondaryLayOut.getChildren().addAll(firstLabel,secondLabel,thirdLabel,fourthLabel,fifthLabel,sixthLabel);
        Scene secondScene = new Scene(secondaryLayOut,230,100);

        Stage newWindow = new Stage();
        newWindow.setTitle("Product Details");
        newWindow.setScene(secondScene);
        newWindow.show();

    }
    @FXML
    void goEdit(ActionEvent event) {
        selectedItem=ProductTable.getSelectionModel().getSelectedItem();
        Button saveBtn = new Button("Save");

        Label firstLabel=new Label("Product Name");
        Label secondLabel=new Label("Product Price");
        Label thirdLabel=new Label("Product Kind");
        TextField fourthLabel=new TextField(selectedItem.getName());
        fourthLabel.setMaxWidth(75);
        TextField fifthLabel=new TextField(selectedItem.getKind());
        fifthLabel.setMaxWidth(75);
        TextField tf=new TextField(String.valueOf(selectedItem.getPrice()));
        tf.setMaxWidth(75);
        StackPane secondaryLayOut1 = new StackPane();
        StackPane.setAlignment(saveBtn,Pos.BOTTOM_RIGHT);
            StackPane.setAlignment(fourthLabel,Pos.TOP_CENTER);
        StackPane.setAlignment(firstLabel, Pos.TOP_LEFT);
        StackPane.setAlignment(secondLabel, Pos.CENTER_LEFT);
        StackPane.setAlignment(thirdLabel, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(fifthLabel,Pos.BOTTOM_CENTER);
        StackPane.setAlignment(tf,Pos.CENTER);
        secondaryLayOut1.getChildren().addAll(firstLabel,secondLabel,thirdLabel,fourthLabel,fifthLabel,tf,saveBtn);



        Scene secondScene1 = new Scene(secondaryLayOut1,230,100);
        Stage newWindow1 = new Stage();
        newWindow1.setTitle("Edit Page");
        newWindow1.setScene(secondScene1);
        newWindow1.show();

        saveBtn.setOnAction(e -> {
            selectedItem.setName((fourthLabel.getText()));
            selectedItem.setKind((fifthLabel.getText()));
            selectedItem.setPrice(Double.parseDouble(tf.getText()));
            Message message1 = new Message(Message.updateItem, selectedItem);
            SimpleClient.getClient().sendMessageToServer(message1);
            newWindow1.close();
        });
    }
    private void handleRefresh(ActionEvent event) {
        try {
            Message msg=new Message(Message.getAllItems);
            SimpleClient.getClient().sendToServer(msg);
            System.out.println("message sent to server to get all products");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @FXML
    void AddToPascket(ActionEvent event) {
/*
        int index = ProductTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        selectedItem = ProductTable.getSelectionModel().getSelectedItem();
        org.example.entities.Client c=Client.getClient().getUser();
        Message msg = new Message(Message.Add2Basket_S, selectedItem,Integer.toString(c.getId()));
        try {
            Client.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
}
