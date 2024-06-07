package org.example.qltv.Home;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.qltv.Main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Return {


    @FXML
    private TextField BookIDEnter;

    @FXML
    private Label BookIDReturn;

    @FXML
    private Label BookNameReturn;

    @FXML
    private Label DueDateReturn;

    @FXML
    private Label IssueDateReturn;

    @FXML
    private TextField ReaderIDEnter;

    @FXML
    private Label ReaderIDReturn;

    @FXML
    private Label ReaderNameReturn;
    @FXML
    private Label LabelReturn;
    DataReaderConnection dataReaderConnection = DataReaderConnection.getInstance();
    DataConnection dataConnection = DataConnection.getInstance();
    @FXML
    public void FindDetailReturn(ActionEvent event) throws SQLException {

        if (BookIDEnter.getText().isBlank()||ReaderIDEnter.getText().isBlank()){
            alert_error("Fill Book ID and Reader ID!");
//            LabelReturn.setText("Fill all the field");
        }else{
            getReaderdetail();
            if(checkFindDetail()){
                getReaderdetail();
            }else{
                alert_error("Borrower information not found!");
            }
        }

    }

    @FXML
    public void HomeReturn(ActionEvent event)throws IOException {
        Main.setRoot("Home");
    }

    @FXML
    public void ReturnBook(ActionEvent event) throws SQLException {
        if(BookIDEnter.getText().isBlank()||ReaderIDEnter.getText().isBlank()||BookIDReturn.getText().isBlank()||BookNameReturn.getText().isBlank()||ReaderIDReturn.getText().isBlank()||ReaderNameReturn.getText().isBlank()||IssueDateReturn.getText().isBlank()||DueDateReturn.getText().isBlank()){
            alert_error("Fill all the field!");
        }else{
            deleteBorrower();
            updateQuantity();
            alert_information("Return book successfully!");
            clear();
        }
    }

    public void getReaderdetail() throws SQLException {
        int bookID = Integer.parseInt(BookIDEnter.getText());
        int readerID = Integer.parseInt(ReaderIDEnter.getText());
        String sql = "Select Book_ID, Book_Name,Reader_Name,Reader_ID, Issue_Date, Due_Date from reader where Book_ID = '" + bookID + "'and Reader_ID = '"+readerID+"'";
        ResultSet resultSet = dataReaderConnection.dbSelect(sql);
        while (resultSet.next()){
            BookIDReturn.setText(String.valueOf(resultSet.getInt("Book_ID")));
            BookNameReturn.setText(resultSet.getString("Book_Name"));
            ReaderNameReturn.setText(resultSet.getString("Reader_Name"));
            ReaderIDReturn.setText(String.valueOf(resultSet.getInt("Reader_ID")));
            IssueDateReturn.setText(String.valueOf(resultSet.getDate("Issue_Date")));
            DueDateReturn.setText(String.valueOf(resultSet.getDate("Due_Date")));
        }
    }
    public void clear(){
        BookIDEnter.setText("");
        ReaderIDEnter.setText("");
        BookNameReturn.setText("");
        BookIDReturn.setText("");
        ReaderIDReturn.setText("");
        BookIDEnter.setText("");
        ReaderNameReturn.setText("");
        IssueDateReturn.setText(null);
        DueDateReturn.setText(null);
    }
    public Integer quantityChange() throws SQLException {
        String bookId = BookIDEnter.getText();
        System.out.println("Bookid " + bookId);
        int bookid = Integer.parseInt(BookIDEnter.getText());

        String sql = "Select Quantity_Book from book_mana where Book_ID = '"+bookid+"'";
        ResultSet resultSet = dataConnection.dbSelect(sql);
        int quantitychange = 0;
        while (resultSet.next()){
            quantitychange = resultSet.getInt("Quantity_Book");
        }
        System.out.println("quantity: "+quantitychange);
        return quantitychange;
    }
    public void updateQuantity() throws SQLException {
        int quantity = quantityChange() + 1;
        System.out.println("after change: "+quantity);
        int bookid = Integer.parseInt(BookIDEnter.getText());

        String sql = "Update book_mana set Quantity_Book = '"+quantity+"'where Book_ID = '"+bookid+"'";
        dataConnection.dbAdd(sql);
    }

    public boolean checkFindDetail() throws SQLException {
        int count = 0;
        String sql = "Select count(*) from reader where Book_ID = '"+BookIDEnter.getText()+"'and Reader_ID = '"+ReaderIDEnter.getText()+"'";
        ResultSet resultSet = dataReaderConnection.dbSelect(sql);
        if(resultSet.next()){
             count = resultSet.getInt(1);
        }
        return count>0;
    }
    public void deleteBorrower(){
        dataReaderConnection = DataReaderConnection.getInstance();
        String sql = "Delete from reader where Book_ID = '"+BookIDEnter.getText()+"'and Reader_ID = '"+ReaderIDEnter.getText()+"'";
        dataReaderConnection.dbAdd(sql);
    }
    public void alert_error(String ale){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText(""+ale);
        alert.show();
    }
    public void alert_information(String ali){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Messege");
        alert.setContentText(""+ali);
        alert.setHeaderText("Messege");
        alert.show();
    }
}

