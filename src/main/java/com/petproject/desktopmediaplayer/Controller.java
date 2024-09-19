package com.petproject.desktopmediaplayer;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.Objects;

public class Controller {
    private MediaPlayer mediaPlayer;

    @FXML
    private MediaView mediaView;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Slider scenesSlider;

    @FXML
    private Button increaseTempoButton;

    @FXML
    private Button decreaseTempoButton;

    @FXML
    private Button increasePitchButton;

    @FXML
    private Button decreasePitchButton;

    private double currentRate = 1.0;
    private double currentPitch = 1.0;

    @FXML
    private void setImage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Image icon = new Image("file:C:\\Users\\levic\\Desktop\\Mich\\Pics\\MyLOGO\\Levit5.png");
        stage.getIcons().add(icon);
    }


    @FXML
    private void handleButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(
                "Select file (.mp4, .mp3, .flac)", "*.mp4", "*.mp3", "*.flac");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showOpenDialog(null);
        String filePath = file.toURI().toString();

        if (filePath != null) {
            Media media = new Media(filePath);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);

            mediaView.setFitHeight(700);
            mediaView.setFitWidth(700);

            volumeSlider.setValue(mediaPlayer.getVolume() * 100);
            volumeSlider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mediaPlayer.setVolume(volumeSlider.getValue() / 100);
                }
            });

            scenesSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    mediaPlayer.seek(Duration.seconds(scenesSlider.getValue()));
                }
            });

            if (filePath.endsWith(".mp3") || filePath.endsWith(".flac")) {
                showAudioControls();
            } else if (filePath.endsWith(".mp4")) {
                hideAudioControls();  // Приховати контролери для відеофайлів
            }

            mediaPlayer.play();
        }
    }

    // Метод для показу кнопок управління темпом і pitch для аудіофайлів
    private void showAudioControls() {
        increaseTempoButton.setVisible(true);
        decreaseTempoButton.setVisible(true);
        increasePitchButton.setVisible(true);
        decreasePitchButton.setVisible(true);

        increaseTempoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentRate += 0.1;
                mediaPlayer.setRate(currentRate);
            }
        });

        decreaseTempoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentRate -= 0.1;
                if (currentRate > 0) {
                    mediaPlayer.setRate(currentRate);
                }
            }
        });

        increasePitchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentPitch += 0.1;
                mediaPlayer.setAudioSpectrumInterval(currentPitch); // Підтримка pitch
            }
        });

        decreasePitchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentPitch -= 0.1;
                if (currentPitch > 0) {
                    mediaPlayer.setAudioSpectrumInterval(currentPitch);
                }
            }
        });
    }

    private void hideAudioControls() {
        increaseTempoButton.setVisible(false);
        decreaseTempoButton.setVisible(false);
        increasePitchButton.setVisible(false);
        decreasePitchButton.setVisible(false);
    }


    @FXML
    private void playMedia(ActionEvent event) {
        mediaPlayer.play();
        mediaPlayer.setRate(1);
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