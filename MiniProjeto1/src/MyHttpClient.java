import java.io.IOException;

public class MyHttpClient {

	private String hostName;
	private int portNumber;
	
	public MyHttpClient(String hostName, int portNumber) throws IOException {
		// TODO Auto-generated constructor stub
	}

	public void getResource(String ObjectName) throws IOException {
		// TODO Auto-generated method stub
		
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
