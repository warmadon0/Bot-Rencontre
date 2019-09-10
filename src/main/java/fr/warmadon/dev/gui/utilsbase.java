package fr.warmadon.dev.gui;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.awt.*;

public class utilsbase {

        final Point dragDelta = new Point();

        public utilsbase(final Stage stage, Scene scene, StageStyle style, Mover moveable) {
            Logger.write("Preparing the base.");


            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException var6) {
                new AppAlert(var6.toString(), Alert.AlertType.ERROR);
                Logger.err(var6.toString());
            } catch (InstantiationException var7) {
                new AppAlert(var7.toString(), Alert.AlertType.ERROR);
                Logger.err(var7.toString());
            } catch (IllegalAccessException var8) {
                new AppAlert(var8.toString(), Alert.AlertType.ERROR);
                Logger.err(var8.toString());
            } catch (UnsupportedLookAndFeelException var9) {
                new AppAlert(var9.toString(), Alert.AlertType.ERROR);
                Logger.err(var9.toString());
            }

            stage.initStyle(style);
            stage.setResizable(false);
            stage.setTitle(GUIJFX.appname);
            stage.setWidth((double)GUIJFX.getWidth());
            stage.setHeight((double)GUIJFX.getHeight());
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent windowEvent) {
                    Platform.exit();
                    System.exit(0);
                }
            });
            if (moveable.equals(Mover.MOVE_ON_CLICK)) {
                scene.setOnMousePressed(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent mouseEvent) {
                        dragDelta.x = (int)(stage.getX() - mouseEvent.getScreenX());
                        dragDelta.y = (int)(stage.getY() - mouseEvent.getScreenY());
                    }
                });
                scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent mouseEvent) {
                        stage.setX(mouseEvent.getScreenX() + (double) dragDelta.x);
                        stage.setY(mouseEvent.getScreenY() + (double) dragDelta.y);
                    }
                });
            }


            stage.setScene(scene);

            stage.show();

            Logger.write("Base loaded.");
            showCopyright();
        }

        public void setIconImage(Stage primaryStage, Image img) {
            primaryStage.getIcons().add(img);
        }

        private void showCopyright() {
            Logger.err(GUIJFX.appname + " by Warmadon");
            Logger.err(" ");
            Logger.err(">> Not allowed <<");
            Logger.err("\t- Reproduction");
            Logger.err("\t- Distribution");
            Logger.err("\t- Modification");
        }
}

