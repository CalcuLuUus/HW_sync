-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 23, 2020 at 11:29 PM
-- Server version: 5.7.30-0ubuntu0.18.04.1
-- PHP Version: 7.2.24-0ubuntu0.18.04.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hw6`
--

-- --------------------------------------------------------

--
-- Table structure for table `courses`
--

CREATE TABLE `courses` (
  `course_id` int(11) NOT NULL,
  `title` varchar(128) NOT NULL,
  `code` tinytext NOT NULL,
  `semester` enum('FALL','SPRING') NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `courses`
--

INSERT INTO `courses` (`course_id`, `title`, `code`, `semester`, `user_id`) VALUES
(1, 'Java Programming', 'JAVA', 'FALL', 15),
(2, 'Formal Languages and Automata', 'FLA', 'SPRING', 5),
(3, 'Compiler Design', 'COMP', 'SPRING', 5),
(4, 'Algorithms and Data Structures', 'ADS', 'FALL', 5),
(5, 'Introduction to Web Programming', 'IWP', 'FALL', 10),
(6, 'Software Engineering', 'SE', 'SPRING', 10);

-- --------------------------------------------------------

--
-- Table structure for table `scores`
--

CREATE TABLE `scores` (
  `score_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `score1` float NOT NULL,
  `score2` float NOT NULL,
  `score3` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `scores`
--

INSERT INTO `scores` (`score_id`, `course_id`, `user_id`, `score1`, `score2`, `score3`) VALUES
(1, 1, 1, 62.5, 58.5, 69),
(2, 2, 1, 60, 58, 79),
(3, 3, 1, 64.5, 68.5, 61),
(4, 2, 2, 74, 67.5, 81),
(5, 3, 2, 56.9, 65.8, 98.4),
(6, 4, 2, 78.9, 76.3, 81.5),
(7, 3, 3, 57.5, 64, 57.5),
(8, 4, 3, 78, 83.2, 87.8),
(9, 5, 3, 67.4, 84.2, 87.7),
(10, 4, 4, 73, 64.5, 68.5),
(11, 5, 4, 83.4, 88.2, 90.6),
(12, 6, 4, 67.4, 70, 71.3),
(13, 5, 6, 78, 62, 85),
(14, 6, 6, 78.9, 76.3, 81.5),
(15, 1, 6, 67.4, 84.2, 87.7),
(16, 6, 7, 52.5, 60, 74.5),
(17, 1, 7, 60, 58, 79),
(18, 2, 7, 64.5, 68.5, 61),
(19, 1, 8, 65.5, 80, 77),
(20, 2, 8, 78, 83.2, 87.8),
(21, 3, 8, 67.4, 84.2, 77.7),
(22, 2, 9, 81, 75, 62),
(23, 3, 9, 83.4, 88.2, 90.6),
(24, 4, 9, 67.4, 84.2, 87.7),
(25, 3, 11, 87, 92.5, 81),
(26, 4, 11, 56.9, 65.8, 78.4),
(27, 5, 11, 78, 83.2, 87.8),
(28, 4, 12, 57.5, 65, 64),
(29, 5, 12, 67.4, 84.2, 87.7),
(30, 5, 13, 47.5, 68.5, 74),
(31, 6, 13, 65.5, 80, 77),
(32, 6, 14, 72.5, 60, 58),
(33, 1, 14, 60, 58, 79),
(34, 1, 16, 77.5, 85, 75),
(35, 2, 16, 73, 64.5, 68.5),
(36, 2, 17, 86, 75, 62.5),
(37, 3, 17, 73, 68.5, 81.5),
(38, 3, 18, 30, 59, 100),
(39, 4, 18, 60.9, 68, 78),
(40, 4, 19, 76, 82, 73),
(41, 5, 19, 85.4, 86.2, 90),
(42, 5, 20, 61.5, 67, 72.5),
(43, 6, 20, 59.5, 63, 64.5),
(44, 6, 21, 75, 67.5, 60),
(45, 1, 21, 87.4, 83.2, 91.6),
(46, 1, 22, 59.5, 70, 68.5),
(47, 2, 22, 78.7, 85.5, 91.1),
(48, 1, 23, 75.8, 81.6, 80),
(49, 2, 24, 68.2, 76.1, 82),
(50, 3, 25, 82.4, 85.7, 88.1),
(51, 4, 26, 74, 67.2, 71.8),
(52, 5, 27, 78.3, 85.6, 91.8),
(53, 6, 28, 82.5, 72.8, 79.5);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `firstname` tinytext NOT NULL,
  `lastname` tinytext NOT NULL,
  `role` enum('STUDENT','TEACHER') NOT NULL,
  `email` tinytext NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `firstname`, `lastname`, `role`, `email`, `password`) VALUES
(1, 'Clint', 'Eastwood', 'STUDENT', 'clint.eastwood@snu.edu', '$2y$10$AGVYp3sgG2Ns2T/a2sGtW.qAT31N4ZT436eFTVQtSxA8PD0rF13ra'),
(2, 'Johny', 'Depp', 'STUDENT', 'johny.depp@snu.edu', '$2y$10$JnUUMyQ2.DW3fMzJTqVd.u74AFjdMvNZNEeoGQRN0YXAEGNJl9Xo.'),
(3, 'Tom', 'Cruise', 'STUDENT', 'tom.cruise@snu.edu', '$2y$10$rrHfUSb5JCeBcuWsUcgKiezkqvYhvE9HJrJOyX6sXAWiPREOCmvF2'),
(4, 'Matt', 'Damon', 'STUDENT', 'matt.damon@snu.edu', '$2y$10$vC8fUmll66SpqJrkPQHe7e4OrimTOVkOkOIHpjoVlGDD82UjFHiPy'),
(5, 'Bill', 'Murray', 'TEACHER', 'bill.murray@snu.edu', '$2y$10$wgC0DTv30dUFyoy2Uyum..O9lw/d6nu.gDqGjMO45ZArSmq2Yzeze'),
(6, 'Scarlett', 'Johansson', 'STUDENT', 'scarlett.johansson@snu.edu', '$2y$10$PtPPzx66ydePAv1MEAaWm.hb1JugSJB6aFeC2uoPo1Bmmi/VG7nYy'),
(7, 'Natalie', 'Portman', 'STUDENT', 'natalie.portman@snu.edu', '$2y$10$6l0lD9s7Lx7qfaFOtaWLpO3u57NZ16tDuAjzG9Sy7Zvt0RJohrply'),
(8, 'Ryan', 'Gosling', 'STUDENT', 'ryan.gosling@snu.edu', '$2y$10$NtzymHeTtjH8D8JUkUkADOZUi5emI5lnZzGJMyB0f0cgC/OlaII7W'),
(9, 'Jennifer', 'Lawrence', 'STUDENT', 'jennifer.lawrence@snu.edu', '$2y$10$TI9OgnOmbR7vLA0l0QsOjOWYETdQZ2pzERVQUFWV3/5xXQDMbOB7u'),
(10, 'Ed', 'Harris', 'TEACHER', 'ed.harris@snu.edu', '$2y$10$RocEWEXkwThJrry/n7oayOqct.hKfdcch/E0awn7gO/tdOQhheGyK'),
(11, 'Emma', 'Watson', 'STUDENT', 'emma.watson@snu.edu', '$2y$10$iVGxKGSp9chQES5hAYafuuvPkxwYKbIEc/NpzsKep7Plod7GpxEO6'),
(12, 'Julia', 'Roberts', 'STUDENT', 'julia.roberts@snu.edu', '$2y$10$pfRGCGR2ECAPLJlqij8tneLyKtUIu6nZSKMUdVBH1sq27fBGYf2p.'),
(13, 'Nicole', 'Kidman', 'STUDENT', 'nicole.kidman@snu.edu', '$2y$10$xMhD9qPdtAbkR3Ys7yzX6O3P/6ctufeSUXK./Y9w09Rwdkr13y/x2'),
(14, 'Angelina', 'Jolie', 'STUDENT', 'angelina.jolie@snu.edu', '$2y$10$/N2hktbgkBcAeosorEsWPOotcQIsW567rBUJmdR8aRo/Az7SXIVLy'),
(15, 'Morgan', 'Freeman', 'TEACHER', 'morgan.freeman@snu.edu', '$2y$10$MrIt12XLtBMQ57TBnfS0i.QuqCiFIZWRsg1qkOUQdg3AF9ye3kgLy'),
(16, 'Emma', 'Stone', 'STUDENT', 'emma.stone@snu.edu', '$2y$10$zD.l7b0TI1W6gAlpg9.En.iH4jjm17.LlYJIFlXjLXQl5Kp7KRdcK'),
(17, 'Zac', 'Efron', 'STUDENT', 'zac.efron@snu.edu', '$2y$10$PauXPW3S8F7bq6MFWC9.7eSAxuen5UjjPgsmVWmfEeMsrpwOuFxVu'),
(18, 'Josette', 'Johansson', 'STUDENT', 'josette.johansson@snu.edu', '$2y$10$8FeOVIPipwyRB0rq0UqoyuA8TcP6cgpKtP1d1Pkm992vwcW5GzM6y'),
(19, 'Maurice', 'Gosling', 'STUDENT', 'maurice.gosling@snu.edu', '$2y$10$npClczNDGbN2ZEtxBpR2JOKYyt3C68t2bjStvFqhQOytf7EeICykq'),
(20, 'Alphonse', 'Damon', 'STUDENT', 'alphonse.damon@snu.edu', '$2y$10$3Wz6JKTKlFutkXz99hSqbuPSN978Arsap0v61.uFKL3Q6SKugWBbG'),
(21, 'Josette', 'Watson', 'STUDENT', 'josette.watson@snu.edu', '$2y$10$JtdP.J4FnNlAF8l59hhu7e5l8VRMpxRx7uHQEUuOJJ5n020BeH6M.'),
(22, 'Raymonde', 'Johansson', 'STUDENT', 'raymonde.johansson@snu.edu', '$2y$10$YyNpUITRBf7j0ZOTdYQgL.0VeCC9iUJp39mYn03X5qUXz6ewnmQwi'),
(23, 'George', 'Clooney', 'STUDENT', 'george.clooney@snu.edu', '$2y$10$dPFDGTif0JicWXM9CYs2w.2Gc.QW.9FYejSW2k/yz6z.eHmFd5U2i'),
(24, 'Dwayne', 'Johnson', 'STUDENT', 'dwayne.johnson@snu.edu', '$2y$10$iGiOQqxjzCTCbdBi5gac8OK3QTxusf7wOHGTeyKvLX8G/lBSNJ5De'),
(25, 'Robert', 'Downey', 'STUDENT', 'robet.downey@snu.edu', '$2y$10$lOT0uqZpGt.hRgB2FabMmeefvI1At4pQpOOBuJfdgjGyVXBJcTwmK'),
(26, 'Chris', 'Hemsworth', 'STUDENT', 'chris.hemsworth@snu.edu', '$2y$10$R3xsPbJ5I1ngVAyL2UBKe.X0flYngQi7GXonVaBFlYUbFAXjAJoAa'),
(27, 'Jackie', 'Chan', 'STUDENT', 'jackie.chan@snu.edu', '$2y$10$RlXUrylQDM5jP/yOMkJQ1uZP9fbBD4LnuCNpvt5wtWxFMeWFmAmsa'),
(28, 'Will', 'Smith', 'STUDENT', 'will', 'smith@snu.edu');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`course_id`);

--
-- Indexes for table `scores`
--
ALTER TABLE `scores`
  ADD PRIMARY KEY (`score_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `courses`
--
ALTER TABLE `courses`
  MODIFY `course_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `scores`
--
ALTER TABLE `scores`
  MODIFY `score_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=54;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
