/*     */ package lib.db;
/*     */ 
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import lib.io.ConfigProperties;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class generalDB
/*     */ {
/*  17 */   static ConnectionDB cdb = null;
/*     */   public static String InsertDB(ConnectionDB db, String table, Statement multiQuery) throws Exception {
/*  19 */     PreparedStatement ps = null;
/*  20 */     JSONObject json = new JSONObject();
/*  21 */     ArrayList<String> titulos = new ArrayList<>();
/*  22 */     JSONArray array = new JSONArray();
/*     */     try {
/*  24 */       multiQuery.executeBatch();
/*  25 */       ps = db.conn.prepareStatement("SELECT *FROM " + table + " ORDER BY 1 DESC LIMIT 1");
/*  26 */       ResultSet rs = ps.executeQuery("SELECT *FROM " + table + " ORDER BY 1 DESC LIMIT 1");
/*  27 */       ResultSetMetaData md = rs.getMetaData();
/*  28 */       int count = md.getColumnCount();
/*  29 */       for (int i = 1; i <= count; i++) {
/*  30 */         titulos.add(md.getColumnLabel(i));
/*     */       }
/*  32 */       if (rs.next()) {
/*  33 */         JSONObject ob = new JSONObject();
/*  34 */         for (int j = 0; j < titulos.size(); j++) {
/*  35 */           ob.put(((String)titulos.get(j)).toUpperCase(), (rs.getObject(titulos.get(j)) == null) ? JSONObject.NULL : rs.getObject(titulos.get(j)));
/*     */         }
/*  37 */         array.put(ob);
/*     */       } 
/*  39 */       json.put("data", array);
/*  40 */       json.put("error", 0);
/*  41 */       json.put("mensaje", "Informacion registrada con exito");
/*  42 */     } catch (SQLException ex) {
/*  43 */       json.put("error", 1);
/*  44 */       json.put("mensaje", ex.getMessage());
/*  45 */       cdb.conn.rollback();
/*  46 */       cdb.close();
/*  47 */       cdb = null;
/*  48 */     } catch (Exception ex) {
/*  49 */       json.put("error", 2);
/*  50 */       json.put("mensaje", ex.getMessage());
/*  51 */       cdb.conn.rollback();
/*  52 */       cdb.close();
/*  53 */       cdb = null;
/*     */     } finally {
/*     */       try {
/*  56 */         ps.close();
/*  57 */       } catch (Exception ex) {
/*  58 */         System.out.println(ex.getMessage());
/*     */       } 
/*     */     } 
/*     */     
/*  62 */     return json.toString();
/*     */   }
/*     */   public static String UpdateDB(ConnectionDB db, String table, Statement multiQuery) throws Exception {
/*  65 */     JSONObject json = new JSONObject();
/*     */     try {
/*  67 */       multiQuery.executeBatch();
/*  68 */       json.put("error", 0);
/*  69 */       json.put("mensaje", "Informacion actualizada con exito");
/*  70 */     } catch (SQLException ex) {
/*  71 */       json.put("error", 1);
/*  72 */       json.put("mensaje", ex.getMessage());
/*  73 */       json.put("mensaje_2", multiQuery.toString());
/*  74 */       cdb.close();
/*  75 */       cdb = null;
/*  76 */     } catch (Exception ex) {
/*  77 */       json.put("error", 2);
/*  78 */       json.put("mensaje", ex.getMessage());
/*  79 */       cdb.conn.rollback();
/*  80 */       cdb.close();
/*  81 */       cdb = null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  86 */     return json.toString();
/*     */   }
/*     */   public static String DeleteDB(ConnectionDB db, String table, Statement multiQuery) throws Exception {
/*  89 */     JSONObject json = new JSONObject();
/*     */     try {
/*  91 */       multiQuery.executeBatch();
/*  92 */       json.put("error", 0);
/*  93 */       json.put("mensaje", "Informacion actualizada con exito");
/*  94 */     } catch (SQLException ex) {
/*  95 */       json.put("error", 1);
/*  96 */       json.put("mensaje", ex.getMessage());
/*  97 */       json.put("mensaje_2", multiQuery.toString());
/*  98 */       cdb.close();
/*  99 */       cdb = null;
/* 100 */     } catch (Exception ex) {
/* 101 */       json.put("error", 2);
/* 102 */       json.put("mensaje", ex.getMessage());
/* 103 */       cdb.conn.rollback();
/* 104 */       cdb.close();
/* 105 */       cdb = null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 110 */     return json.toString();
/*     */   }
/*     */   public static String getColumnName(String table) throws Exception {
/* 113 */     PreparedStatement ps = null;
/* 114 */     ConnectionDB db = new ConnectionDB();
/* 115 */     JSONObject json = new JSONObject();
/* 116 */     JSONArray columns = new JSONArray();
/*     */     try {
/* 118 */       String getTable = "SELECT *FROM " + table + " LIMIT 1";
/* 119 */       ps = db.conn.prepareStatement(getTable);
/* 120 */       ResultSet gt = ps.executeQuery(getTable);
/* 121 */       ResultSetMetaData mdgt = gt.getMetaData();
/* 122 */       int cmdgt = mdgt.getColumnCount();
/* 123 */       for (int i = 1; i <= cmdgt; i++) {
/* 124 */         JSONObject e = new JSONObject();
/* 125 */         e.put("column", mdgt.getColumnLabel(i));
/* 126 */         columns.put(e);
/*     */       } 
/* 128 */       json.put("error", 0);
/* 129 */       json.put("column", columns);
/* 130 */     } catch (SQLException ex) {
/* 131 */       json.put("error", 1);
/* 132 */       json.put("mensaje", ex.getMessage());
/* 133 */     } catch (Exception ex) {
/* 134 */       json.put("error", 2);
/* 135 */       json.put("mensaje", ex.getMessage());
/*     */     } finally {
/* 137 */       ps.close();
/* 138 */       db.close();
/*     */     } 
/* 140 */     return json.toString();
/*     */   }
/*     */   public static String Insert(String row) throws Exception {
/* 143 */     String r = "";
/*     */     try {
/* 145 */       row = new String(row.getBytes("ISO-8859-1"), "UTF-8");
/* 146 */       if (cdb == null) {
/* 147 */         cdb = new ConnectionDB();
/*     */       }
/* 149 */       cdb.conn.setAutoCommit(false);
/* 150 */       boolean com = true;
/* 151 */       JSONObject json = new JSONObject(row);
/* 152 */       if (json.has("COMMIT") && 
/* 153 */         !json.optBoolean("COMMIT")) {
/* 154 */         com = false;
/*     */       }
/*     */       
/* 157 */       JSONObject object = new JSONObject(row);
/* 158 */       String resGetColumn = getColumnName(json.getString("TABLE"));
/* 159 */       JSONObject jGetColumn = new JSONObject(resGetColumn);
/* 160 */       if (jGetColumn.getInt("error") != 0) {
/* 161 */         return resGetColumn;
/*     */       }
/* 163 */       JSONArray columnName = jGetColumn.getJSONArray("column");
/* 164 */       ArrayList<String> tColumnName = new ArrayList<>();
/* 165 */       for (int i = 0; i < columnName.length(); i++) {
/* 166 */         JSONObject e = columnName.getJSONObject(i);
/* 167 */         tColumnName.add(e.getString("column"));
/*     */       } 
/* 169 */       JSONArray item = null;
/* 170 */       if (object.optJSONArray("VALUES") != null) {
/* 171 */         item = object.getJSONArray("VALUES");
/*     */       } else {
/* 173 */         JSONObject aux = object.getJSONObject("VALUES");
/* 174 */         item = new JSONArray();
/* 175 */         item.put(aux);
/*     */       } 
/* 177 */       json.put("TABLE", object.get("TABLE"));
/* 178 */       Statement multiQuery = null;
/* 179 */       multiQuery = cdb.conn.createStatement();
/* 180 */       for (int j = 0; j < item.length(); j++) {
/* 181 */         JSONObject e = new JSONObject(item.getJSONObject(j).toString());
/* 182 */         json.put("VALUES", e);
/* 183 */         String ColName = "";
/* 184 */         String fill = "";
/* 185 */         int c = 0;
/* 186 */         for (int x = 0; x < tColumnName.size(); x++) {
/* 187 */           if (e.has(tColumnName.get(x))) {
/* 188 */             ColName = String.valueOf(ColName) + ((c == 0) ? tColumnName.get(x) : (", " + (String)tColumnName.get(x)));
/* 189 */             fill = String.valueOf(fill) + ((c == 0) ? ("'" + e.get(tColumnName.get(x)) + "'") : (", '" + e.get(tColumnName.get(x)) + "'"));
/* 190 */             c++;
/*     */           } 
/*     */         } 
/* 193 */         String sql = "REPLACE INTO " + json.getString("TABLE") + " (" + ColName + ") VALUES (" + fill + ")";
/* 194 */         System.out.println(sql);
/* 195 */         multiQuery.addBatch(sql);
/*     */       } 
/* 197 */       r = InsertDB(cdb, json.getString("TABLE"), multiQuery);
/* 198 */       JSONObject re = new JSONObject(r);
/* 199 */       if (re.getInt("error") == 0 && com) {
/* 200 */         cdb.conn.commit();
/* 201 */         cdb.close();
/* 202 */         cdb = null;
/*     */       }
/*     */     
/* 205 */     } catch (Exception ex) {
/* 206 */       JSONObject res = new JSONObject();
/* 207 */       res.put("error", 500);
/* 208 */       res.put("mensaje", ex.getMessage());
/* 209 */       cdb.close();
/* 210 */       cdb = null;
/* 211 */       r = res.toString();
/*     */     } 
/* 213 */     return r;
/*     */   }
/*     */   public static String Update(String row) throws Exception {
/* 216 */     String r = "";
/*     */     try {
/* 218 */       if (cdb == null) {
/* 219 */         cdb = new ConnectionDB();
/*     */       }
/* 221 */       cdb.conn.setAutoCommit(false);
/* 222 */       boolean com = true;
/* 223 */       JSONObject json = new JSONObject(row);
/* 224 */       if (json.has("COMMIT") && 
/* 225 */         !json.optBoolean("COMMIT")) {
/* 226 */         com = false;
/*     */       }
/*     */       
/* 229 */       String resGetColumn = getColumnName(json.getString("TABLE"));
/* 230 */       JSONObject jGetColumn = new JSONObject(resGetColumn);
/* 231 */       if (jGetColumn.getInt("error") != 0) {
/* 232 */         return resGetColumn;
/*     */       }
/* 234 */       JSONArray columnName = jGetColumn.getJSONArray("column");
/* 235 */       ArrayList<String> tColumnName = new ArrayList<>();
/* 236 */       for (int i = 0; i < columnName.length(); i++) {
/* 237 */         JSONObject e = columnName.getJSONObject(i);
/* 238 */         tColumnName.add(e.getString("column"));
/*     */       } 
/* 240 */       JSONArray item = null;
/* 241 */       if (json.optJSONArray("UPDATES") != null) {
/* 242 */         item = json.getJSONArray("UPDATES");
/*     */       } else {
/* 244 */         JSONObject set = new JSONObject();
/* 245 */         set.put("SET", json.getJSONObject("SET"));
/* 246 */         set.put("WHERE", json.getJSONObject("WHERE"));
/* 247 */         item = new JSONArray();
/* 248 */         item.put(set);
/*     */       } 
/* 250 */       Statement multiQuery = cdb.conn.createStatement();
/* 251 */       for (int j = 0; j < item.length(); j++) {
/* 252 */         JSONObject e = new JSONObject(item.getJSONObject(j).toString());
/* 253 */         JSONObject set = e.getJSONObject("SET");
/* 254 */         JSONObject where = e.getJSONObject("WHERE");
/* 255 */         String updSql = "";
/* 256 */         String sqlWhere = "";
/* 257 */         int cSet = 0;
/* 258 */         int cWhere = 0;
/* 259 */         for (int x = 0; x < tColumnName.size(); x++) {
/* 260 */           if (e.getJSONObject("SET").has(tColumnName.get(x))) {
/* 261 */             Object setValue = null;
/* 262 */             if (!set.get(tColumnName.get(x)).toString().equals("null")) {
/* 263 */               setValue = "'" + set.get(tColumnName.get(x)) + "'";
/*     */             }
/* 265 */             updSql = String.valueOf(updSql) + ((cSet == 0) ? (String.valueOf(tColumnName.get(x)) + " = " + setValue) : (", " + (String)tColumnName.get(x) + " = " + setValue));
/* 266 */             cSet++;
/*     */           } 
/* 268 */           if (e.getJSONObject("WHERE").has(tColumnName.get(x))) {
/* 269 */             sqlWhere = String.valueOf(sqlWhere) + ((cWhere == 0) ? (String.valueOf(tColumnName.get(x)) + " = '" + where.get(tColumnName.get(x)) + "'") : (" AND " + (String)tColumnName.get(x) + " = '" + where.get(tColumnName.get(x)) + "'"));
/* 270 */             cWhere++;
/*     */           } 
/*     */         } 
/* 273 */         String sql = "UPDATE " + json.getString("TABLE") + " SET " + updSql + " WHERE " + sqlWhere;
/* 274 */         System.out.println(sql);
/* 275 */         multiQuery.addBatch(sql);
/*     */       } 
/* 277 */       r = UpdateDB(cdb, json.getString("TABLE"), multiQuery);
/* 278 */       JSONObject re = new JSONObject(r);
/* 279 */       if (re.getInt("error") == 0 && com) {
/* 280 */         cdb.conn.commit();
/* 281 */         cdb.close();
/* 282 */         cdb = null;
/*     */       } 
/* 284 */       return r;
/*     */     }
/* 286 */     catch (Exception ex) {
/* 287 */       JSONObject res = new JSONObject();
/* 288 */       res.put("error", 500);
/* 289 */       res.put("mensaje", ex.getMessage());
/* 290 */       cdb.close();
/* 291 */       cdb = null;
/* 292 */       r = res.toString();
/*     */       
/* 294 */       return r;
/*     */     } 
/*     */   } public static String Delete(String row) throws Exception {
/* 297 */     String r = "";
/*     */     try {
/* 299 */       if (cdb == null) {
/* 300 */         cdb = new ConnectionDB();
/*     */       }
/* 302 */       cdb.conn.setAutoCommit(false);
/* 303 */       boolean com = true;
/* 304 */       JSONObject json = new JSONObject(row);
/* 305 */       if (json.has("COMMIT") && 
/* 306 */         !json.optBoolean("COMMIT")) {
/* 307 */         com = false;
/*     */       }
/*     */       
/* 310 */       String resGetColumn = getColumnName(json.getString("TABLE"));
/* 311 */       JSONObject jGetColumn = new JSONObject(resGetColumn);
/* 312 */       if (jGetColumn.getInt("error") != 0) {
/* 313 */         return resGetColumn;
/*     */       }
/* 315 */       JSONArray columnName = jGetColumn.getJSONArray("column");
/* 316 */       ArrayList<String> tColumnName = new ArrayList<>();
/* 317 */       for (int i = 0; i < columnName.length(); i++) {
/* 318 */         JSONObject e = columnName.getJSONObject(i);
/* 319 */         tColumnName.add(e.getString("column"));
/*     */       } 
/* 321 */       JSONArray item = null;
/* 322 */       if (json.optJSONArray("DELETE") != null) {
/* 323 */         item = json.getJSONArray("DELETE");
/*     */       } else {
/* 325 */         JSONObject set = new JSONObject();
/* 326 */         set.put("WHERE", json.getJSONObject("WHERE"));
/* 327 */         item = new JSONArray();
/* 328 */         item.put(set);
/*     */       } 
/* 330 */       Statement multiQuery = cdb.conn.createStatement();
/* 331 */       for (int j = 0; j < item.length(); j++) {
/* 332 */         JSONObject e = new JSONObject(item.getJSONObject(j).toString());
/* 333 */         JSONObject where = e.getJSONObject("WHERE");
/* 334 */         String sqlWhere = "";
/* 335 */         int cWhere = 0;
/* 336 */         for (int x = 0; x < tColumnName.size(); x++) {
/* 337 */           if (e.getJSONObject("WHERE").has(tColumnName.get(x))) {
/* 338 */             sqlWhere = String.valueOf(sqlWhere) + ((cWhere == 0) ? (String.valueOf(tColumnName.get(x)) + " = '" + where.get(tColumnName.get(x)) + "'") : (" AND " + (String)tColumnName.get(x) + " = '" + where.get(tColumnName.get(x)) + "'"));
/* 339 */             cWhere++;
/*     */           } 
/*     */         } 
/* 342 */         String sql = "DELETE FROM " + json.getString("TABLE") + " WHERE " + sqlWhere;
/* 343 */         System.out.println(sql);
/* 344 */         multiQuery.addBatch(sql);
/*     */       } 
/* 346 */       r = DeleteDB(cdb, json.getString("TABLE"), multiQuery);
/* 347 */       JSONObject re = new JSONObject(r);
/* 348 */       if (re.getInt("error") == 0 && com) {
/* 349 */         cdb.conn.commit();
/* 350 */         cdb.close();
/* 351 */         cdb = null;
/*     */       }
/*     */     
/* 354 */     } catch (Exception ex) {
/* 355 */       JSONObject res = new JSONObject();
/* 356 */       res.put("error", 500);
/* 357 */       res.put("mensaje", ex.getMessage());
/* 358 */       cdb.close();
/* 359 */       cdb = null;
/* 360 */       r = res.toString();
/*     */     } 
/* 362 */     return r;
/*     */   }
/*     */   
/*     */   public static String Select(String row) throws Exception {
/* 366 */     PreparedStatement ps = null;
/* 367 */     ConnectionDB db = new ConnectionDB();
/* 368 */     JSONObject json = new JSONObject(row);
/* 369 */     System.out.println(json);
/* 370 */     String values = "*";
/* 371 */     String where = " ";
/* 372 */     String sql = "SELECT ";
/* 373 */     ArrayList<String> titulos = new ArrayList<>();
/* 374 */     JSONArray array = new JSONArray();
/*     */     try {
/* 376 */       String resGetColumn = getColumnName(json.getString("TABLE"));
/* 377 */       JSONObject jGetColumn = new JSONObject(resGetColumn);
/* 378 */       JSONArray columnName = jGetColumn.getJSONArray("column");
/* 379 */       ArrayList<String> tColumnName = new ArrayList<>();
/* 380 */       for (int i = 0; i < columnName.length(); i++) {
/* 381 */         JSONObject e = columnName.getJSONObject(i);
/* 382 */         tColumnName.add(e.getString("column"));
/*     */       } 
/* 384 */       if (json.has("COLUMN")) {
/* 385 */         values = "";
/* 386 */         JSONArray e = new JSONArray(json.get("COLUMN").toString());
/* 387 */         for (int x = 0; x < e.length(); x++) {
/* 388 */           values = String.valueOf(values) + ((x == 0) ? (String)e.get(x) : (", " + e.get(x)));
/*     */         }
/*     */       } 
/* 391 */       sql = String.valueOf(sql) + values + " FROM " + json.getString("TABLE");
/* 392 */       if (json.has("WHERE")) {
/* 393 */         where = " WHERE ";
/* 394 */         JSONObject e = json.getJSONObject("WHERE");
/* 395 */         int cWhere = 0;
/* 396 */         for (int x = 0; x < tColumnName.size(); x++) {
/* 397 */           if (e.has(tColumnName.get(x))) {
/* 398 */             where = String.valueOf(where) + ((cWhere == 0) ? "" : " AND ");
/* 399 */             if (e.optJSONObject(tColumnName.get(x)) != null) {
/* 400 */               JSONObject w = e.getJSONObject(tColumnName.get(x));
/* 401 */               if (w.has("BETWEEN")) {
/* 402 */                 JSONArray aw = w.getJSONArray("BETWEEN");
/* 403 */                 where = String.valueOf(where) + (String)tColumnName.get(x) + " BETWEEN ";
/* 404 */                 for (int k = 0; k < aw.length(); k++) {
/* 405 */                   where = String.valueOf(where) + ((k == 0) ? ("'" + aw.get(k)) : ("' AND '" + aw.get(k) + "'"));
/*     */                 }
/* 407 */               } else if (w.has("IN")) {
/* 408 */                 JSONArray aw = w.getJSONArray("IN");
/* 409 */                 where = String.valueOf(where) + (String)tColumnName.get(x) + " IN (";
/* 410 */                 for (int k = 0; k < aw.length(); k++) {
/* 411 */                   where = String.valueOf(where) + ((k == 0) ? ("'" + aw.get(k) + "'") : (" ,'" + aw.get(k) + "'"));
/*     */                 }
/* 413 */                 where = String.valueOf(where) + ") ";
/* 414 */               } else if (w.has("NOTIN")) {
/* 415 */                 JSONArray aw = w.getJSONArray("NOTIN");
/* 416 */                 where = String.valueOf(where) + (String)tColumnName.get(x) + " NOT IN (";
/* 417 */                 for (int k = 0; k < aw.length(); k++) {
/* 418 */                   where = String.valueOf(where) + ((k == 0) ? ("'" + aw.get(k) + "'") : (" ,'" + aw.get(k) + "'"));
/*     */                 }
/* 420 */                 where = String.valueOf(where) + ") ";
/*     */               } 
/*     */             } else {
/* 423 */               where = String.valueOf(where) + (String)tColumnName.get(x) + " = '" + e.get(tColumnName.get(x)) + "'";
/*     */             } 
/* 425 */             cWhere++;
/*     */           } 
/*     */         } 
/*     */       } 
/* 429 */       sql = String.valueOf(sql) + where;
/* 430 */       ps = db.conn.prepareStatement(sql);
/* 431 */       System.out.println(sql);
/* 432 */       ResultSet rs = ps.executeQuery(sql);
/* 433 */       ResultSetMetaData md = rs.getMetaData();
/* 434 */       int count = md.getColumnCount();
/* 435 */       for (int j = 1; j <= count; j++) {
/* 436 */         titulos.add(md.getColumnLabel(j));
/*     */       }
/* 438 */       while (rs.next()) {
/* 439 */         JSONObject ob = new JSONObject();
/* 440 */         for (int k = 0; k < titulos.size(); k++) {
/* 441 */           ob.put(titulos.get(k), (rs.getObject(titulos.get(k)) == null) ? JSONObject.NULL : rs.getObject(titulos.get(k)));
/*     */         }
/* 443 */         array.put(ob);
/*     */       } 
/* 445 */       json.put("data", array);
/* 446 */       json.put("error", 0);
/* 447 */       json.put("mensaje", "Ok");
/*     */     }
/* 449 */     catch (SQLException ex) {
/* 450 */       json.put("data", array);
/* 451 */       json.put("error", 2);
/* 452 */       json.put("sql", sql);
/* 453 */       json.put("mensaje", ex.getMessage());
/* 454 */     } catch (Exception ex) {
/* 455 */       json.put("data", array);
/* 456 */       json.put("error", 1);
/* 457 */       json.put("mensaje", ex.getMessage());
/* 458 */       json.put("line", ex.getStackTrace()[1].getLineNumber());
/*     */     } finally {
/*     */       
/* 461 */       db.close();
/*     */     } 
/* 463 */     return json.toString();
/*     */   }
/*     */   public static String EXECSP(String row) throws Exception {
/* 466 */     PreparedStatement ps = null;
/* 467 */     String sql = "";
/* 468 */     JSONObject object = new JSONObject(row);
/* 469 */     JSONArray array = new JSONArray();
/* 470 */     JSONObject data = new JSONObject();
/* 471 */     ConnectionDB db = new ConnectionDB();
/* 472 */     ArrayList<String> titulos = new ArrayList<>();
/*     */     try {
/* 474 */       String fill = "";
/* 475 */       if (object.optJSONArray("FILTERS") != null) {
/* 476 */         JSONArray filters = new JSONArray(object.get("FILTERS").toString());
/* 477 */         int c = 0;
/* 478 */         for (int ix = 0; ix < filters.length(); ix++) {
/* 479 */           JSONObject e = new JSONObject(filters.getJSONObject(ix).toString());
/* 480 */           c++;
/* 481 */           fill = String.valueOf(fill) + ((c != filters.length()) ? ("'" + e.get("value") + "',") : ("'" + e.get("value") + "'"));
/*     */         } 
/*     */       } 
/* 484 */       sql = "CALL " + object.getString("SP") + "(" + fill + ")";
/* 485 */       System.out.println(sql);
/* 486 */       ps = db.conn.prepareStatement(sql);
/* 487 */       ResultSet rs = ps.executeQuery(sql);
/* 488 */       ResultSetMetaData md = rs.getMetaData();
/* 489 */       int count = md.getColumnCount();
/* 490 */       for (int i = 1; i <= count; i++) {
/* 491 */         titulos.add(md.getColumnLabel(i));
/*     */       }
/* 493 */       while (rs.next()) {
/* 494 */         JSONObject ob = new JSONObject();
/* 495 */         for (int j = 0; j < titulos.size(); j++) {
/* 496 */           ob.put(titulos.get(j), (rs.getObject(titulos.get(j)) == null) ? JSONObject.NULL : rs.getObject(titulos.get(j)));
/*     */         }
/* 498 */         array.put(ob);
/*     */       } 
/* 500 */       data.put("data", array);
/* 501 */       data.put("message", "Ok");
/* 502 */       data.put("error", 0);
/* 503 */     } catch (SQLException e) {
/* 504 */       data.put("data", array);
/* 505 */       data.put("message", e.getMessage());
/* 506 */       data.put("error", 1);
/* 507 */     } catch (Exception e) {
/* 508 */       data.put("data", array);
/* 509 */       data.put("message", e.getMessage());
/* 510 */       data.put("error", 2);
/*     */     } finally {
/* 512 */       ps.close();
/* 513 */       db.close();
/*     */     } 
/* 515 */     return data.toString();
/*     */   }
/*     */   public static String CallSp(String row) throws Exception {
/* 518 */     PreparedStatement ps = null;
/* 519 */     JSONObject json = new JSONObject(row);
/* 520 */     JSONArray array = new JSONArray();
/* 521 */     JSONObject data = new JSONObject();
/* 522 */     ConnectionDB db = new ConnectionDB();
/* 523 */     ArrayList<String> titulos = new ArrayList<>();
/* 524 */     int opt = 1;
/* 525 */     String sql = "";
/*     */     try {
/* 527 */       db.conn.setAutoCommit(false);
/* 528 */       String resGetColumn = getParameterNameSp(json.getString("SP"));
/* 529 */       JSONObject jGetColumn = new JSONObject(resGetColumn);
/* 530 */       JSONArray paramName = jGetColumn.getJSONArray("parameters");
/* 531 */       ArrayList<String> tParamName = new ArrayList<>();
/* 532 */       for (int i = 0; i < paramName.length(); i++) {
/* 533 */         JSONObject e = paramName.getJSONObject(i);
/* 534 */         tParamName.add(e.getString("parameter"));
/*     */       } 
/* 536 */       Statement multiQuery = null;
/* 537 */       multiQuery = db.conn.createStatement();
/* 538 */       String fill = "";
/* 539 */       if (json.has("FILTERS")) {
/* 540 */         JSONArray item = null;
/* 541 */         if (json.optJSONArray("FILTERS") != null) {
/* 542 */           item = json.getJSONArray("FILTERS");
/* 543 */           opt = 2;
/*     */         } else {
/* 545 */           JSONObject aux = json.getJSONObject("FILTERS");
/* 546 */           item = new JSONArray();
/* 547 */           item.put(aux);
/* 548 */           opt = 1;
/*     */         } 
/* 550 */         for (int j = 0; j < item.length(); j++) {
/* 551 */           JSONObject e = new JSONObject(item.getJSONObject(j).toString());
/* 552 */           int c = 0;
/* 553 */           String fill2 = "";
/* 554 */           for (int x = 0; x < tParamName.size(); x++) {
/* 555 */             if (e.has(tParamName.get(x))) {
/* 556 */               fill2 = String.valueOf(fill2) + ((c == 0) ? ("'" + e.get(tParamName.get(x)) + "'") : (", '" + e.get(tParamName.get(x)) + "'"));
/* 557 */               c++;
/*     */             } 
/*     */           } 
/* 560 */           if (j != item.length()) {
/* 561 */             sql = "CALL " + json.getString("SP") + "(" + fill2 + ")";
/* 562 */             multiQuery.addBatch(sql);
/*     */           } 
/*     */         } 
/*     */       } else {
/* 566 */         sql = "CALL " + json.getString("SP") + "(" + fill + ");";
/*     */       } 
/* 568 */       if (opt == 1) {
/* 569 */         System.out.println(sql);
/* 570 */         ps = db.conn.prepareStatement(sql);
/* 571 */         ResultSet rs = ps.executeQuery(sql);
/*     */         try {
/* 573 */           ResultSetMetaData md = rs.getMetaData();
/* 574 */           int count = md.getColumnCount();
/* 575 */           for (int j = 1; j <= count; j++) {
/* 576 */             titulos.add(md.getColumnLabel(j));
/*     */           }
/* 578 */           while (rs.next()) {
/* 579 */             JSONObject ob = new JSONObject();
/* 580 */             for (int k = 0; k < titulos.size(); k++) {
/* 581 */               ob.put(titulos.get(k), (rs.getObject(titulos.get(k)) == null) ? JSONObject.NULL : rs.getObject(titulos.get(k)));
/*     */             }
/* 583 */             array.put(ob);
/*     */           } 
/* 585 */         } catch (Exception exception) {}
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 590 */         multiQuery.executeBatch();
/*     */       } 
/* 592 */       db.conn.commit();
/* 593 */       data.put("data", array);
/* 594 */       data.put("message", "Ok");
/* 595 */       data.put("error", 0);
/* 596 */     } catch (SQLException e) {
/* 597 */       data.put("data", array);
/* 598 */       data.put("message", e.getMessage());
/* 599 */       data.put("error", 1);
/* 600 */     } catch (Exception e) {
/* 601 */       data.put("data", array);
/* 602 */       data.put("message", e.getMessage());
/* 603 */       data.put("message_1", e);
/* 604 */       data.put("message_2", e.getStackTrace());
/* 605 */       data.put("error", 2);
/*     */     } finally {
/*     */       
/* 608 */       db.close();
/*     */     } 
/* 610 */     return data.toString();
/*     */   }
/*     */   public static String getParameterNameSp(String sp) throws Exception {
/* 613 */     PreparedStatement ps = null;
/* 614 */     ConnectionDB db = new ConnectionDB();
/* 615 */     JSONObject json = new JSONObject();
/* 616 */     JSONArray columns = new JSONArray();
/*     */     try {
/* 618 */       String getSp = "SELECT *FROM information_schema.parameters WHERE SPECIFIC_NAME = '" + sp + "' AND SPECIFIC_SCHEMA = '" + ConfigProperties.getProperty("SPECIFIC_SCHEMA") + "';";
/* 619 */       ps = db.conn.prepareStatement(getSp);
/* 620 */       ResultSet rs = ps.executeQuery(getSp);
/* 621 */       while (rs.next()) {
/* 622 */         JSONObject e = new JSONObject();
/* 623 */         e.put("parameter", rs.getString("PARAMETER_NAME"));
/* 624 */         columns.put(e);
/*     */       } 
/* 626 */       json.put("error", 0);
/* 627 */       json.put("parameters", columns);
/* 628 */     } catch (SQLException ex) {
/* 629 */       json.put("error", 1);
/* 630 */       json.put("mensaje", ex.getMessage());
/* 631 */     } catch (Exception ex) {
/* 632 */       json.put("error", 2);
/* 633 */       json.put("mensaje", ex.getMessage());
/*     */     } finally {
/* 635 */       ps.close();
/* 636 */       db.close();
/*     */     } 
/* 638 */     System.out.println(json.toString());
/* 639 */     return json.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\generalDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */