import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * HTTP Server Class
 * 
 * @author Eduardo Proença fc57551
 * @author Marcelo Munteanu fc56359
 * @author Pedro Duque fc52753
 * 
 */
public class MyHttpServer {
	
	private static final String EOL = System.getProperty("line.separator");	

	/**
	 * Main method that runs the server
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		if(args.length != 1) {
			System.err.println("Usage: java EchoServer <port number>");
			System.exit(1);
		}
		
		int portNumber = Integer.parseInt(args[0]);
		try (ServerSocket server = new ServerSocket(portNumber , 5)) {
			System.out.println("Listening for request on port: " + portNumber + " ..." + EOL);
			while (true) {
				Socket socket = server.accept();
				System.out.println("Client connected." + EOL);
				new ClientHandler(socket).start();
			}		
		} catch (IOException e) {
			System.err.println("Could not listen on port " + portNumber + EOL);
      		System.exit(-1);
		}
	}
	
}
