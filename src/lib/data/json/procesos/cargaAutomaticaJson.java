/*     */ package lib.data.json.procesos;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import lib.data.json.dataTable;
/*     */ import lib.db.ProductorDB;
/*     */ import lib.db.mailDB;
/*     */ import lib.security.session;
/*     */ import lib.struc.Productor;
/*     */ import lib.struc.filterSql;
/*     */ import lib.struc.mail;
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
/*     */ @Controller
/*     */ public class cargaAutomaticaJson
/*     */ {
/*     */   @RequestMapping(value = {"/cargaAutomatica/view"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable getShopInJSON(HttpServletRequest request, HttpSession httpSession) {
/*  34 */     session ses = new session(httpSession);
/*  35 */     dataTable data = new dataTable();
/*  36 */     if (ses.isValid()) {
/*     */       
/*  38 */       data.setDraw(0);
/*  39 */       data.init();
/*  40 */       return data;
/*     */     } 
/*     */     
/*  43 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/*  44 */     Map<String, String[]> parameters = request.getParameterMap();
/*  45 */     ArrayList<filterSql> filter = new ArrayList<>();
/*  46 */     for (String key : parameters.keySet()) {
/*     */       
/*  48 */       if (key.startsWith("vw_")) {
/*  49 */         String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
/*  50 */         for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
/*  51 */           System.out.println(String.valueOf(key) + " -> " + val);
/*  52 */           filterSql fil = new filterSql();
/*  53 */           fil.setCampo(key.substring(3));
/*  54 */           fil.setValue(val);
/*  55 */           filter.add(fil);
/*     */           
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/*  61 */     data.setDraw(0);
/*  62 */     data.init();
/*     */     
/*  64 */     int start = Integer.parseInt(((String[])parameters.get("start"))[0]);
/*  65 */     int length = Integer.parseInt(((String[])parameters.get("length"))[0]);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  70 */       ArrayList<mail> datas = mailDB.getMail(filter, "", start, length);
/*     */       
/*  72 */       Iterator<mail> f = datas.iterator();
/*     */       
/*  74 */       data.setRecordsFiltered(mailDB.getMailAll(filter));
/*  75 */       data.setRecordsTotal(mailDB.getMailAll(filter));
/*     */       
/*  77 */       while (f.hasNext()) {
/*  78 */         mail row = f.next();
/*  79 */         String[] d = { row.getFechaRecibido(), row.getFechaCarga(), row.getAsunto(), row.getArchivo(), row.getLaboratorio(), row.getEstado(), (new StringBuilder(String.valueOf(row.getIdMail()))).toString() };
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
/*     */   @RequestMapping(value = {"/productor2/insertProductor"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public boolean insertProductor(@RequestBody Productor row, HttpSession httpSession) throws ParseException {
/*  96 */     boolean resp = false;
/*  97 */     session ses = new session(httpSession);
/*  98 */     if (ses.isValid()) {
/*  99 */       return resp;
/*     */     }
/* 101 */     resp = ProductorDB.insertProductor(row);
/* 102 */     return resp;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/productor2/{codigo}"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public Productor getUserId(@PathVariable String codigo, HttpSession httpSession) throws Exception {
/* 108 */     session ses = new session(httpSession);
/*     */     
/* 110 */     if (ses.isValid()) {
/* 111 */       Productor productor = null;
/* 112 */       return productor;
/*     */     } 
/*     */     
/* 115 */     Productor row = ProductorDB.getProductor(codigo);
/*     */     
/* 117 */     return row;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/productor2/put"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public mesajesJson setUser(@RequestBody Productor row, HttpSession httpSession) throws Exception {
/* 124 */     session ses = new session(httpSession);
/* 125 */     mesajesJson mensaje = new mesajesJson();
/* 126 */     if (ses.isValid()) {
/* 127 */       mensaje.setEstado("error");
/* 128 */       mensaje.setMensaje("Session terminada");
/* 129 */       return mensaje;
/*     */     } 
/* 131 */     System.out.println("PUT::::::::::::::::::::::::::::");
/*     */     
/* 133 */     ProductorDB.updateProductor(row);
/*     */ 
/*     */     
/* 136 */     mensaje.setEstado("ok");
/* 137 */     mensaje.setMensaje("Guardado con exito");
/*     */     
/* 139 */     return mensaje;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/productor2/getAllOutFilter"}, method = {RequestMethod.GET}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public ArrayList<Productor> getAllOutFilter(HttpServletRequest request, HttpSession httpSession) throws Exception {
/* 146 */     session ses = new session(httpSession);
/* 147 */     ArrayList<Productor> productores = new ArrayList<>();
/* 148 */     if (ses.isValid()) {
/*     */       
/* 150 */       productores = null;
/* 151 */       return productores;
/*     */     } 
/* 153 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 154 */     productores = ProductorDB.getProductor(filter, "", 0, 1000);
/* 155 */     return productores;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\json\procesos\cargaAutomaticaJson.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */