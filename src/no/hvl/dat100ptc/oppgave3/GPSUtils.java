package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max;

		max = da[0];

		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}

		return max;
	}

	public static double findMin(double[] da) {

		double min;

		min = da[0];

		for (double d : da) {
			if (d < min) {
				min = d;
			}
		}

		return min;

	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		
		double[] latitudes = new double[gpspoints.length];

		for (int i = 0; i < gpspoints.length; i++) {

			latitudes[i] = gpspoints[i].getLatitude();

		}

		return latitudes;

	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		double[] longitudes = new double[gpspoints.length];

		for (int i = 0; i < gpspoints.length; i++) {

			longitudes[i] = gpspoints[i].getLongitude();

		}

		return longitudes;

	}

	private static int R = 6371000;

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double d;
		double latitude1, longitude1, latitude2, longitude2;
		
		latitude1 = toRadians(gpspoint1.getLatitude());
		latitude2 = toRadians(gpspoint2.getLatitude());
		longitude1 = toRadians(gpspoint1.getLongitude());
		longitude2 = toRadians(gpspoint2.getLongitude());
		
		double a = pow(sin((latitude2-latitude1)/2),2)+cos(latitude1)*cos(latitude2)*pow(sin((longitude2-longitude1)/2),2);
		double c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		d = R*c;
		
		return d;
	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int secs;
		double speed;
		
		secs = gpspoint2.getTime() - gpspoint1.getTime();
		speed = distance(gpspoint1, gpspoint2)/secs * 3.6;
		
		return speed;
	}

	public static String formatTime(int secs) {

		String timestr;
		String TIMESEP = ":";
		
		long HH = secs/3600;
		long MM = (secs%3600)/60;
		long SS = secs%60;
		
		timestr = String.format("  %02d"+TIMESEP+"%02d"+TIMESEP+"%02d", HH, MM, SS);
		
		return timestr;

	}

	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {

		String str;
		
		d = Math.round(d*100);
		d = d/100;
		
		str = String.format("%" + TEXTWIDTH + "s", d);

		return str;

	}
}
