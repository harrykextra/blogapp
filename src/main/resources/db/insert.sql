SET FOREIGN_KEY_CHECKS = 0;

truncate table blog_post;
truncate table author;
truncate table comment;
truncate table author_posts;

INSERT INTO blog_post(id, title, content)
VALUES (41, 'title post 1', 'Post content 1'),
       (42, 'title post 2', 'Post content 2'),
       (43, 'title post 3', 'Post content 3'),
       (44, 'title post 4', 'Post content 4');

SET FOREIGN_KEY_CHECKS = 1;