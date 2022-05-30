package br.com.application.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.application.model.Categoria;

public class CategoriaRepository implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject private EntityManager manager;
	
	public Categoria merge(Categoria categoria) {
		return manager.merge(categoria);
	}
	
	public void persist(Categoria categoria) {
		manager.persist(categoria);
	}
	
	public Categoria porId(Long id) {
		return manager.find(Categoria.class, id);
	}
	
	public List<Categoria> raizes() {
		return manager.createQuery("from Categoria", Categoria.class).getResultList();
	}
	
	public List<Categoria> subcategoriasDe(Categoria categoriaPai) {
		return manager.createQuery("from Categoria where categoriaPai = :raiz", 
				Categoria.class).setParameter("raiz", categoriaPai).getResultList();
	}
	
}