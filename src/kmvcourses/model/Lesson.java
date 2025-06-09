/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.model;

/**
 *
 * @author aarti
 */
public class Lesson 
{
    private String lessonId;
    private String chapterId;
    private String lessonTitle;
    private String lessonContent;
    private int lessonOrder;

    // Constructors
    public Lesson() {}

    public Lesson(String lessonId, String chapterId, String lessonTitle, 
                  String lessonContent, int lessonOrder) {
        this.lessonId = lessonId;
        this.chapterId = chapterId;
        this.lessonTitle = lessonTitle;
        this.lessonContent = lessonContent;
        this.lessonOrder = lessonOrder;
    }

    // Getters and Setters (Encapsulation)
    public String getLessonId() { return lessonId; }
    public void setLessonId(String lessonId) { this.lessonId = lessonId; }

    public String getChapterId() { return chapterId; }
    public void setChapterId(String chapterId) { this.chapterId = chapterId; }

    public String getLessonTitle() { return lessonTitle; }
    public void setLessonTitle(String lessonTitle) { this.lessonTitle = lessonTitle; }

    public String getLessonContent() { return lessonContent; }
    public void setLessonContent(String lessonContent) { this.lessonContent = lessonContent; }

    public int getLessonOrder() { return lessonOrder; }
    public void setLessonOrder(int lessonOrder) { this.lessonOrder = lessonOrder; }

    @Override
    public String toString() {
        return "Lesson{" +
                "lessonId='" + lessonId + '\'' +
                ", chapterId='" + chapterId + '\'' +
                ", lessonTitle='" + lessonTitle + '\'' +
                ", lessonContent='" + lessonContent + '\'' +
                ", lessonOrder=" + lessonOrder +
                '}';
    }
}
