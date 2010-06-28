package delta.common.utils.units;

/**
 * @author DAM
 */
public abstract class Unit
{
  private PhysicalPhenomenon _physicalPhenomenon;

  public abstract String getSymbol();

  protected Unit(PhysicalPhenomenon physicalPhenomenon)
  {
    _physicalPhenomenon=physicalPhenomenon;
  }

  public PhysicalPhenomenon getPhysicalPhenomenon()
  {
    return _physicalPhenomenon;
  }
}
