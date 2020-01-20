// file FiniteMap.java
// 11/3/2000 Jeffrey A. Meunier

import java.util.Enumeration;
import java.util.Vector;

public class FiniteMap
  {

  Vector _vMap;



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  class Element
    {
    public Object _oKey;
    public Object _oValue;

    public Element( Object oKey0, Object oValue0 )
      {
      _oKey = oKey0;
      _oValue = oValue0;
      }
    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public FiniteMap()
    {
    _vMap = new Vector();
    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public Object find( Object oKey0 )
    {
    Element elem = findElement( oKey0 );
    if( elem == null )
      return null;
    return elem._oValue;
    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  protected Element findElement( Object oKey0 )
    {
    Enumeration enumElems = _vMap.elements();
    while( enumElems.hasMoreElements() )
      {
      Element elem = (Element)enumElems.nextElement();
      if( elem._oKey.equals( oKey0 ) )
        return elem;
      }
    return null;
    }



  //------------------------------------------------------------------
  // Add an element to the beginning of the finite map.
  //------------------------------------------------------------------
  public void add( Object oKey0, Object oValue0 )
    {
    _vMap.insertElementAt( new Element( oKey0, oValue0 ), 0 );
    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public boolean update( Object oKey0, Object oValue0 )
    {
    Element elem = findElement( oKey0 );
    if( elem == null )
      return false;
    elem._oValue = oValue0;
    return true;
    }



  //------------------------------------------------------------------
  //------------------------------------------------------------------
  public boolean remove( Object oKey0 )
    {
    Element elem = findElement( oKey0 );
    if( elem == null )
      return false;
    _vMap.removeElement( elem );
    return true;
    }



  public Enumeration elements()
    {
    return _vMap.elements();
    }



  }

// eof
