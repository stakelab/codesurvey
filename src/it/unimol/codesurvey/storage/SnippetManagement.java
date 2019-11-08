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
import it.unimol.codesurvey.bean.Snippet;
import it.unimol.codesurvey.utilities.DbConnection;
import it.unimol.codesurvey.utilities.Utilities;

public class SnippetManagement {

	public static final String TABLE_SNIPPET = "snippet";
	
	public static int insert(Snippet snippet) throws IOException, PropertyVetoException {

		Connection connection = null;
		Statement statement = null;
		int snippetId = 0;
		try {

			connection = DbConnection.getInstance().getConnection();
			statement = connection.createStatement();

			String query = "INSERT INTO " + TABLE_SNIPPET + 
					"(text_to_show,system_name,related_resources)" +
					" VALUES "
					+ "(" + 
					Utilities.getSqlString(snippet.getTextToShow()) + "," +
					Utilities.getSqlString(snippet.getSystemName()) + "," +
					Utilities.getSqlString(snippet.getRelatedResources())
					+ ")";
			
			
			statement.executeUpdate(query);
			
			snippetId = Utilities.getMaxIdInTable(TABLE_SNIPPET);
			
			
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

		return snippetId;
	}
	
	public static boolean existsSnippet(Snippet snippet) throws IOException, PropertyVetoException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {

			connection = DbConnection.getInstance().getConnection();
			statement = connection.createStatement();

			String query = "SELECT * FROM " + TABLE_SNIPPET +
					" WHERE text_to_show=" + Utilities.getSqlString(snippet.getTextToShow());
			
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
	
	public static Collection<Snippet> getSnippets() throws IOException, PropertyVetoException {
		Collection<Snippet> retrievedSnippets = new ArrayList<Snippet>();
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {

			connection = DbConnection.getInstance().getConnection();
			statement = connection.createStatement();

			String query = "SELECT * FROM " + TABLE_SNIPPET;
			
			resultSet = statement.executeQuery(query);
			if(resultSet.next())
				retrievedSnippets = loadSnippetsFromRs(resultSet);
			
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
		
		return retrievedSnippets;
	}
	
	private static Snippet loadSnippetFromRs(ResultSet rs) throws SQLException, IOException, PropertyVetoException{
		Snippet snippet = new Snippet();
		
		snippet.setId(rs.getInt("id"));
		snippet.setTitle(rs.getString("title"));
		snippet.setTextToShow(rs.getString("text_to_show"));
		snippet.setSystemName(rs.getString("system_name"));
		snippet.setRelatedResources(rs.getString("related_resources"));
		snippet.setAssessments((ArrayList<Assessment>) AssessmentManagement.getAssessmentsBySnippetId(snippet.getId()));
		
		return snippet;
	}
	
	
	private static Collection<Snippet> loadSnippetsFromRs(ResultSet rs)throws SQLException, IOException, PropertyVetoException {
		Collection<Snippet> result = new ArrayList<Snippet>();
		do {
			result.add((Snippet) loadSnippetFromRs(rs));
		} while (rs.next());
		
		return result;
	}
	
}
