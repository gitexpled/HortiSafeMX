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
/*     */ import lib.db.CertificacionDB;
/*     */ import lib.db.MercadoCertificacionDB;
/*     */ import lib.db.ProductorCertificacionDB;
/*     */ import lib.db.ProductorDB;
/*     */ import lib.security.session;
/*     */ import lib.struc.Certificacion;
/*     */ import lib.struc.Productor;
/*     */ import lib.struc.ProductorCertificacion;
/*     */ import lib.struc.filterSql;
/*     */ import lib.struc.mercadoCertificacion;
/*     */ import lib.struc.mesajesJson;
/*     */ import org.apache.poi.ss.usermodel.Row;
/*     */ import org.apache.poi.ss.usermodel.Sheet;
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
/*     */ @Controller
/*     */ public class MercadoProductorJson
/*     */ {
/*     */   @RequestMapping(value = {"/mercadoCertificacion/view"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable view(HttpServletRequest request, HttpSession httpSession) {
/*  52 */     session ses = new session(httpSession);
/*  53 */     dataTable data = new dataTable();
/*  54 */     if (ses.isValid()) {
/*     */       
/*  56 */       data.setDraw(0);
/*  57 */       data.init();
/*  58 */       return data;
/*     */     } 
/*     */     
/*  61 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/*  62 */     Map<String, String[]> parameters = request.getParameterMap();
/*  63 */     ArrayList<filterSql> filter = new ArrayList<>();
/*  64 */     for (String key : parameters.keySet()) {
/*     */       
/*  66 */       if (key.startsWith("vw_")) {
/*  67 */         String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
/*  68 */         for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
/*  69 */           System.out.println(String.valueOf(key) + " -> " + val);
/*  70 */           filterSql fil = new filterSql();
/*  71 */           fil.setCampo(key.substring(3));
/*  72 */           fil.setValue(val);
/*  73 */           filter.add(fil);
/*     */           
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/*  79 */     data.setDraw(0);
/*  80 */     data.init();
/*     */     
/*  82 */     int start = Integer.parseInt(((String[])parameters.get("start"))[0]);
/*  83 */     int length = Integer.parseInt(((String[])parameters.get("length"))[0]);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  88 */       ArrayList<mercadoCertificacion> datas = MercadoCertificacionDB.view(filter, "", start, length);
/*     */       
/*  90 */       Iterator<mercadoCertificacion> f = datas.iterator();
/*  91 */       data.setRecordsFiltered(MercadoCertificacionDB.viewAll(filter));
/*  92 */       data.setRecordsTotal(MercadoCertificacionDB.viewAll(filter));
/*     */       
/*  94 */       while (f.hasNext()) {
/*  95 */         mercadoCertificacion row = f.next();
/*  96 */         String[] d = { row.getMercado(), row.getCertificacion(), (new StringBuilder(String.valueOf(row.getId()))).toString() };
/*     */         
/*  98 */         data.setData(d);
/*     */       }
/*     */     
/* 101 */     } catch (Exception e) {
/*     */       
/* 103 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 106 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/mercadoCertificacion/insertmercadoCertificacion"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public boolean insertTipoProducto(@RequestBody mercadoCertificacion row, HttpSession httpSession) throws ParseException {
/* 113 */     boolean resp = false;
/* 114 */     session ses = new session(httpSession);
/* 115 */     if (ses.isValid()) {
/* 116 */       return resp;
/*     */     }
/* 118 */     resp = MercadoCertificacionDB.insertProductorCertificacion(row);
/* 119 */     return resp;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/mercadoCertificacion/{codigo}"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public mercadoCertificacion getProductorCertificacionID(@PathVariable String codigo, HttpSession httpSession) throws Exception {
/* 125 */     session ses = new session(httpSession);
/*     */     
/* 127 */     if (ses.isValid()) {
/* 128 */       mercadoCertificacion mercadoCertificacion = null;
/* 129 */       return mercadoCertificacion;
/*     */     } 
/* 131 */     mercadoCertificacion row = MercadoCertificacionDB.getProdCert(Integer.parseInt(codigo));
/*     */     
/* 133 */     return row;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/mercadoCertificacion/put"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public mesajesJson setProductorCertificacion(@RequestBody mercadoCertificacion row, HttpSession httpSession) throws Exception {
/* 139 */     session ses = new session(httpSession);
/* 140 */     mesajesJson mensaje = new mesajesJson();
/* 141 */     if (ses.isValid()) {
/* 142 */       mensaje.setEstado("error");
/* 143 */       mensaje.setMensaje("Session terminada");
/* 144 */       return mensaje;
/*     */     } 
/* 146 */     System.out.println("PUT::::::::::::::::::::::::::::");
/*     */     
/* 148 */     MercadoCertificacionDB.updateTipoProducto(row);
/*     */ 
/*     */     
/* 151 */     mensaje.setEstado("ok");
/* 152 */     mensaje.setMensaje("Guardado con exito");
/*     */     
/* 154 */     return mensaje;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/mercadoCertificacion/validaCertificacion"}, method = {RequestMethod.POST}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public boolean validaCertificacion(HttpServletRequest request, HttpSession httpSession) {
/* 160 */     boolean resp = true;
/* 161 */     String idMercado = request.getParameter("idMercado");
/* 162 */     String idCertificacion = request.getParameter("idCertificacion");
/* 163 */     session ses = new session(httpSession);
/* 164 */     if (ses.isValid()) {
/* 165 */       return resp;
/*     */     }
/* 167 */     if (MercadoCertificacionDB.getCertificacionVigente(idMercado, idCertificacion) != null)
/*     */     {
/* 169 */       resp = false;
/*     */     }
/* 171 */     return resp;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/mercadoCertificacion/put"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public mesajesJson setPfx(MultipartHttpServletRequest request, HttpServletRequest request2, HttpSession httpSession) throws Exception {
/* 178 */     session ses = new session(httpSession);
/* 179 */     mesajesJson mensaje = new mesajesJson();
/* 180 */     if (ses.isValid()) {
/* 181 */       mensaje.setEstado("error");
/* 182 */       mensaje.setMensaje("Session terminada");
/* 183 */       return mensaje;
/*     */     } 
/*     */     
/* 186 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/* 187 */     Iterator<String> itr = request.getFileNames();
/*     */     
/* 189 */     MultipartFile mpf = request.getFile(itr.next());
/* 190 */     System.out.println(String.valueOf(mpf.getOriginalFilename()) + " uploaded!");
/*     */     
/* 192 */     BufferedReader br = new BufferedReader(new InputStreamReader(mpf.getInputStream(), "ISO-8859-1"));
/*     */     
/* 194 */     XSSFWorkbook xSSFWorkbook = new XSSFWorkbook(mpf.getInputStream());
/* 195 */     Sheet datatypeSheet = xSSFWorkbook.getSheetAt(0);
/* 196 */     Iterator<Row> iterator = datatypeSheet.iterator();
/*     */     
/* 198 */     int i = 0;
/*     */ 
/*     */     
/* 201 */     System.out.println(datatypeSheet.getSheetName());
/*     */     
/* 203 */     while (iterator.hasNext()) {
/* 204 */       Row currentRow = iterator.next();
/* 205 */       if (i > 0) {
/*     */         
/*     */         try {
/* 208 */           System.out.println(currentRow.getCell(0).toString().replace(".0", ""));
/* 209 */           System.out.println(currentRow.getCell(1).toString());
/* 210 */           System.out.println(currentRow.getCell(2).getDateCellValue());
/* 211 */           SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
/*     */ 
/*     */           
/*     */           try {
/* 215 */             Date fm = currentRow.getCell(2).getDateCellValue();
/* 216 */             System.out.println(ft.format(fm));
/* 217 */             ProductorCertificacion row = new ProductorCertificacion();
/* 218 */             String cod = currentRow.getCell(0).toString().replace(".0", "");
/* 219 */             Productor pro = ProductorDB.getProductor((new StringBuilder(String.valueOf(cod))).toString());
/* 220 */             row.setCodProductor(cod);
/* 221 */             Certificacion cer = CertificacionDB.getCertificacionStr(currentRow.getCell(1).toString().trim());
/* 222 */             row.setIdCertificacion(cer.getIdCertificaciones());
/* 223 */             row.setVigencia(ft.format(fm));
/* 224 */             if (cer == null || pro == null) {
/* 225 */               System.out.println("No se ha encontrado productor o certificazdo");
/*     */             } else {
/* 227 */               ProductorCertificacionDB.insertProductorCertificacion(row);
/* 228 */               System.out.println("Insertado");
/*     */             }
/*     */           
/* 231 */           } catch (Exception ee) {
/*     */ 
/*     */             
/* 234 */             System.out.println("ERRORORORRO");
/*     */           }
/*     */         
/*     */         }
/* 238 */         catch (Exception e) {
/* 239 */           e.printStackTrace();
/* 240 */           return mensaje;
/*     */         } 
/*     */       }
/* 243 */       i++;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 251 */     mensaje.setEstado("ok");
/* 252 */     mensaje.setMensaje("Guardado con exito");
/*     */     
/* 254 */     return mensaje;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\json\MercadoProductorJson.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */