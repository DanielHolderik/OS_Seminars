package memory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class MemoryManager {

	private int myNumberOfPages;
	private int myPageSize; // In bytes
	private int myNumberOfFrames;
	private int[] myPageTable; // -1 if page is not in physical memory
	private byte[] myRAM; // physical memory RAM
	private RandomAccessFile myPageFile;
	private int myNextFreeFramePosition = 0;
	private int myNumberOfpageFaults = 0;
	private int myPageReplacementAlgorithm = 0;
	private Queue<Integer> fifoQ = new LinkedList<>();
	private LinkedHashMap<Integer, Integer> LRUMap = new LinkedHashMap<>(myNumberOfFrames, 0.75f, true) { //idk what the 0.75f does but itll only work if its there so imma keep it
    
    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
  
        return size() > myNumberOfFrames;
    }
};

	public MemoryManager(int numberOfPages, int pageSize, int numberOfFrames, String pageFile,
			int pageReplacementAlgorithm) {

		myNumberOfPages = numberOfPages;
		myPageSize = pageSize;
		myNumberOfFrames = numberOfFrames;
		myPageReplacementAlgorithm = pageReplacementAlgorithm;

		initPageTable();
		myRAM = new byte[myNumberOfFrames * myPageSize];

		try {

			myPageFile = new RandomAccessFile(pageFile, "r");

		} catch (FileNotFoundException ex) {
			System.out.println("Can't open page file: " + ex.getMessage());
		}
	}

	private void initPageTable() {
		myPageTable = new int[myNumberOfPages];
		for (int n = 0; n < myNumberOfPages; n++) {
			myPageTable[n] = -1;
		}
	}

	public byte readFromMemory(int logicalAddress) {
		int pageNumber = getPageNumber(logicalAddress);
		int offset = getPageOffset(logicalAddress);

		if (myPageTable[pageNumber] == -1) {
			pageFault(pageNumber);
		}

		int frame = myPageTable[pageNumber];
		int physicalAddress = frame * myPageSize + offset;
		byte data = myRAM[physicalAddress];

		System.out.print("Virtual address: " + logicalAddress);
		System.out.print(" Physical address: " + physicalAddress);
		System.out.println(" Value: " + data);
		return data;
	}

	private int getPageNumber(int logicalAddress) {
		//int pageNr = logicalAddress / 256;
		int pageNr = logicalAddress / myPageSize;
		// we divide by page size to get the page Number
		return pageNr;
	}

	private int getPageOffset(int logicalAddress) {
		//int pageOfst = logicalAddress % 256;
		int pageOfst = logicalAddress % myPageSize;
		// by doing a modulo calculation we will get how many bytes
		// into a page the information we are looking for starts
		return pageOfst;
	}

	private void pageFault(int pageNumber) {
		if (myPageReplacementAlgorithm == Seminar3.NO_PAGE_REPLACEMENT)
			handlePageFault(pageNumber);

		if (myPageReplacementAlgorithm == Seminar3.FIFO_PAGE_REPLACEMENT)
			handlePageFaultFIFO(pageNumber);

		if (myPageReplacementAlgorithm == Seminar3.LRU_PAGE_REPLACEMENT)
			handlePageFaultLRU(pageNumber);

		readFromPageFileToMemory(pageNumber);
	}

	private void readFromPageFileToMemory(int pageNumber) {
		try {
			int frame = myPageTable[pageNumber];
			myPageFile.seek(pageNumber * myPageSize);
			for (int b = 0; b < myPageSize; b++)
				myRAM[frame * myPageSize + b] = myPageFile.readByte();
		} catch (IOException ex) {

		}
	}

	public int getNumberOfPageFaults() {
		return myNumberOfpageFaults;
	}

	private void handlePageFault(int pageNumber) {
        int frame = getFreeFrame();

        if (frame == -1) {
            frame = performPageReplacement(pageNumber);
        }
        myPageTable[pageNumber] = frame;
		readFromPageFileToMemory(pageNumber);

        myNumberOfpageFaults++;
    }
	
	private int getFreeFrame() {
        if (myNextFreeFramePosition < myNumberOfFrames) {
            return myNextFreeFramePosition++; // take the frame and increment it
        } else {
            return -1; // no space available
        }
    }
	private int performPageReplacement(int pageNumber) {
        
        return 0;
	}
	private void handlePageFaultFIFO(int pageNumber) {
		int frame = getFreeFrame();
		
		if (frame == -1){
			int firstIn = fifoQ.poll();
			frame = myPageTable[firstIn]; // get the frame of the oldest element
			myPageTable[firstIn] = -1;    // make the page unavailable
		}
		// now load the page into the free frame
		myPageTable[pageNumber] = frame;
		fifoQ.add(pageNumber);         
		readFromPageFileToMemory(pageNumber);  
	
		myNumberOfpageFaults++;  
	}

	private void handlePageFaultLRU(int pageNumber) {
        int freeFrame = getFreeFrame();

        // if no frame available :
        if (freeFrame == -1) {
            // linked hashmap maintains access order becuase of the way it was configured which means
            // the most recently accessed entry is places at the end so the LRU will be first in the hashmap
            int LRUPage = -1; 
			for (Map.Entry<Integer, Integer> entry : LRUMap.entrySet()) {
    			LRUPage = entry.getKey();
   				break; 
			}

            // find the frame thats free
            freeFrame = myPageTable[LRUPage];

            // remove the page sicne we used it
            myPageTable[LRUPage] = -1;

            // remove from map
            LRUMap.remove(LRUPage);
        }

        // update the selected frame
        myPageTable[pageNumber] = freeFrame;

        // insert into said frame
        LRUMap.put(pageNumber, freeFrame);
        readFromPageFileToMemory(pageNumber);

		//incement
        myNumberOfpageFaults++;
	}
}
