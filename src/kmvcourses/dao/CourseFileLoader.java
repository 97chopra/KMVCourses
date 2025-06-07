/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmvcourses.dao;

/**
 *
 * @author aarti
 */
import kmvcourses.model.Courses;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CourseFileLoader 
{
    public static void loadCoursesFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 6) {
                    String code = parts[0];
                    String name = parts[1];
                    String major = parts[2];
                    int creditPoints = Integer.parseInt(parts[3]);
                    int level = Integer.parseInt(parts[4]);
                    String type = parts[5];

                    Courses course = new Courses(code, name, major, creditPoints, level, type);
                    kmvcourses.dao.CoursesDAO.insertCourse(course);  // Save to DB
                } else {
                    System.out.println("⚠️ Skipping invalid line: " + line);
                }
            }

            System.out.println("✅ All valid courses inserted.");
        } catch (IOException e) {
            System.err.println("⚠️ Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("⚠️ Invalid number format in file: " + e.getMessage());
        }
    }
}
