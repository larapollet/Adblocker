import java.lang.Object;
import java.net.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.io.*;
//mainly use the Socket package, no third partys package or the HTTPURLconnection package
//Should support HTTP/1.1 (dus schrijf bij message to server dat het deze versie is)
public class HTTP_Client {
	
	
	private static final String absolutePath = System.getProperty("user.dir") + "/Files/";
	
	/**
	 * Http client that makes an connection with the server and sends it request.
	 * The respons from the server is printed AND stored in a HTML FILE (TODO).
	 * The connection is closed after the request is finished.
	 * @param argv = ["HTTPCommand", "URI" , "port", HTTPVERSION]
	 * @throws Exception
	 */
	 public static void main(String argv[]) throws Exception{  //argv[] = ["HTTPCommand", "URI" , "port", HTTPVERSION]
		 
	//	 File Clientfile = CreateFile(absolutePath);
		 
		 String command = argv[0];
		 String unedited_uri = argv[1];
		 int port = Integer.parseInt(argv[2]); //Default port = 80

		 if (unedited_uri.startsWith("http://") == false)
			 unedited_uri = "http://" + unedited_uri;
		 
		 URI uri = new URI(unedited_uri);
		 
		 String Host =  uri.getHost();
		 String path = (uri.getPath() + "/");
		 
		 /**
		  * Initialise connection, outputstream to server and inputstream from server.
		  */
		 Socket clientSocket = new Socket(Host, port);
		 DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		 BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		 
		 /**
		  * determine what to send to server and send it
		  */
		 String request = commandFromClient(command, path, Host) + "\n";
         outToServer.writeBytes(request); //chose: writeBytes, writeChars.
         /**
          * Read the response from the server, print it AND STORE IT IN A HTML FILE (TODO)
          */
         String inputLine;
		 while ((inputLine = inFromServer.readLine()) != null) {
             System.out.println(inputLine);
		 	 //writeFile(inputLine, Clientfile);
             }
		 
		 //close stream?
		 clientSocket.close(); //Always close your socket.
	}
	 
	 

	 
	 
	 
	 
	 
	 
	 
	 
	 /**
	  * Returns the string-request that will be sent to the server, based on the given command, path and host.
	  * @param command
	  * @param path
	  * @param host
	  * @return the request from the client to the server.
	  */
	private static String commandFromClient(String command, String path, String host) {
		String commandToServer = new String();
		switch (command) {
			case "GET":
				commandToServer = "GET " + path  + " HTTP/1.1\nHost: " + host + "\nContent-lenght: 0";
				return commandToServer;
			case "POST" :
				System.out.println("Enter data for POST request here. End the request with a dubbel enter:");
				String input_post = getUserInput();
				commandToServer = "POST " + path + " HTTP/1.1\nHost: "+ host + "\nContent-lenght: " + input_post.length() + "\n" + input_post;
				return commandToServer;
			case "PUT":
				System.out.println("Enter data for PUT request here. End the request with a tripple enter:");
				String input_put = getUserInput();
				commandToServer = "PUT " + path + " HTTP/1.1\nHost: "+ host + "\nContent-lenght: " + input_put.length() + "\n" + input_put;
				return commandToServer;
			case "HEAD" : 
				commandToServer = "HEAD " + path + " HTTP/1.1\nHost: " + host + "\nContent-lenght: 0" ;
				return commandToServer;
			default:
				return commandToServer;}
	}
	
//	public static File CreateFile(String absolutepath) { //is this a correct use to make a new file?
//		File htmlFile = new File(absolutePath);
//		if (!htmlFile.exists()) {
//			htmlFile.mkdir();
//		}
//		return htmlFile;}
//	
//	public static void writeFile(String content, File clientfile) throws IOException {
//      FileOutputStream fos = new FileOutputStream(clientfile);
//      DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
//      outStream.writeUTF(content); 
//      outStream.close();	
//	}
	
	/**
	 * Function to scan the strings written by the user. (Used for input prompts PUT and POST)
	 * @return The input that was written by the user.
	 */
	private static String getUserInput() {
	Scanner userInput = new Scanner(System.in);
    String content = userInput.nextLine();
    while (true) {
        String newLine = userInput.nextLine();
        content = content.concat("\n"+newLine);
        if (content.endsWith("\n\n")){
            content = content.substring(0,content.length()-2);
            userInput.close();
            return content;
            	
        }
     }}}
	
//	public static void EmbeddedObjects(){	
//	}
//Scan local html file. Retrieve embedded images. store them locally as well (GET).
//retrieve embedded_objects: search the html file for <img retrieve from the src between the brackets.
//USE A PARSER, BUT NOT AN API.
//	         if (embedded_object == not_done) {
//	        	 	Retrieve(embedded_object);
//	        	 	Store(retrieved_embedded_object);
//	         }
//	       
//	/**
//	 * Function to close the socket of the client. Set the socket of the client to null.
//	 */
//	private void close(Socket clientsocket) {
//		try {
//			clientsocket.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//TODO: third part of the assignment = filter out the ads. 

/**
 * Unused code that is just written differently in this file:
 */

//outToServer.writeBytes(commandFromClient(argv, path));
//outToServer.writeBytes("Host: " + Host);

//StringBuilder responseBuffer = new StringBuilder();
//responseBuffer.append(inFromServer.readLine());
//System.out.println(responseBuffer);

