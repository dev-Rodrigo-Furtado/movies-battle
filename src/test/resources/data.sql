CREATE TABLE IF NOT EXISTS `users` (
  `id` UUID NOT NULL UNIQUE,
  `username` varchar(255) NOT NULL UNIQUE,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `users` (`id`, `username`, `password`) VALUES ('6b7916b0-ce77-49bf-b486-92d625321076', 'player01', '$2a$10$yXUBsbH/MUjKAlFdWq5LbuTkzaDiLmC4CJ7os4ugFQcWQUXbPa3D2');
INSERT INTO `users` (`id`, `username`, `password`) VALUES ('4dc1642e-73f8-4ec4-a053-c639883c747b', 'player02', '$2a$10$yXUBsbH/MUjKAlFdWq5LbuTkzaDiLmC4CJ7os4ugFQcWQUXbPa3D2');

CREATE TABLE IF NOT EXISTS `quizzes` (
  `id` UUID NOT NULL UNIQUE,
  `user_id` UUID  NOT NULL,
  `score` INT NOT NULL,
  `total_correct_answers` INT NOT NULL,
  `total_wrong_answers` INT NOT NULL,
  `status` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `user_id_fk` FOREIGN KEY (`user_id`) REFERENCES users(`id`)
);

CREATE TABLE IF NOT EXISTS `movies` (
  `imdb_id` VARCHAR(255) NOT NULL UNIQUE,
  PRIMARY KEY (`imdb_id`)
);

CREATE TABLE IF NOT EXISTS `rounds` (
  `id` UUID NOT NULL UNIQUE,
  `quiz_id` UUID NOT NULL,
  `result` VARCHAR(255),
  `status` VARCHAR(255),
  PRIMARY KEY (`id`),
  CONSTRAINT `quiz_id_fk` FOREIGN KEY (`quiz_id`) REFERENCES quizzes(`id`)
);

CREATE TABLE IF NOT EXISTS `questions` (
  `id` UUID NOT NULL UNIQUE,
  `round_id` UUID NOT NULL,
  `primary_movie_imdb_id` VARCHAR(255) NOT NULL,
  `secondary_movie_imdb_id` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_round_id` FOREIGN KEY (`round_id`) REFERENCES rounds(`id`),
  CONSTRAINT `fk_primary_movie_imdb_id` FOREIGN KEY (`primary_movie_imdb_id`) REFERENCES movies(`imdb_id`),
  CONSTRAINT `fk_secondary_movie_imdb_id` FOREIGN KEY (`secondary_movie_imdb_id`) REFERENCES movies(`imdb_id`)
);

INSERT INTO `movies` (`imdb_id`) VALUES ('tt0167261');
INSERT INTO `movies` (`imdb_id`) VALUES ('tt1745960');
INSERT INTO `movies` (`imdb_id`) VALUES ('tt10872600');
INSERT INTO `movies` (`imdb_id`) VALUES ('tt8579674');
INSERT INTO `movies` (`imdb_id`) VALUES ('tt7286456');
INSERT INTO `movies` (`imdb_id`) VALUES ('tt4154796');
INSERT INTO `movies` (`imdb_id`) VALUES ('tt4154756');
INSERT INTO `movies` (`imdb_id`) VALUES ('tt3315342');
INSERT INTO `movies` (`imdb_id`) VALUES ('tt1392190');
INSERT INTO `movies` (`imdb_id`) VALUES ('tt0816692');
INSERT INTO `movies` (`imdb_id`) VALUES ('tt0993846');
INSERT INTO `movies` (`imdb_id`) VALUES ('tt0435761');
INSERT INTO `movies` (`imdb_id`) VALUES ('tt0892769');
INSERT INTO `movies` (`imdb_id`) VALUES ('tt0382932');
INSERT INTO `movies` (`imdb_id`) VALUES ('tt0114709');
INSERT INTO `movies` (`imdb_id`) VALUES ('tt0110357');
INSERT INTO `movies` (`imdb_id`) VALUES ('tt0107290');
INSERT INTO `movies` (`imdb_id`) VALUES ('tt0903624');
INSERT INTO `movies` (`imdb_id`) VALUES ('tt0120737');
INSERT INTO `movies` (`imdb_id`) VALUES ('tt0080684');
