-- personId, displayName, login, email
INSERT INTO person VALUES(1, 'Thomas Deville-Duc', 'thomas@gmail.com', 'tdevilleduc', 'password');
INSERT INTO person VALUES(2, 'Marion Gianesini', 'marion@gmail.com', 'mgianesini', 'password');
INSERT INTO person VALUES(3, 'Nicolas Danet', 'nicolas@gmail.com', 'ndanet', 'password');
-- pageId, image, text
INSERT INTO page(id, image, text) VALUES(1, 'image3', 'Ulysse');
INSERT INTO page(id, image, text) VALUES(2, 'image3', 'Dès');
INSERT INTO page(id, image, text) VALUES(3, 'image3', 'Le roman');
INSERT INTO page(id, image, text) VALUES(4, 'image3', 'Voyage au bout de la nuit est le premier roman de Céline, publié en 1932. Ce livre manqua de deux voix le prix Goncourt mais obtient le prix Renaudot1. Il est traduit en 37 langues2.');
INSERT INTO page(id, image, text) VALUES(5, 'image3', 'Le roman est notamment célèbre');
INSERT INTO page(id, image, text) VALUES(6, 'image3', 'Toutefois');
INSERT INTO page(id, image, text) VALUES(7, 'image3', 'Madame Bovary');
INSERT INTO page(id, image, text) VALUES(8, 'image2', 'En plein centre');
-- storyId, authorId, firstPageId, title
INSERT INTO story(story_Id, author_Id, first_Page_Id, title, detailed_Text, image) VALUES(1, 1, 1, 'Ulysse', 'blablabla Ulysse prenons un texte long pour décrire lhistoire', 'imageUlysse');
INSERT INTO story(story_Id, author_Id, first_Page_Id, title, detailed_Text, image) VALUES(2, 2, 4, 'Voyage au bout de la nuit', 'bliblibli voyage voyage !!', 'imageVoyage');
INSERT INTO story(story_Id, author_Id, first_Page_Id, title, detailed_Text, image) VALUES(3, 3, 7, 'Madame Bovary', 'blablabla Bovary', 'imageBovary');
-- progressionId, pageId, personId, storyId
INSERT INTO progression VALUES(1, 3, 1, 2);
INSERT INTO progression VALUES(2, 2, 2, 1);
INSERT INTO progression VALUES(3, 2, 1, 1);
INSERT INTO progression VALUES(5, 6, 3, 2);
INSERT INTO progression VALUES(6, 7, 3, 3);
INSERT INTO progression VALUES(7, 2, 2, 3);
INSERT INTO progression VALUES(8, 12, 3, 1);
-- nextPageId, destinationPageId, pageId, position, text
INSERT INTO next_page VALUES(1, 2, 1, 0, 'gauche');
INSERT INTO next_page VALUES(2, 3, 1, 1, 'droite');
INSERT INTO next_page VALUES(3, 8, 1, 2, 'centre');
INSERT INTO next_page VALUES(4, 5, 4, 0, 'porte de gauche');
INSERT INTO next_page VALUES(5, 6, 4, 1, 'porte de droite');
