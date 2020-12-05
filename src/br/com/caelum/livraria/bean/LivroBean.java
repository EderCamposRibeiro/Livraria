package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;

@Named
@ViewScoped
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Livro livro = new Livro();
	
	@Inject
	private LivroDao daoLivro; //O CDI faz new LivroDao() e injeta
	
	@Inject
	private AutorDao daoAutor; //O CDI faz new LivroDao() e injeta	

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
		if (this.livros == null) {
			this.livros = daoLivro.listaTodos();
		}
		
		return livros;
	}

	public List<Autor> getAutores() {
		return this.daoAutor.listaTodos();
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}
	
    public void carregarLivroPelaId() {
        this.livro = daoLivro.buscaPorId(this.livro.getId());
    }	

	public void gravarAutor() {
		Autor autor = daoAutor.buscaPorId(this.autorid);
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

		if (this.livro.getId() == null) {
			daoLivro.adiciona(this.livro);
			/* Novo livro adicionado, listamos todos os livros novamente*/
			this.livros = daoLivro.listaTodos();
		} else {
			daoLivro.atualiza(this.livro);
		}
		this.livro = new Livro();
	}

	public void remover(Livro livro) {
		System.out.println("Removendo Livro " + livro.getTitulo());
		daoLivro.remove(livro);
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
