/*     */ package lib.data.json.procesos;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import lib.data.json.dataTable;
/*     */ import lib.db.alarmaProductorDB;
/*     */ import lib.security.session;
/*     */ import lib.struc.alarmaProductor;
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
/*     */ @Controller
/*     */ public class alarmaProductorJson
/*     */ {
/*     */   @RequestMapping(value = {"/alarmaProductor/view"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable getView(HttpServletRequest request, HttpSession httpSession) {
/*  29 */     session ses = new session(httpSession);
/*  30 */     dataTable data = new dataTable();
/*  31 */     if (ses.isValid()) {
/*     */       
/*  33 */       data.setDraw(0);
/*  34 */       data.init();
/*  35 */       return data;
/*     */     } 
/*     */     
/*  38 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/*  39 */     Map<String, String[]> parameters = request.getParameterMap();
/*  40 */     ArrayList<filterSql> filter = new ArrayList<>();
/*  41 */     for (String key : parameters.keySet()) {
/*     */       
/*  43 */       if (key.startsWith("vw_")) {
/*  44 */         String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
/*  45 */         for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
/*  46 */           System.out.println(String.valueOf(key) + " -> " + val);
/*  47 */           filterSql fil = new filterSql();
/*  48 */           fil.setCampo(key.substring(3));
/*  49 */           fil.setValue(val);
/*  50 */           filter.add(fil);
/*     */           
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/*  56 */     data.setDraw(0);
/*  57 */     data.init();
/*     */     
/*  59 */     int start = Integer.parseInt(((String[])parameters.get("start"))[0]);
/*  60 */     int length = Integer.parseInt(((String[])parameters.get("length"))[0]);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  65 */       ArrayList<alarmaProductor> datas = alarmaProductorDB.getAll(filter, "", start, length);
/*     */       
/*  67 */       Iterator<alarmaProductor> f = datas.iterator();
/*     */       
/*  69 */       data.setRecordsFiltered(alarmaProductorDB.getAllcount(filter));
/*  70 */       data.setRecordsTotal(alarmaProductorDB.getAllcount(filter));
/*     */       
/*  72 */       while (f.hasNext()) {
/*  73 */         alarmaProductor row = f.next();
/*  74 */         String[] d = { row.getCodProductor(), row.getCodParcela(), row.getProyecto(), row.getEspecie(), (new StringBuilder(String.valueOf(row.getCantidad()))).toString() };
/*     */         
/*  76 */         data.setData(d);
/*     */       }
/*     */     
/*  79 */     } catch (Exception e) {
/*     */       
/*  81 */       e.printStackTrace();
/*     */     } 
/*  83 */     System.out.println(":::::::::::::::::::::::::::::::::::");
/*  84 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/alarmaProductor/{codigo}"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public alarmaProductor getId(@PathVariable String codigo, HttpSession httpSession) throws Exception {
/*  92 */     session ses = new session(httpSession);
/*     */     
/*  94 */     if (ses.isValid()) {
/*  95 */       alarmaProductor alarmaProductor = null;
/*  96 */       return alarmaProductor;
/*     */     } 
/*     */     
/*  99 */     alarmaProductor row = alarmaProductorDB.getId(codigo);
/*     */     
/* 101 */     return row;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/alarmaProductor/put"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public mesajesJson put(@RequestBody alarmaProductor row, HttpSession httpSession) throws Exception {
/* 108 */     session ses = new session(httpSession);
/* 109 */     mesajesJson mensaje = new mesajesJson();
/* 110 */     if (ses.isValid()) {
/* 111 */       mensaje.setEstado("error");
/* 112 */       mensaje.setMensaje("Session terminada");
/* 113 */       return mensaje;
/*     */     } 
/* 115 */     System.out.println("PUT::::::::::::::::::::::::::::");
/*     */     
/* 117 */     alarmaProductorDB.update(row);
/*     */ 
/*     */     
/* 120 */     mensaje.setEstado("ok");
/* 121 */     mensaje.setMensaje("Guardado con exito");
/*     */     
/* 123 */     return mensaje;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\json\procesos\alarmaProductorJson.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */