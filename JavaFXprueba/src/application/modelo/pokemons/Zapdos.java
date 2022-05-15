/**
 * 
 */
package application.modelo.pokemons;

import java.util.Random;

import application.interfaces.Electrico;
import application.interfaces.Volador;
import application.modelo.Pokemon;

/**
 * @author Alumno
 *
 */
public class Zapdos extends Pokemon implements Electrico, Volador {


	public Zapdos(String alias, int vida, int defensa, int ataque, int experiencia, int nivel, int velocidad,
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
		for(int a=0;a<3;a++) {
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
	public int aeroChorro() {
		// TODO Auto-generated method stub
		return (int) (this.getAtaque()*0.5);
	}

	@Override
	public int aireAfilado() {
		// TODO Auto-generated method stub
		return (int) ((this.getAtaque()*0.5)*3);
	}

	@Override
	public int ascensoDraco() {
		// TODO Auto-generated method stub
		return this.getAtaque()*2;
	}

	@Override
	public int ataqueAla() {
		// TODO Auto-generated method stub
		return (int) ((this.getAtaque()*0.25)*3);
	}

	@Override
	public int chispa() {
		// TODO Auto-generated method stub
		return (int) (this.getAtaque()*0.5);
	}

	@Override
	public int chispazo() {
		// TODO Auto-generated method stub
		return (int) (this.getAtaque()*0.75);
	}

	@Override
	public int impactrueno() {
		// TODO Auto-generated method stub
		return this.getAtaque()*5;
	}

	@Override
	public int rayo() {
		// TODO Auto-generated method stub
		return this.getAtaque()*2;
	}

}
