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
import it.unimol.codesurvey.utilities.DbConnection;
import it.unimol.codesurvey.utilities.Utilities;

public class AnswerManagement {

	public static final String TABLE_ANSWER = "answer";
	public static final String TABLE_PROVIDED_ANSWER = "provided_answers";
	
	public static int insert(Answer answer) throws IOException, PropertyVetoException {

		Connection connection = null;
		Statement statement = null;
		int answerId = 0;
		try {
			connection = DbConnection.getInstance().getConnection();
			statement = connection.createStatement();

			String query = "INSERT INTO " + TABLE_ANSWER + 
					"(question_id,text,correct)" + 
					" VALUES "
					+ "(" + 
					answer.getQuestionId() + "," +
					Utilities.getSqlString(answer.getText()) + "," +
					Utilities.booleanToInt(answer.isCorrect()) + ")";
			
			
			statement.executeUpdate(query);
			answerId = Utilities.getMaxIdInTable(TABLE_ANSWER);
			
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

		return answerId;
	}
	
	
	public static int insertProvidedAnswer(Answer providedAnswer, int assessmentId) throws IOException, PropertyVetoException {

		Connection connection = null;
		Statement statement = null;
		int answerId = 0;
		try {
			connection = DbConnection.getInstance().getConnection();
			statement = connection.createStatement();

			String query = "INSERT INTO " + TABLE_PROVIDED_ANSWER + 
					"(assessment_id,question_id,given_answer_id,correct)" + 
					" VALUES "
					+ "(" + 
					assessmentId + "," +
					providedAnswer.getQuestionId() + "," +
					providedAnswer.getId() + "," +
					Utilities.booleanToInt(providedAnswer.isCorrect()) + ")";
			
			
			statement.executeUpdate(query);
			answerId = Utilities.getMaxIdInTable(TABLE_ANSWER);
			
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

		return answerId;
	}
	
	public static Collection<Answer> getAnswersByQuestionId(int questionId) throws IOException, PropertyVetoException {
		Collection<Answer> retrievedAnswers = new ArrayList<Answer>();
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {

			connection = DbConnection.getInstance().getConnection();
			statement = connection.createStatement();

			String query = "SELECT * FROM " + TABLE_ANSWER + 
					" WHERE question_id = " + questionId;
			
			resultSet = statement.executeQuery(query);
			if(resultSet.next())
				retrievedAnswers = loadAnswersFromRs(resultSet);
			
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
		
		return retrievedAnswers;

	}
	
	
	public static Answer getAnswerById(int answerId) throws IOException, PropertyVetoException {
		Answer retrievedAnswer = null;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {

			connection = DbConnection.getInstance().getConnection();
			statement = connection.createStatement();

			String query = "SELECT * FROM " + TABLE_ANSWER + 
					" WHERE id = " + answerId;
			
			resultSet = statement.executeQuery(query);
			if(resultSet.next())
				retrievedAnswer = loadAnswerFromRs(resultSet);
			
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
		
		return retrievedAnswer;

	}
	
	
	
	private static Answer loadAnswerFromRs(ResultSet rs) throws SQLException, IOException, PropertyVetoException{
		Answer answer = new Answer();
		
		answer.setId(rs.getInt("id"));
		answer.setCorrect(Utilities.intToBoolean(rs.getInt("correct")));
		answer.setQuestionId(rs.getInt("question_id"));
		answer.setText(rs.getString("text"));
		
		return answer;
	}
	
	
	private static Collection<Answer> loadAnswersFromRs(ResultSet rs)throws SQLException, IOException, PropertyVetoException {
		Collection<Answer> result = new ArrayList<Answer>();
		do {
			result.add((Answer) loadAnswerFromRs(rs));
		} while (rs.next());
		
		return result;
	}
	
}
