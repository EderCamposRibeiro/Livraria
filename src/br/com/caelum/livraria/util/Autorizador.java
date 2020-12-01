package br.com.caelum.livraria.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.caelum.livraria.modelo.Usuario;

public class Autorizador implements PhaseListener{

	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent event) {
		// Pega a árvore da página;
		FacesContext context = event.getFacesContext();
		// Pega o nome da página
		String nomePagina = context.getViewRoot().getViewId();
		System.out.println("O nome da página é: " + nomePagina);
		
		// Se for a página de ligin, então não faz a verificação:
		if ("/login.xhtml".equals(nomePagina)) {
			return;
		}
		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		
		if (usuarioLogado != null) {
			return;
		}
		
		//Redirecionamento para login.xhtml (Se não for a própria login.xhtml e não está logado:
		NavigationHandler handler = context.getApplication().getNavigationHandler();
		handler.handleNavigation(context, null, "login?faces-redirect=true");
		
		// Pula todas as fases e renderiza a página de login.
		context.renderResponse();
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
