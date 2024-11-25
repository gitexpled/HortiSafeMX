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
/*     */ import lib.struc.TipoProducto;
/*     */ import lib.struc.filterSql;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TipoProductoDB
/*     */ {
/*     */   public static TipoProducto getTipoProducto(String idTipoProducto) throws Exception {
/*  21 */     TipoProducto o = null;
/*  22 */     ConnectionDB db = new ConnectionDB();
/*  23 */     Statement stmt = null;
/*  24 */     String sql = "";
/*     */     
/*     */     try {
/*  27 */       stmt = db.conn.createStatement();
/*     */       
/*  29 */       sql = "SELECT * FROM tipoProducto where idTipoProducto='" + idTipoProducto + "'";
/*     */       
/*  31 */       ResultSet rs = stmt.executeQuery(sql);
/*  32 */       if (rs.next()) {
/*  33 */         o = new TipoProducto();
/*  34 */         o.setIdTipoProducto(rs.getInt("idTipoProducto"));
/*  35 */         o.setTipoProducto(rs.getString("tipoProducto"));
/*     */       } else {
/*  37 */         o = null;
/*     */       } 
/*     */       
/*  40 */       rs.close();
/*  41 */       stmt.close();
/*  42 */       db.conn.close();
/*     */     }
/*  44 */     catch (SQLException e) {
/*     */       
/*  46 */       System.out.println("Error: " + e.getMessage());
/*  47 */       System.out.println("sql: " + sql);
/*  48 */       throw new Exception("getTipoProducto: " + e.getMessage());
/*     */     } finally {
/*  50 */       db.close();
/*     */     } 
/*     */     
/*  53 */     return o;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getTipoProductoAll(ArrayList<filterSql> filter) throws Exception {
/*  58 */     int total = 0;
/*  59 */     Statement stmt = null;
/*  60 */     String sql = "";
/*  61 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/*  64 */       stmt = db.conn.createStatement();
/*     */       
/*  66 */       sql = "SELECT count(1) FROM tipoProducto ";
/*     */       
/*  68 */       if (filter.size() > 0) {
/*  69 */         String andSql = "";
/*  70 */         andSql = String.valueOf(andSql) + " WHERE ";
/*  71 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/*  73 */         while (f.hasNext()) {
/*     */           
/*  75 */           filterSql row = f.next();
/*  76 */           if (!row.getValue().equals("")) {
/*     */             
/*  78 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/*  80 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/*  81 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/*  82 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/*  84 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/*  86 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/*  87 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/*  88 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/*  91 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like'%" + row.getValue() + "%'";
/*  92 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*  98 */       ResultSet rs = stmt.executeQuery(sql);
/*  99 */       while (rs.next()) {
/* 100 */         total = rs.getInt(1);
/*     */       }
/* 102 */       rs.close();
/* 103 */       stmt.close();
/* 104 */       db.conn.close();
/*     */     
/*     */     }
/* 107 */     catch (SQLException e) {
/*     */       
/* 109 */       System.out.println("Error: " + e.getMessage());
/* 110 */       System.out.println("sql: " + sql);
/* 111 */       throw new Exception("getTipoProductoAll: " + e.getMessage());
/*     */     } finally {
/* 113 */       db.close();
/*     */     } 
/*     */     
/* 116 */     return total;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<TipoProducto> getTipoProducto(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 122 */     ArrayList<TipoProducto> tiposProducto = new ArrayList<>();
/* 123 */     Statement stmt = null;
/* 124 */     String sql = "";
/* 125 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 128 */       stmt = db.conn.createStatement();
/*     */       
/* 130 */       sql = "SELECT * FROM tipoProducto ";
/*     */       
/* 132 */       if (filter.size() > 0) {
/* 133 */         String andSql = "";
/* 134 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 135 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 137 */         while (f.hasNext()) {
/*     */           
/* 139 */           filterSql row = f.next();
/*     */           
/* 141 */           if (!row.getValue().equals("")) {
/*     */             
/* 143 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 145 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 146 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 147 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 149 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 151 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 152 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 153 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 156 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 157 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 162 */       if (!order.equals("")) {
/* 163 */         sql = String.valueOf(sql) + " order by ";
/*     */       }
/*     */       
/* 166 */       if (length > 0) {
/* 167 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/* 169 */       ResultSet rs = stmt.executeQuery(sql);
/* 170 */       while (rs.next()) {
/* 171 */         TipoProducto o = new TipoProducto();
/*     */         
/* 173 */         o.setIdTipoProducto(rs.getInt("idTipoProducto"));
/* 174 */         o.setTipoProducto(rs.getString("tipoProducto"));
/* 175 */         tiposProducto.add(o);
/*     */       } 
/* 177 */       rs.close();
/* 178 */       stmt.close();
/* 179 */       db.conn.close();
/*     */     }
/* 181 */     catch (SQLException e) {
/*     */       
/* 183 */       System.out.println("Error: " + e.getMessage());
/* 184 */       System.out.println("sql: " + sql);
/* 185 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/* 187 */       db.close();
/*     */     } 
/*     */     
/* 190 */     return tiposProducto;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean insertTipoProducto(TipoProducto tipoProducto) throws ParseException {
/* 195 */     ConnectionDB db = new ConnectionDB();
/* 196 */     Statement stmt = null;
/* 197 */     boolean resp = true;
/* 198 */     String sql = "";
/*     */     
/*     */     try {
/* 201 */       sql = "INSERT INTO tipoProducto(tipoProducto) Values ('" + tipoProducto.getTipoProducto() + "')";
/* 202 */       stmt = db.conn.createStatement();
/* 203 */       resp = stmt.execute(sql);
/* 204 */       stmt.close();
/*     */     }
/* 206 */     catch (Exception ex) {
/*     */       
/* 208 */       System.out.println(ex.getMessage());
/*     */     } finally {
/*     */       
/* 211 */       db.close();
/*     */     } 
/* 213 */     return resp;
/*     */   }
/*     */   
/*     */   public static void updateTipoProducto(TipoProducto tipoProducto) throws Exception {
/* 217 */     PreparedStatement ps = null;
/* 218 */     String sql = "";
/* 219 */     ConnectionDB db = new ConnectionDB();
/*     */     try {
/* 221 */       db.conn.setAutoCommit(false);
/*     */       
/* 223 */       sql = "update  tipoProducto set tipoProducto=? where idTipoProducto=" + tipoProducto.getIdTipoProducto();
/*     */ 
/*     */       
/* 226 */       ps = db.conn.prepareStatement(sql);
/* 227 */       ps.setString(1, tipoProducto.getTipoProducto());
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
/*     */ 
/*     */   
/*     */   public static TipoProducto getTipoProductoByName(String name) {
/* 251 */     Statement stmt = null;
/* 252 */     TipoProducto tp = null;
/* 253 */     String sql = "";
/* 254 */     ConnectionDB db = new ConnectionDB();
/*     */     try {
/* 256 */       sql = "select * from tipoProducto where tipoProducto='" + name + "'";
/* 257 */       System.out.println(sql);
/* 258 */       stmt = db.conn.createStatement();
/* 259 */       ResultSet rs = stmt.executeQuery(sql);
/* 260 */       if (rs.next()) {
/*     */         
/* 262 */         tp = new TipoProducto();
/* 263 */         tp.setIdTipoProducto(rs.getInt("idTipoProducto"));
/* 264 */         tp.setTipoProducto(rs.getString("tipoProducto"));
/*     */       } 
/* 266 */       rs.close();
/* 267 */       stmt.close();
/* 268 */     } catch (Exception e) {
/*     */       
/* 270 */       e.printStackTrace();
/* 271 */       System.out.println("Error2: " + e.getMessage());
/*     */     } finally {
/* 273 */       db.close();
/*     */     } 
/* 275 */     return tp;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\TipoProductoDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */