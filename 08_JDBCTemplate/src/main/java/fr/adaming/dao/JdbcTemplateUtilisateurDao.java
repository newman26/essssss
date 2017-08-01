package fr.adaming.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import fr.adaming.model.Utilisateur;


/**
 * 
 * @author INTI0261
 *
 */
public class JdbcTemplateUtilisateurDao implements IUtilisateurJdbcTemplateDao{

	private JdbcTemplate jdbcTemplate;
	
	private DataSource dataSource;

	/**
	 * setter du ds pour l'injection de dependance dans spring
	 * 
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * ajout d'un utilisateur
	 */
	@Override
	public void ajouterUtilisateur(Utilisateur pUtilisateur) {

		// requete sql
		String addReq = "INSERT INTO utilisateurs (nom,prenom,mail,password) " + "VALUES (?,?,?,?)";

		jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update( addReq, 
				             new Object[]{ pUtilisateur.getNom(), 
				            		       pUtilisateur.getPrenom(),
				            		       pUtilisateur.getMail(),
				            		       pUtilisateur.getMotDePasse()
				            		      }
						    );
		
	}

	/**
	 * recup d'un user par son id
	 */
	@Override
	public Utilisateur getUtilisateurById(int id) {

		// requete sql
		String getByIdReq = " SELECT * FROM utilisateurs " 
		                  + " WHERE id_utilisateur=?";
		
		//
		jdbcTemplate = new JdbcTemplate(dataSource);
		
		Utilisateur user = jdbcTemplate.queryForObject( 
				                      getByIdReq, 
				                      new Object[]{id},
				                      new UtilisateurRowMapper());
		
		return user;
	}

	/**
	 * 
	 */
	@Override
	public List<Utilisateur> getAllUtilisateurs() {
		
		// requete sql
		String getAlldReq = " SELECT * FROM utilisateurs ";

		//
		jdbcTemplate = new JdbcTemplate(dataSource);
		
//		List<Utilisateur> userList = new ArrayList<>();
//		
//		//
//		List<Map<String, Object>> lignes = 
//				jdbcTemplate.queryForList(getAlldReq);
//		
//		for(Map<String, Object> row : lignes){
//			
//			Utilisateur user = new Utilisateur((int)row.get("id_utilisateur"),
//					                           (String)row.get("nom"), 
//					                           (String)row.get("prenom"), 
//					                           (String)row.get("mail"), 
//					                           (String)row.get("password"));
//			
//			userList.add(user);
//			
//		}
//		
//		return userList;
		return jdbcTemplate.query(getAlldReq,new UtilisateurRowMapper());
	}

}





