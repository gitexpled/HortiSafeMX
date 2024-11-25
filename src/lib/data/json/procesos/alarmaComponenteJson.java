/*     */ package lib.data.json.procesos;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import lib.data.json.dataTable;
/*     */ import lib.db.alarmaComponenteDB;
/*     */ import lib.security.session;
/*     */ import lib.struc.alarmaComponente;
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
/*     */ @Controller
/*     */ public class alarmaComponenteJson
/*     */ {
/*     */   @RequestMapping(value = {"/alarmaComponente/view"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable getView(HttpServletRequest request, HttpSession httpSession) {
/*  30 */     session ses = new session(httpSession);
/*  31 */     dataTable data = new dataTable();
/*  32 */     if (ses.isValid()) {
/*     */       
/*  34 */       data.setDraw(0);
/*  35 */       data.init();
/*  36 */       return data;
/*     */     } 
/*     */     
/*  39 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/*  40 */     Map<String, String[]> parameters = request.getParameterMap();
/*  41 */     ArrayList<filterSql> filter = new ArrayList<>();
/*  42 */     for (String key : parameters.keySet()) {
/*     */       
/*  44 */       if (key.startsWith("vw_")) {
/*  45 */         String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
/*  46 */         for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
/*  47 */           System.out.println(String.valueOf(key) + " -> " + val);
/*  48 */           filterSql fil = new filterSql();
/*  49 */           fil.setCampo(key.substring(3));
/*  50 */           fil.setValue(val);
/*  51 */           filter.add(fil);
/*     */           
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/*  57 */     data.setDraw(0);
/*  58 */     data.init();
/*     */     
/*  60 */     int start = Integer.parseInt(((String[])parameters.get("start"))[0]);
/*  61 */     int length = Integer.parseInt(((String[])parameters.get("length"))[0]);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  66 */       ArrayList<alarmaComponente> datas = alarmaComponenteDB.getAll(filter, "", start, length);
/*     */       
/*  68 */       Iterator<alarmaComponente> f = datas.iterator();
/*     */       
/*  70 */       data.setRecordsFiltered(alarmaComponenteDB.getAllcount(filter));
/*  71 */       data.setRecordsTotal(alarmaComponenteDB.getAllcount(filter));
/*     */       
/*  73 */       while (f.hasNext()) {
/*  74 */         alarmaComponente row = f.next();
/*  75 */         String[] d = { row.getCodProducto(), row.getEspecie(), (new StringBuilder(String.valueOf(row.getCantidad()))).toString() };
/*     */         
/*  77 */         data.setData(d);
/*     */       }
/*     */     
/*  80 */     } catch (Exception e) {
/*     */       
/*  82 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  85 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/alarmaComponente/{codigo}"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public alarmaComponente getId(@PathVariable String codigo, HttpSession httpSession) throws Exception {
/*  93 */     session ses = new session(httpSession);
/*     */     
/*  95 */     if (ses.isValid()) {
/*  96 */       alarmaComponente alarmaComponente = null;
/*  97 */       return alarmaComponente;
/*     */     } 
/*     */     
/* 100 */     alarmaComponente row = alarmaComponenteDB.getId(codigo);
/*     */     
/* 102 */     return row;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/alarmaComponente/put"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public mesajesJson put(@RequestBody alarmaComponente row, HttpSession httpSession) throws Exception {
/* 109 */     session ses = new session(httpSession);
/* 110 */     mesajesJson mensaje = new mesajesJson();
/* 111 */     if (ses.isValid()) {
/* 112 */       mensaje.setEstado("error");
/* 113 */       mensaje.setMensaje("Session terminada");
/* 114 */       return mensaje;
/*     */     } 
/* 116 */     System.out.println("PUT::::::::::::::::::::::::::::");
/*     */     
/* 118 */     alarmaComponenteDB.update(row);
/*     */ 
/*     */     
/* 121 */     mensaje.setEstado("ok");
/* 122 */     mensaje.setMensaje("Guardado con exito");
/*     */     
/* 124 */     return mensaje;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\json\procesos\alarmaComponenteJson.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */