/*     */ package lib.data.json;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import lib.db.userDB;
/*     */ import lib.security.session;
/*     */ import lib.struc.filterSql;
/*     */ import lib.struc.mesajesJson;
/*     */ import lib.struc.user;
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
/*     */ public class userJson
/*     */ {
/*     */   @RequestMapping(value = {"/user/put"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public mesajesJson setUser(@RequestBody user row, HttpSession httpSession) throws Exception {
/*  34 */     session ses = new session(httpSession);
/*  35 */     mesajesJson mensaje = new mesajesJson();
/*  36 */     if (ses.isValid()) {
/*  37 */       mensaje.setEstado("error");
/*  38 */       mensaje.setMensaje("Session terminada");
/*  39 */       return mensaje;
/*     */     } 
/*  41 */     userDB.updateUser(row);
/*     */     
/*  43 */     mensaje.setEstado("ok");
/*  44 */     mensaje.setMensaje("Guardado con exito");
/*     */     
/*  46 */     return mensaje;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/user/{id}"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public user getUserId(@PathVariable int id, HttpSession httpSession) throws Exception {
/*  53 */     session ses = new session(httpSession);
/*     */     
/*  55 */     if (ses.isValid()) {
/*  56 */       user user = null;
/*  57 */       return user;
/*     */     } 
/*     */     
/*  60 */     user row = userDB.getUser(id);
/*     */     
/*  62 */     return row;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/user/view"}, method = {RequestMethod.POST, RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public dataTable getShopInJSON(HttpServletRequest request, HttpSession httpSession) {
/*  69 */     session ses = new session(httpSession);
/*  70 */     dataTable data = new dataTable();
/*  71 */     if (ses.isValid()) {
/*     */       
/*  73 */       data.setDraw(0);
/*  74 */       data.init();
/*  75 */       return data;
/*     */     } 
/*     */     
/*  78 */     Map<String, String[]> parameters = request.getParameterMap();
/*  79 */     ArrayList<filterSql> filter = new ArrayList<>();
/*  80 */     for (String key : parameters.keySet()) {
/*     */       
/*  82 */       if (key.startsWith("vw_")) {
/*  83 */         String[] vals = parameters.get(key); byte b; int i; String[] arrayOfString1;
/*  84 */         for (i = (arrayOfString1 = vals).length, b = 0; b < i; ) { String val = arrayOfString1[b];
/*  85 */           System.out.println(String.valueOf(key) + " -> " + val);
/*  86 */           filterSql fil = new filterSql();
/*  87 */           fil.setCampo(key.substring(3));
/*  88 */           fil.setValue(val);
/*  89 */           filter.add(fil);
/*     */           
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/*  95 */     data.setDraw(0);
/*  96 */     data.init();
/*     */     
/*  98 */     int start = Integer.parseInt(((String[])parameters.get("start"))[0]);
/*  99 */     int length = Integer.parseInt(((String[])parameters.get("length"))[0]);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 104 */       ArrayList<user> datas = userDB.getUsers(filter, "", start, length);
/*     */       
/* 106 */       Iterator<user> f = datas.iterator();
/*     */       
/* 108 */       data.setRecordsFiltered(userDB.getUsersAll(filter));
/* 109 */       data.setRecordsTotal(userDB.getUsersAll(filter));
/*     */       
/* 111 */       while (f.hasNext()) {
/* 112 */         user row = f.next();
/* 113 */         String[] d = { row.getNombre(), row.getApellido(), row.getUser(), row.getMail(), 
/* 114 */             ""+row.getCreacion(),""+row.getBaja(), (new StringBuilder(String.valueOf(row.getEstado()))).toString(), (new StringBuilder(String.valueOf(row.getId()))).toString(), "", "" };
/*     */         
/* 116 */         data.setData(d);
/*     */       }
/*     */     
/* 119 */     } catch (Exception e) {
/*     */       
/* 121 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 124 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/user/insertUser"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public boolean insertUser(@RequestBody user row, HttpSession httpSession) throws ParseException {
/* 131 */     boolean resp = false;
/* 132 */     session ses = new session(httpSession);
/* 133 */     if (ses.isValid()) {
/* 134 */       return resp;
/*     */     }
/* 136 */     resp = userDB.insertUser(row);
/* 137 */     return resp;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/user/validaUserName"}, method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public boolean validaUserName(HttpServletRequest request, HttpSession httpSession) throws Exception {
/* 143 */     boolean resp = true;
/* 144 */     String user = request.getParameter("user");
/* 145 */     session ses = new session(httpSession);
/* 146 */     if (ses.isValid()) {
/* 147 */       return resp;
/*     */     }
/* 149 */     if (userDB.getUserByUser(user) != null)
/*     */     {
/* 151 */       resp = false;
/*     */     }
/* 153 */     return resp;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/user/setStatus"}, method = {RequestMethod.GET}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public mesajesJson setStatus(HttpServletRequest request, HttpSession httpSession) throws NumberFormatException, Exception {
/* 159 */     String id = request.getParameter("id");
/* 160 */     mesajesJson msn = new mesajesJson();
/* 161 */     session ses = new session(httpSession);
/* 162 */     if (ses.isValid()) {
/* 163 */       msn.setEstado("NOK");
/* 164 */       msn.setMensaje("usuario invalido");
/* 165 */       return msn;
/*     */     } 
/* 167 */     user us = userDB.getUser(Integer.parseInt(id));
/* 168 */     if (us.getEstado() == 0) {
/*     */       
/* 170 */       us.setEstado(1);
/*     */     } else {
/*     */       
/* 173 */       us.setEstado(0);
/*     */     } 
/* 175 */     userDB.updateUser(us);
/* 176 */     msn.setEstado("OK");
/* 177 */     msn.setMensaje("Usuario Modificado");
/* 178 */     return msn;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/user/getByUserPass"}, method = {RequestMethod.GET}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public user getUserByPass(HttpServletRequest request, HttpSession httpSession) throws Exception {
/* 185 */     String user = request.getParameter("user");
/* 186 */     String pass = request.getParameter("pass");
/* 187 */     user us = userDB.getUserLogin(user, pass);
/* 188 */     return us;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/user/getUsers"}, method = {RequestMethod.GET}, produces = {"application/json"})
/*     */   @ResponseBody
/*     */   public ArrayList<user> getUsers(HttpServletRequest request, HttpSession httpSession) throws Exception {
/* 194 */     ArrayList<user> users = null;
/* 195 */     session ses = new session(httpSession);
/* 196 */     if (ses.isValid()) {
/* 197 */       return users;
/*     */     }
/* 199 */     ArrayList<filterSql> filter = new ArrayList<>();
/* 200 */     users = userDB.getAllUsers();
/* 201 */     return users;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\jso\\userJson.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */