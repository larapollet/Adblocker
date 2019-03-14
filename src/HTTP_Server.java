import java.net.Socket;
import java.awt.List;
import java.io.*;
import java.net.*;

public class HTTP_Server {
	
	//SERVER SHOULD BE MULTITHREADED!! 
	//how to add third party client?? 
	 public static void main(String argv[]) throws Exception
	 {
	 ServerSocket welcomeSocket = new ServerSocket(8000);
	 while(true)
	 {
	 Socket connectionSocket = welcomeSocket.accept();
	 BufferedReader inFromClient = new BufferedReader(new InputStreamReader (connectionSocket.getInputStream()));
	 DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
	 String clientCommand = inFromClient.readLine();
	 String clientHost = inFromClient.readLine();
	 System.out.println("Received: " + clientCommand);
	 System.out.println("Received2: " + clientHost);
	 String serverarg[] = clientCommand.split("\\s+");
	 String Hostarg[] = clientHost.split("\\s+");
	 String responseFromServer = executeCommand(serverarg[0], serverarg[1], Hostarg[1]);
	 System.out.println(executeCommand(serverarg[0], serverarg[1],Hostarg[1]));
	 //String capsSentence = clientSentence.toUpperCase() + '\n';
	 outToClient.writeBytes(responseFromServer);
	 
	 }}

	private static String executeCommand (String command, String path, String Host) { //serverarg = command , path, HTTP/1.1
		//400 bad request: when host header not added.
		String responseFromServer = new String();
		switch(command) {
		   case "GET" :
			   //If-modified-since-header needed.
			   //als server niet responds: 404 not found?
			   //content lenght or chunked headers!
			   //VERWERKEN VAN HET GET COMMAND?
			   //EMBEDDED IMAGES WEG KRIJGEN IN GET COMMAND
			   //content-lenght or transfer encoding.
			   //als je wilt weten of je request gedaan is: content length is niet altijd even accuraat. Check de laatste aantal bytes.
			    URL url;
			    InputStream is = null;
			    BufferedReader buffer;
			    String line;
			
			    try {
			        url = new URL(Host + path);
			        is = url.openStream();  // throws an IOException
			        buffer = new BufferedReader(new InputStreamReader(is));
			        responseFromServer = "HTTP/1.1 200 OK \n";
			        System.out.println("oeps");
			        while ((line = buffer.readLine()) != null) {
			            responseFromServer += line + "\n";
			        }
			    } catch (MalformedURLException mue) {
			    		responseFromServer = "HTTP/1.1 404 Not Found/Bad Request \n";
			    } catch (IOException ioe) {
			         responseFromServer = "HTTP/1.1 404 Not Found \n"; //Is dit de juiste error code?
			    } 
			   return responseFromServer;
		   
		   case "POST" :
			   //For the POST command, the user input should be appended to an existing file on the server.
			   //If the file does not exist, then the file should be created.
			   //For PUT and POST commands, your user should read a string from an interactive command prompt and send that onwards. 
			   //These two commands will be tested with your HTTP server program.
			   //user input should be appended to an existing file on the server.
			   responseFromServer = "HTTP/1.1 200 OK \n";
			   return responseFromServer;
		      
		   case "PUT" :
			   //user input stored in new text file on the server. (same directory)
			   responseFromServer = "HTTP/1.1 200 OK \n";
			   return responseFromServer;

		   case "HEAD" :
			   responseFromServer = "";
			   return responseFromServer;
			  
		   default : 
			   String responseFromServerDefault = "HTTP/1.1 400 Bad Request";
			   return responseFromServerDefault;
		}
	}}
//https://stackoverflow.com/questions/238547/how-do-you-programmatically-download-a-webpage-in-java
//Also shows how to catch an exception here. -> 400 bad request?