
public class Substitute implements SymCipher
{
	byte[] key;
	
	public void Substitute( byte [] arr )
	{
		key = arr;
	}
	
	public byte[] getKey()
	{
		return key;
	}
	public byte [] encode(String S)
	{
		byte [] s = S.getBytes();
		byte [] encoded = new byte [s.length];
		for( int i = 0; i < s.length; i++ ){
			encoded[i] = key[s[i]];
		}
		return encoded;
	}
	public String decode(byte [] bytes)
	{
		byte [] revKey = new byte [key.length];
		for( int i = 0 ; i < 256 ; i++ ){
			revKey[ key[i] ] = i;
		}
		
		byte [] decoded = new byte [bytes.length];
		for( int i = 0; i< bytes.length; i++ ){
			decoded[i] = revKey[bytes[i]];
		}
		return ( new String(decoded) );
	}
}
			