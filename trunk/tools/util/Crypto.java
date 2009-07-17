/*
 Copyright (c) 2007-2009 WebAppShowCase DBA Appcloem (http://www.appcloem.com). All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

  1. Redistributions of source code must retain the above copyright notice,
     this list of conditions and the following disclaimer.

  2. Redistributions in binary form must reproduce the above copyright
     notice, this list of conditions and the following disclaimer in
     the documentation and/or other materials provided with the distribution.

  3. The names of the authors may not be used to endorse or promote products
     derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL WebAppShowCase
OR ANY CONTRIBUTORS TO THIS SOFTWARE BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package tools.util;

// Step 0: import the Crypto-J classes
import java.util.*;
import java.io.*;
import java.io.*;
     import java.math.BigInteger;
     import java.security.*;
     import java.security.spec.*;
     import java.security.interfaces.*;
     import javax.crypto.*;
     import javax.crypto.spec.*;
     import javax.crypto.interfaces.*;
     import com.sun.crypto.provider.SunJCE;
import javax.crypto.*;	 

public class Crypto {

              static byte[] salt = {
                  (byte)0xc7, (byte)0x73, (byte)0x21, (byte)0x8c,
                  (byte)0x7e, (byte)0xc8, (byte)0xee, (byte)0x99
              };
              private static int count = 20;
			  
			  private static char[] password = null;
			  private static boolean success = false;
				private static int tries = 0;
	   
	   	public static void setPassword(String p){
			//("Set Crypto Password: " + p.substring(0,2));
			tries++;
			if (tries > 5)
			{
				System.out.println("Unable to Set Crypto Password");
				return;
			}

			if (password == null || !success)
				password = p.toCharArray();
			            SunJCE jce = new SunJCE();
                 Security.addProvider(jce);	
		}
		
		public static byte[] encrypt(byte[] ciphertext) throws Exception{
			return encrypt(ciphertext,password);
		}
		
		//DESede
			public static byte[] encrypt(byte[] cleartext,char[] pass) throws Exception{
				return encrypt(cleartext,pass,"DES");
			}
			
			public static byte[] encrypt(byte[] cleartext,String inp,String alg) throws Exception{
				FileInputStream fin = new FileInputStream(inp);
				byte[] ret = encrypt(cleartext,StreamUtil.readChars(fin),alg);
				fin.close();
				return ret;
			}
			
			
			
    public static Cipher getBlowfishCipher(String fn,boolean tf) throws Exception {
	return getBlowfishCipher(true,fn,tf);
}			

    public final static String digest(String credentials) {
                                      	
                                  return digest(credentials,"SHA",null);
                                      }


    public final static String digest(String credentials, String algorithm) {
                                      	
                                  return digest(credentials,algorithm,null);
                                      }


    public final static String digest(String credentials, String algorithm,
                                      String encoding) {

        try {
            // Obtain a new message digest with "digest" encryption
            MessageDigest md =
                (MessageDigest) MessageDigest.getInstance(algorithm).clone();

            // encode the credentials
            // Should use the digestEncoding, but that's not a static field
            if (encoding == null) {
                md.update(credentials.getBytes());
            } else {
                md.update(credentials.getBytes(encoding));                
            }

            // Digest the credentials and return as hexadecimal
            return (convert(md.digest()));
        } catch(Exception ex) {
            tools.util.LogMgr.err("Crypto.digest " + ex.toString());
            return credentials;
        }

    }







public static final int[] DEC = {
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        00, 01, 02, 03, 04, 05, 06, 07,  8,  9, -1, -1, -1, -1, -1, -1,
        -1, 10, 11, 12, 13, 14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, 10, 11, 12, 13, 14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    };




    public static byte[] convert(String digits) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int i = 0; i < digits.length(); i += 2) {
            char c1 = digits.charAt(i);
            if ((i+1) >= digits.length())
                throw new IllegalArgumentException
                    (("convert.odd"));
            char c2 = digits.charAt(i + 1);
            byte b = 0;
            if ((c1 >= '0') && (c1 <= '9'))
                b += ((c1 - '0') * 16);
            else if ((c1 >= 'a') && (c1 <= 'f'))
                b += ((c1 - 'a' + 10) * 16);
            else if ((c1 >= 'A') && (c1 <= 'F'))
                b += ((c1 - 'A' + 10) * 16);
            else
                throw new IllegalArgumentException
                    (("convert.bad"));
            if ((c2 >= '0') && (c2 <= '9'))
                b += (c2 - '0');
            else if ((c2 >= 'a') && (c2 <= 'f'))
                b += (c2 - 'a' + 10);
            else if ((c2 >= 'A') && (c2 <= 'F'))
                b += (c2 - 'A' + 10);
            else
                throw new IllegalArgumentException
                    (("convert.bad"));
            baos.write(b);
        }
        return (baos.toByteArray());

    }


    /**
     * Convert a byte array into a printable format containing a
     * String of hexadecimal digit characters (two per byte).
     *
     * @param bytes Byte array representation
     */
    public static String convert(byte bytes[]) {

        StringBuffer sb = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            sb.append(convertDigit((int) (bytes[i] >> 4)));
            sb.append(convertDigit((int) (bytes[i] & 0x0f)));
        }
        return (sb.toString());

    }




    private static char convertDigit(int value) {

        value &= 0x0f;
        if (value >= 10)
            return ((char) (value - 10 + 'a'));
        else
            return ((char) (value + '0'));

    }




















    public static Cipher getBlowfishCipher(boolean b64, String fn,boolean tf) throws Exception {

        byte[] raw = null;
        File f = new File(fn);
        if (f.exists() && f.length() > 0)
        	raw = FileUtil.getBytesFromFile(f);
        if (raw == null || raw.length < 1)
        {
	        KeyGenerator kgen = KeyGenerator.getInstance("Blowfish");
	        String size = System.getProperty("Blowfish.Key.Size");
	        if (size != null)//size = "448";
	        kgen.init(Integer.parseInt(size));
	        //kgen.init();
	        SecretKey skey = kgen.generateKey();
	        raw = skey.getEncoded();
	        byte[] rawb = raw;
	        if (b64)rawb = Base642.encode(raw);
	    	FileUtil.writeBytesToFile(f,rawb);
    	}
    	else if (b64)
    	 	raw = Base642.decode(raw);
    	 //(raw.length + " KEY " + new String(raw));
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "Blowfish");

        Cipher cipher = Cipher.getInstance("Blowfish");
		if (tf)
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        else
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        return cipher;
    }

			
			
			
			public static Cipher getCipher(String inp,boolean tf,String alg) throws Exception{
				FileInputStream fin = new FileInputStream(inp);
				char[] pass = StreamUtil.readChars(fin);
				fin.close();
				return getCipher(pass,tf,alg);
			}

			public static byte[] encrypt(byte[] cleartext,char[] pass,String alg) throws Exception{
				return encrypt(cleartext,pass,alg,salt);
			}
			public static String encryptString(String cleartext)throws Exception{
				
				String ts = tools.util.AlphaNumeric.generateRandomAlphNumeric(8);
				return "$1$" + ts + "$"  + encryptString(cleartext,ts);
			}
			
			public static String encryptString(String cleartext,String ts)throws Exception{
				
				return new String(Base64Encoder.toBase64(encrypt(cleartext.getBytes(),cleartext.toCharArray(),"DES",ts.getBytes())));
			}
			public static byte[] encrypt(byte[] cleartext,char[] pass,String alg,byte[] tsalt) throws Exception{

              PBEKeySpec pbeKeySpec;
              PBEParameterSpec pbeParamSpec;
              SecretKeyFactory keyFac;

              // Salt

              // Iteration count

              // Create PBE parameter set
              pbeParamSpec = new PBEParameterSpec(tsalt, count);
              pbeKeySpec = new PBEKeySpec(pass);
			  //DESede
			  //PBEWithMD5AndDES
              keyFac = SecretKeyFactory.getInstance("PBEWithMD5And" + alg);
              SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);

              // Create PBE Cipher
              Cipher pbeCipher = Cipher.getInstance("PBEWithMD5And" + alg);

              // Initialize PBE Cipher with key and parameters
              pbeCipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);


              // Encrypt the cleartext
              return pbeCipher.doFinal(cleartext);

		  }
		  
		  public static byte[] decrypt(byte[] ciphertext) throws Exception{
		  	return decrypt(ciphertext,password);
		  }
		  public static byte[] decrypt(byte[] ciphertext,char[] pass) throws Exception{

		    PBEKeySpec pbeKeySpec;
		    PBEParameterSpec pbeParamSpec;
		    SecretKeyFactory keyFac;

		    // Salt

		    // Iteration count

		    // Create PBE parameter set
		    pbeParamSpec = new PBEParameterSpec(salt, count);

		    pbeKeySpec = new PBEKeySpec(pass);
		    keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
		    SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);

		    // Create PBE Cipher
		    Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");

		    // Initialize PBE Cipher with key and parameters
		    pbeCipher.init(Cipher.DECRYPT_MODE, pbeKey, pbeParamSpec);
			

		    byte[] b = pbeCipher.doFinal(ciphertext);
			if (! success && b != null)
			{
				success = true;
				//("FIRST CRYPTO SUCCESS");
			}
			return b;

		}
		  
		public static Cipher getCipher() throws Exception{
			return getCipher(password,true);
		}
		
		public static Cipher getCipher(char[] pass) throws Exception{
			return getCipher(pass,true);
		}		

		public static Cipher getCipher(char[] pass, boolean tf) throws Exception{
			return getCipher(pass,tf,"DES");
		}
		public static Cipher getCipher(char[] pass, boolean tf,String alg) throws Exception{
			if (pass == null)
				pass = password;
		  PBEKeySpec pbeKeySpec;
		  PBEParameterSpec pbeParamSpec;
		  SecretKeyFactory keyFac;

		  // Salt

		  // Iteration count

		  // Create PBE parameter set
		  pbeParamSpec = new PBEParameterSpec(salt, count);
		  pbeKeySpec = new PBEKeySpec(pass);
		  keyFac = SecretKeyFactory.getInstance("PBEWithMD5And" + alg);
		  SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);

		  // Create PBE Cipher
		  Cipher pbeCipher = Cipher.getInstance("PBEWithMD5And" + alg);

		  // Initialize PBE Cipher with key and parameters
		  if (tf)
		  pbeCipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);
			else
			pbeCipher.init(Cipher.DECRYPT_MODE, pbeKey, pbeParamSpec);	

		  // Encrypt the cleartext
		   return pbeCipher;

		}





}