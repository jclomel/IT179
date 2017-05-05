

import java.util.ArrayList;

/**
 * a collection of various ways to run the toll gate simulation.  The arguments supplied at the command line determine how the simulation
 * is run.  See the comments for the main method for an explanation of what the arguments do
 * @author Justin Lomelino
 *
 */
public class sim {

	/**
	 * main method of the traffic simulation.  Depending on the arguments provided, it will run in one of several ways.
	 * 1. if no arguments are provided, structuredSim will run.  The simulation will run once, and will increase the number of lanes for
	 * 	  each rate of traffic flow until the maximum waiting time for any vehicle is less than 15 minutes (900) seconds.  The simulation is
	 *    run for each rate of traffic flow, and for both scenarios of mtime and vtime described in the assignment, with the results output
	 *    to the console in a human-readable format.
	 * 2. if the first argument provided is 'mult' and the second argument is an integer, the above structuredSim will run a number of times
	 *    specified by the integer, with a final averaging of the number of lanes needed for each rate of traffic flow calculated and displayed
	 *    at the end of all the simulations.  This can take a LONG time, for example, 1000 simulations took nearly 24 hours on my hardware.
	 * 3. 3 doubles and 1 int provided as arguments will run the simulation once according to the arguments provided.  The first argument
	 *    represents the mean gate time in seconds, the second represents the variance of gate time in seconds, the third represents the
	 *    flow of traffic in vehicles per second, and the fourth represents the number of gate lanes in the simulation.  Results will be
	 *    displayed in a human-readable format as described in the assignment requirements.
	 *    
	 * @param args the simulation parameters. If none are provided, structuredSim runs once.  If the first argument is 'mult' and the second
	 * is an integer, runs structuredSim a number of times equal to the integer and displays the average number of lanes for each traffic flow.
	 * if 3 doubles and 1 int are provided as described in the assignment, where the first double is the mean gate time in seconds, the second
	 * is the mean variance of the gate time in seconds, the third is the number of vehicles per second, and the integer is the number of lanes
	 * in the simulation, the sim will be run as described in the assignment, displaying human-readable results at the end in order to convey 
	 * the information required by the assignment.
	 */
	public static void main(String[] args){
		//create the TrafficSim object, which handles the simulation
		TrafficSim ts = new TrafficSim();
		
		//if arguments are provided, the simulation is run in different ways
		if(args.length > 0){
			//if the first argument is 'mult', the structuredSim will be run multiple times
			if(args[0].equals("mult")){
				simulateRepeated(ts, Integer.parseInt(args[1]));
			}
			//if 3 doubles and 1 int are provided, the simulation is run as described in the assignment
			else try{
				int time = (3*60*60);
				int maxWaitingTime = (15*60);
				double[] flow = {(1/Double.parseDouble(args[2]))};
				double mean = Double.parseDouble(args[0]);
				double var = Double.parseDouble(args[1]);
				int lanes = Integer.parseInt(args[3]);

				runSim(ts, flow, mean, var, time, maxWaitingTime, lanes, false);
			}
			catch(Exception e){
				System.out.println("Error!  Arguments should be provided in the format:\n"
						+ "  sim <meanService> <varService> <trafficFlow> <boothNo>\n"
						+ "  where <meanService> is a double representing mean gate time,\n"
						+ "  <varService> is a double representing gate time variance,\n"
						+ "  <trafficFlow> is a double representing vehicles/second,\n"
						+ "  and <boothNo> is an integer representing the number of toll booths.\n");
				e.printStackTrace(System.out); System.out.println(e.getMessage());
			}
		}
		/*
		 * The default manner of running, for when no arguments are provided.  This is set to be 'structuredSim' run once, 
		 * but can be easily changed by commenting out this line and uncommenting one of the other lines to run the 
		 * structuredSim multiple times, or to run one predictable simulation in the manner described in the assignment.
		 */
		
		//else simulateRepeated(ts, 1000);
		else structuredSim(ts);
		//else testSim(ts);
	}
	
