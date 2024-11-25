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
/*     */ import lib.struc.bloqueo;
/*     */ import lib.struc.filterSql;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class bloqueadoOpDB
/*     */ {
/*     */   public static bloqueo getId(String id) {
/*  22 */     ConnectionDB db = new ConnectionDB();
/*  23 */     bloqueo o = null;
/*  24 */     Statement stmt = null;
/*  25 */     String sql = "";
/*     */     
/*     */     try {
/*  28 */       stmt = db.conn.createStatement();
/*  29 */       sql = "Select pb.*,u.user from bloqueoOp pb  left join user u on (pb.idUsuario=u.idUser) where pb.idBloqueo=" + id;
/*     */       
/*  31 */       System.out.println(sql);
/*  32 */       ResultSet rs = stmt.executeQuery(sql);
/*  33 */       if (rs.next()) {
/*     */         
/*  35 */         o = new bloqueo();
/*  36 */         o.setIdBloqueo(rs.getInt("idBloqueo"));
/*  37 */         o.setCodProductor(rs.getString("codProductor"));
/*  38 */         o.setIdEspecie(rs.getInt("idEspecie"));
/*  39 */         o.setIdMercado(rs.getInt("idMercado"));
/*  40 */         o.setComentario(rs.getString("cometario"));
/*     */ 
/*     */         
/*  43 */         o.setB(rs.getString("b"));
/*  44 */         o.setCreado(rs.getString("creado"));
/*  45 */         o.setModificado(rs.getString("modificado"));
/*  46 */         if (rs.getString("user") == null) {
/*  47 */           o.setUsuario("sistema");
/*     */         } else {
/*  49 */           o.setUsuario(rs.getString("user"));
/*     */         } 
/*     */       } 
/*     */       
/*  53 */       rs.close();
/*  54 */       stmt.close();
/*  55 */     } catch (Exception ex) {
/*     */       
/*  57 */       System.out.println("Error: " + ex.getMessage());
/*     */     } finally {
/*  59 */       db.close();
/*     */     } 
/*  61 */     return o;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update(bloqueo o) throws Exception {
/*  66 */     PreparedStatement ps = null;
/*  67 */     String sql = "";
/*  68 */     ConnectionDB db = new ConnectionDB();
/*     */     try {
/*  70 */       db.conn.setAutoCommit(false);
/*     */       
/*  72 */       sql = "update  bloqueoOp set cometario=?,b=?,idUsuario=?,  modificado=now() where idBloqueo=?";
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
/*  85 */       ps = db.conn.prepareStatement(sql);
/*     */ 
/*     */ 
/*     */       
/*  89 */       ps.setString(1, o.getComentario());
/*  90 */       ps.setString(2, o.getB());
/*  91 */       ps.setInt(3, o.getIdUsuario());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  96 */       ps.setInt(4, o.getIdBloqueo());
/*     */ 
/*     */ 
/*     */       
/* 100 */       ps.executeUpdate();
/* 101 */       db.conn.commit();
/* 102 */       db.conn.close();
/*     */     }
/* 104 */     catch (SQLException e) {
/*     */       
/* 106 */       System.out.println("Error: " + e.getMessage());
/* 107 */       System.out.println("sql: " + sql);
/* 108 */       throw new Exception("updateLimite: " + e.getMessage());
/* 109 */     } catch (Exception e) {
/* 110 */       e.printStackTrace();
/* 111 */       System.out.println("Error2: " + e.getMessage());
/* 112 */       throw new Exception("updateLimite: " + e.getMessage());
/*     */     } finally {
/*     */       
/* 115 */       db.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int getAllcount(ArrayList<filterSql> filter) throws Exception {
/* 120 */     int total = 0;
/* 121 */     Statement stmt = null;
/* 122 */     String sql = "";
/* 123 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 126 */       stmt = db.conn.createStatement();
/*     */       
/* 128 */       sql = "SELECT count(1) FROM vw_bloqueoOp ";
/*     */       
/* 130 */       if (filter.size() > 0) {
/* 131 */         String andSql = "";
/* 132 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 133 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 135 */         while (f.hasNext()) {
/*     */           
/* 137 */           filterSql row = f.next();
/* 138 */           if (!row.getValue().equals("")) {
/*     */             
/* 140 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 142 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 143 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 144 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 146 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 148 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 149 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 150 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 153 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like'%" + row.getValue() + "%'";
/* 154 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 160 */       ResultSet rs = stmt.executeQuery(sql);
/* 161 */       while (rs.next()) {
/* 162 */         total = rs.getInt(1);
/*     */       }
/* 164 */       rs.close();
/* 165 */       stmt.close();
/* 166 */       db.conn.close();
/*     */     
/*     */     }
/* 169 */     catch (SQLException e) {
/*     */       
/* 171 */       System.out.println("Error: " + e.getMessage());
/* 172 */       System.out.println("sql: " + sql);
/* 173 */       throw new Exception("getLimitesAll: " + e.getMessage());
/*     */     } finally {
/* 175 */       db.close();
/*     */     } 
/* 177 */     return total;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<bloqueo> getAll(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 183 */     ArrayList<bloqueo> arr = new ArrayList<>();
/* 184 */     Statement stmt = null;
/* 185 */     String sql = "";
/* 186 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 189 */       stmt = db.conn.createStatement();
/*     */       
/* 191 */       sql = "SELECT o.*,m.mercado FROM vw_bloqueoOp o   inner join mercado m on (m.idMercado=o.idMercado) ";
/*     */ 
/*     */       
/* 194 */       if (filter.size() > 0) {
/* 195 */         String andSql = "";
/* 196 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 197 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 199 */         while (f.hasNext()) {
/*     */           
/* 201 */           filterSql row = f.next();
/*     */           
/* 203 */           if (!row.getValue().equals("")) {
/*     */             
/* 205 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 207 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 208 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 209 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 211 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 213 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 214 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 215 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 217 */             else if (row.getCampo().equals("mercado")) {
/* 218 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/*     */             } else {
/* 220 */               sql = String.valueOf(sql) + andSql + "o." + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 221 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 226 */       if (!order.equals("")) {
/* 227 */         sql = String.valueOf(sql) + " order by " + order;
/*     */       }
/*     */       
/* 230 */       if (length > 0) {
/* 231 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/* 233 */       System.out.println("sql: " + sql);
/* 234 */       ResultSet rs = stmt.executeQuery(sql);
/* 235 */       while (rs.next()) {
/* 236 */         bloqueo o = new bloqueo();
/*     */         
/* 238 */         o.setIdBloqueo(rs.getInt("idBloqueo"));
/* 239 */         o.setCodProductor(rs.getString("codProductor"));
/* 240 */         o.setMercado(rs.getString("mercado"));
/* 241 */         o.setEspecie(rs.getString("especie"));
/* 242 */         o.setB(rs.getString("b"));
/*     */         
/* 244 */         o.setModificado(rs.getString("modificado"));
/* 245 */         o.setUsuario(rs.getString("user"));
/* 246 */         arr.add(o);
/*     */       } 
/* 248 */       rs.close();
/* 249 */       stmt.close();
/* 250 */       db.conn.close();
/*     */     }
/* 252 */     catch (SQLException e) {
/*     */       
/* 254 */       System.out.println("Error: " + e.getMessage());
/* 255 */       System.out.println("sql: " + sql);
/* 256 */       throw new Exception("getLimite: " + e.getMessage());
/*     */     } finally {
/* 258 */       db.close();
/*     */     } 
/*     */     
/* 261 */     return arr;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean insert(bloqueo o) throws ParseException {
/* 266 */     ConnectionDB db = new ConnectionDB();
/* 267 */     PreparedStatement ps = null;
/* 268 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/* 269 */     boolean resp = true;
/* 270 */     String sql = "";
/*     */     
/*     */     try {
/* 273 */       sql = "INSERT INTO bloqueoOp(codProductor, idEspecie, cometario, b, idUsuario, creado, modificado, idTemporada) Values (?,?,?,?,?,now(),now(),?,?,?)";
/*     */       
/* 275 */       ps = db.conn.prepareStatement(sql);
/*     */       
/* 277 */       ps.setString(1, o.getCodProductor());
/* 278 */       ps.setInt(2, o.getIdEspecie());
/* 279 */       ps.setString(3, o.getComentario());
/* 280 */       ps.setString(4, o.getB());
/* 281 */       ps.setInt(5, o.getIdUsuario());
/* 282 */       ps.setInt(6, o.getIdTemporada());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 288 */       ps.executeUpdate();
/*     */ 
/*     */ 
/*     */       
/* 292 */       ps.close();
/*     */     }
/* 294 */     catch (Exception ex) {
/*     */       
/* 296 */       System.out.println(ex.getMessage());
/*     */     } finally {
/*     */       
/* 299 */       db.close();
/*     */     } 
/* 301 */     return resp;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\bloqueadoOpDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */