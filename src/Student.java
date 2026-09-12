public class Student {
    private final int id;
    private final String name;
    private final double[] grades;

    public Student(int id, String name, double[] grades) {
        this.id = id;
        this.name = name;
        this.grades = grades;
    }

    public double average() {
        double sum = 0;
        for (double grade : grades) sum += grade;
        return grades.length == 0 ? 0 : sum / grades.length;
    }

    public String status() {
        return average() >= 50 ? "PASS" : "FAIL";
    }

    @Override
    public String toString() {
        return id + " - " + name + " | avg=" + String.format("%.2f", average()) + " | " + status();
    }
}
