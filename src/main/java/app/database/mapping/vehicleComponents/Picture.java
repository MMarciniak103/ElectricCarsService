package app.database.mapping.vehicleComponents;

public class Picture {
    private String id;
    private String name;
    private String extension;
    private String contentType;

    public Picture() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getExtension() {
        return extension;
    }

    public String getContentType() {
        return contentType;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", extension='" + extension + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}
