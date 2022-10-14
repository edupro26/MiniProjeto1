import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MyHttpServer {
	
	public static void main(String[] args) throws IOException {
		if(args.length != 1) {
			System.err.println("Usage: java EchoServer <port number>");
			System.exit(1);
		}
		int portNumber = Integer.parseInt(args[0]);
		try (ServerSocket server = new ServerSocket(portNumber)) {
			System.out.println("Listening for request on port: " + portNumber + " ..." + "\r\n");
			while (true) {
				Socket socket = server.accept();
				System.out.println("Client connected.\r\n");
				
				//TODO implementar as threads
				// Alinea f.
				
				processRequest(socket);
			}
		} catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
	}

	private static void processRequest(Socket socket) throws IOException {
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String request;
		while ((request = in.readLine()) != null) {
			System.out.println(request);
			if(!request.equals("")) {
				sendAnswer(out, request);							
			}
			
			//TODO valiadateRequest();
			// Alineas c. d.
		}
	}

	private static void sendAnswer(PrintWriter out, String request) throws FileNotFoundException {
		String[] slipt = request.split(" ");
		String page = slipt[1].substring(1, slipt[1].length());
		if(page.equals("index.html")) {
			String textContent = getObjContent(page);
			out.println("HTTP/1.1 200 OK" + textContent);
		}				
		else {
			out.println("HTTP/1.1 404 Not Found\r\n");
		}
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
