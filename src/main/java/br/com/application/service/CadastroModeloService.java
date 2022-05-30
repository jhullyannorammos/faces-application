package br.com.application.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.application.model.Modelo;
import br.com.application.repository.ModeloRepository;
import br.com.application.util.jpa.Transactional;

@SuppressWarnings("serial")
public class CadastroModeloService implements Serializable{
	
	@Inject ModeloRepository modeloRespository;

	@Transactional
	public void persist(Modelo modelo) throws NegocioException {
		modeloRespository.persist(modelo);
	}
	
	@Transactional
	public Modelo update(Modelo modelo) throws NegocioException {
		return modeloRespository.update(modelo);
	}

}
