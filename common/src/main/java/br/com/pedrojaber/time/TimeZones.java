package br.com.pedrojaber.time;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class TimeZones {

    public static final ZoneId BRAZIL = ZoneId.of("America/Sao_Paulo");

    public static final DateTimeFormatter BRAZIL_DATE_TIME = DateTimeFormatter
            .ofPattern("dd/MM/yyyy HH:mm")
            .withZone(BRAZIL);

    private TimeZones() {
    }
}
