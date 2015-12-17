package com.teamdev.business.implement;

import com.teamdev.business.ChatRoomService;
import com.teamdev.persistence.dom.AuthenticationToken;
import com.teamdev.persistence.dom.ChatRoom;
import com.teamdev.persistence.dom.Message;
import com.teamdev.persistence.dom.User;
import com.teamdev.persistence.repository.ChatRoomRepository;
import com.teamdev.persistence.repository.RepositoryFactory;

public class ChatRoomServiceImpl implements ChatRoomService<ChatRoom, AuthenticationToken> {

    private ChatRoomRepository repository;
    private MessageServiceImpl messageService;
    private AuthenticationServiceImpl authenticationService;
    private long count = 0;

    public ChatRoomServiceImpl(RepositoryFactory repositoryFactory,
                               AuthenticationServiceImpl authenticationService,MessageServiceImpl messageService) {
        this.repository=repositoryFactory.getChatRoomRepository();
        this.authenticationService = authenticationService;
        this.messageService = messageService;
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

        messageService.register(message);

        repository.findById(chatRoom.getId()).getMessages().add(message);
    }

    public void leaveChatRoom(AuthenticationToken token, User user, long chatRoomId) throws Exception {

        authenticationService.isValid(token);

        repository.findById(chatRoomId).getUsers().remove(user);
    }

}
