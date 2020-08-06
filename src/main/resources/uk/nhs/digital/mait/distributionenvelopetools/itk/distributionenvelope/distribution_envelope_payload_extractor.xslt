<?xml version="1.0" encoding="UTF-8"?>
<!--
Copyright 2011 Damian Murphy <murff@warlock.org>

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
-->
<!-- $Id: distribution_envelope_payload_extractor.xslt 30 2016-02-23 13:42:56Z sfarrow $ -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:itk="urn:nhs-itk:ns:201005">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="/">
		<xsl:for-each select="//itk:manifestitem">
			<xsl:variable name="mfstid" select="./@id"/>
			<xsl:variable name="mt" select="./@mimetype"/>
			<xsl:variable name="prid" select="./@profileid"/>
			<xsl:variable name="b64" select="./@base64"/>
			<xsl:variable name="cmp" select="./@compressed"/>
			<xsl:variable name="encr" select="./@encrypted"/>
			<xsl:text>ID:=</xsl:text>
			<xsl:value-of select="$mfstid"/>
			<xsl:text>####MIMETYPE:=</xsl:text>
			<xsl:value-of select="$mt"/>
			<xsl:text>####PROFILEID:=</xsl:text>
			<xsl:value-of select="$prid"/>
			<xsl:text>####BASE64:=</xsl:text>
			<xsl:choose>
				<xsl:when test="string-length($b64) > 0">
					<xsl:value-of select="$b64"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>false</xsl:text>
				</xsl:otherwise>
			</xsl:choose>			
			<xsl:text>####COMPRESSED:=</xsl:text>
			<xsl:choose>
				<xsl:when test="$cmp">
					<xsl:value-of select="$cmp"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>false</xsl:text>
				</xsl:otherwise>
			</xsl:choose>			
			<xsl:text>####ENCRYPTED:=</xsl:text>
			<xsl:choose>
				<xsl:when test="$encr">
					<xsl:value-of select="$encr"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>false</xsl:text>
				</xsl:otherwise>
			</xsl:choose>			
			<xsl:text>####PAYLOADBODY:=</xsl:text>
			<xsl:choose>
				<xsl:when test="$encr='true' or (contains($mt,'xml') and not($cmp='true') and not($b64='true'))">
					<xsl:for-each select="../../../itk:payloads/itk:payload[@id=$mfstid]/*[1]">
						<xsl:copy-of select="."/>
					</xsl:for-each>
				</xsl:when>
				<xsl:otherwise>
					<xsl:copy-of select="../../../itk:payloads/itk:payload[@id=$mfstid]/text()"/>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:text>#-#-#-#-#-#-#-#-#</xsl:text>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
