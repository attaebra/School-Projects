import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class NRU{
	
	private Page [] ft;

	public NRU(String filename, int numframes, int refresh){
		
		//Counters 
		int mem_access = 0;
		int faults = 0;
		int disk_writes = 0;
		int time = 0;
		int prev_time = 0;
		
		if (refresh == 0){
			refresh = 55;
		}
		
		
		//Initialize Page Table
		PageTable pt = new PageTable();
		//Initialize Frame Table
		ft = new Page[numframes];
		
		//Frame table is full
		int num_items = 0;
		String line = null;
		
		
		try{
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			
			PrintWriter writer = new PrintWriter("nru_results_" + filename + ".txt", "UTF-8");
			
			while((line = br.readLine()) != null){
				//Splits line into address and R/W operation
				String str[] = line.split(" ", 2);
				String addr = str[0];
				String op = str[1];
				
				//Increment memory access counter for each line read
				mem_access++;
				
				//Increment time counter each loop
				time++;
				
				if ((time - prev_time) >= refresh){
					resetReferenced(num_items);
					prev_time = time;
				}
				
				//Calls method to determine the page number of a current address to read in
				int pageNum = pt.calculatePage(addr);
				
				//Check if page is in RAM
				if (pt.checkPageTable(pageNum)){
					//If loaded, grab index from FT to update
					int frameIndex = pt.getFrameIndex(pageNum);
					
					//if R operation, set referenced bit and update time stamp.
					//if W operation, set referenced bit and dirty bit and update time stamp.
					if (op.equals("R")){
						ft[frameIndex].setReferenced(true);
					}
					else{
						ft[frameIndex].setReferenced(true);
						ft[frameIndex].setDirty();
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
					
					//Set value in page table equal to its position in the queue
					if (num_items != numframes){
						ft[num_items] = p;
						pt.setFrameNum(pageNum, num_items);
						num_items++;
						faults++;
						writer.println("page fault -- no eviction");
					}
					else{
						boolean evicted = false;
						ArrayList<Integer> al = new ArrayList<Integer>(numframes);
						faults++;
							
						//Checks for a page that has referenced bit false and dirty bit false
						for (int i = 0; i < numframes; i++){
							if (!ft[i].isReferenced() && !ft[i].isDirty()){
								Integer in = new Integer(i);
								al.add(in);
								evicted = true;
								
							}
						}
						
						//If evicted, replace page in FT
						if (evicted){
							Random rand = new Random();
							Integer in = al.get(rand.nextInt(al.size()));
							int i = in.intValue();
						
							pt.setFrameNum(ft[i].getPageNum(), -1);
							ft[i] = p;
							pt.setFrameNum(pageNum, i);
							writer.println("page fault -- evict clean");
						}
						
						
							
						//Checks for page with reference bit false and dirty bit true.
						if (!evicted){
							for (int i = 0; i < numframes; i++){
								if (!ft[i].isReferenced() && ft[i].isDirty()){						
									Integer in = new Integer(i);
									al.add(in);
									evicted = true;
									disk_writes++;
									break;
								}
							}
							
							//If evicted, replace page in FT
							if (evicted){
								Random rand = new Random();
								Integer in = (al.get(rand.nextInt(al.size())));
								int i = in.intValue();
							
								pt.setFrameNum(ft[i].getPageNum(), -1);
								ft[i] = p;
								pt.setFrameNum(pageNum, i);
								writer.println("page fault -- evict dirty");
							}
						}
						
						//Checks for page with reference bit false and dirty bit true
						if (!evicted){
							for (int i = 0; i < numframes; i++){
								if (ft[i].isReferenced() && !ft[i].isDirty()){
			
									Integer in = new Integer(i);
									al.add(in);
									evicted = true;
									break;
								}
							}
							
							//If evicted, replace page in FT
							if (evicted){
								Random rand = new Random();
								Integer in = (al.get(rand.nextInt(al.size())));
								int i = in.intValue();
							
								pt.setFrameNum(ft[i].getPageNum(), -1);
								ft[i] = p;
								pt.setFrameNum(pageNum, i);
								writer.println("page fault -- evict clean");
							}
						}
							
						//Checks for page with reference bit true and dirty bit true
						if (!evicted){
							for (int i = 0; i < numframes; i++){
								if (ft[i].isReferenced() && ft[i].isDirty()){	
									Integer in = new Integer(i);
									al.add(in);
									evicted = true;		
									disk_writes++;
									break;
								}
							}
							
							//If evicted, replace page in FT
							if (evicted){
								Random rand = new Random();
								Integer in = (al.get(rand.nextInt(al.size())));
								int i = in.intValue();
							
								pt.setFrameNum(ft[i].getPageNum(), -1);
								ft[i] = p;
								pt.setFrameNum(pageNum, i);
								writer.println("page fault -- evict dirty");
							}
						}
					}
					
				}
				
				
			}
			
			

			System.out.println("NRU");
			System.out.println("Number of frames: " + numframes);
			System.out.println("Total memory accesses: " + mem_access);
			System.out.println("Total page faults: " + faults);
			System.out.println("Total writes to disk: " + disk_writes);
			
			writer.println();
			writer.println(filename);
			writer.println("NRU");
			writer.println("Number of frames: " + numframes);
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
	
	public void resetReferenced(int num_items){
		for (int i = 0; i < num_items; i++){
			ft[i].setReferenced(false);
		}
	}
}