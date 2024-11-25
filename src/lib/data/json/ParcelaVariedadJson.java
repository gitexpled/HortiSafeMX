/*     */ package lib.data.json;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import lib.db.ParcelaVariedadDB;
/*     */ import lib.security.session;
/*     */ import lib.struc.ParcelaVariedad;
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
/*     */ public class ParcelaVariedadJson
/*     */ {
/*     */   @RequestMapping(value = {"/parcelaVariedad/view"}, method = {RequestMethod.POST, RequestMethod.GET})
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
/*  70 */       ArrayList<ParcelaVariedad> datas = ParcelaVariedadDB.getAll(filter, "", start, length);
/*     */       
/*  72 */       Iterator<ParcelaVariedad> f = datas.iterator();
/*     */       
/*  74 */       data.setRecordsFiltered(ParcelaVariedadDB.getAll(filter));
/*  75 */       data.setRecordsTotal(ParcelaVariedadDB.getAll(filter));
/*     */       
/*  77 */       while (f.hasNext()) {
/*  78 */         ParcelaVariedad row = f.next();
/*     */ 
/*     */         
/*  81 */         String[] d = { (new StringBuilder(String.valueOf(row.getId()))).toString(), row.getCodProductor(), row.getCodParcela(), row.getCodEspecie(),""+row.getCreado(),""+row.getModificado() };
/*     */         
/*  83 */         data.setData(d);
/*     */       }
/*     */     
/*  86 */     } catch (Exception e) {
/*     */       
/*  88 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  91 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/parcelaVariedad/insert"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public boolean insert(@RequestBody ParcelaVariedad row, HttpSession httpSession) throws ParseException {
/*  98 */     boolean resp = false;
/*  99 */     session ses = new session(httpSession);
/* 100 */     if (ses.isValid()) {
/* 101 */       return resp;
/*     */     }
/* 103 */     resp = ParcelaVariedadDB.insert(row);
/* 104 */     return resp;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/parcelaVariedad/{codigo}"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public ParcelaVariedad getById(@PathVariable String codigo, HttpSession httpSession) throws Exception {
/* 110 */     session ses = new session(httpSession);
/*     */     
/* 112 */     if (ses.isValid()) {
/* 113 */       ParcelaVariedad parcelaVariedad = null;
/* 114 */       return parcelaVariedad;
/*     */     } 
/*     */     
/* 117 */     ParcelaVariedad row = ParcelaVariedadDB.get(codigo);
/*     */     
/* 119 */     return row;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/parcelaVariedad/put"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public mesajesJson uodate(@RequestBody ParcelaVariedad row, HttpSession httpSession) throws Exception {
/* 126 */     session ses = new session(httpSession);
/* 127 */     mesajesJson mensaje = new mesajesJson();
/* 128 */     if (ses.isValid()) {
/* 129 */       mensaje.setEstado("error");
/* 130 */       mensaje.setMensaje("Session terminada");
/* 131 */       return mensaje;
/*     */     } 
/* 133 */     System.out.println("PUT::::::::::::::::::::::::::::");
/* 134 */     System.out.println("INSERTO");
/* 135 */     ParcelaVariedadDB.update(row);
/*     */ 
/*     */     
/* 138 */     mensaje.setEstado("ok");
/* 139 */     mensaje.setMensaje("Guardado con exito");
/*     */     
/* 141 */     return mensaje;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/parcelaVariedad/getAllOutFilter"}, method = {RequestMethod.GET}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public ArrayList<ParcelaVariedad> getSelectBox(HttpServletRequest request, HttpSession httpSession) throws Exception {
/* 148 */     session ses = new session(httpSession);
/* 149 */     ArrayList<ParcelaVariedad> parcelaVariedades = new ArrayList<>();
/* 150 */     if (ses.isValid()) {
/*     */       
/* 152 */       parcelaVariedades = null;
/* 153 */       return parcelaVariedades;
/*     */     } 
/* 155 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 156 */     parcelaVariedades = ParcelaVariedadDB.getAll(filter, "", 0, 1000);
/* 157 */     return parcelaVariedades;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\json\ParcelaVariedadJson.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */