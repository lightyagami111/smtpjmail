/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smtp;

/**
 *
 * @author Administrator
 */
import java.net.*;
import java.io.*;
import java.util.*;

/** * Open an SMTP connection to a mailserver and send one mail. * */
public class SMTPConnection {
    /* The socket to the server */

    private Socket connection;
    /* Streams for reading and writing the socket */
    private BufferedReader fromServer;
    private DataOutputStream toServer;
    //private PrintStream toServer;
    private static final int SMTP_PORT = 25;

    /* Are we connected? Used in close() to determine what to do. */
    private boolean isConnected = false;

    /*
     * Create an SMTPConnection object. Create the socket and the associated
     * streams. Initialize SMTP connection.
     */
    public SMTPConnection() throws IOException {
        connection = new Socket("localhost", 25);

        //fromServer = new BufferedReader(new InputStreamReader(System.in));
        //toServer = System.out;
        fromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        toServer = new DataOutputStream(connection.getOutputStream());


        /*
         * Read a line from server and check that the reply code is 220. If not,
         * throw an IOException.
         */
        int serverResponse = this.parseReply(fromServer.readLine());
        if (serverResponse != 220) {
            throw new IOException("Unexpected server response");

        }

        /*
         * SMTP handshake. We need the name of the local machine. Send the
         * appropriate SMTP handshake command.
         */
        String localhost = InetAddress.getLocalHost().getHostName();
        sendCommand("HELO " + localhost, 250);

        isConnected = true;
    }

    /*
     * Send the message. Write the correct SMTP-commands in the correct
     * order. No checking for errors, just throw them to the caller.
     */
    public void send() throws IOException {
        /*
         * Send all the necessary commands to send a message. Call sendCommand() to
         * do the dirty work. Do _not_ catch the exception thrown from
         * sendCommand().
         */
        this.sendCommand("MAIL FROM: <" + Envelope.Sender + ">", 250);
//          String Reset = fromServer.readLine();
//        if (Reset == "reset") {
//            sendCommand("QUIT", 221);
//        }

        this.sendCommand("RCPT TO: <" + Envelope.Recipient + ">", 250);
        this.sendCommand("DATA", 354);
        this.sendCommand(Envelope.Message.Headers + Envelope.Message.Body + "\r\n.", 250);
    }

    /* Close the connection. First, terminate on SMTP level, then close the socket. */
    public void close() {
        isConnected = false;
        try {
            sendCommand("QUIT", 221);
            connection.close();
        } catch (IOException e) {
            System.out.println("Unable to close connection: " + e);
            isConnected = true;
        }
    }

    /*
     * Send an SMTP command to the server. Check that the reply code is what is is
     * supposed to be according to RFC 821.
     */
    private void sendCommand(String command, int rc) throws IOException {

        /* Write command to server and read reply from server. */
        //Use the following if printout out the stdout
        //toServer.write((command).getBytes());
        toServer.writeBytes(command + "\r\n");
        /* Check that the server's reply code is the same as the parameter rc. If not, throw an IOException.*/
        String response = fromServer.readLine();
        System.out.println(response);
        int intResponse = parseReply(response);
        if (intResponse != rc) {
            throw new IOException("Server reply did not match what was expected.");
        }

    }

    /* Parse the reply line from the server. Returns the reply code. */
    private int parseReply(String reply) {
       StringTokenizer st = new StringTokenizer(reply, " ");
        int retVal;
        retVal = Integer.parseInt(st.nextToken());
        return retVal;



    }

    /* Destructor. Closes the connection if something bad happens. */
    protected void finalize() throws Throwable {
        if (isConnected) {
            close();
        }
        super.finalize();
    }
}
