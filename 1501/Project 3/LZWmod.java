/*************************************************************************
 *  Compilation:  javac LZWmod.java
 *  Execution:    java LZWmod - < input.txt   (compress)
 *  Execution:    java LZWmod + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *
 *  Similar to LZW.java, but reads the file byte by byte to save time
 *	and also uses variable numbers of bits (9-16) to represent codewords
 *	Atta Ebrahimi
 *************************************************************************/
import java.math.*;

public class LZWmod {
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
			char c = BinaryStdIn.readChar();		//read a single char at a time
			StringBuilder str1 = new StringBuilder( str.toString() + c);
			if( !st.contains(str1) && str.length()!= 0){
				BinaryStdOut.write( st.get(str), W );
				if( code < L){
					st.put( str1, code );	//add codeword to trie
					code++;
				}
				else{
					if( W < 16 ){	//resize since table can't fit codewords
						W++;
						L = ((new BigInteger("2")).pow(W)).intValue();
						st.put( str1, code );
						code++;
					}
				}
				str = new StringBuilder( str1.substring( str1.length() - 1, str1.length() ) );
				continue;
			}
			else{
				str = str1;		// add a character from the file onto codeword
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
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            if (i < L ) st[i++] = val + s.charAt(0);
			if( i >= L && W <16){	//resize if table is full.  
				W++;
				L = (L*2);
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
