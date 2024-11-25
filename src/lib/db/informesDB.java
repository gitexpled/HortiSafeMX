/*     */ package lib.db;
/*     */ 
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class informesDB
/*     */ {
/*     */   public static String getDetalleRestriccion(String mercado, 
				String productor, String parcela, String turno, 
				String idEspecie,String variedad) throws Exception {
/*  12 */     ArrayList<String[]> data = (ArrayList)new ArrayList<>();
/*     */     
/*  14 */     ConnectionDB db = new ConnectionDB();
/*  15 */     Statement stmt = null;
/*  16 */     String sql = "";
/*  17 */     String html = "";
/*     */     
/*     */     try {
/*  20 */       stmt = db.conn.createStatement();
/*     */       
/*  22 */       sql = "{CALL sp_viewDetalleRest('" + mercado + "','" + productor + "','" + parcela + "','" + turno + "'," + idEspecie + ",'"+variedad+"') }";
/*  23 */       System.out.println("sql: " + sql);
/*     */       
/*  25 */       ResultSet rs = stmt.executeQuery(sql);
/*     */       
/*  27 */       ResultSetMetaData rsmd = rs.getMetaData();
/*  28 */       int columnCount = rsmd.getColumnCount();
/*     */       
/*  30 */       int j = 0;
/*  31 */       html = "<table border=1>";
/*     */       
/*  33 */       html = String.valueOf(html) + "<tr>";
/*  34 */       for (int i = 1; i <= columnCount; i++) {
/*  35 */         html = String.valueOf(html) + "<td>&nbsp;";
/*     */         
/*  37 */         if (rsmd.getColumnLabel(i).equals("resultado")) {
/*  38 */           html = String.valueOf(html) + "Resultado (ppm)";
/*     */         } else {
/*     */           
/*  41 */           String htmlData = rsmd.getColumnLabel(i);
/*  42 */           htmlData = String.valueOf(htmlData.substring(0, 1).toUpperCase()) + htmlData.substring(1).toLowerCase();
/*  43 */           html = String.valueOf(html) + htmlData;
/*     */         } 
/*  45 */         html = String.valueOf(html) + "&nbsp;</td>";
/*     */       } 
/*     */       
/*  48 */       html = String.valueOf(html) + "</tr>";
/*     */       
/*  50 */       while (rs.next()) {
/*     */         
/*  52 */         String[] o = new String[columnCount];
/*  53 */         html = String.valueOf(html) + "<tr>";
/*  54 */         for (int k = 1; k <= columnCount; k++) {
/*  55 */           html = String.valueOf(html) + "<td>&nbsp;";
/*  56 */           o[k - 1] = rs.getString(k);
/*  57 */           if (rs.getString(k) == null) {
/*  58 */             html = (new StringBuilder(String.valueOf(html))).toString();
/*     */           } else {
/*  60 */             html = String.valueOf(html) + o[k - 1];
/*  61 */           }  html = String.valueOf(html) + "&nbsp;</td>";
/*     */         } 
/*     */         
/*  64 */         html = String.valueOf(html) + "</tr>";
/*  65 */         j++;
/*  66 */         data.add(o);
/*     */       } 
/*  68 */       html = String.valueOf(html) + "</table>";
/*  69 */       rs.close();
/*     */       
/*  71 */       stmt.close();
/*  72 */       db.conn.close();
/*     */     }
/*  74 */     catch (SQLException e) {
/*     */       
/*  76 */       System.out.println("Error: " + e.getMessage());
/*  77 */       System.out.println("sql: " + sql);
/*  78 */       throw new Exception("getEstadoProductor: " + e.getMessage());
/*     */     } finally {
/*  80 */       db.close();
/*     */     } 
/*     */     
/*  83 */     return html;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getDetalleRestriccionM(String mercado, 
		String productor, String parcela, 
		String turno, 
		String idEspecie,
		String variedad) throws Exception {
/*  88 */     ArrayList<String[]> data = (ArrayList)new ArrayList<>();
/*     */     
/*  90 */     ConnectionDB db = new ConnectionDB();
/*  91 */     Statement stmt = null;
/*  92 */     String sql = "";
/*  93 */     String html = "";
/*     */     
/*     */     try {
/*  96 */       stmt = db.conn.createStatement();
/*     */       
/*  98 */       sql = "{CALL sp_viewDetalleRestM('" + mercado + "','" + productor + "','" + parcela + "','" + turno + "'," + idEspecie + ",'"+variedad+"') }";
/*  99 */       System.out.println("sql: " + sql);
/*     */       
/* 101 */       ResultSet rs = stmt.executeQuery(sql);
/*     */       
/* 103 */       ResultSetMetaData rsmd = rs.getMetaData();
/* 104 */       int columnCount = rsmd.getColumnCount();
/*     */       
/* 106 */       int j = 0;
/* 107 */       html = "<table border=1>";
/*     */       
/* 109 */       html = String.valueOf(html) + "<tr>";
/* 110 */       for (int i = 1; i <= columnCount; i++) {
/* 111 */         html = String.valueOf(html) + "<td>&nbsp;";
/*     */         
/* 113 */         if (rsmd.getColumnLabel(i).equals("resultado")) {
/* 114 */           html = String.valueOf(html) + "Resultado (ppm)";
/*     */         } else {
/*     */           
/* 117 */           String htmlData = rsmd.getColumnLabel(i);
/* 118 */           htmlData = String.valueOf(htmlData.substring(0, 1).toUpperCase()) + htmlData.substring(1).toLowerCase();
/* 119 */           html = String.valueOf(html) + htmlData;
/*     */         } 
/* 121 */         html = String.valueOf(html) + "&nbsp;</td>";
/*     */       } 
/*     */       
/* 124 */       html = String.valueOf(html) + "</tr>";
/*     */       
/* 126 */       while (rs.next()) {
/*     */         
/* 128 */         String[] o = new String[columnCount];
/* 129 */         html = String.valueOf(html) + "<tr>";
/* 130 */         for (int k = 1; k <= columnCount; k++) {
/* 131 */           html = String.valueOf(html) + "<td>&nbsp;";
/* 132 */           o[k - 1] = rs.getString(k);
/* 133 */           if (rs.getString(k) == null) {
/* 134 */             html = (new StringBuilder(String.valueOf(html))).toString();
/*     */           } else {
/* 136 */             html = String.valueOf(html) + o[k - 1];
/* 137 */           }  html = String.valueOf(html) + "&nbsp;</td>";
/*     */         } 
/*     */         
/* 140 */         html = String.valueOf(html) + "</tr>";
/* 141 */         j++;
/* 142 */         data.add(o);
/*     */       } 
/* 144 */       html = String.valueOf(html) + "</table>";
/* 145 */       rs.close();
/*     */       
/* 147 */       stmt.close();
/* 148 */       db.conn.close();
/*     */     }
/* 150 */     catch (SQLException e) {
/*     */       
/* 152 */       System.out.println("Error: " + e.getMessage());
/* 153 */       System.out.println("sql: " + sql);
/* 154 */       throw new Exception("getEstadoProductor: " + e.getMessage());
/*     */     } finally {
/* 156 */       db.close();
/*     */     } 
/*     */     
/* 159 */     return html;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getDetalleExcel(String id) throws Exception {
/* 164 */     ArrayList<String[]> data = (ArrayList)new ArrayList<>();
/*     */     
/* 166 */     ConnectionDB db = new ConnectionDB();
/* 167 */     Statement stmt = null;
/* 168 */     String sql = "";
/* 169 */     String html = "";
/*     */     
/*     */     try {
/* 172 */       stmt = db.conn.createStatement();
/*     */       
/* 174 */       sql = "{CALL sp_viewDetalleExcel('" + id + "') }";
/*     */ 
/*     */       
/* 177 */       ResultSet rs = stmt.executeQuery(sql);
/*     */       
/* 179 */       ResultSetMetaData rsmd = rs.getMetaData();
/* 180 */       int columnCount = rsmd.getColumnCount();
/*     */       
/* 182 */       int j = 0;
/* 183 */       html = "<table border=1>";
/*     */       
/* 185 */       html = String.valueOf(html) + "<tr>";
/* 186 */       for (int i = 1; i <= columnCount; i++) {
/* 187 */         html = String.valueOf(html) + "<td>";
/*     */         
/* 189 */         html = String.valueOf(html) + rsmd.getColumnName(i);
/* 190 */         html = String.valueOf(html) + "</td>";
/*     */       } 
/*     */       
/* 193 */       html = String.valueOf(html) + "</tr>";
/*     */       
/* 195 */       while (rs.next()) {
/*     */         
/* 197 */         String[] o = new String[columnCount];
/* 198 */         html = String.valueOf(html) + "<tr>";
/* 199 */         for (int k = 1; k <= columnCount; k++) {
/* 200 */           html = String.valueOf(html) + "<td>";
/* 201 */           o[k - 1] = rs.getString(k);
/* 202 */           if (rs.getString(k) == null) {
/* 203 */             html = (new StringBuilder(String.valueOf(html))).toString();
/*     */           } else {
/* 205 */             html = String.valueOf(html) + o[k - 1];
/* 206 */           }  html = String.valueOf(html) + "</td>";
/*     */         } 
/*     */         
/* 209 */         html = String.valueOf(html) + "</tr>";
/* 210 */         j++;
/* 211 */         data.add(o);
/*     */       } 
/* 213 */       html = String.valueOf(html) + "</table>";
/* 214 */       rs.close();
/* 215 */       stmt.close();
/* 216 */       db.conn.close();
/*     */     }
/* 218 */     catch (SQLException e) {
/*     */       
/* 220 */       System.out.println("Error: " + e.getMessage());
/* 221 */       System.out.println("sql: " + sql);
/* 222 */       throw new Exception("getEstadoProductor: " + e.getMessage());
/*     */     } finally {
/* 224 */       db.close();
/*     */     } 
/*     */     
/* 227 */     return html;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\informesDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */