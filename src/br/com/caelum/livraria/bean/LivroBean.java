package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;

@ManagedBean
@ViewScoped
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Livro livro = new Livro();

	private Integer autorid;

	private List<Livro> livros;

	public void setAutorid(Integer autorid) {
		this.autorid = autorid;
	}

	public Integer getAutorid() {
		return autorid;
	}

	public Livro getLivro() {
		return livro;
	}

	public List<Livro> getLivros() {
		DAO<Livro> dao = new DAO<Livro>(Livro.class);
		if (this.livros == null) {
			this.livros = dao.listaTodos();
		}
		
		return livros;
	}

	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}
	
    public void carregarLivroPelaId() {
        this.livro = new DAO<Livro>(Livro.class).buscaPorId(this.livro.getId());
    }	

	public void gravarAutor() {
		Autor autor = new DAO<Autor>(Autor.class).buscaPorId(this.autorid);
		this.livro.adicionaAutor(autor);
		System.out.println("Livro escrito por: " + autor.getNome());
	}

	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			// throw new RuntimeException("Livro deve ter pelo menos um Autor.");
			FacesContext.getCurrentInstance().addMessage("autor",
					new FacesMessage("Livro deve ter pelo menos um Autor!"));
			return;
		}	
		DAO<Livro> dao = new DAO<Livro>(Livro.class);

		if (this.livro.getId() == null) {
			dao.adiciona(this.livro);
			/* Novo livro adicionado, listamos todos os livros novamente*/
			this.livros = dao.listaTodos();
		} else {
			dao.atualiza(this.livro);
		}
		this.livro = new Livro();
	}

	public void remover(Livro livro) {
		System.out.println("Removendo Livro " + livro.getTitulo());
		new DAO<Livro>(Livro.class).remove(livro);
//		this.livros.remove(livro);
	}

	public void removerAutorDoLivro(Autor autor) {
		System.out.println("Removendo Autor do Livro");
		this.livro.removeAutor(autor);
	}
	
	public void carregar(Livro livro) {
		System.out.println("Carrega Livro");
		this.livro = livro;
	}	

	public String formAutor() {
		System.out.println("Chamada do formulário do Autor!");
		return "autor?faces-redirect=true";
	}

	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		String valor = value.toString();
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("ISBN Deveria começar com 1!"));
		}
	}
}
