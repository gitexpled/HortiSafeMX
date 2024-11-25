/*     */ package lib.data.json.procesos;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import lib.data.json.dataTable;
/*     */ import lib.db.ProductorDB;
/*     */ import lib.db.bloqueadoDB;
/*     */ import lib.security.session;
/*     */ import lib.struc.Productor;
/*     */ import lib.struc.bloqueo;
/*     */ import lib.struc.filterSql;
/*     */ import lib.struc.mesajesJson;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.PathVariable;
/*     */ import org.springframework.web.bind.annotation.RequestBody;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.bind.annotation.ResponseBody;
/*     */ 
/*     */ @Controller
/*     */ public class bloqueoJson
/*     */ {
	@RequestMapping(value = {"/bloqueo/view"}, method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public dataTable getView(HttpServletRequest request, HttpSession httpSession) {
		 session ses = new session(httpSession);
		 dataTable data = new dataTable();
		 if (ses.isValid()) {
			 data.setDraw(0);
			 data.init();
			 return data;
		 } 
  
		 System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
		 Map<String, String[]> parameters = request.getParameterMap();
		 ArrayList<filterSql> filter = new ArrayList<>();
		 for (String key : parameters.keySet()) {
			 if (key.startsWith("vw_")) {
				 String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
				 for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
				 System.out.println(String.valueOf(key) + " -> " + val);
				 filterSql fil = new filterSql();
				 fil.setCampo(key.substring(3));
				 fil.setValue(val);
				 filter.add(fil);
				 b++; }
			 } 
		 } 
		 data.setDraw(0);
		 data.init();
 
		 int start = Integer.parseInt(((String[])parameters.get("start"))[0]);
		 int length = Integer.parseInt(((String[])parameters.get("length"))[0]);

/*     */     try {
/*  66 */       ArrayList<bloqueo> datas = bloqueadoDB.getAll(filter, "", start, length);
/*     */       
/*  68 */       Iterator<bloqueo> f = datas.iterator();
/*     */       
/*  70 */       data.setRecordsFiltered(bloqueadoDB.getAllcount(filter));
/*  71 */       data.setRecordsTotal(bloqueadoDB.getAllcount(filter));
/*     */       
/*  73 */       while (f.hasNext()) {
/*  74 */         bloqueo row = f.next();
/*  75 */         String[] d = { (new StringBuilder(String.valueOf(row.getIdBloqueo()))).toString(), row.getCodProductor(), row.getMercado(), row.getEspecie(), row.getVariedad(), row.getB(), row.getActivo() };
/*     */         
/*  77 */         data.setData(d);
/*     */       }
/*     */     
/*  80 */     } catch (Exception e) {
/*     */       
/*  82 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  85 */     return data;
/*     */   }
/*     */ 
/*     */   
	@RequestMapping(value = {"/bloqueo/add"}, method = {RequestMethod.PUT}, produces = {"application/json"})
	@ResponseBody
	public String add(@RequestBody bloqueo row, HttpSession httpSession) throws ParseException {
		String resp = "";
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return resp;
		}
		row.setIdUsuario(ses.getIdUser());
		row.setIdTemporada(ses.getIdTemporada());
		resp = bloqueadoDB.insert(row);
		
		return resp;
	}
/*     */   
/*     */   @RequestMapping(value = {"/bloqueo/{codigo}"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public bloqueo getId(@PathVariable String codigo, HttpSession httpSession) throws Exception {
/* 107 */     session ses = new session(httpSession);
/*     */     
/* 109 */     if (ses.isValid()) {
/* 110 */       bloqueo bloqueo = null;
/* 111 */       return bloqueo;
/*     */     } 
/*     */     
/* 114 */     bloqueo row = bloqueadoDB.getId(codigo);
/*     */     
/* 116 */     return row;
/*     */   }
/*     */ 
/*     */   
	@RequestMapping(value = {"/bloqueo/put"}, method = {RequestMethod.PUT}, produces = {"application/json"})
	@ResponseBody
	public mesajesJson put(@RequestBody bloqueo row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		mesajesJson mensaje = new mesajesJson();
		if (ses.isValid()) {
			mensaje.setEstado("error");
			mensaje.setMensaje("Session terminada");
			return mensaje;
		} 
		System.out.println("PUT::::::::::::::::::::::::::::idBloqueo: " + row.getIdBloqueo());
		row.setIdUsuario(ses.getIdUser());
		row.setIdTemporada(ses.getIdTemporada());
		bloqueadoDB.update(row);

		mensaje.setEstado("ok");
		mensaje.setMensaje("Guardado con exito");
		return mensaje;
	}
   
/*     */   @RequestMapping(value = {"/bloqueo/getAllOutFilter"}, method = {RequestMethod.GET}, produces = {"application/json"})
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
/* 155 */     productores = ProductorDB.getProductor(filter, "", 0, 1000);
/* 156 */     return productores;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\json\procesos\bloqueoJson.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */