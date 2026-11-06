package br.com.pedrojaber.domain;

public enum Urgency {
    LOW, // 7-10
    MEDIUM, // 4-6
    HIGH; // 0-3

    public static Urgency fromGrade(int grade) {
        if (grade >= 0 && grade <= 3)
            return Urgency.HIGH;
        if (grade > 3 && grade <= 6)
            return Urgency.MEDIUM;
        return Urgency.LOW;
    }
}
