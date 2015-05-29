package ws.mahesh.travelassist.beta.trains.trainview.model;

/**
 * Created by mahesh on 28/03/15.
 */
public class TrainViewObject {
    public int id;
    public int cars;
    public int time;
    public int station_id;
    public int start_id;
    public int end_id;
    public int sunday_only;
    public int holiday_only;
    public int non_sunday;
    public String direction;
    public String speed;
    public String spl_info;
    public String platform_no;
    public String platform_side;

    public TrainViewObject(int id, int cars, int time, int station_id, int start_id, int end_id, int sunday_only, int holiday_only, int non_sunday, String direction, String speed, String spl_info, String platform_no, String platform_side) {
        this.id = id;
        this.cars = cars;
        this.time = time;
        this.station_id = station_id;
        this.start_id = start_id;
        this.end_id = end_id;
        this.sunday_only = sunday_only;
        this.holiday_only = holiday_only;
        this.non_sunday = non_sunday;
        this.direction = direction;
        this.speed = speed;
        this.spl_info = spl_info;
        this.platform_no = platform_no;
        this.platform_side = platform_side;
    }

    public int getStation_id() {
        return station_id;
    }

    public void setStation_id(int station_id) {
        this.station_id = station_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCars() {
        return cars;
    }

    public void setCars(int cars) {
        this.cars = cars;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getStart_id() {
        return start_id;
    }

    public void setStart_id(int start_id) {
        this.start_id = start_id;
    }

    public int getEnd_id() {
        return end_id;
    }

    public void setEnd_id(int end_id) {
        this.end_id = end_id;
    }

    public int getSunday_only() {
        return sunday_only;
    }

    public void setSunday_only(int sunday_only) {
        this.sunday_only = sunday_only;
    }

    public int getHoliday_only() {
        return holiday_only;
    }

    public void setHoliday_only(int holiday_only) {
        this.holiday_only = holiday_only;
    }

    public int getNon_sunday() {
        return non_sunday;
    }

    public void setNon_sunday(int non_sunday) {
        this.non_sunday = non_sunday;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getSpl_info() {
        return spl_info;
    }

    public void setSpl_info(String spl_info) {
        this.spl_info = spl_info;
    }

    public String getPlatform_no() {
        return platform_no;
    }

    public void setPlatform_no(String platform_no) {
        this.platform_no = platform_no;
    }

    public String getPlatform_side() {
        return platform_side;
    }

    public void setPlatform_side(String platform_side) {
        this.platform_side = platform_side;
    }

    @Override
    public String toString() {
        return "TrainViewObject{" +
                "id=" + id +
                ", cars=" + cars +
                ", time=" + time +
                ", station_id=" + station_id +
                ", start_id=" + start_id +
                ", end_id=" + end_id +
                ", sunday_only=" + sunday_only +
                ", holiday_only=" + holiday_only +
                ", non_sunday=" + non_sunday +
                ", direction='" + direction + '\'' +
                ", speed='" + speed + '\'' +
                ", spl_info='" + spl_info + '\'' +
                ", platform_no='" + platform_no + '\'' +
                ", platform_side='" + platform_side + '\'' +
                '}';
    }
}
