package org.pillarone.riskanalytics.domain.pc.reserves.cashflow;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.pillarone.riskanalytics.core.packets.IHasOccurrenceDate;
import org.pillarone.riskanalytics.domain.pc.claims.Claim;
import org.pillarone.riskanalytics.domain.pc.claims.ClaimPacketFactory;
import org.pillarone.riskanalytics.domain.utils.marker.IReinsuranceContractMarker;
import org.pillarone.riskanalytics.domain.utils.marker.IPerilMarker;
import org.pillarone.riskanalytics.domain.utils.marker.ISegmentMarker;
import org.pillarone.riskanalytics.core.packets.IAggregatableSummable;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author shartmann (at) munichre (dot) com
 */
public class ClaimDevelopmentPacket extends Claim implements IHasOccurrenceDate, IAggregatableSummable {

    protected static Log LOG = LogFactory.getLog(ClaimDevelopmentPacket.class);

    protected Claim claim;

    private double paid;
    private double reserved;
    private double changeInReserves;

    private IPattern payoutPattern;
    /**
     * negative values correspond to the development period of reserved claims;     // todo(sku): think of using a common definition for pos and neg based on incurred period
     * positive values correspond to the incurred period according to the simulation context
     */
    private int originalPeriod; // according to simulation period
    private DateTime incurredDate;

    public ClaimDevelopmentPacket(){
    }

    public ClaimDevelopmentPacket(Claim claim){
        set(claim);
        setIncurredDate(claim.getDate());
    }

    @Override
    public Claim copy() {
        ClaimDevelopmentPacket copy = ClaimDevelopmentPacketFactory.createPacket();
        copy.set(this);
        return copy;
    }

    /**
     *  This function is not secure in the sense that it does not check if the object itself and
     *  the object received as argument are of equal type.
     */
    @Override
    public Claim getNetClaim(Claim cededClaim) {
        ClaimDevelopmentPacket netClaim = (ClaimDevelopmentPacket) copy();
        netClaim.minus(cededClaim);
        if (cededClaim.notNull()) {
            netClaim.addMarker(IReinsuranceContractMarker.class, cededClaim.getReinsuranceContract());
        }
        return netClaim;
    }

    @Override
    public boolean notNull() {
        return (!(getIncurred() == 0 && paid == 0 && reserved == 0));
    }

    public boolean hasNoneTrivialDevelopment(boolean includeOriginalClaimCheck) {
        if (payoutPattern != null && !payoutPattern.isTrivial()) {
            return true;
        }
        else if (includeOriginalClaimCheck && getOriginalClaim() != null && getOriginalClaim() instanceof ClaimDevelopmentPacket) {
            return (((ClaimDevelopmentPacket) getOriginalClaim()).hasNoneTrivialDevelopment(false));
        }
        else {
            return false;
        }
    }

    public double getIncurredFractionOfPeriod() {
        return originalPeriod + getFractionOfPeriod();
    }

    /**
     *  This function is not secure in the sense that it does not check if the object itself and
     *  the object received as argument are of equal type.
     */
    @Override
    public void plus(Claim claim) {
        setIncurred(getIncurred() + ((ClaimDevelopmentPacket) claim).getIncurred());
        paid += ((ClaimDevelopmentPacket) claim).getPaid();
        reserved += ((ClaimDevelopmentPacket) claim).getReserved();
        changeInReserves += ((ClaimDevelopmentPacket) claim).getChangeInReserves();
//        if (getFractionOfPeriod().equals(claim.getFractionOfPeriod())) {
//            setClaimType(ClaimType.AGGREGATED);
//            setFractionOfPeriod(Math.min(getFractionOfPeriod(), claim.getFractionOfPeriod()));
//        }
    }

    /**
     *  This function is not secure in the sense that it does not check if the object itself and
     *  the object received as argument are of equal type.
     */
    @Override
    public void minus(Claim claim) {
        setIncurred(getIncurred() - ((ClaimDevelopmentPacket) claim).getIncurred());
        paid -= ((ClaimDevelopmentPacket) claim).getPaid();
        reserved -= ((ClaimDevelopmentPacket) claim).getReserved();
        changeInReserves -= ((ClaimDevelopmentPacket) claim).getChangeInReserves();
//        if (getFractionOfPeriod().equals(claim.getFractionOfPeriod())) {
//            setClaimType(ClaimType.AGGREGATED);
//            setFractionOfPeriod(Math.min(getFractionOfPeriod(), claim.getFractionOfPeriod()));
//        }
    }

