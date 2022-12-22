package delta.common.utils.xml.sax.pojos;

/**
 * Base class for all POJOs used in the SAX parser test. 
 * @author DAM
 */
public class IdAndName
{
  private int _id;
  private String _name;

  /**
   * Constructor.
   */
  public IdAndName()
  {
    _id=0;
    _name="";
  }

  /**
   * Get the identifier.
   * @return the identifier.
   */
  public int getId()
  {
    return _id;
  }

  /**
   * Set the identifier.
   * @param id the identifier to set.
   */
  public void setId(int id)
  {
    _id=id;
  }

  /**
   * Get the name.
   * @return the name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  @Override
  public String toString()
  {
    return getClass().getSimpleName()+": id="+_id+", name="+_name;
  }
}
