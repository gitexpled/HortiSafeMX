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
/*     */ import lib.struc.Productor;
/*     */ import lib.struc.Temporada;
/*     */ import lib.struc.filterSql;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProductorDB
/*     */ {
/*     */   public static Productor getProductor(String idProductor) throws Exception {
/*  22 */     Productor o = null;
/*  23 */     ConnectionDB db = new ConnectionDB();
/*  24 */     Statement stmt = null;
/*  25 */     String sql = "";
/*     */     
/*     */     try {
/*  28 */       stmt = db.conn.createStatement();
/*     */       
/*  30 */       sql = "SELECT * FROM productor where codProductor='" + idProductor + "'";
/*     */       
/*  32 */       ResultSet rs = stmt.executeQuery(sql);
/*  33 */       if (rs.next()) {
/*  34 */         o = new Productor();
/*  35 */         o.setCodigo(rs.getString("codProductor"));
/*  36 */         o.setNombre(rs.getString("nombre"));
/*  37 */         o.setCodSap(rs.getString("codSap"));
/*     */         
/*  39 */         o.setCreado(rs.getDate("creado"));
/*  40 */         o.setModificado(rs.getDate("modificado"));
/*  41 */         o.setIdUser(rs.getInt("idUser"));
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/*  47 */         o = null;
/*     */       } 
/*     */       
/*  50 */       rs.close();
/*  51 */       stmt.close();
/*  52 */       db.conn.close();
/*     */     }
/*  54 */     catch (SQLException e) {
/*     */       
/*  56 */       System.out.println("Error: " + e.getMessage());
/*  57 */       System.out.println("sql: " + sql);
/*  58 */       throw new Exception("getUser: " + e.getMessage());
/*     */     } finally {
/*  60 */       db.close();
/*     */     } 
/*     */     
/*  63 */     return o;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateProductor(Productor prod) throws Exception {
/*  68 */     PreparedStatement ps = null;
/*  69 */     String sql = "";
/*  70 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/*  71 */     ConnectionDB db = new ConnectionDB();
/*     */     try {
/*  73 */       db.conn.setAutoCommit(false);
/*     */       
/*  75 */       sql = "update  productor set nombre=?, modificado='" + d + 
/*  76 */         "', idUser=?, codSap=? where codProductor='" + prod.getCodigo() + "'";
/*     */       
/*  78 */       ps = db.conn.prepareStatement(sql);
/*  79 */       ps.setString(1, prod.getNombre());
/*  80 */       ps.setInt(2, prod.getIdUser());
/*  81 */       ps.setString(3, prod.getCodSap());
/*     */ 
/*     */       
/*  84 */       ps.executeUpdate();
/*  85 */       db.conn.commit();
/*  86 */       db.conn.close();
/*     */     }
/*  88 */     catch (SQLException e) {
/*     */       
/*  90 */       System.out.println("Error: " + e.getMessage());
/*  91 */       System.out.println("sql: " + sql);
/*  92 */       throw new Exception("sepPfx: " + e.getMessage());
/*  93 */     } catch (Exception e) {
/*  94 */       e.printStackTrace();
/*  95 */       System.out.println("Error2: " + e.getMessage());
/*  96 */       throw new Exception("sepPfx: " + e.getMessage());
/*     */     } finally {
/*     */       
/*  99 */       db.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getBloqueados(int temporada) throws Exception {
/* 106 */     int total = 0;
/*     */ 
/*     */     
/*     */     try {
/* 110 */       ArrayList<String[]> pp = exportarSapDB.view(temporada);
/*     */       
/* 112 */       for (String[] object : pp) {
/* 113 */         System.out.println(object[1]);
/* 114 */         if (object[1].contains("AX")) {
/* 115 */           total++;
/*     */         }
/*     */       }
/*     */     
/* 119 */     } catch (Exception e) {
/*     */       
/* 121 */       System.out.println("Error: " + e.getMessage());
/*     */       
/* 123 */       throw new Exception("getUsersAll: " + e.getMessage());
/*     */     } 
/*     */     
/* 126 */     return total;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getBloqueados2(int temporada) throws Exception {
/* 132 */     int total = 0;
/* 133 */     Statement stmt = null;
/* 134 */     String sql = "";
/* 135 */     ConnectionDB db = new ConnectionDB();
/* 136 */     Temporada temp = TemporadaDB.getMaxTemprada();
/* 137 */     int idTemp = temp.getIdTemporada();
/*     */     
/*     */     try {
/* 140 */       stmt = db.conn.createStatement();
/*     */ 
/*     */ 
/*     */       
/* 144 */       sql = "select sum(muestrados) from (  SELECT i.* from ( select a.zona as nombre, a.valor cantidad,b.valor muestrados,  FORMAT(IFNULL((b.valor*100)/a.valor,0),1, 'de_DE') as valor from (select p.zona, sum(1)  as valor from productor as p group by zona) as a left join ( select p.zona, sum(1) as valor from  (select max(idRestriciones),idEspecie, codProductor from restriciones where inicial='N' and idEspecie=1 AND idTemporada = 2 group by codProductor,idEspecie) as r inner join productor p on (p.codProductor=r.codProductor) group by p.zona) b on (a.zona=b.zona)) i inner join zona z on (z.nombre=i.nombre)  ) as p";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 159 */       System.out.println(sql);
/* 160 */       ResultSet rs = stmt.executeQuery(sql);
/* 161 */       while (rs.next()) {
/* 162 */         total = rs.getInt(1);
/*     */       }
/* 164 */       rs.close();
/* 165 */       stmt.close();
/* 166 */       db.conn.close();
/*     */     }
/* 168 */     catch (SQLException e) {
/*     */       
/* 170 */       System.out.println("Error: " + e.getMessage());
/* 171 */       System.out.println("sql: " + sql);
/* 172 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/* 174 */       db.close();
/*     */     } 
/*     */     
/* 177 */     return total;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getBloqueadosCereza(int temporada) throws Exception {
/* 182 */     int total = 0;
/* 183 */     Statement stmt = null;
/* 184 */     String sql = "";
/* 185 */     ConnectionDB db = new ConnectionDB();
/* 186 */     Temporada temp = TemporadaDB.getMaxTemprada();
/* 187 */     int idTemp = temp.getIdTemporada();
/*     */     
/*     */     try {
/* 190 */       stmt = db.conn.createStatement();
/*     */ 
/*     */ 
/*     */       
/* 194 */       sql = "select sum(muestrados) from (  SELECT i.* from ( select a.zona as nombre, a.valor cantidad,b.valor muestrados,  FORMAT(IFNULL((b.valor*100)/a.valor,0),1, 'de_DE') as valor from (select p.zona, sum(1)  as valor from productor as p group by zona) as a left join ( select p.zona, sum(1) as valor from  (select max(idRestriciones),idEspecie, codProductor from restriciones where inicial='N' and idEspecie=2 AND idTemporada = 2 group by codProductor,idEspecie) as r inner join productor p on (p.codProductor=r.codProductor) group by p.zona) b on (a.zona=b.zona)) i inner join zona z on (z.nombre=i.nombre)  ) as p";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 209 */       System.out.println(sql);
/* 210 */       ResultSet rs = stmt.executeQuery(sql);
/* 211 */       while (rs.next()) {
/* 212 */         total = rs.getInt(1);
/*     */       }
/* 214 */       rs.close();
/* 215 */       stmt.close();
/* 216 */       db.conn.close();
/*     */     }
/* 218 */     catch (SQLException e) {
/*     */       
/* 220 */       System.out.println("Error: " + e.getMessage());
/* 221 */       System.out.println("sql: " + sql);
/* 222 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/* 224 */       db.close();
/*     */     } 
/*     */     
/* 227 */     return total;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getBloqueadosHoy() throws Exception {
/* 232 */     int total = 0;
/* 233 */     Statement stmt = null;
/* 234 */     String sql = "";
/* 235 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 238 */       stmt = db.conn.createStatement();
/*     */       
/* 240 */       sql = "select sum(p) from (SELECT count(DISTINCT codProductor) as p FROM restriciones where bloqueado='N' and fecha = sysdate() group by codProductor) as s ";
/*     */ 
/*     */       
/* 243 */       ResultSet rs = stmt.executeQuery(sql);
/* 244 */       while (rs.next()) {
/* 245 */         total = rs.getInt(1);
/*     */       }
/* 247 */       rs.close();
/* 248 */       stmt.close();
/* 249 */       db.conn.close();
/*     */     }
/* 251 */     catch (SQLException e) {
/*     */       
/* 253 */       System.out.println("Error: " + e.getMessage());
/* 254 */       System.out.println("sql: " + sql);
/* 255 */       throw new Exception("getUsersAll: " + e.getMessage());
/*     */     } finally {
/* 257 */       db.close();
/*     */     } 
/*     */     
/* 260 */     return total;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getProductoresAll(ArrayList<filterSql> filter) throws Exception {
/* 265 */     int total = 0;
/* 266 */     Statement stmt = null;
/* 267 */     String sql = "";
/* 268 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 271 */       stmt = db.conn.createStatement();
/*     */       
/* 273 */       sql = "SELECT count(1) FROM productor ";
/*     */       
/* 275 */       if (filter.size() > 0) {
/* 276 */         String andSql = "";
/* 277 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 278 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 280 */         while (f.hasNext()) {
/* 281 */           filterSql row = f.next();
/* 282 */           if (!row.getValue().equals("")) {
/* 283 */             if (row.getCampo().endsWith("_to")) {
/* 284 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 285 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 286 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + 
/* 287 */                 sqlDate.format(formatter.parse(row.getValue())) + "'";
/* 288 */             } else if (row.getCampo().endsWith("_from")) {
/* 289 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 290 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 291 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + 
/* 292 */                 sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/* 294 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like'%" + row.getValue() + "%'";
/* 295 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 301 */       ResultSet rs = stmt.executeQuery(sql);
/* 302 */       while (rs.next()) {
/* 303 */         total = rs.getInt(1);
/*     */       }
/* 305 */       rs.close();
/* 306 */       stmt.close();
/* 307 */       db.conn.close();
/*     */     }
/* 309 */     catch (SQLException e) {
/*     */       
/* 311 */       System.out.println("Error: " + e.getMessage());
/* 312 */       System.out.println("sql: " + sql);
/* 313 */       throw new Exception("getUsersAll: " + e.getMessage());
/*     */     } finally {
/* 315 */       db.close();
/*     */     } 
/*     */     
/* 318 */     return total;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ArrayList<Productor> getProductor(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 323 */     ArrayList<Productor> productores = new ArrayList<>();
/* 324 */     Statement stmt = null;
/* 325 */     String sql = "";
/* 326 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 329 */       stmt = db.conn.createStatement();
/*     */       
/* 331 */       sql = "SELECT * FROM productor ";
/*     */       
/* 333 */       if (filter.size() > 0) {
/* 334 */         String andSql = "";
/* 335 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 336 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 338 */         while (f.hasNext()) {
/* 339 */           filterSql row = f.next();
/*     */           
/* 341 */           if (!row.getValue().equals("")) {
/* 342 */             if (row.getCampo().endsWith("_to")) {
/* 343 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 344 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 345 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + 
/* 346 */                 sqlDate.format(formatter.parse(row.getValue())) + "'";
/* 347 */             } else if (row.getCampo().endsWith("_from")) {
/* 348 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 349 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 350 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + 
/* 351 */                 sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/* 353 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 354 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 359 */       if (!order.equals("")) {
/* 360 */         sql = String.valueOf(sql) + " order by " + order;
/*     */       }
/*     */       
/* 363 */       if (length > 0) {
/* 364 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/* 366 */       ResultSet rs = stmt.executeQuery(sql);
/* 367 */       while (rs.next()) {
/* 368 */         Productor row = new Productor();
/*     */         
/* 370 */         row.setCodigo(rs.getString("codProductor"));
/* 371 */         row.setNombre(rs.getString("nombre"));
/* 372 */         row.setCreado(rs.getDate("creado"));
/* 373 */         row.setModificado(rs.getDate("modificado"));
/*     */         
/* 375 */         productores.add(row);
/*     */       } 
/* 377 */       rs.close();
/* 378 */       stmt.close();
/* 379 */       db.conn.close();
/*     */     }
/* 381 */     catch (SQLException e) {
/*     */       
/* 383 */       System.out.println("Error: " + e.getMessage());
/* 384 */       System.out.println("sql: " + sql);
/* 385 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/* 387 */       db.close();
/*     */     } 
/*     */     
/* 390 */     return productores;
/*     */   }
/*     */   
/*     */   public static boolean insertProductor(Productor productor) throws ParseException {
/* 394 */     ConnectionDB db = new ConnectionDB();
/* 395 */     Statement stmt = null;
/* 396 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/* 397 */     boolean resp = true;
/* 398 */     String sql = "";
/*     */     
/*     */     try {
/* 401 */       sql = "INSERT INTO productor(codProductor,nombre,creado,modificado,idUser,codSap) Values ('" + 
/* 402 */         productor.getCodigo() + "','" + productor.getNombre() + "','" + d + "','" + d + "'," + 
/* 403 */         productor.getIdUser() + ",'" + productor.getCodSap() + "')";
/* 404 */       stmt = db.conn.createStatement();
/* 405 */       resp = stmt.execute(sql);
/* 406 */       stmt.close();
/* 407 */       TemporadaDB.setCreateRestriciones();
/*     */     }
/* 409 */     catch (Exception ex) {
/* 410 */       System.out.println(ex.getMessage());
/*     */     } finally {
/* 412 */       db.close();
/*     */     } 
/* 414 */     return resp;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\ProductorDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */