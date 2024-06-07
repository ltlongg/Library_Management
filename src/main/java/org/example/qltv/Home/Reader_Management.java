package org.example.qltv.Home;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.qltv.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class Reader_Management implements Initializable {

    @FXML
    private TextField BookIDBR;

    @FXML
    private TableColumn<Reader, Integer> BookIDTableBR;

    @FXML
    private TableColumn<Reader, String> BookNameTableBR;

    @FXML
    private TableColumn<Reader, Date> DueDateTableBR;

    @FXML
    private TableColumn<Reader, String> GmailTableBR;

    @FXML
    private TableColumn<Reader,Date > IssueDateTableBr;

    @FXML
    private TableColumn<Reader, String> PhoneTableBR;

    @FXML
    private TableColumn<Reader, Integer> ReaderIDTableBR;


    @FXML
    private TextField ReaderIDBR;

    @FXML
    private TableColumn<Reader, String> ReaderNameTableBR;
    @FXML
    private TableView<Reader> TableBR;
//    Statement st;
//    Connection con;
//    ResultSet resultSet;

    DataReaderConnection dataReaderConnection = DataReaderConnection.getInstance();
    public ObservableList<Reader> getReader() throws SQLException {
        ObservableList<Reader> readers = FXCollections.observableArrayList();
        String sql = "Select * from reader";
        ResultSet resultSet = dataReaderConnection.dbSelect(sql);
        while(resultSet.next()){
            Reader rd = new Reader();
            rd.setBookID(resultSet.getInt("Book_ID"));
            rd.setBookName(resultSet.getString("Book_Name"));
            rd.setReaderID(resultSet.getInt("Reader_ID"));
            rd.setReaderName(resultSet.getString("Reader_Name"));
            rd.setPhone(resultSet.getString("Phone"));
            rd.setGmail(resultSet.getString("Gmail"));
            rd.setIssueDate(resultSet.getDate("Issue_Date"));
            rd.setDueDate(resultSet.getDate("Due_Date"));
            readers.add(rd);
        }
        return readers;
    }
    @FXML
    public void HomeBR(ActionEvent event) throws IOException {
        Main.setRoot("Home");
    }

    @FXML
    public void SearchBR(ActionEvent event) throws SQLException {
        if(BookIDBR.getText().isBlank()||ReaderIDBR.getText().isBlank()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
//            alert.setHeaderText("Hello");
            alert.setContentText("Fill all the field");
            alert.show();
        }else{
            search();
            clear();
        }

    }
    public void showReader() throws SQLException {
        ObservableList<Reader> list = getReader();
        BookIDTableBR.setCellValueFactory(new PropertyValueFactory<Reader, Integer>("bookID"));
        BookNameTableBR.setCellValueFactory(new PropertyValueFactory<Reader,String >("bookName"));
        ReaderIDTableBR.setCellValueFactory(new PropertyValueFactory<Reader,Integer>("readerID"));
        ReaderNameTableBR.setCellValueFactory(new PropertyValueFactory<Reader,String>("readerName"));
        PhoneTableBR.setCellValueFactory(new PropertyValueFactory<Reader, String>("phone"));
        GmailTableBR.setCellValueFactory(new PropertyValueFactory<Reader, String>("gmail"));
        IssueDateTableBr.setCellValueFactory(new PropertyValueFactory<Reader, Date>("issueDate"));
        DueDateTableBR.setCellValueFactory(new PropertyValueFactory<Reader, Date>("dueDate"));
        TableBR.setItems(list);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showReader();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ObservableList<Reader> getReaderSearch() throws SQLException {
        ObservableList<Reader> readers = FXCollections.observableArrayList();
        int bookid = Integer.parseInt(BookIDBR.getText());
        int readerid = Integer.parseInt(ReaderIDBR.getText());
        String sql = "Select * from reader where Book_ID = '"+bookid+"'and Reader_ID = '"+readerid+"'";
        ResultSet resultSet = dataReaderConnection.dbSelect(sql);
        while(resultSet.next()){
            Reader rd = new Reader();
            rd.setBookID(resultSet.getInt("Book_ID"));
            rd.setBookName(resultSet.getString("Book_Name"));
            rd.setReaderID(resultSet.getInt("Reader_ID"));
            rd.setReaderName(resultSet.getString("Reader_Name"));
            rd.setPhone(resultSet.getString("Phone"));
            rd.setGmail(resultSet.getString("Gmail"));
            rd.setIssueDate(resultSet.getDate("Issue_Date"));
            rd.setDueDate(resultSet.getDate("Due_Date"));
            readers.add(rd);
        }
        return readers;
    }
    public void search() throws SQLException{
        ObservableList<Reader> listSearch = getReaderSearch();
        BookIDTableBR.setCellValueFactory(new PropertyValueFactory<Reader, Integer>("bookID"));
        BookNameTableBR.setCellValueFactory(new PropertyValueFactory<Reader,String >("bookName"));
        ReaderIDTableBR.setCellValueFactory(new PropertyValueFactory<Reader,Integer>("readerID"));
        ReaderNameTableBR.setCellValueFactory(new PropertyValueFactory<Reader,String>("readerName"));
        PhoneTableBR.setCellValueFactory(new PropertyValueFactory<Reader, String>("phone"));
        GmailTableBR.setCellValueFactory(new PropertyValueFactory<Reader, String>("gmail"));
        IssueDateTableBr.setCellValueFactory(new PropertyValueFactory<Reader, Date>("issueDate"));
        DueDateTableBR.setCellValueFactory(new PropertyValueFactory<Reader, Date>("dueDate"));
        TableBR.setItems(listSearch);
    }
    @FXML
    public void TableDetailBR(ActionEvent e) throws SQLException {
        showReader();
    }
    public void clear(){
        BookIDBR.setText("");
        ReaderIDBR.setText("");
    }
}
