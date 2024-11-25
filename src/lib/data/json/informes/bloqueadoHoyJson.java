/*     */ package lib.data.json.informes;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import lib.data.json.dataTable;
/*     */ import lib.db.estadoProductorDB;
/*     */ import lib.security.session;
/*     */ import lib.struc.filterSql;
/*     */ import lib.struc.restriccion;
/*     */ import org.springframework.stereotype.Controller;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ @Controller
/*     */ public class bloqueadoHoyJson
/*     */ {
/*     */   @RequestMapping(value = {"/bloqueadoHoy/view"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable view(HttpServletRequest request, HttpSession httpSession) {
/*  36 */     session ses = new session(httpSession);
/*  37 */     dataTable data = new dataTable();
/*  38 */     if (ses.isValid()) {
/*     */       
/*  40 */       data.setDraw(0);
/*  41 */       data.init();
/*  42 */       return data;
/*     */     } 
/*     */     
/*  45 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/*  46 */     Map<String, String[]> parameters = request.getParameterMap();
/*  47 */     ArrayList<filterSql> filter = new ArrayList<>();
/*  48 */     for (String key : parameters.keySet()) {
/*     */       
/*  50 */       if (key.startsWith("vw_")) {
/*  51 */         String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
/*  52 */         for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
/*  53 */           System.out.println(String.valueOf(key) + " -> " + val);
/*  54 */           filterSql fil = new filterSql();
/*  55 */           fil.setCampo(key.substring(3));
/*  56 */           fil.setValue(val);
/*  57 */           filter.add(fil);
/*     */           
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/*  63 */     data.setDraw(0);
/*  64 */     data.init();
/*     */     
/*  66 */     int start = Integer.parseInt(((String[])parameters.get("start"))[0]);
/*  67 */     int length = Integer.parseInt(((String[])parameters.get("length"))[0]);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  72 */       ArrayList<restriccion> datas = estadoProductorDB.getAll(filter, "", start, length);
/*     */       
/*  74 */       Iterator<restriccion> f = datas.iterator();
/*  75 */       data.setRecordsFiltered(estadoProductorDB.getAllcount(filter));
/*  76 */       data.setRecordsTotal(estadoProductorDB.getAllcount(filter));
/*     */       
/*  78 */       while (f.hasNext()) {
/*  79 */         restriccion row = f.next();
/*  80 */         String[] d = { row.getCodProductor(), row.getFecha(), row.getCodProducto(), row.getLimite(), row.getMercado(), row.getEspecie(), row.getnMuestra(), row.getAutomatica() };
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
/*     */   @RequestMapping(value = {"/restricciones/view"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable viewRestricciones(HttpServletRequest request, HttpSession httpSession) {
/*  97 */     session ses = new session(httpSession);
/*  98 */     dataTable data = new dataTable();
/*  99 */     if (ses.isValid()) {
/*     */       
/* 101 */       data.setDraw(0);
/* 102 */       data.init();
/* 103 */       return data;
/*     */     } 
/*     */     
/* 106 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/* 107 */     Map<String, String[]> parameters = request.getParameterMap();
/* 108 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 109 */     for (String key : parameters.keySet()) {
/*     */       
/* 111 */       if (key.startsWith("vw_")) {
/* 112 */         String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
/* 113 */         for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
/* 114 */           System.out.println(String.valueOf(key) + " -> " + val);
/* 115 */           filterSql fil = new filterSql();
/* 116 */           fil.setCampo(key.substring(3));
/* 117 */           fil.setValue(val);
/* 118 */           filter.add(fil);
/*     */           
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/* 124 */     data.setDraw(0);
/* 125 */     data.init();
/*     */     
/* 127 */     int start = Integer.parseInt(((String[])parameters.get("start"))[0]);
/* 128 */     int length = Integer.parseInt(((String[])parameters.get("length"))[0]);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 133 */       ArrayList<restriccion> datas = estadoProductorDB.getAllRest(filter, "", start, length);
/*     */       
/* 135 */       Iterator<restriccion> f = datas.iterator();
/* 136 */       data.setRecordsFiltered(estadoProductorDB.getAllcountRest(filter));
/* 137 */       data.setRecordsTotal(estadoProductorDB.getAllcountRest(filter));
/*     */       
/* 139 */       while (f.hasNext()) {
/* 140 */         restriccion row = f.next();
/* 141 */         String[] d = { row.getCodProductor(), row.getNomProductor(), row.getFecha(), row.getCodProducto(), row.getLimite(), row.getVariedad(), row.getEspecie(), row.getnMuestra(), row.getAutomatica() };
/*     */         
/* 143 */         data.setData(d);
/*     */       }
/*     */     
/* 146 */     } catch (Exception e) {
/*     */       
/* 148 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 151 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/restZona/view"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable viewrestZona(HttpServletRequest request, HttpSession httpSession) {
/* 159 */     session ses = new session(httpSession);
/* 160 */     dataTable data = new dataTable();
/* 161 */     if (ses.isValid()) {
/*     */       
/* 163 */       data.setDraw(0);
/* 164 */       data.init();
/* 165 */       return data;
/*     */     } 
/*     */     
/* 168 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/* 169 */     Map<String, String[]> parameters = request.getParameterMap();
/* 170 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 171 */     for (String key : parameters.keySet()) {
/*     */       
/* 173 */       if (key.startsWith("vw_")) {
/* 174 */         String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
/* 175 */         for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
/* 176 */           System.out.println(String.valueOf(key) + " -> " + val);
/* 177 */           filterSql fil = new filterSql();
/* 178 */           fil.setCampo(key.substring(3));
/* 179 */           fil.setValue(val);
/* 180 */           filter.add(fil);
/*     */           
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/* 186 */     data.setDraw(0);
/* 187 */     data.init();
/*     */     
/* 189 */     int start = Integer.parseInt(((String[])parameters.get("start"))[0]);
/* 190 */     int length = Integer.parseInt(((String[])parameters.get("length"))[0]);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 195 */       ArrayList<restriccion> datas = estadoProductorDB.getAllZona(filter, "", start, length);
/*     */       
/* 197 */       Iterator<restriccion> f = datas.iterator();
/* 198 */       data.setRecordsFiltered(estadoProductorDB.getAllcountZona(filter));
/* 199 */       data.setRecordsTotal(estadoProductorDB.getAllcountZona(filter));
/*     */       
/* 201 */       while (f.hasNext()) {
/* 202 */         restriccion row = f.next();
/* 203 */         String[] d = { row.getCodProductor(), row.getnMuestra(), row.getFecha().replace("00:00:00.0", ""), row.getCodProducto(), row.getLimite(), row.getEspecie(), row.getAutomatica() };
/*     */         
/* 205 */         data.setData(d);
/*     */       }
/*     */     
/* 208 */     } catch (Exception e) {
/*     */       
/* 210 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 213 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/restZona5/view"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable viewrestZona5(HttpServletRequest request, HttpSession httpSession) {
/* 220 */     session ses = new session(httpSession);
/* 221 */     dataTable data = new dataTable();
/* 222 */     if (ses.isValid()) {
/*     */       
/* 224 */       data.setDraw(0);
/* 225 */       data.init();
/* 226 */       return data;
/*     */     } 
/*     */     
/* 229 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/* 230 */     Map<String, String[]> parameters = request.getParameterMap();
/* 231 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 232 */     for (String key : parameters.keySet()) {
/*     */       
/* 234 */       if (key.startsWith("vw_")) {
/* 235 */         String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
/* 236 */         for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
/* 237 */           System.out.println(String.valueOf(key) + " -> " + val);
/* 238 */           filterSql fil = new filterSql();
/* 239 */           fil.setCampo(key.substring(3));
/* 240 */           fil.setValue(val);
/* 241 */           filter.add(fil);
/*     */           
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/* 247 */     data.setDraw(0);
/* 248 */     data.init();
/*     */     
/* 250 */     int start = Integer.parseInt(((String[])parameters.get("start"))[0]);
/* 251 */     int length = Integer.parseInt(((String[])parameters.get("length"))[0]);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 256 */       ArrayList<restriccion> datas = estadoProductorDB.getAllZona5(filter, "", start, length);
/*     */       
/* 258 */       Iterator<restriccion> f = datas.iterator();
/* 259 */       data.setRecordsFiltered(estadoProductorDB.getAllcountZona5(filter));
/* 260 */       data.setRecordsTotal(estadoProductorDB.getAllcountZona5(filter));
/*     */       
/* 262 */       while (f.hasNext()) {
/* 263 */         restriccion row = f.next();
/* 264 */         String[] d = { row.getCodProductor(), row.getnMuestra(), row.getFecha().replace("00:00:00.0", ""), row.getCodProducto(), row.getLimite(), row.getEspecie(), row.getAutomatica() };
/*     */         
/* 266 */         data.setData(d);
/*     */       }
/*     */     
/* 269 */     } catch (Exception e) {
/*     */       
/* 271 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 274 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/restZona2/view"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable viewrestZona2(HttpServletRequest request, HttpSession httpSession) {
/* 284 */     session ses = new session(httpSession);
/* 285 */     dataTable data = new dataTable();
/* 286 */     if (ses.isValid()) {
/*     */       
/* 288 */       data.setDraw(0);
/* 289 */       data.init();
/* 290 */       return data;
/*     */     } 
/*     */     
/* 293 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/* 294 */     Map<String, String[]> parameters = request.getParameterMap();
/* 295 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 296 */     for (String key : parameters.keySet()) {
/*     */       
/* 298 */       if (key.startsWith("vw_")) {
/* 299 */         String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
/* 300 */         for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
/* 301 */           System.out.println(String.valueOf(key) + " -> " + val);
/* 302 */           filterSql fil = new filterSql();
/* 303 */           fil.setCampo(key.substring(3));
/* 304 */           fil.setValue(val);
/* 305 */           filter.add(fil);
/*     */           
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/* 311 */     data.setDraw(0);
/* 312 */     data.init();
/*     */     
/* 314 */     int start = Integer.parseInt(((String[])parameters.get("start"))[0]);
/* 315 */     int length = Integer.parseInt(((String[])parameters.get("length"))[0]);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 320 */       ArrayList<restriccion> datas = estadoProductorDB.getAllZona2(filter, "", start, length);
/*     */       
/* 322 */       Iterator<restriccion> f = datas.iterator();
/* 323 */       data.setRecordsFiltered(estadoProductorDB.getAllcountZona2(filter));
/* 324 */       data.setRecordsTotal(estadoProductorDB.getAllcountZona2(filter));
/*     */       
/* 326 */       while (f.hasNext()) {
/* 327 */         restriccion row = f.next();
/* 328 */         String[] d = { row.getCodProductor(), row.getMercado(), row.getEspecie(), row.getAutomatica() };
/*     */         
/* 330 */         data.setData(d);
/*     */       }
/*     */     
/* 333 */     } catch (Exception e) {
/*     */       
/* 335 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 338 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/restMercado/view"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable viewrestMercado(HttpServletRequest request, HttpSession httpSession) {
/* 345 */     session ses = new session(httpSession);
/* 346 */     dataTable data = new dataTable();
/* 347 */     if (ses.isValid()) {
/*     */       
/* 349 */       data.setDraw(0);
/* 350 */       data.init();
/* 351 */       return data;
/*     */     } 
/*     */     
/* 354 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/* 355 */     Map<String, String[]> parameters = request.getParameterMap();
/* 356 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 357 */     for (String key : parameters.keySet()) {
/*     */       
/* 359 */       if (key.startsWith("vw_")) {
/* 360 */         String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
/* 361 */         for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
/* 362 */           System.out.println(String.valueOf(key) + " -> " + val);
/* 363 */           filterSql fil = new filterSql();
/* 364 */           fil.setCampo(key.substring(3));
/* 365 */           fil.setValue(val);
/* 366 */           filter.add(fil);
/*     */           
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/* 372 */     data.setDraw(0);
/* 373 */     data.init();
/*     */     
/* 375 */     int start = Integer.parseInt(((String[])parameters.get("start"))[0]);
/* 376 */     int length = Integer.parseInt(((String[])parameters.get("length"))[0]);
/*     */     
/* 378 */     System.out.println("restricciones por mercsado");
/*     */     
/*     */     try {
/* 381 */       ArrayList<restriccion> datas = estadoProductorDB.getAllMercado(filter, "", start, length);
/*     */       
/* 383 */       Iterator<restriccion> f = datas.iterator();
/* 384 */       data.setRecordsFiltered(estadoProductorDB.getAllcountMercado(filter));
/* 385 */       data.setRecordsTotal(estadoProductorDB.getAllcountMercado(filter));
/*     */       
/* 387 */       while (f.hasNext()) {
/* 388 */         restriccion row = f.next();
/* 389 */         String[] d = { row.getCodProductor(), row.getMercado(), row.getEspecie() };
/*     */         
/* 391 */         data.setData(d);
/*     */       }
/*     */     
/* 394 */     } catch (Exception e) {
/*     */       
/* 396 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 399 */     return data;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\json\informes\bloqueadoHoyJson.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */