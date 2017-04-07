import java.util.*;

public class Add128 implements SymCipher
{
	byte[] key;
	
	public Add128()
	{
		byte [] ar = new byte[128];
		Random rand =  new Random();
		for( int i = 0; i < 128 ; i++ ){
			ar[i] = (byte)rand.nextInt(128);
		}
		key = ar;
	}
	public Add128( byte [] arr )
	{
		key = arr;
	}
	
	public byte[] getKey()
	{
		return key;
	}
	public byte [] encode(String S)
	{
		System.out.println("Message to be encoded: "+S);
		byte [] s = S.getBytes();
		System.out.println("Corresponding bytes: "+s);
		byte[] encoded = new byte [s.length];
		int add = 0;
		for( int i = 0; i < s.length ; i++ ){
			encoded[i] = (byte)(s[i] + key[add]);			//check for overflow???
			add = (add+1)%128;
		}
		System.out.println("Encrypted bytes: "+encoded);
		return encoded;
	}
	
	public String decode(byte [] bytes)
	{
		System.out.println("Array of bytes received: "+bytes);
		byte [] decoded = new byte [ bytes.length ];
		int sub = 0;
		for( int i = 0; i < bytes.length ; i++ ){
			decoded[i] = (byte)(bytes[i] - key[sub]);
			sub = (sub+1)%128;
		}
		System.out.println("Array of decoded bytes: "+decoded);
		String msg = new String(decoded);
		System.out.println("Decoded message: "+msg);
		return ( msg );
	}
}