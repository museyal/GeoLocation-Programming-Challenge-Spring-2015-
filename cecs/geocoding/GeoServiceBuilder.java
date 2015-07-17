package cecs.geocoding;

public class GeoServiceBuilder {
	
	private GeoServiceBuilder() {}
	
	public static GeoService build(ServiceProvider serviceProvider, GeoCoordinates geoCoordinates) {
		
		if(serviceProvider == ServiceProvider.GOOGLE) {
			return new GoogleMapsService(geoCoordinates);
			
		} else if(serviceProvider == ServiceProvider.MAPQUEST) {
			return new MapQuestService(geoCoordinates);
		}
		
		return null;
	}
}
