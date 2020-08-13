package models;

public class RecordFromFile {
    private Integer id;
    private String value;

    public RecordFromFile(Integer id, String value) {
        this.id = id;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "RecordFromFile{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}
