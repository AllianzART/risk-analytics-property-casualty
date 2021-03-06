package org.pillarone.riskanalytics.domain.pc.assetLiabilityMismatch;

import org.pillarone.riskanalytics.core.parameterization.AbstractParameterObject;
import org.pillarone.riskanalytics.core.parameterization.IParameterObjectClassifier;

import java.util.Collections;
import java.util.Map;

/**
 * @author shartmann (at) munichre (dot) com
 */
public class VolumeAbsoluteAssetLiabilityMismatchGeneratorStrategy
        extends AbstractParameterObject implements IAssetLiabilityMismatchGeneratorStrategy {

    public VolumeAbsoluteAssetLiabilityMismatchGeneratorStrategy() {
    }

    public IParameterObjectClassifier getType() {
        return AssetLiabilityMismatchGeneratorStrategyType.VOLUMERELATIVETOINITIALVOLUME;
    }

    public Map getParameters() {
        return Collections.emptyMap();
    }
}
