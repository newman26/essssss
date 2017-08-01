package fr.adaming.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fr.adaming.model.Utilisateur;


public class UtilisateurRowMapper implements RowMapper<Utilisateur>{

	/**
	 * 
	 */
	@Override
	public Utilisateur mapRow(ResultSet rs, int rowNumber) throws SQLException {
		
		Utilisateur user = new Utilisateur(rs.getString("nom"), 
				                           rs.getString("prenom"), 
				                           rs.getString("mail"), 
				                           rs.getString("password"));
		
		return user;
	}

}
