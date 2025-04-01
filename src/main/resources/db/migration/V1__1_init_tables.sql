
CREATE TABLE PersInf (
                         id SERIAL PRIMARY KEY,
                         Surname VARCHAR(40) NOT NULL,
                         Name VARCHAR(40) NOT NULL,
                         phoneNumber VARCHAR(40),
                         email VARCHAR(40),
                         create TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ProfInf (
                         PersInfId INTEGER PRIMARY KEY,
                         skillName VARCHAR(40) NOT NULL,
                         skillDescription TEXT,
                         cost NUMERIC(6,2),
                         persDescription TEXT,
                         exp INTEGER,
                         create TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (PersInfId) REFERENCES PersInf(id) ON DELETE CASCADE
);

CREATE TABLE SkillExchange (
                               exchangeId VARCHAR PRIMARY KEY,
                               skillOffered VARCHAR(40) NOT NULL,
                               userOffering INTEGER NOT NULL,
                               userRequesting INTEGER NOT NULL,
                               FOREIGN KEY (skillOffered) REFERENCES ProfInf(skillName),
                               FOREIGN KEY (userOffering) REFERENCES ProfInf(PersInfId),
                               FOREIGN KEY (userRequesting) REFERENCES PersInf(id)
);

CREATE TABLE Review (
                        reviewId SERIAL PRIMARY KEY,
                        rating INTEGER NOT NULL,
                        comment TEXT,
                        reviewer INTEGER NOT NULL,
                        userEvaluated INTEGER NOT NULL,
                        create TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (reviewer) REFERENCES PersInf(id),
                        FOREIGN KEY (userEvaluated) REFERENCES ProfInf(PersInfId)
);

CREATE TABLE Transaction (
                             transactionId VARCHAR PRIMARY KEY,
                             dateTime TIMESTAMP NOT NULL,
                             status VARCHAR(30) NOT NULL,
                             changeId VARCHAR NOT NULL,
                             FOREIGN KEY (changeId) REFERENCES SkillExchange(exchangeId)
);
