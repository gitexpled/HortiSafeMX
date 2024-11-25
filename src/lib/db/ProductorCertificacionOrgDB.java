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
/*     */ import lib.struc.ProductorCertificacionOrg;
/*     */ import lib.struc.filterSql;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProductorCertificacionOrgDB
/*     */ {
/*     */   public static ProductorCertificacionOrg getProdCert(int idCert) throws Exception {
/*  21 */     ProductorCertificacionOrg o = null;
/*  22 */     ConnectionDB db = new ConnectionDB();
/*  23 */     Statement stmt = null;
/*  24 */     String sql = "";
/*     */     
/*     */     try {
/*  27 */       stmt = db.conn.createStatement();
/*     */       
/*  29 */       sql = "SELECT * FROM productorCertificacionOrg where idCert='" + idCert + "'";
/*     */       
/*  31 */       ResultSet rs = stmt.executeQuery(sql);
/*  32 */       if (rs.next()) {
/*  33 */         o = new ProductorCertificacionOrg();
/*  34 */         o.setIdCert(rs.getInt("idCert"));
/*  35 */         o.setCodProductor(rs.getString("codProductor"));
/*  36 */         o.setCodParcela(rs.getString("codParcela"));
/*  37 */         o.setIdCertificacion(rs.getInt("idCertificacion"));
/*  38 */         o.setVigencia(rs.getString("vigencia"));
/*  39 */         o.setIdMercado(rs.getInt("idMercado"));
/*  40 */         o.setIdEspecie(rs.getInt("idEspecie"));
/*  41 */         System.out.println("sql: " + sql);
/*     */       } else {
/*  43 */         o = null;
/*     */       } 
/*     */       
/*  46 */       rs.close();
/*  47 */       stmt.close();
/*  48 */       db.conn.close();
/*     */     }
/*  50 */     catch (SQLException e) {
/*     */       
/*  52 */       System.out.println("Error: " + e.getMessage());
/*  53 */       System.out.println("sql: " + sql);
/*  54 */       throw new Exception("getTipoProducto: " + e.getMessage());
/*     */     } finally {
/*  56 */       db.close();
/*     */     } 
/*     */     
/*  59 */     return o;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getProductorCertificacionAll(ArrayList<filterSql> filter) throws Exception {
/*  64 */     int total = 0;
/*  65 */     Statement stmt = null;
/*  66 */     String sql = "";
/*  67 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/*  70 */       stmt = db.conn.createStatement();
/*     */       
/*  72 */       sql = "SELECT count(1) FROM productorCertificacionOrg p  inner join  certificacionesOrg c on (c.idCertificaciones=p.idCertificacion)    left join mercado m on (m.idMercado=p.idMercado) left join especie e on (e.idEspecie=p.idEspecie)";
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  77 */       if (filter.size() > 0) {
/*  78 */         String andSql = "";
/*  79 */         andSql = String.valueOf(andSql) + " WHERE ";
/*  80 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/*  82 */         while (f.hasNext()) {
/*     */           
/*  84 */           filterSql row = f.next();
/*  85 */           if (!row.getValue().equals("")) {
/*     */             
/*  87 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/*  89 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/*  90 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/*  91 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/*  93 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/*  95 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/*  96 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/*  97 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 100 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like'%" + row.getValue() + "%'";
/* 101 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 107 */       ResultSet rs = stmt.executeQuery(sql);
/* 108 */       while (rs.next()) {
/* 109 */         total = rs.getInt(1);
/*     */       }
/* 111 */       rs.close();
/* 112 */       stmt.close();
/* 113 */       db.conn.close();
/*     */     
/*     */     }
/* 116 */     catch (SQLException e) {
/*     */       
/* 118 */       System.out.println("Error: " + e.getMessage());
/* 119 */       System.out.println("sql: " + sql);
/* 120 */       throw new Exception("getTipoProductoAll: " + e.getMessage());
/*     */     } finally {
/* 122 */       db.close();
/*     */     } 
/*     */     
/* 125 */     return total;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<ProductorCertificacionOrg> getProductorCertificacion(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 131 */     ArrayList<ProductorCertificacionOrg> productoresCert = new ArrayList<>();
/* 132 */     Statement stmt = null;
/* 133 */     String sql = "";
/* 134 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 137 */       stmt = db.conn.createStatement();
/*     */       
/* 139 */       sql = "SELECT p. *,certificacionescol,m.mercado,e.especie FROM productorCertificacionOrg p inner join  certificacionesOrg c on (c.idCertificaciones=p.idCertificacion)    left join mercado m on (m.idMercado=p.idMercado) left join especie e on (e.idEspecie=p.idEspecie)";
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 144 */       if (filter.size() > 0) {
/* 145 */         String andSql = "";
/* 146 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 147 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 149 */         while (f.hasNext()) {
/*     */           
/* 151 */           filterSql row = f.next();
/*     */           
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
/* 168 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 169 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 174 */       if (!order.equals("")) {
/* 175 */         sql = String.valueOf(sql) + " order by ";
/*     */       }
/*     */       
/* 178 */       if (length > 0) {
/* 179 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/* 181 */       System.out.println("======" + sql);
/* 182 */       ResultSet rs = stmt.executeQuery(sql);
/* 183 */       while (rs.next()) {
/* 184 */         ProductorCertificacionOrg o = new ProductorCertificacionOrg();
/* 185 */         o.setIdCert(rs.getInt("idCert"));
/* 186 */         o.setCodProductor(rs.getString("codProductor"));
/* 187 */         o.setIdCertificacion(rs.getInt("idCertificacion"));
/* 188 */         o.setVigencia(rs.getString("vigencia"));
/* 189 */         o.setCertificado(rs.getString("certificacionescol"));
/* 190 */         o.setCodParcela(rs.getString("codParcela"));
/* 191 */         o.setMercado(rs.getString("mercado"));
/* 192 */         o.setEspecie(rs.getString("especie"));
/* 193 */         productoresCert.add(o);
/*     */       } 
/* 195 */       rs.close();
/* 196 */       stmt.close();
/* 197 */       db.conn.close();
/*     */     }
/* 199 */     catch (SQLException e) {
/*     */       
/* 201 */       System.out.println("Error: " + e.getMessage());
/* 202 */       System.out.println("sql: " + sql);
/* 203 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/* 205 */       db.close();
/*     */     } 
/*     */     
/* 208 */     return productoresCert;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean insertProductorCertificacion(ProductorCertificacionOrg p) throws ParseException {
/* 213 */     ConnectionDB db = new ConnectionDB();
/* 214 */     Statement stmt = null;
/* 215 */     boolean resp = true;
/* 216 */     String sql = "";
/*     */     
/*     */     try {
/* 219 */       sql = "INSERT INTO productorCertificacionOrg(codProductor,codParcela,idCertificacion,idMercado,idEspecie,vigencia) Values ('" + p.getCodProductor() + "','" + p.getCodParcela() + "','" + p.getIdCertificacion() + "','" + p.getIdEspecie() + "','" + p.getIdMercado() + "','" + p.getVigencia() + "')";
/* 220 */       System.out.println(sql);
/* 221 */       stmt = db.conn.createStatement();
/* 222 */       resp = stmt.execute(sql);
/* 223 */       stmt.close();
/*     */     }
/* 225 */     catch (Exception ex) {
/*     */       
/* 227 */       System.out.println(ex.getMessage());
/*     */     } finally {
/*     */       
/* 230 */       db.close();
/*     */     } 
/* 232 */     return resp;
/*     */   }
/*     */   
/*     */   public static void updateTipoProducto(ProductorCertificacionOrg productorCert) throws Exception {
/* 236 */     PreparedStatement ps = null;
/* 237 */     String sql = "";
/* 238 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/* 239 */     ConnectionDB db = new ConnectionDB();
/*     */     try {
/* 241 */       db.conn.setAutoCommit(false);
/*     */       
/* 243 */       sql = "update  productorCertificacionOrg set codProductor=?,codParcela=?,idCertificacion=?,vigencia=? ,idMercado=? ,idEspecie=? where idCert=" + productorCert.getIdCert();
/*     */ 
/*     */       
/* 246 */       ps = db.conn.prepareStatement(sql);
/* 247 */       ps.setString(1, productorCert.getCodProductor());
/* 248 */       ps.setString(2, productorCert.getCodParcela());
/* 249 */       ps.setInt(3, productorCert.getIdCertificacion());
/* 250 */       ps.setString(4, productorCert.getVigencia());
/* 251 */       ps.setInt(5, productorCert.getIdMercado());
/* 252 */       ps.setInt(6, productorCert.getIdEspecie());
/*     */ 
/*     */       
/* 255 */       ps.executeUpdate();
/* 256 */       db.conn.commit();
/* 257 */       db.conn.close();
/*     */     }
/* 259 */     catch (SQLException e) {
/*     */       
/* 261 */       System.out.println("Error: " + e.getMessage());
/* 262 */       System.out.println("sql: " + sql);
/* 263 */       throw new Exception("sepPfx: " + e.getMessage());
/* 264 */     } catch (Exception e) {
/* 265 */       e.printStackTrace();
/* 266 */       System.out.println("Error2: " + e.getMessage());
/* 267 */       throw new Exception("sepPfx: " + e.getMessage());
/*     */     } finally {
/*     */       
/* 270 */       db.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ProductorCertificacionOrg getCertificacionVigente(String codProductor, String certificacion, String idMercado, String idEspecie) {
/* 275 */     Statement stmt = null;
/* 276 */     String sql = "";
/* 277 */     ProductorCertificacionOrg pc = null;
/* 278 */     ConnectionDB db = new ConnectionDB();
/* 279 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/*     */     try {
/* 281 */       sql = "SELECT * FROM productorCertificacionOrg where codProductor='" + codProductor + "' and idCertificacion='" + certificacion + "'  and idMercado='" + idMercado + "'  and idEspecie='" + idEspecie + "' and vigencia >= '" + d + "'";
/* 282 */       System.out.println(sql);
/* 283 */       stmt = db.conn.createStatement();
/* 284 */       ResultSet rs = stmt.executeQuery(sql);
/*     */       
/* 286 */       if (rs.next()) {
/*     */         
/* 288 */         pc = new ProductorCertificacionOrg();
/* 289 */         pc.setIdCert(rs.getInt("idCert"));
/* 290 */         pc.setCodProductor(rs.getString("codProductor"));
/* 291 */         pc.setIdCertificacion(rs.getInt("idCertificacion"));
/* 292 */         pc.setVigencia(rs.getString("vigencia"));
/*     */       } 
/* 294 */       stmt.close();
/* 295 */       rs.close();
/* 296 */     } catch (Exception ex) {
/*     */       
/* 298 */       System.out.println("Error (getCertificacionVigente): " + ex.getMessage());
/*     */     } finally {
/* 300 */       db.close();
/*     */     } 
/* 302 */     return pc;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\ProductorCertificacionOrgDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */