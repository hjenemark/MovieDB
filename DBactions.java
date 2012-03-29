package home.moviedb.mdb;

import java.sql.*;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class DBactions {
	
	private boolean connected;
	private static Connection connection = null;
	private static DBactions dbActions;
	
	private static final String dbClassName = "com.mysql.jdbc.Driver";
	private static final String CONNECTION = "jdbc:mysql://127.0.0.1/MovieDB";
	
	public boolean isConnected(){
		return connected;
	}
	
	private DBactions(String user, String pass){
		connected = false;
		try {
			connect(user, pass);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public static synchronized DBactions getInstance(String user, String pass){
		if (dbActions == null){
			dbActions = new DBactions(user, pass);
		}
		return dbActions;
	}
	
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	private void connect(String user, String pass) throws ClassNotFoundException,SQLException {
		Class.forName(dbClassName);
		
		Properties connProperties = new Properties();
		connProperties.put("user", user);
		connProperties.put("password", pass);
		
		connection = DriverManager.getConnection(CONNECTION, connProperties);
		
		if(connection != null){
			connected = true;
		} else {
			connected = false;
		}
	}
	
	public void disconnect(){
		
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	private void popUpDialog(String message){
		JOptionPane.showInputDialog("")
	}*/

	public boolean saveEntry(MovieDBEntry movie){
		String sqlInputString = "INSERT INTO MovieDB.MovieArchive (title, director, lead, secondary, runtime, imdburl, comments)" +
				" VALUES ( ?, ?, ?, ?, ?, ?, ? ) ";
		
		if(getRow(movie.getTitle()) != null){
			JOptionPane.showMessageDialog(null, "Movie title already exists in database","Private Movie Database", 1);
			return false;
		}
		
		
		try {
			PreparedStatement prepStatement = connection
					.prepareStatement(sqlInputString);
			prepStatement.setString(1, movie.getTitle());
			prepStatement.setString(2, movie.getDirector());
			prepStatement.setString(3, movie.getLead());
			prepStatement.setString(4, movie.getSecondary());
			prepStatement.setInt(5, movie.getRuntime());
			prepStatement.setString(6, movie.getImdburl());
			prepStatement.setString(7, movie.getComments());
			prepStatement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public ResultSet getField(String field){
		String sqlQuery = "SELECT "+field+" FROM `MovieArchive` ORDER BY "+field+";";
		ResultSet result;
		try{
			result = connection.prepareStatement(sqlQuery).executeQuery();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			return null;
		}
		
		return result;
	}
	public ResultSet getField(String field, String sort){
		String sqlQuery = "SELECT "+field+" FROM `MovieArchive` ORDER BY "+sort+";";
		ResultSet result;
		try{
			result = connection.prepareStatement(sqlQuery).executeQuery();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			return null;
		}
		
		return result;
	}
	
	public ResultSet getRow(String title){
		String sqlQuery = "SELECT * FROM `MovieArchive` WHERE title = ?;";
		ResultSet result = null;
		try{
			PreparedStatement prepStatement = connection.prepareStatement(sqlQuery);
			prepStatement.setString(1, title);
			result = prepStatement.executeQuery();
		} catch (Exception e){
			System.out.println(e);
			e.printStackTrace();
		}
		
		try {
			if(!result.next()){
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public MovieDBEntry getEntry(String title){
		ResultSet result = getRow(title);
		MovieDBEntry entry = new MovieDBEntry();
		
		try {
			entry.setTitle(result.getString("title"));
			entry.setDirector(result.getString("director"));
			entry.setLead(result.getString("lead"));
			entry.setSecondary(result.getString("secondary"));
			entry.setRuntime(result.getInt("runtime"));
			entry.setImdburl(result.getString("imdburl"));
			entry.setComments(result.getString("comments"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return entry;
	}
}
