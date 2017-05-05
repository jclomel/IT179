import shapes.*;

public class Asg1 {

	public static void showShape(Shape x) {
		System.out.println(x.toString()); 
		System.out.printf("%15s:%8.4f%n","Perimeter",x.getPerimeter()); 
		System.out.printf("%15s:%8.4f%n","Surface Area",x.getSurfaceArea()); 
		if (x instanceof ThreeD) {
			System.out.printf("%15s:%8.4f%n","Volumn:",((ThreeD)x).getVolume());
		}
		System.out.println();
	}
	
	
	public static void main(String[] args) {
		
		Shape a;

		double x, y, z;
		x = Double.parseDouble(args[0]);
		y = Double.parseDouble(args[1]);
		z = Double.parseDouble(args[2]);
		
		a = new Circle(x);
		showShape(a);
		a = new Rectangle(x,y);		
		showShape(a);
		a = new Cylinder(x,y);
		showShape(a);
		a = new RectanglePrism(x,y,z);		
		showShape(a);
			
		
	}

}
