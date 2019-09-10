package fr.warmadon.dev.gui;

import java.text.SimpleDateFormat;
import java.util.Date;

import fr.warmadon.dev.Bot;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GUIJFX extends Application {
    public static ResourceLocation resources;
    public static String appname = "Yuti", appprefix = "[Yuti]";

    private static int appWidth;
    private static int appHeight;
    
    private GUIJFX gui;
    
    public GUIJFX(Bot bot) {
      bot = bot;
	}


	public static ResourceLocation getResourceLocation() {

        return resources;
    }

	
	@Override
	public void start(Stage stage) throws Exception {
		
        Logger.write(appname + " started!");

        Logger.write(appname + " version: " + 1.0);

        Logger.write( "OS : " + System.getProperty("os.name"));
        Logger.write( "OS Java Arch : " + System.getProperty("os.arch"));
        Logger.write( "OS Version : " + System.getProperty("os.version"));
        Logger.write( "Username : " + System.getProperty("user.name"));
        Logger.write( "Java Vendor : " + System.getProperty("java.vendor"));
        Logger.write( "Java Version : " + System.getProperty("java.version"));
        Logger.write( "Java Home : " + System.getProperty("java.home"));
        Logger.write( "Java Classpath : " + System.getProperty("java.class.path"));
        Logger.write( "Available processors (cores): " + Runtime.getRuntime().availableProcessors());
        Logger.write( "Total memory (bytes): " + Runtime.getRuntime().totalMemory());
        Logger.write( "Date: " + new SimpleDateFormat("hh").format(new Date()) + "H" + new SimpleDateFormat("mm dd/MM/yyyy").format(new Date()));

        Scene scene = new Scene(new AppPanel().createContent());
        utilsbase base = new utilsbase(stage, scene, StageStyle.UNDECORATED, Mover.MOVE_ON_CLICK);
        base.setIconImage(stage, resources.loadImage("favicon.png"));
        new AppAlert("test", Alert.AlertType.ERROR);

    }

    public static int getWidth() {
        return appWidth;
    }

    public static int getHeight() {
        return appHeight;
    }

    public static void drawBackgroundMp4(Pane root, String media) {
        MediaPlayer player = new MediaPlayer(ResourceLocation.getMedia(media));
        MediaView viewer = new MediaView(player);
        viewer.setFitWidth((double)GUIJFX.getWidth());
        viewer.setFitHeight((double)GUIJFX.getHeight());
        player.setAutoPlay(true);
        player.setMute(true);
        viewer.setPreserveRatio(false);
        player.setCycleCount(-1);
        player.play();
        root.getChildren().add(viewer);
    }



}
