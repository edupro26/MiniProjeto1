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
		
		this.socket = new Socket(hostName, portNumber);
	}

	public void getResource(String ObjectName) throws IOException {
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        String request = 
        		"GET /" + ObjectName + " HTTP/1.1" + "\r\n" +
        		"Host: " + hostName + ":" + portNumber + "\r\n";
        out.println(request);
        
        System.out.println("\r\n" + in.readLine() + "\r\n");
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

	public void close() {
		// TODO Auto-generated method stub
		
	}
	
	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}
	
}
