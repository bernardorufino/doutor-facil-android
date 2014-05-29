package br.com.drfacil.android.ext.instance;

import com.google.common.base.Throwables;
import com.google.common.primitives.Primitives;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Class to hold weak references to objects, which can be recreated
// B is the common ancestor for the instances
public abstract class LazyWeakFactory<B> implements InstanceFactory<B> {

    private final Map<Class<? extends B>, WeakReference<? extends B>> mMap = new HashMap<>();

    public <T extends B> T getInstance(Class<T> type) {
        WeakReference<? extends B> ref = mMap.get(type);
        if (ref == null || ref.get() == null) {
            ref = new WeakReference<>(safeCreate(type));
            mMap.put(type, ref);
        }
        return cast(type, ref.get());
    }

    public <T extends B> T putInstance(Class<T> type, T value) {
        WeakReference<? extends B> previous = mMap.put(type, new WeakReference<>(value));
        if (previous == null || previous.get() == null) return null;
        return cast(type, previous.get());
    }

    protected abstract <T extends B> T create(Class<T> type) throws Exception;

    private <T extends B> T safeCreate(Class<T> type) {
        try {
            return create(type);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public void clear() {
        mMap.clear();
    }

    // See guava's MutableClassToInstanceMap#cast()
    private static <B, T extends B> T cast(Class<T> type, B value) {
        return Primitives.wrap(type).cast(value);
    }

    public static class WithFixedArgumentBuilder {

        private List<Class<?>> mArgumentTypes = new ArrayList<>();
        private List<Object> mArgumentValues = new ArrayList<>();

        public <T> WithFixedArgumentBuilder argument(Class<T> type, T value) {
            mArgumentTypes.add(type);
            mArgumentValues.add(value);
            return this;
        }

        public <B> LazyWeakFactory<B>  build() {
            final Class<?>[] types = mArgumentTypes.toArray(new Class<?>[mArgumentTypes.size()]);
            final Object[] values = mArgumentValues.toArray(new Object[mArgumentValues.size()]);
            return new LazyWeakFactory<B>() {
                @Override
                protected <T extends B> T create(Class<T> type) throws Exception {
                    return type.getConstructor(types).newInstance(values);
                }
            };
        }
    }
}
