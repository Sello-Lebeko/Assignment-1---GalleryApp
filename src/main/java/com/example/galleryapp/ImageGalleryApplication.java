package com.example.galleryapp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.InputStream;

public class ImageGalleryApplication extends Application {

    protected ImageView[] thumbnails;
    private ImageView[] fullSizeImages;
    private int currentIndex;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setStyle("-fx-padding: 10px;");

        thumbnails = new ImageView[]{
                createImageView("/images/M.jpg"),
                createImageView("/images/My.png"),
                createImageView("/images/PN.jpg"),
                createImageView("/images/R.jpg"),
                createImageView("/images/OIP.jpg"),
                createImageView("/images/G.jpg"),
                createImageView("/images/P.jpg"),
                createImageView("/images/W.jpg"),
                createImageView("/images/B.jpg"),
        };

        fullSizeImages = new ImageView[]{
                createImageView("/images/M.jpg"),
                createImageView("/images/My.png"),
                createImageView("/images/PN.jpg"),
                createImageView("/images/R.jpg"),
                createImageView("/images/OIP.jpg"),
                createImageView("/images/G.jpg"),
                createImageView("/images/P.jpg"),
                createImageView("/images/W.jpg"),
                createImageView("/images/B.jpg"),

        };

        for (int i = 0; i < thumbnails.length; i++) {
            int index = i;
            thumbnails[i].setPreserveRatio(true);
            thumbnails[i].setFitWidth(140);
            thumbnails[i].setFitHeight(140);
            thumbnails[i].getStyleClass().add("thumbnails");
            thumbnails[i].setOnMouseClicked(event -> showFullSizeImage(index));
            gridPane.add(thumbnails[i], i % 3, i / 3);
        }

        root.setCenter(gridPane);

        Scene scene = new Scene(root, 600, 500);
        scene.getStylesheets().add("/styles.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("ImageGalleryApp");
        primaryStage.show();
    }

    private ImageView createImageView(String imagePath) {
        InputStream inputStream = getClass().getResourceAsStream(imagePath);
        if (inputStream != null) {
            Image image = new Image(inputStream);
            return new ImageView(image);
        } else {
            System.err.println("Failed to load image: " + imagePath);
            return new ImageView();
        }
    }

    private void showFullSizeImage(int index) {
        currentIndex = index;
        ImageView fullImageView = fullSizeImages[index];
        Button nextButton = new Button("Next");
        Button previousButton = new Button("Previous");
        Button backButton = new Button("Back to Thumbnails");

        nextButton.setOnAction(event -> {
            currentIndex = (currentIndex + 1) % fullSizeImages.length;
            fullImageView.setImage(fullSizeImages[currentIndex].getImage());
        });

        previousButton.setOnAction(event -> {
            currentIndex = (currentIndex - 1 + fullSizeImages.length) % fullSizeImages.length;
            fullImageView.setImage(fullSizeImages[currentIndex].getImage());
        });

        backButton.setOnAction(event -> {
            Stage stage = (Stage) fullImageView.getScene().getWindow();
            stage.close();
        });

        StackPane.setAlignment(fullImageView, javafx.geometry.Pos.CENTER);
        StackPane.setAlignment(nextButton, javafx.geometry.Pos.CENTER_RIGHT);
        StackPane.setAlignment(previousButton, javafx.geometry.Pos.CENTER_LEFT);
        StackPane.setAlignment(backButton, javafx.geometry.Pos.BOTTOM_CENTER);

        StackPane.setMargin(nextButton, new Insets(0, 40, 0, 0));
        StackPane.setMargin(previousButton, new Insets(0, 0, 0, 40));

        StackPane stackPane = new StackPane(fullImageView, nextButton, previousButton, backButton);

        Scene scene = new Scene(stackPane);
        scene.getStylesheets().add("/styles.css");

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Full Image View");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
