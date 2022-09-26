package com.cielicki.dominik.allergyapp.ui.messages;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cielicki.dominik.allergyapp.R;
import com.cielicki.dominik.allergyapp.databinding.FragmentMessagesBinding;
import com.cielicki.dominik.allergyapp.ui.home.HomeViewModel;
import com.cielicki.dominik.allergyapprestapi.db.Chat;
import com.cielicki.dominik.allergyapprestapi.db.Question;
import com.cielicki.dominik.allergyapprestapi.db.User;
import com.cielicki.dominik.allergyapprestapi.db.model.ChatList;

/**
 * Klasa implementująca zachowanie zakladki wiadmości.
 */
public class MessagesFragment extends Fragment {

    private MessagesViewModel messagesViewModel;
    private HomeViewModel mHomeViewModel;

    private FragmentMessagesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        messagesViewModel = new ViewModelProvider(getActivity()).get(MessagesViewModel.class);
        mHomeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);

        binding = FragmentMessagesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final RecyclerView recyclerView = binding.messagesRecyclerView;

        messagesViewModel.getChatList().observe(getViewLifecycleOwner(), new Observer<ChatList>() {
            @Override
            public void onChanged(ChatList chatList) {
                ChatListAdapter chatListAdapter = new ChatListAdapter(chatList, messagesViewModel, mHomeViewModel);
                recyclerView.setLayoutManager(new LinearLayoutManager(MessagesFragment.this.getContext()));
                recyclerView.setAdapter(chatListAdapter);
                messagesViewModel.setChatListAdapter(chatListAdapter);
            }
        });

        messagesViewModel.fetchChatsForUserAndGlobal(mHomeViewModel.getCurrentUser().getValue());
        messagesViewModel.setCurrentUserSetting(mHomeViewModel.getUserSettingsHashMap());
        messagesViewModel.setCurrentUser(mHomeViewModel.getCurrentUser().getValue());

        binding.askQuestionButton.setOnClickListener(view -> {
            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.listview_dialog);
            dialog.setTitle("Wybierz temat");
            ListView list = dialog.findViewById(R.id.dialog_list);
            ArrayAdapter<Question> adapter = new ArrayAdapter<Question>(getContext(), R.layout.simple_spinner_item);
            adapter.addAll(mHomeViewModel.getQuestionList().getValue().getQuestionList());
            list.setAdapter(adapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Question question = (Question) parent.getItemAtPosition(position);
                    Chat currentChat = null;

                    for (Chat chat : messagesViewModel.getChatList().getValue().getChatList()) {
                        if (chat.getUser2().getId().compareTo(question.getDesignatedUser().getId()) == 0 && chat.getSubject().equals(question.getSubject())) {
                            currentChat = chat;
                            break;
                        }
                    }

                    if (currentChat == null) {
                        currentChat = new Chat();
                        currentChat.setSubject(question.getSubject());
                        currentChat.setUser(mHomeViewModel.getCurrentUser().getValue());
                        currentChat.setUser2(question.getDesignatedUser());
                    }

                    messagesViewModel.setCurrentChat(currentChat);

                    Fragment fragment = ((AppCompatActivity) getContext()).getSupportFragmentManager().getFragments().get(0);
                    FragmentManager fragmentManager = fragment.getChildFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.messages_fragment, ChatFragment.class, null).addToBackStack("chat").setReorderingAllowed(true).commit();

                    dialog.hide();
                }
            });

            dialog.show();
        });

        binding.askQuestionGeneralButton.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Podaj temat pytania");

            EditText input = new EditText(getContext());
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            builder.setView(input);

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Chat currentChat = new Chat();
                    currentChat.setSubject(input.getText().toString());
                    currentChat.setUser(mHomeViewModel.getCurrentUser().getValue());
                    currentChat.setUser2(User.GLOBAL_CHAT_USER);

                    messagesViewModel.setCurrentChat(currentChat);

                    Fragment fragment = ((AppCompatActivity) getContext()).getSupportFragmentManager().getFragments().get(0);
                    FragmentManager fragmentManager = fragment.getChildFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.messages_fragment, ChatFragment.class, null).addToBackStack("chat").setReorderingAllowed(true).commit();
                }
            });

            builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}