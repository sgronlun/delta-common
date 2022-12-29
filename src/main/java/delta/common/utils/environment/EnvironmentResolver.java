package delta.common.utils.environment;

/**
 * Resolve environment values in input strings.
 * @author DAM
 */
public class EnvironmentResolver
{
  /**
   * Resolve all environment variables/properties in the given string.
   * @param input Input string.
   * @return the resolved string.
   */
  public static String resolveEnvironment(String input)
  {
    String ret=input.replace("${user.home}",System.getProperty("user.home"));
    return ret;
  }
}
