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
	
	
	 public static void main(String argv[]) throws Exception{  //argv[] = ["HTTPCommand", "URI" , "port", HTTPVERSION]
		 
		 File Clientfile = CreateFile(absolutePath);
		 
		 String unedited_uri = argv[1];
		 
		 if (unedited_uri.startsWith("http://") == false)
			 unedited_uri = "http://" + unedited_uri;
		 
		 URI uri = new URI(unedited_uri);
		 
		 String Host =  uri.getHost(); //Seperating Host and Path is used in HTTP/1.1
		 String path = (uri.getPath() + "/");
		 
		 int port = Integer.parseInt(argv[2]); //Default port = 80
		 
		 Socket clientSocket = new Socket(Host, port);
		 
		 DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		 BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		 String request = commandFromClient(argv, path, Host) + "\n";
         outToServer.writeBytes(request); //chose: writeBytes, writeChars.
         
//         StringBuilder responseBuffer = new StringBuilder();
//         responseBuffer.append(inFromServer.readLine());
//         System.out.println(responseBuffer);

         String inputLine;
		 while ((inputLine = inFromServer.readLine()) != null) {
             System.out.println(inputLine);
		 	 //writeFile(inputLine, Clientfile);
             }
		 
		 //close stream?
		 clientSocket.close(); //Always close your socket.
	}

	private static String commandFromClient(String argv[], String path, String host) {
		String command = argv[0];
		String commandToServer = new String();
		switch (command) {
			case "GET":
				commandToServer = "GET " + path  + " HTTP/1.1\nHost:" + host ;
				return commandToServer;
			case "POST" :
				System.out.println("Enter data for POST request here. End the request with a dubbel enter:");
				commandToServer = "POST " + path + " HTTP/1.1\nHost: "+ host + "\n" + getUserInput();
				return commandToServer;
			case "PUT":
				System.out.println("Enter data for PUT request here. End the request with a tripple enter:");
				commandToServer = "PUT " + path + " HTTP/1.1\nHost: " + host + "\n" + getUserInput();
				return commandToServer;
			case "HEAD" : 
				commandToServer = "HEAD " + path + " HTTP/1.1\nHost:" + host ;
				return commandToServer;
			default:
				return commandToServer;}
	}
	
	public static File CreateFile(String absolutepath) { //is this a correct use to make a new file?
		File htmlFile = new File(absolutePath);
		if (!htmlFile.exists()) {
			htmlFile.mkdir();
		}
		return htmlFile;}
	
	public static void writeFile(String content, File clientfile) throws IOException {
      FileOutputStream fos = new FileOutputStream(clientfile);
      DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
      outStream.writeUTF(content); 
      outStream.close();	
	}
	
	private static String getUserInput() {
	Scanner userInput = new Scanner(System.in);
    String content = userInput.nextLine();
    while (true) {
        String newLine = userInput.nextLine();
        content = content.concat("\n"+newLine);
        if (content.endsWith("\n\n")){
            content = content.substring(0,content.length()-2);
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
	//TODO: third part of the assignment = filter out the ads. (How do you know wheter an image is an ad?)

//outToServer.writeBytes(commandFromClient(argv, path));
//outToServer.writeBytes("Host: " + Host);


