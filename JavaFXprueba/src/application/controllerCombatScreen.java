package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import application.modelo.Player;
import application.modelo.Pokemon;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class controllerCombatScreen implements Initializable{

	MediaHandler mh = new MediaHandler();
	DataHandler dh = new DataHandler();
	MediaPlayer bkgMusic = mh.soundChooser("src/application/res/combatMusic.mp3");
	
	@FXML
	Pane vsPanel,chngPokemonPane,atackPane,exitPane;
	@FXML
	ImageView playerPok,advPok,player,chngPokBut,attkBut,chngPokImg1,chngPokImg2,backBut,
	playerPokType1,playerPokType2,advPokType1,advPokType2,atkk1Type,atkk2Type,atkk3Type,atkk4Type;
	@FXML
	ProgressBar playerPokPB,advPokPB;
	@FXML
	Label playerPokLabel,advPokLabel,playerLabel,advLabel,attk1lbl,attk2lbl,attk3lbl,attk4lbl,runLbl,chngLbl,attkLbl;
	ArrayList<Pokemon> pokBag;
	ArrayList<Pokemon> enemyPok;
	File f = new File("src/application/files/playerPok.txt");
	Player ply = new Player("","",0,0);
	FileReader fr;
	BufferedReader bfr;
	Boolean hasBeenPressedCP=false;
	Boolean hasBeenPressedPAL=false;
	String turn="";
	Pokemon currPlyPok;
	Pokemon currAdvPok;
	Boolean hasBeenChanged =false;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Label[] labels = new Label[] {playerPokLabel,advPokLabel,playerLabel,advLabel,attk1lbl,attk2lbl,attk3lbl,attk4lbl,runLbl,chngLbl,attkLbl};
		for(int i=0;i<labels.length;i++) {
			labels[i].setFont(mh.fontChooser("res/Minecraft.ttf",12));
		}
		try {
			fr = new FileReader(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bfr = new BufferedReader(fr);
		ply = dh.getPlayer();
		enemyPok = dh.getEnemyPokemon();
		try {
			pokBag = dh.getPokemonFromTxt(ply,bfr.readLine());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException | SQLException
				| IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(ply.getGender().equals("Chica")) {
			player.setImage(new Image(getClass().getResource("res/CharacterGirl.gif").toExternalForm()));
		}
		playerLabel.setText(ply.getName()+" Lvl."+ply.getLvl());
		advLabel.setText("BOT Lvl."+ply.getLvl());
		

		vsPanel.setOpacity(1);
		bkgMusic.setCycleCount(Timeline.INDEFINITE);
		bkgMusic.play();
		
		FadeTransition ft = new FadeTransition(Duration.millis(750),vsPanel);
		   ft.setFromValue(1);
		   ft.setToValue(0);
		   
		PauseTransition pt = new PauseTransition(Duration.millis(4000));
		
		Timeline enterAnim = new Timeline(
				new KeyFrame(Duration.seconds(4), event -> {pt.play();}),
	            new KeyFrame(Duration.seconds(4.75), event -> {ft.play();}),
	            new KeyFrame(Duration.seconds(5), event -> {vsPanel.setDisable(true);})
	        );
		enterAnim.play();
		
		
		changeAdvPokemon(enemyPok.get(0));
		changeThePokemon(pokBag.get(0));
		updateHp();
		if(pokBag.get(0).getVelocidad()>enemyPok.get(0).getVelocidad()) {
			turn="p1";
		}else {
			turn="p2";
			try {
				advAttackMethod();
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		turnCheck();
		
	}
	public void updateHp() {	
		if(currPlyPok.getCurrentHp() < 0) {
			currPlyPok.setCurrentHp(0);
		}
		if(currAdvPok.getCurrentHp() < 0) {
			currAdvPok.setCurrentHp(0);
		}
		playerPokPB.setProgress((double)currPlyPok.getCurrentHp()/(double)currPlyPok.getVida());
		advPokPB.setProgress((double)currAdvPok.getCurrentHp()/(double)currAdvPok.getVida());
	}
	public void changeThePokemon(Pokemon pok) {
		hasBeenChanged=true;
		currPlyPok=pok;
		playerPokPB.setProgress(pok.getCurrentHp());
		String pokeName = dh.getPokemonName(pok.getClass().toString());
		playerPok.setImage(new Image(getClass().getResource("res/pokemonSprites/"+pokeName.toLowerCase()+"B.gif").toExternalForm()));
		playerPokLabel.setText(pokeName+" \""+pok.getAlias()+"\" Lvl."+pokBag.get(0).getNivel());
		attk1lbl.setText(dh.getPrettyAtackName(pok.getAttk1()));
		attk2lbl.setText(dh.getPrettyAtackName(pok.getAttk2()));
		attk3lbl.setText(dh.getPrettyAtackName(pok.getAttk3()));
		attk4lbl.setText(dh.getPrettyAtackName(pok.getAttk4()));
		if(pok.getClass().getInterfaces().length < 1) {
			playerPokType1.setImage(new Image(getClass().getResource("res/TypesImg/normalT.png").toExternalForm()));
			playerPokType2.setImage(null);
		}else {
			if(pok.getClass().getInterfaces().length > 1) {
				playerPokType1.setImage(new Image(getClass().getResource(
						"res/TypesImg/"+pok.getClass().getInterfaces()[0].getName().substring(23).toLowerCase()+"T.png").toExternalForm()));
				playerPokType2.setImage(new Image(getClass().getResource(
						"res/TypesImg/"+pok.getClass().getInterfaces()[1].getName().substring(23).toLowerCase()+"T.png").toExternalForm()));
			}else {
				playerPokType1.setImage(new Image(getClass().getResource(
						"res/TypesImg/"+pok.getClass().getInterfaces()[0].getName().substring(23).toLowerCase()+"T.png").toExternalForm()));
				playerPokType2.setImage(null);
			}
		}
		int imgCounter=1;
		for(int a=0;a<pokBag.size();a++) {
			String currName = dh.getPokemonName(pokBag.get(a).getClass().toString());
			
			
			if(!currName.equals(pokeName)){
				System.out.println(currName+" "+pokeName);
				if(imgCounter==1) {
					if(pokBag.get(a).getCurrentHp()<=0) {
						chngPokImg1.setDisable(true);
						chngPokImg1.setImage(null);
						imgCounter++;
					}else {
						chngPokImg2.setDisable(false);
						chngPokImg1.setImage(new Image(getClass().getResource("res/pokemonSprites/"+currName.toLowerCase()+".gif").toExternalForm()));
						imgCounter++;
					}
				}
				else {
					if(pokBag.get(a).getCurrentHp()<=0) {
						chngPokImg1.setDisable(true);
						chngPokImg1.setImage(null);
					}else {
						chngPokImg2.setDisable(false);
						chngPokImg2.setImage(new Image(getClass().getResource("res/pokemonSprites/"+currName.toLowerCase()+".gif").toExternalForm()));
					}
				}
			}
		}
		
		//Tipos de los ataques del jugador
		setAtackTypes(pok);
	}
	
	public void changeAdvPokemon(Pokemon pok) {
		currAdvPok = pok;
		advPokPB.setProgress(pok.getCurrentHp());
		//Imagen nombre y otras cosas pokemon adv
				String advPokeName = dh.getPokemonName(pok.getClass().toString());
				advPokLabel.setText(advPokeName+" Lvl."+pok.getNivel());
				advPok.setImage(new Image(getClass().getResource("res/pokemonSprites/"+advPokeName.toLowerCase()+".gif").toExternalForm()));
				//Imagen nombre, ataque y otras cosas pokemon del jugador
				
				//Tipos del Pokemon del jugador
				
				//Tipos del Pokemon del adversario
				if(pok.getClass().getInterfaces().length < 1) {
					advPokType1.setImage(new Image(getClass().getResource("res/TypesImg/normalT.png").toExternalForm()));
					advPokType2.setImage(null);
				}else {
					if(pok.getClass().getInterfaces().length > 1) {
						advPokType1.setImage(new Image(getClass().getResource(
								"res/TypesImg/"+pok.getClass().getInterfaces()[0].getName().substring(23).toLowerCase()+"T.png").toExternalForm()));
						advPokType2.setImage(new Image(getClass().getResource(
								"res/TypesImg/"+pok.getClass().getInterfaces()[1].getName().substring(23).toLowerCase()+"T.png").toExternalForm()));
					}else {
						advPokType1.setImage(new Image(getClass().getResource(
								"res/TypesImg/"+pok.getClass().getInterfaces()[0].getName().substring(23).toLowerCase()+"T.png").toExternalForm()));
						advPokType2.setImage(null);
					}
				}
	}
	
	private void setAtackTypes(Pokemon pokemon) {
		// TODO Auto-generated method stub
		String[] atkks = new String[] {pokemon.getAttk1(),pokemon.getAttk2(),pokemon.getAttk3(),pokemon.getAttk4()};
		ImageView[] attkTypes = new ImageView[] {atkk1Type,atkk2Type,atkk3Type,atkk4Type};
		int numInterfaces=pokemon.getClass().getInterfaces().length;
		ArrayList<String> types = new ArrayList<String>();
		for(String s:atkks) {
			switch(numInterfaces) {
				case 0:
					for(int a=0;a<4;a++) {
						types.add("Normal");
					}
					break;
				case 1:
					try {
						types.add(pokemon.getClass().getInterfaces()[0].getMethod(s).toString().substring(43).split("\\.")[0]);	
					} catch (NoSuchMethodException | SecurityException e1) {
						// TODO Auto-generated catch block
						types.add("Normal");
					}
					break;
				case 2:
					try {
						types.add(pokemon.getClass().getInterfaces()[0].getMethod(s).toString().substring(43).split("\\.")[0]);	
					} catch (NoSuchMethodException | SecurityException e) {
						try {
							types.add(pokemon.getClass().getInterfaces()[1].getMethod(s).toString().substring(43).split("\\.")[0]);
						} catch (NoSuchMethodException | SecurityException e1) {
							types.add("Normal");
						}
					}
						
					break;			
						
				}
			
		}
		
		for(int i=0;i<4;i++) {
			System.out.println(types.get(i));
			attkTypes[i].setImage(new Image(getClass().getResource("res/TypesImg/"+types.get(i).toLowerCase()+"T.png").toExternalForm()));
		}
		//pokemon
		
	}
	public void attackMethod(MouseEvent e) {
		Label eventElement = (Label) e.getSource();
		hasBeenChanged=false;
		try {
			String methodName= dh.unPrettyAtackName(eventElement.getText());
			Object method = currPlyPok.getClass().getMethod(methodName).invoke(currPlyPok);
			String[] ataques = new String[] {currPlyPok.getAttk1(),currPlyPok.getAttk2(),currPlyPok.getAttk3(),currPlyPok.getAttk4()};
			String typ="";
			for(int i=0;i<ataques.length;i++) {
				if(ataques[i].equals(methodName)){
					typ=ataques[i];
					break;
				}
			}
			int a = (int) method;
			int tDMG= dh.getTrueDamage(currPlyPok,a,typ);
			currAdvPok.setCurrentHp(currAdvPok.getCurrentHp()-tDMG);
			turn = "p2";
			turnCheck();
			
			
			
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		}
	}
	public void advAttackMethod() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		if(enemyPok.size()>=0) {
			for(int i=0;i<enemyPok.size();i++) {
				if(enemyPok.get(i).getCurrentHp()<=0) {
					enemyPok.remove(i);
					if(enemyPok.size()>0) {
						changeAdvPokemon(enemyPok.get(0));	
					}else {
						turnCheck();
						return;
					}
				}
			}
			if(hasBeenChanged) {
				Pokemon a = dh.getBestPokemon(enemyPok,currPlyPok,currAdvPok);
				System.out.println(a.getCurrentHp());
				changeAdvPokemon(a);
				
			}
			else {
				Object method;
				int a = 0;
				ArrayList<String> attackTypes = dh.getAtackTypes(currAdvPok);
				String attackType="";
				switch(dh.getBestAtack(attackTypes, currPlyPok)) {
				case 0:
					method = currAdvPok.getClass().getMethod(currAdvPok.getAttk1()).invoke(currAdvPok);
					a = (int) method;
					attackType=attackTypes.get(0);
					break;
				case 1:
					method = currAdvPok.getClass().getMethod(currAdvPok.getAttk2()).invoke(currAdvPok);
					a = (int) method;
					attackType=attackTypes.get(1);
					break;
				case 2:
					method = currAdvPok.getClass().getMethod(currAdvPok.getAttk3()).invoke(currAdvPok);
					a = (int) method;
					attackType=attackTypes.get(2);
					break;
				case 3:
					method = currAdvPok.getClass().getMethod(currAdvPok.getAttk4()).invoke(currAdvPok);
					a = (int) method;
					attackType=attackTypes.get(3);
					break;
				}
				int tDMG= dh.getTrueDamage(currPlyPok,a,attackType);
				System.out.println("Dmg "+tDMG);
				System.out.println("VIDA: "+currPlyPok.getCurrentHp());
				currPlyPok.setCurrentHp(currPlyPok.getCurrentHp()-tDMG);
				System.out.println("VIDA: "+currPlyPok.getCurrentHp());
				
			}
			turn = "p1";
			updateHp();
			turnCheck();
			
		}else {
			turnCheck();
		}
	}
	public void changeToPokemon(MouseEvent e) {
		ImageView eventElement = (ImageView) e.getSource();
		String[] a=eventElement.getImage().getUrl().split("\\/");
		System.out.println(eventElement.getImage().getUrl());
		for(Pokemon pok:pokBag) {
			if(dh.getPokemonName(pok.getClass().toString().toLowerCase()).equals(eventElement.getImage().getUrl().split("\\/")[a.length-1].split("\\.")[0])) {
				changeThePokemon(pok);
				turn = "p2";
				turnCheck();
				try {
					advAttackMethod();
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			}
		}
	}
	public void changePokemon(MouseEvent e) {
		if(!hasBeenPressedCP) {
			chngPokBut.setImage(new Image(getClass().getResource("res/emptyFramePressed.png").toExternalForm()));
			TranslateTransition translate = new TranslateTransition(Duration.millis(200), chngPokemonPane);
			translate.setByY(-90);
			translate.play();
			hasBeenPressedCP=true;
			
		}else {
			chngPokBut.setImage(new Image(getClass().getResource("res/emptyFrame.png").toExternalForm()));
			TranslateTransition translate = new TranslateTransition(Duration.millis(200), chngPokemonPane);
			translate.setByY(90);
			translate.play();
			hasBeenPressedCP=false;
		}
		
	}
	public void pokemonAtackList(MouseEvent e) {
		if(!hasBeenPressedPAL) {
			attkBut.setImage(new Image(getClass().getResource("res/emptyFramePressed.png").toExternalForm()));
			TranslateTransition translate = new TranslateTransition(Duration.millis(200), atackPane);
			translate.setByY(-100);
			translate.play();
			hasBeenPressedPAL=true;
			
		}else {
			attkBut.setImage(new Image(getClass().getResource("res/emptyFrame.png").toExternalForm()));
			TranslateTransition translate = new TranslateTransition(Duration.millis(200), atackPane);
			translate.setByY(100);
			translate.play();
			hasBeenPressedPAL=false;
		}
		
	}
	public void turnCheck() {
		if(turn.equals("p1")) {
			int endMatch = 0;
			for(Pokemon pok: pokBag) {
				if(pok.getCurrentHp()<=0) {
					endMatch++;
				}
			}
			if(endMatch==3) {
				Random ran = new Random();
				dh.upExp(ply,10+ran.nextInt(10));
				runAway(null);
			}
			if(currPlyPok.getCurrentHp()>0) {
				attkBut.setDisable(false);
				atackPane.setOpacity(1);
				atackPane.setDisable(false);
			}else {
				atackPane.setOpacity(0);
				atackPane.setDisable(true);
			}
			chngPokBut.setDisable(false);
			backBut.setDisable(false);
		}else {
			if(enemyPok.size()==0) {
				Random ran = new Random();
				dh.upExp(ply,50+ran.nextInt(100));
				runAway(null);
			}
			attkBut.setDisable(true);
			chngPokBut.setDisable(true);
			backBut.setDisable(true);
			try {
				advAttackMethod();
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			updateHp();
		}
	}
	public void runAway(MouseEvent e) {
		if(e != null) {
			ImageView eventElement = (ImageView) e.getSource();
			eventElement.setImage(new Image(getClass().getResource("res/emptyFramePressed.png").toExternalForm()));
			
		}
		FadeTransition ft = new FadeTransition(Duration.millis(250),exitPane);
		   	ft.setFromValue(0);
		   	ft.setToValue(1);
		   	ft.setAutoReverse(true);

		  
			Timeline changeScreenT = new Timeline(
					new KeyFrame(Duration.seconds(0.1), event -> {bkgMusic.stop();mh.soundEffectChooser("src/application/res/mainMenuBeep.wav").play();}),
		            new KeyFrame(Duration.seconds(0.35), event -> {ft.play();}),
		            new KeyFrame(Duration.seconds(0.35), event -> {ft.play();}),
		            new KeyFrame(Duration.seconds(3), event -> {
						try {
							
							mh.changeScreen("vista/MenuScreen.fxml",exitPane);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					})
		        );
			changeScreenT.play();
	}

}
