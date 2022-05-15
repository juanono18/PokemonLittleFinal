package application;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import application.modelo.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MediaHandler {
	public Timeline soundEffectChooser(String src) {
		Media pick;
		pick = new Media(Paths.get(src).toUri().toString());
		MediaPlayer selectMPlayer = new MediaPlayer(pick);
		Timeline beat = new Timeline(
	            new KeyFrame(Duration.seconds(0.01),         event -> selectMPlayer.play()),
	            new KeyFrame(Duration.ZERO, event -> selectMPlayer.stop())
	        );
		return beat; 
	}
	
	public MediaPlayer soundChooser(String src) {
		Media pick;
		pick = new Media(Paths.get(src).toUri().toString());
		MediaPlayer selectMPlayer = new MediaPlayer(pick);
		return selectMPlayer; 
	}
	
	public Font fontChooser(String src,int size) {
		String fontURL = getClass().getResource(src).toExternalForm();
		Font font = Font.loadFont(fontURL, size);
		return font;
		
	}
	
	public void changeScreen(String nxtScreenSrc,Node node) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource(nxtScreenSrc));
		Stage stage = (Stage) node.getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.setTitle("Pokemon Little");
		stage.getIcons().add(new Image(getClass().getResource("res/Picon.png").toExternalForm()));
		stage.show();
		
	}



}
