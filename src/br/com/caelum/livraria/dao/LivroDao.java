package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.livraria.modelo.Livro;

public class LivroDao implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em; //new EntityManager -> O EntityManager é uma interface e não dá para instanciar um interface
	
	private DAO<Livro> dao;
	
	@PostConstruct
	void init() {
		this.dao = new DAO<Livro>(this.em, Livro.class);
	}

	public void adiciona(Livro t) {
		this.dao.adiciona(t);
	}

	public void remove(Livro t) {
		this.dao.remove(t);
	}

	public void atualiza(Livro t) {
		this.dao.atualiza(t);
	}

	public List<Livro> listaTodos() {
		return this.dao.listaTodos();
	}

	public Livro buscaPorId(Integer id) {
		return this.dao.buscaPorId(id);
	}

}