    @Override
    public ClaimDevelopmentPacket scale(double factor) {
        super.scale(factor);
        paid *= factor;
        reserved *= factor;
        changeInReserves *= factor;
        return this;
    }

    /**
     *  Converts the current packet into a Claim packet setting its value to incurred
     */
    public Claim getClaimPacket() {
        if (claim == null) {
            claim = ClaimPacketFactory.createPacket();
            claim.setOrigin(origin);
            claim.setOriginalClaim(getOriginalClaim());
            claim.setEvent(getEvent());
            claim.setFractionOfPeriod(getFractionOfPeriod());
            claim.setClaimType(getClaimType());
            claim.addMarker(IPerilMarker.class, getPeril());
            claim.addMarker(ISegmentMarker.class, getLineOfBusiness());
        }
        claim.setUltimate(getUltimate());
        return claim;
    }

    public void set(ClaimDevelopmentPacket claim) {
        super.set(claim);
        setPaid(claim.getPaid());
        setReserved(claim.getReserved());
        setOriginalPeriod(claim.getOriginalPeriod());
        setChangeInReserves(claim.getChangeInReserves());
        setOriginalClaim(claim.getOriginalClaim());
        setPayoutPattern(claim.getPayoutPattern());
    }

    protected static final String INCURRED = "incurred";
    protected static final String PAID = "paid";
    protected static final String RESERVED = "reserved";
    protected static final String CHANGE_IN_RESERVES = "changeInReserves";

    @Override
    public Map<String, Number> getValuesToSave() throws IllegalAccessException {
        Map<String, Number> valuesToSave = new HashMap<String, Number>(4);
        valuesToSave.put(INCURRED, getIncurred());
        valuesToSave.put(PAID, paid);
        valuesToSave.put(RESERVED, reserved);
        valuesToSave.put(CHANGE_IN_RESERVES, changeInReserves);
        return valuesToSave;
    }

    @Override
    public List<String> getFieldNames() {
        return Arrays.asList(INCURRED, PAID, RESERVED, CHANGE_IN_RESERVES);
    }

    @Override
    public String toString() {
        String separator = ", ";
        StringBuilder result = new StringBuilder();
        result.append(getIncurred());
        result.append(separator);
        result.append(paid);
        result.append(separator);
        result.append(reserved);
        result.append(separator);
        if (getClaimType() != null) result.append(getClaimType()).append(separator);
        if (origin != null) result.append(origin.getName()).append(separator);
        if (getOriginalClaim() != null) result.append(System.identityHashCode(getOriginalClaim())).append(separator);
        if (getLineOfBusiness() != null) result.append(getLineOfBusiness().getName()).append(separator);
        if (getPeril() != null) result.append(getPeril().getName()).append(separator);
        if (getReinsuranceContract() != null) result.append(getReinsuranceContract().getName()).append(separator);
        result.append(", original period: ").append(originalPeriod);
        if (getDate() != null) result.append(", date: ").append(new SimpleDateFormat("yyyy-MM-dd").format(getDate().toDate()));
        return result.toString();
    }

    public double getIncurred() {
        return getUltimate();
    }

    public void setIncurred(double incurred) {
        setUltimate(incurred);
    }


    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public double getReserved() {
        return reserved;
    }

    public void setReserved(double reserved) {
        this.reserved = reserved;
    }

    public IPattern getPayoutPattern() {
        return payoutPattern;
    }

    public void setPayoutPattern(IPattern payoutPattern) {
        this.payoutPattern = payoutPattern;
    }

    public int getOriginalPeriod() {
        return originalPeriod;
    }

    public void setOriginalPeriod(int originalPeriod) {
        this.originalPeriod = originalPeriod;
    }

    public double getChangeInReserves() {
        return changeInReserves;
    }

    public void setChangeInReserves(double changeInReserves) {
        this.changeInReserves = changeInReserves;
    }

    public DateTime getOccurrenceDate() {
        //AR-111 - IHasOccurrenceDate support
        if(incurredDate != null){
            return incurredDate;
        }
        return getDate();
    }

    public DateTime getIncurredDate() {
        //AR-111 - pre-IHasOccurrenceDate, (temporarily?) left for legacy API support
        if(incurredDate != null){
            return incurredDate;
        }
        return getDate();
    }

    public void setIncurredDate(DateTime incurredDate) {
        this.incurredDate = incurredDate;
    }

    public IAggregatableSummable sum(Collection<IAggregatableSummable> claims) {
        /** PA Note during work on AR-111
         **  This should probably be made a method of the class above...
         **/

        if (claims.isEmpty()) return null;
        if (claims.size() == 1) return claims.iterator().next();

        double paid = 0;
        double reserved = 0;
        double changeInReserves = 0;

        for (IAggregatableSummable claimI: claims) {
            ClaimDevelopmentPacket claim = (ClaimDevelopmentPacket) claimI;
            paid += claim.getPaid();
            reserved += claim.getReserved();
            // Summing every thing this time as the other sum sums even ultimates
            changeInReserves += claim.getChangeInReserves(); //changes in reserves should be incremental by def
        }

//        ClaimRoot baseClaim = new ClaimRoot(ultimate, claims.get(0).getBaseClaim());
        Claim baseClaim = new Claim();
        baseClaim.set(((ClaimDevelopmentPacket)(claims.iterator().next())).getOriginalClaim());

        //..hmmm how to do this right ?

//        DateTime updateDate = claims.get(0).getUpdateDate();
//        int updatePeriod = 0;
//        if (claims.get(0).getUpdatePeriod() != null) {
//            updatePeriod = claims.get(0).getUpdatePeriod();
//        }

        ClaimDevelopmentPacket summedClaims = new ClaimDevelopmentPacket(baseClaim);
        // this may bite...
//        applyMarkers(claims.get(0), summedClaims);

        summedClaims.setPaid(paid);
        summedClaims.setReserved(reserved);
        summedClaims.setChangeInReserves(changeInReserves);
        return summedClaims;
    }

    public List<IAggregatableSummable> aggregateByBaseClaim(Collection<IAggregatableSummable> claims) {

        List<IAggregatableSummable> aggregateByBaseClaim = new ArrayList<IAggregatableSummable>();
        ListMultimap<Claim, ClaimDevelopmentPacket> claimsByBaseClaim = ArrayListMultimap.create();

        for (IAggregatableSummable claim : claims) {
            claimsByBaseClaim.put( ((ClaimDevelopmentPacket) claim).getOriginalClaim(), (ClaimDevelopmentPacket) claim);
        }
        for (Collection<ClaimDevelopmentPacket> claimsWithSameBaseClaim : claimsByBaseClaim.asMap().values()) {
            if (claimsWithSameBaseClaim.size() == 1) {
                aggregateByBaseClaim.add(claimsWithSameBaseClaim.iterator().next());
            }
            else {
                double paid = 0;
                double reserved = 0;
                double changeInReserves = 0;

                for (ClaimDevelopmentPacket claim : claimsWithSameBaseClaim) {
                    paid = claim.getPaid();
                    reserved = claim.getReserved();
                    // I'm not summing as I'd guess these not to be incremental but total values
                    //assumption would be that we are looking at updates to the same claim, in chronological order
                    changeInReserves += claim.getChangeInReserves(); //changes in reserves should be incremental by def
                }
                Claim baseClaim = new Claim();
                baseClaim.set(((ClaimDevelopmentPacket) claims.iterator().next()).getOriginalClaim());

//                int updatePeriod = 0;
//                if (claims.get(0). != null) {
//                    updatePeriod = claims.get(0).getUpdatePeriod();
//                }

                ClaimDevelopmentPacket aggregateClaim = new ClaimDevelopmentPacket(baseClaim);
                aggregateClaim.setPaid(paid);
                aggregateClaim.setReserved(reserved);
                aggregateClaim.setChangeInReserves(changeInReserves);
                //applyMarkers(claims.get(0), aggregateClaim); //Do we need them? Can't find the equivalent fields...
                aggregateByBaseClaim.add(aggregateClaim);
            }
        }
        return aggregateByBaseClaim;
    }


}