package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OrderController implements Initializable{
    double percentage = 1;
    ObservableList<Item> data = FXCollections.observableArrayList();
    public List<Item> itemsList=new ArrayList<>();
    @FXML private TextField NameTF;
    @FXML private TextField PhoneNumberTF;
    @FXML private TableView<Item> ProductTable;
    @FXML private TableColumn<Item, String> imageCol;
    @FXML private TableColumn<Item, String> productName;
    @FXML private TableColumn<Item, String> productKind;
    @FXML private TableColumn<Item, Double> productPrice;
    @FXML private Label instructions;
    @FXML private Label price;
    @FXML private Button CancelB;
    @FXML private Button GetPriceB;
    @FXML private Button OrderB;
    @FXML private CheckBox yesship;
    @FXML private CheckBox noship;
    @FXML private CheckBox yescard;
    @FXML private CheckBox nocard;
    @FXML private DatePicker date;
    @FXML private ComboBox<Integer> HourCB;
    @FXML private ComboBox<Integer> MinuteCB;
    @FXML private Button PersonlPageB;

    @FXML
    void GoPersonlPage(ActionEvent event) {
        try {
            App.setRoot("UserPersonalP");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    void checkYEScard(ActionEvent event) {
        if(nocard.isSelected())
            nocard.setSelected(false);
    }
    @FXML
    void checkNocard(ActionEvent event) {
        if(yescard.isSelected())
            yescard.setSelected(false);
    }
    @FXML
    void checkNo(ActionEvent event) {
        if(yesship.isSelected())
            yesship.setSelected(false);
    }
    @FXML
    void checkYES(ActionEvent event) {
        if(noship.isSelected())
            noship.setSelected(false);
    }

    @FXML
    void Order(ActionEvent event) throws InterruptedException {
         System.out.println("im in the fin for order");
        String construction="";
        if((NameTF.getText().equals("") || PhoneNumberTF.getText().equals("")) && yesship.isSelected())
        {
            construction="Please enter your name and your phone number to reach for delivery.\n";
        }
        if ((!(HourCB.getSelectionModel().getSelectedItem().equals("")) && MinuteCB.getSelectionModel().getSelectedItem().equals(""))
        ||((HourCB.getSelectionModel().getSelectedItem().equals("")) && !(MinuteCB.getSelectionModel().getSelectedItem().equals("")))) {
            construction+= "Please make sure you selected the time right.";
        }
        instructions.setText(construction);
/*        if(construction.equals("")) {
            System.out.println("construction is ==>" + construction);
        }*/
        if(construction.equals("")){
            double price=GetPrice();
            if(yesship.isSelected())price+=20;
            System.out.println("price:"+price);
            if(HourCB.getSelectionModel().getSelectedItem().equals("") && MinuteCB.getSelectionModel().getSelectedItem().equals(""))
            {
                Order order= new Order(price, SimpleClient.getClient().getUser(), yescard.isSelected(), yesship.isSelected());
                Message OrderMsg=new Message(Message.SaveOrder_S,order);
                SimpleClient.getClient().sendMessageToServer(OrderMsg);
            }
            else
            {
                int h=HourCB.getSelectionModel().getSelectedItem();
                int m=MinuteCB.getSelectionModel().getSelectedItem();
                System.out.println("time:   "+h+ " : " +m);
                Order order= new Order(price, SimpleClient.getClient().getUser(), yescard.isSelected(), yesship.isSelected(),date.getValue(),LocalTime.of(h,m));
                Message OrderMsg=new Message(Message.SaveOrder_S,order);
                SimpleClient.getClient().sendMessageToServer(OrderMsg);
            }
        }
    }
    public double GetPrice()
    {
        double price=0;
        for(Item i:itemsList)
        {
            price+=i.getPrice();
        }
        return price;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(SimpleClient.data==null)
            System.out.println("it is empty!");
        initCol();
        List<Item> myList = (List<Item>) SimpleClient.data;
        itemsList=myList;
        loadData(myList);
        HourCB.getItems().addAll(12,13,14,15,16,17,18,19,20,21,22);
        MinuteCB.getItems().addAll(00,15,30);

    }
    private void initCol() {
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

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void loadData(List<Item> myItems) {
        try {
            data.clear();
            for (Item m : myItems) {
                data.add(m);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        ProductTable.setItems(data);
    }

    @FXML
    void CancelOrder(ActionEvent event) {

    }
    @FXML
    void goHome(ActionEvent event) {
        try {
            App.setRoot("cata");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void GetPrice(ActionEvent event) {
        price.setText(GetPrice() +"");
    }
}
