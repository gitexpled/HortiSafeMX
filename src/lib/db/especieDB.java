 package lib.db;
 
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.sql.Statement;
 import java.text.ParseException;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.Iterator;
 import lib.struc.cargaManual;
 import lib.struc.cargaManualDetalle;
 import lib.struc.especie;
 import lib.struc.filterSql;
 
 
 
 public class especieDB
 {
   public static especie getId(String id) {
/*  22 */     ConnectionDB db = new ConnectionDB();
/*  23 */     especie o = new especie();
/*  24 */     Statement stmt = null;
/*  25 */     String sql = "";
     
     try {
/*  28 */       stmt = db.conn.createStatement();
/*  29 */       sql = "Select * from especie where idEspecie=" + id;
/*  30 */       ResultSet rs = stmt.executeQuery(sql);
/*  31 */       if (rs.next()) {
 
 
         
/*  35 */         o.setEspecie(rs.getString("especie"));
/*  36 */         o.setIdEspecie(rs.getInt("idEspecie"));
       } 
 
       
/*  40 */       rs.close();
/*  41 */       stmt.close();
/*  42 */     } catch (Exception ex) {
       
/*  44 */       System.out.println("Error: " + ex.getMessage());
     } finally {
/*  46 */       db.close();
     } 
/*  48 */     return o;
   }
 
   
   public static void update(cargaManual o) throws Exception {
/*  53 */     PreparedStatement ps = null;
/*  54 */     String sql = "";
/*  55 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/*  56 */     ConnectionDB db = new ConnectionDB();
     try {
/*  58 */       db.conn.setAutoCommit(false);
       
/*  60 */       sql = "update  limites set codProducto=?,idMercado=?,idTipoProducto=?,idFuente=?,limite=?,  modificacion='" + 
/*  61 */         d + "' where idLimites='" + 
/*  62 */         "'";
/*  63 */       System.out.println(sql);
/*  64 */       ps = db.conn.prepareStatement(sql);
/*  65 */       ps.setString(1, o.getIdentificador());
 
 
       
/*  69 */       ps.executeUpdate();
/*  70 */       db.conn.commit();
/*  71 */       db.conn.close();
     }
/*  73 */     catch (SQLException e) {
       
/*  75 */       System.out.println("Error: " + e.getMessage());
/*  76 */       System.out.println("sql: " + sql);
/*  77 */       throw new Exception("updateLimite: " + e.getMessage());
/*  78 */     } catch (Exception e) {
/*  79 */       e.printStackTrace();
/*  80 */       System.out.println("Error2: " + e.getMessage());
/*  81 */       throw new Exception("updateLimite: " + e.getMessage());
     } finally {
       
/*  84 */       db.close();
     } 
   }
   
   public static int getAllcount(ArrayList<filterSql> filter) throws Exception {
/*  89 */     int total = 0;
/*  90 */     Statement stmt = null;
/*  91 */     String sql = "";
/*  92 */     ConnectionDB db = new ConnectionDB();
     
     try {
/*  95 */       stmt = db.conn.createStatement();
       
/*  97 */       sql = "SELECT count(1) FROM cargaManual ";
       
/*  99 */       if (filter.size() > 0) {
/* 100 */         String andSql = "";
/* 101 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 102 */         Iterator<filterSql> f = filter.iterator();
         
/* 104 */         while (f.hasNext()) {
           
/* 106 */           filterSql row = f.next();
/* 107 */           if (!row.getValue().equals("")) {
             
/* 109 */             if (row.getCampo().endsWith("_to")) {
               
/* 111 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 112 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 113 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
             }
/* 115 */             else if (row.getCampo().endsWith("_from")) {
               
/* 117 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 118 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 119 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
             } else {
               
/* 122 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like'%" + row.getValue() + "%'";
/* 123 */             }  andSql = " and ";
           } 
         } 
       } 
 
       
/* 129 */       ResultSet rs = stmt.executeQuery(sql);
/* 130 */       while (rs.next()) {
/* 131 */         total = rs.getInt(1);
       }
/* 133 */       rs.close();
/* 134 */       stmt.close();
/* 135 */       db.conn.close();
     
     }
/* 138 */     catch (SQLException e) {
       
/* 140 */       System.out.println("Error: " + e.getMessage());
/* 141 */       System.out.println("sql: " + sql);
/* 142 */       throw new Exception("getLimitesAll: " + e.getMessage());
     } finally {
/* 144 */       db.close();
     } 
/* 146 */     return total;
   }
 
 
   
	public static ArrayList<especie> getAll(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
		ArrayList<especie> arr = new ArrayList<>();
		Statement stmt = null;
	    String sql = "";
	    ConnectionDB db = new ConnectionDB();
     
	    try {
	    	stmt = db.conn.createStatement();
       
	    	sql = "SELECT * FROM especie ";
       
	    	if (filter.size() > 0) {
	    		String andSql = "";
	    		andSql = String.valueOf(andSql) + " WHERE ";
	    		Iterator<filterSql> f = filter.iterator();
         
	    		while (f.hasNext()) {
           
	    			filterSql row = f.next();
           
	    			if (!row.getValue().equals("")) {
             
	    				if (row.getCampo().endsWith("_to")) {
               
	    					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 176 */               	SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 177 */               	sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
	    				}	
/* 179 */             	else if (row.getCampo().endsWith("_from")) {
               
/* 181 */               	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 182 */               	SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 183 */               	sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
             			} else {
               
/* 186 */               	sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 187 */             	}  andSql = " and ";
	    			} 
	    		} 
	    	} 
       
	       if (!order.equals("")) {
	    	   sql = String.valueOf(sql) + " order by " + order;
	       }
       
	       if (length > 0) {
	    	   sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
	       }
	       System.out.println(sql);
	       ResultSet rs = stmt.executeQuery(sql);
	       while (rs.next()) {
	    	   especie o = new especie();
	    	   o.setIdEspecie(rs.getInt("idEspecie"));
	    	   o.setEspecie(rs.getString("especie"));
         
	    	   arr.add(o);
	       } 
	       rs.close();
	       stmt.close();
	       db.conn.close();
	    }
	    catch (SQLException e) {
       
	    	System.out.println("Error: " + e.getMessage());
	    	System.out.println("sql: " + sql);
	    	throw new Exception("getLimite: " + e.getMessage());
	    } finally {
	    	db.close();
	    } 
     
	    return arr;
	}
 
 
 
   
   public static ArrayList<especie> setSelectBox(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 227 */     ArrayList<especie> arr = new ArrayList<>();
/* 228 */     Statement stmt = null;
/* 229 */     String sql = "";
/* 230 */     ConnectionDB db = new ConnectionDB();
     
     try {
/* 233 */       stmt = db.conn.createStatement();
       
/* 235 */       sql = "SELECT e.* FROM especie  e inner join  parcelaVariedad p on (e.idEspecie=p.idEspecie)";
       
/* 237 */       if (filter.size() > 0) {
/* 238 */         String andSql = "";
/* 239 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 240 */         Iterator<filterSql> f = filter.iterator();
         
/* 242 */         while (f.hasNext()) {
           
/* 244 */           filterSql row = f.next();
           
/* 246 */           if (!row.getValue().equals("")) {
             
/* 248 */             if (row.getCampo().endsWith("_to")) {
               
/* 250 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 251 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 252 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
             }
/* 254 */             else if (row.getCampo().endsWith("_from")) {
               
/* 256 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 257 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 258 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
             } else {
               
/* 261 */               sql = String.valueOf(sql) + andSql + " p." + row.getCampo() + " = '" + row.getValue() + "'";
/* 262 */             }  andSql = " and ";
           } 
         } 
       } 
       
/* 267 */       if (!order.equals("")) {
/* 268 */         sql = String.valueOf(sql) + " order by " + order;
       }
       
/* 271 */       if (length > 0) {
/* 272 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
       }
/* 274 */       ResultSet rs = stmt.executeQuery(sql);
/* 275 */       while (rs.next()) {
/* 276 */         especie o = new especie();
/* 277 */         o.setIdEspecie(rs.getInt("idEspecie"));
/* 278 */         o.setEspecie(rs.getString("especie"));
         
/* 280 */         arr.add(o);
       } 
/* 282 */       rs.close();
/* 283 */       stmt.close();
/* 284 */       db.conn.close();
     }
/* 286 */     catch (SQLException e) {
       
/* 288 */       System.out.println("Error: " + e.getMessage());
/* 289 */       System.out.println("sql: " + sql);
/* 290 */       throw new Exception("getLimite: " + e.getMessage());
     } finally {
/* 292 */       db.close();
     } 
     
/* 295 */     return arr;
   }
 
   
   public static boolean insert(cargaManual o) throws ParseException {
/* 300 */     ConnectionDB db = new ConnectionDB();
/* 301 */     PreparedStatement ps = null;
/* 302 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/* 303 */     boolean resp = true;
/* 304 */     String sql = "";
     
     try {
/* 307 */       sql = "INSERT INTO cargaManual(`fecha`,`idUsuario`,`creado`,`modificado`,`laboratorio`,`identificador`,codProductor) Values (now(),1,now(),now(),?,?,?)";
       
/* 309 */       ps = db.conn.prepareStatement(sql);
/* 310 */       ps.setString(1, o.getLaboratorio());
/* 311 */       ps.setString(2, o.getIdentificador());
/* 312 */       ps.setString(3, o.getCodProductor());
 
 
       
/* 316 */       ps.executeUpdate();
/* 317 */       ResultSet rs = ps.getGeneratedKeys();
/* 318 */       int resultado = 0;
/* 319 */       if (rs.next()) {
/* 320 */         resultado = rs.getInt(1);
       }
/* 322 */       rs.close();
       
/* 324 */       Iterator<cargaManualDetalle> f = o.getDetalle().iterator();
       
/* 326 */       while (f.hasNext()) {
/* 327 */         cargaManualDetalle row = f.next();
         
/* 329 */         System.out.println(row.getCodProducto());
/* 330 */         sql = "INSERT INTO cargaManualDetalle(`idCargaManual`,`codProducto`,`limite`) Values (?,?,?)";
         
/* 332 */         ps = db.conn.prepareStatement(sql);
/* 333 */         ps.setInt(1, resultado);
/* 334 */         ps.setString(2, row.getCodProducto());
/* 335 */         ps.setString(3, row.getLimite());
 
 
         
/* 339 */         ps.executeUpdate();
       } 
 
 
       
/* 344 */       ps.close();
     }
/* 346 */     catch (Exception ex) {
       
/* 348 */       System.out.println(ex.getMessage());
     } finally {
       
/* 351 */       db.close();
     } 
/* 353 */     return resp;
   }
   
   public static ArrayList<especie> setSelectBox2(
		   String codProductor,
		   String codParcela,
		   String codTurno) throws Exception {
	   ArrayList<especie> arr = new ArrayList<>();
	   Statement stmt = null;
	   String sql = "";
	   ConnectionDB db = new ConnectionDB();
     
	   try {
		   stmt = db.conn.createStatement();
       
		   sql = "select distinct p.idEspecie,e.especie "
		   		+ "from parcelaVariedad p "
		   		+"left join especie e on e.idEspecie = p.idEspecie "
		   		+"where p.codProductor = '"+codProductor+"' and p.codParcela = '"+codParcela+"' and p.codTurno = '"+codTurno+"'";

		   ResultSet rs = stmt.executeQuery(sql);
		   System.out.println(sql);
		   while (rs.next()) {
			   especie o = new especie();
			   o.setIdEspecie(rs.getInt("idEspecie"));
			   o.setEspecie(rs.getString("especie"));
			   arr.add(o);
		   } 
		   rs.close();
		   stmt.close();
		   db.conn.close();
     }
	   catch (SQLException e) {
		   System.out.println("Error: " + e.getMessage());
		   System.out.println("sql: " + sql);
		   throw new Exception("getLimite: " + e.getMessage());
	   } finally {
		   db.close();
	   } 
	   return arr;
   }
 }

