/*     */ package lib.db;
/*     */ 
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import lib.struc.Fuente;
/*     */ import lib.struc.filterSql;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FuenteDB
/*     */ {
/*     */   public static Fuente getFuente(String idFuente) throws Exception {
/*  20 */     Fuente o = null;
/*  21 */     ConnectionDB db = new ConnectionDB();
/*  22 */     Statement stmt = null;
/*  23 */     String sql = "";
/*     */     
/*     */     try {
/*  26 */       stmt = db.conn.createStatement();
/*     */       
/*  28 */       sql = "SELECT * FROM fuente where idFuente='" + idFuente + "'";
/*     */       
/*  30 */       ResultSet rs = stmt.executeQuery(sql);
/*  31 */       if (rs.next()) {
/*  32 */         o = new Fuente();
/*  33 */         o.setIdFuente(rs.getInt("idFuente"));
/*  34 */         o.setNombre(rs.getString("nombre"));
/*     */       } else {
/*  36 */         o = null;
/*     */       } 
/*     */       
/*  39 */       rs.close();
/*  40 */       stmt.close();
/*  41 */       db.conn.close();
/*     */     }
/*  43 */     catch (SQLException e) {
/*     */       
/*  45 */       System.out.println("Error: " + e.getMessage());
/*  46 */       System.out.println("sql: " + sql);
/*  47 */       throw new Exception("getFuente: " + e.getMessage());
/*     */     } finally {
/*  49 */       db.close();
/*     */     } 
/*     */     
/*  52 */     return o;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getFuenteAll(ArrayList<filterSql> filter) throws Exception {
/*  57 */     int total = 0;
/*  58 */     Statement stmt = null;
/*  59 */     String sql = "";
/*  60 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/*  63 */       stmt = db.conn.createStatement();
/*     */       
/*  65 */       sql = "SELECT count(1) FROM fuente ";
/*     */       
/*  67 */       if (filter.size() > 0) {
/*  68 */         String andSql = "";
/*  69 */         andSql = String.valueOf(andSql) + " WHERE ";
/*  70 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/*  72 */         while (f.hasNext()) {
/*     */           
/*  74 */           filterSql row = f.next();
/*  75 */           if (!row.getValue().equals("")) {
/*     */             
/*  77 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/*  79 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/*  80 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/*  81 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/*  83 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/*  85 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/*  86 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/*  87 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/*  90 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like'%" + row.getValue() + "%'";
/*  91 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*  97 */       ResultSet rs = stmt.executeQuery(sql);
/*  98 */       while (rs.next()) {
/*  99 */         total = rs.getInt(1);
/*     */       }
/* 101 */       rs.close();
/* 102 */       stmt.close();
/* 103 */       db.conn.close();
/*     */     
/*     */     }
/* 106 */     catch (SQLException e) {
/*     */       
/* 108 */       System.out.println("Error: " + e.getMessage());
/* 109 */       System.out.println("sql: " + sql);
/* 110 */       throw new Exception("getFuenteAll: " + e.getMessage());
/*     */     } finally {
/* 112 */       db.close();
/*     */     } 
/*     */     
/* 115 */     return total;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<Fuente> getFuente(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 121 */     ArrayList<Fuente> tiposProducto = new ArrayList<>();
/* 122 */     Statement stmt = null;
/* 123 */     String sql = "";
/* 124 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 127 */       stmt = db.conn.createStatement();
/*     */       
/* 129 */       sql = "SELECT * FROM fuente ";
/*     */       
/* 131 */       if (filter.size() > 0) {
/* 132 */         String andSql = "";
/* 133 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 134 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 136 */         while (f.hasNext()) {
/*     */           
/* 138 */           filterSql row = f.next();
/*     */           
/* 140 */           if (!row.getValue().equals("")) {
/*     */             
/* 142 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 144 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 145 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 146 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 148 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 150 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 151 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 152 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 155 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 156 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 161 */       if (!order.equals("")) {
/* 162 */         sql = String.valueOf(sql) + " order by ";
/*     */       }
/*     */       
/* 165 */       if (length > 0) {
/* 166 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/* 168 */       ResultSet rs = stmt.executeQuery(sql);
/* 169 */       while (rs.next()) {
/* 170 */         Fuente o = new Fuente();
/*     */         
/* 172 */         o.setIdFuente(rs.getInt("idFuente"));
/* 173 */         o.setNombre(rs.getString("nombre"));
/* 174 */         tiposProducto.add(o);
/*     */       } 
/* 176 */       rs.close();
/* 177 */       stmt.close();
/* 178 */       db.conn.close();
/*     */     }
/* 180 */     catch (SQLException e) {
/*     */       
/* 182 */       System.out.println("Error: " + e.getMessage());
/* 183 */       System.out.println("sql: " + sql);
/* 184 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/* 186 */       db.close();
/*     */     } 
/*     */     
/* 189 */     return tiposProducto;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean insertFuente(Fuente fuente) throws ParseException {
/* 194 */     ConnectionDB db = new ConnectionDB();
/* 195 */     Statement stmt = null;
/* 196 */     boolean resp = true;
/* 197 */     String sql = "";
/*     */     
/*     */     try {
/* 200 */       sql = "INSERT INTO fuente(nombre) Values ('" + fuente.getNombre() + "')";
/* 201 */       stmt = db.conn.createStatement();
/* 202 */       resp = stmt.execute(sql);
/* 203 */       stmt.close();
/*     */     }
/* 205 */     catch (Exception ex) {
/*     */       
/* 207 */       System.out.println(ex.getMessage());
/*     */     } finally {
/*     */       
/* 210 */       db.close();
/*     */     } 
/* 212 */     return resp;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateFuente(Fuente fuente) throws Exception {
/* 217 */     PreparedStatement ps = null;
/* 218 */     String sql = "";
/* 219 */     ConnectionDB db = new ConnectionDB();
/*     */     try {
/* 221 */       db.conn.setAutoCommit(false);
/*     */       
/* 223 */       sql = "update  fuente set nombre=? where idFuente=" + fuente.getIdFuente();
/*     */ 
/*     */       
/* 226 */       ps = db.conn.prepareStatement(sql);
/* 227 */       ps.setString(1, fuente.getNombre());
/*     */ 
/*     */       
/* 230 */       ps.executeUpdate();
/* 231 */       db.conn.commit();
/* 232 */       db.conn.close();
/*     */     }
/* 234 */     catch (SQLException e) {
/*     */       
/* 236 */       System.out.println("Error: " + e.getMessage());
/* 237 */       System.out.println("sql: " + sql);
/* 238 */       throw new Exception("sepPfx: " + e.getMessage());
/* 239 */     } catch (Exception e) {
/* 240 */       e.printStackTrace();
/* 241 */       System.out.println("Error2: " + e.getMessage());
/* 242 */       throw new Exception("sepPfx: " + e.getMessage());
/*     */     } finally {
/*     */       
/* 245 */       db.close();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\FuenteDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */