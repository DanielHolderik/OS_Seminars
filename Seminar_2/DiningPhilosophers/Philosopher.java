package DiningPhilosophers;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Philosopher implements Runnable{
    private int myId;
    private Table myTable;
    private boolean leftChopstickAvailable;
    private boolean rightChopstickAvailable;

    public Philosopher(int id,Table table){
        myId = id;
        myTable = table;
    }

    @Override
    public void run(){
        for(int i =0 ; i < 100; i++){
            try {
              
                System.out.println("Philosopher " + myId + " thinks. Iteration "+ i);
                Thread.sleep((int)(Math.random()*100));
                
                leftChopstickAvailable = myTable.getLeftChopstick(myId);
                while (leftChopstickAvailable == false){ // checks if available
                    Thread.sleep(10);   // if not wait until it is
                }
                System.out.println("Philosopher " + myId + " picked up left");

                rightChopstickAvailable = myTable.getRightChopstick(myId);
                while (rightChopstickAvailable == false){
                    myTable.releaseLeftChopstick(myId); // if left not free, release to prevent deadlock
                    Thread.sleep(10);
                    while (leftChopstickAvailable = false){ // nested while loop goes back to 
                        Thread.sleep(10);            //trying to get the left chopstick
                }
            }
                System.out.println("Philosopher " + myId + " pick up right");
              

                System.out.println("Philosopher " + myId + " eats. Iteration "+ i);
                Thread.sleep((int)(Math.random()*100));
               
                myTable.releaseLeftChopstick(myId);
                System.out.println("Philosopher " + myId + " drop left");
                Thread.sleep((int)(Math.random()*10));
                            
                myTable.releaseRightChopstick(myId);
                System.out.println("Philosopher " + myId + " drop right");
                Thread.sleep((int)(Math.random()*10));
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Philosopher.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }
    
}
}
