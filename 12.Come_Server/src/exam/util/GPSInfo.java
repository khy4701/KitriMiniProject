package exam.util;

public class GPSInfo {

	double latitude;
	double longitude;
			
	
	public GPSInfo(double latitude, double longitude) {
		// TODO Auto-generated constructor stub
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	
}
