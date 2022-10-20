import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Client HTTP class
 * 
 * @author Eduardo Proença fc57551
 * @author Marcelo Munteanu fc56359
 * @author Pedro Duque fc52753
 *
 */
public class MyHttpClient {
	
	private final String EOL = System.getProperty("line.separator");
	private String hostName;
	private int portNumber;
	private Socket socket;
	
	/**
	 * Constructor which accepts the server's name and TCP destination port number
	 * 
	 * @param hostName server's name
	 * @param portNumber TCP destination port number
	 * @throws IOException
	 */
	public MyHttpClient(String hostName, int portNumber) throws IOException {
		this.hostName = hostName;
		this.portNumber = portNumber;
		
		this.socket = new Socket(this.hostName, this.portNumber);
	}
	
	/**
	 * Sends a HTTP GET request to get the object indicated in the string ObjectName
	 * 
	 * @param ObjectName object to request to the server
	 * @throws IOException
	 */
	public void getResource(String ObjectName) throws IOException {
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        String request = "GET /" + ObjectName + " HTTP/1.1\\r\\n" + EOL
        				+ "Host: " + hostName + ":" + portNumber + "\\r\\n" + EOL;
        out.println(request);
        
        String rawAnswer = in.readLine();
        String answer = processAnswer(rawAnswer);
        System.out.println(EOL + answer + EOL);
	}

	/**
	 * Sends a HTTP POST request to a hypothetical page /simpleForm.html hosted by our 
	 * server that contains a web form with two fields, StudentName and StudentID, to fill in
	 * 
	 * @param data information to fill the two fields of the web form
	 * @throws IOException
	 */
	public void postData(String[] data) throws IOException {
		// TODO Auto-generated method stub
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        out.println(getPostRequest(data));
        
        String answer = in.readLine();
        System.out.println(EOL + answer + EOL);
	}
	
	/**
	 * Sends a HTTP request with a method name not supported by our server
	 * 
	 * @param wrongMethodName method name
	 * @throws IOException
	 */
	public void sendUnimplementedMethod(String wrongMethodName) throws IOException {
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
        String request = wrongMethodName + " /index.html HTTP/1.1\\r\\n" + EOL
        				+ "Host: " + hostName + ":" + portNumber + "\\r\\n" + EOL;
        out.println(request);
        
        String answer = in.readLine();
        System.out.println(EOL + answer + EOL)
        ;
	}

	/**
	 * Sends three different types of HTTP requests with an incorrect format. The parameter 
	 * type represents a integer used to identify the different types of format problems
	 * 
	 * @param type a integer used to identify the different types of format problems
	 * @throws IOException
	 */
	public void malformedRequest(int type) throws IOException {
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        String request;
        switch (type) {
        	case 1:
        		request = "GET /index.html HTTP/1.1" + EOL
        				+ "Host: " + hostName + ":" + portNumber + "\\r\\n" + EOL;
        		out.println(request);
        		break;
        	case 2:
        		request = "GET /index.html  HTTP/1.1\\r\\n" + EOL
        				+ "Host: " + hostName + ":" + portNumber + "\\r\\n" + EOL;
        		out.println(request);
        		break;
        	case 3:
        		request = "GET /index.html HTTP/\\r\\n" + EOL
        				+ "Host: " + hostName + ":" + portNumber + "\\r\\n" + EOL;
        		out.println(request);
        		break;
        }
        
        String answer = in.readLine();
        System.out.println(EOL + answer + EOL);
	}

	/**
	 * Closes the socket communication channel with the server.
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		this.socket.close();
	}
	
	/**
	 * Returns the answer to a HTTP GET request with a correct format
	 * 
	 * @param answer answer to a HTTP GET request
	 * @return the answer to a HTTP GET request with a correct format
	 */
	private String processAnswer(String answer) {
		String finalAnswer = new String();
		boolean b = true;
		for (int i = 0; i < answer.length(); i++) {			
			finalAnswer += answer.charAt(i);
			if(i + 2 < answer.length()) {
				if(answer.charAt(i + 1) == '<' && Character.isLetter(answer.charAt(i + 2))) {
					if(b == true) {
						finalAnswer = finalAnswer + EOL + EOL;
						b = false;
					}				
					else
						finalAnswer += EOL;
				}	
			}
		} 
		return finalAnswer;
	}
	
	/**
	 * Returns the HTTP POST request with a correct format
	 * 
	 * @param data information to fill the two fields of the web form
	 * @return the HTTP POST request with a correct format
	 */
	private String getPostRequest(String[] data) {
		data[0] = data[0].replaceFirst(" ", "");
		data[0] = data[0].replaceAll(" ", "+");
		data[0] = data[0].replaceAll(":", "=");
		data[1] = data[1].replaceFirst(" ", "");
		data[1] = data[1].replaceAll(":", "=");
        
        String request = "POST /simpleForm.html HTTP/1.1\\r\\n" + EOL
				+ "Host: " + hostName + ":" + portNumber + "\\r\\n" + EOL
				+ "\\r\\n" + EOL
				+ data[0] + "&" + data[1] + "\\r\\n"+ EOL;
        
		return request;
	}
}
