
CREATE TABLE PersInf (
                         id VARCHAR(36) PRIMARY KEY,
                         surname VARCHAR(40) NOT NULL,
                         name VARCHAR(40) NOT NULL,
                         phoneNumber VARCHAR(40),
                         email VARCHAR(40),
                         createdAT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         updatedAT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ProfInf (
                         persId VARCHAR(36) PRIMARY KEY,
                         skillName VARCHAR(40) NOT NULL,
                         skillDescription TEXT,
                         cost NUMERIC(6,2),
                         persDescription TEXT,
                         exp NUMERIC(6,2),
                         rating NUMERIC(6,2),
                         createdAT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         updatedAT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE SkillExchange (
                               exchangeId VARCHAR PRIMARY KEY,
                               skillOffered VARCHAR(40) NOT NULL,
                               userOffering INTEGER NOT NULL,
                               userRequesting INTEGER NOT NULL
);

CREATE TABLE Review (
                        reviewId VARCHAR(36) PRIMARY KEY,
                        rating NUMERIC(6,2) NOT NULL,
                        comment TEXT,
                        reviewer VARCHAR NOT NULL,
                        userEvaluated VARCHAR NOT NULL,
                        createdAT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updatedAT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Transaction (
                             transactionId VARCHAR PRIMARY KEY,
                             dateTime TIMESTAMP NOT NULL,
                             status VARCHAR(30) NOT NULL,
                             changeId VARCHAR NOT NULL
);

CREATE TABLE test_entity (
                             id VARCHAR(36) PRIMARY KEY,
                             name VARCHAR(100) NOT NULL,
                             description TEXT,
                             date_created TIMESTAMP NOT NULL,
                             is_check BOOLEAN NOT NULL DEFAULT false,
                             street VARCHAR(100),
                             city VARCHAR(50),
                             zip_code VARCHAR(10)
);

