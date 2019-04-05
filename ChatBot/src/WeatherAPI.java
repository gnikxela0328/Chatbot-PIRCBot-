import java.io.*;
import java.net.*;
import java.text.DecimalFormat;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeatherAPI {
	
	//Weather constructor
	public WeatherAPI() {
		
	}
	
	//Fetches weather update
	public String getWeather(String zipCode){
		
		//Combines URL into one string
		String BaseURL = "http://api.openweathermap.org/data/2.5/weather?zip="; 
		String APIkey = "&appid=83516ba20d24c3061eac814c76b626a3";
		String APIurl = BaseURL + zipCode + APIkey; 
		StringBuilder grab = new StringBuilder();
		
		try {
			URL url = new URL(APIurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader read = new BufferedReader(new InputStreamReader(conn.getInputStream()));	
			String temp;
			
			//Extracts full fetch
			while((temp = read.readLine()) != null)
				grab.append(temp);
			
			read.close();
			
		}
		//Throws when Zip Code is invalid
		catch (FileNotFoundException e) {
			return "Zip Code does not exist, type 'weather  + Zip Code' to search again";
		}
		catch (Exception e) {
			return "Exception: " + e;
		}
		
		return parseJsonFunction(grab.toString());
		
	}
	
	//Parses JSON object
	public static String parseJsonFunction(String json) {
		JsonObject object = new JsonParser().parse(json).getAsJsonObject();
		
		String cityName = object.get("name").toString();
		JsonObject main = object.getAsJsonObject("main");
		double temperature = kelvinToFahrenheit(main.get("temp").getAsDouble());
		double lowTemp = kelvinToFahrenheit(main.get("temp_min").getAsDouble());
		double highTemp = kelvinToFahrenheit(main.get("temp_max").getAsDouble());
		int humidity = main.get("humidity").getAsInt();
		
		//For temperatures
		DecimalFormat format = new DecimalFormat("#.00");
		
		//Output
		return "City: " + cityName + " || Weather [ High of " + format.format(highTemp) + "F, Low of " 
		+ format.format(lowTemp) + "F; Current temperature " + format.format(temperature) + "F, " 
		+ "Humidity " + format.format(humidity) + "% ]";
	}
	
	//Converts temperatures to fahrenheit
	public static double kelvinToFahrenheit(double value) {
		return ((value - 273.15) * 1.8 + 32);
	}
}
