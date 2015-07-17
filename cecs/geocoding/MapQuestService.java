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

public class MapQuestService extends GeoService {

	private GeoCoordinates geoCoordinates;
	private Map<String, String> parameters;
	
	public MapQuestService(GeoCoordinates geoCoordinates) {
		this.geoCoordinates = geoCoordinates;
		
		String origin_lat, origin_lng, dest_lat, dest_lng;
		
		origin_lat = Double.toString(geoCoordinates.getOriginLatitude());
		origin_lng = Double.toString(geoCoordinates.getOriginLongitude());
		dest_lat = Double.toString(geoCoordinates.getDestinationLatitude());
		dest_lng = Double.toString(geoCoordinates.getDestinationLongitude());
		
		parameters = new HashMap<String, String>();
		parameters.put("key", "Fmjtd%7Cluu82l0znq%2C7a%3Do5-94zsdf");
		
		parameters.put("from", origin_lat + ',' + origin_lng);
		parameters.put("to", dest_lat + ',' + dest_lng);
		
	}
	
	@Override
	public GeoCoordinates submitDirectionsRequest() {
		
		// Fill-in, use documentation (http://open.mapquestapi.com/directions/);
		// 	GoogleMapsService for guidance.
		
		GeoCoordinates geoCoordinates = new GeoCoordinates(this.geoCoordinates.getOriginLatitude(), this.geoCoordinates.getOriginLongitude(), this.geoCoordinates.getDestinationLatitude(), this.geoCoordinates.getDestinationLongitude());
		
		try {
			String url = "http://open.mapquestapi.com/directions/v2/route" + CECSNetUtils.mapToQueryString(this.parameters);
			
			CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
			HttpGet request = new HttpGet(url);
			request.addHeader("Referer",  "http://open.mapquestapi.com/");
			CloseableHttpResponse response = httpClient.execute(request);
			
			try {
			    HttpEntity entity = response.getEntity();
			    JsonReader reader = Json.createReader(entity.getContent());
			    JsonObject obj = reader.readObject();
			    JsonObject routes = obj.getJsonObject("route");
			   	JsonArray legs = routes.getJsonArray("legs");

		    	for(JsonObject leg : legs.getValuesAs(JsonObject.class)) {
		    		geoCoordinates.setDrivingDistance(Double.parseDouble(leg.get("distance").toString()));
		    		geoCoordinates.setDrivingTime(Double.parseDouble(leg.get("time").toString()));
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
