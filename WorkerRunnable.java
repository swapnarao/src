
/* refered to http://tutorials.jenkov.com/java-multithreaded-servers/thread-pooled-server.html for the WorkerRunnable code
 and added a simple functionality to the worker thread. The idea was to make the worker thread convert the data that comes 
 from the url to upper or lower string based on the action requested.*/

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;


public class WorkerRunnable implements Runnable{

    protected Socket clientSocket = null;
    protected String serverText   = null;

    public WorkerRunnable(Socket clientSocket, String serverText) {
        this.clientSocket = clientSocket;
        this.serverText   = serverText;
    }
 
    
    public void run() {
        try {
            InputStream input  = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            BufferedReader in = new BufferedReader(
    		        new InputStreamReader(clientSocket.getInputStream()));
    		String inputLine;
    	    // added functionality to convert data from url to upper or lower case based on request
    		inputLine = in.readLine();
    		StringTokenizer st = new StringTokenizer(inputLine);  
    		st.nextToken();
    		String data = st.nextToken().trim();
    		
    		String d = data.substring(data.indexOf("?")+1 );
    	    // get the action(toUpperCase or toLowerCase)
    		String action = d.substring(0,d.indexOf("?") );
    	     // get the data
    		String strtoconvert = d.substring(data.lastIndexOf("=")-1 );
    	
    		String result= null;
    		// convert data to upper or lower case accordingly
    		if(action.equals("toUpperCase")){
    			 result = strtoconvert.toUpperCase();
    		}
    		else if(action.equals("toLowerCase")){
    			 result = strtoconvert.toLowerCase();
    		}
    		// display the converted string
            output.write(("HTTP/1.1 200 OK\n\nWorkerRunnable: " +this.serverText + " "+result+"").getBytes());
            output.close();
            System.out.println("Request processed: ");
        } catch (IOException e) {
           
            e.printStackTrace();
        }
    }
}
