package br.com.pedrojaber.application.report;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.SortedMap;
import java.util.TreeMap;

import br.com.pedrojaber.domain.Feedback;
import br.com.pedrojaber.domain.Urgency;
import br.com.pedrojaber.time.TimeZones;

public record WeeklyReport(
        LocalDate start,
        LocalDate end,
        List<Feedback> feedbacks,
        OptionalDouble averageGrade,
        SortedMap<LocalDate, Long> countByDay,
        Map<Urgency, Long> countByUrgency) {

    public static WeeklyReport from(LocalDate start, LocalDate end, List<Feedback> feedbacks) {
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End date must not be before start date");
        }

        List<Feedback> sorted = feedbacks.stream()
                .sorted(Comparator.comparing(Feedback::getSubmissionDate))
                .toList();

        OptionalDouble average = sorted.stream()
                .mapToInt(Feedback::getGrade)
                .average();

        SortedMap<LocalDate, Long> byDay = new TreeMap<>();
        start.datesUntil(end.plusDays(1)).forEach(day -> byDay.put(day, 0L));
        sorted.forEach(f -> byDay.merge(dayOf(f), 1L, Long::sum));

        Map<Urgency, Long> byUrgency = new EnumMap<>(Urgency.class);
        for (Urgency urgency : Urgency.values()) {
            byUrgency.put(urgency, 0L);
        }
        sorted.forEach(f -> byUrgency.merge(f.getUrgency(), 1L, Long::sum));

        return new WeeklyReport(
                start,
                end,
                sorted,
                average,
                Collections.unmodifiableSortedMap(byDay),
                Collections.unmodifiableMap(byUrgency));

    }

    public int total() {
        return feedbacks.size();
    }

    public boolean isEmpty() {
        return feedbacks.isEmpty();
    }

    private static LocalDate dayOf(Feedback feedback) {
        return LocalDate.ofInstant(feedback.getSubmissionDate(), TimeZones.BRAZIL);
    }

}
