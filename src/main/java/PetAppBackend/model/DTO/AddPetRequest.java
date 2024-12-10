package PetAppBackend.model.DTO;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddPetRequest {
    private String name;
    private String typeName;
    private int age;

    public String getName() {
        return name;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setAge(int age) {
        this.age = age;
    }
}