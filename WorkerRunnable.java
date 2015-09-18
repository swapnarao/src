
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
    	
    		String action = d.substring(0,d.indexOf("?") );
    	
    		String strtoconvert = d.substring(data.lastIndexOf("=")-1 );
    	
    		String result= null;
    		// get the action from the url and convert the data to upper or lower case accordingly
    		if(action.equals("toUpperCase")){
    			 result = strtoconvert.toUpperCase();
    		}
    		else if(action.equals("toLowerCase")){
    			 result = strtoconvert.toLowerCase();
    		}
            output.write(("HTTP/1.1 200 OK\n\nWorkerRunnable: " +this.serverText + " "+result+"").getBytes());
            output.close();
            System.out.println("Request processed: ");
        } catch (IOException e) {
           
            e.printStackTrace();
        }
    }
}
