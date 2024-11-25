/*    */ package lib.struc;
/*    */ 
/*    */ import java.util.Date;
/*    */ 
/*    */ public class Certificacion
/*    */ {
/*    */   private int idCertificaciones;
/*    */   private int idUser;
/*    */   private String certificacionesCol;
/*    */   private String prefijo;
/*    */   private Date creado;
/*    */   private Date modificado;
/*    */   
/*    */   public int getIdCertificaciones() {
/* 15 */     return this.idCertificaciones;
/*    */   }
/*    */   public void setIdCertificaciones(int idCertificaciones) {
/* 18 */     this.idCertificaciones = idCertificaciones;
/*    */   }
/*    */   public int getIdUser() {
/* 21 */     return this.idUser;
/*    */   }
/*    */   public void setIdUser(int idUser) {
/* 24 */     this.idUser = idUser;
/*    */   }
/*    */   public String getCertificacionesCol() {
/* 27 */     return this.certificacionesCol;
/*    */   }
/*    */   public void setCertificacionesCol(String certificacionesCol) {
/* 30 */     this.certificacionesCol = certificacionesCol;
/*    */   }
/*    */   public Date getCreado() {
/* 33 */     return this.creado;
/*    */   }
/*    */   public void setCreado(Date creado) {
/* 36 */     this.creado = creado;
/*    */   }
/*    */   public Date getModificado() {
/* 39 */     return this.modificado;
/*    */   }
/*    */   public void setModificado(Date modificado) {
/* 42 */     this.modificado = modificado;
/*    */   }
/*    */   public String getPrefijo() {
/* 45 */     return this.prefijo;
/*    */   }
/*    */   public void setPrefijo(String prefijo) {
/* 48 */     this.prefijo = prefijo;
/*    */   }
/*    */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\struc\Certificacion.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */