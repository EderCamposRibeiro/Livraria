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

	private Integer livroId;

	public Integer getLivroId() {
		return livroId;
	}

	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}

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
		return new DAO<Livro>(Livro.class).listaTodos();
	}

	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}
	
    public void carregarLivroPelaId() {
        this.livro = new DAO<Livro>(Livro.class).buscaPorId(this.livroId);
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
		} else {
			if (this.livro.getId() == null) {
				new DAO<Livro>(Livro.class).adiciona(this.livro);
			} else {
				new DAO<Livro>(Livro.class).atualiza(this.livro);
			}
			this.livro = new Livro();
		}

	}

	public void carregar(Livro livro) {
		System.out.println("Carrega Livro");
		this.livro = livro;
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

	public String formAutor() {
		System.out.println("Chamada do formul�rio do Autor!");
		return "autor?faces-redirect=true";
	}

	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		String valor = value.toString();
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("ISBN Deveria come�ar com 1!"));
		}
	}

}
