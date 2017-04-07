import java.io.*;
import java.lang.Math;
import java.util.ArrayList;

public class Opt{

	public Opt(String filename, int num_frames){
		
		//Counters 
		int mem_access = 0;
		int faults = 0;
		int disk_writes = 0;
		
		ArrayList<ArrayList<Integer>> positionArray = new ArrayList<ArrayList<Integer>>();
		
		for (int i = 0; i < 1048576; i++){
			positionArray.add(new ArrayList<Integer>());
		}
		
		//Initialize Page Table
		PageTable pt = new PageTable();
		//Initialize Frame Table
		Page [] ft = new Page[num_frames];
		
		//Frame table full
		int num_items = 0;	
		String line = null;
		
		try{
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			
			while((line = br.readLine()) != null){
				//Splits line into address and R/W operation
				String str[] = line.split(" ", 2);
				String addr = str[0];
				String op = str[1];
				
				//calls method to calculate the page number of current address read in
				int page_num = pt.calculatePage(addr);
				
				positionArray.get(page_num).add(new Integer(mem_access));
				
				mem_access++;
			}
			
			fr = new FileReader(filename);
			
			br = new BufferedReader(fr);
			
			PrintWriter writer = new PrintWriter("opt_results_" + filename + ".txt", "UTF-8");
			
			mem_access = 0;
			
			while((line = br.readLine()) != null){
				//Splits line into address and R/W operation
				String str[] = line.split(" ", 2);
				String addr = str[0];
				String op = str[1];
				
				//Increment memory counter
				mem_access++;
				
				//Calls method to determine the page number of a current address to read in
				int page_num = pt.calculatePage(addr);
				
				//Check if page is in RAM
				if (pt.checkPageTable(page_num)){
					//If loaded, grab index from FT to update
					int frameIndex = pt.getFrameIndex(page_num);
					
					//If R operation, set referenced bit and update time stamp.
					//If W operation, set referenced bit and dirty bit and update time stamp.
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
					//Page was not found in RAM
					//Create page based on R or W
					Page p = null;
					
					if (op.equals("R")){
						p = new Page(page_num, false);
					}
					else{
						p = new Page(page_num, true);
					}
					
					if (num_items != num_frames){
						ft[num_items] = p;
						pt.setFrameNum(page_num, num_items);
						num_items++;
						faults++;
						writer.println("page fault -- no eviction");
					}
					else{
						int maxPos = 0;
						int evictPage = 0;
						boolean evicted = false;
						faults++;
						
						//Attempts to evict page with no lines
						for (int i = 0; i < ft.length; i++){
							if (positionArray.get(ft[i].getPageNum()).size() == 0){
								evictPage = i;
								evicted = true;
							}
						}
						
						if (!evicted){
							//Removes references less than current line
							for (int i = 0; i < ft.length; i++){
								ArrayList<Integer> temp = positionArray.get(ft[i].getPageNum());
								Integer in = temp.get(0);
								int j = in.intValue();
								
								//Removes references until current is greater
								while (j < mem_access && !evicted){
									
									positionArray.get(ft[i].getPageNum()).remove(0);
									
									//Grabs next line and evicts if 0
									if (positionArray.get(ft[i].getPageNum()).size() != 0){	
										temp = positionArray.get(ft[i].getPageNum());
										in = temp.get(0);
										j = in.intValue();
									}
									else{
										evicted = true;
										evictPage = i;
									}
								}
							}
						}
						
						//If no eviction, find furthest away.
						if (!evicted){
							for (int i = 0; i < ft.length; i++){
									
								ArrayList<Integer> temp = positionArray.get(ft[i].getPageNum());
								Integer in = temp.get(0);
								int j = in.intValue();
						
								if (j > maxPos){
									maxPos = j;
									evictPage = i;
								}	
							}
						}
						
						//If dirty, write to disk
						if (ft[evictPage].isDirty()){
							disk_writes++;
							writer.println("page fault -- evict dirty");
						}
						else{
							writer.println("page fault -- evict clean");
						}
						
						pt.setFrameNum(ft[evictPage].getPageNum(), -1);
						ft[evictPage] = p;
						pt.setFrameNum(page_num, evictPage);
					}
				}
			}
			
			System.out.println("OPT");
			System.out.println("Number of frames: " + num_frames);
			System.out.println("Total memory accesses: " + mem_access);
			System.out.println("Total page faults: " + faults);
			System.out.println("Total writes to disk: " + disk_writes);
			
			writer.println();
			writer.println(filename);
			writer.println("OPT");
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