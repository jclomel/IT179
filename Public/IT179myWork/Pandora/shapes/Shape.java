package shapes;

public abstract class Shape {
  /**
   * The default size used in constructing a shape.
   */
  protected static final double DEFAULT_SIZE = ( double ) 1.0;
  protected static final String DEFAULT_NAME = "Unknown";
  
  private String shapeName;

  /**
   * Construct a generic instance of unknown shape type.
   */
  public Shape() {
	  this.shapeName = DEFAULT_NAME;
  }

  /**
   * Construct a Shape whose name is specified in the argument.
   * @param name the name of this kind of shape
   */
  public Shape( String name ) {
	  setShapeName( name );
  }

  /**
   * Set the shape name for this Shape.
   * @param name the name of this kind of shape
   */
  protected void setShapeName( String name ) {
	  shapeName = new String( name );
  }

  /**
   * Get the name of this kind of shape.
   * @return the name of this shape
   */
  public String getShapeName() {
    return shapeName;
  }

  /**
   * Get the surface area of this Shape, but doesn't know how to compute it, hence abstract. 
   * @return the surface area of this Shape
   */
  public abstract double getSurfaceArea();

  /**
   * Get the perimeter of this Shape, but doesn't know how to compute it, hence abstract. 
   * @return the perimeter of this Shape
   */
  public abstract double getPerimeter();
}
