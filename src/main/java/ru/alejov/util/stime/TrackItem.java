package ru.alejov.util.stime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author Ovcharov Alexey
 */
class TrackItem {

    private static final String SP = "\t`";

    private String trackId;
    private final Date date;

    TrackItem(String trackId, Date date) {
        this.trackId = trackId;
        this.date = date;
    }

    TrackItem(String csvRepresentation) throws ParseException {
        String[] split = csvRepresentation.split(Pattern.quote(SP));
        date = new SimpleDateFormat(Constants.TIME_FORMAT).parse(split[0]);
        trackId = split[1];
    }

    @Override
    public String toString() {
        String spaces = "  ";
        String ret = new SimpleDateFormat(Constants.TIME_FORMAT).format(date) + spaces + trackId;
        return ret;
    }

    public String asCsvString() {
        String ret = new SimpleDateFormat(Constants.TIME_FORMAT).format(date) + SP + trackId;
        ret += SP;
        return ret;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public Date getDate() {
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
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }    

}
