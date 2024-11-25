/*     */ package lib.data.json;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStreamReader;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import lib.db.DiccionarioDB;
/*     */ import lib.db.LimiteDB;
/*     */ import lib.db.MercadoDB;
/*     */ import lib.db.especieDB;
/*     */ import lib.security.session;
/*     */ import lib.struc.Diccionario;
/*     */ import lib.struc.Limite;
/*     */ import lib.struc.Mercado;
/*     */ import lib.struc.filterSql;
/*     */ import lib.struc.mesajesJson;
/*     */ import org.apache.poi.ss.usermodel.Row;
/*     */ import org.apache.poi.ss.usermodel.Sheet;
/*     */ import org.apache.poi.ss.util.CellRangeAddress;
/*     */ import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.PathVariable;
/*     */ import org.springframework.web.bind.annotation.RequestBody;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.bind.annotation.ResponseBody;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ import org.springframework.web.multipart.MultipartHttpServletRequest;
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
/*     */ 
/*     */ 
/*     */ @Controller
/*     */ public class LimiteJson
/*     */ {
/*  53 */   private ArrayList<Mercado> listado = new ArrayList<>();
/*     */   
/*     */   @RequestMapping(value = {"/limite/put"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public mesajesJson setLimite(@RequestBody Limite row, HttpSession httpSession) throws Exception {
/*  58 */     session ses = new session(httpSession);
/*  59 */     mesajesJson mensaje = new mesajesJson();
/*  60 */     if (ses.isValid()) {
/*  61 */       mensaje.setEstado("error");
/*  62 */       mensaje.setMensaje("Session terminada");
/*  63 */       return mensaje;
/*     */     } 
/*  65 */     LimiteDB.updateLimite(row);
/*     */     
/*  67 */     mensaje.setEstado("ok");
/*  68 */     mensaje.setMensaje("Guardado con exito");
/*     */     
/*  70 */     return mensaje;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/limite/{id}"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public Limite getLimiteId(@PathVariable String id, HttpSession httpSession) throws Exception {
/*  77 */     session ses = new session(httpSession);
/*     */     
/*  79 */     if (ses.isValid()) {
/*  80 */       Limite limite = null;
/*  81 */       return limite;
/*     */     } 
/*     */     
/*  84 */     Limite row = LimiteDB.getLimite(id);
/*     */     
/*  86 */     return row;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/limite/view"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable getShopInJSON(HttpServletRequest request, HttpSession httpSession) {
/*  93 */     session ses = new session(httpSession);
/*  94 */     dataTable data = new dataTable();
/*  95 */     if (ses.isValid()) {
/*     */       
/*  97 */       data.setDraw(0);
/*  98 */       data.init();
/*  99 */       return data;
/*     */     } 
/*     */     
/* 102 */     Map<String, String[]> parameters = request.getParameterMap();
/* 103 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 104 */     for (String key : parameters.keySet()) {
/*     */       
/* 106 */       if (key.startsWith("vw_")) {
/* 107 */         String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
/* 108 */         for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
/* 109 */           filterSql fil = new filterSql();
/* 110 */           fil.setCampo(key.substring(3));
/* 111 */           fil.setValue(val);
/* 112 */           filter.add(fil);
/*     */           
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/* 118 */     data.setDraw(0);
/* 119 */     data.init();
/*     */     
/* 121 */     int start = Integer.parseInt(((String[])parameters.get("start"))[0]);
/* 122 */     int length = Integer.parseInt(((String[])parameters.get("length"))[0]);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 127 */       ArrayList<Limite> datas = LimiteDB.getLimites(filter, "idMercado", start, length);
/*     */       
/* 129 */       Iterator<Limite> f = datas.iterator();
/*     */       
/* 131 */       data.setRecordsFiltered(LimiteDB.getLimitesAll(filter));
/* 132 */       data.setRecordsTotal(LimiteDB.getLimitesAll(filter));
/*     */       
/* 134 */       while (f.hasNext()) {
/* 135 */         Limite row = f.next();
/* 136 */         String[] d = { row.getMercado(), 
/* 137 */             especieDB.getId((new StringBuilder(String.valueOf(row.getIdEspecie()))).toString()).getEspecie(), 
/* 138 */             row.getLimite(), 
/* 139 */             row.getCodProducto(), 
/* 140 */             row.getTipoProducto(), 
/* 141 */             row.getFuente(), 
/* 142 */             ""+row.getCreado(),""+row.getModificado(), (new StringBuilder(String.valueOf(row.getIdLimite()))).toString(), "" };
/*     */         
/* 144 */         data.setData(d);
/*     */       }
/*     */     
/* 147 */     } catch (Exception e) {
/*     */       
/* 149 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 152 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/limite/insertLimite"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public boolean insertLimite(@RequestBody Limite row, HttpSession httpSession) throws ParseException {
/* 159 */     boolean resp = false;
/* 160 */     session ses = new session(httpSession);
/* 161 */     if (ses.isValid()) {
/* 162 */       return resp;
/*     */     }
/* 164 */     resp = LimiteDB.insertLimite(row);
/* 165 */     return resp;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getCountMergeCell(Sheet h, int p1, int p2) {
/* 170 */     int num = 0;
/* 171 */     int nbrMergedRegions = h.getNumMergedRegions();
/*     */     
/* 173 */     for (int i = 0; i < nbrMergedRegions - 1; i++) {
/*     */ 
/*     */ 
/*     */       
/* 177 */       if (h.getMergedRegion(i) != null) {
/* 178 */         CellRangeAddress mer = h.getMergedRegion(i);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 185 */         if (mer.getFirstColumn() == p1 && mer.getFirstRow() == p2) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 190 */           num = mer.getNumberOfCells();
/* 191 */           Row row = h.getRow(mer.getFirstRow());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 199 */     return num;
/*     */   }
/*     */   
/*     */   private static String largo(String txt, int l) {
/* 203 */     return txt.substring(0, Math.min(l, txt.length()));
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/limite/validaLimite"}, method = {RequestMethod.POST}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public mesajesJson validaLimite(@RequestBody Limite lim, HttpSession httpSession) {
/* 209 */     mesajesJson resp = new mesajesJson();
/* 210 */     resp.setEstado("OK");
/* 211 */     resp.setMensaje("");
/* 212 */     session ses = new session(httpSession);
/* 213 */     if (ses.isValid()) {
/*     */       
/* 215 */       resp.setEstado("NOK");
/* 216 */       resp.setMensaje("Session caducada");
/* 217 */       return resp;
/*     */     } 
/* 219 */     Limite limite = LimiteDB.validaLimite(lim);
/* 220 */     if (limite != null) {
/*     */       
/* 222 */       resp.setEstado("NOK");
/* 223 */       resp.setMensaje("Ya existe un limite con caracteristicas similares para este mercado(limite" + limite.getLimite() + "), favor ingrese distintos valores");
/*     */     } 
/* 225 */     return resp;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/limite/put"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public mesajesJson uploadExcelLimite(MultipartHttpServletRequest request, HttpServletRequest request2, HttpSession httpSession) throws Exception {
/* 231 */     session ses = new session(httpSession);
/* 232 */     mesajesJson mensaje = new mesajesJson();
/* 233 */     if (ses.isValid()) {
/* 234 */       mensaje.setEstado("error");
/* 235 */       mensaje.setMensaje("Session terminada");
/* 236 */       return mensaje;
/*     */     } 
/*     */     
/* 239 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: " + request2.getParameter("idEspecie").trim());
/* 240 */     Iterator<String> itr = request.getFileNames();
/*     */     
/* 242 */     MultipartFile mpf = request.getFile(itr.next());
/* 243 */     System.out.println(String.valueOf(mpf.getOriginalFilename()) + " uploaded!");
/*     */     
/* 245 */     BufferedReader br = new BufferedReader(new InputStreamReader(mpf.getInputStream(), "ISO-8859-1"));
/*     */     
/* 247 */     XSSFWorkbook xSSFWorkbook = new XSSFWorkbook(mpf.getInputStream());
/* 248 */     Sheet datatypeSheet = xSSFWorkbook.getSheetAt(0);
/*     */     
/* 250 */     int numRead = getCountMergeCell(datatypeSheet, 1, 3);
/* 251 */     System.out.println(numRead);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 256 */     Row rowCod = datatypeSheet.getRow(4);
/*     */     
/* 258 */     for (int i = 6; i < 100; i++) {
/*     */       
/* 260 */       System.out.println();
/* 261 */       Row row = datatypeSheet.getRow(i);
/*     */       
/* 263 */       int numFin = getCountMergeCell(datatypeSheet, 1, i);
/* 264 */       if (numFin == numRead)
/*     */         break; 
/* 266 */       if (numFin < 2) {
/* 267 */         String codProducto = row.getCell(1).getStringCellValue().trim();
/* 268 */         System.out.print(String.format("%-22s", new Object[] { largo(row.getCell(1).getStringCellValue(), 20) }));
/* 269 */         if (DiccionarioDB.getDiccionarioStr(row.getCell(1).getStringCellValue().trim()) == null) {
/*     */           
/* 271 */           System.out.println("CREO: " + String.format("%-22s", new Object[] { largo(row.getCell(1).getStringCellValue(), 20) }));
/* 272 */           Diccionario o = new Diccionario();
/* 273 */           o.setCodProducto(row.getCell(1).getStringCellValue().trim());
/* 274 */           o.setCodReemplazo(row.getCell(1).getStringCellValue().trim());
/* 275 */           o.setCreado(new Date());
/* 276 */           o.setModificado(new Date());
/* 277 */           o.setIdUser(ses.getIdUser());
/* 278 */           DiccionarioDB.insertDiccionario(o);
/*     */         } 
/*     */ 
/*     */         
/* 282 */         for (int k = 2; k < numRead; k += 2) {
/* 283 */           String mercado = rowCod.getCell(k).getStringCellValue().trim();
/*     */ 
/*     */ 
/*     */           
/* 287 */           Mercado oMercado3 = MercadoDB.getMercadoStr(mercado);
/* 288 */           if (oMercado3 != null) {
/* 289 */             System.out.print(String.format("%-22s", new Object[] { largo(mercado, 20) }));
/*     */             
/* 291 */             this.listado = MercadoDB.getMercadoStrArray(mercado);
/* 292 */             for (Mercado oMercado : this.listado) {
/* 293 */               String value = "";
/*     */               try {
/* 295 */                 value = row.getCell(k).getStringCellValue();
/* 296 */               } catch (Exception e) {
/* 297 */                 value = String.valueOf(row.getCell(k).getNumericCellValue());
/*     */               } 
/*     */ 
/*     */               
/* 301 */               System.out.println("123" + value);
/* 302 */               value = value.replace(",", ".");
/* 303 */               value = value.replace("(a)", "");
/* 304 */               value = value.replace("(b)", "");
/* 305 */               value = value.replace("(c)", "");
/* 306 */               value = value.replace("(d)", "");
/* 307 */               value = value.replace("(e)", "");
/* 308 */               value = value.replace("TI", "");
/* 309 */               value = value.replace(" T", "");
/* 310 */               value = value.replace("P", "");
/* 311 */               value = value.replace("#", "");
/* 312 */               value = value.trim();
/* 313 */               value = value.replace("ST", "0");
/* 314 */               value = value.replace("EX", "0");
/*     */ 
/*     */               
/* 317 */               System.out.print(" " + String.format("%-5s", new Object[] { largo(value, 5) }));
/* 318 */               Limite lim = new Limite();
/* 319 */               lim.setCodProducto(codProducto);
/* 320 */               lim.setCreado(new Date());
/* 321 */               lim.setModificado(new Date());
/* 322 */               int idEspecie = Integer.parseInt(request2.getParameter("idEspecie").trim());
/* 323 */               lim.setIdEspecie(idEspecie);
/* 324 */               lim.setFuente(request2.getParameter("idFuente").trim());
/* 325 */               int idFuente = Integer.parseInt(request2.getParameter("idFuente").trim());
/* 326 */               lim.setIdFuente(idFuente);
/* 327 */               lim.setIdMercado(oMercado.getIdMercado());
/* 328 */               int tipo = Integer.parseInt(request2.getParameter("idTipoProducto").trim());
/* 329 */               lim.setIdTipoProducto(tipo);
/* 330 */               lim.setLimite(value);
/*     */               
/* 332 */               Limite limVal = LimiteDB.validaLimiteExcel(lim);
/* 333 */               if (limVal == null) {
/*     */                 
/* 335 */                 LimiteDB.insertLimite(lim);
/* 336 */                 if (lim.getIdMercado() == 1) {
/* 337 */                   lim.setIdMercado(15);
/* 338 */                   LimiteDB.insertLimite(lim);
/*     */                 } 
/*     */                 
/*     */                 continue;
/*     */               } 
/* 343 */               Limite limVal2 = LimiteDB.validaLimiteExcel2(lim);
/* 344 */               if (limVal2 == null) {
/* 345 */                 lim.setIdMercado(15);
/* 346 */                 LimiteDB.insertLimite(lim);
/*     */               } else {
/*     */                 
/* 349 */                 limVal2.setIdEspecie(idEspecie);
/* 350 */                 limVal2.setLimite(value);
/* 351 */                 limVal2.setModificado(new Date());
/* 352 */                 LimiteDB.updateLimite(limVal2);
/*     */               } 
/*     */               
/* 355 */               limVal.setIdEspecie(idEspecie);
/* 356 */               limVal.setLimite(value);
/* 357 */               limVal.setModificado(new Date());
/* 358 */               LimiteDB.updateLimite(limVal);
/*     */             }
/*     */           
/*     */           }
/* 362 */           else if (mercado.startsWith("CHINA - PROPUESTAS CHINAS") || mercado.startsWith("CODEX")) {
/* 363 */             Mercado oMercado2 = MercadoDB.getMercadoStr("CHINA");
/*     */             
/* 365 */             System.out.print("China insert 2");
/*     */ 
/*     */ 
/*     */             
/* 369 */             String value = row.getCell(k).getStringCellValue();
/* 370 */             value = value.replace(",", ".");
/* 371 */             value = value.replace("(a)", "");
/* 372 */             value = value.replace("(b)", "");
/* 373 */             value = value.replace("(c)", "");
/* 374 */             value = value.replace("(d)", "");
/* 375 */             value = value.replace("(e)", "");
/* 376 */             value = value.replace("TI", "");
/* 377 */             value = value.replace(" T", "");
/* 378 */             value = value.replace("P", "");
/* 379 */             value = value.replace("#", "");
/* 380 */             value = value.trim();
/* 381 */             value = value.replace("ST", "0");
/* 382 */             value = value.replace("EX", "0");
/*     */             
/* 384 */             System.out.print(" " + String.format("%-5s", new Object[] { largo(value, 5) }));
/* 385 */             Limite lim = new Limite();
/* 386 */             lim.setCodProducto(codProducto);
/* 387 */             lim.setCreado(new Date());
/* 388 */             lim.setModificado(new Date());
/* 389 */             int idEspecie = Integer.parseInt(request2.getParameter("idEspecie").trim());
/* 390 */             lim.setIdEspecie(idEspecie);
/* 391 */             lim.setFuente(request2.getParameter("idFuente").trim());
/* 392 */             int idFuente = Integer.parseInt(request2.getParameter("idFuente").trim());
/* 393 */             lim.setIdFuente(idFuente);
/* 394 */             lim.setIdMercado(oMercado2.getIdMercado());
/* 395 */             int tipo = Integer.parseInt(request2.getParameter("idTipoProducto").trim());
/* 396 */             lim.setIdTipoProducto(tipo);
/* 397 */             lim.setLimite(value);
/*     */             
/* 399 */             if (value.length() == 0) {
/* 400 */               value = "0";
/*     */             }
/* 402 */             if (!value.equals("0")) {
/*     */               
/* 404 */               Limite limVal = LimiteDB.validaLimiteExcelChina(lim);
/* 405 */               if (limVal != null) {
/* 406 */                 limVal.setIdEspecie(idEspecie);
/* 407 */                 limVal.setLimite(value);
/* 408 */                 limVal.setModificado(new Date());
/* 409 */                 LimiteDB.updateLimite(limVal);
/*     */               } else {
/* 411 */                 Limite limVal2 = LimiteDB.validaLimiteExcelChinaMenor(lim);
/* 412 */                 if (limVal2 != null) {
/* 413 */                   limVal2.setIdEspecie(idEspecie);
/* 414 */                   limVal2.setLimite(value);
/* 415 */                   limVal2.setModificado(new Date());
/* 416 */                   LimiteDB.updateLimite(limVal2);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 427 */     Iterator<Row> iterator = datatypeSheet.iterator();
/*     */     
/* 429 */     int j = 0;
/*     */     
/* 431 */     System.out.println(datatypeSheet.getSheetName());
/*     */     
/* 433 */     while (iterator.hasNext()) {
/* 434 */       Row currentRow = iterator.next();
/* 435 */       if (j > 0) {
/*     */         
/*     */         try {
/*     */ 
/*     */           
/* 440 */           SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 463 */         catch (Exception e) {
/* 464 */           e.printStackTrace();
/* 465 */           return mensaje;
/*     */         } 
/*     */       }
/* 468 */       j++;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 476 */     mensaje.setEstado("ok");
/* 477 */     mensaje.setMensaje("Guardado con exito");
/*     */     
/* 479 */     return mensaje;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\json\LimiteJson.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */