CREATE TABLE `fbpage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `active` bit(1) DEFAULT NULL,
  `pageid` varchar(255) DEFAULT NULL,
  `pagename` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

CREATE TABLE `config` (
  `id` int(11) NOT NULL,
  `facebook_posts_on` bit(1) DEFAULT NULL,
  `facebook_videos_on` bit(1) DEFAULT NULL,
  `fb_number_of_posts_to_fetch` int(11) DEFAULT NULL,
  `most_popular_first` bit(1) DEFAULT NULL,
  `newest_first` bit(1) DEFAULT NULL,
  `old_max_days` int(11) DEFAULT NULL,
  `post_time` int(11) DEFAULT NULL,
  `video_order` varchar(255) DEFAULT NULL,
  `youtube_based_on_channels_on` bit(1) DEFAULT NULL,
  `youtube_based_on_query_terms_on` bit(1) DEFAULT NULL,
  `yt_number_of_posts_to_fetch` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `credentials` (
  `id` int(11) NOT NULL,
  `fbappid` varchar(255) DEFAULT NULL,
  `fbappsecret` varchar(255) DEFAULT NULL,
  `youtubeapikey` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `votes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `disliked` bit(1) DEFAULT NULL,
  `liked` bit(1) DEFAULT NULL,
  `post_type` varchar(255) DEFAULT NULL,
  `post_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

CREATE TABLE `ytchannel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `active` bit(1) DEFAULT NULL,
  `channelid` varchar(255) DEFAULT NULL,
  `channelname` varchar(255) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


insert into `config` (`id`,`fb_number_of_posts_to_fetch`,`yt_number_of_posts_to_fetch`,`old_max_days` ,`newest_first`,`most_popular_first`,
`facebook_posts_on`,`facebook_videos_on`,`youtube_based_on_channels_on`,`youtube_based_on_query_terms_on`,`video_order`,`post_time`) values 
(1,20,20,30,0,1,1,1,1,1,'rating',5);

 INSERT INTO `ytchannel`
(`id`,
`active`,
`channelid`,
`channelname`,
`keywords`)
VALUES
(1,1,'UCCjyq_K1Xwfg8Lndy7lKMpA','TechCrunch','');

INSERT INTO `CREDENTIALS` (`id`,`fbappid`,`fbappsecret`,`youtubeapikey`) VALUES
(1,'805156846329043','413a2955941915026b62da5bc44c7de8','AIzaSyCLVpGDz7Jp0kCMqx9s-T1q3xNz-zPNx04');

INSERT INTO `FBPAGE` (`id`, `pagename`,`pageid`,`active`) VALUES
(1, 'TechCrunch','8062627951',1);



