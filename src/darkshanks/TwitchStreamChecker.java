package darkshanks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TwitchStreamChecker {
private static URL url;
private static int streamStatus = 0;

public static void main(String args[]) {
	while(true) {
	// Attempt a GET request to the URL
	try {
		url = new URL("https://api.twitch.tv/kraken/streams/xxxx?client_id=yyyy"); // Replace xxxx with streamer name and yyyy with your client id
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		int status = con.getResponseCode();
		if(status == 200) {
			BufferedReader in = new BufferedReader(
					  new InputStreamReader(con.getInputStream()));
					String inputLine;
					StringBuffer content = new StringBuffer();
					while ((inputLine = in.readLine()) != null) {
					    content.append(inputLine);
					}
					String input = content.toString();
					if(input.contains("\"stream\":null")) {
						System.out.println("Streamer is currently offline!");
						streamStatus = 0;
						 try
					        {
					            Thread.sleep(60000); // Wait for 1 minute before checking
					        }
					        catch(InterruptedException ie){
					            ie.printStackTrace();
					        }
					}
					else {
						if(streamStatus == 0) {
							System.out.println("Streamer is currently online!");
							streamStatus = 1;
							try {
								Thread.sleep(1800000); // Wait for 30 minutes before checking again
							} catch (InterruptedException e) {
								e.printStackTrace();
							} 
						}
					}
					in.close();
		}
		else {
			System.out.println("An error occurred!");
			System.exit(0);
		}
		con.disconnect();
	} catch (MalformedURLException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	}
}
}
