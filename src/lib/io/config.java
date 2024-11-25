/*    */ package lib.io;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class config
/*    */ {
/*    */   public static String getProperty(String value) {
/* 12 */     String fileName = "config";
/*    */     
/* 14 */     ResourceBundle resourceBundle = ResourceBundle.getBundle(fileName, Locale.ENGLISH);
/* 15 */     String result = null;
/*    */     
/*    */     try {
/* 18 */       result = resourceBundle.getString(value);
/*    */     }
/* 20 */     catch (Exception ex) {
/* 21 */       ex.printStackTrace();
/*    */     } 
/*    */     
/* 24 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\io\config.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */