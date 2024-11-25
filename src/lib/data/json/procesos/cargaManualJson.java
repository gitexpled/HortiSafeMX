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
/*     */ import lib.db.cargaManualDB;
/*     */ import lib.security.session;
/*     */ import lib.struc.Productor;
/*     */ import lib.struc.cargaManual;
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
/*     */ @Controller
/*     */ public class cargaManualJson
/*     */ {
/*     */   @RequestMapping(value = {"/cargaManual/view"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable getView(HttpServletRequest request, HttpSession httpSession) {
/*  31 */     session ses = new session(httpSession);
/*  32 */     dataTable data = new dataTable();
/*  33 */     if (ses.isValid()) {
/*     */       
/*  35 */       data.setDraw(0);
/*  36 */       data.init();
/*  37 */       return data;
/*     */     } 
/*     */     
/*  40 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/*  41 */     Map<String, String[]> parameters = request.getParameterMap();
/*  42 */     ArrayList<filterSql> filter = new ArrayList<>();
/*  43 */     for (String key : parameters.keySet()) {
/*     */       
/*  45 */       if (key.startsWith("vw_")) {
/*  46 */         String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
/*  47 */         for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
/*  48 */           System.out.println(String.valueOf(key) + " -> " + val);
/*  49 */           filterSql fil = new filterSql();
/*  50 */           fil.setCampo(key.substring(3));
/*  51 */           fil.setValue(val);
/*  52 */           filter.add(fil);
/*     */           
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/*  58 */     data.setDraw(0);
/*  59 */     data.init();
/*     */     
/*  61 */     int start = Integer.parseInt(((String[])parameters.get("start"))[0]);
/*  62 */     int length = Integer.parseInt(((String[])parameters.get("length"))[0]);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  67 */       ArrayList<cargaManual> datas = cargaManualDB.getAll(filter, "", start, length);
/*     */       
/*  69 */       Iterator<cargaManual> f = datas.iterator();
/*     */       
/*  71 */       data.setRecordsFiltered(cargaManualDB.getAllcount(filter));
/*  72 */       data.setRecordsTotal(cargaManualDB.getAllcount(filter));
/*     */       
/*  74 */       while (f.hasNext()) {
/*  75 */         cargaManual row = f.next();
/*     */         
/*  77 */         String[] d = { (new StringBuilder(String.valueOf(row.getIdCargaManual()))).toString(), row.getCodProductor(), row.getFecha(), row.getLaboratorio(), row.getIdentificador() };
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
/*     */   @RequestMapping(value = {"/cargaManual/add"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public boolean add(@RequestBody cargaManual row, HttpSession httpSession) throws ParseException {
/*  94 */     boolean resp = false;
/*  95 */     session ses = new session(httpSession);
/*  96 */     if (ses.isValid()) {
/*  97 */       return resp;
/*     */     }
/*     */ 
/*     */     
/* 101 */     row.setIdUsuario(ses.getIdUser());
/* 102 */     row.setIdTemporada(ses.getIdTemporada());
/*     */     
/* 104 */     resp = cargaManualDB.insert(row);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 109 */     return resp;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/cargaManual/{codigo}"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public cargaManual getId(@PathVariable String codigo, HttpSession httpSession) throws Exception {
/* 115 */     session ses = new session(httpSession);
/*     */     
/* 117 */     if (ses.isValid()) {
/* 118 */       cargaManual cargaManual = null;
/* 119 */       return cargaManual;
/*     */     } 
/*     */     
/* 122 */     cargaManual row = cargaManualDB.getId(codigo);
/*     */     
/* 124 */     return row;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/cargaManual/put"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public mesajesJson put(@RequestBody Productor row, HttpSession httpSession) throws Exception {
/* 131 */     session ses = new session(httpSession);
/* 132 */     mesajesJson mensaje = new mesajesJson();
/* 133 */     if (ses.isValid()) {
/* 134 */       mensaje.setEstado("error");
/* 135 */       mensaje.setMensaje("Session terminada");
/* 136 */       return mensaje;
/*     */     } 
/*     */     
/* 139 */     ProductorDB.updateProductor(row);
/*     */ 
/*     */     
/* 142 */     mensaje.setEstado("ok");
/* 143 */     mensaje.setMensaje("Guardado con exito");
/*     */     
/* 145 */     return mensaje;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/cargaManual/getAllOutFilter"}, method = {RequestMethod.GET}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public ArrayList<Productor> getAllOutFilter(HttpServletRequest request, HttpSession httpSession) throws Exception {
/* 152 */     session ses = new session(httpSession);
/* 153 */     ArrayList<Productor> productores = new ArrayList<>();
/* 154 */     if (ses.isValid()) {
/*     */       
/* 156 */       productores = null;
/* 157 */       return productores;
/*     */     } 
/* 159 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 160 */     productores = ProductorDB.getProductor(filter, "", 0, 1000);
/* 161 */     return productores;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/cargaManual/updateAnalisis"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public boolean UpdateAnalisis(@RequestBody cargaManual row, HttpSession httpSession) throws Exception {
/* 167 */     session ses = new session(httpSession);
/*     */     
/* 169 */     if (ses.isValid()) {
/* 170 */       return false;
/*     */     }
/* 172 */     return cargaManualDB.UpdateAnalisis(row);
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\json\procesos\cargaManualJson.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */