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
/*     */ import lib.db.ProductorCertificacionOrgDB;
/*     */ import lib.db.ProductorDB;
/*     */ import lib.security.session;
/*     */ import lib.struc.Certificacion;
/*     */ import lib.struc.Productor;
/*     */ import lib.struc.ProductorCertificacionOrg;
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
/*     */ 
/*     */ 
/*     */ @Controller
/*     */ public class ProductorCertificacionOrgJson
/*     */ {
/*     */   @RequestMapping(value = {"/ProductorCertificacionOrg/view"}, method = {RequestMethod.POST, RequestMethod.GET})
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
/*  61 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: MMMMMMMM");
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
/*  88 */       ArrayList<ProductorCertificacionOrg> datas = ProductorCertificacionOrgDB.getProductorCertificacion(filter, "", start, length);
/*     */       
/*  90 */       Iterator<ProductorCertificacionOrg> f = datas.iterator();
/*  91 */       data.setRecordsFiltered(ProductorCertificacionOrgDB.getProductorCertificacionAll(filter));
/*  92 */       data.setRecordsTotal(ProductorCertificacionOrgDB.getProductorCertificacionAll(filter));
/*     */       
/*  94 */       while (f.hasNext()) {
/*  95 */         ProductorCertificacionOrg row = f.next();
/*  96 */         String[] d = { (new StringBuilder(String.valueOf(row.getCodProductor()))).toString(), row.getCodParcela(), row.getCertificado(), row.getMercado(), row.getEspecie(), row.getVigencia(), (new StringBuilder(String.valueOf(row.getIdCert()))).toString() };
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
/*     */   @RequestMapping(value = {"/ProductorCertificacionOrg/insertProductorCertificacion"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public boolean insertTipoProducto(@RequestBody ProductorCertificacionOrg row, HttpSession httpSession) throws ParseException {
/* 113 */     boolean resp = false;
/* 114 */     session ses = new session(httpSession);
/* 115 */     if (ses.isValid()) {
/* 116 */       return resp;
/*     */     }
/* 118 */     resp = ProductorCertificacionOrgDB.insertProductorCertificacion(row);
/* 119 */     return resp;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/ProductorCertificacionOrg/{codigo}"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public ProductorCertificacionOrg getProductorCertificacionID(@PathVariable String codigo, HttpSession httpSession) throws Exception {
/* 125 */     session ses = new session(httpSession);
/*     */     
/* 127 */     if (ses.isValid()) {
/* 128 */       ProductorCertificacionOrg productorCertificacionOrg = null;
/* 129 */       return productorCertificacionOrg;
/*     */     } 
/* 131 */     System.out.println("getProductorCertificacionID::::::::::::::::::::::::::::" + codigo);
/* 132 */     ProductorCertificacionOrg row = ProductorCertificacionOrgDB.getProdCert(Integer.parseInt(codigo));
/*     */     
/* 134 */     return row;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/ProductorCertificacionOrg/putx"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public mesajesJson setProductorCertificacion(@RequestBody ProductorCertificacionOrg row, HttpSession httpSession) throws Exception {
/* 140 */     session ses = new session(httpSession);
/* 141 */     mesajesJson mensaje = new mesajesJson();
/* 142 */     if (ses.isValid()) {
/* 143 */       mensaje.setEstado("error");
/* 144 */       mensaje.setMensaje("Session terminada");
/* 145 */       return mensaje;
/*     */     } 
/* 147 */     System.out.println("PUT::::::::::::::::::::::::::::");
/*     */     
/* 149 */     ProductorCertificacionOrgDB.updateTipoProducto(row);
/*     */ 
/*     */     
/* 152 */     mensaje.setEstado("ok");
/* 153 */     mensaje.setMensaje("Guardado con exito");
/*     */     
/* 155 */     return mensaje;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/ProductorCertificacionOrg/validaCertificacion"}, method = {RequestMethod.POST}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public boolean validaCertificacion(HttpServletRequest request, HttpSession httpSession) {
/* 161 */     boolean resp = true;
/* 162 */     System.out.println("validaCertificacion:::::::::::::::::::::::::::::::::::::::: ");
/* 163 */     String codProductor = request.getParameter("codProd");
/* 164 */     String idCertificacion = request.getParameter("idCertificacion");
/* 165 */     String idMercado = request.getParameter("idMercado");
/* 166 */     String idEspecie = request.getParameter("idEspecie");
/* 167 */     session ses = new session(httpSession);
/* 168 */     if (ses.isValid()) {
/* 169 */       return resp;
/*     */     }
/* 171 */     if (ProductorCertificacionOrgDB.getCertificacionVigente(codProductor, idCertificacion, idMercado, idEspecie) != null)
/*     */     {
/* 173 */       resp = false;
/*     */     }
/* 175 */     return resp;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/ProductorCertificacionOrg/put"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public mesajesJson setPfx(MultipartHttpServletRequest request, HttpServletRequest request2, HttpSession httpSession) throws Exception {
/* 182 */     session ses = new session(httpSession);
/* 183 */     mesajesJson mensaje = new mesajesJson();
/* 184 */     if (ses.isValid()) {
/* 185 */       mensaje.setEstado("error");
/* 186 */       mensaje.setMensaje("Session terminada");
/* 187 */       return mensaje;
/*     */     } 
/*     */     
/* 190 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/* 191 */     Iterator<String> itr = request.getFileNames();
/*     */     
/* 193 */     MultipartFile mpf = request.getFile(itr.next());
/* 194 */     System.out.println(String.valueOf(mpf.getOriginalFilename()) + " uploaded!");
/*     */     
/* 196 */     BufferedReader br = new BufferedReader(new InputStreamReader(mpf.getInputStream(), "ISO-8859-1"));
/*     */     
/* 198 */     XSSFWorkbook xSSFWorkbook = new XSSFWorkbook(mpf.getInputStream());
/* 199 */     Sheet datatypeSheet = xSSFWorkbook.getSheetAt(0);
/* 200 */     Iterator<Row> iterator = datatypeSheet.iterator();
/*     */     
/* 202 */     int i = 0;
/*     */ 
/*     */     
/* 205 */     System.out.println(datatypeSheet.getSheetName());
/*     */     
/* 207 */     while (iterator.hasNext()) {
/* 208 */       Row currentRow = iterator.next();
/* 209 */       if (i > 0) {
/*     */         
/*     */         try {
/* 212 */           System.out.println(currentRow.getCell(0).toString().replace(".0", ""));
/* 213 */           System.out.println(currentRow.getCell(1).toString());
/* 214 */           System.out.println(currentRow.getCell(2).getDateCellValue());
/* 215 */           SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
/*     */ 
/*     */           
/*     */           try {
/* 219 */             Date fm = currentRow.getCell(2).getDateCellValue();
/* 220 */             System.out.println(ft.format(fm));
/* 221 */             ProductorCertificacionOrg row = new ProductorCertificacionOrg();
/* 222 */             String cod = currentRow.getCell(0).toString().replace(".0", "");
/* 223 */             Productor pro = ProductorDB.getProductor((new StringBuilder(String.valueOf(cod))).toString());
/* 224 */             row.setCodProductor(cod);
/* 225 */             Certificacion cer = CertificacionDB.getCertificacionStr(currentRow.getCell(1).toString().trim());
/* 226 */             row.setIdCertificacion(cer.getIdCertificaciones());
/* 227 */             row.setVigencia(ft.format(fm));
/* 228 */             if (cer == null || pro == null) {
/* 229 */               System.out.println("No se ha encontrado productor o certificazdo");
/*     */             } else {
/* 231 */               ProductorCertificacionOrgDB.insertProductorCertificacion(row);
/* 232 */               System.out.println("Insertado");
/*     */             }
/*     */           
/* 235 */           } catch (Exception ee) {
/*     */ 
/*     */             
/* 238 */             System.out.println("ERRORORORRO");
/*     */           }
/*     */         
/*     */         }
/* 242 */         catch (Exception e) {
/* 243 */           e.printStackTrace();
/* 244 */           return mensaje;
/*     */         } 
/*     */       }
/* 247 */       i++;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 255 */     mensaje.setEstado("ok");
/* 256 */     mensaje.setMensaje("Guardado con exito");
/*     */     
/* 258 */     return mensaje;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\json\ProductorCertificacionOrgJson.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */