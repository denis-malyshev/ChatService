package com.teamdev.business.implement;

import com.teamdev.business.AuthenticationService;
import com.teamdev.business.ChatRoomService;
import com.teamdev.persistence.dom.AuthenticationToken;
import com.teamdev.persistence.dom.ChatRoom;
import com.teamdev.persistence.dom.Message;
import com.teamdev.persistence.dom.User;
import com.teamdev.persistence.repository.ChatRoomRepository;
import com.teamdev.persistence.repository.ChatRoomRepositoryImpl;
import com.teamdev.persistence.repository.MessageRepository;

public class ChatRoomServiceImpl implements ChatRoomService<ChatRoom, AuthenticationToken> {

    private ChatRoomRepository repository = new ChatRoomRepositoryImpl();
    private MessageRepository messageRepositoryImpl;
    private AuthenticationService authenticationService;
    private long count = 0;

    public ChatRoomServiceImpl(MessageRepository messageRepositoryImpl, AuthenticationService authenticationService) {
        this.messageRepositoryImpl = messageRepositoryImpl;
        this.authenticationService = authenticationService;
    }

    public boolean register(ChatRoom chatRoom) {
        chatRoom.setId(count++);
        repository.update(chatRoom);
        return true;
    }

    public void joinToChatRoom(AuthenticationToken token, User user, long chatRoomId) throws Exception {

        authenticationService.isValid(token);

        repository.findById(chatRoomId).getUsers().add(user);
    }

    public void sendMessage(AuthenticationToken token, String text, User user, ChatRoom chatRoom) throws Exception {

        authenticationService.isValid(token);

        final Message message = new Message(text, user, chatRoom);
        messageRepositoryImpl.update(message);
        repository.findById(chatRoom.getId()).getMessages().add(message);
    }

    public void leaveChatRoom(AuthenticationToken token, long userId, long chatRoomId) throws Exception {

        authenticationService.isValid(token);

        repository.findById(chatRoomId).getUsers().remove(userId);
    }

}
