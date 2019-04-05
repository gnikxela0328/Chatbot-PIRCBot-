public class MyBotMain {

	public static void main(String args[]) throws Exception {
		
		//Initialization
		MyBot GFbot = new MyBot();
		GFbot.setVerbose(true);
		GFbot.connect("irc.freenode.net");
		GFbot.joinChannel("#pircbot");
		
	}
}
