package delta.common.utils.units;

/**
 * @author DAM
 */
public class SIUnit extends Unit
{
  private String _symbol;

  SIUnit(String symbol, PhysicalPhenomenon physicalPhenomenon)
  {
    super(physicalPhenomenon);
    _symbol=symbol;
  }

  @Override
  public String getSymbol()
  {
    return _symbol;
  }
}
