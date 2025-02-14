package bcheck;

public interface ItemFilter<T extends Item> {
    boolean filter(T item, String searchTerm);
}