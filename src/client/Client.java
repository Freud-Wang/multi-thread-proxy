package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public void request() {
		String request = "GET http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html HTTP/1.1\r\n"
				+ "Host: www.w3.org\r\n"
				+ "Proxy-Connection: keep-alive\r\n"
				+ "Cache-Control: max-age=0\r\n"
				+ "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\r\n"
				+ "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36\r\n"
				+ "Accept-Encoding: gzip, deflate, sdch\r\nAccept-Language: zh-CN,zh;q=0.8,en;q=0.6\r\n"
				+ "\r\n";
		try {
			Socket clientSocket = new Socket("www.w3.org", 80);
			File file = new File("page.html");
			FileWriter fout = new FileWriter(file);
			OutputStream out = clientSocket.getOutputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out.write(request.getBytes());
			String line;
			boolean hdrEnd = false;
			while(true) {
				line = in.readLine();
				if (line == null) {
					break;
				}
				fout.write(line);
				if (!hdrEnd) {
					fout.write("\r\n");
				}
				if (line.equals("")) {
					hdrEnd = true;
				}
				
				
				
			}
			out.close();
			in.close();
			clientSocket.close();
			fout.close();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static long octal2Long(String octal) {
//		long ret = 0;
//		char[] ch = octal.toCharArray();
//		for (int i = 0; i < ch.length; i++) {
//			int base = Integer.parseInt("" + ch[i], 16);
//			System.out.print("base: " + base + "  current ret: " + ret);
//			ret = ret * 16 + base;
//			System.out.println("  ret:" + ret);
//		}
		return Long.parseLong(octal, 16);
	}
	public static void main(String[] args) {
		new Client().request();
	}
	
}
