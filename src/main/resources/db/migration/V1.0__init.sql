 CREATE TABLE IF NOT EXISTS teams (
	`id` BINARY(16) NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT now(),
    `updated_at` DATETIME NOT NULL DEFAULT now(),
    PRIMARY KEY `id` (`id`),
    UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS employees (
	`id` BINARY(16) NOT NULL,
    `first_name` VARCHAR(255) NOT NULL,
    `last_name` VARCHAR(255) NOT NULL,
    `team_id` BINARY(16),
    `created_at` DATETIME NOT NULL DEFAULT now(),
    `updated_at` DATETIME NOT NULL DEFAULT now(),
    PRIMARY KEY `id` (`id`),
    FOREIGN KEY (`team_id`) REFERENCES `teams`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
