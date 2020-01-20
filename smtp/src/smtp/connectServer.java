/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smtp;
import java.net.*;
import java.io.*;
import java.util.*;
/**
 *
 * @author Administrator
 */
public class connectServer {
private Socket connection;
	/* Streams for reading and writing the socket */
	private BufferedReader fromServer;
	private DataOutputStream toServer;
	//private PrintStream toServer;
	private static final int SMTP_PORT = 25;

	public connectServer() throws IOException {
		connection = new Socket("JavaMail",25);




}
}