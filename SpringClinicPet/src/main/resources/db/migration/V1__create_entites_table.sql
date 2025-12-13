create table tb_users(
    id UUID PRIMARY KEY NOT NULL UNIQUE,
    login VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    enabled BOOLEAN NOT NULL,
    crvm VARCHAR(20) UNIQUE
);

create table tb_pet(

    id UUID PRIMARY KEY NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    breed VARCHAR(255) NOT NULL,
    species VARCHAR(20) NOT NULL,
    enabled BOOLEAN NOT NULL,
    owner_id UUID NOT NULL,

    CONSTRAINT fk_owner_id FOREIGN KEY (owner_id) REFERENCES tb_users(id)

);

create table tb_appointment(

    id UUID PRIMARY KEY NOT NULL UNIQUE,
    date_time TIMESTAMP NOT NULL,
    reason TEXT NOT NULL,
    diagnosis_notes TEXT,
    status VARCHAR(50) NOT NULL ,
    pet_id UUID NOT NULL ,
    veterinarian_id UUID ,

   CONSTRAINT fk_pet_id FOREIGN KEY (pet_id) REFERENCES tb_pet(id),
   CONSTRAINT fk_veterinarian_id FOREIGN KEY (veterinarian_id) REFERENCES tb_users(id)

);