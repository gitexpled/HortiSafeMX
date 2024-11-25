/*     */ package lib.struc;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class cargaManual
/*     */ {
/*     */   int idCargaManual;
/*     */   String fecha;
/*     */   int idUsuario;
/*     */   String laboratorio;
/*     */   String identificador;
/*     */   String codProductor;
/*     */   int idEspecie;
/*     */   int idTemporada;
/*     */   int idVariedad;
/*     */   String variedad;
/*     */   String variedades;
/*     */   private String codParcela;
/*     */   private String codTurno;
/*     */   ArrayList<cargaManualDetalle> detalle;
/*     */   private ArrayList<String> codTurnos;
/*     */   
/*     */   public String getVariedades() {
/*  29 */     return this.variedades;
/*     */   }
/*     */   
/*     */   public void setVariedades(String variedades) {
/*  33 */     this.variedades = variedades;
/*     */   }
/*     */   
/*     */   public int getIdVariedad() {
/*  37 */     return this.idVariedad;
/*     */   }
/*     */   
/*     */   public void setIdVariedad(int idVariedad) {
/*  41 */     this.idVariedad = idVariedad;
/*     */   }
/*     */   
/*     */   public String getVariedad() {
/*  45 */     return this.variedad;
/*     */   }
/*     */   
/*     */   public void setVariedad(String variedad) {
/*  49 */     this.variedad = variedad;
/*     */   }
/*     */   
/*     */   public String getFecha() {
/*  53 */     return this.fecha;
/*     */   }
/*     */   
/*     */   public void setFecha(String fecha) {
/*  57 */     this.fecha = fecha;
/*     */   }
/*     */   public int getIdUsuario() {
/*  60 */     return this.idUsuario;
/*     */   }
/*     */   public void setIdUsuario(int idUsuario) {
/*  63 */     this.idUsuario = idUsuario;
/*     */   }
/*     */   public String getLaboratorio() {
/*  66 */     return this.laboratorio;
/*     */   }
/*     */   public void setLaboratorio(String laboratorio) {
/*  69 */     this.laboratorio = laboratorio;
/*     */   }
/*     */   public String getIdentificador() {
/*  72 */     return this.identificador;
/*     */   }
/*     */   public void setIdentificador(String identificador) {
/*  75 */     this.identificador = identificador;
/*     */   }
/*     */   public ArrayList<cargaManualDetalle> getDetalle() {
/*  78 */     return this.detalle;
/*     */   }
/*     */   public void setDetalle(ArrayList<cargaManualDetalle> detalle) {
/*  81 */     this.detalle = detalle;
/*     */   }
/*     */   public int getIdCargaManual() {
/*  84 */     return this.idCargaManual;
/*     */   }
/*     */   public void setIdCargaManual(int idCargaManual) {
/*  87 */     this.idCargaManual = idCargaManual;
/*     */   }
/*     */   public String getCodProductor() {
/*  90 */     return this.codProductor;
/*     */   }
/*     */   public void setCodProductor(String codProductor) {
/*  93 */     this.codProductor = codProductor;
/*     */   }
/*     */   
/*     */   public int getIdEspecie() {
/*  97 */     return this.idEspecie;
/*     */   }
/*     */   
/*     */   public void setIdEspecie(int idEspecie) {
/* 101 */     this.idEspecie = idEspecie;
/*     */   }
/*     */   
/*     */   public int getIdTemporada() {
/* 105 */     return this.idTemporada;
/*     */   }
/*     */   
/*     */   public void setIdTemporada(int idTemporada) {
/* 109 */     this.idTemporada = idTemporada;
/*     */   }
/*     */   
/*     */   public String getCodParcela() {
/* 113 */     return this.codParcela;
/*     */   }
/*     */   
/*     */   public void setCodParcela(String codParcela) {
/* 117 */     this.codParcela = codParcela;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getCodTurno() {
/* 122 */     return this.codTurno;
/*     */   }
/*     */   
/*     */   public void setCodTurno(String codTurno) {
/* 126 */     this.codTurno = codTurno;
/*     */   }
/*     */   
/*     */   public ArrayList<String> getCodTurnos() {
/* 130 */     return this.codTurnos;
/*     */   }
/*     */   
/*     */   public void setCodTurnos(ArrayList<String> codTurnos) {
/* 134 */     this.codTurnos = codTurnos;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\lib\struc\cargaManual.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */