CREATE TABLE IF NOT EXISTS User_address (
    Id SERIAL PRIMARY KEY,
    Last_name varchar(50) NOT NULL,
    First_name varchar(50) NOT NULL,
    Address varchar(100) NOT NULL,
    Phone varchar(20) NOT NULL UNIQUE
);

INSERT INTO User_address
    (Last_name,  First_name, Address,                                                       Phone)
VALUES
    ('Smith',    'Liam',     '140 Carson Rd, Battle Mountain, Nevada(NV), 89820',           '(775) 635-5207'),
    ('Johnson',  'Sophia',   '10345 Memphis Arlington Rd, Arlington, Tennessee(TN), 38002', '(901) 867-5102'),
    ('Williams', 'Noah',     '4058 Ellisboro Rd, Stokesdale, North Carolina(NC), 27357',    '(336) 548-0060'),
    ('Brown',    'Olivia',   '1108 NE 4th Ave, Mineral Wells, Texas(TX), 76067',            '(940) 325-2813'),
    ('Jones',    'Jackson',  '600 Hampshire Dr, Hampshire, Illinois(IL), 60140',            '(847) 683-3943'),
    ('Garcia',   'Riley',    '601 Dani Dr #9, Gallup, New Mexico(NM), 87301',               '(505) 863-6319'),
    ('Miller',   'Aiden',    '3971 S Morey Rd, Lake City, Michigan(MI), 49651',             '(231) 839-0077'),
    ('Davis',    'Emma',     '1862 Harpswell Islands Rd, Orrs Island, Maine(ME), 04066',    '(207) 833-2428')