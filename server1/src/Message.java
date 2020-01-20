// file Message.java
// 11/3/2000 Jeffrey A. Meunier

import java.text.DateFormat;
import java.util.*;

public class Message
  {

  User    _usrSender;
  User    _usrRecipient;
  String  _sSubject;
  Date    _date;
  Vector  _vMessageText;   // message text is a vector of strings
  boolean _bDeleted;
  int     _iOctetSize;
  String  _sUid;           // unique ID



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public Message( String sSender0, String sRecipient0 )
    {
    _usrSender = new User( sSender0 );
    _usrRecipient = new User( sRecipient0 );
    _bDeleted = false;
    _vMessageText = new Vector();
    _sUid = genUid();
    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  String genUid()
    {
    String sChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    int iLen = sChars.length();
    StringBuffer sb = new StringBuffer();
    Date now = new Date();
    long lSeed = now.getTime();
    Random rand = new Random( lSeed );
    for( int i=0; i<10; i++ )
      {
      int iRand = rand.nextInt( iLen );
      sb.append( sChars.charAt( iRand ) );
      }
    return sb.toString();
    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public void addMessageLine( String sLine0 )
    {
    _vMessageText.add( sLine0 );
    _iOctetSize += sLine0.length();
    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public Enumeration getMessageLines()
    {
    return _vMessageText.elements();
    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public String getUid()
    {
    return _sUid;
    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public String getRecipientName()
    {
    return _usrRecipient.getUserName();
    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public int getOctetSize()
    {
    return _iOctetSize;
    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public boolean isDeleted()
    {
    return _bDeleted;
    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public void delete()
    {
    _bDeleted = true;
    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public void setSubject( String sSubj0 )
    {
    _sSubject = sSubj0;
    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public void setDate( String sDate0 )
    {
    DateFormat df;

    // Try to parse the date in four different ways.  Accept the first
    // parse that succeeds.
    try
      {
      df = DateFormat.getDateTimeInstance( DateFormat.FULL, DateFormat.FULL );
      _date = df.parse( sDate0 );
      return;
      }
    catch( Exception e0 )
      {}
    try
      {
      df = DateFormat.getDateTimeInstance( DateFormat.LONG, DateFormat.LONG );
      _date = df.parse( sDate0 );
      return;
      }
    catch( Exception e0 )
      {}
    try
      {
      df = DateFormat.getDateTimeInstance( DateFormat.MEDIUM, DateFormat.MEDIUM );
      _date = df.parse( sDate0 );
      return;
      }
    catch( Exception e0 )
      {}
    try
      {
      df = DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.SHORT );
      _date = df.parse( sDate0 );
      return;
      }
    catch( Exception e0 )
      {}
    }

  }

// eof
