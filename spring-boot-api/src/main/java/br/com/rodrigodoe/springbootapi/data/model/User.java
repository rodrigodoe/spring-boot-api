package br.com.rodrigodoe.springbootapi.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class User implements UserDetails, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "user_name", unique = true)
	private String userName;
	@Column(name = "full_name", unique = true)
	private String fullName;
	@Column(name = "password")
	private String password;
	@Column(name = "account_non_expired")
	private Boolean accountNonExpired;
	@Column(name = "account_non_locked")
	private Boolean accountNonlocked;
	@Column(name = "credentials_non_expired")
	private Boolean credentialsNonExpired;
	@Column(name = "enabled")
	private Boolean enabled;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_permission", joinColumns = { @JoinColumn(name = "id_user") }, inverseJoinColumns = {
			@JoinColumn(name = "id_permission") })
	private List<Permission> permisions;

	public List<String> getRoles() {
		List<String> roles = new ArrayList<>();
		for (Permission permission : this.permisions) {
			roles.add(permission.getDescription());
		}
		return roles;
	}
	
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.permisions;
	}
	
	@Override
	public String getUsername() {
		return this.userName;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	
	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonlocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getFullName() {
		return fullName;
	}



	public void setFullName(String fullName) {
		this.fullName = fullName;
	}



	public Boolean getAccountNonExpired() {
		return accountNonExpired;
	}



	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}



	public Boolean getAccountNonlocked() {
		return accountNonlocked;
	}



	public void setAccountNonlocked(Boolean accountNonlocked) {
		this.accountNonlocked = accountNonlocked;
	}



	public Boolean getCredentialsNonExpired() {
		return credentialsNonExpired;
	}



	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}



	public Boolean getEnabled() {
		return enabled;
	}



	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}



	public List<Permission> getPermisions() {
		return permisions;
	}



	public void setPermisions(List<Permission> permisions) {
		this.permisions = permisions;
	}





	public void setPassword(String password) {
		this.password = password;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNonExpired == null) ? 0 : accountNonExpired.hashCode());
		result = prime * result + ((accountNonlocked == null) ? 0 : accountNonlocked.hashCode());
		result = prime * result + ((credentialsNonExpired == null) ? 0 : credentialsNonExpired.hashCode());
		result = prime * result + ((enabled == null) ? 0 : enabled.hashCode());
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((permisions == null) ? 0 : permisions.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (accountNonExpired == null) {
			if (other.accountNonExpired != null)
				return false;
		} else if (!accountNonExpired.equals(other.accountNonExpired))
			return false;
		if (accountNonlocked == null) {
			if (other.accountNonlocked != null)
				return false;
		} else if (!accountNonlocked.equals(other.accountNonlocked))
			return false;
		if (credentialsNonExpired == null) {
			if (other.credentialsNonExpired != null)
				return false;
		} else if (!credentialsNonExpired.equals(other.credentialsNonExpired))
			return false;
		if (enabled == null) {
			if (other.enabled != null)
				return false;
		} else if (!enabled.equals(other.enabled))
			return false;
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		if (id != other.id)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (permisions == null) {
			if (other.permisions != null)
				return false;
		} else if (!permisions.equals(other.permisions))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}



	
	
	

}
