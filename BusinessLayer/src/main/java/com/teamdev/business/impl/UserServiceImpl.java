package com.teamdev.business.impl;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.teamdev.business.UserService;
import com.teamdev.business.impl.dto.ChatRoomDTO;
import com.teamdev.business.impl.dto.UserDTO;
import com.teamdev.business.impl.exception.AuthenticationException;
import com.teamdev.business.tinytypes.UserEmail;
import com.teamdev.business.tinytypes.UserId;
import com.teamdev.business.tinytypes.UserName;
import com.teamdev.business.tinytypes.UserPassword;
import com.teamdev.persistence.UserRepository;
import com.teamdev.persistence.dom.ChatRoom;
import com.teamdev.persistence.dom.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private HashFunction hashFunction = Hashing.md5();

    public UserServiceImpl() {
    }

    public UserDTO register(UserName name, UserEmail email, UserPassword password) throws AuthenticationException {

        if (userRepository.userCount() > 0 && userRepository.findByMail(email.getEmail()) != null) {
            throw new AuthenticationException("User with the same mail already exists.");
        }

        String passwordHash = hashFunction.newHasher().putString(password.getPassword(), Charset.defaultCharset()).hash().toString();

        User user = new User(name.getName(), email.getEmail(), passwordHash);
        userRepository.update(user);
        return new UserDTO(user.getId(), user.getFirstName(), user.getEmail());
    }

    @Override
    public UserDTO findById(UserId userId) {

        User user = userRepository.findById(userId.getId());

        if (user == null)
            return null;

        return new UserDTO(userId.getId(), user.getFirstName(), user.getEmail());
    }

    @Override
    public Set<ChatRoomDTO> findAvailableChats(UserId userId) {
        Set<ChatRoom> chatRooms = userRepository.findById(userId.getId()).getChatRooms();
        Set<ChatRoomDTO> chatRoomDTOs = new HashSet<>();
        for (ChatRoom chatRoom : chatRooms) {
            chatRoomDTOs.add(new ChatRoomDTO(chatRoom.getId(),chatRoom.getName(),chatRoom.getUsers().size(),chatRoom.getMessages().size()));
        }
        return chatRoomDTOs;
    }
}
