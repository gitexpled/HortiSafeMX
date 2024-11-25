/*     */ package lib.data.json.operaciones;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import lib.data.json.dataTable;
/*     */ import lib.db.ProductorDB;
/*     */ import lib.db.bloqueadoDB;
/*     */ import lib.db.bloqueadoOpDB;
/*     */ import lib.security.session;
/*     */ import lib.struc.Productor;
/*     */ import lib.struc.bloqueo;
/*     */ import lib.struc.filterSql;
/*     */ import lib.struc.mesajesJson;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.PathVariable;
/*     */ import org.springframework.web.bind.annotation.RequestBody;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.bind.annotation.ResponseBody;
/*     */ 
/*     */ @Controller
/*     */ public class bloqueoOPJson {
/*     */   @RequestMapping(value = {"/bloqueoOp/view"}, method = {RequestMethod.POST, RequestMethod.GET})
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
/*  66 */       ArrayList<bloqueo> datas = bloqueadoOpDB.getAll(filter, "", start, length);
/*     */       
/*  68 */       Iterator<bloqueo> f = datas.iterator();
/*     */       
/*  70 */       data.setRecordsFiltered(bloqueadoOpDB.getAllcount(filter));
/*  71 */       data.setRecordsTotal(bloqueadoOpDB.getAllcount(filter));
/*     */       
/*  73 */       while (f.hasNext()) {
/*  74 */         bloqueo row = f.next();
/*  75 */         String[] d = { (new StringBuilder(String.valueOf(row.getIdBloqueo()))).toString(), row.getCodProductor(), row.getMercado(), row.getEspecie(), row.getB() };
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
/*     */   @RequestMapping(value = {"/bloqueoOp/add"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public String add(@RequestBody bloqueo row, HttpSession httpSession) throws ParseException {
/*  92 */     String resp = "";
/*  93 */     session ses = new session(httpSession);
/*  94 */     if (ses.isValid()) {
/*  95 */       return resp;
/*     */     }
/*  97 */     row.setIdUsuario(ses.getIdUser());
/*  98 */     row.setIdTemporada(ses.getIdTemporada());
/*  99 */     resp = bloqueadoDB.insert(row);
/*     */ 
/*     */ 
/*     */     
/* 103 */     return resp;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/bloqueoOp/{codigo}"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public bloqueo getId(@PathVariable String codigo, HttpSession httpSession) throws Exception {
/* 109 */     session ses = new session(httpSession);
/*     */     
/* 111 */     if (ses.isValid()) {
/* 112 */       bloqueo bloqueo = null;
/* 113 */       return bloqueo;
/*     */     } 
/* 115 */     System.out.println("ID: " + codigo);
/* 116 */     bloqueo row = bloqueadoOpDB.getId(codigo);
/*     */     
/* 118 */     return row;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/bloqueoOp/put"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public mesajesJson put(@RequestBody bloqueo row, HttpSession httpSession) throws Exception {
/* 125 */     session ses = new session(httpSession);
/* 126 */     mesajesJson mensaje = new mesajesJson();
/* 127 */     if (ses.isValid()) {
/* 128 */       mensaje.setEstado("error");
/* 129 */       mensaje.setMensaje("Session terminada");
/* 130 */       return mensaje;
/*     */     } 
/* 132 */     System.out.println("PUT::::::::::::::::::::::::::::idBloqueo: " + row.getIdBloqueo());
/*     */     
/* 134 */     row.setIdUsuario(ses.getIdUser());
/* 135 */     row.setIdTemporada(ses.getIdTemporada());
/* 136 */     bloqueadoOpDB.update(row);
/*     */ 
/*     */     
/* 139 */     mensaje.setEstado("ok");
/* 140 */     mensaje.setMensaje("Guardado con exito");
/*     */     
/* 142 */     return mensaje;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/bloqueoOp/getAllOutFilter"}, method = {RequestMethod.GET}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public ArrayList<Productor> getAllOutFilter(HttpServletRequest request, HttpSession httpSession) throws Exception {
/* 149 */     session ses = new session(httpSession);
/* 150 */     ArrayList<Productor> productores = new ArrayList<>();
/* 151 */     if (ses.isValid()) {
/*     */       
/* 153 */       productores = null;
/* 154 */       return productores;
/*     */     } 
/* 156 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 157 */     productores = ProductorDB.getProductor(filter, "", 0, 1000);
/* 158 */     return productores;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\json\operaciones\bloqueoOPJson.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */