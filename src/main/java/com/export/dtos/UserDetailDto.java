package com.export.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor
public class UserDetailDto implements Serializable {

    private static final long serialVersionUID = -4326872364976239L;

    private String secureId;

    private String name;

    private String email;

    private String password;

    private String roleNaname;

    private String roleDescription;
}
