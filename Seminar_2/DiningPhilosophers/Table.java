package DiningPhilosophers;

public class Table {

	private int nbrOfChopsticks;
	private boolean chopstick[]; // true if chopstick[i] is available

	public Table(int nbrOfSticks) {
		nbrOfChopsticks = nbrOfSticks;
		chopstick = new boolean[nbrOfChopsticks];
		for (int i = 0; i < nbrOfChopsticks; i++) {
			chopstick[i] = true;
		}
	}

	public synchronized boolean getLeftChopstick(int n) {
		if (chopstick[n]){ //means it is free
			chopstick[n] = false; //make it unavailable
			return true;
		}else {
			return false;
		}
	}

	public synchronized boolean getRightChopstick(int n) {
		int pos = n + 1;
		if (pos == nbrOfChopsticks)
			pos = 0;
		if (chopstick[pos]){ // if true means its free
			chopstick[pos] = false; // make it unavailable
			return true; //succesfully acquired
		}return false; // chopstick in use
	}

	public synchronized void releaseLeftChopstick(int n) {
		chopstick[n] = true; //becomes available
	}

	public synchronized void releaseRightChopstick(int n) {
		int pos = n + 1;
		if (pos == nbrOfChopsticks)
			pos = 0;
		chopstick[pos] = true; //becomes available
	}
}
