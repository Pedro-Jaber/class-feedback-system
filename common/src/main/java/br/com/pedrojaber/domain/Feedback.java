package br.com.pedrojaber.domain;

import java.time.Instant;
import java.util.UUID;

public class Feedback {

    private String id;
    private String description;
    private int grade;
    private Urgency urgency;
    private Instant submissionDate;

    private Feedback() {
    }

    private Feedback(String id, String description, int grade, Urgency urgency, Instant submissionDate) {

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }

        if (grade < 0 || grade > 10) {
            throw new IllegalArgumentException("Grade must be between 0 and 10");
        }

        this.id = id;
        this.description = description;
        this.grade = grade;
        this.urgency = urgency;
        this.submissionDate = submissionDate;
    }

    public static Feedback create(String description, int grade) {
        return new Feedback(
                UUID.randomUUID().toString(),
                description,
                grade,
                Urgency.fromGrade(grade),
                Instant.now());
    }

    public static Feedback restore(String id, String description, int grade, Urgency urgency, Instant submissionDate) {
        return new Feedback(id, description, grade, urgency, submissionDate);
    }

    public boolean requireNotification() {
        return urgency == Urgency.HIGH;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getGrade() {
        return grade;
    }

    public Urgency getUrgency() {
        return urgency;
    }

    public Instant getSubmissionDate() {
        return submissionDate;
    }

}
