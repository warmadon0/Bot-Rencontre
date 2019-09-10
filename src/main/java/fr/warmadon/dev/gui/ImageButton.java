package fr.warmadon.dev.gui;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;

public class ImageButton extends Button {
    public ImageButton(Pane root) {


        this.setUnHover(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                ImageButton.this.setOpacity(0.8D);
            }
        });
        this.setHover(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                ImageButton.this.setOpacity(1.0D);
            }
        });
        root.getChildren().add(this);
    }

    public void setSize(int width_, int height_) {
        this.setPrefSize((double)width_, (double)height_);
    }

    public void setInvisible() {
        this.setBackground((Background)null);
    }

    public void setPosition(int posX, int posY) {
        this.setLayoutX((double)posX);
        this.setLayoutY((double)posY);
    }

    public void setAction(EventHandler<? super MouseEvent> value) {
        this.onMouseClickedProperty().set(value);
    }

    public final void setHover(EventHandler<? super MouseEvent> value) {
        this.onMouseEnteredProperty().set(value);
    }

    public final void setUnHover(EventHandler<? super MouseEvent> value) {
        this.onMouseExitedProperty().set(value);
    }
}
