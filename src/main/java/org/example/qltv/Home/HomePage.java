package org.example.qltv.Home;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.qltv.Main;


import java.io.IOException;

public class HomePage {

    @FXML
    void BookHome(ActionEvent event) throws IOException {

        Main.setRoot("Management_Book");

    }
    @FXML
    void ReaderHome(ActionEvent event) throws IOException{
        Main.setRoot("Reader_Management");
    }
    @FXML
    void BorrowHome(ActionEvent event) throws IOException {
        Main.setRoot("Borrow");
    }

    @FXML
    void ReturnHome(ActionEvent event) throws IOException{
        Main.setRoot("Return");
    }

    @FXML
    void SignOutHome(ActionEvent event) throws IOException{
        Main.setRoot("SignIn");
    }

}
