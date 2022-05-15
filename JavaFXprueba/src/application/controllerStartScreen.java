package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.modelo.Conexion;
import application.modelo.Cuenta;
import application.modelo.Player;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class controllerStartScreen implements Initializable{
	
	@FXML
	Label dialogText,option1,option2,selector,
	nameChar,nameChar1,nameChar2,nameChar3,nameChar4,
	nameChar5,nameChar6,nameChar7,charBox,charBox1,charBox2,
	charBox3,charBox4,charBox5,charBox6,charBox7;
	
	@FXML
	ImageView profH,textBox;
	
	@FXML
	Rectangle chngRec;
	String gender;
	String name="";
	String username="";
	Boolean finishedtxt=false;
	Boolean alreadyRegistered=false;
	KeyEvent saveEvent;
	int lineCounter=-2;
	MediaHandler mh = new MediaHandler();
	MediaPlayer bkgMusic;
	int TypeStage=0;
	Conexion conex = new Conexion();
	
	FileWriter fw;
	@Override
	public void initialize(URL arg0,ResourceBundle arg1) {
		File saveFile = new File("src/application/files/saveFile.txt");
		try {
			fw = new FileWriter(saveFile);
			fw.write("");
			fw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dialogText.requestFocus();
		bkgMusic = mh.soundChooser("src/application/res/chatMusic.wav");
		bkgMusic.setCycleCount(Timeline.INDEFINITE);
		bkgMusic.play();
		profH.setLayoutX(profH.getLayoutX()-50);
		TranslateTransition translate = new TranslateTransition(Duration.millis(500));
		translate.setByX(50);
		;
			
		FadeTransition ft = new FadeTransition(Duration.millis(250));
		   ft.setFromValue(0.5);
		   ft.setToValue(1);
		   
		SequentialTransition seqT = new SequentialTransition(profH,translate,ft);
		    seqT.play();
		   
		dialogText.setFocusTraversable(true);
		Font font = mh.fontChooser("res/Minecraft.ttf",20);
		dialogText.setFont(font);
		option1.setFont(font);
		option2.setFont(font);
		selector.setFont(font);
		dialogText.wrapTextProperty();
		type("Hola, soy el profesor Hemlock, bienvenido al mundo de los pokemon.");
		 
		// TODO Auto-generated method stub
		
	}

	public void type(String str) {
    	mh.soundEffectChooser("src/application/res/textSound.wav").play();
    	dialogText.requestFocus();
		final IntegerProperty i = new SimpleIntegerProperty(0);
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(
                Duration.seconds(0.01),
                event -> {
					if (i.get() > str.length()) {
						finishedtxt = true;
                        timeline.stop();
                    } else {
                    	
                    	dialogText.setText(str.substring(0, i.get()));
                    	
                        i.set(i.get() + 1);
                    }
                });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
	}
	public void nextLine(KeyEvent e) throws IOException {
		if(lineCounter>-2) {
			nameChar.requestFocus();
		}

		if(e.getCode() == KeyCode.ENTER && finishedtxt) {
			
			switch (lineCounter) {
			
				case -2:
					lineCounter++;
					finishedtxt = false;
					type("Nos conocemos?, estas registrado el la PokedexNetwork?");
					return;
				case -1:
					lineCounter++;
					option1.setText("Si");
					option2.setText("No");
					showOptionChooser(true);
					return;
				
				case 0:
					lineCounter++;
					finishedtxt = false;
					if(alreadyRegistered) {
						
						type("Bueno pues dejame tus datos para iniciar sesion");

					}else {
						type("Bueno ya que estamos, te registrare en un momento!");
						
					}
					return;
				case 1:
					lineCounter++;
					finishedtxt = false;
					if(alreadyRegistered) {
						
						type("Bueno, pues dame tu PokeNet User!");

					}else {
						type("Primero dame tu PokeNet User, estas son tus credenciales!");
						
					}
					
					return;
					
				case 2:
					TypeStage++;
					lineCounter++;
					lineCounter++;
					showNameChooser(true);
					return;
				case 4:
					if(alreadyRegistered) {
						ArrayList <Cuenta> cuentas = conex.getCuentas();
						for(int i=0;i<cuentas.size();i++) {
							if(cuentas.get(i).getUsername().equals(username)) {
								fw.write(cuentas.get(i).getUsername());
								fw.flush();
								changeScreen();
								return;
								
							}
						}
						lineCounter=0;
						TypeStage=0;
						type("Parece que no encontramos tu cuenta... vuelve a intentarlo!");
						return;
						
						
					}else {
						
						lineCounter++;
						finishedtxt = false;
						type("Bueno, ahora que ya estas registrado, dejame preguntarte unos simples datos");
						return;
					}
				case 5:
					lineCounter++;
					finishedtxt = false;
					type("Primero cual es tu nombre?");
					return;
				case 6:
					TypeStage++;
					lineCounter++;
					showNameChooser(true);
					return;
				case 7:
					lineCounter++;
					finishedtxt = false;
					type("Y por ultimo dime, eres chico o chica?");
					return;
				case 8:
					lineCounter++;
					option1.setText("Chico");
					option2.setText("Chica");
					showOptionChooser(true);
					return;
				case 9:
					
					type("Bueno, ya esta todo listo, adelante entrenador!");
					conex.createCuenta(new Cuenta(username));
					conex.createPlayer(new Player(name, gender, 1, 0));
					conex.asignarCuentas();
					fw.write(username);
					fw.flush();
					changeScreen();
					return;
					
			}
			
		}
	}

	private void showNameChooser(Boolean vision) {
		Label[] characters= new Label[] {nameChar,nameChar1,nameChar2,nameChar3,nameChar4,nameChar5,nameChar6,nameChar7,charBox,
				charBox1,charBox2,charBox3,charBox4,charBox5,charBox6,charBox7};
		
		nameChar.focusedProperty();
		nameChar.requestFocus();
		
		
		// TODO Auto-generated method stub
		for(int i =0;i<characters.length;i++) {
			characters[i].setVisible(vision);

			if(vision) {
				Font characterF=mh.fontChooser("res/Minecraft.ttf",25);
				characters[i].setFont(characterF);
				
			}
		}

		
	}

	private void showOptionChooser(Boolean vision) {
		// TODO Auto-generated method stub
		option1.setVisible(vision);
		option2.setVisible(vision);
		selector.setVisible(vision);
		selector.requestFocus();
		
		if(vision) {
			FadeTransition blink = new FadeTransition(Duration.millis(500),selector);
				blink.setFromValue(0);
				blink.setToValue(1);
				blink.setAutoReverse(true);
				blink.setCycleCount(Timeline.INDEFINITE);
				blink.play();
		}
		
		
	}
	
	public void selectorMove(KeyEvent e) {
		saveEvent = e;
		double op1Y = option1.getLayoutY();
		double op2Y = option2.getLayoutY();
		if(e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN) {
			if(op1Y == selector.getLayoutY()) {
				selector.setLayoutY(op2Y);
				mh.soundEffectChooser("src/application/res/select.mp3").play();
				 
			}else if(op2Y == selector.getLayoutY()) {
				selector.setLayoutY(op1Y);
				mh.soundEffectChooser("src/application/res/select.mp3").play();
			}
		}
		if(e.getCode() == KeyCode.ENTER){
			mh.soundEffectChooser("src/application/res/confirmBeep.mp3").play();
			if(lineCounter==0) {
				if(op1Y == selector.getLayoutY()) {
					alreadyRegistered = true;
					type("Ya decia yo que me sonabas");
				}else if(op2Y == selector.getLayoutY()) {
					alreadyRegistered = false;
					type("Ay ,debo haberte confundido...");
				}
				showOptionChooser(false);
				return;
				
			}else {
				if(op1Y == selector.getLayoutY()) {
					gender = option1.getText();
				}else if(op2Y == selector.getLayoutY()) {
					gender = option2.getText();
				}
				type("Oh, conque eres "+gender+" eh!");
				finishedtxt = false;
				showOptionChooser(false);
				return;
				
			}
		}
	}
	
	public void nameTyper(KeyEvent e) {
		String keyTyped = ((Character)e.getCode().toString().charAt(e.getCode().toString().length()-1)).toString();
		Label writteableChars[] = new Label[] {nameChar,nameChar1,nameChar2,nameChar3,nameChar4,nameChar5,nameChar6,nameChar7};
		for(Label label : writteableChars) {
			if(label.getText().equals("") && (keyTyped).matches("([a-zA-Z0-9])\\1*")&& (e.getCode().isLetterKey()||e.getCode().isDigitKey())) {
				label.setText(keyTyped);
				mh.soundEffectChooser("src/application/res/select.mp3").play();
				return;
			}
		}
		if(e.getCode() == KeyCode.BACK_SPACE) {
			for(int i=writteableChars.length-1;i>=0;i--) {
				if(!writteableChars[i].getText().equals("")) {
					writteableChars[i].setText("");
					mh.soundEffectChooser("src/application/res/select.mp3").play();
					return;
				}
			}
		}
		if(e.getCode() == KeyCode.ENTER) {
			String str="";
			mh.soundEffectChooser("src/application/res/confirmBeep.mp3").play();
			for(Label label : writteableChars) {
				str+=label.getText().toLowerCase();
				
			}
			switch (TypeStage){
				case 1:
					for(int i=writteableChars.length-1;i>=0;i--) {
						writteableChars[i].setText("");
					}
					if(alreadyRegistered) {
						type("Okay, te iniciare sesion ahora");
						
					}else {
						type("Vale creare tu cuenta, recuerda tus credenciales!");
						
						
					}
					username = str;
					showNameChooser(false);
					return;
				case 2:
					type(str+" es un bonito nombre!");
					name=str;
					showNameChooser(false);
					return;
					
					
			}
			
			
			finishedtxt = false;
			
		}
	
		
	}
	
	public void changeScreen() throws IOException {
		
		FadeTransition ft = new FadeTransition(Duration.millis(250),chngRec);
		   ft.setFromValue(0);
		   ft.setToValue(1);
			Timeline changeScreenT = new Timeline(
					new KeyFrame(Duration.seconds(0.1), event -> {bkgMusic.stop();mh.soundEffectChooser("src/application/res/mainMenuBeep.wav").play();}),
		            new KeyFrame(Duration.seconds(0.25), event -> ft.play()),
		            new KeyFrame(Duration.seconds(1.5), event -> {
						try {
							
							mh.changeScreen("vista/MenuScreen.fxml",dialogText);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					})
		        );
			changeScreenT.play();
		
	}


}