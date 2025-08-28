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
   public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	public List<Reaction> getReactions() {
		return reactions;
	}
	public void setReactions(List<Reaction> reactions) {
		this.reactions = reactions;
	}
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
   private List<Reaction>reactions=new ArrayList<>();
	@Enumerated(EnumType.STRING)
   private Role role;
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
   
   
   
}
