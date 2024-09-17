package readerwriter1;

public class RWLock {
    int currentReaders = 0;
    boolean isWriting = false;
    int writersQueue = 0;
    public RWLock(){
        // int currentReaders;
        // boolean isWriting;
        // int writersQueue;
    }

    public synchronized void acquireRead(){
        while (isWriting || writersQueue > 0){
            try {
                wait();
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }currentReaders ++;
    }
    public synchronized void releaseRead(){
        currentReaders --;
        if (currentReaders == 0){
            notify();
        }
    }

    public synchronized void acquireWrite(){
        writersQueue ++;
        while (currentReaders > 0 || isWriting == true){
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } 
        }writersQueue --;
         isWriting = true;
    }

    public synchronized void releaseWrite(){
        isWriting = false;
        notify();
    }

}
