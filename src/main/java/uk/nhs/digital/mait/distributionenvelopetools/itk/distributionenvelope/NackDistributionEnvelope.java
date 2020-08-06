/*
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
 */
package uk.nhs.digital.mait.distributionenvelopetools.itk.distributionenvelope;

import java.io.InputStream;
import uk.nhs.digital.mait.distributionenvelopetools.itk.util.ITKException;
import uk.nhs.digital.mait.commonutils.util.configurator.Configurator;

/**
 * Class for constructing and sending a negative acknowledgement.
 *
 * @author Damian Murphy <murff@warlock.org>
 */
public class NackDistributionEnvelope
        extends AckDistributionEnvelope {

    private static final String NACK_TEMPLATE = "infrastructure_nack_template.xml.txt";
    private static final String APP_NACK_ERROR_OID_PROPERTY_NAME = "tks.routingactor.cda.appnackerroroid";
    // default error oid for sub ITK2.2 versions, may be overidden by setting the property above
    private static final String APP_NACK_ERROR_OID_2_1 = "2.16.840.1.113883.2.1.3.2.4.17.268";

    private ITKException ex = null;

    /**
     * Construct a negative acknowledgement, reporting the ITKException to the
     * sender of the distribution envelope.
     *
     * @param d DistributionEnvelope to nack
     * @param e ITKException to report in the nack.
     * @throws ITKException
     */
    public NackDistributionEnvelope(DistributionEnvelope d, ITKException e)
            throws ITKException {
        super(d);
        ex = e;
        ackTemplateNameLocation = NACK_TEMPLATE;
    }

    @Override
    public void makeMessage()
            throws ITKException {
        InputStream is = getClass().getResourceAsStream(ackTemplateNameLocation);
        StringBuilder sb = initContent(is);

        // Exception substitutions (based on template)
        //
        substitute(sb, "__ERROR_ID__", ex.getId());
        substitute(sb, "__ERROR_CODE__", ex.getCode());
        substitute(sb, "__ERROR_TEXT__", ex.getText());
        try {
            Configurator c = Configurator.getConfigurator();
            // Handles ITK2.2 and above default is ITK2.1 or below error oid end 268
            // ITK 2.2 error oid ends with 516 taken from the config file
            String oid = c.getConfiguration(APP_NACK_ERROR_OID_PROPERTY_NAME);
            substitute(sb, "__ERROR_OID__", oid != null ? oid : APP_NACK_ERROR_OID_2_1);
        } catch (Exception ex1) {
            System.err.println("Unable to retrieve application negative acknowledgement error oid "+ex1.getMessage());
        }

        if (ex.getDiagnostics() == null) {
            substitute(sb, "__ERROR_DIAGNOSTICS__", "");
        } else {
            substitute(sb, "__ERROR_DIAGNOSTICS__", "<itk:ErrorDiagnosticText><![CDATA[__ERR_DIAG_REWRITE__]]></itk:ErrorDiagnosticText>");
            substitute(sb, "__ERR_DIAG_REWRITE__", ex.getDiagnostics());
        }
        setDistributionEnvelope(sb.toString());
    }
}
