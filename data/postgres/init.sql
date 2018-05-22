CREATE DATABASE ad_management;

\c ad_management;

CREATE TABLE IF NOT EXISTS ads (
  id      SERIAL PRIMARY KEY,
  name    VARCHAR(255) NOT NULL,
  content TEXT         NOT NULL,
  created TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted BOOLEAN      NOT NULL DEFAULT FALSE
);
