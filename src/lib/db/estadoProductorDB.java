/*      */ package lib.db;
/*      */ 
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.ResultSetMetaData;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import lib.struc.filterSql;
/*      */ import lib.struc.restriccion;
/*      */ import org.json.JSONArray;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class estadoProductorDB
/*      */ {
/*      */   public static ArrayList<String[]> getEstadoProductorPro(int idTemporada, int idEspecie, String productor, String nombre) throws Exception {
/*   22 */     ArrayList<String[]> data = (ArrayList)new ArrayList<>();
/*      */     
/*   24 */     ConnectionDB db = new ConnectionDB();
/*   25 */     Statement stmt = null;
/*   26 */     String sql = "";
/*      */     
/*      */     try {
/*   29 */       stmt = db.conn.createStatement();
/*      */       
/*   31 */       sql = "{CALL sp_viewRestricionesPro(" + idTemporada + "," + idEspecie + ",'" + productor + "','" + nombre + "') }";
/*      */ 
/*      */       
/*   34 */       ResultSet rs = stmt.executeQuery(sql);
/*      */       
/*   36 */       ResultSetMetaData rsmd = rs.getMetaData();
/*   37 */       int columnCount = rsmd.getColumnCount();
/*      */       
/*   39 */       int j = 0;
/*   40 */       while (rs.next()) {
/*      */         
/*   42 */         String[] o = new String[columnCount];
/*      */         
/*   44 */         for (int i = 1; i <= columnCount; i++)
/*      */         {
/*   46 */           o[i - 1] = rs.getString(i);
/*      */         }
/*      */         
/*   49 */         j++;
/*   50 */         data.add(o);
/*      */       } 
/*   52 */       rs.close();
/*   53 */       stmt.close();
/*   54 */       db.conn.close();
/*      */     }
/*   56 */     catch (SQLException e) {
/*      */       
/*   58 */       System.out.println("Error: " + e.getMessage());
/*   59 */       System.out.println("sql: " + sql);
/*   60 */       throw new Exception("getEstadoProductor: " + e.getMessage());
/*      */     } finally {
/*   62 */       db.close();
/*      */     } 
/*      */     
/*   65 */     return data;
/*      */   }
/*      */ 
/*      */   
/*      */   public static ArrayList<String[]> getEstadoProductorA(int idTemporada, int idEspecie, String productor, String nombre, Boolean titulo, String cliente) throws Exception {
/*   70 */     ArrayList<String[]> data = (ArrayList)new ArrayList<>();
/*      */     
/*   72 */     ConnectionDB db = new ConnectionDB();
/*   73 */     Statement stmt = null;
/*   74 */     String sql = "";
/*      */     
/*      */     try {
/*   77 */       stmt = db.conn.createStatement();
/*      */       
/*   79 */       sql = "{CALL sp_viewRestriciones(" + idTemporada + "," + idEspecie + ",'" + productor + "','" + nombre + "','','" + cliente + "','') }";
/*      */ 
/*      */       System.out.println(sql);
/*   82 */       ResultSet rs = stmt.executeQuery(sql);
/*      */       
/*   84 */       ResultSetMetaData rsmd = rs.getMetaData();
/*   85 */       int columnCount = rsmd.getColumnCount();
/*      */ 
/*      */       
/*   88 */       for (int i = 1; i <= columnCount; i++);
/*      */ 
/*      */ 
/*      */       
/*   92 */       int j = 0;
/*   93 */       if (titulo.booleanValue()) {
/*      */         
/*   95 */         String[] o = new String[columnCount];
/*   96 */         System.out.println("TITULOSSSSS");
/*      */         
/*   98 */         for (int x = 1; x <= columnCount; x++)
/*      */         {
/*  100 */           o[x - 1] = rsmd.getColumnLabel(x);
/*      */         }
/*      */         
/*  103 */         if (titulo.booleanValue()) {
/*  104 */           data.add(o);
/*      */         }
/*      */       } 
/*      */       
/*  108 */       while (rs.next()) {
/*      */         
/*  110 */         String[] o = new String[columnCount];
/*      */         
/*  112 */         for (int k = 1; k <= columnCount; k++)
/*      */         {
/*  114 */           o[k - 1] = rs.getString(k);
/*      */         }
/*      */         
/*  117 */         data.add(o);
/*      */         
/*  119 */         j++;
/*      */       } 
/*      */       
/*  122 */       rs.close();
/*  123 */       stmt.close();
/*  124 */       db.conn.close();
/*      */     }
/*  126 */     catch (SQLException e) {
/*      */       
/*  128 */       System.out.println("Error: " + e.getMessage());
/*  129 */       System.out.println("sql: " + sql);
/*  130 */       throw new Exception("getEstadoProductor: " + e.getMessage());
/*      */     } finally {
/*  132 */       db.close();
/*      */     } 
/*      */     
/*  135 */     return data;
/*      */   }
/*      */   public static ArrayList<String[]> getEstadoProductorAOrg(int idTemporada, int idEspecie, String productor, String nombre, Boolean titulo, String cliente) throws Exception {
/*  138 */     ArrayList<String[]> data = (ArrayList)new ArrayList<>();
/*      */     
/*  140 */     ConnectionDB db = new ConnectionDB();
/*  141 */     Statement stmt = null;
/*  142 */     String sql = "";
/*      */     
/*      */     try {
/*  145 */       stmt = db.conn.createStatement();
/*      */       
/*  147 */       sql = "{CALL sp_viewRestricionesOrg(" + idTemporada + "," + idEspecie + ",'" + productor + "','" + nombre + "','','" + cliente + "','') }";
/*      */ 
/*      */       
/*  150 */       ResultSet rs = stmt.executeQuery(sql);
/*      */       
/*  152 */       ResultSetMetaData rsmd = rs.getMetaData();
/*  153 */       int columnCount = rsmd.getColumnCount();
/*      */ 
/*      */       
/*  156 */       for (int i = 1; i <= columnCount; i++);
/*      */ 
/*      */ 
/*      */       
/*  160 */       int j = 0;
/*  161 */       if (titulo.booleanValue()) {
/*      */         
/*  163 */         String[] o = new String[columnCount];
/*  164 */         System.out.println("TITULOSSSSS");
/*      */         
/*  166 */         for (int x = 1; x <= columnCount; x++)
/*      */         {
/*  168 */           o[x - 1] = rsmd.getColumnLabel(x);
/*      */         }
/*      */         
/*  171 */         if (titulo.booleanValue()) {
/*  172 */           data.add(o);
/*      */         }
/*      */       } 
/*      */       
/*  176 */       while (rs.next()) {
/*      */         
/*  178 */         String[] o = new String[columnCount];
/*      */         
/*  180 */         for (int k = 1; k <= columnCount; k++)
/*      */         {
/*  182 */           o[k - 1] = rs.getString(k);
/*      */         }
/*      */         
/*  185 */         data.add(o);
/*      */         
/*  187 */         j++;
/*      */       } 
/*      */       
/*  190 */       rs.close();
/*  191 */       stmt.close();
/*  192 */       db.conn.close();
/*      */     }
/*  194 */     catch (SQLException e) {
/*      */       
/*  196 */       System.out.println("Error: " + e.getMessage());
/*  197 */       System.out.println("sql: " + sql);
/*  198 */       throw new Exception("getEstadoProductor: " + e.getMessage());
/*      */     } finally {
/*  200 */       db.close();
/*      */     } 
/*      */     
/*  203 */     return data;
/*      */   }
/*      */   public static ArrayList<String[]> getEstadoProductorASap(int idTemporada, int idEspecie, String productor, String nombre, Boolean titulo, String cliente) throws Exception {
/*  206 */     ArrayList<String[]> data = (ArrayList)new ArrayList<>();
/*      */     
/*  208 */     ConnectionDB db = new ConnectionDB();
/*  209 */     Statement stmt = null;
/*  210 */     String sql = "";
/*      */     
/*      */     try {
/*  213 */       stmt = db.conn.createStatement();
/*      */       
/*  215 */       sql = "{CALL sp_viewRestricionesSap(" + idTemporada + "," + idEspecie + ",'" + productor + "','" + nombre + "','','" + cliente + "') }";
/*      */ 
/*      */       
/*  218 */       ResultSet rs = stmt.executeQuery(sql);
/*      */       
/*  220 */       ResultSetMetaData rsmd = rs.getMetaData();
/*  221 */       int columnCount = rsmd.getColumnCount();
/*      */ 
/*      */       
/*  224 */       for (int i = 1; i <= columnCount; i++);
/*      */ 
/*      */ 
/*      */       
/*  228 */       int j = 0;
/*  229 */       if (titulo.booleanValue()) {
/*      */         
/*  231 */         String[] o = new String[columnCount];
/*  232 */         System.out.println("TITULOSSSSS");
/*      */         
/*  234 */         for (int x = 1; x <= columnCount; x++)
/*      */         {
/*  236 */           o[x - 1] = rsmd.getColumnLabel(x);
/*      */         }
/*      */         
/*  239 */         if (titulo.booleanValue()) {
/*  240 */           data.add(o);
/*      */         }
/*      */       } 
/*      */       
/*  244 */       while (rs.next()) {
/*      */         
/*  246 */         String[] o = new String[columnCount];
/*      */         
/*  248 */         for (int k = 1; k <= columnCount; k++)
/*      */         {
/*  250 */           o[k - 1] = rs.getString(k);
/*      */         }
/*      */         
/*  253 */         data.add(o);
/*      */         
/*  255 */         j++;
/*      */       } 
/*      */       
/*  258 */       rs.close();
/*  259 */       stmt.close();
/*  260 */       db.conn.close();
/*      */     }
/*  262 */     catch (SQLException e) {
/*      */       
/*  264 */       System.out.println("Error: " + e.getMessage());
/*  265 */       System.out.println("sql: " + sql);
/*  266 */       throw new Exception("getEstadoProductor: " + e.getMessage());
/*      */     } finally {
/*  268 */       db.close();
/*      */     } 
/*      */     
/*  271 */     return data;
/*      */   }
/*      */   
/*      */   public static ArrayList<String[]> getEstadoProductorB(int idTemporada, int idEspecie, String productor, String nombre, Boolean titulo) throws Exception {
/*  275 */     ArrayList<String[]> data = (ArrayList)new ArrayList<>();
/*      */     
/*  277 */     ConnectionDB db = new ConnectionDB();
/*  278 */     Statement stmt = null;
/*  279 */     String sql = "";
/*      */     
/*      */     try {
/*  282 */       stmt = db.conn.createStatement();
/*      */       
/*  284 */       sql = "{CALL sp_viewRestricionesParcela(" + idTemporada + "," + idEspecie + ",'" + productor + "','" + nombre + "') }";
/*  285 */       System.out.println("sql PPPPP: " + sql);
/*      */       
/*  287 */       ResultSet rs = stmt.executeQuery(sql);
/*      */       
/*  289 */       ResultSetMetaData rsmd = rs.getMetaData();
/*  290 */       int columnCount = rsmd.getColumnCount();
/*      */ 
/*      */       
/*  293 */       for (int i = 1; i <= columnCount; i++);
/*      */ 
/*      */ 
/*      */       
/*  297 */       int j = 0;
/*  298 */       if (titulo.booleanValue()) {
/*      */         
/*  300 */         String[] o = new String[columnCount];
/*  301 */         System.out.println("TITULOSSSSS33333");
/*      */         
/*  303 */         for (int x = 1; x <= columnCount; x++)
/*      */         {
/*  305 */           o[x - 1] = rsmd.getColumnLabel(x);
/*      */         }
/*      */         
/*  308 */         if (titulo.booleanValue()) {
/*  309 */           data.add(o);
/*      */         }
/*      */       } 
/*      */       
/*  313 */       while (rs.next()) {
/*      */         
/*  315 */         String[] o = new String[columnCount];
/*      */         
/*  317 */         for (int k = 1; k <= columnCount; k++)
/*      */         {
/*  319 */           o[k - 1] = rs.getString(k);
/*      */         }
/*      */         
/*  322 */         data.add(o);
/*      */         
/*  324 */         j++;
/*      */       } 
/*      */       
/*  327 */       rs.close();
/*  328 */       stmt.close();
/*  329 */       db.conn.close();
/*      */     }
/*  331 */     catch (SQLException e) {
/*      */       
/*  333 */       System.out.println("Error: " + e.getMessage());
/*  334 */       System.out.println("sql: " + sql);
/*  335 */       throw new Exception("getEstadoProductor: " + e.getMessage());
/*      */     } finally {
/*  337 */       db.close();
/*      */     } 
/*      */     
/*  340 */     return data;
/*      */   }
/*      */   public static ArrayList<String[]> getEstadoProductorSAP(int idTemporada, int idEspecie, String productor, String nombre, Boolean titulo) throws Exception {
/*  343 */     ArrayList<String[]> data = (ArrayList)new ArrayList<>();
/*      */     
/*  345 */     ConnectionDB db = new ConnectionDB();
/*  346 */     Statement stmt = null;
/*  347 */     String sql = "";
/*      */     
/*      */     try {
/*  350 */       stmt = db.conn.createStatement();
/*      */       
/*  352 */       sql = "{CALL sp_viewRestricionesSAP2(" + idTemporada + "," + idEspecie + ",'" + productor + "','','','" + nombre + "') }";
/*  353 */       System.out.println(sql);
/*      */       
/*  355 */       ResultSet rs = stmt.executeQuery(sql);
/*      */       
/*  357 */       ResultSetMetaData rsmd = rs.getMetaData();
/*  358 */       int columnCount = rsmd.getColumnCount();
/*      */ 
/*      */       
/*  361 */       for (int i = 1; i <= columnCount; i++);
/*      */ 
/*      */ 
/*      */       
/*  365 */       int j = 0;
/*  366 */       if (titulo.booleanValue()) {
/*      */         
/*  368 */         String[] o = new String[columnCount];
/*  369 */         System.out.println("TITULOSSSSS");
/*      */         
/*  371 */         for (int x = 1; x <= columnCount; x++)
/*      */         {
/*  373 */           o[x - 1] = rsmd.getColumnLabel(x);
/*      */         }
/*      */         
/*  376 */         if (titulo.booleanValue()) {
/*  377 */           data.add(o);
/*      */         }
/*      */       } 
/*      */       
/*  381 */       while (rs.next()) {
/*      */         
/*  383 */         String[] o = new String[columnCount];
/*      */         
/*  385 */         for (int k = 1; k <= columnCount; k++)
/*      */         {
/*  387 */           o[k - 1] = rs.getString(k);
/*      */         }
/*      */         
/*  390 */         data.add(o);
/*      */         
/*  392 */         j++;
/*      */       } 
/*      */       
/*  395 */       rs.close();
/*  396 */       stmt.close();
/*  397 */       db.conn.close();
/*      */     }
/*  399 */     catch (SQLException e) {
/*      */       
/*  401 */       System.out.println("Error: " + e.getMessage());
/*  402 */       System.out.println("sql: " + sql);
/*  403 */       throw new Exception("getEstadoProductor: " + e.getMessage());
/*      */     } finally {
/*  405 */       db.close();
/*      */     } 
/*      */     
/*  408 */     return data;
/*      */   }
/*      */   public static ArrayList<String[]> getEstadoProductorC(int idTemporada, int idEspecie, String productor, String nombre, Boolean titulo) throws Exception {
/*  411 */     ArrayList<String[]> data = (ArrayList)new ArrayList<>();
/*      */     
/*  413 */     ConnectionDB db = new ConnectionDB();
/*  414 */     Statement stmt = null;
/*  415 */     String sql = "";
/*      */     
/*      */     try {
/*  418 */       stmt = db.conn.createStatement();
/*      */       
/*  420 */       sql = "{CALL sp_viewRestricionesParcelaTurno(" + idTemporada + "," + idEspecie + ",'" + productor + "','" + nombre + "') }";
/*      */ 
/*      */       
/*  423 */       ResultSet rs = stmt.executeQuery(sql);
/*      */       
/*  425 */       ResultSetMetaData rsmd = rs.getMetaData();
/*  426 */       int columnCount = rsmd.getColumnCount();
/*      */ 
/*      */       
/*  429 */       for (int i = 1; i <= columnCount; i++);
/*      */ 
/*      */ 
/*      */       
/*  433 */       int j = 0;
/*  434 */       if (titulo.booleanValue()) {
/*      */         
/*  436 */         String[] o = new String[columnCount];
/*  437 */         System.out.println("TITULOSSSSS");
/*      */         
/*  439 */         for (int x = 1; x <= columnCount; x++)
/*      */         {
/*  441 */           o[x - 1] = rsmd.getColumnLabel(x);
/*      */         }
/*      */         
/*  444 */         if (titulo.booleanValue()) {
/*  445 */           data.add(o);
/*      */         }
/*      */       } 
/*      */       
/*  449 */       while (rs.next()) {
/*      */         
/*  451 */         String[] o = new String[columnCount];
/*      */         
/*  453 */         for (int k = 1; k <= columnCount; k++)
/*      */         {
/*  455 */           o[k - 1] = rs.getString(k);
/*      */         }
/*      */         
/*  458 */         data.add(o);
/*      */         
/*  460 */         j++;
/*      */       } 
/*      */       
/*  463 */       rs.close();
/*  464 */       stmt.close();
/*  465 */       db.conn.close();
/*      */     }
/*  467 */     catch (SQLException e) {
/*      */       
/*  469 */       System.out.println("Error: " + e.getMessage());
/*  470 */       System.out.println("sql: " + sql);
/*  471 */       throw new Exception("getEstadoProductor: " + e.getMessage());
/*      */     } finally {
/*  473 */       db.close();
/*      */     } 
/*      */     
/*  476 */     return data;
/*      */   }
/*      */   
/*      */   public static String getRestriccionesMercadoCert(int idMercado) throws Exception {
/*  480 */     String Estado = "";
/*  481 */     ConnectionDB db = new ConnectionDB();
/*  482 */     Statement stmt = null;
/*  483 */     String sql = "";
/*      */     
/*      */     try {
/*  486 */       stmt = db.conn.createStatement();
/*      */ 
/*      */ 
/*      */       
/*  490 */       sql = "SELECT certificacionescol FROM mercadoCertificado m  ";
/*  491 */       sql = String.valueOf(sql) + "inner join certificaciones c on (c.idCertificaciones=m.idCertificaciones) where  ";
/*  492 */       sql = String.valueOf(sql) + "     m.idMercado='" + idMercado + "'";
/*      */ 
/*      */       
/*  495 */       System.out.println("sql: " + sql);
/*  496 */       ResultSet rs = stmt.executeQuery(sql);
/*  497 */       Estado = "";
/*  498 */       String coma = "";
/*  499 */       while (rs.next()) {
/*  500 */         Estado = String.valueOf(Estado) + coma + rs.getString("certificacionescol");
/*  501 */         coma = ", ";
/*      */       } 
/*      */       
/*  504 */       rs.close();
/*  505 */       stmt.close();
/*  506 */       db.conn.close();
/*      */     }
/*  508 */     catch (SQLException e) {
/*      */       
/*  510 */       System.out.println("Error: " + e.getMessage());
/*  511 */       System.out.println("sql: " + sql);
/*  512 */       throw new Exception("getEstadoProductor: " + e.getMessage());
/*      */     } finally {
/*  514 */       db.close();
/*      */     } 
/*      */     
/*  517 */     return Estado;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getBlockProductor(int idTemporada, int idEspecie, int idVariedad, String Mercado, String productor, String parcela, String turno) throws Exception {
/*  524 */     String Estado = "";
/*  525 */     ConnectionDB db = new ConnectionDB();
/*  526 */     Statement stmt = null;
/*  527 */     String sql = "";
/*      */     
/*      */     try {
/*  530 */       stmt = db.conn.createStatement();
/*  531 */       System.out.println(String.valueOf(idTemporada) + "-" + idEspecie + "-" + productor);
/*      */ 
/*      */       
/*  534 */       sql = "SELECT * FROM vw_blockProductorMercado where  ";
/*  535 */       sql = String.valueOf(sql) + "     idTemporada='" + idTemporada + "'";
/*  536 */       sql = String.valueOf(sql) + " and idEspecie='" + idEspecie + "'";
/*      */       
/*  538 */       sql = String.valueOf(sql) + " and codProductor='" + productor + "'";
/*  539 */       sql = String.valueOf(sql) + " and codParcela='" + parcela + "'";
/*      */ 
/*      */ 
/*      */       
/*  543 */       sql = String.valueOf(sql) + "group by idEspecie,codProductor,codParcela";
/*      */ 
/*      */       
/*  546 */       System.out.println("sql: " + sql);
/*  547 */       ResultSet rs = stmt.executeQuery(sql);
/*  548 */       Estado = "<table border=1>";
/*  549 */       Estado = String.valueOf(Estado) + "<tr><td>&nbsp;Huerto&nbsp;</td><td>&nbsp;Estado&nbsp;</td></tr>";
/*  550 */       while (rs.next()) {
/*  551 */         String codParcelaStr = rs.getString("codParcela");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  556 */         Estado = String.valueOf(Estado) + "<tr><td>&nbsp;" + codParcelaStr + "&nbsp;</td><td>&nbsp;No Relacionada</td></tr>";
/*      */       } 
/*  558 */       Estado = String.valueOf(Estado) + "</table>";
/*  559 */       rs.close();
/*  560 */       stmt.close();
/*  561 */       db.conn.close();
/*      */     }
/*  563 */     catch (SQLException e) {
/*      */       
/*  565 */       System.out.println("Error: " + e.getMessage());
/*  566 */       System.out.println("sql: " + sql);
/*  567 */       throw new Exception("getEstadoProductor: " + e.getMessage());
/*      */     } finally {
/*  569 */       db.close();
/*      */     } 
/*      */     
/*  572 */     return Estado;
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getBlockMolecula(int idTemporada, int idEspecie, int idVariedad, String Mercado, String productor, String parcela, String turno) throws Exception {
/*  577 */     String Estado = "";
/*  578 */     ConnectionDB db = new ConnectionDB();
/*  579 */     Statement stmt = null;
/*  580 */     String sql = "";
/*      */     
/*      */     try {
/*  583 */       stmt = db.conn.createStatement();
/*      */       
/*  585 */       sql = "SELECT * FROM vw_blockMolecula where  ";
/*  586 */       sql = String.valueOf(sql) + "     idTemporada='" + idTemporada + "'";
/*  587 */       sql = String.valueOf(sql) + " and idEspecie='" + idEspecie + "'";
/*      */       
/*  589 */       sql = String.valueOf(sql) + " and codProductor='" + productor + "'";
/*  590 */       sql = String.valueOf(sql) + " and codParcela='" + parcela + "'";
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  595 */       System.out.println("sql: " + sql);
/*  596 */       ResultSet rs = stmt.executeQuery(sql);
/*      */       
/*  598 */       Estado = "<table border=1>";
/*  599 */       Estado = String.valueOf(Estado) + "<tr><td>&nbsp;Huerto&nbsp;</td><td>&nbsp;Moleculas&nbsp;</td></tr>";
/*  600 */       while (rs.next()) {
/*  601 */         String codParcelaStr = rs.getString("codParcela");
/*      */         
/*  603 */         String p = rs.getString("p");
/*      */         
/*  605 */         Estado = String.valueOf(Estado) + "<tr><td>&nbsp;" + codParcelaStr + "&nbsp;</td><td>&nbsp;" + p + "&nbsp;</td></tr>";
/*      */       } 
/*  607 */       Estado = String.valueOf(Estado) + "</table>";
/*      */       
/*  609 */       rs.close();
/*  610 */       stmt.close();
/*  611 */       db.conn.close();
/*      */     }
/*  613 */     catch (SQLException e) {
/*      */       
/*  615 */       System.out.println("Error: " + e.getMessage());
/*  616 */       System.out.println("sql: " + sql);
/*  617 */       throw new Exception("getEstadoProductor: " + e.getMessage());
/*      */     } finally {
/*  619 */       db.close();
/*      */     } 
/*      */     
/*  622 */     return Estado;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
	public static String getEstadoProductor(int idTemporada, int idEspecie, String productor, String parcela, String turno, String Mercado,String variedad) throws Exception {
/*  629 */     String Estado = "";
/*  630 */     ConnectionDB db = new ConnectionDB();
/*  631 */     Statement stmt = null;
/*  632 */     String sql = "";
/*      */     
/*      */     try {
/*  635 */       stmt = db.conn.createStatement();
/*  636 */       System.out.println(String.valueOf(idTemporada) + "-" + idEspecie + "-" + productor);
/*  637 */       sql = "{CALL sp_viewRestriciones(" + idTemporada + "," + idEspecie + ",'" + productor + "','" + parcela + "','" + turno + "','','"+variedad+"') }";
/*  638 */       System.out.println("sql: " + sql);
/*  639 */       ResultSet rs = stmt.executeQuery(sql);
/*      */       
/*  641 */       if (rs.next()) {
/*      */ 
/*      */ 
/*      */         
/*  645 */         Estado = rs.getString(Mercado);
/*  646 */         System.out.println("Estado: " + Estado + ", Mercado:" + Mercado);
/*      */       } 
/*  648 */       rs.close();
/*  649 */       stmt.close();
/*  650 */       db.conn.close();
/*      */     }
/*  652 */     catch (SQLException e) {
/*      */       
/*  654 */       System.out.println("Error: " + e.getMessage());
/*  655 */       System.out.println("sql: " + sql);
/*  656 */       throw new Exception("getEstadoProductor: " + e.getMessage());
/*      */     } finally {
/*  658 */       db.close();
/*      */     } 
/*      */     
/*  661 */     return Estado;
/*      */   }
/*      */   
/*      */   public static String getEstadoProductorOrg(int idTemporada, int idEspecie, String productor,
		String parcela, String turno, String Mercado, String variedad) throws Exception {
/*  665 */     String Estado = "";
/*  666 */     ConnectionDB db = new ConnectionDB();
/*  667 */     Statement stmt = null;
/*  668 */     String sql = "";
/*      */     
/*      */     try {
/*  671 */       stmt = db.conn.createStatement();
/*  672 */       System.out.println(String.valueOf(idTemporada) + "-" + idEspecie + "-" + productor);
/*  673 */       sql = "{CALL sp_viewRestricionesOrg(" + idTemporada + "," + idEspecie + ",'" + productor + "','" + parcela + "','" + turno + "','','"+variedad+"') }";
/*  674 */       System.out.println("sql: " + sql);
/*  675 */       ResultSet rs = stmt.executeQuery(sql);
/*      */       
/*  677 */       if (rs.next()) {
/*      */ 
/*      */ 
/*      */         
/*  681 */         Estado = rs.getString(Mercado);
/*  682 */         System.out.println("Estado: " + Estado + ", Mercado:" + Mercado);
/*      */       } 
/*  684 */       rs.close();
/*  685 */       stmt.close();
/*  686 */       db.conn.close();
/*      */     }
/*  688 */     catch (SQLException e) {
/*      */       
/*  690 */       System.out.println("Error: " + e.getMessage());
/*  691 */       System.out.println("sql: " + sql);
/*  692 */       throw new Exception("getEstadoProductor: " + e.getMessage());
/*      */     } finally {
/*  694 */       db.close();
/*      */     } 
/*      */     
/*  697 */     return Estado;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getAllcount(ArrayList<filterSql> filter) throws Exception {
/*  705 */     int total = 0;
/*  706 */     Statement stmt = null;
/*  707 */     String sql = "";
/*  708 */     ConnectionDB db = new ConnectionDB();
/*      */     
/*      */     try {
/*  711 */       stmt = db.conn.createStatement();
/*      */       
/*  713 */       sql = "SELECT count(1) FROM vw_bloqueados ";
/*      */       
/*  715 */       if (filter.size() > 0) {
/*  716 */         String andSql = "";
/*  717 */         andSql = String.valueOf(andSql) + " WHERE ";
/*  718 */         Iterator<filterSql> f = filter.iterator();
/*      */         
/*  720 */         while (f.hasNext()) {
/*      */           
/*  722 */           filterSql row = f.next();
/*  723 */           if (!row.getValue().equals("")) {
/*      */             
/*  725 */             if (row.getCampo().endsWith("_to")) {
/*      */               
/*  727 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/*  728 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/*  729 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*      */             }
/*  731 */             else if (row.getCampo().endsWith("_from")) {
/*      */               
/*  733 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/*  734 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/*  735 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*      */             } else {
/*      */               
/*  738 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like'%" + row.getValue() + "%'";
/*  739 */             }  andSql = " and ";
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  745 */       ResultSet rs = stmt.executeQuery(sql);
/*  746 */       while (rs.next()) {
/*  747 */         total = rs.getInt(1);
/*      */       }
/*  749 */       rs.close();
/*  750 */       stmt.close();
/*  751 */       db.conn.close();
/*      */     
/*      */     }
/*  754 */     catch (SQLException e) {
/*      */       
/*  756 */       System.out.println("Error: " + e.getMessage());
/*  757 */       System.out.println("sql: " + sql);
/*  758 */       throw new Exception("getLimitesAll: " + e.getMessage());
/*      */     } finally {
/*  760 */       db.close();
/*      */     } 
/*  762 */     return total;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static ArrayList<restriccion> getAll(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/*  768 */     ArrayList<restriccion> arr = new ArrayList<>();
/*  769 */     Statement stmt = null;
/*  770 */     String sql = "";
/*  771 */     ConnectionDB db = new ConnectionDB();
/*      */     
/*      */     try {
/*  774 */       stmt = db.conn.createStatement();
/*      */       
/*  776 */       sql = "SELECT * FROM vw_bloqueados ";
/*      */       
/*  778 */       if (filter.size() > 0) {
/*  779 */         String andSql = "";
/*  780 */         andSql = String.valueOf(andSql) + " WHERE ";
/*  781 */         Iterator<filterSql> f = filter.iterator();
/*      */         
/*  783 */         while (f.hasNext()) {
/*      */           
/*  785 */           filterSql row = f.next();
/*      */           
/*  787 */           if (!row.getValue().equals("")) {
/*      */             
/*  789 */             if (row.getCampo().endsWith("_to")) {
/*      */               
/*  791 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/*  792 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/*  793 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*      */             }
/*  795 */             else if (row.getCampo().endsWith("_from")) {
/*      */               
/*  797 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/*  798 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/*  799 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*      */             } else {
/*      */               
/*  802 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/*  803 */             }  andSql = " and ";
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  808 */       if (!order.equals("")) {
/*  809 */         sql = String.valueOf(sql) + " order by " + order;
/*      */       } else {
/*      */         
/*  812 */         sql = String.valueOf(sql) + " order by codProductor ";
/*      */       } 
/*  814 */       if (length > 0) {
/*  815 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*      */       }
/*  817 */       System.out.println(sql);
/*  818 */       ResultSet rs = stmt.executeQuery(sql);
/*  819 */       while (rs.next()) {
/*  820 */         restriccion o = new restriccion();
/*      */ 
/*      */         
/*  823 */         o.setCodProductor(rs.getString("codProductor"));
/*  824 */         o.setFecha(rs.getString("fecha"));
/*  825 */         o.setCodProducto(rs.getString("codProducto"));
/*  826 */         o.setLimite(rs.getString("limite"));
/*      */         
/*  828 */         o.setMercado(rs.getString("mercado"));
/*  829 */         o.setEspecie(rs.getString("especie"));
/*  830 */         o.setnMuestra(rs.getString("nMuestra"));
/*  831 */         o.setAutomatica(rs.getString("Automatica"));
/*  832 */         arr.add(o);
/*      */       } 
/*  834 */       rs.close();
/*  835 */       stmt.close();
/*  836 */       db.conn.close();
/*      */     }
/*  838 */     catch (SQLException e) {
/*      */       
/*  840 */       System.out.println("Error: " + e.getMessage());
/*  841 */       System.out.println("sql: " + sql);
/*  842 */       throw new Exception("getLimite: " + e.getMessage());
/*      */     } finally {
/*  844 */       db.close();
/*      */     } 
/*      */     
/*  847 */     return arr;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getAllcountRest(ArrayList<filterSql> filter) throws Exception {
/*  853 */     int total = 0;
/*  854 */     Statement stmt = null;
/*  855 */     String sql = "";
/*  856 */     ConnectionDB db = new ConnectionDB();
/*      */     
/*      */     try {
/*  859 */       stmt = db.conn.createStatement();
/*      */       
/*  861 */       sql = "SELECT count(1) FROM vw_bloqueadosSinRest ";
/*      */       
/*  863 */       if (filter.size() > 0) {
/*  864 */         String andSql = "";
/*  865 */         andSql = String.valueOf(andSql) + " WHERE ";
/*  866 */         Iterator<filterSql> f = filter.iterator();
/*      */         
/*  868 */         while (f.hasNext()) {
/*      */           
/*  870 */           filterSql row = f.next();
/*  871 */           if (!row.getValue().equals("")) {
/*      */             
/*  873 */             if (row.getCampo().endsWith("_to")) {
/*      */               
/*  875 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/*  876 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/*  877 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*      */             }
/*  879 */             else if (row.getCampo().endsWith("_from")) {
/*      */               
/*  881 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/*  882 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/*  883 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*      */             } else {
/*      */               
/*  886 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like'%" + row.getValue() + "%'";
/*  887 */             }  andSql = " and ";
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  893 */       ResultSet rs = stmt.executeQuery(sql);
/*  894 */       while (rs.next()) {
/*  895 */         total = rs.getInt(1);
/*      */       }
/*  897 */       rs.close();
/*  898 */       stmt.close();
/*  899 */       db.conn.close();
/*      */     
/*      */     }
/*  902 */     catch (SQLException e) {
/*      */       
/*  904 */       System.out.println("Error: " + e.getMessage());
/*  905 */       System.out.println("sql: " + sql);
/*  906 */       throw new Exception("getLimitesAll: " + e.getMessage());
/*      */     } finally {
/*  908 */       db.close();
/*      */     } 
/*  910 */     return total;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static ArrayList<restriccion> getAllRest(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/*  916 */     ArrayList<restriccion> arr = new ArrayList<>();
/*  917 */     Statement stmt = null;
/*  918 */     String sql = "";
/*  919 */     ConnectionDB db = new ConnectionDB();
/*      */     
/*      */     try {
/*  922 */       stmt = db.conn.createStatement();
/*      */       
/*  924 */       sql = "SELECT * FROM vw_bloqueadosSinRest ";
/*      */       
/*  926 */       if (filter.size() > 0) {
/*  927 */         String andSql = "";
/*  928 */         andSql = String.valueOf(andSql) + " WHERE ";
/*  929 */         Iterator<filterSql> f = filter.iterator();
/*      */         
/*  931 */         while (f.hasNext()) {
/*      */           
/*  933 */           filterSql row = f.next();
/*      */           
/*  935 */           if (!row.getValue().equals("")) {
/*      */             
/*  937 */             if (row.getCampo().endsWith("_to")) {
/*      */               
/*  939 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/*  940 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/*  941 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*      */             }
/*  943 */             else if (row.getCampo().endsWith("_from")) {
/*      */               
/*  945 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/*  946 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/*  947 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*      */             } else {
/*      */               
/*  950 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/*  951 */             }  andSql = " and ";
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  956 */       if (!order.equals("")) {
/*  957 */         sql = String.valueOf(sql) + " order by " + order;
/*      */       } else {
/*      */         
/*  960 */         sql = String.valueOf(sql) + " order by codProductor ";
/*      */       } 
/*  962 */       if (length > 0) {
/*  963 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*      */       }
/*  965 */       System.out.println("sql: " + sql);
/*  966 */       ResultSet rs = stmt.executeQuery(sql);
/*  967 */       while (rs.next()) {
/*  968 */         restriccion o = new restriccion();
/*      */ 
/*      */         
/*  971 */         o.setCodProductor(rs.getString("codProductor"));
/*  972 */         o.setFecha(rs.getString("fecha"));
/*  973 */         o.setCodProducto(rs.getString("codProducto"));
/*  974 */         o.setLimite(rs.getString("limite").replace(".", ","));
/*  975 */         o.setNomProductor(rs.getString("nomProductor"));
/*      */         
/*  977 */         o.setEspecie(rs.getString("especie"));
/*  978 */         o.setAutomatica(rs.getString("Automatica"));
/*  979 */         o.setnMuestra(rs.getString("nMuestra"));
/*  980 */         o.setVariedad(rs.getString("variedad"));
/*  981 */         arr.add(o);
/*      */       } 
/*  983 */       rs.close();
/*  984 */       stmt.close();
/*  985 */       db.conn.close();
/*      */     }
/*  987 */     catch (SQLException e) {
/*      */       
/*  989 */       System.out.println("Error: " + e.getMessage());
/*  990 */       System.out.println("sql: " + sql);
/*  991 */       throw new Exception("getLimite: " + e.getMessage());
/*      */     } finally {
/*  993 */       db.close();
/*      */     } 
/*      */     
/*  996 */     return arr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getAllcountZona(ArrayList<filterSql> filter) throws Exception {
/* 1003 */     int total = 0;
/* 1004 */     Statement stmt = null;
/* 1005 */     String sql = "";
/* 1006 */     ConnectionDB db = new ConnectionDB();
/*      */     
/*      */     try {
/* 1009 */       stmt = db.conn.createStatement();
/*      */       
/* 1011 */       sql = "SELECT  count(1) FROM (SELECT distinct z.codProductor,  d.codProducto, z.limite,  z.especie, z.Automatica, z.mail, z.nMuestra  FROM vw_bloqueadosZona as z inner join diccionario as d on (z.codProducto=d.codRemplazo)and z.fechaIngreso BETWEEN '2019/07/01' AND '2020/06/30') as d ";
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1016 */       if (filter.size() > 0) {
/* 1017 */         String andSql = "";
/* 1018 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 1019 */         Iterator<filterSql> f = filter.iterator();
/*      */         
/* 1021 */         while (f.hasNext()) {
/*      */           
/* 1023 */           filterSql row = f.next();
/* 1024 */           if (!row.getValue().equals("")) {
/*      */             
/* 1026 */             if (row.getCampo().endsWith("_to")) {
/*      */               
/* 1028 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 1029 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 1030 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*      */             }
/* 1032 */             else if (row.getCampo().endsWith("_from")) {
/*      */               
/* 1034 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 1035 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 1036 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*      */             } else {
/*      */               
/* 1039 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like'%" + row.getValue() + "%'";
/* 1040 */             }  andSql = " and ";
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1046 */       ResultSet rs = stmt.executeQuery(sql);
/* 1047 */       while (rs.next()) {
/* 1048 */         total = rs.getInt(1);
/*      */       }
/* 1050 */       rs.close();
/* 1051 */       stmt.close();
/* 1052 */       db.conn.close();
/*      */     
/*      */     }
/* 1055 */     catch (SQLException e) {
/*      */       
/* 1057 */       System.out.println("Error: " + e.getMessage());
/* 1058 */       System.out.println("sql: " + sql);
/* 1059 */       throw new Exception("getLimitesAll: " + e.getMessage());
/*      */     } finally {
/* 1061 */       db.close();
/*      */     } 
/* 1063 */     return total;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static ArrayList<restriccion> getAllZona(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 1069 */     ArrayList<restriccion> arr = new ArrayList<>();
/* 1070 */     Statement stmt = null;
/* 1071 */     String sql = "";
/* 1072 */     ConnectionDB db = new ConnectionDB();
/*      */     
/*      */     try {
/* 1075 */       stmt = db.conn.createStatement();
/*      */       
/* 1077 */       sql = "SELECT distinct z.codProductor,  d.codProducto, z.limite,  z.especie, z.Automatica, z.mail, z.nMuestra,z.fechaIngreso  FROM vw_bloqueadosZona as z inner join diccionario as d on (z.codProducto=d.codRemplazo)";
/*      */ 
/*      */ 
/*      */       
/* 1081 */       if (filter.size() > 0) {
/* 1082 */         String andSql = "";
/* 1083 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 1084 */         Iterator<filterSql> f = filter.iterator();
/*      */         
/* 1086 */         while (f.hasNext()) {
/*      */           
/* 1088 */           filterSql row = f.next();
/*      */           
/* 1090 */           if (!row.getValue().equals("")) {
/*      */             
/* 1092 */             if (row.getCampo().endsWith("_to")) {
/*      */               
/* 1094 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 1095 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 1096 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*      */             }
/* 1098 */             else if (row.getCampo().endsWith("_from")) {
/*      */               
/* 1100 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 1101 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 1102 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*      */             } else {
/*      */               
/* 1105 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 1106 */             }  andSql = " and z.fechaIngreso BETWEEN '2019/07/01' AND '2020/06/30' AND ";
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1111 */       if (!order.equals("")) {
/* 1112 */         sql = String.valueOf(sql) + " order by " + order;
/*      */       } else {
/*      */         
/* 1115 */         sql = String.valueOf(sql) + " order by codProductor ";
/*      */       } 
/* 1117 */       if (length > 0) {
/* 1118 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*      */       }
/* 1120 */       System.out.println("sql: " + sql);
/* 1121 */       ResultSet rs = stmt.executeQuery(sql);
/* 1122 */       while (rs.next()) {
/* 1123 */         restriccion o = new restriccion();
/*      */ 
/*      */         
/* 1126 */         o.setCodProductor(rs.getString("codProductor"));
/* 1127 */         o.setFecha(rs.getString("fechaIngreso"));
/* 1128 */         o.setCodProducto(rs.getString("codProducto"));
/* 1129 */         o.setLimite(rs.getString("limite"));
/* 1130 */         o.setnMuestra(rs.getString("nMuestra"));
/*      */         
/* 1132 */         o.setEspecie(rs.getString("especie"));
/* 1133 */         o.setAutomatica(rs.getString("Automatica"));
/* 1134 */         arr.add(o);
/*      */       } 
/* 1136 */       rs.close();
/* 1137 */       stmt.close();
/* 1138 */       db.conn.close();
/*      */     }
/* 1140 */     catch (SQLException e) {
/*      */       
/* 1142 */       System.out.println("Error: " + e.getMessage());
/* 1143 */       System.out.println("sql: " + sql);
/* 1144 */       throw new Exception("getLimite: " + e.getMessage());
/*      */     } finally {
/* 1146 */       db.close();
/*      */     } 
/*      */     
/* 1149 */     return arr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getAllcountZona2(ArrayList<filterSql> filter) throws Exception {
/* 1157 */     int total = 0;
/* 1158 */     Statement stmt = null;
/* 1159 */     String sql = "";
/* 1160 */     ConnectionDB db = new ConnectionDB();
/*      */     
/*      */     try {
/* 1163 */       stmt = db.conn.createStatement();
/*      */       
/* 1165 */       sql = "SELECT  count(distinct codProductor) FROM vw_bloqueadosZona ";
/* 1166 */       sql = "SELECT count(distinct codProductor) FROM (select p.zona, p.codProductor,e.especie from  (select max(idRestriciones),idEspecie, codProductor  from restriciones where inicial='N' AND idTemporada = 2  group by codProductor,idEspecie) as r inner join productor p on (p.codProductor=r.codProductor ) inner join especie e on(e.idEspecie=r.idEspecie )) as mm";
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1171 */       if (filter.size() > 0) {
/* 1172 */         String andSql = "";
/* 1173 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 1174 */         Iterator<filterSql> f = filter.iterator();
/*      */         
/* 1176 */         while (f.hasNext()) {
/*      */           
/* 1178 */           filterSql row = f.next();
/* 1179 */           if (!row.getValue().equals("")) {
/*      */             
/* 1181 */             if (row.getCampo().endsWith("_to")) {
/*      */               
/* 1183 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 1184 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 1185 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*      */             }
/* 1187 */             else if (row.getCampo().endsWith("_from")) {
/*      */               
/* 1189 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 1190 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 1191 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*      */             } else {
/*      */               
/* 1194 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like'%" + row.getValue() + "%'";
/* 1195 */             }  andSql = " and ";
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1201 */       ResultSet rs = stmt.executeQuery(sql);
/* 1202 */       while (rs.next()) {
/* 1203 */         total = rs.getInt(1);
/*      */       }
/* 1205 */       rs.close();
/* 1206 */       stmt.close();
/* 1207 */       db.conn.close();
/*      */     
/*      */     }
/* 1210 */     catch (SQLException e) {
/*      */       
/* 1212 */       System.out.println("Error: " + e.getMessage());
/* 1213 */       System.out.println("sql: " + sql);
/* 1214 */       throw new Exception("getLimitesAll: " + e.getMessage());
/*      */     } finally {
/* 1216 */       db.close();
/*      */     } 
/* 1218 */     return total;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static ArrayList<restriccion> getAllZona2(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 1224 */     ArrayList<restriccion> arr = new ArrayList<>();
/* 1225 */     Statement stmt = null;
/* 1226 */     String sql = "";
/* 1227 */     ConnectionDB db = new ConnectionDB();
/*      */     
/*      */     try {
/* 1230 */       stmt = db.conn.createStatement();
/*      */       
/* 1232 */       sql = "SELECT distinct * FROM (select p.zona, p.codProductor,e.especie from  (select max(idRestriciones),idEspecie, codProductor  from restriciones where inicial='N' AND idTemporada = 2  group by codProductor,idEspecie) as r inner join productor p on (p.codProductor=r.codProductor ) inner join especie e on(e.idEspecie=r.idEspecie )) as mm";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1240 */       if (filter.size() > 0) {
/* 1241 */         String andSql = "";
/* 1242 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 1243 */         Iterator<filterSql> f = filter.iterator();
/*      */         
/* 1245 */         while (f.hasNext()) {
/*      */           
/* 1247 */           filterSql row = f.next();
/*      */           
/* 1249 */           if (!row.getValue().equals("")) {
/*      */             
/* 1251 */             if (row.getCampo().endsWith("_to")) {
/*      */               
/* 1253 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 1254 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 1255 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*      */             }
/* 1257 */             else if (row.getCampo().endsWith("_from")) {
/*      */               
/* 1259 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 1260 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 1261 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*      */             } else {
/*      */               
/* 1264 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 1265 */             }  andSql = " and ";
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1270 */       if (!order.equals("")) {
/* 1271 */         sql = String.valueOf(sql) + " order by " + order;
/*      */       } else {
/*      */         
/* 1274 */         sql = String.valueOf(sql) + " order by codProductor ";
/*      */       } 
/* 1276 */       if (length > 0) {
/* 1277 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*      */       }
/* 1279 */       System.out.println("sql: " + sql);
/* 1280 */       ResultSet rs = stmt.executeQuery(sql);
/* 1281 */       while (rs.next()) {
/* 1282 */         restriccion o = new restriccion();
/*      */ 
/*      */         
/* 1285 */         o.setCodProductor(rs.getString("codProductor"));
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1290 */         o.setMercado(rs.getString("zona"));
/* 1291 */         o.setEspecie(rs.getString("especie"));
/*      */         
/* 1293 */         o.setAutomatica("");
/* 1294 */         arr.add(o);
/*      */       } 
/* 1296 */       rs.close();
/* 1297 */       stmt.close();
/* 1298 */       db.conn.close();
/*      */     }
/* 1300 */     catch (SQLException e) {
/*      */       
/* 1302 */       System.out.println("Error: " + e.getMessage());
/* 1303 */       System.out.println("sql: " + sql);
/* 1304 */       throw new Exception("getLimite: " + e.getMessage());
/*      */     } finally {
/* 1306 */       db.close();
/*      */     } 
/*      */     
/* 1309 */     return arr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getAllcountMercado(ArrayList<filterSql> filter) throws Exception {
/* 1317 */     int total = 0;
/* 1318 */     Statement stmt = null;
/* 1319 */     String sql = "";
/* 1320 */     ConnectionDB db = new ConnectionDB();
/*      */     
/*      */     try {
/* 1323 */       stmt = db.conn.createStatement();
/*      */       
/* 1325 */       sql = "SELECT  count(1) FROM (SELECT r.codProductor,m.mercado as nombre, IFNULL(bp.b,IFNULL(pc.b,r.bloqueado)) as valor FROM ( SELECT r.* FROM restriciones r   INNER JOIN (SELECT  MAX(idRestriciones) AS maxId,`codProductor`,idMercado,idTemporada,idEspecie FROM restriciones  where inicial='N'and carga!='L' and idEspecie=1 and idTemporada=2  GROUP BY `codProductor`,idMercado,idTemporada,idEspecie) rr  ON r.idEspecie=rr.idEspecie  and r.idTemporada=rr.idTemporada and rr.idTemporada=r.idTemporada and r.idMercado = rr.idMercado  and r.`codProductor`=rr.`codProductor` AND r.idRestriciones = rr.maxId) as r  left join productorBloqueo as bp on (bp.activo='Y' and bp.codProductor=r.codProductor and bp.idEspecie=r.idEspecie and bp.idTemporada=r.idTemporada and  bp.idMercado=r.idMercado)   inner join mercado m on (r.idMercado=m.idMercado)  inner join productor p on (r.codProductor=p.codProductor)  left join vw_productorCertificado pc on (r.codProductor=pc.codProductor) left join bloqueoOp bo on (bo.codProductor=r.codProductor and bo.idEspecie=r.idEspecie and bo.idTemporada=r.idTemporada  and  bo.idMercado=r.idMercado) where r.idEspecie=1  and r.idTemporada=2 and IF(bo.b='N',bo.b,IFNULL(bp.b,IFNULL(pc.b,r.bloqueado)))='N'  ) as p ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1341 */       if (filter.size() > 0) {
/* 1342 */         String andSql = "";
/* 1343 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 1344 */         Iterator<filterSql> f = filter.iterator();
/*      */         
/* 1346 */         while (f.hasNext()) {
/*      */           
/* 1348 */           filterSql row = f.next();
/* 1349 */           if (!row.getValue().equals("")) {
/*      */             
/* 1351 */             if (row.getCampo().endsWith("_to")) {
/*      */               
/* 1353 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 1354 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 1355 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*      */             }
/* 1357 */             else if (row.getCampo().endsWith("_from")) {
/*      */               
/* 1359 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 1360 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 1361 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*      */             } else {
/*      */               
/* 1364 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like'%" + row.getValue() + "%'";
/* 1365 */             }  andSql = " and ";
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1371 */       ResultSet rs = stmt.executeQuery(sql);
/* 1372 */       while (rs.next()) {
/* 1373 */         total = rs.getInt(1);
/*      */       }
/* 1375 */       rs.close();
/* 1376 */       stmt.close();
/* 1377 */       db.conn.close();
/*      */     
/*      */     }
/* 1380 */     catch (SQLException e) {
/*      */       
/* 1382 */       System.out.println("Error: " + e.getMessage());
/* 1383 */       System.out.println("sql: " + sql);
/* 1384 */       throw new Exception("getLimitesAll: " + e.getMessage());
/*      */     } finally {
/* 1386 */       db.close();
/*      */     } 
/* 1388 */     return total;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static ArrayList<restriccion> getAllMercado(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 1394 */     ArrayList<restriccion> arr = new ArrayList<>();
/* 1395 */     Statement stmt = null;
/* 1396 */     String sql = "";
/* 1397 */     ConnectionDB db = new ConnectionDB();
/*      */     
/*      */     try {
/* 1400 */       stmt = db.conn.createStatement();
/*      */       
/* 1402 */       sql = "SELECT * FROM (SELECT r.codProductor,m.mercado as nombre, IFNULL(bp.b,IFNULL(pc.b,r.bloqueado)) as valor,r.idEspecie FROM ( SELECT r.* FROM restriciones r   INNER JOIN (SELECT  MAX(idRestriciones) AS maxId,`codProductor`,idMercado,idTemporada,idEspecie FROM restriciones  where inicial='N'and carga!='L' and idEspecie=1 and idTemporada=2  GROUP BY `codProductor`,idMercado,idTemporada,idEspecie) rr  ON r.idEspecie=rr.idEspecie  and r.idTemporada=rr.idTemporada and rr.idTemporada=r.idTemporada and r.idMercado = rr.idMercado  and r.`codProductor`=rr.`codProductor` AND r.idRestriciones = rr.maxId) as r  left join productorBloqueo as bp on (bp.activo='Y' and bp.codProductor=r.codProductor and bp.idEspecie=r.idEspecie and bp.idTemporada=r.idTemporada and  bp.idMercado=r.idMercado)   inner join mercado m on (r.idMercado=m.idMercado)  inner join productor p on (r.codProductor=p.codProductor)  left join vw_productorCertificado pc on (r.codProductor=pc.codProductor) left join bloqueoOp bo on (bo.codProductor=r.codProductor and bo.idEspecie=r.idEspecie and bo.idTemporada=r.idTemporada  and  bo.idMercado=r.idMercado) where r.idEspecie=1  and r.idTemporada=2 and IF(bo.b='N',bo.b,IFNULL(bp.b,IFNULL(pc.b,r.bloqueado)))='N'  ) as p ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1419 */       if (filter.size() > 0) {
/* 1420 */         String andSql = "";
/* 1421 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 1422 */         Iterator<filterSql> f = filter.iterator();
/*      */         
/* 1424 */         while (f.hasNext()) {
/*      */           
/* 1426 */           filterSql row = f.next();
/*      */           
/* 1428 */           if (!row.getValue().equals("")) {
/*      */             
/* 1430 */             if (row.getCampo().endsWith("_to")) {
/*      */               
/* 1432 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 1433 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 1434 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*      */             }
/* 1436 */             else if (row.getCampo().endsWith("_from")) {
/*      */               
/* 1438 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 1439 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 1440 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*      */             } else {
/*      */               
/* 1443 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 1444 */             }  andSql = " and ";
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1449 */       if (!order.equals("")) {
/* 1450 */         sql = String.valueOf(sql) + " order by " + order;
/*      */       } else {
/*      */         
/* 1453 */         sql = String.valueOf(sql) + " order by codProductor ";
/*      */       } 
/* 1455 */       if (length > 0) {
/* 1456 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*      */       }
/* 1458 */       System.out.println("sql: " + sql);
/* 1459 */       ResultSet rs = stmt.executeQuery(sql);
/* 1460 */       while (rs.next()) {
/* 1461 */         restriccion o = new restriccion();
/*      */ 
/*      */         
/* 1464 */         o.setCodProductor(rs.getString("codProductor"));
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1469 */         o.setMercado(rs.getString("nombre"));
/* 1470 */         if (rs.getString("idEspecie").equals("1")) {
/* 1471 */           o.setEspecie("ARANDANO");
/*      */         } else {
/* 1473 */           o.setEspecie("CEREZA");
/* 1474 */         }  o.setAutomatica("");
/* 1475 */         arr.add(o);
/*      */       } 
/* 1477 */       rs.close();
/* 1478 */       stmt.close();
/* 1479 */       db.conn.close();
/*      */     }
/* 1481 */     catch (SQLException e) {
/*      */       
/* 1483 */       System.out.println("Error: " + e.getMessage());
/* 1484 */       System.out.println("sql: " + sql);
/* 1485 */       throw new Exception("getLimite: " + e.getMessage());
/*      */     } finally {
/* 1487 */       db.close();
/*      */     } 
/*      */     
/* 1490 */     return arr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getAllcountZona5(ArrayList<filterSql> filter) throws Exception {
/* 1499 */     int total = 0;
/* 1500 */     Statement stmt = null;
/* 1501 */     String sql = "";
/* 1502 */     ConnectionDB db = new ConnectionDB();
/*      */     
/*      */     try {
/* 1505 */       stmt = db.conn.createStatement();
/*      */       
/* 1507 */       sql = "SELECT  count(1) FROM (SELECT distinct z.codProductor,  d.codProducto, z.limite,  z.especie, z.Automatica, z.mail, z.nMuestra  FROM vw_bloqueadosZona as z inner join diccionario as d on (z.codProducto=d.codRemplazo and d.codProducto not in ('MULTI','MULTIE','MULTIN'))) as d ";
/*      */ 
/*      */ 
/*      */       
/* 1511 */       if (filter.size() > 0) {
/* 1512 */         String andSql = "";
/* 1513 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 1514 */         Iterator<filterSql> f = filter.iterator();
/*      */         
/* 1516 */         while (f.hasNext()) {
/*      */           
/* 1518 */           filterSql row = f.next();
/* 1519 */           if (!row.getValue().equals("")) {
/*      */             
/* 1521 */             if (row.getCampo().endsWith("_to")) {
/*      */               
/* 1523 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 1524 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 1525 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*      */             }
/* 1527 */             else if (row.getCampo().endsWith("_from")) {
/*      */               
/* 1529 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 1530 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 1531 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*      */             } else {
/*      */               
/* 1534 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like'%" + row.getValue() + "%'";
/* 1535 */             }  andSql = " and ";
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1541 */       ResultSet rs = stmt.executeQuery(sql);
/* 1542 */       while (rs.next()) {
/* 1543 */         total = rs.getInt(1);
/*      */       }
/* 1545 */       rs.close();
/* 1546 */       stmt.close();
/* 1547 */       db.conn.close();
/*      */     
/*      */     }
/* 1550 */     catch (SQLException e) {
/*      */       
/* 1552 */       System.out.println("Error: " + e.getMessage());
/* 1553 */       System.out.println("sql: " + sql);
/* 1554 */       throw new Exception("getLimitesAll: " + e.getMessage());
/*      */     } finally {
/* 1556 */       db.close();
/*      */     } 
/* 1558 */     return total;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static ArrayList<restriccion> getAllZona5(ArrayList<filterSql> filter, String order, int start, int length) throws Exception {
/* 1564 */     ArrayList<restriccion> arr = new ArrayList<>();
/* 1565 */     Statement stmt = null;
/* 1566 */     String sql = "";
/* 1567 */     ConnectionDB db = new ConnectionDB();
/*      */     
/*      */     try {
/* 1570 */       stmt = db.conn.createStatement();
/*      */       
/* 1572 */       sql = "SELECT distinct z.codProductor,  d.codProducto, z.limite,  z.especie, z.Automatica, z.mail, z.nMuestra,z.fechaIngreso  FROM vw_bloqueadosZona as z inner join diccionario as d on (z.codProducto=d.codRemplazo and d.codProducto not in ('MULTI','MULTIE','MULTIN'))";
/*      */ 
/*      */ 
/*      */       
/* 1576 */       if (filter.size() > 0) {
/* 1577 */         String andSql = "";
/* 1578 */         andSql = String.valueOf(andSql) + " WHERE ";
/* 1579 */         Iterator<filterSql> f = filter.iterator();
/*      */         
/* 1581 */         while (f.hasNext()) {
/*      */           
/* 1583 */           filterSql row = f.next();
/*      */           
/* 1585 */           if (!row.getValue().equals("")) {
/*      */             
/* 1587 */             if (row.getCampo().endsWith("_to")) {
/*      */               
/* 1589 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 1590 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 1591 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*      */             }
/* 1593 */             else if (row.getCampo().endsWith("_from")) {
/*      */               
/* 1595 */               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 1596 */               SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
/* 1597 */               sql = String.valueOf(sql) + andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='" + sqlDate.format(formatter.parse(row.getValue())) + "'";
/*      */             } else {
/*      */               
/* 1600 */               sql = String.valueOf(sql) + andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
/* 1601 */             }  andSql = " and ";
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1606 */       if (!order.equals("")) {
/* 1607 */         sql = String.valueOf(sql) + " order by " + order;
/*      */       } else {
/*      */         
/* 1610 */         sql = String.valueOf(sql) + " order by codProductor ";
/*      */       } 
/* 1612 */       if (length > 0) {
/* 1613 */         sql = String.valueOf(sql) + " limit " + start + "," + length + " ";
/*      */       }
/* 1615 */       System.out.println("sql: " + sql);
/* 1616 */       ResultSet rs = stmt.executeQuery(sql);
/* 1617 */       while (rs.next()) {
/* 1618 */         restriccion o = new restriccion();
/*      */ 
/*      */         
/* 1621 */         o.setCodProductor(rs.getString("codProductor"));
/* 1622 */         o.setnMuestra(rs.getString("nMuestra"));
/* 1623 */         o.setCodProducto(rs.getString("codProducto"));
/* 1624 */         o.setLimite(rs.getString("limite"));
/* 1625 */         o.setFecha(rs.getString("fechaIngreso"));
/*      */         
/* 1627 */         o.setEspecie(rs.getString("especie"));
/* 1628 */         o.setAutomatica(rs.getString("Automatica"));
/* 1629 */         arr.add(o);
/*      */       } 
/* 1631 */       rs.close();
/* 1632 */       stmt.close();
/* 1633 */       db.conn.close();
/*      */     }
/* 1635 */     catch (SQLException e) {
/*      */       
/* 1637 */       System.out.println("Error: " + e.getMessage());
/* 1638 */       System.out.println("sql: " + sql);
/* 1639 */       throw new Exception("getLimite: " + e.getMessage());
/*      */     } finally {
/* 1641 */       db.close();
/*      */     } 
/*      */     
/* 1644 */     return arr;
/*      */   }
/*      */   public static JSONArray getEstadoProductorSyncSAP(int idTemporada, int idEspecie, String productor, String nombre, Boolean titulo) throws Exception {
/* 1647 */     JSONArray rows = null;
/* 1648 */     ConnectionDB db = new ConnectionDB();
/* 1649 */     Statement stmt = null;
/* 1650 */     String sql = "";
/*      */     
/*      */     try {
/* 1653 */       stmt = db.conn.createStatement();
/*      */       
/* 1655 */       sql = "{CALL sp_viewRestricionesSAPSync(" + idTemporada + "," + idEspecie + ",'" + productor + "','','','" + nombre + "') }";
/* 1656 */       System.out.println(sql);
/*      */       
/* 1658 */       ResultSet rs = stmt.executeQuery(sql);
/*      */       
/* 1660 */       rows = ResultSet2Json.getJson(rs);
/*      */       
/* 1662 */       rs.close();
/* 1663 */       stmt.close();
/* 1664 */       db.conn.close();
/*      */     }
/* 1666 */     catch (SQLException e) {
/*      */       
/* 1668 */       System.out.println("Error: " + e.getMessage());
/* 1669 */       System.out.println("sql: " + sql);
/* 1670 */       throw new Exception("getEstadoProductor: " + e.getMessage());
/*      */     } finally {
/* 1672 */       db.close();
/*      */     } 
/*      */     
/* 1675 */     return rows;
/*      */   }
/*      */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\estadoProductorDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */