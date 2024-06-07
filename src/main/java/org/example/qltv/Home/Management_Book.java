package org.example.qltv.Home;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.qltv.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Filter;

public class Management_Book implements Initializable {
    @FXML
    private Label LabelBook_Management;

    @FXML
    private TextField AuthorName;

    @FXML
    private TableColumn<Book, String> AuthorNameTable;

    @FXML
    private TextField BookID;

    @FXML
    private TableColumn<Book, Integer> BookIDTable;

    @FXML
    private TextField BookName;

    @FXML
    private TableColumn<Book, String> BookNameTable;

    @FXML
    private TableView<Book> BookTable;

    @FXML
    private TextField EnterTextField;
//    @FXML
//    private Button btnUpdate;

    @FXML
    private TextField PublishingYear;

    @FXML
    private TableColumn<Book, Integer> PublishingYearTable;

    @FXML
    private TextField QuantityBook;
    PreparedStatement stmt;

    @FXML
    private TableColumn<Book, Integer> QuantityBookTable;
    DataConnection dataConnection = DataConnection.getInstance();

    @FXML
    void AddButton(ActionEvent event) throws SQLException {
        if(BookID.getText().isBlank() || BookName.getText().isBlank() || AuthorName.getText().isBlank() || PublishingYear.getText().isBlank() || QuantityBook.getText().isBlank()){
            alert_error("Fill all the field");
        }else{
            int count = 0;
            String sql = "select count(*) from book_mana where Book_ID = '"+BookID.getText()+"'";
            ResultSet resultSet = dataConnection.dbSelect(sql);
            if(resultSet.next()){
                count = resultSet.getInt(1);
            }
            if(count == 0){
                String insertField = "INSERT INTO book_mana (Book_ID, Book_Name, Author_Name, Publishing_Year, Quantity_Book) VALUES (?, ?, ?, ?, ?)";
                try {
                    Connection conn = dataConnection.getConnection(); //
                    stmt = conn.prepareStatement(insertField); // dùng prepareStatment tạo một câu lệnh SQL với các tham số thay thế (placeholders), được biểu diễn bằng dấu ?.
                    stmt.setInt(1,Integer.parseInt( BookID.getText())); // đặt giá trị thay thế cho ? trong câu lệnh sql
                    stmt.setString(2, BookName.getText());
                    stmt.setString(3, AuthorName.getText());
                    stmt.setInt(4, Integer.parseInt(PublishingYear.getText()));
                    stmt.setInt(5, Integer.parseInt(QuantityBook.getText()));
                    int rowsInserted = stmt.executeUpdate(); // dùng để thực thi Insert
                    if (rowsInserted > 0) {
                        alert_information("Add book successfully!");
                    } else {
                        alert_error("Add book failed!");
                    }
                    showBook();
                    clear();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                alert_error("Book ID already exist!");
            }
        }

    }

    @FXML
    void DeleteButton(ActionEvent event) {
        if(BookID.getText().isBlank() || BookName.getText().isBlank() || AuthorName.getText().isBlank() || PublishingYear.getText().isBlank() || QuantityBook.getText().isBlank()){
            alert_error("Fill all the field");
        }else {
            Book selectedBook = BookTable.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                String deleteQuery = "DELETE FROM book_mana WHERE Book_ID = ?";
                try {
                    Connection conn = dataConnection.getConnection();
                    stmt = conn.prepareStatement(deleteQuery);
//                PreparedStatement stmt = dataConnection.getConnection().prepareStatement(deleteQuery);
                    stmt.setInt(1, selectedBook.getBookID());
                    int rowsDeleted = stmt.executeUpdate();
                    if (rowsDeleted > 0) {
                        alert_information("Delete book successfully!");
                    } else {
                        alert_error("Delete book failed!");
                    }
                    showBook();
                    clear();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                LabelBook_Management.setText("Please select a book to delete.");
            }
        }
    }



    @FXML
    void UpdateButton(ActionEvent event) {
        if(BookID.getText().isBlank() || BookName.getText().isBlank() || AuthorName.getText().isBlank() || PublishingYear.getText().isBlank() || QuantityBook.getText().isBlank()){
            alert_error("Fill all the field");
        }else {
            Book book = BookTable.getSelectionModel().getSelectedItem();
            String update = "UPDATE book_mana SET Book_ID = ?, Book_Name = ?, Author_Name = ?, Publishing_Year = ?, Quantity_Book = ? WHERE Book_ID = ?";
            try {
                Connection conn = dataConnection.getConnection();
                stmt = conn.prepareStatement(update);
                stmt.setInt(1,Integer.parseInt(BookID.getText()));
                stmt.setString(2, BookName.getText());
                stmt.setString(3, AuthorName.getText());
                stmt.setInt(4, Integer.parseInt(PublishingYear.getText()));
                stmt.setInt(5, Integer.parseInt(QuantityBook.getText()));
                stmt.setInt(6,book.getBookID());
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    alert_information("Update book successfully!");
//                    int a = 0000;
//                    System.out.println(a);
                } else {
                    alert_error("Update book failed");
                }
                BookTable.refresh();
                showBook();
                clear();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void HomeBook(ActionEvent event) throws IOException {
        Main.setRoot("Home");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showBook();
        searchbook();
    }

    public ObservableList<Book> bookObservableList() {
        ObservableList<Book> book = FXCollections.observableArrayList();
        String sql = "SELECT * FROM book_mana";
        try {
            ResultSet resultSet = dataConnection.dbSelect(sql);
            while (resultSet.next()) {
                Book book1 = new Book();
                book1.setBookID(resultSet.getInt("Book_ID"));
                book1.setBookName(resultSet.getString("Book_Name"));
                book1.setAuthorName(resultSet.getString("Author_Name"));
                book1.setPublishingYear(resultSet.getInt("Publishing_Year"));
                book1.setQuantityBook(resultSet.getInt("Quantity_Book"));
                book.add(book1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return book;
    }
    public void showBook() {
//        Management_Book managementBook = new Management_Book();
        ObservableList<Book> showBooklist = bookObservableList();
        BookIDTable.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        BookNameTable.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        AuthorNameTable.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        PublishingYearTable.setCellValueFactory(new PropertyValueFactory<>("publishingYear"));
        QuantityBookTable.setCellValueFactory(new PropertyValueFactory<>("quantityBook"));
        BookTable.setItems(showBooklist);
    }
    @FXML
    void getData(MouseEvent event) {
        Book book = BookTable.getSelectionModel().getSelectedItem();
        BookID.setText(String.valueOf(book.getBookID()));
        BookName.setText(book.getBookName());
        AuthorName.setText(book.getAuthorName());
        PublishingYear.setText(String.valueOf(book.getPublishingYear()));
        QuantityBook.setText(String.valueOf(book.getQuantityBook()));
    }
    public void clear(){
        BookID.setText("");
        BookName.setText("");
        AuthorName.setText("");
        PublishingYear.setText("");
        QuantityBook.setText("");
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
    public void searchbook(){
        Management_Book managementBook = new Management_Book();
        ObservableList<Book> book = managementBook.bookObservableList();
        FilteredList<Book> bookFilteredList = new FilteredList<>(book, b->true);
        EnterTextField.textProperty().addListener((observable, oldValue,newValue)->{
            bookFilteredList.setPredicate(bookSearchModel ->{
                if(newValue.isEmpty() || newValue.isBlank() ){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (bookSearchModel.getBookName().toLowerCase().contains(searchKeyword)){
                    return true;
                }else if(bookSearchModel.getAuthorName().toLowerCase().contains(searchKeyword)){
                    return true;
                }else if (String.valueOf(bookSearchModel.getBookID()).contains(searchKeyword)) {
                    return true;
                } else if (String.valueOf(bookSearchModel.getPublishingYear()).contains(searchKeyword)) {
                    return true;
                } else if (String.valueOf(bookSearchModel.getQuantityBook()).contains(searchKeyword)) {
                    return true;
                } else{
                    return false;
                }
            });
        });
        SortedList<Book> bookSortedList= new SortedList<>(bookFilteredList);
        bookSortedList.comparatorProperty().bind(BookTable.comparatorProperty());
        BookTable.setItems(bookSortedList);
    }
}