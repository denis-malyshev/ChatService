package com.teamdev.business;

import com.teamdev.business.impl.dto.ChatRoomDTO;
import com.teamdev.business.impl.dto.UserDTO;
import com.teamdev.business.impl.exception.AuthenticationException;
import com.teamdev.business.tinytypes.UserEmail;
import com.teamdev.business.tinytypes.UserId;
import com.teamdev.business.tinytypes.UserName;
import com.teamdev.business.tinytypes.UserPassword;

import java.util.Set;

public interface UserService {

    UserDTO register(UserName name, UserEmail email, UserPassword password) throws AuthenticationException;

    UserDTO findById(UserId userId);

    Set<ChatRoomDTO> findAvailableChats(UserId userId);
}
