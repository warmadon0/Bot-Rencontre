package fr.warmadon.dev.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.media.Media;
import javax.imageio.ImageIO;

public class ResourceLocation {
    public ResourceLocation() {
    }

    public static Media getMedia(String media) {
        Media theMedia = null;
        URL resourceUrl = ResourceLocation.class.getResource(getResourceLocation() + media);

        try {
            theMedia = new Media(resourceUrl.toURI().toString());
        } catch (URISyntaxException var4) {
            Logger.err(var4.getReason());
        }

        return theMedia;
    }

    public static Image loadImage(String image) {
        BufferedImage bufferedImage = null;

        try {
            bufferedImage = ImageIO.read(ResourceLocation.class.getResource(getResourceLocation() + image));
        } catch (IOException var3) {
            Logger.err(var3.getMessage());
        }

        Image fxImage = SwingFXUtils.toFXImage(bufferedImage, (WritableImage)null);
        return fxImage;
    }
    public static String resourceLocation = "/resources/";
    public static String getResourceLocation() {
        return resourceLocation;
    }
}

