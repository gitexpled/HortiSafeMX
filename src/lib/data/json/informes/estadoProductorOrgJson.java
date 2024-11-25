/*     */ package lib.data.json.informes;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletOutputStream;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import lib.data.json.dataTable;
/*     */ import lib.db.CertificacionOrgDB;
/*     */ import lib.db.MercadoDB;
/*     */ import lib.db.ProductorCertificacionOrgDB;
/*     */ import lib.db.ProductorDB;
/*     */ import lib.db.especieDB;
/*     */ import lib.db.estadoProductorDB;
/*     */ import lib.security.session;
/*     */ import lib.struc.Mercado;
/*     */ import lib.struc.Productor;
/*     */ import lib.struc.ProductorCertificacionOrg;
/*     */ import lib.struc.especie;
/*     */ import lib.struc.filterSql;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.PathVariable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Controller
/*     */ public class estadoProductorOrgJson
/*     */ {
/*     */   @RequestMapping(value = {"/detalleRestOrg/{mercado}/{productor}/{parcela}/{turno}/{especie}/{variedad}"}, method = {RequestMethod.GET})
/*     */   public void detalleRestOrg(HttpServletResponse response, 
		@PathVariable("mercado") String mercado, 
		@PathVariable("productor") String productor, 
		@PathVariable("parcela") String parcela, 
		@PathVariable("turno") String turno, 
		@PathVariable("especie") String especie, 
		@PathVariable("variedad") String variedad, 
		HttpSession httpSession) throws Exception {
/*  54 */     session ses = new session(httpSession);
/*     */     
/*  56 */     if (ses.isValid()) {
/*     */       
/*     */       try {
/*  59 */         ServletOutputStream servletOutputStream = response.getOutputStream();
/*  60 */         servletOutputStream.write("session terminada".getBytes(Charset.forName("iso-8859-1")));
/*  61 */         servletOutputStream.close();
/*  62 */       } catch (IOException e) {
/*     */         
/*  64 */         e.printStackTrace();
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*  69 */     String html = "";
/*  70 */     ArrayList<filterSql> filter = new ArrayList<>();
/*  71 */     filterSql fil = new filterSql();
/*  72 */     fil.setCampo("codProductor");
/*  73 */     fil.setValue(productor);
/*  74 */     filter.add(fil);
/*     */     
/*  76 */     filterSql fil2 = new filterSql();
/*  77 */     fil2.setCampo("mercado");
/*  78 */     fil2.setValue(mercado);
/*  79 */     filter.add(fil2);
/*  80 */     especie espe = especieDB.getId(especie);
/*  81 */     filterSql fil3 = new filterSql();
/*  82 */     fil3.setCampo("especie");
/*  83 */     fil3.setValue(espe.getEspecie());
/*  84 */     filter.add(fil3);
/*     */     
/*  86 */     ArrayList<ProductorCertificacionOrg> row = ProductorCertificacionOrgDB.getProductorCertificacion(filter, "", 0, 99999);
/*  87 */     Iterator<ProductorCertificacionOrg> f = row.iterator();
/*     */     
/*  89 */     Productor p = ProductorDB.getProductor(productor);
/*     */     
/*  91 */     Mercado m = MercadoDB.getMercadoByName(mercado);
/*  92 */     html = String.valueOf(html) + "<table  width='65%'><tr><td width='80%' valign='top'>";
/*  93 */     html = String.valueOf(html) + "<b style='font-size:18px'>" + productor + ": " + p.getNombre() + "</b>";
/*  94 */     html = String.valueOf(html) + "<br><br><table  width='350px'>";
/*  95 */     html = String.valueOf(html) + "<tr><td width='120px'></td><td> </td></tr>";
/*  96 */     html = String.valueOf(html) + "</table>";
/*  97 */     html = String.valueOf(html) + "</td><td  width='60%'>";
/*  98 */     html = String.valueOf(html) + "<table  width='350px'>";
/*  99 */     html = String.valueOf(html) + "<tr><td>Mercado</td><td> " + mercado + "</td></tr>";
/* 100 */     html = String.valueOf(html) + "<tr><td>Especie</td><td>" + espe.getEspecie() + "</td></tr>";
/*     */     
/* 102 */     String exporta = "Y";
/*     */     
/* 104 */     exporta = estadoProductorDB.getEstadoProductorOrg(ses.getIdTemporada(), espe.getIdEspecie(), productor, parcela, turno, mercado, variedad);
/* 105 */     if (exporta.equals("Y")) {
/* 106 */       html = String.valueOf(html) + "<tr><td>Habilitado</td><td bgcolor='green' align='center'>SI</td></tr>";
/*     */     } else {
/* 108 */       html = String.valueOf(html) + "<tr><td>Habilitado</td><td bgcolor='red' align='center'>NO</td></tr>";
/*     */     } 
/* 110 */     html = String.valueOf(html) + "</table>";
/* 111 */     html = String.valueOf(html) + "</td></tr></table>";
/*     */     
/* 113 */     html = String.valueOf(html) + "<br><b style='font-size:18px'>Vigencia certificados</b><br>";
/* 114 */     if (row.size() > 0) {
/* 115 */       html = String.valueOf(html) + "<table border=1>";
/* 116 */       html = String.valueOf(html) + "<tr><td>&nbsp;Certificado&nbsp;</td><td>&nbsp;Fecha&nbsp;</td><td>&nbsp;Vigencia&nbsp;</td></tr>";
/* 117 */       Date date1 = new Date();
/* 118 */       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
/* 119 */       while (f.hasNext()) {
/* 120 */         ProductorCertificacionOrg o = f.next();
/*     */         
/* 122 */         html = String.valueOf(html) + "<tr><td>&nbsp;" + CertificacionOrgDB.getCertificacion((new StringBuilder(String.valueOf(o.getIdCertificacion()))).toString()).getCertificacionesCol() + "&nbsp;</td>" + "<td>&nbsp;" + o.getVigencia() + "&nbsp;</td>";
/*     */         
/* 124 */         Date date2 = formatter.parse(o.getVigencia());
/*     */         
/* 126 */         if (!date1.after(date2)) {
/* 127 */           html = String.valueOf(html) + "<td>&nbsp;SI&nbsp;</td>";
/*     */         } else {
/* 129 */           html = String.valueOf(html) + "<td>&nbsp;NO&nbsp;</td>";
/*     */         } 
/*     */         
/* 132 */         html = String.valueOf(html) + "</tr>";
/*     */       } 
/*     */       
/* 135 */       html = String.valueOf(html) + "</table><br/><br/>";
/*     */     } else {
/* 137 */       html = String.valueOf(html) + "<div style='font-size:14px'>No presenta certificados</div>";
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 142 */       ServletOutputStream servletOutputStream = response.getOutputStream();
/* 143 */       response.setCharacterEncoding("UTF-8");
/*     */       
/* 145 */       servletOutputStream.write(html.getBytes("UTF-8"));
/* 146 */       servletOutputStream.close();
/* 147 */     } catch (IOException e) {
/*     */       
/* 149 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/cargaAutomaticaOrg/test"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable getTest(HttpServletRequest request, HttpSession httpSession) {
/* 157 */     System.out.println("hola mundo cruel");
/* 158 */     dataTable data = new dataTable();
/*     */     
/* 160 */     data.init();
/* 161 */     data.setDraw(0);
/* 162 */     session ses = new session(httpSession);
/*     */     try {
/* 164 */       ArrayList<String[]> pp = estadoProductorDB.getEstadoProductorA(ses.getIdTemporada(), 1, "", "", Boolean.valueOf(false), "");
/* 165 */       data.setDatas(pp);
/* 166 */       data.setRecordsFiltered(pp.size());
/* 167 */       data.setRecordsTotal(pp.size());
/* 168 */     } catch (Exception e) {
/* 169 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 172 */     return data;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/estadoProductorOrg/view"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable viewReport(HttpServletRequest request, HttpSession httpSession) {
/* 178 */     dataTable data = new dataTable();
/*     */     
/* 180 */     data.init();
/* 181 */     data.setDraw(0);
/* 182 */     session ses = new session(httpSession);
/*     */     
/* 184 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/* 185 */     Map<String, String[]> parameters = request.getParameterMap();
/* 186 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 187 */     int idEspecie = 1;
/* 188 */     String productor = "";
/* 189 */     String nombre = "";
/* 190 */     for (String key : parameters.keySet()) {
/*     */       
/* 192 */       if (key.startsWith("vw_")) {
/* 193 */         String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
/* 194 */         for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
/* 195 */           System.out.println(String.valueOf(key) + " -> " + val);
/* 196 */           filterSql fil = new filterSql();
/* 197 */           fil.setCampo(key.substring(3));
/* 198 */           fil.setValue(val);
/* 199 */           filter.add(fil);
/*     */           
/* 201 */           if (key.equals("vw_especie")) {
/*     */             try {
/* 203 */               idEspecie = Integer.parseInt(val);
/* 204 */             } catch (Exception exception) {}
/*     */           }
/*     */           
/* 207 */           if (key.equals("vw_productor")) {
/*     */             try {
/* 209 */               productor = val;
/* 210 */             } catch (Exception exception) {}
/*     */           }
/*     */           
/* 213 */           if (key.equals("vw_nombre")) {
/*     */             try {
/* 215 */               nombre = val;
/* 216 */             } catch (Exception exception) {}
/*     */           }
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/* 222 */     System.out.println("ses.isValid()" + ses.isValid());
/*     */     
/* 224 */     if (!ses.isValid()) {
/* 225 */       System.out.println("ses.isValid()" + ses.isValid());
/* 226 */       data.setDraw(0);
/* 227 */       data.init();
/*     */       try {
/* 229 */         System.out.println("idEspecie: " + idEspecie);
/* 230 */         ArrayList<String[]> pp = estadoProductorDB.getEstadoProductorAOrg(ses.getIdTemporada(), idEspecie, productor, nombre, Boolean.valueOf(false), "");
/* 231 */         data.setDatas(pp);
/* 232 */         data.setRecordsFiltered(pp.size());
/* 233 */         data.setRecordsTotal(pp.size());
/* 234 */       } catch (Exception e) {
/*     */         
/* 236 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 240 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/estadoProductorProOrg/view"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable getProduccionPO(HttpServletRequest request, HttpSession httpSession) {
/* 247 */     dataTable data = new dataTable();
/*     */     
/* 249 */     data.init();
/* 250 */     data.setDraw(0);
/* 251 */     session ses = new session(httpSession);
/*     */     
/* 253 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/* 254 */     Map<String, String[]> parameters = request.getParameterMap();
/* 255 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 256 */     int idEspecie = 1;
/* 257 */     String productor = "";
/* 258 */     String nombre = "";
/* 259 */     for (String key : parameters.keySet()) {
/*     */       
/* 261 */       if (key.startsWith("vw_")) {
/* 262 */         String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
/* 263 */         for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
/* 264 */           System.out.println(String.valueOf(key) + " -> " + val);
/* 265 */           filterSql fil = new filterSql();
/* 266 */           fil.setCampo(key.substring(3));
/* 267 */           fil.setValue(val);
/* 268 */           filter.add(fil);
/*     */           
/* 270 */           if (key.equals("vw_especie")) {
/*     */             try {
/* 272 */               idEspecie = Integer.parseInt(val);
/* 273 */             } catch (Exception exception) {}
/*     */           }
/*     */           
/* 276 */           if (key.equals("vw_productor")) {
/*     */             try {
/* 278 */               productor = val;
/* 279 */             } catch (Exception exception) {}
/*     */           }
/*     */           
/* 282 */           if (key.equals("vw_nombre")) {
/*     */             try {
/* 284 */               nombre = val;
/* 285 */             } catch (Exception exception) {}
/*     */           }
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/* 291 */     System.out.println("ses.isValid()" + ses.isValid());
/*     */     
/* 293 */     if (!ses.isValid()) {
/*     */       
/* 295 */       data.setDraw(0);
/* 296 */       data.init();
/*     */       try {
/* 298 */         System.out.println("idEspecie: " + idEspecie);
/* 299 */         ArrayList<String[]> pp = estadoProductorDB.getEstadoProductorPro(ses.getIdTemporada(), idEspecie, productor, nombre);
/* 300 */         data.setDatas(pp);
/* 301 */         data.setRecordsFiltered(pp.size());
/* 302 */         data.setRecordsTotal(pp.size());
/* 303 */       } catch (Exception e) {
/*     */         
/* 305 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 309 */     return data;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\json\informes\estadoProductorOrgJson.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */