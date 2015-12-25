package com.teamdev.business;

import com.teamdev.business.implement.model.ChatService;
import com.teamdev.persistence.dom.AuthenticationToken;
import com.teamdev.persistence.dom.ChatRoom;
import com.teamdev.persistence.dom.User;
import com.teamdev.persistence.repository.RepositoryFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserServiceTest {

    RepositoryFactory repositoryFactory;
    ChatService service;
    AuthenticationToken token;
    ChatRoom chatRoom;
    User ivan;
    User masha;

    @Before
    public void setUp() throws Exception {
        repositoryFactory = new RepositoryFactory();
        service = new ChatService(repositoryFactory);
        token = new AuthenticationToken(0L);
        repositoryFactory.getTokenRepository().update(token);
        chatRoom = new ChatRoom("freeRoom");
        service.chatRoomService.create(chatRoom);
    }

    @Test
    public void registerUserIvan() throws Exception {
        ivan = new User("Ivan", "ivana@gmail.com", "pa$$vv0rd");
        service.userService.register(ivan);
        User actual = repositoryFactory.getUserRepository().findById(ivan.getId());
        assertNotNull("The user must exist", actual);
    }

    @Test
    public void registerUserMasha() throws Exception {
        masha = new User("Masha", "masha@gmail.com", "pa$$vv0rd1");
        service.userService.register(masha);
        User actual = repositoryFactory.getUserRepository().findById(masha.getId());
        assertNotNull("The user must exist", actual);
    }

//    @Test
//    public void sendPrivateMessageTestMessageCount() throws Exception {
//
//        registerUserIvan();
//        registerUserMasha();
//
//        service.messageService.sendPrivateMessage(token, "Hello", ivan.getId(), masha.getId());
//
//        int actual = repositoryFactory.getMessageRepository().count();
//        assertEquals("Count of message must be 1", 1, actual);
//    }
//
//    @Test
//    public void sendPrivateMessageInExistingPrivateChat() throws Exception {
//
//        registerUserIvan();
//        registerUserMasha();
//
//        service.messageService.sendPrivateMessage(token, "Hello", ivan.getId(), masha.getId());
//        service.messageService.sendPrivateMessage(token, "Hi", ivan.getId(), masha.getId());
//
//        StringBuilder chatRoomName = new StringBuilder();
//        chatRoomName.append("private-room-").append(ivan.getId()).append(masha.getId());
//
//        ChatRoom chatRoom = repositoryFactory.getChatRoomRepository().findByName(chatRoomName.toString());
//        int actual = chatRoom.getMessages().size();
//        assertEquals("Count of message must be 2", 2, actual);
//    }
}
