package com.auth.modal;

import java.sql.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ecom_user")
@Getter
@Setter
public class UserModal {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_civic_id", length = 10)
	private UUID userCivicId;

	@Column(name = "user_name", length = 150)
	private String userName;

	@Column(name = "mobile")
	private String mobile;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "is_deleted", length = 1, columnDefinition = "int default 0")
	private int isDeleted;

	@CreationTimestamp
	@Column(name = "created_on", updatable = false)
	private Date createdOn;

	@UpdateTimestamp
	@Column(name = "modified_on")
	private Date modifiedOn;

}
