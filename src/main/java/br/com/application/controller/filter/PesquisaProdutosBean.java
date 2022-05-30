package br.com.application.controller.filter;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.application.model.Produto;
import br.com.application.repository.ProdutoRepository;
import br.com.application.repository.filter.ProdutoFilter;
import br.com.application.service.FotoService;
import br.com.application.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaProdutosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ProdutoRepository produtos;
	
	@Inject
	private FotoService fotoService;
	
	private ProdutoFilter filtro;
	private List<Produto> produtosFiltrados;	
	private Produto produtoSelecionado;
	
	public PesquisaProdutosBean() {
		filtro = new ProdutoFilter();
	}
	
	public void excluir() {
		try {
			produtos.remover(produtoSelecionado);
			produtosFiltrados.remove(produtoSelecionado);
			fotoService.deletar(produtoSelecionado.getFoto());
			
			FacesUtil.addInfoMessage("Produto " + produtoSelecionado.getSku() 
					+ " excluído com sucesso.");
		} catch (Exception e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void pesquisar() {
		produtosFiltrados = produtos.filtrados(filtro);
	}
	
	public List<Produto> getProdutosFiltrados() {
		return produtosFiltrados;
	}

	public ProdutoFilter getFiltro() {
		return filtro;
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}
	
}