package com.bridgelabz.fundoonotes.noteutility;

public interface JWTGenerator
{ 
	String generateToken(String id);

	int verifyToken(String token);

}