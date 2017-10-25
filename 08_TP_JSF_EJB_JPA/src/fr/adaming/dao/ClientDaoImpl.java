package fr.adaming.dao;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.adaming.model.Agent;
import fr.adaming.model.Client;

@Stateless
@Local(fr.adaming.dao.IClientDao.class)
public class ClientDaoImpl implements IClientDao {

	@PersistenceContext(unitName = "PU_TP")
	EntityManager em;

	@Override
	public List<Client> getAllClientsByAgent(Agent a) {
		// La requete JPQL
		String req = "SELECT cl FROM Client cl WHERE cl.agent.id=:pId";

		Query query = em.createQuery(req);

		// passage des params
		query.setParameter("pId", a.getId());

		List<Client> liste = query.getResultList();

		return liste;
	}

	@Override
	public Client addClient(Client cl) {
		
		System.out.println("------------- ID du client avant persist   "+cl.getId());
		em.persist(cl);
		System.out.println("\n------------- ID du client apres persist   "+cl.getId());
		return cl;
	}

	@Override
	public int updateClient(Client cl) {
		// Définir la requête SQL
		String req = "UPDATE Client cl SET cl.dateNaissance=:pDate, cl.prenom=:pPrenom,cl.nom=:pNom WHERE cl.agent.id=:pIDAgent AND cl.id=:pIDClient";
		Query query = em.createQuery(req);
		
		// Passage des paramètres
		query.setParameter("pDate", cl.getDateNaissance());
		query.setParameter("pPrenom", cl.getPrenom());
		query.setParameter("pNom", cl.getNom());
		query.setParameter("pIDAgent", cl.getAgent().getId());
		query.setParameter("pIDClient", cl.getId());

		
		return query.executeUpdate();
	}

	@Override
	public int deleteClient(Client cl) {
		// Rédaction de la requête
		String req = "DELETE FROM Client cl WHERE cl.id=:pId AND cl.agent.id=:pAgent";
		Query query = em.createQuery(req);
		query.setParameter("pId", cl.getId());
		query.setParameter("pAgent", cl.getAgent().getId());
		
		
		return query.executeUpdate();
	}

	@Override
	public Client getClientById(Client cl) {
		
		Client rechClient = em.find(Client.class, cl.getId());
		
		if(rechClient != null){
			if (cl.getAgent().getId()==rechClient.getAgent().getId()){

				return rechClient;
			} else {
				return null;
			}
		}else {
			return null;
		}
			
		
		
	}

}
