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
/*     */ import lib.struc.Sector;
/*     */ import lib.struc.filterSql;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SectorDB
/*     */ {
/*     */   public static String delete(String id) throws Exception {
/*  21 */     PreparedStatement ps = null;
/*  22 */     String sql = "";
/*  23 */     ConnectionDB db = new ConnectionDB();
/*  24 */     String resp = "No se pudo eliminar el registro";
/*     */     try {
/*  26 */       db.conn.setAutoCommit(false);
/*     */       
/*  28 */       sql = "DELETE t1 ";
/*  29 */       sql = String.valueOf(sql) + "FROM restriciones t1 ";
/*  30 */       sql = String.valueOf(sql) + "INNER JOIN sector t2 ON (t1.codProductor=t2.codProductor and t1.codParcela=t2.codParcela and t1.codTurno=t2.codTurno) ";
/*  31 */       sql = String.valueOf(sql) + "WHERE  idTurno='" + id + "';";
/*  32 */       System.out.println("sql: " + sql);
/*  33 */       ps = db.conn.prepareStatement(sql);
/*  34 */       ps.executeUpdate();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  44 */       sql = "DELETE t1 ";
/*  45 */       sql = String.valueOf(sql) + "FROM parcelaVariedad t1 ";
/*  46 */       sql = String.valueOf(sql) + "INNER JOIN sector t2 ON (t1.codProductor=t2.codProductor and t1.codParcela=t2.codParcela and t1.codTurno=t2.codTurno) ";
/*  47 */       sql = String.valueOf(sql) + "WHERE  t2.idTurno='" + id + "';";
/*  48 */       System.out.println("sql: " + sql);
/*  49 */       ps = db.conn.prepareStatement(sql);
/*  50 */       ps.executeUpdate();
/*     */ 
/*     */ 
/*     */       
/*  54 */       sql = "delete  from sector  where idTurno='" + id + "';";
/*  55 */       System.out.println("sql: " + sql);
/*  56 */       ps = db.conn.prepareStatement(sql);
/*  57 */       ps.executeUpdate();
/*     */       
/*  59 */       db.conn.commit();
/*  60 */       db.conn.close();
/*  61 */       resp = "Se elimino el registro";
/*     */     }
/*  63 */     catch (SQLException e) {
/*     */       
/*  65 */       resp = "No se pudo eliminar el registro";
/*  66 */       System.out.println("Error: " + e.getMessage());
/*  67 */       System.out.println("sql: " + sql);
/*  68 */       throw new Exception("sepPfx: " + e.getMessage());
/*  69 */     } catch (Exception e) {
/*  70 */       e.printStackTrace();
/*  71 */       resp = "No se pudo eliminar el registro";
/*  72 */       System.out.println("Error2: " + e.getMessage());
/*  73 */       throw new Exception("sepPfx: " + e.getMessage());
/*     */     } finally {
/*     */       
/*  76 */       db.close();
/*     */     } 
/*     */     
/*  79 */     return resp;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Sector get(String id) throws Exception {
/*  84 */     Sector o = null;
/*  85 */     ConnectionDB db = new ConnectionDB();
/*  86 */     Statement stmt = null;
/*  87 */     String sql = "";
/*     */     
/*     */     try {
/*  90 */       stmt = db.conn.createStatement();
/*     */       
/*  92 */       sql = "SELECT * FROM sector where idTurno='" + id + "'";
/*  93 */       System.out.println("sql: " + sql);
/*     */       
/*  95 */       ResultSet rs = stmt.executeQuery(sql);
/*  96 */       if (rs.next()) {
/*  97 */         o = new Sector();
/*  98 */         o.setIdTurno(rs.getInt("idTurno"));
/*  99 */         o.setCodParcela(rs.getString("codParcela"));
/* 100 */         o.setCodProductor(rs.getString("codProductor"));
/* 101 */         o.setCodTurno(rs.getString("codTurno"));
/* 102 */         o.setNombre(rs.getString("nombreTurno"));
/*     */         
/* 104 */         o.setCreado(rs.getDate("creado"));
/* 105 */         o.setModificado(rs.getDate("modificado"));
/* 106 */         o.setIdUser(rs.getInt("idUser"));
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 112 */         o = null;
/*     */       } 
/*     */       
/* 115 */       rs.close();
/* 116 */       stmt.close();
/* 117 */       db.conn.close();
/*     */     }
/* 119 */     catch (SQLException e) {
/*     */       
/* 121 */       System.out.println("Error: " + e.getMessage());
/* 122 */       System.out.println("sql: " + sql);
/* 123 */       throw new Exception("getUser: " + e.getMessage());
/*     */     } finally {
/* 125 */       db.close();
/*     */     } 
/*     */     
/* 128 */     return o;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update(Sector prod) throws Exception {
/* 133 */     PreparedStatement ps = null;
/* 134 */     String sql = "";
/* 135 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/* 136 */     ConnectionDB db = new ConnectionDB();
/*     */     try {
/* 138 */       db.conn.setAutoCommit(false);
/*     */       
/* 140 */       sql = "update  sector set nombre=?,codProductor=?,codParcela=?,codTurno=?, modificado='" + d + 
/* 141 */         "', idUser=? where codTurno='" + prod.getCodTurno() + "'";
/*     */       
/* 143 */       ps = db.conn.prepareStatement(sql);
/* 144 */       ps.setString(1, prod.getNombre());
/* 145 */       ps.setString(2, prod.getCodProductor());
/* 146 */       ps.setString(3, prod.getCodParcela());
/* 147 */       ps.setString(4, prod.getCodTurno());
/* 148 */       ps.setInt(5, prod.getIdUser());
/*     */ 
/*     */       
/* 151 */       ps.executeUpdate();
/* 152 */       db.conn.commit();
/* 153 */       db.conn.close();
/*     */     }
/* 155 */     catch (SQLException e) {
/*     */       
/* 157 */       System.out.println("Error: " + e.getMessage());
/* 158 */       System.out.println("sql: " + sql);
/* 159 */       throw new Exception("sepPfx: " + e.getMessage());
/* 160 */     } catch (Exception e) {
/* 161 */       e.printStackTrace();
/* 162 */       System.out.println("Error2: " + e.getMessage());
/* 163 */       throw new Exception("sepPfx: " + e.getMessage());
/*     */     } finally {
/*     */       
/* 166 */       db.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getAll(ArrayList<filterSql> filter) throws Exception {
/* 178 */     int total = 0;
/* 179 */     Statement stmt = null;
/* 180 */     String sql = "";
/* 181 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 184 */       stmt = db.conn.createStatement();
/*     */       
/* 186 */       sql = "SELECT count(1) FROM sector ";
/*     */       
/* 188 */       if (filter.size() > 0) {
/* 189 */         String andSql = "";
/* 190 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 191 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 193 */         while (f.hasNext()) {
/* 194 */           filterSql row = f.next();
/* 195 */           if (!row.getValue().equals("")) {
/* 196 */             if (row.getCampo().endsWith("_to")) {
/* 197 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 198 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 199 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + 
/* 200 */                 sqlDate.format(formatter.parse(row.getValue())) + "'";
/* 201 */             } else if (row.getCampo().endsWith("_from")) {
/* 202 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 203 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 204 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + 
/* 205 */                 sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             
/*     */             }
/* 208 */             else if (row.getCampo().equals("codParcela")) {
/* 209 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " = '" + row.getValue() + "'";
/*     */             } else {
/* 211 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/*     */             } 
/* 213 */             andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 219 */       ResultSet rs = stmt.executeQuery(sql);
/* 220 */       while (rs.next()) {
/* 221 */         total = rs.getInt(1);
/*     */       }
/* 223 */       rs.close();
/* 224 */       stmt.close();
/* 225 */       db.conn.close();
/*     */     }
/* 227 */     catch (SQLException e) {
/*     */       
/* 229 */       System.out.println("Error: " + e.getMessage());
/* 230 */       System.out.println("sql: " + sql);
/* 231 */       throw new Exception("getUsersAll: " + e.getMessage());
/*     */     } finally {
/* 233 */       db.close();
/*     */     } 
/*     */     
/* 236 */     return total;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ArrayList<Sector> getAll(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 241 */     ArrayList<Sector> Parcelas = new ArrayList<>();
/* 242 */     Statement stmt = null;
/* 243 */     String sql = "";
/* 244 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 247 */       stmt = db.conn.createStatement();
/*     */       
/* 249 */       sql = "SELECT * FROM sector ";
/*     */       
/* 251 */       if (filter.size() > 0) {
/* 252 */         String andSql = "";
/* 253 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 254 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 256 */         while (f.hasNext()) {
/* 257 */           filterSql row = f.next();
/*     */           
/* 259 */           if (!row.getValue().equals("")) {
/* 260 */             if (row.getCampo().endsWith("_to")) {
/* 261 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 262 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 263 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + 
/* 264 */                 sqlDate.format(formatter.parse(row.getValue())) + "'";
/* 265 */             } else if (row.getCampo().endsWith("_from")) {
/* 266 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 267 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 268 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + 
/* 269 */                 sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             
/*     */             }
/* 272 */             else if (row.getCampo().equals("codParcela")) {
/* 273 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " = '" + row.getValue() + "'";
/*     */             } else {
/* 275 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " = '" + row.getValue() + "'";
/*     */             } 
/* 277 */             andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 282 */       if (!order.equals("")) {
/* 283 */         sql = String.valueOf(sql) + " order by ";
/*     */       }
/*     */       
/* 286 */       if (length > 0) {
/* 287 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/* 289 */       System.out.println("sql: " + sql);
/* 290 */       ResultSet rs = stmt.executeQuery(sql);
/* 291 */       while (rs.next()) {
/* 292 */         Sector o = new Sector();
/*     */         
/* 294 */         o.setIdTurno(rs.getInt("idTurno"));
/* 295 */         o.setCodParcela(rs.getString("codParcela"));
/* 296 */         o.setCodProductor(rs.getString("codProductor"));
/* 297 */         o.setCodTurno(rs.getString("codTurno"));
/* 298 */         o.setNombre(rs.getString("nombreTurno"));
/*     */         
/* 300 */         o.setCreado(rs.getDate("creado"));
/* 301 */         o.setModificado(rs.getDate("modificado"));
/* 302 */         o.setIdUser(rs.getInt("idUser"));
/*     */ 
/*     */         
/* 305 */         Parcelas.add(o);
/*     */       } 
/* 307 */       rs.close();
/* 308 */       stmt.close();
/* 309 */       db.conn.close();
/*     */     }
/* 311 */     catch (SQLException e) {
/*     */       
/* 313 */       System.out.println("Error: " + e.getMessage());
/* 314 */       System.out.println("sql: " + sql);
/* 315 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/* 317 */       db.close();
/*     */     } 
/*     */     
/* 320 */     return Parcelas;
/*     */   }
/*     */   
/*     */   public static boolean insert(Sector o) throws ParseException {
/* 324 */     ConnectionDB db = new ConnectionDB();
/* 325 */     Statement stmt = null;
/* 326 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/* 327 */     boolean resp = true;
/* 328 */     String sql = "";
/*     */     
/*     */     try {
/* 331 */       sql = "INSERT INTO sector(codProductor,codParcela,codTurno,creado,modificado,idUser,nombreTurno) Values ('" + 
/* 332 */         o.getCodProductor() + "','" + o.getCodParcela() + "','" + o.getCodTurno() + "','" + d + "','" + d + "'," + 
/* 333 */         o.getIdUser() + ",'" + o.getNombre() + "')";
/* 334 */       stmt = db.conn.createStatement();
/* 335 */       resp = stmt.execute(sql);
/* 336 */       stmt.close();
/* 337 */       //TemporadaDB.setCreateRestriciones();
/*     */     }
/* 339 */     catch (Exception ex) {
/* 340 */       System.out.println(ex.getMessage());
/*     */     } finally {
/* 342 */       db.close();
/*     */     } 
/* 344 */     return resp;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\SectorDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */