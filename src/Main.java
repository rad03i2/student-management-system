public class Main {
    public static void main(String[] args) {
        Student[] students = {
            new Student(1, "Radwan", new double[]{85, 77, 92}),
            new Student(2, "Mariam", new double[]{68, 74, 70}),
            new Student(3, "Ali", new double[]{41, 49, 55})
        };

        System.out.println("Student Management System");
        for (Student student : students) {
            System.out.println(student);
        }
    }
}
