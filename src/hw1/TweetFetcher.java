package hw1;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TweetFetcher implements Runnable {

	@Override
	public void run() {
		DynamoDB dynamoDB = new DynamoDB(new AmazonDynamoDBClient(new BasicAWSCredentials("", "")));
		Table table = dynamoDB.getTable("CloudComputingHW1");
		ConfigurationBuilder cb = new ConfigurationBuilder();

		cb.setDebugEnabled(true).setOAuthConsumerKey("")
				.setOAuthConsumerSecret("")
				.setOAuthAccessToken("")
				.setOAuthAccessTokenSecret("");

		TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
		StatusListener listener = new StatusListener() {
			@Override
			public void onStatus(Status status) {
				if (status.getGeoLocation() == null) {
					return;
				}
				
				Item item = new Item()
						.withPrimaryKey("TweetId", String.valueOf(status.getId()))
						.withString("Text", status.getText())
						.withDouble("Latitude", status.getGeoLocation().getLatitude())
						.withDouble("Longitude", status.getGeoLocation().getLongitude())
						.withBoolean("Sanders", status.getText().contains("Sanders"))
						.withBoolean("Clinton", status.getText().contains("Clinton"))
						.withBoolean("Webb", status.getText().contains("Webb"))
						.withBoolean("O'Malley", status.getText().contains("O'Malley"))
						.withBoolean("Trump", status.getText().contains("Trump"))
						.withBoolean("Bush", status.getText().contains("Bush"))
						.withBoolean("Carson", status.getText().contains("Carson"))
						.withBoolean("Fiorina", status.getText().contains("Fiorina"));
				
				PutItemOutcome outcome = table.putItem(item);
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
			}

			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
			}

			@Override
			public void onScrubGeo(long userId, long upToStatusId) {
			}

			@Override
			public void onStallWarning(StallWarning warning) {
			}

			@Override
			public void onException(Exception ex) {
				ex.printStackTrace();
			}
		};
		twitterStream.addListener(listener);
		twitterStream.sample();
	}

}
