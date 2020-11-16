create sequence hibernate_sequence;

create table draws
(
    id         uuid not null,
    drawn_date timestamp,
    constraint draws_pkey
        primary key (id)
);

create table draws_lottery_numbers
(
    draw_id        uuid not null,
    lottery_number integer,
    constraint fk_draws_id
        foreign key (draw_id) references draws
);
