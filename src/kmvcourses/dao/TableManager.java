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
import java.sql.PreparedStatement;

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
                        System.out.println("️ Duplicate student ID detected - skipping.");
                    } else {
                        throw e;
                    }
                }
            }
            System.out.println("" + insertedCount + " student records inserted successfully.");

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
        System.out.println(" " + insertedCount + " course records inserted successfully.");

    } catch (SQLException e) {
        System.err.println(" Database error: " + e.getMessage());
    }
    }
    public static void createChaptersTable() {
    try (Connection conn = DriverManager.getConnection(DB_URL)) {
        String sql = """
            CREATE TABLE COURSE_CHAPTERS (
                chapter_id VARCHAR(50) PRIMARY KEY,
                course_id VARCHAR(10),
                chapter_title VARCHAR(100),
                chapter_order INTEGER,
                FOREIGN KEY (course_id) REFERENCES COURSES(course_id)
            )
            """;
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
            System.out.println("COURSE_CHAPTERS table created successfully.");
        }
    } catch (SQLException e) {
        if (e.getSQLState().equals("X0Y32")) {
            System.out.println("COURSE_CHAPTERS table already exists.");
        } else {
            System.out.println("Error creating COURSE_CHAPTERS table: " + e.getMessage());
        }
    }
}

public static void createLessonsTable() {
    try (Connection conn = DriverManager.getConnection(DB_URL)) {
        String sql = """
            CREATE TABLE COURSE_LESSONS (
                lesson_id VARCHAR(10) PRIMARY KEY,
                chapter_id VARCHAR(10),
                lesson_title VARCHAR(100),
                lesson_content CLOB,
                lesson_order INTEGER,
                FOREIGN KEY (chapter_id) REFERENCES COURSE_CHAPTERS(chapter_id)
            )
            """;
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
            System.out.println("COURSE_LESSONS table created successfully.");
        }
    } catch (SQLException e) {
        if (e.getSQLState().equals("X0Y32")) {
            System.out.println("COURSE_LESSONS table already exists.");
        } else {
            System.out.println("Error creating COURSE_LESSONS table: " + e.getMessage());
        }
    }
}

public static void createLecturersTable() {
        String createSql = "CREATE TABLE LECTURERS (" +
            "lecturer_id VARCHAR(10) PRIMARY KEY, " +
            "name VARCHAR(50) NOT NULL, " +
            "email VARCHAR(100) UNIQUE)";

        String[] insertSqls = {
            "INSERT INTO LECTURERS VALUES ('KMV001', 'Dr. Sarah Johnson', 's.johnson@kmv.com')",
            "INSERT INTO LECTURERS VALUES ('KMV002', 'Prof. Michael Chen', 'm.chen@kmv.com')",
            "INSERT INTO LECTURERS VALUES ('KMV003', 'Dr. Emma Wilson', 'e.wilson@kmv.com')",
            "INSERT INTO LECTURERS VALUES ('KMV004', 'Prof. David Smith', 'd.smith@kmv.com')",
            "INSERT INTO LECTURERS VALUES ('KMV005', 'Dr. Lisa Kim', 'l.kim@kmv.com')"
        };

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            // Create LECTURERS table
            try {
                stmt.executeUpdate(createSql);
                System.out.println("LECTURERS table created.");
            } catch (SQLException e) {
                if (e.getSQLState().equals("X0Y32")) {
                    System.out.println("LECTURERS table already exists.");
                } else {
                    throw e;
                }
            }

            // Insert sample lecturers
            int insertedCount = 0;
            for (String insertSql : insertSqls) {
                try {
                    stmt.executeUpdate(insertSql);
                    insertedCount++;
                } catch (SQLException e) {
                    if (e.getSQLState().equals("23505")) {
                        System.out.println("⚠️ Duplicate lecturer ID detected - skipping.");
                    } else {
                        throw e;
                    }
                }
            }
            System.out.println("" + insertedCount + " lecturer records inserted successfully.");

        } catch (SQLException e) {
            System.err.println(" Database error: " + e.getMessage());
        }
    }

    public static void createCourseLecturersTable() {
        String createSql = "CREATE TABLE COURSE_LECTURERS (" +
            "course_id VARCHAR(10), " +
            "lecturer_id VARCHAR(10), " +
            "PRIMARY KEY (course_id, lecturer_id), " +
            "FOREIGN KEY (course_id) REFERENCES COURSES(course_id), " +
            "FOREIGN KEY (lecturer_id) REFERENCES LECTURERS(lecturer_id))";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            // Create COURSE_LECTURERS table
            try {
                stmt.executeUpdate(createSql);
                System.out.println("COURSE_LECTURERS table created.");
            } catch (SQLException e) {
                if (e.getSQLState().equals("X0Y32")) {
                    System.out.println("COURSE_LECTURERS table already exists.");
                } else {
                    throw e;
                }
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
    
    public static void createAttendenceTable()
    {
        String createSql = "CREATE TABLE ATTENDANCE (" +
        "attendance_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
        "student_id VARCHAR(20), " +
        "course_id VARCHAR(20), " +
        "date DATE, " +
        "status VARCHAR(10))";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
         Statement stmt = conn.createStatement()) {
        try {
            stmt.executeUpdate(createSql);
            System.out.println("ATTENDANCE table created.");
        } catch (SQLException e) {
            if (e.getSQLState().equals("X0Y32")) {
                System.out.println("ATTENDANCE table already exists.");
            } else {
                throw e;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
        
    }
    
    public static void createGradesTable() {
    String sql = "CREATE TABLE GRADES (" +
                 "grade_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                 "student_id VARCHAR(10), " +
                 "course_id VARCHAR(10), " +
                 "grade DOUBLE, " +
                 "CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES STUDENTS(student_id), " +
                 "CONSTRAINT fk_course FOREIGN KEY (course_id) REFERENCES COURSES(course_id), " +
                 "UNIQUE (student_id, course_id)" +
                 ")";
    try (Connection conn = DriverManager.getConnection(DB_URL);
         Statement stmt = conn.createStatement()) {
        stmt.executeUpdate(sql);
        System.out.println("GRADES table created.");
    } catch (SQLException e) {
        if ("X0Y32".equals(e.getSQLState())) {
            System.out.println("GRADES table already exists.");
        } else {
            e.printStackTrace();
        }
    }
}

public static void addLecturerPasswordColumn() {
    String sql = "ALTER TABLE LECTURERS ADD COLUMN PASSWORD VARCHAR(50)";
    try (Connection conn = DriverManager.getConnection(DB_URL);
         Statement stmt = conn.createStatement()) {
        stmt.executeUpdate(sql);
        System.out.println("Password column added to LECTURERS table.");
    } catch (SQLException e) {
        if ("X0Y32".equals(e.getSQLState())) {
            System.out.println("Password column already exists in LECTURERS table.");
        } else {
            e.printStackTrace();
        }
    }
}

public static void setLecturerPasswords() {
    try (Connection conn = DriverManager.getConnection(DB_URL);
         Statement stmt = conn.createStatement()) {
        stmt.executeUpdate("UPDATE LECTURERS SET PASSWORD = 'pass1' WHERE lecturer_id = 'KMV001'");
        stmt.executeUpdate("UPDATE LECTURERS SET PASSWORD = 'pass2' WHERE lecturer_id = 'KMV002'");
        stmt.executeUpdate("UPDATE LECTURERS SET PASSWORD = 'pass3' WHERE lecturer_id = 'KMV003'");
        stmt.executeUpdate("UPDATE LECTURERS SET PASSWORD = 'pass4' WHERE lecturer_id = 'KMV004'");
        stmt.executeUpdate("UPDATE LECTURERS SET PASSWORD = 'pass5' WHERE lecturer_id = 'KMV005'");
        System.out.println("Lecturer passwords set.");
    } catch (SQLException e) {
        e.printStackTrace();
    }
  }
   public static void createEnrollmentsTable() {
    String sql = "CREATE TABLE ENROLLMENTS (" +
                 "enrollment_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                 "student_id VARCHAR(10) NOT NULL, " +
                 "course_id VARCHAR(10) NOT NULL, " +
                 "FOREIGN KEY (student_id) REFERENCES STUDENTS(student_id), " +
                 "FOREIGN KEY (course_id) REFERENCES COURSES(course_id)" +
                 ")";
    try (Connection conn = DriverManager.getConnection(DB_URL);
         Statement stmt = conn.createStatement()) {
        stmt.executeUpdate(sql);
        System.out.println("ENROLLMENTS table created successfully.");
    } catch (SQLException e) {
        if ("X0Y32".equals(e.getSQLState())) {
            System.out.println("ENROLLMENTS table already exists.");
        } else {
            e.printStackTrace();
        }
    }
}
}

   


