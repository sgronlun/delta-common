package delta.common.utils.units;

/**
 * @author DAM
 */
public class PhysicalPhenomenon
{
  private String _code;

  private Unit _preferredUnit;

  PhysicalPhenomenon(String code, Unit preferredUnit)
  {
    _code=code;
    _preferredUnit=preferredUnit;
  }

  public String getCode()
  {
    return _code;
  }

  public Unit getPreferredUnit()
  {
    return _preferredUnit;
  }
}
