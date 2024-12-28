CREATE DATABASE evoza;
USE evoza;

-- Create the tables for the database
CREATE TABLE avatars (
  avatar_id INTEGER PRIMARY KEY AUTO_INCREMENT,
  avatar_name VARCHAR(255),
  avatar_image BLOB,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

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

CREATE TABLE bookmarks (
  profile_id INTEGER,
  title VARCHAR(255),
  weburl VARCHAR(2083),
  FOREIGN KEY (profile_id) REFERENCES profiles(profile_id)
);

CREATE TABLE histories (
  profile_id INTEGER,
  weburl VARCHAR(2083),
  title VARCHAR(255),
  visited_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (profile_id) REFERENCES profiles(profile_id)
);

CREATE TABLE sessions (
  session_id INTEGER PRIMARY KEY AUTO_INCREMENT,
  profile_id INTEGER,
  token VARCHAR(2083),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  expires_at TIMESTAMP,
  FOREIGN KEY (profile_id) REFERENCES profiles(profile_id)
);

CREATE TABLE OTPs (
  profile_id INTEGER,
  otp_nbr VARCHAR(255)
  );

INSERT INTO profiles (username, fullname, email, pass, profile_pic)
VALUES ('Guest', 'Guest Mode', 'example@nothing.com', '123', 3);

SELECT * FROM profiles;