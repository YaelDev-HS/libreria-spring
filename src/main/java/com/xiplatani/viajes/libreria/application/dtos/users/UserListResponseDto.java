package com.xiplatani.viajes.libreria.application.dtos.users;

import java.util.List;

public class UserListResponseDto {
    private List<UserDto> users;

    public UserListResponseDto() {
    }

    public UserListResponseDto(List<UserDto> users) {
        this.users = users;
    }

    public List<UserDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }
}
