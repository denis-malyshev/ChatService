package com.teamdev.business.implement;

import com.teamdev.business.ChatRoomService;
import com.teamdev.business.implement.error.AuthenticationError;
import com.teamdev.persistence.ChatRoomRepository;
import com.teamdev.persistence.UserRepository;
import com.teamdev.persistence.dom.ChatRoom;
import com.teamdev.persistence.dom.User;
import com.teamdev.persistence.repository.RepositoryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("chatService")
public class ChatRoomServiceImpl implements ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationServiceImpl authenticationService;
    private long count = 0;

    public ChatRoomServiceImpl() {
    }

    public ChatRoomServiceImpl(RepositoryFactory repositoryFactory,
                               AuthenticationServiceImpl authenticationService) {
        this.chatRoomRepository = repositoryFactory.getChatRoomRepository();
        this.authenticationService = authenticationService;
        this.userRepository = repositoryFactory.getUserRepository();
    }

    public void create(ChatRoom chatRoom) {
        chatRoom.setId(count++);
        chatRoomRepository.update(chatRoom);
    }

    public void joinToChatRoom(String token,
                               long userId, long chatRoomId) throws AuthenticationError {

        authenticationService.isValid(token);

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId);
        User user = userRepository.findById(userId);

        user.getChatRooms().add(chatRoom);
        chatRoom.getUsers().add(user);
    }

    public void leaveChatRoom(String token,
                              long userId, long chatRoomId) throws AuthenticationError {

        authenticationService.isValid(token);

        User user = userRepository.findById(userId);
        if (user == null) {
            return;
        }
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId);

        user.getChatRooms().remove(chatRoom);
        chatRoom.getUsers().remove(user);
    }

    public void setChatRoomRepository(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void setAuthenticationService(AuthenticationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }
}
