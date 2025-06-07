/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses;

/**
 *
 * @author aarti
 */
import kmvcourses.dao.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDB 
{
    public static void main(String[] args) {
        DBConnection db = new DBConnection();
        Connection conn = db.getConnection();

        if (conn != null) {
            System.out.println(" Connection to Derby DB successful!");
            try {
                System.out.println("DB Metadata: " + conn.getMetaData().getDatabaseProductName());
            } catch (SQLException e) {
                System.err.println(" Error reading DB metadata: " + e.getMessage());
            }
        } else {
            System.out.println(" Failed to connect to Derby DB.");
        }
    }
}
