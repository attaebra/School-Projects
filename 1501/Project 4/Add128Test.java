import java.util.*;

public class Add128Test
{
	public static void main(String [] args)
	{
		SymCipher cipher = new Add128();
		String message = "Hello world";
		//byte [] arr = message.getBytes();
		byte[] enc = cipher.encode(message);
		System.out.println(enc);
		String dec = cipher.decode(enc);
		System.out.println(dec);
		
		//String key = "55537354069402101778481653628036794842136278976180846258522556912914867259913804497126990054079917596565358731643893129437576363791999371623814955870085702794269268494551668417079653238127816737063137306735121094151374536461493650101421017408490511380254348";
		
		SymCipher cipher1 = new Substitute();
		String message1 = "Hello world";
		//byte [] arr = message.getBytes();
		byte[] enc1 = cipher1.encode(message1);
		System.out.println(enc1);
		String dec1 = cipher1.decode(enc1);
		System.out.println(dec1);
	}
	
}