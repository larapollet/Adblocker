import java.lang.Object;
import java.net.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.*;
//mainly use the Socket package, no third partys package or the HTTPURLconnection package
//Should support HTTP/1.1 (dus schrijf bij message to server dat het deze versie is)
public class HTTP_Client {
	 public static void main(String argv[]) throws Exception{  //argv[] = ["HTTPCommand", "URI" , "port", HTTPVERSION]
		 String unedited_uri = argv[1];
		 if (unedited_uri.startsWith("http://") == false)
			 unedited_uri = "http://" + unedited_uri;
		 
		 URI uri = new URI(unedited_uri);
		 
		 String Host =  uri.getHost(); //Seperating Host and Path is used in HTTP/1.1
		 String path = (uri.getPath() + "/");
		 
		 int port = Integer.parseInt(argv[2]); //Default port = 80
		 
		 // Make a connection given a certain Host and a certain port.
		 Socket clientSocket = new Socket(Host, port);
		 System.out.println(Host);
		 
		 DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		 BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		 String request = commandFromClient(argv, path) + System.lineSeparator() + "Host: " + Host;
         outToServer.writeBytes(request); //chose: writeBytes, writeChars.
         
//         outToServer.writeBytes(commandFromClient(argv, path));
//         outToServer.writeBytes("Host: " + Host);$
        
//         StringBuilder responseBuffer = new StringBuilder();
//         System.out.println(outToServer);
//         responseBuffer.append( inFromServer.readLine());
//         System.out.println(responseBuffer);
//CreateFile(Http); //TODO: ik denk niet dat dit correct gemaakt is.
         
         String inputLine;
		 while ((inputLine = inFromServer.readLine()) != null) {
			System.out.println("lol");
             System.out.println(inputLine);
             //WriteToFile(inputLine, ??); //write to file, store response in an HTML file locally
             }

		 
		 //close stream?
		 clientSocket.close(); //Always close your socket.
	}

	private static String commandFromClient(String argv[], String path) {
		String command = argv[0];
		String commandToServer = new String();
		switch (command) {
			case "GET":
				commandToServer = "GET " + path  + " HTTP/1.1" ;
				System.out.println(commandToServer);
				return commandToServer;
			case "POST" : 
				commandToServer = "POST " + path + " HTTP/1.1";
				return commandToServer;
			case "PUT":
				commandToServer = "PUT " + path + " HTTP/1.1";
				return commandToServer;
			case "HEAD" : 
				commandToServer = "HEAD " + path + " HTTP/1.1" ;
				return commandToServer;
			default:
				return commandToServer;}
	}
//
//	public static void CreateFile(String httpname) { //is this a correct use to make a new file?
//		File htmlFile = new File("path/store/" + httpname +".html");
//		//return file, en geef deze mee aan de client. steeds aan deze file toevoegen dan?
//	}
//	
//	public static void WriteToFile(String inputLine, String httpname) throws IOException {
//        FileOutputStream fos = new FileOutputStream("path/store/" + httpname +".html");
//        DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
//        outStream.writeUTF(inputLine); 
//        outStream.close();
//	}
//	
//	
//	public static void EmbeddedObjects(){
//		
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
}

