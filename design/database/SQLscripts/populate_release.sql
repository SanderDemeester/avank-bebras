

-- Home Page Links
INSERT INTO links (title, url) VALUES
    ('Main site', 'http://www.bebras.be');
INSERT INTO links (title,url) VALUES
	('FAQ', '/faq');
INSERT INTO links (title,url) VALUES
	('Contact','/contact');

-- Pupil Grades
INSERT INTO grades (name, lowerbound, upperbound) VALUES
    ('Ewok',    10, 12),
    ('Wookie',  12, 14),
    ('Padawan', 14, 16),
    ('Jedi',    16, 18);

-- Difficulty levels
INSERT INTO difficulties (name, rank, cpoints, wpoints, npoints) VALUES
    ('Easy',   1, 5, -3, -1),
    ('Medium', 2, 7, -1,  0),
    ('Hard',   3, 10, 0,  1);

--Admin account (password is "XiphiasGladius", it is highly recommended you change this)
INSERT INTO users (id, name, gender, birthdate, registrationdate,
	preflanguage, password, hash, type) VALUES
	('admin',
	'Admin',
	'Other',
	'1970-01-01',
	'1970-01-01',
	'nl',
	'7eb7626b12f024f823c344684fe16fea4c10cd96',
	'9f8de52a23f94792bb448eb4f559e9d4',
	'ADMINISTRATOR'
	);
