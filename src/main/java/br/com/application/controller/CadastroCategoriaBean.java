package br.com.application.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.application.model.Categoria;
import br.com.application.repository.CategoriaRepository;


@SuppressWarnings("serial")
@Named
@ViewScoped
public class CadastroCategoriaBean implements Serializable {
	
	private Categoria categoria;
	@Inject private CategoriaRepository categoriaRepository;
	
	public void inicializar() {
		if(categoria == null) {
			categoria = new Categoria();
		}
	}
	
    public void save() {
    	categoriaRepository.persist(categoria);
	}
    
    public Categoria getCategoria() {
		return categoria;
	}
    
    public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}
