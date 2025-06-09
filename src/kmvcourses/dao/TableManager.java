/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.dao;

/**
 *
 * @author aarti
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class TableManager 
{
    // Embedded Derby DB path (matches your AdminLogin and DBConnection setup)
    private static final String DB_URL = "jdbc:derby:C:/Users/aarti/OneDrive/Desktop/CourseDB;create=true";

    /**
     * Creates the ADMINS table with basic fields: admin_id, name, and password.
     * Also inserts 2 sample admin records for login testing.
     */
    public static void createAdminsNewTable() 
    {
       
    String createSql = "CREATE TABLE ADMINS (" +
        "admin_id VARCHAR(10) PRIMARY KEY, " +
        "first_name VARCHAR(50), " +
        "last_name VARCHAR(50), " +
        "password VARCHAR(20))";
    
    String[] insertSqls = {
        "INSERT INTO ADMINS VALUES ('A101', 'Ravi', 'Kumar', 'admin01rk')",
        "INSERT INTO ADMINS VALUES ('A102', 'Meera', 'Sharma', 'admin02ms')",
        "INSERT INTO ADMINS VALUES ('A103', 'David', 'Lee', 'admin03dl')"
    };
    
   

    try (Connection conn = DriverManager.getConnection(DB_URL);
         Statement stmt = conn.createStatement()) {

        // Create table
        try {
            stmt.executeUpdate(createSql);
            System.out.println("Admin table created.");
        } catch (SQLException e) {
            if (e.getSQLState().equals("X0Y32")) {
                System.out.println("Admin table already exists.");
            } else {
                throw e;
            }
        }

        // Execute INSERT statements
        for (String insertSql : insertSqls) {
            try {
                stmt.executeUpdate(insertSql);
            } catch (SQLException e) {
                if (e.getSQLState().equals("23505, xyz")) {
                    System.out.println("️ Duplicate admin ID detected - skipping.");
                } else {
                    throw e;
                }
            }
        }
        System.out.println(" Admin records inserted successfully.");

    } catch (SQLException e) {
        System.err.println(" Database error: " + e.getMessage());
    }
    
    // Shutdown Derby cleanly
    try {
        DriverManager.getConnection("jdbc:derby:;shutdown=true");
    } catch (SQLException e) {
        if ("XJ015".equals(e.getSQLState())) {
            System.out.println("Derby shut down cleanly.");
        }
    } 
    }
    
    /**
     * Creates the STUDENTS table and inserts student records.
     */
    public static void createStudentsTable() {
        String createSql = "CREATE TABLE STUDENTS (" +
            "student_id VARCHAR(10) PRIMARY KEY, " +
            "first_name VARCHAR(50), " +
            "last_name VARCHAR(50), " +
            "password VARCHAR(20))";
        
        String[] insertSqls = {
            "INSERT INTO STUDENTS VALUES ('S101', 'aarti', 'sharma', 'kmv01as')",
            "INSERT INTO STUDENTS VALUES ('S102', 'john', 'kumar', 'kmv02jk')",
            "INSERT INTO STUDENTS VALUES ('S103', 'maya', 'sharma', 'kmv03ms')",
            "INSERT INTO STUDENTS VALUES ('S104', 'ravi', 'verma', 'kmv04rv')",
            "INSERT INTO STUDENTS VALUES ('S105', 'Kavita', 'sharma', 'kmv05ks')",
            "INSERT INTO STUDENTS VALUES ('S106', 'priya', 'singh', 'kmv06ps')",
            "INSERT INTO STUDENTS VALUES ('S107', 'arjun', 'mehta', 'kmv07am')",
            "INSERT INTO STUDENTS VALUES ('S108', 'neha', 'jain', 'kmv08nj')",
            "INSERT INTO STUDENTS VALUES ('S109', 'rahul', 'patel', 'kmv09rp')",
            "INSERT INTO STUDENTS VALUES ('S110', 'anita', 'kapoor', 'kmv10ak')",
            "INSERT INTO STUDENTS VALUES ('S111', 'oliver', 'smith', 'kmv11os')",
            "INSERT INTO STUDENTS VALUES ('S112', 'emma', 'brown', 'kmv12eb')",
            "INSERT INTO STUDENTS VALUES ('S113', 'liam', 'taylor', 'kmv13lt')",
            "INSERT INTO STUDENTS VALUES ('S114', 'sophia', 'johnson', 'kmv14sj')",
            "INSERT INTO STUDENTS VALUES ('S115', 'noah', 'jackson', 'kmv15nj')",
            "INSERT INTO STUDENTS VALUES ('S116', 'ava', 'white', 'kmv16aw')",
            "INSERT INTO STUDENTS VALUES ('S117', 'elijah', 'thomas', 'kmv17et')",
            "INSERT INTO STUDENTS VALUES ('S118', 'charlotte', 'lee', 'kmv18cl')",
            "INSERT INTO STUDENTS VALUES ('S119', 'james', 'hall', 'kmv19jh')",
            "INSERT INTO STUDENTS VALUES ('S120', 'amelia', 'king', 'kmv20ak')",
            "INSERT INTO STUDENTS VALUES ('S121', 'meena', 'eid', 'kmv21me')",
            "INSERT INTO STUDENTS VALUES ('S122', 'anna', 'asif', 'kmv21aa')",
            "INSERT INTO STUDENTS VALUES ('S123', 'nhi', 'hu', 'kmv22nh')",
            "INSERT INTO STUDENTS VALUES ('S124', 'xinhu', 'lee', 'kmv23xl')",
            "INSERT INTO STUDENTS VALUES ('S125', 'ali', 'mehmood', 'kmv24am')",
            "INSERT INTO STUDENTS VALUES ('S126', 'hania', 'amir', 'kmv25ha')"
        };

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            // Create table
            try {
                stmt.executeUpdate(createSql);
                System.out.println("STUDENTS table created.");
            } catch (SQLException e) {
                if (e.getSQLState().equals("X0Y32")) {
                    System.out.println("STUDENTS table already exists.");
                } else {
                    throw e;
                }
            }

            // Execute INSERT statements
            int insertedCount = 0;
            for (String insertSql : insertSqls) {
                try {
                    stmt.executeUpdate(insertSql);
                    insertedCount++;
                } catch (SQLException e) {
                    if (e.getSQLState().equals("23505")) {
                        System.out.println("⚠️ Duplicate student ID detected - skipping.");
                    } else {
                        throw e;
                    }
                }
            }
            System.out.println("✅ " + insertedCount + " student records inserted successfully.");

        } catch (SQLException e) {
            System.err.println("❌ Database error: " + e.getMessage());
        }
        
        // Shutdown Derby cleanly
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException e) {
            if ("XJ015".equals(e.getSQLState())) {
                System.out.println("Derby shut down cleanly.");
            }
        }
    }
    
    public static void createCoursesTable() {
    String createSql = "CREATE TABLE COURSES (" +
        "course_id VARCHAR(10) PRIMARY KEY, " +
        "course_name VARCHAR(100), " +
        "department VARCHAR(50), " +
        "credits INT, " +
        "level INT, " +
        "type VARCHAR(20))";

    String[] insertSqls = {
        "INSERT INTO COURSES VALUES ('ITC101', 'Intro to Programming', 'IT', 15, 5, 'Core')",
        "INSERT INTO COURSES VALUES ('ITC102', 'Technology in Society', 'IT', 15, 5, 'Core')",
        "INSERT INTO COURSES VALUES ('ITC103', 'Teamwork in Tech', 'IT', 15, 5, 'Core')",
        "INSERT INTO COURSES VALUES ('ITC104', 'Computational Mathematics', 'IT', 15, 5, 'Core')",
        "INSERT INTO COURSES VALUES ('ITC105', 'IT Project Coordination', 'IT', 15, 5, 'Core')",
        "INSERT INTO COURSES VALUES ('ITC106', 'Relational Database', 'IT', 15, 5, 'Core')",
        "INSERT INTO COURSES VALUES ('ITC801', 'Advanced Software Engineering', 'IT', 15, 8, 'Core')",
        "INSERT INTO COURSES VALUES ('ITC802', 'Cloud Computing Architecture', 'IT', 15, 8, 'Core')",
        "INSERT INTO COURSES VALUES ('ITC803', 'CyberSecurity Management', 'IT', 15, 8, 'Core')",
        "INSERT INTO COURSES VALUES ('ITC804', 'Data Mining and Machine Learning', 'IT', 15, 8, 'Core')",
        "INSERT INTO COURSES VALUES ('ITC901', 'Doctoral Seminar in Computing', 'IT', 15, 9, 'Core')",
        "INSERT INTO COURSES VALUES ('ITC902', 'Advanced in AI and Ethics', 'IT', 15, 9, 'Core')",
        "INSERT INTO COURSES VALUES ('ENG101', 'Intro to Engineering', 'Engineering', 15, 5, 'Core')",
        "INSERT INTO COURSES VALUES ('ENG102', 'Engineering Mathematics I', 'Engineering', 15, 5, 'Core')",
        "INSERT INTO COURSES VALUES ('ENG103', 'Introduction to Mechanics', 'Engineering', 15, 5, 'Core')",
        "INSERT INTO COURSES VALUES ('ENG104', 'Engineering Materials', 'Engineering', 15, 5, 'Core')",
        "INSERT INTO COURSES VALUES ('ENG105', 'Computer-Aided Design', 'Engineering', 15, 5, 'Core')",
        "INSERT INTO COURSES VALUES ('ENG106', 'Physics for Engineering', 'Engineering', 15, 5, 'Core')",
        "INSERT INTO COURSES VALUES ('ENG801', 'Advanced Materials Engineering', 'Engineering', 15, 8, 'Core')",
        "INSERT INTO COURSES VALUES ('ENG802', 'Computational Fluid Dynamics', 'Engineering', 15, 8, 'Core')",
        "INSERT INTO COURSES VALUES ('ENG803', 'Advanced Control System', 'Engineering', 15, 8, 'Core')",
        "INSERT INTO COURSES VALUES ('ENG804', 'Engineering Project Management', 'Engineering', 15, 8, 'Core')",
        "INSERT INTO COURSES VALUES ('ENG901', 'Doctoral Seminar in Engineering', 'Engineering', 15, 9, 'Core')",
        "INSERT INTO COURSES VALUES ('ENG902', 'Emerging Engineering Systems', 'Engineering', 15, 9, 'Core')"
    };

    try (Connection conn = DriverManager.getConnection(DB_URL);
         Statement stmt = conn.createStatement()) {

        // Create table
        try {
            stmt.executeUpdate(createSql);
            System.out.println("COURSES table created.");
        } catch (SQLException e) {
            if (e.getSQLState().equals("X0Y32")) {
                System.out.println("COURSES table already exists.");
            } else {
                throw e;
            }
        }

        // Insert data
        int insertedCount = 0;
        for (String insertSql : insertSqls) {
            try {
                stmt.executeUpdate(insertSql);
                insertedCount++;
            } catch (SQLException e) {
                if (e.getSQLState().equals("23505")) {
                    System.out.println("⚠️ Duplicate course ID detected - skipping.");
                } else {
                    throw e;
                }
            }
        }
        System.out.println("✅ " + insertedCount + " course records inserted successfully.");

    } catch (SQLException e) {
        System.err.println("❌ Database error: " + e.getMessage());
    }
}

}




