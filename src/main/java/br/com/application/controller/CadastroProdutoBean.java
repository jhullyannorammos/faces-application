package br.com.application.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.application.model.Categoria;
import br.com.application.model.Modelo;
import br.com.application.model.Produto;
import br.com.application.repository.CategoriaRepository;
import br.com.application.repository.ModeloRepository;
import br.com.application.service.CadastroProdutoService;
import br.com.application.service.FotoService;
import br.com.application.service.NegocioException;
import br.com.application.util.jsf.FacesUtil;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class CadastroProdutoBean implements Serializable {

	@Inject private CadastroProdutoService cadastroProdutoService;
	@Inject private ModeloRepository modeloRepository;
	@Inject private FotoService fotoService;
	@Inject private CategoriaRepository categorias;
	private Produto produto;
	private Categoria categoriaPai;	
	private List<Categoria> categoriasRaizes;
	private List<Categoria> subcategorias;
	private List<Modelo> modelos;
	
	public CadastroProdutoBean() {
		limpar();
	}
	
	@PostConstruct public void inicializar() {
		if (this.produto == null) {
			limpar();
		}
		
		categoriasRaizes = categorias.raizes();
		
		if (this.categoriaPai != null) {
			carregarSubcategorias();
		}
		
		if(this.modelos == null) {
			modelos = modeloRepository.findAll();
		}
	}
	
	public void carregarSubcategorias() {
		subcategorias = categorias.subcategoriasDe(categoriaPai);
	}
	
	private void limpar() {
		produto = new Produto();
		categoriaPai = null;
		subcategorias = new ArrayList<>();
	}
	
	public void salvar() {
		try {
			this.produto = cadastroProdutoService.salvar(this.produto);
			limpar();
			
			FacesUtil.addInfoMessage("Produto salvo com sucesso!");
		} catch (NegocioException negocioException) {
			FacesUtil.addErrorMessage(negocioException.getMessage());
		}
	}
	
	public void upload(FileUploadEvent event) {
	    UploadedFile uploadedFile = event.getFile();
	    
	    try {
	    	fotoService.deletar(produto.getFoto());
			String foto = fotoService.salvarFotoTemp(uploadedFile.getFileName(), event.getFile().getContents());
			produto.setFoto(foto);
		} catch (Exception negocioException) {
			FacesUtil.addErrorMessage(negocioException.getMessage());
		}
	}
	
	public void removerFoto() {
		try {
			fotoService.deletarTemp(produto.getFoto());
		} catch (IOException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
		
		produto.setFoto(null);
	}

	public Produto getProduto() {
		return produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
		
		if (this.produto != null) {
			this.categoriaPai = this.produto.getCategoria().getCategoriaPai();
		}
	}

	public List<Categoria> getCategoriasRaizes() {
		return categoriasRaizes;
	}

	@NotNull
	public Categoria getCategoriaPai() {
		return categoriaPai;
	}

	public void setCategoriaPai(Categoria categoriaPai) {
		this.categoriaPai = categoriaPai;
	}
	
	@NotNull
	public List<Modelo> getModelos() {
		return modelos;
	}
	
	public void setModelos(List<Modelo> modelos) {
		this.modelos = modelos;
	}

	public List<Categoria> getSubcategorias() {
		return subcategorias;
	}
	
	public boolean isEditando() {
		return this.produto.getId() != null;
	}
}