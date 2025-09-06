package blog.com.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="User")
@Builder
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long userId;

   @NotBlank(message = "Name is required")
   private String name;
   @NotBlank(message = "Email is requires")
   @Column(unique = true,nullable = false)
   @Email(message="Invalid email format")
   
   private String  email;
   @NotBlank(message = "Password is required")
//   @Column(unique = false,nullable = false, columnDefinition = "TEXT")

   private String password;
   @OneToMany(mappedBy = "author",cascade = CascadeType.ALL,orphanRemoval = true)
   @JsonIgnore
   private List<Post> posts=new ArrayList<>();
   @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
   @JsonIgnore
   private List<Reaction>reactions=new ArrayList<>();
	@Enumerated(EnumType.STRING)
   private Role role;
	


	
	
	
   
   
   
}
