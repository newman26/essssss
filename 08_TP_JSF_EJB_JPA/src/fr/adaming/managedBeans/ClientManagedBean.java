package fr.adaming.managedBeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import fr.adaming.model.Agent;
import fr.adaming.model.Client;
import fr.adaming.services.IClientService;

@ManagedBean(name = "cMB")
@RequestScoped
public class ClientManagedBean {

	@EJB
	private IClientService clientService;

	private Client client;

	private Agent agent;

	private HttpSession session;

	public ClientManagedBean() {
		this.client = new Client();

	}

	@PostConstruct // cette annotation sert à executer la methode juste apres
					// l'instantiation du managedBean
	public void init() {

		// recuperation du context
		FacesContext context = FacesContext.getCurrentInstance();

		// recuperation de la session
		this.session = (HttpSession) context.getExternalContext().getSession(false);

		// recuperation de l'agent à partir de la session
		this.agent = (Agent) session.getAttribute("agentSession");
	}

	// les getters et setters pour les attributs appelés à partir de la page
	// xhtml
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String ajouterClient() {

		// appel de la methode service pour ajouter le client
		Client clientOut = clientService.addClient(this.client, this.agent);

		if (clientOut.getId() != 0) {

			// recuperer la nouvelle liste à partir de la bd
			List<Client> listeClients = clientService.getAllClientsByAgent(agent);

			// acctualiser la liste des clients dans la session
			session.setAttribute("clientListe", listeClients);

			return "accueil";
		} else {
			// afficher le message d'erreur su la page
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("L'ajout a echoué!!!"));
			return "ajout";

		}

	}

	public String modifierClient() {
		int verif = clientService.updateClient(this.client, this.agent);

		if (verif == 1) {
			// recuperer la nouvelle liste à partir de la bd
			List<Client> listeClients = clientService.getAllClientsByAgent(agent);

			// acctualiser la liste des clients dans la session
			session.setAttribute("clientListe", listeClients);

			return "accueil";

		} else {
			// afficher le message d'erreur su la page
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("L'ajout a echoué!!!"));
			return "#";
		}
	}

	public String supprimerClient() {
		int verif = clientService.deleteClient(this.client, this.agent);

		if (verif == 1) {
			// Récupération de la nouvelle liste à partir de la bd
			List<Client> listeClients = clientService.getAllClientsByAgent(this.agent);
			// acctualiser la liste des clients dans la session
			session.setAttribute("clientListe", listeClients);
			return "accueil";
		} else {
			// afficher le message d'erreur su la page
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La suppression a echoué!!!"));
			return "supp";
		}

	}

	public String rechercherClient(){
		
		Client clientOut = clientService.getClientById(this.client,this.agent);
		
		if(clientOut!=null){
			this.client = clientOut;
			return "recherche";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Client introuvable!!!"));
			return "recherche";
		}
	}
	
}
