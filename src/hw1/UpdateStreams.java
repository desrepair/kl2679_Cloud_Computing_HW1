package hw1;

import java.util.List;
import java.util.Map;

import javax.json.JsonObject;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBStreams;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBStreamsClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.DescribeStreamRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeStreamResult;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.GetRecordsRequest;
import com.amazonaws.services.dynamodbv2.model.GetRecordsResult;
import com.amazonaws.services.dynamodbv2.model.GetShardIteratorRequest;
import com.amazonaws.services.dynamodbv2.model.GetShardIteratorResult;
import com.amazonaws.services.dynamodbv2.model.Record;
import com.amazonaws.services.dynamodbv2.model.Shard;
import com.amazonaws.services.dynamodbv2.model.ShardIteratorType;
import com.amazonaws.util.json.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class UpdateStreams implements Runnable {
	private static AmazonDynamoDBClient dynamoDB = new AmazonDynamoDBClient(new BasicAWSCredentials("", ""));
	private static AmazonDynamoDBStreams streams = new AmazonDynamoDBStreamsClient(new BasicAWSCredentials("", ""));
	
	@Override
	public void run() {
		DescribeTableResult describeTableResult = dynamoDB.describeTable("CloudComputingHW1");
		String myStreamArn = describeTableResult.getTable().getLatestStreamArn();
		DescribeStreamResult describeStreamResult = streams.describeStream(new DescribeStreamRequest()
				.withStreamArn(myStreamArn));
		List<Shard> shards = describeStreamResult.getStreamDescription().getShards();
		
		for (Shard shard : shards) {
			String shardId = shard.getShardId();
			GetShardIteratorRequest getShardIteratorRequest = new GetShardIteratorRequest()
					.withStreamArn(myStreamArn)
					.withShardId(shardId)
					.withShardIteratorType(ShardIteratorType.TRIM_HORIZON);
			GetShardIteratorResult getShardIteratorResult = streams.getShardIterator(getShardIteratorRequest);
			String nextItr = getShardIteratorResult.getShardIterator();
			
			while (nextItr != null) {
				GetRecordsResult getRecordsResult = 
						streams.getRecords(new GetRecordsRequest().withShardIterator(nextItr));
				List<Record> records = getRecordsResult.getRecords();
				for (Record record : records) {
					Map<String, AttributeValue> image = record.getDynamodb().getNewImage();
					DataPoint point = new DataPoint(
							image.get("TweetId").getS(),
							image.get("Text").getS(),
							Double.valueOf(image.get("Latitude").getN()),
							Double.valueOf(image.get("Longitude").getN()),
							image.get("Clinton").getBOOL(),
							image.get("Sanders").getBOOL(),
							image.get("Webb").getBOOL(),
							image.get("OMalley").getBOOL(),
							image.get("Trump").getBOOL(),
							image.get("Bush").getBOOL(),
							image.get("Carson").getBOOL(),
							image.get("Fiorina").getBOOL());
					ObjectWriter writer = new ObjectMapper().writer();
					try {
						String json = writer.writeValueAsString(point);
						System.out.println("Sending new item: " + json);
						SessionHandler.sendToAllConnectedSessions(json);
					} catch (JsonProcessingException ex) {
						System.out.println("Json was wrong.");
					}
				}
				nextItr = getRecordsResult.getNextShardIterator();
			}
		}
	}

}
