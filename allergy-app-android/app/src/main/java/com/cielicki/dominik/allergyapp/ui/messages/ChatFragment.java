package com.cielicki.dominik.allergyapp.ui.messages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cielicki.dominik.allergyapp.common.Utils;
import com.cielicki.dominik.allergyapp.data.Endpoints;
import com.cielicki.dominik.allergyapp.data.RequestQueueSingleton;
import com.cielicki.dominik.allergyapp.databinding.ChatFragmentBinding;
import com.cielicki.dominik.allergyapp.ui.home.HomeViewModel;
import com.cielicki.dominik.allergyapprestapi.db.Chat;
import com.cielicki.dominik.allergyapprestapi.db.Messages;
import com.cielicki.dominik.allergyapprestapi.db.User;
import com.cielicki.dominik.allergyapprestapi.db.model.MessagesList;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasa implementująca zachowanie w oknie chatu.
 */
public class ChatFragment extends Fragment {

    private MessagesViewModel messagesViewModel;
    private ChatFragmentBinding binding;
    private HomeViewModel mHomeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mHomeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        messagesViewModel = new ViewModelProvider(getActivity()).get(MessagesViewModel.class);

        binding = ChatFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final RecyclerView recyclerView = binding.chatRecycler;

        // Aktualizacja recylcerView po otrzymaniu nowych wiadomości.
        messagesViewModel.getMessagesList().observe(getViewLifecycleOwner(), new Observer<MessagesList>() {
            @Override
            public void onChanged(MessagesList messagesList) {
                MessageListAdapter messageListAdapter = new MessageListAdapter(messagesList, messagesViewModel, mHomeViewModel);
                recyclerView.setLayoutManager(new LinearLayoutManager(ChatFragment.this.getContext()));
                recyclerView.setAdapter(messageListAdapter);
                recyclerView.scrollToPosition(messagesList.getMessagesList().size() - 1);
            }
        });

        messagesViewModel.getMessages(messagesViewModel.getCurrentChat());

        if (messagesViewModel.getCurrentChat() != null) {
            if (messagesViewModel.getCurrentChat().getUser2().getId() == User.GLOBAL_CHAT_USER.getId()) {
                binding.toolbarLabel.setTitle("Forum: " + messagesViewModel.getCurrentChat().getSubject());

            } else {
                binding.toolbarLabel.setTitle(messagesViewModel.getCurrentChat().getSubject());
            }
        }

        Button sendButton = binding.messageSendButton;

        // Wysyłanie wiadomości.
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = binding.messageTextView.getText().toString();

                if (messageText.isEmpty()) {
                    return;
                }

                Messages messages = new Messages();
                messages.setDate(new Date());

                Chat currentChat = messagesViewModel.getCurrentChat();

                if (currentChat.getId() == null) {
                    messagesViewModel.saveChat(currentChat, chat -> {
                        messages.setChat(chat);

                        sendMessage(messages, messageText);
                    });

                } else {
                    messages.setChat(currentChat);

                    sendMessage(messages, messageText);
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void sendMessage(Messages messages, String messageText) {
        messages.setSender(mHomeViewModel.getCurrentUser().getValue());
        messages.setRecipient(messages.getChat().getRecipient(mHomeViewModel.getCurrentUser().getValue()));
        messages.setMessage(messageText);
        messages.setStatus(0L);
        binding.messageTextView.setText("");

        try {
            JSONObject messageJsonObject = new JSONObject(Utils.getGson().toJson(messages));

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, Endpoints.MESSAGES.getEndpointUrl() + "/addMessage", messageJsonObject, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            messagesViewModel.addMessage(Utils.getGson().fromJson(response.toString(), Messages.class));
                            messagesViewModel.getCurrentChat().setLastMessage(Utils.getGson().fromJson(response.toString(), Messages.class));
                            messagesViewModel.getChatList().setValue(messagesViewModel.getChatList().getValue());
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            messagesViewModel.addMessage(messages);
//                            messagesViewModel.getCurrentChat().setLastMessage(messages);
//                            messagesViewModel.getChatList().setValue(messagesViewModel.getChatList().getValue());
                            // TODO: Handle error
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("X-ALLERGY-APP", "RNQdP1DabMnp8n0w");

                    return headers;
                }
            };

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000, 0, 0));

            RequestQueueSingleton.getInstance(getContext()).addToQueue(jsonObjectRequest);
        } catch (JSONException e) {
            // Do nothing
        }
    }
}