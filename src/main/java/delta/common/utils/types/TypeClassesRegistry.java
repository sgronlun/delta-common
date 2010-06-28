package delta.common.utils.types;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import delta.common.utils.types.io.xml.TypeClassXMLParser;
import delta.common.utils.types.utils.TypesLoggers;
import delta.common.utils.url.URLTools;

/**
 * Registry for type classes.
 * @author DAM
 */
public final class TypeClassesRegistry
{
  private static final Logger _logger=TypesLoggers.getTypesLogger();

  private static TypeClassesRegistry _instance;

  private Map<String,TypeClass> _typeClasses;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static TypeClassesRegistry getInstance()
  {
    synchronized (TypeClassesRegistry.class)
    {
      if (_instance==null)
      {
        _instance=new TypeClassesRegistry();
      }
      return _instance;
    }
  }

  /**
   * Private constructor.
   */
  private TypeClassesRegistry()
  {
    _typeClasses=new HashMap<String,TypeClass>();
    parseBuiltInTypeClasses();
  }

  /**
   * Register a new type class.
   * @param typeClass Type class to register.
   * @return <code>true</code> upon successfull registration, <code>false</code> otherwise.
   */
  public boolean registerTypeClass(TypeClass typeClass)
  {
    String key=typeClass.getName();
    TypeClass old=_typeClasses.get(key);
    if (old!=null)
    {
      _logger.warn("Type class ["+key+"] already defined.");
      return false;
    }
    _typeClasses.put(key,typeClass);
    return true;
  }

  private void parseBuiltInTypeClasses()
  {
    URL url=URLTools.getFromClassPath("typeClasses.xml",this);
    TypeClassXMLParser parser=new TypeClassXMLParser();
    List<TypeClass> classes=parser.parseBuildInClasses(url);
    TypeClass typeClass;
    for(Iterator<TypeClass> it=classes.iterator();it.hasNext();)
    {
      typeClass=it.next();
      registerTypeClass(typeClass);
    }
  }

  /**
   * Get a type class by name.
   * @param name Type class name.
   * @return A type class or <code>null</code> if not found.
   */
  public TypeClass getTypeClassByName(String name)
  {
    return _typeClasses.get(name);
  }
}
