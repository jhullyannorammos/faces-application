package br.com.application.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.application.enumerator.Fabricante;
import br.com.application.model.Modelo;
import br.com.application.service.CadastroModeloService;
import br.com.application.service.NegocioException;
import br.com.application.util.jsf.FacesUtil;


@SuppressWarnings("serial")
@Named
@ViewScoped
public class CadastroModeloBean implements Serializable { 
	
	@Inject private CadastroModeloService modeloService;
	private Modelo modelo;
	
	public void inicializar() {
		if(modelo == null) {
			instance();
		}
	}
	
	public void instance() {
		this.modelo = new Modelo();
	}
	
	public void persist() {
		try {
			modeloService.persist(modelo);
			FacesUtil.addInfoMessage("Modelo salvo com sucesso!");
		} catch(NegocioException e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage(e.getMessage() + " " + 
			                          e.getCause() + " " +
			                          e.getLocalizedMessage());
		} finally {
			instance();
		}
	}
	
	public Fabricante[] getFabricantes() {
		return Fabricante.values();
	}
	
	public Modelo getModelo() {
		return modelo;
	}
	
	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

}
