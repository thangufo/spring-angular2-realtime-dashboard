INSERT INTO `poll` (id,`name`) VALUES (1,'What is the best Rock band ?');
INSERT INTO `poll` (id,`name`) VALUES (2,'Which MVC framework do you like the most ?');
INSERT INTO `poll` (id,`name`) VALUES (3, 'Which is the best Javascript framework ?');

INSERT INTO `poll_choice` (`id`, `choice`, `poll_id`) VALUES ('1', 'Metallica', '1');
INSERT INTO `poll_choice` (`id`, `choice`, `poll_id`) VALUES ('2', 'Guns N Roses', '1');
INSERT INTO `poll_choice` (`id`, `choice`, `poll_id`) VALUES ('3', 'Queen', '1');

INSERT INTO `poll_choice` (`id`, `choice`, `poll_id`) VALUES ('4', 'Spring Boot/Spring MVC', '2');
INSERT INTO `poll_choice` (`id`, `choice`, `poll_id`) VALUES ('5', 'Ruby on Rails', '2');
INSERT INTO `poll_choice` (`id`, `choice`, `poll_id`) VALUES ('6', 'Django', '2');
INSERT INTO `poll_choice` (`id`, `choice`, `poll_id`) VALUES ('7', 'Symfony (PHP)', '2');
INSERT INTO `poll_choice` (`id`, `choice`, `poll_id`) VALUES ('8', 'Other', '2');

INSERT INTO `poll_choice` (`id`, `choice`, `poll_id`) VALUES ('9', 'Meteor', '3');
INSERT INTO `poll_choice` (`id`, `choice`, `poll_id`) VALUES ('10', 'AngularJS 2', '3');
INSERT INTO `poll_choice` (`id`, `choice`, `poll_id`) VALUES ('11', 'EmberJS', '3');
INSERT INTO `poll_choice` (`id`, `choice`, `poll_id`) VALUES ('12', 'Backbone', '3');
INSERT INTO `poll_choice` (`id`, `choice`, `poll_id`) VALUES ('13', 'Other', '3');
