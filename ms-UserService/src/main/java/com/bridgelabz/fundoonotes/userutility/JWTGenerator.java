package com.bridgelabz.fundoonotes.userutility;

public interface JWTGenerator 
{
	String generateToken(String id);

	int verifyToken(String token);
}


