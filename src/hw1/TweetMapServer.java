package hw1;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanFilter;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;


@ServerEndpoint("/actions")
public class TweetMapServer {
	private DynamoDB dynamoDB;
	private Table table;
	private String tableName = "CloudComputingHW1";
	
	@OnOpen
	public void open(Session session) {
		dynamoDB = new DynamoDB(new AmazonDynamoDBClient(
				new BasicAWSCredentials("", "")));
		table = dynamoDB.getTable(tableName);
		
		SessionHandler.addSession(session);

		ItemCollection<ScanOutcome> scans = table.scan(new ScanFilter("TweetId").ge("0"));
		for (Item scan : scans) {
			System.out.println("Sending Tweet with ID: " + scan.getString("TweetId"));
			SessionHandler.sendToSession(session, scan.toJSON());
		}

		System.out.println("Connection detected.");
	}
	@OnClose
	public void close(Session session) {
		SessionHandler.removeSession(session);
		System.out.println("Session Closed.");
	}
	@OnError
	public void onError(Throwable error) {
		System.out.println(error.getMessage());
	}
	@OnMessage 
	public void handleMessage(String message, Session session) {
	}
}
