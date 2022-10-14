import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MyHttpClient {

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
        
        String request = "GET /" + ObjectName + " HTTP/1.1" + "\r\n";
        out.println(request);
        
        String rawAnswer = in.readLine();
        String answer = processAnswer(rawAnswer);
        System.out.println("\r\n" + answer + "\r\n");
	}
	
	private String processAnswer(String answer) {
		String finalAnswer = new String();
		boolean b = true;
		for (int i = 0; i < answer.length(); i++) {			
			finalAnswer += answer.charAt(i);
			if(i + 2 < answer.length()) {
				if(answer.charAt(i + 1) == '<' && Character.isLetter(answer.charAt(i + 2))) {
					if(b == true) {
						finalAnswer = finalAnswer + "\r\n\r\n";
						b = false;
					}				
					else
						finalAnswer += "\r\n";
				}	
			}
		} 
		return finalAnswer;
	}

	public void postData(String[] data) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void sendUnimplementedMethod(String wrongMethodName) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void malformedRequest(int type) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void close() throws IOException {
		this.socket.close();
		
	}
	
}
