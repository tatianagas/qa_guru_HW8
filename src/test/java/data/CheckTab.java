package data;

public enum CheckTab {
    PURITY ("Чистота"),
    READABILITY ("Читаемость");

    public final String description;

    CheckTab(String description) {
        this.description = description;
    }

}
