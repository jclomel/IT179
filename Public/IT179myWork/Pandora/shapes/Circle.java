package shapes;

public class Circle extends Shape {
	
	private double radius;
	private static final String SHAPE_NAME = "Circle";

	/**
	 * Construct a Circle object using the default shape size for the radius.
	 */
	public Circle() {
		this(Shape.DEFAULT_SIZE);
	}

	/**
	 * Construct a Circle object using the user-supplied radius attribute.
	 * @param radius the radius of the circle to be constructed.  Must be greater than 0.
	 */
	public Circle(double radius) {
		super(SHAPE_NAME);
		if(radius > 0)
			setRadius(radius);
		else {
			setRadius(Shape.DEFAULT_SIZE);
			throw new IllegalArgumentException("The radius must be > 0.");
		}
	}

	/**
	 * Returns the radius of the Circle object.
	 * @return a double representing the radius of the circle.
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Sets the radius of the Circle object to the user-supplied value.
	 * @param radius the new radius of the circle.  Must be greater than 0.
	 */
	public void setRadius(double radius) {
		if(radius > 0)
			this.radius = radius;
		else{
			throw new IllegalArgumentException("The radius must be > 0.");
		}
	}

	/**
	 * Returns the area of the Circle object.
	 * @return a double representing the area of the circle.
	 */
	@Override
	public double getSurfaceArea() {
		return Math.PI * (radius * radius);
	}

	/**
	 * Returns the circumference of the Circle object.
	 * @return a double representing the circumference of the circle.
	 */
	@Override
	public double getPerimeter() {
		return Math.PI * (2 * radius);
	}
	
	/**
	 * Returns a String representation of the Circle object.
	 * @return a String representing the circle object.
	 */
	@Override
	public String toString(){
		return (this.getShapeName() + ": radius = " + this.radius);
	}
	
	

}
