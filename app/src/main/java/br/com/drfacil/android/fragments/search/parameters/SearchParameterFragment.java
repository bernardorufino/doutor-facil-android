package br.com.drfacil.android.fragments.search.parameters;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Window;
import br.com.drfacil.android.model.search.Search;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

import java.lang.reflect.InvocationTargetException;

public class SearchParameterFragment extends DialogFragment {

    private final Search mSearch;

    public static <T extends SearchParameterFragment> T show(
            Search search,
            Class<T> fragmentClass,
            FragmentManager fragmentManager) {
        try {
            T fragment = fragmentClass.getConstructor(Search.class).newInstance(search);
            fragment.show(fragmentManager, fragmentClass.toString());
            return fragment;
        } catch (NoSuchMethodException |
                java.lang.InstantiationException |
                IllegalAccessException |
                InvocationTargetException e) {
            AssertionError error = new AssertionError();
            error.initCause(e);
            throw error;
        }
    }

    public SearchParameterFragment(Search search) {
        mSearch = search;
    }

    public Search getSearch() {
        return mSearch;
    }

    private SettableFuture<SearchParameterFragment> mTaskFuture = SettableFuture.create();

    protected void setFinished() {
        mTaskFuture.set(this);
    }

    protected void setAborted() {
        if (mTaskFuture.isDone()) return;
        mTaskFuture.set(null);
    }

    public ListenableFuture<SearchParameterFragment> getTaskFuture() {
        return mTaskFuture;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        setAborted();
    }
}
