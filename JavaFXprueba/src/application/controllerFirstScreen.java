package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class controllerFirstScreen implements Initializable{
	
	@FXML
	private ImageView StrtBut;
	@FXML
	private ImageView title;
	@FXML
	private ImageView bkg;
	@FXML
	private Rectangle wRec;
	
	
	MediaHandler mh = new MediaHandler();
	MediaPlayer mainMusic;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadScreen();

	}
	
	private void loadScreen() {
		
		
		
		
		// TODO Auto-generated method stub
	
	
		mainMusic=mh.soundChooser("src/application/res/IntroMusic.mp3");
		mainMusic.play();
        
		StrtBut.setFocusTraversable(true);
		title.setLayoutY(300);
	
		TranslateTransition translate = new TranslateTransition(Duration.millis(2000), title);
			translate.setByY(-300);
			translate.play();
					
			PauseTransition pt = new PauseTransition(Duration.millis(2000));
				
		FadeTransition ft = new FadeTransition(Duration.millis(250));
			   ft.setFromValue(0);
			   ft.setToValue(1);
			    	
		SequentialTransition seqT = new SequentialTransition(bkg,pt,ft);
			    seqT.play();
			    
		FadeTransition blink = new FadeTransition(Duration.millis(500));
			   	blink.setFromValue(0);
			    blink.setToValue(1);
			    blink.setAutoReverse(true);
			    blink.setCycleCount(Timeline.INDEFINITE);
		SequentialTransition seqT2 = new SequentialTransition(StrtBut,pt,blink);
			   	seqT2.play();
			   	
		
	}
	
	public void changeScreen(KeyEvent e) throws IOException { 
		FadeTransition ft = new FadeTransition(Duration.millis(250),wRec);
		   ft.setFromValue(0);
		   ft.setToValue(1);
		if(e.getCode() == KeyCode.ENTER && bkg.getOpacity()==1) {
			Timeline changeScreenT = new Timeline(
					new KeyFrame(Duration.seconds(0.1), event -> {mainMusic.stop();mh.soundEffectChooser("src/application/res/mainMenuBeep.wav").play();}),
		            new KeyFrame(Duration.seconds(0.25), event -> ft.play()),
		            new KeyFrame(Duration.seconds(1.5), event -> {
						try {
							
							mh.changeScreen("vista/StartScreen.fxml",StrtBut);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					})
		        );
			changeScreenT.play();
			return;
			
			
			
		}
		
	}
	
}
