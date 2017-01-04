package com.sc.td;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.util.Base64Utils;


/**  
 * AES 是一种可逆加密算法，对用户的敏感信息加密处理 对原始数据进行AES加密后，在进行Base64编码转化；  
 */  
public class EncryptTest {  
  
    /*  
     * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。  
     */  
    private String sKey = "1234567890ABCDEF";  
    private String ivParameter = "1234567890ABCDEF";  
    private static EncryptTest instance = null;  
  
    private EncryptTest() {  
  
    }  
  
    public static EncryptTest getInstance() {  
        if (instance == null)  
            instance = new EncryptTest();  
        return instance;  
    }  
  
    public static String Encrypt(String encData ,String secretKey,String vector) throws Exception {  
  
        if(secretKey == null) {  
            return null;  
        }  
        if(secretKey.length() != 16) {  
            return null;  
        }  
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
        byte[] raw = secretKey.getBytes();  
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
        IvParameterSpec iv = new IvParameterSpec(vector.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度  
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);  
        byte[] encrypted = cipher.doFinal(encData.getBytes("utf-8"));  
        return Base64.encode(encrypted);// 此处使用BASE64做转码。  
    }  
  
  
    // 加密  
    public String encrypt(String sSrc) throws Exception {  
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
        byte[] raw = sKey.getBytes();  
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度  
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);  
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));  
        return Base64.encode(encrypted);// 此处使用BASE64做转码。  
    }  
  
    // 解密  
    public String decrypt(String sSrc) throws Exception {  
        try {  
            byte[] raw = sKey.getBytes("ASCII");  
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());  
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);  
            byte[] encrypted1 = Base64.decode(sSrc);// 先用base64解密  
            byte[] original = cipher.doFinal(encrypted1);  
            String originalString = new String(original, "utf-8");  
            return originalString;  
        } catch (Exception ex) {  
            return null;  
        }  
    }  
  
    public String decrypt(String sSrc,String key,String ivs) throws Exception {  
        try {  
            byte[] raw = key.getBytes("ASCII");  
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
            IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());  
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);  
            byte[] encrypted1 = Base64.decode(sSrc);// 先用base64解密  
            byte[] original = cipher.doFinal(encrypted1);  
            String originalString = new String(original, "utf-8");  
            return originalString;  
        } catch (Exception ex) {  
            return null;  
        }  
    }  
  
    public static String encodeBytes(byte[] bytes) {  
        StringBuffer strBuf = new StringBuffer();  
  
        for (int i = 0; i < bytes.length; i++) {  
            strBuf.append((char) (((bytes[i] >> 4) & 0xF) + ((int) 'a')));  
            strBuf.append((char) (((bytes[i]) & 0xF) + ((int) 'a')));  
        }  
  
        return strBuf.toString();  
    }  
  
    public static void test() {  
        try {  
  
            // 需要加密的字串  
            String cSrc = "你好";  
  
            // 加密  
            long lStart = System.currentTimeMillis();  
            String enString = EncryptTest.getInstance().encrypt(cSrc);  
            System.out.println("1:" + enString);  
  
            long lUseTime = System.currentTimeMillis() - lStart;  
            System.out.println("2：" + lUseTime + "ms");  
            // 解密  
            lStart = System.currentTimeMillis();  
            String DeString = EncryptTest.getInstance().decrypt(enString);  
            System.out.println("3：" + DeString);  
            lUseTime = System.currentTimeMillis() - lStart;  
            System.out.println("4：" + lUseTime + "ms");  
        } catch (Exception e) {  
            e.getMessage();  
        }  
    }  
  
    public static void main(String[] args) {
		test();
	}
  
}  