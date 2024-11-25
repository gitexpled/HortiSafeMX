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
/*     */ import lib.struc.cargaManual;
/*     */ import lib.struc.cargaManualDetalle;
/*     */ import lib.struc.filterSql;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class cargaManualDB
/*     */ {
/*     */   public static cargaManual getId(String id) {
/*  22 */     ConnectionDB db = new ConnectionDB();
/*  23 */     cargaManual o = null;
/*  24 */     Statement stmt = null;
/*  25 */     String sql = "";
/*     */     
/*     */     try {
/*  28 */       stmt = db.conn.createStatement();
/*  29 */       sql = "Select * from cargaManual where idCargaManual=" + id;
/*     */       
/*  31 */       System.out.println(sql);
/*  32 */       ResultSet rs = stmt.executeQuery(sql);
/*  33 */       if (rs.next()) {
/*     */         
/*  35 */         o = new cargaManual();
/*  36 */         int idDb = rs.getInt("idCargaManual");
/*  37 */         o.setIdCargaManual(rs.getInt("idCargaManual"));
/*  38 */         o.setLaboratorio(rs.getString("laboratorio"));
/*  39 */         o.setCodProductor(rs.getString("codProductor"));
/*  40 */         o.setIdentificador(rs.getString("identificador"));
/*  41 */         o.setCodParcela(rs.getString("codParcela"));
/*     */ 
/*     */         
/*  44 */         o.setFecha(rs.getString("fecha"));
/*  45 */         o.setIdEspecie(rs.getInt("idEspecie"));
/*  46 */         o.setCodTurno(rs.getString("codTurno"));
/*  47 */         o.setIdVariedad(rs.getInt("idVariedad"));
/*     */ 
/*     */ 
/*     */         
/*  51 */         sql = "Select * from cargaManualDetalle where idCargaManual=" + idDb;
/*  52 */         System.out.println("sql2: " + sql);
/*  53 */         ResultSet rs2 = stmt.executeQuery(sql);
/*  54 */         ArrayList<cargaManualDetalle> ds = new ArrayList<>();
/*  55 */         while (rs2.next()) {
/*     */           
/*  57 */           cargaManualDetalle d = new cargaManualDetalle();
/*  58 */           d.setCodProducto(rs2.getString("codProducto"));
/*  59 */           d.setLimite(rs2.getString("limite"));
/*     */ 
/*     */           
/*  62 */           ds.add(d);
/*     */         } 
/*  64 */         o.setDetalle(ds);
/*  65 */         rs2.close();
/*     */       } 
/*     */       
/*  68 */       rs.close();
/*  69 */       stmt.close();
/*  70 */     } catch (Exception ex) {
/*     */       
/*  72 */       System.out.println("Error: " + ex.getMessage());
/*     */     } finally {
/*  74 */       db.close();
/*     */     } 
/*  76 */     return o;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update(cargaManual o) throws Exception {
/*  81 */     PreparedStatement ps = null;
/*  82 */     String sql = "";
/*  83 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/*  84 */     ConnectionDB db = new ConnectionDB();
/*     */     try {
/*  86 */       db.conn.setAutoCommit(false);
/*     */       
/*  88 */       sql = "update  limites set codProducto=?,idMercado=?,idTipoProducto=?,idFuente=?,limite=?,  modificacion='" + 
/*  89 */         d + "' where idLimites='" + 
/*  90 */         "'";
/*     */       
/*  92 */       ps = db.conn.prepareStatement(sql);
/*  93 */       ps.setString(1, o.getIdentificador());
/*     */ 
/*     */ 
/*     */       
/*  97 */       ps.executeUpdate();
/*  98 */       db.conn.commit();
/*  99 */       db.conn.close();
/*     */     }
/* 101 */     catch (SQLException e) {
/*     */       
/* 103 */       System.out.println("Error: " + e.getMessage());
/* 104 */       System.out.println("sql: " + sql);
/* 105 */       throw new Exception("updateLimite: " + e.getMessage());
/* 106 */     } catch (Exception e) {
/* 107 */       e.printStackTrace();
/* 108 */       System.out.println("Error2: " + e.getMessage());
/* 109 */       throw new Exception("updateLimite: " + e.getMessage());
/*     */     } finally {
/*     */       
/* 112 */       db.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int getAllcount(ArrayList<filterSql> filter) throws Exception {
/* 117 */     int total = 0;
/* 118 */     Statement stmt = null;
/* 119 */     String sql = "";
/* 120 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 123 */       stmt = db.conn.createStatement();
/*     */       
/* 125 */       sql = "SELECT count(1) FROM cargaManual   WHERE normal='Y'  ";
/*     */ 
/*     */       
/* 128 */       String andSql = "";
/*     */ 
/*     */       
/* 131 */       if (filter.size() > 0) {
/*     */         
/* 133 */         Iterator<filterSql> f = filter.iterator();
/* 134 */         andSql = String.valueOf(andSql) + " and ";
/* 135 */         while (f.hasNext()) {
/*     */           
/* 137 */           filterSql row = f.next();
/* 138 */           if (!row.getValue().equals("")) {
/*     */             
/* 140 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 142 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 143 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 144 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 146 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 148 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 149 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 150 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 153 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like'%" + row.getValue() + "%'";
/* 154 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 160 */       ResultSet rs = stmt.executeQuery(sql);
/* 161 */       while (rs.next()) {
/* 162 */         total = rs.getInt(1);
/*     */       }
/* 164 */       rs.close();
/* 165 */       stmt.close();
/* 166 */       db.conn.close();
/*     */     
/*     */     }
/* 169 */     catch (SQLException e) {
/*     */       
/* 171 */       System.out.println("Error: " + e.getMessage());
/* 172 */       System.out.println("sql: " + sql);
/* 173 */       throw new Exception("getLimitesAll: " + e.getMessage());
/*     */     } finally {
/* 175 */       db.close();
/*     */     } 
/* 177 */     return total;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<cargaManual> getAll(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 183 */     ArrayList<cargaManual> arr = new ArrayList<>();
/* 184 */     Statement stmt = null;
/* 185 */     String sql = "";
/* 186 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 189 */       stmt = db.conn.createStatement();
/*     */       
/* 191 */       sql = "SELECT * FROM cargaManual  WHERE normal='Y'  ";
/*     */ 
/*     */       
/* 194 */       String andSql = "  ";
/*     */ 
/*     */       
/* 197 */       if (filter.size() > 0) {
/*     */         
/* 199 */         Iterator<filterSql> f = filter.iterator();
/* 200 */         andSql = String.valueOf(andSql) + "  and ";
/* 201 */         while (f.hasNext()) {
/*     */           
/* 203 */           filterSql row = f.next();
/*     */           
/* 205 */           if (!row.getValue().equals("")) {
/*     */             
/* 207 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 209 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 210 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 211 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 213 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 215 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 216 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 217 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 220 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 221 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 226 */       if (!order.equals("")) {
/* 227 */         sql = String.valueOf(sql) + " order by " + order;
/*     */       } else {
/* 229 */         sql = String.valueOf(sql) + " order by creado desc";
/*     */       } 
/* 231 */       if (length > 0) {
/* 232 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/* 234 */       ResultSet rs = stmt.executeQuery(sql);
/* 235 */       while (rs.next()) {
/* 236 */         cargaManual o = new cargaManual();
/* 237 */         o.setIdCargaManual(rs.getInt("idCargaManual"));
/* 238 */         o.setFecha(rs.getString("fecha"));
/* 239 */         o.setLaboratorio(rs.getString("laboratorio"));
/* 240 */         o.setIdentificador(rs.getString("identificador"));
/* 241 */         o.setCodProductor(rs.getString("codProductor"));
/* 242 */         arr.add(o);
/*     */       } 
/* 244 */       rs.close();
/* 245 */       stmt.close();
/* 246 */       db.conn.close();
/*     */     }
/* 248 */     catch (SQLException e) {
/*     */       
/* 250 */       System.out.println("Error: " + e.getMessage());
/* 251 */       System.out.println("sql: " + sql);
/* 252 */       throw new Exception("getLimite: " + e.getMessage());
/*     */     } finally {
/* 254 */       db.close();
/*     */     } 
/*     */     
/* 257 */     return arr;
/*     */   }
/*     */   public static void setProcesoRest() throws Exception {
/* 260 */     ConnectionDB db = new ConnectionDB();
/* 261 */     Statement stmt = null;
/* 262 */     String sql = "";
/*     */     
/*     */     try {
/* 265 */       stmt = db.conn.createStatement();
/*     */       
/* 267 */       sql = "{call sp_createRestM(1) }";
/*     */ 
/*     */       
/* 270 */       ResultSet rs = stmt.executeQuery(sql);
/*     */ 
/*     */       
/* 273 */       rs.close();
/* 274 */       stmt.close();
/* 275 */       db.conn.close();
/*     */     }
/* 277 */     catch (SQLException e) {
/*     */       
/* 279 */       System.out.println("Error: " + e.getMessage());
/* 280 */       System.out.println("sql: " + sql);
/* 281 */       throw new Exception("sp_createRestM: " + e.getMessage());
/*     */     } finally {
/* 283 */       db.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean insert(cargaManual o) throws ParseException {
/* 290 */     ConnectionDB db = new ConnectionDB();
/* 291 */     PreparedStatement ps = null;
/* 292 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/* 293 */     boolean resp = true;
/* 294 */     String sql = "";
/*     */     
/*     */     try {
/* 297 */       sql = "INSERT INTO cargaManual(`fecha`,`idUsuario`,`creado`,`modificado`,`laboratorio`,`identificador`,codProductor,idEspecie,idTemporada,codParcela,idVariedad,codTurno) Values (?,?,now(),now(),?,?,?,?,?,?,?,?)";
/*     */       
/* 299 */       ps = db.conn.prepareStatement(sql, 1);
/*     */       
/* 301 */       ps.setString(1, o.getFecha());
/* 302 */       ps.setInt(2, o.getIdUsuario());
/* 303 */       ps.setString(3, o.getLaboratorio());
/* 304 */       ps.setString(4, o.getIdentificador());
/* 305 */       ps.setString(5, o.getCodProductor());
/* 306 */       ps.setInt(6, o.getIdEspecie());
/* 307 */       ps.setInt(7, o.getIdTemporada());
/* 308 */       ps.setString(8, o.getCodParcela());
/* 309 */       ps.setInt(9, o.getIdVariedad());
/* 310 */       ps.setString(10, o.getCodTurno());
/*     */       
/* 312 */       System.out.println(ps);
/* 313 */       ps.executeUpdate();
/* 314 */       ResultSet rs = ps.getGeneratedKeys();
/* 315 */       int resultado = 0;
/* 316 */       if (rs.next()) {
/* 317 */         resultado = rs.getInt(1);
/*     */       }
/* 319 */       rs.close();
/* 320 */       String[] vari = o.getVariedades().split(",");
/* 321 */       Iterator<cargaManualDetalle> f = o.getDetalle().iterator();
/* 322 */       System.out.println("resultado: " + resultado);
/* 323 */       if (resultado != 0) {
/* 324 */         while (f.hasNext()) {
/* 325 */           System.out.println("paseee");
/* 326 */           cargaManualDetalle row = f.next();
/*     */ 
/*     */           
/* 329 */           System.out.println("paseeee");
/* 330 */           sql = "INSERT INTO cargaManualDetalle(`idCargaManual`,`codProducto`,`limite`) Values (?,?,?)";
/*     */           
/* 332 */           ps = db.conn.prepareStatement(sql);
/* 333 */           ps.setInt(1, resultado);
/* 334 */           ps.setString(2, row.getCodProducto());
/* 335 */           ps.setString(3, row.getLimite());
/* 336 */           System.out.println(ps);
/* 337 */           ps.executeUpdate();
/*     */         } 
/*     */       }
/*     */       
/* 341 */       for (int i = 0; i < vari.length; i++) {
/* 342 */         sql = "INSERT INTO cargaManualVariedad(`idCargaManual`,`idVariedad`) Values (?,?)";
/*     */         
/* 344 */         ps = db.conn.prepareStatement(sql);
/* 345 */         ps.setInt(1, resultado);
/* 346 */         ps.setString(2, vari[i]);
/* 347 */         System.out.println(ps);
/* 348 */         ps.executeUpdate();
/*     */       } 
/*     */       
/* 351 */       System.out.println("setProcesoRest()");
/* 352 */       setProcesoRest();
/*     */       
/* 354 */       ps.close();
/*     */ 
/*     */     
/*     */     }
/* 358 */     catch (Exception ex) {
/*     */       
/* 360 */       System.out.println(ex.getMessage());
/*     */     } finally {
/*     */       
/* 363 */       db.close();
/*     */     } 
/* 365 */     return resp;
/*     */   }
/*     */   
/*     */   public static boolean UpdateAnalisis(cargaManual map) throws Exception {
/* 369 */     PreparedStatement ps = null;
/* 370 */     PreparedStatement ps2 = null;
/* 371 */     String sql = "";
/*     */ 
/*     */     
/* 374 */     ConnectionDB db = new ConnectionDB();
/*     */     try {
/* 376 */       sql = "UPDATE cargaManual SET fecha = ?,laboratorio = ?, identificador = ?, codProductor = ?, codParcela = ?, idVariedad = ?,modificado = now() WHERE idCargaManual= ?";
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 381 */       ps = db.conn.prepareStatement(sql);
/*     */       
/* 383 */       ps.setString(1, map.getFecha());
/* 384 */       ps.setString(2, map.getLaboratorio());
/* 385 */       ps.setString(3, map.getIdentificador());
/* 386 */       ps.setString(4, map.getCodProductor());
/*     */       
/* 388 */       ps.setString(5, map.getCodParcela());
/* 389 */       ps.setInt(6, map.getIdVariedad());
/*     */       
/* 391 */       ps.setInt(7, map.getIdCargaManual());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 401 */       System.out.println("update: carga manual:" + map.getIdCargaManual() + "-->" + map.getCodParcela());
/*     */       
/* 403 */       return true;
/* 404 */     } catch (Exception ex) {
/* 405 */       System.out.println("Error: " + ex.getMessage());
/*     */     } finally {
/* 407 */       ps.close();
/*     */       
/* 409 */       db.conn.close();
/*     */     } 
/* 411 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\cargaManualDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */