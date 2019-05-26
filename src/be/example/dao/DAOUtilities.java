package be.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOUtilities {
	
	private DAOUtilities() {}
	
	public static PreparedStatement initPrepQuery(Connection cnx, String sql,boolean returnGeneratedKeys, Object... objects) throws SQLException{
		
		PreparedStatement ps = cnx.prepareStatement(sql,returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
		
		for(int i=0; i< objects.length; i++) {
			ps.setObject(i+1, objects[i]);
		}
		
		return ps;
	}
	
	
	public static void closure(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			}catch (SQLException e) {
				System.err.println("Failure to close Resultset " + e.getMessage());
			}
		}
	}
	
	public static void closure(Statement statement) {
		if(statement != null) {
			try {
				statement.close();
			}catch(SQLException e) {
				System.err.println("Failure to close PreparedStatement " + e.getMessage());
			}
		}
	}
	
	public static void closure(Connection cnx) {
		if(cnx != null ) {
			try {
				cnx.close();
			}catch(SQLException e) {
				System.err.println("Failure to close Connection "+ e.getMessage());
			}
		}
	}
	
	public static void closures(Connection cnx, Statement statement, ResultSet rs) {
		closure(rs);
		closure(statement);
		closure(cnx);
	}
	
	public static void closures(Connection cnx, Statement statement) {
		closure(cnx);
		closure(statement);
	}
}
