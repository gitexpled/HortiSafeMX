/*    */ package lib.struc;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ 
/*    */ 
/*    */ public class mail
/*    */ {
/*    */   int idMail;
/*    */   String fechaRecibido;
/*    */   String fechaCarga;
/*    */   String asunto;
/*    */   String archivo;
/*    */   String laboratorio;
/*    */   private String estado;
/*    */   InputStream file;
/*    */   
/*    */   public String getLaboratorio() {
/* 18 */     return this.laboratorio;
/*    */   }
/*    */   public void setLaboratorio(String laboratorio) {
/* 21 */     this.laboratorio = laboratorio;
/*    */   }
/*    */   public InputStream getFile() {
/* 24 */     return this.file;
/*    */   }
/*    */   public void setFile(InputStream file) {
/* 27 */     this.file = file;
/*    */   }
/*    */   public int getIdMail() {
/* 30 */     return this.idMail;
/*    */   }
/*    */   public void setIdMail(int idGetMail) {
/* 33 */     this.idMail = idGetMail;
/*    */   }
/*    */   public String getFechaRecibido() {
/* 36 */     return this.fechaRecibido;
/*    */   }
/*    */   public void setFechaRecibido(String fechaRecibido) {
/* 39 */     this.fechaRecibido = fechaRecibido;
/*    */   }
/*    */   public String getFechaCarga() {
/* 42 */     return this.fechaCarga;
/*    */   }
/*    */   public void setFechaCarga(String fechaCarga) {
/* 45 */     this.fechaCarga = fechaCarga;
/*    */   }
/*    */   public String getAsunto() {
/* 48 */     return this.asunto;
/*    */   }
/*    */   public void setAsunto(String asunto) {
/* 51 */     this.asunto = asunto;
/*    */   }
/*    */   public String getArchivo() {
/* 54 */     return this.archivo;
/*    */   }
/*    */   public void setArchivo(String archivo) {
/* 57 */     this.archivo = archivo;
/*    */   }
/*    */   public String getEstado() {
/* 60 */     return this.estado;
/*    */   }
/*    */   public void setEstado(String estado) {
/* 63 */     this.estado = estado;
/*    */   }
/*    */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\struc\mail.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */