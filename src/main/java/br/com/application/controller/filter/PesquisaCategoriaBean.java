package br.com.application.controller.filter;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.application.model.Categoria;
import br.com.application.repository.CategoriaRepository;
import br.com.application.service.CategoriaService;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class PesquisaCategoriaBean implements Serializable {
	
	@Inject private CategoriaRepository categoriaService;
	@Inject private Categoria categoria;
	private List<Categoria> categorias;
	
	public PesquisaCategoriaBean() {
		
	}
	
	public void findAll() {
		categorias = categoriaService.raizes();
	}
	
	public List<Categoria> getCategorias() {
		return categorias;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}
	
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}
