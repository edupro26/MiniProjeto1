import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MyHttpClient {
	
	private final String EOL = System.getProperty("line.separator");
	private String hostName;
	private int portNumber;
	private Socket socket;
	
	public MyHttpClient(String hostName, int portNumber) throws IOException {
		this.hostName = hostName;
		this.portNumber = portNumber;
		
		this.socket = new Socket(this.hostName, this.portNumber);
	}
	
	public void getResource(String ObjectName) throws IOException {
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        String request = "GET /" + ObjectName + " HTTP/1.1\\r\\n" + EOL;
        out.println(request);
        
        String rawAnswer = in.readLine();
        String answer = processAnswer(rawAnswer);
        System.out.println(EOL + answer + EOL);
	}
	
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

	public void postData(String[] data) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void sendUnimplementedMethod(String wrongMethodName) throws IOException {
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
        String request = wrongMethodName + " / HTTP/1.1\\r\\n" + EOL;
        out.println(request);
        
        String answer = in.readLine();
        System.out.println(EOL + answer + EOL)
        ;
	}

	public void malformedRequest(int type) throws IOException {
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        String request;
        switch (type) {
        	case 1:
        		request = "GET /index.html HTTP/1.1" + EOL;
        		out.println(request);
        		break;
        	case 2:
        		request = "GET /index.html  HTTP/1.1\\r\\n" + EOL;
        		out.println(request);
        		break;
        	case 3:
        		request = "GET /index.html HTTP/\\r\\n" + EOL;
        		out.println(request);
        		break;
        }
        
        String answer = in.readLine();
        System.out.println(EOL + answer + EOL);
	}

	public void close() throws IOException {
		this.socket.close();
	}
	
}
