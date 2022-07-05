package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class ManagersPersonalPageController {

    @FXML private Button CatalogB;
    @FXML private Button ClientsButton;
    @FXML private Button ComplaintsB;
    @FXML private Button HomeB;
    @FXML private Label instructions;

    @FXML
    void ComplaintsPage(ActionEvent event) {

    }

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
            App.setRoot("Catalog");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    void GoToClientsPage(ActionEvent event) {

    }

}
