package io.github.rad03i2.sms;

public record Course(String code, String title, int credits) {
    public Course {
        code = Student.require(code, "course code"); title = Student.require(title, "course title");
        if (credits < 1 || credits > 30) throw new IllegalArgumentException("credits must be between 1 and 30");
    }
}
