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
/*     */ import lib.struc.Turno;
/*     */ import lib.struc.filterSql;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TurnoDB
/*     */ {
/*     */   public static Turno get(String id) throws Exception {
/*  22 */     Turno o = null;
/*  23 */     ConnectionDB db = new ConnectionDB();
/*  24 */     Statement stmt = null;
/*  25 */     String sql = "";
/*     */     
/*     */     try {
/*  28 */       stmt = db.conn.createStatement();
/*     */       
/*  30 */       sql = "SELECT * FROM turno where idTurno='" + id + "'";
/*  31 */       System.out.println("sql: " + sql);
/*     */       
/*  33 */       ResultSet rs = stmt.executeQuery(sql);
/*  34 */       if (rs.next()) {
/*  35 */         o = new Turno();
/*  36 */         o.setIdTurno(rs.getInt("idTurno"));
/*  37 */         o.setCodParcela(rs.getString("codParcela"));
/*  38 */         o.setCodProductor(rs.getString("codProductor"));
/*  39 */         o.setCodTurno(rs.getString("codTurno"));
/*  40 */         o.setNombre(rs.getString("nombreTurno"));
/*     */         
/*  42 */         o.setCreado(rs.getDate("creado"));
/*  43 */         o.setModificado(rs.getDate("modificado"));
/*  44 */         o.setIdUser(rs.getInt("idUser"));
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/*  50 */         o = null;
/*     */       } 
/*     */       
/*  53 */       rs.close();
/*  54 */       stmt.close();
/*  55 */       db.conn.close();
/*     */     }
/*  57 */     catch (SQLException e) {
/*     */       
/*  59 */       System.out.println("Error: " + e.getMessage());
/*  60 */       System.out.println("sql: " + sql);
/*  61 */       throw new Exception("getUser: " + e.getMessage());
/*     */     } finally {
/*  63 */       db.close();
/*     */     } 
/*     */     
/*  66 */     return o;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update(Turno prod) throws Exception {
/*  71 */     PreparedStatement ps = null;
/*  72 */     String sql = "";
/*  73 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/*  74 */     ConnectionDB db = new ConnectionDB();
/*     */     try {
/*  76 */       db.conn.setAutoCommit(false);
/*     */       
/*  78 */       sql = "update  turno set nombre=?,codProductor=?,codParcela=?,codTurno=?, modificado='" + d + 
/*  79 */         "', idUser=? where codTurno='" + prod.getIdTurno() + "'";
/*     */       
/*  81 */       ps = db.conn.prepareStatement(sql);
/*  82 */       ps.setString(1, prod.getNombre());
/*  83 */       ps.setString(2, prod.getCodProductor());
/*  84 */       ps.setString(3, prod.getCodParcela());
/*  85 */       ps.setString(4, prod.getCodTurno());
/*  86 */       ps.setInt(5, prod.getIdUser());
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getAll(ArrayList<filterSql> filter) throws Exception {
/* 116 */     int total = 0;
/* 117 */     Statement stmt = null;
/* 118 */     String sql = "";
/* 119 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 122 */       stmt = db.conn.createStatement();
/*     */       
/* 124 */       sql = "SELECT count(1) FROM turno ";
/*     */       
/* 126 */       if (filter.size() > 0) {
/* 127 */         String andSql = "";
/* 128 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 129 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 131 */         while (f.hasNext()) {
/* 132 */           filterSql row = f.next();
/* 133 */           if (!row.getValue().equals("")) {
/* 134 */             if (row.getCampo().endsWith("_to")) {
/* 135 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 136 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 137 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + 
/* 138 */                 sqlDate.format(formatter.parse(row.getValue())) + "'";
/* 139 */             } else if (row.getCampo().endsWith("_from")) {
/* 140 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 141 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 142 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + 
/* 143 */                 sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             
/*     */             }
/* 146 */             else if (row.getCampo().equals("codParcela")) {
/* 147 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " = '" + row.getValue() + "'";
/*     */             } else {
/* 149 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/*     */             } 
/* 151 */             andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 157 */       ResultSet rs = stmt.executeQuery(sql);
/* 158 */       while (rs.next()) {
/* 159 */         total = rs.getInt(1);
/*     */       }
/* 161 */       rs.close();
/* 162 */       stmt.close();
/* 163 */       db.conn.close();
/*     */     }
/* 165 */     catch (SQLException e) {
/*     */       
/* 167 */       System.out.println("Error: " + e.getMessage());
/* 168 */       System.out.println("sql: " + sql);
/* 169 */       throw new Exception("getUsersAll: " + e.getMessage());
/*     */     } finally {
/* 171 */       db.close();
/*     */     } 
/*     */     
/* 174 */     return total;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ArrayList<Turno> getAll(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 179 */     ArrayList<Turno> Parcelas = new ArrayList<>();
/* 180 */     Statement stmt = null;
/* 181 */     String sql = "";
/* 182 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 185 */       stmt = db.conn.createStatement();
/*     */       
/* 187 */       sql = "SELECT * FROM turno ";
/*     */       
/* 189 */       if (filter.size() > 0) {
/* 190 */         String andSql = "";
/* 191 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 192 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 194 */         while (f.hasNext()) {
/* 195 */           filterSql row = f.next();
/*     */           
/* 197 */           if (!row.getValue().equals("")) {
/* 198 */             if (row.getCampo().endsWith("_to")) {
/* 199 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 200 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 201 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + 
/* 202 */                 sqlDate.format(formatter.parse(row.getValue())) + "'";
/* 203 */             } else if (row.getCampo().endsWith("_from")) {
/* 204 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 205 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 206 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + 
/* 207 */                 sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             
/*     */             }
/* 210 */             else if (row.getCampo().equals("codParcela")) {
/* 211 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " = '" + row.getValue() + "'";
/*     */             } else {
/* 213 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/*     */             } 
/* 215 */             andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 220 */       if (!order.equals("")) {
/* 221 */         sql = String.valueOf(sql) + " order by ";
/*     */       }
/*     */       
/* 224 */       if (length > 0) {
/* 225 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/* 227 */       System.out.println("sql: " + sql);
/* 228 */       ResultSet rs = stmt.executeQuery(sql);
/* 229 */       while (rs.next()) {
/* 230 */         Turno o = new Turno();
/*     */         
/* 232 */         o.setIdTurno(rs.getInt("idTurno"));
/* 233 */         o.setCodParcela(rs.getString("codParcela"));
/* 234 */         o.setCodProductor(rs.getString("codProductor"));
/* 235 */         o.setCodTurno(rs.getString("codTurno"));
/* 236 */         o.setNombre(rs.getString("nombreTurno"));
/*     */         
/* 238 */         o.setCreado(rs.getDate("creado"));
/* 239 */         o.setModificado(rs.getDate("modificado"));
/* 240 */         o.setIdUser(rs.getInt("idUser"));
/*     */ 
/*     */         
/* 243 */         Parcelas.add(o);
/*     */       } 
/* 245 */       rs.close();
/* 246 */       stmt.close();
/* 247 */       db.conn.close();
/*     */     }
/* 249 */     catch (SQLException e) {
/*     */       
/* 251 */       System.out.println("Error: " + e.getMessage());
/* 252 */       System.out.println("sql: " + sql);
/* 253 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/* 255 */       db.close();
/*     */     } 
/*     */     
/* 258 */     return Parcelas;
/*     */   }
/*     */   
/*     */   public static boolean insert(Turno o) throws ParseException {
/* 262 */     ConnectionDB db = new ConnectionDB();
/* 263 */     Statement stmt = null;
/* 264 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/* 265 */     boolean resp = true;
/* 266 */     String sql = "";
/*     */     
/*     */     try {
/* 269 */       sql = "INSERT INTO turno(codProductor,codParcela,codTurno,creado,modificado,idUser,nombreTurno) Values ('" + 
/* 270 */         o.getCodProductor() + "','" + o.getCodParcela() + "','" + o.getCodTurno() + "','" + d + "','" + d + "'," + 
/* 271 */         o.getIdUser() + ",'" + o.getNombre() + "')";
/* 272 */       stmt = db.conn.createStatement();
/* 273 */       resp = stmt.execute(sql);
/* 274 */       stmt.close();
/* 275 */       TemporadaDB.setCreateRestriciones();
/*     */     }
/* 277 */     catch (Exception ex) {
/* 278 */       System.out.println(ex.getMessage());
/* 279 */       resp = false;
/*     */     } finally {
/* 281 */       db.close();
/*     */     } 
/* 283 */     return resp;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\TurnoDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */