package com.nestef.room.messaging;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nestef.room.R;

import ru.noties.markwon.Markwon;

/**
 * Created by Noah Steffes on 7/5/18.
 */
public class MarkdownBottomSheetFragment extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.markdown_bottom_sheet, container);

        TextView textView = view.findViewById(R.id.markdown_result);
        Markwon.setMarkdown(textView, getString(R.string.markdown_example));

        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}
