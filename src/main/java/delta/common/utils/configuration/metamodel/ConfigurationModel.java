package delta.common.utils.configuration.metamodel;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Represents the model for a <tt>Configuration</tt>.
 * @author DAM
 */
public class ConfigurationModel
{
  private Hashtable<String,TypeModel> _types;
  private Hashtable<String,SectionModel> _sections;
  private ArrayList<SectionModel> _sectionsList;
  private ArrayList<String> _variablesList;

  /**
   * Complete constructor.
   */
  public ConfigurationModel()
  {
    _types=new Hashtable<String,TypeModel>();
    _sections=new Hashtable<String,SectionModel>();
    _sectionsList=new ArrayList<SectionModel>();
  }

  /**
   * Register a new <tt>TypeModel</tt>.
   * @param type_p <tt>TypeModel</tt> to register.
   */
  void addType(TypeModel type_p)
  {
    _types.put(type_p.getName(),type_p);
  }

  /**
   * Get a <tt>TypeModel</tt> by it's name.
   * @param typeName <tt>TypeModel</tt>'s name.
   * @return A <tt>TypeModel</tt> or <tt>null</tt>.
   */
  TypeModel getTypeModel(String typeName)
  {
    return _types.get(typeName);
  }

  /**
   * Get the 'default' type model.
   * @return the 'default' type model.
   */
  TypeModel getDefaultTypeModel()
  {
    return getTypeModel("NONE");
  }

  /**
   * Get a list that contains the fully qualified names for each variable
   * managed by this model. A variable's fully qualified name follow the
   * sectionName@variableName pattern.
   * @return a <tt>List</tt> of fully qualified variable names.
   */
  public List<String> getVariables()
  {
    if (_variablesList==null)
    {
      computeVariables();
    }
    return _variablesList;
  }

  /**
   * Get the model for a variable, designated by it's <code>sectionName</code>
   * and it's <code>variableName</code>.
   * @param sectionName Targeted section's name.
   * @param variableName Targeted variable's name.
   * @return a <tt>VariableModel</tt> for the targeted variable or
   * <code>null</code> if not found.
   */
  public VariableModel getVariable(String sectionName, String variableName)
  {
    VariableModel return_l=null;
    SectionModel model_l=getSection(sectionName);
    if (model_l!=null)
    {
      return_l=model_l.getVariable(variableName);
    }
    return return_l;
  }

  /**
   * Get the model for a section, designated by it's <code>sectionName</code>.
   * @param sectionName Targeted section's name.
   * @return a <tt>VariableModel</tt> for the targeted variable or
   * <code>null</code> if not found.
   */
  private SectionModel getSection(String sectionName)
  {
    return _sections.get(sectionName);
  }

  /**
   * Compute all fully qualified variable names.
   */
  private void computeVariables()
  {
    _variablesList=new ArrayList<String>();
    for(Iterator<SectionModel> it_l=_sectionsList.iterator();it_l.hasNext();)
    {
      SectionModel section_l=(it_l.next());
      ArrayList<VariableModel> variables_l=section_l.getVariables();
      for(Iterator<VariableModel> it2_l=variables_l.iterator();it2_l.hasNext();)
      {
        VariableModel variable_l=(it2_l.next());
        String newEntry_l=section_l.getName()+"@"+variable_l.getName();
        _variablesList.add(newEntry_l);
      }
    }
  }

  /**
   * Add a new <tt>SectionModel</tt>.
   * @param section_p <tt>SectionModel</tt> to add.
   */
  void addSection(SectionModel section_p)
  {
    _sections.put(section_p.getName(),section_p);
    _sectionsList.add(section_p);
  }

  /**
   * Pretty output for this object's content.
   * @param out Output stream.
   * @param nbSpaces Number of spaces at the beginning of each line.
   */
  public void prettyPrint(PrintStream out, int nbSpaces)
  {
    for(int i=0;i<nbSpaces;i++)
      out.print(' ');
    out.println("Types : ");
    for(Iterator<?> it_l=_types.entrySet().iterator();it_l.hasNext();)
    {
      Map.Entry entry_l=(Map.Entry)(it_l.next());
      TypeModel type_l=(TypeModel)(entry_l.getValue());
      type_l.prettyPrint(out,nbSpaces+3);
    }
    for(int i=0;i<nbSpaces;i++)
      out.print(' ');
    out.println("Sections : ");
    for(Iterator<SectionModel> it_l=_sectionsList.iterator();it_l.hasNext();)
    {
      SectionModel section_l=(it_l.next());
      section_l.prettyPrint(out,nbSpaces+3);
    }
  }
}
