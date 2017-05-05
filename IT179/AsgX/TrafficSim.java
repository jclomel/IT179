

import java.util.ArrayList;

import myUtil.LQueue;

/**
 * runs the toll gate simulation.  The parameters supplied when the simulate method is called set the parameters for the simulation.
 * This code is based on the example provided in class, but has been modified to accommodate more than 2 toll gate lanes, as well as 
 * vehicles with an EZPass for quick toll gate transit.
 * @author Justin Lomelino
 *
 */
public class TrafficSim {
	
	static double averageWaiting = 0;
	static double averageGate = 0;
	static int ezPassCount = 0;

	//Constructor is empty, the simulation is set up when the simulate method is called.
	public TrafficSim() {
	}

	/**
	 * This method runs the toll gate simulation.  It can accommodate any number of toll gate lanes, and has vehicles generated according
	 * to the distributions described in the Vehicle class.  New vehicles arriving are generated up to the value provided in timeToSimulate,
	 * after which no more vehicles arrive but vehicles remaining in a toll gate lane or waiting on the road are resolved before the simulation
	 * finishes.
	 * 
	 * The algorithm is this:  First, generate all of the vehicles that will arrive for the total time of the simulation.  Based on the time
	 * that the vehicles arrive, shuffle them into the shortest lane available, or if all are full, into a queue representing vehicles on the
	 * open road waiting for a lane.  While we have vehicles left, first check if all the lanes are empty, and if so send the vehicle to a random
	 * lane to be served.  Otherwise, choose the shortest available lane and send the vehicle there.  If all lanes are full, the vehicle goes
	 * into the queue representing the open road.  Now, we check if there are vehicles on the open road, and if there are, see if there is a lane
	 * that has room for it.  If there is, move the vehicle into that lane.  Finally, select the lane that will finish servicing a vehicle first
	 * and process that lane (serve the vehicle and move the next vehicle in the lane to the toll gate to be serviced).  Continue this process
	 * until all vehicles have been moved into lanes or onto the open road, and then process those vehicles left on the road and in lanes.  Finally,
	 * gather the information required by the assignment about the simulation and output it to the console.
	 * 
	 * @param meanWaitingForVehicleTime a double representing the mean time in seconds that it takes for a new vehicle to arrive at the gates
	 * @param meanGateTime a double representing the mean time in seconds for service at a gate
	 * @param var a double representing the variance of the mean time in seconds for service at a gate
	 * @param timeToSimulate a double representing the amount of time in seconds to generate new vehicles in the simulation
	 * @param lineNo an integer representing the number of toll gate lanes in the simulation
	 */
	public static void simulate(double meanWaitingForVehicleTime, double meanGateTime, double var, double timeToSimulate, int lineNo){
		//the minimum amount of time it takes for a vehicle to transit through a toll gate.  If a vehicle takes this long to transit, it is assumed to have an EZPass
		double minimumGateTime = 1.0;
		//set up the distributions for the time vehicles arrive and the time they take to transit through a toll gate
		Vehicle.setDistribution(meanWaitingForVehicleTime, meanGateTime, var, minimumGateTime);
		
		LQueue<Vehicle> vehiclePool = new LQueue<Vehicle>();	//a pool of all vehicles to arrive in the simulated time
		
		ArrayList<LQueue<Vehicle>> lanePool = new ArrayList<LQueue<Vehicle>>();		//a pool of all the toll gate lanes
		for(int i=1;i<=lineNo;i++){
			lanePool.add(new LQueue<Vehicle>(20));
		}
		
		//a queue to represent the vehicles waiting on the road to enter into a toll gate lane, since the toll gate lanes are limited to 20 vehicles at once
		LQueue<Vehicle> onTheRoad = new LQueue<Vehicle>();
		//a queue representing a 'selected' toll gate lane when dealing with a vehicle
		LQueue<Vehicle> aLine;
		
		int totalCount=0;		//the total number of vehicles to transit the gates
		int maxLength=0;		//the maximum length of a queue for a toll gate, including vehicles waiting on the open road
		double clock=0;			//the 'clock' of the simulation used to determine when vehicles arrive and leave
		
		//generate the first vehicle in the simulation
		Vehicle vehicle = Vehicle.next();
		//while there is time left on the 'clock', continue to generate new vehicles and add them to the pool to be processed
		while(vehicle.whenToArrive() <= timeToSimulate){
			vehiclePool.offer(vehicle);
			vehicle = Vehicle.next();
		}
		totalCount = vehiclePool.size();		//set the total count equal to the number of vehicles in the vehicle pool to represent the number of vehicles to run through the simulation
		
		//continue the simulation until the vehicle pool is empty
		while(!vehiclePool.empty()){
			boolean allLanesEmpty = checkAllLanesEmpty(lanePool);
			
			// all lanes are empty of Vehicles
			if(allLanesEmpty){
				vehicle = vehiclePool.poll();		//get the next vehicle in the vehicle pool
				clock = vehicle.whenToArrive();		//set the clock to the time this vehicle arrives
				LQueue<Vehicle> selectedLane = selectRandomLane(lanePool);		//select a random lane to have the vehicle enter
				selectedLane.offer(vehicle);		
				vehicle.setBeginToServe(clock);		//set the start of the gate service time for the vehicle to the current clock since the gate lanes are empty
				continue;
			}
			
			//we want to select the lane with the vehicle that is ready to leave service soonest to be the next lane to deal with
			aLine = nextToLeave(lanePool);
			
			//the next vehicle arrives before the gate service finishes
			if(vehiclePool.peek().whenToArrive() < aLine.peek().whenToLeave()){
				vehicle = vehiclePool.poll();
				clock = vehicle.whenToArrive();
				aLine = selectShortestLane(lanePool);		//vehicles always enter the shortest lane if they can
				boolean lineSuccess = aLine.offer(vehicle);		//offer the vehicle to the lane.  If it is full, we will deal with it below
				//the shortest lane is full, which means all lanes are full, which means this vehicle will be waiting on the open road
				if(!lineSuccess){
					onTheRoad.offer(vehicle);
					maxLength = (maxLength < onTheRoad.size() ? onTheRoad.size() : maxLength);
				}
				//if there is only one vehicle in the lane, begin gate service for that vehicle
				if(aLine.size() == 1){
					vehicle.setBeginToServe(clock);
				}
				continue;
			}
			
			//there is at least one Vehicle waiting on the road
			else if(onTheRoad.size() > 0 ){
				LQueue<Vehicle> aLane = selectShortestLane(lanePool);		//select the shortest lane for the next vehicle to enter
				//lanes can only hold 20 vehicles, so only enter the lane if it is less than 20 vehicles
				if(aLane.size() < 20){
					vehicle = onTheRoad.poll();		//select the first vehicle waiting on the open road
					aLane.offer(vehicle);			//the vehicle enters the shortest lane
					maxLength = (maxLength < onTheRoad.size() ? onTheRoad.size() : maxLength);		//adjust the maxLength value (although this likely never changes here)
					continue;
				}
				
			}
			//none of the above special cases are true, so deal with the vehicle next leaving a lane
			vehicle = aLine.poll();
			clock = vehicle.whenToLeave();
			if(!aLine.empty()) aLine.peek().setBeginToServe(clock);		//if that lane isn't empty, the vehicle will be served
		}
		
		//vehicle pool is empty, simulation time has been reached, resolve the vehicles still in a lane or on the open road
		aLine = nextToLeave(lanePool);
		//keep dealing with vehicles until the road is empty and all lanes are cleared
		while(aLine != null && onTheRoad.size() > 0){
			//there are still vehicles on the road, so shuffle them into a lane if possible
			if(onTheRoad.size() > 0 ){
				LQueue<Vehicle> aLane = selectShortestLane(lanePool);		//vehicles always enter the shortest lane, if they can
				//if the lane is smaller than 20 vehicles, the vehicle leaves the open road to enter the lane
				if(aLane.size() < 20){
					vehicle = onTheRoad.poll();
					aLane.offer(vehicle);
				}
			}
			//process the next lane that has a vehicle leaving
			vehicle = aLine.poll();
			if(vehicle != null) clock = vehicle.whenToLeave();		//if the lane isn't empty, serve the vehicle and advance the clock
			if(!aLine.empty()) aLine.peek().setBeginToServe(clock);
			//if the lane empties out, we choose the next lane to deal with instead
			else aLine = nextToLeave(lanePool);
		}
		//at this point, all vehicles in the simulation have been processed.  Let's gather some required information about the simulation
		averageWaiting = Vehicle.totalWaitingTime()/totalCount;		//average time spent waiting for a toll gate
		averageGate = Vehicle.averageGateTime();					//average time spent being served at a toll gate
		ezPassCount = Vehicle.ezPassCount();						//the number of vehicles that had an EZPass, and therefore a gateTime of 1 second
		
		//display the required statistics of the simulation.  The header to this table is generated by the method used for simulating, so it isn't displayed on each simulation pass
		System.out.printf("\n     1/%-8.3f%8d %10d %17s %15.3f %15s %10d\n", meanWaitingForVehicleTime, totalCount, ezPassCount, timeFormat(averageWaiting), averageGate, timeFormat(averageWaiting+averageGate), maxLength);	
	}
	
