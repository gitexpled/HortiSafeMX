 package lib.data.json;
 
 import java.text.ParseException;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;
 import lib.db.SectorVariedadDB;
 import lib.security.session;
 import lib.struc.SectorVariedad;
 import lib.struc.filterSql;
 import lib.struc.mesajesJson;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestMethod;
 import org.springframework.web.bind.annotation.ResponseBody;
 
 
 
 
 
 
 
 
 
 @Controller
 public class SectorVariedadJson
 {
   @RequestMapping(value = {"/sectorVariedad/view"}, method = {RequestMethod.POST, RequestMethod.GET})
   @ResponseBody
   public dataTable view(HttpServletRequest request, HttpSession httpSession) {
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
			   for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { 
				   String val = arrayOfString1[b];
				   filterSql fil = new filterSql();
				   fil.setCampo(key.substring(3));
				   fil.setValue(val);
				   filter.add(fil);
				   b++; 
				  }
		   } 
     } 
	   data.setDraw(0);
	   data.init();
     
	   int start = Integer.parseInt(((String[])parameters.get("start"))[0]);
	   int length = Integer.parseInt(((String[])parameters.get("length"))[0]);
     
	   try {
		   ArrayList<SectorVariedad> datas = SectorVariedadDB.getAll(filter, "", start, length);
		   Iterator<SectorVariedad> f = datas.iterator();
		   data.setRecordsFiltered(SectorVariedadDB.getAll(filter));
		   data.setRecordsTotal(SectorVariedadDB.getAll(filter));
    	 
		   while (f.hasNext()) {
			   SectorVariedad row = f.next();
			   String[] d = { (new StringBuilder(String.valueOf(row.getId()))).toString(), row.getCodProductor(), row.getCodParcela(), row.getCodTurno(), row.getCodVariedad(),""+row.getCreado(),""+row.getModificado() };
			   data.setData(d);
		   }
	   } catch (Exception e) {
		   e.printStackTrace();
	   } 
	   return data;
   }
 
   
   @RequestMapping(value = {"/sectorVariedad/insert"}, method = {RequestMethod.PUT}, produces = {"application/json"})
   @ResponseBody
   public boolean insert(@RequestBody SectorVariedad row, HttpSession httpSession) throws ParseException {
	   boolean resp = false;
	   session ses = new session(httpSession);
	   if (ses.isValid()) {
		   return resp;
     }
	   resp = SectorVariedadDB.insert(row);
	   return resp;
   }
   
   @RequestMapping(value = {"/sectorVariedad/{codigo}"}, method = {RequestMethod.GET})
   @ResponseBody
   public SectorVariedad getById(@PathVariable String codigo, HttpSession httpSession) throws Exception {
/* 111 */     session ses = new session(httpSession);
     
/* 113 */     if (ses.isValid()) {
/* 114 */       SectorVariedad sectorVariedad = null;
/* 115 */       return sectorVariedad;
     } 
     
/* 118 */     SectorVariedad row = SectorVariedadDB.get(codigo);
     
/* 120 */     return row;
   }
   @RequestMapping(value = {"/sectorVariedad/drop"}, method = {RequestMethod.GET}, produces = {"application/json"})
   @ResponseBody
   public mesajesJson drop(HttpServletRequest request, HttpSession httpSession) throws Exception {
/* 125 */     String id = request.getParameter("id");
/* 126 */     session ses = new session(httpSession);
/* 127 */     boolean estado = false;
/* 128 */     mesajesJson msn = new mesajesJson();
/* 129 */     if (ses.isValid()) {
       
/* 131 */       msn.setEstado("NOK");
/* 132 */       msn.setMensaje("usuario invalido");
/* 133 */       return msn;
     } 
     
/* 136 */     SectorVariedadDB.delete(id);
     
/* 138 */     msn.setEstado("OK");
/* 139 */     msn.setMensaje("Usuario Modificado");
/* 140 */     return msn;
   }
 
   
   @RequestMapping(value = {"/sectorVariedad/put"}, method = {RequestMethod.PUT}, produces = {"application/json"})
   @ResponseBody
   public mesajesJson uodate(@RequestBody SectorVariedad row, HttpSession httpSession) throws Exception {
/* 147 */     session ses = new session(httpSession);
/* 148 */     mesajesJson mensaje = new mesajesJson();
/* 149 */     if (ses.isValid()) {
/* 150 */       mensaje.setEstado("error");
/* 151 */       mensaje.setMensaje("Session terminada");
/* 152 */       return mensaje;
     } 
/* 154 */     System.out.println("PUT::::::::::::::::::::::::::::");
/* 155 */     System.out.println("INSERTO");
/* 156 */     SectorVariedadDB.update(row);
 
     
/* 159 */     mensaje.setEstado("ok");
/* 160 */     mensaje.setMensaje("Guardado con exito");
     
/* 162 */     return mensaje;
   }
 
   
   @RequestMapping(value = {"/sectorVariedad/getAllOutFilter"}, method = {RequestMethod.GET}, produces = {"application/json"})
   @ResponseBody
   public ArrayList<SectorVariedad> getSelectBox(HttpServletRequest request, HttpSession httpSession) throws Exception {
/* 169 */     session ses = new session(httpSession);
/* 170 */     ArrayList<SectorVariedad> SectorVariedad = new ArrayList<>();
/* 171 */     if (ses.isValid()) {
       
/* 173 */       SectorVariedad = null;
/* 174 */       return SectorVariedad;
     } 
/* 176 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 177 */     SectorVariedad = SectorVariedadDB.getAll(filter, "", 0, 1000);
/* 178 */     return SectorVariedad;
   }
 }