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
        <b>Inkassofall (<xsl:value-of select="@id"/>, 
                    <xsl:value-of select="@zpvnr" />, 
                    <xsl:value-of select="@forderungsart" />/
                    <xsl:value-of select="@forderungsjahr"/>)</b>
        <xsl:apply-templates select="tc:faktura"/>
    </xsl:template>
    
    <xsl:template match="tc:faktura">
        <table width="60%">
            <tr  style="font-weight:bold" align="left">
                <td width="10%">Id</td> 
                <td width="20%">Valuta</td>
                <td width="20%">Belegart</td>
                <td width="40%">Kat/Inst</td>
                <td width="10%*">Betrag</td>
            </tr>
            <xsl:apply-templates select="tc:position" />   
        </table>
    </xsl:template>
    
    <xsl:template match="tc:position">
        <tr>
            <xsl:if test="position()=1">
                <td>
                    <xsl:value-of select="../@id"/>
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
                <xsl:value-of select="tc:kategorie//@type"/>/<xsl:value-of select="tc:kategorie//@subtype"/>, 
                <xsl:value-of select="tc:institution/@art"/>/<xsl:value-of select="tc:institution/@nummer"/>
            </td>
            <td align="right">
                <xsl:value-of select="tc:betrag/text()" />
            </td>
        </tr>
    </xsl:template>

</xsl:stylesheet>
