/**
 * 
 */
package application.modelo.pokemons;

import java.util.Random;

import application.interfaces.Agua;
import application.modelo.Pokemon;

/**
 * @author Alumno
 *
 */
public class Blastoise extends Pokemon implements Agua {

	public Blastoise(String alias, int vida, int defensa, int ataque, int experiencia, int nivel, int velocidad,
			String attk1, String attk2, String attk3, String attk4) {
		super(alias, vida, defensa, ataque, experiencia, nivel, velocidad, attk1, attk2, attk3, attk4);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int falsoTortazo() {
		Random rand = new Random();
		// TODO Auto-generated method stub
		if(rand.nextInt(50)==5) {
			return this.getAtaque()*2;

		}else {
			return this.getAtaque();
			
		}
	}

	@Override
	public int malDeOjo() {
		// TODO Auto-generated method stub
		return (int) (this.getAtaque()*0.1);
	}

	@Override
	public int corte() {
		// TODO Auto-generated method stub
		Random rand = new Random();
		int dmg=0;
		for(int a=0;a<2;a++) {
			dmg+=this.getAtaque()*rand.nextInt(2);
		}
		return dmg;
	}

	@Override
	public int poderOculto() {
		// TODO Auto-generated method stub
		return (int) (this.getAtaque()*0.5);
	}

	@Override
	public int acuaJet() {
		// TODO Auto-generated method stub
		int dmg=0;
		for(int a=0;a<2;a++) {
			dmg+=this.getAtaque()*0.5;
		}
		return dmg;
	}

	@Override
	public int ariaBurbuja() {
		// TODO Auto-generated method stub
		
		return this.getAtaque();
	}

	@Override
	public int hidrobomba() {
		// TODO Auto-generated method stub
		return this.getAtaque()*5;
	}

	@Override
	public int pistolaAgua() {
		// TODO Auto-generated method stub
		return (int) (this.getAtaque()*0.25);
	}


}
