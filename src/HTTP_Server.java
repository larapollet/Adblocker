import java.awt.List;
import java.io.*;
import java.net.*;
import java.util.Scanner;

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
	 // changing string type to uri then to url
	 URI uri = URI.create(serverarg[1]);
	 URL url = uri.toURL();;
	 String responseFromServers = executeCommand(serverarg[0], serverarg[1], Hostarg[1]);
	 System.out.println("Received3: " + responseFromServers);
	 //System.out.println(executeCommand(serverarg[0], serverarg[1],Hostarg[1]));
	 //String capsSentence = clientSentence.toUpperCase() + '\n';
	 outToClient.writeBytes(responseFromServers);
	 
	 }}
	 
	 private static boolean Transfer_Encoding(String responseFromServer) {
		 if(responseFromServer.contains("chunked")) {
			 return true;
		 }
		return false;
	 }
	 
	private static String executeCommand (String command, String path, String Host) throws IOException { //serverarg = command , path, HTTP/1.1
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
			   

//			   URL url = URI.create(path).toURL();
//			   InputStream stream = url.openConnection().getInputStream();
			   
			   //check if Transfer-Encoding: chunked in header
			   if (Transfer_Encoding(executeCommand("HEAD", path, Host))) {
				   // do chunked
			   }
			   else {
				   // do Content-length
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
			   InputStreamReader in = new InputStreamReader(System.in);
	           BufferedReader br = new BufferedReader(in);
	           boolean a =true ;
	           while(a == true) {
	        	   String line = br.readLine();
	        	   responseFromServer+= "\n" + line;
	        	   if (line == "\n") {
	        		   a= false;
	        	   }
	           }
			   return responseFromServer;
			  
		   default : 
			   String responseFromServerDefault = "HTTP/1.1 400 Bad Request";
			   return responseFromServerDefault;
		}
	}}


//https://stackoverflow.com/questions/238547/how-do-you-programmatically-download-a-webpage-in-java
//Also shows how to catch an exception here. -> 400 bad request?