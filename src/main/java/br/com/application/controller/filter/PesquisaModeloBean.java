package br.com.application.controller.filter;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.application.model.Modelo;
import br.com.application.repository.ModeloRepository;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class PesquisaModeloBean implements Serializable {
	
	@Inject private ModeloRepository modeloRepository;
	private List<Modelo> modelos;

	public PesquisaModeloBean() {
		
	}
	
	public void pesquisar() {
		modelos = modeloRepository.findAll();
	}
	
	public List<Modelo> getModelos() {
		return modelos;
	}
	
	public void setModelos(List<Modelo> modelos) {
		this.modelos = modelos;
	}
}
