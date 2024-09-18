package com.petproject.desktopmediaplayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.util.Objects;

public class Controller {
    private MediaPlayer mediaPlayer;

    @FXML
    private MediaView mediaView;

    @FXML // метод буде прив'язуватися під sample.fxml файлику
    private void handleButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.
                ExtensionFilter("Select file (.mp4)", "*.mp4");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showOpenDialog(null);
        String filePath = file.toURI().toString();

        if (filePath != null) {
            Media media = new Media(filePath);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);

            mediaView.setFitHeight(700);
            mediaView.setFitWidth(700);


            mediaPlayer.play();
        }
    }

    @FXML
    private void playMedia(ActionEvent event) {
        mediaPlayer.play();
    }

    @FXML
    private void stopMedia(ActionEvent event) {
        mediaPlayer.stop();
    }

    @FXML
    private void pauseMedia(ActionEvent event) {
        mediaPlayer.pause();
    }

    @FXML
    private void repeatMedia(ActionEvent event) {
        if (Objects.isNull(mediaPlayer.getOnEndOfMedia())) {
            mediaPlayer.setOnEndOfMedia(() ->
            {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
            });
        } else {
            mediaPlayer.setOnEndOfMedia(null);
        }
    }

    @FXML
    private void slowPlayMedia(ActionEvent event) {
        mediaPlayer.setRate(0.75);
    }

    @FXML
    private void verySlowPlayMedia(ActionEvent event) {
        mediaPlayer.setRate(0.4);
    }

    @FXML
    private void normalSpeedPlayMedia(ActionEvent event) {
        mediaPlayer.setRate(1);
    }

    @FXML
    private void fastPlayMedia(ActionEvent event) {
        mediaPlayer.setRate(1.3);
    }

    @FXML
    private void veryFastPlayMedia(ActionEvent event) {
        mediaPlayer.setRate(2);
    }

    @FXML
    private void exitMedia(ActionEvent actionEvent) {
        System.exit(0);
    }
}