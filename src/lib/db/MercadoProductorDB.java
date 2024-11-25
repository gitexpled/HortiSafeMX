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
/*     */ import lib.struc.MercadoProductor;
/*     */ import lib.struc.filterSql;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MercadoProductorDB
/*     */ {
/*     */   public static MercadoProductor get(String id) throws Exception {
/*  25 */     MercadoProductor o = null;
/*  26 */     ConnectionDB db = new ConnectionDB();
/*  27 */     Statement stmt = null;
/*  28 */     String sql = "";
/*     */     
/*     */     try {
/*  31 */       stmt = db.conn.createStatement();
/*     */       
/*  33 */       sql = "SELECT * FROM mercadoProductor where id='" + id + "'";
/*  34 */       System.out.println("sql: " + sql);
/*     */       
/*  36 */       ResultSet rs = stmt.executeQuery(sql);
/*  37 */       if (rs.next()) {
/*  38 */         o = new MercadoProductor();
/*  39 */         o.setId(rs.getInt("id"));
/*  40 */         o.setCodProductor(rs.getString("codProductor"));
/*  41 */         o.setCodParcela(rs.getString("codParcela"));
/*  42 */         o.setIdVariedad(rs.getString("idVariedad"));
/*  43 */         o.setIdMercado(rs.getString("idMercado"));
/*  44 */         o.setCreado(rs.getDate("creado"));
/*  45 */         o.setModificado(rs.getDate("modificado"));
/*  46 */         o.setIdUser(rs.getInt("idUser"));
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
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
/*  63 */       throw new Exception("getUser: " + e.getMessage());
/*     */     } finally {
/*  65 */       db.close();
/*     */     } 
/*     */     
/*  68 */     return o;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update(MercadoProductor o) throws Exception {
/*  73 */     PreparedStatement ps = null;
/*  74 */     String sql = "";
/*  75 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/*  76 */     ConnectionDB db = new ConnectionDB();
/*     */     try {
/*  78 */       db.conn.setAutoCommit(false);
/*     */       
/*  80 */       sql = "update  mercadoProductor set codParcela=?,idVariedad=?, modificado='" + d + 
/*  81 */         "' where id='" + o.getId() + "'";
/*  82 */       System.out.println("sql: " + sql);
/*  83 */       ps = db.conn.prepareStatement(sql);
/*  84 */       ps.setString(1, o.getCodParcela());
/*  85 */       ps.setString(2, o.getIdVariedad());
/*     */ 
/*     */ 
/*     */       
/*  89 */       ps.executeUpdate();
/*  90 */       db.conn.commit();
/*  91 */       db.conn.close();
/*     */     }
/*  93 */     catch (SQLException e) {
/*     */       
/*  95 */       System.out.println("Error: " + e.getMessage());
/*  96 */       System.out.println("sql: " + sql);
/*  97 */       throw new Exception("sepPfx: " + e.getMessage());
/*  98 */     } catch (Exception e) {
/*  99 */       e.printStackTrace();
/* 100 */       System.out.println("Error2: " + e.getMessage());
/* 101 */       throw new Exception("sepPfx: " + e.getMessage());
/*     */     } finally {
/*     */       
/* 104 */       db.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void delete(String id) throws Exception {
/* 112 */     PreparedStatement ps = null;
/* 113 */     String sql = "";
/* 114 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/* 115 */     ConnectionDB db = new ConnectionDB();
/*     */     try {
/* 117 */       db.conn.setAutoCommit(false);
/*     */       
/* 119 */       sql = "delete  from  mercadoProductor where id=?";
/* 120 */       System.out.println("sql: " + sql);
/* 121 */       ps = db.conn.prepareStatement(sql);
/* 122 */       ps.setString(1, id);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 127 */       ps.executeUpdate();
/* 128 */       db.conn.commit();
/* 129 */       db.conn.close();
/*     */     }
/* 131 */     catch (SQLException e) {
/*     */       
/* 133 */       System.out.println("Error: " + e.getMessage());
/* 134 */       System.out.println("sql: " + sql);
/* 135 */       throw new Exception("sepPfx: " + e.getMessage());
/* 136 */     } catch (Exception e) {
/* 137 */       e.printStackTrace();
/* 138 */       System.out.println("Error2: " + e.getMessage());
/* 139 */       throw new Exception("sepPfx: " + e.getMessage());
/*     */     } finally {
/*     */       
/* 142 */       db.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getAll(ArrayList<filterSql> filter) throws Exception {
/* 152 */     int total = 0;
/* 153 */     Statement stmt = null;
/* 154 */     String sql = "";
/* 155 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 158 */       stmt = db.conn.createStatement();
/*     */       
/* 160 */       sql = "SELECT count(1) FROM mercadoProductor pv  left join especie e ON (e.idEspecie = pv.idEspecie)   left join mercado m on (m.idMercado=pv.idMercado)";
/*     */ 
/*     */ 
/*     */       
/* 164 */       if (filter.size() > 0) {
/* 165 */         String andSql = "";
/* 166 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 167 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 169 */         while (f.hasNext()) {
/* 170 */           filterSql row = f.next();
/* 171 */           if (!row.getValue().equals("")) {
/* 172 */             if (row.getCampo().endsWith("_to")) {
/* 173 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 174 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 175 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + 
/* 176 */                 sqlDate.format(formatter.parse(row.getValue())) + "'";
/* 177 */             } else if (row.getCampo().endsWith("_from")) {
/* 178 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 179 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 180 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + 
/* 181 */                 sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             
/*     */             }
/* 184 */             else if (row.getCampo().equals("cod")) {
/* 185 */               sql = String.valueOf(sql) + andSql + "v." + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 186 */             } else if (row.getCampo().equals("mercado")) {
/* 187 */               sql = String.valueOf(sql) + andSql + "m." + row.getCampo() + " like '%" + row.getValue() + "%'";
/*     */             } else {
/* 189 */               sql = String.valueOf(sql) + andSql + "pv." + row.getCampo() + " like '%" + row.getValue() + "%'";
/*     */             } 
/* 191 */             andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 197 */       ResultSet rs = stmt.executeQuery(sql);
/* 198 */       while (rs.next()) {
/* 199 */         total = rs.getInt(1);
/*     */       }
/* 201 */       rs.close();
/* 202 */       stmt.close();
/* 203 */       db.conn.close();
/*     */     }
/* 205 */     catch (SQLException e) {
/*     */       
/* 207 */       System.out.println("Error: " + e.getMessage());
/* 208 */       System.out.println("sql: " + sql);
/* 209 */       throw new Exception("getUsersAll: " + e.getMessage());
/*     */     } finally {
/* 211 */       db.close();
/*     */     } 
/*     */     
/* 214 */     return total;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ArrayList<MercadoProductor> getAll(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 219 */     ArrayList<MercadoProductor> arrays = new ArrayList<>();
/* 220 */     Statement stmt = null;
/* 221 */     String sql = "";
/* 222 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 225 */       stmt = db.conn.createStatement();
/*     */       
/* 227 */       sql = "SELECT pv.*,e.especie,m.mercado FROM mercadoProductor pv left join especie e ON (e.idEspecie = pv.idEspecie)   left join mercado m on (m.idMercado=pv.idMercado)";
/*     */ 
/*     */ 
/*     */       
/* 231 */       if (filter.size() > 0) {
/* 232 */         String andSql = "";
/* 233 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 234 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 236 */         while (f.hasNext()) {
/* 237 */           filterSql row = f.next();
/*     */           
/* 239 */           if (!row.getValue().equals("")) {
/* 240 */             if (row.getCampo().endsWith("_to")) {
/* 241 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 242 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 243 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + 
/* 244 */                 sqlDate.format(formatter.parse(row.getValue())) + "'";
/* 245 */             } else if (row.getCampo().endsWith("_from")) {
/* 246 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 247 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 248 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + 
/* 249 */                 sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             
/*     */             }
/* 252 */             else if (row.getCampo().equals("cod")) {
/* 253 */               sql = String.valueOf(sql) + andSql + "v." + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 254 */             } else if (row.getCampo().equals("mercado")) {
/* 255 */               sql = String.valueOf(sql) + andSql + "m." + row.getCampo() + " like '%" + row.getValue() + "%'";
/*     */             } else {
/* 257 */               sql = String.valueOf(sql) + andSql + "pv." + row.getCampo() + " like '%" + row.getValue() + "%'";
/*     */             } 
/* 259 */             andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 264 */       if (!order.equals("")) {
/* 265 */         sql = String.valueOf(sql) + " order by ";
/*     */       }
/*     */       
/* 268 */       if (length > 0) {
/* 269 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/* 271 */       System.out.println("sql: " + sql);
/* 272 */       ResultSet rs = stmt.executeQuery(sql);
/* 273 */       while (rs.next()) {
/* 274 */         MercadoProductor row = new MercadoProductor();
/*     */         
/* 276 */         row.setId(rs.getInt("id"));
/* 277 */         row.setCodProductor(rs.getString("codProductor"));
/* 278 */         row.setCodParcela(rs.getString("codParcela"));
/*     */         
/* 280 */         row.setIdVariedad(rs.getString("idEspecie"));
/* 281 */         row.setCodVariedad(rs.getString("especie"));
/* 282 */         row.setMercado(rs.getString("mercado"));
/* 283 */         row.setCreado(rs.getDate("creado"));
/* 284 */         row.setModificado(rs.getDate("modificado"));
/* 285 */         row.setIdUser(rs.getInt("idUser"));
/*     */         
/* 287 */         arrays.add(row);
/*     */       } 
/* 289 */       rs.close();
/* 290 */       stmt.close();
/* 291 */       db.conn.close();
/*     */     }
/* 293 */     catch (SQLException e) {
/*     */       
/* 295 */       System.out.println("Error: " + e.getMessage());
/* 296 */       System.out.println("sql: " + sql);
/* 297 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/* 299 */       db.close();
/*     */     } 
/*     */     
/* 302 */     return arrays;
/*     */   }
/*     */   
/*     */   public static boolean insert(MercadoProductor o) throws ParseException {
/* 306 */     ConnectionDB db = new ConnectionDB();
/* 307 */     Statement stmt = null;
/* 308 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/* 309 */     boolean resp = true;
/* 310 */     String sql = "";
/*     */     
/*     */     try {
/* 313 */       sql = "INSERT INTO mercadoProductor(codProductor,codParcela,idEspecie,idMercado,creado,modificado,idUser) Values ('" + 
/* 314 */         o.getCodProductor() + "','" + o.getCodParcela() + "','" + o.getIdVariedad() + "','" + o.getIdMercado() + "','" + d + "','" + d + "'," + 
/* 315 */         o.getIdUser() + ")";
/* 316 */       stmt = db.conn.createStatement();
/* 317 */       resp = stmt.execute(sql);
/* 318 */       stmt.close();
/*     */     
/*     */     }
/* 321 */     catch (Exception ex) {
/* 322 */       System.out.println(ex.getMessage());
/*     */     } finally {
/* 324 */       db.close();
/*     */     } 
/* 326 */     return resp;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\MercadoProductorDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */