package com.fxmxracingteam.userlib.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserTransactionDTO implements Serializable {
    private Integer transactionId;
    private UserDTO userDTO;
}
