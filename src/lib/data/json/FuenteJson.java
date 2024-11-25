/*     */ package lib.data.json;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import lib.db.FuenteDB;
/*     */ import lib.security.session;
/*     */ import lib.struc.Fuente;
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
/*     */ 
/*     */ 
/*     */ @Controller
/*     */ public class FuenteJson
/*     */ {
/*     */   @RequestMapping(value = {"/fuente/view"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable getShopInJSON(HttpServletRequest request, HttpSession httpSession) {
/*  35 */     session ses = new session(httpSession);
/*  36 */     dataTable data = new dataTable();
/*  37 */     if (ses.isValid()) {
/*     */       
/*  39 */       data.setDraw(0);
/*  40 */       data.init();
/*  41 */       return data;
/*     */     } 
/*     */     
/*  44 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/*  45 */     Map<String, String[]> parameters = request.getParameterMap();
/*  46 */     ArrayList<filterSql> filter = new ArrayList<>();
/*  47 */     for (String key : parameters.keySet()) {
/*     */       
/*  49 */       if (key.startsWith("vw_")) {
/*  50 */         String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
/*  51 */         for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
/*  52 */           System.out.println(String.valueOf(key) + " -> " + val);
/*  53 */           filterSql fil = new filterSql();
/*  54 */           fil.setCampo(key.substring(3));
/*  55 */           fil.setValue(val);
/*  56 */           filter.add(fil);
/*     */           
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/*  62 */     data.setDraw(0);
/*  63 */     data.init();
/*     */     
/*  65 */     int start = Integer.parseInt(((String[])parameters.get("start"))[0]);
/*  66 */     int length = Integer.parseInt(((String[])parameters.get("length"))[0]);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  71 */       ArrayList<Fuente> datas = FuenteDB.getFuente(filter, "", start, length);
/*     */       
/*  73 */       Iterator<Fuente> f = datas.iterator();
/*  74 */       data.setRecordsFiltered(FuenteDB.getFuenteAll(filter));
/*  75 */       data.setRecordsTotal(FuenteDB.getFuenteAll(filter));
/*     */       
/*  77 */       while (f.hasNext()) {
/*  78 */         Fuente row = f.next();
/*  79 */         String[] d = { row.getNombre(), "", (new StringBuilder(String.valueOf(row.getIdFuente()))).toString() };
/*     */         
/*  81 */         data.setData(d);
/*     */       }
/*     */     
/*  84 */     } catch (Exception e) {
/*     */       
/*  86 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  89 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/fuente/insertFuente"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public boolean insertFuente(@RequestBody Fuente row, HttpSession httpSession) throws ParseException {
/*  96 */     boolean resp = false;
/*  97 */     session ses = new session(httpSession);
/*  98 */     if (ses.isValid()) {
/*  99 */       return resp;
/*     */     }
/* 101 */     resp = FuenteDB.insertFuente(row);
/* 102 */     return resp;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/fuente/{codigo}"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public Fuente getFuenteId(@PathVariable String codigo, HttpSession httpSession) throws Exception {
/* 108 */     session ses = new session(httpSession);
/*     */     
/* 110 */     if (ses.isValid()) {
/* 111 */       Fuente fuente = null;
/* 112 */       return fuente;
/*     */     } 
/*     */     
/* 115 */     Fuente row = FuenteDB.getFuente(codigo);
/*     */     
/* 117 */     return row;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/fuente/getAllOutFilter"}, method = {RequestMethod.GET}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public ArrayList<Fuente> getAllOutFilter(HttpServletRequest request, HttpSession httpSession) throws Exception {
/* 124 */     session ses = new session(httpSession);
/* 125 */     ArrayList<Fuente> fuentes = new ArrayList<>();
/* 126 */     if (ses.isValid()) {
/*     */       
/* 128 */       fuentes = null;
/* 129 */       return fuentes;
/*     */     } 
/* 131 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 132 */     fuentes = FuenteDB.getFuente(filter, "", 0, 1000);
/* 133 */     return fuentes;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/fuente/put"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public mesajesJson setTipoProducto(@RequestBody Fuente row, HttpSession httpSession) throws Exception {
/* 139 */     session ses = new session(httpSession);
/* 140 */     mesajesJson mensaje = new mesajesJson();
/* 141 */     if (ses.isValid()) {
/* 142 */       mensaje.setEstado("error");
/* 143 */       mensaje.setMensaje("Session terminada");
/* 144 */       return mensaje;
/*     */     } 
/* 146 */     System.out.println("PUT::::::::::::::::::::::::::::");
/*     */     
/* 148 */     FuenteDB.updateFuente(row);
/*     */ 
/*     */     
/* 151 */     mensaje.setEstado("ok");
/* 152 */     mensaje.setMensaje("Guardado con exito");
/*     */     
/* 154 */     return mensaje;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\json\FuenteJson.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */