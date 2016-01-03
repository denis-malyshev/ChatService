package com.teamdev.webapp;

import com.teamdev.business.AuthenticationService;
import com.teamdev.business.ChatRoomService;
import com.teamdev.business.MessageService;
import com.teamdev.business.UserService;
import com.teamdev.business.impl.dto.ChatRoomDTO;
import com.teamdev.business.impl.dto.UserDTO;
import com.teamdev.business.impl.exception.AuthenticationException;
import com.teamdev.business.tinytypes.ChatRoomId;
import com.teamdev.business.tinytypes.Token;
import com.teamdev.business.tinytypes.UserId;
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

    private ContextProvider contextProvider;

    @Override
    public void init() throws ServletException {

        contextProvider = ContextProvider.getInstance();

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

        ChatRoomService chatRoomService = contextProvider.getContext().getBean(ChatRoomService.class);

        Collection<ChatRoomDTO> chatRoomDTOs = chatRoomService.findAll();
        for (ChatRoomDTO chatRoomDTO : chatRoomDTOs) {
            printWriter.println("<H1>" + chatRoomDTO.toString() + "</H1>");
        }
    }

    private void generateSampleData() throws AuthenticationException {

        ChatRoomService chatRoomService = contextProvider.getContext().getBean(ChatRoomService.class);

        ChatRoomDTO chatRoomDTO = chatRoomService.create(new ChatRoom("TestRoom"));

        chatRoomService.create(new ChatRoom("TestRoom1"));
        chatRoomService.create(new ChatRoom("test1"));
        chatRoomService.create(new ChatRoom("test2"));

        UserService userService = contextProvider.getContext().getBean(UserService.class);

        UserDTO userDTO1 = userService.register(new User("Vasya", "vasya@gmail.com", "pwd"));
        UserDTO userDTO2 = userService.register(new User("Masha", "masha@gmai.com", "pwd1"));

        AuthenticationService tokenService = contextProvider.getContext().getBean(AuthenticationService.class);

        Token token1 = tokenService.login("vasya@gmail.com", "pwd");

        UserId id1 = new UserId(userDTO1.getId());
        UserId id2 = new UserId(userDTO2.getId());

        chatRoomService.joinToChatRoom(token1, new UserId(userDTO1.getId()), new ChatRoomId(chatRoomDTO.getId()));

        MessageService messageService = contextProvider.getContext().getBean(MessageService.class);

        messageService.sendPrivateMessage(token1, id1, id2, "Hello, Masha!");
    }
}
