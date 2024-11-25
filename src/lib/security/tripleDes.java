/*    */ package lib.security;
/*    */ 
/*    */ import java.security.MessageDigest;
/*    */ import java.util.Arrays;
/*    */ import javax.crypto.Cipher;
/*    */ import javax.crypto.SecretKey;
/*    */ import javax.crypto.spec.IvParameterSpec;
/*    */ import javax.crypto.spec.SecretKeySpec;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class tripleDes
/*    */ {
/*    */   public static byte[] encrypt(String message) throws Exception {
/* 16 */     MessageDigest md = MessageDigest.getInstance("md5");
/* 17 */     byte[] digestOfPassword = md.digest("exGo*drecriptAPI".getBytes("utf-8"));
/* 18 */     byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
/* 19 */     for (int j = 0, k = 16; j < 8;) {
/* 20 */       keyBytes[k++] = keyBytes[j++];
/*    */     }
/*    */     
/* 23 */     SecretKey key = new SecretKeySpec(keyBytes, "DESede");
/* 24 */     IvParameterSpec iv = new IvParameterSpec(new byte[8]);
/* 25 */     Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
/* 26 */     cipher.init(1, key, iv);
/*    */     
/* 28 */     byte[] plainTextBytes = message.getBytes("utf-8");
/* 29 */     byte[] cipherText = cipher.doFinal(plainTextBytes);
/*    */ 
/*    */ 
/*    */     
/* 33 */     return cipherText;
/*    */   }
/*    */   
/*    */   public static String decrypt(byte[] message) throws Exception {
/* 37 */     MessageDigest md = MessageDigest.getInstance("md5");
/* 38 */     byte[] digestOfPassword = md.digest("exGo*drecriptAPI".getBytes("utf-8"));
/* 39 */     byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
/* 40 */     for (int j = 0, k = 16; j < 8;) {
/* 41 */       keyBytes[k++] = keyBytes[j++];
/*    */     }
/*    */     
/* 44 */     SecretKey key = new SecretKeySpec(keyBytes, "DESede");
/* 45 */     IvParameterSpec iv = new IvParameterSpec(new byte[8]);
/* 46 */     Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
/* 47 */     decipher.init(2, key, iv);
/*    */ 
/*    */ 
/*    */     
/* 51 */     byte[] plainText = decipher.doFinal(message);
/*    */     
/* 53 */     return new String(plainText, "UTF-8");
/*    */   }
/*    */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\security\tripleDes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */