/*    */ package lib.data.json;
/*    */ 
/*    */ import javax.servlet.http.HttpSession;
/*    */ import lib.db.generalDB;
/*    */ import lib.security.session;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.web.bind.annotation.CrossOrigin;
/*    */ import org.springframework.web.bind.annotation.RequestBody;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ import org.springframework.web.bind.annotation.RequestMethod;
/*    */ import org.springframework.web.bind.annotation.ResponseBody;
/*    */ 
/*    */ 
/*    */ @Controller
/*    */ public class generalServices
/*    */ {
/*    */   @RequestMapping(value = {"/general/insert"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*    */   @ResponseBody
/*    */   public String MANTENEDOR(@RequestBody String row, HttpSession httpSession) throws Exception {
/* 20 */     session ses = new session(httpSession);
/* 21 */     if (ses.isValid()) {
/* 22 */       return "";
/*    */     }
/* 24 */     String r = generalDB.Insert(row);
/* 25 */     return r;
/*    */   } @RequestMapping(value = {"/general/update"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*    */   @ResponseBody
/*    */   public String UPDATE(@RequestBody String row, HttpSession httpSession) throws Exception {
/* 29 */     session ses = new session(httpSession);
/* 30 */     if (ses.isValid()) {
/* 31 */       return "";
/*    */     }
/* 33 */     String r = generalDB.Update(row);
/* 34 */     return r;
/*    */   } @RequestMapping(value = {"/general/delete"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*    */   @ResponseBody
/*    */   public String DELETE(@RequestBody String row, HttpSession httpSession) throws Exception {
/* 38 */     session ses = new session(httpSession);
/* 39 */     if (ses.isValid()) {
/* 40 */       return "";
/*    */     }
/* 42 */     String r = generalDB.Delete(row);
/* 43 */     return r;
/*    */   } @RequestMapping(value = {"/general/select"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*    */   @ResponseBody
/*    */   public String SELECT(@RequestBody String row, HttpSession httpSession) throws Exception {
/* 47 */     session ses = new session(httpSession);
/*    */ 
/*    */ 
/*    */     
/* 51 */     String r = generalDB.Select(row);
/* 52 */     return r;
/*    */   } @RequestMapping(value = {"/general/execSp"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*    */   @ResponseBody
/*    */   public String RECOTEC(@RequestBody String row, HttpSession httpSession) throws Exception {
/* 56 */     session ses = new session(httpSession);
/* 57 */     String r = "";
/* 58 */     if (ses.isValid()) {
/* 59 */       return "0";
/*    */     }
/* 61 */     r = generalDB.EXECSP(row);
/* 62 */     return r;
/*    */   } @RequestMapping(value = {"/general/callSp"}, method = {RequestMethod.PUT}, produces = {"application/json"})
/*    */   @CrossOrigin(origins = {"*"})
/*    */   @ResponseBody
/*    */   public String CALLSP(@RequestBody String row, HttpSession httpSession) throws Exception {
/* 67 */     String r = generalDB.CallSp(row);
/* 68 */     return r;
/*    */   }
/*    */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\data\json\generalServices.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */