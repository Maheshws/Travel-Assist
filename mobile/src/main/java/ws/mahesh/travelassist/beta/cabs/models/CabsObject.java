package ws.mahesh.travelassist.beta.cabs.models;

/**
 * Created by mahesh on 30/03/15.
 */
public class CabsObject {
    int Id;
    String Name;
    String City;
    double BaseFare;
    double BaseDistance;
    double BaseWaitTime;
    double FarePerUnit;
    double DistancePerUnit;
    double WaitTimePerUnit;
    double FarePerWaitTime;
    double NightMultiplier;
    int CustomCab;

    public CabsObject() {
    }

    public CabsObject(int id, String name, String city, double baseFare, double baseDistance, double baseWaitTime, double farePerUnit, double distancePerUnit, double waitTimePerUnit, double farePerWaitTime, double nightMultiplier, int customCab) {
        Id = id;
        Name = name;
        City = city;
        BaseFare = baseFare;
        BaseDistance = baseDistance;
        BaseWaitTime = baseWaitTime;
        FarePerUnit = farePerUnit;
        DistancePerUnit = distancePerUnit;
        WaitTimePerUnit = waitTimePerUnit;
        FarePerWaitTime = farePerWaitTime;
        NightMultiplier = nightMultiplier;
        CustomCab = customCab;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public double getBaseFare() {
        return BaseFare;
    }

    public void setBaseFare(double baseFare) {
        BaseFare = baseFare;
    }

    public double getBaseDistance() {
        return BaseDistance;
    }

    public void setBaseDistance(double baseDistance) {
        BaseDistance = baseDistance;
    }

    public double getBaseWaitTime() {
        return BaseWaitTime;
    }

    public void setBaseWaitTime(double baseWaitTime) {
        BaseWaitTime = baseWaitTime;
    }

    public double getFarePerUnit() {
        return FarePerUnit;
    }

    public void setFarePerUnit(double farePerUnit) {
        FarePerUnit = farePerUnit;
    }

    public double getDistancePerUnit() {
        return DistancePerUnit;
    }

    public void setDistancePerUnit(double distancePerUnit) {
        DistancePerUnit = distancePerUnit;
    }

    public double getWaitTimePerUnit() {
        return WaitTimePerUnit;
    }

    public void setWaitTimePerUnit(double waitTimePerUnit) {
        WaitTimePerUnit = waitTimePerUnit;
    }

    public double getFarePerWaitTime() {
        return FarePerWaitTime;
    }

    public void setFarePerWaitTime(double farePerWaitTime) {
        FarePerWaitTime = farePerWaitTime;
    }

    public double getNightMultiplier() {
        return NightMultiplier;
    }

    public void setNightMultiplier(double nightMultiplier) {
        NightMultiplier = nightMultiplier;
    }

    public int getCustomCab() {
        return CustomCab;
    }

    public void setCustomCab(int customCab) {
        CustomCab = customCab;
    }
}
