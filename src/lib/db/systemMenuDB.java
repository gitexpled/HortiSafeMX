/*     */ package lib.db;
/*     */ 
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import lib.struc.systemMenu;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class systemMenuDB
/*     */ {
/*     */   public static ArrayList<systemMenu> getMenu(int idPadre, int idPerfil) throws Exception {
/*  21 */     ArrayList<systemMenu> menus = new ArrayList<>();
/*  22 */     Statement stmt = null;
/*  23 */     PreparedStatement ps = null;
/*  24 */     String sql = "";
/*  25 */     ConnectionDB db = new ConnectionDB();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  30 */       sql = "SELECT m.*,c.numero FROM systemMenu m inner join systemMenuPerfil p on (m.idMenu=p.idMenu and p.idPerfil=? )left join (SELECT idPadre,count(1) as numero FROM systemMenu group by idPadre) c on (m.idMenu=c.idPadre) where m.idPadre=?  order by m.orden ";
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  35 */       ps = db.conn.prepareStatement(sql);
/*  36 */       ps.setInt(1, idPerfil);
/*  37 */       ps.setInt(2, idPadre);
/*     */ 
/*     */       
/*  40 */       ResultSet rs = ps.executeQuery();
/*  41 */       while (rs.next()) {
/*  42 */         systemMenu o = new systemMenu();
/*     */         
/*  44 */         o.setIdMenu(rs.getInt("idMenu"));
/*  45 */         o.setIdPadre(rs.getInt("idPadre"));
/*  46 */         o.setMenu(rs.getString("menu"));
/*  47 */         o.setIcono(rs.getString("icono"));
/*  48 */         o.setUrl(rs.getString("url"));
/*  49 */         o.setCount(rs.getInt("numero"));
/*  50 */         o.setAdm(rs.getString("adm"));
/*     */         
/*  52 */         menus.add(o);
/*     */       } 
/*  54 */       rs.close();
/*  55 */       ps.close();
/*  56 */       db.conn.close();
/*     */     }
/*  58 */     catch (SQLException e) {
/*     */       
/*  60 */       System.out.println("Error: " + e.getMessage());
/*  61 */       System.out.println("sql: " + sql);
/*  62 */       throw new Exception("getMenu: " + e.getMessage());
/*     */     } finally {
/*  64 */       db.close();
/*     */     } 
/*     */     
/*  67 */     return menus;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static systemMenu getMenuUrl(String id) throws Exception {
/*  73 */     systemMenu o = new systemMenu();
/*  74 */     Statement stmt = null;
/*  75 */     PreparedStatement ps = null;
/*  76 */     String sql = "";
/*  77 */     ConnectionDB db = new ConnectionDB();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  82 */       sql = "SELECT m.* FROM systemMenu m  where m.url=?";
/*     */ 
/*     */       
/*  85 */       ps = db.conn.prepareStatement(sql);
/*  86 */       ps.setString(1, id);
/*     */ 
/*     */       
/*  89 */       ResultSet rs = ps.executeQuery();
/*  90 */       while (rs.next()) {
/*     */ 
/*     */         
/*  93 */         o.setIdMenu(rs.getInt("idMenu"));
/*  94 */         o.setIdPadre(rs.getInt("idPadre"));
/*  95 */         o.setMenu(rs.getString("menu"));
/*  96 */         o.setIcono(rs.getString("icono"));
/*  97 */         o.setUrl(rs.getString("url"));
/*     */         
/*  99 */         o.setAdm(rs.getString("adm"));
/* 100 */         o.setProceso(rs.getString("proceso"));
/*     */       } 
/*     */ 
/*     */       
/* 104 */       rs.close();
/* 105 */       ps.close();
/* 106 */       db.conn.close();
/*     */     }
/* 108 */     catch (SQLException e) {
/*     */       
/* 110 */       System.out.println("Error: " + e.getMessage());
/* 111 */       System.out.println("sql: " + sql);
/* 112 */       throw new Exception("getMenu: " + e.getMessage());
/*     */     } finally {
/* 114 */       db.close();
/*     */     } 
/*     */     
/* 117 */     return o;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\db\systemMenuDB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */