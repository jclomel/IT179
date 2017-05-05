package shapes;

public class Cylinder extends Circle implements ThreeD {
	
	private static final String SHAPE_NAME = "Cylinder";
	private double depth;

	/**
	 * Constructs a Cylinder object using the default size for the radius and depth.
	 */
	public Cylinder() {
		this(Shape.DEFAULT_SIZE, Shape.DEFAULT_SIZE);
	}

	/**
	 * Constructs a Cylinder object using the radius and depth parameters supplied by the user.
	 * Supplied parameters must be greater than 0.
	 * @param radius a double representing the radius of the new cylinder.
	 * @param depth a double representing the depth of the new cylinder.
	 */
	public Cylinder(double radius, double depth) {
		super(radius);
		setShapeName(SHAPE_NAME);
		if(depth > 0)
			setDepth(depth);
		else{
			setDepth(Shape.DEFAULT_SIZE);
			throw new IllegalArgumentException("the depth must be > 0.");
		}
	}

	/**
	 * Returns the depth of the Cylinder object.
	 * @return a double representing the depth of the cylinder.
	 */
	@Override
	public double getDepth() {
		return depth;
	}

	/**
	 * Sets the depth of the Cylinder object to a user-supplied value.
	 * The supplied depth must be . 0.
	 * @param theDepth the new depth value of the cylinder.
	 */
	@Override
	public void setDepth(double theDepth) {
		if(theDepth > 0)
			this.depth = theDepth;
		else{
			throw new IllegalArgumentException("the depth must be > 0.");
		}
	}

	/**
	 * Returns the volume of the Cylinder object.
	 * @return a double representing the volume of the cylinder.
	 */
	@Override
	public double getVolume() {
		return getSurfaceArea() * depth;
	}
	
	/**
	 * Returns a String representation of the Cylinder object.
	 * @return a String representation of the cylinder object.
	 */
	@Override
	public String toString(){
		return(this.getShapeName() + ": radius = " + this.getRadius() + ", depth = " + this.depth);
	}

}
