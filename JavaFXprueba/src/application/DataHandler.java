package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import application.modelo.Conexion;
import application.modelo.Player;
import application.modelo.Pokemon;
import application.modelo.tablaTipos;
import application.modelo.pokemons.*;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DataHandler {
	public String getPrettyAtackName(String name) {
		String finishName="";
		for(int i=0;i<name.length();i++) {
			if(i+1 != name.length()) {
				if(Character.isUpperCase(name.charAt(i+1))) {
					finishName+=Character.toLowerCase(name.charAt(i));
					finishName+=" ";
				}
				else {
					finishName+=Character.toLowerCase(name.charAt(i));
					
				}
				
			}
			else {
				finishName+=Character.toLowerCase(name.charAt(i));
			}
			
		}
		return finishName;
		
	}
	public String unPrettyAtackName(String name) {
		String finishName="";
		for(int i=0;i<name.length();i++) {
			if(i+1 != name.length()) {
				if(name.charAt(i+1) == ' ') {
					finishName+=Character.toLowerCase(name.charAt(i));
					finishName+=Character.toUpperCase(name.charAt(i+2));
					i=i+2;
				}
				else {
					finishName+=Character.toLowerCase(name.charAt(i));
					
				}
				
			}
			else {
				finishName+=Character.toLowerCase(name.charAt(i));
			}
			
		}
		return finishName;
		
	}
	public Player getPlayerFromCod(String plyCod) {
		Conexion conex = new Conexion();
		File f = new File("src/application/files/saveFile.txt");
		String username="";
		Player ply = new Player("","",0,0);
		ply = conex.getPlayerData(Integer.parseInt(plyCod));

		return ply;
		
	}
	public Player getPlayer() {
		Conexion conex = new Conexion();
		File f = new File("src/application/files/saveFile.txt");
		String username="";
		Player ply = new Player("","",0,0);
		try {
			FileReader fr = new FileReader(f);
			BufferedReader bfr = new BufferedReader(fr);
			username=bfr.readLine();
			bfr.close();
			String Ccod = conex.getData("cod","cuenta","where username='"+username+"'");
			ply = conex.getPlayerData(Integer.parseInt(Ccod));
			
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ply;
		
	}
	public String getPlayerCod() {
		Conexion conex = new Conexion();
		File f = new File("src/application/files/saveFile.txt");
		String username="";
		String Ccod="";
		try {
			FileReader fr = new FileReader(f);
			BufferedReader bfr = new BufferedReader(fr);
			username=bfr.readLine();
			bfr.close();
			Ccod = conex.getData("cod","cuenta","where username='"+username+"'");
			
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Ccod;
		
	}
	public void lvlUp(Player ply) {
		Conexion conex = new Conexion();
		String Ccod = getPlayerCod();
		conex.updateData("players", "lvl="+(ply.getLvl()+1)+",exp=0", "where cod="+Ccod);
			
		
	}
	public void upExp(Player ply,int exp) {
		Conexion conex = new Conexion(); 
		String Ccod = getPlayerCod();
		conex.updateData("players", "exp="+ply.getExp()+exp, "where cod="+Ccod);

	}
	
	public ArrayList<Pokemon> getPlayerPokemon(Player ply) throws SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		ArrayList<Pokemon> pokemon = new ArrayList<Pokemon>();
		ArrayList<String> pokemonNames = new ArrayList<String>();
		ArrayList<String> codPoks = new ArrayList<String>();
		Conexion conex = new Conexion();
		String codP = getPlayerCod();
		codPoks = conex.getMultipleData("codPok","asignacion","where codP='"+codP+"'");
		for(int i=0;i<codPoks.size();i++) {
			pokemonNames.add(conex.getData("pkname","pokemon","where cod='"+codPoks.get(i)+"'"));
		}
		if(pokemonNames.size() != 0) {
			System.out.println(pokemonNames.size());
			for(int e=0;e<pokemonNames.size();e++) {
				
				String PokeBS = pokemonNames.get(e);
				String[] PokeS = PokeBS.split("\\.");
				String alias=conex.getData("alias","pokemon","where cod='"+codPoks.get(e)+"'");
			
					
						int vida= Integer.parseInt(conex.getData("vida","pokemon","where cod='"+codPoks.get(e)+"'")),
						defensa=Integer.parseInt(conex.getData("defensa","pokemon","where cod='"+codPoks.get(e)+"'"))
						,ataque=Integer.parseInt(conex.getData("atk","pokemon","where cod='"+codPoks.get(e)+"'"))
						,experiencia=Integer.parseInt(conex.getData("exp","pokemon","where cod='"+codPoks.get(e)+"'"))
						,nivel=Integer.parseInt(conex.getData("lvl","pokemon","where cod='"+codPoks.get(e)+"'"))
						,velocidad=Integer.parseInt(conex.getData("velocidad","pokemon","where cod='"+codPoks.get(e)+"'"))
						;
						String attk1=conex.getData("attk1","pokemon","where cod='"+codPoks.get(e)+"'")
								,attk2 = conex.getData("attk2","pokemon","where cod='"+codPoks.get(e)+"'")
								,attk3 = conex.getData("attk3","pokemon","where cod='"+codPoks.get(e)+"'")
								,attk4 = conex.getData("attk4","pokemon","where cod='"+codPoks.get(e)+"'");
						Class<?> clas = Class.forName(pokemonNames.get(e));
						pokemon.add( (Pokemon) clas.getConstructor(
								String.class,int.class,int.class,int.class,int.class,int.class,int.class,String.class,String.class,String.class,String.class
								).newInstance(
										alias,vida,defensa,ataque,experiencia,nivel,velocidad,attk1,attk2,attk3,attk4));
				
			}
			
		}
		
		return pokemon;
	}
	
	public Pokemon getPokemonFromImage(ImageView pokImg,ArrayList<Pokemon> pokArr) {
		Pokemon returnPok = null;
		String PokeBS = pokImg.getImage().getUrl();
		String[] PokeS1 = PokeBS.split("\\/");
		String[] PokeS2 = PokeS1[PokeS1.length-1].split("\\.");
		String pokemon = PokeS2[0].substring(0, 1).toUpperCase() + PokeS2[0].substring(1);
		System.out.println( PokeS2[0]);
		for (int a=0;a<pokArr.size();a++) {
			String PokeBS2 = pokArr.get(a).getClass().toString();
			String[] PokeS12 = PokeBS2.split("\\.");
			String[] PokeS22 = PokeS12[3].split("\\@");
			String pokemon1 = PokeS22[0].substring(0, 1).toUpperCase() + PokeS22[0].substring(1);

			if(pokemon.equals(pokemon1)) {
				returnPok = pokArr.get(a);
			}
		}
		
		
		return returnPok;
		
	}
	public ArrayList<Pokemon> getPVPPokemonFromTxt(int player,String playerCod) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, SQLException, ClassNotFoundException{
		Conexion conex = new Conexion();
		String pokemonTxt="";
		if(player==1) {
			pokemonTxt=conex.getData("P1Poks", "partida","where codP1="+playerCod );

		}else if(player==2) {
			pokemonTxt=conex.getData("P2Poks", "partida","where codP2="+playerCod);
		}
		System.out.println(pokemonTxt);
		String[] pokemonTxtArr = pokemonTxt.split("\\|");
		ArrayList<Pokemon> pokemon = new ArrayList<Pokemon>();
		ArrayList<String> pokemonNames = new ArrayList<String>();
		ArrayList<String> codPoks = new ArrayList<String>();
		ArrayList<String> currentCodPoks = new ArrayList<String>();
		codPoks = conex.getMultipleData("codPok","asignacion","where codP='"+playerCod+"'");
		
		for(int i=0;i<codPoks.size();i++) {
			String currName=conex.getData("pkname","pokemon","where cod='"+codPoks.get(i)+"'");
			for(int a=0;a<pokemonTxtArr.length;a++) {
				
				if(currName.equals("application.modelo.pokemons."+pokemonTxtArr[a])) {
					System.out.println(("CurrName: "+currName + "= application.modelo.pokemons."+pokemonTxtArr[a]));
					pokemonNames.add(conex.getData("pkname","pokemon","where cod='"+codPoks.get(i)+"'"));
					currentCodPoks.add(codPoks.get(i));
				}
				
			}
			
		}
		for(int e=0;e<pokemonNames.size();e++) {
			System.out.println(pokemonNames.get(e));
			String PokeBS = pokemonNames.get(e);
			String[] PokeS = PokeBS.split("\\.");
			String alias=conex.getData("alias","pokemon","where cod='"+currentCodPoks.get(e)+"'");
			int vida= Integer.parseInt(conex.getData("vida","pokemon","where cod='"+currentCodPoks.get(e)+"'"))
			,defensa=Integer.parseInt(conex.getData("defensa","pokemon","where cod='"+currentCodPoks.get(e)+"'"))
			,ataque=Integer.parseInt(conex.getData("atk","pokemon","where cod='"+currentCodPoks.get(e)+"'"))
			,experiencia=Integer.parseInt(conex.getData("exp","pokemon","where cod='"+currentCodPoks.get(e)+"'"))
			,nivel=Integer.parseInt(conex.getData("lvl","pokemon","where cod='"+currentCodPoks.get(e)+"'"))
			,velocidad=Integer.parseInt(conex.getData("velocidad","pokemon","where cod='"+currentCodPoks.get(e)+"'"));
			String attk1=conex.getData("attk1","pokemon","where cod='"+currentCodPoks.get(e)+"'")
			,attk2 = conex.getData("attk2","pokemon","where cod='"+currentCodPoks.get(e)+"'")
			,attk3 = conex.getData("attk3","pokemon","where cod='"+currentCodPoks.get(e)+"'")
			,attk4 = conex.getData("attk4","pokemon","where cod='"+currentCodPoks.get(e)+"'");
			; 
			Class<?> clas = Class.forName(pokemonNames.get(e));
			pokemon.add( (Pokemon) clas.getConstructor(
					String.class,int.class,int.class,int.class,int.class,int.class,int.class,String.class,String.class,String.class,String.class
					).newInstance(
							alias,vida,defensa,ataque,experiencia,nivel,velocidad,attk1,attk2,attk3,attk4));
			
		}
		return pokemon;
		
	}	
	public ArrayList<Pokemon> getPokemonFromTxt(Player ply,String pokemonTxt) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Conexion conex = new Conexion();
		System.out.println(pokemonTxt);
		String[] pokemonTxtArr = pokemonTxt.split("\\|");
		ArrayList<Pokemon> pokemon = new ArrayList<Pokemon>();
		ArrayList<String> pokemonNames = new ArrayList<String>();
		ArrayList<String> codPoks = new ArrayList<String>();
		ArrayList<String> currentCodPoks = new ArrayList<String>();
		String codP = getPlayerCod();
			

		codPoks = conex.getMultipleData("codPok","asignacion","where codP='"+codP+"'");
		
		for(int i=0;i<codPoks.size();i++) {
			String currName=conex.getData("pkname","pokemon","where cod='"+codPoks.get(i)+"'");
			for(int a=0;a<pokemonTxtArr.length;a++) {
				
				if(currName.equals("application.modelo.pokemons."+pokemonTxtArr[a])) {
					System.out.println(("CurrName: "+currName + "= application.modelo.pokemons."+pokemonTxtArr[a]));
					pokemonNames.add(conex.getData("pkname","pokemon","where cod='"+codPoks.get(i)+"'"));
					currentCodPoks.add(codPoks.get(i));
				}
				
			}
			
		}
		for(int e=0;e<pokemonNames.size();e++) {
			System.out.println(pokemonNames.get(e));
			String PokeBS = pokemonNames.get(e);
			String[] PokeS = PokeBS.split("\\.");
			String alias=conex.getData("alias","pokemon","where cod='"+currentCodPoks.get(e)+"'");
			int vida= Integer.parseInt(conex.getData("vida","pokemon","where cod='"+currentCodPoks.get(e)+"'"))
			,defensa=Integer.parseInt(conex.getData("defensa","pokemon","where cod='"+currentCodPoks.get(e)+"'"))
			,ataque=Integer.parseInt(conex.getData("atk","pokemon","where cod='"+currentCodPoks.get(e)+"'"))
			,experiencia=Integer.parseInt(conex.getData("exp","pokemon","where cod='"+currentCodPoks.get(e)+"'"))
			,nivel=Integer.parseInt(conex.getData("lvl","pokemon","where cod='"+currentCodPoks.get(e)+"'"))
			,velocidad=Integer.parseInt(conex.getData("velocidad","pokemon","where cod='"+currentCodPoks.get(e)+"'"));
			String attk1=conex.getData("attk1","pokemon","where cod='"+currentCodPoks.get(e)+"'")
			,attk2 = conex.getData("attk2","pokemon","where cod='"+currentCodPoks.get(e)+"'")
			,attk3 = conex.getData("attk3","pokemon","where cod='"+currentCodPoks.get(e)+"'")
			,attk4 = conex.getData("attk4","pokemon","where cod='"+currentCodPoks.get(e)+"'");
			; 
			Class<?> clas = Class.forName(pokemonNames.get(e));
			pokemon.add( (Pokemon) clas.getConstructor(
					String.class,int.class,int.class,int.class,int.class,int.class,int.class,String.class,String.class,String.class,String.class
					).newInstance(
							alias,vida,defensa,ataque,experiencia,nivel,velocidad,attk1,attk2,attk3,attk4));
			
		}
		return pokemon;
		
	}
	
	public void setInvImages(ArrayList<Pokemon> arrP, ImageView[] lvlPok) {
		ArrayList<String> pkImg = new ArrayList<String>();
		for(int i=0;i<arrP.size();i++) {
			String PokeBS = arrP.get(i).getClass().toString();
			String[] PokeS = PokeBS.split("\\.");
			String[] PokeS2 = PokeS[3].split("\\@");
			pkImg.add(PokeS2[0].toLowerCase());
		}
		for(int a=0;a<pkImg.size();a++) {
				Image lock = new Image(getClass().getResource("res/lock.png").toExternalForm());
				if(lvlPok[a].getImage() == null || lvlPok[a].getImage().getUrl().equals(lock.getUrl())) {
					lvlPok[a].setImage(new Image(getClass().getResource("res/pokemonSprites/"+pkImg.get(a)+".gif").toExternalForm()));
					lvlPok[a].setDisable(false);
					lvlPok[a].setCursor(Cursor.HAND);
				}
			}
	
	}
	
	
	public Pokemon createSinglePokemon (String pokName,String alias,int vida,int defensa,int ataque,int exp,int lvl,int vel) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		Pokemon a = null;
		Class<?> clas = Class.forName(pokName);
		a = (Pokemon) clas.getConstructor(
				String.class,int.class,int.class,int.class,int.class,int.class,int.class,String.class,String.class,String.class,String.class
				).newInstance(
						alias,vida,defensa,ataque,exp,lvl,vel,"","","","");
		return a;
		
	}
	
	public void createRanPokemonSet(int lvl,ArrayList<Pokemon> arrP) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		Conexion conex = new Conexion();
		Random rand = new Random();
		ArrayList<String> allpokemon = new ArrayList<String>(); 
		Collections.addAll(allpokemon, "Blastoise","Charizard","Dugtrio","Gengar",
				"Geodude","Golbat","Machamp","Mrmime","Pidgeot","Pikachu","Snorlax","Venosaur");
		ArrayList<String> pokemon = new ArrayList<String>(); 
		Collections.addAll(pokemon, "Blastoise","Charizard","Dugtrio","Gengar",
				"Geodude","Golbat","Machamp","Mrmime","Pidgeot","Pikachu","Snorlax","Venosaur");
		System.out.println("Size"+arrP.size());
		if(arrP.size() == 0 || arrP.size()>12) {
			
		}else {
			for(int i=0;i<allpokemon.size();i++) {
				for(int a=0;a<arrP.size();a++) {
					String PokeBS = arrP.get(a).getClass().toString();
					String[] PokeS = PokeBS.split("\\.");
					String[] PokeS2 = PokeS[3].split("\\@");
					if(PokeS2[0].equals(allpokemon.get(i))){
							for(int c=0;c<pokemon.size();c++) {
								if(pokemon.get(c).equals(allpokemon.get(i))) {
									pokemon.remove(c);
								}
							}
					}
					
							
					
				}
			}
				
		}
				
		
		
		if(lvl==5) {
			String pKname= "application.modelo.pokemons.Zapdos";
			Pokemon PokCre = createSinglePokemon(pKname,
					"",50+(rand.nextInt(200)+1),50+(rand.nextInt(100)+1),10+(rand.nextInt(20)+1),0,
					5,5+(rand.nextInt(10)+1));
			ArrayList<String> pokAtkks = getAtaques(PokCre);
			conex.createPokemon(pKname, PokCre, pokAtkks);
			conex.asignarPokemon();
			
		}else {
			for(int i=arrP.size();i<(lvl*3);i++) {
				int index = rand.nextInt(pokemon.size());
				String pKname= "application.modelo.pokemons."+pokemon.get(index);
				System.out.println(pokemon.toString());
				for(int b=0;b<pokemon.size();b++) {
					if(pokemon.get(b).equals(pokemon.get(index))) {
						pokemon.remove(b);
					}
				}
				
				System.out.println(pokemon.toString());
				Pokemon PokCre = createSinglePokemon(pKname,
						"",50+(rand.nextInt(200)+1),50+(rand.nextInt(100)+1),10+(rand.nextInt(10)+1),0,
						1,5+(rand.nextInt(10)+1));
				ArrayList<String> pokAtkks = getAtaques(PokCre);
				conex.createPokemon(pKname, PokCre, pokAtkks);
				conex.asignarPokemon();
				
			}
		}
		
	}
	private ArrayList<String> getAtaques(Pokemon pok) {
		String[] normals = {"falsoTortazo","malDeOjo","corte","poderOculto"};
		ArrayList<String> atkks = new ArrayList<String>();
		Random rand = new Random();
		int index;
		if(pok.getClass().getInterfaces().length < 1) {
			for(String poks: normals) {
				atkks.add(poks);
			}
			
		}else {
			if(pok.getClass().getInterfaces().length > 1) {
				for(int a=0;a<pok.getClass().getInterfaces().length;a++) {
					index = rand.nextInt(pok.getClass().getInterfaces()[a].getMethods().length);
					for(int b =0;b<atkks.size();b++) {
						if(atkks.get(b).equals(pok.getClass().getInterfaces()[0].getMethods()[index].getName())) {
							continue;
						}
					}
					atkks.add(pok.getClass().getInterfaces()[a].getMethods()[index].getName());
				}
				
			}else {
				for(int a=0;a<2;a++) {
					index = rand.nextInt(pok.getClass().getInterfaces()[0].getMethods().length);
					for(int b =0;b<atkks.size();b++) {
						if(atkks.get(b).equals(pok.getClass().getInterfaces()[0].getMethods()[index].getName())) {
							continue;
						}
					}
					atkks.add(pok.getClass().getInterfaces()[0].getMethods()[index].getName());
				}
			}
			for(int b=0;b<2;b++) {
				index = rand.nextInt(normals.length);
				for(int c =0;c<atkks.size();c++) {
					if(atkks.get(c).equals(normals[index])) {
						continue;
					}
				}
				atkks.add(normals[index]);
			}
			
		}
		// TODO Auto-generated method stub
		return atkks;
	}
	public String getPokemonName(String fullname) {
		String PokeBS = fullname;
		String[] PokeS = PokeBS.split("\\.");
		String[] PokeS2 = PokeS[3].split("\\@");
		return PokeS2[0];
		
	}
	public ArrayList<Pokemon> getEnemyPokemon() {
		ArrayList<Pokemon> arrP = new ArrayList<Pokemon>();
		ArrayList<String> pokAtkks = new ArrayList<String>();
		Random rand = new Random();
		ArrayList<String> pokemon = new ArrayList<String>(); 
		Collections.addAll(pokemon, "Blastoise","Charizard","Dugtrio","Gengar",
				"Geodude","Golbat","Machamp","Mrmime","Pidgeot","Pikachu","Snorlax","Venosaur");
		for(int i=0;i<3;i++) {
			int index = rand.nextInt(pokemon.size());
			String pKname= "application.modelo.pokemons."+pokemon.get(index);
			System.out.println("Enemy "+i+1+" "+pKname);
			pokemon.remove(index);
			Pokemon PokCre = null;
			try {
				PokCre = createSinglePokemon(pKname,
						"",50+(rand.nextInt(200)+1),50+(rand.nextInt(100)+1),10+(rand.nextInt(10)+1),0,
						1,5+(rand.nextInt(10)+1));
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException
					| ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pokAtkks = getAtaques(PokCre);
			PokCre.setAttk1(pokAtkks.get(0));
			PokCre.setAttk2(pokAtkks.get(1));
			PokCre.setAttk3(pokAtkks.get(2));
			PokCre.setAttk4(pokAtkks.get(3));
			arrP.add(PokCre);
			
			
			
		}
		// TODO Auto-generated method stub
		return arrP;
	}
	public Pokemon getBestPokemon(ArrayList<Pokemon> poke,Pokemon enPok,Pokemon curPok) {
		Pokemon pok=null;
		tablaTipos tt;
		if(enPok.getClass().getInterfaces().length==0){
			tt = tablaTipos.NORMAL;
		}else {
			tt = tablaTipos.valueOf(enPok.getClass().getInterfaces()[0].getName().substring(23).toUpperCase());
			
		}
		//System.out.println("interfaces: "+poke.get(0).getClass().getInterfaces()[0].getName());
		Random ran = new Random();
		ArrayList<String> tipos= new ArrayList<String>();
		String tipo = "";
		for(Pokemon poki:poke) {
			if(poki.getClass().getInterfaces().length<1) {
				tipos.add("Normal");
				
			}else {
				String[] splitt = poki.getClass().getInterfaces()[0].getName().split("\\.");
				tipos.add(splitt[splitt.length-1]);
				
			}
		}
		for(int i=0;i<tt.getDebilidades().length;i++) {
			for(String str: tipos) {
				if(tt.getDebilidades()[i].equals(str)) {
					tipo=str;
				}
			}
				
		}
		
		for(Pokemon pokk:poke) {
			if(pokk.getClass().getInterfaces().length>0) {
				if(pokk.getClass().getInterfaces()[0].getName().substring(23).equals(tipo)) {
					pok = pokk;
				}
				
			}
		}
		if(pok == null) {
			pok = curPok;
		}
		
		
		return pok;
		
	}
	public int getBestAtack(ArrayList<String> atkkTypes,Pokemon curPok) {
		Pokemon pok=null;
		tablaTipos tt = tablaTipos.valueOf(curPok.getClass().getInterfaces()[0].getName().substring(23).toUpperCase());
		//System.out.println("interfaces: "+poke.get(0).getClass().getInterfaces()[0].getName());
		Random ran = new Random();
		ArrayList<String> tipos= new ArrayList<String>();
		int bestAtaque=0;
		for(int a=0;a<atkkTypes.size();a++) {
			for(int i=0;i<tt.getDebilidades().length;i++) {
				if(atkkTypes.get(a).equals(tt.getDebilidades()[i]) && bestAtaque!=0) {
					bestAtaque=a;
					break;
				}
			}
		}
		return bestAtaque;
		
		
	}
	public ArrayList<String> getAtackTypes(Pokemon pokemon) {
		String[] atkks = new String[] {pokemon.getAttk1(),pokemon.getAttk2(),pokemon.getAttk3(),pokemon.getAttk4()};
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
		return types;

		}
	public int getTrueDamage(Pokemon enPok,int plainDmg,String attkType) {
		tablaTipos tt = tablaTipos.valueOf(enPok.getClass().getInterfaces()[0].getName().substring(23).toUpperCase());
		int trueDMG=plainDmg;
		for(int a=0;a<tt.getDebilidades().length;a++) {
			if(tt.getDebilidades()[a].equalsIgnoreCase(attkType)) {
				trueDMG=(int) (trueDMG*2-(enPok.getDefensa()*0.1));
			}
		}
		for(int b=0;b<tt.getFortalezas().length;b++) {
			if(tt.getFortalezas()[b].equalsIgnoreCase(attkType)) {
				trueDMG=(int) (trueDMG/2-(enPok.getDefensa()*0.1));
			}
		}
		if(trueDMG<0) {
			trueDMG=0;
		}
		return trueDMG;
		
	}
	public void sendMPmatchData(String pokemons) {
		Conexion conex = new Conexion();
		conex.crearPartida(pokemons,getPlayerCod());
		try {
			if(getPlayerCod().equals(conex.getData("codP1", "partida","order by cod asc" ))) {
				conex.mergeMatch(getPlayerCod(),pokemons);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
