/*     */ package lib.data.json;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import lib.db.SectorDB;
/*     */ import lib.security.session;
/*     */ import lib.struc.Sector;
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
/*     */ 
/*     */ @Controller
/*     */ public class SectorJson
/*     */ {
/*     */   @RequestMapping(value = {"/sector/drop"}, method = {RequestMethod.GET}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public mesajesJson drop(HttpServletRequest request, HttpSession httpSession) throws Exception {
/*  35 */     String id = request.getParameter("id");
/*  36 */     session ses = new session(httpSession);
/*  37 */     mesajesJson msn = new mesajesJson();
/*  38 */     if (ses.isValid()) {
/*     */       
/*  40 */       msn.setEstado("NOK");
/*  41 */       msn.setMensaje("usuario invalido");
/*  42 */       return msn;
/*     */     } 
/*     */     
/*  45 */     String mensaje = SectorDB.delete(id);
/*     */     
/*  47 */     msn.setEstado("OK");
/*  48 */     msn.setMensaje(mensaje);
/*  49 */     return msn;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/sector/view"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable view(HttpServletRequest request, HttpSession httpSession) {
/*  57 */     session ses = new session(httpSession);
/*  58 */     dataTable data = new dataTable();
/*  59 */     if (ses.isValid()) {
/*     */       
/*  61 */       data.setDraw(0);
/*  62 */       data.init();
/*  63 */       return data;
/*     */     } 
/*     */     
/*  66 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/*  67 */     Map<String, String[]> parameters = request.getParameterMap();
/*  68 */     ArrayList<filterSql> filter = new ArrayList<>();
/*  69 */     for (String key : parameters.keySet()) {
/*     */       
/*  71 */       if (key.startsWith("vw_")) {
/*  72 */         String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
/*  73 */         for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
/*  74 */           filterSql fil = new filterSql();
/*  75 */           fil.setCampo(key.substring(3));
/*  76 */           fil.setValue(val);
/*  77 */           filter.add(fil);
/*     */           
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/*  83 */     data.setDraw(0);
/*  84 */     data.init();
/*     */     
/*  86 */     int start = Integer.parseInt(((String[])parameters.get("start"))[0]);
/*  87 */     int length = Integer.parseInt(((String[])parameters.get("length"))[0]);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  92 */       ArrayList<Sector> datas = SectorDB.getAll(filter, "", start, length);
/*     */       
/*  94 */       Iterator<Sector> f = datas.iterator();
/*     */       
/*  96 */       data.setRecordsFiltered(SectorDB.getAll(filter));
/*  97 */       data.setRecordsTotal(SectorDB.getAll(filter));
/*     */       
/*  99 */       while (f.hasNext()) {
/* 100 */         Sector row = f.next();
/* 101 */         String fsma = "NO";
/* 102 */         String habilitado = "NO";
/*     */         
/* 104 */         String[] d = { (new StringBuilder(String.valueOf(row.getIdTurno()))).toString(), row.getCodProductor(), row.getCodParcela(), row.getCodTurno(),""+row.getCreado(),""+row.getModificado() };
/*     */         
/* 106 */         data.setData(d);
/*     */       }
/*     */     
/* 109 */     } catch (Exception e) {
/*     */       
/* 111 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 114 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/sector/insert"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public boolean insert(@RequestBody Sector row, HttpSession httpSession) throws ParseException {
/* 121 */     boolean resp = false;
/* 122 */     session ses = new session(httpSession);
/* 123 */     if (ses.isValid()) {
/* 124 */       return resp;
/*     */     }
/* 126 */     resp = SectorDB.insert(row);
/* 127 */     return resp;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/sector/{codigo}"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public Sector getById(@PathVariable String codigo, HttpSession httpSession) throws Exception {
/* 133 */     session ses = new session(httpSession);
/*     */     
/* 135 */     if (ses.isValid()) {
/* 136 */       Sector sector = null;
/* 137 */       return sector;
/*     */     } 
/*     */     
/* 140 */     Sector row = SectorDB.get(codigo);
/*     */     
/* 142 */     return row;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/sector/put"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public mesajesJson uodate(@RequestBody Sector row, HttpSession httpSession) throws Exception {
/* 149 */     session ses = new session(httpSession);
/* 150 */     mesajesJson mensaje = new mesajesJson();
/* 151 */     if (ses.isValid()) {
/* 152 */       mensaje.setEstado("error");
/* 153 */       mensaje.setMensaje("Session terminada");
/* 154 */       return mensaje;
/*     */     } 
/* 156 */     System.out.println("PUT::::::::::::::::::::::::::::");
/*     */     
/* 158 */     SectorDB.update(row);
/*     */ 
/*     */     
/* 161 */     mensaje.setEstado("ok");
/* 162 */     mensaje.setMensaje("Guardado con exito");
/*     */     
/* 164 */     return mensaje;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/sector/getAllByParcela/{codProductor}/{codParcela}"}, method = {RequestMethod.GET}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public ArrayList<Sector> getAllByProductor(HttpServletRequest request, HttpSession httpSession, @PathVariable("codProductor") String codProductor, @PathVariable("codParcela") String codParcela) throws Exception {
/* 173 */     session ses = new session(httpSession);
/* 174 */     ArrayList<Sector> Sector = new ArrayList<>();
/* 175 */     if (ses.isValid()) {
/*     */       
/* 177 */       Sector = null;
/* 178 */       return Sector;
/*     */     } 
/* 180 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 181 */     filterSql value = new filterSql();
/* 182 */     value.setCampo("codParcela");
/* 183 */     value.setValue(codParcela);
/* 184 */     filter.add(value);
/*     */     
/* 186 */     filterSql value3 = new filterSql();
/* 187 */     value3.setCampo("codProductor");
/* 188 */     value3.setValue(codProductor);
/* 189 */     filter.add(value3);
/*     */     
/* 191 */     Sector = SectorDB.getAll(filter, "", 0, 1000);
/* 192 */     return Sector;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\json\SectorJson.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */