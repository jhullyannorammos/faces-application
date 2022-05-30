package br.com.application.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.application.enumerator.Fabricante;

@SuppressWarnings("serial")
@Entity
@Table(name = "modelo")
public class Modelo implements Serializable {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull @Enumerated(EnumType.STRING)
	@Column(name = "fabricante", nullable = false, length = 20)
	private Fabricante fabricante;
	
	@Column(unique  = true) private String modelo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	
	
	

}
