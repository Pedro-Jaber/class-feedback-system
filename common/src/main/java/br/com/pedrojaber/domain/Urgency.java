package br.com.pedrojaber.domain;

public enum Urgency {
    LOW("BAIXA"), // 7-10
    MEDIUM("MÉDIA"), // 4-6
    HIGH("ALTA"); // 0-3

    private final String label;

    Urgency(String label) {
        this.label = label;
    }

    public static Urgency fromGrade(int grade) {
        if (grade >= 0 && grade <= 3)
            return Urgency.HIGH;
        if (grade > 3 && grade <= 6)
            return Urgency.MEDIUM;
        return Urgency.LOW;
    }

    public String getLabel() {
        return label;
    }
}
