package com.app.ashu.slideview.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.ashu.slideview.R;

public class ChatMessageViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout leftMsgLayout;

    public LinearLayout rightMsgLayout;

    public TextView leftMsgTextView;

    public TextView rightMsgTextView;

    public ChatMessageViewHolder(View itemView) {
        super(itemView);

        if(itemView!=null) {
            leftMsgLayout = (LinearLayout) itemView.findViewById(R.id.chat_left_msg_layout);
            rightMsgLayout = (LinearLayout) itemView.findViewById(R.id.chat_right_msg_layout);
            leftMsgTextView = (TextView) itemView.findViewById(R.id.chat_left_msg_text_view);
            rightMsgTextView = (TextView) itemView.findViewById(R.id.chat_right_msg_text_view);
        }
    }
}