	/**
	 * returns the lane that will be the next to have a served vehicle leave it from the available lanes in the simulation.  This is
	 * useful to find the lane that should be processed next and also likely the next lane to have a vehicle enter it.
	 * 
	 * @param lanePool an ArrayList of LQueue<Vehicle> representing all of the lanes in the simulation
	 * @return a LQueue<Vehicle> representing the next lane that will be finished serving a vehicle
	 */
	static LQueue<Vehicle> nextToLeave(ArrayList<LQueue<Vehicle>> lanePool){
		LQueue<Vehicle> targetLane = null;			//will hold the lane to return

		//if all the lanes are empty, none of them are the next to have a vehicle finish being served at the toll gate
		if(checkAllLanesEmpty(lanePool)) return null;

		//iterate through all of the lanes in the pool, checking if they are the next lane to have a vehicle leave
		for(int i=0;i<=lanePool.size()-1;i++){
			//the lane is only a candidate for being the target lane if it is not empty
			if(!lanePool.get(i).empty()){
				double whenToLeave = lanePool.get(i).peek().whenToLeave();
				
				targetLane = lanePool.get(i);
				//check each lane, seeing if the whenToLeave is smaller, if so, that lane is the new target
				for(LQueue<Vehicle> lane:lanePool){
					if(!lane.empty()){
						if(lane.peek().whenToLeave() < whenToLeave){
							whenToLeave = lane.peek().whenToLeave();
							targetLane = lane;
						}
					}
				}
			}
		}
		return targetLane;
	}

