/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.db;

/**
 *
 * @author Anthony
 */

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;


public class UsersRepository {
	
        static Connection conn;
        static Statement state;
        static ResultSet result;
        static ResultSetMetaData resultMeta;
        static Object[][] donn;
        static String[] champs;
        static Object[] val;
        static String tableBDD = "user_chet";
       
	
	private UsersRepository() {}

	private static UsersRepository INSTANCE = null;

	public static UsersRepository getInstance() {
		if (INSTANCE == null) {
			synchronized (UsersRepository.class) {
				if (INSTANCE == null) {
					INSTANCE = new UsersRepository();
				}
			}
		}
		return INSTANCE;
	}
	
	public void init() {
            
		try {
			Class.forName("oracle.jdbc.OracleDriver");
                        String url = "jdbc:oracle:thin:@iutdoua-oracle.univ-lyon1.fr:1521:orcl";
                        String user = "p1614217";
                        String passwd = "275939";
                             conn = DriverManager.getConnection(url,user, passwd);
                            System.out.println("Connecter");
                            // Déclaration de la connexion avec la base de donnée
                     state = conn.createStatement();
               
                DatabaseMetaData dbm = conn.getMetaData();
                result = dbm.getTables(null, null, "USER_CHAT", null);
                
                if (!result.next()) {
				state.executeQuery("CREATE TABLE USER_CHAT ( id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY, pseudo VARCHAR2(50) NOT NULL, mdp VARCHAR2(255), CONSTRAINT pk_user_chat PRIMARY KEY (id));");
                } 
               /* while(result.next()){
                    for(int i = 1; i <= resultMeta.getColumnCount(); i++){
                        System.out.print("      " + result.getObject(i).toString() + "      ");
                        System.out.println("\n-------------------------------------------------------------");}
                }*/
                            
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean login (String pseudo, String mdp) {
		ResultSet result;
		try {
			String query = "SELECT * FROM user_chat where pseudo = '"+pseudo+"' and mdp = '"+mdp+"'";
			result = state.executeQuery(query);
			
			int cpt = 0;
			while (result.next())
				cpt++;
			if (cpt == 1){
                            return true;
                        }
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean newLogin (String pseudo, String mdp) {
		String query = "INSERT INTO user_chat(pseudo, mdp) VALUES ('"+pseudo+"','"+mdp+"')";
		try {
			state.executeQuery(query);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public void close() {
		try {
			conn.close();
			state.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}