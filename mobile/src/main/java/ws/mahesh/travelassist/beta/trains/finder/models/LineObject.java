package ws.mahesh.travelassist.beta.trains.finder.models;

/**
 * Created by mahesh on 27/03/15.
 */
public class LineObject {
    int id;
    String code;
    String name;


    public LineObject(int id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
