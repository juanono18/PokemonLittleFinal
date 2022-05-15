/**
 * 
 */
package application.modelo.pokemons;

import java.util.Random;

import application.interfaces.Planta;
import application.interfaces.Veneno;
import application.modelo.Pokemon;

/**
 * @author Alumno
 *
 */
public class Venosaur extends Pokemon implements Planta, Veneno {


	public Venosaur(String alias, int vida, int defensa, int ataque, int experiencia, int nivel, int velocidad,
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
	public int bombaGermen() {
		// TODO Auto-generated method stub
		return (int) (this.getAtaque()*0.75);
	}

	@Override
	public int brazoPincho() {
		// TODO Auto-generated method stub
		return this.getAtaque()*2;
	}

	@Override
	public int fogonazo() {
		// TODO Auto-generated method stub
		return (int) ((this.getAtaque()*0.25)*5);
	}

	@Override
	public int fitoimpulso() {
		// TODO Auto-generated method stub
		return this.getAtaque();
	}

	@Override
	public int acido() {
		// TODO Auto-generated method stub
		return (int) (this.getAtaque()*0.25);
	}

	@Override
	public int lanzaMugre() {
		// TODO Auto-generated method stub
		return this.getAtaque();
	}

	@Override
	public int eructo() {
		// TODO Auto-generated method stub
		return (int) (this.getAtaque()*1.5);
	}

	@Override
	public int puyaNociva() {
		// TODO Auto-generated method stub
		return (int) (this.getAtaque()*0.25);
	}

}
