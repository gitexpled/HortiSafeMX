/*     */ package lib.data.json;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import lib.db.ProductorDB;
/*     */ import lib.security.session;
/*     */ import lib.struc.Productor;
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
/*     */ public class ProductorJson
/*     */ {
/*     */   @RequestMapping(value = {"/productor/view"}, method = {RequestMethod.POST, RequestMethod.GET})
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
/*  50 */           filterSql fil = new filterSql();
/*  51 */           fil.setCampo(key.substring(3));
/*  52 */           fil.setValue(val);
/*  53 */           filter.add(fil);
/*     */           
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/*  59 */     data.setDraw(0);
/*  60 */     data.init();
/*     */     
/*  62 */     int start = Integer.parseInt(((String[])parameters.get("start"))[0]);
/*  63 */     int length = Integer.parseInt(((String[])parameters.get("length"))[0]);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  68 */       ArrayList<Productor> datas = ProductorDB.getProductor(filter, "", start, length);
/*     */       
/*  70 */       Iterator<Productor> f = datas.iterator();
/*     */       
/*  72 */       data.setRecordsFiltered(ProductorDB.getProductoresAll(filter));
/*  73 */       data.setRecordsTotal(ProductorDB.getProductoresAll(filter));
/*     */       
/*  75 */       while (f.hasNext()) {
/*  76 */         Productor row = f.next();
/*  77 */         String fsma = "NO";
/*  78 */         String habilitado = "NO";
/*     */         
/*  80 */         String[] d = { (new StringBuilder(String.valueOf(row.getCodigo()))).toString(), row.getNombre(),""+row.getCreado(),""+row.getModificado() };
/*     */         
/*  82 */         data.setData(d);
/*     */       }
/*     */     
/*  85 */     } catch (Exception e) {
/*     */       
/*  87 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  90 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/productor/insertProductor"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public boolean insertProductor(@RequestBody Productor row, HttpSession httpSession) throws ParseException {
/*  97 */     boolean resp = false;
/*  98 */     session ses = new session(httpSession);
/*  99 */     if (ses.isValid()) {
/* 100 */       return resp;
/*     */     }
/* 102 */     resp = ProductorDB.insertProductor(row);
/* 103 */     return resp;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/productor/{codigo}"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public Productor getUserId(@PathVariable String codigo, HttpSession httpSession) throws Exception {
/* 109 */     session ses = new session(httpSession);
/*     */     
/* 111 */     if (ses.isValid()) {
/* 112 */       Productor productor = null;
/* 113 */       return productor;
/*     */     } 
/*     */     
/* 116 */     Productor row = ProductorDB.getProductor(codigo);
/*     */     
/* 118 */     return row;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/productor/put"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public mesajesJson setUser(@RequestBody Productor row, HttpSession httpSession) throws Exception {
/* 125 */     session ses = new session(httpSession);
/* 126 */     mesajesJson mensaje = new mesajesJson();
/* 127 */     if (ses.isValid()) {
/* 128 */       mensaje.setEstado("error");
/* 129 */       mensaje.setMensaje("Session terminada");
/* 130 */       return mensaje;
/*     */     } 
/* 132 */     System.out.println("PUT::::::::::::::::::::::::::::");
/*     */     
/* 134 */     ProductorDB.updateProductor(row);
/*     */ 
/*     */     
/* 137 */     mensaje.setEstado("ok");
/* 138 */     mensaje.setMensaje("Guardado con exito");
/*     */     
/* 140 */     return mensaje;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/productor/getAllOutFilter"}, method = {RequestMethod.GET}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public ArrayList<Productor> getAllOutFilter(HttpServletRequest request, HttpSession httpSession) throws Exception {
/* 147 */     session ses = new session(httpSession);
/* 148 */     ArrayList<Productor> productores = new ArrayList<>();
/* 149 */     if (ses.isValid()) {
/*     */       
/* 151 */       productores = null;
/* 152 */       return productores;
/*     */     } 
/* 154 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 155 */     productores = ProductorDB.getProductor(filter, "codProductor", 0, 1000);
/* 156 */     return productores;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\json\ProductorJson.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */