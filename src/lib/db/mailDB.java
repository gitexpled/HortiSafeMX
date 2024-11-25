/*     */ package lib.db;
/*     */ 
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import lib.struc.filterSql;
/*     */ import lib.struc.mail;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class mailDB
/*     */ {
/*     */   public static mail getMail(String id) throws Exception {
/*  20 */     mail o = null;
/*  21 */     ConnectionDB db = new ConnectionDB();
/*  22 */     Statement stmt = null;
/*  23 */     String sql = "";
/*     */     
/*     */     try {
/*  26 */       stmt = db.conn.createStatement();
/*     */       
/*  28 */       sql = "SELECT * FROM mail where idGetMail='" + id + "'";
/*     */       
/*  30 */       ResultSet rs = stmt.executeQuery(sql);
/*  31 */       if (rs.next()) {
/*  32 */         o = new mail();
/*  33 */         o.setFechaRecibido(rs.getString("fechaRecibido"));
/*  34 */         o.setFechaCarga(rs.getString("fechaCarga"));
/*  35 */         o.setArchivo(rs.getString("nombreArchivo"));
/*  36 */         o.setAsunto(rs.getString("asuntoMail"));
/*  37 */         o.setIdMail(rs.getInt("idMail"));
/*  38 */         o.setFile(null);
/*     */       } else {
/*     */         
/*  41 */         o = null;
/*     */       } 
/*     */       
/*  44 */       rs.close();
/*  45 */       stmt.close();
/*  46 */       db.conn.close();
/*     */     }
/*  48 */     catch (SQLException e) {
/*     */       
/*  50 */       System.out.println("Error: " + e.getMessage());
/*  51 */       System.out.println("sql: " + sql);
/*  52 */       throw new Exception("getUser: " + e.getMessage());
/*     */     } finally {
/*  54 */       db.close();
/*     */     } 
/*     */     
/*  57 */     return o;
/*     */   }
/*     */ 
/*     */   
/*     */   public static mail getMailFile(String id) throws Exception {
/*  62 */     mail o = null;
/*  63 */     ConnectionDB db = new ConnectionDB();
/*  64 */     Statement stmt = null;
/*  65 */     String sql = "";
/*     */     
/*     */     try {
/*  68 */       stmt = db.conn.createStatement();
/*     */       
/*  70 */       sql = "SELECT * FROM mail where idMail='" + id + "'";
/*     */       
/*  72 */       ResultSet rs = stmt.executeQuery(sql);
/*  73 */       if (rs.next()) {
/*  74 */         o = new mail();
/*  75 */         o.setFechaRecibido(rs.getString("fechaRecibido"));
/*  76 */         o.setFechaCarga(rs.getString("fechaCarga"));
/*  77 */         o.setArchivo(rs.getString("nombreArchivo"));
/*  78 */         o.setAsunto(rs.getString("asuntoMail"));
/*  79 */         o.setIdMail(rs.getInt("idMail"));
/*  80 */         o.setFile(rs.getBinaryStream("file"));
/*     */       } else {
/*     */         
/*  83 */         o = null;
/*     */       } 
/*     */       
/*  86 */       rs.close();
/*  87 */       stmt.close();
/*  88 */       db.conn.close();
/*     */     }
/*  90 */     catch (SQLException e) {
/*     */       
/*  92 */       System.out.println("Error: " + e.getMessage());
/*  93 */       System.out.println("sql: " + sql);
/*  94 */       throw new Exception("getUser: " + e.getMessage());
/*     */     } finally {
/*  96 */       db.close();
/*     */     } 
/*     */     
/*  99 */     return o;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static mail getMailMax() throws Exception {
/* 105 */     mail o = null;
/* 106 */     ConnectionDB db = new ConnectionDB();
/* 107 */     Statement stmt = null;
/* 108 */     String sql = "";
/*     */     
/*     */     try {
/* 111 */       stmt = db.conn.createStatement();
/*     */       
/* 113 */       sql = "SELECT * FROM mail order by fechaCarga desc limit 1";
/*     */       
/* 115 */       ResultSet rs = stmt.executeQuery(sql);
/* 116 */       if (rs.next()) {
/* 117 */         o = new mail();
/* 118 */         o.setFechaRecibido(rs.getString("fechaRecibido"));
/* 119 */         o.setFechaCarga(rs.getString("fechaCarga"));
/* 120 */         o.setArchivo(rs.getString("nombreArchivo"));
/* 121 */         o.setAsunto(rs.getString("asuntoMail"));
/* 122 */         o.setIdMail(rs.getInt("idMail"));
/* 123 */         o.setFile(rs.getBinaryStream("file"));
/*     */       } else {
/*     */         
/* 126 */         o = null;
/*     */       } 
/*     */       
/* 129 */       rs.close();
/* 130 */       stmt.close();
/* 131 */       db.conn.close();
/*     */     }
/* 133 */     catch (SQLException e) {
/*     */       
/* 135 */       System.out.println("Error: " + e.getMessage());
/* 136 */       System.out.println("sql: " + sql);
/* 137 */       throw new Exception("getUser: " + e.getMessage());
/*     */     } finally {
/* 139 */       db.close();
/*     */     } 
/*     */     
/* 142 */     return o;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getMailAll(ArrayList<filterSql> filter) throws Exception {
/* 149 */     int total = 0;
/* 150 */     Statement stmt = null;
/* 151 */     String sql = "";
/* 152 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 155 */       stmt = db.conn.createStatement();
/*     */       
/* 157 */       sql = "SELECT count(1) FROM mail ";
/*     */       
/* 159 */       if (filter.size() > 0) {
/* 160 */         String andSql = "";
/* 161 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 162 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 164 */         while (f.hasNext()) {
/*     */           
/* 166 */           filterSql row = f.next();
/* 167 */           if (!row.getValue().equals("")) {
/*     */             
/* 169 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 171 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 172 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 173 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 175 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 177 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 178 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 179 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 182 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like'%" + row.getValue() + "%'";
/* 183 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 189 */       ResultSet rs = stmt.executeQuery(sql);
/* 190 */       while (rs.next()) {
/* 191 */         total = rs.getInt(1);
/*     */       }
/* 193 */       rs.close();
/* 194 */       stmt.close();
/* 195 */       db.conn.close();
/*     */     
/*     */     }
/* 198 */     catch (SQLException e) {
/*     */       
/* 200 */       System.out.println("Error: " + e.getMessage());
/* 201 */       System.out.println("sql: " + sql);
/* 202 */       throw new Exception("getUsersAll: " + e.getMessage());
/*     */     } finally {
/* 204 */       db.close();
/*     */     } 
/*     */     
/* 207 */     return total;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ArrayList<mail> getMail(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 212 */     ArrayList<mail> productores = new ArrayList<>();
/* 213 */     Statement stmt = null;
/* 214 */     String sql = "";
/* 215 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 218 */       stmt = db.conn.createStatement();
/*     */       
/* 220 */       sql = "SELECT * FROM mail ";
/*     */       
/* 222 */       if (filter.size() > 0) {
/* 223 */         String andSql = "";
/* 224 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 225 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 227 */         while (f.hasNext()) {
/*     */           
/* 229 */           filterSql row = f.next();
/*     */           
/* 231 */           if (!row.getValue().equals("")) {
/*     */             
/* 233 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 235 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 236 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 237 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 239 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 241 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 242 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 243 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 246 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 247 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 252 */       if (!order.equals("")) {
/* 253 */         sql = String.valueOf(sql) + " order by ";
/*     */       } else {
/* 255 */         sql = String.valueOf(sql) + " order by fechaRecibido desc";
/*     */       } 
/* 257 */       if (length > 0) {
/* 258 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/* 260 */       ResultSet rs = stmt.executeQuery(sql);
/* 261 */       while (rs.next()) {
/* 262 */         mail row = new mail();
/*     */ 
/*     */         
/* 265 */         row.setIdMail(rs.getInt("idMail"));
/* 266 */         row.setFechaRecibido(rs.getString("fechaRecibido"));
/* 267 */         row.setFechaCarga(rs.getString("fechaCarga"));
/* 268 */         row.setArchivo(rs.getString("nombreArchivo"));
/* 269 */         row.setAsunto(rs.getString("asuntoMail"));
/* 270 */         row.setLaboratorio(rs.getString("laboratorio"));
/* 271 */         String estado = "No Leido";
/* 272 */         if (rs.getString("estado").equals("Y"))
/*     */         {
/* 274 */           estado = "Leido";
/*     */         }
/* 276 */         row.setEstado(estado);
/* 277 */         row.setFile(null);
/* 278 */         productores.add(row);
/*     */       } 
/* 280 */       rs.close();
/* 281 */       stmt.close();
/* 282 */       db.conn.close();
/*     */     }
/* 284 */     catch (SQLException e) {
/*     */       
/* 286 */       System.out.println("Error: " + e.getMessage());
/* 287 */       System.out.println("sql: " + sql);
/* 288 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/* 290 */       db.close();
/*     */     } 
/*     */     
/* 293 */     return productores;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\mailDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */