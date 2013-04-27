
/* ========================================================================== *\
 *                                                                            *
 *                             DATABASE POPULATOR                             *
 *                             ------------------                             *
 *                                                                            *
 * This script populates the database. Of course, it's written for a certain  *
 * version of this database. The date of the last update is included, change  *
 * it along with the database.                                                *
 *                                                                            *
 *      | Date              | By                                              *
 *      `-----------------  `----------------------------                     *
 *       Sat Apr 13, 00:47   Felix Van der Jeugt                              *
 *                                                                            *
\* ========================================================================== */



/* -------------------------------------------------------------------------- *\
 *                              DATA MANAGEMENT                               *
\* -------------------------------------------------------------------------- */

-- Home Page Links
INSERT INTO links (title, url) VALUES
    ('Main site', 'http://www.bebras.be');

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
