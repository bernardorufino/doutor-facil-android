package br.com.drfacil.android.helpers;


import android.os.Handler;
import android.os.Looper;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewManager;
import android.view.ViewParent;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;

public class CustomViewHelper {

    public static void toggleVisibleGone(View view, boolean visibility) {
        view.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    public static void toggleVisibleInvisible(View view, boolean visibility) {
        view.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }

    public static boolean tryMakeOrphan(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewManager) {
            ((ViewManager) parent).removeView(view);
            return true;
        }
        return false;
    }

    public static <T extends View> T makeOrphan(T view) {
        checkArgument(tryMakeOrphan(view), "Can't make view orphan.");
        return view;
    }

    public static <T> List<T> getSelectedItems(ListView list, Class<T> clazz) {
        Adapter adapter = list.getAdapter();
        SparseBooleanArray positions = list.getCheckedItemPositions();
        List<T> ans = new ArrayList<>();
        for (int i = 0, n = list.getCount(); i < n; i++) {
            if (!positions.get(i)) continue;
            T item = clazz.cast(adapter.getItem(i));
            ans.add(item);
        }
        return ans;
    }

    public static <T> void selectItems(ListView list, Collection<T> items) {
        Set<T> lookup = new HashSet<>(items);
        ListAdapter adapter = list.getAdapter();
        for (int i = 0, n = list.getCount(); i < n; i++) {
            @SuppressWarnings("unchecked")
            T item = (T) adapter.getItem(i);
            list.setItemChecked(i, lookup.contains(item));
        }
    }

    // Posting to UI thread since there are states where it's not allowed to perform clicks (e.g. other thread?)
    public static void performClick(final View view) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                view.performClick();
            }
        });
    }

    // Prevents instantiation
    private CustomViewHelper() {
        throw new AssertionError("Cannot instantiate object from " + this.getClass());
    }
}
