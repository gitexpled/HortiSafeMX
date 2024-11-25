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
/*     */ import lib.struc.Diccionario;
/*     */ import lib.struc.filterSql;
/*     */ 
/*     */ 
/*     */ public class DiccionarioDB
/*     */ {
/*     */   public static Diccionario getDiccionario(String codigo) throws Exception {
/*  19 */     Diccionario o = null;
/*  20 */     ConnectionDB db = new ConnectionDB();
/*  21 */     Statement stmt = null;
/*  22 */     String sql = "";
/*     */     
/*     */     try {
/*  25 */       stmt = db.conn.createStatement();
/*     */       
/*  27 */       sql = "SELECT * FROM diccionario where idDiccionario='" + codigo + "'";
/*  28 */       ResultSet rs = stmt.executeQuery(sql);
/*  29 */       if (rs.next()) {
/*  30 */         o = new Diccionario();
/*  31 */         o.setIdDiccionario(rs.getInt("idDiccionario"));
/*  32 */         o.setCodProducto(rs.getString("codProducto"));
/*  33 */         o.setCodReemplazo(rs.getString("codRemplazo"));
/*  34 */         o.setCreado(rs.getDate("creado"));
/*  35 */         o.setModificado(rs.getDate("modificado"));
/*  36 */         o.setIdUser(rs.getInt("user_idUser"));
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
/*  49 */       throw new Exception("getUser: " + e.getMessage());
/*     */     } finally {
/*  51 */       db.close();
/*     */     } 
/*     */     
/*  54 */     return o;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Diccionario getDiccionarioStr(String codigo) throws Exception {
/*  59 */     Diccionario o = null;
/*  60 */     ConnectionDB db = new ConnectionDB();
/*  61 */     Statement stmt = null;
/*  62 */     String sql = "";
/*     */     
/*     */     try {
/*  65 */       stmt = db.conn.createStatement();
/*     */       
/*  67 */       sql = "SELECT * FROM diccionario where codProducto='" + codigo + "'";
/*  68 */       ResultSet rs = stmt.executeQuery(sql);
/*  69 */       if (rs.next()) {
/*  70 */         o = new Diccionario();
/*  71 */         o.setIdDiccionario(rs.getInt("idDiccionario"));
/*  72 */         o.setCodProducto(rs.getString("codProducto"));
/*  73 */         o.setCodReemplazo(rs.getString("codRemplazo"));
/*  74 */         o.setCreado(rs.getDate("creado"));
/*  75 */         o.setModificado(rs.getDate("modificado"));
/*  76 */         o.setIdUser(rs.getInt("user_idUser"));
/*     */       } else {
/*  78 */         o = null;
/*     */       } 
/*     */       
/*  81 */       rs.close();
/*  82 */       stmt.close();
/*  83 */       db.conn.close();
/*     */     }
/*  85 */     catch (SQLException e) {
/*     */       
/*  87 */       System.out.println("Error: " + e.getMessage());
/*  88 */       System.out.println("sql: " + sql);
/*  89 */       throw new Exception("getUser: " + e.getMessage());
/*     */     } finally {
/*  91 */       db.close();
/*     */     } 
/*     */     
/*  94 */     return o;
/*     */   }
/*     */   
/*     */   public static void updateDiccionario(Diccionario diccionario) throws Exception {
/*  98 */     System.out.println(diccionario.getIdDiccionario());
/*  99 */     PreparedStatement ps = null;
/* 100 */     String sql = "";
/* 101 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/* 102 */     ConnectionDB db = new ConnectionDB();
/*     */     try {
/* 104 */       db.conn.setAutoCommit(false);
/*     */       
/* 106 */       sql = "update  diccionario set codRemplazo=?,  modificado='" + d + "' where idDiccionario='" + diccionario.getIdDiccionario() + 
/* 107 */         "'";
/*     */       
/* 109 */       ps = db.conn.prepareStatement(sql);
/* 110 */       ps.setString(1, diccionario.getCodReemplazo());
/*     */ 
/*     */       
/* 113 */       ps.executeUpdate();
/* 114 */       db.conn.commit();
/* 115 */       db.conn.close();
/*     */     }
/* 117 */     catch (SQLException e) {
/*     */       
/* 119 */       System.out.println("Error: " + e.getMessage());
/* 120 */       System.out.println("sql: " + sql);
/* 121 */       throw new Exception("sepPfx: " + e.getMessage());
/* 122 */     } catch (Exception e) {
/* 123 */       e.printStackTrace();
/* 124 */       System.out.println("Error2: " + e.getMessage());
/* 125 */       throw new Exception("sepPfx: " + e.getMessage());
/*     */     } finally {
/*     */       
/* 128 */       db.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getDiccionariosAll(ArrayList<filterSql> filter) throws Exception {
/* 135 */     int total = 0;
/* 136 */     Statement stmt = null;
/* 137 */     String sql = "";
/* 138 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 141 */       stmt = db.conn.createStatement();
/*     */       
/* 143 */       sql = "SELECT count(1) FROM diccionario where codProducto != codRemplazo";
/*     */       
/* 145 */       if (filter.size() > 0) {
/* 146 */         String andSql = "";
/* 147 */         andSql = String.valueOf(andSql) + " and ";
/* 148 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 150 */         while (f.hasNext()) {
/*     */           
/* 152 */           filterSql row = f.next();
/* 153 */           if (!row.getValue().equals("")) {
/*     */             
/* 155 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 157 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 158 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 159 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 161 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 163 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 164 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 165 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 168 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like'%" + row.getValue() + "%'";
/* 169 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 175 */       ResultSet rs = stmt.executeQuery(sql);
/* 176 */       while (rs.next()) {
/* 177 */         total = rs.getInt(1);
/*     */       }
/* 179 */       rs.close();
/* 180 */       stmt.close();
/*     */ 
/*     */     
/*     */     }
/* 184 */     catch (SQLException e) {
/*     */       
/* 186 */       System.out.println("Error: " + e.getMessage());
/* 187 */       System.out.println("sql: " + sql);
/* 188 */       throw new Exception("getUsersAll: " + e.getMessage());
/*     */     } finally {
/* 190 */       db.close();
/*     */     } 
/*     */     
/* 193 */     return total;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<Diccionario> getDiccionario(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 199 */     ArrayList<Diccionario> diccionarios = new ArrayList<>();
/* 200 */     Statement stmt = null;
/* 201 */     String sql = "";
/* 202 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 205 */       stmt = db.conn.createStatement();
/*     */       
/* 207 */       sql = "SELECT DISTINCT * FROM diccionario where codProducto != codRemplazo";
/*     */       
/* 209 */       if (filter.size() > 0) {
/* 210 */         String andSql = "";
/* 211 */         andSql = String.valueOf(andSql) + " and ";
/* 212 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 214 */         while (f.hasNext()) {
/*     */           
/* 216 */           filterSql row = f.next();
/*     */           
/* 218 */           if (!row.getValue().equals("")) {
/*     */             
/* 220 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 222 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 223 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 224 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 226 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 228 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 229 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 230 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 233 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 234 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 239 */       if (!order.equals("")) {
/* 240 */         sql = String.valueOf(sql) + " order by ";
/*     */       }
/*     */       
/* 243 */       if (length > 0) {
/* 244 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/* 246 */       ResultSet rs = stmt.executeQuery(sql);
/* 247 */       while (rs.next()) {
/* 248 */         Diccionario o = new Diccionario();
/*     */         
/* 250 */         o.setCodProducto(rs.getString("codProducto"));
/* 251 */         o.setCodReemplazo(rs.getString("codRemplazo"));
/* 252 */         o.setCreado(rs.getDate("creado"));
/* 253 */         o.setModificado(rs.getDate("modificado"));
/* 254 */         o.setIdDiccionario(rs.getInt("idDiccionario"));
/* 255 */         diccionarios.add(o);
/*     */       } 
/* 257 */       rs.close();
/* 258 */       stmt.close();
/* 259 */       db.conn.close();
/*     */     }
/* 261 */     catch (SQLException e) {
/*     */       
/* 263 */       System.out.println("Error: " + e.getMessage());
/* 264 */       System.out.println("sql: " + sql);
/* 265 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/* 267 */       db.close();
/*     */     } 
/*     */     
/* 270 */     return diccionarios;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean insertDiccionario(Diccionario diccionario) throws ParseException {
/* 275 */     ConnectionDB db = new ConnectionDB();
/* 276 */     Statement stmt = null;
/* 277 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/* 278 */     boolean resp = true;
/* 279 */     String sql = "";
/*     */     
/*     */     try {
/* 282 */       sql = "INSERT INTO diccionario(codProducto,codRemplazo,creado,modificado,user_idUser) Values ('" + diccionario.getCodProducto() + "','" + diccionario.getCodReemplazo() + "','" + d + "','" + d + "'," + diccionario.getIdUser() + ")";
/* 283 */       stmt = db.conn.createStatement();
/* 284 */       resp = stmt.execute(sql);
/* 285 */       stmt.close();
/*     */     }
/* 287 */     catch (Exception ex) {
/*     */       
/* 289 */       System.out.println(ex.getMessage());
/*     */     } finally {
/*     */       
/* 292 */       db.close();
/*     */     } 
/* 294 */     return resp;
/*     */   }
/*     */   public static Diccionario validaDiccionario(String codigo) throws Exception {
/* 297 */     Diccionario o = null;
/* 298 */     ConnectionDB db = new ConnectionDB();
/* 299 */     Statement stmt = null;
/* 300 */     String sql = "";
/*     */     
/*     */     try {
/* 303 */       stmt = db.conn.createStatement();
/*     */       
/* 305 */       sql = "SELECT * FROM diccionario where codRemplazo='" + codigo + "'";
/* 306 */       ResultSet rs = stmt.executeQuery(sql);
/* 307 */       if (rs.next()) {
/* 308 */         o = new Diccionario();
/* 309 */         o.setIdDiccionario(rs.getInt("idDiccionario"));
/* 310 */         o.setCodProducto(rs.getString("codProducto"));
/* 311 */         o.setCodReemplazo(rs.getString("codRemplazo"));
/* 312 */         o.setCreado(rs.getDate("creado"));
/* 313 */         o.setModificado(rs.getDate("modificado"));
/* 314 */         o.setIdUser(rs.getInt("user_idUser"));
/*     */       } else {
/* 316 */         o = null;
/*     */       } 
/*     */       
/* 319 */       rs.close();
/* 320 */       stmt.close();
/* 321 */       db.conn.close();
/*     */     }
/* 323 */     catch (SQLException e) {
/*     */       
/* 325 */       System.out.println("Error: " + e.getMessage());
/* 326 */       System.out.println("sql: " + sql);
/* 327 */       throw new Exception("getUser: " + e.getMessage());
/*     */     } finally {
/* 329 */       db.close();
/*     */     } 
/*     */     
/* 332 */     return o;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<Diccionario> getSelect() throws Exception {
/* 339 */     ArrayList<Diccionario> diccionarios = new ArrayList<>();
/* 340 */     Statement stmt = null;
/* 341 */     String sql = "";
/* 342 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 345 */       stmt = db.conn.createStatement();
/*     */       
/* 347 */       sql = "SELECT DISTINCT codProducto, codRemplazo FROM  diccionario where codProducto=codRemplazo order by 1 asc";
/*     */ 
/*     */       
/* 350 */       ResultSet rs = stmt.executeQuery(sql);
/* 351 */       while (rs.next()) {
/* 352 */         Diccionario o = new Diccionario();
/*     */         
/* 354 */         o.setCodProducto(rs.getString("codProducto"));
/* 355 */         o.setCodReemplazo(rs.getString("codRemplazo"));
/* 356 */         diccionarios.add(o);
/*     */       } 
/* 358 */       rs.close();
/* 359 */       stmt.close();
/* 360 */       db.conn.close();
/*     */     }
/* 362 */     catch (SQLException e) {
/*     */       
/* 364 */       System.out.println("Error: " + e.getMessage());
/* 365 */       System.out.println("sql: " + sql);
/* 366 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/* 368 */       db.close();
/*     */     } 
/*     */     
/* 371 */     return diccionarios;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getDiccionariosEqualAll(ArrayList<filterSql> filter) throws Exception {
/* 377 */     int total = 0;
/* 378 */     Statement stmt = null;
/* 379 */     String sql = "";
/* 380 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 383 */       stmt = db.conn.createStatement();
/*     */       
/* 385 */       sql = "SELECT count(1) FROM diccionario where codProducto = codRemplazo";
/*     */       
/* 387 */       if (filter.size() > 0) {
/* 388 */         String andSql = "";
/* 389 */         andSql = String.valueOf(andSql) + " and ";
/* 390 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 392 */         while (f.hasNext()) {
/*     */           
/* 394 */           filterSql row = f.next();
/* 395 */           if (!row.getValue().equals("")) {
/*     */             
/* 397 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 399 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 400 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 401 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 403 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 405 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 406 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 407 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 410 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like'%" + row.getValue() + "%'";
/* 411 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 417 */       ResultSet rs = stmt.executeQuery(sql);
/* 418 */       while (rs.next()) {
/* 419 */         total = rs.getInt(1);
/*     */       }
/* 421 */       rs.close();
/* 422 */       stmt.close();
/*     */ 
/*     */     
/*     */     }
/* 426 */     catch (SQLException e) {
/*     */       
/* 428 */       System.out.println("Error: " + e.getMessage());
/* 429 */       System.out.println("sql: " + sql);
/* 430 */       throw new Exception("getUsersAll: " + e.getMessage());
/*     */     } finally {
/* 432 */       db.close();
/*     */     } 
/*     */     
/* 435 */     return total;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ArrayList<Diccionario> getDiccionarioEqual(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 440 */     ArrayList<Diccionario> diccionarios = new ArrayList<>();
/* 441 */     Statement stmt = null;
/* 442 */     String sql = "";
/* 443 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 446 */       stmt = db.conn.createStatement();
/*     */       
/* 448 */       sql = "SELECT DISTINCT * FROM diccionario where codProducto = codRemplazo";
/*     */       
/* 450 */       if (filter.size() > 0) {
/* 451 */         String andSql = "";
/* 452 */         andSql = String.valueOf(andSql) + " and ";
/* 453 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 455 */         while (f.hasNext()) {
/*     */           
/* 457 */           filterSql row = f.next();
/*     */           
/* 459 */           if (!row.getValue().equals("")) {
/*     */             
/* 461 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 463 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 464 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 465 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 467 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 469 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 470 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 471 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 474 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 475 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 480 */       if (!order.equals("")) {
/* 481 */         sql = String.valueOf(sql) + " order by ";
/*     */       }
/*     */       
/* 484 */       if (length > 0) {
/* 485 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/* 487 */       System.out.println("query: " + sql);
/* 488 */       ResultSet rs = stmt.executeQuery(sql);
/* 489 */       while (rs.next()) {
/* 490 */         Diccionario o = new Diccionario();
/*     */         
/* 492 */         o.setCodProducto(rs.getString("codProducto"));
/* 493 */         o.setCodReemplazo(rs.getString("codRemplazo"));
/* 494 */         o.setCreado(rs.getDate("creado"));
/* 495 */         o.setModificado(rs.getDate("modificado"));
/* 496 */         o.setIdDiccionario(rs.getInt("idDiccionario"));
/* 497 */         diccionarios.add(o);
/*     */       } 
/* 499 */       rs.close();
/* 500 */       stmt.close();
/* 501 */       db.conn.close();
/*     */     }
/* 503 */     catch (SQLException e) {
/*     */       
/* 505 */       System.out.println("Error: " + e.getMessage());
/* 506 */       System.out.println("sql: " + sql);
/* 507 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/* 509 */       db.close();
/*     */     } 
/*     */     
/* 512 */     return diccionarios;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<Diccionario> getSelectEqual() throws Exception {
/* 518 */     ArrayList<Diccionario> diccionarios = new ArrayList<>();
/* 519 */     Statement stmt = null;
/* 520 */     String sql = "";
/* 521 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 524 */       stmt = db.conn.createStatement();
/*     */       
/* 526 */       sql = "SELECT DISTINCT codProducto, codRemplazo FROM diccionario where codProducto=codRemplazo order by 1 asc";
/*     */ 
/*     */       
/* 529 */       ResultSet rs = stmt.executeQuery(sql);
/* 530 */       while (rs.next()) {
/* 531 */         Diccionario o = new Diccionario();
/*     */         
/* 533 */         o.setCodProducto(rs.getString("codProducto"));
/* 534 */         o.setCodReemplazo(rs.getString("codRemplazo"));
/* 535 */         diccionarios.add(o);
/*     */       } 
/* 537 */       rs.close();
/* 538 */       stmt.close();
/* 539 */       db.conn.close();
/*     */     }
/* 541 */     catch (SQLException e) {
/*     */       
/* 543 */       System.out.println("Error: " + e.getMessage());
/* 544 */       System.out.println("sql: " + sql);
/* 545 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/* 547 */       db.close();
/*     */     } 
/*     */     
/* 550 */     return diccionarios;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\DiccionarioDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */