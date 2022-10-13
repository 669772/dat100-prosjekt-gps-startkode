package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {

	private GPSPoint[] gpspoints;

	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}

	public double totalDistance() {

		double distance = 0;

		for (int i = 0; i < gpspoints.length - 1; i++) {
			distance = distance + GPSUtils.distance(gpspoints[i], gpspoints[i + 1]);
		}

		return distance;
	}

	public double totalElevation() {

		double elevation = 0;

		for (int i = 0; i < gpspoints.length - 1; i++) {
			double endring = gpspoints[i + 1].getElevation() - gpspoints[i].getElevation();

			if (endring != Math.abs(endring)) {
				endring = 0;
			}
			elevation = elevation + endring;
		}
		return elevation;
	}

	public int totalTime() {

		int time = 0;

		for (int i = 0; i < gpspoints.length - 1; i++) {
			int endring = gpspoints[i + 1].getTime() - gpspoints[i].getTime();
			time = time + endring;
		}
		return time;
	}

	public double[] speeds() {

		double[] speed = new double[gpspoints.length - 1];

		for (int i = 0; i < gpspoints.length - 1; i++) {
			double gjen = GPSUtils.speed(gpspoints[i], gpspoints[i + 1]);

			speed[i] = gjen;
		}
		return speed;
	}

	public double maxSpeed() {

		double maxspeed = 0;

		for (double s : speeds()) {
			if (s > maxspeed) {
				maxspeed = s;
			}
		}
		return maxspeed;
	}

	public double averageSpeed() {

		double average = 0;

		average = ((totalDistance() / totalTime()) * 3.6);

		return average;

	}

	public static double MS = 2.236936;

	public double kcal(double weight, int secs, double speed) {

		double kcal;

		double met = 0;
		double speedmph = speed * MS;

		if (speedmph < 10) {
			met = 4;
		} else if (speedmph < 12) {
			met = 6.0;
		} else if (speedmph < 14) {
			met = 8.0;
		} else if (speedmph < 16) {
			met = 10.0;
		} else if (speedmph < 20) {
			met = 12.0;
		} else {
			met = 16.0;
		}

		kcal = (met * weight * secs)/3600;

		return kcal;

	}

	public double totalKcal(double weight) {

		double totalkcal = 0;
		
		totalkcal = kcal(weight, totalTime(), averageSpeed());
		
		return totalkcal;
	}
	

	private static double WEIGHT = 80.0;

	public void displayStatistics() {

		String out = "";

        System.out.println("==============================================");

        out = "Total time:  " + GPSUtils.formatTime(totalTime())+ "\n Total Distance:  " + totalDistance() + "km \n Total Elevation:  " + totalElevation() + "m \n Max Speed:  " + maxSpeed() + "km/t \n Average Speed:  " + averageSpeed() + "km/t \n Energy:  " + totalKcal(WEIGHT)+ " kcal";

        System.out.println(out);

        System.out.println("==============================================");
		
	}

}
