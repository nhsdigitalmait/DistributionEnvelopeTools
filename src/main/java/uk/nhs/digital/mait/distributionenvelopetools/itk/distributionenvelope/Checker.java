/*
 Copyright 2016  Simon Farrow <simon.farrow1@hscic.gov.uk>

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
// $Id: Edifact2xmlSuite.java 176 2017-02-28 15:55:23Z sfarrow $
package uk.nhs.digital.mait.distributionenvelopetools.itk.distributionenvelope;

/**
 * Necessary to be visisble as a separate class for testing the 
 * DistributionEnvelopeChecker interface
 * @author simonfarrow
 */
public class Checker implements DistributionEnvelopeChecker {

    @Override
    public String check(DistributionEnvelope d, Object o) throws Exception {
        return null;
    }

    @Override
    public void setService(String service) {
    }
}
