import org.jibble.pircbot.*;

public class MyBot extends PircBot{

	//Bot Constructor
	public MyBot() {
		this.setName("GilbertFitzBerald");
	}
	
	//Message Handling
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
			
		//Returns local time and date
		if (message.equalsIgnoreCase("time")) {
			String time = new java.util.Date().toString();
			sendMessage(channel, sender + ": time is now " + time);
		}
		
		//Returns weather of specified zipcode. Format [weather #####]
		if (message.contains("weather")) {
			
			WeatherAPI pull = new WeatherAPI();
			StringBuilder build = new StringBuilder();
			String properZip = "";   //Holds final Zip Code
			boolean zipGood = true; //Check to see if Zip Code is properly formatted
			
			
			//Ensures proper ZipCode entry and extraction
			String [] zipCode = message.split(" ");
			
			if (zipCode[0].length() == 5)
				build.append(zipCode[0]);
			else if (zipCode[1].length() == 5)
				build.append(zipCode[1]);	
			else {
				sendMessage(channel, "Invalid Zip Code, type 'weather  + Zip Code' to search again");
				zipGood = false;
			}
			
			//Proceeds if ZipCode is good
			if(zipGood) {
				properZip = build.toString();
				String data = pull.getWeather(properZip);
				sendMessage(channel, "Weather data : ");
				sendMessage(channel, "-----------------------------------------------------------------------------------");
				sendMessage(channel, data);
				sendMessage(channel, "-----------------------------------------------------------------------------------");
			}
			
		}
		
		//Returns random piece of advice
		if(message.contains("advice")) {
			AdviceAPI pull = new AdviceAPI();
			sendMessage(channel, "Some advice for you; " + pull.getAdvice());
		}
	}
}
