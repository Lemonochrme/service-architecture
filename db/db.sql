CREATE TABLE `users` (
  `id` integer UNIQUE PRIMARY KEY NOT NULL,
  `id_role` integer NOT NULL,
  `username` text UNIQUE NOT NULL,
  `password` text NOT NULL
);

CREATE TABLE `roles` (
  `id` integer UNIQUE PRIMARY KEY NOT NULL,
  `name` text UNIQUE NOT NULL
);

CREATE TABLE `status` (
  `id` integer UNIQUE PRIMARY KEY NOT NULL,
  `name` text UNIQUE NOT NULL
);

CREATE TABLE `requests` (
  `id` integer UNIQUE PRIMARY KEY NOT NULL,
  `id_status` integer NOT NULL,
  `id_user` integer NOT NULL,
  `created_at` date DEFAULT (now()),
  `message` text NOT NULL
);

CREATE TABLE `feedback` (
  `id` integer UNIQUE PRIMARY KEY NOT NULL,
  `id_user` integer NOT NULL,
  `id_request` integer NOT NULL,
  `message` text NOT NULL
);

ALTER TABLE `roles` ADD FOREIGN KEY (`id`) REFERENCES `users` (`id_role`);

ALTER TABLE `users` ADD FOREIGN KEY (`id`) REFERENCES `requests` (`id_user`);

ALTER TABLE `status` ADD FOREIGN KEY (`id`) REFERENCES `requests` (`id_status`);

ALTER TABLE `users` ADD FOREIGN KEY (`id`) REFERENCES `feedback` (`id_user`);

ALTER TABLE `requests` ADD FOREIGN KEY (`id`) REFERENCES `feedback` (`id_request`);
