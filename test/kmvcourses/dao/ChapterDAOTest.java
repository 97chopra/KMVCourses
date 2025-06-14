/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package kmvcourses.dao;


import java.util.List;
import kmvcourses.model.Chapter;
import kmvcourses.model.Lesson;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

/**
 *
 * @author aarti
 */
public class ChapterDAOTest {
    
    private ChapterDAO dao;
    private List<String> testChapterIds;

    @Before
    public void setUp() {
        dao = new ChapterDAOImpl();
        testChapterIds = new ArrayList<>();
    }

    @After
    public void tearDown() {
        // Clean up test chapters
        for (String chapterId : testChapterIds) {
            dao.deleteChapter(chapterId);
        }
    }

    @Test
    public void testAddChapter() {
        Chapter chapter = new Chapter(null, "ITC101", "JUnit Test Chapter", 999);
        boolean result = dao.addChapter(chapter);
        assertTrue("Chapter should be added successfully", result);
        assertNotNull("Chapter ID should be generated", chapter.getChapterId());
        testChapterIds.add(chapter.getChapterId());
    }

    @Test
    public void testUpdateChapter() {
        Chapter chapter = new Chapter(null, "ITC101", "Update Test Chapter", 998);
        boolean added = dao.addChapter(chapter);
        assertTrue("Chapter should be added first", added);
        testChapterIds.add(chapter.getChapterId());

        chapter.setChapterTitle("Updated Title");
        boolean updated = dao.updateChapter(chapter);
        assertTrue("Chapter should be updated successfully", updated);

        Chapter retrieved = dao.getChapterById(chapter.getChapterId());
        assertEquals("Updated Title", retrieved.getChapterTitle());
    }

    @Test
    public void testDeleteChapter() {
        Chapter chapter = new Chapter(null, "ITC101", "Delete Test Chapter", 997);
        boolean added = dao.addChapter(chapter);
        assertTrue("Chapter should be added first", added);
        String chapterId = chapter.getChapterId();

        boolean deleted = dao.deleteChapter(chapterId);
        assertTrue("Chapter should be deleted successfully", deleted);

        Chapter retrieved = dao.getChapterById(chapterId);
        assertNull("Chapter should be deleted", retrieved);
    }

    @Test
    public void testGetChaptersByCourse() {
        Chapter chapter1 = new Chapter(null, "ITC101", "Test Chapter 1", 1);
        Chapter chapter2 = new Chapter(null, "ITC101", "Test Chapter 2", 2);
        dao.addChapter(chapter1);
        dao.addChapter(chapter2);
        testChapterIds.add(chapter1.getChapterId());
        testChapterIds.add(chapter2.getChapterId());

        List<Chapter> chapters = dao.getChaptersByCourse("ITC101");
        assertNotNull("Chapters list should not be null", chapters);
        assertTrue("Should have at least 2 chapters", chapters.size() >= 2);
    }

    @Test
    public void testGetChapterById() {
        Chapter chapter = new Chapter(null, "ITC101", "Find Me Chapter", 996);
        boolean added = dao.addChapter(chapter);
        assertTrue("Chapter should be added", added);
        testChapterIds.add(chapter.getChapterId());

        Chapter found = dao.getChapterById(chapter.getChapterId());
        assertNotNull("Should find the chapter by ID", found);
        assertEquals("Find Me Chapter", found.getChapterTitle());
        assertEquals("ITC101", found.getCourseId());
    }
}

