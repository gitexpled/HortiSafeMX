/*     */ package lib.data.json;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import lib.db.TemporadaDB;
/*     */ import lib.security.session;
/*     */ import lib.struc.Temporada;
/*     */ import lib.struc.filterSql;
/*     */ import lib.struc.mesajesJson;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.PathVariable;
/*     */ import org.springframework.web.bind.annotation.RequestBody;
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
/*     */ @Controller
/*     */ public class TemporadaJson
/*     */ {
/*     */   @RequestMapping(value = {"/temporada/view"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable getShopInJSON(HttpServletRequest request, HttpSession httpSession) {
/*  33 */     session ses = new session(httpSession);
/*  34 */     dataTable data = new dataTable();
/*  35 */     if (ses.isValid()) {
/*     */       
/*  37 */       data.setDraw(0);
/*  38 */       data.init();
/*  39 */       return data;
/*     */     } 
/*     */     
/*  42 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/*  43 */     Map<String, String[]> parameters = request.getParameterMap();
/*  44 */     ArrayList<filterSql> filter = new ArrayList<>();
/*  45 */     for (String key : parameters.keySet()) {
/*     */       
/*  47 */       if (key.startsWith("vw_")) {
/*  48 */         String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
/*  49 */         for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
/*  50 */           System.out.println(String.valueOf(key) + " -> " + val);
/*  51 */           filterSql fil = new filterSql();
/*  52 */           fil.setCampo(key.substring(3));
/*  53 */           fil.setValue(val);
/*  54 */           filter.add(fil);
/*     */           
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/*  60 */     data.setDraw(0);
/*  61 */     data.init();
/*     */     
/*  63 */     int start = Integer.parseInt(((String[])parameters.get("start"))[0]);
/*  64 */     int length = Integer.parseInt(((String[])parameters.get("length"))[0]);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  69 */       ArrayList<Temporada> datas = TemporadaDB.getTemporada(filter, "", start, length);
/*     */       
/*  71 */       Iterator<Temporada> f = datas.iterator();
/*  72 */       data.setRecordsFiltered(TemporadaDB.getTemporadasAll(filter));
/*  73 */       data.setRecordsTotal(TemporadaDB.getTemporadasAll(filter));
/*     */       
/*  75 */       while (f.hasNext()) {
/*  76 */         Temporada row = f.next();
/*  77 */         String[] d = { row.getTemporada(),""+row.getCreado(), (new StringBuilder(String.valueOf(row.getIdUser()))).toString(), (new StringBuilder(String.valueOf(row.getIdTemporada()))).toString() };
/*     */         
/*  79 */         data.setData(d);
/*     */       }
/*     */     
/*  82 */     } catch (Exception e) {
/*     */       
/*  84 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  87 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/temporada/insertTemporada"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public boolean insertTemporada(@RequestBody Temporada row, HttpSession httpSession) throws ParseException {
/*  94 */     boolean resp = false;
/*  95 */     session ses = new session(httpSession);
/*  96 */     if (ses.isValid()) {
/*  97 */       return resp;
/*     */     }
/*  99 */     row.setIdUser(ses.getIdUser());
/* 100 */     resp = TemporadaDB.insertTemporada(row);
/*     */ 
/*     */     
/*     */     try {
/* 104 */       Temporada t = TemporadaDB.getMaxTemprada();
/* 105 */       ses.setIdTemporada(t.getIdTemporada());
/* 106 */       ses.setTemporada(t.getTemporada());
/* 107 */     } catch (Exception e) {
/*     */       
/* 109 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/* 113 */     return resp;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/temporada/{codigo}"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public Temporada getUserId(@PathVariable String codigo, HttpSession httpSession) throws Exception {
/* 119 */     session ses = new session(httpSession);
/*     */     
/* 121 */     if (ses.isValid()) {
/* 122 */       Temporada temporada = null;
/* 123 */       return temporada;
/*     */     } 
/*     */     
/* 126 */     Temporada row = TemporadaDB.getTemporada(codigo);
/*     */     
/* 128 */     return row;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/temporada/put"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public mesajesJson setUser(@RequestBody Temporada row, HttpSession httpSession) throws Exception {
/* 135 */     session ses = new session(httpSession);
/* 136 */     mesajesJson mensaje = new mesajesJson();
/* 137 */     if (ses.isValid()) {
/* 138 */       mensaje.setEstado("error");
/* 139 */       mensaje.setMensaje("Session terminada");
/* 140 */       return mensaje;
/*     */     } 
/* 142 */     System.out.println("PUT::::::::::::::::::::::::::::");
/*     */     
/* 144 */     TemporadaDB.updateTemporada(row);
/*     */ 
/*     */     
/* 147 */     mensaje.setEstado("ok");
/* 148 */     mensaje.setMensaje("Guardado con exito");
/*     */     
/* 150 */     return mensaje;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\json\TemporadaJson.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */