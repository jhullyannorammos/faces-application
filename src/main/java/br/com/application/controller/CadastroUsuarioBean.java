package br.com.application.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DualListModel;

import br.com.application.model.Grupo;
import br.com.application.model.Usuario;
import br.com.application.repository.GrupoRepository;
import br.com.application.service.CadastroUsuarioService;
import br.com.application.service.NegocioException;
import br.com.application.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	private DualListModel<Grupo> listaGrupos;
	
	@Inject private CadastroUsuarioService cadastroUsuarioService;
	@Inject private GrupoRepository grupos;
	
	public void inicializar(){
		if (usuario == null) {
			limpar();
		} else {
			List<Grupo> lista = grupos.todos();
			lista.removeAll(usuario.getGrupos());
			listaGrupos = new DualListModel<>(lista, new ArrayList<>(usuario.getGrupos()));
		}
	}
	
	public void limpar() {
		this.usuario = new Usuario();
		
		listaGrupos = new DualListModel<>(grupos.todos(), new ArrayList<>());
	}
	
	public void salvar() {
		try {
			usuario.setGrupos(listaGrupos.getTarget());
			cadastroUsuarioService.salvar(usuario);
			limpar();
			
			FacesUtil.addInfoMessage("Usuário salvo com sucesso!");
		} catch(NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public boolean isEditando() {
		return usuario != null && usuario.getId() == null;
	}
	
	public DualListModel<Grupo> getListaGrupos() {
		return listaGrupos;
	}
	
	public void setListaGrupos(DualListModel<Grupo> listaGrupos) {
		this.listaGrupos = listaGrupos;
	}
}
