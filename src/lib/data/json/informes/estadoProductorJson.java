package lib.data.json.informes;
 
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lib.data.json.dataTable;
import lib.db.CertificacionDB;
import lib.db.MercadoDB;
import lib.db.ProductorCertificacionDB;
import lib.db.ProductorDB;
import lib.db.especieDB;
import lib.db.estadoProductorDB;
import lib.db.informesDB;
import lib.sap.Sync;
import lib.security.session;
import lib.struc.Mercado;
import lib.struc.Productor;
import lib.struc.ProductorCertificacion;
import lib.struc.especie;
import lib.struc.filterSql;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

	@Controller
	public class estadoProductorJson
	{
		@RequestMapping(value = {"/detalleRest/{mercado}/{productor}/{parcela}/{turno}/{especie}/{variedad}"}, method = {RequestMethod.GET})
		public void getData(HttpServletResponse response, 
		@PathVariable("mercado") String mercado, 
		@PathVariable("productor") String productor,
		@PathVariable("parcela") String parcela, 
		@PathVariable("turno") String turno, 
		@PathVariable("especie") String especie,
		@PathVariable("variedad") String variedad,HttpSession httpSession) throws Exception {
			session ses = new session(httpSession);
		
			if (ses.isValid()) {
				try {
					ServletOutputStream servletOutputStream = response.getOutputStream();
					servletOutputStream.write("session terminada".getBytes(Charset.forName("iso-8859-1")));
					servletOutputStream.close();
				} catch (IOException e) { 
					e.printStackTrace();
				} 

				return;
			} 
			String html = "";
			ArrayList<filterSql> filter = new ArrayList<>();
			filterSql fil = new filterSql();
			fil.setCampo("codProductor");
			fil.setValue(productor);
			filter.add(fil);
			
			ArrayList<ProductorCertificacion> row = ProductorCertificacionDB.getProductorCertificacion(filter, "", 0, 99999);
			Iterator<ProductorCertificacion> f = row.iterator();

			Productor p = ProductorDB.getProductor(productor);
			especie espe = especieDB.getId(especie);
			Mercado m = MercadoDB.getMercadoByName(mercado);
			html = String.valueOf(html) + "<table  width='65%'><tr><td width='80%' valign='top'>";
			html = String.valueOf(html) + "<b style='font-size:18px'>" + productor + ": " + p.getNombre() + "</b>"
					+ "<br><b style='font-size:18px'>Huerto " + parcela + "</b>"
					+ "<br><b style='font-size:18px'>Sector " + turno + "</b>"
					+ "<br><b style='font-size:18px'>Variedad " + variedad + "</b>";
			html = String.valueOf(html) + "<br><br><table  width='350px'>";
			html = String.valueOf(html) + "<tr><td width='120px'>Certificaciones:</td><td> " + estadoProductorDB.getRestriccionesMercadoCert(m.getIdMercado()) + "</td></tr>";
			html = String.valueOf(html) + "</table>";
			html = String.valueOf(html) + "</td><td width='60%'>";
			html = String.valueOf(html) + "<table  width='350px'>";
			html = String.valueOf(html) + "<tr><td>Mercado</td><td> " + mercado + "</td></tr>";
			html = String.valueOf(html) + "<tr><td>Especie</td><td>" + espe.getEspecie() + "</td></tr>";

			String rMoleculas = "No";
			String rProductor = "No";
			if (m.getProductor().equals("Y"))
				rProductor = "Si"; 
			if (m.getRetricionMolecula().equals("Y")) {
				rMoleculas = "Si";
			}
			html = String.valueOf(html) + "<tr><td >Regla Molecula</td><td>" + rMoleculas + "</td></tr>";
			html = String.valueOf(html) + "<tr><td width='120px'>Regla Productor</td><td>" + rProductor + "</td></tr>";
			  
			String exporta = "Y";
 
			exporta = estadoProductorDB.getEstadoProductor(ses.getIdTemporada(), espe.getIdEspecie(), productor, parcela, turno, mercado,variedad);
			if (exporta.equals("SI")) {
				html = String.valueOf(html) + "<tr><td>Habilitado</td><td bgcolor='green' align='center'>SI</td></tr>";
			} else {
				html = String.valueOf(html) + "<tr><td>Habilitado</td><td bgcolor='red' align='center'>NO</td></tr>";
			} 

			html = String.valueOf(html) + "</table>";
			html = String.valueOf(html) + "</td></tr></table>";

			filterSql fil3 = new filterSql();
			fil3.setCampo("idEspecie");
			fil3.setValue(especie);
			filter.add(fil3);
  
			filterSql fil4 = new filterSql();
			fil4.setCampo("mercado");
			fil4.setValue(mercado);
			filter.add(fil4);

			filterSql fil2 = new filterSql();
			fil2.setCampo("activo");
			fil2.setValue("Y");
			filter.add(fil2);

			if (!rMoleculas.equals("No"))
				html = String.valueOf(html) + estadoProductorDB.getBlockMolecula(ses.getIdTemporada(), espe.getIdEspecie(), 0, mercado, productor, parcela, turno); 
			html = String.valueOf(html) + "<br>";
  
			if (!rProductor.equals("No")) {
				html = String.valueOf(html) + estadoProductorDB.getBlockProductor(ses.getIdTemporada(), espe.getIdEspecie(), 0, mercado, productor, parcela, turno);
			}

			html = String.valueOf(html) + "<br><b style='font-size:18px'>Vigencia certificados</b><br>";
			if (row.size() > 0) {
				html = String.valueOf(html) + "<table border=1>";
				html = String.valueOf(html) + "<tr><td>&nbsp;Certificado&nbsp;</td><td>&nbsp;Fecha&nbsp;</td><td>&nbsp;Vigencia&nbsp;</td></tr>";
				Date date1 = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				while (f.hasNext()) {
					ProductorCertificacion o = f.next();

					html = String.valueOf(html) + "<tr><td>&nbsp;" + 
							CertificacionDB.getCertificacion((new StringBuilder(String.valueOf(o.getIdCertificacion()))).toString()).getCertificacionesCol() + 
							"&nbsp;</td>" + "<td>&nbsp;" + o.getVigencia() + "&nbsp;</td>";

					Date date2 = formatter.parse(o.getVigencia());
  
					if (!date1.after(date2)) {
						html = String.valueOf(html) + "<td>&nbsp;SI&nbsp;</td>";
					} else {
						html = String.valueOf(html) + "<td>&nbsp;NO&nbsp;</td>";
					} 
  
					html = String.valueOf(html) + "</tr>";
				} 
  
				html = String.valueOf(html) + "</table><br/><br/>";
			} else {
				html = String.valueOf(html) + "<div style='font-size:14px'>No presenta certificados</div>";
			} 
			html = String.valueOf(html) + "<div style='width:70%; text-align: center;'><b  style='font-size:18px'>Resultado de pesticidas</b></div>";
			html = String.valueOf(html) + "<b  style='font-size:18px'>Carga Automatica</b>";
			html = String.valueOf(html) + informesDB.getDetalleRestriccion(mercado, productor, parcela, turno, especie,variedad);
			html = String.valueOf(html) + "<br/><b  style='font-size:18px'>Carga Manual</b>";
			html = String.valueOf(html) + informesDB.getDetalleRestriccionM(mercado, productor, parcela, turno, especie,variedad);
			html = (new StringBuilder(String.valueOf(html))).toString();

			try {
				ServletOutputStream servletOutputStream = response.getOutputStream();
				response.setCharacterEncoding("UTF-8");

				servletOutputStream.write(html.getBytes("UTF-8"));
				servletOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}

/*     */   @RequestMapping(value = {"/cargaAutomatica/test"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable getTest(HttpServletRequest request, HttpSession httpSession) {
/* 209 */     System.out.println("hola mundo cruel");
/* 210 */     dataTable data = new dataTable();
/*     */     
/* 212 */     data.init();
/* 213 */     data.setDraw(0);
/* 214 */     session ses = new session(httpSession);
/*     */     try {
/* 216 */       ArrayList<String[]> pp = estadoProductorDB.getEstadoProductorA(ses.getIdTemporada(), 1, "", "", Boolean.valueOf(false), "");
/* 217 */       data.setDatas(pp);
/* 218 */       data.setRecordsFiltered(pp.size());
/* 219 */       data.setRecordsTotal(pp.size());
/* 220 */     } catch (Exception e) {
/* 221 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 224 */     return data;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/estadoProductor/view"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable getShopInJSON(HttpServletRequest request, HttpSession httpSession) {
/* 230 */     dataTable data = new dataTable();
/*     */     
/* 232 */     data.init();
/* 233 */     data.setDraw(0);
/* 234 */     session ses = new session(httpSession);
/*     */ 
/*     */     
/* 237 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/* 238 */     Map<String, String[]> parameters = request.getParameterMap();
/* 239 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 240 */     int idEspecie = 1;
/* 241 */     String productor = "";
/* 242 */     String nombre = "";
/* 243 */     for (String key : parameters.keySet()) {
/*     */       
/* 245 */       if (key.startsWith("vw_")) {
/* 246 */         String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
/* 247 */         for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
/* 248 */           System.out.println(String.valueOf(key) + " -> " + val);
/* 249 */           filterSql fil = new filterSql();
/* 250 */           fil.setCampo(key.substring(3));
/* 251 */           fil.setValue(val);
/* 252 */           filter.add(fil);
					System.out.println(key);
					if (key.equals("vw_variedad")) {
						System.out.println("aqui");
					}
/*     */           
/* 254 */           if (key.equals("vw_especie")) {
/*     */             
/*     */             try {
/* 257 */               idEspecie = Integer.parseInt(val);
/* 258 */             } catch (Exception exception) {}
/*     */           }
/*     */           
/* 261 */           if (key.equals("vw_productor")) {
/*     */             
/*     */             try {
/* 264 */               productor = val;
/* 265 */             } catch (Exception exception) {}
/*     */           }
/*     */           
/* 268 */           if (key.equals("vw_nombre")) {
/*     */             
/*     */             try {
/* 271 */               nombre = val;
/* 272 */             } catch (Exception exception) {}
/*     */           }
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/* 278 */     System.out.println("ses.isValid()" + ses.isValid());
/*     */     
/* 280 */     if (!ses.isValid()) {
/* 281 */       System.out.println("ses.isValid()" + ses.isValid());
/* 282 */       data.setDraw(0);
/* 283 */       data.init();
/*     */       try {
/* 285 */         System.out.println("idEspecie: " + idEspecie);
/* 286 */         ArrayList<String[]> pp = estadoProductorDB.getEstadoProductorA(ses.getIdTemporada(), idEspecie, productor, nombre, Boolean.valueOf(false), "");
/* 287 */         data.setDatas(pp);
/* 288 */         data.setRecordsFiltered(pp.size());
/* 289 */         data.setRecordsTotal(pp.size());
/* 290 */       } catch (Exception e) {
/*     */         
/* 292 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 296 */     System.out.println(data.toString());
/* 297 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/estadoProductor/view2"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable view2(HttpServletRequest request, HttpSession httpSession) {
/* 306 */     dataTable data = new dataTable();
/*     */     
/* 308 */     data.init();
/* 309 */     data.setDraw(0);
/* 310 */     session ses = new session(httpSession);
/*     */ 
/*     */     
/* 313 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/* 314 */     Map<String, String[]> parameters = request.getParameterMap();
/* 315 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 316 */     int idEspecie = 1;
/* 317 */     String productor = "";
/* 318 */     String nombre = "";
/* 319 */     for (String key : parameters.keySet()) {
/*     */       
/* 321 */       if (key.startsWith("vw_")) {
/* 322 */         String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
/* 323 */         for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
/* 324 */           System.out.println(String.valueOf(key) + " -> " + val);
/* 325 */           filterSql fil = new filterSql();
/* 326 */           fil.setCampo(key.substring(3));
/* 327 */           fil.setValue(val);
/* 328 */           filter.add(fil);
/*     */           
/* 330 */           if (key.equals("vw_especie")) {
/*     */             
/*     */             try {
/* 333 */               idEspecie = Integer.parseInt(val);
/* 334 */             } catch (Exception exception) {}
/*     */           }
/*     */           
/* 337 */           if (key.equals("vw_productor")) {
/*     */             
/*     */             try {
/* 340 */               productor = val;
/* 341 */             } catch (Exception exception) {}
/*     */           }
/*     */           
/* 344 */           if (key.equals("vw_nombre")) {
/*     */             
/*     */             try {
/* 347 */               nombre = val;
/* 348 */             } catch (Exception exception) {}
/*     */           }
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/* 354 */     System.out.println("ses.isValid()" + ses.isValid());
/*     */     
/* 356 */     if (!ses.isValid()) {
/*     */       
/* 358 */       data.setDraw(0);
/* 359 */       data.init();
/*     */       try {
/* 361 */         System.out.println("idEspecie: " + idEspecie);
/* 362 */         ArrayList<String[]> pp = estadoProductorDB.getEstadoProductorB(ses.getIdTemporada(), idEspecie, productor, nombre, Boolean.valueOf(false));
/* 363 */         data.setDatas(pp);
/* 364 */         data.setRecordsFiltered(pp.size());
/* 365 */         data.setRecordsTotal(pp.size());
/* 366 */       } catch (Exception e) {
/*     */         
/* 368 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 372 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/estadoProductorPro/view"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable getProduccionPO(HttpServletRequest request, HttpSession httpSession) {
/* 383 */     dataTable data = new dataTable();
/*     */     
/* 385 */     data.init();
/* 386 */     data.setDraw(0);
/* 387 */     session ses = new session(httpSession);
/*     */ 
/*     */     
/* 390 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/* 391 */     Map<String, String[]> parameters = request.getParameterMap();
/* 392 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 393 */     int idEspecie = 1;
/* 394 */     String productor = "";
/* 395 */     String nombre = "";
/* 396 */     for (String key : parameters.keySet()) {
/*     */       
/* 398 */       if (key.startsWith("vw_")) {
/* 399 */         String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
/* 400 */         for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
/* 401 */           System.out.println(String.valueOf(key) + " -> " + val);
/* 402 */           filterSql fil = new filterSql();
/* 403 */           fil.setCampo(key.substring(3));
/* 404 */           fil.setValue(val);
/* 405 */           filter.add(fil);
/*     */           
/* 407 */           if (key.equals("vw_especie")) {
/*     */             
/*     */             try {
/* 410 */               idEspecie = Integer.parseInt(val);
/* 411 */             } catch (Exception exception) {}
/*     */           }
/*     */           
/* 414 */           if (key.equals("vw_productor")) {
/*     */             
/*     */             try {
/* 417 */               productor = val;
/* 418 */             } catch (Exception exception) {}
/*     */           }
/*     */           
/* 421 */           if (key.equals("vw_nombre")) {
/*     */             
/*     */             try {
/* 424 */               nombre = val;
/* 425 */             } catch (Exception exception) {}
/*     */           }
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/* 431 */     System.out.println("ses.isValid()" + ses.isValid());
/*     */     
/* 433 */     if (!ses.isValid()) {
/*     */       
/* 435 */       data.setDraw(0);
/* 436 */       data.init();
/*     */       try {
/* 438 */         System.out.println("idEspecie: " + idEspecie);
/* 439 */         ArrayList<String[]> pp = estadoProductorDB.getEstadoProductorPro(ses.getIdTemporada(), idEspecie, productor, nombre);
/* 440 */         data.setDatas(pp);
/* 441 */         data.setRecordsFiltered(pp.size());
/* 442 */         data.setRecordsTotal(pp.size());
/* 443 */       } catch (Exception e) {
/*     */         
/* 445 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 449 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
	@RequestMapping(value = {"/SyncSAP/{tipo}"}, method = {RequestMethod.GET})
	public void SyncSAP(HttpServletResponse response, @PathVariable("tipo") String tipo, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
  
		if (ses.isValid()) {
			OutputStream ServletOutputStream;
			
			try {
				ServletOutputStream servletOutputStream = response.getOutputStream();
				servletOutputStream.write("session terminada".getBytes(Charset.forName("iso-8859-1")));
				servletOutputStream.close();
			} catch (IOException e) {
  
				e.printStackTrace();
			} 
			return;
		} 

		String tipoServicio = "mercado";
		String cliente="N";
		if (tipo.equals("2"))
		{
			cliente="Y";
			tipoServicio = "cliente";
		}

		JSONArray r = estadoProductorDB.getEstadoProductorSyncSAP(ses.getIdTemporada(), 1, "", cliente, Boolean.valueOf(false));

		String html="";
		
		html=Sync.send(r,tipo,tipoServicio);
		
		for(int i = 0; i < r.length(); i++)
		{
		      JSONObject o = r.getJSONObject(i);
		      Iterator<String> keys = o.keys();
		      while(keys.hasNext()) {
		    	    String key = keys.next();
		    	    Object keyvalue = o.get(key);
		      //html+=key+"-->"+keyvalue+"\n";
		      
		      }
		}
		html+="";
		OutputStream outputStream2;

		try {
			ServletOutputStream servletOutputStream = response.getOutputStream();
/* 544 */       response.setCharacterEncoding("UTF-8");
/*     */ 
/*     */       
/* 547 */       servletOutputStream.write(html.getBytes("UTF-8"));
/* 548 */       servletOutputStream.close();
/* 549 */     } catch (IOException e) {
/*     */       
/* 551 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }