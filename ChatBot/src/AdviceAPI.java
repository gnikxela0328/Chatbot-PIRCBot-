import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class AdviceAPI {

	//Advice constructor
	public AdviceAPI() {
		
	}
	
	//Gives back random piece of advice	
	public String getAdvice() {
		String BaseURL = "https://api.adviceslip.com/advice";
		StringBuilder grab = new StringBuilder();
		
		try {
			URL url = new URL(BaseURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader read = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String temp;
				
			//Extracts full fetch
			while((temp = read.readLine()) != null)
				grab.append(temp);
			
			read.close();
		}
		catch(Exception e) {
			return "Exception: " + e;
		}
		
		System.out.println(grab);
		
		return parseJsonFunction(grab.toString());
	}
	
	//Parses Json
	public String parseJsonFunction(String json) {
		JsonObject object = new JsonParser().parse(json).getAsJsonObject();
		
		JsonObject slip = object.getAsJsonObject("slip");
		int slipNum = slip.get("slip_id").getAsInt();
		String advice = slip.get("advice").getAsString();
		
		return "Advice #" + slipNum + ": " + advice;
	}
}
