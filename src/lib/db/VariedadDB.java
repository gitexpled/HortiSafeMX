/*     */ package lib.db;
/*     */ 
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import lib.struc.ProductorCertificacion;
/*     */ import lib.struc.ProductorVariedad;
/*     */ import lib.struc.Variedad;
/*     */ import lib.struc.filterSql;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VariedadDB
/*     */ {
/*     */   public static ArrayList<Variedad> getAll(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/*  21 */     ArrayList<Variedad> Variedades = new ArrayList<>();
/*  22 */     Statement stmt = null;
/*  23 */     String sql = "";
/*  24 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/*  27 */       stmt = db.conn.createStatement();
/*     */       
/*  29 */       sql = "SELECT * FROM variedad ";
/*     */       
/*  31 */       if (filter.size() > 0) {
/*  32 */         String andSql = "";
/*  33 */         andSql = String.valueOf(andSql) + " WHERE ";
/*  34 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/*  36 */         while (f.hasNext()) {
/*  37 */           filterSql row = f.next();
/*     */           
/*  39 */           if (!row.getValue().equals("")) {
/*  40 */             if (row.getCampo().endsWith("_to")) {
/*  41 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/*  42 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/*  43 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + 
/*  44 */                 sqlDate.format(formatter.parse(row.getValue())) + "'";
/*  45 */             } else if (row.getCampo().endsWith("_from")) {
/*  46 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/*  47 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/*  48 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + 
/*  49 */                 sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*  51 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/*  52 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  57 */       if (!order.equals("")) {
/*  58 */         sql = String.valueOf(sql) + " order by ";
/*     */       }
/*     */       
/*  61 */       if (length > 0) {
/*  62 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/*  64 */       ResultSet rs = stmt.executeQuery(sql);
/*  65 */       while (rs.next()) {
/*  66 */         Variedad row = new Variedad();
/*     */         
/*  68 */         row.setCod(rs.getString("cod"));
/*  69 */         row.setNombre(rs.getString("nombre"));
/*  70 */         row.setIdVariedad(rs.getInt("idVariedad"));
/*     */ 
/*     */         
/*  73 */         Variedades.add(row);
/*     */       } 
/*  75 */       rs.close();
/*  76 */       stmt.close();
/*  77 */       db.conn.close();
/*     */     }
/*  79 */     catch (SQLException e) {
/*     */       
/*  81 */       System.out.println("Error: " + e.getMessage());
/*  82 */       System.out.println("sql: " + sql);
/*  83 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/*  85 */       db.close();
/*     */     } 
/*     */     
/*  88 */     return Variedades;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ArrayList<Variedad> getVariedadM(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/*  93 */     ArrayList<Variedad> VariedadArray = new ArrayList<>();
/*  94 */     Statement stmt = null;
/*  95 */     String sql = "";
/*  96 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/*  99 */       stmt = db.conn.createStatement();
/*     */       
/* 101 */       sql = "select v.*, e.especie from variedad v left join especie e on(e.idEspecie = v.idEspecie) where 1 = 1 ";
/*     */       
/* 103 */       if (filter.size() > 0) {
/* 104 */         String andSql = "";
/* 105 */         andSql = String.valueOf(andSql) + " and ";
/* 106 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 108 */         while (f.hasNext()) {
/*     */           
/* 110 */           filterSql row = f.next();
/*     */           
/* 112 */           if (!row.getValue().equals("")) {
/*     */             
/* 114 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 116 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 117 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 118 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 120 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 122 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 123 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 124 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 127 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 128 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 133 */       if (!order.equals("")) {
/* 134 */         sql = String.valueOf(sql) + " order by ";
/*     */       }
/*     */       
/* 137 */       if (length > 0) {
/* 138 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/* 140 */       System.out.println("query: " + sql);
/* 141 */       ResultSet rs = stmt.executeQuery(sql);
/* 142 */       while (rs.next()) {
/* 143 */         Variedad e = new Variedad();
/*     */         
/* 145 */         e.setIdVariedad(rs.getInt("idVariedad"));
/* 146 */         e.setIdEspecie(rs.getInt("idEspecie"));
/* 147 */         e.setCod(rs.getString("cod"));
/* 148 */         e.setNombre(rs.getString("nombre"));
/* 149 */         e.setEspecie(rs.getString("e.especie"));
/*     */         
/* 151 */         VariedadArray.add(e);
/*     */       } 
/* 153 */       rs.close();
/* 154 */       stmt.close();
/* 155 */       db.conn.close();
/*     */     }
/* 157 */     catch (SQLException e) {
/*     */       
/* 159 */       System.out.println("Error: " + e.getMessage());
/* 160 */       System.out.println("sql: " + sql);
/* 161 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/* 163 */       db.close();
/*     */     } 
/*     */     
/* 166 */     return VariedadArray;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getVariedadAll(ArrayList<filterSql> filter) throws Exception {
/* 172 */     int total = 0;
/* 173 */     Statement stmt = null;
/* 174 */     String sql = "";
/* 175 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 178 */       stmt = db.conn.createStatement();
/*     */       
/* 180 */       sql = "SELECT count(1) FROM variedad ";
/*     */       
/* 182 */       if (filter.size() > 0) {
/* 183 */         String andSql = "";
/* 184 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 185 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 187 */         while (f.hasNext()) {
/*     */           
/* 189 */           filterSql row = f.next();
/* 190 */           if (!row.getValue().equals("")) {
/*     */             
/* 192 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 194 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 195 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 196 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 198 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 200 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 201 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 202 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 205 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like'%" + row.getValue() + "%'";
/* 206 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 212 */       ResultSet rs = stmt.executeQuery(sql);
/* 213 */       while (rs.next()) {
/* 214 */         total = rs.getInt(1);
/*     */       }
/* 216 */       rs.close();
/* 217 */       stmt.close();
/* 218 */       db.conn.close();
/*     */     
/*     */     }
/* 221 */     catch (SQLException e) {
/*     */       
/* 223 */       System.out.println("Error: " + e.getMessage());
/* 224 */       System.out.println("sql: " + sql);
/* 225 */       throw new Exception("getCertificadosAll: " + e.getMessage());
/*     */     } finally {
/* 227 */       db.close();
/*     */     } 
/*     */     
/* 230 */     return total;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<ProductorVariedad> getProductorVariedadM(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 236 */     ArrayList<ProductorVariedad> VariedadArray = new ArrayList<>();
/* 237 */     Statement stmt = null;
/* 238 */     String sql = "";
/* 239 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 242 */       stmt = db.conn.createStatement();
/*     */       
/* 244 */       sql = "select  p.codProductor, p.nombre,e.especie,v.idEspecie, group_concat(v.nombre SEPARATOR ' - ' ) as variedades  from VariedadProductor vp left join productor p on(vp.idProductor = p.codProductor)  left join variedad v on(v.idVariedad = vp.idVariedad) LEFT JOIN especie e ON (e.idEspecie = v.idEspecie)  WHERE 1 = 1 ";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 250 */       if (filter.size() > 0) {
/* 251 */         String andSql = "";
/* 252 */         andSql = String.valueOf(andSql) + " and ";
/* 253 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 255 */         while (f.hasNext()) {
/*     */           
/* 257 */           filterSql row = f.next();
/*     */           
/* 259 */           if (!row.getValue().equals("")) {
/*     */             
/* 261 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 263 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 264 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 265 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 267 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 269 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 270 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 271 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 274 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 275 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 280 */       sql = String.valueOf(sql) + "group by 1, 2,3";
/* 281 */       if (!order.equals("")) {
/* 282 */         sql = String.valueOf(sql) + " order by ";
/*     */       }
/*     */       
/* 285 */       if (length > 0) {
/* 286 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/* 288 */       System.out.println("query: " + sql);
/* 289 */       ResultSet rs = stmt.executeQuery(sql);
/* 290 */       while (rs.next()) {
/* 291 */         ProductorVariedad e = new ProductorVariedad();
/*     */         
/* 293 */         e.setIdProductor(rs.getInt("codProductor"));
/* 294 */         e.setProductor(rs.getString("nombre"));
/* 295 */         e.setVariedades(rs.getString("variedades"));
/* 296 */         e.setIdEspecie(rs.getInt("idEspecie"));
/* 297 */         e.setEspecie(rs.getString("especie"));
/*     */         
/* 299 */         VariedadArray.add(e);
/*     */       } 
/* 301 */       rs.close();
/* 302 */       stmt.close();
/* 303 */       db.conn.close();
/*     */     }
/* 305 */     catch (SQLException e) {
/*     */       
/* 307 */       System.out.println("Error: " + e.getMessage());
/* 308 */       System.out.println("sql: " + sql);
/* 309 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/* 311 */       db.close();
/*     */     } 
/*     */     
/* 314 */     return VariedadArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getVariedadProductorAll(ArrayList<filterSql> filter) throws Exception {
/* 321 */     ArrayList<ProductorVariedad> VariedadArray = new ArrayList<>();
/* 322 */     int total = 0;
/* 323 */     Statement stmt = null;
/* 324 */     String sql = "";
/* 325 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 328 */       stmt = db.conn.createStatement();
/*     */       
/* 330 */       sql = "select  count(distinct idProductor) total  from VariedadProductor vp left join productor p on(vp.idProductor = p.codProductor)  left join variedad v on(v.idVariedad = vp.idVariedad) LEFT JOIN especie e ON (e.idEspecie = v.idEspecie)  WHERE 1 = 1 ";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 336 */       if (filter.size() > 0) {
/* 337 */         String andSql = "";
/* 338 */         andSql = String.valueOf(andSql) + " and ";
/* 339 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 341 */         while (f.hasNext()) {
/*     */           
/* 343 */           filterSql row = f.next();
/*     */           
/* 345 */           if (!row.getValue().equals("")) {
/*     */             
/* 347 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 349 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 350 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 351 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 353 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 355 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 356 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 357 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 360 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 361 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 367 */       System.out.println("query: " + sql);
/* 368 */       ResultSet rs = stmt.executeQuery(sql);
/* 369 */       while (rs.next()) {
/* 370 */         ProductorVariedad e = new ProductorVariedad();
/*     */         
/* 372 */         total = rs.getInt("total");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 379 */         VariedadArray.add(e);
/*     */       } 
/* 381 */       rs.close();
/* 382 */       stmt.close();
/* 383 */       db.conn.close();
/*     */     }
/* 385 */     catch (SQLException e) {
/*     */       
/* 387 */       System.out.println("Error: " + e.getMessage());
/* 388 */       System.out.println("sql: " + sql);
/* 389 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/* 391 */       db.close();
/*     */     } 
/*     */     
/* 394 */     return total;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getByCod(String id) throws Exception {
/* 399 */     String idVariedad = "";
/* 400 */     ConnectionDB db = new ConnectionDB();
/* 401 */     Statement stmt = null;
/* 402 */     String sql = "";
/*     */     
/*     */     try {
/* 405 */       stmt = db.conn.createStatement();
/*     */       
/* 407 */       sql = "SELECT * FROM variedad where cod='" + id + "'";
/* 408 */       System.out.println("sql: " + sql);
/*     */       
/* 410 */       ResultSet rs = stmt.executeQuery(sql);
/* 411 */       if (rs.next()) {
/* 412 */         idVariedad = rs.getString("idVariedad");
/*     */       } else {
/*     */         
/* 415 */         idVariedad = null;
/*     */       } 
/*     */       
/* 418 */       rs.close();
/* 419 */       stmt.close();
/* 420 */       db.conn.close();
/*     */     }
/* 422 */     catch (SQLException e) {
/*     */       
/* 424 */       System.out.println("Error: " + e.getMessage());
/* 425 */       System.out.println("sql: " + sql);
/* 426 */       throw new Exception("getUser: " + e.getMessage());
/*     */     } finally {
/* 428 */       db.close();
/*     */     } 
/*     */     
/* 431 */     return idVariedad;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getById(String id) throws Exception {
/* 436 */     String idVariedad = "";
/* 437 */     ConnectionDB db = new ConnectionDB();
/* 438 */     Statement stmt = null;
/* 439 */     String sql = "";
/*     */     
/*     */     try {
/* 442 */       stmt = db.conn.createStatement();
/*     */       
/* 444 */       sql = "SELECT * FROM variedad where idVariedad='" + id + "'";
/* 445 */       System.out.println("sql: " + sql);
/*     */       
/* 447 */       ResultSet rs = stmt.executeQuery(sql);
/* 448 */       if (rs.next()) {
/* 449 */         idVariedad = rs.getString("cod");
/*     */       } else {
/*     */         
/* 452 */         idVariedad = null;
/*     */       } 
/*     */       
/* 455 */       rs.close();
/* 456 */       stmt.close();
/* 457 */       db.conn.close();
/*     */     }
/* 459 */     catch (SQLException e) {
/*     */       
/* 461 */       System.out.println("Error: " + e.getMessage());
/* 462 */       System.out.println("sql: " + sql);
/* 463 */       throw new Exception("getUser: " + e.getMessage());
/*     */     } finally {
/* 465 */       db.close();
/*     */     } 
/*     */     
/* 468 */     return idVariedad;
/*     */   }
/*     */   
/*     */   public static String getNVariedad(String codVariedad) {
/* 472 */     String nVariedad = "";
/* 473 */     Statement stmt = null;
/* 474 */     String sql = "";
/* 475 */     ProductorCertificacion pc = null;
/* 476 */     ConnectionDB db = new ConnectionDB();
/* 477 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/*     */     try {
/* 479 */       sql = "SELECT nombre FROM variedad where cod='" + codVariedad + "'";
/* 480 */       System.out.println(sql);
/* 481 */       stmt = db.conn.createStatement();
/* 482 */       ResultSet rs = stmt.executeQuery(sql);
/* 483 */       if (rs.next())
/*     */       {
/* 485 */         nVariedad = rs.getString("nombre");
/*     */       }
/*     */ 
/*     */       
/* 489 */       stmt.close();
/* 490 */       rs.close();
/* 491 */     } catch (Exception ex) {
/*     */       
/* 493 */       System.out.println("Error (getCertificacionVigente): " + ex.getMessage());
/*     */     } finally {
/* 495 */       db.close();
/*     */     } 
/* 497 */     return nVariedad;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\VariedadDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */