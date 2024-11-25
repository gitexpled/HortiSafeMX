/*    */ package lib.db;
/*    */ 
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ 
/*    */ public class procesosDB
/*    */ {
/*    */   public static void setRestriciones(int idTemporada) throws Exception {
/* 10 */     PreparedStatement ps = null;
/* 11 */     String sql = "";
/* 12 */     ConnectionDB db = new ConnectionDB();
/*    */     
/*    */     try {
/* 15 */       db.conn.setAutoCommit(false);
/*    */ 
/*    */       
/* 18 */       sql = "{ CALL sp_createRest(" + idTemporada + ") }";
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 23 */       ps = db.conn.prepareStatement(sql);
/* 24 */       ps.executeUpdate();
/* 25 */       db.conn.commit();
/* 26 */       db.conn.close();
/*    */     }
/* 28 */     catch (SQLException e) {
/*    */       
/* 30 */       System.out.println("Error: " + e.getMessage());
/* 31 */       System.out.println("sql: " + sql);
/* 32 */       throw new Exception("setRestriciones: " + e.getMessage());
/* 33 */     } catch (Exception e) {
/* 34 */       System.out.println("Error: " + e.getMessage());
/* 35 */       throw new Exception("setRestriciones: " + e.getMessage());
/*    */     } finally {
/* 37 */       db.close();
/*    */     } 
/*    */   }
/*    */   public static void upd_PostComponente(int idTemporada, String codProducto) throws Exception {
/* 10 */     PreparedStatement ps = null;
/* 11 */     String sql = "";
/* 12 */     ConnectionDB db = new ConnectionDB();
/*    */     
/*    */     try {
/* 15 */       db.conn.setAutoCommit(false);
/*    */ 
/*    */       
/* 18 */       sql = "{ CALL upd_PostComponente(" + idTemporada + ", '"+codProducto+"') }";
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 23 */       ps = db.conn.prepareStatement(sql);
/* 24 */       ps.executeUpdate();
/* 25 */       db.conn.commit();
/* 26 */       db.conn.close();
/*    */     }
/* 28 */     catch (SQLException e) {
/*    */       
/* 30 */       System.out.println("Error: " + e.getMessage());
/* 31 */       System.out.println("sql: " + sql);
/* 32 */       throw new Exception("setRestriciones: " + e.getMessage());
/* 33 */     } catch (Exception e) {
/* 34 */       System.out.println("Error: " + e.getMessage());
/* 35 */       throw new Exception("setRestriciones: " + e.getMessage());
/*    */     } finally {
/* 37 */       db.close();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\procesosDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */