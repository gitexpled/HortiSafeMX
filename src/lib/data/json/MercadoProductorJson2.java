/*     */ package lib.data.json;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import lib.db.MercadoProductorDB;
/*     */ import lib.security.session;
/*     */ import lib.struc.MercadoProductor;
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
/*     */ 
/*     */ @Controller
/*     */ public class MercadoProductorJson2
/*     */ {
/*     */   @RequestMapping(value = {"/mercadoProductor/delete"}, method = {RequestMethod.GET}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public mesajesJson delete(HttpServletRequest request, HttpSession httpSession) throws NumberFormatException, Exception {
/*  36 */     String id = request.getParameter("id");
/*  37 */     mesajesJson msn = new mesajesJson();
/*  38 */     session ses = new session(httpSession);
/*  39 */     if (ses.isValid()) {
/*  40 */       msn.setEstado("NOK");
/*  41 */       msn.setMensaje("usuario invalido");
/*  42 */       return msn;
/*     */     } 
/*  44 */     MercadoProductorDB.delete(id);
/*     */     
/*  46 */     msn.setEstado("OK");
/*  47 */     msn.setMensaje("Usuario Modificado");
/*  48 */     return msn;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/mercadoProductor/view"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable view(HttpServletRequest request, HttpSession httpSession) {
/*  56 */     session ses = new session(httpSession);
/*  57 */     dataTable data = new dataTable();
/*  58 */     if (ses.isValid()) {
/*     */       
/*  60 */       data.setDraw(0);
/*  61 */       data.init();
/*  62 */       return data;
/*     */     } 
/*     */     
/*  65 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/*  66 */     Map<String, String[]> parameters = request.getParameterMap();
/*  67 */     ArrayList<filterSql> filter = new ArrayList<>();
/*  68 */     for (String key : parameters.keySet()) {
/*     */       
/*  70 */       if (key.startsWith("vw_")) {
/*  71 */         String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
/*  72 */         for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
/*  73 */           filterSql fil = new filterSql();
/*  74 */           fil.setCampo(key.substring(3));
/*  75 */           fil.setValue(val);
/*  76 */           filter.add(fil);
/*     */           
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/*  82 */     data.setDraw(0);
/*  83 */     data.init();
/*     */     
/*  85 */     int start = Integer.parseInt(((String[])parameters.get("start"))[0]);
/*  86 */     int length = Integer.parseInt(((String[])parameters.get("length"))[0]);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  91 */       ArrayList<MercadoProductor> datas = MercadoProductorDB.getAll(filter, "", start, length);
/*     */       
/*  93 */       Iterator<MercadoProductor> f = datas.iterator();
/*     */       
/*  95 */       data.setRecordsFiltered(MercadoProductorDB.getAll(filter));
/*  96 */       data.setRecordsTotal(MercadoProductorDB.getAll(filter));
/*     */       
/*  98 */       while (f.hasNext()) {
/*  99 */         MercadoProductor row = f.next();
/*     */ 
/*     */         
/* 102 */         String[] d = { (new StringBuilder(String.valueOf(row.getId()))).toString(), row.getCodProductor(), row.getCodParcela(), row.getCodVariedad(), row.getMercado(),""+row.getCreado(),""+row.getModificado() };
/*     */         
/* 104 */         data.setData(d);
/*     */       }
/*     */     
/* 107 */     } catch (Exception e) {
/*     */       
/* 109 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 112 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/mercadoProductor/insert"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public boolean insert(@RequestBody MercadoProductor row, HttpSession httpSession) throws ParseException {
/* 119 */     boolean resp = false;
/* 120 */     session ses = new session(httpSession);
/* 121 */     if (ses.isValid()) {
/* 122 */       return resp;
/*     */     }
/* 124 */     resp = MercadoProductorDB.insert(row);
/* 125 */     return resp;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/mercadoProductor/{codigo}"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public MercadoProductor getById(@PathVariable String codigo, HttpSession httpSession) throws Exception {
/* 131 */     session ses = new session(httpSession);
/*     */     
/* 133 */     if (ses.isValid()) {
/* 134 */       MercadoProductor mercadoProductor = null;
/* 135 */       return mercadoProductor;
/*     */     } 
/*     */     
/* 138 */     MercadoProductor row = MercadoProductorDB.get(codigo);
/*     */     
/* 140 */     return row;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/mercadoProductor/put"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public mesajesJson update(@RequestBody MercadoProductor row, HttpSession httpSession) throws Exception {
/* 147 */     session ses = new session(httpSession);
/* 148 */     mesajesJson mensaje = new mesajesJson();
/* 149 */     if (ses.isValid()) {
/* 150 */       mensaje.setEstado("error");
/* 151 */       mensaje.setMensaje("Session terminada");
/* 152 */       return mensaje;
/*     */     } 
/* 154 */     System.out.println("PUT::::::::::::::::::::::::::::");
/* 155 */     System.out.println("INSERTO");
/* 156 */     MercadoProductorDB.update(row);
/*     */ 
/*     */     
/* 159 */     mensaje.setEstado("ok");
/* 160 */     mensaje.setMensaje("Guardado con exito");
/*     */     
/* 162 */     return mensaje;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/mercadoProductor/getAllOutFilter"}, method = {RequestMethod.GET}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public ArrayList<MercadoProductor> getSelectBox(HttpServletRequest request, HttpSession httpSession) throws Exception {
/* 169 */     session ses = new session(httpSession);
/* 170 */     ArrayList<MercadoProductor> arrays = new ArrayList<>();
/* 171 */     if (ses.isValid()) {
/*     */       
/* 173 */       arrays = null;
/* 174 */       return arrays;
/*     */     } 
/* 176 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 177 */     arrays = MercadoProductorDB.getAll(filter, "", 0, 1000);
/* 178 */     return arrays;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\json\MercadoProductorJson2.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */