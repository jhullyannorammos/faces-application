package br.com.application.enumerator;

public enum Fabricante {

	ANTARCTICA("Antarctica"),
	SAMSUNG("Samsung"),
	WARRIOR("Warrior"),
	LENOVO("Lenovo"),
	PEPSI("PEPSI"),
	DELL("Dell"),
	TEXX("Texx"),
	SONY("Sony");
	
	private String fabricante;
	
	private Fabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	
	public String getFabricante() {
		return fabricante;
	}
	
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

}
