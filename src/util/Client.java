package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

public class Client {
	static String host = "localhost";
	static int port = 8080;
	static String uri = "/forum/";

	public static void main(String[] args) throws IOException {
		// for (int i = 0; i < 100000; i++) {
		Socket client = new Socket(host, port);
		PrintStream ps = new PrintStream(client.getOutputStream());
 
		ps.println("GET " + uri + " HTTP/1.1");
		ps.println("Host: " + host);
		ps.println("User-Agent: Mozilla/5.0 (Windows NT 5.1; rv:18.0) Gecko/20100101 Firefox/18.0");
		ps.println("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		ps.println("Accept-Language: zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		ps.println("Accept-Encoding: gzip, deflate");
		ps.println("Connection: keep-alive");
		ps.println();

		InputStream is = client.getInputStream();

		byte buf[] = new byte[1024];
		while (true) {
			int num = is.read(buf);
			if (num < 0) {
				break;
			}
			System.out.print(new String(buf, 0, num));
		}
	}

	// }

}
