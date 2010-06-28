package delta.common.utils.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a link in a HF network graph.
 * @author DAM
 * @param <L> Link data type.
 */
public class GraphLink<L>
{
  /**
   * ID for the 'from' node.
   */
  private String _fromId;

  /**
   * ID for the 'to' node.
   */
  private String _toId;

  /**
   * List of text lines for the link.
   */
  private List<String> _text;

  /**
   * Full constructor using a single line of text.
   * @param fromId ID of 'from' node.
   * @param toId ID of 'to' node.
   * @param text link's text (a single line).
   */
  public GraphLink(String fromId, String toId, String text)
  {
    _fromId=fromId;
    _toId=toId;
    _text=new ArrayList<String>();
    _text.add(text);
  }

  /**
   * Full constructor using a multi-lines text.
   * @param fromId ID of 'from' node.
   * @param toId ID of 'to' node.
   * @param text link's text (multi-line).
   */
  public GraphLink(String fromId, String toId, List<String> text)
  {
    _fromId=fromId;
    _toId=toId;
    _text=new ArrayList<String>(text);
  }

  /**
   * Indicates if this links has a line which contains the specified
   * <code>text</code>.
   * @param text searched text.
   * @return <code>true</code> if found, <code>false</code> otherwise.
   */
  public boolean hasText(String text)
  {
    return _text.contains(text);
  }

  /**
   * Add a new line of text.
   * @param text of line to be added.
   */
  public void addText(String text)
  {
    _text.add(text);
  }

  /**
   * Sort all text lines.
   */
  public void sortTextLines()
  {
    Collections.sort(_text);
  }

  /**
   * Get the 'from' node's ID.
   * @return the 'from' node's ID.
   */
  public String getFromId()
  {
    return _fromId;
  }

  /**
   * Get the 'to' node's ID.
   * @return the 'to' node's ID.
   */
  public String getToId()
  {
    return _toId;
  }

  /**
   * Get the text lines.
   * @return the text lines.
   */
  public List<String> getText()
  {
    return _text;
  }
}
