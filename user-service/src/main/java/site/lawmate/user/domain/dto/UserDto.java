package site.lawmate.user.domain.dto;

import java.util.List;

import lombok.Data;
import site.lawmate.user.domain.model.Issue;

@Data
public class UserDto {
    private String username;
    private String name;
    private String email;
    private String picture;
    private String password;
    // private Role role;
    private String phone;
    private String age;
    private String gender;
    private String token;
    private Long point;

    // private List<Question> questions;
    // private List<Payment> payments;
    private List<Issue> issues;

    
}
