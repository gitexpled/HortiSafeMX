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
/*     */ import lib.struc.ProductorCertificacion;
/*     */ import lib.struc.filterSql;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProductorCertificacionDB
/*     */ {
/*     */   public static ProductorCertificacion getProdCert(int idCert) throws Exception {
/*  21 */     ProductorCertificacion o = null;
/*  22 */     ConnectionDB db = new ConnectionDB();
/*  23 */     Statement stmt = null;
/*  24 */     String sql = "";
/*     */     
/*     */     try {
/*  27 */       stmt = db.conn.createStatement();
/*     */       
/*  29 */       sql = "SELECT * FROM productorCertificacion where idCert='" + idCert + "'";
/*     */       
/*  31 */       ResultSet rs = stmt.executeQuery(sql);
/*  32 */       if (rs.next()) {
/*  33 */         o = new ProductorCertificacion();
/*  34 */         o.setIdCert(rs.getInt("idCert"));
/*  35 */         o.setCodProductor(rs.getString("codProductor"));
/*  36 */         o.setCodParcela(rs.getString("codParcela"));
/*  37 */         o.setIdCertificacion(rs.getInt("idCertificacion"));
/*  38 */         o.setVigencia(rs.getString("vigencia"));
/*     */       } else {
/*  40 */         o = null;
/*     */       } 
/*     */       
/*  43 */       rs.close();
/*  44 */       stmt.close();
/*  45 */       db.conn.close();
/*     */     }
/*  47 */     catch (SQLException e) {
/*     */       
/*  49 */       System.out.println("Error: " + e.getMessage());
/*  50 */       System.out.println("sql: " + sql);
/*  51 */       throw new Exception("getTipoProducto: " + e.getMessage());
/*     */     } finally {
/*  53 */       db.close();
/*     */     } 
/*     */     
/*  56 */     return o;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getProductorCertificacionAll(ArrayList<filterSql> filter) throws Exception {
/*  61 */     int total = 0;
/*  62 */     Statement stmt = null;
/*  63 */     String sql = "";
/*  64 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/*  67 */       stmt = db.conn.createStatement();
/*     */       
/*  69 */       sql = "SELECT count(1) FROM productorCertificacion ";
/*     */       
/*  71 */       if (filter.size() > 0) {
/*  72 */         String andSql = "";
/*  73 */         andSql = String.valueOf(andSql) + " WHERE ";
/*  74 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/*  76 */         while (f.hasNext()) {
/*     */           
/*  78 */           filterSql row = f.next();
/*  79 */           if (!row.getValue().equals("")) {
/*     */             
/*  81 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/*  83 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/*  84 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/*  85 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/*  87 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/*  89 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/*  90 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/*  91 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/*  94 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like'%" + row.getValue() + "%'";
/*  95 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 101 */       ResultSet rs = stmt.executeQuery(sql);
/* 102 */       while (rs.next()) {
/* 103 */         total = rs.getInt(1);
/*     */       }
/* 105 */       rs.close();
/* 106 */       stmt.close();
/* 107 */       db.conn.close();
/*     */     
/*     */     }
/* 110 */     catch (SQLException e) {
/*     */       
/* 112 */       System.out.println("Error: " + e.getMessage());
/* 113 */       System.out.println("sql: " + sql);
/* 114 */       throw new Exception("getTipoProductoAll: " + e.getMessage());
/*     */     } finally {
/* 116 */       db.close();
/*     */     } 
/*     */     
/* 119 */     return total;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<ProductorCertificacion> getProductorCertificacion(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 125 */     ArrayList<ProductorCertificacion> productoresCert = new ArrayList<>();
/* 126 */     Statement stmt = null;
/* 127 */     String sql = "";
/* 128 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 131 */       stmt = db.conn.createStatement();
/*     */       
/* 133 */       sql = "SELECT p. *,certificacionescol FROM productorCertificacion p inner join  certificaciones c on (c.idCertificaciones=p.idCertificacion)";
/*     */       
/* 135 */       if (filter.size() > 0) {
/* 136 */         String andSql = "";
/* 137 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 138 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 140 */         while (f.hasNext()) {
/*     */           
/* 142 */           filterSql row = f.next();
/*     */           
/* 144 */           if (!row.getValue().equals("")) {
/*     */             
/* 146 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 148 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 149 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 150 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 152 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 154 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 155 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 156 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 159 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 160 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 165 */       if (!order.equals("")) {
/* 166 */         sql = String.valueOf(sql) + " order by ";
/*     */       }
/*     */       
/* 169 */       if (length > 0) {
/* 170 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/* 172 */       System.out.println("---------" + sql);
/* 173 */       ResultSet rs = stmt.executeQuery(sql);
/* 174 */       while (rs.next()) {
/* 175 */         ProductorCertificacion o = new ProductorCertificacion();
/* 176 */         o.setIdCert(rs.getInt("idCert"));
/* 177 */         o.setCodProductor(rs.getString("codProductor"));
/* 178 */         o.setIdCertificacion(rs.getInt("idCertificacion"));
/* 179 */         o.setVigencia(rs.getString("vigencia"));
/* 180 */         o.setCertificado(rs.getString("certificacionescol"));
/* 181 */         o.setCodParcela(rs.getString("codParcela"));
/* 182 */         productoresCert.add(o);
/*     */       } 
/* 184 */       rs.close();
/* 185 */       stmt.close();
/* 186 */       db.conn.close();
/*     */     }
/* 188 */     catch (SQLException e) {
/*     */       
/* 190 */       System.out.println("Error: " + e.getMessage());
/* 191 */       System.out.println("sql: " + sql);
/* 192 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/* 194 */       db.close();
/*     */     } 
/*     */     
/* 197 */     return productoresCert;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean insertProductorCertificacion(ProductorCertificacion productorCert) throws ParseException {
/* 202 */     ConnectionDB db = new ConnectionDB();
/* 203 */     Statement stmt = null;
/* 204 */     boolean resp = true;
/* 205 */     String sql = "";
/*     */     
/*     */     try {
/* 208 */       sql = "INSERT INTO productorCertificacion(codProductor,codParcela,idCertificacion,vigencia) Values ('" + productorCert.getCodProductor() + "','" + productorCert.getCodParcela() + "','" + productorCert.getIdCertificacion() + "','" + productorCert.getVigencia() + "')";
/* 209 */       System.out.println(sql);
/* 210 */       stmt = db.conn.createStatement();
/* 211 */       resp = stmt.execute(sql);
/* 212 */       stmt.close();
/*     */     }
/* 214 */     catch (Exception ex) {
/*     */       
/* 216 */       System.out.println(ex.getMessage());
/*     */     } finally {
/*     */       
/* 219 */       db.close();
/*     */     } 
/* 221 */     return resp;
/*     */   }
/*     */   
/*     */   public static void updateTipoProducto(ProductorCertificacion productorCert) throws Exception {
/* 225 */     PreparedStatement ps = null;
/* 226 */     String sql = "";
/* 227 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/* 228 */     ConnectionDB db = new ConnectionDB();
/*     */     try {
/* 230 */       db.conn.setAutoCommit(false);
/*     */       
/* 232 */       sql = "update  productorCertificacion set codProductor=?,codParcela=?,idCertificacion=?,vigencia=? where idCert=" + productorCert.getIdCert();
/*     */ 
/*     */       
/* 235 */       ps = db.conn.prepareStatement(sql);
/* 236 */       ps.setString(1, productorCert.getCodProductor());
/* 237 */       ps.setString(2, productorCert.getCodParcela());
/* 238 */       ps.setInt(3, productorCert.getIdCertificacion());
/* 239 */       ps.setString(4, productorCert.getVigencia());
/*     */ 
/*     */       
/* 242 */       ps.executeUpdate();
/* 243 */       db.conn.commit();
/* 244 */       db.conn.close();
/*     */     }
/* 246 */     catch (SQLException e) {
/*     */       
/* 248 */       System.out.println("Error: " + e.getMessage());
/* 249 */       System.out.println("sql: " + sql);
/* 250 */       throw new Exception("sepPfx: " + e.getMessage());
/* 251 */     } catch (Exception e) {
/* 252 */       e.printStackTrace();
/* 253 */       System.out.println("Error2: " + e.getMessage());
/* 254 */       throw new Exception("sepPfx: " + e.getMessage());
/*     */     } finally {
/*     */       
/* 257 */       db.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ProductorCertificacion getCertificacionVigente(String codProductor, String certificacion) {
/* 262 */     Statement stmt = null;
/* 263 */     String sql = "";
/* 264 */     ProductorCertificacion pc = null;
/* 265 */     ConnectionDB db = new ConnectionDB();
/* 266 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/*     */     try {
/* 268 */       sql = "SELECT * FROM productorCertificacion where codProductor=" + codProductor + " and idCertificacion=" + certificacion + " and vigencia >= '" + d + "'";
/* 269 */       stmt = db.conn.createStatement();
/* 270 */       ResultSet rs = stmt.executeQuery(sql);
/* 271 */       if (rs.next()) {
/*     */         
/* 273 */         pc = new ProductorCertificacion();
/* 274 */         pc.setIdCert(rs.getInt("idCert"));
/* 275 */         pc.setCodProductor(rs.getString("codProductor"));
/* 276 */         pc.setIdCertificacion(rs.getInt("idCertificacion"));
/* 277 */         pc.setVigencia(rs.getString("vigencia"));
/*     */       } 
/* 279 */       stmt.close();
/* 280 */       rs.close();
/* 281 */     } catch (Exception ex) {
/*     */       
/* 283 */       System.out.println("Error (getCertificacionVigente): " + ex.getMessage());
/*     */     } finally {
/* 285 */       db.close();
/*     */     } 
/* 287 */     return pc;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\ProductorCertificacionDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */