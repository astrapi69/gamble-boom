create sequence hibernate_sequence;

create table drawn_numbers
(
    id         uuid not null,
    drawn_date timestamp,
    constraint drawn_numbers_pkey
        primary key (id)
);

create table drawn_numbers_lottery_numbers
(
    drawn_numbers_id uuid not null,
    lottery_number   integer,
    constraint fk_drawn_numbers_id
        foreign key (drawn_numbers_id) references drawn_numbers
);
