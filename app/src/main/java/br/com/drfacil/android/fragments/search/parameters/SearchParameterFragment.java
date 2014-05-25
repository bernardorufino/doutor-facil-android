package br.com.drfacil.android.fragments.search.parameters;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import br.com.drfacil.android.R;
import br.com.drfacil.android.model.search.Search;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

public class SearchParameterFragment extends DialogFragment {

    private final Search mSearch;
    private Button vOkButton;
    private Button vDismissButton;

    public SearchParameterFragment(Search search) {
        mSearch = search;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vOkButton = (Button) getView().findViewById(R.id.search_parameter_ok_button);
        if (vOkButton != null) vOkButton.setOnClickListener(mOnOkButtonClickListener);
        vDismissButton = (Button) getView().findViewById(R.id.search_parameter_dismiss_button);
        if (vDismissButton != null) vDismissButton.setOnClickListener(mOnDismissButtonClickListener);
    }

    private View.OnClickListener mOnOkButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onOk();
        }
    };

    private View.OnClickListener mOnDismissButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onDismiss();
        }
    };

    protected void onOk() {
        setFinished();
        dismiss();
    }

    protected void onDismiss() {
        setAborted();
        dismiss();
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
