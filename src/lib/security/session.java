/*     */ package lib.security;
/*     */ 
/*     */ import javax.servlet.http.HttpSession;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class session
/*     */ {
/*     */   private HttpSession session;
/*     */   private int idUser;
/*     */   private int idPerfil;
/*     */   private String nombre;
/*     */   private int idTemporada;
/*     */   private String temporada;
/*     */   
/*     */   public int getIdUser() {
/*  18 */     int value = ((Integer)this.session.getAttribute("idUser")).intValue();
/*  19 */     return value;
/*     */   }
/*     */   
/*     */   public void setIdUser(int value) {
/*  23 */     this.session.setAttribute("idUser", Integer.valueOf(value));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIdPerfil() {
/*  28 */     int value = ((Integer)this.session.getAttribute("idPerfil")).intValue();
/*  29 */     return value;
/*     */   }
/*     */   
/*     */   public void setIdPerfil(int value) {
/*  33 */     this.session.setAttribute("idPerfil", Integer.valueOf(value));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNombre() {
/*  38 */     String value = (String)this.session.getAttribute("nombre");
/*  39 */     return value;
/*     */   }
/*     */   
/*     */   public void setNombre(String value) {
/*  43 */     this.session.setAttribute("nombre", value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIdTemporada() {
/*  50 */     int idTemporada = ((Integer)this.session.getAttribute("idTemporada")).intValue();
/*  51 */     return idTemporada;
/*     */   }
/*     */   public void setIdTemporada(int idTemporada) {
/*  54 */     this.session.setAttribute("idTemporada", Integer.valueOf(idTemporada));
/*     */   }
/*     */   
/*     */   public String getTemporada() {
/*  58 */     String value = (String)this.session.getAttribute("temporada");
/*  59 */     return value;
/*     */   }
/*     */   public void setTemporada(String temporada) {
/*  62 */     this.session.setAttribute("temporada", temporada);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public session(HttpSession httpSession) {
/*  69 */     this.session = httpSession;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  74 */     this.session.setMaxInactiveInterval(1800);
/*  75 */     this.session.setAttribute("login", "ok");
/*     */   }
/*     */   
/*     */   public void setValue(String key, String value) {
/*  79 */     this.session.setAttribute(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValue(String key) {
/*  85 */     String value = (String)this.session.getAttribute(key);
/*  86 */     if (value == null) {
/*  87 */       value = "";
/*     */     }
/*     */ 
/*     */     
/*  91 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpSession getSession() {
/*  99 */     return this.session;
/*     */   }
/*     */   public void setSession(HttpSession httpSession) {
/* 102 */     this.session = httpSession;
/*     */   }
/*     */   
/*     */   public boolean isValid() {
/* 106 */     boolean sw = true;
/* 107 */     String login = (String)this.session.getAttribute("login");
/*     */     
/* 109 */     if (login != null) {
/* 110 */       sw = false;
/*     */     }
/*     */ 
/*     */     
/* 114 */     return sw;
/*     */   }
/*     */   
/*     */   public void close() {
/* 118 */     this.session.invalidate();
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\security\session.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */