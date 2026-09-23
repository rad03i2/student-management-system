package io.github.rad03i2.sms;

public record Enrollment(String studentId, String courseCode, Double grade) {
    public Enrollment {
        studentId = Student.require(studentId, "student id"); courseCode = Student.require(courseCode, "course code");
        if (grade != null && (grade < 0 || grade > 100 || !Double.isFinite(grade))) throw new IllegalArgumentException("grade must be between 0 and 100");
    }
}
