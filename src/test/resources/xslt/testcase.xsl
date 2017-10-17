<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:tc="testcase"
                xmlns="http://www.w3.org/1999/xhtml"
                version="1.0">
    <xsl:output method="html"/>
    
    <xsl:param name="testcase" select=""/>
    
    <xsl:variable name="sample" select="document($testcase)"/>
    
    <xsl:variable name="style">
body {font-family: Arial, Helvetica, sans-serif}
.tc-faktura {margin-top: 6pt; width: 80%}
.tc-header {font-weight: bold}
.tc-odd {background-color: lightgrey}
.tc-row {line-height: 16pt}
  </xsl:variable>
  
  <xsl:template match="/">
      <xsl:if test="$sample/tc:testcase">
          <xsl:apply-templates select="$sample/tc:testcase"/>
          <h3>Kontoauszug aktuell</h3>
          <xsl:apply-templates select="tc:kontoauszug"/>
      </xsl:if>
      <xsl:if test="tc:testcase">
           <xsl:apply-templates select="tc:testcase"/>
      </xsl:if>
  </xsl:template>

    <xsl:template match="tc:testcase">
        <html>
            <head>
                <style><xsl:value-of select="$style"/></style>
                <title>Testfall</title>
            </head>
            <body>
                <h3>Testfall</h3>
                <xsl:apply-templates select="tc:inkassoFall"/>
                <xsl:apply-templates select="tc:when/tc:esr"/>
                <h3>Kontoauszug expected</h3>
                <xsl:apply-templates select="tc:then/tc:kontoauszug"/>
            </body>
        </html>
    </xsl:template>
    
  
    <xsl:template match="tc:inkassoFall">
        <h3>Inkassofall (<xsl:value-of select="@id"/>, 
                    <xsl:value-of select="@zpvnr" />, 
                    <xsl:value-of select="@forderungsart" />/
                    <xsl:value-of select="@forderungsjahr"/>)</h3>
        <xsl:apply-templates select="tc:faktura"/>
    </xsl:template>
    
    <xsl:template match="tc:faktura | tc:kontoauszug">
        <table class="tc-faktura">
            <tr class="tc-row tc-header">
                <td width="10%"><xsl:value-of select="@id"/></td> 
                <td width="20%">Valuta</td>
                <td width="20%">Belegart</td>
                <td width="40%">Kat/Inst</td>
                <td width="10%*">Betrag</td>
            </tr>
            <xsl:apply-templates select="tc:position" />   
        </table>
    </xsl:template>
    
    
    <xsl:template match="tc:position">
        <xsl:variable name="odd" select="position() mod 2"/>
        <xsl:variable name="toggleClass" select="concat(substring('tc-even', 1, 9*(1-$odd)), substring('tc-odd', 1, 9*$odd))"/>
        <tr class="tc-row {$toggleClass}">
            <xsl:if test="position()=1">
                <td>
<!--                    <xsl:value-of select="../@id"/>-->
                </td>
                <td>
                    <xsl:value-of select="../@valuta"/>
                </td>
                <td>
                    <xsl:value-of select="../@belegart"/>
                </td>
            </xsl:if>
            <xsl:if test="position()!=1">
                <td colspan="3"/>
            </xsl:if>
            <td>
                <xsl:value-of select="tc:kategorie/@type"/>/<xsl:value-of select="tc:kategorie/@subtype"/>,
                <xsl:value-of select="tc:institution/@art"/>/<xsl:value-of select="tc:institution/@nummer"/>
            </td>
            <td align="right">
                <xsl:value-of select="tc:betrag/text()" />
            </td>
        </tr>
    </xsl:template>
    
    <xsl:template match="tc:esr">
        <h3>Esr-Zahlung: Art=<xsl:value-of select="@art"/>, Faktura=<xsl:value-of select="@faktura"/>
        , Betrag=<xsl:value-of select="@betrag"/></h3>
    </xsl:template>

</xsl:stylesheet>
