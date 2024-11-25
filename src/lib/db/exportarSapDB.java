/*     */ package lib.db;
/*     */ 
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class exportarSapDB
/*     */ {
/*     */   public static ArrayList<String[]> view(int idTemporada) throws Exception {
/*  16 */     ArrayList<String[]> data = (ArrayList)new ArrayList<>();
/*     */     
/*  18 */     ConnectionDB db = new ConnectionDB();
/*  19 */     Statement stmt = null;
/*  20 */     String sql = "";
/*  21 */     String rest = "";
/*  22 */     int export = 0;
/*     */     
/*     */     try {
/*  25 */       stmt = db.conn.createStatement();
/*     */       
/*  27 */       sql = "{CALL sp_viewRestricionesSAP(" + idTemporada + ") }";
/*     */ 
/*     */       
/*  30 */       ResultSet rs = stmt.executeQuery(sql);
/*     */       
/*  32 */       ResultSetMetaData rsmd = rs.getMetaData();
/*  33 */       int columnCount = rsmd.getColumnCount();
/*     */       
/*  35 */       int j = 0;
/*  36 */       while (rs.next()) {
/*     */         
/*  38 */         String[] o = new String[columnCount + 1];
/*     */         
/*  40 */         for (int i = 1; i <= columnCount; i++) {
/*  41 */           if (rs.getString(i) == null) {
/*  42 */             o[i - 1] = "";
/*     */           } else {
/*  44 */             o[i - 1] = rs.getString(i).toString().replace("  ", "").trim();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  51 */             if (i == 2) {
/*  52 */               String[] tmp = rs.getString(i).toString().split(" ");
/*  53 */               Arrays.sort((Object[])tmp);
/*  54 */               StringBuilder builder = new StringBuilder(); byte b; int k; String[] arrayOfString1;
/*  55 */               for (k = (arrayOfString1 = tmp).length, b = 0; b < k; ) { String value = arrayOfString1[b];
/*  56 */                 rest = value.trim();
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
/*  73 */                 if (!rest.equals(""))
/*     */                 {
/*  75 */                   builder.append(String.valueOf(rest) + ","); }  b++; }
/*     */               
/*  77 */               System.out.println(builder.toString());
/*  78 */               String text = builder.toString().trim();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*  84 */               text = text.replace("BA,BB,BC,BD,BE,BH,BI,BJ,BK,BL,BM,BN,BO,BR,BT,BU", "BX");
/*  85 */               text = text.replace("CA,CB,CC,CD,CE,CH,CI,CJ,CK,CL,CM,CN,CO,CR,CT,CU", "CX");
/*  86 */               text = text.replace(",", " ").trim();
/*     */               
/*  88 */               o[i - 1] = text.trim();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*  95 */         j++;
/*  96 */         data.add(o);
/*     */       } 
/*     */       
/*  99 */       rs.close();
/* 100 */       stmt.close();
/* 101 */       db.conn.close();
/*     */     }
/* 103 */     catch (SQLException e) {
/*     */       
/* 105 */       System.out.println("Error: " + e.getMessage());
/* 106 */       System.out.println("sql: " + sql);
/* 107 */       throw new Exception("getEstadoProductor: " + e.getMessage());
/*     */     } finally {
/* 109 */       db.close();
/*     */     } 
/*     */     
/* 112 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getEspecies(int codProductor) throws Exception {
/* 118 */     int export = 0;
/* 119 */     ConnectionDB db = new ConnectionDB();
/* 120 */     Statement stmt = null;
/* 121 */     String sql = "";
/*     */     
/*     */     try {
/* 124 */       stmt = db.conn.createStatement();
/*     */       
/* 126 */       sql = "SELECT arandano,cereza FROM productor\nWHERE codProductor = " + 
/* 127 */         codProductor + 
/* 128 */         " AND bloqueado = 'N'";
/*     */       
/* 130 */       ResultSet rs = stmt.executeQuery(sql);
/*     */       
/* 132 */       if (rs.next())
/*     */       {
/* 134 */         if (rs.getString("cereza") == null) {
/* 135 */           if (rs.getString("arandano").equalsIgnoreCase("Y") && rs.getString("cereza") == null) {
/* 136 */             export = 1;
/*     */           }
/*     */         }
/* 139 */         else if (rs.getString("arandano").equalsIgnoreCase("Y") && rs.getString("cereza").equalsIgnoreCase("Y")) {
/* 140 */           export = 0;
/* 141 */         } else if ((rs.getString("arandano").equalsIgnoreCase("Y") && rs.getString("cereza").equalsIgnoreCase("N")) || rs.getString("cereza").equalsIgnoreCase("")) {
/* 142 */           export = 1;
/* 143 */         } else if (rs.getString("arandano").equalsIgnoreCase("N") && rs.getString("cereza").equalsIgnoreCase("Y")) {
/* 144 */           export = 2;
/* 145 */         } else if (rs.getString("arandano").equalsIgnoreCase("N") && rs.getString("cereza").equalsIgnoreCase("N")) {
/* 146 */           export = 3;
/*     */         } 
/*     */       }
/*     */       
/* 150 */       rs.close();
/* 151 */       stmt.close();
/* 152 */       db.conn.close();
/*     */     }
/* 154 */     catch (SQLException e) {
/*     */       
/* 156 */       System.out.println("Error: " + e.getMessage());
/* 157 */       System.out.println("sql: " + sql);
/* 158 */       throw new Exception("getEspecies: " + e.getMessage());
/*     */     } finally {
/* 160 */       db.close();
/*     */     } 
/*     */     
/* 163 */     return export;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getEstadoProductor(int idTemporada, int idEspecie, String productor, String Mercado) throws Exception {
/* 169 */     String Estado = "";
/* 170 */     ConnectionDB db = new ConnectionDB();
/* 171 */     Statement stmt = null;
/* 172 */     String sql = "";
/*     */     
/*     */     try {
/* 175 */       stmt = db.conn.createStatement();
/*     */       
/* 177 */       sql = "{CALL sp_viewRestriciones(" + idTemporada + "," + idEspecie + ",'" + productor + "') }";
/*     */       
/* 179 */       ResultSet rs = stmt.executeQuery(sql);
/*     */       
/* 181 */       if (rs.next())
/*     */       {
/* 183 */         Estado = rs.getString(Mercado);
/*     */       }
/* 185 */       rs.close();
/* 186 */       stmt.close();
/* 187 */       db.conn.close();
/*     */     }
/* 189 */     catch (SQLException e) {
/*     */       
/* 191 */       System.out.println("Error: " + e.getMessage());
/* 192 */       System.out.println("sql: " + sql);
/* 193 */       throw new Exception("getEstadoProductor: " + e.getMessage());
/*     */     } finally {
/* 195 */       db.close();
/*     */     } 
/*     */     
/* 198 */     return Estado;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\exportarSapDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */