 package lib.data.json;
 
 import java.text.ParseException;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;
 import lib.db.ProductorDB;
 import lib.db.cargaManualDB;
 import lib.db.especieDB;
 import lib.security.session;
 import lib.struc.Productor;
 import lib.struc.cargaManual;
 import lib.struc.especie;
 import lib.struc.filterSql;
 import lib.struc.mesajesJson;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestMethod;
 import org.springframework.web.bind.annotation.ResponseBody;
 
 @Controller
 public class especieJson {
   @RequestMapping(value = {"/especie/view"}, method = {RequestMethod.POST, RequestMethod.GET})
   @ResponseBody
   public dataTable getView(HttpServletRequest request, HttpSession httpSession) {
/*  30 */     session ses = new session(httpSession);
/*  31 */     dataTable data = new dataTable();
/*  32 */     if (ses.isValid()) {
       
/*  34 */       data.setDraw(0);
/*  35 */       data.init();
/*  36 */       return data;
     } 
     
/*  39 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/*  40 */     Map<String, String[]> parameters = request.getParameterMap();
/*  41 */     ArrayList<filterSql> filter = new ArrayList<>();
/*  42 */     for (String key : parameters.keySet()) {
       
/*  44 */       if (key.startsWith("vw_")) {
/*  45 */         String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
/*  46 */         for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
/*  47 */           System.out.println(String.valueOf(key) + " -> " + val);
/*  48 */           filterSql fil = new filterSql();
/*  49 */           fil.setCampo(key.substring(3));
/*  50 */           fil.setValue(val);
/*  51 */           filter.add(fil);
           
           b++; }
       
       } 
     } 
/*  57 */     data.setDraw(0);
/*  58 */     data.init();
     
/*  60 */     int start = Integer.parseInt(((String[])parameters.get("start"))[0]);
/*  61 */     int length = Integer.parseInt(((String[])parameters.get("length"))[0]);
 
 
     
     try {
/*  66 */       ArrayList<cargaManual> datas = cargaManualDB.getAll(filter, "", start, length);
       
/*  68 */       Iterator<cargaManual> f = datas.iterator();
       
/*  70 */       data.setRecordsFiltered(cargaManualDB.getAllcount(filter));
/*  71 */       data.setRecordsTotal(cargaManualDB.getAllcount(filter));
       
/*  73 */       while (f.hasNext()) {
/*  74 */         cargaManual row = f.next();
/*  75 */         String[] d = { (new StringBuilder(String.valueOf(row.getIdCargaManual()))).toString(), row.getFecha(), row.getLaboratorio(), row.getIdentificador() };
         
/*  77 */         data.setData(d);
       }
     
/*  80 */     } catch (Exception e) {
       
/*  82 */       e.printStackTrace();
     } 
     
/*  85 */     return data;
   }
 
   
   @RequestMapping(value = {"/especie/add"}, method = {RequestMethod.PUT}, produces = {"application/json"})
   @ResponseBody
   public boolean add(@RequestBody cargaManual row, HttpSession httpSession) throws ParseException {
/*  92 */     boolean resp = false;
/*  93 */     session ses = new session(httpSession);
/*  94 */     if (ses.isValid()) {
/*  95 */       return resp;
     }
/*  97 */     resp = cargaManualDB.insert(row);
 
 
     
/* 101 */     return resp;
   }
   
   @RequestMapping(value = {"/especie/{codigo}"}, method = {RequestMethod.GET})
   @ResponseBody
   public cargaManual getId(@PathVariable String codigo, HttpSession httpSession) throws Exception {
/* 107 */     session ses = new session(httpSession);
     
/* 109 */     if (ses.isValid()) {
/* 110 */       cargaManual cargaManual = null;
/* 111 */       return cargaManual;
     } 
     
/* 114 */     cargaManual row = cargaManualDB.getId(codigo);
     
/* 116 */     return row;
   }
 
   
   @RequestMapping(value = {"/especie/put"}, method = {RequestMethod.PUT}, produces = {"application/json"})
   @ResponseBody
   public mesajesJson put(@RequestBody Productor row, HttpSession httpSession) throws Exception {
/* 123 */     session ses = new session(httpSession);
/* 124 */     mesajesJson mensaje = new mesajesJson();
/* 125 */     if (ses.isValid()) {
/* 126 */       mensaje.setEstado("error");
/* 127 */       mensaje.setMensaje("Session terminada");
/* 128 */       return mensaje;
     } 
/* 130 */     System.out.println("PUT::::::::::::::::::::::::::::");
     
/* 132 */     ProductorDB.updateProductor(row);
 
     
/* 135 */     mensaje.setEstado("ok");
/* 136 */     mensaje.setMensaje("Guardado con exito");
     
/* 138 */     return mensaje;
   }
 
   
   @RequestMapping(value = {"/especie/getAllOutFilter"}, method = {RequestMethod.GET}, produces = {"application/json"})
   @ResponseBody
   public ArrayList<especie> getAllOutFilter(HttpServletRequest request, HttpSession httpSession) throws Exception {
/* 145 */     session ses = new session(httpSession);
/* 146 */     ArrayList<especie> especies = new ArrayList<>();
/* 147 */     if (ses.isValid()) {
       
/* 149 */       especies = null;
/* 150 */       return especies;
     } 
/* 152 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 153 */     especies = especieDB.getAll(filter, "", 0, 1000);
/* 154 */     return especies;
   }
   
   @RequestMapping(value = {"/especie/getEspecie"}, method = {RequestMethod.GET}, produces = {"application/json"})
   @ResponseBody
   public especie getEspecie(HttpServletRequest request, HttpSession httpSession) throws Exception {
/* 160 */     String id = request.getParameter("id");
/* 161 */     session ses = new session(httpSession);
/* 162 */     especie esp = new especie();
/* 163 */     if (ses.isValid()) {
       
/* 165 */       esp = null;
/* 166 */       return esp;
     } 
/* 168 */     esp = especieDB.getId(id);
/* 169 */     return esp;
   }
   @RequestMapping(value = {"/especie/get/{codProductor}/{codParcela}/{codTurno}"}, method = {RequestMethod.GET}, produces = {"application/json"})
   @ResponseBody
   public ArrayList<especie> getAllByProductor(HttpServletRequest request, HttpSession httpSession, @PathVariable("codProductor") String codProductor, @PathVariable("codParcela") String codParcela, @PathVariable("codTurno") String codTurno) throws Exception {
	   session ses = new session(httpSession);
	   ArrayList<especie> especies = new ArrayList<>();
	   if (ses.isValid()) {
		   especies = null;
		   return especies;
	   } 
	   ArrayList<filterSql> filter = new ArrayList<>();
	   filterSql value = new filterSql();
	   value.setCampo("codParcela");
	   value.setValue(codParcela);
	   filter.add(value);
     
	   filterSql value3 = new filterSql();
	   value3.setCampo("codProductor");
	   value3.setValue(codProductor);
	   filter.add(value3);
 
	   especies = especieDB.setSelectBox(filter, "", 0, 1000);
	   return especies;
   }
   
   @RequestMapping(value = {"/especie/get2/{codProductor}/{codParcela}/{codTurno}"}, method = {RequestMethod.GET}, produces = {"application/json"})
   @ResponseBody
   public ArrayList<especie> getAllByProductor2(
		   HttpServletRequest request, 
		   HttpSession httpSession, 
		   @PathVariable("codProductor") String codProductor, 
		   @PathVariable("codParcela") String codParcela, 
		   @PathVariable("codTurno") String codTurno) throws Exception {
	   session ses = new session(httpSession);
	   ArrayList<especie> especies = new ArrayList<>();
	   if (ses.isValid()) {
		   especies = null;
		   return especies;
	   } 
	  
	   especies = especieDB.setSelectBox2(codProductor,codParcela,codTurno);
	   return especies;
   }
 }