package client;

import static org.junit.Assert.*;

public class ClientTest {

	@org.junit.Test
	public void testConverter() {
		String octal = "4";
		long value = 4;
		assertEquals(String.format("%s equals to %d", octal, value), Client.octal2Long(octal), value);
		
		octal = "44";
		value = 68;
		System.out.println(Client.octal2Long(octal));
		assertEquals(String.format("%s equals to %d", octal, value), Client.octal2Long(octal), value);
		
		octal = "F";
		value = 15;
		System.out.println(Client.octal2Long(octal));
		assertEquals(String.format("%s equals to %d", octal, value), Client.octal2Long(octal), value);
		
		octal = "FF";
		value = 255;
		System.out.println(Client.octal2Long(octal));
		assertEquals(String.format("%s equals to %d", octal, value), Client.octal2Long(octal), value);
	}
}
