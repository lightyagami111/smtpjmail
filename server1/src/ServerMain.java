// file ServerMain.java
// 11/3/2000 Jeffrey A. Meunier



public class ServerMain
  {
  public static void main( String sArgs0[] )
    {
    int iSmtpPort,
        iPop3Port;

    if( sArgs0.length > 0 )
      {
      if( sArgs0.length == 2 )
        {
        try
          {
          iSmtpPort = Integer.parseInt( sArgs0[ 0 ] );
          iPop3Port = Integer.parseInt( sArgs0[ 1 ] );
          if( iSmtpPort == 0 || iPop3Port == 0 )
            {
            System.err.println( "Port numbers must be integers greater than zero, less than 65536" );
            return;
            }
          }
        catch( Exception e0 )
          {
          System.err.println( "Port numbers must be integers greater than zero, less than 65536" );
          return;
          }
        }
      else
        {
        System.err.println( "Please enter an SMTP port number and a POP3 port number" );
        return;
        }
      }
    else
      {
      iPop3Port = 110;
      iSmtpPort = 25;
      }
    MailServer ms = new MailServer( iPop3Port, iSmtpPort );
    if( ms == null )
      return;
    System.out.println( "SMTP (send) port = " + Integer.toString( iSmtpPort ) );
    System.out.println( "POP3 (receive) port = " + Integer.toString( iPop3Port ) );
    ms.start();
    }
  }

// eof
