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
import java.util.*;

/**
 * This class is used for common methods.
 **/
//SharedMethods
public class AlphaNumeric{
private static char[] m_dict;
private static char[] lcdict;
private static String m_dictlowercase = "abcdefghijklmnopqrstuvwxyz0123456789";

static{
				String an = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
				m_dict = an.toCharArray();
				an = "abcdefghijklmnopqrstuvwxyz";
				lcdict = an.toCharArray();
		//		String anl = "abcdefghijklmnopqrstuvwxyz0123456789";
		//		m_dictlowercase = anl.toCharArray();
}


			public static boolean isAlphaNumeric(String s){
				if (!StringUtil.isRealString(s))
					return false;
				String an = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
				char[] c = s.toCharArray();
				for (int ct = 0; ct < c.length;ct++)
					if (an.indexOf(c[ct]) < 0)
						return false;
				return true;
	}


	public static boolean isValid(String s,String o){
				if (!StringUtil.isRealString(s))
					return false;
				String an = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789" + o;
				char[] c = s.toCharArray();
				for (int ct = 0; ct < c.length;ct++)
					if (an.indexOf(c[ct]) < 0)
						return false;
				return true;
	}


				public static boolean isValidPassword(String s){
				if (!StringUtil.isRealString(s))
					return false;
				String an = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()~_+-";
				char[] c = s.toCharArray();
				for (int ct = 0; ct < c.length;ct++)
					if (an.indexOf(c[ct]) < 0)
						return false;
				return true;
	}


	public static boolean isAlphaNumeric(String s,String allw){
		if (!StringUtil.isRealString(s))
			return false;
		String an = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		char[] c = s.toCharArray();
		for (int ct = 0; ct < c.length;ct++)
			if (an.indexOf(c[ct]) < 0 && allw.indexOf(c[ct]) < 0)
				return false;
		return true;
}


				public static boolean isAlphaNumericLowerCase(String s){
				if (!StringUtil.isRealString(s))
					return false;
			//	String an = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
				char[] c = s.toCharArray();
				for (int ct = 0; ct < c.length;ct++)
					if (m_dictlowercase.indexOf(c[ct]) < 0)
						return false;
				return true;
	}

	public static boolean startsWithNumber(String s){
	if (!StringUtil.isRealString(s))
		return false;
//	String an = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	char[] c = s.toCharArray();
		if (new String("0123456789").indexOf(c[0]) < 0)
			return false;
	return true;
}


	static LongNum rlock = new LongNum();

	public static String generateRandomAlphNumeric(int l)
	{
		synchronized(rlock){
			long seed = System.currentTimeMillis();
			if (rlock.value== seed){
				try{
				Thread.sleep(100);
				}catch (Throwable e){}
				seed = System.currentTimeMillis();
				//rlock.value = rlock.value + 600;
			}
		while (true)
		{


			char[] ret = new char[l];
			Random rd = new Random(seed);
				ret[0] = lcdict[rd.nextInt(lcdict.length)];


			rd = new Random(seed);
			for(int ct = 1;ct < l;ct++)
				ret[ct] = m_dict[rd.nextInt(m_dict.length)];
			String re = new String(ret);
			if (!BadWords.isBad(re)){
				rlock.value = seed;

				return re;
			}
		}
		//rlock.value = seed;
	}
	}

}