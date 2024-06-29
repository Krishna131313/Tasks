package StudentCourse;


import java.util.*;

class Course {
    private String code;
    private String title;
    private String description;
    private int capacity;
    private int enrolled;
    private String schedule;

    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.enrolled = 0;
        this.schedule = schedule;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getEnrolled() {
        return enrolled;
    }

    public String getSchedule() {
        return schedule;
    }

    public boolean enroll() {
        if (enrolled < capacity) {
            enrolled++;
            return true;
        } else {
            return false;
        }
    }

    public boolean drop() {
        if (enrolled > 0) {
            enrolled--;
            return true;
        } else {
            return false;
        }
    }
}

class Student {
    private String studentId;
    private String name;
    private List<Course> registeredCourses;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public boolean registerCourse(Course course) {
        if (!registeredCourses.contains(course) && course.enroll()) {
            registeredCourses.add(course);
            return true;
        } else {
            return false;
        }
    }

    public boolean dropCourse(Course course) {
        if (registeredCourses.remove(course)) {
            course.drop();
            return true;
        } else {
            return false;
        }
    }
}

class CourseDatabase {
    private Map<String, Course> courses;

    public CourseDatabase() {
        this.courses = new HashMap<>();
    }

    public void addCourse(Course course) {
        courses.put(course.getCode(), course);
    }

    public Course getCourse(String code) {
        return courses.get(code);
    }

    public Collection<Course> getAllCourses() {
        return courses.values();
    }
}

class StudentDatabase {
    private Map<String, Student> students;

    public StudentDatabase() {
        this.students = new HashMap<>();
    }

    public void addStudent(Student student) {
        students.put(student.getStudentId(), student);
    }

    public Student getStudent(String studentId) {
        return students.get(studentId);
    }
}

public class CourseRegistrationSystem {
    private CourseDatabase courseDatabase;
    private StudentDatabase studentDatabase;
    private Scanner scanner;

    public CourseRegistrationSystem() {
        this.courseDatabase = new CourseDatabase();
        this.studentDatabase = new StudentDatabase();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        CourseRegistrationSystem system = new CourseRegistrationSystem();
        system.seedData();  // Seed initial data
        system.run();  // Start the system
    }

    public void seedData() {
        // Adding some courses
        courseDatabase.addCourse(new Course("CS101", "Introduction to Computer Science", "Basics of computer science", 30, "MWF 9-10AM"));
        courseDatabase.addCourse(new Course("MATH101", "Calculus I", "Differential and integral calculus", 25, "TTh 11-12:30PM"));
        courseDatabase.addCourse(new Course("PHYS101", "Physics I", "Introduction to mechanics", 20, "MWF 10-11AM"));

        // Adding some students
        studentDatabase.addStudent(new Student("S001", "Alice"));
        studentDatabase.addStudent(new Student("S002", "Bob"));
    }

    public void run() {
        while (true) {
            System.out.println("1. List Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. Drop a Course");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    listCourses();
                    break;
                case 2:
                    registerForCourse();
                    break;
                case 3:
                    dropCourse();
                    break;
                case 4:
                    System.out.println("Exiting the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void listCourses() {
        System.out.println("Available Courses:");
        for (Course course : courseDatabase.getAllCourses()) {
            System.out.println(course.getCode() + ": " + course.getTitle() + " (" + course.getEnrolled() + "/" + course.getCapacity() + ")");
            System.out.println("Description: " + course.getDescription());
            System.out.println("Schedule: " + course.getSchedule());
            System.out.println();
        }
    }

    private void registerForCourse() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.next();
        Student student = studentDatabase.getStudent(studentId);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter course code to register: ");
        String courseCode = scanner.next();
        Course course = courseDatabase.getCourse(courseCode);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        if (student.registerCourse(course)) {
            System.out.println("Successfully registered for the course.");
        } else {
            System.out.println("Failed to register for the course. It may be full or you are already registered.");
        }
    }

    private void dropCourse() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.next();
        Student student = studentDatabase.getStudent(studentId);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter course code to drop: ");
        String courseCode = scanner.next();
        Course course = courseDatabase.getCourse(courseCode);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        if (student.dropCourse(course)) {
            System.out.println("Successfully dropped the course.");
        } else {
            System.out.println("Failed to drop the course. You may not be registered for it.");
        }
    }
}

