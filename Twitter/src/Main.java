import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Main {

	private static Sound sound = new Sound();
	public static void main(String[] args) {
		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("resources/config.properties");
			prop.load(input);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Could not load properties file.");
			System.exit(0);
		}
		
		String cKey = prop.getProperty("cKey");
		
		
		StatusListener listener = new StatusListener(){
	        public void onStatus(Status status) {
	        	String language= status.getLang();
	    		String filterLang="en";
	    		if (filterLang.matches(language)){
	    			parseStatus(status);
	    		}
	        }
	        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
	        public void onException(Exception ex) {
	            ex.printStackTrace();
	        }
			public void onDeletionNotice(StatusDeletionNotice arg0) {}
			public void onScrubGeo(long arg0, long arg1) {}
			public void onStallWarning(StallWarning arg0) {}
	    };
	    
	    ConfigurationBuilder cb = new ConfigurationBuilder();
	    cb.setDebugEnabled(true)
	      .setOAuthConsumerKey(prop.getProperty("cKey"))
	      .setOAuthConsumerSecret(prop.getProperty("cSecret"))
	      .setOAuthAccessToken(prop.getProperty("aKey"))
	      .setOAuthAccessTokenSecret(prop.getProperty("aSecret"));
	    
	    TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
	    twitterStream.addListener(listener);
	    twitterStream.sample();
	}

	public static void parseStatus(Status status){
		String text = status.getText();
		//System.out.println(text);
		
		boolean isRetweet = text.startsWith("RT");
		if(isRetweet) text = text.substring(3);
		
		//replace URL
		text = text.replaceAll("(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", "");
		//replace mentions
		//text = text.replaceAll("(?<=^|(?<=[^a-zA-Z0-9-_.]))@([A-Za-z]+[A-Za-z0-9]+)", "");
		//text = text.replaceAll("(?:\\s|\\A)[@]+([A-Za-z0-9-_]+)[!:,.; ] *", "");
		text = text.replaceAll("@[a-zA-Z0-9_]*[!:,.; ] *", "");
		//new lines and extra spaces
		text = text.replaceAll("\n", "");
		text = text.replaceAll("  +", "");
		
		System.out.println(text);
		
		//sound.playNote();
	}
}
