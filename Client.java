import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
public class Client implements Runnable {
	
	public void run(){
		try{
			sendGet();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	public void sendGet() throws Exception {

		String url = " http://localhost:9000?toUpperCase?string=inputString";
		//String url = " http://localhost:9000?toLowerCase?string=inputString";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());
		}
	
	public static void main(String[] args) {
		Client c = new Client();
		Thread t = new Thread(c);
		t.start();
		System.out.println("start");
	}
}
