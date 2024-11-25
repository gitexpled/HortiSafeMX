/*    */ package lib.io;
/*    */ 
/*    */ import java.io.FileInputStream;
/*    */ import java.util.Locale;
/*    */ import java.util.Properties;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ public class ConfigProperties
/*    */ {
/* 10 */   public static String OS = System.getProperty("os.name").toLowerCase();
/*    */   
/*    */   public static String getProperty(String value) {
/* 13 */     FileInputStream fos = null;
/*    */     try {
/* 15 */       Properties pro = new Properties();
/*    */       
/* 17 */       String absPath = OS.contains("win") ? (String.valueOf(getLocalProperty("winDisk")) + getLocalProperty("propertiesFile")) : 
/* 18 */         getLocalProperty("propertiesFile");
/* 19 */       fos = new FileInputStream(absPath);
/* 20 */       pro.load(fos);
/*    */       
/* 22 */       String respuesta = pro.getProperty(value);
/* 23 */       fos.close();
/* 24 */       return respuesta;
/*    */     
/*    */     }
/* 27 */     catch (Exception e) {
/* 28 */       System.out.println(e.getMessage());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 37 */       return "";
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static Properties getPropertiesByServerFile(String pFile) {
/* 43 */     Properties pro = new Properties();
/*    */     try {
/* 45 */       pro.load(new FileInputStream(pFile));
/* 46 */       return pro;
/* 47 */     } catch (Exception e) {
/* 48 */       System.out.println(e.getMessage());
/*    */       
/* 50 */       return pro;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static String getLocalProperty(String value) {
/* 55 */     ResourceBundle config = ResourceBundle.getBundle("config", Locale.ENGLISH);
/* 56 */     return config.getString(value);
/*    */   }
/*    */   
/*    */   public static String getPropertyByFile(String value, String pFile) {
/*    */     try {
/* 61 */       Properties pro = new Properties();
/*    */       
/* 63 */       String absPath = OS.contains("win") ? (String.valueOf(getLocalProperty("winDisk")) + getLocalProperty("propertiesPath") + pFile + ".properties") : (
/* 64 */         String.valueOf(getLocalProperty("propertiesPath")) + pFile + ".properties");
/* 65 */       pro.load(new FileInputStream(absPath));
/* 66 */       return pro.getProperty(value);
/* 67 */     } catch (Exception e) {
/* 68 */       System.out.println(e.getMessage());
/*    */       
/* 70 */       return "";
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\io\ConfigProperties.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */