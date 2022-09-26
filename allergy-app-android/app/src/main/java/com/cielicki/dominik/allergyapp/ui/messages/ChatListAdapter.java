package com.cielicki.dominik.allergyapp.ui.messages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cielicki.dominik.allergyapp.R;
import com.cielicki.dominik.allergyapp.common.SettingsEnum;
import com.cielicki.dominik.allergyapp.common.Utils;
import com.cielicki.dominik.allergyapp.ui.home.HomeViewModel;
import com.cielicki.dominik.allergyapprestapi.db.Chat;
import com.cielicki.dominik.allergyapprestapi.db.Messages;
import com.cielicki.dominik.allergyapprestapi.db.User;
import com.cielicki.dominik.allergyapprestapi.db.UserSettings;
import com.cielicki.dominik.allergyapprestapi.db.model.ChatList;

/**
 * Adapter dla RecyclerView wyświetlającego listę chatów.
 */
public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private ChatList chatList;
    private MessagesViewModel messagesViewModel;
    private HomeViewModel homeViewModel;

    ChatListAdapter(ChatList chatList, MessagesViewModel messagesViewModel, HomeViewModel homeViewModel) {
        this.chatList = chatList;
        this.messagesViewModel = messagesViewModel;
        this.homeViewModel = homeViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View medicineView = layoutInflater.inflate(R.layout.chat_item, parent, false);

        return new ViewHolder(context, medicineView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat chat = chatList.getChatList().get(position);
        User recipient = chat.getRecipient(homeViewModel.getCurrentUser().getValue());

        if (chat.getUser2().getId() == User.GLOBAL_CHAT_USER.getId()) {
            UserSettings userSettings = homeViewModel.getUserSettingsHashMap().get(SettingsEnum.GENERAL_CHAT);

            if (userSettings != null && userSettings.getValue().equals("0")) {
                if (! chat.getUser().getId().equals(homeViewModel.getCurrentUser().getValue().getId())) {
                    holder.itemView.setVisibility(View.GONE);
                    holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                    return;
                }
            }

            holder.subjectTextView.setText("Forum: " + chat.getSubject());

        } else {
            holder.subjectTextView.setText(chat.getSubject());
        }

        holder.recipientNameTextView.setText(recipient.getName() + " " + recipient.getLastName());

        Messages message = chat.getLastMessage();

        if (message != null) {
            holder.lastMessageTextView.setText(message.getMessage());
            holder.timestampTextView.setText(Utils.formatDate(chat.getLastMessage().getDate()));
        }

        holder.cardView.setOnClickListener((event) -> {
            messagesViewModel.setCurrentChat(chat);

            Fragment fragment = ((AppCompatActivity)holder.context).getSupportFragmentManager().getFragments().get(0);
            FragmentManager fragmentManager = fragment.getChildFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.messages_fragment, ChatFragment.class, null).addToBackStack("chat").setReorderingAllowed(true).commit();
        });
    }

    @Override
    public int getItemCount() {
        return chatList.getChatList().size();
    }

    /**
     * Klasa przedstawiająca komponent wyświetlający wiersz listy chatów.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView subjectTextView;
        public TextView recipientNameTextView;
        public TextView lastMessageTextView;
        public TextView timestampTextView;
        public CardView cardView;
        public Context context;

        public ViewHolder(@NonNull Context context, @NonNull View itemView) {
            super(itemView);

            subjectTextView = (TextView) itemView.findViewById(R.id.chat_question_subject);
            recipientNameTextView = (TextView) itemView.findViewById(R.id.chat_recipient);
            lastMessageTextView = (TextView) itemView.findViewById(R.id.chat_last_message);
            timestampTextView = (TextView) itemView.findViewById(R.id.chat_message_timestamp);

            cardView = (CardView) itemView.findViewById(R.id.card_chat);
            this.context = context;
        }
    }
}
