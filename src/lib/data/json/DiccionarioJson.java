/*     */ package lib.data.json;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import lib.db.DiccionarioDB;
/*     */ import lib.security.session;
/*     */ import lib.struc.Diccionario;
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
/*     */ @Controller
/*     */ public class DiccionarioJson
/*     */ {
/*     */   @RequestMapping(value = {"/diccionario/view"}, method = {RequestMethod.POST, RequestMethod.GET})
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
/*  70 */       ArrayList<Diccionario> datas = DiccionarioDB.getDiccionario(filter, "", start, length);
/*     */       
/*  72 */       Iterator<Diccionario> f = datas.iterator();
/*     */       
/*  74 */       data.setRecordsFiltered(DiccionarioDB.getDiccionariosAll(filter));
/*  75 */       data.setRecordsTotal(DiccionarioDB.getDiccionariosAll(filter));
/*     */       
/*  77 */       while (f.hasNext()) {
/*  78 */         Diccionario row = f.next();
/*  79 */         String[] d = { row.getCodProducto(), row.getCodReemplazo(),""+row.getCreado(),""+row.getModificado(), (new StringBuilder(String.valueOf(row.getIdUser()))).toString(), (new StringBuilder(String.valueOf(row.getIdDiccionario()))).toString() };
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
/*     */   @RequestMapping(value = {"/diccionario/insertDiccionario"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public boolean insertDiccionario(@RequestBody Diccionario row, HttpSession httpSession) throws ParseException {
/*  96 */     boolean resp = false;
/*  97 */     session ses = new session(httpSession);
/*  98 */     if (ses.isValid()) {
/*  99 */       return resp;
/*     */     }
/* 101 */     resp = DiccionarioDB.insertDiccionario(row);
/* 102 */     return resp;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/diccionario/{codigo}"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public Diccionario getUserId(@PathVariable String codigo, HttpSession httpSession) throws Exception {
/* 108 */     session ses = new session(httpSession);
/*     */     
/* 110 */     if (ses.isValid()) {
/* 111 */       Diccionario diccionario = null;
/* 112 */       return diccionario;
/*     */     } 
/*     */     
/* 115 */     Diccionario row = DiccionarioDB.getDiccionario(codigo);
/*     */     
/* 117 */     return row;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/diccionario/put"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public mesajesJson setUser(@RequestBody Diccionario row, HttpSession httpSession) throws Exception {
/* 124 */     session ses = new session(httpSession);
/* 125 */     mesajesJson mensaje = new mesajesJson();
/* 126 */     if (ses.isValid()) {
/* 127 */       mensaje.setEstado("error");
/* 128 */       mensaje.setMensaje("Session terminada");
/* 129 */       return mensaje;
/*     */     } 
/* 131 */     System.out.println("PUT::::::::::::::::::::::::::::");
/*     */     
/* 133 */     DiccionarioDB.updateDiccionario(row);
/*     */ 
/*     */     
/* 136 */     mensaje.setEstado("ok");
/* 137 */     mensaje.setMensaje("Guardado con exito");
/*     */     
/* 139 */     return mensaje;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/diccionario/getAllOutFilter"}, method = {RequestMethod.GET}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public ArrayList<Diccionario> getAllOutFilter(HttpServletRequest request, HttpSession httpSession) throws Exception {
/* 145 */     session ses = new session(httpSession);
/* 146 */     ArrayList<Diccionario> diccionarios = new ArrayList<>();
/* 147 */     if (ses.isValid()) {
/*     */       
/* 149 */       diccionarios = null;
/* 150 */       return diccionarios;
/*     */     } 
/*     */     
/* 153 */     diccionarios = DiccionarioDB.getSelect();
/* 154 */     return diccionarios;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/diccionario/validaDiccionario"}, method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public boolean validaDiccionarioName(HttpServletRequest request, HttpSession httpSession) throws Exception {
/* 160 */     boolean resp = true;
/* 161 */     String diccionario = request.getParameter("diccionario");
/* 162 */     System.out.println(diccionario);
/* 163 */     session ses = new session(httpSession);
/* 164 */     if (ses.isValid()) {
/* 165 */       return resp;
/*     */     }
/* 167 */     if (DiccionarioDB.validaDiccionario(diccionario) != null)
/*     */     {
/* 169 */       resp = false;
/*     */     }
/* 171 */     return resp;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/diccionario/viewSame"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable viewSame(HttpServletRequest request, HttpSession httpSession) {
/* 177 */     session ses = new session(httpSession);
/* 178 */     dataTable data = new dataTable();
/* 179 */     if (ses.isValid()) {
/*     */       
/* 181 */       data.setDraw(0);
/* 182 */       data.init();
/* 183 */       return data;
/*     */     } 
/*     */     
/* 186 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/* 187 */     Map<String, String[]> parameters = request.getParameterMap();
/* 188 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 189 */     for (String key : parameters.keySet()) {
/*     */       
/* 191 */       if (key.startsWith("vw_")) {
/* 192 */         String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
/* 193 */         for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
/* 194 */           System.out.println(String.valueOf(key) + " -> " + val);
/* 195 */           filterSql fil = new filterSql();
/* 196 */           fil.setCampo(key.substring(3));
/* 197 */           fil.setValue(val);
/* 198 */           filter.add(fil);
/*     */           
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/* 204 */     data.setDraw(0);
/* 205 */     data.init();
/*     */     
/* 207 */     int start = Integer.parseInt(((String[])parameters.get("start"))[0]);
/* 208 */     int length = Integer.parseInt(((String[])parameters.get("length"))[0]);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 213 */       ArrayList<Diccionario> datas = DiccionarioDB.getDiccionarioEqual(filter, "", start, length);
/*     */       
/* 215 */       Iterator<Diccionario> f = datas.iterator();
/*     */       
/* 217 */       data.setRecordsFiltered(DiccionarioDB.getDiccionariosEqualAll(filter));
/* 218 */       data.setRecordsTotal(DiccionarioDB.getDiccionariosEqualAll(filter));
/*     */       
/* 220 */       while (f.hasNext()) {
/* 221 */         Diccionario row = f.next();
/* 222 */         String[] d = { row.getCodProducto(), (new StringBuilder(String.valueOf(row.getIdDiccionario()))).toString() };
/*     */         
/* 224 */         data.setData(d);
/*     */       }
/*     */     
/* 227 */     } catch (Exception e) {
/*     */       
/* 229 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 232 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/diccionario/getAllOutFilterEqual"}, method = {RequestMethod.GET}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public ArrayList<Diccionario> getAllOutFilterEqual(HttpServletRequest request, HttpSession httpSession) throws Exception {
/* 239 */     session ses = new session(httpSession);
/* 240 */     ArrayList<Diccionario> diccionarios = new ArrayList<>();
/* 241 */     if (ses.isValid()) {
/*     */       
/* 243 */       diccionarios = null;
/* 244 */       return diccionarios;
/*     */     } 
/*     */     
/* 247 */     diccionarios = DiccionarioDB.getSelectEqual();
/* 248 */     return diccionarios;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\json\DiccionarioJson.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */