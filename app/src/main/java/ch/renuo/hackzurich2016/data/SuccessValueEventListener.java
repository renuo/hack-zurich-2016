package ch.renuo.hackzurich2016.data;

public abstract class SuccessValueEventListener<T> {
    private boolean wasCalled = false;

    public void onChangeCall(T object) {
        wasCalled = true;
        onChange(object);
    }

    protected abstract void onChange(T object);

    boolean wasCalled() {
        return wasCalled;
    }
}
