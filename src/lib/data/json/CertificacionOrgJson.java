/*     */ package lib.data.json;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import lib.db.CertificacionOrgDB;
/*     */ import lib.security.session;
/*     */ import lib.struc.CertificacionOrg;
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
/*     */ public class CertificacionOrgJson
/*     */ {
/*     */   @RequestMapping(value = {"/certificacionOrg/view"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable view(HttpServletRequest request, HttpSession httpSession) {
/*  35 */     session ses = new session(httpSession);
/*  36 */     dataTable data = new dataTable();
/*  37 */     if (ses.isValid()) {
/*     */       
/*  39 */       data.setDraw(0);
/*  40 */       data.init();
/*  41 */       return data;
/*     */     } 
/*     */     
/*  44 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ORG");
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
/*  70 */       ArrayList<CertificacionOrg> datas = CertificacionOrgDB.getCertificacion(filter, "", start, length);
/*     */       
/*  72 */       Iterator<CertificacionOrg> f = datas.iterator();
/*  73 */       data.setRecordsFiltered(CertificacionOrgDB.getCertificacionesAll(filter));
/*  74 */       data.setRecordsTotal(CertificacionOrgDB.getCertificacionesAll(filter));
/*     */       
/*  76 */       while (f.hasNext()) {
/*  77 */         CertificacionOrg row = f.next();

/*  78 */         String[] d = { row.getCertificacionesCol(), row.getPrefijo(), row.getMercado(), row.getEspecie(), row.getCreado() + "", ""+row.getModificado(), (new StringBuilder(String.valueOf(row.getIdUser()))).toString(), (new StringBuilder(String.valueOf(row.getIdCertificaciones()))).toString() };
/*     */         
/*  80 */         data.setData(d);
/*     */       }
/*     */     
/*  83 */     } catch (Exception e) {
/*     */       
/*  85 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  88 */     return data;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/certificacionOrg/insertCertificacion"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public boolean insertCertificacion(@RequestBody CertificacionOrg row, HttpSession httpSession) throws ParseException {
/*  94 */     boolean resp = false;
/*  95 */     session ses = new session(httpSession);
/*  96 */     if (ses.isValid()) {
/*  97 */       return resp;
/*     */     }
/*  99 */     resp = CertificacionOrgDB.insertCertificacion(row);
/* 100 */     return resp;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/certificacionOrg/{codigo}"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public CertificacionOrg getUserId(@PathVariable String codigo, HttpSession httpSession) throws Exception {
/* 106 */     session ses = new session(httpSession);
/*     */     
/* 108 */     if (ses.isValid()) {
/* 109 */       CertificacionOrg certificacionOrg = null;
/* 110 */       return certificacionOrg;
/*     */     } 
/*     */     
/* 113 */     CertificacionOrg row = CertificacionOrgDB.getCertificacion(codigo);
/*     */     
/* 115 */     return row;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/certificacionOrg/put"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public mesajesJson update(@RequestBody CertificacionOrg row, HttpSession httpSession) throws Exception {
/* 122 */     session ses = new session(httpSession);
/* 123 */     mesajesJson mensaje = new mesajesJson();
/* 124 */     if (ses.isValid()) {
/* 125 */       mensaje.setEstado("error");
/* 126 */       mensaje.setMensaje("Session terminada");
/* 127 */       return mensaje;
/*     */     } 
/* 129 */     System.out.println("PUT::::::::::::::::::::::::::::");
/*     */     
/* 131 */     CertificacionOrgDB.updateCertificacion(row);
/*     */     
/* 133 */     mensaje.setEstado("ok");
/* 134 */     mensaje.setMensaje("Guardado con exito");
/*     */     
/* 136 */     return mensaje;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/certificacionOrg/getAllOutFilter"}, method = {RequestMethod.GET}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public ArrayList<CertificacionOrg> getAllOutFilter(HttpServletRequest request, HttpSession httpSession) throws Exception {
/* 142 */     session ses = new session(httpSession);
/* 143 */     ArrayList<CertificacionOrg> certificaciones = new ArrayList<>();
/* 144 */     if (ses.isValid()) {
/* 145 */       certificaciones = null;
/* 146 */       return certificaciones;
/*     */     } 
/* 148 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 149 */     certificaciones = CertificacionOrgDB.getCertificacion(filter, "", 0, 1000);
/* 150 */     return certificaciones;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/certificacionOrg/getEspecie/{idMercado}"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public ArrayList<CertificacionOrg> getEspecie(@PathVariable String idMercado, HttpSession httpSession) throws Exception {
/* 156 */     session ses = new session(httpSession);
/*     */     
/* 158 */     if (ses.isValid()) {
/* 159 */       ArrayList<CertificacionOrg> arrayList = null;
/* 160 */       return arrayList;
/*     */     } 
/*     */     
/* 163 */     ArrayList<CertificacionOrg> row = CertificacionOrgDB.getEspecie(idMercado, "", 0, 1000);
/*     */     
/* 165 */     return row;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/certificacionOrg/getCertificado/{idMercado}/{idEspecie}"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public ArrayList<CertificacionOrg> getCertificado(@PathVariable String idMercado, @PathVariable String idEspecie, HttpSession httpSession) throws Exception {
/* 172 */     session ses = new session(httpSession);
/*     */     
/* 174 */     if (ses.isValid()) {
/* 175 */       ArrayList<CertificacionOrg> arrayList = null;
/* 176 */       return arrayList;
/*     */     } 
/*     */     
/* 179 */     ArrayList<CertificacionOrg> row = CertificacionOrgDB.getCertificado(idMercado, idEspecie, "", 0, 1000);
/*     */     
/* 181 */     return row;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\json\CertificacionOrgJson.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */