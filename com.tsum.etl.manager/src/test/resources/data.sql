INSERT INTO `tsum-test`.`Brand` (`id`, `name`) VALUES ('1', 'Brand 1');
INSERT INTO `tsum-test`.`Brand` (`id`, `name`) VALUES ('2', 'Brand 2');
INSERT INTO `tsum-test`.`Brand` (`id`, `name`) VALUES ('3', 'Brand 3');
INSERT INTO `tsum-test`.`Brand` (`id`, `name`) VALUES ('4', 'Brand 4');
INSERT INTO `tsum-test`.`Brand` (`id`, `name`) VALUES ('5', 'Brand 5');

INSERT INTO `tsum-test`.`BrandTag` (`id`, `name`, `externalId`) VALUES ('1', 'Category 1', '1');
INSERT INTO `tsum-test`.`BrandTag` (`id`, `name`, `externalId`) VALUES ('2', 'Category 2', '2');
INSERT INTO `tsum-test`.`BrandTag` (`id`, `name`, `externalId`) VALUES ('3', 'Category 3', '3');
INSERT INTO `tsum-test`.`BrandTag` (`id`, `name`, `externalId`) VALUES ('4', 'Category 4', '4');

INSERT INTO `tsum-test`.`Client` (`Client`) VALUES ('1');

INSERT INTO `tsum-test`.`Product` (`id`, `name`, `sizes`, `price`, `newPrice`, `actionEnabled`, `brandTagId`, `brandId`, `smallPhotoURL`, `largePhotoURL`, `updateTimestamp`) VALUES ('1', 'Name 1', '1', '21', '11', '0', '1', '1', 'http://small.ru', 'http://large.ru', '2017-01-01 00:00:000.000');
INSERT INTO `tsum-test`.`Product` (`id`, `name`, `sizes`, `price`, `newPrice`, `actionEnabled`, `brandTagId`, `brandId`, `smallPhotoURL`, `largePhotoURL`, `updateTimestamp`) VALUES ('2', 'Name 2', '2', '22', '12', '0', '1', '1', 'http://small.ru', 'http://large.ru', '2017-01-01 00:00:000.000');
INSERT INTO `tsum-test`.`Product` (`id`, `name`, `sizes`, `price`, `newPrice`, `actionEnabled`, `brandTagId`, `brandId`, `smallPhotoURL`, `largePhotoURL`, `updateTimestamp`) VALUES ('3', 'Name 3', '3', '23', '13', '0', '2', '2', 'http://small.ru', 'http://large.ru', '2017-01-01 00:00:000.000');
INSERT INTO `tsum-test`.`Product` (`id`, `name`, `sizes`, `price`, `newPrice`, `actionEnabled`, `brandTagId`, `brandId`, `smallPhotoURL`, `largePhotoURL`, `updateTimestamp`) VALUES ('4', 'Name 4', '4', '25', '14', '0', '2', '2', 'http://small.ru', 'http://large.ru', '2017-01-01 00:00:000.000');
INSERT INTO `tsum-test`.`Product` (`id`, `name`, `sizes`, `price`, `newPrice`, `actionEnabled`, `brandTagId`, `brandId`, `smallPhotoURL`, `largePhotoURL`, `updateTimestamp`) VALUES ('5', 'Name 5', '5', '25', '15', '0', '3', '3', 'http://small.ru', 'http://large.ru', '2017-01-01 00:00:000.000');
INSERT INTO `tsum-test`.`Product` (`id`, `name`, `sizes`, `price`, `newPrice`, `actionEnabled`, `actionDetails`, `brandTagId`, `brandId`, `smallPhotoURL`, `largePhotoURL`, `updateTimestamp`) VALUES ('6', 'Name 6', '6', '26', '16', '1', 'Action 6', '3', '3', 'http://small.ru', 'http://large.ru', '2017-01-01 00:00:000.000');
INSERT INTO `tsum-test`.`Product` (`id`, `name`, `sizes`, `price`, `newPrice`, `actionEnabled`, `actionDetails`, `brandTagId`, `brandId`, `smallPhotoURL`, `largePhotoURL`, `updateTimestamp`) VALUES ('7', 'Name 7', '7', '27', '17', '1', 'Action 7', '4', '4', 'http://small.ru', 'http://large.ru', '2017-01-01 00:00:000.000');
INSERT INTO `tsum-test`.`Product` (`id`, `name`, `sizes`, `price`, `newPrice`, `actionEnabled`, `actionDetails`, `brandTagId`, `brandId`, `smallPhotoURL`, `largePhotoURL`, `updateTimestamp`) VALUES ('8', 'Name 8', '8', '28', '18', '1', 'Action 8', '4', '4', 'http://small.ru', 'http://large.ru', '2017-01-01 00:00:000.000');
INSERT INTO `tsum-test`.`Product` (`id`, `name`, `sizes`, `price`, `newPrice`, `actionEnabled`, `actionDetails`, `brandTagId`, `brandId`, `smallPhotoURL`, `largePhotoURL`, `updateTimestamp`) VALUES ('9', 'Name 9', '9', '29', '19', '1', 'Action 9', '4', '5', 'http://small.ru', 'http://large.ru', '2017-01-01 00:00:000.000');
INSERT INTO `tsum-test`.`Product` (`id`, `name`, `sizes`, `price`, `newPrice`, `actionEnabled`, `actionDetails`, `brandTagId`, `brandId`, `smallPhotoURL`, `largePhotoURL`, `updateTimestamp`) VALUES ('10', 'Name 10', '10', '10', '9', '1', 'Action 10', '4', '5', 'http://small.ru', 'http://large.ru', '2017-01-01 00:00:000.000');

INSERT INTO `tsum-test`.`ClientProduct` (`product_id`, `client_id`) VALUES ('1', '1');
INSERT INTO `tsum-test`.`ClientProduct` (`product_id`, `client_id`) VALUES ('2', '1');
INSERT INTO `tsum-test`.`ClientProduct` (`product_id`, `client_id`) VALUES ('3', '1');
INSERT INTO `tsum-test`.`ClientProduct` (`product_id`, `client_id`) VALUES ('4', '1');
INSERT INTO `tsum-test`.`ClientProduct` (`product_id`, `client_id`) VALUES ('5', '1');
