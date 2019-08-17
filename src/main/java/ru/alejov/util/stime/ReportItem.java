package ru.alejov.util.stime;

class ReportItem {

    private final TrackItem trackItem;
    private long timeDiff;

    ReportItem(TrackItem trackItem, long diff) {
        this.trackItem = trackItem;
        this.timeDiff = diff;
    }

    TrackItem getTrackItem() {
        return trackItem;
    }

    long getTimeDiff() {
        return timeDiff;
    }

    void increase(long diff) {
        timeDiff += diff;
    }

}
