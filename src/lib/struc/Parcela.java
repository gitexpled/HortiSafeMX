/*    */ package lib.struc;
/*    */ 
/*    */ import java.util.Date;
/*    */ 
/*    */ 
/*    */ public class Parcela
/*    */ {
/*    */   private String codigo;
/*    */   private String codigoProductor;
/*    */   private String nombre;
/*    */   
/*    */   public String getCodigo() {
/* 13 */     return this.codigo;
/*    */   } private Date creado; private Date modificado; private int idUser;
/*    */   public void setCodigo(String codigo) {
/* 16 */     this.codigo = codigo;
/*    */   }
/*    */   public String getCodigoProductor() {
/* 19 */     return this.codigoProductor;
/*    */   }
/*    */   public void setCodigoProductor(String codigoProductor) {
/* 22 */     this.codigoProductor = codigoProductor;
/*    */   }
/*    */   public String getNombre() {
/* 25 */     return this.nombre;
/*    */   }
/*    */   public void setNombre(String nombre) {
/* 28 */     this.nombre = nombre;
/*    */   }
/*    */   public Date getCreado() {
/* 31 */     return this.creado;
/*    */   }
/*    */   public void setCreado(Date creado) {
/* 34 */     this.creado = creado;
/*    */   }
/*    */   public Date getModificado() {
/* 37 */     return this.modificado;
/*    */   }
/*    */   public void setModificado(Date modificado) {
/* 40 */     this.modificado = modificado;
/*    */   }
/*    */   public int getIdUser() {
/* 43 */     return this.idUser;
/*    */   }
/*    */   public void setIdUser(int idUser) {
/* 46 */     this.idUser = idUser;
/*    */   }
/*    */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\struc\Parcela.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */