package com.cielicki.dominik.allergyapp.ui.messages;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cielicki.dominik.allergyapp.common.SettingsEnum;
import com.cielicki.dominik.allergyapp.common.Utils;
import com.cielicki.dominik.allergyapp.data.Endpoints;
import com.cielicki.dominik.allergyapp.data.RequestQueueSingleton;
import com.cielicki.dominik.allergyapprestapi.db.Chat;
import com.cielicki.dominik.allergyapprestapi.db.Messages;
import com.cielicki.dominik.allergyapprestapi.db.User;
import com.cielicki.dominik.allergyapprestapi.db.UserSettings;
import com.cielicki.dominik.allergyapprestapi.db.model.ChatList;
import com.cielicki.dominik.allergyapprestapi.db.model.MessagesList;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasa służąca do przechowywania informacji, których nie chcemy wielokrotnie pobierać.
 * Na przykład czy zmianie orientacji telefonu.
 */
public class MessagesViewModel extends ViewModel {

    private MutableLiveData<ChatList> chatList = new MutableLiveData<ChatList>();
    private MutableLiveData<Chat> currentChat = new MutableLiveData<Chat>();
    private MutableLiveData<MessagesList> messagesList = new MutableLiveData<MessagesList>();
    private HashMap<Long, MutableLiveData<MessagesList>> allMessages = new HashMap<>();
    private MutableLiveData<Thread> messageCheckingThreadLiveData = new MutableLiveData<>();
    private HashMap<SettingsEnum, UserSettings> currentUserSetting;
    private MutableLiveData<Messages> lastMessage = new MutableLiveData<Messages>();
    private User currentUser = null;
    private ChatListAdapter chatListAdapter;
    private Application application;

    {
        lastMessage.setValue(new Messages());
        lastMessage.getValue().setId(-1L);
    }

    public MessagesViewModel() {
    }

    public MessagesViewModel(@NonNull Application application) {
        this.application = application;
    }

    public MutableLiveData<ChatList> getChatList() {
        return chatList;
    }

    public MutableLiveData<MessagesList> getMessagesList() {
        return messagesList;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setCurrentUserSetting(HashMap<SettingsEnum, UserSettings> currentUserSetting) {
        this.currentUserSetting = currentUserSetting;
    }

    public HashMap<SettingsEnum, UserSettings> getCurrentUserSetting() {
        return this.currentUserSetting;
    }

    public void addMessage(Messages messages) {
        MessagesList list = messagesList.getValue();

        if (! list.getMessagesList().contains(messages)) {
            messagesList.getValue().getMessagesList().add(messages);
            messagesList.setValue(messagesList.getValue());
        }
    }

    public MutableLiveData<Messages> getLastMessage() {
        return lastMessage;
    }

    public HashMap<Long, MutableLiveData<MessagesList>> getAllMessages() {
        return this.allMessages;
    }

    public void setCurrentChat(Chat currentChat) {
        this.currentChat.setValue(currentChat);

        if (currentChat.getId() != null && ! chatList.getValue().getChatList().contains(currentChat)) {
            if (! allMessages.containsKey(currentChat.getId())) {
                allMessages.put(currentChat.getId(), new MutableLiveData<>());
                allMessages.get(currentChat.getId()).setValue(new MessagesList());
                messagesList.setValue(allMessages.get(currentChat.getId()).getValue());
            }

            chatList.getValue().getChatList().add(currentChat);
            chatList.setValue(chatList.getValue());
        }
    }

    public Chat getCurrentChat() {
        return this.currentChat.getValue();
    }

    public void getMessages(Chat chat) {
        if (chat == null) {
            return;
        }

        if (allMessages.containsKey(chat.getId())) {
            messagesList.setValue(allMessages.get(chat.getId()).getValue());

        } else {
            allMessages.put(chat.getId(), new MutableLiveData<>());
            allMessages.get(chat.getId()).setValue(new MessagesList());
            messagesList.setValue(allMessages.get(chat.getId()).getValue());
        }
    }

    /**
     * Pobiera wiadomości z serwera dla podanego chatu.
     *
     * @param chat Obiekt chatu.
     */
    public void fetchMessages(Chat chat) {
        try {
            JSONObject chatJsonObject = new JSONObject(Utils.getGson().toJson(chat));

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, Endpoints.MESSAGES.getEndpointUrl() + "/getMessagesByChat", chatJsonObject, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            MessagesList messages = Utils.getGson().fromJson(response.toString(), MessagesList.class);

                            if (! allMessages.containsKey(chat.getId())) {
                                MutableLiveData<MessagesList> messagesLiveData = new MutableLiveData<>();
                                messagesLiveData.setValue(messages);
                                allMessages.put(chat.getId(), messagesLiveData);

                            } else {
                                allMessages.get(chat.getId()).setValue(messages);
                            }

                            if (messages.getMessagesList().size() > 0) {
                                int lastMessageIndex = messages.getMessagesList().size() - 1;
                                chat.setLastMessage(messages.getMessagesList().get(lastMessageIndex));
                                chatList.setValue(chatList.getValue());
                            }

                            for (Messages messages1: messages.getMessagesList()) {
                                if (messages1.getId() > lastMessage.getValue().getId()) {
                                    lastMessage.setValue(messages1);
                                }
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            messagesList = new MutableLiveData<MessagesList>();

                        }
                    }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("X-ALLERGY-APP", "RNQdP1DabMnp8n0w");

                    return headers;
                }
            };

            RequestQueueSingleton.getInstance(application).addToQueue(jsonObjectRequest);
        } catch (JSONException e) {
            // Do nothing
        }
    }

    /**
     * Pobiera chaty dla podanego użytkownika.
     *
     * @param user Obiekt użytkownika.
     */
    public void fetchChats(User user) {
        try {
            JSONObject userJsonObject = new JSONObject(Utils.getGson().toJson(user));

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, Endpoints.CHAT.getEndpointUrl() + "/getChatsForUser", userJsonObject, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            ChatList chats = Utils.getGson().fromJson(response.toString(), ChatList.class);
                            chatList.setValue(chats);

                            for (Chat chat: chats.getChatList()) {
                                fetchMessages(chat);
                            }

                            startMessageChecking();
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            chatList = new MutableLiveData<ChatList>();

                        }
                    }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("X-ALLERGY-APP", "RNQdP1DabMnp8n0w");

                    return headers;
                }
            };

            RequestQueueSingleton.getInstance(application).addToQueue(jsonObjectRequest);
        } catch (JSONException e) {
            // Do nothing
        }
    }

    /**
     * Pobieranie chatów dla podanego użytkownika i globalnych.
     *
     * @param user Obiekt użytkownika.
     */
    public void fetchChatsForUserAndGlobal(User user) {
        try {
            JSONObject userJsonObject = new JSONObject(Utils.getGson().toJson(user));

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, Endpoints.CHAT.getEndpointUrl() + "/getChatsForUserAndGlobal", userJsonObject, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            ChatList chats = Utils.getGson().fromJson(response.toString(), ChatList.class);
                            chatList.setValue(chats);

                            for (Chat chat: chats.getChatList()) {
                                fetchMessages(chat);
                            }

                            startMessageChecking();
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            chatList = new MutableLiveData<ChatList>();

                        }
                    }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("X-ALLERGY-APP", "RNQdP1DabMnp8n0w");

                    return headers;
                }
            };

            RequestQueueSingleton.getInstance(application).addToQueue(jsonObjectRequest);
        } catch (JSONException e) {
            // Do nothing
        }
    }

    /**
     * Zapisywanie chatu do bazy danych.
     *
     * @param chat Obiekt chatu.
     */
    public void saveChat(Chat chat, RequestQueueSingleton.VolleyChatCallback callback) {
        try {
            JSONObject userJsonObject = new JSONObject(Utils.getGson().toJson(chat));

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, Endpoints.CHAT.getEndpointUrl() + "/addChat", userJsonObject, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Chat chat = Utils.getGson().fromJson(response.toString(), Chat.class);
                            setCurrentChat(chat);

                            callback.onSuccess(chat);
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                        }
                    }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("X-ALLERGY-APP", "RNQdP1DabMnp8n0w");

                    return headers;
                }
            };

            RequestQueueSingleton.getInstance(application).addToQueue(jsonObjectRequest);
        } catch (JSONException e) {
            // Do nothing
        }
    }

    /**
     * Uruchamia wątek pobierający nowe wiadomości.
     */
    private void startMessageChecking() {
        if (messageCheckingThreadLiveData.getValue() == null) {
            MessageChecker messageCheckingThread = new MessageChecker(application, currentUser, this);

            messageCheckingThread.start();
            messageCheckingThreadLiveData.setValue(messageCheckingThread);
        }
    }

    public void setChatListAdapter(ChatListAdapter chatListAdapter) {
        this.chatListAdapter = chatListAdapter;
    }

    public ChatListAdapter getChatListAdapter() {
        return this.chatListAdapter;
    }
}

/**
 * Wątek pobierający nowe wiadmości z serwera.
 */
class MessageChecker extends Thread {
    MutableLiveData<ChatList> chatList;
    HashMap<Long, MutableLiveData<MessagesList>> allMessages;
    Application application;
    MutableLiveData<MessagesList> currentMessagesList;
    MutableLiveData<Messages> lastMessage;
    User currentUser;
    ChatListAdapter chatListAdapter;
    MessagesViewModel messagesViewModel;

    public MessageChecker(Application application, User currentUser, MessagesViewModel messagesViewModel) {
        super("AllergyAppMessageChecker");

        this.application = application;
        this.currentUser = currentUser;
        this.messagesViewModel = messagesViewModel;
    }

    public void run() {
        try {
            while (true) {
                Thread.sleep(5000);

                try {
                    lastMessage = messagesViewModel.getLastMessage();
                    // getNewMessagesForChat
                    JSONObject userJsonObject = new JSONObject(Utils.getGson().toJson(currentUser));
                    JSONObject array = new JSONObject();
                    array.put("user", userJsonObject);
                    JSONObject messageJsonObject = new JSONObject(Utils.getGson().toJson(lastMessage.getValue()));
                    array.put("message", messageJsonObject);

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.POST, Endpoints.MESSAGES.getEndpointUrl() + "/getNewMessagesForUser", array, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    MessagesList messages = Utils.getGson().fromJson(response.toString(), MessagesList.class);

                                    for (Messages messages1: messages.getMessagesList()) {
                                        Long chatId = messages1.getChat().getId();

                                        allMessages = messagesViewModel.getAllMessages();
                                        HashMap<Long, MutableLiveData<MessagesList>> debugallMessages = allMessages;
                                        chatList = messagesViewModel.getChatList();
                                        currentMessagesList = messagesViewModel.getMessagesList();
                                        chatListAdapter = messagesViewModel.getChatListAdapter();

                                        if (! allMessages.containsKey(chatId)) {
                                            MutableLiveData<MessagesList> messagesLiveData = new MutableLiveData<>();
                                            MessagesList newMessagesList = new MessagesList();
                                            newMessagesList.getMessagesList().add(messages1);
                                            messagesLiveData.setValue(newMessagesList);
                                            allMessages.put(chatId, messagesLiveData);
                                            chatList.getValue().getChatList().add(messages1.getChat());
                                            chatList.setValue(chatList.getValue());

                                        } else {
                                            if (! allMessages.get(chatId).getValue().getMessagesList().contains(messages1)) {
                                                allMessages.get(chatId).getValue().getMessagesList().add(messages1);
                                            }

                                            currentMessagesList.postValue(allMessages.get(chatId).getValue());

                                            for (int i = 0; i < chatList.getValue().getChatList().size(); i++) {
                                                Chat chat = chatList.getValue().getChatList().get(i);

                                                if (chat.getId().equals(chatId)) {
                                                    chat.setLastMessage(messages1);
                                                    chatListAdapter.notifyItemChanged(i);
                                                }
                                            }

//                                            for (Chat chat: chatList.getValue().getChatList()) {
//                                                if (chat.getId().equals(chatId)) {
//                                                    chat.setLastMessage(messages1);
//                                                }
//                                            }
                                        }

                                        lastMessage.setValue(messages1);
                                    }
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // Do nothing
                                }
                            }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> headers = new HashMap<>();
                            headers.put("X-ALLERGY-APP", "RNQdP1DabMnp8n0w");

                            return headers;
                        }
                    };

                    RequestQueueSingleton.getInstance(application).addToQueue(jsonObjectRequest);
                } catch (JSONException e) {
                    // Do nothing
                }
                }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}