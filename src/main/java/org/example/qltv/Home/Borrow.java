package org.example.qltv.Home;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
//import org.example.qltv.DatabaseConnection;
import org.example.qltv.Main;
//import org.w3c.dom.CDATASection;

import java.io.IOException;
import java.sql.*;
import java.util.regex.Pattern;

//import org.example.qltv.Home.DataConnection;
public class Borrow {
    @FXML
    private Label AuthorNameBorrow;

    @FXML
    private Label BookIDBorrow;

    @FXML
    private TextField BookIDEnterBorrow;

    @FXML
    private DatePicker DueDateBorrow;

    @FXML
    private DatePicker IssueDateBorrow;

    @FXML
    private Label PublishingYearBorrow;
    @FXML
    private Label QuantityBorrow;
    @FXML
    private TextField enterGmail;


    @FXML
    private TextField enterName;

    @FXML
    private TextField enterPhone;

    @FXML
    private TextField enterReaderID;

    @FXML
    private Label BookNameBorrow;
    PreparedStatement st;
    @FXML
    private Label LabelBR;
    DataConnection dataConnection;
    DataReaderConnection dataReaderConnection;
    @FXML
    public void BorrowBook(ActionEvent event) throws IOException, SQLException {
        if(BookIDEnterBorrow.getText().isBlank() || enterReaderID.getText().isBlank()||enterName.getText().isBlank()||enterPhone.getText().isBlank()||enterGmail.getText().isBlank()||IssueDateBorrow.getValue()==null||DueDateBorrow.getValue()==null){
            alert_error("Fill all the field");
        }else {
            int quantity = quantityChange();
            if(quantity != 0 && !checkID()&&checkformat()){
                dataReaderConnection = DataReaderConnection.getInstance();
                String sqldb = "Insert into reader( Book_ID, Book_Name, Reader_ID, Reader_Name, Phone, Gmail, Issue_Date, Due_Date) values (?,?,?,?,?,?,?,?)";
                Connection con = dataReaderConnection.getConnection(); // kết nối tới cơ sở dữ liệu
                try{
                    st = con.prepareStatement(sqldb); // Tạo một đối tượng prepareStatement
                    st.setInt(1,Integer.parseInt(BookIDEnterBorrow.getText())); // gán giá trij cho tham số thứ 1
                    st.setString(2, String.valueOf(BookNameBorrow.getText()));
                    st.setInt(3,Integer.parseInt(enterReaderID.getText()));
                    st.setString(4,enterName.getText());
                    st.setString(5,enterPhone.getText());
                    st.setString(6,enterGmail.getText());
                    st.setDate(7, Date.valueOf(IssueDateBorrow.getValue()));
                    st.setDate(8,Date.valueOf(DueDateBorrow.getValue()));
                    st.executeUpdate();  // thực thi truy vấn
//                Reader_Management readerManagement = new Reader_Management();
//                readerManagement.showReader();
                    updateQuantity();
                    alert_information("Borrow book successfully!");
                    clear();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }else{
                alert_error("Can not borrow book. Please check all field!");
            }
        }
    }
    @FXML
    public void HomeBorrow(ActionEvent event) throws IOException {
        Main.setRoot("Home");
    }
    public void getBookdetail() throws SQLException {
        int bookID = Integer.parseInt(BookIDEnterBorrow.getText());
        DataConnection dataConnection = DataConnection.getInstance();
        String sql = "Select * from book_mana where Book_ID = '" + bookID + "'";
        ResultSet resultSet = dataConnection.dbSelect(sql);
        while (resultSet.next()){
            BookIDBorrow.setText(String.valueOf(resultSet.getInt("Book_ID")));
            BookNameBorrow.setText(resultSet.getString("Book_Name"));
            AuthorNameBorrow.setText(resultSet.getString("Author_Name"));
            PublishingYearBorrow.setText(String.valueOf(resultSet.getInt("Publishing_Year")));
            QuantityBorrow.setText(String.valueOf(resultSet.getInt("Quantity_Book")));
        }
    }
    @FXML
    public void searchBorrow(ActionEvent event) throws SQLException{
        if(BookIDEnterBorrow.getText().isBlank()){
            alert_error("Fill Book ID!");
        }else{
            if(checkBookID()){
                getBookdetail();
            }else {
                alert_error("Can not find that book");
            }
        }
    }
    public void clear() {
        enterReaderID.setText("");
        enterName.setText("");
        enterPhone.setText("");
        enterGmail.setText("");
        BookIDEnterBorrow.setText("");
        IssueDateBorrow.setValue(null);
        DueDateBorrow.setValue(null);
        BookIDBorrow.setText("");
        BookNameBorrow.setText("");
        AuthorNameBorrow.setText("");
        PublishingYearBorrow.setText("");
        QuantityBorrow.setText("");
    }
    public Integer quantityChange() throws SQLException {
        DataConnection dataConnection = DataConnection.getInstance();
        String bookId = BookIDEnterBorrow.getText();
        System.out.println("Bookid " + bookId);
        int bookid = Integer.parseInt(BookIDEnterBorrow.getText());
        String sql = "Select Quantity_Book from book_mana where Book_ID = '"+bookid+"'";
        ResultSet resultSet = dataConnection.dbSelect(sql);
        int quantitychange = 0;
        while (resultSet.next()){
            quantitychange = resultSet.getInt("Quantity_Book");
        }
        System.out.println("quantity: "+quantitychange);
        return quantitychange;
    }


    public void updateQuantity() throws SQLException{
        int quantity = quantityChange()-1;
//        int quantity = 4-1;
        System.out.println("after update: "+quantity);
        int bookid = Integer.parseInt(BookIDEnterBorrow.getText());

        String sql = "Update book_mana set Quantity_Book = '"+quantity+"'where Book_ID = '"+bookid+"'";
        dataConnection = DataConnection.getInstance();
        dataConnection.dbAdd(sql);
    }
    public boolean checkBookID() throws SQLException{
        int count = 0;
        String sql = "Select count(*) from book_mana where Book_ID = '"+BookIDEnterBorrow.getText()+"'";
        dataConnection = DataConnection.getInstance();
        ResultSet resultSet = dataConnection.dbSelect(sql);
        while (resultSet.next()){
            count = resultSet.getInt(1);
        }
        return count>0;
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
    public boolean checkID() throws SQLException {
        String sql = "select count(*) from reader where Book_ID = '"+BookIDEnterBorrow.getText()+"'and Reader_ID = '"+enterReaderID.getText()+"'";
        dataReaderConnection = DataReaderConnection.getInstance();
        ResultSet resultSet = dataReaderConnection.dbSelect(sql);
        int count = 0;
        if(resultSet.next()){
            count = resultSet.getInt(1);
        }
        return count > 0;
    }
    public boolean checkformat(){
        String phone = enterPhone.getText();
        Pattern pt = Pattern.compile("^[0][0-9]{9}$");
        String gmail = enterGmail.getText();
        Pattern pt_2 = Pattern.compile("^[a-zA-Z0-9]+@[a-z]+(\\.[a-z)]+)$");
        if(pt.matcher(phone).find()&&pt_2.matcher(gmail).find()){
            return true;
        }
        else{
            return false;
        }
    }
}

