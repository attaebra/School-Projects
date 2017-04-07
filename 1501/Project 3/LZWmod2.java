/*************************************************************************
*  Compilation:  javac LZWmod2.java
 *  Execution:    java LZWmod2 - < input.txt   (compress)
 *  Execution:    java LZWmod2 + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 
 Very similar to LZWmod, except when the codeword table is full
 it throws it away and starts a new one.
 
 * Atta Ebrahimi
 *************************************************************************/
import java.math.*;

public class LZWmod2 {
    private static final int R = 256;        // number of input chars
    private static int L = 512;       // number of codewords = 2^W
    private static int W = 9;         // codeword width

    public static void compress() { 
        TSTmod<Integer> st = new TSTmod<Integer>();
        for (int i = 0; i < R; i++)
            st.put(new StringBuilder("" + (char) i), i);
        int code = R+1;  // R is codeword for EOF
		StringBuilder str = new StringBuilder("");
		boolean end = false;
		while( BinaryStdIn.isEmpty() == false ){
			char c = BinaryStdIn.readChar();
			StringBuilder str1 = new StringBuilder( str.toString() + c);
			if( !st.contains(str1) && str.length()!= 0){
				BinaryStdOut.write( st.get(str), W );
				if( code < L){
					st.put( str1, code );
					code++;
				}
				else{
					if( W < 16 ){
						W++;
						L = ((new BigInteger("2")).pow(W)).intValue();
						st.put( str1, code );
						code++;
					}
					else if( W== 16 ){			//throw out dictionary and make new one
						W = 9;
						L = 512;
						st = new TSTmod<Integer>();
						for (int i = 0; i < R; i++)
							st.put(new StringBuilder("" + (char) i), i);
						code = R+1;  // R is codeword for EOF
					}
				}
				str = new StringBuilder( str1.substring( str1.length() - 1, str1.length() ) );
				continue;
			}
			else{
				str = str1;
			}
		}
		
		if( st.contains(str) ){			//for last character
			BinaryStdOut.write( st.get(str), W );
		}
		BinaryStdOut.write(R,W);		//end of file char
		BinaryStdOut.close();
			
    } 


    public static void expand() {
        String[] st = new String[65536];
        int i; // next available codeword value

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = BinaryStdIn.readInt(W);
        String val = st[codeword];

        while (true) {
            BinaryStdOut.write(val);
            codeword = BinaryStdIn.readInt(W);
            if (codeword == R) break;
            String s = st[codeword];
			String str = (""+codeword+"-"+s+"_\n");
            if (i == codeword) {
				char ch = val.charAt(0);
				s = val + ch;   // special case hack
			}
            if (i < L ) {
				char c1 = s.charAt(0);
				st[i++] = val + c1;
			}
			if( i >= L && W <16){
				W++;
				L = (L*2);
			}
			if( i >= L && W == 16 ){
				BinaryStdOut.write( s );
				W = 9;
				L = 512;
				st = new String[65536];
				// initialize symbol table with all 1-character strings
				for (i = 0; i < R; i++)
					st[i] = "" + (char) i;
				st[i++] = "";                        // (unused) lookahead for EOF
				
				codeword = BinaryStdIn.readInt(W);
				val = st[codeword];
				continue;
			}
            val = s;
        }
        BinaryStdOut.close();
    }



    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new RuntimeException("Illegal command line argument");
    }

}
