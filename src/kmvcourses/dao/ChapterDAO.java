/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.dao;

import java.util.List;
import kmvcourses.model.Chapter;
import kmvcourses.model.Lesson;



/**
 *
 * @author aarti
 */
public interface ChapterDAO 
{
    // Chapter operations
    boolean addChapter(Chapter chapter);
    boolean updateChapter(Chapter chapter);
    boolean deleteChapter(String chapterId);
    List<Chapter> getChaptersByCourse(String courseId);
    Chapter getChapterById(String chapterId);
    
    // Lesson operations
    boolean addLesson(Lesson lesson);
    boolean updateLesson(Lesson lesson);
    boolean deleteLesson(String lessonId);
    List<Lesson> getLessonsByChapter(String chapterId);
    Lesson getLessonById(String lessonId);
}
