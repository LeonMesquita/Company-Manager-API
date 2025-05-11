package com.company_manager.api.models;

import com.company_manager.api.models.enums.ProfileEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String username;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER) // Garante que sempre que buscar os usuários do banco, vai buscar os perfis juntos
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Garante que não vai retornar para o usuário quais são os perfis dele
    @CollectionTable(name = "user_profile") // Define o nome que profiles vai ter no banco de dados
    @Column(name = "profile", nullable = false)
    private Set<Integer> profiles = new HashSet<>();

    // Mapeia a lista de profiles e retorna eles em forma de enum, ao invés de uma lista de inteiros
    public Set<ProfileEnum> getProfiles() {
        return this.profiles.stream().map(ProfileEnum::toEnum).collect(Collectors.toSet());
    }

    //Método para adicionar perfis ao usuario
    public void addProfile(ProfileEnum profileEnum) {
        this.profiles.add(profileEnum.getCode());
    }


}
