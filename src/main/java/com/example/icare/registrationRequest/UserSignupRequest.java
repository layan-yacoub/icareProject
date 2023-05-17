package com.example.icare.registrationRequest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSignupRequest {
   private String firstName ;
   private String lastName ;
   private String email  ;
   private String password ;

}
