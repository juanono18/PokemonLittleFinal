package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import application.modelo.Conexion;
import application.modelo.Player;
import application.modelo.Pokemon;
import application.modelo.pokemons.Charizard;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class controllerMenuScreen implements Initializable {
	@FXML
	Pane pokeInv;
	@FXML
	ImageView vsaiBut,playerSprite,exitBut,pokeBut,expBar,extImg,extTxt,
	lvl11,lvl12,lvl13,lvl21,lvl22,lvl23,lvl31,lvl32,lvl33,lvl41,lvl42,lvl43,lvl51,sel1,sel2,sel3,viewPok,pokType1,pokType2;
	@FXML
	Label playerInfo,pokNamLvl,pokPs,pokAttk,pokDef,pokVel,Attk1lbl,Attk2lbl,Attk3lbl,Attk4lbl;
	@FXML
	Rectangle extRec;
	
	
	
	
	MediaHandler mh = new MediaHandler();
	DataHandler dh = new DataHandler();
	MediaPlayer bkgMusic = mh.soundChooser("src/application/res/menuMusic.wav");
	Player ply;
	Conexion conex = new Conexion();
	Boolean hasBeenPressed=false;
	ArrayList<Pokemon> arrP;
	ArrayList<Image> selP;
	FileWriter fw;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		File saveFile = new File("src/application/files/playerPok.txt");
		try {
			fw = new FileWriter(saveFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updateData();
		updateExperience();
		pokeInv.setLayoutX(600);
		bkgMusic.setCycleCount(Timeline.INDEFINITE);
		bkgMusic.play();
		playerInfo.setAlignment(Pos.CENTER);
		playerInfo.setFont(mh.fontChooser("res/Minecraft.ttf",20));
		playerInfo.setText(ply.getName()+" Lvl."+ply.getLvl());
	}
	public void updateData() {	
		try {
			ply = dh.getPlayer();
			arrP = dh.getPlayerPokemon(ply);
			getInvPokemon();
			setSpriteGender();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void updateExperience() {
		ply = dh.getPlayer();
		playerInfo.setText(ply.getName()+" Lvl."+ply.getLvl());
		int currExp = ply.getExp();
		int totExpNeed = (ply.getLvl())*100;
		int currProg = (int) Math.round(((double)currExp/(double)totExpNeed)*10);
		if(currProg>=1) {
			if(currProg>10) {	
				dh.lvlUp(ply);
				updateExperience();
				updateData();
				return;
			}
			expBar.setImage(new Image(getClass().getResource("res/ProgBar/ProgBar"+currProg+".png").toExternalForm()));
		
		}
		else{
			expBar.setImage(new Image(getClass().getResource("res/ProgBar/ProgBar.png").toExternalForm()));
		}
		
	}
	public void setSpriteGender() {
		if(ply.getGender().equals("Chica")) {
			playerSprite.setImage(new Image(getClass().getResource("res/CharacterGirl.gif").toExternalForm()));
		}
	}
	public void butPress(MouseEvent e) {
		ImageView element = (ImageView) e.getSource();
		String iD = element.getId();
		if(iD.equals("vsaiBut")) {
			element.setImage(new Image(getClass().getResource("res/VSAIbuttonPressed.png").toExternalForm()));
			searchMatch();
		}
		if(iD.equals("exitBut")) {
			element.setImage(new Image(getClass().getResource("res/exitButPressed.png").toExternalForm()));
			returnToMainMenu();
			
		}
		
	}
	public void butRelease(MouseEvent e) {
		ImageView element = (ImageView) e.getSource();
		String iD = element.getId();
		if(iD.equals("vsaiBut")) {
			element.setImage(new Image(getClass().getResource("res/VSAIbutton.png").toExternalForm()));
		}
		if(iD.equals("exitBut")) {
			element.setImage(new Image(getClass().getResource("res/exitBut.png").toExternalForm()));
			
		}
		
	}
	public void pokeInvBut(MouseEvent e) throws InterruptedException {
		if(hasBeenPressed) {
			pokeBut.setImage(new Image(getClass().getResource("res/pokeBut.png").toExternalForm()));
			TranslateTransition translate = new TranslateTransition(Duration.millis(250), pokeInv);
			translate.setByX(600);
			translate.play();
			hasBeenPressed=false;
			return;
			
		}else {
			
			pokeBut.setImage(new Image(getClass().getResource("res/pokeButPressed.png").toExternalForm()));
			TranslateTransition translate = new TranslateTransition(Duration.millis(250), pokeInv);
			translate.setByX(-604);
			translate.play();
			hasBeenPressed=true;
			return;
		}
	}
	

	public void getInvPokemon() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		Label[] labels = new Label[]{pokNamLvl,pokPs,pokAttk,pokDef,pokVel,Attk1lbl,Attk2lbl,Attk3lbl,Attk4lbl};
		ImageView[] lvlPok= new ImageView[] {lvl11,lvl12,lvl13,lvl21,lvl22,lvl23,lvl31,lvl32,lvl33,lvl41,lvl42,lvl43,lvl51};
		for(int i=0;i<labels.length;i++) {
			labels[i].setFont(mh.fontChooser("res/Minecraft.ttf",11));
		}
		if(ply.getLvl() == 5) {
			if(arrP.size()<13) {
			dh.createRanPokemonSet(5, arrP);
			dh.setInvImages(arrP, lvlPok);
			updateData();
			}else {
				dh.setInvImages(arrP, lvlPok);
			}
		}else {
			for(int a=1;a<=4;a++) {
				if(ply.getLvl() == a) {
					if(arrP.size()>=(a*3)) {
						dh.setInvImages(arrP, lvlPok);
						break;
					}
					else {
						dh.createRanPokemonSet(a, arrP);
						updateData();
					}
				}
			}
			
		}

	}
		
	
	
	public void selectPok(MouseEvent e) {
		ImageView[] selectores= new ImageView[] {sel1,sel2,sel3};
		ImageView eventElement = (ImageView) e.getSource();
		if(e.getButton().equals(MouseButton.SECONDARY)) {
			viewPok.setImage(eventElement.getImage());
			Pokemon selPok = dh.getPokemonFromImage(eventElement,arrP);
			String PokeBS2 = selPok.getClass().toString();
			String[] PokeS12 = PokeBS2.split("\\.");
			String[] PokeS22 = PokeS12[3].split("\\@");
			pokNamLvl.setText(PokeS22[0]+" Lvl."+selPok.getNivel());
			Attk1lbl.setText("Atk1: "+dh.getPrettyAtackName(selPok.getAttk1()));
			Attk2lbl.setText("Atk2: "+dh.getPrettyAtackName(selPok.getAttk2()));
			Attk3lbl.setText("Atk3: "+dh.getPrettyAtackName(selPok.getAttk3()));
			Attk4lbl.setText("Atk4: "+dh.getPrettyAtackName(selPok.getAttk4()));
			pokPs.setText("ps "+selPok.getVida()+"/"+selPok.getVida());
			pokAttk.setText("atk "+selPok.getAtaque());
			pokDef.setText("def "+selPok.getDefensa());
			pokVel.setText("vel "+selPok.getVelocidad());
			if(selPok.getClass().getInterfaces().length < 1) {
				pokType1.setImage(new Image(getClass().getResource("res/TypesImg/normalT.png").toExternalForm()));
				pokType2.setImage(null);
			}else {
				if(selPok.getClass().getInterfaces().length > 1) {
					pokType1.setImage(new Image(getClass().getResource(
							"res/TypesImg/"+selPok.getClass().getInterfaces()[0].getName().substring(23).toLowerCase()+"T.png").toExternalForm()));
					pokType2.setImage(new Image(getClass().getResource(
							"res/TypesImg/"+selPok.getClass().getInterfaces()[1].getName().substring(23).toLowerCase()+"T.png").toExternalForm()));
				}else {
					pokType1.setImage(new Image(getClass().getResource(
							"res/TypesImg/"+selPok.getClass().getInterfaces()[0].getName().substring(23).toLowerCase()+"T.png").toExternalForm()));
					pokType2.setImage(null);
				}
			}
			return;
		}
		if(e.getButton().equals(MouseButton.PRIMARY)){
			for(int i=0;i<selectores.length;i++) {
				if(selectores[i].getImage() == null) {
					for(int a=0;a<selectores.length;a++) {
						if(selectores[a].getImage() != null){
							if(selectores[a].getImage().equals(eventElement.getImage())) {
								return;
							}
						}
					}
					selectores[i].setImage(eventElement.getImage());
		
					
					break;
				}
			}
		}
			
	}
	public String getSelectedPokemon() {
		ImageView[] selectores= new ImageView[] {sel1,sel2,sel3};
		String currPok="";
		String pokemon="";
		for(int i=0;i<selectores.length;i++) {
			String PokeBS = selectores[i].getImage().getUrl();
			String[] PokeS1 = PokeBS.split("\\/");
			String[] PokeS2 = PokeS1[PokeS1.length-1].split("\\.");
			currPok = PokeS2[0].substring(0, 1).toUpperCase() + PokeS2[0].substring(1);

			pokemon += currPok+"|";
		}
		System.out.println(pokemon);
		return pokemon;
	}
	
	public void deSelectPok(MouseEvent e) {
		ImageView eventElement = (ImageView) e.getSource();
		eventElement.setImage(null);
	}
	public void searchMatch() {
		try {
			fw.write(getSelectedPokemon());
			fw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		extTxt.setImage(new Image(getClass().getResource("res/matchSearchTxt.png").toExternalForm()));
		extRec.setLayoutX(-3);
		FadeTransition ft = new FadeTransition(Duration.millis(250),extRec);
		   	ft.setFromValue(0);
		   	ft.setToValue(1);
		   	ft.setAutoReverse(true);
		FadeTransition ft2 = new FadeTransition(Duration.millis(300),extImg);
			ft2.setFromValue(0);
			ft2.setToValue(1);
			ft2.setAutoReverse(true);
		FadeTransition ft3 = new FadeTransition(Duration.millis(350),extTxt);
			ft3.setFromValue(0);
			ft3.setToValue(1);
			ft3.setAutoReverse(true);
		  
			Timeline changeScreenT = new Timeline(
					new KeyFrame(Duration.seconds(0.1), event -> {bkgMusic.stop();mh.soundEffectChooser("src/application/res/mainMenuBeep.wav").play();}),
		            new KeyFrame(Duration.seconds(0.35), event -> {ft.play();ft2.play();ft3.play();}),
		            new KeyFrame(Duration.seconds(0.35), event -> {ft.play();ft2.play();ft3.play();}),
		            new KeyFrame(Duration.seconds(3), event -> {
						try {
							
							mh.changeScreen("vista/CombateScreen.fxml",extRec);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					})
		        );
			changeScreenT.play();
		
		
	}
	public void returnToMainMenu() {
		extRec.setLayoutX(-3);
		FadeTransition ft = new FadeTransition(Duration.millis(250),extRec);
		   	ft.setFromValue(0);
		   	ft.setToValue(1);
		   	ft.setAutoReverse(true);
		FadeTransition ft2 = new FadeTransition(Duration.millis(300),extImg);
			ft2.setFromValue(0);
			ft2.setToValue(1);
			ft2.setAutoReverse(true);
		FadeTransition ft3 = new FadeTransition(Duration.millis(350),extTxt);
			ft3.setFromValue(0);
			ft3.setToValue(1);
			ft3.setAutoReverse(true);
		  
			Timeline changeScreenT = new Timeline(
					new KeyFrame(Duration.seconds(0.1), event -> {bkgMusic.stop();mh.soundEffectChooser("src/application/res/mainMenuBeep.wav").play();}),
		            new KeyFrame(Duration.seconds(0.35), event -> {ft.play();ft2.play();ft3.play();}),
		            new KeyFrame(Duration.seconds(0.35), event -> {ft.play();ft2.play();ft3.play();}),
		            new KeyFrame(Duration.seconds(3), event -> {
						try {
							
							mh.changeScreen("vista/Main.fxml",extRec);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					})
		        );
			changeScreenT.play();
		
		
	}
	

	

}
