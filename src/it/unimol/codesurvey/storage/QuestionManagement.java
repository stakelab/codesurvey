package it.unimol.codesurvey.storage;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import it.unimol.codesurvey.bean.Answer;
import it.unimol.codesurvey.bean.Question;
import it.unimol.codesurvey.utilities.DbConnection;
import it.unimol.codesurvey.utilities.Utilities;

public class QuestionManagement {

	public static final String TABLE_QUESTION = "question";
	
	public static int insert(Question question) throws IOException, PropertyVetoException {

		Connection connection = null;
		Statement statement = null;
		int questionId = 0;
		try {
			connection = DbConnection.getInstance().getConnection();
			statement = connection.createStatement();

			String query = "INSERT INTO " + TABLE_QUESTION + 
					"(question)" + 
					" VALUES "
					+ "(" + Utilities.getSqlString(question.getQuestion()) + ")";
			
			
			statement.executeUpdate(query);
			questionId = Utilities.getMaxIdInTable(TABLE_QUESTION);
			
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

		return questionId;
	}
	
	public static Collection<Question> getQuestionsBySnippetId(int snippetId) throws IOException, PropertyVetoException {
		Collection<Question> retrievedQuestions = new ArrayList<Question>();
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {

			connection = DbConnection.getInstance().getConnection();
			statement = connection.createStatement();

			String query = "SELECT * FROM " + TABLE_QUESTION + 
					" WHERE snippet_id = " + snippetId;
			
			resultSet = statement.executeQuery(query);
			if(resultSet.next())
				retrievedQuestions = loadQuestionsFromRs(resultSet);
			
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
		
		return retrievedQuestions;

	}
	
	
	
	private static Question loadQuestionFromRs(ResultSet rs) throws SQLException, IOException, PropertyVetoException{
		Question question = new Question();
		
		question.setId(rs.getInt("id"));
		question.setQuestion(rs.getString("question"));
		question.setAnswers((ArrayList<Answer>) AnswerManagement.getAnswersByQuestionId(question.getId()));
		
		return question;
	}
	
	
	private static Collection<Question> loadQuestionsFromRs(ResultSet rs)throws SQLException, IOException, PropertyVetoException {
		Collection<Question> result = new ArrayList<Question>();
		do {
			result.add((Question) loadQuestionFromRs(rs));
		} while (rs.next());
		
		return result;
	}
	
}
