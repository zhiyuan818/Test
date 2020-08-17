package com.jjxt.ssm.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {


	/**
	 * 是否为空
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isEmpty(Object o) {
		return (o==null || "".equals(o) || "null".equals(o));
	}


	/**
	 * 是否可转化为数字
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isNum(Object o) {
		try {
			new BigDecimal(o.toString());
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 是否可转化为Long型数字
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isLong(Object o) {
		try {
			new Long(o.toString());
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 转化为Long型数字, 不可转化时返回0
	 * 
	 * @param o
	 * @return
	 */
	public static Long toLong(Object o) {
		if (isLong(o)) {
			return new Long(o.toString());
		} else {
			return 0L;
		}
	}

	/**
	 * 转化为int型数字, 不可转化时返回0
	 * 
	 * @param o
	 * @return
	 */
	public static int toInt(Object o) {
		if (isNum(o)) {
			return new Integer(o.toString());
		} else {
			return 0;
		}
	}

	/**
	 * 按字符从左截取固定长度字符串, 防止字符串超长, 默认截取50
	 * 
	 * @param o
	 * @return
	 */
	public static String holdmaxlength(Object o) {
		int maxlength = 50;
		if (o == null) {
			return "";
		}
		return subStringByByte(o, maxlength);
	}

	/**
	 * 从左截取固定长度字符串, 防止字符串超长, maxlength为0时默认50
	 * 
	 * @param o
	 * @return
	 */
	public static String holdmaxlength(Object o, int maxlength) {
		maxlength = maxlength <= 0 ? 50 : maxlength;
		if (o == null) {
			return "";
		}
		return subStringByByte(o, maxlength);
	}

	/**
	 * 按字节截取字符串
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	private static String subStringByByte(Object o, int len) {
		if (o == null) {
			return "";
		}
		String str = o.toString();
		String result = null;
		if (str != null) {
			byte[] a = str.getBytes();
			if (a.length <= len) {
				result = str;
			} else if (len > 0) {
				result = new String(a, 0, len);
				int length = result.length();
				if (str.charAt(length - 1) != result.charAt(length - 1)) {
					if (length < 2) {
						result = null;
					} else {
						result = result.substring(0, length - 1);
					}
				}
			}
		}
		return result;
	}


	/**
	 * 逗号表达式_删除
	 * 
	 * @param commaexpress
	 *            原逗号表达式 如 A,B,C
	 * @param delelement
	 *            删除元素 C,A
	 * @return B
	 */
	public static String comma_del(String commaexpress, String delelement) {
		if ((commaexpress == null) || (delelement == null) || (commaexpress.trim().equals(delelement.trim()))) {
			return "";
		}
		String[] deletelist = delelement.split(",");
		String result = commaexpress;
		for (String delstr : deletelist) {
			result = comma_delone(result, delstr);
		}
		return result;
	}

	/**
	 * 逗号表达式_单一删除
	 * 
	 * @param commaexpress
	 *            原逗号表达式 如 A,B,C
	 * @param delelement
	 *            删除元素 C
	 * @return A,B
	 */
	public static String comma_delone(String commaexpress, String delelement) {
		if ((commaexpress == null) || (delelement == null) || (commaexpress.trim().equals(delelement.trim()))) {
			return "";
		}
		String[] strlist = commaexpress.split(",");
		StringBuffer result = new StringBuffer();
		for (String str : strlist) {
			if ((!str.trim().equals(delelement.trim())) && (!"".equals(str.trim()))) {
				result.append(str.trim() + ",");
			}
		}
		return result.toString().substring(0, result.length() - 1 > 0 ? result.length() - 1 : 0);
	}


	/**
	 * 替换字符串,支持字符串为空的情形
	 * 
	 * @param strData
	 * @param regex
	 * @param replacement
	 * @return
	 */
	public static String replace(String strData, String regex, String replacement) {
		return strData == null ? "" : strData.replaceAll(regex, replacement);
	}

	/**
	 * 字符串转为HTML显示字符
	 * 
	 * @param strData
	 * @return
	 */
	public static String String2HTML(String strData) {
		if (strData == null || "".equals(strData)) {
			return "";
		}
		strData = replace(strData, "&", "&amp;");
		strData = replace(strData, "<", "&lt;");
		strData = replace(strData, ">", "&gt;");
		strData = replace(strData, "\"", "&quot;");
		return strData;
	}

	/** * 把异常信息转换成字符串，以方便保存 */
	public static String getexceptionInfo(Exception e) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			e.printStackTrace(new PrintStream(baos));
		} finally {
			try {
				baos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return baos.toString();
	}

	/** 过滤特殊符号 */
	public static String regex(String str) {
		Pattern pattern = Pattern.compile("[0-9-:/ ]");// 中文汉字编码区间
		Matcher matcher;
		char[] array = str.toCharArray();
		for (int i = 0; i < array.length; i++) {
			matcher = pattern.matcher(String.valueOf(array[i]));
			if (!matcher.matches()) {// 空格暂不替换
				str = str.replace(String.valueOf(array[i]), "");// 特殊字符用空字符串替换
			}
		}

		return str;
	}

	public static String comma_insert(String commaexpress, String newelement, int index) {
		int length = commaexpress.length();
		if (index > length) {
			index = length;
		} else if (index < 0) {
			index = 0;
		}
		String result = commaexpress.substring(0, index) + newelement
				+ commaexpress.substring(index, commaexpress.length());
		return result;
	}

	/**
	 * 将"/"替换成"\"
	 * 
	 * @param strDir
	 * @return
	 */
	public static String changeDirection(String strDir) {
		String s = "/";
		String a = "\\";
		if (strDir != null && !" ".equals(strDir)) {
			if (strDir.contains(s)) {
				strDir = strDir.replace(s, a);
			}
		}
		return strDir;
	}

	/**
	 * 去除字符串中 头和尾的空格，中间的空格保留
	 * 
	 * @Title: trim @Description: TODO @return String @throws
	 */
	public static String trim(String s) {
		int i = s.length();// 字符串最后一个字符的位置
		int j = 0;// 字符串第一个字符
		int k = 0;// 中间变量
		char[] arrayOfChar = s.toCharArray();// 将字符串转换成字符数组
		while ((j < i) && (arrayOfChar[(k + j)] <= ' '))
			++j;// 确定字符串前面的空格数
		while ((j < i) && (arrayOfChar[(k + i - 1)] <= ' '))
			--i;// 确定字符串后面的空格数
		return (((j > 0) || (i < s.length())) ? s.substring(j, i) : s);// 返回去除空格后的字符串
	}

	/**
	 * 得到大括号中的内容
	 * 
	 * @param str
	 * @return
	 */
	public static String getBrackets(String str) {
		int a = str.indexOf("{");
		int c = str.indexOf("}");
		if (a >= 0 && c >= 0 & c > a) {
			return (str.substring(a + 1, c));
		} else {
			return str;
		}
	}

	/**
	 * 将字符串中所有的，替换成|
	 * 
	 * @param str
	 * @return
	 */
	public static String commaToVerti(String str) {
		if (str != null && !"".equals(str) && str.contains(",")) {
			return str.replaceAll(",", "|");
		} else {
			return str;
		}
	}

	/**
	 * 去掉字符串中、前、后的空格
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static String extractBlank(String name) {
		if (name != null && !"".equals(name)) {
			return name.replaceAll(" +", "");
		} else {
			return name;
		}
	}

	/**
	 * 将null换成""
	 * 
	 * @param str
	 * @return
	 */
	public static String ConvertStr(String str) {
		return str != null && !"null".equals(str) ? str.trim() : "";
	}

	/**
	 * 将数组用逗号拼接成字符串
	 * 
	 * @param param
	 * @return
	 */
	public static String ArraySplicing(String[] param) {
		StringBuffer sb = new StringBuffer();
		if ((!isEmpty(param)) && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				if (i == 0) {
					sb.append(param[i]);
				} else {
					sb.append(",").append(param[i]);
				}
			}
		}
		return sb.toString();
	}

	public static String urlencode(String src) {
		String result = null;

		if (src != null) {
			try {
				result = URLEncoder.encode(src, "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public static String urlencode(String src, String encoding) {
		String result = null;

		if (src != null) {
			try {
				result = URLEncoder.encode(src, encoding);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * URLEncode the string.
	 * 
	 * @param src
	 * @return
	 */
	public static String urldecode(String src) {
		String result = null;

		if (src != null) {
			try {
				result = URLDecoder.decode(src, "UTF-8"	);
			} catch (Exception e) {
				result = src;
				e.printStackTrace();
			}
		}

		return result;
	}

	public static String urldecode(String src, String encoding) {
		String result = null;

		if (src != null) {
			try {
				result = URLDecoder.decode(src, encoding);
			} catch (Exception e) {
				result = src;
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * Convert bytes to Hex String.
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex(byte[] bytes) {
		if (bytes == null)
			return null;

		final char[] hexArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] hexChars = new char[bytes.length * 2];
		int v;
		for (int j = 0; j < bytes.length; j++) {
			v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars).toLowerCase();
	}

	/**
	 * Compute a SHA1 digest.
	 */
	public static String sha1(String src) {

		String result = null;
		MessageDigest digest = null;

		if (src == null)
			return null;

		try {
			digest = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		if (digest != null) {
			digest.reset();
			byte[] data = null;

			try {
				data = digest.digest(src.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			if (data != null) {
				result = bytesToHex(data);
			}
		}
		return result;
	}

	/**
	 * Compute a MD5 digest.
	 * 
	 * @param src
	 * @return
	 */
	public static String md5(String src) {

		String result = null;
		MessageDigest digest = null;

		if (src == null)
			return null;

		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		if (digest != null) {
			digest.reset();
			byte[] data = null;

			try {
				data = digest.digest(src.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			if (data != null) {
				result = bytesToHex(data);
			}
		}
		return result;
	}
	public static String getStringRandom(int length) {  

        String val = "";  
        Random random = new Random();        
        //length为几位密码 
        for(int i = 0; i < length; i++) {          
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";  
            //输出字母还是数字  
            if( "char".equalsIgnoreCase(charOrNum) ) {  
                //输出是大写字母还是小写字母  
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;  
                val += (char)(random.nextInt(26) + temp);  
            } else if( "num".equalsIgnoreCase(charOrNum) ) {  
                val += String.valueOf(random.nextInt(10));  
            }  
        }  
        return val;  
    } 
	
	public static String urldecodeChineseString(String val) {
		if(isEmpty(val)){
			return "";
		}
		String decStr = StringUtil.urldecode(val, "ISO-8859-1");
		if (decStr.matches("^(?:[\\x00-\\x7f]|[\\xe0-\\xef][\\x80-\\xbf]{2})+$")) {
			// LOGGER.debug("Detected Chinese String encoding: UTF-8 for {}.",
			// val);
			return StringUtil.urldecode(val, "UTF-8");
		} else {
			// LOGGER.debug("Detected Chinese String encoding: GBK for {}.",
			// val);
			return StringUtil.urldecode(val, "GBK");
		}
	}
	public static Map<String, String> stringToMap(String plain, String eqaulsType,
            String spliceType) {
		if (plain == null || plain.isEmpty()) {
			return null;
		}
		Map<String, String> map = new HashMap<>();
		String[] split = plain.split(spliceType);
		for (String kv : split) {
			if ("|".equals(kv)) {
				continue;
			}
			String[] kvArr = kv.split(eqaulsType);
			if (kvArr.length == 2) {
				map.put(kvArr[0], kvArr[1]);
			} else {
				map.put(kvArr[0], "");
			}
		}
		return map;
	}
	public static void main(String[] args) {
		System.out.println(isNum("a"));
		System.out.println(isNum("-1"));
		System.out.println(isNum("01"));
		System.out.println(isNum("1E3"));
		System.out.println(isNum("1.a"));
		System.out.println(isLong("014650"));
		System.out.println(Long.parseLong("014650"));
	}

}