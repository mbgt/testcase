<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:tc="testcase"
                xmlns="http://www.w3.org/1999/xhtml"
                version="1.0">
    <xsl:output method="html"/>

    <xsl:template match="tc:testcase">
        <html>
            <head>
                <title>Testfall</title>
            </head>
            <body>
                <xsl:apply-templates select="tc:inkassoFall"/>
            </body>
        </html>
    </xsl:template>
    
  
    <xsl:template match="tc:inkassoFall">
        <table width="80%"  >
            <tr><td>Inkassofall</td><td>Forderungsart</td><td>Forderungsjahr</td></tr>
          <tr>
            <td><xsl:value-of select="@id" /></td>
            <td><xsl:value-of select="@forderungsart" /></td>
             <td><xsl:value-of select="@forderungsjahr"/></td>
          </tr>
        </table>
    </xsl:template>

</xsl:stylesheet>
