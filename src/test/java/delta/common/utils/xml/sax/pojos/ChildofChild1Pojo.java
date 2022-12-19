package delta.common.utils.xml.sax.pojos;

/**
 * Child of Child 1 POJO for SAX parsing tests.
 * @author DAM
 */
public class ChildofChild1Pojo
{
  private int _id;
  private String _name;

  /**
   * Constructor.
   */
  public ChildofChild1Pojo()
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
    return "ChildOfChild1: id="+_id+", name="+_name;
  }
}
