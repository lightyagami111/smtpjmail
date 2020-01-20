// file PostOffice.java
// 11/3/2000 Jeffrey A. Meunier

import java.util.*;



public class PostOffice
  {

  FiniteMap _fmMailBoxes;



  //------------------------------------------------------------------
  // Create a new PostOffice.
  //------------------------------------------------------------------
  public PostOffice()
    {
    _fmMailBoxes = new FiniteMap();
    }



  //------------------------------------------------------------------
  // Dispatch a message to the post office.
  // This determines the recipient's name, and places the message
  // in the user's mailbox.
  //------------------------------------------------------------------
  public void sendMessage( Message msg0 )
    {
    String sRecipient = msg0.getRecipientName();
    addMessage( sRecipient, msg0 );
    }



  //------------------------------------------------------------------
  // Add a message to the user's mailbox.  If the user's mailbox
  // does not exist, create a new mailbox for the user.
  //------------------------------------------------------------------
  void addMessage( String sUser0, Message msg0 )
    {
    MailBox mb = (MailBox)_fmMailBoxes.find( sUser0 );
    if( mb == null )
      {
      mb = new MailBox( sUser0 );
      _fmMailBoxes.add( sUser0, mb );
      }
    mb.addMessage( msg0 );
    }


  //------------------------------------------------------------------
  // Get the mailbox for a user.  If the mailbox does not exist,
  // create a new mailbox for the user.
  //------------------------------------------------------------------
  MailBox getMailBox( String sUser0 )
    {
    MailBox mb = (MailBox)_fmMailBoxes.find( sUser0 );
    if( mb == null )
      {
      mb = new MailBox( sUser0 );
      _fmMailBoxes.add( sUser0, mb );
      }
    return mb;
    }


  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public Enumeration getMailBoxes()
    {
    return _fmMailBoxes.elements();
    }


  }

// eof
