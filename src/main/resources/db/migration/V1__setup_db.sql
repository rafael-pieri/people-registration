CREATE TABLE person
(
    id UUID NOT NULL CONSTRAINT person_pk PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    cpf VARCHAR(20) NOT NULL,
    birth DATE,
    CONSTRAINT cpf_unique UNIQUE (cpf)
);

CREATE TABLE address
(
    id uuid NOT NULL CONSTRAINT address_pk PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    number NUMERIC NOT NULL,
    complement VARCHAR(60),
    district VARCHAR(60) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(60) NOT NULL,
    country VARCHAR(60) NOT NULL,
    person_id uuid NOT NULL,
    CONSTRAINT person_id_fk FOREIGN KEY (person_id) REFERENCES person(id)
);