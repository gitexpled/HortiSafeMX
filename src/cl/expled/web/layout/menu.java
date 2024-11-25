/*     */ package cl.expled.web.layout;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import lib.db.systemMenuDB;
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
/*     */ public class menu
/*     */ {
/*     */   public String[] create(int idPadre, String selectMenu, int perfil) {
/*  31 */     String[] res = { "", "" };
/*  32 */     String str = "";
/*  33 */     selectMenu = selectMenu.replace(".jsp", "");
/*     */     
/*     */     try {
/*  36 */       ArrayList<systemMenu> datos = systemMenuDB.getMenu(idPadre, perfil);
/*     */       
/*  38 */       Iterator<systemMenu> f = datos.iterator();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  43 */       while (f.hasNext()) {
/*  44 */         systemMenu row = f.next();
/*  45 */         String menuRoot = "";
/*  46 */         String menuRow = "";
/*  47 */         String menuActive = "";
/*     */         
/*  49 */         if (row.getCount() > 0) {
/*     */ 
/*     */           
/*  52 */           String[] array = create(row.getIdMenu(), selectMenu, perfil);
/*  53 */           if (array[0].equals("Y")) {
/*     */             
/*  55 */             menuRoot = "start active open";
/*  56 */             menuRow = "open";
/*  57 */             res[0] = "Y";
/*     */           } 
/*  59 */           String icono = "";
/*  60 */           if (row.getIcono() != null)
/*  61 */             icono = "<i class='" + row.getIcono() + "'></i>"; 
/*  62 */           System.out.println(String.valueOf(row.getMenu()) + " -> " + res[0]);
/*  63 */           str = String.valueOf(str) + "<li class='nav-item " + menuRoot + "'><a href='javascript:;'";
/*  64 */           str = String.valueOf(str) + "class='nav-link nav-toggle'>" + icono + "  <span";
/*  65 */           str = String.valueOf(str) + " class='title'>" + row.getMenu() + "</span> <span class='arrow " + menuRow + "'></span></a>";
/*  66 */           str = String.valueOf(str) + "\t\t<ul class='sub-menu'>";
/*  67 */           str = String.valueOf(str) + array[1];
/*     */           
/*  69 */           str = String.valueOf(str) + "</ul>";
/*  70 */           str = String.valueOf(str) + "</li>";
/*     */           
/*     */           continue;
/*     */         } 
/*  74 */         if (row.getUrl().equals(selectMenu)) {
/*     */           
/*  76 */           res[0] = "Y";
/*  77 */           menuActive = "start active open";
/*     */         } 
/*     */         
/*  80 */         if (row.getAdm().equals("Y")) {
/*  81 */           str = String.valueOf(str) + "\t\t\t<li class='nav-item  " + menuActive + "'><a href='/HortiSafeMX/webApp/pageAdm/" + row.getUrl() + "'";
/*     */         } else {
/*  83 */           str = String.valueOf(str) + "\t\t\t<li class='nav-item  " + menuActive + "'><a href='/HortiSafeMX/webApp/page/" + row.getUrl() + "'";
/*  84 */         }  str = String.valueOf(str) + "\t\t\t\tclass='nav-link '> <span class='title'>" + row.getMenu() + "</span>";
/*  85 */         str = String.valueOf(str) + "\t\t\t</a></li>";
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*  93 */     catch (Exception e) {
/*     */       
/*  95 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 100 */     res[1] = str;
/* 101 */     return res;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\cl\expled\web\layout\menu.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */