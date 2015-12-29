package com.teamdev.webapp;

import com.teamdev.business.impl.dto.ChatRoomDTO;
import com.teamdev.business.impl.dto.UserDTO;
import com.teamdev.business.impl.exception.AuthenticationException;
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
        } catch (AuthenticationException authenticationException) {
            authenticationException.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=utf-8");

        PrintWriter printWriter = resp.getWriter();

        Collection<ChatRoomDTO> chatRoomDTOs = services.getChatRoomService().findAll();
        for (ChatRoomDTO chatRoomDTO : chatRoomDTOs) {
            printWriter.println("<H1>" + chatRoomDTO.toString() + "</H1>");
        }
    }

    private void generateSampleData() throws AuthenticationException {

        ChatRoomDTO chatRoomDTO = services.getChatRoomService().create(new ChatRoom("TestRoom"));
        services.getChatRoomService().create(new ChatRoom("TestRoom1"));
        services.getChatRoomService().create(new ChatRoom("test1"));
        services.getChatRoomService().create(new ChatRoom("test2"));

        UserDTO userDTO1 = services.getUserService().register(new User("Vasya", "vasya@gmail.com", "pwd"));
        UserDTO userDTO2 = services.getUserService().register(new User("Masha", "masha@gmai.com", "pwd1"));

        String token1 = services.getTokenService().login("vasya@gmail.com", "pwd");
        String token2 = services.getTokenService().login("masha@gmai.com", "pwd1");

        services.getChatRoomService().joinToChatRoom(token1, userDTO1.getId(), chatRoomDTO.getId());

        services.getMessageService().sendPrivateMessage(token1, "Hello, Masha!", userDTO1.getId(), userDTO2.getId());
        services.getMessageService().sendPrivateMessage(token2, "Hello, Vasya", userDTO1.getId(), userDTO2.getId());
        services.getMessageService().sendPrivateMessage(token1, "How are you?", userDTO1.getId(), userDTO2.getId());
        services.getMessageService().sendPrivateMessage(token2, "I'm fine. And you?", userDTO1.getId(), userDTO2.getId());
    }
}