	/**
	 * takes in a time in seconds and formats it to be displayed hh:mm:ss to be more human-readable
	 * @param T a double representing the time, in seconds
	 * @return a String representing the human-readable format of the time in the format hh:mm:ss
	 */
	public static String timeFormat(double T){
		String str = "";
		long s, m, t=Math.round(T);
		s = t%60;
		str = s+str;
		if(s<10) str = "0"+str;
		t = t/60;
		m = t%60;
		str = m + ":" + str;
		if(m<10) str = "0"+str;
		t = t/60;
		return t+":"+str;
	}
	
	/**
	 * select a random lane from the lane pool, and return it
	 * @param lanePool an ArrayList of LQueue<Vehicle> representing all lanes in the simulation
	 * @return a LQueue<Vehicle> representing a random lane from the lane pool
	 */
	public static LQueue<Vehicle> selectRandomLane(ArrayList<LQueue<Vehicle>> lanePool){
		double laneSelector = Math.random();		//the value used to decide which lane to choose
		double laneFraction = 1.0 / lanePool.size();		//divide the available numbers up into equal portions, one for each lane
		LQueue<Vehicle> selectedLane = null;		//holds the lane to return as the randomly selected lane
		
		for(int i=0; i<=lanePool.size()-1; i++){
			// selector is greater than the previous lane's value but less than it's own value
			if((laneSelector > laneFraction * i)&&(laneSelector < laneFraction * (i+1))){
				selectedLane = lanePool.get(i);
			}
		}
		//as long as we chose a lane, return it as the selected lane
		if(selectedLane != null) return selectedLane;
		else throw new NullPointerException();		//there was an error, no lane was chosen.  Should not normally run ever.
	}

	/**
	 * selects the shortest lane in the simulation from the available lanes, based on the number of vehicles in the lane.
	 * @param lanePool an ArrayList of LQueue<Vehicle> representing the lanes available in the simulation
	 * @return a LQueue<Vehicle> representing the shortest lane based on number of vehicles in it.  If two lanes are equal, the first lane checked is the one returned
	 */
	public static LQueue<Vehicle> selectShortestLane(ArrayList<LQueue<Vehicle>> lanePool){
		LQueue<Vehicle> selectedLane = lanePool.get(0);		//start by choosing the first lane in the pool
		//iterate through the lanes in the pool, checking each
		for(int i=1; i<=lanePool.size()-1;i++){
			if(lanePool.get(i).size()<selectedLane.size()){		//if the size of a pool is less than the already selected lane, it becomes the new selected lane
				selectedLane = lanePool.get(i);
			}
		}
		return selectedLane;		//return the selected lane, which will be the shortest lane, or in case of a tie, the first lane in the tie
	}
	
	/**
	 * checks to see if all the lanes in the simulation are empty.  Returns true if all the lanes are empty
	 * @param lanePool an ArrayList of LQueue<Vehicle> representing all the toll gate lanes in the simulation
	 * @return a boolean, true if all the lanes are empty, otherwise false.
	 */
	public static boolean checkAllLanesEmpty(ArrayList<LQueue<Vehicle>> lanePool){
		//iterate through all the lanes in the simulation, checking to see if they are empty
		for(int i=0;i<=lanePool.size()-1;i++){

			if(!lanePool.get(i).empty()) return false;		//if a lane is not empty, then all lanes are not empty, so return false
		}
		return true;		//every lane was empty, so return true
	}
	
}
