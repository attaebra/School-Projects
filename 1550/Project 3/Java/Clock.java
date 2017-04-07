import java.io.*;

public class Clock{
	
	public Clock(String filename, int num_frames){
		
		//Counters
		int mem_access = 0;
		int faults = 0;
		int disk_writes = 0;
		
		//Initialize Page Table
		PageTable pt = new PageTable();
		//Initialize queue
		Page [] queue = new Page[num_frames];
		
		//Hand for circular queue
		int hand = 0;
		
		String line = null;
		
		try{
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			
			PrintWriter writer = new PrintWriter("clock_results_" + filename + ".txt", "UTF-8");
			
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
					if (queue[hand] == null){
				
						queue[hand] = p;				//Place page in queue
						pt.setFrameNum(pageNum, hand);	//Value in page table = position in queue
						hand++;							//Increment hand pointer
						faults++;						//Increment faults
						
						//Reset to beginning if hand == num_frames
						if (hand == num_frames){
							hand = 0;
						}
						
						writer.println("page fault -- no eviction");
					}
					else{
						//If queue is full, page must be evicted
						boolean evicted = false;		//Determine whether page has been evicted
						faults++;						//Increment faults
						
						//Loop until page is evicted
						while (!evicted){
							//Check if page is referenced
							if (queue[hand].isReferenced()){
								queue[hand].setReferenced(false);		//Set referenced to false
								hand++;									//Increment to next position
								
								//Reset to beginning if hand == num_frames
								if (hand == num_frames){
									hand = 0;
								}
							}
							else if(!queue[hand].isReferenced()){
								//If unreferenced page was found
								evicted = true;		//Evicted true, exit loop
								
								//Write to disk if dirty
								if (queue[hand].isDirty()){
									disk_writes++;
									writer.println("page fault -- evict dirty");
								}
								else{
									writer.println("page fault -- evict clean");
								}
								
								//Set index to -1, shows page is not in RAM
								pt.setFrameNum(queue[hand].getPageNum(), -1);
								//Assign current position to new page
								queue[hand] = p;
								//Update page index in PT for new page to tell where in queue it is
								pt.setFrameNum(pageNum, hand);
								//Increment hand to next position in queue.
								hand++;
								
								//If hand == num_frames, reset to beginning.
								if (hand == num_frames){
									hand = 0;
								}
							}
						}
					}
				}
			}
			
			System.out.println("\nClock");
			System.out.println("Number of frames: " + num_frames);
			System.out.println("Total memory accesses: " + mem_access);
			System.out.println("Total page faults: " + faults);
			System.out.println("Total writes to disk: " + disk_writes);
			
			writer.println();
			writer.println(filename);
			writer.println("Clock");
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