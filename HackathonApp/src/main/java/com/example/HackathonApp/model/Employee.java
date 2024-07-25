package com.example.HackathonApp.model;

import java.util.Base64;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "employees")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;


	@Column(name = "first_name")
	@NotBlank(message = "Name is mandatory")
	@Size(min = 3, max = 50, message = "First Name must be between 3 and 50 characters")
    @Pattern(regexp = "^[^0-9]*$", message = "First Name cannot contain numbers")
	private String firstName;

	@Column(name = "last_name")
	@NotBlank(message = "Last name is mandatory")
	@Size(min = 3, max = 50, message = "Last Name must be between 3 and 50 characters")
    @Pattern(regexp = "^[^0-9]*$", message = "Last Name cannot contain numbers")
	private String lastName;
	
	
	@Column(name = "email_id")
	@NotBlank(message = "Email is mandatory")
	@Email
	private String emailId;


	@Column(name="skills")
	@NotBlank
	private String skills;

	@NotBlank
	@Column(name="experience")
	private String experience;

	@Lob
    @Column(name = "photo", columnDefinition = "LONGBLOB")
    private byte[] profilePhoto;

	public byte[] getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    // Method to get Base64 encoded profile photo
    public String getProfilePhotoBase64() {
        return profilePhoto != null ? Base64.getEncoder().encodeToString(profilePhoto) : null;
    }
}
	
