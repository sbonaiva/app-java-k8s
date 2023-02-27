CREATE TABLE customer (
	id BIGSERIAL NOT NULL,
	customer_id BIGINT,
	name VARCHAR(255) NOT NULL,
	CONSTRAINT pk_customer PRIMARY KEY (id),
	CONSTRAINT fk_customer_address FOREIGN KEY(customer_id) REFERENCES customer(id)
);