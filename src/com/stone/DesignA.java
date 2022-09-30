package com.stone;


public class DesignA
{
	private int ridersServed = 0;
	private int ridersRejected = 0;

	private Elevator[] _elevators = new Elevator[4];

	private int numElevators = _elevators.length;

	//  Constructor creates 4 elevators with ids 1,2,3,4
	public DesignA()
	{
		_elevators[0] = new Elevator(1, 1, 5, "Elevator 1");
		_elevators[1] = new Elevator(2, 1, 5, "Elevator 2");
		_elevators[2] = new Elevator(3, 1, 5, "Elevator 3");
		_elevators[3] = new Elevator(4, 1, 5, "Elevator 4");
	}

	public void MakeElevatorRequest(Elevator _elevator, RiderRequest request)
	{
		var elevatorThread = _elevator.elevatorThread;
		var threadState = elevatorThread.getState();

		if (_elevator.RequestElevator(request)) {
			++this.ridersServed;
			logPassengersServed();
		} else {
			++this.ridersRejected;
			logPassengersRejected();
		}

	}


	public void PrintElevatorStatuses()
	{
		for (var elevatorThread : _elevators) {
			System.out.println(elevatorThread.elevatorThread.getName() + " " + elevatorThread.elevatorThread.getState());

		}
	}

	//  returns specific elevator by ID (1-4) within building design A
	//	used for simulation
	public Elevator GetElevatorByID(int elevatorID)
	{
		return _elevators[elevatorID - 1];
	}

	public int getNumElevators()
	{
		return numElevators;
	}

	public void logPassengersServed()
	{
		System.out.println("Total Passengers Served: " + ridersServed);
	}

	public void logPassengersRejected()
	{
		System.out.println("Total Passengers Rejected: " + ridersRejected);
	}

}
