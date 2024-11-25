/*     */ package lib.db;
/*     */ 
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import lib.struc.alarmaComponente;
/*     */ import lib.struc.filterSql;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class alarmaComponenteDB
/*     */ {
/*     */   public static alarmaComponente getId(String id) {
/*  24 */     ConnectionDB db = new ConnectionDB();
/*  25 */     alarmaComponente o = null;
/*  26 */     Statement stmt = null;
/*  27 */     String sql = "";
/*     */     
/*     */     try {
/*  30 */       stmt = db.conn.createStatement();
/*  31 */       sql = "Select * from `vw_alarmacomponente` where codProducto='" + id + "'";
/*  32 */       ResultSet rs = stmt.executeQuery(sql);
/*  33 */       if (rs.next()) {
/*     */         
/*  35 */         o = new alarmaComponente();
/*  36 */         o.setCodProducto(rs.getString("codProducto"));
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  41 */       rs.close();
/*  42 */       stmt.close();
/*  43 */     } catch (Exception ex) {
/*     */       
/*  45 */       System.out.println("Error: " + ex.getMessage());
/*     */     } finally {
/*  47 */       db.close();
/*     */     } 
/*  49 */     return o;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update(alarmaComponente o) throws Exception {
/*  54 */     PreparedStatement ps = null;
/*  55 */     String sql = "";
/*  56 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/*  57 */     ConnectionDB db = new ConnectionDB();
/*     */     try {
/*  59 */       db.conn.setAutoCommit(false);
/*     */ 
/*     */       
/*  62 */       sql = "update  mailExcel set codProducto='" + o.getCodProductoNew() + "' where codProducto='" + o.getCodProducto() + "'";
/*     */       
/*  64 */       System.out.println(sql);
/*     */       
/*  66 */       ps = db.conn.prepareStatement(sql);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  72 */       ps.executeUpdate();
/*     */       
/*  74 */       db.conn.commit();
/*  75 */       procesosDB.upd_PostComponente(o.getIdTemporada(), o.getCodProductoNew());
/*  76 */       db.conn.close();
/*     */     }
/*  78 */     catch (SQLException e) {
/*     */       
/*  80 */       System.out.println("Error: " + e.getMessage());
/*  81 */       System.out.println("sql: " + sql);
/*  82 */       throw new Exception("update: " + e.getMessage());
/*  83 */     } catch (Exception e) {
/*  84 */       e.printStackTrace();
/*  85 */       System.out.println("Error2: " + e.getMessage());
/*  86 */       throw new Exception("update: " + e.getMessage());
/*     */     } finally {
/*     */       
/*  89 */       db.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int getAllcount(ArrayList<filterSql> filter) throws Exception {
/*  94 */     int total = 0;
/*  95 */     Statement stmt = null;
/*  96 */     String sql = "";
/*  97 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 100 */       stmt = db.conn.createStatement();
/*     */       
/* 102 */       sql = "SELECT count(1) FROM vw_alarmacomponente ";
/*     */       
/* 104 */       if (filter.size() > 0) {
/* 105 */         String andSql = "";
/* 106 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 107 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 109 */         while (f.hasNext()) {
/*     */           
/* 111 */           filterSql row = f.next();
/* 112 */           if (!row.getValue().equals("")) {
/*     */             
/* 114 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 116 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 117 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 118 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 120 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 122 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 123 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 124 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 127 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like'%" + row.getValue() + "%'";
/* 128 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 134 */       ResultSet rs = stmt.executeQuery(sql);
/* 135 */       while (rs.next()) {
/* 136 */         total = rs.getInt(1);
/*     */       }
/* 138 */       rs.close();
/* 139 */       stmt.close();
/* 140 */       db.conn.close();
/*     */     
/*     */     }
/* 143 */     catch (SQLException e) {
/*     */       
/* 145 */       System.out.println("Error: " + e.getMessage());
/* 146 */       System.out.println("sql: " + sql);
/* 147 */       throw new Exception("getLimitesAll: " + e.getMessage());
/*     */     } finally {
/* 149 */       db.close();
/*     */     } 
/* 151 */     return total;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<alarmaComponente> getAll(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 157 */     ArrayList<alarmaComponente> arr = new ArrayList<>();
/* 158 */     Statement stmt = null;
/* 159 */     String sql = "";
/* 160 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 163 */       stmt = db.conn.createStatement();
/*     */       
/* 165 */       sql = "SELECT * FROM vw_alarmacomponente ";
/*     */       
/* 167 */       if (filter.size() > 0) {
/* 168 */         String andSql = "";
/* 169 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 170 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 172 */         while (f.hasNext()) {
/*     */           
/* 174 */           filterSql row = f.next();
/*     */           
/* 176 */           if (!row.getValue().equals("")) {
/*     */             
/* 178 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 180 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 181 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 182 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 184 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 186 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 187 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 188 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 191 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 192 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 197 */       if (!order.equals("")) {
/* 198 */         sql = String.valueOf(sql) + " order by " + order;
/*     */       }
/*     */       
/* 201 */       if (length > 0) {
/* 202 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/* 204 */       ResultSet rs = stmt.executeQuery(sql);
/* 205 */       while (rs.next()) {
/* 206 */         alarmaComponente o = new alarmaComponente();
/* 207 */         o.setCantidad(rs.getInt("cantidad"));
/*     */         
/* 209 */         o.setCodProducto(rs.getString("codProducto"));
/* 210 */         o.setEspecie(rs.getString("especie"));
/* 211 */         arr.add(o);
/*     */       } 
/* 213 */       rs.close();
/* 214 */       stmt.close();
/* 215 */       db.conn.close();
/*     */     }
/* 217 */     catch (SQLException e) {
/*     */       
/* 219 */       System.out.println("Error: " + e.getMessage());
/* 220 */       System.out.println("sql: " + sql);
/* 221 */       throw new Exception("getLimite: " + e.getMessage());
/*     */     } finally {
/* 223 */       db.close();
/*     */     } 
/*     */     
/* 226 */     return arr;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\alarmaComponenteDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */