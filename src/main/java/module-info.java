module com.petproject.desktopmediaplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;


    opens com.petproject.desktopmediaplayer to javafx.fxml;
    exports com.petproject.desktopmediaplayer;
}