package com.cielicki.dominik.allergyapp.ui.messages;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cielicki.dominik.allergyapp.R;
import com.cielicki.dominik.allergyapp.common.Utils;
import com.cielicki.dominik.allergyapp.ui.home.HomeViewModel;
import com.cielicki.dominik.allergyapprestapi.db.Messages;
import com.cielicki.dominik.allergyapprestapi.db.User;
import com.cielicki.dominik.allergyapprestapi.db.model.MessagesList;

import java.util.Calendar;

/**
 * Adapter dla RecyclerView wyświetlającego wiadomości chatu.
 */
public class MessageListAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private MessagesList messageList;
    private MessagesViewModel messagesViewModel;
    private HomeViewModel homeViewModel;

    public MessageListAdapter(MessagesList messageList, MessagesViewModel messagesViewModel, HomeViewModel homeViewModel) {
        this.homeViewModel = homeViewModel;
        this.messageList = messageList;
        this.messagesViewModel = messagesViewModel;
    }

    @Override
    public int getItemCount() {
        return messageList.getMessagesList().size();
    }


    @Override
    public int getItemViewType(int position) {
        Messages message = messageList.getMessagesList().get(position);
        User currentUser = homeViewModel.getCurrentUser().getValue();

        if (message.getSender().getId().equals(currentUser.getId())) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Messages message = (Messages) messageList.getMessagesList().get(position);
        boolean changeDate = true;

        if (position > 0) {
            Calendar messageDate = Calendar.getInstance();
            messageDate.setTime(message.getDate());

            Messages previousMessage = (Messages) messageList.getMessagesList().get(position - 1);
            Calendar previousMessageDate = Calendar.getInstance();
            previousMessageDate.setTime(previousMessage.getDate());

            if (
                    messageDate.get(Calendar.DAY_OF_MONTH) != previousMessageDate.get(Calendar.DAY_OF_MONTH)
                    ||  messageDate.get(Calendar.MONTH) != previousMessageDate.get(Calendar.MONTH)
                    ||  messageDate.get(Calendar.YEAR) != previousMessageDate.get(Calendar.YEAR)
            ) {
                changeDate = true;

            } else {
                changeDate = false;
            }
        }

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message, changeDate);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message, changeDate);
        }
    }

    /**
     * Klasa przedstawiająca komponent wyświetlający wiadomości wysłane.
     */
    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, dateReceived;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.message_text_sent);
            timeText = (TextView) itemView.findViewById(R.id.message_timestamp_sent);
            dateReceived = (TextView) itemView.findViewById(R.id.message_date_sent);
        }

        void bind(Messages message, boolean changeDate) {
            messageText.setText(message.getMessage());

            // Format the stored timestamp into a readable String using method.
            timeText.setText(Utils.formatMessageTimestamp(message.getDate()));

            if (changeDate) {
                dateReceived.setText(Utils.formatMessageDate(message.getDate()));
            } else {
                dateReceived.setVisibility(View.GONE);
            }
        }
    }

    /**
     * Klasa przedstawiająca komponent wyświetlający wiadomości otrzymane.
     */
    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText, dateReceived;
        ImageView profileImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.message_text_received);
            timeText = (TextView) itemView.findViewById(R.id.message_timestamp_received);
            nameText = (TextView) itemView.findViewById(R.id.message_user_received);
            dateReceived = (TextView) itemView.findViewById(R.id.message_date_received);
        }

        void bind(Messages message, boolean changeDate) {
            messageText.setText(message.getMessage());

            // Format the stored timestamp into a readable String using method.
            timeText.setText(Utils.formatMessageTimestamp(message.getDate()));

            nameText.setText(message.getSender().getName() + " " + message.getSender().getLastName());

            if (changeDate) {
                dateReceived.setText(Utils.formatMessageDate(message.getDate()));
            } else {
                dateReceived.setVisibility(View.GONE);
            }
        }
    }
}