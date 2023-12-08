// Include below 4 references in your program;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

// Include below code in your program

class MessageApi
{
	public static void main(String args[]) throws IOException
	{

final String POST_PARAMS = "{\"ApiKey\":\"INSERT_YOUR_JUVLON_ACCOUNT_API_KEY\"" +
 
             ",\"requests\":[{\"subject\":\"Hello\"" +
                       ",\"from\":\"support@juvlon.com\"" +
 
                       ",\"body\":\"This is an API test from Juvlon\"" + 
                       ",\"to\":\"sales@nichelive.com\"}]}";

System.out.println(POST_PARAMS);
URL obj = null;
try {
	obj = new URL("https://api2.juvlon.com/v4/httpSendMail");
} catch (MalformedURLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
try {
	postConnection.setRequestMethod("POST");
} catch (ProtocolException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

postConnection.setRequestProperty("Content-Type", "application/json");
postConnection.setDoOutput(true);
OutputStream os = postConnection.getOutputStream();
os.write(POST_PARAMS.getBytes());
os.flush();
os.close();
int responseCode = postConnection.getResponseCode();
System.out.println(responseCode);
}
}