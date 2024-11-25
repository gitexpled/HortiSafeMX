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
 import lib.struc.Parcela;
 import lib.struc.Variedad;
 import lib.struc.filterSql;
 
 
 
 
 
 
 public class ParcelaDB
 {
   public static Parcela get(String codProductor, String id) throws Exception {
/*  24 */     Parcela o = null;
/*  25 */     ConnectionDB db = new ConnectionDB();
/*  26 */     Statement stmt = null;
/*  27 */     String sql = "";
     
     try {
/*  30 */       stmt = db.conn.createStatement();
       
/*  32 */       sql = "SELECT * FROM parcela where codProductor='" + codProductor + "' and codParcela='" + id + "'";
/*  33 */       System.out.println("sql: " + sql);
       
/*  35 */       ResultSet rs = stmt.executeQuery(sql);
/*  36 */       if (rs.next()) {
/*  37 */         o = new Parcela();
/*  38 */         o.setCodigo(rs.getString("codParcela"));
/*  39 */         o.setCodigoProductor(rs.getString("codProductor"));
/*  40 */         o.setNombre(rs.getString("nombre"));
         
/*  42 */         o.setCreado(rs.getDate("creado"));
/*  43 */         o.setModificado(rs.getDate("modificado"));
/*  44 */         o.setIdUser(rs.getInt("idUser"));
       
       }
       else {
 
         
/*  50 */         o = null;
       } 
       
/*  53 */       rs.close();
/*  54 */       stmt.close();
/*  55 */       db.conn.close();
     }
/*  57 */     catch (SQLException e) {
       
/*  59 */       System.out.println("Error: " + e.getMessage());
/*  60 */       System.out.println("sql: " + sql);
/*  61 */       throw new Exception("getUser: " + e.getMessage());
     } finally {
/*  63 */       db.close();
     } 
     
/*  66 */     return o;
   }
 
   
   public static void update(Parcela prod) throws Exception {
/*  71 */     PreparedStatement ps = null;
/*  72 */     String sql = "";
/*  73 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/*  74 */     ConnectionDB db = new ConnectionDB();
     try {
/*  76 */       db.conn.setAutoCommit(false);
       
/*  78 */       sql = "update  parcela set nombre=?, modificado='" + d + 
/*  79 */         "', idUser=? where codProductor='" + prod.getCodigoProductor() + "' and  codParcela='" + prod.getCodigo() + "'";
       
/*  81 */       ps = db.conn.prepareStatement(sql);
/*  82 */       ps.setString(1, prod.getNombre());
       
/*  84 */       ps.setInt(2, prod.getIdUser());
 
       
/*  87 */       ps.executeUpdate();
/*  88 */       db.conn.commit();
/*  89 */       db.conn.close();
     }
/*  91 */     catch (SQLException e) {
       
/*  93 */       System.out.println("Error: " + e.getMessage());
/*  94 */       System.out.println("sql: " + sql);
/*  95 */       throw new Exception("sepPfx: " + e.getMessage());
/*  96 */     } catch (Exception e) {
/*  97 */       e.printStackTrace();
/*  98 */       System.out.println("Error2: " + e.getMessage());
/*  99 */       throw new Exception("sepPfx: " + e.getMessage());
     } finally {
       
/* 102 */       db.close();
     } 
   }
 
 
 
 
 
 
   
   public static int getBloqueadosHoy() throws Exception {
/* 113 */     int total = 0;
/* 114 */     Statement stmt = null;
/* 115 */     String sql = "";
/* 116 */     ConnectionDB db = new ConnectionDB();
     
     try {
/* 119 */       stmt = db.conn.createStatement();
       
/* 121 */       sql = "select sum(p) from (SELECT count(DISTINCT codParcela) as p FROM restriciones where bloqueado='N' and fecha = sysdate() group by codParcela) as s ";
 
       
/* 124 */       ResultSet rs = stmt.executeQuery(sql);
/* 125 */       while (rs.next()) {
/* 126 */         total = rs.getInt(1);
       }
/* 128 */       rs.close();
/* 129 */       stmt.close();
/* 130 */       db.conn.close();
     }
/* 132 */     catch (SQLException e) {
       
/* 134 */       System.out.println("Error: " + e.getMessage());
/* 135 */       System.out.println("sql: " + sql);
/* 136 */       throw new Exception("getUsersAll: " + e.getMessage());
     } finally {
/* 138 */       db.close();
     } 
     
/* 141 */     return total;
   }
 
   
   public static int getAll(ArrayList<filterSql> filter) throws Exception {
/* 146 */     int total = 0;
/* 147 */     Statement stmt = null;
/* 148 */     String sql = "";
/* 149 */     ConnectionDB db = new ConnectionDB();
     
     try {
/* 152 */       stmt = db.conn.createStatement();
       
/* 154 */       sql = "SELECT count(1) FROM parcela ";
       
/* 156 */       if (filter.size() > 0) {
/* 157 */         String andSql = "";
/* 158 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 159 */         Iterator<filterSql> f = filter.iterator();
         
/* 161 */         while (f.hasNext()) {
/* 162 */           filterSql row = f.next();
/* 163 */           if (!row.getValue().equals("")) {
/* 164 */             if (row.getCampo().endsWith("_to")) {
/* 165 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 166 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 167 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + 
/* 168 */                 sqlDate.format(formatter.parse(row.getValue())) + "'";
/* 169 */             } else if (row.getCampo().endsWith("_from")) {
/* 170 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 171 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 172 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + 
/* 173 */                 sqlDate.format(formatter.parse(row.getValue())) + "'";
             } else {
/* 175 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like'%" + row.getValue() + "%'";
/* 176 */             }  andSql = " and ";
           } 
         } 
       } 
 
       
/* 182 */       ResultSet rs = stmt.executeQuery(sql);
/* 183 */       while (rs.next()) {
/* 184 */         total = rs.getInt(1);
       }
/* 186 */       rs.close();
/* 187 */       stmt.close();
/* 188 */       db.conn.close();
     }
/* 190 */     catch (SQLException e) {
       
/* 192 */       System.out.println("Error: " + e.getMessage());
/* 193 */       System.out.println("sql: " + sql);
/* 194 */       throw new Exception("getUsersAll: " + e.getMessage());
     } finally {
/* 196 */       db.close();
     } 
     
/* 199 */     return total;
   }
 
 
   
   public static ArrayList<Variedad> getAllByVariables5(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 205 */     ArrayList<Variedad> Variedad = new ArrayList<>();
/* 206 */     Statement stmt = null;
/* 207 */     String sql = "";
/* 208 */     ConnectionDB db = new ConnectionDB();
     
     try {
/* 211 */       stmt = db.conn.createStatement();
       
/* 213 */       sql = "select * from variedad wheree idVariedad in ( select idVariedad from parcelaVariedad pv where codProductor = 'M1002' and pv.codParcela = '2' and pv.codTurno = '1' and pv.idEspecie = '5') ";
 
 
 
       
/* 218 */       System.out.println("sql: " + sql);
/* 219 */       ResultSet rs = stmt.executeQuery(sql);
/* 220 */       while (rs.next()) {
/* 221 */         Variedad o = new Variedad();
         
/* 223 */         o.setIdVariedad(rs.getInt("idVariedad"));
/* 224 */         o.setCod(rs.getString("cod"));
/* 225 */         o.setNombre(rs.getString("nombre"));
         
/* 227 */         Variedad.add(o);
       } 
/* 229 */       rs.close();
/* 230 */       stmt.close();
/* 231 */       db.conn.close();
     }
/* 233 */     catch (SQLException e) {
       
/* 235 */       System.out.println("Error: " + e.getMessage());
/* 236 */       System.out.println("sql: " + sql);
/* 237 */       throw new Exception("getUsers: " + e.getMessage());
     } finally {
/* 239 */       db.close();
     } 
     
/* 242 */     return Variedad;
   }
 
   
   public static ArrayList<Parcela> getAll(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 247 */     ArrayList<Parcela> Parcelas = new ArrayList<>();
/* 248 */     Statement stmt = null;
/* 249 */     String sql = "";
/* 250 */     ConnectionDB db = new ConnectionDB();
     
     try {
/* 253 */       stmt = db.conn.createStatement();
       
/* 255 */       sql = "SELECT * FROM parcela ";
       
/* 257 */       if (filter.size() > 0) {
/* 258 */         String andSql = "";
/* 259 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 260 */         Iterator<filterSql> f = filter.iterator();
         
/* 262 */         while (f.hasNext()) {
/* 263 */           filterSql row = f.next();
           
/* 265 */           if (!row.getValue().equals("")) {
/* 266 */             if (row.getCampo().endsWith("_to")) {
/* 267 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 268 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 269 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + 
/* 270 */                 sqlDate.format(formatter.parse(row.getValue())) + "'";
/* 271 */             } else if (row.getCampo().endsWith("_from")) {
/* 272 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 273 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 274 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + 
/* 275 */                 sqlDate.format(formatter.parse(row.getValue())) + "'";
             } else {
/* 277 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " = '" + row.getValue() + "'";
/* 278 */             }  andSql = " and ";
           } 
         } 
       } 
       
/* 283 */       if (!order.equals("")) {
/* 284 */         sql = String.valueOf(sql) + " order by ";
       }
       
/* 287 */       if (length > 0) {
/* 288 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
       }
/* 290 */       System.out.println("sql: " + sql);
/* 291 */       ResultSet rs = stmt.executeQuery(sql);
/* 292 */       while (rs.next()) {
/* 293 */         Parcela o = new Parcela();
         
/* 295 */         o.setCodigo(rs.getString("codParcela"));
/* 296 */         o.setCodigoProductor(rs.getString("codProductor"));
/* 297 */         o.setNombre(rs.getString("nombre"));
         
/* 299 */         o.setCreado(rs.getDate("creado"));
/* 300 */         o.setModificado(rs.getDate("modificado"));
/* 301 */         o.setIdUser(rs.getInt("idUser"));
 
         
/* 304 */         Parcelas.add(o);
       } 
/* 306 */       rs.close();
/* 307 */       stmt.close();
/* 308 */       db.conn.close();
     }
/* 310 */     catch (SQLException e) {
       
/* 312 */       System.out.println("Error: " + e.getMessage());
/* 313 */       System.out.println("sql: " + sql);
/* 314 */       throw new Exception("getUsers: " + e.getMessage());
     } finally {
/* 316 */       db.close();
     } 
     
/* 319 */     return Parcelas;
   }
   
   public static boolean insert(Parcela o) throws ParseException {
/* 323 */     ConnectionDB db = new ConnectionDB();
/* 324 */     Statement stmt = null;
/* 325 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/* 326 */     boolean resp = true;
/* 327 */     String sql = "";
     
     try {
/* 330 */       sql = "INSERT INTO parcela(codParcela,nombre,creado,modificado,idUser,codProductor) Values ('" + 
/* 331 */         o.getCodigo() + "','" + o.getNombre() + "','" + d + "','" + d + "'," + 
/* 332 */         o.getIdUser() + ",'" + o.getCodigoProductor() + "')";
/* 333 */       stmt = db.conn.createStatement();
/* 334 */       resp = stmt.execute(sql);
/* 335 */       stmt.close();
/* 336 */       //TemporadaDB.setCreateRestriciones();
     }
/* 338 */     catch (Exception ex) {
/* 339 */       System.out.println(ex.getMessage());
     } finally {
/* 341 */       db.close();
     } 
/* 343 */     return resp;
   }
   public static ArrayList<Variedad> getAllByVariables3(
		   String codProductor,
		   String parcela_,
		   String especie_,
		   String turno_) throws Exception {
	   ArrayList<Variedad> Variedad = new ArrayList<>();
	   Statement stmt = null;
	   String sql = "";
	   ConnectionDB db = new ConnectionDB();
     try {
    	 stmt = db.conn.createStatement();
    	 sql = "SELECT distinct "
				+"pv.idVariedad,v.cod,"
				  +"(select nombre from variedad where cod = v.cod ) as nombre "
				+"FROM "
				    +"parcelaVariedad pv "
				        +"LEFT JOIN "
				    +"variedad v ON (v.idVariedad = pv.idVariedad) "
				        +"LEFT JOIN "
				    +"sector t ON (pv.codParcela = t.codParcela "
				        +"AND cast(pv.codTurno as signed) = t.codTurno) "
				+"WHERE "
				    +"t.codProductor = "+codProductor+" "
				    +"and pv.codProductor = "+codProductor+" "
				        +"AND pv.codParcela = "+parcela_+" "
				        +"AND pv.codTurno  = "+turno_+" "
				        +"and pv.idEspecie = "+especie_+"";
 
    	 System.out.println("sql: " + sql);
    	 ResultSet rs = stmt.executeQuery(sql);
    	 while (rs.next()) {
    		 Variedad o = new Variedad();
         
    		 o.setIdVariedad(rs.getInt("idVariedad"));
    		 o.setCod(rs.getString("cod"));
    		 o.setNombre(rs.getString("nombre"));
         
    		 Variedad.add(o);
    	 } 
    	 rs.close();
    	 stmt.close();
    	 db.conn.close();
     }
     catch (SQLException e) {
       
    	 System.out.println("Error: " + e.getMessage());
    	 System.out.println("sql: " + sql);
    	 throw new Exception("getUsers: " + e.getMessage());
     } finally {
    	 db.close();
     } 
     return Variedad;
   }
 }

