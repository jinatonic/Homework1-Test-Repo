package edu.upenn.cis350.gpx;

import java.util.Date;

/**
 * Represents a track point in a GPX file.
 */

public class GPXtrkpt {

    // latitude
    private double _lat;
    // longitude
    private double _lon;
    // time
    private Date _time;

    public GPXtrkpt(double lat, double lon, Date time) {
	_lat = lat;
	_lon = lon;
	_time = time;
    }

    /* Accessors */
    public double lat() { return _lat; }
    public double lon() { return _lon; }
    public Date time() { return _time; }
}
