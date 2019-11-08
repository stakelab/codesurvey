CREATE TABLE IF NOT EXISTS `snippet` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(500) NOT NULL,
  `text_to_show` text NOT NULL,
  `system_name` varchar(80) DEFAULT NULL,
  `related_resources` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `question` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `question` text NOT NULL,
  `snippet_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `question_snippetId` (`snippet_id`),
  CONSTRAINT `question_snippetId` FOREIGN KEY (`snippet_id`) REFERENCES `snippet` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `answer` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `question_id` int(11) unsigned NOT NULL,
  `text` text NOT NULL,
  `correct` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `answer_questionId` (`question_id`),
  CONSTRAINT `answer_questionId` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `participant` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL DEFAULT '',
  `email` varchar(50) DEFAULT NULL,
  `position` varchar(50) DEFAULT NULL,
  `java_experience` int(10) DEFAULT NULL,
  `programming_experience` int(10) DEFAULT NULL,
  `username` varchar(30) NOT NULL DEFAULT '',
  `password` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `assessment` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `participant_id` int(11) unsigned NOT NULL,
  `snippet_id` int(11) unsigned NOT NULL,
  `seconds_needed` int(11) NOT NULL,
  `understood` tinyint(4) NOT NULL,
  `correctness` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `assessment_participant` (`participant_id`),
  KEY `assessment_snippet` (`snippet_id`),
  CONSTRAINT `assessment_participant` FOREIGN KEY (`participant_id`) REFERENCES `participant` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `assessment_snippet` FOREIGN KEY (`snippet_id`) REFERENCES `snippet` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `provided_answers` (
  `assessment_id` int(11) unsigned NOT NULL,
  `question_id` int(11) unsigned NOT NULL,
  `given_answer_id` int(11) unsigned NOT NULL,
  `correct` tinyint(4) NOT NULL,
  PRIMARY KEY (`assessment_id`,`question_id`),
  KEY `question_providedAnswers` (`question_id`),
  KEY `answer_providedAnswers` (`given_answer_id`),
  CONSTRAINT `answer_providedAnswers` FOREIGN KEY (`given_answer_id`) REFERENCES `answer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `assessment_providedAnswers` FOREIGN KEY (`assessment_id`) REFERENCES `assessment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `question_providedAnswers` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `classes` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `text_to_show` longtext NOT NULL,
  `system_name` varchar(80) DEFAULT NULL,
  `related_resources` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `skills` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `field` varchar(100) NOT NULL,
  `exp` varchar(2000) NOT NULL,
  `participant_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `uniqueSkill` UNIQUE KEY (`participant_id`, `field`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
