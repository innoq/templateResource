--- name: find all -- no params
---
--- This query returns all people from the
--- people table.
---

select *
  from people;

--- name: find person by id
---
--- This query reads the summary-data for
--- a given id
---
--- Params:
--- :id -- The person-id

select * from person where id = :id

--- name: find person by undeclared id
---
--- This query reads the summary-data for
--- a given id, but this time the documentation
--- doesn't give a hint, that this query template
--- requires a parameter

select * from person where id = :id;


--- name: find people by name and city
---
--- Params:
--- :name -- person's name
--- :city -- city of birth

select * from people where name = :name and city = :city;


