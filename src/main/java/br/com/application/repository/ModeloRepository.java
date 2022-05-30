package br.com.application.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import br.com.application.model.Modelo;
import br.com.application.service.NegocioException;

@SuppressWarnings("serial")
public class ModeloRepository implements Serializable {
	
	@Inject private EntityManager manager;
	private List<Modelo> modelos;
	
	public List<Modelo> findAll(){
		modelos = manager.createQuery("from Modelo", Modelo.class).getResultList();
		return modelos;
	}
	
	public Modelo findBy(Long codigo) {
		return this.manager.find(Modelo.class, codigo);
	}

	public void persist(Modelo modelo) {
		manager.persist(modelo);
	}
	
	public Modelo update(Modelo modelo) {
		return manager.merge(modelo);
	}
	
	public void remove(Modelo modelo) throws NegocioException {
		try {
			modelo = findBy(modelo.getId());
			manager.remove(modelo);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Modelo não pode ser excluído.");
		}
	}

}
