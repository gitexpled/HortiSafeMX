/*     */ package cl.expled.web.layout;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import lib.io.config;
/*     */ import lib.security.session;
/*     */ import lib.struc.filterSql;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.Model;
/*     */ import org.springframework.web.bind.annotation.PathVariable;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.servlet.ModelAndView;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Controller
/*     */ public class Element
/*     */ {
/*     */   @RequestMapping(value = {"/layout_menu/{id:.+}"}, method = {RequestMethod.GET})
/*     */   public ModelAndView menu(Model model, @PathVariable String id, HttpSession httpSession) {
/*  27 */     session ses = new session(httpSession);
/*  28 */     if (ses.isValid()) {
/*  29 */       return new ModelAndView("redirect:/webApp/login");
/*     */     }
/*  31 */     id = id.replace(".jsp", "");
/*  32 */     System.out.println("PERFIL::::::::::::::::::::::::::::_" + id + "=" + ses.getIdPerfil());
/*     */ 
/*     */ 
/*     */     
/*  36 */     menu m = new menu();
/*  37 */     String[] strMenu = m.create(0, id, ses.getIdPerfil());
/*     */     
/*  39 */     model.addAttribute("menu", strMenu[1]);
/*  40 */     model.addAttribute("hola", "2");
/*     */     
/*  42 */     return new ModelAndView("layout/menu");
/*     */   }
/*     */   
/*     */   @RequestMapping({"/layout_user"})
/*     */   public ModelAndView menu_user(Model model, HttpSession httpSession) {
/*  47 */     session ses = new session(httpSession);
/*  48 */     if (ses.isValid()) {
/*  49 */       return new ModelAndView("redirect:/webApp/login");
/*     */     }
/*  51 */     model.addAttribute("nombre", ses.getValue("nombre"));
/*  52 */     return new ModelAndView("layout/user");
/*     */   }
/*     */   
/*     */   @RequestMapping({"/layout_alert"})
/*     */   public ModelAndView Alert(Model model, HttpSession httpSession) {
/*  57 */     session ses = new session(httpSession);
/*  58 */     if (ses.isValid()) {
/*  59 */       return new ModelAndView("redirect:/webApp/login");
/*     */     }
/*  61 */     ArrayList<filterSql> filter = new ArrayList<>();
/*     */     try {
/*  63 */       int productor = 0;
/*  64 */       int componente = 0;
/*  65 */       int bloqueDia = 0;
/*  66 */       int total = 0;
/*     */       
/*  68 */       model.addAttribute("alarmas", (new StringBuilder(String.valueOf(total))).toString());
/*  69 */       model.addAttribute("alarmaProductor", (new StringBuilder(String.valueOf(productor))).toString());
/*  70 */       model.addAttribute("alarmaComponente", (new StringBuilder(String.valueOf(componente))).toString());
/*     */       
/*  72 */       model.addAttribute("temporada", ses.getTemporada());
/*     */     }
/*  74 */     catch (Exception e) {
/*     */       
/*  76 */       e.printStackTrace();
/*     */     } 
/*  78 */     String message = "esto es una maldita prueba";
/*  79 */     return new ModelAndView("layout/alert", "message", message);
/*     */   }
/*     */   
/*     */   @RequestMapping({"/layout_config"})
/*     */   public ModelAndView Config(Model model, HttpSession httpSession) {
/*  84 */     session ses = new session(httpSession);
/*  85 */     if (ses.isValid()) {
/*  86 */       return new ModelAndView("redirect:/webApp/login");
/*     */     }
/*     */     
/*  89 */     model.addAttribute("id", "jajajajaj :)");
/*  90 */     String message = "esto es una maldita prueba";
/*  91 */     return new ModelAndView("layout/config", "message", message);
/*     */   }
/*     */   @RequestMapping({"/layout_javaScriptHeader"})
/*     */   public ModelAndView javaScript(Model model, HttpSession httpSession) {
/*  95 */     session ses = new session(httpSession);
/*  96 */     if (ses.isValid()) {
/*  97 */       return new ModelAndView("redirect:/webApp/login");
/*     */     }
/*     */     
/* 100 */     model.addAttribute("webPageApp", config.getProperty("webPageApp"));
/*     */     
/* 102 */     model.addAttribute("id", "jajajajaj :)");
/* 103 */     String message = "esto es una maldita prueba";
/* 104 */     return new ModelAndView("layout/javaScriptHeader", "message", message);
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\cl\expled\web\layout\Element.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */