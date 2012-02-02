package edu.upenn.cis350.gpx;

import java.util.ArrayList;

/**
 * Represents a track segment, which is just a collection of zero or more track points.
 */

public class GPXtrkseg {
    
    private ArrayList<GPXtrkpt> _trkpts;

    public GPXtrkseg(ArrayList<GPXtrkpt> trkpts) {
    	_trkpts = trkpts;
    }

    /**
     * Get the track point for the given index.
     *
     * @param index The index of the point to be retrieved.
     * @return The track point at the provided index. Return null if the index is too large (i.e., is larger than the number of points)
     */
    public GPXtrkpt trkpt(int index) {
    	if (index >= _trkpts.size()) return null;
    	else return (GPXtrkpt)(_trkpts.get(index));
    }

    /**
     * @return the number of track points in this segment
     */
    public int numPoints() {
    	return _trkpts.size();
    }

    /**
     * @return an array of track point objects
     */
    public GPXtrkpt[] trkpts() {
    	GPXtrkpt pts[] = new GPXtrkpt[_trkpts.size()];
    	for (int i = 0; i < pts.length; i++) pts[i] = (GPXtrkpt)_trkpts.get(i);
    	return pts;
    }

}
