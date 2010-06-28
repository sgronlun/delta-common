package delta.common.utils.graph.gui.swing;

import java.awt.Dimension;
import java.awt.Rectangle;

/**
 * Zoom transformer that manages independant x and y scales.
 * @author DAM
 */
public class ZoomTransformer
{
  /**
   * Horizontal zoom factor.
   */
  private float _xFactor;

  /**
   * Vertical zoom factor.
   */
	private float _yFactor;

	/**
   * Default constructor (zoom factors are set to 1.0).
	 */
  public ZoomTransformer()
	{
		_xFactor=1;
		_yFactor=1;
	}

  /**
   * Constructor.
   * @param factor zoom factor for both horizontal and vertical axes.
   */
  public ZoomTransformer(float factor)
	{
		_xFactor=factor;
		_yFactor=factor;
	}

  /**
   * Full constructor.
   * @param xfactor horizontal factor.
   * @param yfactor vertical factor.
   */
  public ZoomTransformer(float xfactor, float yfactor)
	{
		_xFactor=xfactor;
		_yFactor=yfactor;
	}

	/**
   * Change both horizontal and vertical factors to the same value.
   * @param newFactor new factor to use.
	 */
  public void changeFactor(float newFactor)
	{
		_xFactor=newFactor;
		_yFactor=newFactor;
	}

  /**
   * Change horizontal and vertical factors.
   * @param newXFactor new horizontal factor.
   * @param newYFactor new vertical factor.
   */
  public void changeFactors(float newXFactor, float newYFactor)
	{
		_xFactor=newXFactor;
		_yFactor=newYFactor;
	}

	/**
   * Get the horizontal factor.
   * @return the horizontal factor.
	 */
  public float getXFactor() { return _xFactor; }

  /**
   * Get the vertical factor.
   * @return the vertical factor.
   */
  public float getYFactor() { return _yFactor; }

	/**
   * Apply zoom factors to the specified rectangle <code>r</code>.
   * @param r source rectangle.
   * @return a transformed rectangle.
	 */
  public Rectangle transform(Rectangle r)
	{
		r=new Rectangle((int)(r.x*_xFactor),(int)(r.y*_yFactor),(int)(r.width*_xFactor),(int)(r.height*_yFactor));
		return r;
	}

  /**
   * Apply inverse zoom factors to the specified rectangle <code>r</code>.
   * @param r source rectangle.
   * @return a transformed rectangle.
   */
  public Rectangle inverseTransform(Rectangle r)
	{
		r=new Rectangle((int)(r.x/_xFactor),(int)(r.y/_yFactor),(int)(r.width/_xFactor),(int)(r.height/_yFactor));
		return r;
	}

  /**
   * Apply zoom factors to the specified dimension <code>d</code>.
   * @param d source dimension.
   * @return a transformed dimension.
   */
	public Dimension transform(Dimension d)
	{
		return new Dimension((int)(d.width*_xFactor),(int)(d.height*_yFactor));
	}

  /**
   * Apply inverse zoom factors to the specified dimension <code>d</code>.
   * @param d source dimension.
   * @return a transformed dimension.
   */
	public Dimension inverseTransform(Dimension d)
	{
		return new Dimension((int)(d.width/_xFactor),(int)(d.height/_yFactor));
	}
}
