 package lib.data.json;
 
 import java.text.ParseException;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;
 import lib.db.ParcelaDB;
 import lib.security.session;
 import lib.struc.Parcela;
 import lib.struc.Variedad;
 import lib.struc.filterSql;
 import lib.struc.mesajesJson;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestMethod;
 import org.springframework.web.bind.annotation.ResponseBody;
 
 
 
 
 
 
 
 
 
 
 @Controller
 public class ParcelaJson
 {
   @RequestMapping(value = {"/parcela/view"}, method = {RequestMethod.POST, RequestMethod.GET})
   @ResponseBody
   public dataTable view(HttpServletRequest request, HttpSession httpSession) {
/*  37 */     session ses = new session(httpSession);
/*  38 */     dataTable data = new dataTable();
/*  39 */     if (ses.isValid()) {
       
/*  41 */       data.setDraw(0);
/*  42 */       data.init();
/*  43 */       return data;
     } 
     
/*  46 */     System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
/*  47 */     Map<String, String[]> parameters = request.getParameterMap();
/*  48 */     ArrayList<filterSql> filter = new ArrayList<>();
/*  49 */     for (String key : parameters.keySet()) {
       
/*  51 */       if (key.startsWith("vw_")) {
/*  52 */         String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
/*  53 */         for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
/*  54 */           filterSql fil = new filterSql();
/*  55 */           fil.setCampo(key.substring(3));
/*  56 */           fil.setValue(val);
/*  57 */           filter.add(fil);
           
           b++; }
       
       } 
     } 
/*  63 */     data.setDraw(0);
/*  64 */     data.init();
     
/*  66 */     int start = Integer.parseInt(((String[])parameters.get("start"))[0]);
/*  67 */     int length = Integer.parseInt(((String[])parameters.get("length"))[0]);
 
 
     
     try {
/*  72 */       ArrayList<Parcela> datas = ParcelaDB.getAll(filter, "", start, length);
       
/*  74 */       Iterator<Parcela> f = datas.iterator();
       
/*  76 */       data.setRecordsFiltered(ParcelaDB.getAll(filter));
/*  77 */       data.setRecordsTotal(ParcelaDB.getAll(filter));
       
/*  79 */       while (f.hasNext()) {
/*  80 */         Parcela row = f.next();
/*  81 */         String fsma = "NO";
/*  82 */         String habilitado = "NO";
         
/*  84 */         String[] d = { (new StringBuilder(String.valueOf(row.getCodigoProductor()))).toString(), (new StringBuilder(String.valueOf(row.getCodigo()))).toString(), row.getNombre(),""+row.getCreado(),""+row.getModificado() };
         
/*  86 */         data.setData(d);
       }
     
/*  89 */     } catch (Exception e) {
       
/*  91 */       e.printStackTrace();
     } 
     
/*  94 */     return data;
   }
 
   
   @RequestMapping(value = {"/parcela/insert"}, method = {RequestMethod.PUT}, produces = {"application/json"})
   @ResponseBody
   public boolean insert(@RequestBody Parcela row, HttpSession httpSession) throws ParseException {
/* 101 */     boolean resp = false;
/* 102 */     session ses = new session(httpSession);
/* 103 */     if (ses.isValid()) {
/* 104 */       return resp;
     }
/* 106 */     resp = ParcelaDB.insert(row);
/* 107 */     return resp;
   }
   
   @RequestMapping(value = {"/parcela/{codProductor}/{codParcela}"}, method = {RequestMethod.GET})
   @ResponseBody
   public Parcela getById(@PathVariable String codProductor, @PathVariable String codParcela, HttpSession httpSession) throws Exception {
/* 113 */     session ses = new session(httpSession);
     
/* 115 */     if (ses.isValid()) {
/* 116 */       Parcela parcela = null;
/* 117 */       return parcela;
     } 
     
/* 120 */     Parcela row = ParcelaDB.get(codProductor, codParcela);
     
/* 122 */     return row;
   }
 
   
   @RequestMapping(value = {"/parcela/put"}, method = {RequestMethod.PUT}, produces = {"application/json"})
   @ResponseBody
   public mesajesJson uodate(@RequestBody Parcela row, HttpSession httpSession) throws Exception {
/* 129 */     session ses = new session(httpSession);
/* 130 */     mesajesJson mensaje = new mesajesJson();
/* 131 */     if (ses.isValid()) {
/* 132 */       mensaje.setEstado("error");
/* 133 */       mensaje.setMensaje("Session terminada");
/* 134 */       return mensaje;
     } 
/* 136 */     System.out.println("PUT::::::::::::::::::::::::::::");
     
/* 138 */     ParcelaDB.update(row);
 
     
/* 141 */     mensaje.setEstado("ok");
/* 142 */     mensaje.setMensaje("Guardado con exito");
     
/* 144 */     return mensaje;
   }
 
   
   @RequestMapping(value = {"/parcela/getAllOutFilter"}, method = {RequestMethod.GET}, produces = {"application/json"})
   @ResponseBody
   public ArrayList<Parcela> getSelectBox(HttpServletRequest request, HttpSession httpSession) throws Exception {
/* 151 */     session ses = new session(httpSession);
/* 152 */     ArrayList<Parcela> Parcelaes = new ArrayList<>();
/* 153 */     if (ses.isValid()) {
       
/* 155 */       Parcelaes = null;
/* 156 */       return Parcelaes;
     } 
/* 158 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 159 */     Parcelaes = ParcelaDB.getAll(filter, "", 0, 1000);
/* 160 */     return Parcelaes;
   }
   
   @RequestMapping(value = {"/parcela/getAllByProductor/{codProductor}"}, method = {RequestMethod.GET}, produces = {"application/json"})
   @ResponseBody
   public ArrayList<Parcela> getAllByProductor(HttpServletRequest request, HttpSession httpSession, @PathVariable("codProductor") String codProductor) throws Exception {
/* 166 */     session ses = new session(httpSession);
/* 167 */     ArrayList<Parcela> Parcelaes = new ArrayList<>();
/* 168 */     if (ses.isValid()) {
       
/* 170 */       Parcelaes = null;
/* 171 */       return Parcelaes;
     } 
/* 173 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 174 */     filterSql value = new filterSql();
/* 175 */     value.setCampo("codProductor");
/* 176 */     value.setValue(codProductor);
/* 177 */     filter.add(value);
/* 178 */     Parcelaes = ParcelaDB.getAll(filter, "", 0, 1000);
/* 179 */     return Parcelaes;
   }
   @RequestMapping(value = {"/cargamanual/getAllByVariables3/{codProductor},{parcela_},{especie_},{turno_}"}, method = {RequestMethod.GET}, produces = {"application/json"})
   @ResponseBody
   public ArrayList<Variedad> getAllByVariables3(HttpServletRequest request, HttpSession httpSession,
		   @PathVariable("codProductor") String codProductor,
		   @PathVariable("parcela_") String parcela_,
		   @PathVariable("especie_") String especie_,
		   @PathVariable("turno_") String turno_
		   ) throws Exception {
/* 184 */     session ses = new session(httpSession);
/* 185 */     ArrayList<Variedad> Variedad = new ArrayList<>();
/* 186 */     if (ses.isValid()) {
       
/* 188 */       Variedad = null;
/* 189 */       return Variedad;
     } 

/* 196 */     Variedad = ParcelaDB.getAllByVariables3(codProductor,parcela_,especie_,turno_);
/* 197 */     return Variedad;
   }

 }

