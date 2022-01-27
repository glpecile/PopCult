package ar.edu.itba.paw.webapp.dto.input;

import java.time.LocalDateTime;

/**
 * {
 *     "dateTime": "2019-12-03T10:15:30"
 * }
 */
public class DateTimeDto {

    private LocalDateTime dateTime;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
