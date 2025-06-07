/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.controller;

/**
 *
 * @author aarti
 */
import kmvcourses.model.Courses;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class courseFileManager 
{
    //  1. Create list of default courses
    public static List<Courses> createDefaultCourses() {
        List<Courses> courses = new ArrayList<>();

        // IT undergraduate
        courses.add(new Courses("ITC101", "Intro to Programming", "IT", 15, 5, "Core"));
        courses.add(new Courses("ITC102", "Technology in Society", "IT", 15, 5, "Core"));
        courses.add(new Courses("ITC103", "Teamwork in Tech", "IT", 15, 5, "Core"));
        courses.add(new Courses("ITC104", "Computational Mathematics", "IT", 15, 5, "Core"));
        courses.add(new Courses("ITC105", "IT Project Coordination", "IT", 15, 5, "Core"));
        courses.add(new Courses("ITC106", "Relational Database", "IT", 15, 5, "Core"));

        // IT Masters
        courses.add(new Courses("ITC801", "Advanced Software Engineering", "IT", 15, 8, "Core"));
        courses.add(new Courses("ITC802", "Cloud Computing Architecture", "IT", 15, 8, "Core"));
        courses.add(new Courses("ITC803", "CyberSecurity Management", "IT", 15, 8, "Core"));
        courses.add(new Courses("ITC804", "Data Mining and Machine Learning", "IT", 15, 8, "Core"));

        // IT PhD
        courses.add(new Courses("ITC901", "Doctoral Seminar in Computing", "IT", 15, 9, "Core"));
        courses.add(new Courses("ITC902", "Advanced in AI and Ethics", "IT", 15, 9, "Core"));

        // Engineering
        courses.add(new Courses("ENG101", "Intro to Engineering", "Engineering", 15, 5, "Core"));
        courses.add(new Courses("ENG102", "Engineering Mathematics I", "Engineering", 15, 5, "Core"));
        courses.add(new Courses("ENG103", "Introduction to Mechanics", "Engineering", 15, 5, "Core"));
        courses.add(new Courses("ENG104", "Engineering Materials", "Engineering", 15, 5, "Core"));
        courses.add(new Courses("ENG105", "Computer-Aided Design", "Engineering", 15, 5, "Core"));
        courses.add(new Courses("ENG106", "Physics for Engineering", "Engineering", 15, 5, "Core"));

        // Engineering Masters
        courses.add(new Courses("ENG801", "Advanced Materials Engineering", "Engineering", 15, 8, "Core"));
        courses.add(new Courses("ENG802", "Computational Fluid Dynamics", "Engineering", 15, 8, "Core"));
        courses.add(new Courses("ENG803", "Advanced Control System", "Engineering", 15, 8, "Core"));
        courses.add(new Courses("ENG804", "Engineering Project Management", "Engineering", 15, 8, "Core"));

        // Engineering PhD
        courses.add(new Courses("ENG901", "Doctoral Seminar in Engineering", "Engineering", 15, 9, "Core"));
        courses.add(new Courses("ENG902", "Emerging Engineering Systems", "Engineering", 15, 9, "Core"));

        return courses;
    }

    //  2. Write courses to text file
    public static void writeCoursesToFile(String filename, List<Courses> courses) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Courses course : courses) {
                writer.write(course.getCode() + "," + course.getName() + "," +
                        course.getMajor() + "," + course.getCreditPoints() + "," +
                        course.getLevel() + "," + course.getType());
                writer.newLine();
            }
            System.out.println("✅ Courses.txt written successfully.");
        } catch (IOException e) {
            System.err.println("⚠️ Error writing to file: " + e.getMessage());
        }
    }

    //  3. Main test method to trigger writing
    public static void main(String[] args) {
        List<Courses> defaultCourses = createDefaultCourses();
        writeCoursesToFile("Courses.txt", defaultCourses);
    }
}
