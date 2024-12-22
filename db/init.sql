INSERT INTO `service-architecture`.roles (id, name)
VALUES
    (1, 'User'),
    (2, 'Volunteer'),
    (3, 'Admin');

INSERT INTO `service-architecture`.status (id, name)
VALUES
    (1, 'Waiting'),
    (2, 'Validated'),
    (3, 'Rejected'),
    (4, 'Selected'),
    (5, 'Finished');

INSERT INTO `service-architecture`.users (id, id_role, username, password)
VALUES
    (1, 1, 'toto', 'toto'),
    (2, 2, 'helper', '1234'),
    (3, 3, 'admin', 'admin');