package PetAppBackend.model.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterUserDTO {

    @NotNull
    @Size(min = 3,max = 15, message = "Username must contains between 3 and 15 characters")
    private String username;

    @NotNull
    @Email(message = "Email is not valid")
    private String email;

    @NotNull
    @Size(min = 3,message = "Password must have at least 3 characters")
    private String password;

    @NotNull
    @Size(min = 3,message = "Password must have at least 3 characters")
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
