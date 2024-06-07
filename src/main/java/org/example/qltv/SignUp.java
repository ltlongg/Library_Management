package org.example.qltv;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class SignUp {
    //public String Name, Phone, Username, Password;
    @FXML
    private PasswordField ConfirmPasswordSignUp;
    @FXML
    private TextField NameSignUp;

    @FXML
    private PasswordField PasswordSignUp;
    @FXML
    private Label InvalidSignUpMessege;
    @FXML
    private TextField PhoneSignUp;

    @FXML
    private TextField UsernameSignUp;
    @FXML
    public void OkSignUp(ActionEvent event) throws IOException, SQLException {
        if(PasswordSignUp.getText().equals(ConfirmPasswordSignUp.getText())&&NameSignUp.getText().isBlank()==false && PhoneSignUp.getText().isBlank()==false && UsernameSignUp.getText().isBlank()==false && PasswordSignUp.getText().isBlank()==false){
            int check = checkSignUp();
            if(check==1 && checkpus()){
                ValidateSignUp();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Messeger");
                alert.setHeaderText("Messeger");
                alert.setContentText("Sign up successfully!");
                alert.show();
                clear();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Wrong phone or name format or account already exists!");
                alert.show();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Fill all the field!");
            alert.show();
        }
//        Main.setRoot("SignIn");
    }

    @FXML
    public void ReturnSignUp(ActionEvent event) throws IOException {
        Main.setRoot("SignIn");
    }
    public void ValidateSignUp() throws SQLException{

        String insertField = "insert into user_account(Name,phone, Username, Password) \n" +
                "values ('";
        String insertValues = NameSignUp.getText()+"','"+ PhoneSignUp.getText()+"','"+ UsernameSignUp.getText()+"','"+ PasswordSignUp.getText()+"')";
        String inserSignUp = insertField + insertValues;
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        databaseConnection.dbUpdate(inserSignUp);
    }
    public int checkSignUp(){
        Pattern pt = Pattern.compile("^[0-9]{10}$");
        String phone = PhoneSignUp.getText();
        Pattern pt_2 = Pattern.compile("^[a-zA-Z ]+$");
        String name = NameSignUp.getText();
        if(pt.matcher(phone).find()&&pt_2.matcher(name).find()){
            return 1;
        }else{
            return 0;
        }
    }

    public boolean checkpus() throws SQLException {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        String sql = "Select phone, Username from user_account";
        ResultSet resultSet = databaseConnection.dbSelect(sql);
        int check = 0;
        while (resultSet.next()) {
            String phone = resultSet.getString("phone");
            String username = resultSet.getString("Username");
            if (phone.equals(PhoneSignUp.getText()) || username.equals(UsernameSignUp.getText())){
                check = 1;
                break;
            }
        }
        if(check == 0){
            return true;
        }else{
            return false;
        }
    }

//    public static void main(String[] args) {
//        SignUp signUp = new SignUp();
//        signUp.
//    }
    public void clear(){
        NameSignUp.setText("");
        PhoneSignUp.setText("");
        UsernameSignUp.setText("");
        PasswordSignUp.setText("");
        ConfirmPasswordSignUp.setText("");
    }
}

