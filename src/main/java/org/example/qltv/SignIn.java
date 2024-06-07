package org.example.qltv;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignIn {
    DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    @FXML
    private Label InvalidSignInMassege;

    @FXML
    private PasswordField PasswordText;
    @FXML
    private Button Cancel;
    @FXML
    private TextField UsernameText;
    int check = 0;

    @FXML
    public void OnClickSignIn(ActionEvent event) throws IOException, SQLException {
        String username = UsernameText.getText();
        String password = PasswordText.getText();

        if (username.isBlank() || password.isBlank()) {
            InvalidSignInMassege.setText("Fill all the fields!");
        } else {
            if (validateSignIn(username, password)){
                InvalidSignInMassege.setText("Sign in Successfully!");
                Main.setRoot("Home");
            } else {
                InvalidSignInMassege.setText("Username or Password wrong!");
            }
        }
    }

    @FXML
    public void OnClickSignUp(ActionEvent event) throws IOException {
        Main.setRoot("SignUp");

    }
    @FXML
    public void OnClickCancel(ActionEvent event) {
        Stage stage = (Stage) Cancel.getScene().getWindow();
        stage.close();// chỉ đóng 1 cửa sổ cụ thể cho stage đại diện
        Platform.exit(); // dùng để đóng tất cả các cửa sổ, giải phóng các tài nguyên và thoát khỏi ứng dụng
    }

    public boolean validateSignIn(String UsernameText, String PasswordText) throws SQLException {
        int count = 0;
        String sql = "Select count(*) from user_account where Username  = '" + UsernameText + "' and Password = '" + PasswordText + "'";
        //System.out.println(sql);
        ResultSet resultSet = databaseConnection.dbSelect(sql);  // cau lenh
        if (resultSet.next()) {
            count = resultSet.getInt(1);

        }
        return count > 0;

    }
}
