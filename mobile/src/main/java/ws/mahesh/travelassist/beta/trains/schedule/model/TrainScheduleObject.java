package ws.mahesh.travelassist.beta.trains.schedule.model;

/**
 * Created by mahesh on 28/03/15.
 */
public class TrainScheduleObject {
    public int id;
    public int cars;
    public int src_time;
    public int dest_time;
    public int src_station_id;
    public int dest_station_id;
    public int start_id;
    public int end_id;
    public int sunday_only;
    public int holiday_only;
    public int non_sunday;
    public String direction;
    public String speed;
    public String spl_info;

    public TrainScheduleObject(int id, int cars, int src_time, int dest_time, int src_station_id, int dest_station_id, int start_id, int end_id, int sunday_only, int holiday_only, int non_sunday, String direction, String speed, String spl_info) {
        this.id = id;
        this.cars = cars;
        this.src_time = src_time;
        this.dest_time = dest_time;
        this.src_station_id = src_station_id;
        this.dest_station_id = dest_station_id;
        this.start_id = start_id;
        this.end_id = end_id;
        this.sunday_only = sunday_only;
        this.holiday_only = holiday_only;
        this.non_sunday = non_sunday;
        this.direction = direction;
        this.speed = speed;
        this.spl_info = spl_info;
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

    public int getSrc_time() {
        return src_time;
    }

    public void setSrc_time(int src_time) {
        this.src_time = src_time;
    }

    public int getDest_time() {
        return dest_time;
    }

    public void setDest_time(int dest_time) {
        this.dest_time = dest_time;
    }

    public int getSrc_station_id() {
        return src_station_id;
    }

    public void setSrc_station_id(int src_station_id) {
        this.src_station_id = src_station_id;
    }

    public int getDest_station_id() {
        return dest_station_id;
    }

    public void setDest_station_id(int dest_station_id) {
        this.dest_station_id = dest_station_id;
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

    @Override
    public String toString() {
        return "TrainScheduleObject{" +
                "id=" + id +
                ", cars=" + cars +
                ", src_time=" + src_time +
                ", dest_time=" + dest_time +
                ", src_station_id=" + src_station_id +
                ", dest_station_id=" + dest_station_id +
                ", start_id=" + start_id +
                ", end_id=" + end_id +
                ", sunday_only=" + sunday_only +
                ", holiday_only=" + holiday_only +
                ", non_sunday=" + non_sunday +
                ", direction='" + direction + '\'' +
                ", speed='" + speed + '\'' +
                ", spl_info='" + spl_info + '\'' +
                '}';
    }
}
