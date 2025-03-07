package ru.alejov.util.stime;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author Ovcharov Alexey
 */
class TrackItem {

    private static final String SP = "\t`";

    private String trackId;
    private final LocalDateTime date;

    TrackItem(String trackId, LocalDateTime date) {
        this.trackId = trackId;
        this.date = date;
    }

    TrackItem(LocalDate currentDay, String csvRepresentation) throws ParseException {
        String[] split = csvRepresentation.split(Pattern.quote(SP));
        LocalTime localTime = LocalTime.parse(split[0], Constants.TIME_FORMATTER);
        date = LocalDateTime.of(currentDay, localTime);
        trackId = split[1];
    }

    @Override
    public String toString() {
        String spaces = "  ";
        return date.format(Constants.TIME_FORMATTER) + spaces + trackId;
    }

    public String asCsvString() {
        String ret = date.format(Constants.TIME_FORMATTER) + SP + trackId;
        ret += SP;
        return ret;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public LocalDateTime getDate() {
        return date;
    }


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.trackId);
        hash = 19 * hash + Objects.hashCode(this.date);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TrackItem other = (TrackItem) obj;
        if (!Objects.equals(this.trackId, other.trackId)) {
            return false;
        }
        return Objects.equals(this.date, other.date);
    }    

}
