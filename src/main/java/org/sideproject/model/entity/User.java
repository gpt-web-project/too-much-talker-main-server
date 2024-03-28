package org.sideproject.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.sideproject.repository.UserRepository;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "users")
public class User extends Auditable{

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
    private String createdIp;

    @Column(nullable = false)
    private boolean isActive;

    public User(String username, String password, String createdIp){
        this.username = username;
        this.password = password;
        this.createdIp = createdIp;
        this.isActive = true;
    }

    public void withdrawal(){
        this.isActive = false;
    }

}
