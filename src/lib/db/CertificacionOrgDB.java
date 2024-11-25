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
/*     */ import lib.struc.CertificacionOrg;
/*     */ import lib.struc.filterSql;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CertificacionOrgDB
/*     */ {
/*     */   public static CertificacionOrg getCertificacion(String idCertificacion) throws Exception {
/*  21 */     CertificacionOrg o = null;
/*  22 */     ConnectionDB db = new ConnectionDB();
/*  23 */     Statement stmt = null;
/*  24 */     String sql = "";
/*     */     
/*     */     try {
/*  27 */       stmt = db.conn.createStatement();
/*     */       
/*  29 */       sql = "SELECT * FROM certificacionesOrg where idCertificaciones='" + idCertificacion + "'";
/*  30 */       System.out.println(sql);
/*  31 */       ResultSet rs = stmt.executeQuery(sql);
/*  32 */       if (rs.next()) {
/*  33 */         o = new CertificacionOrg();
/*  34 */         o.setIdCertificaciones(rs.getInt("idCertificaciones"));
/*  35 */         o.setCertificacionesCol(rs.getString("certificacionescol"));
/*  36 */         o.setCreado(rs.getDate("creacion"));
/*  37 */         o.setModificado(rs.getDate("modificacion"));
/*  38 */         o.setIdUser(rs.getInt("idUser"));
/*  39 */         o.setIdMercado(rs.getInt("idMercado"));
/*  40 */         o.setIdEspecie(rs.getInt("idEspecie"));
/*  41 */         o.setPrefijo(rs.getString("prefijo"));
/*     */       } else {
/*  43 */         o = null;
/*     */       } 
/*     */       
/*  46 */       rs.close();
/*  47 */       stmt.close();
/*  48 */       db.conn.close();
/*     */     }
/*  50 */     catch (SQLException e) {
/*     */       
/*  52 */       System.out.println("Error: " + e.getMessage());
/*  53 */       System.out.println("sql: " + sql);
/*  54 */       throw new Exception("getCertificaciones: " + e.getMessage());
/*     */     } finally {
/*  56 */       db.close();
/*     */     } 
/*     */     
/*  59 */     return o;
/*     */   }
/*     */ 
/*     */   
/*     */   public static CertificacionOrg getCertificacionStr(String idCertificacion) throws Exception {
/*  64 */     CertificacionOrg o = null;
/*  65 */     ConnectionDB db = new ConnectionDB();
/*  66 */     Statement stmt = null;
/*  67 */     String sql = "";
/*     */     
/*     */     try {
/*  70 */       stmt = db.conn.createStatement();
/*     */       
/*  72 */       sql = "SELECT * FROM certificacionesOrg where certificacionescol='" + idCertificacion + "'";
/*  73 */       System.out.println(sql);
/*  74 */       ResultSet rs = stmt.executeQuery(sql);
/*  75 */       if (rs.next()) {
/*  76 */         o = new CertificacionOrg();
/*  77 */         o.setIdCertificaciones(rs.getInt("idCertificaciones"));
/*  78 */         o.setCertificacionesCol(rs.getString("certificacionescol"));
/*  79 */         o.setCreado(rs.getDate("creacion"));
/*  80 */         o.setModificado(rs.getDate("modificacion"));
/*  81 */         o.setIdUser(rs.getInt("idUser"));
/*  82 */         o.setPrefijo(rs.getString("prefijo"));
/*  83 */         o.setIdEspecie(rs.getInt("idEspecie"));
/*     */       } else {
/*  85 */         o = null;
/*     */       } 
/*     */       
/*  88 */       rs.close();
/*  89 */       stmt.close();
/*  90 */       db.conn.close();
/*     */     }
/*  92 */     catch (SQLException e) {
/*     */       
/*  94 */       System.out.println("Error: " + e.getMessage());
/*  95 */       System.out.println("sql: " + sql);
/*  96 */       throw new Exception("getCertificaciones: " + e.getMessage());
/*     */     } finally {
/*  98 */       db.close();
/*     */     } 
/*     */     
/* 101 */     return o;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateCertificacion(CertificacionOrg certificacion) throws Exception {
/* 106 */     PreparedStatement ps = null;
/* 107 */     String sql = "";
/* 108 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/* 109 */     ConnectionDB db = new ConnectionDB();
/*     */     try {
/* 111 */       db.conn.setAutoCommit(false);
/*     */       
/* 113 */       sql = "update  certificacionesOrg set idUser=?,certificacionescol=?,prefijo=?,idMercado=?, idEspecie=?,  modificacion='" + d + "' where idCertificaciones='" + certificacion.getIdCertificaciones() + "'";
/*     */       
/* 115 */       System.out.println(sql);
/* 116 */       ps = db.conn.prepareStatement(sql);
/* 117 */       ps.setInt(1, certificacion.getIdUser());
/* 118 */       ps.setString(2, certificacion.getCertificacionesCol());
/* 119 */       ps.setString(3, certificacion.getPrefijo());
/* 120 */       ps.setInt(4, certificacion.getIdMercado());
/* 121 */       ps.setInt(5, certificacion.getIdEspecie());
/*     */       
/* 123 */       ps.executeUpdate();
/* 124 */       db.conn.commit();
/* 125 */       db.conn.close();
/*     */     }
/* 127 */     catch (SQLException e) {
/*     */       
/* 129 */       System.out.println("Error: " + e.getMessage());
/* 130 */       System.out.println("sql: " + sql);
/* 131 */       throw new Exception("sepCertificacion: " + e.getMessage());
/* 132 */     } catch (Exception e) {
/* 133 */       e.printStackTrace();
/* 134 */       System.out.println("Error2: " + e.getMessage());
/* 135 */       throw new Exception("sepCertificacion: " + e.getMessage());
/*     */     } finally {
/*     */       
/* 138 */       db.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getCertificacionesAll(ArrayList<filterSql> filter) throws Exception {
/* 145 */     int total = 0;
/* 146 */     Statement stmt = null;
/* 147 */     String sql = "";
/* 148 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 151 */       stmt = db.conn.createStatement();
/*     */       
/* 153 */       sql = "SELECT count(1) FROM certificacionesOrg c left join mercado m  on(m.idMercado=c.idMercado) left join especie e  on(e.idEspecie=c.idEspecie)";
/*     */       
/* 155 */       if (filter.size() > 0) {
/* 156 */         String andSql = "";
/* 157 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 158 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 160 */         while (f.hasNext()) {
/* 161 */           filterSql row = f.next();
/* 162 */           if (!row.getValue().equals("")) {
/* 163 */             if (row.getCampo().endsWith("_to")) {
/* 164 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 165 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 166 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/* 167 */             } else if (row.getCampo().endsWith("_from")) {
/* 168 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 169 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 170 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 172 */             else if (row.getCampo().equals("mercado")) {
/* 173 */               sql = String.valueOf(sql) + andSql + " m." + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 174 */             } else if (row.getCampo().equals("especie")) {
/* 175 */               sql = String.valueOf(sql) + andSql + " e." + row.getCampo() + " like '%" + row.getValue() + "%'";
/*     */             } else {
/* 177 */               sql = String.valueOf(sql) + andSql + " c." + row.getCampo() + " like '%" + row.getValue() + "%'";
/*     */             } 
/* 179 */             andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 185 */       ResultSet rs = stmt.executeQuery(sql);
/* 186 */       while (rs.next()) {
/* 187 */         total = rs.getInt(1);
/*     */       }
/* 189 */       rs.close();
/* 190 */       stmt.close();
/* 191 */       db.conn.close();
/*     */     }
/* 193 */     catch (SQLException e) {
/*     */       
/* 195 */       System.out.println("Error: " + e.getMessage());
/* 196 */       System.out.println("sql: " + sql);
/* 197 */       throw new Exception("getCertificacionesAll: " + e.getMessage());
/*     */     } finally {
/* 199 */       db.close();
/*     */     } 
/*     */     
/* 202 */     return total;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ArrayList<CertificacionOrg> getCertificacion(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 207 */     ArrayList<CertificacionOrg> certificaciones = new ArrayList<>();
/* 208 */     Statement stmt = null;
/* 209 */     String sql = "";
/* 210 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 213 */       stmt = db.conn.createStatement();
/*     */       
/* 215 */       sql = "SELECT c.*,m.mercado,e.especie FROM certificacionesOrg c left join mercado m  on(m.idMercado=c.idMercado) left join especie e  on(e.idEspecie=c.idEspecie)";
/*     */       
/* 217 */       if (filter.size() > 0) {
/* 218 */         String andSql = "";
/* 219 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 220 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 222 */         while (f.hasNext()) {
/* 223 */           filterSql row = f.next();
/*     */           
/* 225 */           if (!row.getValue().equals("")) {
/* 226 */             if (row.getCampo().endsWith("_to")) {
/* 227 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 228 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 229 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/* 230 */             } else if (row.getCampo().endsWith("_from")) {
/* 231 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 232 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 233 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 235 */             else if (row.getCampo().equals("mercado")) {
/* 236 */               sql = String.valueOf(sql) + andSql + " m." + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 237 */             } else if (row.getCampo().equals("especie")) {
/* 238 */               sql = String.valueOf(sql) + andSql + " e." + row.getCampo() + " like '%" + row.getValue() + "%'";
/*     */             } else {
/* 240 */               sql = String.valueOf(sql) + andSql + " c." + row.getCampo() + " like '%" + row.getValue() + "%'";
/*     */             } 
/* 242 */             andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 247 */       if (!order.equals("")) {
/* 248 */         sql = String.valueOf(sql) + " order by ";
/*     */       }
/*     */       
/* 251 */       if (length > 0) {
/* 252 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/* 254 */       ResultSet rs = stmt.executeQuery(sql);
/* 255 */       while (rs.next()) {
/* 256 */         CertificacionOrg o = new CertificacionOrg();
/*     */         
/* 258 */         o.setIdCertificaciones(rs.getInt("idCertificaciones"));
/* 259 */         o.setCertificacionesCol(rs.getString("certificacionescol"));
/* 260 */         o.setPrefijo(rs.getString("prefijo"));
/* 261 */         o.setCreado(rs.getDate("creacion"));
/* 262 */         o.setModificado(rs.getDate("modificacion"));
/* 263 */         o.setIdUser(rs.getInt("idUser"));
/* 264 */         o.setIdMercado(rs.getInt("idMercado"));
/* 265 */         o.setMercado(rs.getString("mercado"));
/* 266 */         o.setEspecie(rs.getString("especie"));
/* 267 */         certificaciones.add(o);
/*     */       } 
/* 269 */       rs.close();
/* 270 */       stmt.close();
/* 271 */       db.conn.close();
/*     */     }
/* 273 */     catch (SQLException e) {
/*     */       
/* 275 */       System.out.println("Error: " + e.getMessage());
/* 276 */       System.out.println("sql: " + sql);
/* 277 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/* 279 */       db.close();
/*     */     } 
/*     */     
/* 282 */     return certificaciones;
/*     */   }
/*     */   
/*     */   public static boolean insertCertificacion(CertificacionOrg c) throws ParseException {
/* 286 */     ConnectionDB db = new ConnectionDB();
/* 287 */     Statement stmt = null;
/* 288 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/* 289 */     boolean resp = true;
/* 290 */     String sql = "";
/*     */     try {
/* 292 */       sql = "INSERT INTO certificacionesOrg(idUser,certificacionescol,prefijo,creacion,modificacion,idMercado,idEspecie) Values ('" + c.getIdUser() + "','" + c.getCertificacionesCol() + "','" + c.getPrefijo() + 
/* 293 */         "','" + d + "','" + d + "','" + c.getIdMercado() + "','" + c.getIdEspecie() + "')";
/* 294 */       System.out.println(sql);
/* 295 */       stmt = db.conn.createStatement();
/* 296 */       resp = stmt.execute(sql);
/* 297 */       stmt.close();
/*     */     }
/* 299 */     catch (Exception ex) {
/* 300 */       System.out.println(ex.getMessage());
/*     */     } finally {
/* 302 */       db.close();
/*     */     } 
/* 304 */     return resp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<CertificacionOrg> getEspecie(String idMercado, String order, int start, int length) throws Exception {
/* 315 */     ArrayList<CertificacionOrg> certificaciones = new ArrayList<>();
/* 316 */     Statement stmt = null;
/* 317 */     String sql = "";
/* 318 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 321 */       stmt = db.conn.createStatement();
/*     */       
/* 323 */       sql = "SELECT c.*,m.mercado,e.especie FROM certificacionesOrg c left join mercado m  on(m.idMercado=c.idMercado) left join especie e  on(e.idEspecie=c.idEspecie)";
/*     */       
/* 325 */       sql = String.valueOf(sql) + " where c.idMercado='" + idMercado + "'";
/* 326 */       if (!order.equals("")) {
/* 327 */         sql = String.valueOf(sql) + " order by ";
/*     */       }
/*     */       
/* 330 */       if (length > 0) {
/* 331 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/* 333 */       ResultSet rs = stmt.executeQuery(sql);
/* 334 */       while (rs.next()) {
/* 335 */         CertificacionOrg o = new CertificacionOrg();
/*     */         
/* 337 */         o.setIdCertificaciones(rs.getInt("idCertificaciones"));
/* 338 */         o.setCertificacionesCol(rs.getString("certificacionescol"));
/* 339 */         o.setPrefijo(rs.getString("prefijo"));
/* 340 */         o.setCreado(rs.getDate("creacion"));
/* 341 */         o.setModificado(rs.getDate("modificacion"));
/* 342 */         o.setIdUser(rs.getInt("idUser"));
/* 343 */         o.setIdMercado(rs.getInt("idMercado"));
/* 344 */         o.setMercado(rs.getString("mercado"));
/* 345 */         o.setEspecie(rs.getString("especie"));
/* 346 */         o.setIdEspecie(rs.getInt("idEspecie"));
/* 347 */         certificaciones.add(o);
/*     */       } 
/* 349 */       rs.close();
/* 350 */       stmt.close();
/* 351 */       db.conn.close();
/*     */     }
/* 353 */     catch (SQLException e) {
/*     */       
/* 355 */       System.out.println("Error: " + e.getMessage());
/* 356 */       System.out.println("sql: " + sql);
/* 357 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/* 359 */       db.close();
/*     */     } 
/*     */     
/* 362 */     return certificaciones;
/*     */   }
/*     */   
/*     */   public static ArrayList<CertificacionOrg> getCertificado(String idMercado, String idEspecie, String order, int start, int length) throws Exception {
/* 366 */     ArrayList<CertificacionOrg> certificaciones = new ArrayList<>();
/* 367 */     Statement stmt = null;
/* 368 */     String sql = "";
/* 369 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 372 */       stmt = db.conn.createStatement();
/*     */       
/* 374 */       sql = "SELECT c.*,m.mercado,e.especie FROM certificacionesOrg c left join mercado m  on(m.idMercado=c.idMercado) left join especie e  on(e.idEspecie=c.idEspecie)";
/*     */       
/* 376 */       sql = String.valueOf(sql) + " where c.idMercado='" + idMercado + "' and c.idEspecie='" + idEspecie + "'";
/* 377 */       if (!order.equals("")) {
/* 378 */         sql = String.valueOf(sql) + " order by ";
/*     */       }
/*     */       
/* 381 */       if (length > 0) {
/* 382 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/* 384 */       ResultSet rs = stmt.executeQuery(sql);
/* 385 */       while (rs.next()) {
/* 386 */         CertificacionOrg o = new CertificacionOrg();
/*     */         
/* 388 */         o.setIdCertificaciones(rs.getInt("idCertificaciones"));
/* 389 */         o.setCertificacionesCol(rs.getString("certificacionescol"));
/* 390 */         o.setPrefijo(rs.getString("prefijo"));
/* 391 */         o.setCreado(rs.getDate("creacion"));
/* 392 */         o.setModificado(rs.getDate("modificacion"));
/* 393 */         o.setIdUser(rs.getInt("idUser"));
/* 394 */         o.setIdMercado(rs.getInt("idMercado"));
/* 395 */         o.setMercado(rs.getString("mercado"));
/* 396 */         o.setEspecie(rs.getString("especie"));
/* 397 */         o.setIdEspecie(rs.getInt("idEspecie"));
/* 398 */         certificaciones.add(o);
/*     */       } 
/* 400 */       rs.close();
/* 401 */       stmt.close();
/* 402 */       db.conn.close();
/*     */     }
/* 404 */     catch (SQLException e) {
/*     */       
/* 406 */       System.out.println("Error: " + e.getMessage());
/* 407 */       System.out.println("sql: " + sql);
/* 408 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/* 410 */       db.close();
/*     */     } 
/*     */     
/* 413 */     return certificaciones;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\CertificacionOrgDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */