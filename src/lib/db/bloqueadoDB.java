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
/*     */ public class bloqueadoDB
/*     */ {
/*     */   public static bloqueo getId(String id) {
/*  22 */     ConnectionDB db = new ConnectionDB();
/*  23 */     bloqueo o = null;
/*  24 */     Statement stmt = null;
/*  25 */     String sql = "";
/*     */     
/*     */     try {
/*  28 */       stmt = db.conn.createStatement();
/*  29 */       sql = "Select pb.*,u.user from productorBloqueo pb inner join user u on (pb.idUsuario=u.idUser) where pb.idBloqueo=" + id;
/*  30 */       ResultSet rs = stmt.executeQuery(sql);
/*  31 */       if (rs.next()) {
/*     */         
/*  33 */         o = new bloqueo();
/*  34 */         o.setIdBloqueo(rs.getInt("idBloqueo"));
/*  35 */         o.setCodProductor(rs.getString("codProductor"));
/*  36 */         o.setIdEspecie(rs.getInt("idEspecie"));
/*  37 */         o.setIdMercado(rs.getInt("idMercado"));
/*  38 */         o.setComentario(rs.getString("cometario"));
/*     */         
/*  40 */         o.setActivo(rs.getString("activo"));
/*  41 */         o.setB(rs.getString("b"));
/*  42 */         o.setCreado(rs.getString("creado"));
/*  43 */         o.setModificado(rs.getString("modificado"));
/*  44 */         o.setUsuario(rs.getString("user"));
/*  45 */         o.setIdVariedad(rs.getInt("idVariedad"));
/*     */       } 
/*     */       
/*  48 */       rs.close();
/*  49 */       stmt.close();
/*  50 */     } catch (Exception ex) {
/*     */       
/*  52 */       System.out.println("Error: " + ex.getMessage());
/*     */     } finally {
/*  54 */       db.close();
/*     */     } 
/*  56 */     return o;
/*     */   }
/*     */ 
/*     */   
	public static void update(bloqueo o) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			db.conn.setAutoCommit(false);
			sql = "update  productorBloqueo set codProductor=?,idEspecie=?,cometario=?,b=?,idUsuario=?,  "
					+ "idTemporada=?,  activo=?,idMercado=?,modificado=now(), idVariedad=? ,"
					+"codParcela = ?, codTurno = ? "
					+ "where idBloqueo=?";

			ps = db.conn.prepareStatement(sql);
			ps.setString(1, o.getCodProductor());
			ps.setInt(2, o.getIdEspecie());
			ps.setString(3, o.getComentario());
			ps.setString(4, o.getB());
			ps.setInt(5, o.getIdUsuario());
			ps.setInt(6, o.getIdTemporada());
			ps.setString(7, o.getActivo());
			ps.setInt(8, o.getIdMercado());
			ps.setInt(9, o.getIdVariedad());
			ps.setString(10, o.getCodParcela());
			ps.setString(11, o.getCodTurno());
			ps.setInt(12, o.getIdBloqueo());
			

			ps.executeUpdate();
			int resultado = o.getIdBloqueo();
			sql = "DELETE FROM prdBloqueoVariedad where idPrdBloqueo = ?";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, resultado);
			ps.executeUpdate();
			String[] vari = o.getVariedades().split(",");
			
			for (int i = 0; i < vari.length; i++) {
				sql = "INSERT INTO prdBloqueoVariedad(`idPrdBloqueo`,`idVariedad`) Values (?,?)";
  
				ps = db.conn.prepareStatement(sql);
				ps.setInt(1, resultado);
				ps.setString(2, vari[i]);
				ps.executeUpdate();
			} 
			sql = "DELETE FROM prdBloqueoMercado where idPrdBloqueo = ?";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, resultado);
			ps.executeUpdate();
			String[] mercados = o.getMercados().split(",");
			
			for (int j = 0; j < mercados.length; j++) {
				sql = "INSERT INTO prdBloqueoMercado(`idPrdBloqueo`,`idMercado`) Values (?,?)";
 
				ps = db.conn.prepareStatement(sql);
				ps.setInt(1, resultado);
				ps.setString(2, mercados[j]);
				System.out.println(ps);
				ps.executeUpdate();
			} 
			db.conn.commit();
			db.conn.close();
		}
		catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("updateLimite: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error2: " + e.getMessage());
			throw new Exception("updateLimite: " + e.getMessage());
		} finally {
			db.close();
		} 
	}

/*     */   public static int getAllcount(ArrayList<filterSql> filter) throws Exception {
/* 147 */     int total = 0;
/* 148 */     Statement stmt = null;
/* 149 */     String sql = "";
/* 150 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 153 */       stmt = db.conn.createStatement();
/*     */       
/* 155 */       sql = "SELECT count(1) FROM vw_bloqueo ";
/*     */       
/* 157 */       if (filter.size() > 0) {
/* 158 */         String andSql = "";
/* 159 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 160 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 162 */         while (f.hasNext()) {
/*     */           
/* 164 */           filterSql row = f.next();
/* 165 */           if (!row.getValue().equals("")) {
/*     */             
/* 167 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 169 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 170 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 171 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 173 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 175 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 176 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 177 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 180 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like'%" + row.getValue() + "%'";
/* 181 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 187 */       ResultSet rs = stmt.executeQuery(sql);
/* 188 */       while (rs.next()) {
/* 189 */         total = rs.getInt(1);
/*     */       }
/* 191 */       rs.close();
/* 192 */       stmt.close();
/* 193 */       db.conn.close();
/*     */     
/*     */     }
/* 196 */     catch (SQLException e) {
/*     */       
/* 198 */       System.out.println("Error: " + e.getMessage());
/* 199 */       System.out.println("sql: " + sql);
/* 200 */       throw new Exception("getLimitesAll: " + e.getMessage());
/*     */     } finally {
/* 202 */       db.close();
/*     */     } 
/* 204 */     return total;
/*     */   }
/*     */ 
/*     */ 
/*     */   
	public static ArrayList<bloqueo> getAll(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 210 */     ArrayList<bloqueo> arr = new ArrayList<>();
/* 211 */     Statement stmt = null;
/* 212 */     String sql = "";
/* 213 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 216 */       stmt = db.conn.createStatement();
/*     */       
/* 218 */       sql = "SELECT "
				+ "pb.idBloqueo, pb.codProductor, pb.idEspecie, pb.cometario," 
					+"CASE "
					    +"WHEN pb.b = 'N' THEN 'SI' "
					     +"WHEN pb.b = 'Y' THEN 'NO' "
					    +"ELSE '' "
					+"END AS b,"
					 +"pb.idUsuario, pb.creado, pb.modificado,"
					 +"pb.idTemporada, pb.activo, pb.idMercado, pb.idVariedad, pb.codParcela, pb.codTurno,"
		+ "case when pb.idMercado = -1 then 'Todos' else t.mercado  end mercado,"
		+ " case when pb.idVariedad = -1 then 'Todos' else t2.variedad end variedad, "
		+ "e.especie, u.user  FROM productorBloqueo pb left join (select group_concat(m.mercado SEPARATOR ' , ') as mercado, pbm.idPrdBloqueo from prdBloqueoMercado pbm left join mercado m on(m.idMercado = pbm.idMercado ) group by 2)t on (t.idPrdBloqueo = pb.idBloqueo) left join (select group_concat(v.nombre SEPARATOR ' , ') as variedad, pbv.idPrdBloqueo from prdBloqueoVariedad pbv left join variedad v on(v.idVariedad = pbv.idVariedad ) group by 2)t2 on (t2.idPrdBloqueo = pb.idBloqueo) JOIN especie e ON (e.idEspecie = pb.idEspecie) JOIN user u ON (u.idUser = pb.idUsuario)";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 228 */       if (filter.size() > 0) {
/* 229 */         String andSql = "";
/* 230 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 231 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 233 */         while (f.hasNext()) {
/*     */           
/* 235 */           filterSql row = f.next();
/*     */           
/* 237 */           if (!row.getValue().equals("")) {
/*     */             
/* 239 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 241 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 242 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 243 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 245 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 247 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 248 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 249 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 252 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '" + row.getValue() + "'";
/* 253 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 258 */       if (!order.equals("")) {
/* 259 */         sql = String.valueOf(sql) + " order by " + order;
/*     */       } else {
/* 261 */         sql = String.valueOf(sql) + " order by codProductor ASC";
/*     */       } 
/*     */       
/* 264 */       if (length > 0) {
/* 265 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/* 267 */       System.out.println("sql: " + sql);
/* 268 */       ResultSet rs = stmt.executeQuery(sql);
/* 269 */       while (rs.next()) {
/* 270 */         bloqueo o = new bloqueo();
/*     */         
/* 272 */         o.setIdBloqueo(rs.getInt("idBloqueo"));
/* 273 */         o.setCodProductor(rs.getString("codProductor"));
/* 274 */         o.setMercado(rs.getString("mercado"));
/* 275 */         o.setEspecie(rs.getString("especie"));
/* 276 */         o.setB(rs.getString("b"));
/* 277 */         o.setActivo(rs.getString("activo"));
/* 278 */         o.setModificado(rs.getString("modificado"));
/* 279 */         o.setUsuario(rs.getString("user"));
/* 280 */         o.setVariedad(rs.getString("variedad"));
/* 281 */         arr.add(o);
/*     */       } 
/* 283 */       rs.close();
/* 284 */       stmt.close();
/* 285 */       db.conn.close();
/*     */     }
/* 287 */     catch (SQLException e) {
/*     */       
/* 289 */       System.out.println("Error: " + e.getMessage());
/* 290 */       System.out.println("sql: " + sql);
/* 291 */       throw new Exception("getLimite: " + e.getMessage());
/*     */     } finally {
/* 293 */       db.close();
/*     */     } 
/*     */     
/* 296 */     return arr;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<bloqueo> getAll2(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 302 */     ArrayList<bloqueo> arr = new ArrayList<>();
/* 303 */     Statement stmt = null;
/* 304 */     String sql = "";
/* 305 */     ConnectionDB db = new ConnectionDB();
/*     */     
/*     */     try {
/* 308 */       stmt = db.conn.createStatement();
/*     */       
/* 310 */       sql = "SELECT * FROM vw_bloqueo ";
/*     */       
/* 312 */       if (filter.size() > 0) {
/* 313 */         String andSql = "";
/* 314 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 315 */         Iterator<filterSql> f = filter.iterator();
/*     */         
/* 317 */         while (f.hasNext()) {
/*     */           
/* 319 */           filterSql row = f.next();
/*     */           
/* 321 */           if (!row.getValue().equals("")) {
/*     */             
/* 323 */             if (row.getCampo().endsWith("_to")) {
/*     */               
/* 325 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 326 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 327 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             }
/* 329 */             else if (row.getCampo().endsWith("_from")) {
/*     */               
/* 331 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 332 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 333 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*     */             } else {
/*     */               
/* 336 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '" + row.getValue() + "'";
/* 337 */             }  andSql = " and ";
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 342 */       if (!order.equals("")) {
/* 343 */         sql = String.valueOf(sql) + " order by " + order;
/*     */       } else {
/* 345 */         sql = String.valueOf(sql) + " order by codProductor ASC";
/*     */       } 
/*     */       
/* 348 */       if (length > 0) {
/* 349 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*     */       }
/* 351 */       System.out.println("sql: " + sql);
/* 352 */       ResultSet rs = stmt.executeQuery(sql);
/* 353 */       while (rs.next()) {
/* 354 */         bloqueo o = new bloqueo();
/*     */         
/* 356 */         o.setIdBloqueo(rs.getInt("idBloqueo"));
/* 357 */         o.setCodProductor(rs.getString("codProductor"));
/* 358 */         o.setMercado(rs.getString("mercado"));
/* 359 */         o.setEspecie(rs.getString("especie"));
/* 360 */         o.setB(rs.getString("b"));
/* 361 */         o.setActivo(rs.getString("activo"));
/* 362 */         o.setModificado(rs.getString("modificado"));
/* 363 */         o.setUsuario(rs.getString("user"));
/* 364 */         o.setVariedad(rs.getString("variedad"));
/* 365 */         arr.add(o);
/*     */       } 
/* 367 */       rs.close();
/* 368 */       stmt.close();
/* 369 */       db.conn.close();
/*     */     }
/* 371 */     catch (SQLException e) {
/*     */       
/* 373 */       System.out.println("Error: " + e.getMessage());
/* 374 */       System.out.println("sql: " + sql);
/* 375 */       throw new Exception("getLimite: " + e.getMessage());
/*     */     } finally {
/* 377 */       db.close();
/*     */     } 
/*     */     
/* 380 */     return arr;
/*     */   }

	public static String insert(bloqueo o) throws ParseException {
		ConnectionDB db = new ConnectionDB();
		PreparedStatement ps = null,ps2 = null,ps3 = null,ps4 = null;
		String sql = "",sql2 = "",sql3 = "",sql4 = "";
		String respuesta = "";
		
		try {
			sql = "INSERT INTO productorBloqueo(codProductor, idEspecie, cometario, b, idUsuario, creado, modificado, idTemporada, activo, idMercado, idVariedad,codParcela,codTurno) Values (?,?,?,?,?,now(),now(),?,?,?,?,?,?)";

			ps = db.conn.prepareStatement(sql);
			ps.setString(1, o.getCodProductor());
			ps.setInt(2, o.getIdEspecie());
			ps.setString(3, o.getComentario());
			ps.setString(4, o.getB());
			ps.setInt(5, o.getIdUsuario());
			ps.setInt(6, o.getIdTemporada());
			ps.setString(7, o.getActivo());
			ps.setInt(8, o.getIdMercado());
			ps.setInt(9, o.getIdVariedad());
			ps.setString(10, o.getCodParcela());
			ps.setString(11, o.getCodTurno());
			
			
			ps.execute();
//			ResultSet rs = ps.getGeneratedKeys();
			
			int resultado = 0;
			sql4 = "SELECT max(idBloqueo) AS idBloqueo FROM productorBloqueo";
			ps4 = db.conn.prepareStatement(sql4);
			ResultSet rs4 = ps4.executeQuery(sql4);

			while (rs4.next()) {
				resultado = rs4.getInt("idBloqueo");
			}
			
//			respuesta += "resultado = 0";
//			if (rs.next()) {
//				resultado = rs.getInt(1);
//				
//			}
			respuesta += "resultado" + resultado + "//";
			String[] vari = o.getVariedades().split(",");
			respuesta += "variedades" + o.getVariedades() + "//";
			for (int i = 0; i < vari.length; i++) {
				sql2 = "INSERT INTO prdBloqueoVariedad(`idPrdBloqueo`,`idVariedad`) Values (?,?)";
 
				ps2 = db.conn.prepareStatement(sql2);
				ps2.setInt(1, resultado);
				ps2.setString(2, vari[i]);
				respuesta += ps2.toString();
				ps2.execute();
			} 
			String[] mercados = o.getMercados().split(",");
			for (int j = 0; j < mercados.length; j++) {
				sql3 = "INSERT INTO prdBloqueoMercado(`idPrdBloqueo`,`idMercado`) Values (?,?)";
				
				ps3 = db.conn.prepareStatement(sql3);
				ps3.setInt(1, resultado);
				ps3.setString(2, mercados[j]);
				respuesta += ps3.toString();
				ps3.execute();
			} 
			ps.close();
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			db.close();
		} 
		return respuesta;
	}
}
