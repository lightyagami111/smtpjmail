// file MailBox.java
// 11/3/2000 Jeffrey A. Meunier

import java.util.Enumeration;
import java.util.Vector;

public class MailBox
  {

  String _sUserName;     // user name for this mailbox
  Vector _vMessages;     // this vector holds messages for this mailbox
  int _iMsgCount;        // this is the number of messages, equal to _vMessages.length()
  int _iOctetSize;       // total size of mailbox in octets (bytes)



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public MailBox( String sUserName0 )
    {
    _sUserName = sUserName0;
    _vMessages = new Vector();
    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public void addMessage( Message msg0 )
    {
    _vMessages.addElement( msg0 );
    _iMsgCount++;
    _iOctetSize += msg0.getOctetSize();
    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public int getMessageCount()
    {
    return _iMsgCount;
    }


  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public int getOctetSize()
    {
    return _iOctetSize;
    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public Enumeration getMessages()
    {
    return _vMessages.elements();
    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public Message getMessage( int iIndex0 )
    {
    return (Message)_vMessages.get( iIndex0 );
    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public boolean deleteMessage( int iIndex0 )
    {
    if( iIndex0 >= _vMessages.size() )
      return false;
    Message msg = (Message)_vMessages.get( iIndex0 );
    if( msg.isDeleted() )
      return false;
    msg.delete();
    _iMsgCount--;
    _iOctetSize -= msg.getOctetSize();
    return true;
    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public void update()
    {
    Vector vNew = new Vector();
    Enumeration enumm = _vMessages.elements();
    while( enumm.hasMoreElements() )
      {
      Message msg = (Message)enumm.nextElement();
      if( !msg.isDeleted() )
        vNew.add( msg );
      }
    System.out.println( "mailbox deleted " + Integer.toString( _vMessages.size() - vNew.size() ) + " messages" );
    _vMessages = vNew;
    }



  }

// eof
