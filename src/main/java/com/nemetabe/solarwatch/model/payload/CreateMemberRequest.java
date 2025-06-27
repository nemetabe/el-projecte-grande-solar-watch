package com.nemetabe.solarwatch.model.payload;

import lombok.Data;

@Data
public class CreateMemberRequest {
    private String username;
    private String password;
}
