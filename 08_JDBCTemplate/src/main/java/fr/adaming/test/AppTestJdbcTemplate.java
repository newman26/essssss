package fr.adaming.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import fr.adaming.dao.IUtilisateurJdbcTemplateDao;
import fr.adaming.dao.JdbcTemplateUtilisateurDao;
import fr.adaming.model.Utilisateur;



public class AppTestJdbcTemplate {

	public static void main(String[] args) {

		// context
		ApplicationContext context = new ClassPathXmlApplicationContext("user-allModules.xml");

		// recu du bean dao
		IUtilisateurJdbcTemplateDao userDAO = (JdbcTemplateUtilisateurDao) 
				context.getBean("userJdbcTemplateDaoBean");

		// def des users a ajouter
		Utilisateur user1 = new Utilisateur("MALANDRINO", "pier-jean", "maland@gmail.com", "123");
		Utilisateur user2 = new Utilisateur("BRISSON", "jimmy", "brisson@gmail.com", "345");
		Utilisateur user3 = new Utilisateur("ROSTAGNAT", "philippe", "ros@gmail.com", "789");
		Utilisateur user4 = new Utilisateur("VIGNON", "fabien", "vign@gmail.com", "963");
		
		Utilisateur user7 = new Utilisateur("AGIS", "frantz", "agis@gmail.com", "524");


		// ajout des users
		 userDAO.ajouterUtilisateur(user1);
		 userDAO.ajouterUtilisateur(user2);
		 userDAO.ajouterUtilisateur(user3);
		 userDAO.ajouterUtilisateur(user4);
		 userDAO.ajouterUtilisateur(user7);

		// recup user par id
		System.out.println("=============== USERS BY ID =============");
		Utilisateur user5 = userDAO.getUtilisateurById(1);
		Utilisateur user6 = userDAO.getUtilisateurById(5);
		System.out.println("\tuser ----> : " + user5.getPrenom());
		System.out.println("\tuser ----> : " + user6.getPrenom());

		// recup users
		System.out.println("=============== USERS LIST =============");
		List<Utilisateur> userList = userDAO.getAllUtilisateurs();
		for (Utilisateur util : userList) {
			System.out.println("\t- " + util.getPrenom());
		}
		
		System.out.println("-------------------------- "+userList.size());

	}

}
