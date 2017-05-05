

import myUtil.ExpDistribution;
import myUtil.NormalDistribution;

/**
 * The Vehicle class represents one vehicle on the road in the traffic sim.  Since it is important that the times handed out to the 
 * vehicles for their arrival and their time through the gate are controlled, the class has a protected constructor, instead making
 * a public 'next' method available for generating a new vehicle, which makes sure that the arrival and gate times for the vehicle are
 * set using the appropriate distribution curves.  The basis for this code comes from the example provided in class.
 * @author Justin Lomelino
 *
 */
public class Vehicle implements Comparable{
	static protected ExpDistribution vehicleDistr;
	static protected NormalDistribution gateTimeDistr;
	static protected double currentTime, totalWaitingTime, totalGateTime, minimumGateTime, meanGTime;
	static protected int totalCount=0;
	static protected int ezPassCount=0;
	
	/**
	 * Called when a new simulation is started, in order to set the vehicle distribution curves for arrival times and gate times.  Also
	 * resets all counters to 0 for each new simulation.
	 * 
	 * @param meanWaitingForVehicleTime the mean time for a vehicle to arrive at the gate
	 * @param meanGateTime the mean time for the vehicle to be served at the gate and be on it's way, in seconds
	 * @param var the variance in gate service times, in seconds
	 * @param min the minimum time for service at the gate.  In our simulation, this is typically 1 second to represent a vehicle with EZPass
	 */
	static public void setDistribution(double meanWaitingForVehicleTime, double meanGateTime, double var, double min){
		vehicleDistr = new ExpDistribution(meanWaitingForVehicleTime);
		gateTimeDistr = new NormalDistribution(meanGateTime, var);
		totalCount = 0;
		ezPassCount = 0;
		currentTime = totalWaitingTime = totalGateTime = 0;
		minimumGateTime = min;
		meanGTime = meanGateTime;
	}
	
	/**
	 * this method generates a new Vehicle in the simulation, along with associated data like the time the vehicle will arrive
	 * and the time the vehicle will spend at the gate getting service.  It also checks to see if the service time is less than
	 * the mean gate time, and if it is, sets the service time to the minimum value to represent a vehicle with an EZPass.  Since
	 * the gate time is a Normal distribution curve, this implies that roughly half of all vehicles in the simulation will have an 
	 * EZPass.  The constructor for the Vehicle class is protected, so calling this method is the appropriate way to generate a new
	 * vehicle in the simulation, as this method will call the constructor according to the parameters of the assignment.
	 * @return a Vehicle, representing a new vehicle arriving in the simulation.
	 */
	static public Vehicle next(){
		currentTime = currentTime + vehicleDistr.next();
		double gateTime = gateTimeDistr.sample();
		//if the gateTime is less than the mean gateTime, the gateTime is set to 1.0 to represent a vehicle with an EZPass, as described in the assignment
		if(gateTime < meanGTime){
			gateTime = minimumGateTime;
			ezPassCount++;
		}
		else totalGateTime += gateTime;	//ignore EasyPass Vehicles in totalGateTime
		totalCount++;
		return new Vehicle(currentTime, gateTime);
	}
	
	/**
	 * returns the total of all waiting times done by all vehicles.
	 * @return a double representing the total time spent waiting by all vehicles in the simulation.
	 */
	public static double totalWaitingTime(){
		return totalWaitingTime;
	}
	
	/**
	 * returns the total of all waiting times done by all vehicles that do not have an EZPass
	 * @return a double representing the total time spent waiting by all vehicles in the simulation that do not have an EZPass
	 */
	public static double totalWaitingTimeWOEz(){
		return totalWaitingTime - (minimumGateTime * ezPassCount);
	}
	
	/**
	 * returns the average time spent by vehicles being serviced at a gate
	 * @return a double representing the average time spent by vehicles being serviced at a gate
	 */
	public static double averageGateTime(){
		return totalGateTime / (totalCount-ezPassCount);
	}
	
	/**
	 * returns the total count of all vehicles to pass through a gate in the simulation
	 * @return an integer representing the total number of vehicles to pass through a gate in the simulation
	 */
	public static int totalCount(){
		return totalCount;
	}
	
	/**
	 * returns the total count of all vehicles in the simulation with an EZPass, and therefor having a gate service time of 1 second
	 * @return an integer representing the total number of vehicles in the simulation with an EZPass
	 */
	public static int ezPassCount(){
		return ezPassCount;
	}
	
	protected double timeToArrive, timeBeginsToServe, serviceNeeded;
	
	/**
	 * A protected Constructor for the vehicle class.  Should only be called by Vehicle.next()
	 * @param timeToArrive a double representing the time the vehicle will arrive at the gates
	 * @param serviceNeeded a double representing the amount of time needed to pass through a gate
	 */
	protected Vehicle(double timeToArrive, double serviceNeeded){
		this.timeToArrive = timeToArrive;
		this.serviceNeeded = serviceNeeded;
		timeBeginsToServe = -1;
	}
	
	/**
	 * returns the time the vehicle will arrive at the gates in the simulation
	 * @return a double representing when the vehicle will arrive at the gates in the simulation, in seconds
	 */
	public double whenToArrive(){
		return timeToArrive;
	}
	
	/**
	 * sets the time that the vehicle starts to pass through a gate, and adds the time spent waiting by the vehicle to the total
	 * waiting time for all vehicles
	 * @param time a double representing the time that the vehicle is finished waiting and arrives at a gate to be served
	 */
	public void setBeginToServe(double time){
		timeBeginsToServe = time;
		totalWaitingTime += (this.timeBeginsToServe - this.timeToArrive);
	}
	
	/**
	 * returns the time that the vehicle will be finished waiting and being served at a gate, and thus leaving the simulation
	 * @return a double, represents the time that the vehicle will be leaving the simulation after waiting for a gate and then being served
	 */
	public double whenToLeave(){
		//since the time for the simulation starts at 0, something has gone wrong if the below conditional runs.
		if(timeBeginsToServe < 0){
			System.out.println("Error, timeBeginsToServe < 0!");
			int a=0, b=2/a;
			return 1E10/a;
		}
		return timeBeginsToServe + serviceNeeded;
	}

	/**
	 * this method is only included so that the SimpleListLk class in myUtil could be reused.  The SimpleListLk requires all list elements
	 * be comparable, but in our simulation comparing to vehicles is meaningless, so this function will always return the value 0.  This
	 * method should not be called, as it has no meaning or function in the simulation.
	 */
	@Override
	public int compareTo(Object arg0) {
		// Vehicle comparison is meaningless, this just exists to allow reuse of the SimpleListLk class.  DO NOT CALL!
		return 0;
	}

}
