CREATE TABLE customer_address (
	id BIGSERIAL NOT NULL,
	postal_code VARCHAR(8) NOT NULL,
	street VARCHAR(255) NOT NULL,
	number VARCHAR(50),
	complement VARCHAR(50),
	neighborhood VARCHAR(255) NOT NULL,
	city VARCHAR(255) NOT NULL,
	state VARCHAR(2) NOT NULL,
	CONSTRAINT pk_customer_address PRIMARY KEY (id)
);