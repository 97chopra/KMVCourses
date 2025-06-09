/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.dao;

/**
 *
 * @author aarti
 */
public class Chapter 
{
    private String chapterId;
    private String courseId;
    private String chapterTitle;
    private int chapterOrder;
    
    //Constructors
    public Chapter(){}
    
    public Chapter(String chapterId, String courseId, String chapterTitle, int chapterOrder)
    {
        this.chapterId = chapterId;
        this.courseId = courseId;
        this.chapterTitle = chapterTitle;
        this.chapterOrder = chapterOrder;
    }
    
    //Getting and setter (Encapsulation)
    public String getChapterId()
    {
        return chapterId;
    }
    
    public void setChapterId(String chapterId)
    {
        this.chapterId = chapterId;
    }
    
    public String getCourseId()
    {
        return courseId;
    }
    
    public void setCourseId(String courseId)
    {
        this.courseId = courseId;
    }
    
    public String getChapterTitle()
    {
        return chapterTitle;
    }
    
    public void setChapterTitle(String chapterTitle)
    {
        this.chapterTitle = chapterTitle;
    }
    
    public int getChapterOrder()
    {
        return chapterOrder;
    }
    
    public void setChapterOrder(int chapterOrder)
    {
        this.chapterOrder = chapterOrder;
    }
    
    @Override
    public String toString()
    {
        return "Chapter{" + 
                "chapterId=" + chapterId + '\''+
                ", courseId=" + courseId + '\'' +
                ", chapterTitle=" + chapterTitle + '\'' +
                ", chapterOrder=" + chapterOrder + '\'' +
                '}';
    }
}
