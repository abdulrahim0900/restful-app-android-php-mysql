package edu.cs.birzeit.restapp.GSON;


public class Course {

    String CourseName;
    String CourseCategory;
    String NoOfLectures;

    public Course() {

    }
    public Course(String courseName, String courseCategory, String noOfLectures) {

        CourseName = courseName;
        CourseCategory = courseCategory;
        NoOfLectures = String.valueOf(noOfLectures);
    }
    public String getCourseName() {
        return CourseName;
    }
    public void setCourseName(String courseName) {
        CourseName = courseName;
    }
    public String getCourseCategory() {
        return CourseCategory;
    }
    public void setCourseCategory(String courseCategory) {
        CourseCategory = courseCategory;
    }
    public String getNoOfLectures() {
        return NoOfLectures;
    }
    public void setNoOfLectures(String noOfLectures) {
        NoOfLectures = noOfLectures;
    }
    @Override
    public String toString() {
        return "Course [CourseName=" + CourseName + ", CourseCategory=" + CourseCategory + ", NoOfLectures="
                + NoOfLectures + "]";
    }





}
