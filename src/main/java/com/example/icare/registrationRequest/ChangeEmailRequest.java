package com.example.icare.registrationRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeEmailRequest {
    Long nutritionistId;
    String password;
    String newEmail;
}
