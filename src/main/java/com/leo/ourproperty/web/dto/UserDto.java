package com.leo.ourproperty.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotBlank
    private String name;

    @NotBlank
    @Email(message = "Formato do e-mail inválido",  regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String email;

    @NotBlank
    @CPF
    private String cpf;

    @NotBlank
    private String password;

    @NotBlank
    @Pattern(regexp = "ADMIN|BROKER|CAPTATION", message = "Role must be one of: ADMIN, BROKER, CAPTATION")
    private String role;

}
