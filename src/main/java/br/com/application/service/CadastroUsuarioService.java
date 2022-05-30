package br.com.application.service;

import java.io.Serializable;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.com.application.model.Usuario;
import br.com.application.repository.UsuarioRepository;
import br.com.application.util.jpa.Transactional;

public class CadastroUsuarioService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioRepository usuarios;
	
	@Transactional
	public Usuario salvar(Usuario usuario) throws NegocioException {
		if (usuario.getId() != null && StringUtils.isBlank(usuario.getSenha())) {
			usuario.setSenha(usuarios.buscarSenha(usuario.getId()));
		}
		
		return usuarios.guardar(usuario);
	}
}
