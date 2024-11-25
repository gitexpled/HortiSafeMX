/*    */ package lib.db;
/*    */ 
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.ResultSetMetaData;
/*    */ import org.json.JSONArray;
/*    */ import org.json.JSONObject;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ResultSet2Json
/*    */ {
/*    */   public static JSONArray getJson(ResultSet rs) {
/* 16 */     JSONArray json = new JSONArray();
/*    */     try {
/* 18 */       ResultSetMetaData rsmd = rs.getMetaData();
/* 19 */       while (rs.next()) {
/* 20 */         int numColumns = rsmd.getColumnCount();
/* 21 */         JSONObject obj = new JSONObject();
/* 22 */         for (int i = 1; i <= numColumns; i++) {
/* 23 */           String column_name = rsmd.getColumnLabel(i);
/* 24 */           obj.put(column_name, rs.getObject(column_name));
/*    */         } 
/* 26 */         json.put(obj);
/*    */       } 
/* 28 */     } catch (Exception e) {
/*    */       
/* 30 */       e.printStackTrace();
/*    */     } 
/*    */     
/* 33 */     return json;
/*    */   }
/*    */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\ResultSet2Json.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */