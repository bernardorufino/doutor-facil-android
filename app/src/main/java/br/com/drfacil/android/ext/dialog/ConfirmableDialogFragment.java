package br.com.drfacil.android.ext.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import br.com.drfacil.android.R;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

public class ConfirmableDialogFragment extends DialogFragment {

    private Button vOkButton;
    private Button vDismissButton;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vOkButton = (Button) getView().findViewById(R.id.confirmable_dialog_ok_button);
        if (vOkButton != null) vOkButton.setOnClickListener(mOnOkButtonClickListener);
        vDismissButton = (Button) getView().findViewById(R.id.confirmable_dialog_dismiss_button);
        if (vDismissButton != null) vDismissButton.setOnClickListener(mOnDismissButtonClickListener);
    }

    public Button getOkButton() {
        return vOkButton;
    }

    public Button getDismissButton() {
        return vDismissButton;
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

    private SettableFuture<ConfirmableDialogFragment> mTaskFuture = SettableFuture.create();

    protected void setFinished() {
        mTaskFuture.set(this);
    }

    protected void setAborted() {
        if (mTaskFuture.isDone()) return;
        mTaskFuture.set(null);
    }

    public ListenableFuture<? extends ConfirmableDialogFragment> getTaskFuture() {
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
