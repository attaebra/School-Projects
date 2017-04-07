import java.util.*;

public class Substitute implements SymCipher
{
	byte[] key;
	byte[] revkey;
	
	public Substitute( byte [] arr )
	{
		key = arr;
	}
	public Substitute()
	{
		byte [] arr = new byte [256];
		ArrayList<Integer> list = new ArrayList<Integer>(256);
		for( int i = 0; i< 256 ; i++){
			list.add((Integer)i);
		}
		Random rand = new Random();
		int num = -1;
		int in = 0;
		for( int i = 0; i < 256 ; i++){
			num = rand.nextInt(256)%(256-i);
			in = list.get(num);
			arr[i]= (byte)in;
			list.remove(num);
		}
		key = arr;
	}
	
	public byte[] getKey( )
	{
		return key;
	}
	public byte [] encode(String S)
	{
		System.out.println("Message to be encoded: "+S);
		byte [] s = S.getBytes();
		System.out.println("Corresponding bytes: "+s);
		byte [] encoded = new byte [s.length];
		for( int i = 0; i < s.length; i++ ){
			//System.out.println("orig: "+s[i]+"   enc"+key[s[i]&0x000000ff]);
			encoded[i] = key[s[i]&0x000000ff];
		}
		System.out.println("Encrypted bytes: "+encoded);
		return encoded;
	}
	public String decode(byte [] bytes)
	{
		System.out.println("Array of bytes received: "+bytes);
		byte [] revKey = new byte [key.length];
		for( int i = 0 ; i < 256 ; i++ ){
			revKey[ key[i&0x000000ff]&0x000000ff ] = (byte)i;
		}
		
		byte [] decoded = new byte [bytes.length];
		for( int i = 0; i< bytes.length; i++ ){
			decoded[i] = revKey[bytes[i&0x000000ff]&0x000000ff];
		}
		System.out.println("Array of decoded bytes: "+decoded);
		String msg = new String(decoded);
		System.out.println("Decoded message: "+msg);
		return ( msg);
		/*
		byte [] decoded = new byte [bytes.length];
		for( int i = 0 ; i < bytes.length ; i++){
			decoded[i] = (byte)find(bytes[i], bytes);
		}
		return ( new String (decoded));
		*/
	}
	private int find(byte a, byte[] ar)
	{
		for( int i = 0; i < ar.length ; i++){
			if( ar[i] == a)
				return i;
		}
		return -1;
	}
}