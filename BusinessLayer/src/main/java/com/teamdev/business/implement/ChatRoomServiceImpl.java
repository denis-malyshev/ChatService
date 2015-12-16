package com.teamdev.business.implement;

import com.teamdev.business.ChatRoomService;
import com.teamdev.persistence.dom.AuthenticationToken;
import com.teamdev.persistence.dom.ChatRoom;
import com.teamdev.persistence.dom.Message;
import com.teamdev.persistence.repository.ChatRoomRepository;
import com.teamdev.persistence.repository.ChatRoomRepositoryImpl;
import com.teamdev.persistence.repository.MessageRepository;
import com.teamdev.persistence.repository.MessageRepositoryImpl;

public class ChatRoomServiceImpl implements ChatRoomService<ChatRoom, AuthenticationToken> {

    private ChatRoomRepository repository = new ChatRoomRepositoryImpl();
    private MessageRepository messageRepositoryImpl;
    private long count = 0;

    public ChatRoomServiceImpl(MessageRepository messageRepositoryImpl) {
        this.messageRepositoryImpl = messageRepositoryImpl;
    }

    public boolean register(ChatRoom chatRoom) {
        chatRoom.setId(count++);
        repository.update(chatRoom);
        return true;
    }

    public void joinToChatRoom(AuthenticationToken token, long userId, long chatRoomId) {

        repository.findById(chatRoomId).getUsers().add(userId);
    }

    public void sendMessage(AuthenticationToken token, String text, long userId, long chatRoomId) {
        final Message message = new Message(text, userId, chatRoomId);
        messageRepositoryImpl.update(message);
        repository.findById(chatRoomId).getMessages().add(message);
    }

    public void leaveChatRoom(AuthenticationToken token, long userId, long chatRoomId) {
        repository.findById(chatRoomId).getUsers().remove(userId);
    }

    public ChatRoomRepository getRepository() {
        return repository;
    }

    public MessageRepository getMessageRepositoryImpl() {
        return messageRepositoryImpl;
    }
}
