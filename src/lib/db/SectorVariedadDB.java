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
 import lib.struc.SectorVariedad;
 import lib.struc.filterSql;
 
 
 
 
 
 
 
 public class SectorVariedadDB
 {
   public static SectorVariedad get(String id) throws Exception {
/*  24 */     SectorVariedad o = null;
/*  25 */     ConnectionDB db = new ConnectionDB();
/*  26 */     Statement stmt = null;
/*  27 */     String sql = "";
     
     try {
/*  30 */       stmt = db.conn.createStatement();
       
/*  32 */       sql = "SELECT * FROM sectorVariedad where id='" + id + "'";
/*  33 */       System.out.println("sql: " + sql);
       
/*  35 */       ResultSet rs = stmt.executeQuery(sql);
/*  36 */       if (rs.next()) {
/*  37 */         o = new SectorVariedad();
/*  38 */         o.setId(rs.getInt("id"));
/*  39 */         o.setCodParcela(rs.getString("codParcela"));
/*  40 */         o.setIdVariedad(rs.getString("idVariedad"));
/*  41 */         o.setCreado(rs.getDate("creado"));
/*  42 */         o.setModificado(rs.getDate("modificado"));
/*  43 */         o.setIdUser(rs.getInt("idUser"));
       } else {
         
/*  46 */         o = null;
       } 
       
/*  49 */       rs.close();
/*  50 */       stmt.close();
/*  51 */       db.conn.close();
     }
/*  53 */     catch (SQLException e) {
       
/*  55 */       System.out.println("Error: " + e.getMessage());
/*  56 */       System.out.println("sql: " + sql);
/*  57 */       throw new Exception("getUser: " + e.getMessage());
     } finally {
/*  59 */       db.close();
     } 
     
/*  62 */     return o;
   }
 
   
   public static void update(SectorVariedad o) throws Exception {
/*  67 */     PreparedStatement ps = null;
/*  68 */     String sql = "";
/*  69 */     String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/*  70 */     ConnectionDB db = new ConnectionDB();
     try {
/*  72 */       db.conn.setAutoCommit(false);
       
/*  74 */       sql = "update  sectorVariedad set codParcela=?,idVariedad=?, modificado='" + d + "' where id='" + o.getId() + "'";
/*  75 */       System.out.println("sql: " + sql);
/*  76 */       ps = db.conn.prepareStatement(sql);
/*  77 */       ps.setString(1, o.getCodParcela());
/*  78 */       ps.setString(2, o.getIdVariedad());
 
       
/*  81 */       ps.executeUpdate();
/*  82 */       db.conn.commit();
/*  83 */       db.conn.close();
     }
/*  85 */     catch (SQLException e) {
       
/*  87 */       System.out.println("Error: " + e.getMessage());
/*  88 */       System.out.println("sql: " + sql);
/*  89 */       throw new Exception("sepPfx: " + e.getMessage());
/*  90 */     } catch (Exception e) {
/*  91 */       e.printStackTrace();
/*  92 */       System.out.println("Error2: " + e.getMessage());
/*  93 */       throw new Exception("sepPfx: " + e.getMessage());
     } finally {
       
/*  96 */       db.close();
     } 
   }
 
 
   
   public static boolean delete(String id) throws Exception {
/* 103 */     PreparedStatement ps = null;
/* 104 */     String sql = "";
/* 105 */     ConnectionDB db = new ConnectionDB();
/* 106 */     boolean resp = true;
     try {
/* 108 */       db.conn.setAutoCommit(false);
       
/* 110 */       sql = "DELETE t1 ";
/* 111 */       sql = String.valueOf(sql) + "FROM restriciones t1 ";
/* 112 */       sql = String.valueOf(sql) + "inner join variedad v on (v.idVariedad=t1.idVariedad) ";
/* 113 */       sql = String.valueOf(sql) + "INNER JOIN parcelaVariedad t2 ON (t1.codProductor=t2.codProductor and t1.codParcela=t2.codParcela and t1.codTurno=t2.codTurno and t2.idVariedad=v.idVariedad) ";
/* 114 */       sql = String.valueOf(sql) + "WHERE  id='" + id + "';";
/* 115 */       System.out.println("sql: " + sql);
/* 116 */       ps = db.conn.prepareStatement(sql);
/* 117 */       ps.executeUpdate();
 
 
 
 
 
 
 
 
 
       
/* 128 */       sql = "delete  from parcelaVariedad  where id='" + id + "';";
/* 129 */       System.out.println("sql: " + sql);
/* 130 */       ps = db.conn.prepareStatement(sql);
/* 131 */       ps.executeUpdate();
       
/* 133 */       db.conn.commit();
/* 134 */       db.conn.close();
     }
/* 136 */     catch (SQLException e) {
       
/* 138 */       resp = false;
/* 139 */       System.out.println("Error: " + e.getMessage());
/* 140 */       System.out.println("sql: " + sql);
/* 141 */       throw new Exception("sepPfx: " + e.getMessage());
/* 142 */     } catch (Exception e) {
/* 143 */       e.printStackTrace();
/* 144 */       resp = false;
/* 145 */       System.out.println("Error2: " + e.getMessage());
/* 146 */       throw new Exception("sepPfx: " + e.getMessage());
     } finally {
       
/* 149 */       db.close();
     } 
     
/* 152 */     return resp;
   }
 
 
   
   public static int getBloqueadosHoy() throws Exception {
/* 158 */     int total = 0;
/* 159 */     Statement stmt = null;
/* 160 */     String sql = "";
/* 161 */     ConnectionDB db = new ConnectionDB();
     
     try {
/* 164 */       stmt = db.conn.createStatement();
       
/* 166 */       sql = "select sum(p) from (SELECT count(DISTINCT codParcela) as p FROM restriciones where bloqueado='N' and fecha = sysdate() group by codParcela) as s ";
       
/* 168 */       ResultSet rs = stmt.executeQuery(sql);
/* 169 */       while (rs.next()) {
/* 170 */         total = rs.getInt(1);
       }
/* 172 */       rs.close();
/* 173 */       stmt.close();
/* 174 */       db.conn.close();
     }
/* 176 */     catch (SQLException e) {
       
/* 178 */       System.out.println("Error: " + e.getMessage());
/* 179 */       System.out.println("sql: " + sql);
/* 180 */       throw new Exception("getUsersAll: " + e.getMessage());
     } finally {
/* 182 */       db.close();
     } 
     
/* 185 */     return total;
   }
 
   
   public static int getAll(ArrayList<filterSql> filter) throws Exception {
/* 190 */     int total = 0;
/* 191 */     Statement stmt = null;
/* 192 */     String sql = "";
/* 193 */     ConnectionDB db = new ConnectionDB();
     
     try {
/* 196 */       stmt = db.conn.createStatement();
       
/* 198 */       sql = "SELECT count(1) FROM parcelaVariedad pv left join variedad v on (v.cod=pv.id) left join sector t on (pv.codParcela=t.codParcela and pv.codTurno=t.codTurno )";
       
/* 200 */       if (filter.size() > 0) {
/* 201 */         String andSql = "";
/* 202 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 203 */         Iterator<filterSql> f = filter.iterator();
         
/* 205 */         while (f.hasNext()) {
/* 206 */           filterSql row = f.next();
/* 207 */           if (!row.getValue().equals("")) {
/* 208 */             if (row.getCampo().endsWith("_to")) {
/* 209 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 210 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 211 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/* 212 */             } else if (row.getCampo().endsWith("_from")) {
/* 213 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 214 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 215 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
             }
/* 217 */             else if (row.getCampo().equals("cod")) {
/* 218 */               sql = String.valueOf(sql) + andSql + "v." + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 219 */             } else if (row.getCampo().equals("codProductor")) {
/* 220 */               sql = String.valueOf(sql) + andSql + "t." + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 221 */             } else if (row.getCampo().equals("codVariedad")) {
/* 222 */               sql = String.valueOf(sql) + andSql + "pv.idVariedad" + " like '%" + row.getValue() + "%'";
             } else {
/* 224 */               sql = String.valueOf(sql) + andSql + "pv." + row.getCampo() + " like '%" + row.getValue() + "%'";
             } 
/* 226 */             andSql = " and ";
           } 
         } 
       } 
       
/* 231 */       System.out.println(sql);
/* 232 */       ResultSet rs = stmt.executeQuery(sql);
/* 233 */       while (rs.next()) {
/* 234 */         total = rs.getInt(1);
       }
/* 236 */       rs.close();
/* 237 */       stmt.close();
/* 238 */       db.conn.close();
     }
/* 240 */     catch (SQLException e) {
       
/* 242 */       System.out.println("Error: " + e.getMessage());
/* 243 */       System.out.println("sql: " + sql);
/* 244 */       throw new Exception("getUsersAll: " + e.getMessage());
     } finally {
/* 246 */       db.close();
     } 
     
/* 249 */     return total;
   }
   
   public static ArrayList<SectorVariedad> getAll(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
	   ArrayList<SectorVariedad> Parcelaes = new ArrayList<>();
	   Statement stmt = null;
	   String sql = "";
	   ConnectionDB db = new ConnectionDB();
     
	   try {
		   stmt = db.conn.createStatement();
       
		   sql = "SELECT distinct pv.*,v.cod,t.codProductor FROM parcelaVariedad pv "
		   		+ "left join variedad v on (v.idVariedad=pv.idVariedad) "
		   		+ "left join sector t on (pv.codParcela=t.codParcela and pv.codTurno=t.codTurno and pv.codProductor = t.codProductor)";
       
		   if (filter.size() > 0) {
			   String andSql = "";
			   andSql = String.valueOf(andSql) + " WHERE ";
			   Iterator<filterSql> f = filter.iterator();
         
			   while (f.hasNext()) {
				   filterSql row = f.next();
           
				   if (!row.getValue().equals("")) {
					   if (row.getCampo().endsWith("_to")) {
						   SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
						   SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
						   sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
					   } else if (row.getCampo().endsWith("_from")) {
						   SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
						   SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
						   sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
					   }
					   else if (row.getCampo().equals("cod")) {
						   sql = String.valueOf(sql) + andSql + "v." + row.getCampo() + " like '%" + row.getValue() + "%' and t.codProductor = like '%" + row.getValue() + "%'";
					   } else if (row.getCampo().equals("codProductor")) {
						   sql = String.valueOf(sql) + andSql + "t." + row.getCampo() + " like '%" + row.getValue() + "%'";
					   } else if (row.getCampo().equals("codVariedad")) {
						   sql = String.valueOf(sql) + andSql + "pv.idVariedad" + " like '%" + row.getValue() + "%'";
					   } else {
						   sql = String.valueOf(sql) + andSql + "pv." + row.getCampo() + " like '%" + row.getValue() + "%'";
					   } 
					   andSql = " and ";
				   } 
			   } 
		   } 
       
		   if (!order.equals("")) {
			   sql = String.valueOf(sql) + " order by ";
		   }
		   if (length > 0) {
			   sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
		   }
		   System.out.println(sql);
		   ResultSet rs = stmt.executeQuery(sql);
		   while (rs.next()) {
			   SectorVariedad row = new SectorVariedad();
			   row.setId(rs.getInt("id"));
			   row.setCodProductor(rs.getString("codProductor"));
			   row.setCodParcela(rs.getString("codParcela"));
			   row.setCodTurno(rs.getString("codTurno"));
			   row.setIdVariedad(rs.getString("idVariedad"));
			   row.setCodVariedad(rs.getString("cod"));
			   row.setCreado(rs.getDate("creado"));
			   row.setModificado(rs.getDate("modificado"));
			   row.setIdUser(rs.getInt("idUser"));
         
			   Parcelaes.add(row);
		   } 
		   rs.close();
		   stmt.close();
		   db.conn.close();
		   System.out.println("sql: " + sql);
	   }
	   catch (SQLException e) {
		   System.out.println("Error: " + e.getMessage());
		   System.out.println("sql: " + sql);
		   throw new Exception("getUsers: " + e.getMessage());
	   } finally {
		   db.close();
	   } 
	   return Parcelaes;
   }
   
   public static boolean insert(SectorVariedad o) throws ParseException {
	   ConnectionDB db = new ConnectionDB();
	   Statement stmt = null;
	   String d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
	   boolean resp = true;
	   String sql = "";
     
	   try {
		   sql = "INSERT INTO parcelaVariedad(codProductor, codParcela, codTurno, idVariedad, creado, modificado, idUser,idEspecie)Values ('" + 
				   o.getCodProductor() + "','" + o.getCodParcela() + "'," + 
				   "'" + o.getCodTurno() + "'," + 
				   o.getIdVariedad() + ",'" + d + "','" + d + "'," + o.getIdUser() + "," + o.getIdEspecie() + ")";
       
		   System.out.println(sql);
		   stmt = db.conn.createStatement();
		   resp = stmt.execute(sql);
		   stmt.close();
		   //TemporadaDB.setCreateRestriciones();
	   }
	   catch (Exception ex) {
		   System.out.println(ex.getMessage());
		   ex.printStackTrace();
     } finally {
    	 db.close();
     } 
	   return resp;
   }
 }