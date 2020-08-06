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
<!-- $Id: encrypted_data_extractor.xslt 30 2016-02-23 13:42:56Z sfarrow $ -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xenc="http://www.w3.org/2001/04/xmlenc#" xmlns:ds="http://www.w3.org/2000/09/xmldsig#">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="/">
		<xsl:for-each select="//xenc:EncryptedData">
			<xsl:for-each select="./ds:KeyInfo/xenc:EncryptedKey">
				<xsl:text>####KEYNAME:=</xsl:text>
				<xsl:value-of select="./ds:KeyInfo/ds:KeyName"/>
				<xsl:text>####ENCRYPTEDKEY:=</xsl:text>
				<xsl:value-of select="./xenc:CipherData/xenc:CipherValue"/>
			</xsl:for-each>
			<xsl:text>#-#-#-#-#-#-#-#-#</xsl:text>
			<xsl:value-of select="./xenc:CipherData/xenc:CipherValue"/>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
