package component;


public class MapElement<T> {
    public final String name;
    public final T object;

    public MapElement(String name, T object) {
        this.name = name;
        this.object = object;
    }
}
