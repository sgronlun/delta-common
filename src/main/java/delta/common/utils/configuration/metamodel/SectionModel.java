package delta.common.utils.configuration.metamodel;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Represents the model for a <tt>Configuration</tt>'s section.
 * @author DAM
 */
public class SectionModel
{
  private String name;
  private Hashtable<String,VariableModel> variables;
  private ArrayList<VariableModel> variablesList;

  /**
   * Complete constructor.
   * @param name_p Name.
   */
  public SectionModel(String name_p)
  {
    name=name_p;
    variables=new Hashtable<String,VariableModel>();
    variablesList=new ArrayList<VariableModel>();
  }

  /**
   * Get the name of the associated section.
   * @return the name of the associated section.
   */
  public String getName()
  {
    return name;
  }

  /**
   * Get the list of managed <tt>VariableModel</tt>.
   * @return the list of managed <tt>VariableModel</tt>.
   */
  public ArrayList<VariableModel> getVariables()
  {
    return variablesList;
  }

  /**
   * Add a new <tt>VariableModel</tt>.
   * @param variableModel <tt>VariableModel</tt> to add.
   */
  public void addVariable(VariableModel variableModel)
  {
    variables.put(variableModel.getName(),variableModel);
    variablesList.add(variableModel);
  }

  /**
   * Get a <tt>VariableModel</tt> by it's name.
   * @param variableName_p Desired variable model's name.
   * @return designated <tt>VariableModel</tt> or <code>null</code> if not found.
   */
  public VariableModel getVariable(String variableName_p)
  {
    return variables.get(variableName_p);
  }

  /**
   * Pretty output for this object's content.
   * @param out Output stream.
   * @param nbSpaces Number of spaces at the beginning of each line.
   */
  public void prettyPrint(PrintStream out, int nbSpaces)
  {
    for(int i=0;i<nbSpaces;i++) out.print(' ');
    out.println("Section ["+name+"]");
    for(Iterator<VariableModel> it_l=variablesList.iterator();it_l.hasNext();)
    {
      VariableModel variable_l=(it_l.next());
      variable_l.prettyPrint(out,nbSpaces+3);
    }
  }
}
