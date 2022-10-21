import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * ClientHandler Class to run threads
 * 
 * @author Eduardo Proença fc57551
 * @author Marcelo Munteanu fc56359
 * @author Pedro Duque fc52753
 *
 */
public class ClientHandler extends Thread{
	
	private static final String EOL = System.getProperty("line.separator");
	private Socket socket;
	
	/**
	 * Accepts the communication socket and creates
	 * a new ClientHandler for this thread
	 * 
	 * @param socket
	 */
	public ClientHandler(Socket socket) {
		this.socket = socket;
	}

	/**
	 * Runs the thread
	 */
	public void run() {
		try {
			processRequest(socket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads the client's request and responds with the appropriate answer
	 * 
	 * @param socket our communication socket
	 * @throws IOException
	 */
	public static void processRequest(Socket socket) throws IOException {
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String s;
		StringBuilder sb = new StringBuilder();
		while ((s = in.readLine()) != null) {
			if(s.isEmpty()) {
				System.out.println(sb.toString());
				String[] request = sb.toString().split(EOL);
				String answer = getAnswer(request[0].split(" "));
				out.println(answer);
				sb = new StringBuilder();
			}
			else {
				sb.append(s);
				sb.append(EOL);
			}		
		}
	}
	
	/**
	 * Returns the appropriate answer for the client's request
	 * 
	 * @param request the client's request
	 * @return the appropriate answer for the client's request
	 * @throws FileNotFoundException
	 */
	private static String getAnswer(String[] request) throws FileNotFoundException {
		if(!valiadateFormat(request))
			return "HTTP/1.1 400 Bad Request\\r\\n" + EOL;
		
		if(request[0].equals("GET")) {
			String page = request[1].substring(1, request[1].length());
			if(page.equals("index.html")) {
				String textContent = getObjContent(page);
				return "HTTP/1.1 200 OK\\r\\n" + textContent;
			}
			else {
				return "HTTP/1.1 404 Not Found\\r\\n" + EOL;
			}
		}
		if(request[0].equals("POST")) {
			return "HTTP/1.1 200 OK\\r\\n" + EOL;
		}
		
		return "HTTP/1.1 501 Not Implemented\\r\\n" + EOL;
	}
	
	/**
	 * Verifies if there is any format error in the client's request
	 * 
	 * @param request the client's request
	 * @return false if there is a format error in the client's
	 * 			request, true otherwise
	 */
	private static boolean valiadateFormat(String[] request) {
		if(request.length != 3) {
			return false;
		}
		if(!request[2].contains("\\r\\n") || !request[2].contains("HTTP/1.1")) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Returns the text content of the file represent by the string ObjectName
	 * 
	 * @param ObjectName of the file
	 * @return the text content of the file represent by the string ObjectName,
	 * 			null if the file could not be read
	 * @throws FileNotFoundException
	 */
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
