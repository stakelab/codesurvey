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
import it.unimol.codesurvey.utilities.DbConnection;
import it.unimol.codesurvey.utilities.Utilities;

public class AssessmentManagement {

	public static final String TABLE_ASSESSMENT = "assessment";
	
	public static int insert(Assessment assessment) throws IOException, PropertyVetoException {

		Connection connection = null;
		Statement statement = null;
		int assessmentId = 0;
		try {

			connection = DbConnection.getInstance().getConnection();
			statement = connection.createStatement();

			String query = "INSERT INTO " + TABLE_ASSESSMENT + 
					"(participant_id,snippet_id,seconds_needed,understood,correctness)" + 
					" VALUES "
					+ "(" + 
					assessment.getParticipantId() + "," +
					assessment.getSnippetId() + "," +
					assessment.getSecondsNeeded() + "," +
					Utilities.booleanToInt(assessment.isUnderstood()) + "," +
					assessment.getCorrectnessOfCheckingQuestions() + ")";
			
			
			statement.executeUpdate(query);
			assessmentId = Utilities.getMaxIdInTable(TABLE_ASSESSMENT);
			
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

		return assessmentId;
	}
	
	public static boolean hasParticipantAlreadyEvaluatedAsnippet(int participantId, int snippetId) throws IOException, PropertyVetoException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {

			connection = DbConnection.getInstance().getConnection();
			statement = connection.createStatement();

			String query = "SELECT * FROM " + TABLE_ASSESSMENT +
					" WHERE participant_id=" + participantId + 
					" AND snippet_id=" + snippetId;
			
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
	
	
	public static Collection<Assessment> getAssessmentsBySnippetId(int snippetId) throws IOException, PropertyVetoException {
		Collection<Assessment> retrievedAssessments = new ArrayList<Assessment>();
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {

			connection = DbConnection.getInstance().getConnection();
			statement = connection.createStatement();

			String query = "SELECT * FROM " + TABLE_ASSESSMENT + 
					" WHERE snippet_id = " + snippetId;
			
			resultSet = statement.executeQuery(query);
			if(resultSet.next())
				retrievedAssessments = loadAssessmentsFromRs(resultSet);
			
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
		
		return retrievedAssessments;

	}
	
	
	public static Collection<Assessment> getAssessmentsByParticipantId(int participantId) throws IOException, PropertyVetoException {
		Collection<Assessment> retrievedAssessments = new ArrayList<Assessment>();
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {

			connection = DbConnection.getInstance().getConnection();
			statement = connection.createStatement();

			String query = "SELECT * FROM " + TABLE_ASSESSMENT + 
					" WHERE participant_id = " + participantId;
			
			resultSet = statement.executeQuery(query);
			if(resultSet.next())
				retrievedAssessments = loadAssessmentsFromRs(resultSet);
			
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
		
		return retrievedAssessments;

	}
	
	
	private static Assessment loadAssessmentFromRs(ResultSet rs) throws SQLException, IOException, PropertyVetoException{
		Assessment assessment = new Assessment();
		
		assessment.setId(rs.getInt("id"));
		assessment.setParticipantId(rs.getInt("participant_id"));
		assessment.setSnippetId(rs.getInt("snippet_id"));
		assessment.setSecondsNeeded(rs.getInt("seconds_needed"));
		assessment.setUnderstood(Utilities.intToBoolean(rs.getInt("understood")));
		assessment.setCorrectnessOfCheckingQuestions(rs.getDouble("correctness"));
		
		return assessment;
	}
	
	
	private static Collection<Assessment> loadAssessmentsFromRs(ResultSet rs)throws SQLException, IOException, PropertyVetoException {
		Collection<Assessment> result = new ArrayList<Assessment>();
		do {
			result.add((Assessment) loadAssessmentFromRs(rs));
		} while (rs.next());
		
		return result;
	}
	
}
