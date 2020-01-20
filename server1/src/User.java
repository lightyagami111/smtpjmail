// file User.java
// 11/3/2000 Jeffrey A. Meunier

public class User
  {
  String _sFullName;
  String _sUserName;
  String _sEmailAddress;
  String _sPassword;



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public User( String sUserName0 )
    {
    _sUserName = sUserName0;
    }


  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public String getUserName()
    {
    return _sUserName;
    }



  }

// eof
