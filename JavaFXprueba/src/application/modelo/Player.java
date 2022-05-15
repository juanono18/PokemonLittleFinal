package application.modelo;

public class Player {
	String name,gender;
	int lvl,exp;
	public Player(String name, String gender, int lvl,int exp) {
		this.name = name;
		this.gender = gender;
		this.lvl = lvl;
		this.exp = exp;
	}
	@Override
	public String toString() {
		return "Player [name=" + name + ", gender=" + gender + ", lvl=" + lvl + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getLvl() {
		return lvl;
	}
	public void setLvl(int lvl) {
		this.lvl = lvl;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
}
