package com.bridgelabz.fundoonotes.userdao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.usermodels.User;

@Repository
public interface UserDetailsRepository extends JpaRepository<User, Integer>
{

		Optional<User> findByEmailId(String emailId);

}
