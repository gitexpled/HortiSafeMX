/*    */ package lib.db;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.DriverManager;
/*    */ import java.sql.SQLException;
/*    */ import lib.io.ConfigProperties;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConnectionDB
/*    */ {
/* 13 */   public Connection conn = null;
/*    */ 
/*    */   
/*    */   public ConnectionDB() {
/*    */     try {
/* 19 */       Class.forName(ConfigProperties.getProperty("driverDB"));
/* 20 */       this.conn = DriverManager.getConnection(ConfigProperties.getProperty("urlDB"), ConfigProperties.getProperty("userDB"), 
/* 21 */           ConfigProperties.getProperty("passDB"));
/*    */     
/*    */     }
/* 24 */     catch (ClassNotFoundException e) {
/* 25 */       System.out.println("Clase no encontrada: " + e.getMessage());
/* 26 */     } catch (SQLException e) {
/*    */       
/* 28 */       System.out.println("Error Conexion: " + e.getMessage());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() {
/*    */     try {
/* 37 */       if (this.conn != null)
/*    */       {
/* 39 */         this.conn.close();
/*    */       }
/*    */     }
/* 42 */     catch (SQLException e) {
/*    */       
/* 44 */       System.out.println("Error cerrar conexion: " + e.getMessage());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\ConnectionDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */