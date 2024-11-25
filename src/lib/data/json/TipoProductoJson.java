/*     */ package lib.data.json;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import lib.db.TipoProductoDB;
/*     */ import lib.security.session;
/*     */ import lib.struc.TipoProducto;
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
/*     */ public class TipoProductoJson
/*     */ {
/*     */   @RequestMapping(value = {"/tipoProducto/view"}, method = {RequestMethod.POST, RequestMethod.GET})
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
/*  71 */       ArrayList<TipoProducto> datas = TipoProductoDB.getTipoProducto(filter, "", start, length);
/*     */       
/*  73 */       Iterator<TipoProducto> f = datas.iterator();
/*  74 */       data.setRecordsFiltered(TipoProductoDB.getTipoProductoAll(filter));
/*  75 */       data.setRecordsTotal(TipoProductoDB.getTipoProductoAll(filter));
/*     */       
/*  77 */       while (f.hasNext()) {
/*  78 */         TipoProducto row = f.next();
/*  79 */         String[] d = { row.getTipoProducto(), "", (new StringBuilder(String.valueOf(row.getIdTipoProducto()))).toString() };
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
/*     */   @RequestMapping(value = {"/tipoProducto/insertTipoProducto"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public boolean insertTipoProducto(@RequestBody TipoProducto row, HttpSession httpSession) throws ParseException {
/*  96 */     boolean resp = false;
/*  97 */     session ses = new session(httpSession);
/*  98 */     if (ses.isValid()) {
/*  99 */       return resp;
/*     */     }
/* 101 */     resp = TipoProductoDB.insertTipoProducto(row);
/* 102 */     return resp;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/tipoProducto/{codigo}"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public TipoProducto getTipoProductoId(@PathVariable String codigo, HttpSession httpSession) throws Exception {
/* 108 */     session ses = new session(httpSession);
/*     */     
/* 110 */     if (ses.isValid()) {
/* 111 */       TipoProducto tipoProducto = null;
/* 112 */       return tipoProducto;
/*     */     } 
/*     */     
/* 115 */     TipoProducto row = TipoProductoDB.getTipoProducto(codigo);
/*     */     
/* 117 */     return row;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/tipoProducto/getAllOutFilter"}, method = {RequestMethod.GET}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public ArrayList<TipoProducto> getAllOutFilter(HttpServletRequest request, HttpSession httpSession) throws Exception {
/* 123 */     session ses = new session(httpSession);
/* 124 */     ArrayList<TipoProducto> tiposProductos = new ArrayList<>();
/* 125 */     if (ses.isValid()) {
/*     */       
/* 127 */       tiposProductos = null;
/* 128 */       return tiposProductos;
/*     */     } 
/* 130 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 131 */     tiposProductos = TipoProductoDB.getTipoProducto(filter, "", 0, 1000);
/* 132 */     return tiposProductos;
/*     */   }
/*     */   @RequestMapping(value = {"/tipoProducto/put"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public mesajesJson setTipoProducto(@RequestBody TipoProducto row, HttpSession httpSession) throws Exception {
/* 137 */     session ses = new session(httpSession);
/* 138 */     mesajesJson mensaje = new mesajesJson();
/* 139 */     if (ses.isValid()) {
/* 140 */       mensaje.setEstado("error");
/* 141 */       mensaje.setMensaje("Session terminada");
/* 142 */       return mensaje;
/*     */     } 
/* 144 */     System.out.println("PUT::::::::::::::::::::::::::::");
/*     */     
/* 146 */     TipoProductoDB.updateTipoProducto(row);
/*     */ 
/*     */     
/* 149 */     mensaje.setEstado("ok");
/* 150 */     mensaje.setMensaje("Guardado con exito");
/*     */     
/* 152 */     return mensaje;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/tipoProducto/validaTp"}, method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public boolean validaTp(HttpServletRequest request, HttpSession httpSession) throws Exception {
/* 159 */     boolean resp = true;
/* 160 */     String name = request.getParameter("name");
/* 161 */     session ses = new session(httpSession);
/* 162 */     if (ses.isValid()) {
/* 163 */       return resp;
/*     */     }
/* 165 */     if (TipoProductoDB.getTipoProductoByName(name) != null)
/*     */     {
/* 167 */       resp = false;
/*     */     }
/* 169 */     return resp;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\json\TipoProductoJson.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */