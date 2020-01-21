package ai.brace.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateService {
    public static LocalDateTime getTimestampFromEpoch(Long epoch) {
        return Instant.ofEpochMilli(epoch).atZone(ZoneOffset.UTC).toLocalDateTime();
    }

    public static LocalDateTime getUtcFromString(String timestamp) {
        return LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
