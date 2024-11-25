/*    */ package cl.expled.web.page;
/*    */ 
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import lib.db.TemporadaDB;
/*    */ import lib.db.userDB;
/*    */ import lib.security.session;
/*    */ import lib.struc.Temporada;
/*    */ import lib.struc.user;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.Model;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ import org.springframework.web.bind.annotation.RequestMethod;
/*    */ import org.springframework.web.servlet.ModelAndView;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Controller
/*    */ public class login
/*    */ {
/*    */   @RequestMapping(value = {"/login"}, method = {RequestMethod.POST, RequestMethod.GET})
/*    */   public ModelAndView loginPage(Model model, HttpServletRequest request, HttpSession httpSession) {
/* 28 */     Map<String, String[]> parameters = request.getParameterMap();
/*    */ 
/*    */     
/*    */     try {
/* 32 */       System.out.println("login IN--->" + ((String[])parameters.get("username"))[0] + "," + ((String[])parameters.get("password"))[0]);
/* 33 */       if (userDB.validateUser(((String[])parameters.get("username"))[0], ((String[])parameters.get("password"))[0]).booleanValue()) {
/*    */         
/* 35 */         session ses = new session(httpSession);
/* 36 */         user u = userDB.getUserByUser(((String[])parameters.get("username"))[0]);
/* 37 */         ses.init();
/*    */         
/* 39 */         ses.setIdPerfil(u.getIdPerfil());
/* 40 */         ses.setIdUser(u.getId());
/* 41 */         ses.setNombre(u.getNombre());
/*    */ 
/*    */ 
/*    */         
/* 45 */         Temporada t = TemporadaDB.getMaxTemprada();
/* 46 */         if (t != null) {
/* 47 */           ses.setIdTemporada(t.getIdTemporada());
/* 48 */           ses.setTemporada(t.getTemporada());
/*    */         } 
/*    */ 
/*    */ 
/*    */         
/* 53 */         return new ModelAndView("redirect:/webApp/page/homePage");
/*    */       } 
/* 55 */       return new ModelAndView("login");
/*    */     }
/* 57 */     catch (Exception e) {
/*    */       
/* 59 */       System.out.println(e.getMessage());
/* 60 */       e.printStackTrace();
/* 61 */       model.addAttribute("alerta", "display-hide");
/* 62 */       return new ModelAndView("login");
/*    */     } 
/*    */   }
/*    */   
/*    */   @RequestMapping({"/index"})
/*    */   public ModelAndView helloWorld2(Model model) {
/* 68 */     model.addAttribute("id", "jajajajaj :)");
/* 69 */     String message = "esto es una maldita prueba";
/* 70 */     return new ModelAndView("home", "message", message);
/*    */   }
/*    */   
/*    */   @RequestMapping({"/exit"})
/*    */   public ModelAndView exit(Model model, HttpSession httpSession) {
/* 75 */     session ses = new session(httpSession);
/* 76 */     ses.close();
/* 77 */     return new ModelAndView("redirect:/webApp/login");
/*    */   }
/*    */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\cl\expled\web\page\login.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */