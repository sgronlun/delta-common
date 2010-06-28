package delta.common.utils.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

/**
 * A tools class to parse GNU-style command line options.
 * @author DAM
 */
public class CommandLineOptions
{
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  /**
   * Option seed
   */
  public static final String OPTION_SEED="--";
  /**
   * Option name and optional value separator
   */
  public static final String FIELD_AND_VALUE_SEPARATOR="=";

  private List<String> _options;
  private List<String> _values;

  /**
   * Constructor.
   */
  public CommandLineOptions()
  {
    _options=new ArrayList<String>();
    _values=new ArrayList<String>();
  }

  /**
   * Get the number of parsed options.
   * @return the number of parsed options.
   */
  public int getNbOptions()
  {
    return _options.size();
  }

  /**
   * Get the name of the option at specified <code>index</code>.
   * @param index Index of targeted option.
   * @return An option name or an empty string.
   */
  public String getOption(int index)
  {
    int nb=_options.size();
    if ((index>=0) && (index<nb))
    {
      return _options.get(index);
    }
    return "";
  }

  /**
   * Get the value of the option at specified <code>index</code>.
   * @param index Index of targeted option.
   * @return An option value or an empty string.
   */
  public String getValue(int index)
  {
    int nb=_options.size();
    if ((index>=0) && (index<nb))
    {
      return _values.get(index);
    }
    return "";
  }

  /**
   * Get the value of an option designated by its name.
   * @param optionName Name of the option to get.
   * @return A value or <code>null</code> if no such option.
   */
  public String getValue(String optionName)
  {
    if ((optionName==null) || (optionName.length()==0))
    {
      return null;
    }
    int nb=_options.size();
    String option;
    String value=null;
    for(int i=0;i<nb;i++)
    {
      option=_options.get(i);
      if (optionName.equals(option))
      {
        value=_values.get(i);
        break;
      }
    }
    return value;
  }

  /**
   * Parse some command line arguments.
   * @param args Arguments to parse.
   * @return <code>true</code> if parse was successful, <code>false</code> otherwise.
   */
  public boolean parse(String[] args)
  {
    _options.clear();
    _values.clear();
    boolean ret=true;
    if (args!=null)
    {
      int nb=args.length;
      String arg;
      String optionAndOptValue;
      String option;
      String value;
      for(int i=0;i<nb;i++)
      {
        arg=args[i];
        if (arg.startsWith(OPTION_SEED))
        {
          optionAndOptValue=arg.substring(OPTION_SEED.length());
          int index=optionAndOptValue.indexOf(FIELD_AND_VALUE_SEPARATOR);
          if (index!=-1)
          {
            option=optionAndOptValue.substring(0,index);
            value=optionAndOptValue.substring(index+FIELD_AND_VALUE_SEPARATOR.length());
          }
          else
          {
            option=optionAndOptValue;
            value="";
          }
          _options.add(option);
          _values.add(value);
        }
        else
        {
          _logger.warn("Ignored arg ["+arg+"]");
          ret=false;
        }
      }
    }
    return ret;
  }
}
