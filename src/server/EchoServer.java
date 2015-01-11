package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * 
 * The EchoServer is a simple server which return what client sends.
 * 
 * @author JeremyFu
 *
 */
public class EchoServer {

	/*
	 * The ServerSocket is the socket waiting for incoming request.
	 * ServerSocket uses a well-known port number so the client can reach.
	 * When a request arrives, call accept method which instantiates a new
	 * socket to communicate with the client. 
	 * The ServerSocket is backed by TCP, which is a connection oriented 
	 * protocol. Therefore, the new instantiated socket takes care of all
	 * reliability of transportation.
	 */
	private ServerSocket soc;
	private Logger logger;
	
	/**
	 * This method initiates ServerSocket and binds to a designated port.
	 * @param port
	 * @throws IOException
	 */
	void init(int port) throws IOException {
		soc = new ServerSocket(port);
		logger = Logger.getLogger("echo-server");
		logger.info("The server socket has been initiated and listens at port:" + port);
	}
	
	/**
	 * This method performs as a single thread server. It listens at a port.
	 * Once a request arrives, the accepts the connection and uses the client socket to
	 * communicate with the client.
	 */
	void echo() {
		if (soc == null) {
			throw new RuntimeException("The ServerSocket hasn't been instantiated.");
		}
		
		/*
		 * This buffer is used to retrieve information from client
		 */
		byte[] buff = new byte[1024];
		while(true) {
			Socket clientSoc = null;
			InputStream in = null;
			OutputStream out = null;
			try {
				/*
				 * The accept methods will be blocked until a request arrives and
				 * instantiates a new client socket. You should use the client socket
				 * to communicate with client in order to ensure reliable transportation.
				 */
				clientSoc = soc.accept();
				logger.info("Accept a request from " + clientSoc.getInetAddress().getHostName());
				
				/*
				 * The InputStream object is used to read information sent by client
				 */
				in = clientSoc.getInputStream();
				
				/*
				 * The OutputStream object is used to write information to client
				 */
				out = clientSoc.getOutputStream();
				
				while(in.available() > 0) {
					/*
					 * If there is at least one byte available in the buffer of InputStream,
					 * the server reads the message and directly writes back to client(i.e. echo)
					 */
					in.read(buff);
					String body = "<html><body><h1>Hello World!</h1></body></html>";
					out.write(getOKHeader(body.length()).getBytes());
					out.write(body.getBytes());
					
				}
				/*
				 * When you finish echoing all message from client, you should release all those following
				 * resources in order to avoid memory leaks.
				 */
				in.close();
				out.close();
				clientSoc.close();
			} catch (IOException e) {
				logger.warning("IOException caught: " + e.getMessage());
			} 
		}
	}
	
	private String getOKHeader(int length) {
		return String.format("HTTP/1.1 200 OK\r\nContent-Type: "
				+ "text/html; charset=utf-8\r\nContent-Length: "
				+ "%d\r\n\r\n", length);
	}
	
	public static void main(String[] args) {
		EchoServer server = new EchoServer();
		try {
			server.init(14740);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.echo();
	}
	
}
