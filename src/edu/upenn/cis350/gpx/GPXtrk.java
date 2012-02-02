package edu.upenn.cis350.gpx;

/**
 * Represents a GPX track, which includes a name and some number
 * of GPX track segments.
 */

import java.util.ArrayList;

public class GPXtrk {
    
    // the name for this track
    private String _name;
    // a list of track segments
    private ArrayList<GPXtrkseg> _trksegs;

    public GPXtrk(String name, ArrayList<GPXtrkseg> trksegs) {
    	_name = name;
    	_trksegs = trksegs;
    }

    public String name() { return _name; }

    /**
     * Get the track segment for the given index.
     *
     * @param index The index of the segment to be retrieved.
     * @return The track segment at the provided index. Return null if the index is too large (i.e., is larger than the number of segments)
     */
    public GPXtrkseg trkseg(int index) {
    	if (index >= _trksegs.size()) return null;
    	else return (GPXtrkseg)(_trksegs.get(index));
    }

    /**
     * @return the number of segments
     */
    public int numSegments() {
    	return _trksegs.size();
    }
    
    /**
     * @return an array containing all of the track segments.
     */
    public GPXtrkseg[] trksegs() {
		GPXtrkseg segs[] = new GPXtrkseg[_trksegs.size()];
		for (int i = 0; i < segs.length; i++) segs[i] = (GPXtrkseg)_trksegs.get(i);
		return segs;
    }

}