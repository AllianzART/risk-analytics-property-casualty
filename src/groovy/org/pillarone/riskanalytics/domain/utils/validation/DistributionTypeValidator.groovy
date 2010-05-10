package org.pillarone.riskanalytics.domain.utils.validation

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.pillarone.riskanalytics.core.parameterization.IParameterObjectClassifier
import org.pillarone.riskanalytics.core.parameterization.ParameterValidationError
import org.pillarone.riskanalytics.core.parameterization.ParameterValidationService
import org.pillarone.riskanalytics.core.parameterization.validation.IParameterizationValidator
import org.pillarone.riskanalytics.core.simulation.item.parameter.ParameterHolder
import org.pillarone.riskanalytics.core.simulation.item.parameter.ParameterObjectParameterHolder
import org.pillarone.riskanalytics.domain.utils.DistributionType

class DistributionTypeValidator implements IParameterizationValidator {

    private static Log LOG = LogFactory.getLog(DistributionTypeValidator)
    private static final double EPSILON = 1E-6 // guard for "close-enough" checks instead of == for doubles

    private ParameterValidationService validationService

    public DistributionTypeValidator() {
        validationService = new ParameterValidationService()
        registerConstraints()
    }

    List<ParameterValidationError> validate(List<ParameterHolder> parameters) {

        List<ParameterValidationError> errors = []

        for (ParameterHolder parameter in parameters) {
            if (parameter instanceof ParameterObjectParameterHolder) {
                IParameterObjectClassifier classifier = parameter.getClassifier()
                if (classifier instanceof DistributionType) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug "validating ${parameter.path}"
                    }
                    errors.addAll(validationService.validate(classifier, parameter.getParameterMap()))
                }
            }
        }

        return errors
    }

    private void registerConstraints() {
        validationService.register(DistributionType.POISSON) {Map type ->
            if (type.lambda >= 0) return true
            ["distribution.type.error.poisson.negative.lambda", type.lambda]
        }
        validationService.register(DistributionType.EXPONENTIAL) {Map type ->
            type.lambda < 0 ? ["distribution.type.error.exponential.negative.lambda", type.lambda] :
                true
        }
        validationService.register(DistributionType.BETA) {Map type ->
            type.alpha < 0 ? ["distribution.type.error.exponential.negative.alpha", type.alpha] :
                type.beta < 0 ? ["distribution.type.error.exponential.negative.beta", type.beta] :
                    true
        }

        validationService.register(DistributionType.NEGATIVEBINOMIAL) {Map type ->
            if (type.gamma > 0) return true
            ["distribution.tpye.error.negativebinomial.gamma.negative.or.zero", type.gamma]
        }
        validationService.register(DistributionType.NEGATIVEBINOMIAL) {Map type ->
            if ((0..1).containsWithinBounds(type.p)) return true
            ["distribution.tpye.error.negativebinomial.p.out.of.range", type.p]
        }
        validationService.register(DistributionType.DISCRETEEMPIRICAL) {Map type ->
            double[] values = type.discreteEmpiricalValues.getColumnByName('observations')
            if (!values) {
                return ["distribution.type.error.discreteempirical.observations.empty"]
            }
            for (int i = 1; i < values.length; i++) {
                if (values[i - 1] > values[i]) {
                    return ["distribution.type.error.discreteempirical.observations.not.increasing", i, values[i - 1], values[i]]
                }
            }
            return true
        }
        validationService.register(DistributionType.DISCRETEEMPIRICAL) {Map type ->
            double[] values = type.discreteEmpiricalValues.getColumnByName('probabilities')
            if (!values) {
                return ["distribution.type.error.discreteempirical.probabilities.empty"]
            }
            double sum = values.inject(0) {temp, it -> temp + it }
            if (isCloseEnough(sum, 1d)) return true
            ["distribution.type.error.discreteempirical.probabilities.sum.not.one", sum, values]
        }
        validationService.register(DistributionType.DISCRETEEMPIRICALCUMULATIVE) {Map type ->
            double[] values = type.discreteEmpiricalCumulativeValues.getColumnByName('observations')
            if (!values) {
                return ["distribution.type.error.discreteempiricalcumulative.observations.empty"]
            }
            for (int i = 1; i < values.length; i++) {
                if (values[i - 1] > values[i]) {
                    return ["distribution.type.error.discreteempiricalcumulative.observations.not.increasing", i, values[i - 1], values[i]]
                }
            }
            return true
        }
        validationService.register(DistributionType.DISCRETEEMPIRICALCUMULATIVE) {Map type ->
            double[] values = type.discreteEmpiricalCumulativeValues.getColumnByName('cumulative probabilities')
            if (!values) {
                return ["distribution.type.error.discreteempiricalcumulative.probabilities.empty"]
            }
            for (int i = 1; i < values.length; i++) {
                if (values[i - 1] > values[i]) {
                    return ["distribution.type.error.discreteempiricalcumulative.probabilities.not.increasing", i, values[i - 1], values[i]]
                }
            }
            if (!isCloseEnough(values[-1], 1d)) {
                return ["distribution.type.error.discreteempiricalcumulative.probability.last.value.not.1", values[values.length - 1]]
            }
            return true
        }
        validationService.register(DistributionType.NORMAL) {Map type ->
            if (type.stDev > 0) return true
            ["distribution.type.error.normal.sigma.negative.or.zero", type.stDev]
        }
        validationService.register(DistributionType.LOGNORMAL) {Map type ->
            if (type.stDev > 0) return true
            ["distribution.type.error.lognormal.sigma.negative.or.zero", type.stDev]
        }
        // todo(sku): check for further restrictions
        validationService.register(DistributionType.LOGNORMAL_MU_SIGMA) {Map type ->
            if (type.sigma > 0) return true
            ["distribution.type.error.lognormal_mu_sigma.sigma.negative.or.zero", type.sigma]
        }
        validationService.register(DistributionType.PARETO) {Map type ->
            if (type.alpha > 0) return true
            ["distribution.type.error.pareto.alpha.negative.or.zero", type.alpha]
        }
        validationService.register(DistributionType.PARETO) {Map type ->
            if (type.beta > 0) return true
            ["distribution.type.error.pareto.beta.negative.or.zero", type.beta]
        }
        validationService.register(DistributionType.UNIFORM) {Map type ->
            if (type.a < type.b) return true
            ["distribution.type.error.uniform", type.a, type.b]
        }
        validationService.register(DistributionType.PIECEWISELINEAR) {Map type ->
            double[] values = type.supportPoints.getColumnByName('values')
            if (!values) {
                return ["distribution.type.error.piecewiselinear.values.empty"]
            }
            for (int i = 1; i < values.length; i++) {
                if (values[i - 1] > values[i]) {
                    return ["distribution.type.error.piecewiselinear.values.not.increasing", i, values[i - 1], values[i]]
                }
            }
            return true
        }
        validationService.register(DistributionType.PIECEWISELINEAR) {Map type ->
            double[] cdf = type.supportPoints.getColumnByName('cummulative probabilities')
            if (!cdf) {
                return ["distribution.type.error.piecewiselinear.cummulative.probabilities.empty"]
            }
            for (int i = 1; i < cdf.length; i++) {
                if (cdf[i - 1] > cdf[i]) {
                    return ["distribution.type.error.piecewiselinear.cummulative.probabilities.not.increasing", i, cdf[i - 1], cdf[i]]
                }
            }
            if (!isCloseEnough(cdf[0], 0d)) {
                return ["distribution.type.error.piecewiselinear.cdf.first.value.not.null", cdf[0]]
            }
            if (!isCloseEnough(cdf[-1], 1d)) {
                return ["distribution.type.error.piecewiselinear.cdf.last.value.not.1", cdf[cdf.length - 1]]
            }
            return true
        }
        validationService.register(DistributionType.PIECEWISELINEAREMPIRICAL) {Map type ->
            double[] values = type.observations.getColumnByName('observations')
            if (!values) {
                return ["distribution.type.error.piecewiselinearempirical.observations.empty"]
            }
            for (int i = 1; i < values.length; i++) {
                if (values[i - 1] > values[i]) {
                    return ["distribution.type.error.piecewiselinear.observations.not.increasing", i, values[i - 1], values[i]]
                }
            }
            return true
        }
        validationService.register(DistributionType.TRIANGULARDIST) {Map type ->
            if (type.a <= type.m && type.m <= type.b) return true
            ["distribution.type.error.triangular", type.a, type.b, type.m]
        }
        validationService.register(DistributionType.CHISQUAREDIST) {Map type ->
            if (type.n > 0) return true
            ["distribution.type.error.chisquare.n.negative.or.zero", type.n]
        }
        validationService.register(DistributionType.STUDENTDIST) {Map type ->
            if (type.n > 0) return true
            ["distribution.type.error.student.n.negative.or.zero", type.n]
        }
        validationService.register(DistributionType.BINOMIALDIST) {Map type ->
            if ((0..1).containsWithinBounds(type.p)) return true
            ["distribution.tpye.error.binomial.p.out.of.range", type.p]
        }
        validationService.register(DistributionType.BINOMIALDIST) {Map type ->
            if (type.n > 0) return true
            ["distribution.tpye.error.binomial.n.negative.or.zero", type.n]
        }
        validationService.register(DistributionType.INVERSEGAUSSIANDIST) {Map type ->
            if (type.mu > 0) return true
            ["distribution.type.error.inversegaussian.mu.negative.or.zero", type.mu]
        }
        validationService.register(DistributionType.INVERSEGAUSSIANDIST) {Map type ->
            if (type.lambda > 0) return true
            ["distribution.type.error.inversegaussian.lambda.negative.or.zero", type.lambda]
        }
        validationService.register(DistributionType.CONSTANTS) {Map type ->
            double[] values = type.constants.getColumnByName('constants')
            if (values && values.size() > 0) return true
            ["distribution.type.error.constants.empty", values]
        }
    }

    private boolean isCloseEnough(double candidate, double compareAgainst) {
        (candidate - compareAgainst).abs() < EPSILON
    }
}