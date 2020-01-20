// file SmtpListener.java
// 11/3/2000 Jeffrey A. Meunier

// This waits for a socket connection on the SMTP server socket, and when a
// connection is made it spawns a new SMTP handler.

import java.io.*;
import java.net.*;

public class SmtpListener implements Runnable
  {

  ServerSocket _sskSmtpServer;
  Thread _thread;
  PostOffice _postOffice;



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public SmtpListener( ServerSocket sskSmtpServer0, PostOffice postOffice0 )
    {
    _sskSmtpServer = sskSmtpServer0;
    _postOffice = postOffice0;
    _thread = new Thread( this );
    _thread.start();
    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public void run()
    {
    System.err.println( "Starting SMTP listener" );
    for(;;)
      {
      try
        {
        Socket skSmtpClient = _sskSmtpServer.accept();
        SmtpHandler smtpHandler = new SmtpHandler( skSmtpClient, _postOffice );  // spawns automatically
        }
      catch( IOException ioe0 )
        {
        System.err.println( "ERROR: SMTP listener caught error accepting connection from client, continuing..." );
        }
      }
    }



  }

// eof

