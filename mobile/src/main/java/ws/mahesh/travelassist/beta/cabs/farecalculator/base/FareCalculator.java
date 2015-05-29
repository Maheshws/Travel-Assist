package ws.mahesh.travelassist.beta.cabs.farecalculator.base;

import ws.mahesh.travelassist.beta.cabs.models.CabsObject;

/**
 * Created by mahesh on 30/03/15.
 */
public class FareCalculator {
    CabsObject object;
    double DistanceTravelled;
    double WaitTime;
    double TotalAmount;

    double distanceExtra;
    double waitExtra;
    double baseAmount;
    double extraDistanceAmount;
    double extraWaitingAmount;
    double nightAmount;

    public FareCalculator(CabsObject object, double distanceTravelled, double waitTime) {
        this.object = object;
        DistanceTravelled = distanceTravelled;
        WaitTime = waitTime;
        calculate();
    }

    private void calculate() {
        distanceExtra = DistanceTravelled - object.getBaseDistance();
        if (distanceExtra < 0.0)
            distanceExtra = 0.0;
        baseAmount = object.getBaseFare();
        extraDistanceAmount = (distanceExtra * object.getDistancePerUnit()) * (object.getFarePerUnit());

        waitExtra = WaitTime - object.getBaseWaitTime();
        if (waitExtra < 0.0)
            waitExtra = 0.0;
        extraWaitingAmount = (waitExtra * object.getWaitTimePerUnit()) * (object.getFarePerWaitTime());
        TotalAmount = baseAmount + extraDistanceAmount + extraWaitingAmount;
        nightAmount = TotalAmount * object.getNightMultiplier();

    }

    public CabsObject getObject() {
        return object;
    }

    public double getDistanceTravelled() {
        return DistanceTravelled;
    }

    public double getWaitTime() {
        return WaitTime;
    }

    public double getTotalAmount() {
        return TotalAmount;
    }

    public double getDistanceExtra() {
        return distanceExtra;
    }

    public double getBaseAmount() {
        return baseAmount;
    }

    public double getExtraDistanceAmount() {
        return extraDistanceAmount;
    }

    public double getWaitExtra() {
        return waitExtra;
    }

    public double getExtraWaitingAmount() {
        return extraWaitingAmount;
    }

    public double getNightAmount() {
        return nightAmount;
    }

    @Override
    public String toString() {
        return "FareCalculator{" +
                "object=" + object +
                ", DistanceTravelled=" + DistanceTravelled +
                ", WaitTime=" + WaitTime +
                ", TotalAmount=" + TotalAmount +
                ", distanceExtra=" + distanceExtra +
                ", waitExtra=" + waitExtra +
                ", baseAmount=" + baseAmount +
                ", extraDistanceAmount=" + extraDistanceAmount +
                ", extraWaitingAmount=" + extraWaitingAmount +
                ", nightAmount=" + nightAmount +
                '}';
    }
}
