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
/*     */ import lib.struc.Certificacion;
/*     */ import lib.struc.filterSql;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CertificacionDB
/*     */ {
/*     */   public static Certificacion getCertificacion(String idCertificacion) throws Exception {
/*  20 */     Certificacion o = null;
/*  21 */     ConnectionDB db = new ConnectionDB();
/*  22 */     Statement stmt = null;
/*  23 */     String sql = "";
/*     */     
/*     */     try {
/*  26 */       stmt = db.conn.createStatement();
/*     */       
/*  28 */       sql = "SELECT * FROM certificaciones where idCertificaciones='" + idCertificacion + "'";
/*  29 */       System.out.println(sql);
/*  30 */       ResultSet rs = stmt.executeQuery(sql);
/*  31 */       if (rs.next()) {
/*  32 */         o = new Certificacion();
/*  33 */         o.setIdCertificaciones(rs.getInt("idCertificaciones"));
/*  34 */         o.setCertificacionesCol(rs.getString("certificacionescol"));
/*  35 */         o.setCreado(rs.getDate("creacion"));
/*  36 */         o.setModificado(rs.getDate("modificacion"));
/*  37 */         o.setIdUser(rs.getInt("idUser"));
/*  38 */         o.setPrefijo(rs.getString("prefijo"));
/*     */       } else {
/*  40 */         o = null;
/*     */       } 
/*     */       
/*  43 */       rs.close();
/*  44 */       stmt.close();
/*  45 */       db.conn.close();
/*     */     }
/*  47 */     catch (SQLException e) {
/*     */       
/*  49 */       System.out.println("Error: " + e.getMessage());
/*  50 */       System.out.println("sql: " + sql);
/*  51 */       throw new Exception("getCertificaciones: " + e.getMessage());
/*     */     } finally {
/*  53 */       db.close();
/*     */     } 
/*     */     
/*  56 */     return o;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Certificacion getCertificacionStr(String idCertificacion) throws Exception {
/*  61 */     Certificacion o = null;
/*  62 */     ConnectionDB db = new ConnectionDB();
/*  63 */     Statement stmt = null;
/*  64 */     String sql = "";
/*     */     
/*     */     try {
/*  67 */       stmt = db.conn.createStatement();
/*     */       
/*  69 */       sql = "SELECT * FROM certificaciones where certificacionescol='" + idCertificacion + "'";
/*  70 */       System.out.println(sql);
/*  71 */       ResultSet rs = stmt.executeQuery(sql);
/*  72 */       if (rs.next()) {
/*  73 */         o = new Certificacion();
/*  74 */         o.setIdCertificaciones(rs.getInt("idCertificaciones"));
/*  75 */         o.setCertificacionesCol(rs.getString("certificacionescol"));
/*  76 */         o.setCreado(rs.getDate("creacion"));
/*  77 */         o.setModificado(rs.getDate("modificacion"));
/*  78 */         o.setIdUser(rs.getInt("idUser"));
/*  79 */         o.setPrefijo(rs.getString("prefijo"));
/*     */       } else {
/*  81 */         o = null;
/*     */       } 
/*     */       
/*  84 */       rs.close();
/*  85 */       stmt.close();
/*  86 */       db.conn.close();
/*     */     }
/*  88 */     catch (SQLException e) {
/*     */       
/*  90 */       System.out.println("Error: " + e.getMessage());
/*  91 */       System.out.println("sql: " + sql);
/*  92 */       throw new Exception("getCertificaciones: " + e.getMessage());
/*     */     } finally {
/*  94 */       db.close();
/*     */     } 
/*     */     
/*  97 */     return o;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateCertificacion(Certificacion certificacion) throws Exception {
/* 102 */     PreparedStatement ps = null;
/* 103 */     String sql = "";
/* 104 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/* 105 */     ConnectionDB db = new ConnectionDB();
/*     */     try {
/* 107 */       db.conn.setAutoCommit(false);
/*     */       
/* 109 */       sql = "update  certificaciones set idUser=?,certificacionescol=?,prefijo=?,  modificacion='" + d + "' where idCertificaciones='" + certificacion.getIdCertificaciones() + 
/* 110 */         "'";
/*     */       
/* 112 */       System.out.println(sql);
/* 113 */       ps = db.conn.prepareStatement(sql);
/* 114 */       ps.setInt(1, certificacion.getIdUser());
/* 115 */       ps.setString(2, certificacion.getCertificacionesCol());
/* 116 */       ps.setString(3, certificacion.getPrefijo());
/*     */       
/* 118 */       ps.executeUpdate();
/* 119 */       db.conn.commit();
/* 120 */       db.conn.close();
/*     */     }
/* 122 */     catch (SQLException e) {
/*     */       
/* 124 */       System.out.println("Error: " + e.getMessage());
/* 125 */       System.out.println("sql: " + sql);
/* 126 */       throw new Exception("sepCertificacion: " + e.getMessage());
/* 127 */     } catch (Exception e) {
/* 128 */       e.printStackTrace();
/* 129 */       System.out.println("Error2: " + e.getMessage());
/* 130 */       throw new Exception("sepCertificacion: " + e.getMessage());
/*     */     } finally {
/*     */       
/* 133 */       db.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getCertificacionesAll(ArrayList<filterSql> filter) throws Exception {
/* 140 */     int total = 0;
/* 141 */     Statement stmt = null;
/* 142 */     String sql = "";
/* 143 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 146 */       stmt = db.conn.createStatement();
/*     */       
/* 148 */       sql = "SELECT count(1) FROM certificaciones ";
/*     */       
/* 150 */       if (filter.size() > 0) {
/* 151 */         String andSql = "";
/* 152 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 153 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 155 */         while (f.hasNext()) {
/*     */           
/* 157 */           filterSql row = f.next();
/* 158 */           if (!row.getValue().equals("")) {
/*     */             
/* 160 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 162 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 163 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 164 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 166 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 168 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 169 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 170 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 173 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like'%" + row.getValue() + "%'";
/* 174 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 180 */       ResultSet rs = stmt.executeQuery(sql);
/* 181 */       while (rs.next()) {
/* 182 */         total = rs.getInt(1);
/*     */       }
/* 184 */       rs.close();
/* 185 */       stmt.close();
/* 186 */       db.conn.close();
/*     */     
/*     */     }
/* 189 */     catch (SQLException e) {
/*     */       
/* 191 */       System.out.println("Error: " + e.getMessage());
/* 192 */       System.out.println("sql: " + sql);
/* 193 */       throw new Exception("getCertificacionesAll: " + e.getMessage());
/*     */     } finally {
/* 195 */       db.close();
/*     */     } 
/*     */     
/* 198 */     return total;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<Certificacion> getCertificacion(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 204 */     ArrayList<Certificacion> certificaciones = new ArrayList<>();
/* 205 */     Statement stmt = null;
/* 206 */     String sql = "";
/* 207 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 210 */       stmt = db.conn.createStatement();
/*     */       
/* 212 */       sql = "SELECT * FROM certificaciones ";
/*     */       
/* 214 */       if (filter.size() > 0) {
/* 215 */         String andSql = "";
/* 216 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 217 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 219 */         while (f.hasNext()) {
/*     */           
/* 221 */           filterSql row = f.next();
/*     */           
/* 223 */           if (!row.getValue().equals("")) {
/*     */             
/* 225 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 227 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 228 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 229 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 231 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 233 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 234 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 235 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 238 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 239 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 244 */       if (!order.equals("")) {
/* 245 */         sql = String.valueOf(sql) + " order by ";
/*     */       }
/*     */       
/* 248 */       if (length > 0) {
/* 249 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/* 251 */       ResultSet rs = stmt.executeQuery(sql);
/* 252 */       while (rs.next()) {
/* 253 */         Certificacion o = new Certificacion();
/*     */         
/* 255 */         o.setIdCertificaciones(rs.getInt("idCertificaciones"));
/* 256 */         o.setCertificacionesCol(rs.getString("certificacionescol"));
/* 257 */         o.setPrefijo(rs.getString("prefijo"));
/* 258 */         o.setCreado(rs.getDate("creacion"));
/* 259 */         o.setModificado(rs.getDate("modificacion"));
/* 260 */         o.setIdUser(rs.getInt("idUser"));
/* 261 */         certificaciones.add(o);
/*     */       } 
/* 263 */       rs.close();
/* 264 */       stmt.close();
/* 265 */       db.conn.close();
/*     */     }
/* 267 */     catch (SQLException e) {
/*     */       
/* 269 */       System.out.println("Error: " + e.getMessage());
/* 270 */       System.out.println("sql: " + sql);
/* 271 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/* 273 */       db.close();
/*     */     } 
/*     */     
/* 276 */     return certificaciones;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean insertCertificacion(Certificacion certificacion) throws ParseException {
/* 281 */     ConnectionDB db = new ConnectionDB();
/* 282 */     Statement stmt = null;
/* 283 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/* 284 */     boolean resp = true;
/* 285 */     String sql = "";
/*     */     
/*     */     try {
/* 288 */       sql = "INSERT INTO certificaciones(idUser,certificacionescol,prefijo,creacion,modificacion) Values ('" + certificacion.getIdUser() + "','" + certificacion.getCertificacionesCol() + "','" + certificacion.getPrefijo() + "','" + d + "','" + d + "')";
/* 289 */       System.out.println(sql);
/* 290 */       stmt = db.conn.createStatement();
/* 291 */       resp = stmt.execute(sql);
/* 292 */       stmt.close();
/*     */     }
/* 294 */     catch (Exception ex) {
/*     */       
/* 296 */       System.out.println(ex.getMessage());
/*     */     } finally {
/*     */       
/* 299 */       db.close();
/*     */     } 
/* 301 */     return resp;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\CertificacionDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */