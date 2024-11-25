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
/*     */ import lib.db.ProductorCertificacionDB;
/*     */ import lib.db.ProductorDB;
/*     */ import lib.security.session;
/*     */ import lib.struc.Certificacion;
/*     */ import lib.struc.Productor;
/*     */ import lib.struc.ProductorCertificacion;
/*     */ import lib.struc.filterSql;
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
/*     */ public class CertificacionProductorJson
/*     */ {
/*     */   @RequestMapping(value = {"/ProductorCertificacion/view"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable getShopInJSON(HttpServletRequest request, HttpSession httpSession) {
/*  50 */     session ses = new session(httpSession);
/*  51 */     dataTable data = new dataTable();
/*  52 */     if (ses.isValid()) {
/*     */       
/*  54 */       data.setDraw(0);
/*  55 */       data.init();
/*  56 */       return data;
/*     */     } 
/*     */     
/*  59 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/*  60 */     Map<String, String[]> parameters = request.getParameterMap();
/*  61 */     ArrayList<filterSql> filter = new ArrayList<>();
/*  62 */     for (String key : parameters.keySet()) {
/*     */       
/*  64 */       if (key.startsWith("vw_")) {
/*  65 */         String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
/*  66 */         for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
/*  67 */           System.out.println(String.valueOf(key) + " -> " + val);
/*  68 */           filterSql fil = new filterSql();
/*  69 */           fil.setCampo(key.substring(3));
/*  70 */           fil.setValue(val);
/*  71 */           filter.add(fil);
/*     */           
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/*  77 */     data.setDraw(0);
/*  78 */     data.init();
/*     */     
/*  80 */     int start = Integer.parseInt(((String[])parameters.get("start"))[0]);
/*  81 */     int length = Integer.parseInt(((String[])parameters.get("length"))[0]);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  86 */       ArrayList<ProductorCertificacion> datas = ProductorCertificacionDB.getProductorCertificacion(filter, "", start, length);
/*     */       
/*  88 */       Iterator<ProductorCertificacion> f = datas.iterator();
/*  89 */       data.setRecordsFiltered(ProductorCertificacionDB.getProductorCertificacionAll(filter));
/*  90 */       data.setRecordsTotal(ProductorCertificacionDB.getProductorCertificacionAll(filter));
/*     */       
/*  92 */       while (f.hasNext()) {
/*  93 */         ProductorCertificacion row = f.next();
/*  94 */         String[] d = { (new StringBuilder(String.valueOf(row.getCodProductor()))).toString(), row.getCodParcela(), row.getCertificado(), row.getVigencia(), (new StringBuilder(String.valueOf(row.getIdCert()))).toString() };
/*     */         
/*  96 */         data.setData(d);
/*     */       }
/*     */     
/*  99 */     } catch (Exception e) {
/*     */       
/* 101 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 104 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/ProductorCertificacion/insertProductorCertificacion"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public boolean insertTipoProducto(@RequestBody ProductorCertificacion row, HttpSession httpSession) throws ParseException {
/* 111 */     boolean resp = false;
/* 112 */     session ses = new session(httpSession);
/* 113 */     if (ses.isValid()) {
/* 114 */       return resp;
/*     */     }
/* 116 */     resp = ProductorCertificacionDB.insertProductorCertificacion(row);
/* 117 */     return resp;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/ProductorCertificacion/{codigo}"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public ProductorCertificacion getProductorCertificacionID(@PathVariable String codigo, HttpSession httpSession) throws Exception {
/* 123 */     session ses = new session(httpSession);
/*     */     
/* 125 */     if (ses.isValid()) {
/* 126 */       ProductorCertificacion productorCertificacion = null;
/* 127 */       return productorCertificacion;
/*     */     } 
/* 129 */     ProductorCertificacion row = ProductorCertificacionDB.getProdCert(Integer.parseInt(codigo));
/*     */     
/* 131 */     return row;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/ProductorCertificacion/putx"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public mesajesJson setProductorCertificacion(@RequestBody ProductorCertificacion row, HttpSession httpSession) throws Exception {
/* 137 */     session ses = new session(httpSession);
/* 138 */     mesajesJson mensaje = new mesajesJson();
/* 139 */     if (ses.isValid()) {
/* 140 */       mensaje.setEstado("error");
/* 141 */       mensaje.setMensaje("Session terminada");
/* 142 */       return mensaje;
/*     */     } 
/* 144 */     System.out.println("PUT::::::::::::::::::::::::::::");
/*     */     
/* 146 */     ProductorCertificacionDB.updateTipoProducto(row);
/*     */ 
/*     */     
/* 149 */     mensaje.setEstado("ok");
/* 150 */     mensaje.setMensaje("Guardado con exito");
/*     */     
/* 152 */     return mensaje;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/ProductorCertificacion/validaCertificacion"}, method = {RequestMethod.POST}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public boolean validaCertificacion(HttpServletRequest request, HttpSession httpSession) {
/* 158 */     boolean resp = true;
/* 159 */     String codProductor = request.getParameter("codProd");
/* 160 */     String idCertificacion = request.getParameter("idCertificacion");
/* 161 */     session ses = new session(httpSession);
/* 162 */     if (ses.isValid()) {
/* 163 */       return resp;
/*     */     }
/* 165 */     if (ProductorCertificacionDB.getCertificacionVigente(codProductor, idCertificacion) != null)
/*     */     {
/* 167 */       resp = false;
/*     */     }
/* 169 */     return resp;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/ProductorCertificacion/put"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public mesajesJson setPfx(MultipartHttpServletRequest request, HttpServletRequest request2, HttpSession httpSession) throws Exception {
/* 176 */     session ses = new session(httpSession);
/* 177 */     mesajesJson mensaje = new mesajesJson();
/* 178 */     if (ses.isValid()) {
/* 179 */       mensaje.setEstado("error");
/* 180 */       mensaje.setMensaje("Session terminada");
/* 181 */       return mensaje;
/*     */     } 
/*     */     
/* 184 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/* 185 */     Iterator<String> itr = request.getFileNames();
/*     */     
/* 187 */     MultipartFile mpf = request.getFile(itr.next());
/* 188 */     System.out.println(String.valueOf(mpf.getOriginalFilename()) + " uploaded!");
/*     */     
/* 190 */     BufferedReader br = new BufferedReader(new InputStreamReader(mpf.getInputStream(), "ISO-8859-1"));
/*     */     
/* 192 */     XSSFWorkbook xSSFWorkbook = new XSSFWorkbook(mpf.getInputStream());
/* 193 */     Sheet datatypeSheet = xSSFWorkbook.getSheetAt(0);
/* 194 */     Iterator<Row> iterator = datatypeSheet.iterator();
/*     */     
/* 196 */     int i = 0;
/*     */ 
/*     */     
/* 199 */     System.out.println(datatypeSheet.getSheetName());
/*     */     
/* 201 */     while (iterator.hasNext()) {
/* 202 */       Row currentRow = iterator.next();
/* 203 */       if (i > 0) {
/*     */         
/*     */         try {
/* 206 */           System.out.println(currentRow.getCell(0).toString().replace(".0", ""));
/* 207 */           System.out.println(currentRow.getCell(1).toString());
/* 208 */           System.out.println(currentRow.getCell(2).getDateCellValue());
/* 209 */           SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
/*     */ 
/*     */           
/*     */           try {
/* 213 */             Date fm = currentRow.getCell(2).getDateCellValue();
/* 214 */             System.out.println(ft.format(fm));
/* 215 */             ProductorCertificacion row = new ProductorCertificacion();
/* 216 */             String cod = currentRow.getCell(0).toString().replace(".0", "");
/* 217 */             Productor pro = ProductorDB.getProductor((new StringBuilder(String.valueOf(cod))).toString());
/* 218 */             row.setCodProductor(cod);
/* 219 */             Certificacion cer = CertificacionDB.getCertificacionStr(currentRow.getCell(1).toString().trim());
/* 220 */             row.setIdCertificacion(cer.getIdCertificaciones());
/* 221 */             row.setVigencia(ft.format(fm));
/* 222 */             if (cer == null || pro == null) {
/* 223 */               System.out.println("No se ha encontrado productor o certificazdo");
/*     */             } else {
/* 225 */               ProductorCertificacionDB.insertProductorCertificacion(row);
/* 226 */               System.out.println("Insertado");
/*     */             }
/*     */           
/* 229 */           } catch (Exception ee) {
/*     */ 
/*     */             
/* 232 */             System.out.println("ERRORORORRO");
/*     */           }
/*     */         
/*     */         }
/* 236 */         catch (Exception e) {
/* 237 */           e.printStackTrace();
/* 238 */           return mensaje;
/*     */         } 
/*     */       }
/* 241 */       i++;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 249 */     mensaje.setEstado("ok");
/* 250 */     mensaje.setMensaje("Guardado con exito");
/*     */     
/* 252 */     return mensaje;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\json\CertificacionProductorJson.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */