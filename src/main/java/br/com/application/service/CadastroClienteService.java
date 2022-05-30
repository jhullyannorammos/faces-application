package br.com.application.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.application.model.Cliente;
import br.com.application.repository.ClienteRepository;
import br.com.application.util.jpa.Transactional;

public class CadastroClienteService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject private ClienteRepository clientes;
	
	@Transactional
	public Cliente salvar(Cliente cliente) throws NegocioException {
		return clientes.guardar(cliente);
	}
}