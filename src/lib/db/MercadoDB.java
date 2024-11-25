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
/*     */ import lib.struc.Mercado;
/*     */ import lib.struc.filterSql;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MercadoDB
/*     */ {
/*     */   public static Mercado getMercado(String idMercado) throws Exception {
/*  20 */     Mercado o = null;
/*  21 */     ConnectionDB db = new ConnectionDB();
/*  22 */     Statement stmt = null;
/*  23 */     String sql = "";
/*     */     
/*     */     try {
/*  26 */       stmt = db.conn.createStatement();
/*     */       
/*  28 */       sql = "SELECT * FROM vw_mercados where idMercado='" + idMercado + "'";
/*     */       
/*  30 */       ResultSet rs = stmt.executeQuery(sql);
/*  31 */       if (rs.next()) {
/*  32 */         o = new Mercado();
/*  33 */         o.setIdMercado(rs.getInt("idMercado"));
/*  34 */         o.setMercado(rs.getString("mercado"));
/*  35 */         o.setMercado2(rs.getString("mercado2"));
/*  36 */         o.setPf(rs.getString("pf"));
/*  37 */         o.setRegla(rs.getString("regla"));
/*  38 */         o.setCliente(rs.getString("cliente"));
/*     */         
/*  40 */         o.setIdMercadoPadre(rs.getInt("idMercadoPadre"));
/*  41 */         o.setPorcentaje(rs.getString("porcentaje"));
/*     */         
/*  43 */         o.setProductor(rs.getString("productor"));
/*  44 */         o.setRetricionMolecula(rs.getString("retricionMolecula"));
/*  45 */         o.setMolecula(rs.getInt("molecula"));
/*     */         
/*  47 */         o.setCreado(rs.getDate("creado"));
/*  48 */         o.setModificado(rs.getDate("modificado"));
/*  49 */         o.setIdUser(rs.getInt("idUser"));
/*  50 */         o.setVisible(rs.getString("visible"));
/*     */       } else {
/*  52 */         o = null;
/*     */       } 
/*     */       
/*  55 */       rs.close();
/*  56 */       stmt.close();
/*  57 */       db.conn.close();
/*     */     }
/*  59 */     catch (SQLException e) {
/*     */       
/*  61 */       System.out.println("Error: " + e.getMessage());
/*  62 */       System.out.println("sql: " + sql);
/*  63 */       throw new Exception("getMercado: " + e.getMessage());
/*     */     } finally {
/*  65 */       db.close();
/*     */     } 
/*     */     
/*  68 */     return o;
/*     */   }
/*     */   
/*     */   public static ArrayList<Mercado> getMercadoStrArray(String mercado) throws Exception {
/*  72 */     ArrayList<Mercado> listado = new ArrayList<>();
/*     */     
/*  74 */     ConnectionDB db = new ConnectionDB();
/*  75 */     Statement stmt = null;
/*  76 */     String sql = "";
/*     */     
/*     */     try {
/*  79 */       stmt = db.conn.createStatement();
/*     */       
/*  81 */       sql = "SELECT * FROM vw_mercados where mercado2='" + mercado + "'";
/*     */       
/*  83 */       ResultSet rs = stmt.executeQuery(sql);
/*  84 */       while (rs.next()) {
/*     */         
/*  86 */         Mercado o = new Mercado();
/*  87 */         o.setIdMercado(rs.getInt("idMercado"));
/*  88 */         o.setMercado(rs.getString("mercado"));
/*  89 */         o.setCreado(rs.getDate("creado"));
/*  90 */         o.setModificado(rs.getDate("modificado"));
/*  91 */         o.setIdUser(rs.getInt("idUser"));
/*  92 */         o.setRegla(rs.getString("regla"));
/*  93 */         listado.add(o);
/*     */       } 
/*     */       
/*  96 */       rs.close();
/*  97 */       stmt.close();
/*  98 */       db.conn.close();
/*     */     }
/* 100 */     catch (SQLException e) {
/*     */       
/* 102 */       System.out.println("Error: " + e.getMessage());
/* 103 */       System.out.println("sql: " + sql);
/* 104 */       throw new Exception("getMercado: " + e.getMessage());
/*     */     } finally {
/* 106 */       db.close();
/*     */     } 
/*     */     
/* 109 */     return listado;
/*     */   }
/*     */   
/*     */   public static Mercado getMercadoStr(String mercado) throws Exception {
/* 113 */     Mercado o = null;
/* 114 */     ConnectionDB db = new ConnectionDB();
/* 115 */     Statement stmt = null;
/* 116 */     String sql = "";
/*     */     
/*     */     try {
/* 119 */       stmt = db.conn.createStatement();
/*     */       
/* 121 */       sql = "SELECT * FROM vw_mercados where mercado2='" + mercado + "'";
/*     */       
/* 123 */       ResultSet rs = stmt.executeQuery(sql);
/* 124 */       if (rs.next()) {
/* 125 */         o = new Mercado();
/* 126 */         o.setIdMercado(rs.getInt("idMercado"));
/* 127 */         o.setMercado(rs.getString("mercado"));
/* 128 */         o.setRegla(rs.getString("regla"));
/* 129 */         o.setCreado(rs.getDate("creado"));
/* 130 */         o.setModificado(rs.getDate("modificado"));
/* 131 */         o.setIdUser(rs.getInt("idUser"));
/*     */       } else {
/* 133 */         o = null;
/*     */       } 
/*     */       
/* 136 */       rs.close();
/* 137 */       stmt.close();
/* 138 */       db.conn.close();
/*     */     }
/* 140 */     catch (SQLException e) {
/*     */       
/* 142 */       System.out.println("Error: " + e.getMessage());
/* 143 */       System.out.println("sql: " + sql);
/* 144 */       throw new Exception("getMercado: " + e.getMessage());
/*     */     } finally {
/* 146 */       db.close();
/*     */     } 
/*     */     
/* 149 */     return o;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateMercado(Mercado mercado) throws Exception {
/* 154 */     PreparedStatement ps = null;
/* 155 */     String sql = "";
/* 156 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/* 157 */     ConnectionDB db = new ConnectionDB();
/*     */     try {
/* 159 */       db.conn.setAutoCommit(false);
/*     */       
/* 161 */       sql = "update  mercado set idUser=?,mercado=?, mercado2=?, pf=?,regla=?,cliente=?,idMercadoPadre=?,porcentaje=?,productor=?,retricionMolecula=?,molecula=?,visible=?,  modificado='" + d + "' where idMercado='" + mercado.getIdMercado() + 
/* 162 */         "'";
/*     */       
/* 164 */       System.out.println(sql);
/* 165 */       ps = db.conn.prepareStatement(sql);
/* 166 */       ps.setInt(1, mercado.getIdUser());
/* 167 */       ps.setString(2, mercado.getMercado());
/* 168 */       ps.setString(3, mercado.getMercado2());
/* 169 */       ps.setString(4, mercado.getPf());
/* 170 */       ps.setString(5, mercado.getRegla());
/* 171 */       ps.setString(6, mercado.getCliente());
/* 172 */       ps.setInt(7, mercado.getIdMercadoPadre());
/* 173 */       ps.setString(8, mercado.getPorcentaje());
/*     */       
/* 175 */       ps.setString(9, mercado.getProductor());
/* 176 */       ps.setString(10, mercado.getRetricionMolecula());
/* 177 */       ps.setInt(11, mercado.getMolecula());
/* 178 */       ps.setString(12, mercado.getVisible());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 183 */       ps.executeUpdate();
/* 184 */       db.conn.commit();
/* 185 */       db.conn.close();
/*     */     }
/* 187 */     catch (SQLException e) {
/*     */       
/* 189 */       System.out.println("Error: " + e.getMessage());
/* 190 */       System.out.println("sql: " + sql);
/* 191 */       throw new Exception("sepMercado: " + e.getMessage());
/* 192 */     } catch (Exception e) {
/* 193 */       e.printStackTrace();
/* 194 */       System.out.println("Error2: " + e.getMessage());
/* 195 */       throw new Exception("sepMercado: " + e.getMessage());
/*     */     } finally {
/*     */       
/* 198 */       db.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getMercadosAll(ArrayList<filterSql> filter) throws Exception {
/* 205 */     int total = 0;
/* 206 */     Statement stmt = null;
/* 207 */     String sql = "";
/* 208 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 211 */       stmt = db.conn.createStatement();
/*     */       
/* 213 */       sql = "SELECT count(1) FROM vw_mercados ";
/*     */       
/* 215 */       if (filter.size() > 0) {
/* 216 */         String andSql = "";
/* 217 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 218 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 220 */         while (f.hasNext()) {
/*     */           
/* 222 */           filterSql row = f.next();
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
/* 238 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like'%" + row.getValue() + "%'";
/* 239 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 245 */       ResultSet rs = stmt.executeQuery(sql);
/* 246 */       while (rs.next()) {
/* 247 */         total = rs.getInt(1);
/*     */       }
/* 249 */       rs.close();
/* 250 */       stmt.close();
/* 251 */       db.conn.close();
/*     */     
/*     */     }
/* 254 */     catch (SQLException e) {
/*     */       
/* 256 */       System.out.println("Error: " + e.getMessage());
/* 257 */       System.out.println("sql: " + sql);
/* 258 */       throw new Exception("getMercadosAll: " + e.getMessage());
/*     */     } finally {
/* 260 */       db.close();
/*     */     } 
/*     */     
/* 263 */     return total;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<Mercado> getMercado(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 269 */     ArrayList<Mercado> mercados = new ArrayList<>();
/* 270 */     Statement stmt = null;
/* 271 */     String sql = "";
/* 272 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 275 */       stmt = db.conn.createStatement();
/*     */       
/* 277 */       sql = "SELECT * FROM vw_mercados ";
/*     */       
/* 279 */       if (filter.size() > 0) {
/* 280 */         String andSql = "";
/* 281 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 282 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 284 */         while (f.hasNext()) {
/*     */           
/* 286 */           filterSql row = f.next();
/*     */           
/* 288 */           if (!row.getValue().equals("")) {
/*     */             
/* 290 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 292 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 293 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 294 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 296 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 298 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 299 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 300 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 303 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 304 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 309 */       if (!order.equals("")) {
/* 310 */         sql = String.valueOf(sql) + " order by idMercado";
/*     */       }
/*     */       
/* 313 */       if (length > 0) {
/* 314 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/* 316 */       ResultSet rs = stmt.executeQuery(sql);
/* 317 */       while (rs.next()) {
/* 318 */         Mercado o = new Mercado();
/*     */         
/* 320 */         o.setIdMercado(rs.getInt("idMercado"));
/* 321 */         o.setMercado(rs.getString("mercado"));
/* 322 */         o.setMercado2(rs.getString("mercado2"));
/* 323 */         o.setPf(rs.getString("pf"));
/* 324 */         o.setCreado(rs.getDate("creado"));
/* 325 */         o.setModificado(rs.getDate("modificado"));
/* 326 */         o.setIdUser(rs.getInt("idUser"));
/* 327 */         o.setRegla(rs.getString("regla"));
/* 328 */         o.setCliente(rs.getString("cliente"));
/* 329 */         o.setVisible(rs.getString("visible"));
/* 330 */         mercados.add(o);
/*     */       } 
/* 332 */       rs.close();
/* 333 */       stmt.close();
/* 334 */       db.conn.close();
/*     */     }
/* 336 */     catch (SQLException e) {
/*     */       
/* 338 */       System.out.println("Error: " + e.getMessage());
/* 339 */       System.out.println("sql: " + sql);
/* 340 */       throw new Exception("getMercado: " + e.getMessage());
/*     */     } finally {
/* 342 */       db.close();
/*     */     } 
/*     */     
/* 345 */     return mercados;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean insertMercado(Mercado mercado) throws ParseException {
/* 350 */     ConnectionDB db = new ConnectionDB();
/* 351 */     Statement stmt = null;
/* 352 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/* 353 */     boolean resp = true;
/* 354 */     String sql = "";
/*     */     
/*     */     try {
/* 357 */       sql = "INSERT INTO mercado(idUser,mercado,mercado2,pf,regla,cliente,idMercadoPadre,porcentaje,creado,modificado,visible) Values ('" + mercado.getIdUser() + "','" + mercado.getMercado() + "','" + mercado.getMercado2() + "','" + mercado.getPf() + "','" + mercado.getRegla() + "','" + mercado.getCliente() + "','" + mercado.getIdMercadoPadre() + "','" + mercado.getPorcentaje() + "','" + d + "','" + d + "','" + mercado.getVisible() + "')";
/* 358 */       stmt = db.conn.createStatement();
/* 359 */       resp = stmt.execute(sql);
/* 360 */       stmt.close();
/* 361 */       TemporadaDB.setCreateRestriciones();
/*     */     }
/* 363 */     catch (Exception ex) {
/*     */       
/* 365 */       System.out.println(ex.getMessage());
/*     */     } finally {
/*     */       
/* 368 */       db.close();
/*     */     } 
/* 370 */     return resp;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Mercado getMercadoByName(String mercado) {
/* 375 */     ConnectionDB db = new ConnectionDB();
/* 376 */     Mercado o = null;
/* 377 */     Statement stmt = null;
/* 378 */     String sql = "";
/*     */     try {
/* 380 */       sql = "Select * from vw_mercados where lower(mercado)=lower('" + mercado + "')";
/* 381 */       System.out.println(sql);
/* 382 */       stmt = db.conn.createStatement();
/* 383 */       ResultSet rs = stmt.executeQuery(sql);
/* 384 */       if (rs.next()) {
/*     */         
/* 386 */         o = new Mercado();
/* 387 */         o.setIdMercado(rs.getInt("idMercado"));
/* 388 */         o.setMercado(rs.getString("mercado"));
/* 389 */         o.setMercado2(rs.getString("mercado2"));
/* 390 */         o.setPf(rs.getString("pf"));
/* 391 */         o.setRegla(rs.getString("regla"));
/* 392 */         o.setCliente(rs.getString("cliente"));
/*     */         
/* 394 */         o.setIdMercadoPadre(rs.getInt("idMercadoPadre"));
/* 395 */         o.setPorcentaje(rs.getString("porcentaje"));
/*     */         
/* 397 */         o.setProductor(rs.getString("productor"));
/* 398 */         o.setRetricionMolecula(rs.getString("retricionMolecula"));
/* 399 */         o.setMolecula(rs.getInt("molecula"));
/*     */         
/* 401 */         o.setCreado(rs.getDate("creado"));
/* 402 */         o.setModificado(rs.getDate("modificado"));
/* 403 */         o.setIdUser(rs.getInt("idUser"));
/*     */       } 
/* 405 */       rs.close();
/* 406 */       stmt.close();
/* 407 */     } catch (Exception e) {
/*     */       
/* 409 */       System.out.println("getMercadoByName: " + e.getMessage());
/*     */     } 
/* 411 */     return o;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Mercado getMercadoByPf(String mercado) {
/* 416 */     ConnectionDB db = new ConnectionDB();
/* 417 */     Mercado o = null;
/* 418 */     Statement stmt = null;
/* 419 */     String sql = "";
/*     */     try {
/* 421 */       sql = "Select * from vw_mercados where lower(pf)=lower('" + mercado + "')";
/* 422 */       System.out.println(sql);
/* 423 */       stmt = db.conn.createStatement();
/* 424 */       ResultSet rs = stmt.executeQuery(sql);
/* 425 */       if (rs.next()) {
/*     */         
/* 427 */         o = new Mercado();
/* 428 */         o.setIdMercado(rs.getInt("idMercado"));
/* 429 */         o.setMercado(rs.getString("mercado"));
/* 430 */         o.setMercado2(rs.getString("mercado2"));
/* 431 */         o.setPf(rs.getString("pf"));
/* 432 */         o.setRegla(rs.getString("regla"));
/* 433 */         o.setCliente(rs.getString("cliente"));
/*     */         
/* 435 */         o.setIdMercadoPadre(rs.getInt("idMercadoPadre"));
/* 436 */         o.setPorcentaje(rs.getString("porcentaje"));
/*     */         
/* 438 */         o.setProductor(rs.getString("productor"));
/* 439 */         o.setRetricionMolecula(rs.getString("retricionMolecula"));
/* 440 */         o.setMolecula(rs.getInt("molecula"));
/*     */         
/* 442 */         o.setCreado(rs.getDate("creado"));
/* 443 */         o.setModificado(rs.getDate("modificado"));
/* 444 */         o.setIdUser(rs.getInt("idUser"));
/*     */       } 
/* 446 */       rs.close();
/* 447 */       stmt.close();
/* 448 */     } catch (Exception e) {
/*     */       
/* 450 */       System.out.println("getMercadoByName: " + e.getMessage());
/*     */     } 
/* 452 */     return o;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\MercadoDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */