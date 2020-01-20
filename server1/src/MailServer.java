// file MailServer.java

import java.io.*;
import java.net.*;

public class MailServer
  {
  int _iPop3Port = 1025;
  int _iSmtpPort = 1026;

  ServerSocket _sskPop3Server;
  ServerSocket _sskSmtpServer;


  SmtpListener _smtpListener;



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public MailServer( int iPop3Port0, int iSmtpPort0 )
    {
    _iPop3Port = iPop3Port0;
    _iSmtpPort = iSmtpPort0;

    try
      {
      _sskPop3Server = new ServerSocket( _iPop3Port );
      }
    catch( IOException ioe0 )
      {
      System.err.println( "ERROR: could not open POP3 server socket on port " + Integer.toString( _iPop3Port ) );
      return;
      }

    try
      {
      _sskSmtpServer = new ServerSocket( _iSmtpPort );
      }
    catch( IOException ioe0 )
      {
      System.err.println( "ERROR: could not open SMTP server socket on port " + Integer.toString( _iSmtpPort ) );
      return;
      }

    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public void start()
    {
    if( _sskPop3Server == null || _sskSmtpServer == null )
      return;
    PostOffice po = new PostOffice();
   
    _smtpListener = new SmtpListener( _sskSmtpServer, po );  // spawns automatically
    }



  }

// eof
