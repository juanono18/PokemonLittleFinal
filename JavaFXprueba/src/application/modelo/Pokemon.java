package application.modelo;

public abstract class Pokemon {
	

	String alias,attk1,attk2,attk3,attk4;
	private int vida,defensa,ataque,experiencia,nivel,velocidad,currentHp;
	
	public Pokemon(String alias, int vida, int defensa, int ataque, int experiencia, int nivel, int velocidad,String attk1,String attk2,String attk3,String attk4) {
		super();
		this.alias = alias;
		this.vida = vida;
		this.defensa = defensa;
		this.ataque = ataque;
		this.experiencia = experiencia;
		this.nivel = nivel;
		this.velocidad = velocidad;
		this.currentHp = vida;
		this.attk1 = attk1;
		this.attk2 = attk2;
		this.attk3 = attk3;
		this.attk4 = attk4;
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	public int getDefensa() {
		return defensa;
	}

	public void setDefensa(int defensa) {
		this.defensa = defensa;
	}

	public int getAtaque() {
		return ataque;
	}

	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}

	public int getExperiencia() {
		return experiencia;
	}

	public void setExperiencia(int experiencia) {
		this.experiencia = experiencia;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public int getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public int getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(int currentHp) {
		this.currentHp = currentHp;
	}

	public String getAttk1() {
		return attk1;
	}

	public void setAttk1(String attk1) {
		this.attk1 = attk1;
	}

	public String getAttk2() {
		return attk2;
	}

	public void setAttk2(String attk2) {
		this.attk2 = attk2;
	}

	public String getAttk3() {
		return attk3;
	}

	public void setAttk3(String attk3) {
		this.attk3 = attk3;
	}

	public String getAttk4() {
		return attk4;
	}

	public void setAttk4(String attk4) {
		this.attk4 = attk4;
	}
	
	//Daño normal pero no puede bajar del 1 ps de vida
	public abstract int falsoTortazo();
	//Pokemon enemigo no puede ser cambiado
	public abstract int malDeOjo();
	//Daño sin efectos secundarios
	public abstract int corte();
	//Daño en base a los Stats del pokemon(https://pokemon.fandom.com/es/wiki/C%C3%A1lculo_del_poder_oculto)
	public abstract int poderOculto();
	
	@Override
	public String toString() {
		return "Pokemon [alias=" + alias + ", attk1=" + attk1 + ", attk2=" + attk2 + ", attk3=" + attk3 + ", attk4="
				+ attk4 + ", vida=" + vida + ", defensa=" + defensa + ", ataque=" + ataque + ", experiencia="
				+ experiencia + ", nivel=" + nivel + ", velocidad=" + velocidad + ", currentHp=" + currentHp + "]";
	}
	

}
