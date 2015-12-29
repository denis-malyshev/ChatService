package com.teamdev.webapp;

import com.teamdev.business.implement.dto.ChatRoomDto;
import com.teamdev.business.implement.error.AuthenticationError;
import com.teamdev.persistence.dom.ChatRoom;
import com.teamdev.persistence.dom.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

public class TestServlet extends HttpServlet {

    private ServiceProvider services;

    @Override
    public void init() throws ServletException {

        services = ServiceProvider.getInstance();

        try {
            generateSampleData();
        } catch (AuthenticationError authenticationError) {
            authenticationError.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=utf-8");

        PrintWriter printWriter = resp.getWriter();

        Collection<ChatRoomDto> chatRoomDtos = services.getChatRoomService().findAll();
        for (ChatRoomDto chatRoomDto : chatRoomDtos) {
            printWriter.println("<H1>" + chatRoomDto.toString() + "</H1>");
        }
    }

    private void generateSampleData() throws AuthenticationError {

        ChatRoom chatRoom = new ChatRoom("TestRoom");

        services.getChatRoomService().create(new ChatRoom("TestRoom"));
        services.getChatRoomService().create(new ChatRoom("test1"));
        services.getChatRoomService().create(new ChatRoom("test2"));

        User user1 = new User("Vasya", "vasya@gmail.com", "pwd");
        User user2 = new User("Masha", "masha@gmai.com", "pwd1");

        services.getUserService().register(user1);
        services.getUserService().register(user2);

        services.getTokenService().login("vasya@gmail.com", "pwd");
        services.getTokenService().login("masha@gmai.com", "pwd1");

        services.getChatRoomService().joinToChatRoom(user1.getToken(), user1.getId(), chatRoom.getId());

        services.getMessageService().sendPrivateMessage(user1.getToken(), "Hello, Masha!", user1.getId(), user2.getId());
        services.getMessageService().sendPrivateMessage(user1.getToken(), "Hello, Vasya", user1.getId(), user2.getId());
        services.getMessageService().sendPrivateMessage(user1.getToken(), "How are you?", user1.getId(), user2.getId());
        services.getMessageService().sendPrivateMessage(user1.getToken(), "I'm fine. And you?", user1.getId(), user2.getId());
    }
}
