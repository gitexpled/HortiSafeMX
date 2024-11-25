/*     */ package lib.data.json;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import lib.db.homePageDB;
/*     */ import lib.security.session;
/*     */ import lib.struc.TemporadaZona;
/*     */ import lib.struc.grafico;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.PathVariable;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.bind.annotation.ResponseBody;
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
/*     */ public class homePage
/*     */ {
/*     */   @RequestMapping(value = {"/homePage/productorZona/{id}/{mercado}"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public ArrayList<grafico> productorZona(@PathVariable int id, @PathVariable int mercado, HttpServletRequest request, HttpSession httpSession) {
/*  33 */     ArrayList<grafico> data = new ArrayList<>();
/*     */ 
/*     */     
/*  36 */     session ses = new session(httpSession);
/*     */ 
/*     */     
/*  39 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/*     */ 
/*     */     
/*  42 */     if (!ses.isValid()) {
/*     */       
/*     */       try {
/*     */         
/*  46 */         System.out.println("idEspecie: ");
/*     */         try {
/*  48 */           id *= 1;
/*  49 */           if (id <= 1)
/*     */           {
/*  51 */             id = 1;
/*     */           }
/*     */         }
/*  54 */         catch (Exception e) {
/*  55 */           id = 1;
/*     */         } 
/*     */         try {
/*  58 */           mercado *= 1;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*  64 */         catch (Exception e) {
/*  65 */           mercado = 1;
/*     */         } 
/*     */         
/*  68 */         System.out.println(data.toString());
/*  69 */       } catch (Exception e) {
/*     */         
/*  71 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  75 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/homePage/temporadaZona/{id}"})
/*     */   @ResponseBody
/*     */   public ArrayList<TemporadaZona> getTemporadaZona(@PathVariable int id, HttpServletRequest request, HttpSession httpSession) {
/*  83 */     ArrayList<TemporadaZona> data = new ArrayList<>();
/*  84 */     session ses = new session(httpSession);
/*  85 */     if (!ses.isValid()) {
/*     */       try {
/*  87 */         data = homePageDB.getTemporadaZona();
/*  88 */       } catch (Exception e) {
/*     */         
/*  90 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*  93 */     return data;
/*     */   }
/*     */   @RequestMapping(value = {"/homePage/productorMercado/{id}"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public ArrayList<grafico> productorMercado(@PathVariable int id, HttpServletRequest request, HttpSession httpSession) {
/*  98 */     System.out.println(request.getServletPath());
/*  99 */     ArrayList<grafico> data = new ArrayList<>();
/*     */ 
/*     */     
/* 102 */     session ses = new session(httpSession);
/*     */ 
/*     */     
/* 105 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/*     */ 
/*     */     
/* 108 */     if (!ses.isValid()) {
/*     */       
/*     */       try {
/*     */         
/* 112 */         System.out.println("idEspecie: ");
/*     */         try {
/* 114 */           id *= 1;
/* 115 */           if (id <= 1)
/*     */           {
/* 117 */             id = 1;
/*     */           }
/*     */         }
/* 120 */         catch (Exception e) {
/* 121 */           id = 1;
/*     */         } 
/*     */         
/* 124 */         System.out.println(data.toString());
/* 125 */       } catch (Exception e) {
/*     */         
/* 127 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/* 131 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/homePage/top10Producto/{id}"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public ArrayList<grafico> productorZona(@PathVariable int id, HttpServletRequest request, HttpSession httpSession) {
/* 139 */     ArrayList<grafico> data = new ArrayList<>();
/*     */ 
/*     */     
/* 142 */     session ses = new session(httpSession);
/*     */ 
/*     */     
/* 145 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/*     */ 
/*     */     
/* 148 */     if (!ses.isValid()) {
/*     */       
/*     */       try {
/*     */         
/* 152 */         System.out.println("idEspecie: ");
/*     */         try {
/* 154 */           id *= 1;
/* 155 */           if (id <= 1)
/*     */           {
/* 157 */             id = 1;
/*     */           }
/*     */         }
/* 160 */         catch (Exception e) {
/* 161 */           id = 1;
/*     */         } 
/*     */ 
/*     */         
/* 165 */         System.out.println(data.toString());
/* 166 */       } catch (Exception e) {
/*     */         
/* 168 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/* 172 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/homePage/getProductor5/{id}"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public ArrayList<grafico> getProductor5(@PathVariable int id, HttpServletRequest request, HttpSession httpSession) {
/* 180 */     ArrayList<grafico> data = new ArrayList<>();
/*     */ 
/*     */     
/* 183 */     session ses = new session(httpSession);
/*     */ 
/*     */     
/* 186 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/*     */ 
/*     */     
/* 189 */     if (!ses.isValid()) {
/*     */       
/*     */       try {
/*     */         
/* 193 */         System.out.println("idEspecie: ");
/*     */         try {
/* 195 */           id *= 1;
/* 196 */           if (id <= 1)
/*     */           {
/* 198 */             id = 1;
/*     */           }
/*     */         }
/* 201 */         catch (Exception e) {
/* 202 */           id = 1;
/*     */         } 
/*     */ 
/*     */         
/* 206 */         System.out.println(data.toString());
/* 207 */       } catch (Exception e) {
/*     */         
/* 209 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/* 213 */     return data;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\json\homePage.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */