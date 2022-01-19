-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema tourtogether
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema tourtogether
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tourtogether` DEFAULT CHARACTER SET utf8 ;
USE `tourtogether` ;

-- -----------------------------------------------------
-- Table `tourtogether`.`KakaoUser`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourtogether`.`KakaoUser` (
  `kakao_user_client_id` VARCHAR(45) NOT NULL,
  `kakao_user_email` VARCHAR(45) NULL,
  `kakao_user_logout_redirect_uri` VARCHAR(45) NULL,
  PRIMARY KEY (`kakao_user_client_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tourtogether`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourtogether`.`User` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(45) NULL,
  `user_nickname` VARCHAR(45) NULL,
  `user_profile_image` VARCHAR(45) NULL,
  `user_phone` VARCHAR(45) NULL,
  `user_joindate` DATETIME NULL,
  `user_login_platform` INT NULL,
  `user_score` INT NULL,
  `user_client_id` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_nickname_UNIQUE` (`user_nickname` ASC),
  INDEX `fk_User_Kakao_user_idx` (`user_client_id` ASC),
  CONSTRAINT `fk_User_Kakao_user`
    FOREIGN KEY (`user_client_id`)
    REFERENCES `tourtogether`.`KakaoUser` (`kakao_user_client_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tourtogether`.`KakaoToken`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourtogether`.`KakaoToken` (
  `kakao_token_client_id` VARCHAR(45) NOT NULL,
  `kakao_token_access_token` VARCHAR(45) NULL,
  `kakao_token_acess_token_expires_in` INT NULL,
  `kakao_token_refresh_token` VARCHAR(45) NULL,
  `kakao_token_refresh_token_expires_in` INT NULL,
  PRIMARY KEY (`kakao_token_client_id`),
  CONSTRAINT `fk_Kakao_token_Kakao_user1`
    FOREIGN KEY (`kakao_token_client_id`)
    REFERENCES `tourtogether`.`KakaoUser` (`kakao_user_client_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tourtogether`.`UserExp`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourtogether`.`UserExp` (
  `user_exp_score` INT NOT NULL,
  `user_exp_name` VARCHAR(45) NULL,
  PRIMARY KEY (`user_exp_score`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tourtogether`.`UserSpot`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourtogether`.`UserSpot` (
  `user_spot_id` INT NOT NULL AUTO_INCREMENT,
  `user_spot_name` VARCHAR(45) NULL,
  `user_spot_latitude` DECIMAL NULL,
  `user_spot_longitude` DECIMAL NULL,
  `user_spot_user_id` INT NOT NULL,
  PRIMARY KEY (`user_spot_id`),
  INDEX `fk_User_spot_User1_idx` (`user_spot_user_id` ASC),
  CONSTRAINT `fk_User_spot_User1`
    FOREIGN KEY (`user_spot_user_id`)
    REFERENCES `tourtogether`.`User` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tourtogether`.`Board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourtogether`.`Board` (
  `board_id` INT NOT NULL AUTO_INCREMENT,
  `board_name` VARCHAR(45) NULL,
  `board_is_active` TINYINT NULL,
  PRIMARY KEY (`board_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tourtogether`.`BoardLikes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourtogether`.`BoardLikes` (
  `board_likes_id` INT NOT NULL,
  `board_likes_board_id` INT NOT NULL AUTO_INCREMENT,
  `board_likes_user_id` INT NOT NULL,
  INDEX `fk_Board_likes_User1_idx` (`board_likes_user_id` ASC),
  PRIMARY KEY (`board_likes_id`),
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
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tourtogether`.`TwoDepth`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourtogether`.`TwoDepth` (
  `two_depth_code` INT NOT NULL,
  `two_depth_name` VARCHAR(45) NULL,
  PRIMARY KEY (`two_depth_code`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tourtogether`.`TourSpot`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourtogether`.`TourSpot` (
  `tour_spot_id` INT NOT NULL AUTO_INCREMENT,
  `tour_spot_is_top_100` TINYINT NULL,
  `tour_spot_two_depth_code` INT NOT NULL,
  PRIMARY KEY (`tour_spot_id`),
  INDEX `fk_Tour_spot_Two_depth1_idx` (`tour_spot_two_depth_code` ASC),
  CONSTRAINT `fk_Tour_spot_Two_depth1`
    FOREIGN KEY (`tour_spot_two_depth_code`)
    REFERENCES `tourtogether`.`TwoDepth` (`two_depth_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tourtogether`.`Schedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourtogether`.`Schedule` (
  `schedule_id` INT NOT NULL AUTO_INCREMENT,
  `schedule_day` INT NULL,
  `schedule_order` INT NULL,
  `schedule_additional` VARCHAR(45) NULL,
  `schecule_user_spot_id` INT NOT NULL,
  `schedule_tour_spot_id` INT NOT NULL,
  `schedule_board_id` INT NOT NULL,
  PRIMARY KEY (`schedule_id`),
  INDEX `fk_Schedule_Tour_spot1_idx` (`schedule_tour_spot_id` ASC),
  INDEX `fk_Schedule_Board1_idx` (`schedule_board_id` ASC),
  INDEX `fk_Schedule_User_spot1_idx` (`schecule_user_spot_id` ASC),
  CONSTRAINT `fk_Schedule_Tour_spot1`
    FOREIGN KEY (`schedule_tour_spot_id`)
    REFERENCES `tourtogether`.`TourSpot` (`tour_spot_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Schedule_Board1`
    FOREIGN KEY (`schedule_board_id`)
    REFERENCES `tourtogether`.`Board` (`board_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Schedule_User_spot1`
    FOREIGN KEY (`schecule_user_spot_id`)
    REFERENCES `tourtogether`.`UserSpot` (`user_spot_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tourtogether`.`Memo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourtogether`.`Memo` (
  `memo_board_id` INT NOT NULL,
  `memo_content` TEXT NULL,
  PRIMARY KEY (`memo_board_id`),
  CONSTRAINT `fk_Memo_Board1`
    FOREIGN KEY (`memo_board_id`)
    REFERENCES `tourtogether`.`Board` (`board_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tourtogether`.`Category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourtogether`.`Category` (
  `category_board_id` INT NOT NULL,
  `category_with_whom` INT NULL,
  `category_season` INT NULL,
  `category_area` INT NULL,
  `catetory_theme` INT NULL,
  PRIMARY KEY (`category_board_id`),
  CONSTRAINT `fk_Category_Board1`
    FOREIGN KEY (`category_board_id`)
    REFERENCES `tourtogether`.`Board` (`board_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tourtogether`.`TourSpotInfo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourtogether`.`TourSpotInfo` (
  `tour_spot_info_tour_spot_id` INT NOT NULL,
  `tour_spot_info_name` VARCHAR(45) NULL,
  `tour_spot_info_summary` VARCHAR(200) NULL,
  `tour_spot_info_address` VARCHAR(100) NULL,
  `tour_spot_info_photo_url` VARCHAR(100) NULL,
  `tour_spot_info_latitude` DECIMAL NULL,
  `tour_spot_info_longitude` DECIMAL NULL,
  `tour_spot_info_time` VARCHAR(45) NULL,
  `tour_spot_info_fee` VARCHAR(45) NULL,
  `tour_spot_info_holiday` VARCHAR(45) NULL,
  `tour_spot_info_parking` VARCHAR(45) NULL,
  PRIMARY KEY (`tour_spot_info_tour_spot_id`),
  CONSTRAINT `fk_Tour_spot_info_Tour_spot1`
    FOREIGN KEY (`tour_spot_info_tour_spot_id`)
    REFERENCES `tourtogether`.`TourSpot` (`tour_spot_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tourtogether`.`OneDepth`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourtogether`.`OneDepth` (
  `one_depth_code` INT NOT NULL,
  `one_depth_name` VARCHAR(45) NULL,
  PRIMARY KEY (`one_depth_code`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tourtogether`.`BoardParticipant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourtogether`.`BoardParticipant` (
  `board_participant_id` INT NOT NULL AUTO_INCREMENT,
  `board_participant_board_id` INT NOT NULL,
  `board_participant_user_id` INT NOT NULL,
  INDEX `fk_Board_participant_Board1_idx` (`board_participant_board_id` ASC),
  INDEX `fk_Board_participant_User1_idx` (`board_participant_user_id` ASC),
  PRIMARY KEY (`board_participant_id`),
  CONSTRAINT `fk_Board_participant_User1`
    FOREIGN KEY (`board_participant_user_id`)
    REFERENCES `tourtogether`.`User` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Board_participant_Board1`
    FOREIGN KEY (`board_participant_board_id`)
    REFERENCES `tourtogether`.`Board` (`board_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
