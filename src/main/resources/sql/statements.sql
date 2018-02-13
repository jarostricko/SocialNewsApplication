
create table if not exists `FBPAGE` (
`id` int(11) NOT NULL,
`pagename` varchar(200) CHARACTER SET latin1 DEFAULT NULL,
`pageid` varchar(200) CHARACTER SET latin1 DEFAULT NULL
);

create table if not exists `CREDENTIALS` (
`id` int(11) NOT NULL,
`appid` varchar(200) CHARACTER SET latin1 DEFAULT NULL,
`appsecret` varchar(200) CHARACTER SET latin1 DEFAULT NULL
);

create table if not exists `CONFIG` (
`id` int(11) NOT NULL,
`fb_number_of_posts_to_fetch` int not null,
`yt_number_of_posts_to_fetch` int not null,
`old_max_days` int not null,
`newest_first` BIT(1),
`most_popular_first` BIT(1),
`facebook_posts_on` BIT(1),
`facebook_videos_on` BIT(1),
`youtube_based_on_channels_on` BIT(1),
`youtube_based_on_query_terms_on` BIT(1),
`video_order` varchar(255)
);

create table if not exists `CONFIG` (
`id` int(11) NOT NULL
);

create table if not exists `votes` (
`id` int(11) NOT NULL,
`date` datetime,
`disliked` bit,
`liked` bit,
`post_type` varchar(255),
`post_id` varchar(255)
);

insert into `config` (`id`,`fb_number_of_posts_to_fetch`,`yt_number_of_posts_to_fetch`,`old_max_days` ,`newest_first`,`most_popular_first`,
`facebook_posts_on`,`facebook_videos_on`,`youtube_based_on_channels_on`,`youtube_based_on_query_terms_on`,`video_order`) values 
(1,20,20,30,0,1,1,1,1,1,'rating');

update `config` set `fb_number_of_posts_to_fetch` = 7,`yt_number_of_posts_to_fetch`=8, `old_max_days` = 9,
 `newest_first` = 1, `most_popular_first` = 0,
 `facebook_posts_on` = 1,`facebook_videos_on` = 1,
 `youtube_based_on_channels_on` = 1, `youtube_based_on_query_terms_on` = 1, `video_order` = 'rating'
 where `id` = 1;
 
 INSERT INTO `teamproject`.`ytchannel`
(`id`,
`active`,
`channelid`,
`channelname`,
`keywords`)
VALUES
(2,1,'UCCjyq_K1Xwfg8Lndy7lKMpA','TechCrunch','');

UPDATE `teamproject`.`ytchannel`
SET
`id` = 1,
`active` = 0,
`channelid` = 'UCOmcA3f_RrH6b9NmcNa4tdg',
`channelname` = 'CNET',
`keywords` = ''
WHERE `id` = 1;




select * from `credentials`;

INSERT INTO `CREDENTIALS` (`id`,`fbappid`,`fbappsecret`,`youtubeapikey`) VALUES
(1,'805156846329043','413a2955941915026b62da5bc44c7de8','123');

update `CREDENTIALS` set `fbappid`='805156846329043',
`fbappsecret`='413a2955941915026b62da5bc44c7de8'
,`youtubeapikey`='AIzaSyCLVpGDz7Jp0kCMqx9s-T1q3xNz-zPNx04'
 where `id` = 1;


INSERT INTO `FBPAGE` (`id`, `pagename`,`pageid`,`active`) VALUES
(2, 'TechCrunch','8062627951',1);

UPDATE `teamproject`.`fbpage`
SET
`active` = 0,
`pageid` = '8062627951',
`pagename` = 'TechCrunch'
WHERE `id` = 2;


INSERT INTO `FBPAGE` (`id`, `pagename`,`pageid`,`active`) VALUES
(1, 'kpi.fei.tuke','1017923091578229',1);

select * from `votes`;

drop table `config`;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;


/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
