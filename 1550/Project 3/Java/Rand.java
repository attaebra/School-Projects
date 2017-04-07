import java.io.*;
import java.util.Random;


/*
 * I wrote this one last, literally just modified clock.
*/

public class Rand{
	public Rand(String filename, int num_frames){
		
		Random rn = new Random();
		//Counters
		int mem_access = 0;
		int faults = 0;
		int disk_writes = 0;
		
		//Initialize Page Table
		PageTable pt = new PageTable();
		//Initialize queue
		Page [] queue = new Page[num_frames];
		
		//Random number to choose
		int rand_page = 0;
		
		String line = null;
		
		try{
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			
			PrintWriter writer = new PrintWriter("random_results_" + filename + ".txt", "UTF-8");
			
			while((line = br.readLine()) != null){
				//Splits line into address and R/W operation
				String str[] = line.split(" ", 2);
				String addr = str[0];
				String op = str[1];
				
				//Increment memory access counter for each line read
				mem_access++;
				
				//Calls method to determine the page number of a current address to read in
				int pageNum = pt.calculatePage(addr);
				
				//Check if page is in RAM
				if (pt.checkPageTable(pageNum)){
					//If loaded, grab index from FT to update
					int frameIndex = pt.getFrameIndex(pageNum);
					
					//if R operation, set referenced bit and update time stamp.
					//if W operation, set referenced bit and dirty bit and update time stamp.
					if (op.equals("R")){
						queue[frameIndex].setReferenced(true);
					}
					else{
						queue[frameIndex].setReferenced(true);
						queue[frameIndex].setDirty();
					}
					
					writer.println("hit");
				}
				else{
					//Page was not found in ram
					//Create object based on read or write operation
					Page p = null;
					
					if (op.equals("R")){
						p = new Page(pageNum, false);
					}
					else{
						p = new Page(pageNum, true);
					}
					
					//If current position is null, that means queue is not full
					if (queue[rand_page] == null){
				
						queue[rand_page] = p;				//Place page in queue
						pt.setFrameNum(pageNum, rand_page);	//Value in page table = position in queue
						rand_page++;							//Increment hand pointer
						faults++;						//Increment faults
						
						//Reset to beginning if hand == num_frames
						if (rand_page == num_frames){
							rand_page = 0;
						}
						
						writer.println("page fault -- no eviction");
					}
					else{
						//If queue is full, page must be evicted
						boolean evicted = false;		//Determine whether page has been evicted
						faults++;						//Increment faults
						
						rand_page = rn.nextInt(num_frames - 1) + 1;
						
						if (queue[rand_page].isDirty()){
							disk_writes++;
							writer.println("page fault -- evict dirty");
						}
						else{
							writer.println("page fault -- evict clean");
						}
						
						pt.setFrameNum(queue[rand_page].getPageNum(), -1);
						queue[rand_page] = p;
						pt.setFrameNum(pageNum, rand_page);
						rand_page++;
						
						if (rand_page == num_frames){
							rand_page = 0;
						}
					}
				}
			}
			
			System.out.println("\nRandom");
			System.out.println("Number of frames: " + num_frames);
			System.out.println("Total memory accesses: " + mem_access);
			System.out.println("Total page faults: " + faults);
			System.out.println("Total writes to disk: " + disk_writes);
			
			writer.println();
			writer.println(filename);
			writer.println("Random");
			writer.println("Number of frames: " + num_frames);
			writer.println("Total memory accesses: " + mem_access);
			writer.println("Total page faults: " + faults);
			writer.println("Total writes to disk: " + disk_writes);
			
			writer.close();
		}
		catch(FileNotFoundException ex){
			System.out.println("Unable to open file '" + filename + "'");
		}
		catch(IOException ex){
			System.out.println("Error reading file");
		}
		
	}
}