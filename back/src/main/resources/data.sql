-- displayName, login, email
--INSERT INTO person(display_name, email, login, password) VALUES('Thomas Deville-Duc', 'thomas@gmail.com', 'tdevilleduc', 'password');
--INSERT INTO person(display_name, email, login, password) VALUES('Marion Gianesini', 'marion@gmail.com', 'mgianesini', 'password');
--INSERT INTO person(display_name, email, login, password) VALUES('Nicolas Danet', 'nicolas@gmail.com', 'ndanet', 'password');
-- USERS -- userId, displayName, login, email
INSERT INTO users(username, password) VALUES('tdevilleduc', 'password');
INSERT INTO users(username, password) VALUES('mgianesini', 'password');
INSERT INTO users(username, password) VALUES('ndanet', 'password');
-- PAGE -- storyId, image, text
INSERT INTO page(story_id, image, text) VALUES(1, 'image3', 'Ulysse');
INSERT INTO page(story_id, image, text) VALUES(1, 'image3', 'Dès');
INSERT INTO page(story_id, image, text) VALUES(1, 'image3', 'Le roman');
INSERT INTO page(story_id, image, text) VALUES(2, 'image3', 'Voyage au bout de la nuit est le premier roman de Céline, publié en 1932. Ce livre manqua de deux voix le prix Goncourt mais obtient le prix Renaudot1. Il est traduit en 37 langues2.');
INSERT INTO page(story_id, image, text) VALUES(2, 'image3', 'Le roman est notamment célèbre');
INSERT INTO page(story_id, image, text) VALUES(2, 'image3', 'Toutefois');
INSERT INTO page(story_id, image, text) VALUES(3, 'image3', 'Madame Bovary');
INSERT INTO page(story_id, image, text) VALUES(1, 'image2', 'En plein centre');
-- STORY -- authorId, firstPageId, title
INSERT INTO story(author_Id, first_Page_Id, title, detailed_Text, image) VALUES(1, 1, 'Ulysse', 'blablabla Ulysse prenons un texte long pour décrire lhistoire', 'imageUlysse');
INSERT INTO story(author_Id, first_Page_Id, title, detailed_Text, image) VALUES(2, 4, 'Voyage au bout de la nuit', 'bliblibli voyage voyage !!', 'imageVoyage');
INSERT INTO story(author_Id, first_Page_Id, title, detailed_Text, image) VALUES(3, 7, 'Madame Bovary', 'blablabla Bovary', 'imageBovary');
-- PROGRESSION -- progressionId, pageId, userId, storyId
INSERT INTO progression(id, actual_Page_Id, user_id, story_id) VALUES(1, 3, 1, 2);
INSERT INTO progression(id, actual_Page_Id, user_id, story_id) VALUES(2, 2, 2, 1);
INSERT INTO progression(id, actual_Page_Id, user_id, story_id) VALUES(3, 2, 1, 1);
INSERT INTO progression(id, actual_Page_Id, user_id, story_id) VALUES(5, 6, 3, 2);
INSERT INTO progression(id, actual_Page_Id, user_id, story_id) VALUES(6, 7, 3, 3);
INSERT INTO progression(id, actual_Page_Id, user_id, story_id) VALUES(7, 2, 2, 3);
INSERT INTO progression(id, actual_Page_Id, user_id, story_id) VALUES(8, 12, 3, 1);
-- NEXT_PAGE -- nextPageId, destinationPageId, pageId, position, text
INSERT INTO next_page VALUES(1, 2, 1, 0, 'gauche');
INSERT INTO next_page VALUES(2, 3, 1, 1, 'droite');
INSERT INTO next_page VALUES(3, 8, 1, 2, 'centre');
INSERT INTO next_page VALUES(4, 5, 4, 0, 'porte de gauche');
INSERT INTO next_page VALUES(5, 6, 4, 1, 'porte de droite');
-- ENEMY -- id, image, text
INSERT INTO enemy(name, image, level, life_Points) VALUES('Balrog', 'imageBalrog', 6, 25);
INSERT INTO enemy(name, image, level, life_Points) VALUES('Peon', 'imagePeon', 1, 2);
INSERT INTO enemy(name, image, level, life_Points) VALUES('Slim', 'imageSlim', 1, 3);
INSERT INTO enemy(name, image, level, life_Points) VALUES('Rat', 'imageRat', 1, 3);