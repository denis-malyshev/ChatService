package com.teamdev.webapp;

import com.teamdev.business.implement.dto.ChatRoomDto;
import com.teamdev.business.implement.error.AuthenticationError;
import com.teamdev.business.implement.model.ChatServiceOnSpring;
import com.teamdev.persistence.dom.ChatRoom;
import com.teamdev.persistence.dom.Message;
import com.teamdev.persistence.dom.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@WebServlet("/chats")
public class TestServlet extends HttpServlet {

    private ChatServiceOnSpring service;

    @Override
    public void init() throws ServletException {

        service = new ChatServiceOnSpring();

        try {
            generateSampleData();
        } catch (AuthenticationError authenticationError) {
            authenticationError.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=utf-8");

        PrintWriter pw = resp.getWriter();


        if (req.getParameterNames().hasMoreElements() && req.getParameter("token").equals("abc")) {
            Collection<ChatRoomDto> chatRoomDtos = service.chatRoomService.findAll();
            Iterator<ChatRoomDto> iterator = chatRoomDtos.iterator();
            while (iterator.hasNext()) {
                pw.println("<H1>" + iterator.next().toString() + "</H1>");

            }
        } else {
            resp.sendError(403, "Invalid token.");
        }
    }

    private void generateSampleData() throws AuthenticationError {

        ChatRoom chatRoom = new ChatRoom("TestRoom");

        service.chatRoomService.create(chatRoom);

        User user1 = new User("Vasya", "vasya@gmai.com", "pwd");
        User user2 = new User("Masha", "masha@gmai.com", "pwd1");

        service.userService.register(user1);
        service.userService.register(user2);

        service.tokenService.login("vasya@gmail.com","pwd");

        service.chatRoomService.joinToChatRoom(user1.getToken(),user1.getId(),chatRoom.getId());

        List<Message> messages = new ArrayList<>();
        messages.add(new Message("Hello, Masha!", user1, user2));
        messages.add(new Message("Hello, Vasya", user2, user1));
        messages.add(new Message("How are you?", user1, user2));
        messages.add(new Message("I'm fine. And you?", user2, user1));

        for (int i = 0; i < messages.size(); i++) {
            service.messageService.create(messages.get(i));
        }
    }
}
