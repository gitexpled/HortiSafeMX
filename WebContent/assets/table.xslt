<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <html>
  <body>
  <xsl:if test="count(/Z_MF99_LMR_SAP_CARGA/OUTPUT/ET_LOG_VALIDA/item)>0">
  <h2>Resultado con Errores(<xsl:value-of select="count(/Z_MF99_LMR_SAP_CARGA/OUTPUT/ET_LOG_VALIDA/item)"/>)</h2>
  <table border="1">
    <tr >
      <th>TYPE</th>
      <th>ID</th>
      <th>NUMBER</th>
      <th>MESSAGE</th>
    </tr>
    <xsl:for-each select="/Z_MF99_LMR_SAP_CARGA/OUTPUT/ET_LOG_VALIDA/item">
    <tr>
      <td><xsl:value-of select="TYPE"/></td>
      <td><xsl:value-of select="ID"/></td>
       <td><xsl:value-of select="NUMBER"/></td>
      <td><xsl:value-of select="MESSAGE"/></td>
    </tr>
    </xsl:for-each>
  </table>
  </xsl:if>
   <xsl:if test="count(/Z_MF99_LMR_SAP_CARGA/OUTPUT/ET_LOG_CARGA/item)>0">
   <h2>Resultado Exitoso(<xsl:value-of select="count(/Z_MF99_LMR_SAP_CARGA/OUTPUT/ET_LOG_CARGA/item)"/>)</h2>
  <table border="1">
    <tr >
      <th>MESSAGE</th>
    </tr>
    <xsl:for-each select="/Z_MF99_LMR_SAP_CARGA/OUTPUT/ET_LOG_CARGA/item">
    <tr>
      <td><xsl:value-of select="MESSAGE"/></td>
    </tr>
    </xsl:for-each>
  </table>
  </xsl:if>
  </body>
  </html>
</xsl:template>

</xsl:stylesheet>