/*     */ package cl.expled.web.page;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import lib.db.MercadoDB;
/*     */ import lib.security.session;
/*     */ import lib.struc.Mercado;
/*     */ import lib.struc.filterSql;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.Model;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.servlet.ModelAndView;
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
/*     */ @Controller
/*     */ public class informes
/*     */ {
/*     */   @RequestMapping({"/adm/produccion_estadoProductor"})
/*     */   public ModelAndView estadoProductorProContent(Model model, HttpSession httpSession) {
/*  60 */     session ses = new session(httpSession);
/*  61 */     if (ses.isValid()) {
/*  62 */       return new ModelAndView("redirect:/webApp/login");
/*     */     }
/*  64 */     ArrayList<Mercado> mer = null;
/*     */     try {
/*  66 */       ArrayList<filterSql> filter = new ArrayList<>();
/*  67 */       filterSql fs = new filterSql();
/*  68 */       fs.setCampo("visible");
/*  69 */       fs.setValue("Y");
/*  70 */       filter.add(fs);
/*  71 */       mer = MercadoDB.getMercado(filter, "idMercado", 0, 9999);
/*  72 */     } catch (Exception e) {
/*     */       
/*  74 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/*  78 */     List<Mercado> aa = mer;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  83 */     model.addAttribute("th", mer);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     return new ModelAndView("content/produccion/estadoProductor");
/*     */   }
/*     */   
/*     */   @RequestMapping({"/adm/informe_estadoProductor"})
/*     */   public ModelAndView estadoProductorContent(Model model, HttpSession httpSession) {
/*  93 */     session ses = new session(httpSession);
/*  94 */     if (ses.isValid()) {
/*  95 */       return new ModelAndView("redirect:/webApp/login");
/*     */     }
/*  97 */     ArrayList<Mercado> mer = null;
/*     */     try {
/*  99 */       ArrayList<filterSql> filter = new ArrayList<>();
/* 100 */       filterSql fs = new filterSql();
/* 101 */       fs.setCampo("visible");
/* 102 */       fs.setValue("Y");
/* 103 */       filter.add(fs);
/* 104 */       mer = MercadoDB.getMercado(filter, "idMercado", 0, 9999);
/* 105 */     } catch (Exception e) {
/*     */       
/* 107 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/* 111 */     List<Mercado> aa = mer;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     model.addAttribute("th", mer);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 121 */     return new ModelAndView("content/informe/estadoProductor");
/*     */   }
/*     */   @RequestMapping({"/adm/informe_estadoProductorOrg"})
/*     */   public ModelAndView estadoProductorContentOrg(Model model, HttpSession httpSession) {
/* 125 */     session ses = new session(httpSession);
/* 126 */     if (ses.isValid()) {
/* 127 */       return new ModelAndView("redirect:/webApp/login");
/*     */     }
/* 129 */     ArrayList<Mercado> mer = null;
/*     */     try {
/* 131 */       ArrayList<filterSql> filter = new ArrayList<>();
/* 132 */       filterSql fs = new filterSql();
/* 133 */       fs.setCampo("visible");
/* 134 */       fs.setValue("Y");
/* 135 */       filter.add(fs);
/* 136 */       mer = MercadoDB.getMercado(filter, "idMercado", 0, 9999);
/* 137 */     } catch (Exception e) {
/*     */       
/* 139 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/* 143 */     List<Mercado> aa = mer;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 148 */     model.addAttribute("th", mer);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 153 */     return new ModelAndView("content/informe/estadoProductorOrg");
/*     */   }
/*     */   
/*     */   @RequestMapping({"/adm/informe_exportarSap"})
/*     */   public ModelAndView exportarSap(Model model, HttpSession httpSession) {
/* 158 */     session ses = new session(httpSession);
/* 159 */     if (ses.isValid()) {
/* 160 */       return new ModelAndView("redirect:/webApp/login");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 167 */     return new ModelAndView("content/informe/exportarSap");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/adm/informe_estadoProductor2"})
/*     */   public ModelAndView estadoProductorContent2(Model model, HttpSession httpSession) {
/* 174 */     session ses = new session(httpSession);
/* 175 */     if (ses.isValid()) {
/* 176 */       return new ModelAndView("redirect:/webApp/login");
/*     */     }
/* 178 */     ArrayList<Mercado> mer = null;
/*     */     try {
/* 180 */       ArrayList<filterSql> filter = new ArrayList<>();
/* 181 */       mer = MercadoDB.getMercado(filter, "idMercado", 0, 9999);
/* 182 */     } catch (Exception e) {
/*     */       
/* 184 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/* 188 */     List<Mercado> aa = mer;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 193 */     model.addAttribute("th", mer);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 198 */     return new ModelAndView("content/informe/estadoProductor2");
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\cl\expled\web\page\informes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */