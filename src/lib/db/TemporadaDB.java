/*     */ package lib.db;
/*     */ 
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import lib.struc.Temporada;
/*     */ import lib.struc.filterSql;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TemporadaDB
/*     */ {
/*     */   public static Temporada getMaxTemprada() throws Exception {
/*  22 */     Temporada o = null;
/*  23 */     ConnectionDB db = new ConnectionDB();
/*  24 */     Statement stmt = null;
/*  25 */     String sql = "";
/*     */     
/*     */     try {
/*  28 */       stmt = db.conn.createStatement();
/*     */       
/*  30 */       sql = "SELECT * FROM temporada order by idTemporada desc limit 1";
/*     */ 
/*     */ 
/*     */       
/*  34 */       ResultSet rs = stmt.executeQuery(sql);
/*  35 */       if (rs.next()) {
/*  36 */         o = new Temporada();
/*  37 */         o.setIdTemporada(rs.getInt("idTemporada"));
/*  38 */         o.setTemporada(rs.getString("temporada"));
/*  39 */         o.setCreado(rs.getDate("creacion"));
/*  40 */         o.setIdUser(rs.getInt("idUser"));
/*     */       } else {
/*  42 */         o = null;
/*     */       } 
/*     */       
/*  45 */       rs.close();
/*  46 */       stmt.close();
/*  47 */       db.conn.close();
/*     */     }
/*  49 */     catch (SQLException e) {
/*     */       
/*  51 */       System.out.println("Error: " + e.getMessage());
/*  52 */       System.out.println("sql: " + sql);
/*  53 */       throw new Exception("getTemporadas: " + e.getMessage());
/*     */     } finally {
/*  55 */       db.close();
/*     */     } 
/*     */     
/*  58 */     return o;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Temporada getTemporada(String idTemporada) throws Exception {
/*  63 */     Temporada o = null;
/*  64 */     ConnectionDB db = new ConnectionDB();
/*  65 */     Statement stmt = null;
/*  66 */     String sql = "";
/*     */     
/*     */     try {
/*  69 */       stmt = db.conn.createStatement();
/*     */       
/*  71 */       sql = "SELECT t.*,u.user FROM temporada t left join user u on (u.idUser=t.idUser)  where idTemporada='" + idTemporada + "'";
/*     */       
/*  73 */       ResultSet rs = stmt.executeQuery(sql);
/*  74 */       if (rs.next()) {
/*  75 */         o = new Temporada();
/*  76 */         o.setIdTemporada(rs.getInt("idTemporada"));
/*  77 */         o.setTemporada(rs.getString("temporada"));
/*  78 */         o.setCreado(rs.getDate("creacion"));
/*  79 */         o.setIdUser(rs.getInt("idUser"));
/*  80 */         o.setUsuario(rs.getString("user"));
/*     */       } else {
/*  82 */         o = null;
/*     */       } 
/*     */       
/*  85 */       rs.close();
/*  86 */       stmt.close();
/*  87 */       db.conn.close();
/*     */     }
/*  89 */     catch (SQLException e) {
/*     */       
/*  91 */       System.out.println("Error: " + e.getMessage());
/*  92 */       System.out.println("sql: " + sql);
/*  93 */       throw new Exception("getTemporadas: " + e.getMessage());
/*     */     } finally {
/*  95 */       db.close();
/*     */     } 
/*     */     
/*  98 */     return o;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getTemporadasAll(ArrayList<filterSql> filter) throws Exception {
/* 103 */     int total = 0;
/* 104 */     Statement stmt = null;
/* 105 */     String sql = "";
/* 106 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 109 */       stmt = db.conn.createStatement();
/*     */       
/* 111 */       sql = "SELECT count(1) FROM temporada ";
/*     */       
/* 113 */       if (filter.size() > 0) {
/* 114 */         String andSql = "";
/* 115 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 116 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 118 */         while (f.hasNext()) {
/*     */           
/* 120 */           filterSql row = f.next();
/* 121 */           if (!row.getValue().equals("")) {
/*     */             
/* 123 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 125 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 126 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 127 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 129 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 131 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 132 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 133 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 136 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like'%" + row.getValue() + "%'";
/* 137 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 143 */       ResultSet rs = stmt.executeQuery(sql);
/* 144 */       while (rs.next()) {
/* 145 */         total = rs.getInt(1);
/*     */       }
/* 147 */       rs.close();
/* 148 */       stmt.close();
/* 149 */       db.conn.close();
/*     */     
/*     */     }
/* 152 */     catch (SQLException e) {
/*     */       
/* 154 */       System.out.println("Error: " + e.getMessage());
/* 155 */       System.out.println("sql: " + sql);
/* 156 */       throw new Exception("getTemporadasAll: " + e.getMessage());
/*     */     } finally {
/* 158 */       db.close();
/*     */     } 
/*     */     
/* 161 */     return total;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<Temporada> getTemporada(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 167 */     ArrayList<Temporada> temporadas = new ArrayList<>();
/* 168 */     Statement stmt = null;
/* 169 */     String sql = "";
/* 170 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 173 */       stmt = db.conn.createStatement();
/*     */       
/* 175 */       sql = "SELECT * FROM temporada ";
/*     */       
/* 177 */       if (filter.size() > 0) {
/* 178 */         String andSql = "";
/* 179 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 180 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 182 */         while (f.hasNext()) {
/*     */           
/* 184 */           filterSql row = f.next();
/*     */           
/* 186 */           if (!row.getValue().equals("")) {
/*     */             
/* 188 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 190 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 191 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 192 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 194 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 196 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 197 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 198 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 201 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 202 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 207 */       if (!order.equals("")) {
/* 208 */         sql = String.valueOf(sql) + " order by ";
/*     */       }
/*     */       
/* 211 */       if (length > 0) {
/* 212 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/* 214 */       ResultSet rs = stmt.executeQuery(sql);
/* 215 */       while (rs.next()) {
/* 216 */         Temporada o = new Temporada();
/*     */         
/* 218 */         o.setIdTemporada(rs.getInt("idTemporada"));
/* 219 */         o.setTemporada(rs.getString("temporada"));
/* 220 */         o.setCreado(rs.getDate("creacion"));
/* 221 */         o.setIdUser(rs.getInt("idUser"));
/* 222 */         temporadas.add(o);
/*     */       } 
/* 224 */       rs.close();
/* 225 */       stmt.close();
/* 226 */       db.conn.close();
/*     */     }
/* 228 */     catch (SQLException e) {
/*     */       
/* 230 */       System.out.println("Error: " + e.getMessage());
/* 231 */       System.out.println("sql: " + sql);
/* 232 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/* 234 */       db.close();
/*     */     } 
/*     */     
/* 237 */     return temporadas;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean insertTemporada(Temporada temporada) throws ParseException {
/* 242 */     ConnectionDB db = new ConnectionDB();
/* 243 */     Statement stmt = null;
/* 244 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/* 245 */     boolean resp = true;
/* 246 */     String sql = "";
/*     */     
/*     */     try {
/* 249 */       sql = "INSERT INTO temporada(idUser,temporada,creacion) Values ('" + temporada.getIdUser() + "','" + temporada.getTemporada() + "','" + d + "')";
/* 250 */       stmt = db.conn.createStatement();
/* 251 */       resp = stmt.execute(sql);
/* 252 */       stmt.close();
/* 253 */       setCreateRestriciones();
/*     */     }
/* 255 */     catch (Exception ex) {
/*     */       
/* 257 */       System.out.println(ex.getMessage());
/*     */     } finally {
/*     */       
/* 260 */       db.close();
/*     */     } 
/* 262 */     return resp;
/*     */   }
/*     */   
/*     */   public static void updateTemporada(Temporada temp) throws Exception {
/* 266 */     PreparedStatement ps = null;
/* 267 */     String sql = "";
/* 268 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/* 269 */     ConnectionDB db = new ConnectionDB();
/*     */     try {
/* 271 */       db.conn.setAutoCommit(false);
/*     */       
/* 273 */       sql = "update  temporada set temporada=? where idTemporada='" + temp.getIdTemporada() + 
/* 274 */         "'";
/*     */       
/* 276 */       ps = db.conn.prepareStatement(sql);
/* 277 */       ps.setString(1, temp.getTemporada());
/*     */ 
/*     */       
/* 280 */       ps.executeUpdate();
/* 281 */       db.conn.commit();
/* 282 */       db.conn.close();
/*     */     }
/* 284 */     catch (SQLException e) {
/*     */       
/* 286 */       System.out.println("Error: " + e.getMessage());
/* 287 */       System.out.println("sql: " + sql);
/* 288 */       throw new Exception("sepPfx: " + e.getMessage());
/* 289 */     } catch (Exception e) {
/* 290 */       e.printStackTrace();
/* 291 */       System.out.println("Error2: " + e.getMessage());
/* 292 */       throw new Exception("sepPfx: " + e.getMessage());
/*     */     } finally {
/*     */       
/* 295 */       db.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setCreateRestriciones() throws Exception {
/* 302 */     ConnectionDB db = new ConnectionDB();
/* 303 */     Statement stmt = null;
/* 304 */     String sql = "";
/*     */     
/*     */     try {
/* 307 */       stmt = db.conn.createStatement();
/*     */       
/* 309 */       sql = "{CALL sp_createTemporada()}";
/*     */ 
/*     */       
/* 312 */       ResultSet rs = stmt.executeQuery(sql);
/*     */ 
/*     */       
/* 315 */       rs.close();
/* 316 */       stmt.close();
/* 317 */       db.conn.close();
/*     */     }
/* 319 */     catch (SQLException e) {
/*     */       
/* 321 */       System.out.println("Error: " + e.getMessage());
/* 322 */       System.out.println("sql: " + sql);
/* 323 */       throw new Exception("getEstadoProductor: " + e.getMessage());
/*     */     } finally {
/* 325 */       db.close();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\TemporadaDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */