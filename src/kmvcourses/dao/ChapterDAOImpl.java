/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.dao;

import kmvcourses.model.Chapter;
import kmvcourses.model.Lesson;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author aarti
 */
public class ChapterDAOImpl implements ChapterDAO
{
    private static final Logger LOGGER = Logger.getLogger(ChapterDAOImpl.class.getName());
    private static final String DB_URL = "jdbc:derby:C:/Users/aarti/OneDrive/Desktop/CourseDB;create=true";
    
    // Composition: Use DBConnection for database operations
    private DBConnection dbConnection;
    
    public ChapterDAOImpl() {
        this.dbConnection = new DBConnection();
    }
    
    // method to generate chapterID
    private String generateShortRandomId() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder("CH");
        Random rnd = new Random();
        for (int i = 0; i < 6; i++) { // 2+6=8 chars total
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    // Chapter Operations
    @Override
    public boolean addChapter(Chapter chapter) {
        String sql = "INSERT INTO COURSE_CHAPTERS (chapter_id, course_id, chapter_title, chapter_order) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection("jdbc:derby:C:/Users/aarti/OneDrive/Desktop/CourseDB;create=true");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             String chapterId = generateShortRandomId();
             chapter.setChapterId(chapterId);
            
            
            stmt.setString(1, chapter.getChapterId());
            stmt.setString(2, chapter.getCourseId());
            stmt.setString(3, chapter.getChapterTitle());
            stmt.setInt(4, chapter.getChapterOrder());
            
            int result = stmt.executeUpdate();
            boolean success = result > 0;
            
            if (success) {
                LOGGER.info("Chapter added successfully: " + chapter.getChapterTitle());
            } else {
                LOGGER.warning("Failed to add chapter: " + chapter.getChapterTitle());
            }
            
            return success;
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding chapter: " + chapter.getChapterTitle(), e);
            return false;
        }
    }

    @Override
    public boolean updateChapter(Chapter chapter) {
        String sql = "UPDATE COURSE_CHAPTERS SET course_id=?, chapter_title=?, chapter_order=? WHERE chapter_id=?";
        
        try (Connection conn = DriverManager.getConnection("jdbc:derby:C:/Users/aarti/OneDrive/Desktop/CourseDB;create=true");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, chapter.getCourseId());
            stmt.setString(2, chapter.getChapterTitle());
            stmt.setInt(3, chapter.getChapterOrder());
            stmt.setString(4, chapter.getChapterId());
            
            int result = stmt.executeUpdate();
            boolean success = result > 0;
            
            if (success) {
                LOGGER.info("Chapter updated successfully: " + chapter.getChapterId());
            } else {
                LOGGER.warning("No chapter found to update with ID: " + chapter.getChapterId());
            }
            
            return success;
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating chapter: " + chapter.getChapterId(), e);
            return false;
        }
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        // Use transaction to ensure data integrity
        try (Connection conn = DriverManager.getConnection("jdbc:derby:C:/Users/aarti/OneDrive/Desktop/CourseDB;create=true")) {
            conn.setAutoCommit(false);
            
            try {
                // First delete all lessons in this chapter
                deleteLessonsByChapter(chapterId, conn);
                
                // Then delete the chapter
                String sql = "DELETE FROM COURSE_CHAPTERS WHERE chapter_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, chapterId);
                    int result = stmt.executeUpdate();
                    
                    conn.commit();
                    boolean success = result > 0;
                    
                    if (success) {
                        LOGGER.info("Chapter deleted successfully: " + chapterId);
                    } else {
                        LOGGER.warning("No chapter found to delete with ID: " + chapterId);
                    }
                    
                    return success;
                }
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting chapter: " + chapterId, e);
            return false;
        }
    }

    @Override
    public List<Chapter> getChaptersByCourse(String courseId) {
        String sql = "SELECT * FROM COURSE_CHAPTERS WHERE course_id = ? ORDER BY chapter_order";
        List<Chapter> chapters = new ArrayList<>();
        
        try (Connection conn = DriverManager.getConnection("jdbc:derby:C:/Users/aarti/OneDrive/Desktop/CourseDB;create=true");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, courseId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Chapter chapter = new Chapter(
                    rs.getString("chapter_id"),
                    rs.getString("course_id"),
                    rs.getString("chapter_title"),
                    rs.getInt("chapter_order")
                );
                chapters.add(chapter);
            }
            
            LOGGER.info("Retrieved " + chapters.size() + " chapters for course: " + courseId);
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching chapters for course: " + courseId, e);
        }
        
        return chapters;
    }

    @Override
    public Chapter getChapterById(String chapterId) {
        String sql = "SELECT * FROM COURSE_CHAPTERS WHERE chapter_id = ?";
        
        try (Connection conn = DriverManager.getConnection("jdbc:derby:C:/Users/aarti/OneDrive/Desktop/CourseDB;create=true");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, chapterId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Chapter(
                    rs.getString("chapter_id"),
                    rs.getString("course_id"),
                    rs.getString("chapter_title"),
                    rs.getInt("chapter_order")
                );
            }
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching chapter: " + chapterId, e);
        }
        
        return null;
    }

    // Lesson Operations
    @Override
    public boolean addLesson(Lesson lesson) {
        String sql = "INSERT INTO COURSE_LESSONS (lesson_id, chapter_id, lesson_title, lesson_content, lesson_order) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection("jdbc:derby:C:/Users/aarti/OneDrive/Desktop/CourseDB;create=true");

             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            if (lesson.getLessonId() == null || lesson.getLessonId().isEmpty()) {
                lesson.setLessonId(generateLessonId());
            }
            
            stmt.setString(1, lesson.getLessonId());
            stmt.setString(2, lesson.getChapterId());
            stmt.setString(3, lesson.getLessonTitle());
            stmt.setString(4, lesson.getLessonContent());
            stmt.setInt(5, lesson.getLessonOrder());
            
            int result = stmt.executeUpdate();
            boolean success = result > 0;
            
            if (success) {
                LOGGER.info("Lesson added successfully: " + lesson.getLessonTitle());
            }
            
            return success;
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding lesson: " + lesson.getLessonTitle(), e);
            return false;
        }
    }

    @Override
    public boolean updateLesson(Lesson lesson) {
        String sql = "UPDATE COURSE_LESSONS SET chapter_id=?, lesson_title=?, lesson_content=?, lesson_order=? WHERE lesson_id=?";
        
        try (Connection conn = DriverManager.getConnection("jdbc:derby:C:/Users/aarti/OneDrive/Desktop/CourseDB;create=true");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, lesson.getChapterId());
            stmt.setString(2, lesson.getLessonTitle());
            stmt.setString(3, lesson.getLessonContent());
            stmt.setInt(4, lesson.getLessonOrder());
            stmt.setString(5, lesson.getLessonId());
            
            int result = stmt.executeUpdate();
            boolean success = result > 0;
            
            if (success) {
                LOGGER.info("Lesson updated successfully: " + lesson.getLessonId());
            }
            
            return success;
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating lesson: " + lesson.getLessonId(), e);
            return false;
        }
    }

    @Override
    public boolean deleteLesson(String lessonId) {
        String sql = "DELETE FROM COURSE_LESSONS WHERE lesson_id = ?";
        
        try (Connection conn = DriverManager.getConnection("jdbc:derby:C:/Users/aarti/OneDrive/Desktop/CourseDB;create=true");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, lessonId);
            int result = stmt.executeUpdate();
            boolean success = result > 0;
            
            if (success) {
                LOGGER.info("Lesson deleted successfully: " + lessonId);
            }
            
            return success;
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting lesson: " + lessonId, e);
            return false;
        }
    }

    @Override
    public List<Lesson> getLessonsByChapter(String chapterId) {
        String sql = "SELECT * FROM COURSE_LESSONS WHERE chapter_id = ? ORDER BY lesson_order";
        List<Lesson> lessons = new ArrayList<>();
        
        try (Connection conn = DriverManager.getConnection("jdbc:derby:C:/Users/aarti/OneDrive/Desktop/CourseDB;create=true");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, chapterId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Lesson lesson = new Lesson(
                    rs.getString("lesson_id"),
                    rs.getString("chapter_id"),
                    rs.getString("lesson_title"),
                    rs.getString("lesson_content"),
                    rs.getInt("lesson_order")
                );
                lessons.add(lesson);
            }
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching lessons for chapter: " + chapterId, e);
        }
        
        return lessons;
    }

    @Override
    public Lesson getLessonById(String lessonId) {
        String sql = "SELECT * FROM COURSE_LESSONS WHERE lesson_id = ?";
        
        try (Connection conn = DriverManager.getConnection("jdbc:derby:C:/Users/aarti/OneDrive/Desktop/CourseDB;create=true");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, lessonId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Lesson(
                    rs.getString("lesson_id"),
                    rs.getString("chapter_id"),
                    rs.getString("lesson_title"),
                    rs.getString("lesson_content"),
                    rs.getInt("lesson_order")
                );
            }
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching lesson: " + lessonId, e);
        }
        
        return null;
    }

    // Private helper methods (Encapsulation)
    private String generateChapterId() {
        return "CH" + System.currentTimeMillis();
    }

    private String generateLessonId() {
        return "LS" + System.currentTimeMillis();
    }

    private void deleteLessonsByChapter(String chapterId, Connection conn) throws SQLException {
        String sql = "DELETE FROM COURSE_LESSONS WHERE chapter_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, chapterId);
            stmt.executeUpdate();
        }
    }
}
