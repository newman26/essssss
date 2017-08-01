package fr.adaming.dao;

import java.util.List;

import fr.adaming.model.Utilisateur;



public interface IUtilisateurJdbcTemplateDao {

	void ajouterUtilisateur(Utilisateur utilisateur);

	Utilisateur getUtilisateurById(int id);

	List<Utilisateur> getAllUtilisateurs();
	
	
}
