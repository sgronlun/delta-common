package delta.common.utils.commands;

import delta.common.utils.text.StringSplitter;

/**
 * Storage class for a command info (id+args).
 * Each instance is non-mutable for safety reasons.
 * @author DAM
 */
public class Command
{
  private String _id;
  private String[] _args;

  /**
   * Simple constructor.
   * @param commandID Command's ID.
   */
  public Command(String commandID)
  {
    _id=commandID;
    _args=new String[0];
  }

  /**
   * Convenience constructor.
   * @param commandID Command's ID.
   * @param arg Simple arg.
   */
  public Command(String commandID, String arg)
  {
    _id=commandID;
    if (arg!=null)
    {
      _args=new String[1];
      _args[0]=arg;
    }
    else
    {
      _args=new String[0];
    }
  }

  /**
   * Full constructor.
   * @param commandID Command's ID.
   * @param args Command's arguments.
   */
  public Command(String commandID, String[] args)
  {
    _id=commandID;
    if (args!=null)
    {
      int nb=args.length;
      _args=new String[nb];
      System.arraycopy(args,0,_args,0,nb);
    }
    else
    {
      _args=new String[0];
    }
  }

  /**
   * Get the command's ID.
   * @return the command's ID.
   */
  public String getID()
  {
    return _id;
  }

  /**
   * Get the command's arguments.
   * @return the command's arguments.
   */
  public String[] getArgs()
  {
    int nbArgs=_args.length;
    String[] ret=new String[nbArgs];
    System.arraycopy(_args,0,ret,0,nbArgs);
    return ret;
  }

  /**
   * Get the number of arguments.
   * @return the number of arguments.
   */
  public int getNbArgs()
  {
    return _args.length;
  }

  /**
   * Get argument at specified index.
   * @param index Index of desired argument.
   * @return A string argument or <code>null</code> if index is out of bounds.
   */
  public String getArg(int index)
  {
    int nbArgs=_args.length;
    String ret=null;
    if ((index>=0) && (index<nbArgs))
    {
      ret=_args[index];
    }
    return ret;
  }

  /**
   * Build this command and its arguments as a formatted string.
   * @return A formatted <tt>String</tt>.
   */
  public String build()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_id);
    if (_args.length>0)
    {
      sb.append('(');
      for(int i=0;i<_args.length;i++)
      {
        if (i>0) sb.append(',');
        sb.append(_args[i]);
      }
      sb.append(')');
    }
    return sb.toString();
  }

  /**
   * Parse a command line.
   * @param commandLine Command to parse.
   * @return A command.
   */
  public static Command parse(String commandLine)
  {
    String commandID="";
    String argsList="";
    int openParenthesis=commandLine.indexOf('(');
    if (openParenthesis==-1)
    {
      commandID=commandLine;
    }
    else
    {
      commandID=commandLine.substring(0,openParenthesis);
      if (commandLine.length()>openParenthesis)
      {
        argsList=commandLine.substring(openParenthesis+1);
        int endParenthesis=argsList.lastIndexOf(')');
        if (endParenthesis!=-1)
        {
          argsList=argsList.substring(0,endParenthesis);
        }
      }
    }
    commandID=commandID.trim();
    String[] args=StringSplitter.split(argsList);
    Command ret=new Command(commandID,args);
    return ret;
  }

  /**
   * Standard toString() method.
   * @return Stringified representation of this object.
   */
  @Override
  public String toString()
  {
    return build();
  }
}
