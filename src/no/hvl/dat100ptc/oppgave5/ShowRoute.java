package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;

	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		showRouteMap(MARGIN + MAPYSIZE);

		showStatistics();
	}

	// antall x-pixels per lengdegrad
	public double xstep() {

		double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));

		double xstep = MAPXSIZE / (Math.abs(maxlon - minlon));

		return xstep;
	}

	// antall y-pixels per breddegrad
	public double ystep() {

		double ystep;

		double maxlon = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		ystep = MAPXSIZE / (Math.abs(maxlon - minlon));

		return ystep;

	}

	public void showRouteMap(int ybase) {

		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		int x = 0;
		int y = 0;

		for (int i = 0; i < gpspoints.length; i++) {

			int x2 = x;
			int y2 = y;

			y = (int) ((gpspoints[i].getLatitude() - minlat) * ystep());
			x = (int) ((gpspoints[i].getLongitude() - minlon) * xstep());

			setColor(0, 225, 0);
			fillCircle(x, (ybase - y), 3);
			if (i > 0) {
				drawLine(x2, ybase - y2, x, ybase - y);
			}
		}

	}

	public void showStatistics() {

		int TEXTDISTANCE = 20;

		setColor(0, 0, 0);
		setFont("Courier", 12);

		int WEIGHT = 80;

		String out = "";

		out = "\n Total time:  " + GPSUtils.formatTime(gpscomputer.totalTime()) + "\n Total Distance:  "
				+ gpscomputer.totalDistance() + "km \n Total Elevation:  " + gpscomputer.totalElevation()
				+ "m \n Max Speed:  " + gpscomputer.maxSpeed() + "km/t \n Average Speed:  " + gpscomputer.averageSpeed()
				+ "km/t \n Energy:  " + gpscomputer.totalKcal(WEIGHT) + " kcal";

		String[] out1 = out.split("\n");

		for (int i = 0; i < out1.length; i++) {

			drawString(out1[i], 10, TEXTDISTANCE * i);

		}
	}

}
