package PetAppBackend.model.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 3,max = 15, message = "Username must contain between 3 and 15 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 3,message = "Password must have at least 3 characters")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
