import java.awt.List;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
	 DataInputStream inFromClientcont = new DataInputStream(connectionSocket.getInputStream());
	 DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
	 String clientCommand = inFromClient.readLine();
	 String clientHost = inFromClient.readLine();
	 String clientContentLenght = inFromClient.readLine();
//	 String clientContentLenght1 = inFromClient.readLine();
//	 String clientContentLenght2 = inFromClient.readLine();
//	 String clientContentLenght3 = inFromClient.readLine();
	 String contentlenght[] = clientContentLenght.split("\\s+");
//	 String contentlenght1[] = clientContentLenght1.split("\\s+");
//	 String contentlenght2[] = clientContentLenght2.split("\\s+");
//	 String contentlenght3[] = clientContentLenght3.split("\\s+");
	 String contentFromClient = "";
//	 System.out.println("Received3: " +clientContentLenght1);
//	 System.out.println("Received3: " +clientContentLenght2);
//	 System.out.println("Received3: " +clientContentLenght3);
	 System.out.println("Received3: " +clientContentLenght);
	 System.out.println("Received: " + clientCommand);
	 System.out.println("Received2: " + clientHost);
	 while (contentFromClient.length() < Integer.parseInt(contentlenght[1])) {
		 contentFromClient += inFromClient.readLine() + "\n";
		 System.out.println("Received2: " + contentFromClient);}
	 System.out.println("+" + contentFromClient + "+");
	 String serverarg[] = clientCommand.split("\\s+");
	 String Hostarg[] = clientHost.split("\\s+");
	 String responseFromServers = executeCommand(serverarg[0], serverarg[1], Hostarg[1], contentFromClient);
	 System.out.println("+" +executeCommand(serverarg[0], serverarg[1],Hostarg[1], contentFromClient+ "+"));
	 outToClient.writeBytes(responseFromServers);
	 // changing string type to uri then to url

	 }}
	 
	 
	 //??
	 private static boolean Transfer_Encoding(DataInputStream inputStream) {
         StringBuilder responseBuffer = new StringBuilder();
         
         // Look for relevant headers: content-length, transfer-encoding.
         for (String line : responseBuffer.toString().split("\n")) {
             if (line.toLowerCase().startsWith("content-length:")) {
            	 return false;
             }
             if (line.toLowerCase().contains("chunked")){
                 return true;
             }}
		return (Boolean) null;} // throw exception ?
         
	 /**
	  * Read body of content-length message from inputstream.
	  * @param inputStream
	  * @return
	 * @throws IOException 
	  */
	 
