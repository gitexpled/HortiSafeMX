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
/*     */ import lib.struc.filterSql;
/*     */ import lib.struc.mercadoCertificacion;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MercadoCertificacionDB
/*     */ {
/*     */   public static mercadoCertificacion getProdCert(int idCert) throws Exception {
/*  22 */     mercadoCertificacion o = null;
/*  23 */     ConnectionDB db = new ConnectionDB();
/*  24 */     Statement stmt = null;
/*  25 */     String sql = "";
/*     */     
/*     */     try {
/*  28 */       stmt = db.conn.createStatement();
/*     */       
/*  30 */       sql = "SELECT * FROM mercadoCertificado where id='" + idCert + "'";
/*     */       
/*  32 */       ResultSet rs = stmt.executeQuery(sql);
/*  33 */       if (rs.next()) {
/*  34 */         o = new mercadoCertificacion();
/*  35 */         o.setIdMercado(rs.getInt("idMercado"));
/*  36 */         o.setIdCertificacion(rs.getInt("idCertificaciones"));
/*     */       } else {
/*  38 */         o = null;
/*     */       } 
/*     */       
/*  41 */       rs.close();
/*  42 */       stmt.close();
/*  43 */       db.conn.close();
/*     */     }
/*  45 */     catch (SQLException e) {
/*     */       
/*  47 */       System.out.println("Error: " + e.getMessage());
/*  48 */       System.out.println("sql: " + sql);
/*  49 */       throw new Exception("getTipoProducto: " + e.getMessage());
/*     */     } finally {
/*  51 */       db.close();
/*     */     } 
/*     */     
/*  54 */     return o;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int viewAll(ArrayList<filterSql> filter) throws Exception {
/*  59 */     int total = 0;
/*  60 */     Statement stmt = null;
/*  61 */     String sql = "";
/*  62 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/*  65 */       stmt = db.conn.createStatement();
/*     */ 
/*     */       
/*  68 */       sql = "SELECT count(1)  FROM mercadoCertificado p inner join  certificaciones c on (c.idCertificaciones=p.idCertificaciones) inner join  mercado m on (m.idMercado=p.idMercado) ";
/*     */ 
/*     */ 
/*     */       
/*  72 */       if (filter.size() > 0) {
/*  73 */         String andSql = "";
/*  74 */         andSql = String.valueOf(andSql) + " WHERE ";
/*  75 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/*  77 */         while (f.hasNext()) {
/*     */           
/*  79 */           filterSql row = f.next();
/*  80 */           if (!row.getValue().equals("")) {
/*     */             
/*  82 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/*  84 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/*  85 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/*  86 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/*  88 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/*  90 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/*  91 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/*  92 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/*  95 */               sql = String.valueOf(sql) + andSql + " p." + row.getCampo() + " = '" + row.getValue() + "'";
/*  96 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 102 */       ResultSet rs = stmt.executeQuery(sql);
/* 103 */       while (rs.next()) {
/* 104 */         total = rs.getInt(1);
/*     */       }
/* 106 */       rs.close();
/* 107 */       stmt.close();
/* 108 */       db.conn.close();
/*     */     
/*     */     }
/* 111 */     catch (SQLException e) {
/*     */       
/* 113 */       System.out.println("Error: " + e.getMessage());
/* 114 */       System.out.println("sql: " + sql);
/* 115 */       throw new Exception("getTipoProductoAll: " + e.getMessage());
/*     */     } finally {
/* 117 */       db.close();
/*     */     } 
/*     */     
/* 120 */     return total;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<mercadoCertificacion> view(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 126 */     ArrayList<mercadoCertificacion> rows = new ArrayList<>();
/* 127 */     Statement stmt = null;
/* 128 */     String sql = "";
/* 129 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 132 */       stmt = db.conn.createStatement();
/*     */       
/* 134 */       sql = "SELECT p.*,certificacionescol,m.mercado FROM mercadoCertificado p inner join  certificaciones c on (c.idCertificaciones=p.idCertificaciones) inner join  mercado m on (m.idMercado=p.idMercado) ";
/*     */ 
/*     */ 
/*     */       
/* 138 */       if (filter.size() > 0) {
/* 139 */         String andSql = "";
/* 140 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 141 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 143 */         while (f.hasNext()) {
/*     */           
/* 145 */           filterSql row = f.next();
/*     */           
/* 147 */           if (!row.getValue().equals("")) {
/*     */             
/* 149 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 151 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 152 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 153 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 155 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 157 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 158 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 159 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 162 */               sql = String.valueOf(sql) + andSql + " p." + row.getCampo() + " = '" + row.getValue() + "'";
/* 163 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 168 */       if (!order.equals("")) {
/* 169 */         sql = String.valueOf(sql) + " order by ";
/*     */       }
/*     */       
/* 172 */       if (length > 0) {
/* 173 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/* 175 */       System.out.println(sql);
/* 176 */       ResultSet rs = stmt.executeQuery(sql);
/* 177 */       while (rs.next()) {
/* 178 */         mercadoCertificacion o = new mercadoCertificacion();
/* 179 */         o.setId(rs.getInt("id"));
/* 180 */         o.setIdMercado(rs.getInt("idMercado"));
/* 181 */         o.setIdCertificacion(rs.getInt("idCertificaciones"));
/*     */         
/* 183 */         o.setCertificacion(rs.getString("certificacionescol"));
/* 184 */         o.setMercado(rs.getString("mercado"));
/* 185 */         rows.add(o);
/*     */       } 
/* 187 */       rs.close();
/* 188 */       stmt.close();
/* 189 */       db.conn.close();
/*     */     }
/* 191 */     catch (SQLException e) {
/*     */       
/* 193 */       System.out.println("Error: " + e.getMessage());
/* 194 */       System.out.println("sql: " + sql);
/* 195 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/* 197 */       db.close();
/*     */     } 
/*     */     
/* 200 */     return rows;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean insertProductorCertificacion(mercadoCertificacion row) throws ParseException {
/* 205 */     ConnectionDB db = new ConnectionDB();
/* 206 */     Statement stmt = null;
/* 207 */     boolean resp = true;
/* 208 */     String sql = "";
/*     */     
/*     */     try {
/* 211 */       sql = "INSERT INTO mercadoCertificado(idMercado,idCertificaciones) Values ('" + row.getIdMercado() + "','" + row.getIdCertificacion() + "')";
/* 212 */       System.out.println(sql);
/* 213 */       stmt = db.conn.createStatement();
/* 214 */       resp = stmt.execute(sql);
/* 215 */       stmt.close();
/*     */     }
/* 217 */     catch (Exception ex) {
/*     */       
/* 219 */       System.out.println(ex.getMessage());
/*     */     } finally {
/*     */       
/* 222 */       db.close();
/*     */     } 
/* 224 */     return resp;
/*     */   }
/*     */   
/*     */   public static void updateTipoProducto(mercadoCertificacion productorCert) throws Exception {
/* 228 */     PreparedStatement ps = null;
/* 229 */     String sql = "";
/* 230 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/* 231 */     ConnectionDB db = new ConnectionDB();
/*     */     try {
/* 233 */       db.conn.setAutoCommit(false);
/*     */       
/* 235 */       sql = "update  mercadoCertificado set idMercado=?,idCertificaciones=? where id=" + productorCert.getId();
/*     */ 
/*     */       
/* 238 */       ps = db.conn.prepareStatement(sql);
/* 239 */       ps.setInt(1, productorCert.getIdMercado());
/* 240 */       ps.setInt(2, productorCert.getIdCertificacion());
/*     */ 
/*     */ 
/*     */       
/* 244 */       ps.executeUpdate();
/* 245 */       db.conn.commit();
/* 246 */       db.conn.close();
/*     */     }
/* 248 */     catch (SQLException e) {
/*     */       
/* 250 */       System.out.println("Error: " + e.getMessage());
/* 251 */       System.out.println("sql: " + sql);
/* 252 */       throw new Exception("sepPfx: " + e.getMessage());
/* 253 */     } catch (Exception e) {
/* 254 */       e.printStackTrace();
/* 255 */       System.out.println("Error2: " + e.getMessage());
/* 256 */       throw new Exception("sepPfx: " + e.getMessage());
/*     */     } finally {
/*     */       
/* 259 */       db.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static mercadoCertificacion getCertificacionVigente(String idMercado, String certificacion) {
/* 264 */     Statement stmt = null;
/* 265 */     String sql = "";
/* 266 */     mercadoCertificacion pc = null;
/* 267 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 270 */       sql = "SELECT * FROM mercadoCertificado where idMercado=" + idMercado + " and idCertificaciones=" + certificacion + " ";
/* 271 */       stmt = db.conn.createStatement();
/* 272 */       ResultSet rs = stmt.executeQuery(sql);
/* 273 */       if (rs.next()) {
/*     */         
/* 275 */         pc = new mercadoCertificacion();
/* 276 */         pc.setIdMercado(rs.getInt("idMercado"));
/* 277 */         pc.setIdCertificacion(rs.getInt("idCertificaciones"));
/*     */       } 
/*     */ 
/*     */       
/* 281 */       stmt.close();
/* 282 */       rs.close();
/* 283 */     } catch (Exception ex) {
/*     */       
/* 285 */       System.out.println("Error (getCertificacionVigente): " + ex.getMessage());
/*     */     } finally {
/* 287 */       db.close();
/*     */     } 
/* 289 */     return pc;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\MercadoCertificacionDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */