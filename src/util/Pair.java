package util;

public class Pair<T,S> {
    private T first;
    private S second;

    public Pair(T t, S s) {
        this.first = t;
        this.second = s;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public S getSecond() {
        return second;
    }

    public void setSecond(S second) {
        this.second = second;
    }

    @Override
    public int hashCode() {
        return first.hashCode() + second.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Pair) {
            return first.equals(((Pair)obj).first) && second.equals(((Pair)obj).second);
        }

        return false;
    }

    @Override
    public String toString() {
        return String.format("(%s,%s)", first.toString(), second.toString());
    }
}
