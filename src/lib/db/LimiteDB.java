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
/*     */ import lib.struc.Limite;
/*     */ import lib.struc.filterSql;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LimiteDB
/*     */ {
/*     */   public static Limite getLimite(String idLimite) {
/*  20 */     ConnectionDB db = new ConnectionDB();
/*  21 */     Limite limite = null;
/*  22 */     Statement stmt = null;
/*  23 */     String sql = "";
/*     */     
/*     */     try {
/*  26 */       stmt = db.conn.createStatement();
/*  27 */       sql = "Select * from limites where idLimites=" + idLimite;
/*  28 */       ResultSet rs = stmt.executeQuery(sql);
/*  29 */       if (rs.next()) {
/*     */         
/*  31 */         limite = new Limite();
/*  32 */         limite.setIdLimite(rs.getInt("idLimites"));
/*  33 */         limite.setCodProducto(rs.getString("codProducto"));
/*  34 */         limite.setIdMercado(rs.getInt("idMercado"));
/*  35 */         limite.setIdTipoProducto(rs.getInt("idTipoProducto"));
/*  36 */         limite.setIdFuente(rs.getInt("idFuente"));
/*  37 */         limite.setLimite(rs.getString("limite"));
/*  38 */         limite.setCreado(rs.getDate("creado"));
/*  39 */         limite.setModificado(rs.getDate("modificacion"));
/*  40 */         limite.setIdEspecie(rs.getInt("idEspecie"));
/*     */       } 
/*  42 */       rs.close();
/*  43 */       stmt.close();
/*  44 */     } catch (Exception ex) {
/*     */       
/*  46 */       System.out.println("Error: " + ex.getMessage());
/*     */     } finally {
/*  48 */       db.close();
/*     */     } 
/*  50 */     return limite;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateLimite(Limite limite) throws Exception {
/*  55 */     PreparedStatement ps = null;
/*  56 */     String sql = "";
/*  57 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/*  58 */     ConnectionDB db = new ConnectionDB();
/*     */     try {
/*  60 */       db.conn.setAutoCommit(false);
/*     */       
/*  62 */       sql = "update  limites set codProducto=?,idMercado=?,idTipoProducto=?,idFuente=?,limite=?,  modificacion='" + d + "',idEspecie=" + limite.getIdEspecie() + " where idLimites='" + limite.getIdLimite() + 
/*  63 */         "'";
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  68 */       ps = db.conn.prepareStatement(sql);
/*  69 */       ps.setString(1, limite.getCodProducto());
/*  70 */       ps.setInt(2, limite.getIdMercado());
/*  71 */       ps.setInt(3, limite.getIdTipoProducto());
/*  72 */       ps.setInt(4, limite.getIdFuente());
/*  73 */       ps.setString(5, limite.getLimite());
/*     */ 
/*     */       
/*  76 */       ps.executeUpdate();
/*  77 */       db.conn.commit();
/*  78 */       db.conn.close();
/*     */     }
/*  80 */     catch (SQLException e) {
/*     */       
/*  82 */       System.out.println("Error: " + e.getMessage());
/*  83 */       System.out.println("sql: " + sql);
/*  84 */       throw new Exception("updateLimite: " + e.getMessage());
/*  85 */     } catch (Exception e) {
/*  86 */       e.printStackTrace();
/*  87 */       System.out.println("Error2: " + e.getMessage());
/*  88 */       throw new Exception("updateLimite: " + e.getMessage());
/*     */     } finally {
/*     */       
/*  91 */       db.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int getLimitesAll(ArrayList<filterSql> filter) throws Exception {
/*  96 */     int total = 0;
/*  97 */     Statement stmt = null;
/*  98 */     String sql = "";
/*  99 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 102 */       stmt = db.conn.createStatement();
/*     */       
/* 104 */       sql = "SELECT count(1) FROM limites l";
/*     */       
/* 106 */       if (filter.size() > 0) {
/* 107 */         String andSql = "";
/* 108 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 109 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 111 */         while (f.hasNext()) {
/*     */           
/* 113 */           filterSql row = f.next();
/* 114 */           if (!row.getValue().equals("")) {
/*     */             
/* 116 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 118 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 119 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 120 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'"; continue;
/*     */             } 
/* 122 */             if (row.getCampo().endsWith("_from")) {
/*     */               
/* 124 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 125 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 126 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */               continue;
/*     */             } 
/* 129 */             if (row.getCampo().equals("l.codProducto")) {
/* 130 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '" + row.getValue() + "' ";
/* 131 */               andSql = " and "; continue;
/*     */             } 
/* 133 */             sql = String.valueOf(sql) + andSql + row.getCampo() + " = " + row.getValue() + " ";
/* 134 */             andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
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
/* 156 */       throw new Exception("getLimitesAll: " + e.getMessage());
/*     */     } finally {
/* 158 */       db.close();
/*     */     } 
/* 160 */     return total;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<Limite> getLimites(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 166 */     ArrayList<Limite> limites = new ArrayList<>();
/* 167 */     Statement stmt = null;
/* 168 */     String sql = "";
/* 169 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 172 */       stmt = db.conn.createStatement();
/*     */       
/* 174 */       sql = "SELECT l.*,m.mercado,t.tipoProducto, f.nombre as fuente FROM limites l inner join mercado m on (l.idMercado=m.idMercado) inner join tipoProducto t on (l.idTipoProducto=t.idTipoProducto) inner join fuente f on (l.idFuente=f.idFuente)";
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 179 */       if (filter.size() > 0) {
/* 180 */         String andSql = "";
/* 181 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 182 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 184 */         while (f.hasNext()) {
/*     */           
/* 186 */           filterSql row = f.next();
/*     */           
/* 188 */           if (!row.getValue().equals("")) {
/*     */             
/* 190 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 192 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 193 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 194 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 196 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 198 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 199 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 200 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */ 
/*     */             
/*     */             }
/* 204 */             else if (row.getCampo().equals("l.codProducto")) {
/* 205 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '" + row.getValue() + "' ";
/*     */             } else {
/* 207 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " = " + row.getValue() + " ";
/*     */             } 
/* 209 */             andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 214 */       if (!order.equals("")) {
/* 215 */         sql = String.valueOf(sql) + " order by " + order;
/*     */       }
/*     */       
/* 218 */       if (length > 0) {
/* 219 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/* 221 */       System.out.println(sql);
/* 222 */       ResultSet rs = stmt.executeQuery(sql);
/* 223 */       while (rs.next()) {
/* 224 */         Limite limite = new Limite();
/* 225 */         limite.setIdLimite(rs.getInt("idLimites"));
/* 226 */         limite.setCodProducto(rs.getString("codProducto"));
/* 227 */         limite.setIdMercado(rs.getInt("idMercado"));
/* 228 */         limite.setIdTipoProducto(rs.getInt("idTipoProducto"));
/* 229 */         limite.setIdFuente(rs.getInt("idFuente"));
/* 230 */         limite.setLimite(rs.getString("limite"));
/* 231 */         limite.setCreado(rs.getDate("creado"));
/* 232 */         limite.setModificado(rs.getDate("modificacion"));
/* 233 */         limite.setMercado(rs.getString("mercado"));
/* 234 */         limite.setTipoProducto(rs.getString("tipoProducto"));
/* 235 */         limite.setFuente(rs.getString("fuente"));
/* 236 */         limite.setIdEspecie(rs.getInt("idEspecie"));
/* 237 */         limites.add(limite);
/*     */       } 
/* 239 */       System.out.println(limites.size());
/* 240 */       rs.close();
/* 241 */       stmt.close();
/* 242 */       db.conn.close();
/*     */     }
/* 244 */     catch (SQLException e) {
/*     */       
/* 246 */       System.out.println("Error: " + e.getMessage());
/* 247 */       System.out.println("sql: " + sql);
/* 248 */       throw new Exception("getLimite: " + e.getMessage());
/*     */     } finally {
/* 250 */       db.close();
/*     */     } 
/*     */     
/* 253 */     return limites;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean insertLimite(Limite limite) throws ParseException {
/* 258 */     ConnectionDB db = new ConnectionDB();
/* 259 */     Statement stmt = null;
/* 260 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/* 261 */     boolean resp = true;
/* 262 */     String sql = "";
/*     */     
/*     */     try {
/* 265 */       sql = "INSERT INTO limites(codProducto,idMercado,idTipoProducto,idFuente,limite,creado,modificacion,idEspecie) Values ('" + limite.getCodProducto() + "'," + limite.getIdMercado() + "," + limite.getIdTipoProducto() + "," + limite.getIdFuente() + ",'" + limite.getLimite() + "','" + d + "','" + d + "'," + limite.getIdEspecie() + ")";
/* 266 */       stmt = db.conn.createStatement();
/* 267 */       resp = stmt.execute(sql);
/* 268 */       stmt.close();
/*     */     }
/* 270 */     catch (Exception ex) {
/*     */       
/* 272 */       System.out.println(ex.getMessage());
/*     */     } finally {
/*     */       
/* 275 */       db.close();
/*     */     } 
/* 277 */     return resp;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Limite validaLimite(Limite limite) {
/* 282 */     ConnectionDB db = new ConnectionDB();
/* 283 */     Limite limit = null;
/* 284 */     Statement stmt = null;
/* 285 */     String sql = "";
/*     */     
/*     */     try {
/* 288 */       sql = "SELECT * FROM limites where idMercado=" + limite.getIdMercado() + " and codProducto='" + limite.getCodProducto() + "' and idEspecie =" + limite.getIdEspecie() + " and idTipoProducto=" + limite.getIdTipoProducto() + " and idFuente=" + limite.getIdFuente() + " and idLimites!=" + limite.getIdLimite();
/* 289 */       System.out.println("limite: " + sql);
/* 290 */       stmt = db.conn.createStatement();
/* 291 */       ResultSet rs = stmt.executeQuery(sql);
/* 292 */       if (rs.next()) {
/*     */         
/* 294 */         limit = new Limite();
/* 295 */         limit.setIdLimite(rs.getInt("idLimites"));
/* 296 */         limit.setCodProducto(rs.getString("codProducto"));
/* 297 */         limit.setIdMercado(rs.getInt("idMercado"));
/* 298 */         limit.setIdTipoProducto(rs.getInt("idTipoProducto"));
/* 299 */         limit.setIdFuente(rs.getInt("idFuente"));
/* 300 */         limit.setLimite(rs.getString("limite"));
/* 301 */         limit.setCreado(rs.getDate("creado"));
/* 302 */         limit.setModificado(rs.getDate("modificacion"));
/* 303 */         limite.setIdEspecie(rs.getInt("idEspecie"));
/*     */       } 
/* 305 */       rs.close();
/* 306 */       stmt.close();
/* 307 */     } catch (Exception ex) {
/*     */       
/* 309 */       System.out.println("validaLimite Error: " + ex.getMessage());
/*     */     } finally {
/* 311 */       db.close();
/*     */     } 
/* 313 */     return limit;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Limite validaLimiteExcel(Limite limite) {
/* 319 */     ConnectionDB db = new ConnectionDB();
/* 320 */     Limite limit = null;
/* 321 */     Statement stmt = null;
/* 322 */     String sql = "";
/*     */     
/*     */     try {
/* 325 */       sql = "SELECT * FROM limites where idMercado=" + limite.getIdMercado() + " and codProducto='" + limite.getCodProducto() + "' and idEspecie='" + limite.getIdEspecie() + "'";
/*     */       
/* 327 */       stmt = db.conn.createStatement();
/* 328 */       ResultSet rs = stmt.executeQuery(sql);
/* 329 */       if (rs.next()) {
/*     */         
/* 331 */         limit = new Limite();
/* 332 */         limit.setIdLimite(rs.getInt("idLimites"));
/* 333 */         limit.setCodProducto(rs.getString("codProducto"));
/* 334 */         limit.setIdMercado(rs.getInt("idMercado"));
/* 335 */         limit.setIdTipoProducto(rs.getInt("idTipoProducto"));
/* 336 */         limit.setIdFuente(rs.getInt("idFuente"));
/* 337 */         limit.setLimite(rs.getString("limite"));
/* 338 */         limit.setCreado(rs.getDate("creado"));
/* 339 */         limit.setModificado(rs.getDate("modificacion"));
/* 340 */         limite.setIdEspecie(rs.getInt("idEspecie"));
/*     */       } 
/* 342 */       rs.close();
/* 343 */       stmt.close();
/* 344 */     } catch (Exception ex) {
/*     */       
/* 346 */       System.out.println("validaLimite Error: " + ex.getMessage());
/*     */     } finally {
/* 348 */       db.close();
/*     */     } 
/* 350 */     return limit;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Limite validaLimiteExcel2(Limite limite) {
/* 355 */     ConnectionDB db = new ConnectionDB();
/* 356 */     Limite limit = null;
/* 357 */     Statement stmt = null;
/* 358 */     String sql = "";
/*     */     
/*     */     try {
/* 361 */       sql = "SELECT * FROM limites where idMercado='15' and codProducto='" + limite.getCodProducto() + "' and idEspecie='" + limite.getIdEspecie() + "'";
/*     */       
/* 363 */       stmt = db.conn.createStatement();
/* 364 */       ResultSet rs = stmt.executeQuery(sql);
/* 365 */       if (rs.next()) {
/*     */         
/* 367 */         limit = new Limite();
/* 368 */         limit.setIdLimite(rs.getInt("idLimites"));
/* 369 */         limit.setCodProducto(rs.getString("codProducto"));
/* 370 */         limit.setIdMercado(rs.getInt("idMercado"));
/* 371 */         limit.setIdTipoProducto(rs.getInt("idTipoProducto"));
/* 372 */         limit.setIdFuente(rs.getInt("idFuente"));
/* 373 */         limit.setLimite(rs.getString("limite"));
/* 374 */         limit.setCreado(rs.getDate("creado"));
/* 375 */         limit.setModificado(rs.getDate("modificacion"));
/* 376 */         limite.setIdEspecie(rs.getInt("idEspecie"));
/*     */       } 
/* 378 */       rs.close();
/* 379 */       stmt.close();
/* 380 */     } catch (Exception ex) {
/*     */       
/* 382 */       System.out.println("validaLimite Error: " + ex.getMessage());
/*     */     } finally {
/* 384 */       db.close();
/*     */     } 
/* 386 */     return limit;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Limite validaLimiteExcelChina(Limite limite) {
/* 391 */     ConnectionDB db = new ConnectionDB();
/* 392 */     Limite limit = null;
/* 393 */     Statement stmt = null;
/* 394 */     String sql = "";
/*     */     
/*     */     try {
/* 397 */       sql = "SELECT * FROM limites where (limite=0 ) and  idMercado=" + limite.getIdMercado() + " " + 
/* 398 */         " and codProducto='" + limite.getCodProducto() + "' and idEspecie='" + limite.getIdEspecie() + "'";
/*     */       
/* 400 */       stmt = db.conn.createStatement();
/* 401 */       ResultSet rs = stmt.executeQuery(sql);
/* 402 */       if (rs.next()) {
/*     */         
/* 404 */         limit = new Limite();
/* 405 */         limit.setIdLimite(rs.getInt("idLimites"));
/* 406 */         limit.setCodProducto(rs.getString("codProducto"));
/* 407 */         limit.setIdMercado(rs.getInt("idMercado"));
/* 408 */         limit.setIdTipoProducto(rs.getInt("idTipoProducto"));
/* 409 */         limit.setIdFuente(rs.getInt("idFuente"));
/* 410 */         limit.setLimite(rs.getString("limite"));
/* 411 */         limit.setCreado(rs.getDate("creado"));
/* 412 */         limit.setModificado(rs.getDate("modificacion"));
/* 413 */         limite.setIdEspecie(rs.getInt("idEspecie"));
/*     */       } 
/* 415 */       rs.close();
/* 416 */       stmt.close();
/* 417 */     } catch (Exception ex) {
/*     */       
/* 419 */       System.out.println("validaLimite Error: " + ex.getMessage());
/*     */     } finally {
/* 421 */       db.close();
/*     */     } 
/* 423 */     return limit;
/*     */   }
/*     */   
/*     */   public static Limite validaLimiteExcelChinaMenor(Limite limite) {
/* 427 */     ConnectionDB db = new ConnectionDB();
/* 428 */     Limite limit = null;
/* 429 */     Statement stmt = null;
/* 430 */     String sql = "";
/*     */     
/*     */     try {
/* 433 */       sql = "SELECT * FROM limites where (limite>'" + limite.getLimite() + "' ) and  idMercado=" + limite.getIdMercado() + " " + 
/* 434 */         " and codProducto='" + limite.getCodProducto() + "' and idEspecie='" + limite.getIdEspecie() + "'";
/*     */       
/* 436 */       stmt = db.conn.createStatement();
/* 437 */       ResultSet rs = stmt.executeQuery(sql);
/* 438 */       if (rs.next()) {
/*     */         
/* 440 */         limit = new Limite();
/* 441 */         limit.setIdLimite(rs.getInt("idLimites"));
/* 442 */         limit.setCodProducto(rs.getString("codProducto"));
/* 443 */         limit.setIdMercado(rs.getInt("idMercado"));
/* 444 */         limit.setIdTipoProducto(rs.getInt("idTipoProducto"));
/* 445 */         limit.setIdFuente(rs.getInt("idFuente"));
/* 446 */         limit.setLimite(rs.getString("limite"));
/* 447 */         limit.setCreado(rs.getDate("creado"));
/* 448 */         limit.setModificado(rs.getDate("modificacion"));
/* 449 */         limite.setIdEspecie(rs.getInt("idEspecie"));
/*     */       } 
/* 451 */       rs.close();
/* 452 */       stmt.close();
/* 453 */     } catch (Exception ex) {
/*     */       
/* 455 */       System.out.println("validaLimite Error: " + ex.getMessage());
/*     */     } finally {
/* 457 */       db.close();
/*     */     } 
/* 459 */     return limit;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\LimiteDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */