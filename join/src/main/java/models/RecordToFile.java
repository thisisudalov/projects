package models;

public class RecordToFile {
    private Integer id;
    private String firstParameter;
    private String secondParameter;

    public Integer getId() {
        return id;
    }

    public RecordToFile(Integer id, String firstParameter, String secondParameter) {
        this.id = id;
        this.firstParameter = firstParameter;
        this.secondParameter = secondParameter;
    }

    @Override
    public String toString() {
        return "\n"
                + id +
                "\t |" + firstParameter +
                "\t |" + secondParameter;
    }
}
