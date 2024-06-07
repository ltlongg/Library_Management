module org.example.qltv {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.dlsc.formsfx;
    requires java.sql;
    exports org.example.qltv.Home;
    opens org.example.qltv to javafx.fxml;
    exports org.example.qltv;
    opens org.example.qltv.Home;

}