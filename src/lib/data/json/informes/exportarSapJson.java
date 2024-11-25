/*    */ package lib.data.json.informes;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import lib.data.json.dataTable;
/*    */ import lib.db.exportarSapDB;
/*    */ import lib.security.session;
/*    */ import lib.struc.filterSql;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ import org.springframework.web.bind.annotation.RequestMethod;
/*    */ import org.springframework.web.bind.annotation.ResponseBody;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Controller
/*    */ public class exportarSapJson
/*    */ {
/*    */   @RequestMapping(value = {"/exportarSap/view"}, method = {RequestMethod.POST, RequestMethod.GET})
/*    */   @ResponseBody
/*    */   public dataTable view(HttpServletRequest request, HttpSession httpSession) {
/* 36 */     dataTable data = new dataTable();
/*    */     
/* 38 */     data.init();
/* 39 */     data.setDraw(0);
/* 40 */     session ses = new session(httpSession);
/*    */ 
/*    */     
/* 43 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/* 44 */     Map<String, String[]> parameters = request.getParameterMap();
/* 45 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 46 */     int idEspecie = 1;
/* 47 */     for (String key : parameters.keySet()) {
/*    */       
/* 49 */       if (key.startsWith("vw_")) {
/* 50 */         String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
/* 51 */         for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
/* 52 */           System.out.println(String.valueOf(key) + " -> " + val);
/* 53 */           filterSql fil = new filterSql();
/* 54 */           fil.setCampo(key.substring(3));
/* 55 */           fil.setValue(val);
/* 56 */           filter.add(fil);
/*    */           
/*    */           b++; }
/*    */       
/*    */       } 
/*    */     } 
/*    */     
/* 63 */     System.out.println("ses.isValid()" + ses.isValid());
/* 64 */     System.out.println("asdadasdasd");
/* 65 */     if (!ses.isValid()) {
/*    */       
/* 67 */       data.setDraw(0);
/* 68 */       data.init();
/*    */       
/*    */       try {
/* 71 */         ArrayList<String[]> pp = exportarSapDB.view(ses.getIdTemporada());
/* 72 */         data.setDatas(pp);
/* 73 */         data.setRecordsFiltered(pp.size());
/* 74 */         data.setRecordsTotal(pp.size());
/* 75 */       } catch (Exception e) {
/*    */         
/* 77 */         e.printStackTrace();
/*    */       } 
/*    */     } 
/*    */     
/* 81 */     return data;
/*    */   }
/*    */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\json\informes\exportarSapJson.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */