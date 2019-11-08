package it.unimol.codesurvey.storage;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import it.unimol.codesurvey.bean.Assessment;
import it.unimol.codesurvey.bean.Participant;
import it.unimol.codesurvey.utilities.DbConnection;
import it.unimol.codesurvey.utilities.Utilities;

public class ParticipantManagement {

	public static final String TABLE_PARTICIPANT = "participant";
	
	public static int insert(Participant participant) throws IOException, PropertyVetoException {

		Connection connection = null;
		Statement statement = null;
		int participantId = 0;
		try {

			connection = DbConnection.getInstance().getConnection();
			statement = connection.createStatement();

			String query = "INSERT INTO " + TABLE_PARTICIPANT + 
					"(name,email,position,java_experience,programming_experience,username,password)" + 
					" VALUES "
					+ "(" + 
					Utilities.getSqlString(participant.getName()) + "," +
					Utilities.getSqlString(participant.getEmail()) + "," +
					Utilities.getSqlString(participant.getPosition()) + "," +
					participant.getJavaExperience() + "," +
					participant.getProgrammingExperience() + "," +
					Utilities.getSqlString(participant.getUsername()) + "," +
					Utilities.getSqlString(participant.getPassword()) 
					+ ")";
			
			
			statement.executeUpdate(query);
			
			participantId = Utilities.getMaxIdInTable(TABLE_PARTICIPANT);
			
			
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

		return participantId;
	}
	
	public static boolean existsParticipant(String username) throws IOException, PropertyVetoException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {

			connection = DbConnection.getInstance().getConnection();
			statement = connection.createStatement();

			String query = "SELECT * FROM " + TABLE_PARTICIPANT +
					" WHERE username=" + Utilities.getSqlString(username);
			
			resultSet = statement.executeQuery(query);
			if(resultSet.next())
				return true;
			
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
	
	public static Participant login(String username, String password) throws IOException, PropertyVetoException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		Participant loggedIn = null;
		
		try {

			connection = DbConnection.getInstance().getConnection();
			statement = connection.createStatement();

			String query = "SELECT * FROM " + TABLE_PARTICIPANT +
					" WHERE username=" + Utilities.getSqlString(username) +
					" AND password=" + Utilities.getSqlString(password);
			
			resultSet = statement.executeQuery(query);
			if(resultSet.next())
				loggedIn = loadParticipantFromRs(resultSet);
			
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
		
		return loggedIn;
	}
	
	public static Collection<Participant> getParticipants() throws IOException, PropertyVetoException {
		Collection<Participant> retrievedParticipants = new ArrayList<Participant>();
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {

			connection = DbConnection.getInstance().getConnection();
			statement = connection.createStatement();

			String query = "SELECT * FROM " + TABLE_PARTICIPANT;
			
			resultSet = statement.executeQuery(query);
			if(resultSet.next())
				retrievedParticipants = loadParticipantsFromRs(resultSet);
			
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
		
		return retrievedParticipants;

	}
	
	
	private static Participant loadParticipantFromRs(ResultSet rs) throws SQLException, IOException, PropertyVetoException{
		Participant participant = new Participant();
		
		participant.setId(rs.getInt("id"));
		participant.setName(rs.getString("name"));
		participant.setEmail(rs.getString("email"));
		participant.setPosition(rs.getString("position"));
		participant.setJavaExperience(rs.getInt("java_experience"));
		participant.setJavaExperience(rs.getInt("programming_experience"));
		participant.setUsername(rs.getString("username"));
		participant.setAssessments((ArrayList<Assessment>) AssessmentManagement.getAssessmentsByParticipantId(participant.getId()));
		
		return participant;
	}
	
	
	private static Collection<Participant> loadParticipantsFromRs(ResultSet rs)throws SQLException, IOException, PropertyVetoException {
		Collection<Participant> result = new ArrayList<Participant>();
		do {
			result.add((Participant) loadParticipantFromRs(rs));
		} while (rs.next());
		
		return result;
	}
	
}
