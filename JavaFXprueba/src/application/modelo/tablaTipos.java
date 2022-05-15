package application.modelo;

import java.util.ArrayList;

public enum tablaTipos
{
	AGUA(new String[] {"Planta","Electrico"},new String[] {"Roca","Tierra","Fuego"}),
	BICHO(new String[] {"Volador","Roca","Fuego"},new String[] {"Planta","Psiquico"}),
	ELECTRICO(new String[] {"Tierra"},new String[] {"Volador","Agua"}),
	FANTASMA(new String[] {"Fantasma","Psiquico"},new String[] {"Fantasma"}),
	FUEGO(new String[] {"Agua","Roca","Tierra"},new String[] {"Planta","Bicho"}),
	LUCHA(new String[] {"Volador","Psiquico"},new String[] {"Normal","Roca"}),
	PLANTA(new String[] {"Volador","Bicho","Veneno"},new String[] {"Agua","Roca","Tierra"}),
	PSIQUICO(new String[] {"Fantasma","Bicho"},new String[] {"Lucha","Veneno"}),
	ROCA(new String[] {"Agua","Planta","Tierra","Lucha"},new String[] {"Volador","Bicho","Fuego"}),
	TIERRA(new String[] {"Agua","Planta"},new String[] {"Electrico","Roca","Fuego","Veneno"}),
	VENENO(new String[] {"Tierra","Psiquico"},new String[] {"Planta"}),
	VOLADOR(new String[] {"Electrico","Roca"},new String[] {"Planta","Bicho","Lucha"}),
	NORMAL(new String[] {"Lucha"},new String[] {""});
	
	private String[] debilidades;
	private String[] fortalezas;
	
	
	private tablaTipos (String[] debilidades, String[] fortalezas){
		this.debilidades = debilidades;
		this.fortalezas = fortalezas;
	}

	public String[] getDebilidades() {
		return debilidades;
	}

	public String[] getFortalezas() {
		return fortalezas;
	}	
	
}