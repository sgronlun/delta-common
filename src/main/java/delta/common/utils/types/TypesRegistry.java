package delta.common.utils.types;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import delta.common.utils.types.utils.TypesLoggers;

/**
 * Registry for types.
 * @author DAM
 */
public final class TypesRegistry
{
  private static final Logger _logger=TypesLoggers.getTypesLogger();

  private static TypesRegistry _instance;

  /**
   * Name of default types namespace.
   */
  public static final String DEFAULT_NAMESPACE="";
  /**
   * Separator to use between namespace and type.
   */
  private static final char NAMESPACE_AND_TYPE_SEPARATOR=':';

  private Map<String,TypesNamespace> _typeNamespaces;
  private TypesNamespace _default;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static TypesRegistry getInstance()
  {
    synchronized (TypesRegistry.class)
    {
      if (_instance==null)
      {
        _instance=new TypesRegistry();
      }
      return _instance;
    }
  }

  /**
   * Private constructor.
   */
  private TypesRegistry()
  {
    _typeNamespaces=new HashMap<String,TypesNamespace>();
    createDefaultNamespace();
  }

  private void createDefaultNamespace()
  {
    _default=new TypesNamespace(DEFAULT_NAMESPACE);
    registerTypesNamespace(_default);
    createBuiltInTypes();
  }

  private void createBuiltInTypes()
  {
    TypeClassesRegistry typeClassesRegistry=TypeClassesRegistry.getInstance();

    TypeClass booleanClass=typeClassesRegistry.getTypeClassByName(BooleanType.TYPE_NAME);
    // Boolean
    Type booleanType=booleanClass.buildType(BuiltInTypes.BOOLEAN);
    _default.registerType(booleanType);

    TypeClass stringClass=typeClassesRegistry.getTypeClassByName(StringType.TYPE_NAME);
    // String
    Type stringType=new StringType();
    stringType.setNameAndType(BuiltInTypes.STRING,stringClass);
    _default.registerType(stringType);

    TypeClass intClass=typeClassesRegistry.getTypeClassByName(IntegerType.TYPE_NAME);
    // Positive integer
    Type positiveIntType=intClass.buildType(BuiltInTypes.POSITIVE_INTEGER);
    positiveIntType.setParameterValue(IntegerType.PARAM_MIN,Integer.valueOf(0));
    _default.registerType(positiveIntType);
    // Full range integer
    Type intType=intClass.buildType(BuiltInTypes.INTEGER);
    _default.registerType(intType);

    TypeClass typeClass=typeClassesRegistry.getTypeClassByName(TypeType.TYPE_NAME);
    // Type
    Type typeType=typeClass.buildType(BuiltInTypes.TYPE);
    _default.registerType(typeType);
  }

  /**
   * Register a new types namespace.
   * @param typeClass Type class to register.
   * @return <code>true</code> upon successful registration, <code>false</code> otherwise.
   */
  private boolean registerTypesNamespace(TypesNamespace typeClass)
  {
    String key=typeClass.getName();
    TypesNamespace old=_typeNamespaces.get(key);
    if (old!=null)
    {
      _logger.warn("Types namespace ["+key+"] already defined.");
      return false;
    }
    _typeNamespaces.put(key,typeClass);
    return true;
  }

  /**
   * Resolve a namespace and type.
   * @param defautNamespace Namespace to use if it is not specified.
   * @param typeSpecification A type name or a namespace/type_name specification.
   * @return A string array of length 2 that contains the resolved namespace and type name.
   */
  private String[] resolveNamespaceAndType(String defautNamespace, String typeSpecification)
  {
    String namespace=defautNamespace;
    if (namespace==null)
    {
      namespace=DEFAULT_NAMESPACE;
    }
    String typeName=typeSpecification;
    {
      int separator=typeSpecification.indexOf(NAMESPACE_AND_TYPE_SEPARATOR);
      if (separator!=-1)
      {
        namespace=typeSpecification.substring(0,separator);
        typeName=typeSpecification.substring(separator+1);
      }
    }
    return new String[]{namespace,typeName};
  }

  /**
   * Get a types registry by name.
   * @param name Types registry name.
   * @return A types registry or <code>null</code> if not found.
   */
  public TypesNamespace getTypesNamespace(String name)
  {
    TypesNamespace ret=_typeNamespaces.get(name);
    return ret;
  }

  /**
   * Resolve a type.
   * @param typeSpecification A type name or a namespace:type_name specification.
   * @return A type or <code>null</code> if not found.
   */
  public Type getType(String typeSpecification)
  {
    Type ret=getType(DEFAULT_NAMESPACE,typeSpecification);
    return ret;
  }

  /**
   * Resolve a type.
   * @param defaultNamespace Namespace to use if it is not specified.
   * @param typeSpecification A type name or a namespace/type_name specification.
   * @return A type or <code>null</code> if not found.
   */
  public Type getType(String defaultNamespace, String typeSpecification)
  {
    Type ret=null;
    String[] nameSpaceAndType=resolveNamespaceAndType(defaultNamespace,typeSpecification);
    String namespace=nameSpaceAndType[0];
    String typeName=nameSpaceAndType[1];

    // Find namespace
    TypesNamespace typesNamespace=getTypesNamespace(namespace);
    if (typesNamespace!=null)
    {
      ret=typesNamespace.getTypeByName(typeName);
      if (ret==null)
      {
        _logger.error("Unknown type ["+typesNamespace.getName()+NAMESPACE_AND_TYPE_SEPARATOR+typeName+"]");
      }
    }
    else
    {
      _logger.error("Unknown types namespace ["+namespace+"]");
    }
    return ret;
  }
}
