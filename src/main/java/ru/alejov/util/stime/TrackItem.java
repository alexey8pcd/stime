package ru.alejov.util.stime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

class TrackItem {

    private static final String SP = "\t`";

    private String trackId;
    private final Date date;
    private String description;

    TrackItem(String trackId, Date date) {
        this.trackId = trackId;
        this.date = date;
    }

    TrackItem(String csvRepresentation) throws ParseException {
        String[] split = csvRepresentation.split(Pattern.quote(SP));
        date = new SimpleDateFormat(Constants.TIME_FORMAT).parse(split[0]);
        trackId = split[1];
        if (split.length > 2) {
            description = split[2];
        }
    }

    @Override
    public String toString() {
        String spaces = "  ";
        String ret = new SimpleDateFormat(Constants.TIME_FORMAT).format(date) + spaces + trackId;
        if (description != null && !description.isEmpty()) {
            ret += spaces + description;
        }
        return ret;
    }

    void setDescription(String desc) {
        this.description = desc;
    }

    String asCsvString() {

        String ret = new SimpleDateFormat(Constants.TIME_FORMAT).format(date) + SP + trackId;
        ret += SP;
        if (description != null && !description.isEmpty()) {
            ret += description;
        }
        return ret;
    }

    String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    Date getDate() {
        return date;
    }

    String getDescription() {
        return description;
    }

}
