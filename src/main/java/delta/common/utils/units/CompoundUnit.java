package delta.common.utils.units;

/**
 * @author DAM
 */
public class CompoundUnit extends Unit
{
  private String _simpleSymbol;
  //private String _complexSymbol;

  private SIUnit[] _units;
  private int[] _powers;

  public CompoundUnit(PhysicalPhenomenon physicalPhenomenon, String simpleSymbol, SIUnit[] units, int[] powers)
  {
    super(physicalPhenomenon);
    _simpleSymbol=simpleSymbol;
    if (units==null) throw new IllegalArgumentException();
    if (powers==null) throw new IllegalArgumentException();
    if (units.length!=powers.length) throw new IllegalArgumentException();
    // todo check for duplicate SIUnits
    _units=new SIUnit[units.length];
    System.arraycopy(units,0,_units,0,units.length);
    _powers=new int[powers.length];
    System.arraycopy(powers,0,_powers,0,powers.length);
  }

  @Override
  public String getSymbol()
  {
    return _simpleSymbol;
  }
}
