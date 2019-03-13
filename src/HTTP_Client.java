import java.lang.Object;
import java.net.*;
import java.net.InetAddress;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.*;
//mainly use the Socket package, no third partys package or the HTTPURLconnection package
//Should support HTTP/1.1 (dus schrijf bij message to server dat het deze versie is)
public class HTTP_Client {
	 public static void main(String argv[]) throws Exception{  //argv[] = ["HTTPCommand", "URI" , "port", HTTPVERSION] -> http/1.1 needs seperated host and path (for now: always host and path)
		 String Http = argv[1];
		 int port = Integer.parseInt(argv[2]); //Default port = 80
		 //InetAddress httpAddress = InetAddress.getByName(Http); //is deze wel correct?
		 URL url = new URL(argv[1]);
		 
		 String Host =  url.getHost(); //Seperating Host and Path is used in HTTP/1.1
		 String path = (url.getPath() + "/");
		 
		 Socket clientSocket = new Socket(Host ,port);
		 DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		 BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		 System.out.println(Host);
		 System.out.println(path);
		 System.out.println("inputstream: " + clientSocket.getInputStream());
		 
		 //CreateFile(Http); //TODO: ik denk niet dat dit correct gemaakt is.
         outToServer.writeBytes(commandFromClient(argv, path)); //chose: writeBytes, writeChars.
         outToServer.writeBytes("Host: " + Host);
         String inputLine;
		 while ((inputLine = inFromServer.readLine()) != null) {
             System.out.println(inputLine);
             //WriteToFile(inputLine, ??); //write to file, store response in an HTML file locally
     }

		 
//Scan local html file. Retrieve embedded images. store them locally as well (GET).
//retrieve embedded_objects: search the html file for <img retrieve from the src between the brackets.
//USE A PARSER, BUT NOT AN API.
//         if (embedded_object == not_done) {
//        	 	Retrieve(embedded_object);
//        	 	Store(retrieved_embedded_object);
//         }
//         
//TODO: third part of the assignment = filter out the ads. (How do you know wheter an image is an ad?)
		 
		 //close stream?
		 clientSocket.close(); //Always close your socket.
	}

	private static String commandFromClient(String argv[], String path) {
		String command = argv[0];
		String commandToServer = new String();
		switch (command) {
			case "GET":
				commandToServer = "GET " + path  + " HTTP/1.1";
				System.out.println(commandToServer);
				return commandToServer;
			case "POST" : 
				commandToServer = "POST " + path + " HTTP/1.1";
				return commandToServer;
			case "PUT":
				commandToServer = "PUT " + path + " HTTP/1.1";
				//there should pop up an input prompt
				// -> first signal to server and then signal from server for input prompt?
				// -> direct input prompt en then signal to server?
				return commandToServer;
			case "HEAD" : 
				commandToServer = "HEAD " + path + " HTTP/1.1" ;
				return commandToServer;
			default:
				return commandToServer;}
	}

	public static void CreateFile(String httpname) { //is this a correct use to make a new file?
		File htmlFile = new File("path/store/" + httpname +".html");
		//return file, en geef deze mee aan de client. steeds aan deze file toevoegen dan?
	}
	
	public static void WriteToFile(String inputLine, String httpname) throws IOException {
        FileOutputStream fos = new FileOutputStream("path/store/" + httpname +".html");
        DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
        outStream.writeUTF(inputLine); 
        outStream.close();
	}
	
	
	public static void EmbeddedObjects(){
		
	}
}