//	 private static  read_message(DataInputStream inputStream) {
//		 int length = 0;
//         //boolean chunked = false;
//         StringBuilder responseBuffer = new StringBuilder();
//         
//         // Look for relevant headers: content-length, transfer-encoding.
//         for (String line : responseBuffer.toString().split("\n")) {
//             if (line.toLowerCase().startsWith("content-length:")) {
//                 String[] lineParts = line.split(":");
//                 length = Integer.parseInt(lineParts[1]);
//             }
//             if (line.toLowerCase().contains("chunked")){
//                 chunked = true;
//             }
//             String[0] lst = ;
//             
//         }
//		return null;
//		 
//	 }
	 
	 
	 /**
	  * Read body of chunked message from inputstream.
	  * @param inputStream
	  * @return
	 * @throws IOException 
	  */
	 private static byte[] read_chunks(DataInputStream inputStream) throws IOException {
	        int length = -1;
	        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	        while (length != 0){
	            StringBuilder responseBuffer = new StringBuilder();

	            // Read the first line
	            while (!responseBuffer.toString().endsWith("\n")) {
	                responseBuffer.append((char) inputStream.readByte());
	            }

	            // Retrieve the chunk length
	            String[] firstline = responseBuffer.toString().split(";");
	            length = Integer.parseInt(firstline[0].replace("\n",""), 16); //only interested in first part, extra paramters ignored

	            // Read the chunk
	            buffer.write(readBody(inputStream, length));
	            if (length!=0) {
	                inputStream.readByte();
	                inputStream.readByte();
	            }
	        } // repeat same process for next chunk
	        return buffer.toByteArray();
	    }	 


	/**
	 * Read from the inputStream for a length "length" and returns it in a byte array.
	 * @throws IOException
	 */
	private static byte[] readBody(DataInputStream inputStream, int length) throws IOException {
	    int byteCount = 0;
	    byte[] bytes = new byte[length];
	    while (byteCount != length) {
	        byteCount += inputStream.read(bytes, byteCount, length - byteCount); // nb of bytes read is returned as an int 
	    }
	    return bytes;
	}

	//ArrayList myList = new ArrayList(); // used for HEAD
	private static String executeCommand (String command, String path, String Host, String inFromClient) throws IOException { //serverarg = command , path, HTTP/1.1
		//400 bad request: when host header not added.
		String responseFromServer = new String();
		String absolutePath = System.getProperty("user.dir") + "/Serverfile" + path;
		switch(command) {
		   case "GET" :
//			   //If-modified-since-header needed.
//			   //als server niet responds: 404 not found?
//			   //content lenght or chunked headers!
//			   //VERWERKEN VAN HET GET COMMAND?
//			   //EMBEDDED IMAGES WEG KRIJGEN IN GET COMMAND
//			   //content-lenght or transfer encoding.
//			   //als je wilt weten of je request gedaan is: content length is niet altijd even accuraat. Check de laatste aantal bytes.
//			   
//
////			   URL url = URI.create(path).toURL();
////			   InputStream stream = url.openConnection().getInputStream();
//			   byte[] bytes;
//			   //check if Transfer-Encoding: chunked in header
//			   if (Transfer_Encoding(executeCommand("HEAD", path, Host, inFromClient))) {
//				   // do chunked
//				   
//				   String string = new String(read_chunks(inFromClient), "UTF-8"); //certain charset to convert byte to string
//				   return string;
//			   }
//			   else {
//				   // do Content-length
//			   }
//			   
//			   
			   responseFromServer = "";
			   BufferedReader bufReader = new BufferedReader(new StringReader(inFromClient));
			   String line=null;
			   while( (line=bufReader.readLine()) != null ){
				   responseFromServer+= "\n" + line;
				   System.out.println("heeyyy" + responseFromServer);

			   }
			   System.out.println("heeyyy" + responseFromServer);
			   return responseFromServer;
		   
		/**
		 * Independent of the fact that the file already exist or not. the content will be written. Either in an existing file
		 * or in a new file with this absolute path.
		 */
		   case "POST" :
		       File postfile = new File(absolutePath);
		       try(BufferedWriter output = new BufferedWriter(new FileWriter(absolutePath, true)))
		       {output.append(inFromClient);}
			   responseFromServer = "HTTP/1.1 200 OK \n";
			   return responseFromServer;
			   
		   case "PUT" :
		       File putfile = new File(absolutePath);
		       if (putfile.createNewFile() == false) {
		    	   		Boolean create = false;
		    	   		int number = 0;
		    	   		String new_path = System.getProperty("user.dir") + "/Serverfile" + Integer.toString(number) + path;
		    	   		System.out.println(new_path);
		       		while (create == false) {
		       			new_path = System.getProperty("user.dir") + "/Serverfile" + Integer.toString(number) + path;
			       		File new_file = new File(new_path);
			       		create = new_file.createNewFile();
		       			number += 1;}
		       		try (BufferedWriter output = new BufferedWriter(new FileWriter(new_path, true)))
		       				{output.append(inFromClient);}}
		       else 
		       try (BufferedWriter output = new BufferedWriter(new FileWriter(absolutePath, true))) {output.append(inFromClient);}
			   responseFromServer = "HTTP/1.1 200 OK \n";
			   return responseFromServer;
			   
		   case "HEAD" :
			   responseFromServer = "";
			   BufferedReader bufReader1 = new BufferedReader(new StringReader(inFromClient));
			   String line1=null;
			   while( (line1=bufReader1.readLine()) != "\n" ){
				   responseFromServer+= "\n" + line1;
				   System.out.println("heeyyy" + responseFromServer);

			   }
			   System.out.println("heeyyy" + responseFromServer);
			   return responseFromServer;
			   
			   
//			   responseFromServer = "";
//			   InputStreamReader in = new InputStreamReader(System.in);
//	           BufferedReader br = new BufferedReader(in);
//	           boolean a =true ;
//	           while(a == true) {
//	        	   String line = br.readLine();
//	        	   responseFromServer+= "\n" + line;
//	        	   if (line == "\n") {
//	        		   a= false;
//	        	   }
//	           }
//	           System.out.println(responseFromServer);

			  
		   default : 
			   String responseFromServerDefault = "HTTP/1.1 400 Bad Request";
			   return responseFromServerDefault;
		}
	}}

//https://stackoverflow.com/questions/238547/how-do-you-programmatically-download-a-webpage-in-java
//Also shows how to catch an exception here. -> 400 bad request?