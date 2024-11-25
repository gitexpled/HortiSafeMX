/*     */ package cl.expled.web.page;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import lib.db.systemMenuDB;
/*     */ import lib.security.session;
/*     */ import lib.struc.filterSql;
/*     */ import lib.struc.systemMenu;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.Model;
/*     */ import org.springframework.web.bind.annotation.PathVariable;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
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
/*     */ @Controller
/*     */ public class page
/*     */ {
/*     */   @RequestMapping(value = {"/page/{id:.+}"}, method = {RequestMethod.GET})
/*     */   public ModelAndView mainDefault(Model model, @PathVariable("id") String id, HttpSession httpSession) {
/*  32 */     session ses = new session(httpSession);
/*  33 */     if (ses.isValid()) {
/*  34 */       return new ModelAndView("redirect:/webApp/login");
/*     */     }
/*  36 */     systemMenu m = new systemMenu();
/*     */     try {
/*  38 */       m = systemMenuDB.getMenuUrl(id);
/*  39 */       model.addAttribute("proceso", m.getProceso());
/*  40 */       model.addAttribute("pagina", m.getMenu());
/*  41 */     } catch (Exception e) {
/*     */       
/*  43 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  48 */     ArrayList<filterSql> filter = new ArrayList<>();
/*     */     try {
/*  50 */       filter = new ArrayList<>();
/*  51 */       filterSql pp = new filterSql();
/*  52 */       pp.setCampo("arandano");
/*  53 */       pp.setValue("Y");
/*  54 */       filter.add(pp);
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
/*     */     }
/*  94 */     catch (Exception e) {
/*     */       
/*  96 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 101 */     model.addAttribute("content", id);
/* 102 */     model.addAttribute("controller", "content/" + id);
/* 103 */     model.addAttribute("javaScriptPage", id.replace(".", "/"));
/* 104 */     model.addAttribute("rand", Integer.valueOf(ThreadLocalRandom.current().nextInt(10000, 100000)));
/*     */ 
/*     */ 
/*     */     
/* 108 */     return new ModelAndView("layout/_main");
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/pageAdm/{id:.+}"}, method = {RequestMethod.GET})
/*     */   public ModelAndView mainAdm(Model model, @PathVariable("id") String id, HttpSession httpSession) {
/* 113 */     session ses = new session(httpSession);
/* 114 */     if (ses.isValid()) {
/* 115 */       return new ModelAndView("redirect:/webApp/login");
/*     */     }
/*     */     
/*     */     try {
/* 119 */       systemMenu m = systemMenuDB.getMenuUrl(id);
/* 120 */       model.addAttribute("proceso", m.getProceso());
/* 121 */       model.addAttribute("pagina", m.getMenu());
/* 122 */     } catch (Exception e) {
/*     */       
/* 124 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 127 */     model.addAttribute("content", id);
/* 128 */     model.addAttribute("controller", "adm/" + id.replace(".", "_"));
/* 129 */     model.addAttribute("javaScriptPage", id.replace(".", "/"));
/* 130 */     model.addAttribute("rand", Integer.valueOf(ThreadLocalRandom.current().nextInt(10000, 100000)));
/*     */ 
/*     */     
/* 133 */     return new ModelAndView("layout/_main");
/*     */   }
/*     */   @RequestMapping({"/content/{id:.+}"})
/*     */   public ModelAndView contentDefault(Model model, @PathVariable("id") String id, HttpSession httpSession) throws Exception {
/* 137 */     session ses = new session(httpSession);
/* 138 */     if (ses.isValid()) {
/* 139 */       return new ModelAndView("redirect:/webApp/login");
/*     */     }
/* 141 */     model.addAttribute("IDFILTER", "aa");
/*     */     
/* 143 */     System.out.println("MVL-JSP:" + id);
/* 144 */     return new ModelAndView("content/" + id.replace(".jsp", "").replace(".", "/"));
/*     */   }
/*     */   
/*     */   @RequestMapping({"/content/{id:.+}/{key}/{esp}"})
/*     */   public ModelAndView contentDefaultData(Model model, @PathVariable("id") String id, @PathVariable("key") String key, @PathVariable("esp") String esp, HttpSession httpSession) throws Exception {
/* 149 */     session ses = new session(httpSession);
/* 150 */     if (ses.isValid()) {
/* 151 */       return new ModelAndView("redirect:/webApp/login");
/*     */     }
/*     */     
/* 154 */     System.out.println("_");
/* 155 */     System.out.println(key);
/* 156 */     String decodedToUTF8 = new String(key.replace(".jsp", "").getBytes("ISO-8859-1"), "UTF-8");
/*     */     
/* 158 */     System.out.println(decodedToUTF8);
/*     */ 
/*     */ 
/*     */     
/* 162 */     model.addAttribute("IDFILTER", decodedToUTF8.replace(".jsp", ""));
/*     */     
/* 164 */     if (esp.replace(".jsp", "").equals("1")) {
/* 165 */       model.addAttribute("ESPECIE", "ARANDANO");
/*     */     } else {
/* 167 */       model.addAttribute("ESPECIE", "CEREZA");
/*     */     } 
/*     */     
/* 170 */     return new ModelAndView("content/" + id.replace(".jsp", "").replace(".", "/"));
/*     */   }
/*     */   @RequestMapping({"/content/{id:.+}/{key}/{esp}/{nmuestra}"})
/*     */   public ModelAndView contentDefaultData2(Model model, @PathVariable("id") String id, @PathVariable("key") String key, @PathVariable("esp") String esp, @PathVariable("nmuestra") String nmuestra, HttpSession httpSession) throws Exception {
/* 174 */     session ses = new session(httpSession);
/* 175 */     if (ses.isValid()) {
/* 176 */       return new ModelAndView("redirect:/webApp/login");
/*     */     }
/* 178 */     model.addAttribute("IDFILTER", key.replace(".jsp", ""));
/*     */ 
/*     */ 
/*     */     
/* 182 */     model.addAttribute("NMUESTRA", nmuestra.replace(".jsp", ""));
/*     */ 
/*     */     
/* 185 */     if (esp.replace(".jsp", "").equals("1")) {
/* 186 */       model.addAttribute("ESPECIE", "ARANDANO");
/*     */     } else {
/* 188 */       model.addAttribute("ESPECIE", "CEREZA");
/*     */     } 
/*     */     
/* 191 */     return new ModelAndView("content/" + id.replace(".jsp", "").replace(".", "/"));
/*     */   }
/*     */   @RequestMapping(value = {"/page2/{id:.+}"}, method = {RequestMethod.GET})
/*     */   public ModelAndView mainDefaultData(Model model, @PathVariable("id") String id, @RequestParam("key") String key, @RequestParam("especie") String especie, HttpSession httpSession) {
/* 195 */     session ses = new session(httpSession);
/* 196 */     if (ses.isValid()) {
/* 197 */       return new ModelAndView("redirect:/webApp/login");
/*     */     }
/* 199 */     systemMenu m = new systemMenu();
/*     */     try {
/* 201 */       m = systemMenuDB.getMenuUrl(id);
/* 202 */       model.addAttribute("proceso", m.getProceso());
/* 203 */       model.addAttribute("pagina", m.getMenu());
/* 204 */     } catch (Exception e) {
/*     */       
/* 206 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 211 */     model.addAttribute("content", id);
/* 212 */     model.addAttribute("controller", "content/" + id + "/" + key + "/" + especie);
/* 213 */     model.addAttribute("javaScriptPage", id.replace(".", "/"));
/* 214 */     model.addAttribute("rand", Integer.valueOf(ThreadLocalRandom.current().nextInt(10000, 100000)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 220 */     return new ModelAndView("layout/_main");
/*     */   }
/*     */   @RequestMapping(value = {"/page3/{id:.+}"}, method = {RequestMethod.GET})
/*     */   public ModelAndView mainDefaultData2(Model model, @PathVariable("id") String id, @RequestParam("key") String key, @RequestParam("especie") String especie, @RequestParam("muestra") String nmuestra, HttpSession httpSession) {
/* 224 */     session ses = new session(httpSession);
/* 225 */     if (ses.isValid()) {
/* 226 */       return new ModelAndView("redirect:/webApp/login");
/*     */     }
/* 228 */     systemMenu m = new systemMenu();
/*     */     try {
/* 230 */       m = systemMenuDB.getMenuUrl(id);
/* 231 */       model.addAttribute("proceso", m.getProceso());
/* 232 */       model.addAttribute("pagina", m.getMenu());
/* 233 */     } catch (Exception e) {
/*     */       
/* 235 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 240 */     model.addAttribute("content", id);
/* 241 */     model.addAttribute("controller", "content/" + id + "/" + key + "/" + especie + "/" + nmuestra);
/* 242 */     model.addAttribute("javaScriptPage", id.replace(".", "/"));
/* 243 */     model.addAttribute("rand", Integer.valueOf(ThreadLocalRandom.current().nextInt(10000, 100000)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 249 */     return new ModelAndView("layout/_main");
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\cl\expled\web\page\page.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */