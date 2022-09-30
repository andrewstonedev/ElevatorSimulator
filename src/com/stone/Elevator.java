package com.stone;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

enum DIRECTION
{
	UP, DOWN, REST
}

public class Elevator
{
	Thread elevatorThread;

	private int id;
	private int maxCapacity;
	private boolean atCapacity = false;
	private int currentCapacity;
	private int numFloors;
	private int currentFloor;
	//  inTransit holds state of elevator being in transit
	private boolean inTransit = false;
	//	destinationFloorFloor is taken from front of queue
	private DIRECTION direction;
	private int destinationFloor = -1;
	//	Up is true, down is false
	private boolean UpDownDirection = true;

	private Lock lock = new ReentrantLock();

	// Constructor
	protected Elevator(int id, int maxCapacity, int numFloors, String threadName)
	{
		this.id = id;
		this.maxCapacity = maxCapacity;
		this.numFloors = numFloors;
		this.currentFloor = 1;
		this.direction = DIRECTION.REST;
		this.currentCapacity = 0;
		elevatorThread = new Thread(threadName);
	}

	// If an elevator can fulfill a passenger request return true
	// else return false
	protected boolean RequestElevator(RiderRequest request)
	{
		if (CanDoInTransitPickUp(request)) {
			System.out.println("TODO: In-transit pickup TRUE");
			return true;
		} else {
			System.out.println("TODO: In-transit pickup FALSE");
			return false;
		}
	}

	private boolean CanDoInTransitPickUp(RiderRequest request)
	{
		if (atCapacity)
			return false;

			// Case 1: In-transit moving up
			//  if in-transit && moving up
			//  && current floor is ≤ requested floor
			//  && requested floor ≤ destination floor
		else if (
						this.inTransit && direction != DIRECTION.DOWN
										&& this.currentFloor <= request.requestedOnFloor
										&& request.requestedOnFloor <= destinationFloor
		) {
			return true;
		}
		// Case 2: In-transit moving down
		//  if in-transit && moving down
		//  && current floor is ≥ requested floor
		//  && requested floor ≥ destination floor
		else if (
						this.inTransit && direction != DIRECTION.UP
										&& this.currentFloor >= request.requestedOnFloor
										&& request.requestedOnFloor >= destinationFloor
		) {
			return true;
		} else {
			// throw new RuntimeException("[ERR]: Unhandled case for CanDoInTransitPickUp()");
			return false;
		}
	}

	private void RunWaitForTask()
	{
		Runnable WaitForTask = () -> {
			try {
				System.out.println(elevatorThread.getName() + " [Waiting]");
				elevatorThread.wait();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		};
		elevatorThread.start();
	}

	//  Time required to stop, load/unload at a floor takes 15 seconds
	//  puts elevator thread to sleep for 15 seconds
	//	changed to 3 seconds for testing
	private void RunFloorStopDelay() throws InterruptedException
	{
		//	Thread.sleep(15*1000);
		System.out.println(this.elevatorThread.getName()+" [Stopped at Floor]");
		this.elevatorThread.sleep(3 * 1000);
	}

	private void RunTransportPassenger() throws InterruptedException
	{

		AtomicInteger floorsToTravel = new AtomicInteger(Math.abs(this.currentFloor - this.destinationFloor));
		Runnable Transport = () -> {
			for (int floor = floorsToTravel.get(); floor == 0; floorsToTravel.getAndDecrement()) {
				try {
					elevatorThread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO:
					//  if thread state: RUNNING and is requested
					//  check: is elevator is at capacity
					//  check: is elevator traveling in same direction of request (pick-up otw to destination)
					throw new RuntimeException(e);
				}
			}
		};

		// elevator is in transit
		this.inTransit = true;
		this.destinationFloor = destinationFloor;

		// elevator arrives at destination floor
		this.inTransit = false;
		this.currentFloor = this.destinationFloor;
		this.destinationFloor = -1;
	}

	private void setDestinationFloor(int destinationFloor)
	{
		this.destinationFloor = destinationFloor;
		System.out.println("Elevator " + this.id + " sent to floor " + destinationFloor);
	}

}
