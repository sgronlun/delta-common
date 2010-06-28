package delta.common.framework.objects.data;

import java.io.Serializable;
import java.util.Comparator;

/**
 * A comparator for <tt>DataObject</tt>s.
 * @author DAM
 * @param <E>
 */
public class DataObjectComparator<E extends DataObject<E>> implements Comparator<Object>,Serializable
{
  private static final long serialVersionUID=1L;

  public int compareDataObjects(DataObject<E> do1, DataObject<E> do2)
  {
    String label1=do1.getLabel();
    String label2=do2.getLabel();
    int ret=label1.compareTo(label2);
    return ret;
  }

  @SuppressWarnings("unchecked")
  public int compare(Object o1, Object o2)
  {
    DataObject<E> do1=(DataObject<E>)o1;
    DataObject<E> do2=(DataObject<E>)o2;
    int ret=compareDataObjects(do1,do2);
    return ret;
  }
}
