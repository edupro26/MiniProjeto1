import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MyHttpServer {
	
	private static final String EOL = System.getProperty("line.separator");
	
	public static void main(String[] args) throws IOException {
		if(args.length != 1) {
			System.err.println("Usage: java EchoServer <port number>");
			System.exit(1);
		}
		int portNumber = Integer.parseInt(args[0]);
		try (ServerSocket server = new ServerSocket(portNumber)) {
			System.out.println("Listening for request on port: " + portNumber + " ..." + EOL);
			while (true) {
				Socket socket = server.accept();
				System.out.println("Client connected." + EOL);
				
				//TODO implementar as threads
				// Alinea f.
				
				processRequest(socket);
			}
		} catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber + EOL);
            System.exit(-1);
        }
	}

	public static void processRequest(Socket socket) throws IOException {
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String request;
		while ((request = in.readLine()) != null) {
			System.out.println(request);
			if(!request.equals("")) {
				String[] slipt = request.split(" ");				
				String page = slipt[1].substring(1, slipt[1].length());
				String answer = getAnswer(slipt, page);
				out.println(answer);										
			}
		}
	}
	
	private static String getAnswer(String[] request, String objContent) throws FileNotFoundException {
		if(!valiadateFormat(request))
			return "HTTP/1.1 400 Bad Request" + EOL;
		
		if(request[0].equals("GET")) {
			if(objContent.equals("index.html")) {
				String textContent = getObjContent(objContent);
				return "HTTP/1.1 200 OK" + textContent;
			}
			else {
				return "HTTP/1.1 404 Not Found" + EOL;
			}
		}
		if(request[0].equals("POST")) {
			//TODO implementar POST
		}
		
		return "HTTP/1.1 501 Not Implemented" + EOL;
	}
	
	private static boolean valiadateFormat(String[] request) {
		if(request.length != 3) {
			return false;
		}
		if(!request[2].contains("\\r\\n") || !request[2].contains("HTTP/1.1")) {
			return false;
		}
		
		return true;
	}
	
	private static String getObjContent(String ObjectName) throws FileNotFoundException {
		StringBuilder content = new StringBuilder();
        FileReader fReader = new FileReader(ObjectName);
        String result;
        try {
            BufferedReader bReader = new BufferedReader(fReader);
            String value;
            while ((value = bReader.readLine()) != null) {
                content.append(value);
            }
            result = content.toString();
            bReader.close();
        } catch (Exception e) {
        	result = null;
        }
		return result;
	}
}
