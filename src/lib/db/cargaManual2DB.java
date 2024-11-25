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
/*     */ public class cargaManual2DB
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
/* 125 */       sql = "SELECT count(1) FROM cargaManual  WHERE normal='N' ";
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
/* 191 */       sql = "SELECT * FROM cargaManual WHERE normal='N' ";
/*     */       
/* 193 */       String andSql = "  ";
/*     */ 
/*     */       
/* 196 */       if (filter.size() > 0) {
/*     */         
/* 198 */         Iterator<filterSql> f = filter.iterator();
/*     */ 
/*     */         
/* 201 */         while (f.hasNext()) {
/*     */           
/* 203 */           andSql = " and ";
/* 204 */           filterSql row = f.next();
/*     */           
/* 206 */           if (!row.getValue().equals("")) {
/*     */             
/* 208 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 210 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 211 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 212 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'"; continue;
/*     */             } 
/* 214 */             if (row.getCampo().endsWith("_from")) {
/*     */               
/* 216 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 217 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 218 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */               continue;
/*     */             } 
/* 221 */             sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 227 */       if (!order.equals("")) {
/* 228 */         sql = String.valueOf(sql) + " order by " + order;
/*     */       } else {
/* 230 */         sql = String.valueOf(sql) + " order by creado desc";
/*     */       } 
/* 232 */       if (length > 0) {
/* 233 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/* 235 */       ResultSet rs = stmt.executeQuery(sql);
/* 236 */       while (rs.next()) {
/* 237 */         cargaManual o = new cargaManual();
/* 238 */         o.setIdCargaManual(rs.getInt("idCargaManual"));
/* 239 */         o.setFecha(rs.getString("fecha"));
/* 240 */         o.setLaboratorio(rs.getString("laboratorio"));
/* 241 */         o.setIdentificador(rs.getString("identificador"));
/* 242 */         o.setCodProductor(rs.getString("codProductor"));
/* 243 */         arr.add(o);
/*     */       } 
/* 245 */       rs.close();
/* 246 */       stmt.close();
/* 247 */       db.conn.close();
/*     */     }
/* 249 */     catch (SQLException e) {
/*     */       
/* 251 */       System.out.println("Error: " + e.getMessage());
/* 252 */       System.out.println("sql: " + sql);
/* 253 */       throw new Exception("getLimite: " + e.getMessage());
/*     */     } finally {
/* 255 */       db.close();
/*     */     } 
/*     */     
/* 258 */     return arr;
/*     */   }
/*     */   public static void setProcesoRest() throws Exception {
/* 261 */     ConnectionDB db = new ConnectionDB();
/* 262 */     Statement stmt = null;
/* 263 */     String sql = "";
/*     */     
/*     */     try {
/* 266 */       stmt = db.conn.createStatement();
/*     */       
/* 268 */       sql = "{call sp_createRestM(1) }";
/*     */ 
/*     */       
/* 271 */       ResultSet rs = stmt.executeQuery(sql);
/*     */ 
/*     */       
/* 274 */       rs.close();
/* 275 */       stmt.close();
/* 276 */       db.conn.close();
/*     */     }
/* 278 */     catch (SQLException e) {
/*     */       
/* 280 */       System.out.println("Error: " + e.getMessage());
/* 281 */       System.out.println("sql: " + sql);
/* 282 */       throw new Exception("sp_createRestM: " + e.getMessage());
/*     */     } finally {
/* 284 */       db.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean insert(cargaManual o) throws ParseException {
/* 291 */     ConnectionDB db = new ConnectionDB();
/* 292 */     PreparedStatement ps = null;
/* 293 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/* 294 */     boolean resp = true;
/* 295 */     String sql = "";
/*     */     
/*     */     try {
/* 298 */       sql = "INSERT INTO cargaManual(`fecha`,`idUsuario`,`creado`,`modificado`,`laboratorio`,`identificador`,codProductor,idEspecie,idTemporada,codParcela,idVariedad,codTurno,normal) Values (?,?,now(),now(),?,?,?,?,?,?,?,?,'N')";
/*     */       
/* 300 */       ps = db.conn.prepareStatement(sql, 1);
/*     */       
/* 302 */       ps.setString(1, o.getFecha());
/* 303 */       ps.setInt(2, o.getIdUsuario());
/* 304 */       ps.setString(3, o.getLaboratorio());
/* 305 */       ps.setString(4, o.getIdentificador());
/* 306 */       ps.setString(5, o.getCodProductor());
/* 307 */       ps.setInt(6, o.getIdEspecie());
/* 308 */       ps.setInt(7, o.getIdTemporada());
/*     */ 
/*     */       
/* 311 */       ps.setString(8, o.getCodParcela());
/* 312 */       ps.setInt(9, o.getIdVariedad());
/* 313 */       ps.setString(10, o.getCodTurno());
/*     */ 
/*     */       
/* 316 */       ps.executeUpdate();
/* 317 */       ResultSet rs = ps.getGeneratedKeys();
/* 318 */       int resultado = 0;
/* 319 */       if (rs.next()) {
/* 320 */         resultado = rs.getInt(1);
/*     */       }
/* 322 */       rs.close();
/*     */       
/* 324 */       Iterator<cargaManualDetalle> f = o.getDetalle().iterator();
/* 325 */       System.out.println("resultado: " + resultado);
/* 326 */       if (resultado != 0) {
/* 327 */         while (f.hasNext()) {
/* 328 */           System.out.println("paseee");
/* 329 */           cargaManualDetalle row = f.next();
/*     */ 
/*     */           
/* 332 */           System.out.println("paseeee");
/* 333 */           sql = "INSERT INTO cargaManualDetalle(`idCargaManual`,`codProducto`,`limite`) Values (?,?,?)";
/*     */           
/* 335 */           ps = db.conn.prepareStatement(sql);
/* 336 */           ps.setInt(1, resultado);
/* 337 */           ps.setString(2, row.getCodProducto());
/* 338 */           ps.setString(3, row.getLimite());
/*     */           
/* 340 */           ps.executeUpdate();
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 345 */       setProcesoRest();
/* 346 */       ps.close();
/*     */     }
/* 348 */     catch (Exception ex) {
/*     */       
/* 350 */       System.out.println(ex.getMessage());
/*     */     } finally {
/*     */       
/* 353 */       db.close();
/*     */     } 
/* 355 */     return resp;
/*     */   }
/*     */   
/*     */   public static boolean UpdateAnalisis(cargaManual map) throws Exception {
/* 359 */     PreparedStatement ps = null;
/* 360 */     PreparedStatement ps2 = null;
/* 361 */     String sql = "";
/*     */ 
/*     */     
/* 364 */     ConnectionDB db = new ConnectionDB();
/*     */     try {
/* 366 */       sql = "UPDATE cargaManual SET fecha = ?,laboratorio = ?, identificador = ?, codProductor = ?, codParcela = ?, idVariedad = ?,modificado = now() WHERE idCargaManual= ?";
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 371 */       ps = db.conn.prepareStatement(sql);
/*     */       
/* 373 */       ps.setString(1, map.getFecha());
/* 374 */       ps.setString(2, map.getLaboratorio());
/* 375 */       ps.setString(3, map.getIdentificador());
/* 376 */       ps.setString(4, map.getCodProductor());
/*     */       
/* 378 */       ps.setString(5, map.getCodParcela());
/* 379 */       ps.setInt(6, map.getIdVariedad());
/*     */       
/* 381 */       ps.setInt(7, map.getIdCargaManual());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 391 */       System.out.println("update: carga manual:" + map.getIdCargaManual() + "-->" + map.getCodParcela());
/*     */       
/* 393 */       return true;
/* 394 */     } catch (Exception ex) {
/* 395 */       System.out.println("Error: " + ex.getMessage());
/*     */     } finally {
/* 397 */       ps.close();
/*     */       
/* 399 */       db.conn.close();
/*     */     } 
/* 401 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\cargaManual2DB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */