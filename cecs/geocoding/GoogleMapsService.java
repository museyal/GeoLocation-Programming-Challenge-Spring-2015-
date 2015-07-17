package cecs.geocoding;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import cecs.CECSNetUtils;

public class GoogleMapsService extends GeoService {

	private GeoCoordinates geoCoordinates;
	private Map<String, String> parameters;

	public GoogleMapsService(GeoCoordinates geoCoordinates) {
		this.geoCoordinates = geoCoordinates;
		
		String origin_lat, origin_lng, dest_lat, dest_lng;
		
		origin_lat = Double.toString(geoCoordinates.getOriginLatitude());
		origin_lng = Double.toString(geoCoordinates.getOriginLongitude());
		dest_lat = Double.toString(geoCoordinates.getDestinationLatitude());
		dest_lng = Double.toString(geoCoordinates.getDestinationLongitude());
		
		parameters = new HashMap<String, String>();
		parameters.put("key", "AIzaSyCpOui1wORmwjCG-J8rn2bBu4BMbSvW52w");
		parameters.put("origin", origin_lat + ',' + origin_lng);
		parameters.put("destination", dest_lat + ',' + dest_lng);	
	}
	
	@Override
	public GeoCoordinates submitDirectionsRequest() {

		//	documentation: https://developers.google.com/maps/documentation/directions/
		
		GeoCoordinates geoCoordinates = new GeoCoordinates(this.geoCoordinates.getOriginLatitude(),
				this.geoCoordinates.getOriginLongitude(), this.geoCoordinates.getDestinationLatitude(),
				this.geoCoordinates.getDestinationLongitude());
		
		
		try {
			String url = "https://maps.googleapis.com/maps/api/directions/json" + CECSNetUtils.mapToQueryString(parameters);
			
			CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
			HttpGet request = new HttpGet(url);
			//System.out.println(url);
			CloseableHttpResponse response = httpClient.execute(request);
			
			try {
			    HttpEntity entity = response.getEntity();
			    JsonReader reader = Json.createReader(entity.getContent());
			    JsonObject obj = reader.readObject();
			    JsonArray routes = obj.getJsonArray("routes");
			    
			    for(JsonObject route : routes.getValuesAs(JsonObject.class)) {
			    	JsonArray legs = route.getJsonArray("legs");
			    	
			    	for(JsonObject leg : legs.getValuesAs(JsonObject.class)) {
			    		geoCoordinates.setDrivingDistance(Double.parseDouble(leg.getJsonObject("distance").get("value").toString()));
			    		geoCoordinates.setDrivingTime(Double.parseDouble(leg.getJsonObject("duration").get("value").toString()));
			    	}
			    }
			    
			} finally {
			    response.close();
			}
			
		} catch (ClientProtocolException e) {

			e.printStackTrace();
			
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		return geoCoordinates;
	}
}
