/*     */ package lib.data.json;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import lib.db.TurnoDB;
/*     */ import lib.security.session;
/*     */ import lib.struc.Turno;
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
/*     */ public class TurnoJson
/*     */ {
/*     */   @RequestMapping(value = {"/turno/view"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable view(HttpServletRequest request, HttpSession httpSession) {
/*  35 */     session ses = new session(httpSession);
/*  36 */     dataTable data = new dataTable();
/*  37 */     if (ses.isValid()) {
/*     */       
/*  39 */       data.setDraw(0);
/*  40 */       data.init();
/*  41 */       return data;
/*     */     } 
/*     */     
/*  44 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/*  45 */     Map<String, String[]> parameters = request.getParameterMap();
/*  46 */     ArrayList<filterSql> filter = new ArrayList<>();
/*  47 */     for (String key : parameters.keySet()) {
/*     */       
/*  49 */       if (key.startsWith("vw_")) {
/*  50 */         String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
/*  51 */         for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
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
/*  70 */       ArrayList<Turno> datas = TurnoDB.getAll(filter, "", start, length);
/*     */       
/*  72 */       Iterator<Turno> f = datas.iterator();
/*     */       
/*  74 */       data.setRecordsFiltered(TurnoDB.getAll(filter));
/*  75 */       data.setRecordsTotal(TurnoDB.getAll(filter));
/*     */       
/*  77 */       while (f.hasNext()) {
/*  78 */         Turno row = f.next();
/*  79 */         String fsma = "NO";
/*  80 */         String habilitado = "NO";
/*     */         
/*  82 */         String[] d = { (new StringBuilder(String.valueOf(row.getIdTurno()))).toString(), row.getCodProductor(), row.getCodParcela(), row.getCodTurno(),""+row.getCreado(),""+row.getModificado() };
/*     */         
/*  84 */         data.setData(d);
/*     */       }
/*     */     
/*  87 */     } catch (Exception e) {
/*     */       
/*  89 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  92 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/turno/insert"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public boolean insert(@RequestBody Turno row, HttpSession httpSession) throws ParseException {
/*  99 */     boolean resp = false;
/* 100 */     session ses = new session(httpSession);
/* 101 */     if (ses.isValid()) {
/* 102 */       return resp;
/*     */     }
/* 104 */     resp = TurnoDB.insert(row);
/* 105 */     return resp;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/turno/{codigo}"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public Turno getById(@PathVariable String codigo, HttpSession httpSession) throws Exception {
/* 111 */     session ses = new session(httpSession);
/*     */     
/* 113 */     if (ses.isValid()) {
/* 114 */       Turno turno = null;
/* 115 */       return turno;
/*     */     } 
/*     */     
/* 118 */     Turno row = TurnoDB.get(codigo);
/*     */     
/* 120 */     return row;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/turno/put"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public mesajesJson uodate(@RequestBody Turno row, HttpSession httpSession) throws Exception {
/* 127 */     session ses = new session(httpSession);
/* 128 */     mesajesJson mensaje = new mesajesJson();
/* 129 */     if (ses.isValid()) {
/* 130 */       mensaje.setEstado("error");
/* 131 */       mensaje.setMensaje("Session terminada");
/* 132 */       return mensaje;
/*     */     } 
/* 134 */     System.out.println("PUT::::::::::::::::::::::::::::");
/*     */     
/* 136 */     TurnoDB.update(row);
/*     */ 
/*     */     
/* 139 */     mensaje.setEstado("ok");
/* 140 */     mensaje.setMensaje("Guardado con exito");
/*     */     
/* 142 */     return mensaje;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/turno/getAllByParcela/{codProductor}/{codParcela}"}, method = {RequestMethod.GET}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public ArrayList<Turno> getAllByProductor(HttpServletRequest request, HttpSession httpSession, @PathVariable("codProductor") String codProductor, @PathVariable("codParcela") String codParcela) throws Exception {
/* 151 */     session ses = new session(httpSession);
/* 152 */     ArrayList<Turno> turnos = new ArrayList<>();
/* 153 */     if (ses.isValid()) {
/*     */       
/* 155 */       turnos = null;
/* 156 */       return turnos;
/*     */     } 
/* 158 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 159 */     filterSql value = new filterSql();
/* 160 */     value.setCampo("codParcela");
/* 161 */     value.setValue(codParcela);
/* 162 */     filter.add(value);
/*     */     
/* 164 */     filterSql value3 = new filterSql();
/* 165 */     value3.setCampo("codProductor");
/* 166 */     value3.setValue(codProductor);
/* 167 */     filter.add(value3);
/*     */     
/* 169 */     turnos = TurnoDB.getAll(filter, "", 0, 1000);
/* 170 */     return turnos;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\json\TurnoJson.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */