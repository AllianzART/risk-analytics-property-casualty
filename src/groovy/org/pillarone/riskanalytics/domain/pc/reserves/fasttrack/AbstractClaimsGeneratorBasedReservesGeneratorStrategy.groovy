package org.pillarone.riskanalytics.domain.pc.reserves.fasttrack

import org.pillarone.riskanalytics.core.parameterization.ComboBoxTableMultiDimensionalParameter
import org.pillarone.riskanalytics.core.parameterization.IParameterObjectClassifier
import org.pillarone.riskanalytics.domain.utils.marker.IPerilMarker
import org.pillarone.riskanalytics.core.parameterization.AbstractParameterObject

/**
 * @author shartmann (at) munichre (dot) com
 */

abstract public class AbstractClaimsGeneratorBasedReservesGeneratorStrategy extends AbstractParameterObject
    implements IReservesGeneratorStrategy{

    ComboBoxTableMultiDimensionalParameter basedOnClaimsGenerators = new ComboBoxTableMultiDimensionalParameter(
            [], ["Claims Generators"], IPerilMarker)

    abstract public IParameterObjectClassifier getType()

    public Map getParameters() {
        return [
                'basedOnClaimsGenerators' : basedOnClaimsGenerators
        ]
    }
}
