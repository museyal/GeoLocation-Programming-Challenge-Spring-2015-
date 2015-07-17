package cecs.geocoding;

public class GeoCoordinates {

	private double originLatitude;
	private double originLongitude;
	private double destinationLatitude;
	private double destinationLongitude;
	private double drivingDistance;
	private double drivingTime;
	
	public GeoCoordinates(double originLatitude, double originLongitude,
			double destinationLatitude, double destinationLongitude) {

		this.originLatitude = originLatitude;
		this.originLongitude = originLongitude;
		this.destinationLatitude = destinationLatitude;
		this.destinationLongitude = destinationLongitude;
	}
	
	public double getOriginLatitude() {
		return originLatitude;
	}
	
	public void setOriginLatitude(double originLatitude) {
		this.originLatitude = originLatitude;
	}
	
	public double getOriginLongitude() {
		return originLongitude;
	}
	
	public void setOriginLongitude(double originLongitude) {
		this.originLongitude = originLongitude;
	}
	
	public double getDestinationLatitude() {
		return destinationLatitude;
	}
	
	public void setDestinationLatitude(double destinationLatitude) {
		this.destinationLatitude = destinationLatitude;
	}
	
	public double getDestinationLongitude() {
		return destinationLongitude;
	}
	
	public void setDestinationLongitude(double destinationLongitude) {
		this.destinationLongitude = destinationLongitude;
	}

	public double getDrivingDistance() {
		return drivingDistance;
	}

	public void setDrivingDistance(double drivingDistance) {
		this.drivingDistance = drivingDistance;
	}

	public double getDrivingTime() {
		return drivingTime;
	}

	public void setDrivingTime(double drivingTime) {
		this.drivingTime = drivingTime;
	}

	@Override
	public String toString() {
		return "GeoCoordinates [originLatitude=" + originLatitude
				+ ", originLongitude=" + originLongitude
				+ ", destinationLatitude=" + destinationLatitude
				+ ", destinationLongitude=" + destinationLongitude
				+ ", drivingDistance=" + drivingDistance + ", drivingTime="
				+ drivingTime + "]";
	}
}
