/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
import java.io.*;
import java.net.*;

class GetMessage {

    public static void main(String[] args) {
        String Server = "localhost";
        int Port = 25;

        Socket client = null;

        try {
            // Attempt to create a client socket connected to the
            // server program.

            client = new Socket(Server, Port);

            // Create a buffered reader for line-oriented reading from the
            // standard input device.

            BufferedReader stdin;
            stdin = new BufferedReader(new InputStreamReader(System.in));

            // Create a buffered reader for line-oriented reading from the
            // socket.

            InputStream is = client.getInputStream();
            BufferedReader sockin;
            sockin = new BufferedReader(new InputStreamReader(is));

            // Create a print writer for line-oriented writing to the
            // socket.

            OutputStream os = client.getOutputStream();
            PrintWriter sockout;
            sockout = new PrintWriter(os, true); // true for auto-flush

            // Display POP3 greeting from POP3 server program.

            System.out.println("Server Name:" + sockin.readLine());

            while (true) {
                // Display a client prompt.

                System.out.print("print user :");

                // Read a command string from the standard input device.

                String cmd = stdin.readLine();

                // Write tuhe command string to the POP3 server program.

                sockout.println(cmd);
                String reply = sockin.readLine();
                System.out.println("Server say: " + reply);
                // Read a reply string from the POP3 server program.
                String name = stdin.readLine();
                sockout.println(name);
                //  String reply = sockin.readLine ();

                // Display the first line of this reply string.
                String nameVer = sockin.readLine();
                System.out.println("username is " + nameVer);

                //message
//     String message1 = sockin.readLine();
//     System.out.println ("Message " + message1);
//   String message2 = sockin.readLine();
//     System.out.println ("Message " + message2);
//     String message3 = sockin.readLine();
//     System.out.println ("Message " + message3);
//     String message4 = sockin.readLine();
//     System.out.println ("Message " + message4);
//     String message5 = sockin.readLine();
//     System.out.println ("Message " + message5);
//     String message6 = sockin.readLine();
//     System.out.println ("Message " + message6);
                String msgnum = sockin.readLine();
                System.out.println(msgnum);
                String meslist;
                meslist = sockin.readLine();
                System.out.println(meslist);
                // sockout.println (message1);
                System.out.print("print message #");
                String selmsg = stdin.readLine();

                sockout.println(selmsg);
                String message1;
                while ((message1 = sockin.readLine()) != null) {
                    System.out.println(message1);
                    //sockout.println (message1);
                }



                // If the RETR command was entered and it succeeded, keep
                // reading all lines until a line is read that begins with
                // a . character. These lines constitute an email message.

                if (cmd.toLowerCase().startsWith("retr")
                        && reply.charAt(0) == '+') {
                    do {
                        reply = sockin.readLine();
                        System.out.println("S:" + reply);
                        if (reply != null && reply.length() > 0) {
                            if (reply.charAt(0) == '.') {
                                break;
                            }
                        }
                    } while (true);
                }

                // If the QUIT command was entered, quit.

                if (cmd.toLowerCase().startsWith("quit")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            try {
                // Attempt to close the client socket.

                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
            }
        }
    }
}
