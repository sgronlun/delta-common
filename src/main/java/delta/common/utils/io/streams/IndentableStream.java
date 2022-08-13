package delta.common.utils.io.streams;

import java.io.PrintStream;

/**
 * A PrinitStream wrapper that handles indentation.
 * @author DAM
 */
public class IndentableStream
{
  private int _indentationLevel;
  private String _indentation;
  private PrintStream _output;
  private String _lineSeed;
  private boolean _isStartOfLine;

  /**
   * Constructor.
   * @param output Output.
   */
  public IndentableStream(PrintStream output)
  {
    this(output,"\t");
  }

  /**
   * Constructor.
   * @param output Output.
   * @param indentation Indentation element.
   */
  public IndentableStream(PrintStream output, String indentation)
  {
    _output=output;
    _indentation=indentation;
    _lineSeed="";
    _isStartOfLine=true;
  }

  /**
   * Get the current indentation level.
   * @return the current indentation level.
   */
  public int getIndentationLevel()
  {
    return _indentationLevel;
  }

  /**
   * Get the atomatic indentation string.
   * @return a string.
   */
  public String getIndentation()
  {
    return _indentation;
  }

  /**
   * Increment the indeentation level.
   */
  public void incrementIndendationLevel()
  {
    setIndentationLevel(_indentationLevel+1);
  }

  /**
   * Decrement the indentation level.
   */
  public void decrementIndentationLevel()
  {
    setIndentationLevel(_indentationLevel-1);
  }

  /**
   * Set the indentation level.
   * @param level Level to set.
   */
  public void setIndentationLevel(int level)
  {
    if (level<0)
    {
      level=0;
    }
    if (level==0)
    {
      _lineSeed="";
    }
    else if (level==1)
    {
      _lineSeed=_indentation;
    }
    else
    {
      StringBuilder sb=new StringBuilder();
      for(int i=0;i<level;i++)
      {
        sb.append(_indentation);
      }
      _lineSeed=sb.toString();
    }
    _indentationLevel=level;
  }

  /**
   * Print the given string to the managed stream (no new line).
   * @param data Data to print.
   */
  public void print(String data)
  {
    int startIndex=0;
    while(startIndex<data.length())
    {
      handleStartOfLine();
      int newLineIndex=data.indexOf('\n',startIndex);
      if (newLineIndex==-1)
      {
        _output.print(data.substring(startIndex));
        startIndex=data.length();
      }
      else
      {
        _output.print(data.substring(startIndex,newLineIndex+1));
        _isStartOfLine=true;
        startIndex=newLineIndex+1;
      }
    }
  }

  /**
   * Print the given string to the managed stream (add a new line).
   * @param data Data to print.
   */
  public void println(String data)
  {
    print(data);
    _output.println();
    _isStartOfLine=true;
  }

  /**
   * Print the given object to the managed stream (add a new line).
   * @param value Data to print.
   */
  public void println(Object value)
  {
    println((value!=null)?value.toString():"null");
  }

  /**
   * Print the given integer value to the managed stream (add a new line).
   * @param value Data to print.
   */
  public void println(int value)
  {
    println(String.valueOf(value));
  }

  private void handleStartOfLine()
  {
    if (_isStartOfLine)
    {
      if (_lineSeed.length()>0)
      {
        _output.print(_lineSeed);
      }
      _isStartOfLine=false;
    }
  }
}
