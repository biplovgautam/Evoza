CREATE DATABASE evoza;
USE evoza;


-- Create the tables for the database
CREATE TABLE avatars (
  avatar_id INTEGER PRIMARY KEY AUTO_INCREMENT,
  avatar_name VARCHAR(255),
  avatar_image BLOB,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- create profiles table
CREATE TABLE profiles (
  profile_id INTEGER PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(255),
  fullname VARCHAR(255),
  email VARCHAR(255),
  pass VARCHAR(255),
  profile_pic INTEGER,
  is_active BOOLEAN DEFAULT FALSE,
  FOREIGN KEY (profile_pic) REFERENCES avatars(avatar_id)
);

-- create bookmarks table
CREATE TABLE bookmarks (
  bookmark_id INTEGER PRIMARY KEY AUTO_INCREMENT,
  profile_id INTEGER,
  title VARCHAR(255),
  weburl VARCHAR(2083)
);


CREATE TABLE histories (
  history_id INTEGER PRIMARY KEY AUTO_INCREMENT,
  profile_id INTEGER,
  weburl VARCHAR(767),
  title VARCHAR(255),
  visited_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY unique_history (profile_id, weburl(767))
);

CREATE TABLE sessions (
  session_id INTEGER PRIMARY KEY AUTO_INCREMENT,
  profile_id INTEGER,
  token VARCHAR(2083),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  expires_at TIMESTAMP
);


CREATE TABLE OTPs (
  profile_id INTEGER,
  otp_nbr VARCHAR(255)
  );

INSERT INTO profiles (username, fullname, email, pass, profile_pic)
VALUES ('Guest', 'Guest Mode', 'example@nothing.com', '123', 3);

SELECT * FROM profiles;