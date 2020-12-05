package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.modelo.Autor;

@Named
@ViewScoped //Atenção: javax.faces.view.ViewScoped; 
public class AutorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Autor autor = new Autor();

	@Inject
	private AutorDao dao;//O CDI faz new AutorDao() e injeta
	
	private Integer autorId;
	
	public Integer getAutorId() {
		return autorId;
	}
	
	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public void carregarAutorPelaId() {
		this.autor = this.dao.buscaPorId(autorId);
	}

	public String gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		this.dao.adiciona(this.autor);

		autor = new Autor();

		return "livro?faces-redirect=true";
	}

	public void remover(Autor autor) {

		System.out.println("Removendo Autor " + autor.getNome());
		this.dao.remove(autor);

	}

	public List<Autor> getAutores() {
		return this.dao.listaTodos();
	}
		
	
	public Autor getAutor() {
		return autor;
	}
	
	public void setAutor(Autor autor) {
		this.autor = autor;
	}	
}
