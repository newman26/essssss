package fr.adaming.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import fr.adaming.model.Agent;
import fr.adaming.model.Client;
import fr.adaming.services.IAgentService;
import fr.adaming.services.IClientService;

@ManagedBean(name = "aMB")
@RequestScoped
public class AgentManagedBean implements Serializable {

	// transformation de l'association UML en JAVA et l'injection de service
	@EJB
	private IAgentService agentService;

	@EJB
	private IClientService clientService;

	// les attributs utilisé dans la pages
	private Agent agent;

	// Constructeur vide
	public AgentManagedBean() {
		this.agent = new Agent();
	}

	public IAgentService getAgentService() {
		return agentService;
	}

	public void setAgentService(IAgentService agentService) {
		this.agentService = agentService;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public IClientService getClientService() {
		return clientService;
	}

	public void setClientService(IClientService clientService) {
		this.clientService = clientService;
	}

	// les methodes metiers du managedBean
	public String seConnecter() {
		try {
			Agent agentOut = agentService.isExist(this.agent);

			// recuperation de la liste des clients
			List<Client> listeClients = agentOut.getListeClients();//clientService.getAllClientsByAgent(agentOut);

			// ajouter la liste des client dans la session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("clientListe", listeClients);

			// ajouter l'agent dans la session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("agentSession", agentOut);

			return "succes";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("L'identifient et/ ou le mot de passe erroné(s)"));
		}
		return "echec";

	}

	// la methode pour se deconnecter
	public String seDeconnecter() {
		// recuperer la session et la fermer
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "login";
	}

}
