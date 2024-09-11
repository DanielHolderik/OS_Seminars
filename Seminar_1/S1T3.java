public class S1T3 implements Runnable{
    

    @Override
    public void run(){
    long currentTime = System.currentTimeMillis();
    double timeElapsedSeconds = (System.currentTimeMillis() - currentTime) /1000; 

        while (timeElapsedSeconds < 60){
            try {
                Thread.sleep(10);
                timeElapsedSeconds += 0.01;
                System.out.println("Elapsed Time:" + timeElapsedSeconds + "\n");

            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        }
        System.out.println("Stopwatch reached 60 seconds, joining threads... ");
    } 

    public static void main(String[] args) throws InterruptedException{

    S1T3 stopWatch = new S1T3();
    Thread thread = new Thread(stopWatch);
    thread.start();
    thread.join();
    }
}
