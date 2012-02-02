package edu.upenn.cis350.gpx;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class GPXcalculatorTest {
	
	GPXtrkpt pt1, pt2, pt3, pt4;
	GPXtrkpt pt1dup, pt2dup;
	GPXtrkpt boundaryPt1, boundaryPt2, boundaryPt3, boundaryPt4;
	GPXtrkpt invalidPt1, invalidPt2, invalidPt3, invalidPt4;
	
	@Before
	public void setUp() throws Exception {
		/* Initialize variables for testing */
		pt1 = new GPXtrkpt(0, 0, new Date(12345)); // origin
		pt1dup = new GPXtrkpt(0, 0, new Date(12345)); // origin
		pt2 = new GPXtrkpt(0, 10, new Date(23456)); // 10 from pt1
		pt2dup = new GPXtrkpt(0, 10, new Date(23456)); // 10 from pt1
		pt3 = new GPXtrkpt(10, 0, new Date(34567)); // 10 from pt2
		pt4 = new GPXtrkpt(16, 8, new Date(45678)); // 10 from pt3
		
		boundaryPt1 = new GPXtrkpt(90, 0, new Date(12345)); // MAX lat
		boundaryPt2 = new GPXtrkpt(-90, 0, new Date(23456)); // MIN lat
		boundaryPt3 = new GPXtrkpt(0, 180, new Date(34567)); // MAX long
		boundaryPt4 = new GPXtrkpt(0, -180, new Date(45678)); // MIN long
		
		invalidPt1 = new GPXtrkpt(100, 0, new Date(12345)); // lat > 90
		invalidPt2 = new GPXtrkpt(-100, 0, new Date(23456)); // lat < -90
		invalidPt3 = new GPXtrkpt(0, 190, new Date(34567)); // long > 180
		invalidPt4 = new GPXtrkpt(0, -190, new Date(45678)); // long < -180
	}
	
	/** 
	 * Basic test for calculate distance functionality with normal inputs
	 */
	@Test
	public void test_calculateDistanceTraveled_normalInputsForDistance() {
		ArrayList<GPXtrkpt> ptArr = new ArrayList<GPXtrkpt>();
		ptArr.add(pt1);
		ptArr.add(pt2);
		ptArr.add(pt3);
		ptArr.add(pt4);
		
		GPXtrkseg seg = new GPXtrkseg(ptArr);
		
		ArrayList<GPXtrkseg> segArr = new ArrayList<GPXtrkseg>();
		segArr.add(seg);
		
		GPXtrk trk = new GPXtrk("test", segArr);
		
		double result = GPXcalculator.calculateDistanceTraveled(trk);
		
		/* Total distance = 30, as shown above */
		assertEquals(30, result, 0);
	}
	
	/**
	 * Test calculate distance with boundary points
	 */
	@Test
	public void test_calculateDistanceTraveled_boundaryInputsForDistance() {
		ArrayList<GPXtrkpt> ptArr1 = new ArrayList<GPXtrkpt>();
		ptArr1.add(boundaryPt1);
		ptArr1.add(boundaryPt2); // distance = 180
		ArrayList<GPXtrkpt> ptArr2 = new ArrayList<GPXtrkpt>();
		ptArr2.add(boundaryPt3);
		ptArr2.add(boundaryPt4); // distance = 360
		
		GPXtrkseg seg1 = new GPXtrkseg(ptArr1);
		GPXtrkseg seg2 = new GPXtrkseg(ptArr2);
		
		ArrayList<GPXtrkseg> segArr = new ArrayList<GPXtrkseg>();
		segArr.add(seg1);
		segArr.add(seg2);
		
		GPXtrk trk = new GPXtrk("test", segArr);
		
		double result = GPXcalculator.calculateDistanceTraveled(trk);
		
		/* Total distance = 540 = 180 + 360 */
		assertEquals(540, result, 0);
	}
	
	/**
	 * Test calculate distance with points that have 0 distance (same points essentially)
	 */
	@Test
	public void test_calculateDistanceTraveled_pointsWithZeroDistance() {
		ArrayList<GPXtrkpt> ptArr1 = new ArrayList<GPXtrkpt>();
		ptArr1.add(pt1);
		ptArr1.add(pt1dup); // distance = 0 because they are on the same point
		ArrayList<GPXtrkpt> ptArr2 = new ArrayList<GPXtrkpt>();
		ptArr2.add(pt2);
		ptArr2.add(pt2dup); // distance = 0 because they are also on the same point
		
		GPXtrkseg seg1 = new GPXtrkseg(ptArr1);
		GPXtrkseg seg2 = new GPXtrkseg(ptArr2);
		
		ArrayList<GPXtrkseg> segArr = new ArrayList<GPXtrkseg>();
		segArr.add(seg1);
		segArr.add(seg2);
		
		GPXtrk trk = new GPXtrk("test", segArr);
		
		double result = GPXcalculator.calculateDistanceTraveled(trk);
		
		assertEquals(0, result, 0);
	}
	
	/** 
	 * Test with points exceeding MAX latitude (distance for that segment should be 0)
	 */
	@Test
	public void test_calculateDistanceTraveled_exceedMaxLat() {
		ArrayList<GPXtrkpt> ptArr1 = new ArrayList<GPXtrkpt>();
		ptArr1.add(pt1);
		ptArr1.add(pt2); // total distance = 10
		ArrayList<GPXtrkpt> ptArr2 = new ArrayList<GPXtrkpt>();
		ptArr2.add(pt3);
		ptArr2.add(pt4);
		ptArr2.add(invalidPt1); // total distance = 0 
		
		GPXtrkseg seg1 = new GPXtrkseg(ptArr1);
		GPXtrkseg seg2 = new GPXtrkseg(ptArr2);
		
		ArrayList<GPXtrkseg> segArr = new ArrayList<GPXtrkseg>();
		segArr.add(seg1);
		segArr.add(seg2);
		
		GPXtrk trk = new GPXtrk("test", segArr);
		
		double result = GPXcalculator.calculateDistanceTraveled(trk);
		
		/* Total distance = 10, as shown above */
		assertEquals(10, result, 0);
	}
	
	/** 
	 * Test with points exceeding MIN latitude (distance for that segment should be 0)
	 * Essentially same test as above but with different invalidPt#
	 */
	@Test
	public void test_calculateDistanceTraveled_exceedMinLat() {
		ArrayList<GPXtrkpt> ptArr1 = new ArrayList<GPXtrkpt>();
		ptArr1.add(pt1);
		ptArr1.add(pt2); // total distance = 10
		ArrayList<GPXtrkpt> ptArr2 = new ArrayList<GPXtrkpt>();
		ptArr2.add(pt3);
		ptArr2.add(pt4);
		ptArr2.add(invalidPt2); // total distance = 0 
		
		GPXtrkseg seg1 = new GPXtrkseg(ptArr1);
		GPXtrkseg seg2 = new GPXtrkseg(ptArr2);
		
		ArrayList<GPXtrkseg> segArr = new ArrayList<GPXtrkseg>();
		segArr.add(seg1);
		segArr.add(seg2);
		
		GPXtrk trk = new GPXtrk("test", segArr);
		
		double result = GPXcalculator.calculateDistanceTraveled(trk);
		
		/* Total distance = 10, as shown above */
		assertEquals(10, result, 0);
	}
	
	/** 
	 * Test with points exceeding MAX longitude (distance for that segment should be 0)
	 * Essentially same test as above but with different invalidPt#
	 */
	@Test
	public void test_calculateDistanceTraveled_exceedMaxLong() {
		ArrayList<GPXtrkpt> ptArr1 = new ArrayList<GPXtrkpt>();
		ptArr1.add(pt1);
		ptArr1.add(pt2); // total distance = 10
		ArrayList<GPXtrkpt> ptArr2 = new ArrayList<GPXtrkpt>();
		ptArr2.add(pt3);
		ptArr2.add(pt4);
		ptArr2.add(invalidPt3); // total distance = 0 
		
		GPXtrkseg seg1 = new GPXtrkseg(ptArr1);
		GPXtrkseg seg2 = new GPXtrkseg(ptArr2);
		
		ArrayList<GPXtrkseg> segArr = new ArrayList<GPXtrkseg>();
		segArr.add(seg1);
		segArr.add(seg2);
		
		GPXtrk trk = new GPXtrk("test", segArr);
		
		double result = GPXcalculator.calculateDistanceTraveled(trk);
		
		/* Total distance = 10, as shown above */
		assertEquals(10, result, 0);
	}
	
	/** 
	 * Test with points exceeding MIN longitude (distance for that segment should be 0)
	 * Essentially same test as above but with different invalidPt#
	 */
	@Test
	public void test_calculateDistanceTraveled_exceedMinLong() {
		ArrayList<GPXtrkpt> ptArr1 = new ArrayList<GPXtrkpt>();
		ptArr1.add(pt1);
		ptArr1.add(pt2); // total distance = 10
		ArrayList<GPXtrkpt> ptArr2 = new ArrayList<GPXtrkpt>();
		ptArr2.add(pt3);
		ptArr2.add(pt4);
		ptArr2.add(invalidPt4); // total distance = 0 
		
		GPXtrkseg seg1 = new GPXtrkseg(ptArr1);
		GPXtrkseg seg2 = new GPXtrkseg(ptArr2);
		
		ArrayList<GPXtrkseg> segArr = new ArrayList<GPXtrkseg>();
		segArr.add(seg1);
		segArr.add(seg2);
		
		GPXtrk trk = new GPXtrk("test", segArr);
		
		double result = GPXcalculator.calculateDistanceTraveled(trk);
		
		/* Total distance = 10, as shown above */
		assertEquals(10, result, 0);
	}

	/**
	 * Test if GPXtrk argument is null
	 */
	@Test
	public void test_calculateDistanceTraveled_nullInput() {
		double result = GPXcalculator.calculateDistanceTraveled(null);
		assertEquals(-1, result, 0);
	}
	
	/**
	 * Test if given GPXtrk object has no GPXtrkseg objects in array
	 */
	@Test
	public void test_calculateDistanceTraveled_noGPXtrkseg() {
		GPXtrk trk = new GPXtrk("test", new ArrayList<GPXtrkseg>());
		
		double result = GPXcalculator.calculateDistanceTraveled(trk);
		assertEquals(-1, result, 0);
	}
	
	/** 
	 * Test that NULL GPXtrkseg objects return 0 as distance
	 */
	@Test
	public void test_calculateDistanceTraveled_nullGPXtrksegObj() {
		// ptArr1 total distance = 10
		ArrayList<GPXtrkpt> ptArr1 = new ArrayList<GPXtrkpt>();
		ptArr1.add(pt1);
		ptArr1.add(pt2);
		
		// ptArr2 total distance = 10
		ArrayList<GPXtrkpt> ptArr2 = new ArrayList<GPXtrkpt>();
		ptArr2.add(pt3);
		ptArr2.add(pt4);
		
		GPXtrkseg seg1 = new GPXtrkseg(ptArr1);
		GPXtrkseg seg2 = new GPXtrkseg(ptArr2);
		
		ArrayList<GPXtrkseg> segArr = new ArrayList<GPXtrkseg>();
		segArr.add(seg1); // distance = 10
		segArr.add(null); // should not impact total distance
		segArr.add(seg2); // distance = 10
		
		GPXtrk trk = new GPXtrk("test", segArr);
		
		double result = GPXcalculator.calculateDistanceTraveled(trk);
		
		/* Total distance = 20, since the null segment do not affect total distance */
		assertEquals(20, result, 0);
	}
	
	/**
	 * Test that GPXtrkseg objects that doesn't contain any points do not contribute to total distance
	 */
	@Test
	public void test_calculateDistanceTraveled_emptyGPXtrksegObj() {
		// ptArr1 total distance = 10
		ArrayList<GPXtrkpt> ptArr1 = new ArrayList<GPXtrkpt>();
		ptArr1.add(pt1);
		ptArr1.add(pt2);
		
		GPXtrkseg seg1 = new GPXtrkseg(ptArr1);
		GPXtrkseg seg2 = new GPXtrkseg(new ArrayList<GPXtrkpt>());
		
		ArrayList<GPXtrkseg> segArr = new ArrayList<GPXtrkseg>();
		segArr.add(seg1); // distance = 10
		segArr.add(seg2); // distance = 0, no points in segment
		
		GPXtrk trk = new GPXtrk("test", segArr);
		
		double result = GPXcalculator.calculateDistanceTraveled(trk);
		
		/* Total distance = 10, since the segment with no points does not affect total distance */
		assertEquals(10, result, 0);
	}
	
	/**
	 * Test that GPXtrkseg objects that contain only one point do not contribute to total distance
	 */
	@Test
	public void test_calculateDistanceTraveled_GPXtrksegObjWithOnePt() {
		// ptArr1 total distance = 10
		ArrayList<GPXtrkpt> ptArr1 = new ArrayList<GPXtrkpt>();
		ptArr1.add(pt1);
		ptArr1.add(pt2);
		// ptArr2 total distance = 0, only 1 point
		ArrayList<GPXtrkpt> ptArr2 = new ArrayList<GPXtrkpt>();
		ptArr2.add(pt3);
		
		GPXtrkseg seg1 = new GPXtrkseg(ptArr1);
		GPXtrkseg seg2 = new GPXtrkseg(ptArr2);
		
		ArrayList<GPXtrkseg> segArr = new ArrayList<GPXtrkseg>();
		segArr.add(seg1); // distance = 10
		segArr.add(seg2); // distance = 10
		
		GPXtrk trk = new GPXtrk("test", segArr);
		
		double result = GPXcalculator.calculateDistanceTraveled(trk);
		
		/* Total distance = 10, since the segment with only one point does not affect total distance */
		assertEquals(10, result, 0);
	}
	
	/**
	 * Test that if any point in GPXtrkseg is null, the whole segment is considered distance of 0
	 */
	@Test
	public void test_calculateDistanceTraveled_GPXtrksegObjContainingNullPt() {
		// ptArr1 total distance = 10
		ArrayList<GPXtrkpt> ptArr1 = new ArrayList<GPXtrkpt>();
		ptArr1.add(pt1);
		ptArr1.add(pt2);
		
		// ptArr2 total distance = 0 (contains null point)
		ArrayList<GPXtrkpt> ptArr2 = new ArrayList<GPXtrkpt>();
		ptArr2.add(pt3);
		ptArr2.add(pt4);
		ptArr2.add(null);
		
		GPXtrkseg seg1 = new GPXtrkseg(ptArr1);
		GPXtrkseg seg2 = new GPXtrkseg(ptArr2);
		
		ArrayList<GPXtrkseg> segArr = new ArrayList<GPXtrkseg>();
		segArr.add(seg1); // distance = 10
		segArr.add(seg2); // distance = 0
		
		GPXtrk trk = new GPXtrk("test", segArr);
		
		double result = GPXcalculator.calculateDistanceTraveled(trk);
		
		/* Total distance = 10, since the segment containing a null point will have distance of 0 */
		assertEquals(10, result, 0);
	}
}
