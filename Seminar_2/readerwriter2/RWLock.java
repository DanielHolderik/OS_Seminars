package readerwriter2;

import java.util.concurrent.locks.ReentrantReadWriteLock;
    //COMMENTED OUT LINES ARE PART OF ORIGINAL SOLUTION
    //COMMENTED OUT LINES ARE PART OF ORIGINAL SOLUTION
    //COMMENTED OUT LINES ARE PART OF ORIGINAL SOLUTION

public class RWLock {
    // int currentReaders = 0;
    // boolean isWriting = false;
    // int writersQueue = 0;
    private ReentrantReadWriteLock lock;
    private ReentrantReadWriteLock.ReadLock readerLock;
    private ReentrantReadWriteLock.WriteLock writerLock;

    public RWLock(){
        lock = new ReentrantReadWriteLock(true);
        readerLock = lock.readLock();
        writerLock = lock.writeLock();
        // int currentReaders;
        // boolean isWriting;
        // int writersQueue;
    }

    public void acquireRead(){

        readerLock.lock();

        // while (isWriting || writersQueue != 0){
        //     try {
        //         wait();
        //     } catch (InterruptedException e){
        //         Thread.currentThread().interrupt();
        //     }
        // }currentReaders ++;
    }
    public void releaseRead(){
        
        readerLock.unlock();

        // currentReaders --;
        // if (currentReaders == 0){
        //     notify();
        // }
    }

    public void acquireWrite(){

        writerLock.lock();

        // writersQueue ++;
        // while (currentReaders != 0 || isWriting == true){
        //     try {
        //         wait();
        //     } catch (InterruptedException e) {
        //         Thread.currentThread().interrupt();
        //     } 
        // }writersQueue --;
        //  isWriting = true;
    }

    public void releaseWrite(){

        writerLock.unlock();

        // isWriting = false;
        // notify();
    }

}
