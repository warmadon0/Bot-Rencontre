package fr.warmadon.dev.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class AppPanel {

    private ImageButton news;

    public Parent createContent(){
        Pane contentPane = new Pane();
        contentPane.setPrefSize(GUIJFX.getWidth(),GUIJFX.getHeight());
        GUIJFX.drawBackgroundMp4(contentPane, "background.mp4");

        this.news = new ImageButton(contentPane);
        this.news.setGraphic(new ImageView(GUIJFX.resources.loadImage("news.png")));
        this.news.setStyle("-fx-background-color: rgba(53,89,119,0); -fx-text-fill: white;");
        this.news.setFont(FontLoader.loadFont("Roboto-Light.ttf", "Roboto Lt", 18F));
        this.news.setPosition(GUIJFX.getWidth() / 2 - 110, GUIJFX.getHeight() / 2 + 20);
        this.news.setSize(150,35);
        this.news.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });




        return contentPane;
    }

}
