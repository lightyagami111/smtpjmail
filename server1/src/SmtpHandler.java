// file SmtpHandler.java
// 11/3/2000 Jeffrey A. Meunier

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import smtp.ConnectToDatabase;

public class SmtpHandler implements Runnable
  {

  public static String _sServerName = "JavaMail";

  Socket _skSmtpClient;
  Thread _thread;
  BufferedReader _br;
  PrintWriter _pw;
  PostOffice _postOffice;



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public SmtpHandler( Socket skSmtpClient0, PostOffice postOffice0 )
    {
    _skSmtpClient = skSmtpClient0;
    _postOffice = postOffice0;
    _thread = new Thread( this );
    _thread.start();
    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public void run()
    {
    System.out.println( "SMTP: connection accepted on " + _skSmtpClient.toString() );

    try
      {
      InputStream inpStream = _skSmtpClient.getInputStream();
      OutputStream outStream = _skSmtpClient.getOutputStream();

      _br = new BufferedReader( new InputStreamReader( inpStream ) );
      _pw = new PrintWriter( outStream );
      }
    catch( IOException ioe0 )
      {
      System.err.println( "ERROR: could not extract streams from SMTP client socket" );
      return;
      }

    try
      {
            try {
                startSession();
            } catch (SQLException ex) {
                Logger.getLogger(SmtpHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SmtpHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
      }
    catch( IOException ioe0 )
      {
      System.err.println( "ERROR: general I/O error during SMTP transaction session" );
      }

    System.err.println( "SMTP: finished session, closing connection\n" );

    try
      {
      _skSmtpClient.close();
      }
    catch( IOException ioe0 )
      {
      System.err.println( "ERROR: exception caught trying to close SMTP client socket, continuing..." );
      }

    }


  //------------------------------------------------------------------
  //------------------------------------------------------------------
  void write( String s0 )
    {
    System.err.println( "SMTP: server sending (" + s0 + ")" );
    _pw.println( s0 );
    _pw.flush();
    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  String read()
  throws IOException
    {
    String s = _br.readLine();
    System.err.println( "SMTP: client sent    [" + s + "]" );
    return s;
    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  void ack()
    {
    write( "250 OK" );
    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  void nak()
    {
    write( "451 Requested action aborted: local error in processing" );
    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  void startSession()
  throws IOException, SQLException, ClassNotFoundException
    {
    write( "220 " + _sServerName + " Service ready" );
    String sUserMessage;
    for(;;)
      {
      sUserMessage = read();
      if( sUserMessage == null )
        return;
      if( sUserMessage.startsWith( "EHLO" ) )
        break;
      else if( sUserMessage.startsWith( "HELO" ) )
        break;
      else if( sUserMessage.equals( "QUIT" ) )
        return;
      else if( sUserMessage.equals( "user" ) )
          readMessage2( sUserMessage );
      }

    write( "250 " + _sServerName );

    for(;;)
      {
      sUserMessage = read();
      if( sUserMessage.startsWith( "MAIL" ) )
        readMessage( sUserMessage );
      else if( sUserMessage.equals( "QUIT" ) )
        break;
      else if( sUserMessage.equals( "user" ) )
          readMessage2( sUserMessage );
        break;
      }
    write( "221 " + _sServerName + " closing connection" );
    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  String findUname( String s0 )
  throws IOException
    {
    int i, j;
    for( i=0; s0.charAt( i ) != '<'; i++ );
    for( j=i; s0.charAt( j ) != '@'; j++ );
    return s0.substring( i+1, j );
    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  void readMessage( String sUserMessage0 )
  throws IOException, SQLException, ClassNotFoundException
    {
    String sSenderUname = findUname( sUserMessage0 );
    System.out.println(sSenderUname);

        Connection connection = ConnectToDatabase.createConnection();
        String qqq = "select * from username where name=? ";
        PreparedStatement statement = connection.prepareStatement(qqq);
        statement.setString(1, sSenderUname);
        statement.executeQuery();
        ResultSet fff = statement.getResultSet();
        if (fff.next()) {

            System.out.println("User Vertified");
                    ack();

        } else {
            System.out.println("User NOOT Vertified");
           // reset();
        }
        connection.commit();
        statement.close();
        connection.close();

    //ack();
    String sRecptMessage = read();
    String sRecptUname = findUname( sRecptMessage );
    ack();
    String s = read();
    if( !s.equals( "DATA" ) )
      {
      nak();
      return;
      }
    write( "354 Enter mail, end with \".\" on a line by itself" );
    Message message = new Message( sSenderUname, sRecptUname );
    for(;;)
      {
      s = read();
      if( s.startsWith( "Subject: " ) )
        message.setSubject( s.substring( 9 ) );
      else if( s.startsWith( "Date: " ) )
        message.setDate( s.substring( 6 ) );
      else if( s.equals( "QUIT" ) )
        return;
      else if( s.equals( "." ) )
        break;
      message.addMessageLine( s );
      }
    write( "250 Message accepted for delivery" );
    _postOffice.sendMessage( message );
    }


void readMessage2( String Mess )
  throws IOException, SQLException, ClassNotFoundException
    {
//BufferedReader user=null;
   // write("ddfdfd");
    String s= Mess;

    System.out.println(s);

   write("Enter username");
   String user=_br.readLine();

 //String RecName = findUname( user );
  System.out.println(user);
////
        Connection connection = ConnectToDatabase.createConnection();
        String qqq = "select * from username where name=? ";
        PreparedStatement statement = connection.prepareStatement(qqq);
        statement.setString(1, user);
        statement.executeQuery();
        ResultSet fff = statement.getResultSet();
        if (fff.next()) {

            //System.out.println("User Vertified");
            write("Vertified");

                 //   ack();

        } else {
            //System.out.println("User NOOT Vertified");
            write("Not Vertified");
           // reset();
        }
        connection.commit();
        statement.close();
        connection.close();
        int x=user.indexOf('@');
        String ss=user.substring(0,x );
        System.out.println(ss);
       MailBox mb = (MailBox) _postOffice._fmMailBoxes.find( ss );
     // mb.getMessage(0);
      int msgnum= mb._iMsgCount;
      write("Messages In INBOX "+ msgnum);
      for(int i=0;i<msgnum;i++){
      String msglist =mb.getMessage(i)._usrSender._sUserName;
        int vv=i+1;
//            System.out.println("Message: "+e.nextElement());
           write(""+vv+" - From "+msglist);

      }
      String msgsel=_br.readLine();
int selmsgnum = Integer.parseInt(msgsel);
int sel2=selmsgnum-1;

      Enumeration e =mb.getMessage(sel2)._vMessageText.elements();
        while(e.hasMoreElements()){
//            System.out.println("Message: "+e.nextElement());
           write(""+e.nextElement());
        }


    }

  }

// eof
