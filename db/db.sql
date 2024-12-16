CREATE TABLE `users` (
  `id` integer UNIQUE PRIMARY KEY AUTO_INCREMENT,
  `id_role` integer NOT NULL,
  `username` text UNIQUE NOT NULL,
  `password` text NOT NULL
);

CREATE TABLE `roles` (
  `id` integer UNIQUE PRIMARY KEY AUTO_INCREMENT,
  `name` text UNIQUE NOT NULL
);

CREATE TABLE `status` (
  `id` integer UNIQUE PRIMARY KEY AUTO_INCREMENT,
  `name` text UNIQUE NOT NULL
);

CREATE TABLE `requests` (
  `id` integer UNIQUE PRIMARY KEY AUTO_INCREMENT,
  `id_status` integer NOT NULL,
  `id_user` integer NOT NULL,
  `created_at` date DEFAULT (now()),
  `message` text NOT NULL
);

CREATE TABLE `feedback` (
  `id` integer UNIQUE PRIMARY KEY AUTO_INCREMENT,
  `id_user` integer NOT NULL,
  `id_request` integer NOT NULL,
  `message` text NOT NULL
);

ALTER TABLE `users` ADD FOREIGN KEY (`id_role`) REFERENCES `roles` (`id`);

ALTER TABLE `requests` ADD FOREIGN KEY (`id_user`) REFERENCES `users` (`id`);

ALTER TABLE `requests` ADD FOREIGN KEY (`id_status`) REFERENCES `status` (`id`);

ALTER TABLE `feedback` ADD FOREIGN KEY (`id_user`) REFERENCES `users` (`id`);

ALTER TABLE `feedback` ADD FOREIGN KEY (`id_request`) REFERENCES `requests` (`id`);
