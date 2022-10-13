import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MyHttpServer {
	
	private static String getRequestObj(String ObjectName) throws FileNotFoundException {
		StringBuilder content = new StringBuilder();
        FileReader fReader = new FileReader(ObjectName);
        String result = null;
        try {
            BufferedReader bReader = new BufferedReader(fReader);
            String value;
            while ((value = bReader.readLine()) != null) {
                content.append(value);
            }
            result = content.toString();
            bReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		return result;
	}
	
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
				System.out.println("Client connect.\r\n");		
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String request;
				while ((request = in.readLine()) != null) {
					System.out.println(request);
					
					//TODO imprimir conteudo do index.html
					
					String answer = "HTTP/1.1 200 OK\r\n";
					out.println(answer);
				}
			}
		} catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
		
	}
	
}
