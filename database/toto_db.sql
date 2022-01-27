-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema tourtogether
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema tourtogether
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tourtogether` DEFAULT CHARACTER SET utf8 ;
USE `tourtogether` ;

-- -----------------------------------------------------
-- Table `tourtogether`.`Board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourtogether`.`Board` (
  `board_id` INT(11) NOT NULL AUTO_INCREMENT,
  `board_name` VARCHAR(45) NULL DEFAULT NULL,
  `board_is_active` TINYINT(4) NULL DEFAULT NULL,
  PRIMARY KEY (`board_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tourtogether`.`KakaoUser`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourtogether`.`KakaoUser` (
  `kakao_user_client_id` VARCHAR(45) NOT NULL,
  `kakao_user_email` VARCHAR(45) NULL DEFAULT NULL,
  `kakao_user_logout_redirect_uri` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`kakao_user_client_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tourtogether`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourtogether`.`User` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(45) NULL DEFAULT NULL,
  `user_nickname` VARCHAR(45) NULL DEFAULT NULL,
  `user_profile_image` VARCHAR(45) NULL DEFAULT NULL,
  `user_phone` VARCHAR(45) NULL DEFAULT NULL,
  `user_joindate` DATETIME NULL DEFAULT NULL,
  `user_login_platform` INT(11) NULL DEFAULT NULL,
  `user_score` INT(11) NULL DEFAULT NULL,
  `user_client_id` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_nickname_UNIQUE` (`user_nickname` ASC),
  INDEX `fk_User_Kakao_user_idx` (`user_client_id` ASC),
  CONSTRAINT `fk_User_Kakao_user`
    FOREIGN KEY (`user_client_id`)
    REFERENCES `tourtogether`.`KakaoUser` (`kakao_user_client_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tourtogether`.`BoardLikes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourtogether`.`BoardLikes` (
  `board_likes_id` INT(11) NOT NULL,
  `board_likes_board_id` INT(11) NOT NULL AUTO_INCREMENT,
  `board_likes_user_id` INT(11) NOT NULL,
  PRIMARY KEY (`board_likes_id`),
  INDEX `fk_Board_likes_User1_idx` (`board_likes_user_id` ASC),
  INDEX `fk_Board_likes_Board1` (`board_likes_board_id` ASC),
  CONSTRAINT `fk_Board_likes_Board1`
    FOREIGN KEY (`board_likes_board_id`)
    REFERENCES `tourtogether`.`Board` (`board_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Board_likes_User1`
    FOREIGN KEY (`board_likes_user_id`)
    REFERENCES `tourtogether`.`User` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tourtogether`.`BoardParticipant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourtogether`.`BoardParticipant` (
  `board_participant_id` INT(11) NOT NULL AUTO_INCREMENT,
  `board_participant_board_id` INT(11) NOT NULL,
  `board_participant_user_id` INT(11) NOT NULL,
  PRIMARY KEY (`board_participant_id`),
  INDEX `fk_Board_participant_Board1_idx` (`board_participant_board_id` ASC),
  INDEX `fk_Board_participant_User1_idx` (`board_participant_user_id` ASC),
  CONSTRAINT `fk_Board_participant_Board1`
    FOREIGN KEY (`board_participant_board_id`)
    REFERENCES `tourtogether`.`Board` (`board_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Board_participant_User1`
    FOREIGN KEY (`board_participant_user_id`)
    REFERENCES `tourtogether`.`User` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tourtogether`.`Category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourtogether`.`Category` (
  `category_board_id` INT(11) NOT NULL,
  `category_with_whom` INT(11) NULL DEFAULT NULL,
  `category_season` INT(11) NULL DEFAULT NULL,
  `category_area` INT(11) NULL DEFAULT NULL,
  `catetory_theme` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`category_board_id`),
  CONSTRAINT `fk_Category_Board1`
    FOREIGN KEY (`category_board_id`)
    REFERENCES `tourtogether`.`Board` (`board_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tourtogether`.`KakaoToken`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourtogether`.`KakaoToken` (
  `kakao_token_client_id` VARCHAR(45) NOT NULL,
  `kakao_token_access_token` VARCHAR(45) NULL DEFAULT NULL,
  `kakao_token_acess_token_expires_in` INT(11) NULL DEFAULT NULL,
  `kakao_token_refresh_token` VARCHAR(45) NULL DEFAULT NULL,
  `kakao_token_refresh_token_expires_in` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`kakao_token_client_id`),
  CONSTRAINT `fk_Kakao_token_Kakao_user1`
    FOREIGN KEY (`kakao_token_client_id`)
    REFERENCES `tourtogether`.`KakaoUser` (`kakao_user_client_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tourtogether`.`Memo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourtogether`.`Memo` (
  `memo_board_id` INT(11) NOT NULL,
  `memo_content` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`memo_board_id`),
  CONSTRAINT `fk_Memo_Board1`
    FOREIGN KEY (`memo_board_id`)
    REFERENCES `tourtogether`.`Board` (`board_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tourtogether`.`OneDepth`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourtogether`.`OneDepth` (
  `one_depth_code` INT(11) NOT NULL,
  `one_depth_name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`one_depth_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tourtogether`.`TwoDepth`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourtogether`.`TwoDepth` (
  `two_depth_code` INT(11) NOT NULL,
  `two_depth_name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`two_depth_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tourtogether`.`TourSpot`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourtogether`.`TourSpot` (
  `tour_spot_id` INT(11) NOT NULL AUTO_INCREMENT,
  `tour_spot_name` TEXT NULL DEFAULT NULL,
  `tour_spot_summary` TEXT NULL DEFAULT NULL,
  `tour_spot_address` TEXT NULL DEFAULT NULL,
  `tour_spot_two_depth_code` INT(11) NOT NULL,
  `tour_spot_photo_url` TEXT NULL DEFAULT NULL,
  `tour_spot_latitude` DECIMAL(24,10) NULL DEFAULT NULL,
  `tour_spot_longitude` DECIMAL(24,10) NULL DEFAULT NULL,
  `tour_spot_time` TEXT NULL DEFAULT NULL,
  `tour_spot_fee` TEXT NULL DEFAULT NULL,
  `tour_spot_holiday` TEXT NULL DEFAULT NULL,
  `tour_spot_parking` TEXT NULL DEFAULT NULL,
  `tour_spot_is_top_100` TINYINT(4) NULL DEFAULT NULL,
  PRIMARY KEY (`tour_spot_id`),
  INDEX `fk_Tour_spot_Two_depth1_idx` (`tour_spot_two_depth_code` ASC),
  CONSTRAINT `fk_Tour_spot_Two_depth1`
    FOREIGN KEY (`tour_spot_two_depth_code`)
    REFERENCES `tourtogether`.`TwoDepth` (`two_depth_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 362
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tourtogether`.`UserSpot`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourtogether`.`UserSpot` (
  `user_spot_id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_spot_name` VARCHAR(45) NULL DEFAULT NULL,
  `user_spot_latitude` DECIMAL(10,0) NULL DEFAULT NULL,
  `user_spot_longitude` DECIMAL(10,0) NULL DEFAULT NULL,
  `user_spot_user_id` INT(11) NOT NULL,
  PRIMARY KEY (`user_spot_id`),
  INDEX `fk_User_spot_User1_idx` (`user_spot_user_id` ASC),
  CONSTRAINT `fk_User_spot_User1`
    FOREIGN KEY (`user_spot_user_id`)
    REFERENCES `tourtogether`.`User` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tourtogether`.`Schedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourtogether`.`Schedule` (
  `schedule_id` INT(11) NOT NULL AUTO_INCREMENT,
  `schedule_day` INT(11) NULL DEFAULT NULL,
  `schedule_order` INT(11) NULL DEFAULT NULL,
  `schedule_additional` VARCHAR(45) NULL DEFAULT NULL,
  `schecule_user_spot_id` INT(11) NOT NULL,
  `schedule_tour_spot_id` INT(11) NOT NULL,
  `schedule_board_id` INT(11) NOT NULL,
  PRIMARY KEY (`schedule_id`),
  INDEX `fk_Schedule_Tour_spot1_idx` (`schedule_tour_spot_id` ASC),
  INDEX `fk_Schedule_Board1_idx` (`schedule_board_id` ASC),
  INDEX `fk_Schedule_User_spot1_idx` (`schecule_user_spot_id` ASC),
  CONSTRAINT `fk_Schedule_Board1`
    FOREIGN KEY (`schedule_board_id`)
    REFERENCES `tourtogether`.`Board` (`board_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Schedule_Tour_spot1`
    FOREIGN KEY (`schedule_tour_spot_id`)
    REFERENCES `tourtogether`.`TourSpot` (`tour_spot_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Schedule_User_spot1`
    FOREIGN KEY (`schecule_user_spot_id`)
    REFERENCES `tourtogether`.`UserSpot` (`user_spot_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tourtogether`.`UserExp`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourtogether`.`UserExp` (
  `user_exp_score` INT(11) NOT NULL,
  `user_exp_name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`user_exp_score`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
