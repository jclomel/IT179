package shapes;


public final class RectanglePrism extends Rectangle implements ThreeD {
	private double depth;

/**
   * Construct a Cube object using the default size
   * for its length, height and depth.
   */
  public RectanglePrism() {
    this( Shape.DEFAULT_SIZE, Shape.DEFAULT_SIZE, Shape.DEFAULT_SIZE );
  }

  /**
   * Construct a Cube object using the arguments.
   * If an argument is {@literal <=} 0, the default size specified in Shape
   * is used instead.
   * @param theLength the length of this Cube; must be {@literal >} 0
   * @param theHeight the height of this Cube; must be {@literal >} 0
   * @param theDepth the depth of this Cube; must be {@literal >} 0
   */
  public RectanglePrism( double theLength, double theHeight, double theDepth ) {
    super( theLength, theHeight );
    if ( theDepth <= 0.0 ) {
      setDepth( Shape.DEFAULT_SIZE );
    }
    else {
      setDepth( theDepth );
    }
    setShapeName( "RectanglePrism" );
  }

  /* overridden methods inherited from Rectangle */
  /**
   * Get the surface area of this Cube.
   * @return the surface area of this Cube
   */
  public double getSurfaceArea() {
    return super.getSurfaceArea() * 2 // front and back
        + this.depth * getHeight() * 2 // sides
        + this.depth * getLength() * 2; // top and bottom
  }

  /**
   * Get the perimeter of this Cube.
   * @return the perimeter of this Cube
   */
  public double getPerimeter() {
    return 2 * super.getPerimeter() + 4 * this.depth;
  }

  /**
   * Get the volume of this RectanglePrism.
   * @return the volume of this RectanglePrism
   */
  public double getVolume() {
    return super.getSurfaceArea() * this.depth;
  }

  /**
   * Get the depth of this Cube.
   * @return the depth of this Cube
   */
  public double getDepth() {
    return this.depth;
  }

  /**
   * Set the depth of this Cube.
   * @param theDepth the new depth of this Cube; must be {@literal >} 0
   */
  public void setDepth( double theDepth ) {
      this.depth = theDepth;
  }

  /* overridden methods inherited from Object */
  /**
   * Returns a String object representing this Cube's value.
   * Overridden from Object.
   * @return a string representation of this object
   */
  public String toString() {
    return getShapeName() + ": " + "length = " + getLength() +
        ", height = " + getHeight() + ", depth = " + getDepth();
  }
}