	/**
	 * A predictable run of the simulation in the manner described in the assignment.  Values for the simulation are hard-coded to help
	 * makes the outcome more predictable.  This is mainly used for testing purposes.
	 * @param ts a TrafficSim object on which to run the simulation
	 */
	private static void testSim(TrafficSim ts){
		ts.simulate(1, 3, 6, (3*60*60), 2);
	}
	
	/**
	 * A structured run of the simulation.  Each traffic flow rate is run, with the number of lanes increasing by one and the sim re-running
	 * when the average waiting time per vehicle is greater than 15 minutes (900 seconds).  Therefore, the last run for each traffic flow rate
	 * will have the appropriate number of lanes for that flow rate of traffic.  The simulations are run for both value sets of mtime and vtime
	 * as described in the assignment in order to illustrate how a change in these values can result in a change in the number of toll gate lanes
	 * required.
	 * 
	 * @param ts a TrafficSim object on which to run the simulations
	 * @return an ArrayList of ArrayLists of Integers containing the number of lanes required for each flow rate of traffic to keep the average
	 * waiting time below 15 minutes (900 seconds)
	 */
	private static ArrayList<ArrayList<Integer>> structuredSim(TrafficSim ts){
		ArrayList<ArrayList<Integer>> results = new ArrayList<ArrayList<Integer>>();
		
		int lanes = 1;
		int time = (3*60*60);
		int maxWaitingTime = (15*60);
		double[] flows = {(1/0.1), (1/0.5), (1/1.0), (1/1.5), (1/2.0), (1/2.5), (1/3.0), (1/3.5), (1/4.0)};
		double[] mtimes = {3.0, 2.5};
		double[] vtimes = {6.0, 4.0};
		
		results.add(runSim(ts, flows, mtimes[0], vtimes[0], time, maxWaitingTime, lanes, true));
		results.add(runSim(ts, flows, mtimes[1], vtimes[1], time, maxWaitingTime, lanes, true));
		
		return results;
		
	}
	
	/**
	 * prints the header for each simulation run to the console.  Contains information about the total time the simulation is run, the
	 * mean gate time and variance of the mean gate time, and the number of starting toll gate lanes in the simulation.
	 * 
	 * @param ts a TrafficSim object on which to run the simulations
	 * @param timeSimulated the amount of time that is simulated, in a human-readable format.  Note that the simulation may run longer than
	 * this value to process vehicles remaining in lanes and the road, but no new vehicles are generated after this time is reached
	 * @param meanGateTime the mean time in seconds it takes a vehicle to be served at a toll gate
	 * @param varianceTime the variance of the mean time in seconds that it takes a vehicle to be served at a toll gate
	 * @param numberOfLines an integer representing the starting number of toll lanes in the simulation
	 */
	private static void displayHeader(TrafficSim ts, double timeSimulated, double meanGateTime, double varianceTime, int numberOfLines){
		System.out.printf("\n\nTimeSimulated = %s", TrafficSim.timeFormat(timeSimulated));
		System.out.printf("    Mean of Gate Time = %s seconds", meanGateTime);
		System.out.printf("    Variance Gate Time = %s seconds", varianceTime);
		System.out.printf("    Toll Gate Lanes = "+numberOfLines + "\n");
	}
	
	/**
	 * Runs the simulation.  If the commandFlag is set true, will re-run the simulation until the average waiting time for vehicles
	 * is less than 15 minutes (900 seconds).
	 * 
	 * @param ts a TrafficSim object on which to run the simulation(s)
	 * @param flows a double[] containing the traffic flow rates to simulate
	 * @param gate a double representing the mean time of service in seconds at a toll gate
	 * @param var a double representing the variance of the mean time of service in seconds at a toll gate
	 * @param time a double representing the amount of time to simulate new vehicles arriving, in seconds
	 * @param maxWaitingTime an integer representing the maximum time a vehicle should have to wait before re-running the simulation with more lanes.  This value is in seconds
	 * @param lanes an integer representing the number of toll gate lanes in the simulation
	 * @param commandFlag a boolean, if true, the simulation is re-run with increasing number of toll gates until the average waiting time is less than 15 minutes (900 seconds),
	 * if false, the simulation is just run once with the values provided
	 * @return an ArrayList of Integers representing the number of toll gate lanes needed for each traffic flow in the flows array
	 */
	private static ArrayList<Integer> runSim(TrafficSim ts, double[] flows, double gate, double var, double time, int maxWaitingTime, int lanes, boolean commandFlag){
		ArrayList<Integer> gatesNeeded = new ArrayList<Integer>(flows.length);
		
		displayHeader(ts, time, gate, var, lanes);
		for(double flow:flows){
			System.out.printf("\n----------FLOW: 1 VEHICLE EVERY %2.3f SECONDS", flow);
			System.out.print("\n VEHICLE/SEC      TOTAL   EASYPASS   AVERAGE-WAITING    AVERAGE-GATE    AVERAGE-TIME    MAX ROAD");
			TrafficSim.simulate(flow, gate, var, time, lanes);
			//if the commandFlag is set to true, we re-run the simulation until the average waiting time is less than 15 minutes, increasing the number of lanes by 1
			if(commandFlag){
				while(ts.averageWaiting>=maxWaitingTime){
					lanes++;
					System.out.print("++   number of lanes increased to " + lanes);
					TrafficSim.simulate(flow, gate, var, time, lanes);
				}
				gatesNeeded.add(lanes);
				
			}
		}
		return gatesNeeded;
	}

	/**
	 * runs a structuredSim as described in the comment for structuredSim multiple times.  After all simulations are run, the average number
	 * of lanes required for each traffic flow and both sets of mtime and vtime values is displayed, along with a recommended number of lanes
	 * to cover all possible lane results.  This is a great way to help 'smooth out' the results provided by the distribution curves used in 
	 * these simulations in order to make an intelligent decision as to how many lanes are actually required.  Since the simulation can have
	 * different results each time it is run, running a large number of times and taking an average derives a more reliable result than just
	 * running the simulation once.  This can potentially take a LONG time depending on the number of runs and the hardware used.  For example,
	 * on my own hardware 1000 runs took about 24 hours to complete.
	 * 
	 * @param ts a TrafficSim object on which to run the simulations
	 * @param numberOfSimulations an integer representing the number of times to run the structuredSim before taking an average of the results
	 */
	private static void simulateRepeated(TrafficSim ts, int numberOfSimulations){
		//set up storage for the results of the structuredSims to be run so an average can be found
		ArrayList<ArrayList<ArrayList<Integer>>> allTheResults = new ArrayList<ArrayList<ArrayList<Integer>>>();
		double flow1 = 0, flow2=0, flow3=0, flow4=0, flow5=0, flow6=0, flow7=0, flow8 = 0, flow9=0;
		double flow11 = 0, flow12=0, flow13=0, flow14=0, flow15=0, flow16=0, flow17=0, flow18 = 0, flow19=0;
		
		//for a number of times equal to numberOfSimulations, display the simulation number count and then run the sim, saving the results to the allTheResults ArrayList
		for(int i=0;i<numberOfSimulations;i++){
			System.out.println("\n\n~*~ Beginning simulation number " + (i+1) + " ~*~");
			allTheResults.add(structuredSim(ts));
		}
		
		//once all the simulations have run, parse out the results saved in allTheResults into a double for each traffic flow so an average can be derived
		for(int i=0;i<allTheResults.size();i++){
			ArrayList<ArrayList<ArrayList<Integer>>> values =  allTheResults;

			flow1 += (double)values.get(i).get(0).get(0);
			flow2 += (double)values.get(i).get(0).get(1);
			flow3 += (double)values.get(i).get(0).get(2);
			flow4 += (double)values.get(i).get(0).get(3);
			flow5 += (double)values.get(i).get(0).get(4);
			flow6 += (double)values.get(i).get(0).get(5);
			flow7 += (double)values.get(i).get(0).get(6);
			flow8 += (double)values.get(i).get(0).get(7);
			flow9 += (double)values.get(i).get(0).get(8);
			flow11 += (double)values.get(i).get(1).get(0);
			flow12 += (double)values.get(i).get(1).get(1);
			flow13 += (double)values.get(i).get(1).get(2);
			flow14 += (double)values.get(i).get(1).get(3);
			flow15 += (double)values.get(i).get(1).get(4);
			flow16 += (double)values.get(i).get(1).get(5);
			flow17 += (double)values.get(i).get(1).get(6);
			flow18 += (double)values.get(i).get(1).get(7);
			flow19 += (double)values.get(i).get(1).get(8);
		}
		
		//display the average number of lanes per traffic flow rate, as well as the recommended number of lanes, for each mtime and vtime set
		System.out.println("\n*************************************************");
		System.out.println("*    Recommended Lanes for each traffic flow    *");
		System.out.println("*                                               *");
		System.out.println("*      mtime = 3    vtime = 6                   *");
		System.out.printf("*   0.1    average: %2.3f     recommended: %2.0f   *\n", (flow1/allTheResults.size()), Math.ceil(flow1/allTheResults.size()));
		System.out.printf("*   0.5    average: %2.3f     recommended: %2.0f   *\n", (flow2/allTheResults.size()), Math.ceil(flow2/allTheResults.size()));
		System.out.printf("*   1.0    average: %2.3f     recommended: %2.0f   *\n", (flow3/allTheResults.size()), Math.ceil(flow3/allTheResults.size()));
		System.out.printf("*   1.5    average: %2.3f     recommended: %2.0f   *\n", (flow4/allTheResults.size()), Math.ceil(flow4/allTheResults.size()));
		System.out.printf("*   2.0    average: %2.3f     recommended: %2.0f   *\n", (flow5/allTheResults.size()), Math.ceil(flow5/allTheResults.size()));
		System.out.printf("*   2.5    average: %2.3f    recommended: %2.0f   *\n", (flow6/allTheResults.size()), Math.ceil(flow6/allTheResults.size()));
		System.out.printf("*   3.0    average: %2.3f    recommended: %2.0f   *\n", (flow7/allTheResults.size()), Math.ceil(flow7/allTheResults.size()));
		System.out.printf("*   3.5    average: %2.3f    recommended: %2.0f   *\n", (flow8/allTheResults.size()), Math.ceil(flow8/allTheResults.size()));
		System.out.printf("*   4.0    average: %2.3f    recommended: %2.0f   *\n", (flow9/allTheResults.size()), Math.ceil(flow9/allTheResults.size()));
		System.out.println("*                                               *");
		System.out.println("*      mtime = 2.5    vtime = 4                 *");
		System.out.printf("*   0.1    average: %2.3f     recommended: %2.0f   *\n", (flow11/allTheResults.size()), Math.ceil(flow11/allTheResults.size()));
		System.out.printf("*   0.5    average: %2.3f     recommended: %2.0f   *\n", (flow12/allTheResults.size()), Math.ceil(flow12/allTheResults.size()));
		System.out.printf("*   1.0    average: %2.3f     recommended: %2.0f   *\n", (flow13/allTheResults.size()), Math.ceil(flow13/allTheResults.size()));
		System.out.printf("*   1.5    average: %2.3f     recommended: %2.0f   *\n", (flow14/allTheResults.size()), Math.ceil(flow14/allTheResults.size()));
		System.out.printf("*   2.0    average: %2.3f     recommended: %2.0f   *\n", (flow15/allTheResults.size()), Math.ceil(flow15/allTheResults.size()));
		System.out.printf("*   2.5    average: %2.3f     recommended: %2.0f   *\n", (flow16/allTheResults.size()), Math.ceil(flow16/allTheResults.size()));
		System.out.printf("*   3.0    average: %2.3f    recommended: %2.0f   *\n", (flow17/allTheResults.size()), Math.ceil(flow17/allTheResults.size()));
		System.out.printf("*   3.5    average: %2.3f    recommended: %2.0f   *\n", (flow18/allTheResults.size()), Math.ceil(flow18/allTheResults.size()));
		System.out.printf("*   4.0    average: %2.3f    recommended: %2.0f   *\n", (flow19/allTheResults.size()), Math.ceil(flow19/allTheResults.size()));
		System.out.println("*************************************************");
	}
}
