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
		// Pega a �rvore da p�gina;
		FacesContext context = event.getFacesContext();
		// Pega o nome da p�gina
		String nomePagina = context.getViewRoot().getViewId();
		System.out.println("O nome da p�gina �: " + nomePagina);
		
		// Se for a p�gina de ligin, ent�o n�o faz a verifica��o:
		if ("/login.xhtml".equals(nomePagina)) {
			return;
		}
		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		
		if (usuarioLogado != null) {
			return;
		}
		
		//Redirecionamento para login.xhtml (Se n�o for a pr�pria login.xhtml e n�o est� logado:
		NavigationHandler handler = context.getApplication().getNavigationHandler();
		handler.handleNavigation(context, null, "login?faces-redirect=true");
		
		// Pula todas as fases e renderiza a p�gina de login.
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
