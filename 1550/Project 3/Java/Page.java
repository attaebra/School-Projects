public class Page{
	
	private int page_num;
	private boolean referenced;
	private boolean dirty;
	private boolean [] time;
	
	public Page(int num, boolean d){
		page_num = num;
		referenced = true;
		dirty = d;
		time = new boolean [8];
		
		for (int i = 0; i < time.length; i++){
			time[i] = false;
		}
	}
	
	public void setReferenced(boolean bool){
		referenced = bool;
	}
	
	public void setDirty(){
		dirty = true;	
	}
	
	public boolean isDirty(){
		return dirty;
	}
	
	public boolean isReferenced(){
		return referenced;
	}
	
	public int getPageNum(){
		return page_num;
	}
}