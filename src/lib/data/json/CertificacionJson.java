/*     */ package lib.data.json;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import lib.db.CertificacionDB;
/*     */ import lib.security.session;
/*     */ import lib.struc.Certificacion;
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
/*     */ public class CertificacionJson
/*     */ {
/*     */   @RequestMapping(value = {"/certificacion/view"}, method = {RequestMethod.POST, RequestMethod.GET})
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
/*  69 */       ArrayList<Certificacion> datas = CertificacionDB.getCertificacion(filter, "", start, length);
/*     */       
/*  71 */       Iterator<Certificacion> f = datas.iterator();
/*  72 */       data.setRecordsFiltered(CertificacionDB.getCertificacionesAll(filter));
/*  73 */       data.setRecordsTotal(CertificacionDB.getCertificacionesAll(filter));
/*     */       
/*  75 */       while (f.hasNext()) {
/*  76 */         Certificacion row = f.next();
      String[] d = { row.getCertificacionesCol(), row.getPrefijo(),""+row.getCreado(),""+row.getModificado(), (new StringBuilder(String.valueOf(row.getIdUser()))).toString(), (new StringBuilder(String.valueOf(row.getIdCertificaciones()))).toString() };
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
/*     */   @RequestMapping(value = {"/certificacion/insertCertificacion"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public boolean insertCertificacion(@RequestBody Certificacion row, HttpSession httpSession) throws ParseException {
/*  94 */     boolean resp = false;
/*  95 */     session ses = new session(httpSession);
/*  96 */     if (ses.isValid()) {
/*  97 */       return resp;
/*     */     }
/*  99 */     resp = CertificacionDB.insertCertificacion(row);
/* 100 */     return resp;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/certificacion/{codigo}"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public Certificacion getUserId(@PathVariable String codigo, HttpSession httpSession) throws Exception {
/* 106 */     session ses = new session(httpSession);
/*     */     
/* 108 */     if (ses.isValid()) {
/* 109 */       Certificacion certificacion = null;
/* 110 */       return certificacion;
/*     */     } 
/*     */     
/* 113 */     Certificacion row = CertificacionDB.getCertificacion(codigo);
/*     */     
/* 115 */     return row;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/certificacion/put"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public mesajesJson setUser(@RequestBody Certificacion row, HttpSession httpSession) throws Exception {
/* 122 */     session ses = new session(httpSession);
/* 123 */     mesajesJson mensaje = new mesajesJson();
/* 124 */     if (ses.isValid()) {
/* 125 */       mensaje.setEstado("error");
/* 126 */       mensaje.setMensaje("Session terminada");
/* 127 */       return mensaje;
/*     */     } 
/* 129 */     System.out.println("PUT::::::::::::::::::::::::::::");
/*     */     
/* 131 */     CertificacionDB.updateCertificacion(row);
/*     */ 
/*     */     
/* 134 */     mensaje.setEstado("ok");
/* 135 */     mensaje.setMensaje("Guardado con exito");
/*     */     
/* 137 */     return mensaje;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/certificacion/getAllOutFilter"}, method = {RequestMethod.GET}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public ArrayList<Certificacion> getAllOutFilter(HttpServletRequest request, HttpSession httpSession) throws Exception {
/* 143 */     session ses = new session(httpSession);
/* 144 */     ArrayList<Certificacion> certificaciones = new ArrayList<>();
/* 145 */     if (ses.isValid()) {
/*     */       
/* 147 */       certificaciones = null;
/* 148 */       return certificaciones;
/*     */     } 
/* 150 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 151 */     certificaciones = CertificacionDB.getCertificacion(filter, "", 0, 1000);
/* 152 */     return certificaciones;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\json\CertificacionJson.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */