/*     */ package lib.data.json;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import lib.db.VariedadDB;
/*     */ import lib.security.session;
/*     */ import lib.struc.ProductorVariedad;
/*     */ import lib.struc.Variedad;
/*     */ import lib.struc.filterSql;
/*     */ import org.springframework.stereotype.Controller;
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
/*     */ public class VariedadJson
/*     */ {
/*     */   @RequestMapping(value = {"/variedad/getAllOutFilter"}, method = {RequestMethod.GET}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public ArrayList<Variedad> getSelectBox(HttpServletRequest request, HttpSession httpSession) throws Exception {
/*  32 */     session ses = new session(httpSession);
/*  33 */     ArrayList<Variedad> Variedades = new ArrayList<>();
/*  34 */     if (ses.isValid()) {
/*     */       
/*  36 */       Variedades = null;
/*  37 */       return Variedades;
/*     */     } 
/*  39 */     ArrayList<filterSql> filter = new ArrayList<>();
/*  40 */     Variedades = VariedadDB.getAll(filter, "", 0, 1000);
/*  41 */     return Variedades;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/variedadM/viewSame"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable variedadM(HttpServletRequest request, HttpSession httpSession) {
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
/*  86 */       ArrayList<Variedad> datas = VariedadDB.getVariedadM(filter, "", start, length);
/*     */       
/*  88 */       Iterator<Variedad> f = datas.iterator();
/*     */       
/*  90 */       data.setRecordsFiltered(VariedadDB.getVariedadAll(filter));
/*  91 */       data.setRecordsTotal(VariedadDB.getVariedadAll(filter));
/*     */       
/*  93 */       while (f.hasNext()) {
/*  94 */         Variedad row = f.next();
/*  95 */         String[] d = { row.getEspecie(), row.getCod(), row.getNombre(), (new StringBuilder(String.valueOf(row.getIdVariedad()))).toString() };
/*     */         
/*  97 */         data.setData(d);
/*     */       }
/*     */     
/* 100 */     } catch (Exception e) {
/*     */       
/* 102 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 105 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/ProductorVariedad/viewSame"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable ProductorVariedad(HttpServletRequest request, HttpSession httpSession) {
/* 115 */     session ses = new session(httpSession);
/* 116 */     dataTable data = new dataTable();
/* 117 */     if (ses.isValid()) {
/*     */       
/* 119 */       data.setDraw(0);
/* 120 */       data.init();
/* 121 */       return data;
/*     */     } 
/*     */     
/* 124 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/* 125 */     Map<String, String[]> parameters = request.getParameterMap();
/* 126 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 127 */     for (String key : parameters.keySet()) {
/*     */       
/* 129 */       if (key.startsWith("vw_")) {
/* 130 */         String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
/* 131 */         for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
/* 132 */           System.out.println(String.valueOf(key) + " -> " + val);
/* 133 */           filterSql fil = new filterSql();
/* 134 */           fil.setCampo(key.substring(3));
/* 135 */           fil.setValue(val);
/* 136 */           filter.add(fil);
/*     */           
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/* 142 */     data.setDraw(0);
/* 143 */     data.init();
/*     */     
/* 145 */     int start = Integer.parseInt(((String[])parameters.get("start"))[0]);
/* 146 */     int length = Integer.parseInt(((String[])parameters.get("length"))[0]);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 151 */       ArrayList<ProductorVariedad> datas = VariedadDB.getProductorVariedadM(filter, "", start, length);
/*     */       
/* 153 */       Iterator<ProductorVariedad> f = datas.iterator();
/*     */       
/* 155 */       data.setRecordsFiltered(VariedadDB.getVariedadProductorAll(filter));
/* 156 */       data.setRecordsTotal(VariedadDB.getVariedadProductorAll(filter));
/*     */       
/* 158 */       while (f.hasNext()) {
/* 159 */         ProductorVariedad row = f.next();
/* 160 */         String[] d = { (new StringBuilder(String.valueOf(row.getIdProductor()))).toString(), row.getProductor(), (new StringBuilder(String.valueOf(row.getEspecie()))).toString(), row.getVariedades(), (new StringBuilder(String.valueOf(row.getIdEspecie()))).toString() };
/*     */         
/* 162 */         data.setData(d);
/*     */       }
/*     */     
/* 165 */     } catch (Exception e) {
/*     */       
/* 167 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 170 */     return data;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\json\VariedadJson.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */