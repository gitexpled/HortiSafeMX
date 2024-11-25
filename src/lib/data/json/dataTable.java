/*    */ package lib.data.json;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ public class dataTable
/*    */ {
/*    */   private ArrayList<String[]> Data;
/*    */   private int draw;
/*    */   private int recordsTotal;
/*    */   private int recordsFiltered;
/*    */   
/*    */   public void init() {
/* 14 */     this.Data = (ArrayList)new ArrayList<>();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDatas(ArrayList<String[]> value) {
/* 20 */     this.Data = value;
/*    */   }
/*    */   
/*    */   public ArrayList<String[]> getData() {
/* 24 */     return this.Data;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setData(String[] value) {
/* 29 */     this.Data.add(value);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDraw() {
/* 34 */     return this.draw;
/*    */   }
/*    */   
/*    */   public void setDraw(int value) {
/* 38 */     this.draw = value;
/*    */   }
/*    */   
/*    */   public int getRecordsTotal() {
/* 42 */     return this.recordsTotal;
/*    */   }
/*    */   
/*    */   public void setRecordsTotal(int value) {
/* 46 */     this.recordsTotal = value;
/*    */   }
/*    */   
/*    */   public int getRecordsFiltered() {
/* 50 */     return this.recordsFiltered;
/*    */   }
/*    */   
/*    */   public void setRecordsFiltered(int value) {
/* 54 */     this.recordsFiltered = value;
/*    */   }
/*    */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\json\dataTable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */