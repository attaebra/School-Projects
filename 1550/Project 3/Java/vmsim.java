import java.io.*;

public class vmsim{
	public static void main(String [] args){
		int refresh = 0;
		String filename = null;
		
		if (args.length != 5 && args.length != 7){
			System.out.println("Invalid Number of arguments. Arguments should be "
			+ "\"java vmsim -n <numframes> -a <algorithm> [-r <refresh rate>] <tracefile>\"");
			return;
		}
	
		int num_frames = Integer.parseInt(args[1]);
		String algorithm = args[3];
		
		if (args.length == 5) filename = args[4];
		
		else if (args.length == 7){
			refresh = Integer.parseInt(args[5]);
			filename = args[6];
		}
		
		if (algorithm.toLowerCase().equals("opt")){
			Opt o = new Opt(filename, num_frames);
		}
		else if (algorithm.toLowerCase().equals("clock")){
			Clock c = new Clock(filename, num_frames);
		}
		else if (algorithm.toLowerCase().equals("nru")){
			NRU n = new NRU(filename, num_frames, refresh);
		}
		else if (algorithm.toLowerCase().equals("random")){
			Rand a = new Rand(filename, num_frames);
		}
	}
}