package hw1;

public class DataPoint {
	private String TweetId;
	private String Text;
	private double Latitude;
	private double Longitude;
	private boolean Clinton;
	private boolean Sanders;
	private boolean Webb;
	private boolean OMalley;
	private boolean Trump;
	private boolean Bush;
	private boolean Carson;
	private boolean Fiorina;
	
	
	public DataPoint(String tweetId, String text, double latitude, double longitude, boolean clinton, boolean sanders,
			boolean webb, boolean oMalley, boolean trump, boolean bush, boolean carson, boolean fiorina) {
		TweetId = tweetId;
		Text = text;
		Latitude = latitude;
		Longitude = longitude;
		Clinton = clinton;
		Sanders = sanders;
		Webb = webb;
		OMalley = oMalley;
		Trump = trump;
		Bush = bush;
		Carson = carson;
		Fiorina = fiorina;
	}
	public String getTweetId() {
		return TweetId;
	}
	public void setTweetId(String tweetId) {
		TweetId = tweetId;
	}
	public String getText() {
		return Text;
	}
	public void setText(String text) {
		Text = text;
	}
	public double getLatitude() {
		return Latitude;
	}
	public void setLatitude(double latitude) {
		Latitude = latitude;
	}
	public double getLongitude() {
		return Longitude;
	}
	public void setLongitude(double longitude) {
		Longitude = longitude;
	}
	public boolean isClinton() {
		return Clinton;
	}
	public void setClinton(boolean clinton) {
		Clinton = clinton;
	}
	public boolean isSanders() {
		return Sanders;
	}
	public void setSanders(boolean sanders) {
		Sanders = sanders;
	}
	public boolean isWebb() {
		return Webb;
	}
	public void setWebb(boolean webb) {
		Webb = webb;
	}
	public boolean isOMalley() {
		return OMalley;
	}
	public void setOMalley(boolean oMalley) {
		OMalley = oMalley;
	}
	public boolean isTrump() {
		return Trump;
	}
	public void setTrump(boolean trump) {
		Trump = trump;
	}
	public boolean isBush() {
		return Bush;
	}
	public void setBush(boolean bush) {
		Bush = bush;
	}
	public boolean isCarson() {
		return Carson;
	}
	public void setCarson(boolean carson) {
		Carson = carson;
	}
	public boolean isFiorina() {
		return Fiorina;
	}
	public void setFiorina(boolean fiorina) {
		Fiorina = fiorina;
	}
}
