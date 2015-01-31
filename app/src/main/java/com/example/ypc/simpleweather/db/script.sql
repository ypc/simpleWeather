create table Province (
    id integer primary key autoincrement ,
    province_name text,
    province_code text)

--市
create table City (
    id integer primary key autoincrement,
    city_name text,
    city_code text,
    province_id integer)

--县
create table County (
    id integer primary key autoincrement,
    county_name text,
    county_code text,
    city_id integer)