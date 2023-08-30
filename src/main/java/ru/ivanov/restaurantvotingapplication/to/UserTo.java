package ru.ivanov.restaurantvotingapplication.to;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.ivanov.restaurantvotingapplication.HasIdAndEmail;
import ru.ivanov.restaurantvotingapplication.model.Role;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UserTo extends NamedTo implements HasIdAndEmail {
    @Email
    @NotBlank
    @Size(max = 128)
    String email;

    @NotBlank
    @Size(min = 5, max = 32)
    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
    String password;


    public UserTo(Integer id, String name, String email) {
        super(id, name);
        this.email = email;
    }

    public UserTo(Integer id, String name, String email, String password) {
        super(id, name);
        this.email = email;
        this.password = password;
    }


    @Override
    public String toString() {
        return "UserTo:" + id + '[' + email + ']';
    }
}
