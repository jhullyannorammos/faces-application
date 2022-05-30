package br.com.application.controller.filter;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.application.model.Usuario;
import br.com.application.repository.UsuarioRepository;
import br.com.application.repository.filter.UsuarioFilter;
import br.com.application.service.NegocioException;
import br.com.application.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaUsuariosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioRepository usuarios;
	
	private UsuarioFilter filtro;
	private List<Usuario> usuariosFiltrados;
	
	private Usuario usuarioSelecionado;
	
	public PesquisaUsuariosBean() {
		filtro = new UsuarioFilter();
	}
	
	public void excluir() {
		try {
			usuarios.remover(usuarioSelecionado);
			usuariosFiltrados.remove(usuarioSelecionado);
			
			FacesUtil.addInfoMessage("Usuario " + usuarioSelecionado.getNome() 
					+ " excluído com sucesso.");
		} catch (NegocioException ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public void pesquisar() {
		usuariosFiltrados = usuarios.filtrados(filtro);
	}
	
	public List<Usuario> getUsuariosFiltrados() {
		return usuariosFiltrados;
	}

	public UsuarioFilter getFiltro() {
		return filtro;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}
	
}