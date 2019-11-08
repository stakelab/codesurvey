package it.unimol.codesurvey.utilities;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utilities {

	static final String dbDriver = "com.mysql.jdbc.Driver";
	static final String dbAddress;
	static final String dbUsername;
	static final String dbPassword;

	static {
		if (!System.getenv().containsKey("MYSQL_HOST")) {
			System.err.println("You must specify the environment variable MYSQL_HOST");
			System.exit(-1);
		}

		if (!System.getenv().containsKey("MYSQL_USER")) {
			System.err.println("You must specify the environment variable MYSQL_USER");
			System.exit(-1);
		}

		if (!System.getenv().containsKey("MYSQL_PASSWORD")) {
			System.err.println("You must specify the environment variable MYSQL_PASSWORD");
			System.exit(-1);
		}

		if (!System.getenv().containsKey("MYSQL_DATABASE")) {
			System.err.println("You must specify the environment variable MYSQL_DATABASE");
			System.exit(-1);
		}

		dbAddress = "jdbc:mysql://" + System.getenv("MYSQL_HOST") + ":3306/" + System.getenv("MYSQL_DATABASE");
		dbUsername = System.getenv("MYSQL_USER");
		dbPassword = System.getenv("MYSQL_PASSWORD");
	}
	
	public static int numberOfSnippetsToUnderstand = 8;
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	//general
	public static String defaultErrorMessage = "ERROR: ";

	public static String getSqlString(String input){
		return "\"" + input.replace("'", "").replace("\"", "") + "\"";
	}
	
	public static int booleanToInt(boolean value){
		if(value)
			return 1;
		else
			return 0;
	}
	
	public static boolean intToBoolean(int value){
		return value == 1;
	}
	
	public static String getSQLdate(Date date){
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		return Utilities.getSqlString(dateFormatter.format(date));
	}

	public static double roundTo1Decimal(double val) {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat df2 = new DecimalFormat("#.#", symbols);
        return Double.parseDouble(df2.format(val));
	}
	
	public static int getMaxIdInTable(String tableName) throws IOException, PropertyVetoException{
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {

			connection = DbConnection.getInstance().getConnection();
			statement = connection.createStatement();

			String query = "SELECT MAX(id) AS id FROM " + tableName;
			
			resultSet = statement.executeQuery(query);
			if(resultSet.next())
				return resultSet.getInt("id");
			
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
		
		return 0;
		
	}
	
	
	
	
	
	
}
