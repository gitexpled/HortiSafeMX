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
/*     */ import lib.struc.ParcelaVariedad;
/*     */ import lib.struc.filterSql;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParcelaVariedadDB
/*     */ {
/*     */   public static ParcelaVariedad get(String id) throws Exception {
/*  24 */     ParcelaVariedad o = null;
/*  25 */     ConnectionDB db = new ConnectionDB();
/*  26 */     Statement stmt = null;
/*  27 */     String sql = "";
/*     */     
/*     */     try {
/*  30 */       stmt = db.conn.createStatement();
/*     */       
/*  32 */       sql = "SELECT * FROM parcelaVariedad where id='" + id + "'";
/*  33 */       System.out.println("sql: " + sql);
/*     */       
/*  35 */       ResultSet rs = stmt.executeQuery(sql);
/*  36 */       if (rs.next()) {
/*  37 */         o = new ParcelaVariedad();
/*  38 */         o.setId(rs.getInt("id"));
/*  39 */         o.setCodParcela(rs.getString("codParcela"));
/*  40 */         o.setIdEspecie(rs.getString("idEspecie"));
/*  41 */         o.setCreado(rs.getDate("creado"));
/*  42 */         o.setModificado(rs.getDate("modificado"));
/*  43 */         o.setIdUser(rs.getInt("idUser"));
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/*  49 */         o = null;
/*     */       } 
/*     */       
/*  52 */       rs.close();
/*  53 */       stmt.close();
/*  54 */       db.conn.close();
/*     */     }
/*  56 */     catch (SQLException e) {
/*     */       
/*  58 */       System.out.println("Error: " + e.getMessage());
/*  59 */       System.out.println("sql: " + sql);
/*  60 */       throw new Exception("getUser: " + e.getMessage());
/*     */     } finally {
/*  62 */       db.close();
/*     */     } 
/*     */     
/*  65 */     return o;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update(ParcelaVariedad o) throws Exception {
/*  70 */     PreparedStatement ps = null;
/*  71 */     String sql = "";
/*  72 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/*  73 */     ConnectionDB db = new ConnectionDB();
/*     */     try {
/*  75 */       db.conn.setAutoCommit(false);
/*     */       
/*  77 */       sql = "update  parcelaVariedad set codParcela=?,idEspecie=?, modificado='" + d + 
/*  78 */         "' where id='" + o.getId() + "'";
/*  79 */       System.out.println("sql: " + sql);
/*  80 */       ps = db.conn.prepareStatement(sql);
/*  81 */       ps.setString(1, o.getCodParcela());
/*  82 */       ps.setString(2, o.getIdEspecie());
/*     */ 
/*     */ 
/*     */       
/*  86 */       ps.executeUpdate();
/*  87 */       db.conn.commit();
/*  88 */       db.conn.close();
/*     */     }
/*  90 */     catch (SQLException e) {
/*     */       
/*  92 */       System.out.println("Error: " + e.getMessage());
/*  93 */       System.out.println("sql: " + sql);
/*  94 */       throw new Exception("sepPfx: " + e.getMessage());
/*  95 */     } catch (Exception e) {
/*  96 */       e.printStackTrace();
/*  97 */       System.out.println("Error2: " + e.getMessage());
/*  98 */       throw new Exception("sepPfx: " + e.getMessage());
/*     */     } finally {
/*     */       
/* 101 */       db.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getBloqueadosHoy() throws Exception {
/* 112 */     int total = 0;
/* 113 */     Statement stmt = null;
/* 114 */     String sql = "";
/* 115 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 118 */       stmt = db.conn.createStatement();
/*     */       
/* 120 */       sql = "select sum(p) from (SELECT count(DISTINCT codParcela) as p FROM restriciones where bloqueado='N' and fecha = sysdate() group by codParcela) as s ";
/*     */ 
/*     */       
/* 123 */       ResultSet rs = stmt.executeQuery(sql);
/* 124 */       while (rs.next()) {
/* 125 */         total = rs.getInt(1);
/*     */       }
/* 127 */       rs.close();
/* 128 */       stmt.close();
/* 129 */       db.conn.close();
/*     */     }
/* 131 */     catch (SQLException e) {
/*     */       
/* 133 */       System.out.println("Error: " + e.getMessage());
/* 134 */       System.out.println("sql: " + sql);
/* 135 */       throw new Exception("getUsersAll: " + e.getMessage());
/*     */     } finally {
/* 137 */       db.close();
/*     */     } 
/*     */     
/* 140 */     return total;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getAll(ArrayList<filterSql> filter) throws Exception {
/* 145 */     int total = 0;
/* 146 */     Statement stmt = null;
/* 147 */     String sql = "";
/* 148 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 151 */       stmt = db.conn.createStatement();
/*     */       
/* 153 */       sql = "SELECT count(1) FROM parcelaVariedad pv left join especie v on (v.idEspecie=pv.idEspecie) ";
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
/* 166 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + 
/* 167 */                 sqlDate.format(formatter.parse(row.getValue())) + "'";
/* 168 */             } else if (row.getCampo().endsWith("_from")) {
/* 169 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 170 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 171 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + 
/* 172 */                 sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             
/*     */             }
/* 175 */             else if (row.getCampo().equals("pf")) {
/* 176 */               sql = String.valueOf(sql) + andSql + "v." + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 177 */             } else if (row.getCampo().equals("codProductor")) {
/* 178 */               sql = String.valueOf(sql) + andSql + "pv." + row.getCampo() + " like '%" + row.getValue() + "%'";
/*     */             } else {
/* 180 */               sql = String.valueOf(sql) + andSql + "pv." + row.getCampo() + " like '%" + row.getValue() + "%'";
/*     */             } 
/* 182 */             andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 188 */       ResultSet rs = stmt.executeQuery(sql);
/* 189 */       while (rs.next()) {
/* 190 */         total = rs.getInt(1);
/*     */       }
/* 192 */       rs.close();
/* 193 */       stmt.close();
/* 194 */       db.conn.close();
/*     */     }
/* 196 */     catch (SQLException e) {
/*     */       
/* 198 */       System.out.println("Error: " + e.getMessage());
/* 199 */       System.out.println("sql: " + sql);
/* 200 */       throw new Exception("getUsersAll: " + e.getMessage());
/*     */     } finally {
/* 202 */       db.close();
/*     */     } 
/*     */     
/* 205 */     return total;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ArrayList<ParcelaVariedad> getAll(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 210 */     ArrayList<ParcelaVariedad> Parcelaes = new ArrayList<>();
/* 211 */     Statement stmt = null;
/* 212 */     String sql = "";
/* 213 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 216 */       stmt = db.conn.createStatement();
/*     */       
/* 218 */       sql = "SELECT pv.*,v.pf FROM parcelaVariedad pv left join especie v on (v.idEspecie=pv.idEspecie) ";
/*     */       
/* 220 */       if (filter.size() > 0) {
/* 221 */         String andSql = "";
/* 222 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 223 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 225 */         while (f.hasNext()) {
/* 226 */           filterSql row = f.next();
/*     */           
/* 228 */           if (!row.getValue().equals("")) {
/* 229 */             if (row.getCampo().endsWith("_to")) {
/* 230 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 231 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 232 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + 
/* 233 */                 sqlDate.format(formatter.parse(row.getValue())) + "'";
/* 234 */             } else if (row.getCampo().endsWith("_from")) {
/* 235 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 236 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 237 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + 
/* 238 */                 sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             
/*     */             }
/* 241 */             else if (row.getCampo().equals("pf")) {
/* 242 */               sql = String.valueOf(sql) + andSql + "v." + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 243 */             } else if (row.getCampo().equals("codProductor")) {
/* 244 */               sql = String.valueOf(sql) + andSql + "pv." + row.getCampo() + " like '%" + row.getValue() + "%'";
/*     */             } else {
/* 246 */               sql = String.valueOf(sql) + andSql + "pv." + row.getCampo() + " like '%" + row.getValue() + "%'";
/*     */             } 
/* 248 */             andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 253 */       if (!order.equals("")) {
/* 254 */         sql = String.valueOf(sql) + " order by ";
/*     */       }
/*     */       
/* 257 */       if (length > 0) {
/* 258 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/* 260 */       ResultSet rs = stmt.executeQuery(sql);
/* 261 */       while (rs.next()) {
/* 262 */         ParcelaVariedad row = new ParcelaVariedad();
/*     */         
/* 264 */         row.setId(rs.getInt("id"));
/* 265 */         row.setCodProductor(rs.getString("codProductor"));
/* 266 */         row.setCodParcela(rs.getString("codParcela"));
/* 267 */         row.setCodTurno(rs.getString("codTurno"));
/* 268 */         row.setIdEspecie(rs.getString("idEspecie"));
/* 269 */         row.setCodEspecie(rs.getString("pf"));
/* 270 */         row.setCreado(rs.getDate("creado"));
/* 271 */         row.setModificado(rs.getDate("modificado"));
/* 272 */         row.setIdUser(rs.getInt("idUser"));
/*     */         
/* 274 */         Parcelaes.add(row);
/*     */       } 
/* 276 */       rs.close();
/* 277 */       stmt.close();
/* 278 */       db.conn.close();
/*     */     }
/* 280 */     catch (SQLException e) {
/*     */       
/* 282 */       System.out.println("Error: " + e.getMessage());
/* 283 */       System.out.println("sql: " + sql);
/* 284 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/* 286 */       db.close();
/*     */     } 
/*     */     
/* 289 */     return Parcelaes;
/*     */   }
/*     */   
/*     */   public static boolean insert(ParcelaVariedad o) throws ParseException {
/* 293 */     ConnectionDB db = new ConnectionDB();
/* 294 */     Statement stmt = null;
/* 295 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/* 296 */     boolean resp = true;
/* 297 */     String sql = "";
/*     */     
/*     */     try {
/* 300 */       sql = "INSERT INTO parcelaVariedad(codParcela,codTurno,idEspecie,creado,modificado,idUser,codProductor) Values ('" + 
/* 301 */         o.getCodParcela() + "','" + o.getCodTurno() + "','" + o.getIdEspecie() + "','" + d + "','" + d + "'," + 
/* 302 */         o.getIdUser() + ",'" + o.getCodProductor() + "')";
/* 303 */       stmt = db.conn.createStatement();
/* 304 */       resp = stmt.execute(sql);
/* 305 */       stmt.close();
/* 306 */       //TemporadaDB.setCreateRestriciones();
/*     */     }
/* 308 */     catch (Exception ex) {
/* 309 */       System.out.println(ex.getMessage());
/*     */     } finally {
/* 311 */       db.close();
/*     */     } 
/* 313 */     return resp;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\ParcelaVariedadDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */