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
/*     */ import lib.struc.Productor;
/*     */ import lib.struc.alarmaProductor;
/*     */ import lib.struc.filterSql;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class alarmaProductorDB
/*     */ {
/*     */   public static alarmaProductor getId(String id) {
/*  23 */     ConnectionDB db = new ConnectionDB();
/*  24 */     alarmaProductor o = null;
/*  25 */     Statement stmt = null;
/*  26 */     String sql = "";
/*     */     
/*     */     try {
/*  29 */       stmt = db.conn.createStatement();
/*  30 */       sql = "Select * from `vw_alarmaproductor` where codProductor='" + id + "'";
/*  31 */       ResultSet rs = stmt.executeQuery(sql);
/*  32 */       if (rs.next()) {
/*     */         
/*  34 */         o = new alarmaProductor();
/*  35 */         o.setCodProductor(rs.getString("codProductor"));
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  40 */       rs.close();
/*  41 */       stmt.close();
/*  42 */     } catch (Exception ex) {
/*     */       
/*  44 */       System.out.println("Error: " + ex.getMessage());
/*     */     } finally {
/*  46 */       db.close();
/*     */     } 
/*  48 */     return o;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update(alarmaProductor o) throws Exception {
/*  53 */     PreparedStatement ps = null;
/*  54 */     String sql = "";
/*  55 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/*  56 */     ConnectionDB db = new ConnectionDB();
/*     */     try {
/*  58 */       db.conn.setAutoCommit(false);
/*     */       
/*  60 */       Productor p = ProductorDB.getProductor(o.getCodProductorNew());
/*  61 */       sql = "update  mailExcel set codProductor=? , nombreProductor=? where codProductor=?";
/*     */ 
/*     */       
/*  64 */       ps = db.conn.prepareStatement(sql);
/*  65 */       ps.setString(1, o.getCodProductorNew());
/*  66 */       ps.setString(2, p.getNombre());
/*  67 */       ps.setString(3, o.getCodProductor());
/*     */ 
/*     */ 
/*     */       
/*  71 */       ps.executeUpdate();
/*     */       
/*  73 */       db.conn.commit();
/*     */       
/*  75 */       db.conn.close();
/*     */     }
/*  77 */     catch (SQLException e) {
/*     */       
/*  79 */       System.out.println("Error: " + e.getMessage());
/*  80 */       System.out.println("sql: " + sql);
/*  81 */       throw new Exception("update: " + e.getMessage());
/*  82 */     } catch (Exception e) {
/*  83 */       e.printStackTrace();
/*  84 */       System.out.println("Error2: " + e.getMessage());
/*  85 */       throw new Exception("update: " + e.getMessage());
/*     */     } finally {
/*     */       
/*  88 */       db.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int getAllcount(ArrayList<filterSql> filter) throws Exception {
/*  93 */     int total = 0;
/*  94 */     Statement stmt = null;
/*  95 */     String sql = "";
/*  96 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/*  99 */       stmt = db.conn.createStatement();
/*     */       
/* 101 */       sql = "SELECT count(1) FROM vw_alarmaproductor ";
/*     */       
/* 103 */       if (filter.size() > 0) {
/* 104 */         String andSql = "";
/* 105 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 106 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 108 */         while (f.hasNext()) {
/*     */           
/* 110 */           filterSql row = f.next();
/* 111 */           if (!row.getValue().equals("")) {
/*     */             
/* 113 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 115 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 116 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 117 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 119 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 121 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 122 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 123 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 126 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like'%" + row.getValue() + "%'";
/* 127 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 133 */       ResultSet rs = stmt.executeQuery(sql);
/* 134 */       while (rs.next()) {
/* 135 */         total = rs.getInt(1);
/*     */       }
/* 137 */       rs.close();
/* 138 */       stmt.close();
/* 139 */       db.conn.close();
/*     */     
/*     */     }
/* 142 */     catch (SQLException e) {
/*     */       
/* 144 */       System.out.println("Error: " + e.getMessage());
/* 145 */       System.out.println("sql: " + sql);
/* 146 */       throw new Exception("getLimitesAll: " + e.getMessage());
/*     */     } finally {
/* 148 */       db.close();
/*     */     } 
/* 150 */     return total;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<alarmaProductor> getAll(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 156 */     ArrayList<alarmaProductor> arr = new ArrayList<>();
/* 157 */     Statement stmt = null;
/* 158 */     String sql = "";
/* 159 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 162 */       stmt = db.conn.createStatement();
/*     */       
/* 164 */       sql = "SELECT * FROM vw_alarmaproductor ";
/*     */       
/* 166 */       if (filter.size() > 0) {
/* 167 */         String andSql = "";
/* 168 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 169 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 171 */         while (f.hasNext()) {
/*     */           
/* 173 */           filterSql row = f.next();
/*     */           
/* 175 */           if (!row.getValue().equals("")) {
/*     */             
/* 177 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 179 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 180 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 181 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 183 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 185 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 186 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 187 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 190 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 191 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 196 */       if (!order.equals("")) {
/* 197 */         sql = String.valueOf(sql) + " order by " + order;
/*     */       }
/*     */       
/* 200 */       if (length > 0) {
/* 201 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/* 203 */       ResultSet rs = stmt.executeQuery(sql);
/* 204 */       while (rs.next()) {
/* 205 */         alarmaProductor o = new alarmaProductor();
/* 206 */         o.setCantidad(rs.getInt("cantidad"));
/*     */         
/* 208 */         o.setCodProductor(rs.getString("codProductor"));
/* 209 */         o.setCodParcela(rs.getString("codParcela"));
/* 210 */         o.setProyecto(rs.getString("proyecto"));
/* 211 */         o.setEspecie(rs.getString("especie"));
/* 212 */         arr.add(o);
/*     */       } 
/* 214 */       rs.close();
/* 215 */       stmt.close();
/* 216 */       db.conn.close();
/*     */     }
/* 218 */     catch (SQLException e) {
/*     */       
/* 220 */       System.out.println("Error: " + e.getMessage());
/* 221 */       System.out.println("sql: " + sql);
/* 222 */       throw new Exception("getLimite: " + e.getMessage());
/*     */     } finally {
/* 224 */       db.close();
/*     */     } 
/*     */     
/* 227 */     return arr;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\alarmaProductorDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */