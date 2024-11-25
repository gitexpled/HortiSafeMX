/*     */ package lib.db;
/*     */ 
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import lib.struc.Temporada;
/*     */ import lib.struc.TemporadaZona;
/*     */ import lib.struc.grafico;
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
/*     */ public class homePageDB
/*     */ {
/*     */   public static ArrayList<grafico> getProductorMercado(int idEspecie) throws Exception {
/*  24 */     ArrayList<grafico> gs = new ArrayList<>();
/*  25 */     Statement stmt = null;
/*  26 */     String sql = "";
/*  27 */     ConnectionDB db = new ConnectionDB();
/*  28 */     Temporada temp = TemporadaDB.getMaxTemprada();
/*  29 */     int idTemp = temp.getIdTemporada();
/*     */     
/*     */     try {
/*  32 */       stmt = db.conn.createStatement();
/*     */       
/*  34 */       sql = "SELECT m.mercado as nombre, count(IFNULL(bp.b,IFNULL(pc.b,r.bloqueado))) as valor FROM (SELECT r.* FROM restriciones r  INNER JOIN (SELECT  MAX(idRestriciones) AS maxId,`codProductor`,idMercado,idTemporada,idEspecie FROM restriciones where inicial='N'and carga!='L' and idEspecie=" + 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  39 */         idEspecie + " and idTemporada=" + idTemp + " " + 
/*  40 */         " GROUP BY `codProductor`,idMercado,idTemporada,idEspecie) rr " + 
/*  41 */         " ON r.idEspecie=rr.idEspecie  and r.idTemporada=rr.idTemporada and rr.idTemporada=r.idTemporada " + 
/*  42 */         "and r.idMercado = rr.idMercado  and r.`codProductor`=rr.`codProductor` " + 
/*  43 */         "AND r.idRestriciones = rr.maxId) as r " + "left join productorBloqueo as bp  " + 
/*  44 */         "on (bp.activo='Y' and bp.codProductor=r.codProductor and bp.idEspecie=r.idEspecie " + 
/*  45 */         "and bp.idTemporada=r.idTemporada and  bp.idMercado=r.idMercado) " + 
/*  46 */         " inner join mercado m on (r.idMercado=m.idMercado) " + 
/*  47 */         "inner join productor p on (r.codProductor=p.codProductor) " + 
/*  48 */         "left join vw_productorCertificado pc on (r.codProductor=pc.codProductor) " + 
/*  49 */         "left join bloqueoOp bo on (bo.codProductor=r.codProductor and bo.idEspecie=r.idEspecie and bo.idTemporada=r.idTemporada and  bo.idMercado=r.idMercado)" + 
/*  50 */         "where r.idEspecie=" + idEspecie + "  and r.idTemporada=" + idTemp + " and IF(bo.b='N',bo.b,IFNULL(bp.b,IFNULL(pc.b,r.bloqueado)))='N' ";
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  55 */       sql = String.valueOf(sql) + "  GROUP BY r.idMercado,r.idEspecie ";
/*  56 */       System.out.println(sql);
/*  57 */       ResultSet rs = stmt.executeQuery(sql);
/*  58 */       while (rs.next()) {
/*  59 */         grafico o = new grafico();
/*     */         
/*  61 */         o.setNombre(rs.getString("nombre"));
/*  62 */         o.setValor(rs.getInt("valor"));
/*  63 */         gs.add(o);
/*     */       } 
/*  65 */       rs.close();
/*  66 */       stmt.close();
/*  67 */       db.conn.close();
/*     */     }
/*  69 */     catch (SQLException e) {
/*     */       
/*  71 */       System.out.println("Error: " + e.getMessage());
/*  72 */       System.out.println("sql: " + sql);
/*  73 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/*  75 */       db.close();
/*     */     } 
/*     */     
/*  78 */     return gs;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ArrayList<grafico> getProductorZona(int idEspecie, int idTemporada) throws Exception {
/*  83 */     ArrayList<grafico> gs = new ArrayList<>();
/*  84 */     Statement stmt = null;
/*  85 */     String sql = "";
/*  86 */     ConnectionDB db = new ConnectionDB();
/*  87 */     Temporada temp = TemporadaDB.getMaxTemprada();
/*  88 */     int idTemp = temp.getIdTemporada();
/*     */     
/*     */     try {
/*  91 */       stmt = db.conn.createStatement();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 112 */       sql = String.valueOf(sql) + " GROUP BY p.zona,r.idEspecie )  i  inner join zona z on (z.nombre=i.nombre) order by z.orden asc";
/*     */ 
/*     */ 
/*     */       
/* 116 */       sql = "SELECT i.* from ( select a.zona as nombre, a.valor cantidad,b.valor muestrados,  FORMAT(IFNULL((b.valor*100)/a.valor,0),1, 'de_DE') as valor from (select p.zona, sum(1)  as valor from productor as p ";
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 121 */       if (idEspecie == 1) {
/* 122 */         sql = String.valueOf(sql) + " where p.arandano='Y' ";
/*     */       } else {
/* 124 */         sql = String.valueOf(sql) + " where p.cereza='Y' ";
/* 125 */       }  sql = String.valueOf(sql) + "group by zona) as a left join ( select p.zona, sum(1) as valor from  (select max(idRestriciones),idEspecie, codProductor from restriciones where inicial='N' and idEspecie=" + 
/*     */ 
/*     */ 
/*     */         
/* 129 */         idEspecie + " AND idTemporada=" + idTemp + " group by codProductor,idEspecie) as r ";
/*     */       
/* 131 */       if (idEspecie == 1) {
/* 132 */         sql = String.valueOf(sql) + "inner join productor p on (p.codProductor=r.codProductor and p.arandano='Y') ";
/*     */       } else {
/* 134 */         sql = String.valueOf(sql) + "inner join productor p on (p.codProductor=r.codProductor  and p.cereza='Y') ";
/* 135 */       }  sql = String.valueOf(sql) + "group by p.zona) b on (a.zona=b.zona)) i";
/* 136 */       sql = String.valueOf(sql) + " inner join zona z on (z.nombre=i.nombre) order by z.orden asc";
/*     */       
/* 138 */       System.out.println(sql);
/* 139 */       ResultSet rs = stmt.executeQuery(sql);
/* 140 */       while (rs.next()) {
/* 141 */         grafico o = new grafico();
/*     */         
/* 143 */         o.setNombre(rs.getString("nombre"));
/* 144 */         o.setValor(rs.getInt("valor"));
/* 145 */         gs.add(o);
/*     */       } 
/* 147 */       rs.close();
/* 148 */       stmt.close();
/* 149 */       db.conn.close();
/*     */     }
/* 151 */     catch (SQLException e) {
/*     */       
/* 153 */       System.out.println("Error: " + e.getMessage());
/* 154 */       System.out.println("sql: " + sql);
/* 155 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/* 157 */       db.close();
/*     */     } 
/*     */     
/* 160 */     return gs;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<grafico> getTop10Producto(int idEspecie, int idMercado) throws Exception {
/* 166 */     ArrayList<grafico> gs = new ArrayList<>();
/* 167 */     Statement stmt = null;
/* 168 */     String sql = "";
/* 169 */     ConnectionDB db = new ConnectionDB();
/* 170 */     Temporada temp = TemporadaDB.getMaxTemprada();
/* 171 */     int idTemp = temp.getIdTemporada();
/*     */     
/*     */     try {
/* 174 */       stmt = db.conn.createStatement();
/*     */       
/* 176 */       sql = "select d.codProducto,sum(a.valor) as valor from  (select codProducto,count(1) as valor from (SELECT distinct  r.idEspecie, r.codProductor, cd.codProducto ,'' as pp FROM restriciones r inner join cargaManualDetalle cd on (r.carga='M' and  cd.idCargaManualDetalle=r.idMailExcel  and r.idTemporada=" + 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 181 */         idTemp + ")" + 
/* 182 */         "union  " + 
/* 183 */         "SELECT distinct  r.idEspecie, r.codProductor, d.codProducto ,m.nMuestra " + 
/* 184 */         " FROM restriciones r  " + 
/* 185 */         " inner join mailExcel m on (r.carga!='M'  and m.idMailExcel =r.idMailExcel " + 
/* 186 */         " and r.idTemporada=" + idTemp + ")  " + 
/* 187 */         " inner join diccionario d on (d.codRemplazo=m.codProducto ) " + 
/* 188 */         " ) p " + 
/* 189 */         "where p.idEspecie=" + idEspecie + " " + 
/* 190 */         "group by codProducto order by count(1) desc " + 
/* 191 */         "limit 10) as a " + 
/* 192 */         "inner join   diccionario d on (d.codRemplazo=a.codProducto ) " + 
/* 193 */         " where a.codProducto not in ('MULTI','MULTIE','MULTIN')" + 
/* 194 */         " group by d.codProducto order by sum(a.valor) desc";
/*     */       
/* 196 */       ResultSet rs = stmt.executeQuery(sql);
/* 197 */       while (rs.next()) {
/* 198 */         grafico o = new grafico();
/*     */         
/* 200 */         o.setNombre(rs.getString("codProducto"));
/* 201 */         o.setValor(rs.getInt("valor"));
/* 202 */         gs.add(o);
/*     */       } 
/* 204 */       rs.close();
/* 205 */       stmt.close();
/* 206 */       db.conn.close();
/*     */     }
/* 208 */     catch (SQLException e) {
/*     */       
/* 210 */       System.out.println("Error: " + e.getMessage());
/* 211 */       System.out.println("sql: " + sql);
/* 212 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/* 214 */       db.close();
/*     */     } 
/*     */     
/* 217 */     return gs;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ArrayList<grafico> getProductor5(int idEspecie, int idMercado) throws Exception {
/* 222 */     ArrayList<grafico> gs = new ArrayList<>();
/* 223 */     Statement stmt = null;
/* 224 */     String sql = "";
/* 225 */     ConnectionDB db = new ConnectionDB();
/* 226 */     Temporada temp = TemporadaDB.getMaxTemprada();
/* 227 */     int idTemp = temp.getIdTemporada();
/*     */     
/*     */     try {
/* 230 */       stmt = db.conn.createStatement();
/*     */       
/* 232 */       sql = "select codProductor as nombre,count(bloqueado) as valor from (SELECT DISTINCT  r.idEspecie, r.codProductor, cd.codProducto, r.bloqueado  FROM restriciones r inner join cargaManualDetalle cd on (r.carga='M' and r.bloqueado='N' and cd.idCargaManualDetalle=r.idMailExcel  and r.idTemporada=" + 
/*     */ 
/*     */ 
/*     */         
/* 236 */         idTemp + ")" + 
/* 237 */         "union  " + 
/* 238 */         "SELECT DISTINCT r.idEspecie, r.codProductor, m.codProducto, r.bloqueado " + 
/* 239 */         "FROM restriciones r " + 
/* 240 */         "inner join mailExcel m on (r.carga='A'  and m.idMailExcel =r.idMailExcel and r.idTemporada=" + idTemp + ") " + 
/* 241 */         ") p " + 
/* 242 */         "where p.idEspecie=" + idEspecie + " " + 
/* 243 */         "group by codProductor " + 
/* 244 */         "HAVING COUNT(bloqueado) > 5;";
/*     */       
/* 246 */       sql = "select  codProductor as nombre,count(1) as valor,nMuestra  from (SELECT DISTINCT  r.idEspecie, r.codProductor, cd.codProducto ,cd.idCargaManual as nMuestra  FROM restriciones r   inner join cargaManualDetalle cd on (r.carga='M'  and cd.idCargaManualDetalle=r.idMailExcel  and r.idTemporada=" + 
/*     */ 
/*     */         
/* 249 */         idTemp + ") " + 
/* 250 */         " union   " + 
/* 251 */         " SELECT DISTINCT r.idEspecie, r.codProductor, d.codProducto ,m.nMuestra " + 
/* 252 */         " FROM restriciones r  " + 
/* 253 */         " inner join mailExcel m on (r.carga!='M' and  m.idMailExcel =r.idMailExcel and r.idTemporada=" + idTemp + ")  " + 
/* 254 */         " inner join   diccionario d on (d.codRemplazo=m.codProducto and d.codProducto not in ('MULTI','MULTIE','MULTIN')) ) p  " + 
/* 255 */         " where p.idEspecie=" + idEspecie + "  " + 
/* 256 */         " group by codProductor ,nMuestra " + 
/* 257 */         " HAVING COUNT(1) > 4; ";
/*     */       
/* 259 */       System.out.println(sql);
/*     */       
/* 261 */       ResultSet rs = stmt.executeQuery(sql);
/* 262 */       while (rs.next()) {
/* 263 */         grafico o = new grafico();
/*     */         
/* 265 */         o.setNombre(rs.getString("nombre"));
/* 266 */         o.setValor(rs.getInt("valor"));
/* 267 */         o.setTitle(rs.getString("nMuestra"));
/* 268 */         gs.add(o);
/*     */       } 
/* 270 */       rs.close();
/* 271 */       stmt.close();
/* 272 */       db.conn.close();
/*     */     }
/* 274 */     catch (SQLException e) {
/*     */       
/* 276 */       System.out.println("Error: " + e.getMessage());
/* 277 */       System.out.println("sql: " + sql);
/* 278 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } finally {
/* 280 */       db.close();
/*     */     } 
/*     */     
/* 283 */     return gs;
/*     */   }
/*     */   
/*     */   public static ArrayList<TemporadaZona> getTemporadaZona() throws Exception {
/* 287 */     ArrayList<TemporadaZona> lista = new ArrayList<>();
/* 288 */     ConnectionDB db = new ConnectionDB();
/* 289 */     Statement stmt = null;
/* 290 */     String query = "select idtemporada,nombreTemporada from temporada order by idtemporada desc";
/*     */     try {
/* 292 */       stmt = db.conn.createStatement();
/* 293 */       ResultSet rs = stmt.executeQuery(query);
/* 294 */       while (rs.next()) {
/* 295 */         TemporadaZona o = new TemporadaZona();
/*     */         
/* 297 */         o.setNombre(rs.getString(1));
/* 298 */         o.setValor(rs.getString(0));
/* 299 */         lista.add(o);
/*     */       } 
/* 301 */       rs.close();
/*     */     }
/* 303 */     catch (SQLException e) {
/* 304 */       System.out.println("Error: " + e.getMessage());
/* 305 */       System.out.println("sql: " + query);
/* 306 */       throw new Exception("getUsers: " + e.getMessage());
/*     */     } 
/*     */     
/* 309 */     stmt.close();
/* 310 */     db.close();
/*     */     
/* 312 */     return lista;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\homePageDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */