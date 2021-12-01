create table AUTHENTIFICATION_UTILISATEURS(
    id int identity primary key,
    selector varchar(12),
    validator varchar(64),
    user_id int foreign key references utilisateurs(no_utilisateur) on delete cascade
);


