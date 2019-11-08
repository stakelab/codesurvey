package it.unimol.codesurvey.storage;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import it.unimol.codesurvey.bean.Class;
import it.unimol.codesurvey.utilities.DbConnection;
import it.unimol.codesurvey.utilities.Utilities;

public class ClassManagement {

	public static final String TABLE_SNIPPET = "classes";
	
	public static int insert(Class snippet) throws IOException, PropertyVetoException {

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
	
	public static boolean existsClass(Class snippet) throws IOException, PropertyVetoException {
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
		
	public static Class getClass(int id) throws IOException, PropertyVetoException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {

			connection = DbConnection.getInstance().getConnection();
			statement = connection.createStatement();

			String query = "SELECT * FROM " + TABLE_SNIPPET + " WHERE id="+id;
			
			resultSet = statement.executeQuery(query);
			if(resultSet.next())
				return loadClassFromRs(resultSet);
			
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
		
		return null;
        }
	
	
	private static Class loadClassFromRs(ResultSet rs) throws SQLException, IOException, PropertyVetoException{
		Class snippet = new Class();
		
		snippet.setId(rs.getInt("id"));
		snippet.setTextToShow(rs.getString("text_to_show"));
		snippet.setSystemName(rs.getString("system_name"));
		snippet.setRelatedResources(rs.getString("related_resources"));
		
		return snippet;
	}
}
