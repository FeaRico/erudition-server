package ru.mahach.eruditionserver.sse.type;

public enum EventType {
    ITEM("item"),
    QUESTION("question"),
    ANSWER("answer");

    private final String name;

    EventType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
