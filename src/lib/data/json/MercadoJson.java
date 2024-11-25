/*     */ package lib.data.json;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import lib.db.MercadoDB;
/*     */ import lib.security.session;
/*     */ import lib.struc.Mercado;
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
/*     */ public class MercadoJson
/*     */ {
/*     */   @RequestMapping(value = {"/mercado/view"}, method = {RequestMethod.POST, RequestMethod.GET})
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
/*  70 */       ArrayList<Mercado> datas = MercadoDB.getMercado(filter, "", start, length);
/*     */       
/*  72 */       Iterator<Mercado> f = datas.iterator();
/*  73 */       data.setRecordsFiltered(MercadoDB.getMercadosAll(filter));
/*  74 */       data.setRecordsTotal(MercadoDB.getMercadosAll(filter));
/*     */       
/*  76 */       while (f.hasNext()) {
/*  77 */         Mercado row = f.next();
/*  78 */         String[] d = { row.getMercado(), row.getMercado2(), row.getPf(), row.getRegla(), row.getCliente(),""+row.getCreado(),""+row.getModificado(), (new StringBuilder(String.valueOf(row.getIdUser()))).toString(), (new StringBuilder(String.valueOf(row.getIdMercado()))).toString() };
/*     */         
/*  80 */         data.setData(d);
/*     */       }
/*     */     
/*  83 */     } catch (Exception e) {
/*     */       
/*  85 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  88 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/mercado/insertMercado"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public boolean insertMercado(@RequestBody Mercado row, HttpSession httpSession) throws ParseException {
/*  95 */     boolean resp = false;
/*  96 */     session ses = new session(httpSession);
/*  97 */     if (ses.isValid()) {
/*  98 */       return resp;
/*     */     }
/* 100 */     resp = MercadoDB.insertMercado(row);
/* 101 */     return resp;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/mercado/{codigo}"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public Mercado getId(@PathVariable String codigo, HttpSession httpSession) throws Exception {
/* 107 */     session ses = new session(httpSession);
/*     */     
/* 109 */     if (ses.isValid()) {
/* 110 */       Mercado mercado = null;
/* 111 */       return mercado;
/*     */     } 
/*     */     
/* 114 */     Mercado row = MercadoDB.getMercado(codigo);
/*     */     
/* 116 */     return row;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/mercado/put"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public mesajesJson update(@RequestBody Mercado row, HttpSession httpSession) throws Exception {
/* 123 */     session ses = new session(httpSession);
/* 124 */     mesajesJson mensaje = new mesajesJson();
/* 125 */     if (ses.isValid()) {
/* 126 */       mensaje.setEstado("error");
/* 127 */       mensaje.setMensaje("Session terminada");
/* 128 */       return mensaje;
/*     */     } 
/* 130 */     System.out.println("PUT::::::::::::::::::::::::::::");
/*     */     
/* 132 */     MercadoDB.updateMercado(row);
/*     */ 
/*     */     
/* 135 */     mensaje.setEstado("ok");
/* 136 */     mensaje.setMensaje("Guardado con exito");
/*     */     
/* 138 */     return mensaje;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/mercado/getAllOutFilter"}, method = {RequestMethod.GET}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public ArrayList<Mercado> getAllOutFilter(HttpServletRequest request, HttpSession httpSession) throws Exception {
/* 144 */     session ses = new session(httpSession);
/* 145 */     ArrayList<Mercado> mercados = new ArrayList<>();
/* 146 */     if (ses.isValid()) {
/*     */       
/* 148 */       mercados = null;
/* 149 */       return mercados;
/*     */     } 
/* 151 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 152 */     mercados = MercadoDB.getMercado(filter, "", 0, 1000);
/* 153 */     return mercados;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/mercado/getAllOutFilter2"}, method = {RequestMethod.GET}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public ArrayList<Mercado> getAllOutFilter2(HttpServletRequest request, HttpSession httpSession) throws Exception {
/* 160 */     session ses = new session(httpSession);
/* 161 */     ArrayList<Mercado> mercados = new ArrayList<>();
/* 162 */     if (ses.isValid()) {
/*     */       
/* 164 */       mercados = null;
/* 165 */       return mercados;
/*     */     } 
/* 167 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 168 */     filterSql fil = new filterSql();
/* 169 */     fil.setCampo("cliente");
/* 170 */     fil.setValue("N");
/* 171 */     filter.add(fil);
/* 172 */     mercados = MercadoDB.getMercado(filter, "", 0, 1000);
/* 173 */     return mercados;
/*     */   }
/*     */   @RequestMapping(value = {"/mercado/soloClientes"}, method = {RequestMethod.GET}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public ArrayList<Mercado> soloClientes(HttpServletRequest request, HttpSession httpSession) throws Exception {
/* 178 */     session ses = new session(httpSession);
/* 179 */     ArrayList<Mercado> mercados = new ArrayList<>();
/* 180 */     if (ses.isValid()) {
/*     */       
/* 182 */       mercados = null;
/* 183 */       return mercados;
/*     */     } 
/* 185 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 186 */     filterSql fil = new filterSql();
/* 187 */     fil.setCampo("cliente");
/* 188 */     fil.setValue("Y");
/* 189 */     filter.add(fil);
/* 190 */     mercados = MercadoDB.getMercado(filter, "", 0, 1000);
/* 191 */     return mercados;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/mercado/validaMercadoName"}, method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public boolean validaMercadoName(HttpServletRequest request, HttpSession httpSession) throws Exception {
/* 198 */     boolean resp = true;
/* 199 */     String mercado = request.getParameter("mercado");
/* 200 */     session ses = new session(httpSession);
/* 201 */     if (ses.isValid()) {
/* 202 */       return resp;
/*     */     }
/* 204 */     if (MercadoDB.getMercadoByName(mercado) != null)
/*     */     {
/* 206 */       resp = false;
/*     */     }
/* 208 */     return resp;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\json\MercadoJson.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */