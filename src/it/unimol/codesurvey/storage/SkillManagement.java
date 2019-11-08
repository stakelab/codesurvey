package it.unimol.codesurvey.storage;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import it.unimol.codesurvey.bean.Participant;
import it.unimol.codesurvey.utilities.DbConnection;
import it.unimol.codesurvey.utilities.Utilities;

public class SkillManagement {

	public static final String TABLE_EXPERIENCE = "skills";
	
	public static void upsert(Participant participant, String skill, String experience) throws IOException, PropertyVetoException {

		Connection connection = null;
		Statement statement = null;
		try {

			connection = DbConnection.getInstance().getConnection();
			statement = connection.createStatement();

			String query = "INSERT INTO " + TABLE_EXPERIENCE + 
					"(participant_id, field, exp)" + 
					" VALUES "
					+ "(" + 
					participant.getId() + "," +
					Utilities.getSqlString(skill) + "," +
					Utilities.getSqlString(experience) 
					+ ") ON DUPLICATE KEY UPDATE "
					+ "`exp` = " + Utilities.getSqlString(experience);
			
			statement.executeUpdate(query);			
			
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} finally {
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	public static boolean hasAddedSkills(Participant participant) throws IOException, PropertyVetoException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnection.getInstance().getConnection();
			statement = connection.createStatement();

			String query = "SELECT COUNT(*) AS addedSkills FROM " + TABLE_EXPERIENCE + " WHERE participant_id = " + participant.getId();
			
			resultSet = statement.executeQuery(query);
			if (resultSet.next()) {
				int addedSkills = resultSet.getInt("addedSkills");
				return addedSkills != 0;
			}
			
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} finally {
			if (resultSet != null)
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return false;
	}
	
	public static void retrieveExperience(Participant participant) throws IOException, PropertyVetoException {
		Map<String, String> skills = new HashMap<>(); 
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {

			connection = DbConnection.getInstance().getConnection();
			statement = connection.createStatement();

			String query = "SELECT * FROM " + TABLE_EXPERIENCE + " WHERE participant_id = " + participant.getId();
			
			resultSet = statement.executeQuery(query);
			if(resultSet.next())
				skills = loadSkillsFromRs(resultSet);
			
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} finally {
			if (resultSet != null)
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		participant.setSkills(skills);
	}
	
	private static Map<String, String> loadSkillsFromRs(ResultSet rs)throws SQLException, IOException, PropertyVetoException {
		Map<String, String> result = new HashMap<>();
		do {
			result.put(rs.getString("field"), rs.getString("exp"));
		} while (rs.next());
		
		return result;
	}
	
}
