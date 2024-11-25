/*     */ package cl.expled.web.page;
/*     */ 
/*     */ import java.net.URL;
/*     */ import javax.xml.soap.MessageFactory;
/*     */ import javax.xml.soap.SOAPBody;
/*     */ import javax.xml.soap.SOAPConnection;
/*     */ import javax.xml.soap.SOAPConnectionFactory;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPEnvelope;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.soap.SOAPPart;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Controller
/*     */ public class download
/*     */ {
/*     */   public static String getDTE(int id, String rut) throws Exception {
/* 183 */     String urlSolicitud = "http://10.55.55.30:8080/wsQueryDte/services/query?wsdl";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 188 */     SOAPConnectionFactory scFactory = SOAPConnectionFactory.newInstance();
/* 189 */     SOAPConnection con = scFactory.createConnection();
/* 190 */     MessageFactory factory = MessageFactory.newInstance();
/* 191 */     SOAPMessage message = factory.createMessage();
/* 192 */     SOAPPart soapPart = message.getSOAPPart();
/* 193 */     SOAPEnvelope envelope = soapPart.getEnvelope();
/* 194 */     envelope.addNamespaceDeclaration("getXmlLibro", "http://wsQueryDte");
/*     */ 
/*     */     
/* 197 */     SOAPBody body = envelope.getBody();
/*     */     
/* 199 */     SOAPElement dte = body.addChildElement("getXmlLibro", "getXmlLibro");
/*     */     
/* 201 */     SOAPElement Rut = dte.addChildElement("Rut");
/* 202 */     SOAPElement Clave = dte.addChildElement("Clave");
/*     */     
/* 204 */     SOAPElement idws = dte.addChildElement("id");
/*     */     
/* 206 */     Rut.setTextContent(rut);
/* 207 */     Clave.setTextContent("1234");
/* 208 */     idws.setTextContent((new StringBuilder(String.valueOf(id))).toString());
/*     */ 
/*     */     
/* 211 */     URL endpoint = new URL(urlSolicitud);
/* 212 */     SOAPMessage responseSII = con.call(message, endpoint);
/* 213 */     System.out.println("####################################");
/*     */ 
/*     */     
/* 216 */     String mensaje = "";
/*     */     
/*     */     try {
/* 219 */       SOAPEnvelope env = responseSII.getSOAPPart().getEnvelope();
/* 220 */       SOAPBody sb = env.getBody();
/*     */       
/* 222 */       NodeList gloza = sb.getElementsByTagName("ax21:gloza");
/*     */       
/* 224 */       String sgloza = gloza.item(0).getTextContent();
/* 225 */       NodeList estado = sb.getElementsByTagName("ax21:estado");
/*     */       
/* 227 */       String sestado = estado.item(0).getTextContent();
/* 228 */       System.out.println(sgloza);
/*     */       
/* 230 */       if (sestado.equals("NOK"))
/*     */       {
/* 232 */         throw new Exception(sgloza);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 237 */       String sXml = sb.getElementsByTagName("ax21:xml").item(0).getTextContent();
/* 238 */       mensaje = sXml;
/*     */ 
/*     */       
/* 241 */       System.out.println(sestado);
/* 242 */     } catch (Exception e) {
/* 243 */       mensaje = "Error ws: " + e.getMessage();
/* 244 */       throw new Exception("Error ws: " + e.getMessage());
/*     */     } 
/*     */     
/* 247 */     return mensaje;
/*     */   }
/*     */ }


/* Location:              C:\Users\jose.rodriguez\Desktop\Nueva carpeta (3)\WEB-INF\classes\!\cl\expled\web\page\download.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */