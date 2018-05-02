CREATE TABLE accounts
(
  id INT AUTO_INCREMENT PRIMARY KEY,
  creationDate DATETIME DEFAULT now(),
  balance NUMERIC(18,2) DEFAULT 0
);

CREATE TABLE operations
(
  id INT AUTO_INCREMENT PRIMARY KEY,
  transferDate DATETIME DEFAULT now(),
  accountIdFrom INT,
  accountIdTo INT,
  balance NUMERIC(18,2) DEFAULT 0
);