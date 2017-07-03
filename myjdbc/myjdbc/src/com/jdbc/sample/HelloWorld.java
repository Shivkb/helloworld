package com.jdbc.sample;

import java.sql.SQLException;

public class HelloWorld {

	public static void main(String[] args) {

		SqlDriver myDriver = new SqlDriver("root", "root", "mysql");
		
        // get a connection the db..
		try {
			myDriver.getConnection();
		} catch (SQLException e) {
			
		} finally {
			
		}
		
		// view the table..
        try {
        	myDriver.viewTable("testdb");
        } catch (SQLException e) {
        	
        } finally {
        	
        }
	}
}

