package cecs.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import cecs.geocoding.GeoCoordinates;
import cecs.geocoding.GeoServiceBuilder;
import cecs.geocoding.ServiceProvider;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;


public class GeoCodingClient {
	
	public static void main(String[] args) {
		
		double origin_lat, origin_lng, dest_lat, dest_lng;
		
		try {
			FileInputStream fis = new FileInputStream(new File("coordinates.json"));
			JsonReader reader = Json.createReader(fis);

			JsonObject obj = reader.readObject();
			JsonArray results = obj.getJsonArray("directions");
			
			for(JsonObject result : results.getValuesAs(JsonObject.class)) {

				origin_lat = Double.parseDouble(result.getJsonNumber("origin_latitude").toString());
				origin_lng = Double.parseDouble(result.getJsonNumber("origin_longitude").toString());
				dest_lat = Double.parseDouble(result.getJsonNumber("destination_latitude").toString());
				dest_lng = Double.parseDouble(result.getJsonNumber("destination_longitude").toString());
				
				GeoCoordinates geoCoordinates = new GeoCoordinates(origin_lat, origin_lng, dest_lat, dest_lng);
				geoCoordinates = GeoServiceBuilder.build(ServiceProvider.MAPQUEST, geoCoordinates).submitDirectionsRequest();
				//geoCoordinates = GeoServiceBuilder.build(ServiceProvider.GOOGLE, geoCoordinates).submitDirectionsRequest();
				
				System.out.println(geoCoordinates);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
