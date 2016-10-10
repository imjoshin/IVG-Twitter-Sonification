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
	            parseStatus(status);
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
		String language= status.getLang();
		String filterLang="en";
		if (filterLang.matches(language)){
			System.out.println(status.getUser().getName() + " : " + status.getText());
		}
		
		
	}
}
