package io.github.rad03i2.sms;

public record Student(String id, String name, String email) {
    public Student {
        id = require(id, "student id"); name = require(name, "student name"); email = require(email, "email");
        if (!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) throw new IllegalArgumentException("Invalid email: " + email);
    }
    static String require(String value, String label) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(label + " must not be blank");
        if (value.indexOf('\n') >= 0 || value.indexOf('\r') >= 0) throw new IllegalArgumentException(label + " must be one line");
        return value.trim();
    }
}
